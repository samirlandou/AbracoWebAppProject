package br.com.abracowebmanagement.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;


@ManagedBean
@SessionScoped
@Named("login")
public class LoginController implements Serializable{

	private static final long serialVersionUID = 5358085187631674579L;
	
	private UserDomain user ;
	private UserDomain loggedUser;
	private UserDomain rememberPassword;
	private String redirect;
	private boolean isLogged = false;
	private boolean isDisconnected = false;

	
	@PostConstruct
	public void init() {
		
		//UserDomain
		user = new UserDomain();
		user.setPersonDomain(new PersonDomain());
		
		loggedUser = new UserDomain();
		
		rememberPassword = new UserDomain();
		rememberPassword.setPersonDomain(new PersonDomain());
	}

	
	public String doConnect() {
		String redirectTo = "";
		try {
			UserDAO userDAO = new UserDAO();
			loggedUser = userDAO.authenticate(user.getUserName(), user.getPassword());
			
			if (loggedUser == null) {
				Messages.addGlobalError("Usuário e/ou senha incorretos");
			} else {
				redirectTo = "contents/home.xhtml?faces-redirect=true";
				isLogged = true;
			} 
		} catch (Exception e) {
				Messages.addGlobalError("Usuário e/ou senha incorretos");
		}
		return redirectTo;
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
	
	
    public String doDisconnect() {
    	isDisconnected = true;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);       
        session.invalidate();
        Messages.addGlobalError("Desconetado com sucesso !!!");
		return "client.deconnect";
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


	public boolean isLogged() {
		return isLogged;
	}


	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}


	public boolean isDisconnected() {
		return isDisconnected;
	}


	public void setDisconnected(boolean isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	
}
