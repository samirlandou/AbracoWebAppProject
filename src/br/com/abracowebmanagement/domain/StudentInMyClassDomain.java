package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	 * about the Student
	 */
	
	@Column(name = "DE_COMMENT_STUDENT", length = 100, nullable = false)
	private String commentStudent;

	
	/*
	 * Student
	 * Insert Student Data Table into StudentInMyClassDomain 
	 */
	@ManyToOne
	@JoinColumn
	private StudentDomain studentDomain;

	
	/*
	 * Login
	 * Insert Login Data Table into StudentInMyClassDomain 
	 */
	@ManyToOne
	@JoinColumn
	private UserDomain userDomain;

		
	/*
	 * Getters and Setters
	 */
	
	public String getCommentStudent() {
		return commentStudent;
	}


	public void setCommentStudent(String commentStudent) {
		this.commentStudent = commentStudent;
	}


	public StudentDomain getStudentDomain() {
		return studentDomain;
	}


	public void setStudentDomain(StudentDomain studentDomain) {
		this.studentDomain = studentDomain;
	}


	public UserDomain getLoginDomain() {
		return userDomain;
	}


	public void setLoginDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}
	
}
