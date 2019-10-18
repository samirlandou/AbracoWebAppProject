package br.com.abracowebmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImageController {
	
	@ManagedProperty("#{param.imageUserPath}") //GetParameter from user.xhtml
	private String imageUserPath;
	
	private StreamedContent streamContent;
	
	
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
		
		
		if(imageUserPath.toString() == null || imageUserPath.toString().isEmpty()){
			Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
			InputStream is = Files.newInputStream(path);
			streamContent = new DefaultStreamedContent(is);
		} else{
			Path path = Paths.get(imageUserPath);
			InputStream is = Files.newInputStream(path);
			streamContent = new DefaultStreamedContent(is);			
		}
		
		/*
		Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
		InputStream is = Files.newInputStream(path);
		streamContent = new DefaultStreamedContent(is);
		*/
		/*try {
			if(imageUserPath != null || !imageUserPath.isEmpty()){
				Path path = Paths.get(imageUserPath);
				InputStream is = Files.newInputStream(path);
				imageUserStreamContent = new DefaultStreamedContent(is);
			} else{
				Path path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads/Users/imagePattern/man.png");
				InputStream is = Files.newInputStream(path);
				imageUserStreamContent = new DefaultStreamedContent(is);			
			}
		} catch (IOException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a imagem do usuário!!!");
			e.printStackTrace();
		}	*/	
		return streamContent;
	}
	
	public void setStreamContent(StreamedContent imageUserStreamContent) {
		this.streamContent = imageUserStreamContent;
	}
	
	

}
