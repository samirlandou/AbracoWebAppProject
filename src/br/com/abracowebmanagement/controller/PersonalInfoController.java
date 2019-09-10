package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.PersonalInfoDAO;
import br.com.abracowebmanagement.domain.PersonalInfoDomain;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PersonalInfoController implements Serializable {
	
	private PersonalInfoDomain personalInfoDomain;
	private List<PersonalInfoDomain> personalInfoListDomain;

	

	
	/**
	 * List Method
	 * @return
	 */
	@PostConstruct
	public void DoList(){
		try {
			//List personal Info
			PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
			personalInfoDAO.list();
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
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa !!!");
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


	public List<PersonalInfoDomain> getPersonalInfoListDomain() {
		return personalInfoListDomain;
	}


	public void setPersonalInfoListDomain(List<PersonalInfoDomain> personalInfoListDomain) {
		this.personalInfoListDomain = personalInfoListDomain;
	}
	
	
	
	
}
