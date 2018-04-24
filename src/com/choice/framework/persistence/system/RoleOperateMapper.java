package com.choice.framework.persistence.system;

import java.util.HashMap;
import java.util.List;

import com.choice.framework.domain.system.Module;
import com.choice.framework.domain.system.Role;
import com.choice.framework.domain.system.RoleOperate;

public interface RoleOperateMapper {
	/**
	 * 通过模块和操作的对应关系查询，用于角色分配权限左边的模块操作树
	 * @return
	 */
	public List<Module> findModuleOperateForTree();
	/**
	 * 通过点击右侧树查询需要向右侧添加那些模块以及操作
	 * @param module
	 * @return
	 */
	public List<Module> findModuleOperateForAddList(Module module);
	/**
	 * 通过角色查询角色已经有的操作信息，用于第一次进入页面时加载操作信息
	 * @param role
	 * @return
	 */
	public List<Module> findRoleOperateList(Role role);
	/**
	 * 通过角色id数组查询所有对应的role_operate id
	 * @param listId
	 * @return
	 */
	public List<RoleOperate> findRoleOperateListByRoleIds(List<String> listId);
	/**
	 * 通过角色id数组查询角色权限，获得角色权限调整后需要删除的角色权限范围对应的role_operate id
	 * 用于获得删除role_operate_range表的role_operate id
	 * @param listId
	 * @return
	 */
	public List<RoleOperate> findDeleteRoleOperateIdsListByRoleIds(HashMap hashMap);
	/**
	 * 查询已经选中的模块和操作
	 * @param role
	 * @return
	 */
	public List<Module> findSelectRoleOperateList(Role role);	
	/**
	 * 保存操作-角色对应信息
	 * @param roleOperate
	 */
	public void insertRoleOperate(RoleOperate roleOperate);
	/**
	 * 删除角色-操作对应信息（按照角色Id）
	 * @param roleId
	 */
	public void deleteRoleOperateByRoleId(List<String> listId);
}
