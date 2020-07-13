package br.com.abracowebmanagement.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.UserDomain;

@ManagedBean
@SessionScoped
public class ThemeSettingController {
	
	//Domain
	UserDomain userDomain;
	
	//Controller
	LoginController loginController;
	ThemeSwitcherController themeSwitcherController;
	
	
	@PostConstruct
	public void doInit() {
		
		//Instantiate UserDomain
		userDomain = new UserDomain();
		
		//FacesContext for Login
		FacesContext fcLogin = FacesContext.getCurrentInstance();
		
		//FacesContext for Theme
		FacesContext fcTheme = FacesContext.getCurrentInstance();
		
		
		//Get External Context from LoginController
		loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
		
		//Get External Context from ThemeSwitcherController
		themeSwitcherController = (ThemeSwitcherController) fcTheme.getExternalContext().getSessionMap().get("themeSwitcherController");
		
		//Store Initial Theme Value
		themeSwitcherController.setInitialTheme(loginController.getLoggedUser().getUserTheme());
		
		//Set New User theme value
		loginController.getLoggedUser().setUserTheme(themeSwitcherController.getTheme());		

	}

	
	public void doSave(){
		
		try {
			//Condition to save Theme
			if(!themeSwitcherController.getInitialTheme().equals(themeSwitcherController.getTheme())){
			
			//Get userDomain info
			userDomain = loginController.getLoggedUser();
				
			//Save User new Theme using merge
			UserDAO userDAO = new UserDAO();
			userDAO.merge(userDomain);
			
			//Store again Initial Theme Value
			themeSwitcherController.setInitialTheme(loginController.getLoggedUser().getUserTheme());
			
			//Inform messages for saving successful
			Messages.addGlobalInfo("O tema '" + themeSwitcherController.getTheme() + "' foi salvo com sucesso!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
