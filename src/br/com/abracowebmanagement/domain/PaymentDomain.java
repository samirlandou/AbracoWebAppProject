package br.com.abracowebmanagement.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_PAYMENT")
public class PaymentDomain extends GenericDomain{

	@Column(name = "NM_PAYMENT", length = 20, nullable = false)
	private String paymentName;
	
	@Column(name = "DE_PAYMENT_TYPE", precision = 7, scale = 2, nullable = false)
	private BigDecimal paymentType;

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public BigDecimal getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(BigDecimal paymentType) {
		this.paymentType = paymentType;
	}	
}
