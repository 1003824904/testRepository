package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.Role;

public interface RoleMapper {

	/**
	 * 通过编号查询角色
	 * @param role
	 * @return
	 */
	public Role findRoleById(Role role);
	/**
	 * 通过条件查询角色列表
	 * @param role
	 * @return
	 */
	public List<Role> findAllRole(Role role);
	
	/**通过条件查询角色列表(包括时间权限信息)
	 * @param role
	 * @return
	 */
	public List<Role> findAllRoleAndTime(Role role);
	
	/**
	 * 查询所有的角色信息，并标记当前账户已关联的角色，用于界面显示
	 * @param accountId
	 * @return
	 */
	public List<Role> findRoleList(String accountId);
	/**
	 * 查询该账号的所有角色信息
	 * @param accountId
	 * @return
	 */
	public List<Role> findRoleMainTableList(String accountId);
	
	/**
	 * 保存角色信息
	 * @param role
	 */
	public void saveRole(Role role);
	/**
	 * 修改角色信息
	 * @param role
	 */
	public void updateRole(Role role);
	/**
	 * 删除角色信息
	 * @param listId
	 */
	public void deleteRole(List<String> listId);

}
