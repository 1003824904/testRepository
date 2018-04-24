package com.choice.framework.domain.system;

import java.io.Serializable;

import com.choice.orientationSys.constants.StringConstant;

public class Operate implements Serializable{
	private static final long serialVersionUID = -5875698656374182743L;
	public String id;
	public String name;
	public Module module;
	public String useable;
	public String type;
	
	public Operate(){
		id="";
		name="";
		useable=StringConstant.TRUE;
		type="";
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}

	/**
	 * @return the useable
	 */
	public String getUseable() {
		return useable;
	}

	/**
	 * @param useable the useable to set
	 */
	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	
}
