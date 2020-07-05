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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.tomcat.util.codec.binary.Base64;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.FileUtil;
import br.com.abracowebmanagement.util.MethodUtil;
import br.com.abracowebmanagement.util.PersonUtil;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {
	
	private static final long serialVersionUID = 6033236523831734740L;
	
	private UserDomain userDomain;
	private List<UserDomain> usersDomain;
	private List<PersonDomain> personsDomain;

	public PersonUtil persontUtil = new PersonUtil();
	public MethodUtil methodUtil =  new MethodUtil();
	
	private List <StreamedContent> userImages;
	public FileUtil fileUtil;
	private DefaultStreamedContent myImage;
	public List<Path> tempFiles = new ArrayList<Path>();
	
	private String destinationUserImageFile;
	Path tempFile;
	boolean userNameFlag;
	boolean completeNameFlag;
	boolean upload;
	boolean editFlag;
	boolean disableDeleteImageButton;
	
	String userName, cnpj;
	
	UserDomain resultDomain;
	
	private UploadedFile File;
	
	private String oldPassword;
		
	//Login Controller
	LoginController loginController = new LoginController();

	
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
			
			//Clean Variables
			doClean();
		      
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}
	

	
	public void doClean(){
		
		//Set Field
		userName = "";
		cnpj = "";
		
		//Set Flag
		userNameFlag = false;
		completeNameFlag = false;
		upload = false;
		editFlag = false;
		disableDeleteImageButton = true;
		
	}
	

	/**
	 * Validate a CNPJ
	 */
	public void doValidateCNPJ(){
		
		if(!methodUtil.validateCNPJ(userDomain.getCnpj())){
			
			//Error Message
			Messages.addGlobalError("O CNPJ informado é invalido!");
		}
	}

	
	public String doDecodePassword(String password){
		
		
		return password;
		
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
			
			//List of Users name
			List<String> users = new ArrayList<>();
			
			//Get all User's Name
			for(UserDomain user : usersDomain){				
				users.add(user.getPersonDomain().getCompleteName());
			}
			
			//Instantiate List of person
			PersonDAO personDAO = new PersonDAO();
			personsDomain = personDAO.findByActiveUserNotRegistered(users);
			
			//Set Old Password
			oldPassword = "";
			
			//Clean Variables
			doClean();
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro tentar gerar a lista de pessoas");
			e.printStackTrace();
		}
	}	


	/**
	 * check field Method. <br/>
	 * @author samirlandou <br/>
	 * @since 27/04/2020
	 * @return
	 */
	public String checkField(){
		
		if(!userName.equals(userDomain.getUserName())){
			return "userName";
		} else if(!cnpj.equals(userDomain.getCnpj())){
			return "cnpj";
		} else{
			return "";
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
			
			//Set Save Flag
			 boolean saveFlag = true;
			
			//Save User with merge method
			UserDAO userDAO = new UserDAO();
			
			//Cryptography with MD5 HEX
			//SimpleHash hash = new SimpleHash("MD5", userDomain.getPasswordWithoutCryptography());
			
			//Set cryptography in the real firstPassword
			//userDomain.setPassword(hash.toHex());
			
			//UserDomain result =  userDAO.merge(userDomain);
			 
			//Validate the CNPJ before Saving
			/*if(!methodUtil.validateCNPJ(userDomain.getCnpj())){
				
				//Set Save Flag
				saveFlag = false;
				
				//Error Message
				Messages.addGlobalError("O CNPJ informado é invalido!");
			}*/
			
			//verify if already exists userName
			/*UserDomain result = new UserDomain();
			UserDomain result2 = new UserDomain();
			
			if(usersDomain.size() >= 1){
				result = userDAO.findByUserName(userDomain.getUserName());
				result2 = userDAO.findByCNPJ(userDomain.getCnpj());
				
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
			}*/
			
			//Set cryptography in the real firstPassword				
			if(userDomain.getPasswordWithoutCryptography() != null && oldPassword != null
					&& userDomain.getPasswordWithoutCryptography().equals(oldPassword)){
				
				//Encrypt password
				userDomain.setPassword(new String(Base64.encodeBase64(userDomain.getPasswordWithoutCryptography().getBytes())));					
			} else{
				
				//Set Save Flag to False
				saveFlag = false;
				
				//Password Error Message.
				Messages.addGlobalError("Senha não conforme. Favor verificar de novo!");
			}				
			
			//Search duplicate person
			if(!editFlag || (editFlag && usersDomain.size() >= 1  && !checkField().equals(""))){
				
				//Instantiate Result Domain
				resultDomain = new UserDomain();
				
				if(checkField().equals("userName") && (resultDomain = userDAO.findByUserName(userDomain.getUserName())) != null){
					
					if(userName.equalsIgnoreCase(userDomain.getUserName())){
						
						//Set Save Flag
						saveFlag = false;
						
						//Error Message
						Messages.addGlobalError("O Usuário \"" + userDomain.getUserName() + "\" já existe.");
						
					}
					
				} else if(checkField().equals("cnpj") && (resultDomain = userDAO.findByCNPJ(userDomain.getCnpj())) != null){
					
					//Set Save Flag
					saveFlag = false;
					
					//Error Message
					Messages.addGlobalError("O CNPJ \"" + userDomain.getCnpj() + "\" já pertence a \"" + resultDomain.getPersonDomain().getCompleteName() + "\".");				
				}
			}
			
			
			//Condition to save or not the information according to the userName
			if(saveFlag){
				
				//Set Default Theme Value While Saving
				if(userDomain.getUserTheme() == null){
					userDomain.setUserTheme("ui-lightness");
				}
				
				//Save User with merge method
				//resultDomain = userDAO.merge(userDomain);
				
				//Set Path (result --> System.getProperty("user.home") = C:/Users/Samir Landou)
				String userFolderPath = System.getProperty("user.home")
						+"/Documents/Desenvolvimento/Uploads/Users/"
						+ userDomain.getPersonDomain().getId()
						+"/Login";
				
				//Create Folder according to the UserName
				methodUtil.createFolder(userFolderPath);
				
				//verify if there was an upload file.
				if(upload){
					
					//Get origin file
					Path originFile = Paths.get(userDomain.getImageUserPath());
					
					//Get destination file
					Path destinationFile = Paths.get(userFolderPath
														+ "/userImage."
														+ FilenameUtils.getExtension(userDomain.getImageUserFileName()));
					
					//Copy origin File do the destination path
					Files.copy(originFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
					
					//Copy destination path
					destinationUserImageFile = destinationFile.toString();
					
					//Set Image User Path
					userDomain.setImageUserFileName(destinationUserImageFile);
					
					//Delete all temporary files
					doDeleteTempfiles();

					//Face Context Login
					FacesContext fcLogin = FacesContext.getCurrentInstance();
					
					//Get External Context from LoginController
					loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
					
					//Update user Image Path in loginController
					loginController.getLoggedUser().setImageUserFileName(destinationUserImageFile);
					
					upload = false;
				}

				//Save User with merge method
				userDAO.merge(userDomain);
				
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");				
				
				//Clean informations in the panelGrid
				//doNewRegister();
				
				//List again User (very import to update the list)
				usersDomain = userDAO.descendList("id");
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
			
			//Get user image file
			//Path userFile = null;
			//userFile = Paths.get(userDomain.getImageUserPath());
			
			//Delete User
			UserDAO userDAO = new UserDAO();
			userDAO.delete(userDomain);
			
			//Delete user image file
			//Files.deleteIfExists(userFile);
			
			//Set Path (result --> System.getProperty("user.home") = C:/Users/Samir Landou)
			String userFolderPath = System.getProperty("user.home")
					+"/Documents/Desenvolvimento/Uploads/Users/"
					+ userDomain.getPersonDomain().getId();
			
			methodUtil.deleteFolder(userFolderPath);
					
			
			//List again User (very import to update the list)
			usersDomain = userDAO.descendList("id");
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("O usuário '" + userDomain.getUserName() + "' foi excluido com sucesso!!!");
			
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
			
			//Clean Variables
			doClean();
			
			//Set edit flag
			editFlag = true;
			
			//Capture the event from the cursor in user.xhtml
			userDomain = (UserDomain) event.getComponent().getAttributes()
					.get("selectedUserByCursor");
			
			//Decrypt Password
			userDomain.setPasswordWithoutCryptography(new String(Base64.decodeBase64(userDomain.getPassword().getBytes())));
			
			//Set Old Password
			oldPassword = userDomain.getPasswordWithoutCryptography();
			
			//Set some old User Domain Values.
			userName = userDomain.getUserName();
			cnpj = userDomain.getCnpj();

			//Instantiate List of person
			PersonDAO personDAO = new PersonDAO();
			personsDomain = personDAO.descendList("id");
			
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
    public void doHandleFileUpload(FileUploadEvent event) {
    	//userDomain.setImageUser(event.getFile().getContents());
    	   	
    	try {
    		//original file
    		UploadedFile uploadFile  =  event.getFile();
    		
    		if(uploadFile != null ){
        		//Set the original file name
        		userDomain.setImageUserFileName(uploadFile.getFileName().toString());
        		
        		//Create Destination file in Temporary Directory (C:\Users\Samir Landou\AppData\Local\Temp)
    			tempFile = Files.createTempFile(null, null);
    			
    			//Add temporary file in a list
    			tempFiles.add(tempFile);
    			
    			//Copy original file into destination file
    			Files.copy(uploadFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
    			
    			//Get path through @Transcient
    			userDomain.setImageUserPath(tempFile.toString());
    			
    			Messages.addGlobalInfo("Upload realizado com sucesso!");
    			
    			//Set upload to true while tempFiles is not empty
    			if(!tempFiles.isEmpty()){
    				upload = true;
    			}
    						
    			System.out.println("Temporary File: " + userDomain.getImageUserPath());   			
    		}

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
    
    
    /**
     * Delete User Image file
     */
    public void doDeleteFile(){

		//Face Context Login
		FacesContext fcLogin = FacesContext.getCurrentInstance();
		
		//Get External Context from LoginController
		loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
		
		//Delete File
    	if (methodUtil.deleteFile(loginController.getLoggedUser().getImageUserFileName())) {
    		
    		//Delete all temporary files
    		doDeleteTempfiles();
    		
			//Set Image User Path
			userDomain.setImageUserFileName("");
			
			//Update user Image Path in loginController
			loginController.getLoggedUser().setImageUserFileName("");
    		
    		//Informs delete successfully
    		Messages.addGlobalInfo("A imagem foi deletada com sucesso!");			
		} else{
			
			//Set Image User Path
			userDomain.setImageUserFileName("");
			
			//Informs image can not be deleted.
			Messages.addGlobalWarn("Essa imagem não pode ser deletada.");
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


	public boolean isDisableDeleteImageButton() {
		return disableDeleteImageButton;
	}


	public void setDisableDeleteImageButton(boolean disableDeleteImageButton) {
		this.disableDeleteImageButton = disableDeleteImageButton;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public UploadedFile getFile() {
		return File;
	}


	public void setFile(UploadedFile file) {
		File = file;
	}

/*
	public PersonDomain getPersonDomain() {
		return personDomain;
	}


	public void setPersonDomain(PersonDomain personDomain) {
		this.personDomain = personDomain;
	}	*/
	
}
