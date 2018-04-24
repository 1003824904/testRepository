package com.choice.framework.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.RoleOperateConstants;
import com.choice.framework.domain.system.Module;
import com.choice.framework.domain.system.Role;
import com.choice.framework.service.system.OperateService;
import com.choice.framework.service.system.RoleOperateService;
import com.choice.framework.service.system.RoleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.Local;
import com.choice.orientationSys.constants.StringConstant;

@Controller
@RequestMapping(value = "roleOperate")
public class RoleOperateController {

	@Autowired
	private RoleOperateService roleOperateService;

	@Autowired
	private OperateService opeateService;
	
	@Autowired
	private RoleService roleService;
	/**
	 * 角色-操作对应页面
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findAllRole(ModelMap modelMap, Role role)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("role", roleService.findRoleById(role));
		modelMap.put("tree", roleOperateService.findModuleOperateForTree());
		//查询角色现有的操作列表
		modelMap.put("roleOperateList",roleOperateService.findRoleOperateList(role));
		modelMap.put("operateType", opeateService.findOperateTypeList());
		return new ModelAndView(RoleOperateConstants.LIST_ROLEOPERATE, modelMap);
	}
	/**
	 * 通过ajax方式添加需要对应的操作信息
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public List<Module> addRoleOperate(@RequestBody Module module)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		 List<Module> moduleList=roleOperateService.findModuleOperateForAddList(module);
		 for (int i = 0; i < moduleList.size(); i++) {
			 moduleList.get(i).setName(Local.show(moduleList.get(i).getName()));  //国际化转
		}
		return moduleList;
	}
	/**
	 * 保存选择对应的操作信息，全删全增
	 * @param modelMap
	 * @param roleOperateId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView save(ModelMap modelMap, String roleOperateId,
			String roleId) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		roleOperateService.insertRoleOperate(roleOperateId, roleId);
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	/**
	 * 角色-我的工作桌面对应页面
	 * @param modelMap
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listMainTable")
	public ModelAndView findAllMainTable(ModelMap modelMap, Role role)
			throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("role", roleService.findRoleById(role));
		return new ModelAndView(RoleOperateConstants.LIST_ROLE_MAIN_TABLE, modelMap);
	}
}
