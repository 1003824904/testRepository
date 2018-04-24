package com.choice.framework.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.AccountRole;
import com.choice.framework.domain.system.Module;
import com.sun.corba.se.impl.orbutil.closure.Constant;

public class AccountCache implements Serializable{

	private static final long serialVersionUID = -5875698656374182743L;
	
	//账号
	private Account account;
	
	//账号关联的角色
	private List<AccountRole> accountRoleList;
	
	//账号拥有的模块
	private List<Module> moduleList;
	
	//账号拥有的操作
	//HashMap<String, HashMap<String, Boolean>>的含义：HashMap<模块Id, HashMap<操作Id, 是否拥有操作>>
	private HashMap<String, HashMap<String, Boolean>> moduleOperateMap;
	
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<AccountRole> getAccountRoleList() {
		return accountRoleList;
	}
	public void setAccountRoleList(List<AccountRole> accountRoleList) {
		this.accountRoleList = accountRoleList;
	}
	public List<Module> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}
	public HashMap<String, HashMap<String, Boolean>> getModuleOperateMap() {
		return moduleOperateMap;
	}
	public void setModuleOperateMap(
			HashMap<String, HashMap<String, Boolean>> moduleOperateMap) {
		this.moduleOperateMap = moduleOperateMap;
	}
	
}
