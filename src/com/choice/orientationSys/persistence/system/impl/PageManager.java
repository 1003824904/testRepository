package com.choice.orientationSys.persistence.system.impl;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.choice.orientationSys.persistence.system.PageMapper;
import com.choice.orientationSys.util.Page;



@Component
public class PageManager<E> extends SqlSessionDaoSupport implements PageMapper<E>{

	public String yourMapper;
	
	@Resource
	public void setSqlSession(SqlSessionTemplate sqlSession){
		super.setSqlSessionTemplate(sqlSession);
	}
	
	public List<E> selectPage(Object queryObject, Page page,String selectName) {
		setPageCount(queryObject, page, selectName);
		
		return (List<E>) getSqlSession().selectList(selectName, queryObject,
				new RowBounds(page.getOffset(), page.getLimit()));
		
	}

	private void setPageCount(Object queryObject, Page page, String selectName) {
		String sql = getSqlSession().getConfiguration()
				.getMappedStatement(selectName).getSqlSource()
				.getBoundSql(queryObject).getSql();
			sql=sql.replaceAll("ORDER BY [^\\)]*$", "");
		
		List l=getSqlSession().getConfiguration().getMappedStatement(selectName).getBoundSql(queryObject).getParameterMappings();
		Field field = null;
		for(int i=0;i<l.size();i++){
			try {
				String curField = ((ParameterMapping)l.get(i)).getProperty();
				Object curObject = queryObject;
				Object result = null;
				String typeName = null;
				String[] fields = curField.split("\\.");
				int len = fields.length;
				for(int k = 0;k < len;k++){
					if(curObject.getClass().getName().equals("java.util.HashMap")){
						if(k == len-1){
							result = ((Map)curObject).get(fields[k]);
							typeName = result.getClass().getName();
							typeName=typeName.substring(typeName.lastIndexOf(".")+1);
						}else{
							curObject = ((Map)curObject).get(fields[k]);
						}
					}else{
						if(k == len-1){
							String className = curObject.getClass().getName();
							int index = className.indexOf("$") >= 0 ? className.indexOf("$") : className.length();
							field = Class.forName(className.substring(0, index)).getDeclaredField(fields[k]);
							//field=curObject.getClass().getDeclaredField(fields[k]);
							typeName = field.getType().getName();
							typeName=typeName.substring(typeName.lastIndexOf(".")+1);
							boolean accessible = field.isAccessible();
							field.setAccessible(true);
							result = field.get(curObject);
							field.setAccessible(accessible);
						}else{
							field = curObject.getClass().getDeclaredField(fields[k]);
							boolean accessible = field.isAccessible();
							field.setAccessible(true);
							curObject = field.get(curObject);
							field.setAccessible(accessible);
						}
					}
				}
				sql=sql.replaceAll("\"", "");
				String prefix="'";
				String suffix="'";
				//System.out.println(sql+"|step1");
				
				//如果SQL中有模糊查询"%"?"%"的，用?替换
				if("%".equals(sql.substring(sql.indexOf("?")-1, sql.indexOf("?")))){
					prefix=prefix+"%";
					sql=sql.replaceFirst("\\%\\?","?");
				}
				//System.out.println(sql+"|step2");
				if(sql.indexOf("?")+1 < sql.length())
				{
					if("%".equals(sql.substring(sql.indexOf("?")+1, sql.indexOf("?")+2))){
						suffix="%"+suffix;
						sql=sql.replaceFirst("\\?\\%","?");
					}
				}
				//System.out.println(sql+"|step3");
				
				//最终的SQL
				if("int".equals(typeName) || "Integer".equals(typeName)){
					sql=sql.replaceFirst("\\?",result.toString());
				}else if("Date".equals(typeName)){
					DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
					sql=sql.replaceFirst("\\?", "to_date("+prefix+dft.format(result)+suffix+",'yyyy-MM-dd')");
				}else{
					sql=sql.replaceFirst("\\?",prefix+result.toString()+suffix);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		page.setSql(sql.substring(sql.indexOf("FROM")));
		List result = getSqlSession().selectList("page.count", page);
		if(result.size()==1)
			page.setCount(Integer.parseInt(result.get(0).toString()));
		else
			page.setCount(result.size());
		
	}

	public String getYourMapper() {
		return yourMapper;
	}

	public void setYourMapper(String yourMapper) {
		this.yourMapper = yourMapper;
	}


}

