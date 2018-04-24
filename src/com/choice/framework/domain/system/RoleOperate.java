package com.choice.framework.domain.system;

import java.io.Serializable;

public class RoleOperate implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
	private String id;
	
	private String roleId;
	
	private String operateId;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the operateId
	 */
	public String getOperateId() {
		return operateId;
	}
	/**
	 * @param operateId the operateId to set
	 */
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	
}
