package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import br.com.abracowebmanagement.dao.ContractModelDAO;
import br.com.abracowebmanagement.domain.contract.ContractModelDomain;

@ManagedBean
@ViewScoped
public class ContractModelController implements Serializable {
	
	private static final long serialVersionUID = -1051465978591927531L;
	
	private ContractModelDomain contractModelDomain;
	private List<ContractModelDomain> contractModelsDomain;
	ContractModelDomain resultDomain;
	
	private String textFromEditor;
	String oldDescription;
	
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
			//List Person
			ContractModelDAO contractModelDAO = new ContractModelDAO();
			contractModelsDomain= contractModelDAO.list();
						
			//Login
			FacesContext fc = FacesContext.getCurrentInstance();
			loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");
			
			//Set old Description
			oldDescription = "";
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações dos modelos de contrato !!!");
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
		
		//Reset textFromEditor value
		textFromEditor = new String();
		
		//Set Old Description
		oldDescription = getContractModelDomain().getContractModelName();
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
			
			//Set Save Flag
			 boolean saveFlag = true;
			 
			//Save Person with merge method
			ContractModelDAO contractModelDAO = new ContractModelDAO();
			
			//Instantiate Result Domain
			resultDomain = new ContractModelDomain();
			
			if (oldDescription != null && (resultDomain = contractModelDAO
					.findByContractModelName(contractModelDomain.getContractModelName())) != null) {

				if (oldDescription.equalsIgnoreCase(contractModelDomain.getContractModelName())) {

					// Set Save Flag
					saveFlag = false;

					// Error Message
					Messages.addGlobalError("O Modelo de Contrato \"" + contractModelDomain.getContractModelName() + "\" já existe.");

				}
			}

			//Condition to save or not the information according to the ContractModelName
			if(saveFlag){
				
				//get byte from String
				contractModelDomain.setContractModelDescription(textFromEditor.getBytes());
				
				//Save Actual Date
				contractModelDomain.setSaveContractModelDate(new Date());
				
				//Set LoginUser
				contractModelDomain.setContractModelSaveLoginUser(loginController.getLoggedUser().getUserName());
				
				//Do Merge
				contractModelDAO.merge(contractModelDomain);
				
				//Clean informations in the panelGrid
				//doNewRegister();
				
				//List again Person (very import to update the list)
				contractModelsDomain = contractModelDAO.list();
				
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");				
			}

		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações do modelo do contrato!");
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
			Messages.addGlobalInfo(contractModelDomain.getContractModelName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar pois os dados de " + contractModelDomain.getContractModelName() + " está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: " + contractModelDomain.getContractModelName());
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
			textFromEditor = new String(contractModelDomain.getContractModelDescription(), Charset.defaultCharset());
			
			//Set Old Description
			oldDescription = "";
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + contractModelDomain.getContractModelName());
			e.printStackTrace();			
		}		
	}
	
	
	/**
	 * Success Message
	 * @param successEvent
	 */
	public void successListener(final ClipboardSuccessEvent successEvent){
		Messages.addGlobalInfo("A Tag foi copiada com sucesso!");
	}
	
	
	/**
	 * Error Message
	 * @param errorEvent
	 */
	public void errorListener(final ClipboardErrorEvent errorEvent){
		Messages.addGlobalError("Erro ao copiar a Tag do contrato!");
	}


	public ContractModelDomain getContractModelDomain() {
		return contractModelDomain;
	}


	public void setContractModelDomain(ContractModelDomain contractModelDomain) {
		this.contractModelDomain = contractModelDomain;
	}


	public List<ContractModelDomain> getContractModelsDomain() {
		return contractModelsDomain;
	}


	public void setContractModelsDomain(List<ContractModelDomain> contractModelsDomain) {
		this.contractModelsDomain = contractModelsDomain;
	}


	public String getTextFromEditor() {
		return textFromEditor;
	}


	public void setTextFromEditor(String textFromEditor) {
		this.textFromEditor = textFromEditor;
	}

	
	/*
	 * Getters and Setters
	 */
	
	
}
