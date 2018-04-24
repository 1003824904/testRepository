package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.AccountRole;

public interface AccountRoleMapper {
	/**
	 * 查询账号角色的关联信息
	 * @param accountRole
	 * @return
	 */
	public List<AccountRole> findAccountRole(AccountRole accountRole);
	
	
	/**
	 * 设置账号和角色的关联
	 * @param accountRole
	 */
	public void saveAccountRole(AccountRole accountRole);
	
	/**
	 * 根据账号id和角色id，删除账号和角色的关联信息
	 * @param accountRole
	 */
	public void deleteAccountRole(AccountRole accountRole);
	
	/**
	 * 根据角色id列表，删除对应的账号和角色的关联信息
	 * @param accountRole
	 */
	public void deleteAccountRoleByRoleId(List<String> listId);
	
	
	/**
	 * 根据账号id列表，删除对应的账号和角色的关联信息
	 * @param accountRole
	 */
	public void deleteAccountRoleByAccountId(List<String> listId);
	
	
	
}