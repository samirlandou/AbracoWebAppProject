package br.com.abracowebmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	 * Personal Info
	 * Associate a personal info to a student
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonalInfoDomain personalInfo;
	
	
	/*
	 * Contact Info
	 * Associate a contact to a student
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private ContactDomain contact;
	
	
	/*
	 * MyClass
	 * Associate classes to a student
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private ClassDomain myClass;


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


	public ClassDomain getMyClass() {
		return myClass;
	}


	public void setMyClass(ClassDomain myClass) {
		this.myClass = myClass;
	}
	
}
