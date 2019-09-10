package br.com.abracowebmanagement.test;


import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.abracowebmanagement.dao.PersonalInfoDAO;
import br.com.abracowebmanagement.domain.PersonalInfoDomain;

public class PersonalInfoDAOTest {
	
	@Test
	@Ignore
	public void save(){
		
		//Set information
		PersonalInfoDomain personalInfoDomain= new PersonalInfoDomain();
		personalInfoDomain.setCompleteName("SAMIR K. A. LANDOU");
		personalInfoDomain.setNationality("BENIN");
		personalInfoDomain.setSex('M');

		
		//Save Personal Info
		PersonalInfoDAO personalInfoDAO = new PersonalInfoDAO();
		personalInfoDAO.save(personalInfoDomain);
		
		
		//Impress PersonalInfo saved
		System.out.println("Register saved: ");
		System.out.println(personalInfoDomain.getId()
				+ " - "
				+ personalInfoDomain.getCompleteName()
				+ " - "
				+ personalInfoDomain.getNationality()
				+ " - "
				+ personalInfoDomain.getSex()
				);
	}
	
	
	@Test
	//@Ignore
	public void listar(){
		PersonalInfoDAO personalInfoDAO= new PersonalInfoDAO();
		List<PersonalInfoDomain> personalInfoDomain = personalInfoDAO.list();
		
		//Impress PersonalInfo
		System.out.println("Total of Register found: " + personalInfoDomain.size());	
		for(PersonalInfoDomain personalInfo: personalInfoDomain){
			System.out.println(personalInfo.getId()
					+ " - "
					+ personalInfo.getCompleteName()
					+ " - "
					+ personalInfo.getNationality()
					+ " - "
					+ personalInfo.getSex()
					);
		}
	}

	
	@Test
	@Ignore
	public void search(){
		
		//Choose the id
		Long id = 2L;
		
		//Search PersonalInfo
		PersonalInfoDAO personalInfoDAO= new PersonalInfoDAO();
		PersonalInfoDomain personalInfoDomain = personalInfoDAO.search(id);
		
		//Impress PersonalInfo
		if(personalInfoDomain == null){
			System.out.println("There is no register!");
		}else{
			System.out.println("Register found: ");
			System.out.println(personalInfoDomain.getId()
					+ " - "
					+ personalInfoDomain.getCompleteName()
					+ " - "
					+ personalInfoDomain.getNationality()
					+ " - "
					+ personalInfoDomain.getSex()
					);
		}		
	}
	
	
	@Test
	@Ignore
	public void delete(){
		
		//Choose the id
		Long id = 3L;
		
		//Search PersonalInfo
		PersonalInfoDAO personalInfoDAO= new PersonalInfoDAO();
		PersonalInfoDomain personalInfoDomain = personalInfoDAO.search(id);
		
		//Impress PersonalInfo
		if(personalInfoDomain == null){
			System.out.println("There is no register!");
		}else{
			//Delete PersonalInfo
			personalInfoDAO.delete(personalInfoDomain);
			
			//Impress PersonalInfo deleted
			System.out.println("Register deleted: ");
			System.out.println(personalInfoDomain.getId()
					+ " - "
					+ personalInfoDomain.getCompleteName()
					+ " - "
					+ personalInfoDomain.getNationality()
					+ " - "
					+ personalInfoDomain.getSex()
					);
		}		
	}

	
	@Test
	@Ignore
	public void update(){
		
		//Choose the id
		Long id = 1L;
		
		//Search PersonalInfo
		PersonalInfoDAO personalInfoDAO= new PersonalInfoDAO();
		PersonalInfoDomain personalInfoDomain = personalInfoDAO.search(id);

		//Impress PersonalInfo
		if(personalInfoDomain == null){
			System.out.println("There is no register!");
		}else{
			
			//Edit the new PersonalInfo information
			personalInfoDomain.setCompleteName("SAMIR LANDOU");
			personalInfoDomain.setNationality("BENIN");
			personalInfoDomain.setSex('M');
			
			//Update PersonalInfo
			personalInfoDAO.update(personalInfoDomain);			
			
			//Impress PersonalInfo after updating
			System.out.println("Register after updating: ");
			System.out.println(personalInfoDomain.getId()
					+ " - "
					+ personalInfoDomain.getCompleteName()
					+ " - "
					+ personalInfoDomain.getNationality()
					+ " - "
					+ personalInfoDomain.getSex()
					);
		}		
	}

}
