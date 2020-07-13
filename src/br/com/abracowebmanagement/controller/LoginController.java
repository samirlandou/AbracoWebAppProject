package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.PersonDAO;
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
	private String loginTime;
	private String logoffTime;
	private boolean isLogged = false;
	private boolean isDisconnected = false;
	private boolean accessRightByProfile;

	PersonDomain personDomain;
	PersonDAO personDAO = new PersonDAO();
	
	
	/* public  void checkFirstData() {
			personDomain = new PersonDomain();
			
			
			personDomain = personDAO.search((long) 1);
			
			//Check if first data Exist
			if(personDomain != null && personDomain.getProfile().equals("ADMINISTRADOR(A)")){
				
				//Inform that data exist.
				System.out.println("Abraço Cultaral's data already exist!");
			} else{
				
				//Set data
				personDomain.setAddress("Rua dos Pinheiros, 706");
				personDomain.setCompleteName("Abraço Cultural");
				personDomain.setCountry("Brasil");
				personDomain.setCpf("697.458.687-67");
				personDomain.setEmail("abraco@abracocultural.com.br");
				personDomain.setId(1L);
				personDomain.setLanguage1("BR");
				personDomain.setLanguage2("");
				personDomain.setLanguage3("");
				personDomain.setProfile("ADMINISTRADOR(A)");
				personDomain.setSex('F');
				personDomain.setStatus(true);
				personDomain.setTelephone("(11)32323-2323");
				
				//Save data
				personDAO.save(personDomain);
				
				//Inform that data exist.
				System.out.println("Abraço Cultaral's data is inserted with success!");
			}		 
 
	 }*/
	
	
	@PostConstruct
	public void init() {
		
		//UserDomain
		user = new UserDomain();
		user.setPersonDomain(new PersonDomain());
		
		loggedUser = new UserDomain();
		
		rememberPassword = new UserDomain();
		rememberPassword.setPersonDomain(new PersonDomain());
		
		accessRightByProfile = false;	
		
		//Check First Data
		//checkFirstData();
	}

	
	public String doConnect() {
		String redirectTo = "";
		try {
			
			UserDAO userDAO = new UserDAO();
			loggedUser = userDAO.authenticate(user.getUserName(), user.getPassword());
			
			if (loggedUser == null) {
				Messages.addGlobalError("Usuário e/ou senha incorretos");
			} else {
				if(loggedUser.getPersonDomain().getStatus() == true){
					//Execute it to find the user access 
					doAccessRight();
					System.out.println("Access Right User [" + loggedUser.getUserName() + "] = " + accessRightByProfile);
					
					redirectTo = "contents/home.xhtml?faces-redirect=true";
					isLogged = true;
					

					
					//Log
					loginTime = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());       
			        System.out.println("Accesso:  " + loginTime + " [" + loggedUser.getUserName() + "]");			
				} else{
					Messages.addGlobalError("Acesso Exprirado!");
				}
	
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
				rememberPassword = userDAO.findByUserName(user.getUserName());
				
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
        
        //Log
		logoffTime = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());       
        System.out.println("Desconetado:  " + logoffTime + " [" + loggedUser.getUserName() + "]");
        
        Messages.addGlobalInfo("Desconetado com sucesso !!!");
		return "client.disconnect";
    }
    
    
	private void doAccessRight(){
    	if(loggedUser.getUserName() != null
    			&& (loggedUser.getPersonDomain().getProfile().equals("ADMINISTRADOR(A)")
    			|| loggedUser.getPersonDomain().getProfile().equals("COORDENADOR(A)"))){
    		accessRightByProfile = true;
    	}else{
    		accessRightByProfile = false;
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


	public String getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	public String getLogoffTime() {
		return logoffTime;
	}


	public void setLogoffTime(String logoffTime) {
		this.logoffTime = logoffTime;
	}


	public boolean isAccessRightByProfile() {
		return accessRightByProfile;
	}


	public void setAccessRightByProfile(boolean accessRightByProfile) {
		this.accessRightByProfile = accessRightByProfile;
	}
	
	
}
