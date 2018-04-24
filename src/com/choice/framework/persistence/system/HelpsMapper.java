package com.choice.framework.persistence.system;

import java.util.List;

import com.choice.framework.domain.system.Helps;

public interface HelpsMapper {
	/**
	 * 查询所有帮助信息
	 * @param help
	 * @return
	 */
	public List<Helps> listHelps(Helps help);

	/**
	 * 修改描述
	 * @param helps
	 */
	public void updateHelps(Helps helps);
	/**
	 * 添加帮助
	 * @param helps
	 */
	public void saveHelps(Helps helps);
	/**
	 * 删除单个帮助
	 * @param helps
	 */
	public void deleteHelp(Helps helps);
}
