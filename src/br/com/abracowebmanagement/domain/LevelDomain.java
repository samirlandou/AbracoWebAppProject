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
@Table(name = "TB_LEVEL")
public class LevelDomain extends GenericDomain{
	
	
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
	 * Level Name
	 */
	@Column(name = "NM_LEVEL", length = 20, nullable = false)
	private String levelName;	
	
	
	/*
	 * Level Description
	 */	
	@Column(name = "DE_LEVEL", length = 10, nullable = false)
	private String levelDescription;

	
	/*
	 * Language <br/>
	 * Insert LanguageDomain as Foreign Key
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private LanguageDomain languageDomain;
	
	/*
	 * Save Level Date
	 * Get Level Saving Date and Hour
	 * 
	 */
	@Column(name = "DT_SAVE_LEVEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SaveLevelDate;
	
	
	/*
	 * Level Save Login User
	 * Get the Last Login User Who has Saved the Level
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_LEVEL", length = 30, nullable = false)
	private String  levelSaveLoginUser;


	
	/*
	 * Getters and Setters
	 */
	
	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	public String getLevelDescription() {
		return levelDescription;
	}


	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}


	public LanguageDomain getLanguageDomain() {
		return languageDomain;
	}


	public void setLanguageDomain(LanguageDomain languageDomain) {
		this.languageDomain = languageDomain;
	}


	public Date getSaveLevelDate() {
		return SaveLevelDate;
	}


	public void setSaveLevelDate(Date saveLevelDate) {
		SaveLevelDate = saveLevelDate;
	}


	public String getLevelSaveLoginUser() {
		return levelSaveLoginUser;
	}


	public void setLevelSaveLoginUser(String levelSaveLoginUser) {
		this.levelSaveLoginUser = levelSaveLoginUser;
	}
	
}
