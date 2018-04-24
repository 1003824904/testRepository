package com.choice.framework.persistence.system;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choice.framework.domain.system.AccountOther;
import com.choice.framework.domain.system.User;

/**
 * 系统配置
 * @author 孙胜彬
 *
 */
public interface AccountOtherMapper {
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> findAllUser(User user);
	
	/**
	 * 保存 
	 */
	public void saveAccountOther(AccountOther accountOther);
	
	/**
	 * 保存 
	 */
	public void updateAccountOther(AccountOther accountOther);
	
	/**
	 * 删除
	 */
	public void deleteAccountOther(@Param("ids")String ids);
	
}
