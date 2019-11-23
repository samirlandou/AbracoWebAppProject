package br.com.abracowebmanagement.controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.FileUtil;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {
	
	private static final long serialVersionUID = 6033236523831734740L;
	
	private UserDomain userDomain;
	private List<UserDomain> usersDomain;
	private List<PersonDomain> personsDomain;

	
	private StreamedContent sc;
	private List <StreamedContent> userImages;
	public FileUtil fileUtil;
	private DefaultStreamedContent myImage;
	public List<Path> tempFiles = new ArrayList<Path>();
	
	private String destinationUserImageFile;
	Path tempFile;
	boolean userNameFlag;
	boolean completeNameFlag;
	boolean upload = false;

	
	
	
	public UserController(){
		
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
			usersDomain= userDAO.descendList("id");
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
		
		//Set upload image to false
		upload = false;
		
		try {
			//Instantiate new User
			userDomain = new UserDomain();
			
			//Instantiate List of person
			PersonDAO personDAO = new PersonDAO();
			personsDomain = personDAO.list();
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
			
			
			//Cryptography with MD5 HEX
			SimpleHash hash = new SimpleHash("MD5", userDomain.getPasswordWithoutCryptography());
			
			//Set cryptography in the real password
			userDomain.setPassword(hash.toHex());
			
			//UserDomain result =  userDAO.merge(userDomain);
			
			//verify if already exists userName
			UserDomain result = new UserDomain();
			UserDomain result2 = new UserDomain();
			
			if(usersDomain.size() >= 1){
				result = userDAO.FindByUserName(userDomain.getUserName());
				result2 = userDAO.FindByCompleteName(userDomain.getPersonDomain().getCompleteName());
				
				//Condition to save or not the information according to the userName
				if(result == null){
					userNameFlag = true;
				} else{
					Messages.addGlobalError("o nome '" + userDomain.getUserName() + "' já existe. Favor escolher outro nome.");										
					userNameFlag = false;					
				}

				
				//Condition to save or not the information according to the completeName
				if(result2 == null){
					completeNameFlag = true;
				} else{
					Messages.addGlobalError("Essa pessoa já foi cadastrada. Favor escolher outra pessoa.");
					completeNameFlag = false;				
				}				
			} else{
				userNameFlag = true;
				completeNameFlag = true;
			}
			
			
			//Condition to save or not the information according to the userName
			if(userNameFlag && completeNameFlag){
				result = userDAO.merge(userDomain);
				
				//verify if there was an upload file.
				if(upload){
					
					//Get origin file
					Path originFile = Paths.get(userDomain.getImageUserPath());
					
					//Get destination file
					Path destinationFile = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/"
														+ result.getId()
														+ "."
														+ FilenameUtils.getExtension(userDomain.getImageUserFileName()));
					
					//Copy origin File do the destination path
					Files.copy(originFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
					
					//Copy destination path
					destinationUserImageFile = destinationFile.toString();
					
					
					//Delete all temporary files
					doDeleteTempfiles();				
				}
				
				//Clean informations in the panelGrid
				doNewRegister();
				
				//List again User (very import to update the list)
				usersDomain = userDAO.list();
				
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");
				
				//Set upload image to false
				upload = false;
			}
				
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa!");
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
			
			//Get user image file
			Path userFile = null;
			userFile = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/" 
										+ userDomain.getId()
										+ "."
										+ FilenameUtils.getExtension(userDomain.getImageUserFileName()));
			
			//Gelete user image file
			Files.deleteIfExists(userFile);
			
			//List again User (very import to update the list)
			usersDomain= userDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(userDomain.getUserName() + " foi excluido com sucesso!!!");
			
			//Set upload image to false
			upload = false;
			
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
			
			
			if(userDomain.getImageUserPath() != null){
				if(!userDomain.getImageUserFileName().isEmpty()){
					userDomain.setImageUserPath("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/" 
							+ userDomain.getId()
							+ "."
							+ FilenameUtils.getExtension(userDomain.getImageUserFileName()));				
				}				
			}


			//Instantiate List of person
			PersonDAO personDAO = new PersonDAO();
			personsDomain = personDAO.list();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma pessoas");
			e.printStackTrace();
		}
	}

	
	/**
	 * Upload File in temp windows directory .<br/>
	 * @since 23/09/2019
	 * @author samirlandou
	 * @param event
	 */
    public void handleFileUpload(FileUploadEvent event) {
    	//userDomain.setImageUser(event.getFile().getContents());
    	   	
    	try {
    		//original file
    		UploadedFile uploadFile  = event.getFile();
    		
    		//get the original file name
    		userDomain.setImageUserFileName(uploadFile.getFileName().toString());
    		
    		//Destination file in Temporary Directory (C:\Users\Samir Landou\AppData\Local\Temp)
			tempFile = Files.createTempFile(null, null);
			
			//Add temporary file in a list
			tempFiles.add(tempFile);
			
			//Copy original file into destination file
			Files.copy(uploadFile.getInputstream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
			
			//Get path through @Transcient
			userDomain.setImageUserPath(tempFile.toString());
			
			Messages.addGlobalInfo("Upload realizado com sucesso!");
			
			//Set upload to true while tempFiles is not empty
			if(!tempFiles.isEmpty()){
				upload = true;
			}
						
			System.out.println("Temporary File: " + userDomain.getImageUserPath());
		} catch (IOException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar realizar o upload do arquivo.");
			e.printStackTrace();
		} 	
    	
    }
    
    /**
     * Delete temporary files
     */
    public void doDeleteTempfiles(){
    	
    	if(upload){
	    	for(Path tempFile: tempFiles){
	    		try {
					Files.deleteIfExists(tempFile);
				} catch (IOException e) {
					Messages.addGlobalError("Ocorreu um erro ao tentar deletar os arquivos temporários.");
					e.printStackTrace();
				}
	    	}    		
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


	public StreamedContent getSc() {
		return sc;
	}


	public void setSc(StreamedContent sc) {
		this.sc = sc;
	}


	public List<StreamedContent> getUserImages() {
		return userImages;
	}


	public void setUserImages(List<StreamedContent> userImages) {
		this.userImages = userImages;
	}


	public DefaultStreamedContent getMyImage() {
		return myImage;
	}


	public void setMyImage(DefaultStreamedContent myImage) {
		this.myImage = myImage;
	}


	public String getDestinationUserImageFile() {
		return destinationUserImageFile;
	}


	public void setDestinationUserImageFile(String destinationUserImageFile) {
		this.destinationUserImageFile = destinationUserImageFile;
	}

/*
	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}	*/
	
}
