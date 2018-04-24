package com.choice.framework.persistence.system;

import java.util.HashMap;
import java.util.List;

import com.choice.framework.domain.system.User;

public interface UserMapper {
	/**
	 * 查询所有人员
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user);
	
	/**
	 * 根据人员id查询人员
	 * @param id
	 * @return
	 */
	public User findUserById(String id);
	
	/**
	 * 查询人员及账号信息
	 * @param map
	 * @return
	 */
	public List<User> findUserAccount(HashMap<String, Object> map);
	
	/**
	 * 保存人员
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * 更新人员
	 * @param user
	 */
	public void updateUser(User user);
	
	
	/**
	 * 删除人员
	 * @param listId
	 */
	public void deleteUser(List<String> listId);
	
}