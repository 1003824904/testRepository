package com.choice.framework.service.system;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Module;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.ModuleMapper;
import com.choice.framework.util.CodeHelper;
/**
 * 模块的业务类
 * @author secret
 *
 */
@Service
public class ModuleService {

	@Autowired
	private ModuleMapper moduleMapper;

	private final transient Log log = LogFactory.getLog(ModuleService.class);

	/**
	 * 以模块id为条件查询模块信息
	 * @param module 查询到得模块信息（单个）
	 * @return
	 * @throws CRUDException
	 */
	public Module findModuleById(Module module) throws CRUDException {
		try {
			return moduleMapper.findModuleById(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 查询模块全部信息（包括上级模块和下级模块）
	 * @param module
	 * @return
	 * @throws CRUDException
	 */
	public Module findModuleAll(Module module) throws CRUDException {
		try {
			return moduleMapper.findModuleAll(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 查询模块信息列表
	 * @param module
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findModuleList(Module module) throws CRUDException {
		try {
			return moduleMapper.findModuleList(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询模块树（过滤掉自身及子部门、存在操作的模块）
	 * @param module
	 * @return
	 * @throws CRUDException
	 */
	public List<Module> findModuleSelect(Module module) throws CRUDException {
		try {
			return moduleMapper.findModuleSelect(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 新增模块信息的方法
	 * @param module
	 * @throws CRUDException
	 */
	public void saveModule(Module module) throws CRUDException {
		try {
			/**
			 * 给新增的模块生成主键
			 */
			module.setId(CodeHelper.createUUID());
			/**
			 * 处理code和seq
			 */
			updateCodeAndSeq(module);
			/**
			 * 新增模块信息
			 */
			moduleMapper.saveModule(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 修改模块信息
	 * @param module
	 * @throws CRUDException
	 */
	public void updateModule(Module module) throws CRUDException {
		try {
			String pid=module.getCode();
			/**
			 * 处理code和seq
			 */
			updateCodeAndSeq(module);
			moduleMapper.updateModule(module);
			module.getParentModule().setCode(pid);
			moduleMapper.updateChildCode(module);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 删除模块信息
	 * @param listId
	 * @throws CRUDException
	 */
	public void deleteModule(List<String> listId) throws CRUDException {
		try {
			moduleMapper.deleteModule(listId);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}

	/**
	 * 处理新增修改时模块code和Sequence的变化
	 * @param module
	 */
	private void updateCodeAndSeq(Module module) {
		if(null == module.getPriparentId() || !module.getPriparentId().equals(module.getParentModule().getId())){
		/**
		 * 查询模块code应有的编号
		 */
		Module temp = moduleMapper.findMaxCode(module);
		
		if (temp == null) {
			String code = moduleMapper.findModuleById(module.getParentModule()).getCode();
			module.setCode(code + "-" + "0001");
		}else {
			/**
			 * 先补0在截断确保是4位数
			 */
			String newCode = "000" + temp.getCode();
			newCode = newCode.substring(newCode.length() - 4, newCode.length());
			/**
			 * IF上级编号是空说明是跟模块，直接使用code
			 */
			if (temp.getParentModule() == null) {
				module.setCode(newCode);
			/**
			 * else上级不是根模块，要把上级模块code拼接上
			 */
			} else {
				module.setCode(temp.getParentModule().getCode() + "-" + newCode);
			}
		}
		}
		/**
		 * 更新Sequence
		 */
		moduleMapper.updateSequence(module);
	}
	/**
	 * 根据多个id查询模块信息列表
	 * @param ids
	 * @return
	 * @throws CRUDException
	 * @author ZGL
	 */
	public List<Module> findModuleByIds(String ids) throws CRUDException {
		try {
			return moduleMapper.findModuleByIds(ids);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
}
