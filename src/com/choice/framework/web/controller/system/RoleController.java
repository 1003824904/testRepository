package com.choice.framework.web.controller.system;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.RoleConstants;
import com.choice.framework.constants.system.RoleOperateConstants;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.domain.system.Role;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.RoleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private LogsMapper logsMapper;
	
	/**
	 * 角色列表
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findAllRole(ModelMap modelMap, Role role)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("roleList", roleService.findAllRole(role));
		modelMap.put("queryRole",role);
		return new ModelAndView(RoleConstants.LIST_ROLE, modelMap);
	}

	/**
	 * 打开新增页面
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(ModelMap modelMap, Role role) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(RoleConstants.SAVE_ROLE, modelMap);
	}
	/**
	 * 保存角色
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView save(ModelMap modelMap, Role role,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		roleService.saveRole(role);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增角色信息:"+role.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	/**
	 * 打开修改页面
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(ModelMap modelMap, Role role) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("role", roleService.findRoleById(role));
		return new ModelAndView(RoleConstants.UPDATE_ROLE, modelMap);
	}
	/**
	 * 修改角色信息
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByUpdate")
	public ModelAndView saveByUpdate(ModelMap modelMap, Role role,HttpSession session)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		roleService.updateRole(role);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改角色信息:"+role.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	/**
	 * 删除角色信息
	 * @param modelMap
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, String roleId,Role role,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<String> ids = Arrays.asList(roleId.split(","));
		roleService.deleteRole(ids);
		modelMap.put("roleList", roleService.findAllRole(role));
		modelMap.put("queryRole",role);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除角色信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(RoleConstants.LIST_ROLE, modelMap);
	}
	@RequestMapping(value = "/saveByUpdateMainTable")
	public ModelAndView saveByUpdateMainTable(ModelMap modelMap, Role role)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		roleService.updateRole(role);
		modelMap.put("role", role);
		modelMap.put("result", "success");
		return new ModelAndView(RoleOperateConstants.LIST_ROLE_MAIN_TABLE, modelMap);
	}
}
