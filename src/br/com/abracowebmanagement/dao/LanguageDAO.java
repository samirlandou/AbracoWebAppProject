package br.com.abracowebmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.LanguageDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class LanguageDAO extends GenericDAO<LanguageDomain> {	

	
	public LanguageDomain findByLanguageName(String languageName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(LanguageDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("languageName", languageName));
			
			//Get result
			LanguageDomain result = new LanguageDomain();
			result = (LanguageDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LanguageDomain> findByLanguageNameOrDescription(String languageName, String languageDescription){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(LanguageDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			//consult.add(Restrictions.eq("languageName", languageName));
			
			//Add Criterion to search language
			Criterion searchLanguageName = Restrictions.eq("languageName", languageName);
			
			//Add Criterion to search language
			Criterion searchLanguageDescription = Restrictions.eq("languageDescription", languageDescription);
			
			//Get Result
			consult.add(Restrictions.or(searchLanguageName, searchLanguageDescription));
			
			//Get result
			List<LanguageDomain> result = new ArrayList<LanguageDomain>();
			result =  consult.list();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
}
