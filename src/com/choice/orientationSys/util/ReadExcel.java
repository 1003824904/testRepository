package com.choice.orientationSys.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.domain.StudentInfo;
public class ReadExcel {
	
	//学生信息的集合
	public static List<StudentInfo> listBasicInfo = new ArrayList<StudentInfo>(); 
	//错误信息的集合
	public static Map<Integer,String[]> message = new HashMap<Integer,String[]>();
	
	/**
	 * 反射机制set参数
	 * @param objParam
	 * @param propertyMethod
	 * @param param
	 * @throws Exception
	 */
	private static void reflexMethod(Object objParam,String propertyMethod,String param)throws Exception{
		if(propertyMethod.contains(".")){
			//获得.之前的get方法
			String getCode = propertyMethod.substring(0, propertyMethod.indexOf("."));
			StringBuffer getStringBuff = new StringBuffer("get");
			getStringBuff.append(getCode.substring(0, 1).toUpperCase());
			getStringBuff.append(getCode.substring(1));
			Method methodOne = objParam.getClass().getMethod(getStringBuff.toString(), new Class[]{});
			
			//获得get方法之后获得其返回类型，然后实例化该对象
			String methodType= methodOne.getReturnType().toString();
			Class objClass = Class.forName(methodType.substring(methodType.indexOf(" ")+1));
			Object obj = objClass.newInstance();
			
			//截取.后面的方法名称，并获得其set方法
			String setCode = propertyMethod.substring(propertyMethod.indexOf(".")+1);
			StringBuffer setStringBuff = new StringBuffer("set");
			setStringBuff.append(setCode.substring(0,1).toUpperCase());
			setStringBuff.append(setCode.substring(1));
			Method setMethodOne = obj.getClass().getMethod(setStringBuff.toString(), param.getClass());
			//把需要设置的参数设置到get方法返回类型的对象当中
			setMethodOne.invoke(obj, new Object[] {param});
			
			//然后把对象传入到要保存的对象当中
			getStringBuff.replace(0, 1, "s");
			Method setMethodTwo = objParam.getClass().getMethod(getStringBuff.toString(),methodOne.getReturnType());
			setMethodTwo.invoke(objParam, new Object[] {obj});
			
		}else{
			String method = "set"+propertyMethod
			.substring(0, 1).toUpperCase() + propertyMethod.substring(1);
			Method methodEl = objParam.getClass().getMethod(method,param.getClass());
			methodEl.invoke(objParam, new Object[] {param});
		}
	}
	
	/**
	 * 读取学生信息Excel
	 * @param path 用户上传的Excel路径
	 * @return 读取Excel的结果
	 */
	public static String readExcel(String path,List<String> listEnumMeaning,List<String> listName){
		listBasicInfo.clear();
		message.clear();
		
		try {
			Workbook book = Workbook.getWorkbook(new File(path));
			//获得第三个工作表对象
			Sheet sheet = book.getSheet(2);
			Cell cell ;
			
			int colNum = sheet.getColumns();//列数
			int rowNum = sheet.getRows();//行数
			
			//学生Excel字段
			String[] studentCol = {"身份证号",
									"姓名",
									"性别",
									"出生日期",
									"考生类别",
									"民族",
									"政治面貌",
									"考生特征",
									"毕业类别",
									"外语语种",
									"户口所在地",
									"地区",
									"考试类型",
									"外语口试",
									"外语听力",
									"应试卷种",
									"总分",
									"投档成绩",
									"投档志愿",
									"专业1",
									"专业2",
									"专业3",
									"专业4",
									"专业5",
									"专业6",
									"录取专业",
									"学制",
									"计划性质",
									"批次",
									"科类",
									"收件人",
									"中学代码",
									"中学名称",
									"准考证号",
									"考生号",
									"邮寄地址",
									"邮政编码",
									"联系电话",
									"会考考号",
									"考生特长",
									"奖励或处分",
									"考生评语",
									"身高",
									"体重",
									"年度",
									"省市"};
			//学生列字段
			String[] studentColumns = {	"identityId",
										"name",
										"sex.enum_meaning",
										"birthday",
										"studentType.enum_meaning",
										"nationality.enum_meaning",
										"politicsFace.enum_meaning",
										"examineeCharacter",
										"graduationType.enum_meaning",
										"foreignLanguage.enum_meaning",
										"hkLocal",
										"region.name",
										"examType.enum_meaning",
										"oralExamination",
										"oralListener",
										"testTaking.enum_meaning",
										"totalScore",
										"sendMark",
										"sendWish.enum_meaning",
										"professionIdFirst.zymc",
										"professionIdSecond.zymc",
										"professionIdThird.zymc",
										"professionIdFourth.zymc",
										"professionIdFifth.zymc",
										"professionIdSix.zymc",
										"professionId.zymc",
										"educationSys.enum_meaning",
										"planNature.enum_meaning",
										"ascertain.enum_meaning",
										"professionCode.enum_meaning",
										"addressee",
										"middCode",
										"middName",
										"admissionCard",
										"studentId",
										"mailAddress",
										"postCode",
										"tel",
										"unionExamId",
										"strongSide",
										"rewardPunish",
										"examineeEvaluate",
										"height",
										"weight",
										"enterYear",
										"provinceCity.name"};
			//验证信息
			String[] validateColumns = {"[0-9]\\d{0,18}",						//身份证号
										"[\u4e00-\u9fa5]*",						//名字
										"(\u7537)|(\u5973)",					//性别					
										"(19|20)[\\d]{2}\\d{1,2}\\d{1,2}",		//生日
										"[\u4e00-\u9fa5]*",						//学生类型
										"[\u4e00-\u9fa5]*",						//民族
										"\\s|[\u4e00-\u9fa5]*",						//政治面貌
										"",											//考生特征
										"\\s|[\u4e00-\u9fa5]*",						//毕业类别
										"\\s|[\u4e00-\u9fa5]*",						//外语语种
										"\\s|[\u4e00-\u9fa5]*",						//户口所在地
										"\\s|[0-9]\\d{3,28}",						//地区
										"\\s|[\u4e00-\u9fa5]*",						//考试类型
										"",											//外语口语
										"",											//外语听力
										"\\s|[\u4e00-\u9fa5]*",						//应试卷种
										"\\s|\\d{0,3}",								//总分
										"\\s|\\d{0,3}",								//投档成绩
										"\\s|[1-9]",								//投档志愿
										"",											//专业1
										"",											//专业2
										"",											//专业3
										"",											//专业4
										"",											//专业5
										"",											//专业6
										"\\s|[\u4e00-\u9fa5]*",						//录取专业
										"\\s|[1-9]",								//学制
										"\\s|[\u4e00-\u9fa5]*",						//计划定向
										"\\s|[\u4e00-\u9fa5]*",						//批次
										"\\s|[\u4e00-\u9fa5]*",						//专业克雷吗
										"",											//收件人
										"\\s|\\d{0,8}",								//中学代码
										"\\s|[\u4e00-\u9fa5]*",						//中学名称
										"\\s|\\d{0,15}",							//准考证号
										"\\s|^\\d{10,20}",							//考生号
										"",											//邮寄地址
										"\\s|\\d{0,6}",								//邮政编码
										"\\s|^(13|15|18)[0-9]\\d{8}",				//联系电话
										"\\s|\\d{0,15}",							//会考考号
										"",											//特长
										"",											//奖励或触发
										"",											//考生评价
										"\\s|\\d{0,3}",								//身高
										"\\s|\\d{0,3}",								//体重
										"\\s|([0-9]*[1-9][0-9]*)",					//招生年度
										"\\s|[\u4e00-\u9fa5]*"						//省市
										};
			//检查用户提交的excel列是否对应
			for(int j=0;j<colNum;j++){
				if(! (studentCol[j].trim()) .equals((sheet.getCell(j,0).getContents()).trim())){	
					return "FileError" ;
				}
			}
			for(int i=1;i<rowNum;i++ ){	
				String[] str = new String[colNum];
				String[] meg = new String[colNum]; //错误信息
				String[] nullMeg = new String[colNum]; //空错误信息
				
				StudentInfo basicInfo = new StudentInfo();
				basicInfo.setId(Util.getUUID());
				basicInfo.setDeleteFlag("F");
				for(int col=0;col<colNum;col++){
					cell = sheet.getCell(col,i);//读取单元格
					String col_zh = studentCol[col];//读取当前单元格的列名
					String col_column = studentColumns[col];//读取当前单元格的字段名
					String col_validate = validateColumns[col];//读取当前单元格的验证信息
					String currentCol = cell.getContents();//读取当前单元格内容
					if(!"".equals(col_validate)){
						if("".equals(cell.getContents().trim()) || cell.getContents() == null){
							str[col] = "";
							meg[col] = col_zh+"不能为空";
							nullMeg[col] = col_zh+"不能为空";
						}else{
							if(!"".equals(col_validate)){//验证信息非空
								if(currentCol.matches(col_validate)){
									str[col] = currentCol;
									reflexMethod(basicInfo, col_column, currentCol);
									if(col_column.contains(".enum_meaning")){
										listEnumMeaning.add(currentCol);
									}else if(col_column.contains(".zyjc")){
										listName.add(currentCol);
									}
								}else{
									meg[col] = col_zh + StringConstant.INPUT_EXCEL_ERROR;//不符合条件则输入非法
								}
							}else{
								str[col] = currentCol;
								reflexMethod(basicInfo, col_column, currentCol);
								if(col_column.contains(".enum_meaning")){
									listEnumMeaning.add(currentCol);
								}else if(col_column.contains(".zyjc")){
									listName.add(currentCol);
								}
							}
						}
					}else{
						str[col] = currentCol;
						reflexMethod(basicInfo, col_column, currentCol);
						if(!"".equals(currentCol)){
						if(col_column.contains(".enum_meaning")){
							listEnumMeaning.add(currentCol);
						}else if(col_column.contains(".zymc")){
							listName.add(currentCol);
						}
						}
					}
				}
				
				int msgError = 0;
				for(String eg:nullMeg){
					if(eg != null && !"".equals(eg)){
						msgError++;
					}
				}
				if(msgError != 45 && i != rowNum ){
					if(meg!=null){
						for (String s : str) {
							if (s != null && !"".equals(s)) {
								listBasicInfo.add(basicInfo);
								System.out.println(str);
								break;
							}
						}
					}
				}
				//保存错误信息
				if(msgError !=32 && i != rowNum){
					for(String s:meg){
						if(s != null && !"".equals(s)){
							message.put(i, meg);
							break;
						}
					} 
				}
			}
			book.close();
			if(message.size()>0){
				return "error";
			}
			if(listBasicInfo.size() == 0){
				return "";
			}
			return "ok";
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
}
