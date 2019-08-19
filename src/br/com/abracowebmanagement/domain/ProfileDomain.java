package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_PROFILE")
public class ProfileDomain extends GenericDomain{
	
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
	 * AbracoDomain
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private AbracoDomain abraco;
	
	
	/*
	 * Status
	 * Ex: ENABLED, DISABLED, STAND BY, BLOCKED
	 */
	@Column(name = "DE_STATUS", length = 10, nullable = false)
	private String status;
	
	
	/*
	 * Function
	 * The function of the profile.
	 * Example: Academics coordinator, professor
	 */
	@Column(name = "DE_FUNCTION", length = 30, nullable = false)
	private String function;	
	

	/*
	 * Language
	 * Description of teaching language
	 * 
	 * AR --> Arab
	 * FR --> French
	 * EN --> English
	 * ES --> Spanish
	 * PT --> Portuguese
	 */
	@Column(name = "DE_LANGUAGE", length = 2, nullable = false)
	private String language;		
	
	/*
	 * Data register
	 * The date when the user have been registered
	 */
	@Column(name = "DT_REGISTER", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date registerDate;


	
	/*
	 * Getters and Setters
	 */
	public AbracoDomain getAbraco() {
		return abraco;
	}

	public void setAbraco(AbracoDomain abraco) {
		this.abraco = abraco;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
}
