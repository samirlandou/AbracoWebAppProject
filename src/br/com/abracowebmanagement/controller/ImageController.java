package br.com.abracowebmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.abracowebmanagement.domain.UserDomain;

@ManagedBean
@RequestScoped
public class ImageController {
	
	UserDomain userDomain = new UserDomain();;
	
	//@ManagedProperty("#{param.imageUserPath}") //GetParameter from user.xhtml
	/*@ManagedProperty("#{param.photo}") //GetParameter from user.xhtml*/
	private String imageUserPath;
	
	private StreamedContent streamContent;
	
	
	//Login Controller
	LoginController loginController = new LoginController();
	
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
	
	public void teste(){
		System.out.println("TESTE DE IMAGEM");
	}
	
	public StreamedContent getStreamContent() throws IOException {
		
		//Set Default Image User Path		
		Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Default/Login/defaultUserImage.png");
		
		if(loginController.getLoggedUser().getImageUserFileName() != null){
			path = Paths.get(loginController.getLoggedUser().getImageUserFileName());
		}
		
		InputStream is = Files.newInputStream(path);
		streamContent = new DefaultStreamedContent(is);
	
		return streamContent;
	}
	
	public void setStreamContent(StreamedContent imageUserStreamContent) {
		this.streamContent = imageUserStreamContent;
	}
	
	

}
