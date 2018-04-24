package com.choice.framework.web.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.choice.framework.util.CacheUtil;
import com.choice.framework.vo.AccountCache;
import com.choice.framework.constants.StringConstant;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	
	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		String path = request.getServletPath();
		String accountId = (String)(request.getSession().getAttribute("accountId"));
		if((null != path && "orderMeal".equals(path.split("/")[1]))||(null != path && "orders".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "pwdFind".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "clientlogin".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "mall".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "orderfdItem".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "orderDesk".equals(path.split("/")[1]))){
			return true;
		}
		if((null != path && "couponRecord".equals(path.split("/")[1]))){
			return true;
		}
		if(null != path && "firstPage".equals(path.split("/")[1])){
			return true;
		}
		if(null != path && "chat".equals(path.split("/")[1])){
			return true;
		}
		if(null != path && "login".equals(path.split("/")[1])){
			return true;
		}
		if(null != path && "/login/loginOut.do".equals(path)){
				response.sendRedirect(request.getContextPath()+ "/view/login_.jsp");
				return false;
		}
		if(null != path && !"/login/loginIn.do".equals(path)){
			if((null == accountId) || ("".equals(accountId))){
				response.sendRedirect(request.getContextPath()+ "/view/out.jsp");
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		
		String path = request.getServletPath();
		if((null != path && "orderMeal".equals(path.split("/")[1]))||(null != path && "orders".equals(path.split("/")[1]))){
			return;
		}
		if((null != path && "pwdFind".equals(path.split("/")[1]))){
			return ;
		}
		if(null != path && "firstPage".equals(path.split("/")[1])){
			return ;
		}
		if(null != path && "orderDesk".equals(path.split("/")[1])){
			return ;
		}
		if(null != path && "chat".equals(path.split("/")[1])){
			return ;
		}
		if(null != path && "mall".equals(path.split("/")[1])){
			return ;
		}
		if(null != path && "login".equals(path.split("/")[1])){
			return ;
		}
		if((null != path && "clientlogin".equals(path.split("/")[1]))){
			return ;
		}
		if((null != path && "orderfdItem".equals(path.split("/")[1]))){
			return ;
		}
		if((null != path && "couponRecord".equals(path.split("/")[1]))){
			return ;
		}
		String accountId = (String)(request.getSession().getAttribute("accountId"));
		HashMap<String,Boolean> operateMap  = new HashMap<String, Boolean>();
		
		//判断是否存在账号名称，并且路径是否符合“/controllerMapping/methodMapping.do”的形式
		if((null != accountId) && (!"".equals(accountId))
			&& (null != path.split("/")  && path.split("/").length > 1)){
			
			//获取账号的缓存信息
			CacheUtil cacheUtil = CacheUtil.getInstance();
			AccountCache accountCache = (AccountCache)cacheUtil.get(StringConstant.ACCOUNT_CACHE, accountId);
			
			String moduleMapping = path.split("/")[1];
			if(null!=accountCache){
				HashMap<String,HashMap<String,Boolean>> moduleOperateMap = accountCache.getModuleOperateMap();
				
				//获取账号请求访问模块的操作集合
				if(moduleOperateMap.containsKey(moduleMapping))
					operateMap = moduleOperateMap.get(moduleMapping);
			}
		}else if(null != path && "/login/loginIn.do".equals(path)){
			return;
		}else{
			response.sendRedirect(request.getContextPath()+ "/view/login.jsp");
		}
		
		if(null!=modelAndView)
			modelAndView.getModel().put("operateMap", operateMap);
		
	}
	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
//			Object handler, Exception ex) throws Exception {
//		
//	}

}
