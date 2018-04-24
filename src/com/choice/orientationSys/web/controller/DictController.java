package com.choice.orientationSys.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.orientationSys.constants.DictConstants;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.domain.Dict;
import com.choice.orientationSys.service.DictService;

@Controller
@RequestMapping(value = "dict")
public class DictController {

	@Autowired
	private DictService dictService;
	/**
	 * 左侧树页面
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findDict(ModelMap modelMap,Dict dict) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("dictList", dictService.findAllDict(dict));
		modelMap.put("typ", "root");
		return new ModelAndView(DictConstants.LIST, modelMap);
	}
	/**
	 * 右侧列表页面
	 */
	@RequestMapping(value = "/table")
	public ModelAndView findAllCodeDes(ModelMap modelMap,Dict dict,String typ) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("dictList", dictService.findAllDict(dict));
		modelMap.put("id", dict.getId());
		modelMap.put("typ", typ);
		return new ModelAndView(DictConstants.TABLE_DICT,modelMap);
	}
	/**
	 * 新增页面
	 */
	@RequestMapping(value = "/addLeft")
	public ModelAndView addLeft(ModelMap modelMap) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return new ModelAndView(DictConstants.SAVE_LEFT_DICT);
	}
	/**
	 * 保存新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(ModelMap modelMap, Dict dict) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		dictService.saveDict(dict);
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/updateLeft")
	public ModelAndView updateLeft(ModelMap modelMap,String id) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		modelMap.put("dict", dictService.findDictById(id));
		return new ModelAndView(DictConstants.UPDATE_LEFT_DICT,modelMap);
	}
	/**
	 * 保存修改
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(ModelMap modelMap, Dict dict) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		dictService.updateDict(dict);
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap modelMap, Dict dict) throws Exception
	{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		dictService.deleteDict(dict);
		modelMap.put("dictList", dictService.findAllDict(dict));
		return new ModelAndView(StringConstant.ACTION_DONE, modelMap);
	}
}
