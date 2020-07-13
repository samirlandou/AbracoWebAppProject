package br.com.abracowebmanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	private Boolean presenceFlag;
	

	/*
	 *Presence begin date and Hour
	 * Inform the beginning time and date of the class
	 */
	@Column(name = "DT_PRESENCE_CLASS_BEGIN", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date presenceBeginDate;
	
	
	/*
	 *Presence End date and Hour
	 * Inform the end time and date of the class
	 */
	@Column(name = "DT_END_PRESENCE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date presenceEndDate;	

	
	/*
	 *Presence Time
	 * Inform how many time were the class
	 */
	@Column(name = "DT_TIME_CLASS", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date presenceTime;
	
	
	/*
	 * Comment Class
	 * Comment if there is a specific notification to inform
	 * about the class
	 * */
	@Column(name = "DE_COMMENT_CLASS", length = 100)
	private String presenceClassComment;

	
	/*
	 * UserName
	 * Get the name (through login user) of the user who created the Schedule event.
	 * from UserDomain class.
	 * */
	@Column(name = "DE_LOGIN_USER_PRESENCE", length = 30)
	private String presenceLoginUser;
	
	
	/*
	 * StudentInMyClass
	 * Choose information from "ContractStudentDomain" Data Table into PresenceDomain 
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private ContractStudentDomain contractStudentDomain;


	/*
	 * Get the class code from MyClassDomain
	 * Choose information from "MyClassDomain" Data Table into PresenceDomain
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private MyClassDomain myClassDomain;

	
	/*
	 * Login
	 * Insert Login Data Table into PresenceDomain
	 * Inform here who save the presence list.
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private UserDomain userDomain;
	
	
	/*
	 * Getters and Setters
	 */
	
	public Boolean getPresenceFlag() {
		return presenceFlag;
	}


	public void setPresenceFlag(Boolean presenceFlag) {
		this.presenceFlag = presenceFlag;
	}


	public Date getPresenceBeginDate() {
		return presenceBeginDate;
	}


	public void setPresenceBeginDate(Date presenceBeginDate) {
		this.presenceBeginDate = presenceBeginDate;
	}


	public Date getPresenceEndDate() {
		return presenceEndDate;
	}


	public void setPresenceEndDate(Date presenceEndDate) {
		this.presenceEndDate = presenceEndDate;
	}


	public Date getPresenceTime() {
		return presenceTime;
	}


	public void setPresenceTime(Date presenceTime) {
		this.presenceTime = presenceTime;
	}


	public String getPresenceClassComment() {
		return presenceClassComment;
	}


	public void setPresenceClassComment(String presenceClassComment) {
		this.presenceClassComment = presenceClassComment;
	}


	public String getPresenceLoginUser() {
		return presenceLoginUser;
	}


	public void setPresenceLoginUser(String presenceLoginUser) {
		this.presenceLoginUser = presenceLoginUser;
	}


	public MyClassDomain getMyClassDomain() {
		return myClassDomain;
	}


	public void setMyClassDomain(MyClassDomain myClassDomain) {
		this.myClassDomain = myClassDomain;
	}


	public UserDomain getUserDomain() {
		return userDomain;
	}


	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}
	
}
