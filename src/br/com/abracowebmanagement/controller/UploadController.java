package br.com.abracowebmanagement.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;


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
	    contents = uploadedFile.getContent();
		
	    System.out.println(fileName);
	    System.out.println(contentType);
	    System.out.println(contents.length);
	    
	    //transform bytes do File
	    path = Paths.get("C:/Users/Samir Landou/Documents/Desenvolvimento/Uploads");
	    

		try {
		    File file = new File(path.toString(), fileName);
		    
		    OutputStream out = new FileOutputStream(file);
		    out.write(uploadedFile.getContent());
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

	public StreamedContent getStreamContent() throws IOException {

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
		
		
		
		/*//Works with inputstream.
		InputStream is = Files.newInputStream(path);
		
		streamContent = DefaultStreamedContent.builder().stream(() -> is).build();
		*/
		
		//Testing with content
		streamContent = DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(contents)).build();
		
		//InputStream is = new ByteArrayInputStream(contents);
		//streamContent = new DefaultStreamedContent(new ByteArrayInputStream(contents), contentType, fileName);
		
		return streamContent;
	}

	public void setStreamContent(StreamedContent streamContent) {
		this.streamContent = streamContent;
	}

}
