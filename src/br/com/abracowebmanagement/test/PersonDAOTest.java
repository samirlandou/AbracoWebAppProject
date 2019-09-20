package br.com.abracowebmanagement.test;


import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.domain.PersonDomain;

public class PersonDAOTest {
	
	@Test
	@Ignore
	public void save(){
		
		//Set information
		PersonDomain personDomain= new PersonDomain();
		personDomain.setCompleteName("SAMIR K. A. LANDOU");
		personDomain.setNationality("BENIN");
		personDomain.setSex('M');

		
		//Save Person
		PersonDAO personDAO = new PersonDAO();
		personDAO.save(personDomain);
		
		
		//Impress Person saved
		System.out.println("Register saved: ");
		System.out.println(personDomain.getId()
				+ " - "
				+ personDomain.getCompleteName()
				+ " - "
				+ personDomain.getNationality()
				+ " - "
				+ personDomain.getSex()
				);
	}
	
	
	@Test
	//@Ignore
	public void listar(){
		PersonDAO personDAO= new PersonDAO();
		List<PersonDomain> personDomain = personDAO.list();
		
		//Impress Person
		System.out.println("Total of Register found: " + personDomain.size());	
		for(PersonDomain person: personDomain){
			System.out.println(person.getId()
					+ " - "
					+ person.getCompleteName()
					+ " - "
					+ person.getNationality()
					+ " - "
					+ person.getSex()
					);
		}
	}

	
	@Test
	@Ignore
	public void search(){
		
		//Choose the id
		Long id = 2L;
		
		//Search Person
		PersonDAO personDAO= new PersonDAO();
		PersonDomain personDomain = personDAO.search(id);
		
		//Impress Person
		if(personDomain == null){
			System.out.println("There is no register!");
		}else{
			System.out.println("Register found: ");
			System.out.println(personDomain.getId()
					+ " - "
					+ personDomain.getCompleteName()
					+ " - "
					+ personDomain.getNationality()
					+ " - "
					+ personDomain.getSex()
					);
		}		
	}
	
	
	@Test
	@Ignore
	public void delete(){
		
		//Choose the id
		Long id = 3L;
		
		//Search Person
		PersonDAO personDAO= new PersonDAO();
		PersonDomain personDomain = personDAO.search(id);
		
		//Impress Person
		if(personDomain == null){
			System.out.println("There is no register!");
		}else{
			//Delete Person
			personDAO.delete(personDomain);
			
			//Impress Person deleted
			System.out.println("Register deleted: ");
			System.out.println(personDomain.getId()
					+ " - "
					+ personDomain.getCompleteName()
					+ " - "
					+ personDomain.getNationality()
					+ " - "
					+ personDomain.getSex()
					);
		}		
	}

	
	@Test
	@Ignore
	public void update(){
		
		//Choose the id
		Long id = 1L;
		
		//Search Person
		PersonDAO personDAO= new PersonDAO();
		PersonDomain personDomain = personDAO.search(id);

		//Impress Person
		if(personDomain == null){
			System.out.println("There is no register!");
		}else{
			
			//Edit the new Person information
			personDomain.setCompleteName("SAMIR LANDOU");
			personDomain.setNationality("BENIN");
			personDomain.setSex('M');
			
			//Update Person
			personDAO.update(personDomain);			
			
			//Impress Person after updating
			System.out.println("Register after updating: ");
			System.out.println(personDomain.getId()
					+ " - "
					+ personDomain.getCompleteName()
					+ " - "
					+ personDomain.getNationality()
					+ " - "
					+ personDomain.getSex()
					);
		}		
	}

}
