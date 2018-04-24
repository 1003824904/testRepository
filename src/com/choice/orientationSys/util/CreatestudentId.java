package com.choice.orientationSys.util;

import java.util.Random;

import com.choice.orientationSys.domain.StudentInfo;

public class CreatestudentId {
	 static Random r = new Random();
	public static String createId(StudentInfo studentInfo){
		//String professionId=studentInfo.getProfessionId().getId().substring(0, 8);
		  String[] data = {"0","1","2","3","4","5","6","7","8","9"};
		  String eightrandom="";
	        Random   r=new   Random();
	        for(int   i=0;i<12;i++)
	        {
	        	eightrandom=eightrandom+data[r.nextInt(10)];
	         }
	  //      System.out.println(professionId+eightrandom);
		return eightrandom;
	}
	
}
