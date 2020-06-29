package br.com.abracowebmanagement.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
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
			Date localDateTime, Date localDateTime2){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			consult.add(Restrictions.eq("scheduleEventDescription", description));
			consult.add(Restrictions.eq("scheduleEventBeginDate", localDateTime));
			consult.add(Restrictions.eq("scheduleEventEndDate", localDateTime2));
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
	public List<ScheduleEventDomain> searchByBeginDateAndEndDate(Date start, Date end, String userName){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			
			consult.createAlias("userDomain", "u");
			
			Criterion result1 = Restrictions.between("scheduleEventBeginDate", start, end);
			
			Criterion result2 = Restrictions.between("scheduleEventEndDate", start, end);
			
			Criterion result3 = Restrictions.and(Restrictions.eq("publicFlag", false),
												 Restrictions.eq("u.userName", userName));
			
			Criterion result4 = Restrictions.eq("publicFlag", true);
			
			consult.add(Restrictions.or(result1,result2));
			
			consult.add(Restrictions.or(result3,result4));
			
			//consult.add(result3);
			
			//consult.add(result4);
			
			//consult.add(result5);
			
			/*consult.add(Restrictions.conjunction().
							add(Restrictions.or(result1, result2)).
							add(Restrictions.eq("publicFlag", true)).
							add(Restrictions.and(result3, result4))							
					);*/
			
			/*consult.add(Restrictions.or(result1, result2));
			
			consult.add(Restrictions.eq("publicFlag", true));
			
			consult.createAlias("userDomain", "u");*/
			
			//consult.add(Restrictions.and(Restrictions.eq("publicFlag", false), 
					//Restrictions.eq("u.userName", userName)));
			
			/*consult.add(Restrictions.between("scheduleEventBeginDate", start, end));*/
										 
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
	

	public ScheduleEventDomain searchByDescriptionAndLoadEventsPeriod(String description, Date loadEventsBeginPeriod, Date loadEventsEndPeriod){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria consult = session.createCriteria(ScheduleEventDomain.class);
			consult.add(Restrictions.between("scheduleEventBeginDate", loadEventsBeginPeriod, loadEventsEndPeriod));
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
