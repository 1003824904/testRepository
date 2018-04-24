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

import com.choice.framework.constants.system.ModuleConstants;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.domain.system.Module;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.ModuleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.util.Util;
/**
 * 模块跳转控制类
 * @author secret
 *
 */
@Controller
@RequestMapping(value = "module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	@Autowired
	private LogsMapper logsMapper;
	
	/**
	 * 控制模块顶级页面，处理模块树和右边iframe页面应该显示的模块信息
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findModuleList(ModelMap modelMap,Module module) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 加载模块树信息
		 */
		modelMap.put("treeList", moduleService.findModuleList(new Module()));
		/**
		 * 加载右边iframe页面应该显示的信息
		 */
		
		modelMap.put("tableState",moduleService.findModuleById(module));
		
		return new ModelAndView(ModuleConstants.LIST_MODULE, modelMap);
	}
	
	/**
	 * 控制页面右边iframe部分
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findModuleTableList(ModelMap modelMap,Module module) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 处理右边iframe显示的内容，if不是根节点的情况直接查询模块，上级模块，子模块列表的信息
		 */
		if(!module.getId().equals(StringConstant.ROOT_ID)){
			Module moduleAll = moduleService.findModuleAll(module);
			modelMap.put("moduleAll", moduleAll);
		/**
		 * else如果是跟节点，因为数据库中没有存根节点，必须通过数据模拟，
		 * 查出根节点下的所有模块并set到要返回的module的child下
		 */
		}else{
			module.setParentModule(new Module());
			module.getParentModule().setId(StringConstant.ROOT_ID);
			module.setChildModule(moduleService.findModuleList(module));
			modelMap.put("moduleAll",module);
		}
		return new ModelAndView(ModuleConstants.TABLE_MODULE, modelMap);
	}
	
	/**
	 * 控制打开新增页面的方法
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(ModelMap modelMap,Module module) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(ModuleConstants.SAVE_MODULE,modelMap);
	}
	
	/**
	 * 控制新增页面的保存操作
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView saveByAdd(ModelMap modelMap, Module module,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 新增模块信息
		 */
		moduleService.saveModule(module);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增模块信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		/**
		 * 跳转到统一的ACTION_DONE（操作完成）
		 */
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 控制打开修改页面的操作
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(ModelMap modelMap, Module module) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 要修改的模块信息内容
		 */
		module=moduleService.findModuleById(module);
		modelMap.put("module",module);

		return new ModelAndView(ModuleConstants.UPDATE_MODULE, modelMap);
	}

	/**
	 * 控制修改页面的保存操作
	 * @param modelMap
	 * @param module
	 * @param priparentId 原父模块，如果现在的父模块id和原父模块id相同，不需要变化
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByUpdate")
	public ModelAndView saveByUpdate(ModelMap modelMap, Module module,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 保存修改的信息
		 */
		moduleService.updateModule(module);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改模块信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}

	/**
	 * 控制删除模块信息操作
	 * @param modelMap
	 * @param moduleId
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, String moduleId, Module module,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 传入参数为list（为了支持多个删除和单个删除）
		 */
		List<String> ids = Arrays.asList(moduleId.split(","));
		moduleService.deleteModule(ids);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除模块信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 选择模块
	 * @param modelMap
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/select")
	public ModelAndView selectModule(ModelMap modelMap, String code) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		Module module=new Module();
		module.setCode(code);
		modelMap.put("moduleList", moduleService.findModuleSelect(module));
		
		return new ModelAndView(ModuleConstants.SELECT_MODULE, modelMap);
	}

}
