package br.com.abracowebmanagement.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.util.HibernateUtil;

public class GenericDAO<Entity> {
	
	private Class<Entity> myEntityClass;
	
	@SuppressWarnings("unchecked")
	public GenericDAO(){
		this.myEntityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Method to save
	 * @param entity
	 */
	public void save(Entity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch (RuntimeException error) {
			if(transaction != null){
				transaction.rollback();
			}
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	//Merge Original
	/**
	 * Method to merge
	 * @param entity
	 */
	/*public void merge(Entity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.merge(entity);
			transaction.commit();
		} catch (RuntimeException error) {
			if(transaction != null){
				transaction.rollback();
			}
			throw error;
		}finally {
			session.close();
		}
	}*/
	
	
	/**
	 * Method to list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> list(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(myEntityClass);
			List<Entity> result = consult.list();
			return result;
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	/**
	 * Method to search one result by id
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Entity search(Long id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(myEntityClass);
			consult.add(Restrictions.idEq(id));
			//List<Entity> result = consult.list();
			Entity result = (Entity) consult.uniqueResult();
			return result;
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}

	
	/**
	 * Method to delete
	 * @param entity
	 */
	public void delete(Entity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		} catch (RuntimeException error) {
			if(transaction != null){
				transaction.rollback();
			}
			throw error;
		}finally {
			session.close();
		}
	}

	
	/**
	 * Method to update
	 * @param entity
	 */
	public void update(Entity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} catch (RuntimeException error) {
			if(transaction != null){
				transaction.rollback();
			}
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	/**
	 * Method to merge
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	public Entity merge(Entity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Entity result = (Entity) session.merge(entity);
			transaction.commit();
			return result;
		} catch (RuntimeException error) {
			if(transaction != null){
				transaction.rollback();
			}
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Entity> ascendList(String campoOrdenacao) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(myEntityClass);
			consulta.addOrder(Order.asc(campoOrdenacao));
			List<Entity> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> descendList(String campoOrdenacao) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(myEntityClass);
			consulta.addOrder(Order.desc(campoOrdenacao));
			List<Entity> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
