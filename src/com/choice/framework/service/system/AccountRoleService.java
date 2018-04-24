package com.choice.framework.service.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.choice.framework.constants.system.AccountConstants;
import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.AccountRole;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.AccountMapper;
import com.choice.framework.persistence.system.AccountRoleMapper;
import com.choice.framework.persistence.system.RoleMapper;
import com.choice.framework.util.CodeHelper;
import com.choice.framework.util.MD5;
import com.choice.orientationSys.constants.StringConstant;

@Service
public class AccountRoleService {
	
	private static Logger log = Logger.getLogger(AccountRoleService.class);
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;
	
	/**
	 * 查询账号角色关联信息
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public List<AccountRole> findAccountRole(AccountRole accountRole) throws CRUDException{
		try{
			return accountRoleMapper.findAccountRole(accountRole);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	
	/**
	 * 增加账号和角色的关联
	 * @param listId
	 * @throws CRUDException
	 */
	public void saveAccountRole(String roleIds, String accountId) throws CRUDException {
		
		//获取当前账号已经关联的角色信息
		HashMap<String,AccountRole> accountRoleMap = this.comboAccountRole(accountId);
		
		List<String> roleList = Arrays.asList(roleIds.split(","));
		try{
			//如果角色编号列表不为空，则遍历集合，并保存。
			if(roleList != null && roleList.size() > 0){
				AccountRole accountRole ;
				String id ;
				for(String roleId : roleList){
					//将当前账号和角色组合进行md5加密，这样，相同账号和角色对应的uuid始终是相同的
					id = MD5.md5(accountId + roleId);
					
					//如果此角色没有和当前账号关联，则需要保存；
					if(!accountRoleMap.containsKey(id)){
						accountRole = new AccountRole();
						
						accountRole.setId(id);
						accountRole.setAccountId(accountId);
						accountRole.setRoleId(roleId);
						
						accountRoleMapper.saveAccountRole(accountRole);
					}
				}
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 删除账号和角色的关联
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteAccountRole(String roleIds, String accountId) throws CRUDException {
		
		List<String> roleList = Arrays.asList(roleIds.split(","));
		try{
			//如果角色编号列表不为空，则遍历集合，并保存。
			if(roleList != null && roleList.size() > 0){
				AccountRole accountRole ;
				for(String roleId : roleList){
					accountRole = new AccountRole();
					
					accountRole.setAccountId(accountId);
					accountRole.setRoleId(roleId);
					
					//将当前账号和角色
					accountRoleMapper.deleteAccountRole(accountRole);
				}
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	

	/**
	 * 将获取的List集合转换成HashMap
	 * @param accountId
	 * @return
	 * @throws CRUDException
	 */
	private HashMap<String,AccountRole> comboAccountRole(String accountId) throws CRUDException{
		HashMap<String,AccountRole> accountRoleMap = new HashMap<String, AccountRole>();
		AccountRole ar = new AccountRole();
		ar.setAccountId(accountId);
		
		//获取当前账号已经关联的角色信息
		List<AccountRole> accountRoleList = accountRoleMapper.findAccountRole(ar);
		for(AccountRole accountRole : accountRoleList){
			accountRoleMap.put(accountRole.getId(), accountRole);
		}
		
		return accountRoleMap;
	}
	
}
