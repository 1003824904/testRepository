package com.choice.orientationSys.persistence;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choice.orientationSys.domain.ColumnsChoose;
import com.choice.orientationSys.domain.DictColumns;

public interface DictColumnsMapper {
	
	public List<DictColumns> listDictColumnsByAccount(DictColumns dictColumns);
	
	public List<DictColumns> listDictColumnsByTable(DictColumns dictColumns);
	
	public void deleteColumnsChoose(DictColumns dictColumns);
	
	public void insertBatch(ColumnsChoose columnsChooses);
	
	public void updateAllColumn();
	
	public void updateColumns(HashMap<String, Object> map);
	
	public DictColumns findColumn();
	
	public void updateColInfo(DictColumns dictColumns);
	
	public List<DictColumns> listTable();
	
	public void delColumns(List<String> list);
	
	public void addColumns(DictColumns dictColumns);
	
	public DictColumns findColumnById(DictColumns dictColumns);
	
	public int getMaxId();
}
