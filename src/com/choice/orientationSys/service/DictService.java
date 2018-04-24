package com.choice.orientationSys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.domain.Dict;
import com.choice.orientationSys.persistence.DictMapper;
import com.choice.orientationSys.util.Util;

@Service
public class DictService {
	
	@Autowired
	private DictMapper dictMapper;
	/**
	 * 系统常量通用查询方法
	 */
	public Dict findDict(String id){
		return dictMapper.findDict(id);
	}

	/**
	 * 查找
	 */
	public List<Dict> findAllDict(Dict dict){
		return dictMapper.findAllDict(dict);
	}
	/**
	 * 保存新增
	 */
	public void saveDict(Dict dict){
		dict.setId(Util.getUUID());
		//添加主字典时候加入上级字典为32个0
		if(dict.getParent_id()=="" || dict.getParent_id()==null){
			dict.setParent_id(StringConstant.ROOT_ID);
		}
		if(null==dict.getIs_initial()){
			dict.setIs_initial("");
		}
		dictMapper.saveDict(dict);
	}
	/**
	 * 根据ID查找
	 */
	public Dict findDictById(String id){
		return dictMapper.findDictById(id);
	}
	/**
	 * 保存修改
	 */
	public void updateDict(Dict dict){
		//添加主字典时候加入上级字典为32个0
		if(dict.getParent_id()=="" || dict.getParent_id()==null){
			dict.setParent_id(StringConstant.ROOT_ID);
		}
		if(null==dict.getIs_initial()){
			dict.setIs_initial("");
		}
		dictMapper.updateDict(dict);
	}
	/**
	 * 假删除
	 */
	public void deleteDict(Dict dict){
		dictMapper.deleteDict(dict.getId());
	}
}
