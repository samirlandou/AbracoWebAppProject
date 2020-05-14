package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServlet;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import br.com.abracowebmanagement.dao.ContractDAO;
import br.com.abracowebmanagement.dao.ContractModelDAO;
import br.com.abracowebmanagement.dao.ContractSettingDAO;
import br.com.abracowebmanagement.domain.ContractDomain;
import br.com.abracowebmanagement.domain.ContractModelDomain;
import br.com.abracowebmanagement.domain.ContractSettingDomain;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.ContractUtil;
import br.com.abracowebmanagement.util.DateUtil;
import br.com.abracowebmanagement.util.NumberFormatUtil;

@ManagedBean
@ViewScoped
public class ContractController extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = 3166453786287189367L;

	
	//Logger
	//private static final Logger logger = Logger.getLogger("ContractController.class");	
	
	//Classes with Utilities
	public DateUtil dateUtil = new DateUtil();
	public ContractUtil contractUtil = new ContractUtil();
	public NumberFormatUtil numberFormatUtil = new NumberFormatUtil();
	
	private ContractDomain contractDomain;
	private List<ContractDomain> contractsDomain;
	private ContractSettingDomain contractSettingDomain = new ContractSettingDomain();
	
	//Login Controller
	LoginController loginController = new LoginController();
	
	//Create Map to store levels values
	private Map<String, String> levels;
	private Map<String, String>	firstClassDay;
	private Map<String, String>	secondClassDay;
	
	//User and Person
	private List<UserDomain> usersDomain;
	private List<PersonDomain> students;
	private List<PersonDomain> professors;
	
	
	//Skip for wizard component
	private boolean skip;
	private boolean skipRendered;
	private boolean	secondClassDayRendered;
	private boolean	saveRendered;
	
	//Contract class
	private String total;
	private String totalWeekly;
	private String contractStatus;
	private String semester;
	private String contractCode;
	private String dayClass;
	private String dayClassFullDescription;
	private String classLanguage;
	private String classPlace;
	private String contractBody;
	private String codeDescription;
	private List<ContractModelDomain> contractModelsDomain;
	
	//To calculate total time
	private String decimalRealPriceDescription;
	private String decimalProfessorPriceDescription;
	private String decimalTotalPackageRealPriceDescription;
	private String decimalTotalPackageProfessorPriceDescription;
	private int classDayQuantity;
	private boolean activeBreakClassHour;
	String pathSeparator = System.getProperty("file.separator");

	
	/**
	 * List Method. <br/>
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 */
	@PostConstruct
	public void doList(){
		try {
			
			//List Contract
			ContractDAO contractDAO = new ContractDAO();
			contractsDomain = contractDAO.list();
			
			//Get Contract Setting Parameters
			ContractSettingDAO contractSettingDAO = new ContractSettingDAO();
			contractSettingDomain = contractSettingDAO.search((long)1);
			
			
			//List first class day
			firstClassDay = contractUtil.getFullClassDayComboList();
			
			//Face Context Login
			FacesContext fcLogin = FacesContext.getCurrentInstance();
			
			//Get External Context from LoginController
			loginController = (LoginController) fcLogin.getExternalContext().getSessionMap().get("loginController");
			
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações das pessoas !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Method for New Registration. <br/>
	 * Set here new instance of Domain. <br/>
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 */
	public void doNewRegister(){		
		//Instantiate new Contract
		contractDomain = new ContractDomain();
		
		//Instantiate Contract Models
		contractModelsDomain = new ArrayList<ContractModelDomain>();
		
		//List Contract
		ContractModelDAO contractModelDAO = new ContractModelDAO();
		contractModelsDomain = contractModelDAO.list();
		
		if(contractModelsDomain.isEmpty()){
			Messages.addGlobalWarn("Favor, criar um modelo de contrato antes de continuar!");
		}
		
		//Reset Variables
		doClean();
	}


	public void doClean(){
		
		//Contract class
		total = "";
		totalWeekly = "";
		dayClass= "";
		
		//Reset price
		decimalRealPriceDescription = "";
		decimalProfessorPriceDescription = "";
		decimalTotalPackageRealPriceDescription = "";
		decimalTotalPackageProfessorPriceDescription = "";
		
		//Set Class Day Quantity to 1
		classDayQuantity = 1;
				
		//set skip
		skipRendered = false;
		skip = false;
		
		//Set Save
		saveRendered = false;
		
		//Set Break Class Hour
		activeBreakClassHour = false;
	}
	
	
	/**
	 * UpDate Level
	 */
	public void doUpdateLevel(){
		
		//Instantiate Levels
		levels = new TreeMap<String, String>();
		
		if(contractDomain != null){
			
			levels = contractUtil.getFullDescriptionLevelComboList(contractDomain.getClassLanguageDescription());
			
			//List Students
			//PersonDAO personDAO = new PersonDAO();
			//professors = personDAO.findByActiveProfessorAndLanguage(contractDomain.getClassLanguageDescription());
			//students = personDAO.findByActiveStudentAndLanguage(contractDomain.getLanguageDescription());
		}
	}
	
	
	
	public void doUpdatePrice(){
		
		if(contractDomain.getClassModuleDescription()!= null){
			
			//EXTENSIVE
			if(contractDomain.getClassModuleDescription().equals("EXTENSIVO")){
				//Real Price
				contractDomain.setRealPriceDescription(getContractSettingDomain().getExtensiveRealPriceDescription());
				
				//Professor Price
				contractDomain.setProfessorPriceDescription(getContractSettingDomain().getExtensiveProfessorPriceDescription());
			}

			
			//INTENSIVE
			if(contractDomain.getClassModuleDescription().equals("INTENSIVO")){
				//Real Price
				contractDomain.setRealPriceDescription(getContractSettingDomain().getIntensiveRealPriceDescription());
				
				//Professor Price
				contractDomain.setProfessorPriceDescription(getContractSettingDomain().getIntensiveProfessorPriceDescription());
			}
			
			
			//PRIVATE
			if(contractDomain.getClassModuleDescription().equals("PARTICULAR")){
				//Real Price
				contractDomain.setRealPriceDescription(getContractSettingDomain().getPrivateRealPriceDescription());
				
				//Professor Price
				contractDomain.setProfessorPriceDescription(getContractSettingDomain().getPrivateProfessorPriceDescription());
			}			
			
			
			//INCOMPANY
			if(contractDomain.getClassModuleDescription().equals("INCOMPANY")){
				//Real Price
				contractDomain.setRealPriceDescription(getContractSettingDomain().getInCompanyRealPriceDescription());
				
				//Professor Price
				contractDomain.setProfessorPriceDescription(getContractSettingDomain().getInCompanyProfessorPriceDescription());
			}
			
			
			//ONLINE
			if(contractDomain.getClassModuleDescription().equals("ONLINE")){
				//Real Price
				contractDomain.setRealPriceDescription(getContractSettingDomain().getInCompanyRealPriceDescription());
				
				//Professor Price
				contractDomain.setProfessorPriceDescription(getContractSettingDomain().getInCompanyProfessorPriceDescription());
			}
			
			//Format Price
			decimalRealPriceDescription = numberFormatUtil.currencyFormat(contractDomain.getRealPriceDescription());
			decimalProfessorPriceDescription = numberFormatUtil.currencyFormat(contractDomain.getProfessorPriceDescription());
						
		}		
	}
	
	
	public void doUpdateTotalPrice(){
		
		//Total Real Package Price
		decimalTotalPackageRealPriceDescription = numberFormatUtil.currencyFormat(contractDomain.getClassPackageHour() * contractDomain.getRealPriceDescription());
		contractDomain.setTotalPackageRealPriceDescription(contractDomain.getClassPackageHour() * contractDomain.getRealPriceDescription());
		
		//Total Professor Package Price
		decimalTotalPackageProfessorPriceDescription = numberFormatUtil.currencyFormat(contractDomain.getClassPackageHour() * contractDomain.getProfessorPriceDescription());
		contractDomain.setTotalPackageProfessorPriceDescription(contractDomain.getClassPackageHour() * contractDomain.getProfessorPriceDescription());
	}
	
	
	/**
	 * Update Second Class Day
	 */
	public void doUpdateSecondClassDay(){
		
		//Instantiate second class day
		secondClassDay = new TreeMap<String, String>();
		
		//List secondClassDay
		if(contractDomain.getFirstClassDayDescription() != null){
			if(contractDomain.getFirstClassDayDescription().equals("SEXTAS")
					|| contractDomain.getFirstClassDayDescription().equals("SÁBADOS")){
				secondClassDayRendered = false;
			} else{
				secondClassDay.putAll(firstClassDay);
				secondClassDay.remove(contractDomain.getFirstClassDayDescription());
				secondClassDay.remove("SEXTAS");
				secondClassDay.remove("SÁBADO");
				secondClassDayRendered = true;
			}
		}else{
			secondClassDayRendered = false;
		}
	}

	
	/**
	 * Calculate Class Hour
	 */
	public void doCalculateClassHour(){
		Date begin = contractDomain.getBeginClassHour();
		Date end = contractDomain.getEndClassHour();
		int pause;
		
		//Set Pause value
		if(activeBreakClassHour){
			pause = 15;
		} else{
			pause = 0;
		}
		
		//Set break Class Hour value
		contractDomain.setBreakClassHour(pause);
		
		if(begin != null && end != null && begin.before(end)){		

			//Set Daily Class Hour
			contractDomain.setClassTimeHour(DateUtil.returnDiffInMinutes(begin, end, pause));
			total = DateUtil.returnDiffBetweenDates2(begin, end, pause);
				
			//Set Weekly class Hour
			contractDomain.setClassWeeklyTimeHour(DateUtil.returnDiffInMinutesWithQuantity(begin, end, pause, classDayQuantity));
			totalWeekly = DateUtil.returnDiffBetweenDatesWithQuantity(begin, end, pause, classDayQuantity);

		} else{
			Messages.addGlobalError("Ocorreu um erro. Favor verificar as datas");
		}	
	}

	
	/**
	 * Create Contract Code
	 */
	public void doCreateContractCode(){
		String year = DateUtil.formatDate(new Date(), "yyyy");
		String reference = "01/06/" + year;
		Date semesterReference;
		try {
			semesterReference = DateUtil.parse(reference, "dd/mm/yyyy");
			
			//Define the Semesters
			if(contractDomain.getBeginDate().before(semesterReference)){
				semester = "1°SEM"+ year.substring(2, 4);
			} else{
				semester = "2°SEM"+ year.substring(2, 4);
			}
			
			//Define Class Day
			if(contractDomain.getSecondClassDayDescription().isEmpty()){
				
				dayClass = contractDomain.getFirstClassDayDescription();
				
				dayClassFullDescription = contractUtil.getShortClassDayDescription(contractDomain.getFirstClassDayDescription());
			} else{
				
				dayClass = contractUtil.getShortClassDayDescription(contractDomain.getFirstClassDayDescription())
						+ "_"
						+ contractUtil.getShortClassDayDescription(contractDomain.getSecondClassDayDescription());
				
				dayClassFullDescription =  contractDomain.getFirstClassDayDescription()
						+ " e "
						+ contractDomain.getSecondClassDayDescription();
				
				classDayQuantity = 2;
				
			}
			
			
			//Code Description Example: 1°SEM19_EN1_EXT_PINH_SEG_TER_19H00_20H30
			
			//Define Begin and End Hour
			String classHour = DateUtil.formatDate(contractDomain.getBeginClassHour(), "HH:mm")
								+ "_"
								+ DateUtil.formatDate(contractDomain.getEndClassHour(), "HH:mm");
			
			//Creating Contract Code 
			if(getCodeDescription().trim() != ""){
				contractCode = semester
								+ "_"
								+ contractUtil.getShortDescriptionLevel(contractDomain.getClassLevelDescription(), contractDomain.getClassLanguageDescription()) 
								+ "_"
								+ contractUtil.getShortModuleClassDescription(contractDomain.getClassModuleDescription())
								+ "_"
								+ contractUtil.getShortPlaceDescription(contractDomain.getClassPlaceDescription())
								+ "_"
								+ dayClass
								+ "_"
								+ classHour.replace(":", "H")
								+"_"
								+ getCodeDescription().trim().toUpperCase();
				
			} else{
				contractCode = semester
								+ "_"
								+ contractUtil.getShortDescriptionLevel(contractDomain.getClassLevelDescription(), contractDomain.getClassLanguageDescription()) 
								+ "_"
								+ contractUtil.getShortModuleClassDescription(contractDomain.getClassModuleDescription())
								+ "_"
								+ contractUtil.getShortPlaceDescription(contractDomain.getClassPlaceDescription())
								+ "_"
								+ dayClass
								+ "_"
								+ classHour.replace(":", "H");				
			}

			
			contractDomain.setContractCodeDescription(contractCode);
							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 */
	public void doSave(){
		
		//Save Contract with merge method
		
		ContractDAO contractDAO = new ContractDAO();
		contractDAO.findByContractName(contractDomain.getContractCodeDescription());
		
		try {
			
        	if(contractDomain.getClassPackageHour() == 0){
        		//Return error message when package is null.
        		Messages.addGlobalError("O pacote tem que ser maior ou igual a '1'. Favor volte na Aba 'Curso'");
        		
        	} else if(contractDAO.findByContractName(contractDomain.getContractCodeDescription()).size() > 0){
        		//Return error message when contract code exist.
        		Messages.addGlobalError("Esse Contrato já existe.");
        		
        	} else{
			
				//Save Actual Date
				contractDomain.setSaveContractDate(new Date());
				
				//Set LoginUser
				contractDomain.setContractSaveLoginUser(loginController.getLoggedUser().getUserName());
				
				//Set closed Flag
				contractDomain.setClosedContractFlag(false);
				
				//Merge information
				contractDAO.merge(contractDomain);
				
				//Clean informations in the panelGrid
				doNewRegister();
				
				//List again Contract (very import to update the list)
				contractsDomain = contractDAO.list();
				
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");
				
				//Reset Variables
				doClean();
        	}
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações do contrato!");
			e.printStackTrace();
		}		
	}

	
	/**
	 * Delete Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 19/12/2019
	 */
	public void doDelete(ActionEvent event){
		try {
			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes()
					.get("selectedContractByCursor");

			//Delete Contract
			ContractDAO contractDAO = new ContractDAO();
			contractDAO.delete(contractDomain);
						
			//List again Contract (very import to update the list)
			contractsDomain= contractDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(contractDomain.getContractCodeDescription() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar pois os dados de " + contractDomain.getContractCodeDescription() + " está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: " + contractDomain.getContractCodeDescription());
			}
			e.printStackTrace();			
		}
	}

	
	/**
	 * Message for Opened/Closed Contract
	 */
	public void addMessage() {
		//Add Message for toggleSwitch Component from contract.xhtml
		String summary = contractDomain.getClosedContractFlag() ? "Ativo(a)" : "Desativado(a)";
		Messages.addGlobalInfo(summary);
	}

	
	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 19/12/2019
	 */
	public void doEdit(ActionEvent event){
		
		//Set skip
		skip = false;
		skipRendered = true;
		
		try {			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + contractDomain.getContractCodeDescription());
			e.printStackTrace();			
		}		
	}

	
	
	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 19/12/2019
	 */
	public void doContractDetailEdit(ActionEvent event){
		
		//Set skip
		skip = false;
		skipRendered = true;
		
		try {			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + contractDomain.getContractCodeDescription());
			e.printStackTrace();			
		}		
	}	

	
	/**
	 * Show Report using DataBase Connection</br>
	 * @param event
	 */
    public void doShowContractReport(ActionEvent event) {

		//Capture the event from the cursor in contract.xhtml
		contractDomain = (ContractDomain) event.getComponent().getAttributes()
				.get("selectedContractByCursor");
    	
		//Get Report Path to be used in a FileStream
		String reportPath =  Faces.getRealPath("reports/contract_professor.jasper");    	
    	  	
		//Get Report's Image (LOGO) Path 
		String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
				
		//Set Report Name
		String reportName = "Contrato";
		
		//Create Map to store parameters
		Map<String, Object> parameters = new HashMap<>();
		
		//Define Map's Parameters
		parameters.put("Parameter1", "texto do Paramêtro");
		parameters.put("abracoLogo", imagePath);    	
    	
    	//Instantiate  the Report Controller
        ReportController relatorio = new ReportController();
        
        //Export Report as PDF Stream
        relatorio.showReport(reportPath, parameters, reportName);
    }


	/**
	 * Show OR Download OR Print Report Without Data Base Connection.</br>
	 * @param event
	 */
    public void doContractReportByProfessor(ActionEvent event) {


    	//-------------- Get UIComponents values ----------------------------------
    	
		//Capture the event from the cursor in contract.xhtml
		contractDomain = (ContractDomain) event.getComponent().getAttributes()
				.get("selectedContractByCursor");
		
		//Capture DataTable components values using Faces.getViewRoot().findComponent("idForm:idComponent");
		//DataTable dataTable = (DataTable) Faces.getViewRoot().findComponent("contractListFormID:contractDataTableFormID");
		
		//Get Components values
		//String component = dataTable.getId();
		
		String commandButtonID = event.getComponent().getClientId();

		
		
		//-------------- Set Parameters values ----------------------------------	
		
		//Get Report Path to be used in a FileStream
		String reportPath =  Faces.getRealPath("reports/report1.jasper");    	
    	  	
		//Get Report's Image (LOGO) Path 
		//String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
				
		//Set Report Name
		String reportName = "Contrato_" /*+ contractDomain.getPersonDomain().getCompleteName()*/;
		
		//Create Map to store parameters
		Map<String, Object> parameters = new HashMap<>();
		
		//Define Map's Parameters
		parameters.put("ContractBody", getContractBody());
		//parameters.put("AbracoLogo", imagePath);
    	

		
		//-------------- Set Report values ----------------------------------
		
    	//Instantiate  the Report Controller
        ReportController relatorio = new ReportController();
        
        //Choose Action according to the ID of component      
        if(commandButtonID.contains("showContractID")){       	
	        //Show Report as PDF Stream
	        relatorio.showReportWithoutConnection(reportPath, parameters, reportName);      	
        } else if(commandButtonID.contains("downloadContractID")){
	        //Download Report as PDF
	        relatorio.downloadReportWithoutConnection(reportPath, parameters, reportName);         	
        } else if(commandButtonID.contains("printContractID")){
	        //Just Print Report
	        relatorio.printReportWithoutConnection(reportPath, parameters, reportName);
        } else{
        	return;
        }
    }
    
    
    /**
     * Download Report</br>
     * @param event
     */
    public void doDownloadContractReport(ActionEvent event) {

		//Capture the event from the cursor in contract.xhtml
		contractDomain = (ContractDomain) event.getComponent().getAttributes()
				.get("selectedContractByCursor");
    	
		//Get Report Path to be used in a FileStream
		String reportPath =  Faces.getRealPath("reports/contract_professor.jasper");    	
    	  	
		//Get Report's Image (LOGO) Path 
		String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
				
		//Set Report Name
		String reportName = "Contrato";
		
		//Create Map to store parameters
		Map<String, Object> parameters = new HashMap<>();
		
		//Define Map's Parameters
		parameters.put("Parameter1", "texto do Paramêtro");
		parameters.put("abracoLogo", imagePath);    	
    	
    	//Instantiate  the Report Controller
        ReportController relatorio = new ReportController();
        
        //Export Report as PDF Stream
        relatorio.showReport(reportPath, parameters, reportName);
    }
    
    
    
	/**
	 * Flow Process to fill forms
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 * @param event
	 * @return
	 */
    public String doContractFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
        	
        	if(event.getNewStep().equals("contractModelTabID")){       		
        		if(contractModelsDomain.isEmpty()){
        			Messages.addGlobalWarn("Favor, criar um modelo de contrato antes de continuar!");
        		}
        	}
        	
        	if(event.getNewStep().equals("resumeTabID")){
        		//saveRendered = true;
        		doCreateContractCode();
        		doCalculateClassHour();
        		doUpdatePrice();
        		doUpdateTotalPrice();
        	}

            return event.getNewStep();
        }
    }
   
 
	/**
	 * Flow Process to fill forms
	 * @author samirlandou <br/>
	 * @since 21/04/2020
	 * @param event
	 * @return
	 */
    public String doContractDetailFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
        	
        	/*if(event.getNewStep().equals("contractModelTabID")){       		
        		if(contractModelsDomain.isEmpty()){
        			Messages.addGlobalWarn("Favor, criar um modelo de contrato antes de continuar!");
        		}
        	}*/
        	
        	/*if(event.getNewStep().equals("resumeTabID")){

        	}*/

            return event.getNewStep();
        }
    }    
    
    
    
    /**
     * Contract Message
     */
	public void addContractMessage() {
		//Add Message for toggleSwitch Component
		contractStatus = contractDomain.getClosedContractFlag() ? "Contrato Finalizado!" : "Contrato Ativo!";
		Messages.addGlobalInfo(contractStatus);
	}

	
    /**
     * Contract Break Class Hour Message
     */
	public void addContractBreakClassHourMessage() {
		//Add Message for selectBooleanCheckBox Component
		Messages.addGlobalInfo(activeBreakClassHour ? "Aula com Pausa de 15mn" : "Aula sem Pausa!");
	}

	
	/**
	 * Success Message
	 * @param successEvent
	 */
	public void successListener(final ClipboardSuccessEvent successEvent){
		Messages.addGlobalInfo("O código foi copiado com sucesso!");
	}
	
	
	/**
	 * Error Message
	 * @param errorEvent
	 */
	public void errorListener(final ClipboardErrorEvent errorEvent){
		Messages.addGlobalError("Erro ao copiar o código do contrato!");
	}

	
    /*	
	public void printProfessorContract(){

		try {
			
			//Get report path and the image. 
			String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
			String reportPath =  Faces.getRealPath("reports/contract_professor.jasper");
			
			//Create HashMap to store parameters
			Map<String, Object> parameters = new HashMap<>();
			
			//Define Parameters
			parameters.put("Parameter1", "texto do Paramêtro");
			parameters.put("abracoLogo", imagePath);
			
			
			//Get a connection from session
			Connection connection = HibernateUtil.getOpenConnection();
			
			//Invoke Print Report
			JasperPrint myReport = JasperFillManager.fillReport(reportPath, parameters, connection);
			
			//Print Report
			//JasperPrintManager.printReport(myReport, true);
			
			//Save Report in PDF
			//JasperViewer.viewReport(myReport);
			
			try {
				//Export
				JasperPrintManager.printReport(myReport, false);
			} catch (Exception e) {
				Messages.addGlobalError("O contrato PDF não foi salvo.");
			}
			
			
		} catch (JRException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			e.printStackTrace();
		}	
	}*/
	
	
    /*public void doShowReport() {
    	
    	//Print the Report
        ReportController relatorio = new ReportController();
        
        //Export Report as PDF Stream
        relatorio.getReport();
    }*/ 

	
	
	
	
	
	/*
	 * Getters and Setters
	 */
	
	public ContractDomain getContractDomain() {
		return contractDomain;
	}


	public void setContractDomain(ContractDomain contractDomain) {
		this.contractDomain = contractDomain;
	}


	public boolean isSkip() {
		return skip;
	}


	public void setSkip(boolean skip) {
		this.skip = skip;
	}


	public List<ContractDomain> getContractsDomain() {
		return contractsDomain;
	}


	public void setContractsDomain(List<ContractDomain> contractsDomain) {
		this.contractsDomain = contractsDomain;
	}


	public Map<String, String> getLevels() {
		return levels;
	}


	public void setLevels(Map<String, String> levels) {
		this.levels = levels;
	}


	public List<UserDomain> getUsersDomain() {
		return usersDomain;
	}


	public void setUsersDomain(List<UserDomain> usersDomain) {
		this.usersDomain = usersDomain;
	}


	public List<PersonDomain> getStudents() {
		return students;
	}


	public void setStudents(List<PersonDomain> students) {
		this.students = students;
	}


	public List<PersonDomain> getProfessors() {
		return professors;
	}


	public void setProfessors(List<PersonDomain> professors) {
		this.professors = professors;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public boolean isSkipRendered() {
		return skipRendered;
	}


	public void setSkipRendered(boolean skipRendered) {
		this.skipRendered = skipRendered;
	}


	public Map<String, String> getSecondClassDay() {
		return secondClassDay;
	}


	public void setSecondClassDay(Map<String, String> secondClassDay) {
		this.secondClassDay = secondClassDay;
	}


	public Map<String, String> getFirstClassDay() {
		return firstClassDay;
	}


	public void setFirstClassDay(Map<String, String> firstClassDay) {
		this.firstClassDay = firstClassDay;
	}


	public boolean isSecondClassDayRendered() {
		return secondClassDayRendered;
	}


	public void setSecondClassDayRendered(boolean secondClassDayRendered) {
		this.secondClassDayRendered = secondClassDayRendered;
	}


	public String getContractStatus() {
		return contractStatus;
	}


	public void setContractStatus(String summary) {
		this.contractStatus = summary;
	}


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getContractCode() {
		return contractCode;
	}


	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}


	public String getDayClass() {
		return dayClass;
	}


	public void setDayClass(String dayClass) {
		this.dayClass = dayClass;
	}


	public String getClassLanguage() {
		return classLanguage;
	}


	public void setClassLanguage(String language) {
		this.classLanguage = language;
	}


	public String getClassPlace() {
		return classPlace;
	}


	public void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}


	public String getDayClassFullDescription() {
		return dayClassFullDescription;
	}


	public void setDayClassFullDescription(String dayClassFullDescription) {
		this.dayClassFullDescription = dayClassFullDescription;
	}


	public String getDecimalRealPriceDescription() {
		return decimalRealPriceDescription;
	}


	public void setDecimalRealPriceDescription(String decimalPriceDescription) {
		this.decimalRealPriceDescription = decimalPriceDescription;
	}


	public boolean isSaveRendered() {
		return saveRendered;
	}


	public void setSaveRendered(boolean saveRendered) {
		this.saveRendered = saveRendered;
	}


	public String getContractBody() {
		return contractBody;
	}


	public void setContractBody(String contractBody) {
		this.contractBody = contractBody;
	}


	public List<ContractModelDomain> getContractModelsDomain() {
		return contractModelsDomain;
	}


	public void setContractModelsDomain(List<ContractModelDomain> contractModelsDomain) {
		this.contractModelsDomain = contractModelsDomain;
	}


	public String getDecimalProfessorPriceDescription() {
		return decimalProfessorPriceDescription;
	}


	public void setDecimalProfessorPriceDescription(String decimalProfessorPriceDescription) {
		this.decimalProfessorPriceDescription = decimalProfessorPriceDescription;
	}


	public ContractSettingDomain getContractSettingDomain() {
		return contractSettingDomain;
	}


	public void setContractSettingDomain(ContractSettingDomain contractSettingDomain) {
		this.contractSettingDomain = contractSettingDomain;
	}


	public String getDecimalTotalPackageRealPriceDescription() {
		return decimalTotalPackageRealPriceDescription;
	}


	public void setDecimalTotalPackageRealPriceDescription(String decimalTotalPackageRealPriceDescription) {
		this.decimalTotalPackageRealPriceDescription = decimalTotalPackageRealPriceDescription;
	}


	public String getCodeDescription() {
		return codeDescription;
	}


	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}


	/*public ContractModelDomain getContractModelDomain() {
		return contractModelDomain;
	}


	public void setContractModelDomain(ContractModelDomain contractModelDomain) {
		this.contractModelDomain = contractModelDomain;
	}*/


	public String getDecimalTotalPackageProfessorPriceDescription() {
		return decimalTotalPackageProfessorPriceDescription;
	}


	public void setDecimalTotalPackageProfessorPriceDescription(String decimalTotalPackageProfessorPriceDescription) {
		this.decimalTotalPackageProfessorPriceDescription = decimalTotalPackageProfessorPriceDescription;
	}


	public String getTotalWeekly() {
		return totalWeekly;
	}


	public void setTotalWeekly(String totalWeekly) {
		this.totalWeekly = totalWeekly;
	}


	public int getClassDayQuantity() {
		return classDayQuantity;
	}


	public void setClassDayQuantity(int classDayQuantity) {
		this.classDayQuantity = classDayQuantity;
	}


	public boolean isActiveBreakClassHour() {
		return activeBreakClassHour;
	}


	public void setActiveBreakClassHour(boolean activeBreakClassHour) {
		this.activeBreakClassHour = activeBreakClassHour;
	}	
	
}
