package com.choice.framework.service.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.domain.system.User;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.UserMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class UserService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询所有人员
	 * @param user
	 * @return
	 * @throws CRUDException
	 */
	public List<User> findUser(User user) throws CRUDException{
		try{
			return userMapper.findUser(user);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 根据人员id查询人员
	 * @param id
	 * @return
	 * @throws CRUDException
	 */
	public User findUserById(String id) throws CRUDException{
		try{
			return userMapper.findUserById(id);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询人员及账号信息
	 * @param user
	 * @return
	 * @throws CRUDException
	 */
	public List<User> findUserAccount(HashMap<String, Object> map) throws CRUDException{
		try{
			return userMapper.findUserAccount(map);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 保存人员
	 * @param user
	 * @throws CRUDException
	 */
	public void saveUser(User user) throws CRUDException{
		try{
			user.setId(CodeHelper.createUUID());
			user.setDeleteFlag(StringConstant.FALSE); 
			user.setState(StringConstant.STATE_IN_OFFICE);
			
			userMapper.saveUser(user);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 更新人员
	 * @param user
	 * @throws CRUDException
	 */
	public void updateUser(User user) throws CRUDException{
		try{
			userMapper.updateUser(user);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 删除人员
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteUser(String ids) throws CRUDException {
		List<String> listId = Arrays.asList(ids.split(","));
		try{
			userMapper.deleteUser(listId);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
}
