package br.com.abracowebmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImageController2 {
	
	//Face Context Login
	FacesContext context = FacesContext.getCurrentInstance();
	
	//Set Request Parameters in Map
	Map<String, String> parameters = context.getExternalContext().getRequestParameterMap();
	
	private String imageUserPath;
	
	private StreamedContent streamContent;
	
	

	public String getImageUserPath() {
		return imageUserPath;
	}

	public void setImageUserPath(String imageUserPath) {
		this.imageUserPath = imageUserPath;
	}

	public StreamedContent getStreamContent() {
		//Set Default Image User Path		
		//Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Default/Login/defaultUserImage.png");
		
		setImageUserPath(parameters.get("imageUser"));

		
		
		//if(imageUserPath.toString() == null || imageUserPath.toString().isEmpty()){
			
			//Set Image DedfaultUser Path While 
			//Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Default/Login/defaultUserImage.png");
			//Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
			//Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/joelaronson/Login/userImage.png");
			
			/*InputStream is = Files.newInputStream(path);
			streamContent = new DefaultStreamedContent(is);
		} else{
			Path path = Paths.get(imageUserPath);
			InputStream is = Files.newInputStream(path);
			streamContent = new DefaultStreamedContent(is);			
		}*/
		
		/*
		Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
		InputStream is = Files.newInputStream(path);
		streamContent = new DefaultStreamedContent(is);
		*/
		try {
			if(imageUserPath != null || !imageUserPath.isEmpty()){
				Path path = Paths.get(imageUserPath);
				InputStream is = Files.newInputStream(path);
				streamContent = new DefaultStreamedContent(is);
			} else{
				Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
				InputStream is = Files.newInputStream(path);
				streamContent = new DefaultStreamedContent(is);			
			}
		} catch (IOException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a imagem do usuário!!!");
			e.printStackTrace();
		}		
		return streamContent;
	}

	public void setStreamContent(StreamedContent streamContent) {
		this.streamContent = streamContent;
	}
	

}
