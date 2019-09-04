package br.com.abracowebmanagement.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.abracowebmanagement.dao.LoginDAO;
import br.com.abracowebmanagement.domain.UserDomain;

public class LoginDAOTest {
	
	@Test
	@Ignore
	public void save(){
		
		//Set information
		UserDomain userDomain= new UserDomain();
		userDomain.setUserName("samir.landou");
		userDomain.setPassword("snoopDoggy");
		userDomain.setRememberPassword("meu Cachorro");
		
		//Save login
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.save(userDomain);
		
		//Impress Login saved
		System.out.println("Register saved: ");
		System.out.println(userDomain.getId()
				+ " - "
				+ userDomain.getUserName()
				+ " - "
				+ userDomain.getPassword()
				+ " - "
				+ userDomain.getRememberPassword()
				);
	}

	
	@Test
	@Ignore
	public void listar(){
		LoginDAO loginDAO= new LoginDAO();
		List<UserDomain> userDomain = loginDAO.list();
		
		//Impress Login
		System.out.println("Total of Register found: " + userDomain.size());	
		for(UserDomain login: userDomain){
			System.out.println(login.getId()
					+ " - "
					+ login.getUserName()
					+ " - "
					+ login.getPassword()
					+ " - "
					+ login.getRememberPassword()
					);
		}
	}

	
	@Test
	@Ignore
	public void search(){
		
		//Choose the id
		Long id = 2L;
		
		//Search Login
		LoginDAO loginDAO= new LoginDAO();
		UserDomain userDomain = loginDAO.search(id);
		
		//Impress Login
		if(userDomain == null){
			System.out.println("There is no register!");
		}else{
			System.out.println("Register found: ");
			System.out.println(userDomain.getId()
					+ " - "
					+ userDomain.getUserName()
					+ " - "
					+ userDomain.getPassword()
					+ " - "
					+ userDomain.getRememberPassword()
					);
		}		
	}
	
	
	@Test
	@Ignore
	public void delete(){
		
		//Choose the id
		Long id = 3L;
		
		//Search Login
		LoginDAO loginDAO= new LoginDAO();
		UserDomain userDomain = loginDAO.search(id);
		
		//Impress Login
		if(userDomain == null){
			System.out.println("There is no register!");
		}else{
			//Delete Login
			loginDAO.delete(userDomain);
			
			//Impress Login deleted
			System.out.println("Register deleted: ");
			System.out.println(userDomain.getId()
					+ " - "
					+ userDomain.getUserName()
					+ " - "
					+ userDomain.getPassword()
					+ " - "
					+ userDomain.getRememberPassword()
					);
		}		
	}

	
	@Test
	@Ignore
	public void update(){
		
		//Choose the id
		Long id = 1L;
		
		//Search Login
		LoginDAO loginDAO= new LoginDAO();
		UserDomain userDomain = loginDAO.search(id);

		//Impress Login
		if(userDomain == null){
			System.out.println("There is no register!");
		}else{
			
			//Edit the new Login information
			userDomain.setUserName("samir.landou");
			userDomain.setPassword("snoopDoggyDog");
			userDomain.setRememberPassword("Meu Cachorro do Benin");
			
			//Update Login
			loginDAO.update(userDomain);			
			
			//Impress Login after updating
			System.out.println("Register after updating: ");
			System.out.println(userDomain.getId()
					+ " - "
					+ userDomain.getUserName()
					+ " - "
					+ userDomain.getPassword()
					+ " - "
					+ userDomain.getRememberPassword()
					);
		}		
	}

}
