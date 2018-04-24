package com.choice.framework.domain.system;


import java.io.Serializable;

import com.choice.orientationSys.constants.StringConstant;


public class AccountRole implements Serializable{
	
	private static final long serialVersionUID = -5875698656374182743L;
	
    private String id;

    private String accountId;
    
    private String roleId;

    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
