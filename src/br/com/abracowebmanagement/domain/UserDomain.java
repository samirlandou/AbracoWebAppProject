package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_USER")	
public class UserDomain extends GenericDomain{
	
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
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID_LOGIN")
	private Long id;*/
	
	


	/*
	 * Phone number
	 */
	@Column(name = "DE_CNPJ", length = 18, nullable = true)
	private String cnpj;
	
	/*
	 * User Name
	 */
	@Column(name = "NM_USER", length = 30, nullable = false)
	private String userName;

	
	/*
	 * Password <br/>
	 * Using MD5 cryptography to encode the password
	 */
	
	@Column(name = "DE_PASSWORD", length = 32, nullable = false)
	private String password;

	
	/*
	 * Password <br/>
	 * Without MD5 cryptography
	 * 
	 */
	@Transient
	private String passwordWithoutCryptography;
	
	
	
	/*
	 * Remember password <br/>
	 * Sentence that will help to remember the password.
	 */
	@Column(name = "DE_REMEMBER_PASSWORD", length = 100, nullable = false)
	private String rememberPassword;

	
	/*
	 * Person <br/>
	 * Insert Person Data Table into UserDomain 
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private PersonDomain personDomain;
	
	
	/*
	 * Image User File Name <br/>
	 * content the file name
	 */
	@Column(name = "NM_IMAGE_USER_FILE", length = 100)
	private String imageUserFileName;
	
		
	/*
	 * User Theme <br/>
	 * content user theme description
	 */
	@Column(name = "DE_USER_THEME", length = 20)
	private String userTheme;
	
	/*
	 * User <br/>
	 * Save User Image Temporarily with @Transcient
	 */
	@Transient
	private String imageUserPath;
	
	
	/*
	 * User
	 * Image
	 */
	//@Lob
	/*@JoinColumn(name = "DE_USER_IMAGE")
	private byte[] imageUser;*/

	
	
	/*
	 * Getters and Setters
	 */
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {		
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {	
		this.password = password;
	}


	public String getRememberPassword() {
		return rememberPassword;
	}


	public void setRememberPassword(String rememberPassword) {
		this.rememberPassword = rememberPassword;
	}


	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}


	public String getImageUserPath() {
		return imageUserPath;
	}


	public void setImageUserPath(String imageUserPath) {
		this.imageUserPath = imageUserPath;
	}


	public String getImageUserFileName() {
		return imageUserFileName;
	}


	public void setImageUserFileName(String imageUserFileName) {
		this.imageUserFileName = imageUserFileName;
	}


	public String getPasswordWithoutCryptography() {
		return passwordWithoutCryptography;
	}


	public void setPasswordWithoutCryptography(String passwordWithoutCryptography) {		
		this.passwordWithoutCryptography = passwordWithoutCryptography;
	}


	public String getUserTheme() {
		return userTheme;
	}


	public void setUserTheme(String userTheme) {
		this.userTheme = userTheme;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	/*public byte[] getImageUser() {
		return imageUser;
	}


	public void setImageUser(byte[] userImage) {
		this.imageUser = userImage;
	}	*/
	
}
