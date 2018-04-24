package com.choice.orientationSys.persistence;


import java.util.List;

import com.choice.orientationSys.domain.Dict;


public interface DictMapper {

	/**
	 * 查询所有
	 */
	public List<Dict> findAllDict(Dict dict);
	/**
	 * 系统常量通用查询方法
	 */
	public Dict findDict(String id);
	/**
	 * 根据ID查询
	 */
	public Dict findDictById(String id);
	/**
	 * 保存新增
	 */
	public void saveDict(Dict dict);
	/**
	 * 保存修改
	 */
	public void updateDict(Dict dict);
	/**
	 * 删除
	 */
	public void deleteDict(String id);
}
