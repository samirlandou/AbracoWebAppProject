package br.com.abracowebmanagement.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class UserDAO extends GenericDAO<UserDomain> implements Serializable{
	
	private static final long serialVersionUID = -6179707130291792232L;

	/**
	 * Authentication Method
	 * 
	 * @author samirlandou
	 * @param userName
	 * @param password
	 * @returns
	 */
	public UserDomain authenticate(String userName, String password){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			//CreatAlias
			consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("userName", userName));
			
			//Cryptography with MD5 HEX
			//SimpleHash hash = new SimpleHash("MD5", password);
			
			//Add restriction for password
			consult.add(Restrictions.eq("password", new String(Base64.encodeBase64(password.getBytes()))));
			
			//Get result
			UserDomain result = new UserDomain();
			result = (UserDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	public UserDomain findByUserName(String userName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("userName", userName));
			
			//Get result
			UserDomain result = new UserDomain();
			result = (UserDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	public UserDomain findByCompleteName(String completName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("p.completeName", completName));
			
			//Get result
			UserDomain result = new UserDomain();
			result = (UserDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	public UserDomain findByCNPJ(String cnpj){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			consult.createAlias("userDomain", "u");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("u.cnpj", cnpj));
			
			//Get result
			UserDomain result = new UserDomain();
			result = (UserDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserDomain> findByActiveProfessorAndLanguage(String language){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String[] profile = {"PROFESSOR(A)", "COORDENADOR(A)"};
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			//Criterion student = Restrictions.eq(propertyName, value);
			
			consult.createAlias("personDomain", "p");
			
			//Add restriction for status
			consult.add(Restrictions.eq("p.status", true));
			
			//Add restriction for profile
			consult.add(Restrictions.in("p.profile", profile));
			
			//Add restriction for language
			consult.add(Restrictions.eq("p.language1", language));
			
			consult.addOrder(Order.desc("p.completeName"));
			
			//Get result
			List<UserDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<UserDomain> findByActiveProfessorAndTeachingLanguage(String language){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String[] profile = {"PROFESSOR(A)", "COORDENADOR(A)"};
		
		try{
			Criteria consult = session.createCriteria(UserDomain.class);
			
			//Criterion student = Restrictions.eq(propertyName, value);
			
			consult.createAlias("personDomain", "p");
			
			//Add restriction for status
			consult.add(Restrictions.eq("p.status", true));
			
			//Add restriction for profile
			consult.add(Restrictions.in("p.profile", profile));
			
			//Add restriction for language
			//Add restriction for language
			consult.add(Restrictions.or(Restrictions.eq("teachingLanguage1", language), 
										Restrictions.eq("teachingLanguage2", language)));
			
			consult.addOrder(Order.desc("p.completeName"));
			
			//Get result
			List<UserDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}	
	
}
