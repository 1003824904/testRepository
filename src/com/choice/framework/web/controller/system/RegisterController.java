package com.choice.framework.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.RegisterConstant;
import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.AccountRole;
import com.choice.framework.domain.system.Department;
import com.choice.framework.domain.system.Role;
import com.choice.framework.domain.system.User;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.service.system.RegisterService;
import com.choice.framework.service.system.RoleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;

@Controller
@RequestMapping(value = "register")
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	@Autowired
	private RoleService roleSerivce;
	
	@RequestMapping(value="initRegister")
	public ModelAndView initRegister(ModelMap modelMap, Department department,String departmentId) throws CRUDException{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("listRole", roleSerivce.findAllRole(new Role()));
		return new ModelAndView(RegisterConstant.INIT_REG,modelMap);
	}
	
	/**
	 * ajax保存账号
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxSaveByAdd", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveByAdd(@RequestParam String roleId, @RequestParam String name, @RequestParam String password,@RequestParam String userName,@RequestParam String departmentId) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		AccountRole accountRole = new AccountRole();
		accountRole.setRoleId(roleId);
		Account account = new Account();
		account.setName(name);
		account.setPassword(password);
		User user = new User();
		user.setName(userName);
		Department department = new Department();
		if("".equals(departmentId) || departmentId==null){
			departmentId = RegisterConstant.ROOT_DEPARTMENTID;
		}
		department.setId(departmentId);
		user.setDepartment(department);
		account.setUser(user);
		return registerService.saveAccount(account,accountRole,user);
		 
	}
}
