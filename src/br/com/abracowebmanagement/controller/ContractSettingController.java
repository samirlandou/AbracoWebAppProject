package br.com.abracowebmanagement.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.ContractSettingDAO;
import br.com.abracowebmanagement.domain.ContractSettingDomain;
import br.com.abracowebmanagement.domain.UserDomain;

@ManagedBean
@ViewScoped
public class ContractSettingController {
	
	//Domain
	UserDomain userDomain;
	private ContractSettingDomain contractSettingDomain;
	
	//Controller
	public LoginController loginController;
	
	
	@PostConstruct
	public void doInit() {
		
		//Instantiate UserDomain
		userDomain = new UserDomain();
		
		//FacesContext for Login
		FacesContext fcLogin = FacesContext.getCurrentInstance();
				
		//Get External Context from LoginController
		loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");

		//Contract Setting Controller
		ContractSettingDAO contractSettingDAO = new ContractSettingDAO();
		contractSettingDomain = new ContractSettingDomain();
		
		//Get first line of Contract Setting Domain
		contractSettingDomain = contractSettingDAO.search((long) 1);

	}

	
	public void doSave(){
		
		try {
			//Save Person with merge method
			ContractSettingDAO contractSettingDAO = new ContractSettingDAO();
			
			//Save Actual Date
			contractSettingDomain.setSaveContractSettingDate(new Date());
			
			//Set LoginUser
			contractSettingDomain.setContractSettingSaveLoginUser(loginController.getLoggedUser().getUserName());
			
			//Do Merge
			contractSettingDAO.merge(contractSettingDomain);
			
			//Inform messages for saving successful
			Messages.addGlobalInfo("'Parâmetro do Contrato' foi salvo com sucesso!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


	public ContractSettingDomain getContractSettingDomain() {
		return contractSettingDomain;
	}


	public void setContractSettingDomain(ContractSettingDomain contractSettingDomain) {
		this.contractSettingDomain = contractSettingDomain;
	}
	

}
