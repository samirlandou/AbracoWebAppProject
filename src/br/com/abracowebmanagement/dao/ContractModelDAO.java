package br.com.abracowebmanagement.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.contract.ContractModelDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class ContractModelDAO extends GenericDAO<ContractModelDomain> {	

	
	public ContractModelDomain findByContractModelName(String contractModelName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(ContractModelDomain.class);
			
			//consult.createAlias("personDomain", "p");
			
			//Add restriction for userName
			consult.add(Restrictions.eq("contractModelName", contractModelName));
			
			//Get result
			ContractModelDomain result = new ContractModelDomain();
			result = (ContractModelDomain) consult.uniqueResult();
			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
}
