package br.com.abracowebmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.LevelDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class LevelDAO extends GenericDAO<LevelDomain> {	

	
	public LevelDomain findByLevelName(String levelName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(LevelDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("levelName", levelName));
			
			//Get result
			LevelDomain result = new LevelDomain();
			result = (LevelDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LevelDomain> findByLevelNameOrDescription(String levelName, String levelDescription, String language){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(LevelDomain.class);
			
			consult.createAlias("languageDomain", "l");
			
			//Add Criterion to search level
			Criterion searchLevelName = Restrictions.eq("levelName", levelName);
			
			//Add Criterion to search level
			Criterion searchLevelDescription = Restrictions.eq("levelDescription", levelDescription);
			
			//Add Restriction for language
			consult.add(Restrictions.eq("l.languageName", language));			
			
			//Add First Restriction
			consult.add(Restrictions.or(searchLevelName, searchLevelDescription));
			
			//Get result
			List<LevelDomain> result = new ArrayList<LevelDomain>();
			result =  consult.list();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
}
