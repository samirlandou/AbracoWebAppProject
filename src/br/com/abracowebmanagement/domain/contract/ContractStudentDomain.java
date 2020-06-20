package br.com.abracowebmanagement.domain.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.abracowebmanagement.domain.generic.GenericDomain;
import br.com.abracowebmanagement.domain.person.PersonDomain;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_CONTRACT_STUDENT")
public class ContractStudentDomain extends GenericDomain{

	/**
	 * Nomenclature for data base
	 * TB -> TABLE
	 * NM -> NAME
	 * DE -> DESCRIPTION
	 * TP -> TYPE
	 * DT -> DATE
	 * FG -> FLAG 
	 * HR -> HOUR
	 */

	
	//-------------  Student Payment Contract ------------------------------
		
	/*
	 * Student Payment Contract
	 * It can be : Integral/A vista | Parcelamento (x4) | Gratuito
	 * 
	 */
	@Column(name = "DE_STUDENT_PAYMENT_CONTRACT_STUDENT", length = 25,  nullable = false)
	private String  studentPaymentContractDescription;
	
	
	/*
	 * Student Payment Type Contract
	 *
	 * It can be : Dinheiro | Depósito | Boleto | Cartão de Débito | PagSeguro/Crédito | Gratuito
	 * 
	 */
	@Column(name = "TP_STUDENT_PAYMENT_CONTRACT_STUDENT", length = 25, nullable = false)
	private String  studentPaymentContractType;
		
	
	/*
	 *Class time
	 * Informs the Total package Price.
	 */
	@Column(name = "DE_DISCOUNT_PRICE_CONTRACT_STUDENT", precision = 6, scale = 2, nullable = false)
	private double studentDiscountPriceDescription;
	
	
	/*
	 * Receive Book Contract Student
	 * Close the class when it ends.
	 * Example:
	 * True ---> closed
	 * False --> opened
	 */
	@Column(name = "FG_RECEIVE_BOOK_CONTRACT_STUDENT", nullable = false)
	private Boolean studentReceiveBookFlag;
	
		
	/*
	 * Sign Contract Student
	 * Inform if the contract was signed.
	 * Example:
	 * True ---> Signed
	 * False --> not signed
	 */
	@Column(name = "FG_SIGN_CONTRACT_STUDENT", nullable = false)
	private Boolean signContractStudentFlag;
	
	
	//----------- Date when the Contract has been saved ------------------
	
	/*
	 * Save Contract Date
	 * Get Contract Saving Date
	 * 
	 */
	@Column(name = "DT_SAVE_CONTRACT_STUDENT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date saveContractStudentDate;
	
		
	/*
	 * Contract Save Login User
	 * Get the Last Login User Who Has Saved the Contract
	 * 
	 */
	@Column(name = "DE_LOGIN_USER_SAVE_CONTRACT_STUDENT", nullable = false)
	private String  contractStudentSaveLoginUser;
	
	
	//-------------- Foreign Keys -----------------------------
		
	
	/*
	 * Person --> For Student
	 * Insert Person Data Table into ContractStudentDomain
	 * Inform here the name of the student.
	 */	
	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;

	
	/*
	 * Contract
	 * Insert Contract Data Table into ContractDomain
	 * Inform here the the contract.
	 */	
	@ManyToOne
	@JoinColumn(nullable = false)
	private ContractDomain contractDomain;

	
	
	/*
	 * Getters and Setters
	 */
	

	public String getStudentPaymentContractDescription() {
		return studentPaymentContractDescription;
	}

	public void setStudentPaymentContractDescription(String studentPaymentContractDescription) {
		this.studentPaymentContractDescription = studentPaymentContractDescription;
	}

	public String getStudentPaymentContractType() {
		return studentPaymentContractType;
	}

	public void setStudentPaymentContractType(String studentPaymentContractType) {
		this.studentPaymentContractType = studentPaymentContractType;
	}

	public PersonDomain getPersonDomain() {
		return personDomain;
	}

	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}

	public ContractDomain getContractDomain() {
		return contractDomain;
	}

	public void setContractDomain(ContractDomain contractDomain) {
		this.contractDomain = contractDomain;
	}

	public double getStudentDiscountPriceDescription() {
		return studentDiscountPriceDescription;
	}

	public void setStudentDiscountPriceDescription(double studentDiscountPriceDescription) {
		this.studentDiscountPriceDescription = studentDiscountPriceDescription;
	}

	public Boolean getStudentReceiveBookFlag() {
		return studentReceiveBookFlag;
	}

	public void setStudentReceiveBookFlag(Boolean studentReceiveBookFlag) {
		this.studentReceiveBookFlag = studentReceiveBookFlag;
	}

	public Date getSaveContractStudentDate() {
		return saveContractStudentDate;
	}

	public void setSaveContractStudentDate(Date saveContractStudentDate) {
		this.saveContractStudentDate = saveContractStudentDate;
	}

	public String getContractStudentSaveLoginUser() {
		return contractStudentSaveLoginUser;
	}

	public void setContractStudentSaveLoginUser(String contractStudentSaveLoginUser) {
		this.contractStudentSaveLoginUser = contractStudentSaveLoginUser;
	}

	public Boolean getSignContractStudentFlag() {
		return signContractStudentFlag;
	}

	public void setSignContractStudentFlag(Boolean signContractStudentFlag) {
		this.signContractStudentFlag = signContractStudentFlag;
	}


}
