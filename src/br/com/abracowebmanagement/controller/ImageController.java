package br.com.abracowebmanagement.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImageController implements Serializable{

	//@ManagedProperty("#{param.imageUserPath}") //GetParameter from user.xhtml
	/*@ManagedProperty("#{param.photo}") //GetParameter from user.xhtml*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 3961908747365220327L;
		
	//Instantiate Login Controller
	LoginController loginController = new LoginController();
	
	//Instantiate Image Path an StreamContent
	private String imageUserPath;	
	private StreamedContent streamContent;
		
	
	@PostConstruct
	public void init() {

		//Face Context Login
		FacesContext fcLogin = FacesContext.getCurrentInstance();
		
		//Get External Context from LoginController
		loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
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
		streamContent = DefaultStreamedContent.builder().stream(() -> is).build();
	
		return streamContent;
	}
	
	public void setStreamContent(StreamedContent imageUserStreamContent) {
		this.streamContent = imageUserStreamContent;
	}	

}
