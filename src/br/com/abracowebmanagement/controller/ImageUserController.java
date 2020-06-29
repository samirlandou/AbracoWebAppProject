package br.com.abracowebmanagement.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.tomcat.util.codec.binary.Base64;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.user.UserDomain;
import br.com.abracowebmanagement.util.MethodUtil;

@ManagedBean
@ViewScoped
public class ImageUserController implements Serializable{

	//@ManagedProperty("#{param.imageUserPath}") //GetParameter from user.xhtml
	/*@ManagedProperty("#{param.photo}") //GetParameter from user.xhtml*/


	/**
	 * 
	 */
	private static final long serialVersionUID = -1641289067866059726L;

	//Instantiate UserDomain
	private UserDomain userDomain = new UserDomain();
		
	//Instantiate Login Controller
	LoginController loginController = new LoginController();
	
	public MethodUtil methodUtil =  new MethodUtil();
	
	public List<Path> tempFiles = new ArrayList<Path>();
	Path tempFile;
	
	private String imageUserPath;	
	private StreamedContent streamContent;
	private String oldPassword;	
	private UploadedFile file;
	String destinationUserImageFile;
		
	boolean upload;

	
	
	@PostConstruct
	public void init() {

		//Face Context Login
		FacesContext fcLogin = FacesContext.getCurrentInstance();
		
		//Get External Context from LoginController
		loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
		
		//Set UserDomain
		userDomain = loginController.getLoggedUser();
		
		userDomain.setPasswordWithoutCryptography(new String(Base64.decodeBase64(userDomain.getPassword().getBytes())));

		//Set Old Password
		oldPassword = userDomain.getPasswordWithoutCryptography();
	
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
        		
    			//set the original file name
        		userDomain.setImageUserFileName(uploadFile.getFileName().toString());
        		
        		//Create destination file in Temporary Directory (C:\Users\Samir Landou\AppData\Local\Temp)
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
    
    
 
	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 11/09/2019
	 */
	public void doSave(){	
		
		try {
			
			//Set Save Flag
			 boolean saveFlag = true;
			
			//Save User with merge method
			UserDAO userDAO = new UserDAO();
			
			//Encrypt password
			//userDomain.setPasswordWithoutCryptography(new String(Base64.encodeBase64(userDomain.getPasswordWithoutCryptography().getBytes())));
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

			
			//Condition to save or not the information according to the userName
			if(saveFlag){
				
				//Set Default Theme Value While Saving
				if(userDomain.getUserTheme() == null){
					userDomain.setUserTheme("ui-lightness");
				}
				
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
			}
				
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa!");
			e.printStackTrace();
		}		
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
	
    
	/*
	 * Getters and Setters
	 */
	
	public String getImageUserPath() {
		return imageUserPath;
	}
	
	public void setImageUserPath(String imagePath) {
		this.imageUserPath = imagePath;
	}
	
	public StreamedContent getStreamContent() throws IOException {
		
		//Set Default Image User Path		
		Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Default/Login/defaultUserImage.png");
		
		if(loginController.getLoggedUser().getImageUserFileName() != null
				&& new File(loginController.getLoggedUser().getImageUserFileName()).exists()){
			path = Paths.get(loginController.getLoggedUser().getImageUserFileName());
		}
		
		InputStream is = Files.newInputStream(path);
		//streamContent = new DefaultStreamedContent(is);
		/**
		 * Ref:https://stackoverflow.com/questions/59576891/primefaces-8-0-defaultstreamedcontent-builder-stream-asks-for-serializablesu
		 * 
		 * Create 2 times 
		 * DefaultStreamedContent.builder().contentType(contentType).name(name).stream(() -> is).build();
		 * 
		 * Create 1 time with fileOutputStream
		 * DefaultStreamedContent.builder().contentType(contentType).name(name).stream(() -> new FileInputStream(....)).build();
		 * 
		 */
		streamContent = DefaultStreamedContent.builder().stream(() -> is).build();
		
		
		return streamContent;
	}
	
	public void setStreamContent(StreamedContent imageUserStreamContent) {
		this.streamContent = imageUserStreamContent;
	}


	public UserDomain getUserDomain() {
		return userDomain;
	}


	public void setUserDomain(UserDomain userDomain) {
		this.userDomain = userDomain;
	}


	public UploadedFile getFile() {
		return file;
	}



	public void setFile(UploadedFile file) {
		this.file = file;
	}



	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}	

}
