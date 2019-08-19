package br.com.abracowebmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_COORDINATOR")
public class CoordinatorDomain extends GenericDomain{

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
	 * Personal Info
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonalInfoDomain personalInfo;
	
	
	/*
	 * Contact Info
	 * Associate a contact to a coordinator
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ContactDomain contact;
	
	
	/*
	 * Profile Info
	 * Associate a profile to a coordinator
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ProfileDomain profile;
	
	
	/*
	 * Professor Info
	 * Associate professors to a coordinator
	 */
	@ManyToOne
	@JoinColumn
	private ProfessorDomain professor;	


	/*
	 * Getters and Setters
	 */
	public PersonalInfoDomain getPersonalInfo() {
		return personalInfo;
	}


	public void setPersonalInfo(PersonalInfoDomain personalInfo) {
		this.personalInfo = personalInfo;
	}


	public ContactDomain getContact() {
		return contact;
	}


	public void setContact(ContactDomain contact) {
		this.contact = contact;
	}


	public ProfileDomain getProfile() {
		return profile;
	}


	public void setProfile(ProfileDomain profile) {
		this.profile = profile;
	}


	/**
	 * @return the professor
	 */
	public ProfessorDomain getProfessor() {
		return professor;
	}


	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(ProfessorDomain professor) {
		this.professor = professor;
	}
}
