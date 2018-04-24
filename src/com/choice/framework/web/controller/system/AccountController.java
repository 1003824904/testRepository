package com.choice.framework.web.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.AccountConstants;
import com.choice.framework.domain.system.Department;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.domain.system.User;
import com.choice.framework.domain.system.Account;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.DepartmentService;
import com.choice.framework.service.system.UserService;
import com.choice.framework.service.system.AccountService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.framework.constants.StringConstant;
import com.choice.orientationSys.util.ReadProperties;
import com.choice.orientationSys.util.Util;


@Controller
@RequestMapping(value = "account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
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
		
		return new ModelAndView(AccountConstants.LIST_ACCOUNT, modelMap);
	}
	/**
	 * 查询所有信息
	 */
	@RequestMapping("/findAccount")
	@ResponseBody
	public Object findAllDepartment(ModelMap modelMap, Account account) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<Account> listAccount=accountService.findAccountEmp(account);
		return listAccount;
	}
	/**
	 * 根据编号（id）查询账号
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findUserAccount(ModelMap modelMap, String departmentCode) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("departmentCode", departmentCode);
		
		modelMap.put("userList", userService.findUserAccount(map));
		modelMap.put("SEX_RADIO", StringConstant.SEX_RADIO);
		modelMap.put("ACCOUNT_STATE", StringConstant.ACCOUNT_STATE);
		
		ReadProperties rp = new ReadProperties();
		String choice = rp.getStrByParam("choice");
		modelMap.put("choice", choice);
		return new ModelAndView(AccountConstants.TABLE_ACCOUNT, modelMap);
	}
	
	/**
	 * 新增账号
	 * @param modelMap
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView addAccount(ModelMap modelMap, String userId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("user", userService.findUserById(userId));
		
		return new ModelAndView(AccountConstants.ADD_ACCOUNT, modelMap);
	}
	
	/**
	 * 修改账号
	 * @param modelMap
	 * @param id
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateAccount(ModelMap modelMap, String id) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("account", accountService.findAccountById(id));
		
		return new ModelAndView(AccountConstants.UPDATE_ACCOUNT, modelMap);
	}
	
	@RequestMapping(value="/updatePassword")
	public ModelAndView updatePassword(ModelMap modelMap,HttpSession session) throws CRUDException{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("account", accountService.findAccountById((String)session.getAttribute("accountId")));
		
		return new ModelAndView(AccountConstants.UPDATE_ACCOUNT, modelMap);
	}
	/**
	 * 根据编号（id）查询账号(来源于用户界面)
	 * @param modelMap
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tableFromUser")
	public ModelAndView findAccountFromUser(ModelMap modelMap, String userId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		Account account = new Account();
		User user = new User();
		user.setId(userId);
		account.setUser(user);
		
		modelMap.put("accountList", accountService.findAccount(account));
		modelMap.put("ACCOUNT_STATE", StringConstant.ACCOUNT_STATE);
		modelMap.put("userId", userId);
		
		return new ModelAndView(AccountConstants.TABLE_ACCOUNT_FROM_USER, modelMap);
	}
	
	/**
	 * 新增账号(来源于用户界面)
	 * @param modelMap
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFromUser")
	public ModelAndView addAccountFromUser(ModelMap modelMap, String userId) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("user", userService.findUserById(userId));
		
		return new ModelAndView(AccountConstants.ADD_ACCOUNT, modelMap);
	}
	
	/**
	 * 修改账号(来源于用户界面)
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFromUser")
	public ModelAndView updateAccountFromUser(ModelMap modelMap, String id) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("account", accountService.findAccountById(id));
		
		return new ModelAndView(AccountConstants.UPDATE_ACCOUNT, modelMap);
	}
	
	/**
	 * ajax保存账号
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxSaveByAdd", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveByAdd(@RequestParam String userId, @RequestParam String name, @RequestParam String password,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增账号:"+name,session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
				
		User user = new User();
		user.setId(userId);
		Account account = new Account();
		account.setName(name);
		account.setPassword(password);
		account.setUser(user);
		
		return accountService.saveAccount(account);
		 
	}
	
	/**
	 * ajax验证账号密码
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxValidate", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxValidate(@RequestBody Account account) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return accountService.validatePassword(account);
		
	}
	
	/**
	 * ajax修改账号密码
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxSaveByUpdate", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveByUpdate(@RequestBody Account account,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改账号密码:"+account.getName(),session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
				
		return accountService.updatePassword(account);
	}
	
	/**
	 * ajax修改账号的状态
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxChangeState", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxChangeAccountState(@RequestParam String state, @RequestParam String ids,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改账号的状态",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
				
		accountService.updateAccountState(state,ids);
		return StringConstant.TRUE;
	}
	
	/**
	 * ajax删除账号信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxDelete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam String ids,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除账号信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		accountService.deleteAccount(ids);
		return StringConstant.TRUE;
	}
	
}

