package com.choice.framework.domain.system;

import java.io.Serializable;

public class RoleOperateRange implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
	private String id;
	
	private String role_operateId;
	
	private String departmentIds;
	
	private String userIds;
	
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
	 * @return the role_operateId
	 */
	public String getRole_operateId() {
		return role_operateId;
	}
	/**
	 * @param role_operateId the role_operateId to set
	 */
	public void setRole_operateId(String role_operateId) {
		this.role_operateId = role_operateId;
	}
	/**
	 * @return the departmentIds
	 */
	public String getDepartmentIds() {
		return departmentIds;
	}
	/**
	 * @param departmentIds the departmentIds to set
	 */
	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}
	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
}
