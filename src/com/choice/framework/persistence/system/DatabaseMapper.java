package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.Tables;

public interface DatabaseMapper {
	
	/**
	 * 查询所有数据库表格
	 * @return
	 */
	public List<Tables> queryTables();

}
