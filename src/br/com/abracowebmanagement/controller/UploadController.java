package br.com.abracowebmanagement.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


@ManagedBean
@RequestScoped
public class UploadController {
	
	private UploadedFile uploadedFile;
	private String fileName;
	private String contentType;
	private byte[] contents;
	private StreamedContent streamContent;
	Path path;
	
	
	public void upload() {
	    //String fileName = uploadedFile.getFileName();
	    //String contentType = uploadedFile.getContentType();
	    //byte[] contents = uploadedFile.getContents(); // Or getInputStream()
	    // ... Save it, now!
		
		/*From Database.
		Blob blob = resultSet.getBlob("pictureBlob");               
		byte [] data = blob.getBytes( 1, ( int ) blob.length() );
		BufferedImage img = null;
		try {
		img = ImageIO.read(new ByteArrayInputStream(data));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		drawPicture(img);  //  void drawPicture(Image img);*/

	    fileName = uploadedFile.getFileName();
	    contentType = uploadedFile.getContentType();
	    contents = uploadedFile.getContents();
		
	    System.out.println(fileName);
	    System.out.println(contentType);
	    System.out.println(contents.length);
	    
	    //transform bytes do File
	    path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads");
	    

		try {
		    File file = new File(path.toString(), fileName);
		    
		    OutputStream out = new FileOutputStream(file);
		    out.write(uploadedFile.getContents());
		    out.close();
			
			//Info Message
			Messages.addGlobalInfo("Upload finalizado.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//Info Message
			Messages.addGlobalError("Erro no upload...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Info Message
			Messages.addGlobalError("Erro ao fazer o upload...");
		}	    
	}
	
	
	
	

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public StreamedContent getStreamContent() {


		InputStream is = null;
		try {
			is = Files.newInputStream(path);
			streamContent = new DefaultStreamedContent(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//InputStream is = new ByteArrayInputStream(contents);
		//streamContent = new DefaultStreamedContent(new ByteArrayInputStream(contents), contentType, fileName);
		
		return streamContent;
	}

	public void setStreamContent(StreamedContent streamContent) {
		this.streamContent = streamContent;
	}

}
