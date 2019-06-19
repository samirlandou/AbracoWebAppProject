package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_login")	
public class LoginDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID_LOGIN")
	private Long id;
	
	@Column(name = "DE_USERNAME", length = 20, nullable = false)
	private String userName;
	
	@Column(name = "DE_PASSWORD", length = 20, nullable = false)
	private String password;
	
	@Column(name = "DE_REMEMBER_PASSWORD", length = 100, nullable = false)
	private String rememberPassword;
}
