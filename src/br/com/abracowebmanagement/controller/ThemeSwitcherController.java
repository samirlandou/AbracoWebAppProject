package br.com.abracowebmanagement.controller;


import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.themeswitcher.ThemeSwitcher;

import br.com.abracowebmanagement.domain.UserDomain;

@ManagedBean
@SessionScoped
public class ThemeSwitcherController {
	
	//Instantiate User Domain
	UserDomain userDomain = new UserDomain();

	//Create Map to store themes values
	private Map<String, String> themes;

	//Default Theme --> "ui-lightness"
	private String theme = null;
	
	//Initial Theme
	private String initialTheme;

	@PostConstruct
	public void doInit() {

		FacesContext fcLogin = FacesContext.getCurrentInstance();
		
		//Get LoginController External Context
		LoginController loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
		
		themes = new TreeMap<String, String>();
		themes.put("Aristo", "aristo");
		themes.put("Black-Tie", "black-tie");
		themes.put("Blitzer", "blitzer");
		themes.put("Bluesky", "bluesky");
		themes.put("Casablanca", "casablanca");
		themes.put("Cupertino", "cupertino");
		themes.put("Dark-Hive", "dark-hive");
		themes.put("Dot-Luv", "dot-luv");
		themes.put("Eggplant", "eggplant");
		themes.put("Excite-Bike", "excite-bike");
		themes.put("Flick", "flick");
		themes.put("Glass-X", "glass-x");
		themes.put("Hot-Sneaks", "hot-sneaks");
		themes.put("Humanity", "humanity");
		themes.put("Le-Frog", "le-frog");
		themes.put("Luna-Amber", "luna-amber");
		themes.put("Luna-Blue", "luna-blue");
		themes.put("Luna-Green", "luna-green");
		themes.put("Luna-Pink", "luna-pink");
		themes.put("Midnight", "midnight");
		themes.put("Mint-Choc", "mint-choc");
		themes.put("Nova-Colored", "nova-colored");
		themes.put("Nova-Dark", "nova-dark");
		themes.put("Nova-Light", "nova-light");
		themes.put("Omega", "omega");
		themes.put("Overcast", "overcast");
		themes.put("Pepper-Grinder", "pepper-grinder");
		themes.put("Redmond", "redmond");
		themes.put("Rocket", "rocket");
		themes.put("Sam", "sam");
		themes.put("Smoothness", "smoothness");
		themes.put("South-Street", "south-street");
		themes.put("Start", "start");
		themes.put("Sunny", "sunny");
		themes.put("Swanky-Purse", "swanky-purse");
		themes.put("Trontastic", "trontastic");
		themes.put("Vader", "vader");
		themes.put("UI-Lightness", "ui-lightness");
		themes.put("UI-Darkness", "ui-darkness");
		
		
		if(loginController != null && loginController.isLogged() && loginController.getLoggedUser().getUserTheme() != null){
			
			//Set Theme According to the user theme
			setTheme(loginController.getLoggedUser().getUserTheme());

		} else{
			
			//Set Default Theme
			setTheme("ui-lightness");
			
			//Set Initial Theme
			setInitialTheme("ui-lightness");
		}
	}
	
   
	public void saveTheme(AjaxBehaviorEvent ajax){
	   setTheme((String) ((ThemeSwitcher)ajax.getSource()).getValue());
   }

   
	/*
	 * Getters and Setters
	 */
	
	public Map<String, String> getThemes() {
		return themes;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}


	public String getInitialTheme() {
		return initialTheme;
	}


	public void setInitialTheme(String initialTheme) {
		this.initialTheme = initialTheme;
	}
}
