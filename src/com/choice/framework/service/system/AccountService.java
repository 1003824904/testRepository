package com.choice.framework.service.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.constants.system.AccountConstants;
import com.choice.framework.domain.system.Account;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.AccountMapper;
import com.choice.framework.persistence.system.AccountRoleMapper;
import com.choice.framework.util.CodeHelper;
import com.choice.framework.util.MD5;
import com.choice.framework.constants.StringConstant;

@Service
public class AccountService {
	
	private static Logger log = Logger.getLogger(AccountService.class);
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;
	
	/**
	 * 查询所有账号
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public List<Account> findAccount(Account account) throws CRUDException{
		try{
			return accountMapper.findAccount(account);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询单个账号
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public Account findSingleAccount(Account account) throws CRUDException{
		try{
			return accountMapper.findSingleAccount(account);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询业务员
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public List<Account> findAccountEmp(Account account) throws CRUDException{
		try{
			return accountMapper.findAccountEmp(account);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询单个业务员
	 * @param account
	 * @return
	 * @throws CRUDException
	 */
	public Account findAccountEmpById(String id) throws CRUDException{
		try{
			return accountMapper.findAccountEmpById(id);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 根据账号id查询账号
	 * @param id
	 * @return
	 * @throws CRUDException
	 */
	public Account findAccountById(String id) throws CRUDException{
		try{
			return accountMapper.findAccountById(id);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 保存账号
	 * @param account
	 * @throws CRUDException
	 */
	public String saveAccount(Account account) throws CRUDException{
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
			account.setState(StringConstant.STATE_ENABLE);
			
			if(account.getPassword() != null || !account.getPassword().equals(""))
				account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			else
				account.setPassword(MD5.md5(account.getName()+StringConstant.INIT_PASSWORD));
			
			accountMapper.saveAccount(account);
			info = StringConstant.TRUE;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		
		return info;
	}
	
	/**
	 * 验证更改账号密码
	 * @param account
	 * @throws CRUDException
	 */
	public String validatePassword(Account account) throws CRUDException{
		String info = StringConstant.FALSE;
		try{
			account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			Account accountResult = accountMapper.validatePassword(account);
			
			if(accountResult != null 
				&& accountResult.getId() != null 
				&& !accountResult.getId().equals("")){
				
				info = StringConstant.TRUE;
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		
		return info;
	}
	
	/**
	 * 更改账号密码
	 * @param account
	 * @throws CRUDException
	 */
	public String updatePassword(Account account) throws CRUDException{
		String info = StringConstant.FALSE;
		
		if(account.getPassword() == null || account.getPassword().equals(""))
			return AccountConstants.MESSAGE_PASSWORD_ISNULL;
		
		try{
			account.setPassword(MD5.md5(account.getName()+account.getPassword()));
			accountMapper.updatePassword(account);
			info = StringConstant.TRUE;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		
		return info;
	}
	
	/**
	 * 删除账号
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteAccount(String ids) throws CRUDException {
		List<String> listId = Arrays.asList(ids.split(","));
		try{
			accountMapper.deleteAccount(listId);
			accountRoleMapper.deleteAccountRoleByAccountId(listId);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 修改账号的状态
	 * @param listId
	 * @throws CRUDException
	 */
	public void updateAccountState(String state, String ids) throws CRUDException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> listId = Arrays.asList(ids.split(","));
		map.put("state", state);
		map.put("list", listId);
		
		try{
			accountMapper.updateAccountState(map);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
}
