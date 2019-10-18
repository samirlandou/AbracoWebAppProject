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
@Table(name = "TB_SCHEDULE_EVENT")
public class ScheduleEventDomain extends GenericDomain {

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
	 * Schedule Event description
	 * Inform how many was the class
	 */
	@Column(name = "DE_SCHEDULE_EVENT", length = 20, nullable = false)
	private String scheduleEventDescription;
	
	
	/*
	 * Schedule Event type
	 * Example: Cultural Class, Pedagogic Meeting, Lecture, Event, Happy Hour,...
	 */
	@Column(name = "TP_SCHEDULE_EVENT", length = 20, nullable = false)
	private String scheduleEventType;
	
	
	/*
	 * Schedule Event begin date and Hour
	 * Inform how many was the class
	 */
	@Column(name = "DT_BEGIN_SCHEDULE_EVENT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleEventBeginDate;
	
	/*
	 * Schedule Event begin date and Hour
	 * Inform how many was the class
	 */
	@Column(name = "DT_END_SCHEDULE_EVENT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleEventEndDate;
	
	
	/*
	 * Schedule Event time
	 * Inform how many time will be the event
	 */	
	@Column(name = "DT_TIME_SCHEDULE_EVENT", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date scheduleEventTime;	
	
	
	/*
	 * Schedule Event comment
	 * Comment specific notification about the event
	 */		
	@Column(name = "DE_COMMENT_SCHEDULE_EVENT", length = 100, nullable = false)
	private Date scheduleEventEndComment;
	
	
	/*
	 * UserName
	 * Get the name (through login user) of the user who created the Schedule event.
	 * from UserDomain class.
	 * */
	@Column(name = "DE_LOGIN_USER_SCHEDULE_EVENT", length = 30)
	private String scheduleEventLoginUser;
	
	
	/*
	 * User
	 * Insert User Data Table into ScheduleDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private UserDomain userDomain;


	public String getScheduleEventDescription() {
		return scheduleEventDescription;
	}


	public void setScheduleEventDescription(String scheduleEventDescription) {
		this.scheduleEventDescription = scheduleEventDescription;
	}


	public String getScheduleEventType() {
		return scheduleEventType;
	}


	public void setScheduleEventType(String scheduleEventType) {
		this.scheduleEventType = scheduleEventType;
	}


	public Date getScheduleEventBeginDate() {
		return scheduleEventBeginDate;
	}


	public void setScheduleEventBeginDate(Date scheduleEventBeginDate) {
		this.scheduleEventBeginDate = scheduleEventBeginDate;
	}


	public Date getScheduleEventEndDate() {
		return scheduleEventEndDate;
	}


	public void setScheduleEventEndDate(Date scheduleEventEndDate) {
		this.scheduleEventEndDate = scheduleEventEndDate;
	}


	public Date getScheduleEventTime() {
		return scheduleEventTime;
	}


	public void setScheduleEventTime(Date scheduleEventTime) {
		this.scheduleEventTime = scheduleEventTime;
	}


	public Date getScheduleEventEndComment() {
		return scheduleEventEndComment;
	}


	public void setScheduleEventEndComment(Date scheduleEventEndComment) {
		this.scheduleEventEndComment = scheduleEventEndComment;
	}


	public String getScheduleEventLoginUser() {
		return scheduleEventLoginUser;
	}


	public void setScheduleEventLoginUser(String scheduleEventLoginUser) {
		this.scheduleEventLoginUser = scheduleEventLoginUser;
	}
	
	
}
