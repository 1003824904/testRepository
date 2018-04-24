package com.choice.orientationSys.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.choice.orientationSys.constants.StringConstant;
import com.choice.orientationSys.domain.StudentInfo;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

public class ReadDBF {
	
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
	
	public static Map<String, List> readDBF(String path)    
	   
    {    
	   List<String> listEnumValue = new ArrayList<String>();
	   List<String> listProfessionalName = new ArrayList<String>();
	   List<StudentInfo> listStudentInfo = new ArrayList<StudentInfo>();
	   Map<String, List> map = new HashMap<String, List>();
       InputStream fis = null;    
   
        try     
   
        {    
   
            //读取文件的输入流    
   
            fis  = new FileInputStream(path);    
   
            //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息    
   
            DBFReader reader = new DBFReader(fis);     
            //设置编码方式,防止中文乱码
            reader.setCharactersetName("GB2312"); 
            //调用DBFReader对实例方法得到path文件中字段的个数    
   
            int fieldsCount = reader.getFieldCount();    
            
            //取出字段信息    
            List<String> fieldList = new ArrayList<String>();
            for( int i=0; i<fieldsCount; i++)     
   
            {    
   
              DBFField field = reader.getField(i);    
   
              System.out.println(field.getName());
              fieldList.add(field.getName());
   
            }    
            //学生Excel字段,要读取的列
			String[] studentCol = {"KSH",
									"ZKZH",
									"XM",
									"XBDM",
									"CSNY",
									"ZZMMDM",
									"MZDM",
									"KSLBDM",
									"BYLBDM",
									"ZXDM",
									"ZXMC",
									"WYYZDM",
									"KSTZ",
									"DQDM",
									"SFZH",
									"JTDZ",
									"YZBM",
									"LXDH",
									"KSTC",
									"WYKS",
									"KSLXDM",
									"SJR",
									"YSJZDM",
									"WYTL",
									"PCDM",
									"KLDM",
									"JHXZ",
									"CJ",
									"TDCJ",
									"ZGF",
									"TDZY",
									"LQZY",
									"BZ",
									"ZYDH1",
									"ZYDH2",
									"ZYDH3",
									"ZYDH4",
									"ZYDH5",
									"ZYDH6"
			};
			List<String> stuCoList = Arrays.asList(studentCol);
			//学生属性字段
			String[] studentColumns = {	
										"examinee",
										"admissionCard",
										"name",
										"sex.enum_value",
										"birthday",
										"politicsFace.enum_value",
										"nationality.enum_value",
										"studentType.enum_value",
										"graduationType.enum_value",
										"middCode",
										"middName",
										"foreignLanguage.enum_value",
										"examineeCharacter",
										"region.code",
										"identityId",
										"familyAddress",
										"postCode",
										"tel",
										"strongSide",
										"oralExamination",
										"examType.enum_value",
										"addressee",
										"testTaking.enum_value",
										"oralListener",
										"ascertain.enum_value",
										"professionCode.enum_value",
										"planNature.enum_value",
										"totalScore",
										"sendMark",
										"highestScore",
										"sendWish.enum_value",
										"professionId.zyh",
										"remark",
										"professionIdFirst.zyh",
										"professionIdSecond.zyh",
										"professionIdThird.zyh",
										"professionIdFourth.zyh",
										"professionIdFifth.zyh",
										"professionIdSix.zyh"
			};
			//TODO 
			
            Object[] rowValues;    
   
            //一条条取出path文件中记录    
   
            while((rowValues = reader.nextRecord()) != null)     
   
            {    
            	StudentInfo studentInfo = new StudentInfo();
            	studentInfo.setId(Util.getUUID());
            	studentInfo.setDeleteFlag(StringConstant.FALSE); 
            	int j = 0;
	              for( int i=0; i<rowValues.length; i++)     
	   
	              {   
	            		
	            		if(stuCoList.contains(fieldList.get(i))){
	                		String col_column = studentColumns[j];
	                		String currentCol = rowValues[i].toString().trim();
	                		if(studentCol[j].equals("CSNY")){
	                			DateFormat format = new SimpleDateFormat("yyyyMMdd");  
	                			Date date = null;
	                			currentCol = format.format(date.parse(currentCol));
	                			System.out.println(currentCol);

	                		}
	                		reflexMethod(studentInfo, col_column,currentCol );
	                		if(col_column.contains(".enum_value")){
	    						listEnumValue.add(currentCol);
	    					}else if(col_column.contains(".zyh")){
	    						listProfessionalName.add(currentCol);
	    					}
                		   System.out.println(rowValues[i]);  
	                		j++;
	                	}  
	                
	              }    
	              listStudentInfo.add(studentInfo);
            }    
            map.put("dict", listEnumValue);
            map.put("professional", listProfessionalName);
            map.put("listStudentInfo", listStudentInfo);
            
            return map;
   
          }    
          catch(Exception e)     
   
          {    
   
          e.printStackTrace();    
   
          }    
   
          finally   
   
          {    
   
          try{    
   
               fis.close();    
   
          }catch(Exception e){}    
   
          }
		return map;    
   
    }    

}
