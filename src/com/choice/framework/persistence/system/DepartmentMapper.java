package com.choice.framework.persistence.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.choice.framework.domain.system.Department;

public interface DepartmentMapper {
	/**
	 * 查询所有部门
	 * @param department
	 * @return
	 */
	public List<Department> findDepartment(Department department);
	
	/**
	 * 根据部门id查询部门
	 * @param id
	 * @return
	 */
	public Department findDepartmentById(String id);
	
	/**
	 * 查询部门信息（过滤掉自身部门及子部门）
	 * @param department
	 * @return
	 */
	public List<Department> findDepartmentSelect(Department department);
	
	/**
	 * 保存部门
	 * @param department
	 */
	public void saveDepartment(Department department);
	
	/**
	 * 更新部门
	 * @param department
	 */
	public void updateDepartment(Department department);
	
	/**
	 * 更新部门显示的顺序
	 * @param department
	 */
	public void updateDepartmentSequence(Department department);
	
	/**
	 * 更新部门的code
	 * @param department
	 */
	public void updateChildCode(Department department);
	
	/**
	 * 获取部门code的最大值和父部门code
	 * @param department
	 * @return
	 */
	public HashMap<String, String> getMaxCode(Department department);
	
	/**
	 * 删除部门
	 * @param listId
	 */
	public void deleteDepartment(List<String> listId);
	
	/**
	 * 20111114增加部门对应人员
	 * @return
	 */
	public List<Department> findDepartmentUser();
	
	/**
	 * 根据编号（viewcode）查询部门
	 * @return
	 */
	public Department findViewCodeInDepartment(Department department);
}