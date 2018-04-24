package com.choice.framework.service.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Role;
import com.choice.framework.domain.system.RoleOperate;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.AccountRoleMapper;
import com.choice.framework.persistence.system.RoleMapper;
import com.choice.framework.persistence.system.RoleOperateMapper;
import com.choice.framework.persistence.system.RoleOperateRangeMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleOperateMapper roleOperateMapper;
	
	@Autowired
	private RoleOperateRangeMapper roleOperateRangeMapper;
	
	@Autowired
	private AccountRoleMapper accountRoleMapper;

	private final transient Log log = LogFactory.getLog(RoleService.class);

	/**
	 * 查询单个角色信息
	 * @param role
	 * @return
	 * @throws CRUDException
	 */
	public Role findRoleById(Role role) throws CRUDException {
		try {
			return roleMapper.findRoleById(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 查询全部角色信息
	 * @param role
	 * @return
	 * @throws CRUDException
	 */
	public List<Role> findAllRole(Role role) throws CRUDException {
		try {
			return roleMapper.findAllRole(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询所有的角色信息，并标记当前账户已关联的角色，用于界面显示
	 * @param accountId
	 * @return
	 */
	public List<Role> findRoleList(String accountId) throws CRUDException {
		try {
			return roleMapper.findRoleList(accountId);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 查询该账号的所有角色信息
	 * @param accountId
	 * @return
	 * @throws CRUDException
	 */
	public List<Role> findRoleMainTableList(String accountId) throws CRUDException {
		try {
			return roleMapper.findRoleMainTableList(accountId);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 保存角色信息
	 * @param role
	 * @throws CRUDException
	 */
	public void saveRole(Role role) throws CRUDException {
		try {
			role.setId(CodeHelper.createUUID());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			role.setMadedate(sdf.format(new Date()));
			roleMapper.saveRole(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 更新角色信息
	 * @param role
	 * @throws CRUDException
	 */
	public void updateRole(Role role) throws CRUDException {
		try {
			roleMapper.updateRole(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 删除角色，并且级联删除角色对应权限，角色对应权限操作范围
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteRole(List<String> listId) throws CRUDException {
		try {
			/**
			 * 查询需要删除的角色权限对应ids，用于级联删除操作权限
			 */
			List<RoleOperate> tempRoleOperateIds=roleOperateMapper.findRoleOperateListByRoleIds(listId);
			List<String> roleOperateIds =new ArrayList<String>();
			for (RoleOperate roleOperate : tempRoleOperateIds) {
				roleOperateIds.add(roleOperate.getId());
			}
			/**
			 * 以下是级联删除4个表q
			 */
			roleMapper.deleteRole(listId);
			roleOperateMapper.deleteRoleOperateByRoleId(listId);
			if (roleOperateIds.size()!=0) {
				roleOperateRangeMapper.deleteRoleOperateRange(roleOperateIds);
				accountRoleMapper.deleteAccountRoleByRoleId(listId);
			}
						
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

}
