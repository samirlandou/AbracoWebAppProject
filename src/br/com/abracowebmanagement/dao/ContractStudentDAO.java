package br.com.abracowebmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.contract.ContractStudentDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class ContractStudentDAO extends GenericDAO<ContractStudentDomain> {

	
	/**
	 * Find contract code
	 * @param contractName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<ContractStudentDomain> listByContractCodeDescription(String contractCode){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			Criteria consult = session.createCriteria(ContractStudentDomain.class);
			
			//Order
			consult.addOrder(Order.desc("id"));	
			
			consult.createAlias("contractDomain", "c");
			
			//Add restriction for contractName
			consult.add(Restrictions.eq("c.contractCodeDescription", contractCode));
	
			//Get result
			List<ContractStudentDomain> result = consult.list();			
			return result;
			
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
}
