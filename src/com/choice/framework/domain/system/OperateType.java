package com.choice.framework.domain.system;

import java.io.Serializable;

public class OperateType implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	
	public String id;
	public String name;
	
	public OperateType(){
		id="";
		name="";
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
