package com.choice.framework.util;

import java.io.InputStream;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class CacheUtil {
	private static CacheManager CACHE_MANAGER;
	private static CacheUtil cacheUtil = new CacheUtil();
	
	private CacheUtil(){}
	
	public static CacheUtil getInstance(String file){
		CacheManager cacheManager;
		
		//获取配置文件的路径
		InputStream inputStream = CacheUtil.class.getResourceAsStream(file);
		
		//如果存在配置文件，则根据文件创建CacheManager
		//如果不存在，则创建默认的CacheManager
        if(inputStream != null){
	    	try {
	    		cacheManager = CacheManager.create(inputStream);
	    		CACHE_MANAGER = cacheManager;
	    		return cacheUtil;
	        } catch (Throwable t) {
	        	return getInstance();
	        } 
        }else{
        	return getInstance();
        }
	}
	
	public static CacheUtil getInstance(){
		CacheManager cacheManager;
		try {
			cacheManager = CacheManager.create();
			CACHE_MANAGER = cacheManager;
		} catch (Throwable t) {
			cacheManager = CacheManager.create();
			CACHE_MANAGER = cacheManager;
		} 
		
		return cacheUtil;
	}
	
	/**
     * 添加缓存
     * 
     * @param cacheName 缓存策略名称
     * @param key 缓存键
     * @param value 缓存值
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        if(value == null) {
        	cache.remove(key);
        } else {
        	cache.put(new Element(key, value));
        }
    }

    /**
     * 获得缓存
     * 
     * @param cacheName 缓存策略名称
     * @param key 缓存键
     * @return
     */
    public Object get(String cacheName, String key) {
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        } else {
            return element.getValue();
        }
    }
    
    /**
     * 刷新缓存
     * @param cacheName
     * @param key
     */
    public void flush(String cacheName, String key) {
    	Cache cache = CACHE_MANAGER.getCache(cacheName);
    	cache.remove(key);
    }

}
