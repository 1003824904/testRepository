package com.choice.framework.web.controller.system;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.AccountRoleConstants;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.AccountRoleService;
import com.choice.framework.service.system.RoleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.framework.constants.StringConstant;
import com.choice.framework.domain.system.Logs;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "accountRole")
public class AccountRoleController {
	
	@Autowired
	private AccountRoleService accountRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private LogsMapper logsMapper;
	
	/**
	 * 根据编号（id）查询账号
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findUserAccount(ModelMap modelMap, String accountId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("roleList", roleService.findRoleList(accountId));
		modelMap.put("accountId", accountId);
		modelMap.put("ACCOUNT_ROLE_STATE", StringConstant.ACCOUNT_ROLE_STATE);
		
		return new ModelAndView(AccountRoleConstants.TABLE_ACCOUNT_ROLE, modelMap);
	}
	
	
	/**
	 * ajax增加账号角色信息
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxAddAccountRole", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxAddAccountRole(@RequestParam String roleIds, @RequestParam String accountId,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		accountRoleService.saveAccountRole(roleIds,accountId);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增账号和角色关联",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return StringConstant.TRUE;
	}
	
	/**
	 * ajax增加账号角色信息
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxDeleteAccountRole", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxDeleteAccountRole(@RequestParam String roleIds, @RequestParam String accountId,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		accountRoleService.deleteAccountRole(roleIds,accountId);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除账号和角色关联",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return StringConstant.TRUE;
	}
	
}

