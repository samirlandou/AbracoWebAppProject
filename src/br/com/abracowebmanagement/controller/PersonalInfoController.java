package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.PersonalInfoDAO;
import br.com.abracowebmanagement.domain.PersonalInfoDomain;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PersonalInfoController implements Serializable {
	
	private PersonalInfoDomain personalInfoDomain;
	private List<PersonalInfoDomain> personalInfosDomain;

	

	
	/**
	 * List Method
	 * @return
	 */
	@PostConstruct
	public void doList(){
		try {
			//List personal Info
			PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
			personalInfosDomain= personalInfoDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Set here new instance of Domain.
	 */
	public void doNewRegister(){				
		personalInfoDomain = new PersonalInfoDomain();
	}
	

	/**
	 * Save Method
	 */
	public void doSave(){
		/* 
		//this code is used with PrimeFaces
		String messageText = "Programação Web com java";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);*/		
		
		try {
			//Save Personal Info
			PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
			personalInfoDAO.save(personalInfoDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again Personal Info (very import to update the list)
			personalInfosDomain= personalInfoDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa !!!");
			e.printStackTrace();
		}		
	}
	
	/**
	 * Delete Method
	 * @param event
	 */
	public void doDelete(ActionEvent event){
		try {
			
			//Capture the event from the cursor in personalInfo.xhtml
			personalInfoDomain = (PersonalInfoDomain) event.getComponent().getAttributes()
					.get("selectedPersonalInfoByCursor");

			//Delete Personal Info
			PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
			personalInfoDAO.delete(personalInfoDomain);
						
			//List again Personal Info (very import to update the list)
			personalInfosDomain= personalInfoDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(personalInfoDomain.getCompleteName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir as informações de: " + personalInfoDomain.getCompleteName());
			e.printStackTrace();			
		}
	}

	
	/*
	 * Getters and Setters
	 */
	
	public PersonalInfoDomain getPersonalInfoDomain() {
		return personalInfoDomain;
	}


	public void setPersonalInfoDomain(PersonalInfoDomain personalInfoDomain) {
		this.personalInfoDomain = personalInfoDomain;
	}


	public List<PersonalInfoDomain> getPersonalInfosDomain() {
		return personalInfosDomain;
	}


	public void setPersonalInfosDomain(List<PersonalInfoDomain> personalInfosDomain) {
		this.personalInfosDomain = personalInfosDomain;
	}

	

	
	
}
