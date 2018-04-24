package com.choice.orientationSys.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.choice.framework.util.ForResourceFiles;
import com.choice.orientationSys.constants.StringConstant;

@Component
@Scope(value="prototype")
public class Page {
	private int nowPage;//当前�?
	private int pageSize;//每页显示的记录数
	private int count;//总记录数
	private int maxSize;//每页最大显示的记录数
	private String queryKey;//
	private String sql;

	public Page() {
		nowPage = 1;
		pageSize = 20;
	}

	//从第几条数据�?��
	public int getOffset() {
		
		int tempOffest=(nowPage - 1) * pageSize;
		if(tempOffest < count){
			return tempOffest;
		}else{
			return count / pageSize;
		}
	}

	//当前页显示几条数�?
	public int getLimit() {
		if ((count - getOffset()) > pageSize) {
			return pageSize;
		} else {
			return count - getOffset();
		}
	}

	public int getPageCount() {
		if(count<=pageSize){
			return 1;
		}else if(count%pageSize==0){
			return count / pageSize;
		}else{
			
			return count / pageSize+1;
			
		}

	}

	public int getMaxSize() {
//		maxSize=Integer.parseInt(ForResourceFiles.getValByKey("","MAXSIZE"));
		maxSize=Integer.parseInt(ForResourceFiles.getValBySQL(StringConstant.MAXSIZE));
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setCount(int Count) {
		this.count = Count;
	}
	public int getCount(){
		return count;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the queryKey
	 */
	public String getQueryKey() {
		return queryKey;
	}

	/**
	 * @param queryKey the queryKey to set
	 */
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
