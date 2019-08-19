package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ABRACO")
public class AbracoDomain extends GenericDomain {

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
	 * Abraco name
	 * Ex: SP, RJ
	 */
	@Column(name = "NM_ABRACO", length = 2, nullable = false)
	private String abracoName;
	
	
	/*
	 * Login
	 * Associate a login to Abraco
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private LoginDomain login;

	
	/*
	 * Getters and Setters
	 */
	public String getDescription() {
		return abracoName;
	}

	public void setAbracoName(String abracoName) {
		this.abracoName = abracoName;
	}

	public LoginDomain getLogin() {
		return login;
	}

	public void setLogin(LoginDomain login) {
		this.login = login;
	}	
}
