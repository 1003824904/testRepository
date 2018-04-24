package com.choice.framework.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.RoleOperateRangeConstants;
import com.choice.framework.domain.system.Role;
import com.choice.framework.domain.system.RoleOperateRange;
import com.choice.framework.service.system.DepartmentService;
import com.choice.framework.service.system.RoleOperateRangeService;
import com.choice.framework.service.system.RoleOperateService;
import com.choice.framework.service.system.RoleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.orientationSys.constants.StringConstant;

@Controller
@RequestMapping(value = "roleOperateRange")
public class RoleOperateRangeController {

	@Autowired
	private RoleOperateService roleOperateService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private RoleOperateRangeService roleOperateRangeService;
	
	@Autowired
	private RoleService roleService;

	/**
	 * 初始化页面
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findAllRole(ModelMap modelMap, Role role)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		//角色信息
		modelMap.put("role", roleService.findRoleById(role));
		//角色操作列表，左侧树
		modelMap.put("roleOperateList", roleOperateService.findSelectRoleOperateList(role));
		//部门人员列表，右侧列表
		modelMap.put("departmentUserList", departmentService.findDepartmentUser());
		return new ModelAndView(RoleOperateRangeConstants.LIST_ROLEOPERATERANGE,modelMap);
	}

	/**
	 * 通过ajax在前台显示选中的操作范围
	 * @param roleoperaterange
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public RoleOperateRange addRoleOperateRange(@RequestBody RoleOperateRange roleoperaterange)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return roleOperateRangeService.findRoleOperateRangeOne(roleoperaterange);
	}
	
	/**
	 * 保存操作信息
	 * @param modelMap
	 * @param roleoperaterange
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView insertRoleOperateRange(ModelMap modelMap, RoleOperateRange roleoperaterange)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		roleOperateRangeService.saveRoleOperateRange(roleoperaterange);
		return new ModelAndView(StringConstant.ACTION_DONE,modelMap);
	}
}
