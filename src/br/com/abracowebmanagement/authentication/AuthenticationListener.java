package br.com.abracowebmanagement.authentication;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.abracowebmanagement.controller.LoginController;

public class AuthenticationListener implements PhaseListener{

	private static final long serialVersionUID = -7165124711351129164L;
	
	// site web http://balusc.omnifaces.org/2006/09/debug-jsf-lifecycle.html
	@Override
	public void afterPhase(PhaseEvent event) {
		//System.out.println("BEFORE PHASE: " + event.getPhaseId());
		
		//Get current page
		String 	currentPage = Faces.getViewId();
		
		//System.out.println("Current page: " + currentPage);
		
		//Boolean to check if the current page is a login page.
		boolean isLoginPage = currentPage.contains("login.xhtml");

		FacesContext fc = FacesContext.getCurrentInstance();
		
		//Get Session
		//LoginController loginController = Faces.getSessionAttribute("loginController");
		LoginController loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");
		
		//System.out.println("AuthenticationController: " + loginController);
		
		//boolean variable to verify if the user have already logged.
		boolean isLogged = false;
		boolean isDisconnected = false;
		
		if(loginController!= null){
			isLogged = loginController.isLogged();
			isDisconnected = loginController.isDisconnected();
		}
		
		
		//if current pages is not the login page, redirect current pages to login pages to force the user authentication
		if(!isLoginPage){
			

			//Check if the user has already passed to the login page.
			if(!isLogged && !isDisconnected){				
				Faces.navigate("/pages/login.xhtml?faces-redirect=true");
				return;
			}
		}else{
			if(isLogged){
				Faces.navigate("/pages/contents/home.xhtml?faces-redirect=true");
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		//System.out.println("AFTER PHASE: " + event.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
