package br.com.abracowebmanagement.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.abracowebmanagement.domain.scheduleevent.ScheduleEventDomain;
import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class ScheduleEventDAO extends GenericDAO<ScheduleEventDomain>{
	
	
	/**
	 * Method to search one result by id
	 * @param id
	 * @return
	 */
	
	//Rewrite this like the example of UserDAO changing attribute
	
	public ScheduleEventDomain searchByDescriptionAndBeginDateAndEndDate(String description,
			Date beginDate, Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			consult.add(Restrictions.eq("scheduleEventDescription", description));
			consult.add(Restrictions.eq("scheduleEventBeginDate", beginDate));
			consult.add(Restrictions.eq("scheduleEventEndDate", endDate));
			//List<Entity> result = consult.list();
			ScheduleEventDomain result = (ScheduleEventDomain) consult.uniqueResult();
			return result;
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ScheduleEventDomain> searchByBeginDateAndEndDate(Date beginDate, Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			consult.add(Restrictions.between("scheduleEventBeginDate", beginDate, endDate));
			//consult.add(Restrictions.eq("scheduleEventEndDate", endDate));
			List<ScheduleEventDomain> result = null;
			result = consult.list();
			return result;
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}
	}
	

	public ScheduleEventDomain searchByDescriptionAndLoadEventsPeriod(String description, Date beginDate, Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			consult.add(Restrictions.between("scheduleEventBeginDate", beginDate, endDate));
			consult.add(Restrictions.eq("scheduleEventDescription", description));
			ScheduleEventDomain result = null;
			result = (ScheduleEventDomain) consult.uniqueResult();	
			return result;
		} catch (RuntimeException error) {
			throw error;
		}finally {
			session.close();
		}		
	}


}
