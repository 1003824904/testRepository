package com.choice.framework.service.system;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Helps;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.HelpsMapper;

@Service
public class HelpsService {
	private static Logger log = Logger.getLogger(HelpsService.class);
	
	@Autowired
	HelpsMapper helpsMapper;
	
	/**
	 * 感觉条件查询帮助
	 * @param help
	 * @return
	 * @throws CRUDException
	 */
	public List<Helps> listHelps(Helps help)throws CRUDException{
		try{
			return helpsMapper.listHelps(help);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 修改帮助
	 * @param helps
	 * @return
	 * @throws CRUDException
	 */
	public boolean updateHelps(Helps helps)throws CRUDException {
		try{
			helpsMapper.updateHelps(helps);
			return true;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 添加帮助
	 * @param helps
	 * @return
	 * @throws CRUDException
	 */
	public boolean saveHelps(Helps helps)throws CRUDException {
		try{
			helpsMapper.saveHelps(helps);
			return true;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 删除帮助
	 * @param helps
	 * @return
	 * @throws CRUDException
	 */
	public boolean deleteHelp(Helps help)throws CRUDException {
		try{
			helpsMapper.deleteHelp(help);
			return true;
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
}
