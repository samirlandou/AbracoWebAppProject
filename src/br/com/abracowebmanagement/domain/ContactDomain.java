package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CONTACT")
public class ContactDomain extends GenericDomain{
	
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
	 * E-mail
	 */
	@Column(name = "DE_EMAIL", length = 50, nullable = false)
	private String email;
	
	
	/*
	 * Phone number
	 */
	@Column(name = "DE_TELEPHONE", length = 20, nullable = false)
	private Integer telephone;
	
	
	/*
	 * Address
	 */
	@Column(name = "DE_ADDRESS", length = 100)
	private String address;

	
	/*
	 * Getter and Setters
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
