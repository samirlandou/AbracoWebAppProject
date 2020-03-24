package br.com.abracowebmanagement.test;

import org.hibernate.Session;
import org.junit.Test;

import br.com.abracowebmanagement.hibernate.HibernateUtil;

public class HibernateUtilTest {
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		sessao.close();
		HibernateUtil.getSessionFactory().close();
	}
}
