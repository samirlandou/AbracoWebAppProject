package br.com.abracowebmanagement.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    
    //Transform a session to a connection
    public static Connection getOpenConnection(){
    	
    	Session session = sessionFactory.openSession();
    	
    	Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
    		
    		@Override
    		public Connection execute(Connection conn) throws SQLException {
    			// TODO Auto-generated method stub
    			return conn;
    		}
		});
    	
    	return connection;
    	
    }
    

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure();

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	
			return sessionFactory;
			
            /*return new Configuration().configure().buildSessionFactory(
			    new StandardServiceRegistryBuilder().build() );*/
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}