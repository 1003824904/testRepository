package com.choice.framework.web.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.UserConstants;
import com.choice.framework.domain.system.Department;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.domain.system.User;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.DepartmentService;
import com.choice.framework.service.system.UserService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
	public ModelAndView findAllDepartment(ModelMap modelMap, Department department, String departmentId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<Department> departmentList = departmentService.findDepartment(department);
		
		modelMap.put("departmentList", departmentList);
		
		if(departmentId == null || departmentId.equals(""))
			departmentId = StringConstant.ROOT_ID;
		
		modelMap.put("department", departmentService.findDepartmentById(departmentId));
		
		return new ModelAndView(UserConstants.LIST_USER, modelMap);
	}
	
	/**
	 * 根据编号（id）查询人员
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findUser(ModelMap modelMap, String departmentCode) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		User user = new User();
		Department department = new Department();
		department.setCode(departmentCode);
		user.setDepartment(department);
		
		modelMap.put("userList", userService.findUser(user));
		modelMap.put("SEX_RADIO", StringConstant.SEX_RADIO);
		
		return new ModelAndView(UserConstants.TABLE_USER, modelMap);
	}
	
	/**
	 * 新增人员
	 * @param modelMap
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView addUser(ModelMap modelMap) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("USER_STATE", StringConstant.USER_STATE);
		
		return new ModelAndView(UserConstants.ADD_USER, modelMap);
	}
	
	/**
	 * 保存新增的人员
	 * @param modelMap
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView saveUserByAdd(ModelMap modelMap, User user,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		userService.saveUser(user);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增人员:"+user.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 修改人员
	 * @param modelMap
	 * @param id
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateUser(ModelMap modelMap, String id, User user) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		
		List<User> userList = userService.findUser(user);
		
		modelMap.put("userList", userList);
		modelMap.put("user", userService.findUserById(id));
		
		return new ModelAndView(UserConstants.UPDATE_USER, modelMap);
	}
	
	
	
	/**
	 * 保存修改的人员
	 * @param modelMap
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByUpdate")
	public ModelAndView saveUserByUpdate(ModelMap modelMap, User user,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		userService.updateUser(user);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改人员:"+user.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 删除人员信息
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, String ids,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		userService.deleteUser(ids);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除人员",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
}

