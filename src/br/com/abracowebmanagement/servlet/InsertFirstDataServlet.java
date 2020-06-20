package br.com.abracowebmanagement.servlet;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import br.com.abracowebmanagement.dao.ContractSettingDAO;
import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.contract.ContractSettingDomain;
import br.com.abracowebmanagement.domain.person.PersonDomain;
import br.com.abracowebmanagement.domain.user.UserDomain;

@SuppressWarnings("serial")
public class InsertFirstDataServlet extends HttpServlet{		
		
		public void init() throws ServletException{
		
		PersonDomain personDomain = new PersonDomain();
		PersonDomain personDomain2 = new PersonDomain();
		
		boolean insertUserDataFlag = false;
		
		//Set Person data
		personDomain.setAddress("Endereço da empresa");
		personDomain.setCompleteName("Nome da Empresa");
		personDomain.setCountry("Brasil");
		personDomain.setCpf("697.458.687-67");
		personDomain.setEmail("email@empresa.com.br");
		personDomain.setId(1L);
		personDomain.setLanguage1("BR");
		personDomain.setLanguage2("BR");
		personDomain.setLanguage3("BR");
		personDomain.setProfile("ADMINISTRADOR(A)");
		personDomain.setSex('O');
		personDomain.setStatus(true);
		personDomain.setTelephone("(11)32323-2323");
		PersonDAO personDAO = new PersonDAO();
		
		//Check data in database.
		personDomain2 = personDAO.search((long) 1);
		
		//Check if first data Exist
		if(personDomain2 != null && personDomain2.getProfile().equals("ADMINISTRADOR(A)")){
			
			//Inform Person that data exist.
			System.out.println("Start checking first data...");
			
		} else{

			//Save data.
			personDAO.save(personDomain);
			
			//Enable Insert User Data.
			insertUserDataFlag = true;
			
			//Inform that Person data has been inserted.
			System.out.println("First 'Person' data has been inserted successfully!");
		}

		
		/**
		 * 
		 */
		
		
		if (insertUserDataFlag) {
			
			//Instantiate UserDomain.
			UserDomain userDomain = new UserDomain();
			
			//Set User Data.
			userDomain.setCnpj("23.656.821/0001-86");
			userDomain.setId(1L);
			userDomain.setImageUserFileName("");
			userDomain.setImageUserPath("");
			userDomain.setPassword("MTIzNDU2");
			userDomain.setPersonDomain(personDomain);
			userDomain.setRememberPassword("Lembrar a senha da Empresa");
			userDomain.setUserName("admin");
			userDomain.setUserTheme("ui-lightness");
			userDomain.setPasswordWithoutCryptography("");
			userDomain.setTeachingLanguage1("PT");
			
			//Save User Data.
			UserDAO userDAO = new UserDAO();
			userDAO.save(userDomain);

			//Inform that User data has been inserted.
			System.out.println("First 'User' data has been inserted successfully!");		

			
			/**
			 * 
			 * 
			 * */			

			
			//Instantiate ContractSettingDomain.
			ContractSettingDomain contractSettingDomain = new ContractSettingDomain();
			
			//Set ContractSetting Data.
			contractSettingDomain.setContractSettingSaveLoginUser("abraco");
			contractSettingDomain.setExtensiveProfessorPriceDescription(60);
			contractSettingDomain.setExtensiveRealPriceDescription(75);
			contractSettingDomain.setId(1L);
			contractSettingDomain.setInCompanyProfessorPriceDescription(60);
			contractSettingDomain.setInCompanyRealPriceDescription(80);
			contractSettingDomain.setIntensiveProfessorPriceDescription(60);
			contractSettingDomain.setIntensiveRealPriceDescription(75);
			contractSettingDomain.setOnlineProfessorPriceDescription(60);
			contractSettingDomain.setOnlineRealPriceDescription(75);
			contractSettingDomain.setPrivateProfessorPriceDescription(55);
			contractSettingDomain.setPrivateRealPriceDescription(75);
			contractSettingDomain.setSaveContractSettingDate(new Date());
						
			//Save ContractDomain Data.
			ContractSettingDAO contractSettingDAO = new ContractSettingDAO();
			contractSettingDAO.save(contractSettingDomain);	
			
			//Inform that data has been inserted.
			System.out.println("First 'ContractSetting' data has been inserted successfully!");			

			
			/**
			 * 
			 */

			
			//Disable insertUserDataFlag
			insertUserDataFlag = false;
			
		} else{
			
			//Inform the end.
			System.out.println("End checking first data.");
		}

	}

}
