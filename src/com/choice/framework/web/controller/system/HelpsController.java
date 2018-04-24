package com.choice.framework.web.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.HelpsConstants;
import com.choice.framework.domain.system.Helps;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.HelpsService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value="helps")
public class HelpsController {
	
	@Autowired
	private HelpsService helpsService; 
	@Autowired
	private LogsMapper logsMapper;

	/**
	 * 显示帮助管理页面
	 * @param modelMap
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(ModelMap modelMap,Helps helps,String typ) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(helps.getProjectId()==null||helps.getProjectId()==""){//默认开始显示 【决策】
			helps.setProjectId("TELE");
		}
		List<Helps> listHelps=helpsService.listHelps(helps);
		modelMap.put("listHelps", listHelps);
		modelMap.put("project", helps.getProjectId());
		if(typ==null||typ==""){
			return new ModelAndView(HelpsConstants.HELPS_LIST, modelMap);
		}else{
			modelMap.put("helps", listHelps.size()>0?listHelps.get(0):null);
			return new ModelAndView(HelpsConstants.HELPS_EDITOR, modelMap);
		}
	}
	/**
	 * 修改帮助信息
	 * @param modelMap
	 */
	@RequestMapping(value = "/updateHelps")
	public ModelAndView updateHelps(ModelMap modelMap,Helps helps,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(helpsService.updateHelps(helps)){
			//加入日志
			Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改帮助信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
			logsMapper.addLogs(logs);
			
			return new ModelAndView(StringConstant.ACTION_DONE);
		}
		return new ModelAndView(StringConstant.ERROR_DONE);
	}
	/**
	 * 添加帮助 跳转
	 * @param modelMap
	 */
	@RequestMapping(value = "/addHelp")
	public ModelAndView addHelp(ModelMap modelMap) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(HelpsConstants.SAVE_HELP);
	}
	/**
	 * 添加帮助
	 * @param modelMap
	 */
	@RequestMapping(value = "/saveHelps")
	public ModelAndView saveHelps(ModelMap modelMap,Helps helps,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(helps.getProjectId()!=null||helps.getProjectId()!=""){
			helps.setId(helps.getProjectId()+"-"+helps.getId());
			if(helpsService.saveHelps(helps)){
				//加入日志
				Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增帮助信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
				logsMapper.addLogs(logs);
				
				return new ModelAndView(StringConstant.ACTION_DONE);
			}
		}
		return new ModelAndView(StringConstant.ERROR_DONE);
	}

	/**
	 * 显示帮助
	 * @param modelMap
	 */
	@RequestMapping(value = "/showHelp")
	public ModelAndView showHelp(ModelMap modelMap,Helps help) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(helpsService.listHelps(help).size()>0){
			modelMap.put("help", helpsService.listHelps(help).get(0));
			return new ModelAndView(HelpsConstants.SHOW_HELP,modelMap);
		}
		return new ModelAndView(HelpsConstants.ERROR_HELP);
	}
	/**
	 * ID（缩写）验证
	 * @param modelMap
	 */
	@RequestMapping(value = "/validate")
	@ResponseBody
	public String validate(ModelMap modelMap,Helps help) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		help.setId(help.getProjectId()+"-"+help.getId());
		if(helpsService.listHelps(help).size()>0){
			return "error";
		}
		return "success";
	}
	/**
	 * 删除单个帮助
	 * @param modelMap
	 */
	@RequestMapping(value = "/deleteHelp")
	public ModelAndView deleteHelp(ModelMap modelMap,Helps help,HttpSession session) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		if(helpsService.deleteHelp(help)){
			//加入日志
			Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除单个帮助信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
			logsMapper.addLogs(logs);
			
			return new ModelAndView(StringConstant.ACTION_DONE);
		}
		return new ModelAndView(StringConstant.ERROR_DONE);
	}
}
