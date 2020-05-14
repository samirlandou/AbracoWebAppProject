package br.com.abracowebmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class PersonDAO extends GenericDAO<PersonDomain> {	

	
	public PersonDomain findByEmail(String email){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for telephone
			consult.add(Restrictions.eq("email", email));
			
			//Get result
			PersonDomain result = new PersonDomain();
			result = (PersonDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	public PersonDomain findByTelephone(String tel){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for telephone
			consult.add(Restrictions.eq("telephone", tel));
			
			//Get result
			PersonDomain result = new PersonDomain();
			result = (PersonDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	public PersonDomain findByCompletename(String name){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for completeName
			consult.add(Restrictions.eq("completeName", name));
			
			//Get result
			PersonDomain result = new PersonDomain();
			result = (PersonDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}		
	
	@SuppressWarnings("unchecked")
	public List<PersonDomain> findByActiveStudent(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//consult.createAlias("personDomain", "p");

			//Add restriction for status
			consult.add(Restrictions.eq("status", 1));
			
			//Add restriction for profile
			consult.add(Restrictions.eq("profile", "ESTUDANTE"));
			
			//Get result
			List<PersonDomain> result = consult.list();			
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}

		
	@SuppressWarnings("unchecked")
	public List<PersonDomain> findByActiveStudentAndLanguage(String language, String profile){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//Add restriction for status
			consult.add(Restrictions.eq("status", true));			
			
			//Add restriction for profile
			consult.add(Restrictions.eq("profile", profile));
			
			//Add restriction for language
			consult.add(Restrictions.eq("language", language));
			
			//Order by Complete Name
			consult.addOrder(Order.desc("completeName"));
			
			//Get result
			List<PersonDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PersonDomain> findByActiveProfessorAndLanguage(String language){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String[] profile = {"PROFESSOR(A)", "COORDENADOR(A)"};
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//Criterion student = Restrictions.eq(propertyName, value);
			
			//Add restriction for status
			consult.add(Restrictions.eq("status", true));
			
			//Add restriction for profile
			consult.add(Restrictions.in("profile", profile));
			
			//Add restriction for language
			consult.add(Restrictions.eq("language", language));
			
			consult.addOrder(Order.desc("completeName"));
			
			//Get result
			List<PersonDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}

	public PersonDomain findByCPF(String cpf){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(PersonDomain.class);
			
			//Add restriction for CPF
			consult.add(Restrictions.eq("cpf", cpf));
			
			//Get result
			PersonDomain result = new PersonDomain();
			result = (PersonDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}	
	
}
