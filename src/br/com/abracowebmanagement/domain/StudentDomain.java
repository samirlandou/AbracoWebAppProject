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
	 * Person
	 * Insert Person Data Table into StudentDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;
	
	
	/*
	 * Getters and Setters
	 */
	
	public String getGoal() {
		return goal;
	}


	public void setGoal(String goal) {
		this.goal = goal;
	}


	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}
	
}
