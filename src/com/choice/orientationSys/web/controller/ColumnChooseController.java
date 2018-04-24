package com.choice.orientationSys.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.orientationSys.constants.ColumnsChooseConstants;
import com.choice.orientationSys.domain.DictColumns;
import com.choice.orientationSys.service.DictColumnsService;

@RequestMapping("columnChoose")
@Controller
public class ColumnChooseController {

	@Autowired
	private DictColumnsService dictColumnsService;
	
	
	/**
	 * 更新列信息
	 * @param dictColumns
	 */
	@RequestMapping("/updateColInfo")
	@ResponseBody
	public void updateColInfoScm(DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		dictColumnsService.updateColInfo(dictColumns);
	}
	/**
	 * 查询table列表
	 * @param modelMap
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/listTable")
	public ModelAndView listTable(ModelMap modelMap,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		modelMap.put("listTable", dictColumnsService.listTable());
		return new ModelAndView(ColumnsChooseConstants.LIST_TABLE,modelMap);
	}
	/**
	 * 跳转到列选择管理页面
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(){
		return new ModelAndView(ColumnsChooseConstants.LIST);
	}
	/**
	 * 查询table对应列名
	 * @param modelMap
	 * @param dictColumns
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/listColumns")
	public ModelAndView listColumns(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		modelMap.put("listColumns", dictColumnsService.listDictColumnsByTable(dictColumns));
		return new ModelAndView(ColumnsChooseConstants.LIST_COLUMNS,modelMap);
	}
	//保存新添加的列名
	@RequestMapping("/saveColumnByAdd")
	public ModelAndView saveColumnByAdd(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		if(null == dictColumns || null == dictColumns.getId()){
			return new ModelAndView(ColumnsChooseConstants.ADD_COLUMNS);
		}else{
			dictColumnsService.addColumns(dictColumns);
			return new ModelAndView(StringConstant.ACTION_DONE);
		}
	}
	/**
	 * 跳转到新增页面
	 * @param modelMap
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/addColumn")
	public ModelAndView addColumn(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		modelMap.put("action", "saveColumnByAdd.do");
		modelMap.put("sysName", sysName);
		dictColumns.setId(String.valueOf(dictColumnsService.getMaxId()+1));
		modelMap.put("column", dictColumns);
		return new ModelAndView(ColumnsChooseConstants.ADD_COLUMNS);
	}
	/**
	 * 删除列
	 * @param modelMap
	 * @param dictColumns
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/delColumn")
	public ModelAndView delColumn(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		dictColumnsService.delColumns(dictColumns);
		return new ModelAndView(StringConstant.ACTION_DONE);
	}
	/**
	 * 跳转到更新页面
	 * @param modelMap
	 * @param dictColumns
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/updateColumn")
	public ModelAndView updateColumn(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		modelMap.put("action", "saveColumnByUpdate.do");
		modelMap.put("sysName", sysName);
		modelMap.put("column", dictColumnsService.findColumnById(dictColumns));
		return new ModelAndView(ColumnsChooseConstants.ADD_COLUMNS);
	}
	/**
	 * 保存更新的列信息
	 * @param modelMap
	 * @param dictColumns
	 * @param sysName
	 * @return
	 */
	@RequestMapping("/saveColumnByUpdate")
	public ModelAndView saveColumnByUpdate(ModelMap modelMap,DictColumns dictColumns,String sysName){
		DataSourceSwitch.setDataSourceType(sysName.equals("scm") ? DataSourceInstances.SCM : DataSourceInstances.TELE);//选择数据源
		modelMap.put("column", dictColumnsService.findColumnById(dictColumns));
		dictColumnsService.updateColInfo(dictColumns);
		return new ModelAndView(StringConstant.ACTION_DONE);
	}
}
