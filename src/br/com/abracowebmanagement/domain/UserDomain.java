package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_USER")	
public class UserDomain extends GenericDomain{
	
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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID_LOGIN")
	private Long id;*/
	
	/*
	 * User Name
	 */
	@Column(name = "NM_USER", length = 30, nullable = false)
	private String userName;

	
	/*
	 * Password
	 * Use MD5 cryptography for encode the password
	 */
	
	@Column(name = "DE_PASSWORD", length = 32, nullable = false)
	private String password;
	
	
	/*
	 * Remember password
	 * Sentence that will help to remember the password.
	 */
	@Column(name = "DE_REMEMBER_PASSWORD", length = 100, nullable = false)
	private String rememberPassword;

	
	/*
	 * Profile
	 * Insert Profile Data Table into UserDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ProfileDomain ProfileDomain;

	
	/*
	 * Personal Info
	 * Insert Personal Info Data Table into UserDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonalInfoDomain personalInfoDomain;

	
	/*
	 * Getters and Setters
	 */
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRememberPassword() {
		return rememberPassword;
	}


	public void setRememberPassword(String rememberPassword) {
		this.rememberPassword = rememberPassword;
	}


	public ProfileDomain getProfileDomain() {
		return ProfileDomain;
	}


	public void setProfileDomain(ProfileDomain profileDomain) {
		ProfileDomain = profileDomain;
	}


	public PersonalInfoDomain getPersonalInfoDomain() {
		return personalInfoDomain;
	}


	public void setPersonalInfoDomain(PersonalInfoDomain personalInfoDomain) {
		this.personalInfoDomain = personalInfoDomain;
	}	
	
}
