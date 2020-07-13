package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.LanguageDAO;
import br.com.abracowebmanagement.domain.LanguageDomain;

@ManagedBean
@ViewScoped
public class LanguageController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Domain
	private LanguageDomain languageDomain;
	private List<LanguageDomain> languagesDomain;
	LanguageDomain resultDomain;
	
	String oldDescription;
	String oldName;
	
	//Login Controller
	LoginController loginController = new LoginController();

	

	
	
	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	@PostConstruct
	public void doList(){
		try {
			//List Language
			LanguageDAO languageDAO = new LanguageDAO();
			languagesDomain = languageDAO.ascendList("languageName");
						
			//Login
			FacesContext fc = FacesContext.getCurrentInstance();
			loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");
			
			//Clean Variables
			doClean();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações da língua.");
			e.printStackTrace();			
		}		
	}	
	

	public void doClean(){
		
		//Instantiate new Language
		languageDomain = new LanguageDomain();
		
		//Set old Name
		oldName = "";
		
		//Set old Description
		oldDescription = "";
	}
	
	
	/**
	 * Method for New Registration. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	public void doNewRegister(){		
		
		//Instantiate new Language
		languageDomain = new LanguageDomain();
		
		//Set Old Description
		oldName = getLanguageDomain().getLanguageName();
			
		//Set Old Description
		oldDescription = getLanguageDomain().getLanguageDescription();		
	}
	

	
	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	public void doSave(){		
		
		try {
			
			//Set Save Flag
			 boolean saveFlag = true;
			 
			//Save Language with merge method
			LanguageDAO languageDAO = new LanguageDAO();
			
			//Instantiate Result List Domain
			List<LanguageDomain> resultListDomain = new ArrayList<LanguageDomain>();
			
			resultListDomain = languageDAO.findByLanguageNameOrDescription(languageDomain.getLanguageName(), languageDomain.getLanguageDescription());
			
			for(LanguageDomain result : resultListDomain){
				
				//Check if languguesDomain size is not null
				if (languagesDomain.size() >= 1) {

					//Check duplicated Name
					if(oldName != null){
						
						if (!oldName.equalsIgnoreCase(languageDomain.getLanguageName())
								&& result.getLanguageName().equalsIgnoreCase(languageDomain.getLanguageName())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A língua \"" + languageDomain.getLanguageName() + "\" já existe.");
							
							break;
						}					
					} else{
						
						if (result.getLanguageName().equalsIgnoreCase(languageDomain.getLanguageName())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A língua \"" + languageDomain.getLanguageName() + "\" já existe.");
							
							break;
						}						
					}

					
					//Check duplicated Description
					if(oldDescription != null){
						
						if (!oldDescription.equalsIgnoreCase(languageDomain.getLanguageDescription()) 
								&& result.getLanguageDescription().equalsIgnoreCase(languageDomain.getLanguageDescription())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A descrição \"" + languageDomain.getLanguageDescription() + "\" já existe.");

							break;
						}
					} else{
						
						if (result.getLanguageDescription().equalsIgnoreCase(languageDomain.getLanguageDescription())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A descrição \"" + languageDomain.getLanguageDescription() + "\" já existe.");

							break;
						}						
					}
				}				
			}
						
			
			//Condition to save or not the information according to the LanguageName/LanguageDescription
			if(saveFlag){
				//Save Language with merge method
				//LanguageDAO languageDAO = new LanguageDAO();
				
				//Save Actual Date
				languageDomain.setSaveLanguageDate(new Date());
				
				//Set LoginUser
				languageDomain.setLanguageSaveLoginUser(loginController.getLoggedUser().getUserName());
				
				//Do Merge
				languageDAO.merge(languageDomain);				
				
				//Set old Name
				oldName = languageDomain.getLanguageName();
				
				//Set old Description
				oldDescription = languageDomain.getLanguageDescription();
				
				//Instantiate languagesDomain
				languagesDomain = new ArrayList<LanguageDomain>();
				
				//List again Language (very import to update the list)
				languagesDomain = languageDAO.descendList("languageName");
				
				//This code is uses OmniFaces and it looks more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso.");		
			} else{
								
				//Instantiate languagesDomain
				languagesDomain = new ArrayList<LanguageDomain>();
				
				//List again Language (very import to update the list)
				languagesDomain = languageDAO.ascendList("languageName");
			}

		} catch (Exception e) {
			//Error message
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações da língua.");
			e.printStackTrace();
		}		
	}
	
	/**
	 * Delete Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 04/02/2020
	 */
	public void doDelete(ActionEvent event){
		try {
			
			//Capture the event from the cursor in language.xhtml
			languageDomain = (LanguageDomain) event.getComponent().getAttributes()
					.get("selectedLanguageByCursor");

			//Delete Language
			LanguageDAO languageDAO = new LanguageDAO();
			languageDAO.delete(languageDomain);
			
			//Instantiate LanguesDomain
			languagesDomain = new ArrayList<LanguageDomain>();
			
			//List again Language (very import to update the list)
			languagesDomain = languageDAO.ascendList("languageName");
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(languageDomain.getLanguageName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.getCause().toString().contains("SQLIntegrityConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar \"" + languageDomain.getLanguageName() + "\" pois está sendo usado em outro processo.");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: \"" + languageDomain.getLanguageName() + "\"");
			}
			e.printStackTrace();			
		}
	}

	
	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 04/02/2020ss
	 */
	public void doEdit(ActionEvent event){
		try {			
			//Capture the event from the cursor in language.xhtml
			languageDomain = (LanguageDomain) event.getComponent().getAttributes()
					.get("selectedLanguageByCursor");
			
			//Set Old Name
			oldName = languageDomain.getLanguageName();
						
			//Set Old Description
			oldDescription = languageDomain.getLanguageDescription();			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: \"" + languageDomain.getLanguageName() + "\"");
			e.printStackTrace();			
		}		
	}
	

	
	/*
	 * Getters and Setters
	 */
		
	public LanguageDomain getLanguageDomain() {
		return languageDomain;
	}


	public void setLanguageDomain(LanguageDomain languageDomain) {
		this.languageDomain = languageDomain;
	}


	public List<LanguageDomain> getLanguagesDomain() {
		return languagesDomain;
	}


	public void setLanguagesDomain(List<LanguageDomain> languagesDomain) {
		this.languagesDomain = languagesDomain;
	}
	
}
