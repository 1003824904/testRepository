package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.Logs;

public interface LogsMapper {
	/**
	 * 查询所有日志
	 * @param logs
	 * @return
	 */
	public List<Logs> findLogs(Logs logs);
	
	/**
	 * 删除日志
	 * @param logs
	 */
	public void deleteLogs(List<String> listId);
	
	/**
	 * 添加日志
	 * @param logs
	 */
	public void addLogs(Logs logs);
}
