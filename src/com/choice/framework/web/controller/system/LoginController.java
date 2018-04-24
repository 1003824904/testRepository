package com.choice.framework.web.controller.system;


import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.choice.framework.constants.StringConstant;
import com.choice.framework.constants.system.LoginConstants;
import com.choice.framework.domain.system.Account;
import com.choice.framework.domain.system.Logs;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.service.system.LoginService;
import com.choice.framework.service.system.ModuleService;
import com.choice.framework.util.DataSourceInstances;
import com.choice.framework.util.DataSourceSwitch;
import com.choice.framework.util.DateFormat;
import com.choice.framework.util.ProgramConstants;
import com.choice.framework.vo.AccountCache;
import com.choice.orientationSys.util.Util;

@Controller
@RequestMapping(value = "login")
@SessionAttributes({"accountId","ChoiceAcct","accountName","locale","firmId","firmDes","ip"})
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private LogsMapper logsMapper;
	@Autowired
	private ModuleService moduleService;
	
	/**
	 * 账号登录
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginIn")
	public ModelAndView loginIn(HttpServletResponse response,HttpSession session,HttpServletRequest request, ModelMap modelMap, Account account, String remember,String locale) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		String info = loginService.validateLogin(account);
		if(info.equals(StringConstant.TRUE)){
			AccountCache accountCache = loginService.loadAuthorityInfo(account);
			modelMap.put("accountCache", accountCache);
			modelMap.put("accountId", accountCache.getAccount().getId());
			modelMap.put("account", account);
			modelMap.put("accountName", account.getName());
			modelMap.put("locale", locale);
			
			//判断是否自动登录
			Cookie rememberCookie = new Cookie("remember", remember);
			rememberCookie.setMaxAge(10 * 24 * 60 * 60);
			rememberCookie.setPath("/");
			response.addCookie(rememberCookie);
			if (StringConstant.TRUE.equals(remember)) {
				Cookie nameCookie = new Cookie("accountName", account.getName());
				Cookie pwCookie = new Cookie("password", account.getPassword());
				Cookie localeCookie = new Cookie("locale",locale);
				nameCookie.setMaxAge(10 * 24 * 60 * 60);
				nameCookie.setPath("/");
				pwCookie.setMaxAge(10 * 24 * 60 * 60);
				pwCookie.setPath("/");
				localeCookie.setMaxAge(10 * 24 * 60 * 60);
				localeCookie.setPath("/");
				response.addCookie(nameCookie);
				response.addCookie(pwCookie);
				response.addCookie(localeCookie);
			} else {
//				Cookie nameCookie = new Cookie("accountName", "");
				Cookie nameCookie = new Cookie("accountName", account.getName());
				Cookie pwCookie = new Cookie("password", "");
				Cookie localeCookie = new Cookie("locale","");
//				nameCookie.setMaxAge(0);
				nameCookie.setMaxAge(10 * 24 * 60 * 60);
				nameCookie.setPath("/");
				pwCookie.setMaxAge(0);
				pwCookie.setPath("/");
				localeCookie.setMaxAge(0);
				localeCookie.setPath("/");
				
				response.addCookie(nameCookie);
				response.addCookie(pwCookie);
				response.addCookie(localeCookie);
			}
			modelMap.put("sysDate", DateFormat.getStringByDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			//modelMap.put("ChoiceAcct",map.get("code"));
			modelMap.put("ChoiceAcct","1");
			//获取当前登录用户定义的常用操作
			//加入登陆日志
			String ip=Util.getIpAddr(request);
			modelMap.put("ip", ip);
			Logs logs=new Logs(Util.getUUID(),accountCache.getAccount().getId(),new Date(),LoginConstants.LOGIN_EVENTS,LoginConstants.LOGIN_CONTENTS,ip,ProgramConstants.OVERALL);
			logsMapper.addLogs(logs);
			return new ModelAndView(LoginConstants.MAIN, modelMap);
		}
		else{
			Cookie nameCookie = new Cookie("accountName", account.getName());
			nameCookie.setMaxAge(10 * 24 * 60 * 60);
			nameCookie.setPath("/");
			response.addCookie(nameCookie);
			modelMap.put("info", info);
			modelMap.put("loginOut", "loginOut");
			return new ModelAndView(LoginConstants.LOGIN, modelMap);
		}
		
	}
	
	/**
	 * 账号登出
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginOut")
	public ModelAndView loginOut(HttpServletResponse response,HttpSession session, ModelMap modelMap) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
	//	Cookie nameCookie = new Cookie("accountName", "");
		Cookie pwCookie = new Cookie("password", "");
		Cookie localeCookie = new Cookie("locale","");
		Cookie rememberCookie = new Cookie("remember", "F");
	//	nameCookie.setMaxAge(-1);
	//	nameCookie.setPath("/");
		pwCookie.setMaxAge(-1);
		pwCookie.setPath("/");
		localeCookie.setMaxAge(-1);
		localeCookie.setPath("/");
		rememberCookie.setMaxAge(-1);
		rememberCookie.setPath("/");
		
	//	response.addCookie(nameCookie);
		response.addCookie(pwCookie);
		response.addCookie(localeCookie);
		response.addCookie(rememberCookie);

		session.removeAttribute("accountId");
		modelMap.put("loginOut", "loginOut");
		return new ModelAndView(LoginConstants.LOGIN, modelMap);
	}
	
	/**
	 * 账号登出
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginNull")
	public ModelAndView loginNull(HttpServletResponse response,HttpSession session, ModelMap modelMap) throws Exception{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.SCM);//选择数据源
		return null;
	}
	
}

