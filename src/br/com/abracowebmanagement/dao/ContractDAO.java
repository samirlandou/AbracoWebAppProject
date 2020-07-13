package br.com.abracowebmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.ContractDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class ContractDAO extends GenericDAO<ContractDomain> {

	
	/**
	 * Find constract code
	 * @param contractName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<ContractDomain> findByContractName(String contractName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(ContractDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for contractName
			consult.add(Restrictions.eq("contractCodeDescription", contractName));
			
			//Get result
			List<ContractDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
}
