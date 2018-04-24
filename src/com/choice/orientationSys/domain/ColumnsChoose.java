package com.choice.orientationSys.domain;
public class ColumnsChoose {
	private String id;//主键
	private String accountId;//账号ID
	private String dictColumnId;//表字段字典
	private Integer sequence;
	
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
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
	public String getDictColumnId() {
		return dictColumnId;
	}
	public void setDictColumnId(String dictColumnId) {
		this.dictColumnId = dictColumnId;
	}
}
