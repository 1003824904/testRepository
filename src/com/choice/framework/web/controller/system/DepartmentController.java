package com.choice.framework.web.controller.system;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.DepartmentConstants;
import com.choice.framework.domain.system.Department;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.DepartmentService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private LogsMapper logsMapper;
	
	/**
	 * 查询所有部门
	 * @param modelMap
	 * @param department
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findAllDepartment(ModelMap modelMap, Department department, String departmentId,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源	
		List<Department> departmentList = departmentService.findDepartment(department);
		
		modelMap.put("departmentList", departmentList);
		
		if(departmentId == null || departmentId.equals(""))
			departmentId = StringConstant.ROOT_ID;
		
		modelMap.put("department", departmentService.findDepartmentById(departmentId));
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.SELECT,"查询所有部门",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(DepartmentConstants.LIST_DEPARTMENT, modelMap);
	}
	
	/**
	 * 根据编号（id）查询部门
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findDepartment(ModelMap modelMap, String id) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(id == null || id.equals("") 
			|| id.equals(StringConstant.ROOT_ID)){
			
			Department dept = new Department();
			Department parentDept = new Department();
			parentDept.setId(StringConstant.ROOT_ID);
			dept.setParentDepartment(parentDept);
			
			dept.setChildDepartmentList(departmentService.findDepartment(dept));
			modelMap.put("department", dept);
		}else{
			modelMap.put("department", departmentService.findDepartmentById(id));
		}
		
		return new ModelAndView(DepartmentConstants.TABLE_DEPARTMENT, modelMap);
	}
	
	
	/**
	 * 根据编号（viewcode）查询部门
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCode",method=RequestMethod.POST )
	@ResponseBody
	public String findViewCodeInDepartment(ModelMap modelMap, String viewcode, String parentDepartmentId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
			if(departmentService.findViewCodeInDepartment(viewcode, parentDepartmentId)) {
				return "T";
			}
			else {
				return "F";
			}
	}
	
	/**
	 * 新增部门
	 * @param modelMap
	 * @param department
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView addDepartment(ModelMap modelMap, Department department) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(DepartmentConstants.ADD_DEPARTMENT, modelMap);
	}
	
	/**
	 * 保存新增的部门
	 * @param modelMap
	 * @param department
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView saveDepartmentByAdd(ModelMap modelMap, Department department,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		departmentService.saveDepartment(department);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增部门:"+department.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 修改部门
	 * @param modelMap
	 * @param id
	 * @param department
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateDepartment(ModelMap modelMap, String id, Department department) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<Department> departmentList = departmentService.findDepartment(department);
		
		modelMap.put("departmentList", departmentList);
		modelMap.put("department", departmentService.findDepartmentById(id));
		
		return new ModelAndView(DepartmentConstants.UPDATE_DEPARTMENT, modelMap);
	}
	
	
	
	/**
	 * 保存修改的部门
	 * @param modelMap
	 * @param department
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByUpdate")
	public ModelAndView saveDepartmentByUpdate(ModelMap modelMap, Department department,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		department.setName(java.net.URLDecoder.decode(department.getName() , "UTF-8"));
		departmentService.updateDepartment(department);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改部门:"+department.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 删除部门信息
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, String id,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<String> listId = Arrays.asList(id.split(","));
		
		departmentService.deleteDepartment(listId);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除部门",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 选择部门
	 * @param modelMap
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/select")
	public ModelAndView selectDepartment(ModelMap modelMap, String code) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		Department department = new Department();
		department.setCode(code);
		List<Department> departmentList = departmentService.findDepartmentSelect(department);
		
		modelMap.put("departmentList", departmentList);
		
		return new ModelAndView(DepartmentConstants.SELECT_DEPARTMENT, modelMap);
	}
}

