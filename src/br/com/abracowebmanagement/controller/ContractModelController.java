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
import br.com.abracowebmanagement.domain.ContractModelDomain;

@ManagedBean
@ViewScoped
public class ContractModelController implements Serializable {
	
	private static final long serialVersionUID = -1051465978591927531L;
	
	private ContractModelDomain ContractModelDomain;
	private List<ContractModelDomain> ContractModelsDomain;
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
			//List contract Model
			ContractModelDAO ContractModelDAO = new ContractModelDAO();
			ContractModelsDomain= ContractModelDAO.list();
						
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
		//Instantiate new contract Model
		ContractModelDomain = new ContractModelDomain();
		
		//Reset textFromEditor value
		textFromEditor = new String();
		
		//Set Old Description
		oldDescription = "";
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
			 
			//Save contract Model with merge method
			ContractModelDAO ContractModelDAO = new ContractModelDAO();
			
			//Instantiate Result Domain
			resultDomain = new ContractModelDomain();
			
			if (oldDescription != null && (resultDomain = ContractModelDAO
					.findByContractModelName(ContractModelDomain.getContractModelName())) != null) {

				if (oldDescription.equalsIgnoreCase(ContractModelDomain.getContractModelName())) {

					// Set Save Flag
					saveFlag = false;

					// Error Message
					Messages.addGlobalError("O Modelo de Contrato \"" + ContractModelDomain.getContractModelName() + "\" já existe.");

				}
			}

			//Condition to save or not the information according to the ContractModelName
			if(saveFlag){
				
				//get byte from String
				ContractModelDomain.setContractModelDescription(textFromEditor.getBytes());
				
				//Save Actual Date
				ContractModelDomain.setSaveContractModelDate(new Date());
				
				//Set LoginUser
				ContractModelDomain.setContractModelSaveLoginUser(loginController.getLoggedUser().getUserName());
				
				//Do Merge
				ContractModelDAO.merge(ContractModelDomain);
				
				//Clean informations in the panelGrid
				//doNewRegister();
				
				//List again contract Model (very import to update the list)
				ContractModelsDomain = ContractModelDAO.list();
				
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
			
			//Capture the event from the cursor in contractModel.xhtml
			ContractModelDomain = (ContractModelDomain) event.getComponent().getAttributes()
					.get("selectedContractModelByCursor");

			//Delete contract Model
			ContractModelDAO ContractModelDAO = new ContractModelDAO();
			ContractModelDAO.delete(ContractModelDomain);
						
			//List again contract Model (very import to update the list)
			ContractModelsDomain = ContractModelDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(ContractModelDomain.getContractModelName() + " foi excluido com sucesso.");
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar \"" + ContractModelDomain.getContractModelName() + "\" pois está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: \"" + ContractModelDomain.getContractModelName() + "\"");
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
			//Capture the event from the cursor in contractModel.xhtml
			ContractModelDomain = (ContractModelDomain) event.getComponent().getAttributes()
					.get("selectedContractModelByCursor");
			textFromEditor = new String(ContractModelDomain.getContractModelDescription(), Charset.defaultCharset());
			
			//Set Old Description
			oldDescription = ContractModelDomain.getContractModelName();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: \"" + ContractModelDomain.getContractModelName() + "\"");
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


	
	/*
	 * Getters and Setters
	 */
	
	public ContractModelDomain getContractModelDomain() {
		return ContractModelDomain;
	}


	public void setContractModelDomain(ContractModelDomain ContractModelDomain) {
		this.ContractModelDomain = ContractModelDomain;
	}


	public List<ContractModelDomain> getContractModelsDomain() {
		return ContractModelsDomain;
	}


	public void setContractModelsDomain(List<ContractModelDomain> ContractModelsDomain) {
		this.ContractModelsDomain = ContractModelsDomain;
	}


	public String getTextFromEditor() {
		return textFromEditor;
	}


	public void setTextFromEditor(String textFromEditor) {
		this.textFromEditor = textFromEditor;
	}
	
	
}
