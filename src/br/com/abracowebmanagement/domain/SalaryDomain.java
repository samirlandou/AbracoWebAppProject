package br.com.abracowebmanagement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_SALARY")
public class SalaryDomain extends GenericDomain{

	@Column(name = "NM_SALARY", length = 20, nullable = false)
	private String salaryName;
	
	@Column(name = "DE_SALARY_TYPE", precision = 7, scale = 2, nullable = false)
	private BigDecimal salaryType;

	public String getSalaryName() {
		return salaryName;
	}

	public void setSalaryName(String salaryName) {
		this.salaryName = salaryName;
	}

	public BigDecimal getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(BigDecimal salaryType) {
		this.salaryType = salaryType;
	}
}
