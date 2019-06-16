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
	@Column(name ="id_login")
	private Long idLogin;
	
	@Column(name = "de_username", length = 20, nullable = false)
	private String userName;
	
	@Column(name = "de_password", length = 20, nullable = false)
	private String password;
	
	@Column(name = "de_remember_password", length = 100, nullable = false)
	private String rememberPassword;
}
