package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CONTRACT_SETTING")
public class ContractSettingDomain extends GenericDomain{
	
	/**
	 * Nomenclature for data base
	 * TB -> TABLE
	 * NM -> NAME
	 * DE -> DESCRIPTION
	 * TP -> TYPE
	 * DT -> DATE
	 * FG -> FLAG 
	 */
	

	//----------------- EXTENSIVE --------------------------------------------------------------
	/*
	 *Class Price
	 * informs the Extensive Real Price per Hour
	 */
	@Column(name = "DE_EXTENSIVE_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double extensiveRealPriceDescription;
	
	
	/*Class Price
	 * informs the Extensive Professor Price Per Hour
	 */
	@Column(name = "DE_EXTENSIVE_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double extensiveProfessorPriceDescription;
	

	
	
	//----------------- INTENSIVE --------------------------------------------------------------	
	/*
	 *Class Price
	 * informs the Intensive Real Price per Hour
	 */
	@Column(name = "DE_INTENSIVE_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double intensiveRealPriceDescription;
	
	
	/*Class Price
	 * informs the Intensive Professor Price Per Hour
	 */
	@Column(name = "DE_INTENSIVE_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double intensiveProfessorPriceDescription;
	
	

	
	//----------------- PRIVATE --------------------------------------------------------------	
	/*
	 *Class Price
	 * informs the Private Real Price per Hour
	 */
	@Column(name = "DE_PRIVATE_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double privateRealPriceDescription;
	
	
	/*Class Price
	 * informs the Private Professor Price Per Hour
	 */
	@Column(name = "DE_PRIVATE_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double privateProfessorPriceDescription;

	
	
	
	//----------------- INCOMPANYE --------------------------------------------------------------	
	/*
	 *Class Price
	 * informs the InCompany Real Price per Hour
	 */
	@Column(name = "DE_INCOMPANY_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double inCompanyRealPriceDescription;
	
	
	/*Class Price
	 * informs the InCompany Professor Price Per Hour
	 */
	@Column(name = "DE_INCOMPANY_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double inCompanyProfessorPriceDescription;

	
	
	
	//----------------- ONLINE --------------------------------------------------------------	
	/*
	 *Class Price
	 * informs the Online Real Price per Hour
	 */
	@Column(name = "DE_ONLINE_REAL_PRICE", precision = 3, scale = 2, nullable = false)
	private double onlineRealPriceDescription;
	
	
	/*Class Price
	 * informs the Online Professor Price Per Hour
	 */
	@Column(name = "DE_ONLINE_PROFESSOR_PRICE", precision = 3, scale = 2, nullable = false)
	private double onlineProfessorPriceDescription;

	
	/*
	 * Save Contract setting Date
	 * Get Contract setting Saving Hour
	 * 
	 */
	@Column(name = "DT_SAVE_CONTRACT_SETTING")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SaveContractSettingDate;
	
	
	/*
	 * Contract Setting Save Login User
	 * Get the Last Login User Who has Saved the Contract Setting
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_CONTRACT_SETTING", length = 30, nullable = false)
	private String  contractSettingSaveLoginUser;
	
	
	
	public double getExtensiveRealPriceDescription() {
		return extensiveRealPriceDescription;
	}


	public void setExtensiveRealPriceDescription(double extensiveRealPriceDescription) {
		this.extensiveRealPriceDescription = extensiveRealPriceDescription;
	}


	public double getExtensiveProfessorPriceDescription() {
		return extensiveProfessorPriceDescription;
	}


	public void setExtensiveProfessorPriceDescription(double extensiveProfessorPriceDescription) {
		this.extensiveProfessorPriceDescription = extensiveProfessorPriceDescription;
	}


	public double getIntensiveRealPriceDescription() {
		return intensiveRealPriceDescription;
	}


	public void setIntensiveRealPriceDescription(double intensiveRealPriceDescription) {
		this.intensiveRealPriceDescription = intensiveRealPriceDescription;
	}


	public double getIntensiveProfessorPriceDescription() {
		return intensiveProfessorPriceDescription;
	}


	public void setIntensiveProfessorPriceDescription(double intensiveProfessorPriceDescription) {
		this.intensiveProfessorPriceDescription = intensiveProfessorPriceDescription;
	}


	public double getPrivateRealPriceDescription() {
		return privateRealPriceDescription;
	}


	public void setPrivateRealPriceDescription(double privateRealPriceDescription) {
		this.privateRealPriceDescription = privateRealPriceDescription;
	}


	public double getPrivateProfessorPriceDescription() {
		return privateProfessorPriceDescription;
	}


	public void setPrivateProfessorPriceDescription(double privateProfessorPriceDescription) {
		this.privateProfessorPriceDescription = privateProfessorPriceDescription;
	}


	public double getInCompanyRealPriceDescription() {
		return inCompanyRealPriceDescription;
	}


	public void setInCompanyRealPriceDescription(double inCompanyRealPriceDescription) {
		this.inCompanyRealPriceDescription = inCompanyRealPriceDescription;
	}


	public double getInCompanyProfessorPriceDescription() {
		return inCompanyProfessorPriceDescription;
	}


	public void setInCompanyProfessorPriceDescription(double inCompanyProfessorPriceDescription) {
		this.inCompanyProfessorPriceDescription = inCompanyProfessorPriceDescription;
	}


	public Date getSaveContractSettingDate() {
		return SaveContractSettingDate;
	}


	public void setSaveContractSettingDate(Date saveContractSettingDate) {
		SaveContractSettingDate = saveContractSettingDate;
	}


	public String getContractSettingSaveLoginUser() {
		return contractSettingSaveLoginUser;
	}


	public void setContractSettingSaveLoginUser(String contractSettingSaveLoginUser) {
		this.contractSettingSaveLoginUser = contractSettingSaveLoginUser;
	}


	public double getOnlineRealPriceDescription() {
		return onlineRealPriceDescription;
	}


	public void setOnlineRealPriceDescription(double onlineRealPriceDescription) {
		this.onlineRealPriceDescription = onlineRealPriceDescription;
	}


	public double getOnlineProfessorPriceDescription() {
		return onlineProfessorPriceDescription;
	}


	public void setOnlineProfessorPriceDescription(double onlineProfessorPriceDescription) {
		this.onlineProfessorPriceDescription = onlineProfessorPriceDescription;
	}

}
