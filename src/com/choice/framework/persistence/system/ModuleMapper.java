package com.choice.framework.persistence.system;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choice.framework.domain.system.Module;

/**
 * 模块mapper接口类
 * @author secret
 *
 */
public interface ModuleMapper {

	/**
	 * 按照id查询单个模块信息（不带上下级）
	 * @param module
	 * @return
	 */
	public Module findModuleById(Module module);
	
	/**
	 * 查询完整的模块信息（带上下级）
	 * @param module
	 * @return
	 */
	public Module findModuleAll(Module module);
	
	/**
	 * 查询模块列表
	 * @param module
	 * @return
	 */
	public List<Module> findModuleList(Module module);
	
	/**
	 * 查询模块树（过滤掉自身及子部门、存在操作的模块）
	 * @param module
	 * @return
	 */
	public List<Module> findModuleSelect(Module module);
	
	/**
	 * 查询账号拥有的模块信息
	 * @param accountId
	 * @return
	 */
	public List<Module> findModuleByAccount(String accountId);
	
	/**
	 * 根据编号查询模块信息
	 * @param listCode
	 * @return
	 */
	public List<Module> findModuleByCodes(List<String> listCode);
	
	/**
	 * 新增模块信息
	 * @param module
	 */
	public void saveModule(Module module);
	
	/**
	 * 跟新模块信息
	 * @param module
	 */
	public void updateModule(Module module);
	
	/**
	 * 更新当前节点下seq信息
	 * @param module
	 */
	public void updateSequence(Module module);
	
	/**
	 * 跟新所有下级模块code
	 * @param module
	 */
	public void updateChildCode(Module module);
	
	/**
	 * 删除模块信息（同时支持单删多删）
	 * @param listId
	 */
	public void deleteModule(List<String> listId);
	
	/**
	 * 计算当前模块code应该是多少
	 * @param module
	 * @return
	 */
	public Module findMaxCode(Module module);
	/**
	 * 根据多个id查询模块信息列表
	 * @param string
	 * @return
	 * @author ZGL
	 */
	public List<Module> findModuleByIds(@Param("idString") String idString);
	
}
