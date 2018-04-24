package com.choice.orientationSys.domain;

import java.util.Date;
import java.util.List;

import com.choice.framework.domain.system.Account;
import com.choice.orientationSys.constants.StringConstant;

public class StudentInfo {
	
	private String id;
	//基本信息
	private String name;         			 //姓名
	private String identityId;   			 //身份证号
	private Dict sex;             			//性别
	private String birthday;      			//出生年月
	private Dict nationality;    			 //民族
	//private Area nativePlace;    			 //籍贯
	
	//联系方式
	private String email;        			 //电子邮箱
	private String tel;          			 //联系电话
	private String homePage;     			 //主页地址
	private String postCode;     			 //邮政编码
	
	//学生信息
	private Dict politicsFace;  			//政治类型
	private Dict marriageState;  			 //婚姻状况
//	private Area birthplace;   				 //出生地
	private String nowlive;      			 //现住址
	private String hkLocal;       			//户口所在地
	private Dict hknature;        			//户口性质
	private String strongSide;   			 //特长
	
	//入学信息
	private Dict studentSource; 			//学生来源
	private Dict studentType;     			//考生类别
	private Dict cultureStyle;    			//培养方式
	private String enterTime;     			//入学时间
	private String graduationTime;			//毕业年份
	
	private String admissionCard; 			//准考证号
	private Dict educationSys;     			//学制
	private Dict grade;         			//年级
//	private Professional professionId;		//录取专业
//	private Professional professionIdFirst; //专业1
//	private Professional professionIdSecond;//专业2
//	private Professional professionIdThird; //专业3
//	private Professional professionIdFourth;//专业4
//	private Professional professionIdFifth; //专业5
//	private Professional professionIdSix;  //专业5
	private Dict professionCode; 		   //专业科类码
//	private Faculty courtyard;       		//院系(生成学号用到)
	private Integer adjustProfessionalTime; //调专业次数
	
	//报到信息
	private Dict healthy;        			 //健康状况
	private String noteId;        			//通知书编号
	private String enterFormId;   			//报名表编号
	private String enterState;    			//报到状态
	
	//其他信息
	private Dict emigrant;         			//港澳台侨
	private Dict getEducationBgWay;			//获取学历方式
	private Dict researchDirection;			//研究方向
	private Dict planNature;    			//计划定向
	private String isOrder;        			//是否订单
	private Account createAccount;			//创建人
	private Date createDate;				//创建时间
	private Account operateAccount;			//操作人
	private Date operateDate;       		//操作时间
	private Date reportTime;					//报到时间
	
	//新加
//	private Area  region;       			 //地区
	private String examineeCharacter;		 //考生特征
	private Dict  graduationType; 			 //毕业类别
	private Dict foreignLanguage;			 //外语语种
	private Dict examType;       			 //考试类型
	private String oralExamination;			 //外语口试
	private String oralListener;   			 //外语听力
	private Dict testTaking;      			 //应试卷种
	private String totalScore;   			 //总分
	private String sendMark;     			 //投档成绩
	private Dict sendWish;     				 //投档志愿
	private Dict   ascertain;    			 //批次
	private String addressee;    			 //收件人
	private String middCode;    			 //中学代码
	private String middName;    			 //中学名称
	private String mailAddress; 			 //邮寄地址
	private String unionExamId;				 //会考考号
	private String rewardPunish; 			 //奖励处罚
	private String examineeEvaluate;		 //考生评价
	private String height;					 //身高
	private String weight;  				 //体重
//	private Area provinceCity;				 //省市
	private String deleteFlag;               //是否退学
	private String enterYear;				//年度
	private String isAbleAlterZY; 			//是否能调专业
	private String studentId;				//学号
	private String familyAddress;			//家庭地址
	private List<String> ids; 
	private String examinee;				//考生号
	private String highestScore;			//最高分
	private String remark;					//备注
	
	public StudentInfo() {
		deleteFlag = StringConstant.FALSE;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public Dict getSex() {
		return sex;
	}
	public void setSex(Dict sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Dict getNationality() {
		return nationality;
	}
	public void setNationality(Dict nationality) {
		this.nationality = nationality;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Dict getMarriageState() {
		return marriageState;
	}
	public void setMarriageState(Dict marriageState) {
		this.marriageState = marriageState;
	}
	public String getNowlive() {
		return nowlive;
	}
	public void setNowlive(String nowlive) {
		this.nowlive = nowlive;
	}
	public String getHkLocal() {
		return hkLocal;
	}
	public void setHkLocal(String hkLocal) {
		this.hkLocal = hkLocal;
	}
	public Dict getHknature() {
		return hknature;
	}
	public void setHknature(Dict hknature) {
		this.hknature = hknature;
	}
	public String getStrongSide() {
		return strongSide;
	}
	public void setStrongSide(String strongSide) {
		this.strongSide = strongSide;
	}
	public Dict getStudentSource() {
		return studentSource;
	}
	public void setStudentSource(Dict studentSource) {
		this.studentSource = studentSource;
	}
	public Dict getStudentType() {
		return studentType;
	}
	public void setStudentType(Dict studentType) {
		this.studentType = studentType;
	}
	public Dict getCultureStyle() {
		return cultureStyle;
	}
	public void setCultureStyle(Dict cultureStyle) {
		this.cultureStyle = cultureStyle;
	}

	

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public String getGraduationTime() {
		return graduationTime;
	}
	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getAdmissionCard() {
		return admissionCard;
	}
	public void setAdmissionCard(String admissionCard) {
		this.admissionCard = admissionCard;
	}
	public Dict getEducationSys() {
		return educationSys;
	}
	public void setEducationSys(Dict educationSys) {
		this.educationSys = educationSys;
	}
	public Dict getGrade() {
		return grade;
	}
	public void setGrade(Dict grade) {
		this.grade = grade;
	}
	public Dict getProfessionCode() {
		return professionCode;
	}
	public void setProfessionCode(Dict professionCode) {
		this.professionCode = professionCode;
	}
	public Dict getHealthy() {
		return healthy;
	}
	public void setHealthy(Dict healthy) {
		this.healthy = healthy;
	}

	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getEnterFormId() {
		return enterFormId;
	}
	public void setEnterFormId(String enterFormId) {
		this.enterFormId = enterFormId;
	}

	public String getEnterState() {
		return enterState;
	}
	public void setEnterState(String enterState) {
		this.enterState = enterState;
	}

	public Dict getGetEducationBgWay() {
		return getEducationBgWay;
	}
	public void setGetEducationBgWay(Dict getEducationBgWay) {
		this.getEducationBgWay = getEducationBgWay;
	}
	public Dict getResearchDirection() {
		return researchDirection;
	}
	public void setResearchDirection(Dict researchDirection) {
		this.researchDirection = researchDirection;
	}

	public Dict getPlanNature() {
		return planNature;
	}
	public void setPlanNature(Dict planNature) {
		this.planNature = planNature;
	}
	public String getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date perateDate) {
		this.operateDate = perateDate;
	}
	public Dict getPoliticsFace() {
		return politicsFace;
	}
	public void setPoliticsFace(Dict politicsFace) {
		this.politicsFace = politicsFace;
	}
	public String getExamineeCharacter() {
		return examineeCharacter;
	}
	public void setExamineeCharacter(String examineeCharacter) {
		this.examineeCharacter = examineeCharacter;
	}
	public Dict getGraduationType() {
		return graduationType;
	}
	public void setGraduationType(Dict graduationType) {
		this.graduationType = graduationType;
	}
	public Dict getForeignLanguage() {
		return foreignLanguage;
	}
	public void setForeignLanguage(Dict foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}
	public Dict getExamType() {
		return examType;
	}
	public void setExamType(Dict examType) {
		this.examType = examType;
	}
	public String getOralExamination() {
		return oralExamination;
	}
	public void setOralExamination(String oralExamination) {
		this.oralExamination = oralExamination;
	}
	public String getOralListener() {
		return oralListener;
	}
	public void setOralListener(String oralListener) {
		this.oralListener = oralListener;
	}
	public Dict getTestTaking() {
		return testTaking;
	}
	public void setTestTaking(Dict testTaking) {
		this.testTaking = testTaking;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public String getSendMark() {
		return sendMark;
	}
	public void setSendMark(String sendMark) {
		this.sendMark = sendMark;
	}
	public Dict getSendWish() {
		return sendWish;
	}
	public void setSendWish(Dict sendWish) {
		this.sendWish = sendWish;
	}
	public Dict getAscertain() {
		return ascertain;
	}
	public void setAscertain(Dict ascertain) {
		this.ascertain = ascertain;
	}
	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	public String getMiddCode() {
		return middCode;
	}
	public void setMiddCode(String middCode) {
		this.middCode = middCode;
	}
	public String getMiddName() {
		return middName;
	}
	public void setMiddName(String middName) {
		this.middName = middName;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getUnionExamId() {
		return unionExamId;
	}
	public void setUnionExamId(String unionExamId) {
		this.unionExamId = unionExamId;
	}
	public String getRewardPunish() {
		return rewardPunish;
	}
	public void setRewardPunish(String rewardPunish) {
		this.rewardPunish = rewardPunish;
	}
	public String getExamineeEvaluate() {
		return examineeEvaluate;
	}
	public void setExamineeEvaluate(String examineeEvaluate) {
		this.examineeEvaluate = examineeEvaluate;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getAdjustProfessionalTime() {
		return adjustProfessionalTime;
	}
	public void setAdjustProfessionalTime(Integer adjustProfessionalTime) {
		this.adjustProfessionalTime = adjustProfessionalTime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getYear() {
		return enterYear;
	}
	public void setYear(String year) {
		this.enterYear = year;
	}

	public String getEnterYear() {
		return enterYear;
	}

	public void setEnterYear(String enterYear) {
		this.enterYear = enterYear;
	}

	public String getIsAbleAlterZY() {
		return isAbleAlterZY;
	}

	public void setIsAbleAlterZY(String isAbleAlterZY) {
		this.isAbleAlterZY = isAbleAlterZY;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Account getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(Account createAccount) {
		this.createAccount = createAccount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Account getOperateAccount() {
		return operateAccount;
	}

	public void setOperateAccount(Account operateAccount) {
		this.operateAccount = operateAccount;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getExaminee() {
		return examinee;
	}

	public void setExaminee(String examinee) {
		this.examinee = examinee;
	}

	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
