package com.choice.framework.service.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.RoleOperateRange;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.RoleOperateRangeMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class RoleOperateRangeService {

	@Autowired
	private RoleOperateRangeMapper roleOperateRangeMapper;

	private final transient Log log = LogFactory .getLog(RoleOperateRangeService.class);
	
	/**
	 * 通过条件查询一个RoleOperateRange对象
	 * @param roleoperaterange
	 * @return
	 * @throws CRUDException
	 */
	public RoleOperateRange findRoleOperateRangeOne(RoleOperateRange roleoperaterange) throws CRUDException {
		try {
			return roleOperateRangeMapper.findRoleOperateRangeOne(roleoperaterange);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 保存操作范围信息
	 * @param roleoperaterange
	 * @throws CRUDException
	 */
	public void saveRoleOperateRange(RoleOperateRange roleoperaterange) throws CRUDException {
		try {
			
			//deleteRoleOperateRange方法支持多删需要把传入的Role_operateId写成数组
			ArrayList<String> listId=new ArrayList<String>();
			listId.add(roleoperaterange.getRole_operateId());
			//删除原有操作范围信息，通过操作id为条件
			roleOperateRangeMapper.deleteRoleOperateRange(listId);
			//新增记录
			roleoperaterange.setId(CodeHelper.createUUID());
			roleOperateRangeMapper.saveRoleOperateRange(roleoperaterange);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 删除操作范围
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteRoleOperateRange(List<String> listId) throws CRUDException {
		try {
			roleOperateRangeMapper.deleteRoleOperateRange(listId);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
}
