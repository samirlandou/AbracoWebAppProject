package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_PERSONAL_INFO")
public class PersonalInfoDomain extends GenericDomain{
	
	
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
	 * Complete name
	 */
	@Column(name = "NM_COMPLETE_NAME", length = 20, nullable = false)
	private String completeName;	
	
	
	/*
	 * Nationality
	 */
	@Column(name = "DE_NATIONALITY", length = 20, nullable = false)
	private String nationality;

	
	/*
	 * RG Identity
	 */
	@Column(name = "DE_RG", length = 20)
	private String rg;

	
	/*
	 * RNE Identity
	 */
	@Column(name = "DE_RNE", length = 20)
	private String rne;
	
	
	/*
	 * CPF identity
	 */
	@Column(name = "DE_CPF", length = 20, nullable = false)
	private String cpf;
	
	
	/*
	 * CNPJ identity
	 */
	@Column(name = "DE_CNPJ", length = 20)
	private String cnpj;

	
	/*
	 * Getters and Setters
	 */
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
