package com.choice.orientationSys.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.choice.framework.domain.system.Account;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.domain.Dict;
import com.choice.orientationSys.domain.StudentInfo;
import com.choice.framework.exception.CRUDException;
import com.choice.orientationSys.persistence.DictMapper;
import com.choice.orientationSys.persistence.StudentInfoMapper;
import com.choice.orientationSys.persistence.system.impl.PageManager;
import com.choice.orientationSys.util.CreatestudentId;
import com.choice.orientationSys.util.Page;
import com.choice.orientationSys.util.ReadDBF;
import com.choice.orientationSys.util.ReadExcel;
import com.choice.orientationSys.util.Util;
@Service
public class StudentInfoService {
	@Autowired
	private StudentInfoMapper studentInfoMapper;
	@Autowired
	private DictMapper dictMapper;
	@Autowired
	private PageManager<StudentInfo> pageManager;
	private static Logger log = Logger.getLogger(StudentInfoService.class);
	
	private static HashMap<String, HashMap<String, String>> hashMap = new HashMap<String, HashMap<String,String>>();
	
	/**
	 * 保存学生的基本信息
	 * 
	 */
	public void saveInfo(StudentInfo studentInfo,HttpSession session)throws CRUDException{
		try{
			
			StudentInfo student=studentInfo;
			studentInfo.setBirthday(studentInfo.getBirthday().substring(0, 4)+studentInfo.getBirthday().substring(5, 7)+studentInfo.getBirthday().substring(8,10));
			studentInfo.setEnterTime(studentInfo.getEnterTime().substring(0,4)+studentInfo.getEnterTime().substring(5, 7));
			student.setId(Util.getUUID());
			student.setStudentId(CreatestudentId.createId(student));
			Account account  =  new Account();
			account.setId((String)session.getAttribute("accountId"));
			studentInfo.setCreateAccount(account);
			studentInfo.setCreateDate(new Date());
			studentInfo.setOperateAccount(account);
			studentInfo.setOperateDate(new Date());
			student.setEnterState(StringConstant.STATE_DISABLE);
			studentInfoMapper.saveBasicinfo(studentInfo);
		} catch (RuntimeException e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 根据学生身份证号查询学生信息
	 * @param studentInfo
	 * @return
	 * @throws CRUDException 
	 */
	public List<StudentInfo> findStudent(StudentInfo studentInfo,Page page) throws CRUDException {
		List<StudentInfo> listStudent = new ArrayList<StudentInfo>();
		try {
			//listStudent = studentInfoMapper.findStudent(studentInfo);
			listStudent = pageManager.selectPage(studentInfo, page, StudentInfoMapper.class.getName()+".findStudent");
		} catch (RuntimeException e) {
			log.error(e);
			throw new CRUDException(e);
		}
		return listStudent;
	}
	/**
	 * 修改学生信息
	 * @param studentInfo
	 * @throws CRUDException
	 */
	public void updateStudentInfo(StudentInfo studentInfo,HttpSession session) throws CRUDException {
		try {

		

			if(studentInfo.getEnterTime()!=null && !"".equals(studentInfo.getEnterTime()) && studentInfo.getEnterTime().length()==7){

				studentInfo.setEnterTime(studentInfo.getEnterTime().substring(0,4)+studentInfo.getEnterTime().substring(5, 7));
			}
			if(studentInfo.getBirthday() !=null && !"".equals(studentInfo.getBirthday()) && studentInfo.getBirthday().length()==10){
				studentInfo.setBirthday(studentInfo.getBirthday().substring(0, 4)+studentInfo.getBirthday().substring(5, 7)+studentInfo.getBirthday().substring(8,10));
			}
			Account account  =  new Account();
			account.setId((String)session.getAttribute("accountId"));
			studentInfo.setOperateAccount(account);
			studentInfo.setOperateDate(new Date());
			studentInfoMapper.updateStudent(studentInfo);
		}catch (RuntimeException e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	public StudentInfo findStudentById(String studentId) throws CRUDException {
		try {
			return studentInfoMapper.findStudentById(studentId);
		} catch (RuntimeException e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
	/**
	 * 修改报到状态
	 * @param studentInfo
	 * @throws CRUDException
	 */
	public void updateStudentAffirmStatus(StudentInfo studentInfo,HttpSession session) throws CRUDException{
		try {
			Account account  =  new Account();
			account.setId((String)session.getAttribute("accountId"));
			studentInfo.setOperateAccount(account);
			studentInfo.setOperateDate(new Date());
			studentInfo.setReportTime(new Date());
			studentInfoMapper.updateStudentAffirmStatus(studentInfo);
			//kettle
			String path = session.getServletContext().getRealPath("/")+"ktr"+"\\"+StringConstant.KTR;
			//TestTrans.runTransformation(path,studentInfo.getId());
		} catch (RuntimeException e){
			log.error(e);
			throw new CRUDException(e);
		}
	}
	
	/**
	 *
	 * @param response
	 * @param request
	 * @throws IOException
	 * 下载信息模板
	 */
	public void downloadTemplate(String fileName,HttpServletResponse response,HttpServletRequest request)throws IOException{
		OutputStream outp = null;
		FileInputStream in = null;
		try{
			String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "\\" + "template\\";   
			String filedownload = ctxPath + fileName; 
			fileName = URLEncoder.encode(fileName, "UTF-8");  
		    //要下载的模板所在的绝对路径
		    response.reset();   
		    response.addHeader("Content-Disposition", "attachment; filename=" + fileName );   
		    response.setContentType("application/octet-stream;charset=UTF-8");   
		    outp = response.getOutputStream();
		    in = new FileInputStream(filedownload);
		    byte[] b = new byte[1024];
		    int i = 0;
		    while((i = in.read(b)) > 0){
		    	outp.write(b, 0, i);
		    }
		    outp.flush();
		}catch(Exception e){
			log.error(e);
		}finally{
			if(in != null){
				in.close();
				in = null;
			}
			if(outp != null){
				outp.close();
				outp = null;
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @throws IOException
	 * 上传文件
	 */
	public String upload(HttpServletRequest request) throws IOException{
		String realFileName = "";
		try {
			// 转型为MultipartHttpRequest
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 根据前台的name名称得到上传的文件
			MultipartFile file = multipartRequest.getFile("file");
			// 获得文件名:
			realFileName = file.getOriginalFilename();
			// 获取路径
			String ctxPath = request.getSession().getServletContext().getRealPath("/")+"temp\\";
			String fileuploadPath = ctxPath ;
			// 创建文件 
			File dirPath = new File(fileuploadPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			File uploadFile = new File(fileuploadPath + realFileName);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realFileName;
	}
	
	/**
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * 验证Excel中填写的信息
	 */
	public Map<String,String> validate(HttpServletRequest request,String fileName,HttpSession session) throws CRUDException{
		List<String> listEnumMeaning= new ArrayList<String>();
		List<String> listName= new ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		try{
			String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "\\" + "temp\\";   
			String validatePath = ctxPath + fileName; 
			//获得验证Excel的结果
			StringBuffer validateResult = checkingExcel(validatePath,request,listEnumMeaning,listName,fileName);
			map.put("validateResult", validateResult.toString());
			if("ok".equals(validateResult.toString())){
				map.put("importResult", saveStudentInfo(request,listEnumMeaning,listName,session,fileName));
				deleteFile(validatePath);
			}else{
				deleteFile(validatePath);
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		return map;
	}
	
	/**


	/**
	 * 
	 * @param request
	 * @return
	 * 从Excel读取记录保存到数据库
	 */
	public String saveStudentInfo(HttpServletRequest request,List<String> listEnumMeaning,List<String> listName,HttpSession session,String fileName) throws CRUDException{
		List<StudentInfo> listStudentInfo = new ArrayList<StudentInfo>();
		List<Dict> dictList = new ArrayList<Dict>();
//		List<Professional> professionalList = new ArrayList<Professional>();
		listStudentInfo = ReadExcel.listBasicInfo;
		try{
		//	professionalList = professionalMapper.findProfessionalByName(listName);//查询excel中专业的name集合对应在字典表中的id
			//dictList = dictMapper.findDictByName(listEnumMeaning);//查询excel中其他信息项字典表name集合对应在字典表中的id
			for(StudentInfo  basicInfo:listStudentInfo){
				basicInfo.setSex(findDict(basicInfo.getSex().getEnum_meaning(), dictList));
				basicInfo.setPoliticsFace(findDict(basicInfo.getPoliticsFace().getEnum_meaning(),dictList));
				basicInfo.setNationality(findDict(basicInfo.getNationality().getEnum_meaning(),dictList));
				basicInfo.setStudentType(findDict(basicInfo.getStudentType().getEnum_meaning(),dictList));
				basicInfo.setGraduationType(findDict(basicInfo.getGraduationType().getEnum_meaning(),dictList));
				basicInfo.setForeignLanguage(findDict(basicInfo.getForeignLanguage().getEnum_meaning(),dictList));
				basicInfo.setTestTaking(findDict(basicInfo.getTestTaking().getEnum_meaning(),dictList));
				basicInfo.setSendWish(findDict(basicInfo.getSendWish().getEnum_meaning(),dictList));
				basicInfo.setPlanNature(findDict(basicInfo.getPlanNature().getEnum_meaning(),dictList));
				basicInfo.setAscertain(findDict(basicInfo.getAscertain().getEnum_meaning(),dictList));
				basicInfo.setProfessionCode(findDict(basicInfo.getProfessionCode().getEnum_meaning(),dictList));
	//			basicInfo.setProfessionId(findProfessional(basicInfo.getProfessionId().getZymc(),professionalList));
				Account account  =  new Account();
				account.setId((String)session.getAttribute("accountId"));
				basicInfo.setCreateAccount(account);
				basicInfo.setCreateDate(new Date());
				basicInfo.setOperateAccount(account);
				basicInfo.setOperateDate(new Date());
				basicInfo.setEnterState("0");
				basicInfo.setStudentId(CreatestudentId.createId(basicInfo));
				//查询专业信息
				studentInfoMapper.saveBasicinfo(basicInfo);
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
	
		return StringConstant.READ_EXCEL_OK;
	}
	
	/**
	 * 
	 * @param enumMeaning
	 * @param dictList
	 * @return
	 * @throws CRUDException
	 * excel中用到的方法。根据excel的中文名，查询出字典表中对应的id
	 */
	public Dict findDict(String enumMeaning,List<Dict> dictList) throws CRUDException{
		Dict dictRe = new Dict();
		try{
			for(Dict dict : dictList){
				if(enumMeaning.equals(dict.getEnum_meaning())){
					dictRe.setId(dict.getId());
					break;
				}
			}
		}catch(Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		return dictRe;
	}
	
	public Dict getDict(String str){
		Dict dict = new Dict();
		String[] strArr = str.split("-");
		String id = hashMap.get(str).get(strArr[1]);
		dict.setId(id);
		return dict;
	}
	/**
	 * 
	 * @param path
	 * @param request
	 * @return
	 * 验证Excel格式
	 */
	public StringBuffer checkingExcel(String path,HttpServletRequest request,List<String> listEnumMeaning,List<String> listName ,String filename){
		String result=null;
	    result  = ReadExcel.readExcel(path,listEnumMeaning,listName);
		StringBuffer sb = new StringBuffer();
	    if("FileError".equals(result)){
	    	return sb.append(StringConstant.READ_EXCEL_ERROR);
	    }
        
	    if("error".equals(result)){
	    	Map<Integer,String[]> listMegs = ReadExcel.message;
	    	Set<Integer> keys = listMegs.keySet();
	    	for(Integer key:keys){
	    		sb.append("<li>第"+(int)(key+1)+"行：");
				for(int k=0;k<listMegs.get(key).length;k++){
					if(listMegs.get(key)[k] != null ){
						sb.append("["+listMegs.get(key)[k]+"]");
					}
				}
			}
	    	return sb;
	    }
	    if("".equals(result)){
	    	sb.append("Excel 为空");
		    return sb;
	    }
	    sb.append("ok");
	    return sb;
	}
	
	/**
	 * 删除用户上传的文件
	 * @param request
	 * @param filename
	 */
	public void deleteFile(String path){
	    File file = new File(path);
	    if(file.exists()){
	    	file.delete();
	    }
	}
	/**
	 * 
	 * @param id
	 * @return
	 * 根据id查询报名人员的详细信息
	 */
	public StudentInfo findBasicInfoByParam(StudentInfo studentInfo) throws CRUDException{
		try{
			studentInfo = studentInfoMapper.findBasicInfoByParam(studentInfo);
		}catch (Exception e){
			log.error(e);
			throw new CRUDException(e);
		}
		return studentInfo;
	}
	/**
	 * 下载打印插件
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	public void downLoad(HttpServletResponse response,HttpServletRequest request) throws IOException{
		OutputStream outp = null;
		FileInputStream in = null;
		try{
			String fileName = "install_lodop.exe";
			String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "\\" + "printPlugin\\";   
			String filedownload = ctxPath + fileName; 
			fileName = URLEncoder.encode(fileName, "UTF-8");  
		    //要下载的模板所在的绝对路径
		    response.reset();   
		    response.addHeader("Content-Disposition", "attachment; filename=" + fileName );   
		    response.setContentType("application/octet-stream;charset=UTF-8");   
		    outp = response.getOutputStream();
		    in = new FileInputStream(filedownload);
		    byte[] b = new byte[1024];
		    int i = 0;
		    while((i = in.read(b)) > 0){
		    	outp.write(b, 0, i);
		    }
		    outp.flush();
		}catch(Exception e){
			log.error(e);
		}finally{
			if(in != null){
				in.close();
				in = null;
			}
			if(outp != null){
				outp.close();
				outp = null;
			}
		}
	}
	/**
	 * 批量修改信息
	 */
	public void update(StudentInfo studentInfo,String idList)  throws CRUDException{
		StudentInfo student=studentInfo;
		try{
		List<String> ids=Arrays.asList(idList.split(","));
		student.setIds(ids);
		studentInfoMapper.updateInfoByIds(student);
		}catch(Exception e){
			log.error(e);
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 删除
	 */
	public  void deleteInfo(List<String> idList)throws CRUDException{
		try{
			studentInfoMapper.deleteInfo(idList);
		}catch(Exception e){
			log.error(e);
		}
		}
	
	/**
	 * 根据状态查询学生信息
	 */
	public List<StudentInfo> findStudentByState(String state)throws CRUDException{
		List<StudentInfo> list=null;
		StudentInfo studentInfo=new StudentInfo();
		studentInfo.setEnterState(state);
		studentInfo.setId(Util.getUUID());
		try{
			list=studentInfoMapper.findStudent(studentInfo);
		}catch(Exception e){
			log.error(e);
		}
		return list;
		}
	/**
	 * 修改学生信息
	 * @param studentInfo
	 * @throws CRUDException
	 */
	public void updateStudentInfos(StudentInfo studentInfo,HttpSession session) throws CRUDException {
		try {
			Account account  =  new Account();
			account.setId((String)session.getAttribute("accountId"));
			studentInfo.setOperateAccount(account);
			studentInfo.setOperateDate(new Date());
			studentInfoMapper.updateStudent(studentInfo);
		} catch (RuntimeException e) {
			log.error(e);
			throw new CRUDException(e);
		}
	}
}
