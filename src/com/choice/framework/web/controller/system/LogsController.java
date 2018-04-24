package com.choice.framework.web.controller.system;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.system.LogsConstants;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.service.system.LogsService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.orientationSys.util.Page;

@Controller
@RequestMapping(value = "logs")
public class LogsController {
	
	@Autowired
	private LogsService logsService;
	
	/**
	 * 查询所有日志
	 * @param modelMap
	 * @param logs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findLogs(ModelMap modelMap, Logs logs, Page page,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		String accountName = logs.getAccountname();
		if (null == accountName) {
			logs.setAccountId(session.getAttribute("accountId").toString());
		}else {
			logs.setAccountId(null);
		}
		List<Logs> logsList = logsService.findLogs(logs, page);		
		modelMap.put("logsList", logsList);
		modelMap.put("queryLogs", logs);
		modelMap.put("pageobj", page);
		return new ModelAndView(LogsConstants.LIST_LOGS, modelMap);
	}
	
	/**
	 * 删除日志
	 * @param logs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/delete")
	public ModelAndView delete(ModelMap modelMap, String id, Logs logs,Page page) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<String> ids = Arrays.asList(id.split(","));
		logsService.deleteLogs(ids);
		List<Logs> logsList = logsService.findLogs(logs, page);
		modelMap.put("logsList", logsList);
		modelMap.put("queryLogs", logs);
		modelMap.put("pageobj", page);
		return new ModelAndView(LogsConstants.LIST_LOGS, modelMap);
	}
	
	/**
	 * 添加日志
	 * @param logs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/add")
	public ModelAndView savebyadd(ModelMap modelMap, HttpServletRequest request, HttpSession session, Logs logs, Page page) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
	//	logs.setContents("a");
	//	logs.setEvents("a");

		Date date = new Date();
		logs.setLogindate(date);
	//	logs.setId("00000");
		logsService.addLogs(logs,request,session);
		List<Logs> logsList = logsService.findLogs(null, page);
		modelMap.put("logsList", logsList);
		modelMap.put("queryLogs", logs);
		modelMap.put("pageobj", page);
		return new ModelAndView(LogsConstants.LIST_LOGS, modelMap);
	}
	
	@RequestMapping(value ="/getlogs")
	public ModelAndView getLogs(ModelMap modelMap,HttpServletRequest request) throws CRUDException{
		modelMap.put("files", logsService.listLogs(request));
		return new ModelAndView(LogsConstants.LIST_LOGFILES,modelMap);
	}
	@RequestMapping(value ="/downloadlogs")
	public void downLog(String fileName,HttpServletRequest request,HttpServletResponse response){
		logsService.getLogs(fileName,request,response);
	}
	@RequestMapping(value ="/deletelogs")
	public ModelAndView delLog(ModelMap modelMap,String fileName,HttpServletRequest request) throws CRUDException{
		logsService.delLogFiel(request,fileName);
		modelMap.put("files", logsService.listLogs(request));
		return new ModelAndView(LogsConstants.LIST_LOGFILES,modelMap);
	}
	
}
