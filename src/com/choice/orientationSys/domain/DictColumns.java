package com.choice.orientationSys.domain;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope(value="prototype")
public class DictColumns {
	private String id;//主键
	private String tableName;//表名
	private String columnName;//列名
	private String zhColumnName;//列中文名
	private String properties;//对象属性
	private String accountId;//账号ID
	private String columnWidth;//列宽
	private String columnType;//列类型
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	private List<String> idList;//主键集合
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getZhColumnName() {
		return zhColumnName;
	}
	public void setZhColumnName(String zhColumnName) {
		this.zhColumnName = zhColumnName;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
}
