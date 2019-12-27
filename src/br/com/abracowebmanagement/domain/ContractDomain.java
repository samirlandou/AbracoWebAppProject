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
@Table(name = "TB_CONTRACT")
public class ContractDomain extends GenericDomain{

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
	 * Class Code to Define Period Reference
	 * Composition: Number Semester + Semester + Type + Level + Place + Period (date + hour class)
	 * Example: 1Sem19_EN1_Ext_PINH_SEG-TER_19H_01h30
	 * Example: 2Sem19_INT_FRA1.1_Int_TATU_SAB_10H_03h00
	 * Example: 2Sem19_ES_PAR_EXT_QUA_19H30_01h30
	 * 
	 * */
	@Column(name = "DE_CODE", length = 50, nullable = false)
	private String codeDescription;


	/*
	 * Language Description
	 * Example:
	 * AR --> Arab
	 * FR --> French, FR1 --> French 1, ...
	 * EN --> English
	 * ES --> Spanish
	 * */
	@Column(name = "DE_LANGUAGE", length = 20, nullable = false)
	private String languageDescription;
	
	
	/*
	 * Class level
	 * Example:
	 * 
	 * Spanish:	Espanhol 1 (A1); Espanhol 1 (A2); Espanhol 3 (B1.1);
	 * 			Espanhol 4 (B1.2); Espanhol 5 (B1.3); Conversação (B1/B2);
	 * 
	 * French:	Francês 1 (A1.1); Francês 2 (A1.2); Francês 3 (A2.1);
	 *  		Francês 4 (A2.2); Francês 5 (B1.1); Francês 6 (B1.2);
	 *  		Francês 7 (B1.3); Conversação (B1/B2);
	 *  
	 * English:	Inglês 1 (A1.1); Inglês 2 (A1.2); Inglês 3 (A2.1);
	 * 			Inglês 4 (A2.2); Inglês 5 (B1.1); Inglês 6 (B1.2);
	 * 			Conversação (B1/B2);
	 * 
	 * Arab:	Arábe 1; Arábe 2; Arábe 3; Arábe 4;
	 */
	 
	@Column(name = "DE_LEVEL", length = 6, nullable = false) 
	private String levelDescription;
	
	/* 
	 * Class type
	 * Example:
	 * PAR --> Particular
	 * EXT --> Extensive
	 * INT --> Intensive
	 * INC --> InCompany
	 */
	@Column(name = "TP_CLASS", length = 4, nullable = false) 
	private String classType;
	
	
	/*
	 * Begin date
	 * The date when the class begins
	 */
	@Column(name = "DT_BEGIN", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	
	
	/*
	 * End date
	 * The date when the class will end
	 */
	@Column(name = "DT_END", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	/*
	 * Hour Begin Class
	 * Time - when the class begins
	 */
	@Column(name = "HR_BEGIN_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date hourBeginClass;
	
	
	/*
	 * Hour End Class
	 * Time - when the class will end
	 */
	@Column(name = "HR_END_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date hourEndClass;
	
	/*
	 * Hour Pause Class
	 * Pause Time
	 */
	@Column(name = "HR_PAUSE_CLASS", nullable = false)
	private int hourPauseClass;
		
	/*
	 * Total Hour of the Class
	 * Total Hour
	 */
	@Column(name = "HR_TOTAL_CLASS", nullable = false)
	private int hourTotalClass;
	
	/*
	 * Day 1 of the Class
	 * day number 1
	 */
	@Column(name = "DE_FIRST_DAY_CLASS", length = 3, nullable = false)
	private String dayClass1;
	
	/*
	 * Day 2 of the Class
	 * day number 2
	 */
	@Column(name = "DE_SECOND_DAY_CLASS", length = 3)
	private String dayClass2;
	
	
	/*
	 * Class level
	 * Choose Where will be the class
	 * Example: Class 1, Class 2, Class 3 , ...
	 */
	@Column(name = "DE_PLACE", length = 4) 
	private String placeDescription;
	
	
	/*
	 *Class Time Package
	 * informs the total time package of the class
	 */
	@Column(name = "DE_PACKAGE", length = 6, nullable = false)
	private int packageDescription;
	
	
	/*
	 *Class time
	 * informs the total time package of the class
	 */
	@Column(name = "DE_PRICE", precision = 7, scale = 2, nullable = false)
	private double priceDescription;
	
	
	/*
	 * Comment Class
	 * Comment if there is a specific notification to inform
	 * about the opening class
	 * */
	@Column(name = "DE_COMMENT", length = 100)
	private String commentDescription;
	
	
	/*
	 * Close Class Flag
	 * Close the class when it ends.
	 * Example:
	 * True ---> closed
	 * False --> opened
	 */
	@Column(name = "FG_CLOSED", nullable = false)
	private Boolean closedFlag;

	
	/*
	 * User
	 * Insert User Data Table into ClassDomain
	 * Inform here the name of the teacher.
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private UserDomain userDomain;

	
	/*
	 * User
	 * Insert User Data Table into ClassDomain
	 * Inform here the name of the substitute teacher.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private UserDomain substituteUserDomain;
	
	/*
	 * Person
	 * Insert Person Data Table into ClassDomain
	 * Inform here the name of the student.
	 */	
	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;
	
	
	/*
	 * Getters and Setters
	 */
	
	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String myClassCode) {
		this.codeDescription = myClassCode;
	}

	public String getLanguageDescription() {
		return languageDescription;
	}

	public void setLanguageDescription(String myClassLanguage) {
		this.languageDescription = myClassLanguage;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date myclassBeginDate) {
		this.beginDate = myclassBeginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date myClassEndDate) {
		this.endDate = myClassEndDate;
	}

	public Boolean getClosedFlag() {
		return closedFlag;
	}

	public void setClosedFlag(Boolean closedFlag) {
		this.closedFlag = closedFlag;
	}

	public UserDomain getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}

	public String getLevelDescription() {
		return levelDescription;
	}

	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}

	public double getPriceDescription() {
		return priceDescription;
	}

	public void setPriceDescription(double priceDescription) {
		this.priceDescription = priceDescription;
	}

	public PersonDomain getPersonDomain() {
		return personDomain;
	}

	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}

	public Date getHourBeginClass() {
		return hourBeginClass;
	}

	public void setHourBeginClass(Date hourBeginClass) {
		this.hourBeginClass = hourBeginClass;
	}

	public Date getHourEndClass() {
		return hourEndClass;
	}

	public void setHourEndClass(Date hourEndClass) {
		this.hourEndClass = hourEndClass;
	}

	public String getCommentDescription() {
		return commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

	public UserDomain getSubstituteUserDomain() {
		return substituteUserDomain;
	}

	public void setSubstituteUserDomain(UserDomain substituteUserDomain) {
		this.substituteUserDomain = substituteUserDomain;
	}

	public String getDayClass1() {
		return dayClass1;
	}

	public void setDayClass1(String dayClass1) {
		this.dayClass1 = dayClass1;
	}

	public String getDayClass2() {
		return dayClass2;
	}

	public void setDayClass2(String dayClass2) {
		this.dayClass2 = dayClass2;
	}

	public String getPlaceDescription() {
		return placeDescription;
	}

	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	public int getPackageDescription() {
		return packageDescription;
	}

	public void setPackageDescription(int packageDescription) {
		this.packageDescription = packageDescription;
	}

	public int getHourPauseClass() {
		return hourPauseClass;
	}

	public void setHourPauseClass(int hourPauseClass) {
		this.hourPauseClass = hourPauseClass;
	}

	public int getHourTotalClass() {
		return hourTotalClass;
	}

	public void setHourTotalClass(int hourTotalClass) {
		this.hourTotalClass = hourTotalClass;
	}

}
