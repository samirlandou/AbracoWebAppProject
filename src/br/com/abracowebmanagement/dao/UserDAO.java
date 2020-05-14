package br.com.abracowebmanagement.dao;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class UserDAO extends GenericDAO<UserDomain> {
	
	
	/**
	 * Authentication Method
	 * 
	 * @author samirlandou
	 * @param userName
	 * @param password
	 * @return
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
			
			consult.createAlias("pDomain", "p");
			
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
	
}
