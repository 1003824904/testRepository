package com.choice.framework.domain.system;

import java.util.Date;

public class Logs {
	
	private String id;

	private String accountId;
	
    private String accountname;
    
    private Date logindate;
    
    private Date logindateEnd;
    
	private String contents;

    private String events;
    
    private String ips;
	private String orderBy;//排序
	private String orderDes;
	private String program;
	
	public Logs() {
		super();
	}

	public Logs(String contents, String events) {
		super();
		this.contents = contents;
		this.events = events;
	}
	
	public Logs(String id, String accountId, Date logindate,String events, String contents,
			 String ips, String program) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.logindate = logindate;
		this.contents = contents;
		this.events = events;
		this.ips = ips;
		this.program = program;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public Date getLogindateEnd() {
		return logindateEnd;
	}

	public void setLogindateEnd(Date logindateEnd) {
		this.logindateEnd = logindateEnd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

}
