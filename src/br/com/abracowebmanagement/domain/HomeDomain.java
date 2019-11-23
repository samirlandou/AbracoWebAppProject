package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_SCHEDULE_EVENT")
public class HomeDomain extends GenericDomain {

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
	 * Schedule Event description
	 * Inform how many was the class
	 */
	@Column(name = "DE_HOME", length = 20, nullable = false)
	private String homeDescription;

	
	
	public String getHomeDescription() {
		return homeDescription;
	}

	public void setHomeDescription(String homeDescription) {
		this.homeDescription = homeDescription;
	}
	
}
