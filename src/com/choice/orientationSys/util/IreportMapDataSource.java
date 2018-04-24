package com.choice.orientationSys.util;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class IreportMapDataSource implements JRDataSource {

	private List<Map<String,Object>> data;
	private int index = -1;
	
	public IreportMapDataSource(List<Map<String,Object>> data){
		this.data = data;
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		return data.get(index).get(field.getName().toUpperCase());
	}


	public boolean next() throws JRException {
		index ++;
		return index < data.size();
	}

}
