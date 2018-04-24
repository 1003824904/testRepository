package com.choice.framework.domain.system;

import java.io.Serializable;
import java.util.List;

import com.choice.orientationSys.constants.StringConstant;


public class Department implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
    private String id;

    private String name;

    private String code;
    
	private String viewCode;

	private Integer sequence;

    private String deleteFlag;
    
    private Department parentDepartment;
    
    private List<Department> childDepartmentList;
    /**
     * 20111114部门下人员列表
     */
    private List<User> userList;
    
    //构造方法，给删除标识附默认值（TRUE：已删除；FALSE：未删除）
    public Department(){
    	deleteFlag = StringConstant.FALSE;
    }

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
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

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public List<Department> getChildDepartmentList() {
		return childDepartmentList;
	}

	public void setChildDepartmentList(List<Department> childDepartmentList) {
		this.childDepartmentList = childDepartmentList;
	}

    
}
