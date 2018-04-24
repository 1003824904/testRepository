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

import com.choice.framework.constants.system.OperateConstants;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.domain.system.Module;
import com.choice.framework.domain.system.Operate;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.ModuleService;
import com.choice.framework.service.system.OperateService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.ProgramConstants;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "operate")
public class OperateController {

	@Autowired
	private OperateService opeateService;
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private LogsMapper logsMapper;
	
	/**
	 * 进入操作页面的控制
	 * @param modelMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findOperateList(ModelMap modelMap,Operate operate) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 加载模块树信息
		 */
		modelMap.put("treeList", moduleService.findModuleList(new Module()));
		/**
		 * 加载右边iframe页面应该显示的信息
		 */
		modelMap.put("tableState",operate);
		
		return new ModelAndView(OperateConstants.LIST_OPERATE, modelMap);
	}
	
	/**
	 * 界面右侧iframe的控制
	 * @param modelMap
	 * @param operate
	 * @param addable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findOperateTable(ModelMap modelMap,Operate operate,String addable)throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("moduleId", operate.getModule().getId());
		modelMap.put("addable", addable);
		modelMap.put("operateList", opeateService.findOperateByModuleId(operate));
		
		return new ModelAndView(OperateConstants.TABLE_OPERATE, modelMap);
	}

	
	/**
	 * 打开添加操作窗口的控制方法
	 * @param modelMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(ModelMap modelMap,Operate operate)throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("module", moduleService.findModuleById(operate.getModule()));
		modelMap.put("operateType", opeateService.findOperateTypeList());
		
		return new ModelAndView(OperateConstants.SAVE_OPERATE, modelMap);
	}
	
	/**
	 * 保存新增
	 * @param modelMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByAdd")
	public ModelAndView saveByAdd(ModelMap modelMap,Operate operate,HttpSession session)throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		opeateService.saveOperate(operate);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.INSERT,"新增操作信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 打开修改页面
	 * @param modelMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(ModelMap modelMap,Operate operate)throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("operateType", opeateService.findOperateTypeList());
		modelMap.put("operate", opeateService.findOperateById(operate));
		return new ModelAndView(OperateConstants.UPDATE_OPERATE, modelMap);
	}
	
	/**
	 * 保存修改
	 * @param modelMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveByUpdate")
	public ModelAndView saveByUpdate(ModelMap modelMap, Operate operate,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		opeateService.updateOperate(operate);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.UPDATE,"修改操作信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);
		
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 删除操作信息
	 * @param modelMap
	 * @param operateId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, String operateId,HttpSession session) throws Exception {
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		/**
		 * 传入参数为list（为了支持多个删除和单个删除）
		 */
		List<String> ids = Arrays.asList(operateId.split(","));
		opeateService.deleteOperate(ids);
		
		//加入日志
		Logs logs=new Logs(Util.getUUID(),session.getAttribute("accountId").toString(),new Date(),ProgramConstants.DELETE,"删除操作信息",session.getAttribute("ip").toString(),ProgramConstants.OVERALL);
		logsMapper.addLogs(logs);

		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	
	/**
	 * 跳转到选择日志事件信息页面（日志管理）
	 * @param modelMap
	 * @param pubGrp
	 * @return
	 * @author ygb
	 * @throws CRUDException
	 */
	@RequestMapping("toChooseOperate")
	public ModelAndView toChooseFdGrp(ModelMap modelMap,String single,String domId,String callBack) throws CRUDException{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);
		modelMap.put("domId", domId);
		modelMap.put("callBack", callBack);
		modelMap.put("single", single);
		modelMap.put("eventsList", opeateService.findLogsEvents());
		return new ModelAndView(OperateConstants.CHOOSE_OPERATE,modelMap);
	}
}
