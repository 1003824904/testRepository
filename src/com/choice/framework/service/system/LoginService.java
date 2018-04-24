package com.choice.framework.service.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.LoginConstants;
import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.AccountRole;
import com.choice.framework.domain.system.Module;
import com.choice.framework.domain.system.Operate;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.AccountMapper;
import com.choice.framework.persistence.system.AccountRoleMapper;
import com.choice.framework.persistence.system.ModuleMapper;
import com.choice.framework.util.CacheUtil;
import com.choice.framework.util.CodeHelper;
import com.choice.framework.util.MD5;
import com.choice.framework.vo.AccountCache;

@Service
public class LoginService {
	
	private static Logger log = Logger.getLogger(LoginService.class);
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	/**
	 * 验证账号登录信息
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public String validateLogin(Account account) throws CRUDException{
		String info = StringConstant.TRUE;
		Account accountResult;
		
		//判断用户名是否为空
		if(account.getName() == null || account.getName().equals(""))
			return LoginConstants.MESSAGE_NAME_ISNULL;
		
		//判断用户密码是否为空
		else if(account.getPassword() == null || account.getPassword().equals(""))
			return LoginConstants.MESSAGE_PASSWORD_ISNULL;
		
		try{
			//判断用户名是否存在
			accountResult = accountMapper.findAccountByName(account.getName());
			if(accountResult == null 
					|| (accountResult.getId() == null || accountResult.getId().equals("")))
				return LoginConstants.MESSAGE_NAME_NOT_EXIST;
			
			//判断密码是否正确
			String password = account.getPassword();	//存放输入时的原始密码
			account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			accountResult = accountMapper.validatePassword(account);
			account.setPassword(password);	//将密码还原为输入时的密码
			if(accountResult == null 
					|| (accountResult.getId() == null || accountResult.getId().equals("")))
				return LoginConstants.MESSAGE_PASSWORD_ERROR;
			//账号状态未被审核
			account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			accountResult = accountMapper.validateStatus(account);
			account.setPassword(password);	//将密码还原为输入时的密码
			if(accountResult == null 
					|| (accountResult.getId() == null || accountResult.getId().equals("")))
				return LoginConstants.MESSAGE_STATUS_ERROR;
			
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		
		return info;
	}
	
	
	public AccountCache loadAuthorityInfo(Account account) throws CRUDException{
		//获取登录账号信息
		String password = account.getPassword();	//存放输入时的原始密码
		account.setPassword(MD5.md5(account.getName()+account.getPassword()));
		Account accountResult = accountMapper.validatePassword(account);
		account.setPassword(password);	//将密码还原为输入时的密码
		
		//获取登陆账号关联的角色信息
		AccountRole accountRole = new AccountRole();
		accountRole.setAccountId(accountResult.getId());
		List<AccountRole> accountRoleList = accountRoleMapper.findAccountRole(accountRole);
		
		//获取登陆账号拥有的模块和操作信息
		List<Module> oldModuleList = moduleMapper.findModuleByAccount(accountResult.getId());
		
		//组合模块信息（用于菜单显示）
		List<Module> moduleList = this.comboModule(oldModuleList);
		
		//获取登陆账号拥有的操作信息
		HashMap<String, HashMap<String, Boolean>> moduleOperateMap = this.comboOperate(oldModuleList);
		
		//TODO:获取登陆账号拥有的操作范围(部门)信息
		
		
		//TODO:获取登陆账号拥有的操作范围(人员)信息
		
		
		//清空账号的缓存信息，并重新添加账号的缓存信息
		CacheUtil cacheUtil = CacheUtil.getInstance();
		if(cacheUtil.get(StringConstant.ACCOUNT_CACHE, accountResult.getId()) != null){
			cacheUtil.flush(StringConstant.ACCOUNT_CACHE, accountResult.getId());
		}
		AccountCache accountCache = new AccountCache();
		accountCache.setAccount(accountResult);
		accountCache.setAccountRoleList(accountRoleList);
		accountCache.setModuleList(moduleList);
		accountCache.setModuleOperateMap(moduleOperateMap);
		cacheUtil.put(StringConstant.ACCOUNT_CACHE, accountResult.getId(), accountCache);
		
		return (AccountCache)cacheUtil.get(StringConstant.ACCOUNT_CACHE, accountResult.getId());
	}
	
	/**
	 * 获取需要显示的模块信息
	 * @param accountId
	 * @return
	 * @throws CRUDException
	 */
	private List<Module> comboModule(List<Module> moduleList) throws CRUDException{
		
		//如果模块为空，则直接返回一个空的模块列表
		if(moduleList == null || moduleList.size() == 0)
			return new ArrayList<Module>();
		
		List<String> listCode = new ArrayList<String>();
		
		//模块编号
		String moduleCode;
		
		//截取的模块编号
		String code;
		
		//存放“-”分割后的编号
		String[] codes ;
		
		//每一级编号的长度
		int len = CodeHelper.CODE_LENGTH;
		
		//截取编号的截止位置
		int endIndex = 0;
		
		//遍历拥有操作的模块（只有最后一层模块有操作，其它模块没有操作），
		//根据模块的编号进行拆分，找到模块对应的父类及祖先
		for(Module module : moduleList){
			moduleCode = module.getCode();
			codes = moduleCode.split("-");
			for(int i = 0,n = codes.length; i < n; i++){
				endIndex = len + (len+1)*i;
				code = moduleCode.substring(0, endIndex);
			//	System.out.println(code);
				if(!listCode.contains(code)){
					listCode.add(code);
				}
			}
		}
		
		return moduleMapper.findModuleByCodes(listCode);
	}
	
	/**
	 * 获取需要显示的操作信息
	 * @param moduleList
	 * @return
	 * @throws CRUDException
	 */
	private HashMap<String, HashMap<String, Boolean>> comboOperate(List<Module> moduleList) throws CRUDException{
		HashMap<String, HashMap<String, Boolean>> moduleOperateMap = new HashMap<String, HashMap<String,Boolean>>();
		
		//遍历模块，判断模块是否有url，并且url的结构是否符合“/controllerMapping/methodMapping.do”的形式。
		//如果满足上述条件，则遍历模块下的操作，并组合成“HashMap<模块Id, HashMap<操作Id, 是否拥有操作>>”的形式。
		for(Module module : moduleList){
			if(module.getUrl() != null && !module.getUrl().equals("")
				&& module.getUrl().split("/").length > 1){
					
				String key = module.getUrl().split("/")[1];
				HashMap<String, Boolean> operateMap = 
						moduleOperateMap.containsKey(key) ? moduleOperateMap.get(key) : new HashMap<String, Boolean>();
				for(Operate operate : module.getChildOperate()){
					operateMap.put(operate.getType(), Boolean.TRUE);
				}
				moduleOperateMap.put(key, operateMap);
			}
		}
		
		return moduleOperateMap;
	}
	
	/**
	 * 验证账号登录信息
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public String findUser(Account account) throws CRUDException{
		Account accountResult = accountMapper.findSingleAccount(account);
		if(null!=accountResult && null!=accountResult.getUser()){
			return accountResult.getUser().getName();
		}else{
			return "";
		}
	}
}
