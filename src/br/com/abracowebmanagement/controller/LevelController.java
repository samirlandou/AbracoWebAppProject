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
import br.com.abracowebmanagement.dao.LevelDAO;
import br.com.abracowebmanagement.domain.LanguageDomain;
import br.com.abracowebmanagement.domain.LevelDomain;

@ManagedBean
@ViewScoped
public class LevelController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Domain
	private LevelDomain levelDomain;
	private List<LevelDomain> levelsDomain;
	private List<LanguageDomain> languagesDomain;
	LevelDomain resultDomain;
	
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
			//List Level
			LevelDAO levelDAO = new LevelDAO();
			levelsDomain = levelDAO.descendList("languageDomain");
						
			//Login
			FacesContext fc = FacesContext.getCurrentInstance();
			loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");
			
			//Clean Variables
			doClean();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações do nível.");
			e.printStackTrace();			
		}		
	}	
	

	public void doClean(){
		
		//Instantiate new Level
		levelDomain = new LevelDomain();
		
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
		
		//Instantiate new Level
		levelDomain = new LevelDomain();
		
		//get List of Languages
		LanguageDAO languageDAO = new LanguageDAO();		
		languagesDomain = languageDAO.descendList("languageDomain");
		
		//Set Old Description
		oldName = getLevelDomain().getLevelName();
			
		//Set Old Description
		oldDescription = getLevelDomain().getLevelDescription();		
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
			 
			//Save Level with merge method
			LevelDAO levelDAO = new LevelDAO();
			
			//Instantiate Result List Domain
			List<LevelDomain> resultListDomain = new ArrayList<LevelDomain>();
			
			resultListDomain = levelDAO.findByLevelNameOrDescription(levelDomain.getLevelName(), 
					levelDomain.getLevelDescription(), levelDomain.getLanguageDomain().getLanguageName());
			
			for(LevelDomain result : resultListDomain){
				
				//Check if levelsDomain size is not null
				if (levelsDomain.size() >= 1) {

					//Check duplicated Name
					if(oldName != null){
						
						if (!oldName.equalsIgnoreCase(levelDomain.getLevelName())
								&& result.getLevelName().equalsIgnoreCase(levelDomain.getLevelName())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("O nível \"" + levelDomain.getLevelName() + "\" já existe.");
							
							break;
						}						
					} else{
						
						if (result.getLevelName().equalsIgnoreCase(levelDomain.getLevelName())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("O nível \"" + levelDomain.getLevelName() + "\" já existe.");
							
							break;
						}
					}

					
					//Check duplicated Description
					if(oldDescription != null){
						
						if (!oldDescription.equalsIgnoreCase(levelDomain.getLevelDescription()) 
								&& result.getLevelDescription().equalsIgnoreCase(levelDomain.getLevelDescription())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A descrição \"" + levelDomain.getLevelDescription() + "\" já existe.");

							break;
						}						
					} else{
						
						if (result.getLevelDescription().equalsIgnoreCase(levelDomain.getLevelDescription())) {

							// Set Save Flag
							saveFlag = false;

							// Error Message
							Messages.addGlobalFatal("A descrição \"" + levelDomain.getLevelDescription() + "\" já existe.");

							break;
						}						
					}
				}		
			}
						
			
			//Condition to save or not the information according to the LevelName/LevelDescription
			if(saveFlag){
				//Save Level with merge method
				//LevelDAO levelDAO = new LevelDAO();
				
				//Save Actual Date
				levelDomain.setSaveLevelDate(new Date());
				
				//Set LoginUser
				levelDomain.setLevelSaveLoginUser(loginController.getLoggedUser().getUserName());
				
				//Do Merge
				levelDAO.merge(levelDomain);				
				
				//Set old Name
				oldName = levelDomain.getLevelName();
				
				//Set old Description
				oldDescription = levelDomain.getLevelDescription();
				
				//Instantiate levelsDomain
				levelsDomain = new ArrayList<LevelDomain>();
				
				//List again Level (very import to update the list)
				levelsDomain = levelDAO.descendList("languageDomain");
				
				//This code is uses OmniFaces and it looks more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso.");		
			} else{
								
				//Instantiate levelsDomain
				levelsDomain = new ArrayList<LevelDomain>();
				
				//List again Level (very import to update the list)
				levelsDomain = levelDAO.descendList("languageDomain");
			}

		} catch (Exception e) {
			//Error message
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações do nível.");
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
			
			//Capture the event from the cursor in level.xhtml
			levelDomain = (LevelDomain) event.getComponent().getAttributes()
					.get("selectedLevelByCursor");

			//Delete Level
			LevelDAO levelDAO = new LevelDAO();
			levelDAO.delete(levelDomain);
			
			//Instantiate LanguesDomain
			levelsDomain = new ArrayList<LevelDomain>();
			
			//List again Level (very import to update the list)
			levelsDomain = levelDAO.descendList("languageDomain");
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(levelDomain.getLevelName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.getCause().toString().contains("SQLIntegrityConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar \"" + levelDomain.getLevelName() + "\" pois está sendo usado em outro processo.");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: \"" + levelDomain.getLevelName() + "\"");
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
			//Capture the event from the cursor in level.xhtml
			levelDomain = (LevelDomain) event.getComponent().getAttributes()
					.get("selectedLevelByCursor");
			
			//get List of Languages
			LanguageDAO languageDAO = new LanguageDAO();
			languagesDomain = languageDAO.descendList("languageDomain");
			
			//Set Old Name
			oldName = levelDomain.getLevelName();
						
			//Set Old Description
			oldDescription = levelDomain.getLevelDescription();			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: \"" + levelDomain.getLevelName() + "\"");
			e.printStackTrace();			
		}		
	}
	

	
	/*
	 * Getters and Setters
	 */
		
	public LevelDomain getLevelDomain() {
		return levelDomain;
	}


	public void setLevelDomain(LevelDomain levelDomain) {
		this.levelDomain = levelDomain;
	}


	public List<LevelDomain> getLevelsDomain() {
		return levelsDomain;
	}


	public void setLevelsDomain(List<LevelDomain> levelsDomain) {
		this.levelsDomain = levelsDomain;
	}


	public List<LanguageDomain> getLanguagesDomain() {
		return languagesDomain;
	}


	public void setLanguagesDomain(List<LanguageDomain> languagesDomain) {
		this.languagesDomain = languagesDomain;
	}
	
}
