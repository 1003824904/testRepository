package com.choice.orientationSys.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;



/**
 * 自定义标�?分页�?
 * @author Administrator
 *
 */
public class PageTag extends TagSupport {

	
	protected Object page; //page对象
	protected String path; //项目路径
	protected String form; //指定表单
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	
	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

	// ��дdoStartTag()����
	@Override
	public int doStartTag()  throws JspException {
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

//			System.out.println("*************"+this.getClass().getResource("/").toString().replace("file:/", "").replace("classes/", "")+"*************");
			
//			properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, request.getContextPath()+"/WEB-INF/tld/");
//			System.out.println("*************-----------------:"+ request.getContextPath()+"/WEB-INF/tld/");
			properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, request.getSession().getServletContext().getRealPath("/WEB-INF/tld"));//jboss
//			properties.put(Velocity.FILE_RESOURCE_LOADER_PATH, this.getClass().getResource("/").toString().replace("file:/", "").replace("classes/", "")+"tld/");//weblogic  tomcat
			properties.put(Velocity.INPUT_ENCODING, "UTF-8");
			properties.put(Velocity.OUTPUT_ENCODING, "UTF-8");
			ve.init(properties);
			VelocityContext vContext = new VelocityContext();
			vContext.put("page", page);
			vContext.put("form", form);
			vContext.put("path", request.getContextPath());
			Template template = ve.getTemplate("page.vm", "UTF-8");

			// 取得velocity的上下文context

			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(vContext, writer);
			
			pageContext.getOut().print(writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (EVAL_BODY_INCLUDE);

	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

	

}
