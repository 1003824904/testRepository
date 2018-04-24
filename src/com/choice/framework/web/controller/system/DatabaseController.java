package com.choice.framework.web.controller.system;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.DatabaseConstants;
import com.choice.framework.domain.system.Tables;
import com.choice.framework.service.system.DatabaseService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.FileWorked;
import com.choice.orientationSys.util.Page;

@Controller
@RequestMapping(value = "database")
public class DatabaseController {
	
	@Autowired
	private DatabaseService databaseService;
	
	/**
	 * 导出数据库
	 * @param modelMap
	 * @param logs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView listDatabase(ModelMap modelMap, Tables tables, Page page) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		List<Tables> tablesList = databaseService.queryTables(tables, page);		
		modelMap.put("tablesList", tablesList);
		modelMap.put("pageobj", page);
		return new ModelAndView(DatabaseConstants.LIST_DATABASE, modelMap);
	}
	
	/**
	 * 跳转到导出页面
	 */
	@RequestMapping("/exportDmp")
	public ModelAndView exportDmp(ModelMap modelMap, HttpServletRequest request, String name) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if (name !=null) {
			List<String> names = Arrays.asList(name.split(","));
			databaseService.exportTables(names, request);
		} else {
			databaseService.exportDatabase(request);
		}
		return new ModelAndView(DatabaseConstants.EXPORT_DMP,modelMap);
	}
	
	/**
	 * 下载模板信息
	 */
	@RequestMapping(value = "/downloadTemplate")
	public void downloadTemplate(ModelMap modelMap, HttpServletResponse response,
			HttpServletRequest request, Tables tables, Page page) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		String fileName = "logist.dmp";
		FileWorked.downloadTemplate(response, request, fileName);
	}
	
	/**
	 * 跳转到导入页面
	 */
	@RequestMapping("/importDmp")
	public ModelAndView importDmp(ModelMap modelMap){
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(DatabaseConstants.IMPORT_DMP,modelMap);
	}
	
	/**
	 * 导入dmp
	 */
	@RequestMapping(value = "/loadDmp", method = RequestMethod.POST)
	public ModelAndView loadDmp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		String realFilePath = databaseService.upload(request, response);
		databaseService.importDmpfile(realFilePath, request);
		return new ModelAndView(StringConstant.ACTION_DONE,modelMap);
	}

}
