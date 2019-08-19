package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_LOGIN")	
public class LoginDomain extends GenericDomain{
	
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
	@Column(name = "NM_USERNAME", length = 20, nullable = false)
	private String userName;

	/*
	 * Password
	 */
	
	@Column(name = "DE_PASSWORD", length = 20, nullable = false)
	private String password;
	
	
	/*
	 * Remember password
	 * Sentence that will help to remember the password.
	 */
	@Column(name = "DE_REMEMBER_PASSWORD", length = 100, nullable = false)
	private String rememberPassword;

	
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
}
