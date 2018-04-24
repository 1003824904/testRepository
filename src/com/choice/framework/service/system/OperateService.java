package com.choice.framework.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Operate;
import com.choice.framework.domain.system.OperateType;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.OperateMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class OperateService {
	
	@Autowired
	private OperateMapper operateMapper;
	
	private final transient Log log = LogFactory.getLog(ModuleService.class);
	
	/**
	 * 通过模块编号查询所有符合条件的操作列表
	 * @param operate
	 * @return
	 * @throws CRUDException
	 */
	public List<Operate> findOperateByModuleId(Operate operate) throws CRUDException {
		try {
			return operateMapper.findOperateByModuleId(operate);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 通过操作id查询单个操作信息
	 * @param operate
	 * @return
	 * @throws CRUDException
	 */
	public Operate findOperateById(Operate operate) throws CRUDException {
		try {
			return operateMapper.findOperateById(operate);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 查询所有操作列表，用于角色-操作对应界面上方的操作类型批量对应
	 * @return
	 * @throws CRUDException
	 */
	public List<OperateType> findOperateTypeList()throws CRUDException {
		try {
			return operateMapper.findOperateTypeList();
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 保存操作信息
	 * @param operate
	 * @throws CRUDException
	 */
	public void saveOperate(Operate operate) throws CRUDException {
		try {
			operate.setId(CodeHelper.createUUID());
			operateMapper.saveOperate(operate);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 修改操作信息
	 * @param operate
	 * @throws CRUDException
	 */
	public void updateOperate(Operate operate) throws CRUDException {
		try {
			operateMapper.updateOperate(operate);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 删除操作信息
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteOperate(List<String> listId) throws CRUDException {
		try {
			operateMapper.deleteOperate(listId);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询所有操作类型列表
	 * @return
	 * @author ygb
	 * @throws CRUDException
	 */
	public List<Map<String,Object>> findLogsEvents()throws CRUDException {
		try {
			List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ID", "login");
			map.put("NAME", "登陆");
			rs.add(map);
			List<OperateType> list = operateMapper.findOperateTypeList();
			for(int i=0;i<list.size();i++){
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("ID", list.get(i).getId());
				m.put("NAME", list.get(i).getName());
				rs.add(m);
			}
			return rs;
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
}
