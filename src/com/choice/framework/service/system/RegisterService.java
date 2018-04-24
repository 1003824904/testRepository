package com.choice.framework.service.system;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.AccountConstants;
import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.AccountRole;
import com.choice.framework.domain.system.User;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.AccountMapper;
import com.choice.framework.persistence.system.AccountRoleMapper;
import com.choice.framework.persistence.system.UserMapper;
import com.choice.framework.util.CodeHelper;
import com.choice.framework.util.MD5;
import com.choice.orientationSys.util.Util;

@Service
public class RegisterService {
private static Logger log = Logger.getLogger(AccountService.class);
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 保存账号
	 * @param account
	 * @throws CRUDException
	 */
	public String saveAccount(Account account,AccountRole accountRole,User user) throws CRUDException{
		String info = StringConstant.FALSE;
		
		/*
		 *	判断账号名称是否为空。
		 *	(1)为空则直接返回提示信息；
		 *	(2)不为空，则根据名称查询账号，如果得到返回结果，证明系统中存在此账号，
		 *		直接返回提示信息，需要用户重新输入账号名称
		 * 
		 */
		if(account.getName() != null && !account.getName().equals("")){
			Account accountResult = accountMapper.findAccountByName(account.getName());
			if(accountResult != null 
				&& (accountResult.getId() != null && !accountResult.getId().equals(""))){
				return AccountConstants.MESSAGE_NAME_EXIST;
			}
		}else{
			return AccountConstants.MESSAGE_NAME_ISNULL;
		}
		
		try{
			account.setId(CodeHelper.createUUID());
			account.setState(StringConstant.STATE_DISABLE);
			
			if(account.getPassword() != null || !account.getPassword().equals(""))
				account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			else
				account.setPassword(MD5.md5(account.getName()+StringConstant.INIT_PASSWORD));
			
			//保存用户
			user.setId(Util.getUUID());
			userMapper.saveUser(user);
			account.setUser(user);
			accountMapper.saveAccount(account);
			accountRole.setId(CodeHelper.createUUID());
			accountRole.setAccountId(account.getId());
			//保存账号的角色
			accountRoleMapper.saveAccountRole(accountRole);
			
			info = StringConstant.TRUE;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		
		return info;
	}
}
