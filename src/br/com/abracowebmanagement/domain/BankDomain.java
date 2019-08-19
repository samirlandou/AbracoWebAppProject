package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_BANK")
public class BankDomain extends GenericDomain{
	
	@Column(name = "NM_BANK", length = 20)
	private String bankName;
	
	@Column(name = "DE_ACOUNT_TYPE", length = 20)
	private String accountType;
	
	@Column(name = "DE_ACOUNT", length = 20)
	private String account;	
	
	@Column(name = "DE_AGENCY", length = 20)
	private String agency;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
}
