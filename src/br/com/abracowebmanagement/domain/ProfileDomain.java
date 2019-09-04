package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	 * Profile
	 * Example: STUDENT, PROFESSOR, COORDINATOR, GESTOR, VISITOR
	 * OBS.: ADMINISTRATOR will be the profile that will allow setting configurations
	 */
	@Column(name = "DE_PROFILE", length = 20, nullable = false)
	private String profile;
	
	
	/*
	 * Status
	 * Example: ENABLED, DISABLED, STAND BY, BLOCKED
	 */
	@Column(name = "DE_STATUS", length = 20, nullable = false)
	private String status;
	

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
	@Column(name = "DE_LANGUAGE", length = 20, nullable = false)
	private String language;


	
	/*
	 * Getters and Setters
	 */
	
	public String getProfile() {
		return profile;
	}


	public void setProfile(String profile) {
		this.profile = profile;
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
	
}
