package com.choice.framework.persistence.system;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choice.framework.domain.system.Account;

public interface AccountMapper {
	/**
	 * 查询所有账号
	 * @param account
	 * @return
	 */
	public List<Account> findAccount(Account account);
	/**
	 * 查询账号
	 * @param account
	 * @return
	 */
	public Account findSingleAccount(Account account);
	/**
	 * 查询业务员
	 * @param account
	 * @return
	 */
	public List<Account> findAccountEmp(Account account);
	
	/**
	 * 根据ID查询业务员
	 * @return
	 */
	public Account findAccountEmpById(@Param("id")String id);
	/**
	 * 根据账号id查询账号
	 * @param id
	 * @return
	 */
	public Account findAccountById(String id);
	
	/**
	 * 根据账号name查询账号
	 * @param id
	 * @return
	 */
	public Account findAccountByName(String name);
	
	/**
	 * 验证账号密码
	 * @param account
	 * @return
	 */
	public Account validatePassword(Account account);
	/**
	 * 验证账号启用
	 * @param account
	 * @return
	 */
	public Account validateStatus(Account account);
	/**
	 * 保存账号
	 * @param account
	 */
	public void saveAccount(Account account);
	
	/**
	 * 更新账号密码
	 * @param account
	 */
	public void updatePassword(Account account);
	
	/**
	 * 修改账号状态
	 * @param map
	 */
	public void updateAccountState(HashMap<String, Object> map);
	
	
	/**
	 * 删除账号
	 * @param listId
	 */
	public void deleteAccount(List<String> listId);
	
	
}