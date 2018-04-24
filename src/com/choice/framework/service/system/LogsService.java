package com.choice.framework.service.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choice.framework.domain.system.Logs;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.LogsMapper;
import com.choice.framework.util.ForResourceFiles;
import com.choice.orientationSys.persistence.system.impl.PageManager;
import com.choice.orientationSys.util.Page;
import com.choice.orientationSys.util.Util;

@Service
public class LogsService {
	
	private static Logger log = Logger.getLogger(LogsService.class);
	
	@Autowired
	private LogsMapper logsMapper;
	@Autowired
	private PageManager<Logs> pageManager;
	
	/**
	 * 查询日志信息
	 * @param logs
	 * @return
	 * @throws CRUDException
	 */
	public List<Logs> findLogs(Logs logs, Page page) throws CRUDException{
		try{
			if(null != logs.getLogindateEnd()){
				SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
				String d = sft.format(logs.getLogindateEnd());
				logs.setLogindateEnd(sft.parse(d.substring(0, 10)+" 23:59:59"));
			}
			if(null != logs.getEvents() && !"".equals(logs.getEvents())){
				logs.setEvents("'"+logs.getEvents().replaceAll(",", "','")+"'");
			}
			if(logs.getOrderBy()!=null&& logs.getOrderBy().length()!=0 && logs.getOrderBy().substring(logs.getOrderBy().length()-1, logs.getOrderBy().length()).equals(",")){
				logs.setOrderBy(logs.getOrderBy().substring(0, logs.getOrderBy().lastIndexOf(",")));
			}
			return pageManager.selectPage(logs, page, LogsMapper.class.getName()+".findLogs");
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 添加日志信息
	 * @param logs
	 * @throws CRUDException
	 */
	public void addLogs(Logs logs,HttpServletRequest request,HttpSession session) throws CRUDException{
		try{
			logs.setId(Util.getUUID());//主键
			String accountId=session.getAttribute("accountId").toString();//账号id
			logs.setAccountId(accountId);
			logs.setIps(Util.getIpAddr(request));//ip  地址
			logs.setLogindate(new Date());		//登陆时间日期
			logsMapper.addLogs(logs);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 删除日志信息
	 * @param logs
	 * @throws CRUDException
	 */
	public void deleteLogs(List<String> listId) throws CRUDException{
		try{
			logsMapper.deleteLogs(listId);
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	//获取日志目录中所有日志文件
	public List<String> listLogs(HttpServletRequest request) throws CRUDException{
		List<String> fileNames = null;
		try{
			File f = new File(ForResourceFiles.getValByKey("log4j.properties", "log4j.appender.log2file.File")).getParentFile();
			String[] names = null;
			if(f.isDirectory() && f.canRead()){
				names = f.list(new FilenameFilter(){
					@Override
					public boolean accept(File file, String str) {
						if("log".equals(str.substring(str.lastIndexOf(".")+1)))
							return true;
						else
							return false;
					}
					
				});
			}
			fileNames = Arrays.asList(names);
		}catch(Exception e){
			throw new CRUDException(e);
		}
		return fileNames;
	}
	//日志文件下载
	public void getLogs(String filename,HttpServletRequest request,HttpServletResponse response){
		File f = new File(ForResourceFiles.getValByKey("log4j.properties", "log4j.appender.log2file.File")).getParentFile();
		File aim = new File(f,filename);
		if(aim.isFile() && aim.canRead()){
			response.setContentType("text/plain; charset=UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="  
	                + filename);
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(aim);
			out = response.getOutputStream();
			byte[] buffer = new byte[2048];
			int size = in.read(buffer);
			while(size > 0){
				out.write(buffer, 0, size);
				size = in.read(buffer);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//日志文件删除
	public void delLogFiel(HttpServletRequest request,String filename){
		File f = new File(ForResourceFiles.getValByKey("log4j.properties", "log4j.appender.log2file.File")).getParentFile();
		String[] filenames = filename.split(",");
		for(String name : filenames){
			File aim = new File(f,name);
			if(aim.isFile()){
				aim.delete();
			}
		}
	}
}
