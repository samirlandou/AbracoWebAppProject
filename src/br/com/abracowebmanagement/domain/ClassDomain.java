package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CLASS")
public class ClassDomain extends GenericDomain{

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
	 * Class Period Reference
	 * Example: 1st-SEM-2019 --> First Semester 2019
	 * Example: 2nd-SEM-2019 --> Second Semester 2019
	 * */
	@Column(name = "DE_CLASS_REFENCE", length = 12)
	private String classReference;

	/*
	 * Languages
	 * Example:
	 * AR --> Arab
	 * FR --> French
	 * EN --> English
	 * ES --> Spanish
	 * */
	@Column(name = "DE_LANGUAGE", length = 2, nullable = false)
	private String language;
	
	
	/*
	 * Class level
	 * Example:
	 * BAS --> BASIC,
	 * INT --> INTERMEDIATE,
	 * ADV --> ADVANCE,
	 * CON --> CONVERSATION
	 */
	@Column(name = "DE_CLASS_LEVEL", length = 3, nullable = false) 
	private String classLevel;
	
	/* 
	 * Class type
	 * Example:
	 * PRV --> Private
	 * EXT --> Extensive
	 * INT --> Intensive
	 */
	@Column(name = "DE_CLASS_TYPE", length = 3, nullable = false) 
	private String classType;
	
	
	/*
	 * Begin date
	 * The date when the class begins
	 */
	@Column(name = "DT_BEGIN_CLASS", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date classBeginDate;
	
	
	/*
	 * End date
	 * The date when the class will end
	 */
	@Column(name = "DT_END_CLASS", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date classEndDate;
	
	
	/*
	 *Class time
	 * informs the time of the class
	 */
	@Column(name = "DT_TIME_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date classTime;
	

	/*
	 * Close class
	 * Close the class when it ends.
	 * Example:
	 * True ---> closed
	 * False --> opened
	 */
	@Column(name = "FG_CLOSED_CLASS", nullable = false)
	private Boolean closedClass;

	
	/*
	 * Class level
	 * Example: Class 1, Class 2, Class 3 , ...
	 */
	@Column(name = "DE_CLASSROOM", length = 15) 
	private String classRoom;
	
	
	/*
	 * Professor Info
	 * Associate student presences to a class
	 */
	@ManyToOne
	@JoinColumn
	private PresenceDomain presence;

	
	/*
	 * Getters and Setters
	 */
	public String getClassReference() {
		return classReference;
	}


	public void setClassReference(String classReference) {
		this.classReference = classReference;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getClassLevel() {
		return classLevel;
	}


	public void setClassLevel(String classLevel) {
		this.classLevel = classLevel;
	}


	public String getClassType() {
		return classType;
	}


	public void setClassType(String classType) {
		this.classType = classType;
	}


	public Date getClassBeginDate() {
		return classBeginDate;
	}


	public void setClassBeginDate(Date classBeginDate) {
		this.classBeginDate = classBeginDate;
	}


	public Date getClassEndDate() {
		return classEndDate;
	}


	public void setClassEndDate(Date classEndDate) {
		this.classEndDate = classEndDate;
	}


	public Date getClassTime() {
		return classTime;
	}


	public void setClassTime(Date classTime) {
		this.classTime = classTime;
	}


	public Boolean getClosedClass() {
		return closedClass;
	}


	public void setClosedClass(Boolean closedClass) {
		this.closedClass = closedClass;
	}


	/**
	 * @return the presence
	 */
	public PresenceDomain getPresence() {
		return presence;
	}


	/**
	 * @param presence the presence to set
	 */
	public void setPresence(PresenceDomain presence) {
		this.presence = presence;
	}


	public String getClassRoom() {
		return classRoom;
	}


	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
}
