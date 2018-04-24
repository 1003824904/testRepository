package com.choice.framework.service.system;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.domain.system.Department;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.DepartmentMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class DepartmentService {
	
	private static Logger log = Logger.getLogger(DepartmentService.class);
	
	@Autowired
	private DepartmentMapper departmentMapper;

	/**
	 * 查询所有部门
	 * @param department
	 * @return
	 * @throws CRUDException
	 */
	public List<Department> findDepartment(Department department) throws CRUDException{
		try{
			return departmentMapper.findDepartment(department);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 根据部门id查询部门
	 * @param id
	 * @return
	 * @throws CRUDException
	 */
	public Department findDepartmentById(String id) throws CRUDException{
		try{
			return departmentMapper.findDepartmentById(id);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询部门信息（过滤掉自身部门及子部门）
	 * @param department
	 * @return
	 * @throws CRUDException
	 */
	public List<Department> findDepartmentSelect(Department department) throws CRUDException{
		try{
			return departmentMapper.findDepartmentSelect(department);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 保存部门
	 * @param department
	 * @throws CRUDException
	 */
	public void saveDepartment(Department department) throws CRUDException{
		try{
			department.setId(CodeHelper.createUUID());
			department.setDeleteFlag(StringConstant.FALSE); 
			this.updateCode(department);
			
			departmentMapper.updateDepartmentSequence(department);
			departmentMapper.saveDepartment(department);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 更新部门
	 * @param department
	 * @throws CRUDException
	 */
	public void updateDepartment(Department department) throws CRUDException{
		try{
			Department oldDept = departmentMapper.findDepartmentById(department.getId());
			
			//如果改变了上级部门，需要更新部门的code
			if(oldDept.getParentDepartment() != null
				&& oldDept.getParentDepartment().getId() != null
				&& !oldDept.getParentDepartment().getId().equals("")
				&& !oldDept.getParentDepartment().getId().equals(department.getParentDepartment().getId())){
				
				this.updateCode(department);
			}
			
			if(!oldDept.getSequence().equals(department.getSequence()))
				departmentMapper.updateDepartmentSequence(department);
			
			departmentMapper.updateDepartment(department);
			
			//更新部门下面嵌套包含的所有子部门的code
			department.getParentDepartment().setCode(oldDept.getCode());
			departmentMapper.updateChildCode(department);
			
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 删除部门
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteDepartment(List<String> listId) throws CRUDException {
		try{
			departmentMapper.deleteDepartment(listId);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 查询部门用户列表20111114增加
	 * @return
	 * @throws CRUDException
	 */
	public List<Department> findDepartmentUser()throws CRUDException{
		try{
			return departmentMapper.findDepartmentUser();
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 根据编号（viewcode）查询部门
	 * @return
	 * @throws CRUDException
	 */
	public Boolean findViewCodeInDepartment(String viewcode, String parentDepartmentId)throws CRUDException{
		try{
			Department department = new Department();
			department.setViewCode(viewcode);
			Department parentDepartment = new Department();
			parentDepartment.setId(parentDepartmentId);
			department.setParentDepartment(parentDepartment);
			if (departmentMapper.findViewCodeInDepartment(department)==null) {
				return false;
			}
			return true;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	
	/**
	 * 更新部门code
	 * @param department
	 * @throws CRUDException
	 */
	private void updateCode(Department department) throws CRUDException {
		String maxCode = "";
		String parentCode = "";
		HashMap<String,String> map = departmentMapper.getMaxCode(department);
		
		if(map != null && map.size() > 0){
			if(map.containsKey("maxCode"))
				maxCode = map.get("maxCode");
			if(map.containsKey("parentCode"))
				parentCode = map.get("parentCode");
		}
		
		department.setCode(CodeHelper.getNextCode(maxCode, parentCode));
	}
}
