package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_MYCLASS")
public class MyClassDomain extends GenericDomain{

	/**
	 * Nomenclature for data base
	 * TB -> TABLE
	 * NM -> NAME
	 * DE -> DESCRIPTION
	 * TP -> TYPE
	 * DT -> DATE
	 * FG -> FLAG 
	 */
	
	
	/*
	 * Class Period Reference
	 * Example: 1st-SEM-2019 --> First Semester 2019
	 * Example: 2nd-SEM-2019 --> Second Semester 2019
	 * */
	@Column(name = "DE_MYCLASS_REFENCE", length = 12, nullable = false)
	private String myClassReference;
	
	
	/*
	 * Class Name 
	 * Example: Frances1WAKANDA
	 * Example: 2nd-SEM-2019 --> Second Semester 2019
	 * */
	@Column(name = "NM_MYCLASS", length = 30, nullable = false)
	private String myClassName;	

	/*
	 * Languages
	 * Example:
	 * AR --> Arab
	 * FR --> French, FR1 --> French 1, ...
	 * EN --> English
	 * ES --> Spanish
	 * */
	@Column(name = "DE_MYCLASS_LANGUAGE", length = 20, nullable = false)
	private String myClassLanguage;
	
	
	/*
	 * Class level
	 * Example:
	 * BAS --> BASIC,
	 * INT --> INTERMEDIATE,
	 * ADV --> ADVANCE,
	 * CON --> CONVERSATION
	 
	@Column(name = "DE_CLASS_LEVEL", length = 3, nullable = false) 
	private String classLevel;*/
	
	/* 
	 * Class type
	 * Example:
	 * PRV --> Private
	 * EXT --> Extensive
	 * INT --> Intensive
	 */
	@Column(name = "TP_MYCLASS", length = 3, nullable = false) 
	private String myClassType;
	
	
	/*
	 * Begin date
	 * The date when the class begins
	 */
	@Column(name = "DT_BEGIN_MYCLASS", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date myclassBeginDate;
	
	
	/*
	 * End date
	 * The date when the class will end
	 */
	@Column(name = "DT_END_MYCLASS", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date MyClassEndDate;
	
	
	/*
	 *Class time
	 * informs the total time package of the class
	 */
	@Column(name = "DT_TIME_PACKAGE_MYCLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date myClassTimePackage;

	
	/*
	 * Class level
	 * Choose Where will be the class
	 * Example: Class 1, Class 2, Class 3 , ...
	 */
	@Column(name = "DE_MYCLASSROOM", length = 16) 
	private String myClassRoom;	
	
	
	/*
	 * Comment MyClass
	 * Comment if there is a specific notification to inform
	 * about the opening class
	 * */
	@Column(name = "DE_COMMENT_MyCLASS", length = 100)
	private String commentMyClass;
	
	
	/*
	 * Close class
	 * Close the class when it ends.
	 * Example:
	 * True ---> closed
	 * False --> opened
	 */
	@Column(name = "FG_CLOSED_MYCLASS", nullable = false)
	private Boolean myClosedClass;
	
	
	/*
	 * Presence
	 * Insert presence Data Table into MyClassDomain
	 */
	@ManyToOne
	@JoinColumn
	private PresenceDomain presenceDomain;


	/*
	 * Student In My Class
	 * Insert StudentInMyClass Data Table into MyClassDomain
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private StudentInMyClassDomain studentInMyClassDomain;

	
	/*
	 * Login
	 * Insert Login Data Table into MyClassDomain
	 * Inform here the name of class teacher.
	 */
	@OneToOne
	@JoinColumn
	private UserDomain userDomain;
	
	
	/*
	 * Getters and Setters
	 */
	
	public String getMyClassReference() {
		return myClassReference;
	}

	public void setMyClassReference(String myClassReference) {
		this.myClassReference = myClassReference;
	}

	public String getMyClassName() {
		return myClassName;
	}

	public void setMyClassName(String myClassName) {
		this.myClassName = myClassName;
	}

	public String getMyClassLanguage() {
		return myClassLanguage;
	}

	public void setMyClassLanguage(String myClassLanguage) {
		this.myClassLanguage = myClassLanguage;
	}

	public String getMyClassType() {
		return myClassType;
	}

	public void setMyClassType(String myClassType) {
		this.myClassType = myClassType;
	}

	public Date getMyclassBeginDate() {
		return myclassBeginDate;
	}

	public void setMyclassBeginDate(Date myclassBeginDate) {
		this.myclassBeginDate = myclassBeginDate;
	}

	public Date getMyClassEndDate() {
		return MyClassEndDate;
	}

	public void setMyClassEndDate(Date myClassEndDate) {
		MyClassEndDate = myClassEndDate;
	}

	public Date getMyClassTimePackage() {
		return myClassTimePackage;
	}

	public void setMyClassTimePackage(Date myClassTimePackage) {
		this.myClassTimePackage = myClassTimePackage;
	}

	public String getMyClassRoom() {
		return myClassRoom;
	}

	public void setMyClassRoom(String myClassRoom) {
		this.myClassRoom = myClassRoom;
	}

	public String getCommentMyClass() {
		return commentMyClass;
	}

	public void setCommentMyClass(String commentMyClass) {
		this.commentMyClass = commentMyClass;
	}

	public Boolean getMyClosedClass() {
		return myClosedClass;
	}

	public void setMyClosedClass(Boolean myClosedClass) {
		this.myClosedClass = myClosedClass;
	}

	public PresenceDomain getPresenceDomain() {
		return presenceDomain;
	}

	public void setPresenceDomain(PresenceDomain presenceDomain) {
		this.presenceDomain = presenceDomain;
	}

	public StudentInMyClassDomain getStudentInMyClassDomain() {
		return studentInMyClassDomain;
	}

	public void setStudentInMyClassDomain(StudentInMyClassDomain studentInMyClassDomain) {
		this.studentInMyClassDomain = studentInMyClassDomain;
	}

	public UserDomain getLoginDomain() {
		return userDomain;
	}

	public void setLoginDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}

}
