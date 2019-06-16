package br.com.abracowebmanagement.main;

import br.com.abracowebmanagement.util.HibernateUtil;

public class GenerateTable {
	
	public static void main(String[] args) {
		HibernateUtil.getSessionFactory();
		HibernateUtil.getSessionFactory().close();
	}
}
