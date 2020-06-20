package br.com.abracowebmanagement.domain.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.abracowebmanagement.domain.generic.GenericDomain;

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
	 * Phone number
	 */
	@Column(name = "DE_CPF", length = 14, nullable = false)
	private String cpf;		
	
	
	/*
	 * Complete name
	 * (Name + Surname)
	 */
	@Column(name = "NM_COMPLETE_NAME", length = 60, nullable = false)
	private String completeName;	
	
	
	/*
	 * Nationality
	 * Brazil as Default
	 */
	@Column(name = "DE_COUNTRY", length = 100, nullable = false)
	private String country;

	
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
	@Column(name = "DE_TELEPHONE", length = 20, nullable = true)
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
	 * Description of teaching language3
	 * 
	 * AR --> Arab
	 * FR --> French
	 * EN --> English
	 * ES --> Spanish
	 * PT --> Portuguese
	 */
	@Column(name = "DE_LANGUAGE1", length = 2, nullable = false)
	private String language1;
	

	/*
	 * Language
	 * Description of teaching language3
	 * 
	 * AR --> Arab
	 * FR --> French
	 * EN --> English
	 * ES --> Spanish
	 * PT --> Portuguese
	 */
	@Column(name = "DE_LANGUAGE2", length = 2, nullable = true)
	private String language2;	
	

	/*
	 * Language
	 * Description of teaching language3
	 * 
	 * AR --> Arab
	 * FR --> French
	 * EN --> English
	 * ES --> Spanish
	 * PT --> Portuguese
	 */
	@Column(name = "DE_LANGUAGE3", length = 2, nullable = true)
	private String language3;
	
	
	/*
	 * Getters and Setters
	 */	

	public String getCompleteName() {
		return completeName;
	}


	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
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


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getLanguage1() {
		return language1;
	}


	public void setLanguage1(String language1) {
		this.language1 = language1;
	}


	public String getLanguage2() {
		return language2;
	}


	public void setLanguage2(String language2) {
		this.language2 = language2;
	}


	public String getLanguage3() {
		return language3;
	}


	public void setLanguage3(String language3) {
		this.language3 = language3;
	}

	
}
