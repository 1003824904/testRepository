package com.choice.orientationSys.domain;

import java.util.List;

public class Dict {
	
	private static final long serialVersionUID = -7072712905566149491L;
	
	private String id ;
	private String enum_meaning ;
	private String enum_value ;
	private String parent_id ;
	private String deleteFlag ;
	private String is_initial ;
	private String remarks ;
	private Dict parentDict;
	private List<Dict> listDict;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnum_meaning() {
		return enum_meaning;
	}
	public void setEnum_meaning(String enum_meaning) {
		this.enum_meaning = enum_meaning;
	}
	public String getEnum_value() {
		return enum_value;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public void setEnum_value(String enum_value) {
		this.enum_value = enum_value;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getIs_initial() {
		return is_initial;
	}
	public void setIs_initial(String is_initial) {
		this.is_initial = is_initial;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Dict getParentDict() {
		return parentDict;
	}
	public void setParentDict(Dict parentDict) {
		this.parentDict = parentDict;
	}
	public List<Dict> getListDict() {
		return listDict;
	}
	public void setListDict(List<Dict> listDict) {
		this.listDict = listDict;
	}
	
	

	
}
