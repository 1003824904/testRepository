package com.choice.framework.persistence.system;


import java.util.List;

import com.choice.framework.domain.system.RoleOperateRange;


public interface RoleOperateRangeMapper{

	public RoleOperateRange  findRoleOperateRangeOne(RoleOperateRange roleoperaterange);
	
	public List<RoleOperateRange> findAllRoleOperateRange(RoleOperateRange roleoperaterange);
	
	public void saveRoleOperateRange(RoleOperateRange roleoperaterange);
	
	public void deleteRoleOperateRange(List<String> listId);
	
}
