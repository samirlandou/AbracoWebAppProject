package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_LANGUAGE")
public class LanguageDomain extends GenericDomain{
	
	
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
	 * Language Name
	 */
	@Column(name = "NM_LANGUAGE", length = 20, nullable = false)
	private String languageName;	
	
	
	/*
	 * Language Description
	 */	
	@Column(name = "DE_LANGUAGE", length = 10, nullable = false)
	private String languageDescription;

	
	/*
	 * Save Language Date
	 * Get Language Saving Date and Hour
	 * 
	 */
	@Column(name = "DT_SAVE_LANGUAGE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SaveLanguageDate;
	
	
	/*
	 * Language Save Login User
	 * Get the Last Login User Who has Saved the Language
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_LANGUAGE", length = 30, nullable = false)
	private String  languageSaveLoginUser;


	
	/*
	 * Getters and Setters
	 */
	
	public String getLanguageName() {
		return languageName;
	}


	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}


	public String getLanguageDescription() {
		return languageDescription;
	}


	public void setLanguageDescription(String languageDescription) {
		this.languageDescription = languageDescription;
	}


	public Date getSaveLanguageDate() {
		return SaveLanguageDate;
	}


	public void setSaveLanguageDate(Date saveLanguageDate) {
		SaveLanguageDate = saveLanguageDate;
	}


	public String getLanguageSaveLoginUser() {
		return languageSaveLoginUser;
	}


	public void setLanguageSaveLoginUser(String languageSaveLoginUser) {
		this.languageSaveLoginUser = languageSaveLoginUser;
	}	
}
