package com.choice.framework.service.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Module;
import com.choice.framework.domain.system.Role;
import com.choice.framework.domain.system.RoleOperate;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.RoleOperateMapper;
import com.choice.framework.persistence.system.RoleOperateRangeMapper;
import com.choice.framework.util.EncryptUtil;
import com.choice.framework.util.OracleUtil;

@Service
public class RoleOperateService {

	@Autowired
	private RoleOperateMapper roleOperateMapper;
	
	@Autowired
	private RoleOperateRangeMapper roleOperateRangeMapper;
	

	private final transient Log log = LogFactory.getLog(RoleOperateService.class);
	/**
	 * 角色-操作对应界面生成左侧树
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findModuleOperateForTree() throws CRUDException {
		try {
			return roleOperateMapper.findModuleOperateForTree();
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 通过点击左侧树查询右侧要添加那些操作
	 * @param module
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findModuleOperateForAddList(Module module)
			throws CRUDException {
		try {
			return roleOperateMapper.findModuleOperateForAddList(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 通过角色查询角色-操作列表，用于进入操作-角色对应页面时初始化页面
	 * @param role
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findRoleOperateList(Role role) throws CRUDException {
		try {
			return roleOperateMapper.findRoleOperateList(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 查询已经选中的模块和操作
	 * @param role
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findSelectRoleOperateList(Role role) throws CRUDException {
		try {
			return roleOperateMapper.findSelectRoleOperateList(role);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 批量对应角色-操作关系，全删全增
	 * @param roleOperateId
	 * @param roleId
	 * @throws CRUDException
	 */
	public void insertRoleOperate(String operateIds, String roleId)
			throws CRUDException {
		try {
			//ids是传入的多个操作ID
			List<String> ids = Arrays.asList(operateIds.split(","));
			//首先开始查询需要删除的操作范围记录
			HashMap<String,Object> map=new HashMap<String,Object>();
			String inCondition = OracleUtil.getOraInSql("operateId", ids);
			inCondition = inCondition.replace("in", "not in").replace("or", "and");
			map.put("inCondition", inCondition);
			map.put("roleId", roleId);
			//通过统一角色下，不在保存列表中的操作ID，获得到需要删除那些操作范围的OperateIds
			List<RoleOperate> roleOperateIds=roleOperateMapper.findDeleteRoleOperateIdsListByRoleIds(map);
			/**
			 * 如果结果大于0说明有操作范围需要删除
			 * 这里没有考虑操作类型，因为只有select，update，delete有操作范围
			 * 但页面传入的operateIds是包括所有操作类型的，所以有时候没有select，update，delete这三种类型的操作
			 * 还是可以查出数据，但是效率影响不大，决定不处理这种情况。
			 */
			if(roleOperateIds.size()>0){
				//把取出的id变为方法需要的List<String>
				List<String> delRoleOperateIds=new ArrayList<String>();
				for (RoleOperate roleOperate : roleOperateIds) {
					delRoleOperateIds.add(roleOperate.getId());
				}
				//先删除范围
				roleOperateRangeMapper.deleteRoleOperateRange(delRoleOperateIds);
			}
			
			//然后删除角色操作记录
			List<String> roleIds=new ArrayList<String>();
			roleIds.add(roleId);
			roleOperateMapper.deleteRoleOperateByRoleId(roleIds);
			
			//最后在新增角色操作记录
			RoleOperate roleOperate = new RoleOperate();
			for (int i = 0; i < ids.size(); i++) {
				roleOperate = new RoleOperate();
				/**
				 * 编码没有使用UUID，使用了roleID+操作ID的MD5加密串
				 * 这样可以保证同角色对应操作的ID始终是固定的便于数据范围的操作。
				 */
				roleOperate.setId(EncryptUtil.encodeMD5String(roleId+ids.get(i).toString()));
				roleOperate.setRoleId(roleId);
				roleOperate.setOperateId(ids.get(i).toString());
				roleOperateMapper.insertRoleOperate(roleOperate);
				System.out.println(i);
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

}
