package br.com.abracowebmanagement.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;


@ManagedBean
@SessionScoped
public class LoginController {

	private UserDomain user;
	private UserDomain loggedUser;
	private UserDomain rememberPassword;
	private String redirect;

	
	@PostConstruct
	public void init() {
		
		//instantiate UserDomain
		user = new UserDomain();
		user.setPersonDomain(new PersonDomain());
		
		loggedUser = new UserDomain();
		rememberPassword = new UserDomain();
				
		redirect = "";
		/*instantiate PersonDomain
		user.setPersonDomain(new PersonDomain());*/
		//login.setPersonDomain(new PersonDomain());
		rememberPassword.setPersonDomain(new PersonDomain());
	}

	
	public String doAuthenticate() {
		
		try {
			UserDAO userDAO = new UserDAO();
			loggedUser = userDAO.authenticate(user.getUserName(), user.getPassword());
			
			if (loggedUser == null) {
				Messages.addGlobalError("Usuário e/ou senha incorretos");
				redirect = "";
			} else {
				redirect = "contents/home.xhtml";
			} 
		} catch (Exception e) {
				Messages.addGlobalError("Usuário e/ou senha incorretos");
		}
		return redirect;
	}

	
	public void doRememberPassword(){
		try {
			if(user.getUserName() == null){
				Messages.addGlobalWarn("Favor informar seu usuário e tentar de novo!");
				
			} else{
				UserDAO userDAO = new UserDAO();
				rememberPassword = userDAO.FindByUserName(user.getUserName());
				
				if(rememberPassword == null){
					Messages.addGlobalWarn("Favor informar um usuário válido!");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError(e.getMessage());
		}		
	}

	
	/*
	 * Getters and Setters
	 */
	
	public UserDomain getUser() {
		return user;
	}

	public void setUser(UserDomain userName) {
		this.user = userName;
	}

	public UserDomain getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserDomain loggedUser) {
		this.loggedUser = loggedUser;
	}

	public UserDomain getRememberPassword() {
		return rememberPassword;
	}

	public void setRememberPassword(UserDomain rememberPassword) {
		this.rememberPassword = rememberPassword;
	}


	public String getRedirect() {
		return redirect;
	}


	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
}
