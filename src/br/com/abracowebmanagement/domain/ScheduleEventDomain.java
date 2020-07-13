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
	 * VL -> VALUE
	 */
	/*
	 * Schedule Event description
	 * Inform how many was the class
	 */
	@Column(name = "DE_SCHEDULE_EVENT", length = 20, nullable = false)
	private String scheduleEventDescription;
	
	
	/*
	 * Schedule Event type
	 * Example: Regular or Cultural Class(Extensive/Intensive/Private) , Pedagogic Meeting, Lecture, Event, Happy Hour,...
	 */
	@Column(name = "TP_SCHEDULE_EVENT", length = 20, nullable = false)
	private String scheduleEventType;

	
	/*
	 * Schedule Event begin date and Hour
	 * Inform the begin event date
	 */
	//@Column(name = "DT_BEGIN_SCHEDULE_EVENT", columnDefinition = "TIMESTAMP", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_BEGIN_SCHEDULE_EVENT", nullable = false)
	private Date scheduleEventBeginDate;
	
	
	
	/*
	 * Schedule Event end date and Hour
	 * Inform the end of the event
	 */
	//@Column(name = "DT_END_SCHEDULE_EVENT", columnDefinition = "TIMESTAMP", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_END_SCHEDULE_EVENT", nullable = false)
	private Date scheduleEventEndDate;
	
	
	/*
	 * Schedule Event time
	 * Inform how many time will be the event
	 	
	@Column(name = "VL_TIME_SCHEDULE_EVENT", nullable = false)
	private Long scheduleEventTime;	*/
	
	
	/*
	 * Schedule Event comment
	 * Comment specific notification about the event
	 */		
	@Column(name = "DE_COMMENT_SCHEDULE_EVENT", length = 100, nullable = true)
	private String scheduleEventComment;
	
	
	/*
	 * UserName
	 * Get the name (through login user) of the user who created the Schedule event.
	 * from UserDomain class.
	 * 
	@Column(name = "DE_LOGIN_USER_SCHEDULE_EVENT", length = 30)
	private String scheduleEventLoginUser;*/

		
	/*
	 * Status
	 * Example: ENABLED or DISABLED
	 */
	@Column(name = "FG_PUBLIC", nullable = false)
	private Boolean publicFlag;

	
	/*
	 * Status
	 * Example: ENABLED or DISABLED
	 */
	@Column(name = "FG_EDIT", nullable = false)
	private Boolean editFlag;

	
	/*
	 * Status
	 * Example: ENABLED or DISABLED
	 */
	@Column(name = "FG_ALLDAY", nullable = false)
	private Boolean allDayFlag;	

	
	/*
	 * Schedule Event comment
	 * Comment specific notification about the event
	 */		
	@Column(name = "NM_STYLECLASS", length = 60, nullable = true)
	private String styleclassName;
	

	
	/*
	 * Status
	 * Example: ENABLED or DISABLED
	 
	@Column(name = "FG_OVERLAP", nullable = false)
	private Boolean overLapFlag;*/	
	
	
	/*
	 * User
	 * Insert User Data Table into ScheduleDomain 
	 */
	@ManyToOne
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



	public String getScheduleEventComment() {
		return scheduleEventComment;
	}



	public void setScheduleEventComment(String scheduleEventComment) {
		this.scheduleEventComment = scheduleEventComment;
	}



	public Boolean getPublicFlag() {
		return publicFlag;
	}



	public void setPublicFlag(Boolean publicFlag) {
		this.publicFlag = publicFlag;
	}



	public Boolean getEditFlag() {
		return editFlag;
	}



	public void setEditFlag(Boolean editFlag) {
		this.editFlag = editFlag;
	}



	public Boolean getAllDayFlag() {
		return allDayFlag;
	}



	public void setAllDayFlag(Boolean allDayFlag) {
		this.allDayFlag = allDayFlag;
	}



	public String getStyleclassName() {
		return styleclassName;
	}



	public void setStyleclassName(String styleclassName) {
		this.styleclassName = styleclassName;
	}



	public UserDomain getUserDomain() {
		return userDomain;
	}



	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}
	
	
}
