package com.choice.framework.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.AccountOther;
import com.choice.framework.persistence.system.AccountOtherMapper;
import com.choice.framework.util.CodeHelper;

@Service
public class AccountOtherService {
	
	@Autowired
	private AccountOtherMapper accountOtherMapper;

	/**
	 * 保存
	 * @param id
	 * @return
	 */
	public void saveAccountOther(AccountOther accountOther){
		accountOtherMapper.saveAccountOther(accountOther);
	}
	
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	public void updateAccountOther(AccountOther accountOther){
		accountOtherMapper.updateAccountOther(accountOther);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public void deleteAccountOther(String ids){
		ids = CodeHelper.replaceCode(ids);
		accountOtherMapper.deleteAccountOther(ids);
	}
}
