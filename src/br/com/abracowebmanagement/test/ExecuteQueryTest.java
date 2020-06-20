package br.com.abracowebmanagement.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.junit.Test;

public class ExecuteQueryTest	 {

	//@Test
	public void testQuery(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_abracocultural?serverTimezone=America/Sao_Paulo", "root","q1w2e3r4");
			
			//Change the function here to another.
			//consultTable(con);
			alterTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void consultTable(Connection con){
		/*
		 String query1 = 
		         "DECLARE \n" +
		         "   sys_date DATE;"+
		         "" +
		         "BEGIN\n" +
		         "" +
		         "   SELECT SYSDATE INTO sys_date FROM dual;\n" +
		         "" +
		         "END;\n";
		*/
		
		String query2 = "select * from db_abracocultural.tb_user";
		
		try {			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			while (rs.next())
				System.out.println(rs.getLong(1) + " " + rs.getString(6));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public void alterTable(Connection con){
		
		String query = "ALTER TABLE `db_abracocultural`.`tb_contract` DROP FOREIGN KEY `FK_4h8erdrk2klse1mw4oao9m6q6` ALTER TABLE `db_abracocultural`.`tb_contract` DROP COLUMN `contractModelDomain_ID`,DROP INDEX `FK_4h8erdrk2klse1mw4oao9m6q6`";
		
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	/*
	ALTER TABLE `db_abracocultural`.`tb_contract` 
	DROP FOREIGN KEY `FK_4h8erdrk2klse1mw4oao9m6q6`;
	ALTER TABLE `db_abracocultural`.`tb_contract` 
	DROP COLUMN `contractModelDomain_ID`,
	DROP INDEX `FK_4h8erdrk2klse1mw4oao9m6q6` ;
	*/
	
	
}