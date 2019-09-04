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
@Table(name = "TB_PRESENCE")
public class PresenceDomain extends GenericDomain{
	
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
	 * Presence 
	 * Check student presence
	 * Example: true --> PRESENTE or false --> ABSENT
	 */
	@Column(name = "FG_PRESENCE", nullable = false)
	private Boolean presence;
	

	/*
	 *Class Time
	 * Inform how many was the class
	 */
	@Column(name = "DT_TIME_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date timeClass;

	
	/*
	 *Presence
	 * Inform student presence date for each class
	 */
	@Column(name = "DT_PRESENCE_CLASS", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date presenceClassDate;
	
	
	/*
	 * Comment Class
	 * Comment if there is a specific notification to inform
	 * about the class
	 * */
	@Column(name = "DE_COMMENT_CLASS", length = 100)
	private String commentClass;

	
	/*
	 * Login
	 * Insert Login Data Table into PresenceDomain 
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private StudentInMyClassDomain studentInMyClassDomain;


	
	/*
	 * Getters and Setters
	 */

	public Boolean getPresence() {
		return presence;
	}


	public void setPresence(Boolean presence) {
		this.presence = presence;
	}


	public Date getTimeClass() {
		return timeClass;
	}


	public void setTimeClass(Date timeClass) {
		this.timeClass = timeClass;
	}


	public Date getPresenceClassDate() {
		return presenceClassDate;
	}


	public void setPresenceClassDate(Date presenceClassDate) {
		this.presenceClassDate = presenceClassDate;
	}


	public String getCommentClass() {
		return commentClass;
	}


	public void setCommentClass(String commentClass) {
		this.commentClass = commentClass;
	}


	public StudentInMyClassDomain getStudentInMyClassDomain() {
		return studentInMyClassDomain;
	}


	public void setStudentInMyClassDomain(StudentInMyClassDomain studentInMyClassDomain) {
		this.studentInMyClassDomain = studentInMyClassDomain;
	}
	
}
