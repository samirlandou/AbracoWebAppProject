package br.com.abracowebmanagement.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {
	
	private static final long serialVersionUID = 6033236523831734740L;
	
	private UserDomain userDomain;
	private List<UserDomain> usersDomain;
	private List<PersonDomain> personsDomain;
	

	
	/**
	 * Transform the image to an array bytes to save it in the data base.<br/>
	 * @since 23/09/2019
	 * @author samirlandou
	 * @param event
	 */
    public void handleFileUpload(FileUploadEvent event) {
    	userDomain.setImageUser(event.getFile().getContents());  	 
    }
	
    
    /**
     * Take the array bytes an converts it into an real image.
     * @author samirlandou <br/>
     * @since 23/09/2019 <br/>
     * @return
     * @throws IOException
     */
    public StreamedContent imageViewer() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(userDomain.getImageUser()));
        }
    }
	
	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 11/09/2019
	 */
	@PostConstruct
	public void doList(){
		try {
			//List User
			UserDAO userDAO = new UserDAO();
			usersDomain= userDAO.list();
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
		
		try {
			//Instantiate new User
			userDomain = new UserDomain();
			
			//Instantiate List of person
			PersonDAO personDAO = new PersonDAO();
			personDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro tentar gerar a lista de pessoas");
			e.printStackTrace();
		}
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
			//Save User with merge method
			UserDAO userDAO = new UserDAO();
			userDAO.merge(userDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again User (very import to update the list)
			usersDomain= userDAO.list();
			
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
			
			//Capture the event from the cursor in user.xhtml
			userDomain = (UserDomain) event.getComponent().getAttributes()
					.get("selectedUserByCursor");

			//Delete User
			UserDAO userDAO = new UserDAO();
			userDAO.delete(userDomain);
						
			//List again User (very import to update the list)
			usersDomain= userDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(userDomain.getUserName() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir as informações de: " + userDomain.getUserName());
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
			//Capture the event from the cursor in user.xhtml
			userDomain = (UserDomain) event.getComponent().getAttributes()
					.get("selectedUserByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + userDomain.getUserName());
			e.printStackTrace();			
		}		
	}

	
	/*
	 * Getters and Setters
	 */
	
	public UserDomain getUserDomain() {
		return userDomain;
	}


	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}


	public List<UserDomain> getUsersDomain() {
		return usersDomain;
	}


	public void setUsersDomain(List<UserDomain> usersDomain) {
		this.usersDomain = usersDomain;
	}


	public List<PersonDomain> getPersonsDomain() {
		return personsDomain;
	}


	public void setPersonsDomain(List<PersonDomain> personsDomain) {
		this.personsDomain = personsDomain;
	}

/*
	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}	*/
	
}
