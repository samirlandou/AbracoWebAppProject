package br.com.abracowebmanagement.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContext implements ServletContextListener{
	
	public void contextDestroyed(ServletContextEvent arg0){
		HibernateUtil.getSessionFactory().close();
	}

	public void contextInitialized(ServletContextEvent event){
		//HibernateUtil.getSessionFactory().openSession();
		HibernateUtil.getSessionFactory();
	}
	
}
