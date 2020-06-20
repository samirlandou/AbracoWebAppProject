package br.com.abracowebmanagement.domain.accessright;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.abracowebmanagement.domain.MyClassDomain;
import br.com.abracowebmanagement.domain.generic.GenericDomain;
import br.com.abracowebmanagement.domain.user.UserDomain;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ACCESS_RIGHT")	
public class AccessRightDomain extends GenericDomain{
	
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
	 * Get User Name (as Professor) form UserDomain
	 * Insert UserDomain Data Table into AccessRightDomain.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private UserDomain userDomain;

	
	/*
	 * Get the class code from MyClassDomain
	 * Insert MyClassDomain Data Table into AccessRightDomain.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private MyClassDomain myClassDomain;

	
	/*
	 * Getters and Setters
	 */
	
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
