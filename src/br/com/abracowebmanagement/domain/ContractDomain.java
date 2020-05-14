package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	 * HR -> HOUR
	 */

	
	//-------------  Class ------------------------------
	
	/*
	 * Class Code to Define Period Reference
	 * Composition: Number Semester + Semester + Type + Level + Place + Period (date + hour class)
	 * Example: 1Sem19_EN1_Ext_PINH_SEG-TER_19H_01h30
	 * Example: 2Sem19_INT_FRA1.1_Int_TATU_SAB_10H_03h00
	 * Example: 2Sem19_ES_PAR_EXT_QUA_19H30_01h30
	 * 
	 * */
	@Column(name = "DE_CONTRACT_CODE", length = 50, nullable = false)
	private String contractCodeDescription;
	
	
	/*
	 * Class level
	 * Choose the place of the class
	 * Example:  
	 * PINH -->PINHEIROS,
	 * TATU -->TATUAPE,
	 * COMP -->INCOMPANY
	 * Tentar criar aqui uma Tela para registraro o lugar da aula
	 * (TABLE TB_CLASS_PLACE (ID, DE_PLACE_CODE(15), DE_PLACE DESCRIPTION(5))
	 */
	@Column(name = "DE_CLASS_PLACE", length = 15) 
	private String classPlaceDescription;

	
	/* 
	 * Class type
	 * Example:
	 * PAR --> Particular
	 * EXT --> Extensive
	 * INT --> Intensive
	 * INC --> InCompany
	 */
	@Column(name = "DE_CLASS_MODULE", length = 25, nullable = false) 
	private String classModuleDescription;
	
	/*
	 * Language Description
	 * Example:
	 * AR --> Arab
	 * FR --> French, FR1 --> French 1, ...
	 * EN --> English
	 * ES --> Spanish
	 * */
	@Column(name = "DE_CLASS_LANGUAGE", length = 25, nullable = false)
	private String classLanguageDescription;
	
	
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
	 
	@Column(name = "DE_CLASS_LEVEL", length = 25, nullable = false) 
	private String classLevelDescription;

	
	/*
	 * Hour Begin Class
	 * Time - when the class begins
	 */
	@Column(name = "HR_BEGIN_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date beginClassHour;
	
	
	/*
	 * Hour End Class
	 * Time - when the class will end
	 */
	@Column(name = "HR_END_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date endClassHour;	

	
	/*
	 * Hour Break Class
	 * Break Time
	 */
	@Column(name = "HR_BREAK_CLASS", nullable = false)
	private int breakClassHour;

		
	/*
	 * Class Time
	 * Number of hour for each class
	 */
	@Column(name = "HR_CLASS_TIME", nullable = false)
	private int classTimeHour;	
	
	/*
	 * Class Time
	 * Number of hour for each class
	 */
	@Column(name = "HR_WEEKLY_CLASS_TIME", nullable = false)
	private int classWeeklyTimeHour;		
	
	/*
	 * First Class Day
	 * day number 1
	 */
	@Column(name = "DE_FIRST_CLASS_DAY", length = 25, nullable = false)
	private String firstClassDayDescription;
	
	/*
	 * Second Class Day
	 * day number 2 (If exist)
	 */
	@Column(name = "DE_SECOND_CLASS_DAY", length = 25)
	private String secondClassDayDescription;

	
	
	/*
	 *Class price
	 * informs the real price of the class
	 */
	@Column(name = "DE_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double realPriceDescription;
	
	
	/*
	 *Class price
	 * Informs the Professor Price of the class
	 */
	@Column(name = "DE_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double professorPriceDescription;
	
		
	/*
	 *Class Time Package
	 * Informs the total time package of the class
	 */
	@Column(name = "HR_CLASS_PACKAGE", length = 3, nullable = false)
	private int classPackageHour;
	
	
	/*
	 *Class time
	 * Informs the Total package Price.
	 */
	@Column(name = "DE_TOTAL_PACKAGE_REAL_PRICE", precision = 6, scale = 2, nullable = false)
	private double totalPackageRealPriceDescription;
	
	
	
	/*
	 *Class time
	 * Informs the Total package Price.
	 */
	@Column(name = "DE_TOTAL_PACKAGE_PROFESSOR_PRICE", precision = 6, scale = 2, nullable = false)
	private double totalPackageProfessorPriceDescription;

	

	//-------------  Deadline ------------------------------
	
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
	


	
	//-------------- Others -----------------------------
	
	
	
	/*
	 * Comment Contract
	 * Comment if there is a specific notification to inform about the class.
	 * */
	@Column(name = "DE_COMMENT", length = 100)
	private String commentDescription;
	
	
	/*
	 * Close ContractFlag
	 * Close the class when it ends.
	 * Example:
	 * True ---> closed
	 * False --> opened
	 */
	@Column(name = "FG_CLOSED_CONTRACT", nullable = false)
	private Boolean closedContractFlag;
	
	
	
	//----------- Date when the Contract has been saved ------------------
	
	/*
	 * Save Contract Date
	 * Get Contract Saving Date
	 * 
	 */
	@Column(name = "DT_SAVE_CONTRACT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date saveContractDate;
	
		
	/*
	 * Contract Save Login User
	 * Get the Last Login User Who Has Saved the Contract
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_CONTRACT", nullable = false)
	private String  contractSaveLoginUser;

	
	
	//-------------- Foreign Keys -----------------------------
		
	
	/*
	 * Person
	 * Insert Person Data Table into ContractDomain
	 * Inform here the name of the student.
	 */	
	/*@ManyToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;*/
	
	
	/*
	 * Person
	 * Insert Contract Model Data Table into ContractDomain
	 * Inform here the name of the student.
	 */	
	@ManyToOne
	@JoinColumn(nullable = false)
	private ContractModelDomain contractModelDomain;

	
	
	
	/*
	 * Getters and Setters
	 */
	


	public String getContractCodeDescription() {
		return contractCodeDescription;
	}


	public void setContractCodeDescription(String contractCodeDescription) {
		this.contractCodeDescription = contractCodeDescription;
	}


	public String getClassPlaceDescription() {
		return classPlaceDescription;
	}


	public void setClassPlaceDescription(String classPlaceDescription) {
		this.classPlaceDescription = classPlaceDescription;
	}


	public String getClassModuleDescription() {
		return classModuleDescription;
	}


	public void setClassModuleDescription(String classModuleDescription) {
		this.classModuleDescription = classModuleDescription;
	}


	public String getClassLanguageDescription() {
		return classLanguageDescription;
	}


	public void setClassLanguageDescription(String classLanguageDescription) {
		this.classLanguageDescription = classLanguageDescription;
	}


	public String getClassLevelDescription() {
		return classLevelDescription;
	}


	public void setClassLevelDescription(String classLevelDescription) {
		this.classLevelDescription = classLevelDescription;
	}


	public Date getBeginClassHour() {
		return beginClassHour;
	}


	public void setBeginClassHour(Date beginClassHour) {
		this.beginClassHour = beginClassHour;
	}


	public Date getEndClassHour() {
		return endClassHour;
	}


	public void setEndClassHour(Date endClassHour) {
		this.endClassHour = endClassHour;
	}


	public int getBreakClassHour() {
		return breakClassHour;
	}


	public void setBreakClassHour(int breakClassHour) {
		this.breakClassHour = breakClassHour;
	}


	public int getClassTimeHour() {
		return classTimeHour;
	}


	public void setClassTimeHour(int classTimeHour) {
		this.classTimeHour = classTimeHour;
	}


	public String getFirstClassDayDescription() {
		return firstClassDayDescription;
	}


	public void setFirstClassDayDescription(String firstClassDayDescription) {
		this.firstClassDayDescription = firstClassDayDescription;
	}


	public String getSecondClassDayDescription() {
		return secondClassDayDescription;
	}


	public void setSecondClassDayDescription(String secondClassDayDescription) {
		this.secondClassDayDescription = secondClassDayDescription;
	}


	public double getRealPriceDescription() {
		return realPriceDescription;
	}


	public void setRealPriceDescription(double patternPriceDescription) {
		this.realPriceDescription = patternPriceDescription;
	}


	public double getProfessorPriceDescription() {
		return professorPriceDescription;
	}


	public void setProfessorPriceDescription(double professorPriceDescription) {
		this.professorPriceDescription = professorPriceDescription;
	}


	public int getClassPackageHour() {
		return classPackageHour;
	}


	public void setClassPackageHour(int classPackageHour) {
		this.classPackageHour = classPackageHour;
	}


	public double getTotalPackageRealPriceDescription() {
		return totalPackageRealPriceDescription;
	}


	public void setTotalPackageRealPriceDescription(double totalPackageRealPriceDescription) {
		this.totalPackageRealPriceDescription = totalPackageRealPriceDescription;
	}


	public String getCommentDescription() {
		return commentDescription;
	}


	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}


	public Boolean getClosedContractFlag() {
		return closedContractFlag;
	}


	public void setClosedContractFlag(Boolean closedFlag) {
		this.closedContractFlag = closedFlag;
	}


	public Date getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Date getSaveContractDate() {
		return saveContractDate;
	}


	public void setSaveContractDate(Date saveContractDate) {
		this.saveContractDate = saveContractDate;
	}


	public String getContractSaveLoginUser() {
		return contractSaveLoginUser;
	}


	public void setContractSaveLoginUser(String contractSaveLoginUser) {
		this.contractSaveLoginUser = contractSaveLoginUser;
	}


	/*public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}*/


	public ContractModelDomain getContractModelDomain() {
		return contractModelDomain;
	}


	public void setContractModelDomain(ContractModelDomain contractModelDomain) {
		this.contractModelDomain = contractModelDomain;
	}


	public double getTotalPackageProfessorPriceDescription() {
		return totalPackageProfessorPriceDescription;
	}


	public void setTotalPackageProfessorPriceDescription(double totalPackageProfessorPriceDescription) {
		this.totalPackageProfessorPriceDescription = totalPackageProfessorPriceDescription;
	}


	public int getClassWeeklyTimeHour() {
		return classWeeklyTimeHour;
	}


	public void setClassWeeklyTimeHour(int classWeeklyTimeHour) {
		this.classWeeklyTimeHour = classWeeklyTimeHour;
	}

}
