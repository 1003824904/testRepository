package com.choice.framework.domain.system;

import java.io.Serializable;

/**
 * 角色模块对象
 * @author 
 *
 */
public class Role implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
	/**
	* 角色编号
	*/
	private String id;
	/**
	* 角色名称
	*/
	private String name;
	/**
	* 是否使用
	*/
	private String deleteFlag;
	private String madedate;
	/**
	 * 新加入   我的工作桌面
	 */
	private String menu;
	
	public Role() {
		id = "";
		name = "";
		deleteFlag = "";
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

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMadedate() {
		return madedate;
	}

	public void setMadedate(String madedate) {
		this.madedate = madedate;
	}

}

