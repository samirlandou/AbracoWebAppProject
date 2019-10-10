package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.domain.PersonDomain;

@ManagedBean
@ViewScoped
public class PersonController implements Serializable {
	
	private static final long serialVersionUID = 4006387744926175284L;
	
	private PersonDomain personDomain;
	private List<PersonDomain> personsDomain;
	

	
	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 11/09/2019
	 */
	@PostConstruct
	public void doList(){
		try {
			//List Person
			PersonDAO personDAO = new PersonDAO();
			personsDomain= personDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Method for New Registration. <br/>
	 * Set here new instance of Domain. <br/>
	 * @author samirlandou <br/>
	 * @since 11/09/2019
	 */
	public void doNewRegister(){		
		//Instantiate new Person
		personDomain = new PersonDomain();	
	}
	

	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 11/09/2019
	 */
	public void doSave(){
		/* 
		//this code is used with PrimeFaces
		String messageText = "Programação Web com java";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);*/		
		
		try {
			//Save Person with merge method
			PersonDAO personDAO = new PersonDAO();
			personDAO.merge(personDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again Person (very import to update the list)
			personsDomain= personDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa !!!");
			e.printStackTrace();
		}		
	}
	
	/**
	 * Delete Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 11/09/2019
	 */
	public void doDelete(ActionEvent event){
		try {
			
			//Capture the event from the cursor in person.xhtml
			personDomain = (PersonDomain) event.getComponent().getAttributes()
					.get("selectedPersonByCursor");

			//Delete Person
			PersonDAO personDAO = new PersonDAO();
			personDAO.delete(personDomain);
						
			//List again Person (very import to update the list)
			personsDomain= personDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(personDomain.getCompleteName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir as informações de: " + personDomain.getCompleteName());
			e.printStackTrace();			
		}
	}

	
	public void addMessage() {

		String summary = personDomain.getStatus() ? "Ativo(a)" : "Desativado(a)";
		Messages.addGlobalError(summary);
	}
	
	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 11/09/2019
	 */
	public void doEdit(ActionEvent event){
		try {			
			//Capture the event from the cursor in person.xhtml
			personDomain = (PersonDomain) event.getComponent().getAttributes()
					.get("selectedPersonByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + personDomain.getCompleteName());
			e.printStackTrace();			
		}		
	}

	
	/*
	 * Getters and Setters
	 */
	
	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}


	public List<PersonDomain> getPersonsDomain() {
		return personsDomain;
	}


	public void setPersonsDomain(List<PersonDomain> personsDomain) {
		this.personsDomain = personsDomain;
	}	
	
}
