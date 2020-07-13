package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.util.MethodUtil;
import br.com.abracowebmanagement.util.PersonUtil;

/**
 * Person Class
 * @author samirlandou
 * @since 11/09/2019
 */

@ManagedBean
@ViewScoped
public class PersonController implements Serializable {
	
	private static final long serialVersionUID = 4006387744926175284L;
	
	private PersonDomain personDomain;
	private PersonDomain oldPersonDomain;
	private List<PersonDomain> personsDomain;
	private Map<String, String>	country;
	
	public PersonUtil persontUtil = new PersonUtil();
	public MethodUtil methodUtil =  new MethodUtil();
	
	boolean editFlag;
	String completeName, cpf, email, telephone;

	PersonDomain resultDomain;

	private boolean itemDisabledFlag;

	
	/**
	 * List Method
	 */
	@PostConstruct
	public void doList(){
		try {
			//List Person
			PersonDAO personDAO = new PersonDAO();
			personsDomain = personDAO.descendList("id");
			
			//List Country
			country = persontUtil.getFullCountryComboList();
			
			//Clean
			doClean();
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Clean Method.
	 */
	public void doClean(){
		//Set Edit Flag
		editFlag = false;
		
		//Set ItemDisabledFlag Flag
		itemDisabledFlag = true;
		
		//Set Check Field Variables to Null
		completeName = "";
		cpf = "";
		email = "";
		telephone = "";
	}
	
	
	/**
	 * Validate a CPF
	 */
	public void doValidateCPF(){
		
		if(!methodUtil.validateCPF(personDomain.getCpf())){
			
			//Error Message
			Messages.addGlobalError("O CPF informado é invalido!");
		}
	}
	
	
	/**
	 * Validate Email
	 */
	public void doValidateEmail(){
				
		if(!methodUtil.validateEmail(personDomain.getEmail())){
			
			//Error Message
			Messages.addGlobalError("O Email informado é invalido!");
		}	
	}
	
	
	/*public void doValidateTelephone(){
		
		if(personDomain.getTelephone().matches("[^0-9]")){
			
			//Error Message
			Messages.addGlobalError("O Telefone informado não é valido!");
		}
	}*/

	
	/**
	 * Method for New Registration. <br/>
	 * Set here new instance of Domain. <br/>
	 */
	public void doNewRegister(){
		
		//Instantiate new Person Domain
		personDomain = new PersonDomain();
		
		//Clean Variables
		doClean();
	}
		
	
	/**
	 * Save Method.
	 */
	public void doSave(){
		/* 
		//This code is used with PrimeFaces
		String messageText = "Programação Web com java";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);*/		
		
		try {
			
			//Save User with merge method
			PersonDAO personDAO = new PersonDAO();

			//Set Save Flag
			 boolean saveFlag = true;
			 
			 
			//Validate the CPF before Saving
			/*if(!methodUtil.validateCPF(personDomain.getCpf())){
				
				//Set Save Flag
				saveFlag = false;
				
				//Error Message
				Messages.addGlobalError("O CPF informado é invalido!");
			}*/
			
			
			//Validate the Email before Saving
			if(!methodUtil.validateEmail(personDomain.getEmail())){
				
				//Set Save Flag
				saveFlag = false;
				
				//Error Message
				Messages.addGlobalError("O Email informado é invalido!");
			}
			
			
			//Search duplicate person
			if(!editFlag || (editFlag && personsDomain.size() >= 1  && !checkField().equals(""))){
				
				//Instantiate Result Domain
				resultDomain = new PersonDomain();
				
				if(checkField().equals("completeName") && (resultDomain = personDAO.findByCompleteName(personDomain.getCompleteName())) != null){
					
					if(completeName.equalsIgnoreCase(personDomain.getCompleteName())){
						
						//Set Save Flag
						saveFlag = false;
						
						//Error Message
						Messages.addGlobalError("O Nome completo \"" + personDomain.getCompleteName() + "\" já existe.");						
					}

					
				} else if(checkField().equals("cpf") && (resultDomain = personDAO.findByCPF(personDomain.getCpf())) != null){
					
					//Set Save Flag
					saveFlag = false;
					
					//Error Message
					Messages.addGlobalError("O CPF \"" + personDomain.getCpf() + "\" já pertence a \"" + resultDomain.getCompleteName() + "\".");
					
				} else if(checkField().equals("telephone") && (resultDomain = personDAO.findByTelephone(personDomain.getTelephone())) != null){
					
					//Set Save Flag
					saveFlag = false;
					
					//Error Message
					Messages.addGlobalError("O Telefone \"" + personDomain.getTelephone() + "\" já pertence a \"" + resultDomain.getCompleteName() + "\".");
					
				} else if(checkField().equals("email") && (resultDomain = personDAO.findByEmail(personDomain.getEmail())) != null){
					
					//Set Save Flag
					saveFlag = false;
					
					//Error Message
					Messages.addGlobalError("O E-mail \"" + personDomain.getEmail() + "\" já pertence a \"" + resultDomain.getCompleteName() + "\".");
					
				}		
			}
			
			if(saveFlag){
				
				//Save Person with merge method
				personDAO.merge(personDomain);
					
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");				

				//Clean informations in the panelGrid
				//doNewRegister();
				
				//Set again variables
				completeName = personDomain.getCompleteName();
				cpf = personDomain.getCpf();
				email = personDomain.getEmail();
				telephone = personDomain.getTelephone();
				
				//Instantiate personsDomain
				personsDomain = new ArrayList<PersonDomain>();
				
				//List again Person (very import to update the list)
				personsDomain = personDAO.descendList("id");
				
			}else{
				
				//Instantiate personsDomain
				personsDomain = new ArrayList<PersonDomain>();
				
				//List again Person (very import to update the list)
				personsDomain = personDAO.descendList("id");
			}
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa!");
			e.printStackTrace();
		}		
	}


	/**
	 * check field Method
	 */
	public String checkField(){
		
		if(!completeName.equals(personDomain.getCompleteName())){
			return "completeName";
		} else if(!cpf.equals(personDomain.getCpf())){
			return "cpf";
		} else if(!email.equals(personDomain.getEmail())){
			return "email";
		} else if(!telephone.equals(personDomain.getTelephone())){
			return "telephone";
		} else{
			return "";
		}
	}
	
	
	/**
	 * Delete Method
	 * @param event
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
			personsDomain = personDAO.descendList("id");
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(personDomain.getCompleteName() + " foi excluido com sucesso!!!");
			
		} catch (Exception e) {
			/*ConstraintViolationException*/
			if(e.getCause().toString().contains("SQLIntegrityConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar \"" + personDomain.getCompleteName() + "\" pois está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: \"" + personDomain.getCompleteName() + "\"");
			}
			e.printStackTrace();
		}
	}

	
	/**
	 * Message Method
	 */
	public void addMessage() {
		//Add Message for toggleSwitch Component from person.xhtml
		String summary = personDomain.getStatus() ? "Ativo" : "Inativo";
		Messages.addGlobalInfo(summary);
	}

	
	/**
	 * Edit Method.
	 * @param event
	 */
	public void doEdit(ActionEvent event){
		try {
			
			//Clean Variables
			doClean();
			
			//Set edit flag
			editFlag = true;
			
			//Capture the event from the cursor
			personDomain = (PersonDomain) event.getComponent().getAttributes()
					.get("selectedPersonByCursor");
			
			//Set some old Person Domain Values.
			completeName = personDomain.getCompleteName();
			cpf = personDomain.getCpf();
			email = personDomain.getEmail();
			telephone = personDomain.getTelephone();
			
			//Condition do Set itemDisabled Flag to true for selectItem ADMINISTRADOR(A)
			if(personDomain.getProfile().equals("ADMINISTRADOR(A)")){
				itemDisabledFlag = false;
			}
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: \"" + personDomain.getCompleteName() + "\"");
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


	public Map<String, String> getCountry() {
		return country;
	}


	public void setCountry(Map<String, String> country) {
		this.country = country;
	}


	public PersonDomain getOldPersonDomain() {
		return oldPersonDomain;
	}


	public void setOldPersonDomain(PersonDomain oldPersonDomain) {
		this.oldPersonDomain = oldPersonDomain;
	}


	public boolean isItemDisabledFlag() {
		return itemDisabledFlag;
	}


	public void setItemDisabledFlag(boolean itemDisabledFlag) {
		this.itemDisabledFlag = itemDisabledFlag;
	}	
	
}
