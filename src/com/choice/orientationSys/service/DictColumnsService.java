package com.choice.orientationSys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.exception.CRUDException;
import com.choice.orientationSys.domain.ColumnsChoose;
import com.choice.orientationSys.domain.DictColumns;
import com.choice.orientationSys.persistence.DictColumnsMapper;
import com.choice.orientationSys.util.Util;


@Service
public class DictColumnsService {
	@Autowired
	private DictColumnsMapper dictColumnsMapper;
	
	private final transient Log log = LogFactory.getLog(DictColumnsService.class);
	/**
	 * 按照条件列出该用户要显示的字段
	 * @param dictColumns
	 * @return
	 */
	public List<DictColumns> listDictColumnsByAccount(DictColumns dictColumns,String defaultColumns){
		List<DictColumns> dictColumnsList = dictColumnsMapper.listDictColumnsByAccount(dictColumns);
		//如果当前账号没有存在关联字段则查询默认字段显示
		if(dictColumnsList==null||dictColumnsList.size()<=0){
			DictColumns dictColumnParam = new DictColumns();
			dictColumnParam.setIdList(Arrays.asList(defaultColumns.split(",")));
			dictColumnsList = dictColumnsMapper.listDictColumnsByAccount(dictColumnParam);
		}
		return dictColumnsList;
	}
	/**
	 * 按表的名字查询所有的字段
	 * @param dictColumns
	 * @return
	 */
	public List<DictColumns> listDictColumnsByTable(DictColumns dictColumns) {
		List<DictColumns> dictColumnsList = dictColumnsMapper.listDictColumnsByTable(dictColumns);
		return dictColumnsList;
	}
	/**
	 * 按照账户ID跟字段ID删除用户显示的字段
	 * @param columnsChoose
	 * @throws CRUDException 
	 */
	public void deleteColumnsChoose(DictColumns dictColumns) throws CRUDException{
		try {
			dictColumnsMapper.deleteColumnsChoose(dictColumns);
		} catch (Exception e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 保存用户要现实的字段
	 * @param columnsChoose
	 * @throws CRUDException 
	 */
	public void saveColumnsChoose(DictColumns dictColumns) throws CRUDException{
			//先删除当前用户显示的字段
			deleteColumnsChoose(dictColumns);
			
			List<String> columnsIdList = Arrays.asList(dictColumns.getId().split(","));
			
			ColumnsChoose saveColumnsChoose = null;
			try {
				for(int i = 0 ; i < columnsIdList.size() ; i++){
					saveColumnsChoose = new ColumnsChoose();
					saveColumnsChoose.setId(Util.getUUID());
					saveColumnsChoose.setAccountId(dictColumns.getAccountId());
					saveColumnsChoose.setDictColumnId(columnsIdList.get(i));
					saveColumnsChoose.setSequence(i);
					//然后再保存现在要更新的字段
					dictColumnsMapper.insertBatch(saveColumnsChoose);
				}
			} catch (Exception e) {
				log.error(e);
				throw new CRUDException(e);
			}
	}
	
	/**
	 * 查询所有的列
	 * @param dictColumns
	 * @return
	 */
	public List<DictColumns> findAll(DictColumns dictColumns){
		List<DictColumns> dictColumnsList = new ArrayList<DictColumns>();
		try {
			dictColumnsList = dictColumnsMapper.listDictColumnsByAccount(dictColumns);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictColumnsList;
	}
	
	
	public void updateColumns(String column){
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("columnName", column);
			dictColumnsMapper.updateAllColumn();
			dictColumnsMapper.updateColumns(map);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//更新列信息(列宽，类型)
	public void updateColInfo(DictColumns column){
		try {
			dictColumnsMapper.updateColInfo(column);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public List<DictColumns> listTable(){
		List<DictColumns> list = null;
		try {
			list = dictColumnsMapper.listTable();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void delColumns(DictColumns dictColumns){
		try {
			List<String> list = Arrays.asList(dictColumns.getId().split(","));
			dictColumnsMapper.delColumns(list);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public void addColumns(DictColumns dictColumns){
		try {
			dictColumnsMapper.addColumns(dictColumns);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public DictColumns findColumnById(DictColumns dictColumns){
		DictColumns column = null;
		try {
			column = dictColumnsMapper.findColumnById(dictColumns);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return column;
	}
	
	public DictColumns findColumn(){
		return dictColumnsMapper.findColumn();
	}
	
	public int getMaxId(){
		return dictColumnsMapper.getMaxId();
	}
}
