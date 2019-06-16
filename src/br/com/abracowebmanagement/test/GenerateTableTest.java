package br.com.abracowebmanagement.test;

import org.junit.jupiter.api.Test;

import br.com.abracowebmanagement.util.HibernateUtil;

public class GenerateTableTest {
	
	@Test
	public void gerar() {
		HibernateUtil.getSessionFactory();
		HibernateUtil.getSessionFactory().close();
	}
}
