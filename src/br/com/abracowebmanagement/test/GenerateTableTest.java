package br.com.abracowebmanagement.test;

import org.junit.Test;

import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class GenerateTableTest {
	
	@Test
	public void generate() {
		HibernateUtil.getSessionFactory();
		HibernateUtil.getSessionFactory().close();
	}
	
}