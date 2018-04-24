package com.choice.framework.domain.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.choice.orientationSys.constants.StringConstant;


public class User implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
    private String id;

    private String name;

    private String code;
    
	private String sex;

	private String state;

    private Date birthday;
    
    private String deleteFlag;
    
    private Department department;
    
    private List<Account> accountList;
    
    private AccountOther accountOther;
    
    /*
     * 构造方法
     * 删除标识（TRUE：已删除；FALSE：未删除）
     */
    public User(){
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public AccountOther getAccountOther() {
		return accountOther;
	}

	public void setAccountOther(AccountOther accountOther) {
		this.accountOther = accountOther;
	}


    
}
