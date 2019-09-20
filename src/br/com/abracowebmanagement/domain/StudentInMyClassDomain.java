package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_STUDENT_IN_MYCLASS")	
public class StudentInMyClassDomain extends GenericDomain{
	
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
	 * Comment Student
	 * Comment if there is a specific notification to inform
	 * about the Student.
	 */
	
	@Column(name = "DE_COMMENT_STUDENT", length = 100, nullable = false)
	private String commentStudent;

	
	/*
	 * Get Student's Name (as Person) from PersonDomain
	 * Insert PersonDomain Data Table into StudentInMyClassDomain.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;

	
	/*
	 * Get User Name from UserDomain
	 * Insert UserDomain Data Table into StudentInMyClassDomain.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private UserDomain userDomain;

	
	/*
	 * Get MyClass Code from MyClassDomain
	 * Insert MyClassDomain Data Table into StudentInMyClassDomain.
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private MyClassDomain myClassDomain;

	
	/*
	 * Getters and Setters
	 */

	public String getCommentStudent() {
		return commentStudent;
	}


	public void setCommentStudent(String commentStudent) {
		this.commentStudent = commentStudent;
	}


	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}


	public UserDomain getUserDomain() {
		return userDomain;
	}


	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}


	public MyClassDomain getMyClassDomain() {
		return myClassDomain;
	}


	public void setMyClassDomain(MyClassDomain myClassDomain) {
		this.myClassDomain = myClassDomain;
	}
	
}
