package com.choice.orientationSys.persistence.system;

import java.util.List;

import com.choice.orientationSys.util.Page;




public interface PageMapper<E> {
	public List<E> selectPage(Object queryObject, Page page,String selectName);
	
}
