package br.com.abracowebmanagement.domain.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.abracowebmanagement.domain.generic.GenericDomain;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CONTRACT_MODEL")
public class ContractModelDomain extends GenericDomain{
	
	
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
	 * Contract Model Name
	 */
	@Column(name = "NM_CONTRACT_MODEL", length = 30, nullable = false)
	private String contractModelName;	
	
	
	/*
	 * Contract Model Description
	 
	@Column(name = "DE_CONTRACT_MODEL", nullable = false)
	private String contractModelDescription;*/

	/*
	 * Contract Model Description
	 */
	@Lob
	@Column(name = "DE_CONTRACT_MODEL", nullable = false)
	private byte[] contractModelDescription;
	
	/*
	 * Contract Model Comments
	 */
	@Column(name = "DE_COMMENT", length = 100)
	private String commentDescription;

	
	/*
	 * Save Contract Model Date
	 * Get Contract Model Saving Hour
	 * 
	 */
	@Column(name = "DT_SAVE_CONTRACT_MODEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SaveContractModelDate;
	
	
	/*
	 * Contract Model Save Login User
	 * Get the Last Login User Who has Saved the Contract Model
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_CONTRACT_MODEL", length = 30, nullable = false)
	private String  contractModelSaveLoginUser;



	
	/*
	 * Getters and Setters
	 */	
	

	public String getContractModelName() {
		return contractModelName;
	}


	public void setContractModelName(String contractModelName) {
		this.contractModelName = contractModelName;
	}

/*
	public String getContractModelDescription() {
		return contractModelDescription;
	}


	public void setContractModelDescription(String contractModelDescription) {
		this.contractModelDescription = contractModelDescription;
	}*/


	public String getCommentDescription() {
		return commentDescription;
	}


	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}


	public Date getSaveContractModelDate() {
		return SaveContractModelDate;
	}


	public void setSaveContractModelDate(Date saveContractModelDate) {
		SaveContractModelDate = saveContractModelDate;
	}


	public String getContractModelSaveLoginUser() {
		return contractModelSaveLoginUser;
	}


	public void setContractModelSaveLoginUser(String contractModelSaveLoginUser) {
		this.contractModelSaveLoginUser = contractModelSaveLoginUser;
	}


	public byte[] getContractModelDescription() {
		return contractModelDescription;
	}


	public void setContractModelDescription(byte[] contractModelDescription) {
		this.contractModelDescription = contractModelDescription;
	}
	
}
