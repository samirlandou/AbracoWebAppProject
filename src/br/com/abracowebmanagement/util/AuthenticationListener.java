package br.com.abracowebmanagement.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.abracowebmanagement.controller.LoginController;
import br.com.abracowebmanagement.domain.UserDomain;

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
		
		//if current pages is not the login page, redirect current pages to login pages to force the user authentication
		if(!isLoginPage){
			
			//Get Session
			LoginController loginController = Faces.getSessionAttribute("loginController");
			
			//System.out.println("AuthenticationController: " + loginController);			
			
			//Check if the user has already passed to the login page.
			if(loginController == null){
				Faces.navigate("/pages/login.xhtml");
				return;
			}
						
			//if the user has not already logged one time, redirect him to do login page.
			UserDomain user = loginController.getLoggedUser();
			if(user == null){
				Faces.navigate("/pages/login.xhtml");
				return;
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
