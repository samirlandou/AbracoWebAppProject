package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_USER")
public class UserDomain extends GenericDomain{
	
	@Column(name = "NM_FIRST_NAME", length = 20, nullable = false)
	private String firstName;
	
	@Column(name = "NM_MIDDLE_NAME", length = 20)
	private String middleName;
	
	@Column(name = "NM_LAST_NAME", length = 20, nullable = false)
	private String lastName;	
	
	@Column(name = "DE_USER_STATUS", length = 20, nullable = false)
	private String userStatus;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
}
