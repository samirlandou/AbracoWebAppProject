package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_PERSON")
public class PersonDomain extends GenericDomain{
	
	
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
	 * Complete name
	 * (Name + Surname)
	 */
	@Column(name = "NM_COMPLETE_NAME", length = 40, nullable = false)
	private String completeName;	
	
	
	/*
	 * Nationality
	 * Brazil as Default
	 */
	@Column(name = "DE_NATIONALITY", length = 20, nullable = false)
	private String nationality;

	
	/*
	 * SEX Identity
	 * M --> Male
	 * F -->Female
	 */
	@Column(name = "DE_SEX", length = 1,  nullable = false)
	private Character sex;

	
	/*
	 * E-mail
	 */
	@Column(name = "DE_EMAIL", length = 60, nullable = false)
	private String email;
	
	
	/*
	 * Phone number
	 */
	@Column(name = "DE_TELEPHONE", length = 20, nullable = false)
	private String telephone;
	
	
	/*
	 * Address
	 */
	@Column(name = "DE_ADDRESS", length = 100)
	private String address;

	/*
	 * Profile
	 * Example: STUDENT, PROFESSOR, COORDINATOR, GESTOR, VISITOR
	 * OBS.: ADMINISTRATOR will be the profile that will allow setting configurations
	 */
	@Column(name = "DE_PROFILE", length = 20, nullable = false)
	private String profile;
	
	
	/*
	 * Status
	 * Example: ENABLED or DISABLED
	 */
	@Column(name = "FG_STATUS", nullable = false)
	private Boolean status;
	

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
	 * Getters and Setters
	 */	

	public String getCompleteName() {
		return completeName;
	}


	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public Character getSex() {
		return sex;
	}


	public void setSex(Character sex) {
		this.sex = sex;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getProfile() {
		return profile;
	}


	public void setProfile(String profile) {
		this.profile = profile;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}
	
}
