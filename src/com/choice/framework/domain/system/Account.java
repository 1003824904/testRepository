package com.choice.framework.domain.system;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.choice.orientationSys.constants.StringConstant;

public class Account implements Serializable {
	
	private static final long serialVersionUID = -5875698656374182743L;
	
    private String id;

    private String name;
    
    private String password;

	private String state;

    private String deleteFlag;
    
    private String code;
    
    private String names;
    
    private String sex;
    
    private Date birthday;
    
    private String positncode;
    
    private String ySail;
    
    private User user;
    
    //所属角色
    private List<Role> roleList;
    /*
     * 构造方法
     * 删除标识（TRUE：已删除；FALSE：未删除）
     */
    public Account(){
    	deleteFlag = StringConstant.FALSE;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPositncode() {
		return positncode;
	}

	public void setPositncode(String positncode) {
		this.positncode = positncode;
	}

	public String getySail() {
		return ySail;
	}

	public void setySail(String ySail) {
		this.ySail = ySail;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
