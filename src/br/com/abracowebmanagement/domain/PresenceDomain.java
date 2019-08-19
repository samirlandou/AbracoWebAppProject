package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	 *Presence
	 * Informs student presence date for each class
	 */
	@Column(name = "DT_PRESENCE_CLASS")
	@Temporal(TemporalType.DATE)
	private Date presenceClassDate;

	
	/*
	 * Presence 
	 * Check student presence
	 * Example: true --> PRES or false --> ABS.
	 */
	@Column(name = "FG_PRESENCE", nullable = false)
	private Boolean presence;
	
	
	/*
	 * Comment if there is a specific notification to inform
	 * about the presence of the students
	 * */
	@Column(name = "DE_COMMENT", length = 50)
	private String comment;


	
	/*
	 * Getters and Setters
	 */
	public Date getPresenceClassDate() {
		return presenceClassDate;
	}


	public void setPresenceClassDate(Date presenceClassDate) {
		this.presenceClassDate = presenceClassDate;
	}


	public Boolean getPresence() {
		return presence;
	}


	public void setPresence(Boolean presence) {
		this.presence = presence;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
}
