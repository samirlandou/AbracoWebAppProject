package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.ContractModelDAO;
import br.com.abracowebmanagement.domain.ContractModelDomain;

@ManagedBean
@ViewScoped
public class TestController implements Serializable {
	
	private static final long serialVersionUID = -1051465978591927531L;
	
	private ContractModelDomain contractModelDomain;
	private List<ContractModelDomain> contractModelsDomain;
	

	
	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	@PostConstruct
	public void doList(){
		try {
			//List Person
			ContractModelDAO contractModelDAO = new ContractModelDAO();
			contractModelsDomain= contractModelDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Method for New Registration. <br/>
	 * Set here new instance of Domain. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	public void doNewRegister(){		
		//Instantiate new Person
		contractModelDomain = new ContractModelDomain();	
	}
	

	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 04/02/2020
	 */
	public void doSave(){
		/* 
		//This code is used with PrimeFaces
		String messageText = "Programação Web com java";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);*/		
		
		try {
			//Save Person with merge method
			ContractModelDAO contractModelDAO = new ContractModelDAO();
			contractModelDAO.merge(contractModelDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again Person (very import to update the list)
			contractModelsDomain = contractModelDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa!");
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
			
			//Capture the event from the cursor in person.xhtml
			contractModelDomain = (ContractModelDomain) event.getComponent().getAttributes()
					.get("selectedPersonByCursor");

			//Delete Person
			ContractModelDAO contractModelDAO = new ContractModelDAO();
			contractModelDAO.delete(contractModelDomain);
						
			//List again Person (very import to update the list)
			contractModelsDomain= contractModelDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(contractModelDomain + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar pois os dados de " + contractModelDomain + " está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: " + contractModelDomain);
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
			//Capture the event from the cursor in person.xhtml
			contractModelDomain = (ContractModelDomain) event.getComponent().getAttributes()
					.get("selectedPersonByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + contractModelDomain);
			e.printStackTrace();			
		}		
	}

	
	/*
	 * Getters and Setters
	 */
	
	public ContractModelDomain getContractModelDomain() {
		return contractModelDomain;
	}


	public void setContractModelDomain(ContractModelDomain contractModelDomain) {
		this.contractModelDomain = contractModelDomain;
	}


	public List<ContractModelDomain> getPersonsDomain() {
		return contractModelsDomain;
	}


	public void setPersonsDomain(List<ContractModelDomain> contractModelsDomain) {
		this.contractModelsDomain = contractModelsDomain;
	}	
	
}
