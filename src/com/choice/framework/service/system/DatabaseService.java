package com.choice.framework.service.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.driver.Const;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.choice.framework.domain.system.Tables;
import com.choice.framework.exception.CRUDException;
import com.choice.framework.persistence.system.DatabaseMapper;
import com.choice.orientationSys.persistence.system.impl.PageManager;
import com.choice.orientationSys.util.Page;

@Service
public class DatabaseService {
	
	private static Logger log = Logger.getLogger(LogsService.class);
	@Autowired
	private DatabaseMapper databaseMapper;
	@Autowired
	private PageManager<Tables> pageManager;

	
	private String fileName="/jdbc.properties";
	private String driver = "";
	private String url = "";
	private String username ="";
	private String password = "";
	private String netname = "";
	Connection conn=null;
	
	/**
	 * 导出数据库
	 * @param logs
	 * @return
	 * @throws CRUDException
	 */
	public void exportDatabase(HttpServletRequest request) throws CRUDException{
		try{
			Runtime r= Runtime.getRuntime();
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/") + "\\";
			connGain();
			String exp = "cmd /c start exp " + username + "/" + password;
			if (!"localdb".equals(netname)) {
				exp = exp + "@" + netname;
			}
			exp = exp + " file=";
			exp = exp + ctxPath +"logist.dmp";
//			Process p=r.exec("cmd /c start exp logist/logist file=F:/logist.dmp");
//			p.waitFor();
//			p.destroy();
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 获取数据库账户信息
	 * @param logs
	 * @return
	 * @throws CRUDException
	 */
	public Connection connGain() throws CRUDException{
		Properties p = new Properties();
		try {

			InputStream in = Const.class.getResourceAsStream(fileName);
			p.load(in);
			in.close();
			if(p.containsKey("jdbc.driver")){
				this.driver = p.getProperty("jdbc.driver");
				}
			if(p.containsKey("jdbc.url")){
				this.url = p.getProperty("jdbc.url");
				}
			if(p.containsKey("jdbc.username")){
				this.username = p.getProperty("jdbc.username");
				}

			if(p.containsKey("jdbc.password")){
				this.password = p.getProperty("jdbc.password");
				}

			if(p.containsKey("netname")){
				this.netname = p.getProperty("netname");
				}
			} catch (IOException ex) {
				log.error(ex);
				// Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
				}
		System.out.println(p.getProperty("driver"));
		try {
			Class.forName(this.driver);
			conn = DriverManager.getConnection(this.url,this.username,this.password);
			} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("获取连接异常");
			} catch (ClassNotFoundException ex) {
			System.out.print("加载驱动出错");
			ex.printStackTrace();
			}
		return conn;
		}
	
	/**
	 * 查询数据库表
	 * @return
	 * @throws CRUDException
	 */
	public List<Tables> queryTables( Tables tables, Page page) throws CRUDException{
		try{
			return pageManager.selectPage(tables, page, DatabaseMapper.class.getName()+".queryTables");
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 查询数据库表
	 * @return
	 * @throws CRUDException
	 */
	public void exportTables(List<String> names, HttpServletRequest request) throws CRUDException{
		try{
			Runtime r= Runtime.getRuntime();
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/");
			connGain();
			String exp = "cmd /c start exp " + username + "/" + password;
			if (!"localdb".equals(netname)) {
				exp = exp + "@" + netname;
			}
			exp = exp + " file=";
			exp = exp + ctxPath +"logist.dmp" + "tables=("+names+")";
//			Process p=r.exec("cmd /c start exp logist/logist file=F:/logist1.dmp tables=("+names+")");
			Process p=r.exec(exp);
			p.waitFor();
			p.destroy();
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 * 将文件上传到temp文件夹下
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String upload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String realFilePath = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");
			String realFileName = file.getOriginalFilename();
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/");
			String fileuploadPath = ctxPath;
			File dirPath = new File(fileuploadPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			realFilePath = fileuploadPath + realFileName;
			File uploadFile = new File(realFilePath);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realFilePath;
	}

	/**
	 * 对dmp进行验证
	 */
	public void importDmpfile(String path, HttpServletRequest request) throws CRUDException {
		try{
			Runtime r= Runtime.getRuntime();
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/");
			connGain();
			String imp = "cmd /c start imp " + username + "/" + password;
			if (!"localdb".equals(netname)) {
				imp = imp + "@" + netname;
			}
			imp = imp + " file=";
			imp = imp + ctxPath +"logist.dmp";
			Process p=r.exec(imp);
//			Process p=r.exec("cmd /c start imp logist/logist file="+path);
			p.waitFor();
			p.destroy();
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
}
