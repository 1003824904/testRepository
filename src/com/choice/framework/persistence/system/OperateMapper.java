package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.Operate;
import com.choice.framework.domain.system.OperateType;

public interface OperateMapper {

	/**
	 * 通过id查询操作
	 * @param operate
	 * @return
	 */
	public Operate findOperateById(Operate operate);

	/**
	 * 通过模块id查询操作列表
	 * @param operate
	 * @return
	 */
	public List<Operate> findOperateByModuleId(Operate operate);

	/**
	 * 保存操作信息
	 * @param operate
	 */
	public void saveOperate(Operate operate);
	

	/**
	 * 修改操作信息
	 * @param operate
	 */
	public void updateOperate(Operate operate);
	
	/**
	 * 删除操作信息
	 * @param listId
	 */
	public void deleteOperate(List<String> listId);
	
	/**
	 * 查找操作种类列表
	 * @return
	 */
	public List<OperateType> findOperateTypeList();
}
