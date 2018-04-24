package com.choice.framework.domain.system;

import java.io.Serializable;
import java.util.List;

import com.choice.orientationSys.constants.StringConstant;
/**
 * 模块实体
 * @author secret
 *
 */
public class Module implements Serializable{
	
	private static final long serialVersionUID = -5875698656374182743L;
	
	private String id;
	private String code;
	private String name;
	private Module parentModule;
	private List<Module> childModule;
	private int sequence;
	private String url;
	private String useable;
	private String showable;
	private List<Operate> childOperate;
	private String priparentId;   //原父模块 ，如果模块修改后的父模块与原父模块相同，不用进行修改模块的code

	public Module() {
		id = StringConstant.ROOT_ID;
		code = "";
		name = "";
		sequence = 0;
		useable = StringConstant.TRUE;
		showable = StringConstant.TRUE;
	}

	/**
	 * @return the childOperate
	 */
	public List<Operate> getChildOperate() {
		return childOperate;
	}

	/**
	 * @param childOperate the childOperate to set
	 */
	public void setChildOperate(List<Operate> childOperate) {
		this.childOperate = childOperate;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parentModule
	 */
	public Module getParentModule() {
		return parentModule;
	}

	/**
	 * @param parentModule
	 *            the parentModule to set
	 */
	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	/**
	 * @return the childModule
	 */
	public List<Module> getChildModule() {
		return childModule;
	}

	/**
	 * @param childModule
	 *            the childModule to set
	 */
	public void setChildModule(List<Module> childModule) {
		this.childModule = childModule;
	}

	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the useable
	 */
	public String getUseable() {
		return useable;
	}

	/**
	 * @param useable
	 *            the useable to set
	 */
	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShowable() {
		return showable;
	}

	public void setShowable(String showable) {
		this.showable = showable;
	}

	public String getPriparentId() {
		return priparentId;
	}

	public void setPriparentId(String priparentId) {
		this.priparentId = priparentId;
	}

}
