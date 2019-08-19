package br.com.abracowebmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_PROFESSOR")
public class ProfessorDomain extends GenericDomain{

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
	 * Associate a personal info to a professor
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonalInfoDomain personalInfo;
	
	
	/*
	 * Contact Info
	 * Associate a contact to a professor
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ContactDomain contact;
	
	
	/*
	 * Profile Info
	 * Associate a profile to a professor
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private ProfileDomain profile;
	
	
	/*
	 * Student Info
	 * Associate students to a professor
	 */
	@ManyToOne
	@JoinColumn
	private StudentDomain student;


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


	public StudentDomain getStudent() {
		return student;
	}


	public void setStudent(StudentDomain student) {
		this.student = student;
	}
}
