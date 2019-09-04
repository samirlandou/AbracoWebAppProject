package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_STUDENT")	
public class StudentDomain extends GenericDomain{
	
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
	 * Goal
	 * Inform here the goal of the student to teach this language.
	 */
	
	@Column(name = "DE_GOAL", length = 100, nullable = false)
	private String goal;

	
	/*
	 * Profile
	 * Insert Profile Data Table into StudentDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ProfileDomain ProfileDomain;

	
	/*
	 * Personal Info
	 * Insert Personal Info Data Table into StudentDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonalInfoDomain personalInfoDomain;
	
	
	/*
	 * Getters and Setters
	 */
	
	public String getGoal() {
		return goal;
	}


	public void setGoal(String goal) {
		this.goal = goal;
	}


	public ProfileDomain getProfileDomain() {
		return ProfileDomain;
	}


	public void setProfileDomain(ProfileDomain profileDomain) {
		ProfileDomain = profileDomain;
	}


	public PersonalInfoDomain getPersonalInfoDomain() {
		return personalInfoDomain;
	}


	public void setPersonalInfoDomain(PersonalInfoDomain personalInfoDomain) {
		this.personalInfoDomain = personalInfoDomain;
	}
	
}
