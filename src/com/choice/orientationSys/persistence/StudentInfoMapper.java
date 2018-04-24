package com.choice.orientationSys.persistence;

import java.util.List;
import com.choice.orientationSys.domain.StudentInfo;

public interface StudentInfoMapper {
	
	
	public void saveBasicinfo(StudentInfo studentInfo);//保存学生信息
	/**
	 * 根据身份证号码查询学生信息
	 */
	public List<StudentInfo> findStudent(StudentInfo studentInfo);
	/**
	 * 修改学生的信息
	 */
	public void updateStudent(StudentInfo studentInfo);
	/**
	 * 修改确认报到
	 * @param studentInfo
	 */
	public void updateStudentAffirmStatus(StudentInfo studentInfo);
	/**
	 * 根据id查询信息
	 */
    public StudentInfo findStudentById(String studentId);
    
	public StudentInfo findBasicInfoByParam(StudentInfo studentInfo);
	
	public void updateInfoByIds(StudentInfo studentInfo);
	/**
	 * 删除
	 */
	public void deleteInfo(List<String> idList);

	/**
	 * 修改学生的信息
	 */
	public void updateStudents(StudentInfo studentInfo);

	/**
	 * 根据录取专业查询专业号和院系号生成学号
	 */
	public StudentInfo findStudentByProfessionalId(String id);

	
}
