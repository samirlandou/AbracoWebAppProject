package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.abracowebmanagement.dao.ProfileDAO;
import br.com.abracowebmanagement.domain.ProfileDomain;

@ManagedBean
@ViewScoped
public class ProfileController implements Serializable{
	
	private static final long serialVersionUID = 7798649148673134784L;
	
	private ProfileDomain profileDomain;
	private List<ProfileDomain> profilesDomain;
	

	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 16/09/2019
	 */
	@PostConstruct
	public void doList(){
		try {
			//List Person
			ProfileDAO profileDAO = new ProfileDAO();
			profilesDomain= profileDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}
	
	
	/**
	 * Method for New Registration. <br/>
	 * Set here new instance of Domain. <br/>
	 * @author samirlandou <br/>
	 * @since 16/09/2019
	 */
	public void doNewRegister(){		
		//Instantiate new Profile
		profileDomain = new ProfileDomain();
	}
	


	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 16/09/2019
	 */
	public void doSave(){

		try {
			//Save Person with merge method
			ProfileDAO profileDAO = new ProfileDAO();
			profileDAO.merge(profileDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again Person (very import to update the list)
			profilesDomain= profileDAO.list();
			
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
			
			//Capture the event from the cursor in profile.xhtml
			profileDomain = (ProfileDomain) event.getComponent().getAttributes()
					.get("selectedProfileByCursor");

			//Delete Person
			ProfileDAO profileDAO = new ProfileDAO();
			profileDAO.delete(profileDomain);
						
			//List again Person (very import to update the list)
			profilesDomain= profileDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Esse perfil foi excluido com sucesso!!!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir as informações desse perfil.");
			e.printStackTrace();			
		}
	}	
	

	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 11/09/2019
	 */
	public void doEdit(ActionEvent event){
		try {			
			//Capture the event from the cursor in profile.xhtml
			profileDomain = (ProfileDomain) event.getComponent().getAttributes()
					.get("selectedProfileByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações desse perfil.");
			e.printStackTrace();			
		}		
	}
	
    public void addMessage() {
    	
        String summary =  profileDomain.getStatus()? "Desativado(a)" : "Ativo(a)";
        Messages.addGlobalError(summary);
    }
	
	
	/*
	 * Getters and Setters
	 */
	
	public ProfileDomain getProfileDomain() {
		return profileDomain;
	}
	public void setProfileDomain(ProfileDomain profileDomain) {
		this.profileDomain = profileDomain;
	}
	public List<ProfileDomain> getProfilesDomain() {
		return profilesDomain;
	}
	public void setProfilesDomain(List<ProfileDomain> profilesDomain) {
		this.profilesDomain = profilesDomain;
	}

}
