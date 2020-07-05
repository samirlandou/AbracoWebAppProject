package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServlet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import br.com.abracowebmanagement.dao.ContractDAO;
import br.com.abracowebmanagement.dao.ContractModelDAO;
import br.com.abracowebmanagement.dao.ContractSettingDAO;
import br.com.abracowebmanagement.dao.ContractStudentDAO;
import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.dao.UserDAO;
import br.com.abracowebmanagement.domain.ContractDomain;
import br.com.abracowebmanagement.domain.ContractModelDomain;
import br.com.abracowebmanagement.domain.ContractSettingDomain;
import br.com.abracowebmanagement.domain.ContractStudentDomain;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.ContractUtil;
import br.com.abracowebmanagement.util.DateUtil;
import br.com.abracowebmanagement.util.MethodUtil;
import br.com.abracowebmanagement.util.NumberFormatUtil;


/**
 * Contract Class
 * @author samirlandou
 * @since 19/12/2019
 */
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
	public MethodUtil methodUtil =  new MethodUtil();
	
	//Domain
	private ContractDomain contractDomain;
	private PersonDomain newPersonDomain;
	private ContractStudentDomain contractStudentDomain = new ContractStudentDomain();
	private ContractSettingDomain contractSettingDomain = new ContractSettingDomain();
	
	//list<Domain>
	private List<ContractDomain> contractsDomain = 	new ArrayList<ContractDomain>();
	private List<ContractStudentDomain> contractStudentsDomain = new ArrayList<ContractStudentDomain>();;
	
	//Controller
	LoginController loginController = new LoginController();
	
	//Create Map to store levels values
	private Map<String, String> levels;
	private Map<String, String>	firstClassDay;
	private Map<String, String>	secondClassDay;
	private Map<String, String>	paymentType;
	
	//User and Person
	private List<UserDomain> usersDomain;
	private List<PersonDomain> students;
	private List<UserDomain> professors;	
	
	//Skip for wizard component
	private boolean skip;
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
	private String personCPF;
	private List<ContractModelDomain> contractModelsDomain;
	
	//To calculate total time
	private String decimalRealPriceDescription;
	private String decimalProfessorPriceDescription;
	private String decimalTotalPackageRealPriceDescription;
	private String decimalTotalPackageProfessorPriceDescription;
	private int classDayQuantity;
	private boolean activeBreakClassHour;
	private boolean saveStudentFlag;
	
	//Save Button in Contract Student Dialog
	private boolean enableComponentOnContractStudent;
	
	//Hash Map
	Map<String, String> map = new HashMap<String, String>();
	
	String pathSeparator = System.getProperty("file.separator");

	
	/**
	 * Post Construct Method
	 */
	@PostConstruct
	public void doList(){
		try {
			
			//List Contract
			ContractDAO contractDAO = new ContractDAO();
			contractsDomain = contractDAO.descendList("id");
			
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
			Messages.addGlobalError("Ocorreu um erro ao listar as informações dos contratos !!!");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * New Contract
	 */
	public void doNewRegister(){	
		
		//Instantiate new Contract
		contractDomain = new ContractDomain();
		
		//Instantiate Contract Models
		contractModelsDomain = new ArrayList<ContractModelDomain>();
		
		//List Contract
		ContractModelDAO contractModelDAO = new ContractModelDAO();
		contractModelsDomain = contractModelDAO.descendList("id");
		
		if(contractModelsDomain.isEmpty()){
			Messages.addGlobalWarn("Favor, criar um modelo de contrato antes de continuar!");
		}
		
		//Reset Variables
		doClean();
	}


	/**
	 * Clean values
	 */
	public void doClean(){
		
		//Instantiate new Contract
		contractDomain = new ContractDomain();
		
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
		skip = false;
		
		//Set Save
		saveRendered = false;
		
		//Set Break Class Hour
		activeBreakClassHour = false;
		
		//Save Student
		saveStudentFlag = false;
		
		//Set Day Class Full Description
		dayClassFullDescription ="";		
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
	
	
	/**
	 * Update Price
	 */
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
	
	
	/**
	 * Update Total Price
	 */
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
	 * Save Contract
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
			
        		//Set Contract Code Description to UpperCase
        		contractDomain.setContractCodeDescription(contractDomain.getContractCodeDescription().toUpperCase());
        		
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
				
				//Instantiate Contracts
				contractsDomain = new ArrayList<ContractDomain>();
				
				//List again Contract (very import to update the list)
				contractsDomain = contractDAO.descendList("id");
				
				//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
				Messages.addGlobalInfo("Salvou com sucesso!");
				
				//Reset Variables
				//doClean();
        	}
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações do contrato!");
			e.printStackTrace();
		}		
	}

	
	/**
	 * Save Contract
	 * @author samirlandou
	 */
/*	public void doSaveFromEdit(){
		
		//Save Contract with merge method
		
		ContractDAO contractDAO = new ContractDAO();
		
		try {
		
			//Save Actual Date
			contractDomain.setSaveContractDate(new Date());
			
			//Set LoginUser
			contractDomain.setContractSaveLoginUser(loginController.getLoggedUser().getUserName());
			
			//Set closed Flag
			contractDomain.setClosedContractFlag(false);
			
			//Merge information
			contractDAO.merge(contractDomain);
			
			//Clean informations in the panelGrid
			//doNewRegister();
			
			//List again Contract (very import to update the list)
			contractsDomain = contractDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso!");
			
			//Reset Variables
			//doClean();
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações do contrato!");
			e.printStackTrace();
		}		
	}*/

	
	
	/**
	 * Delete Contract
	 * @param event
	 */
	public void doDelete(ActionEvent event){
		try {
			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes()
					.get("selectedContractByCursor");
			
			//Search for ContractStudent with contract as Foreign Key
			List<ContractStudentDomain> searchContractStudentToBeDeleted = new ArrayList<ContractStudentDomain>();
			
			//Delete Student
			ContractStudentDAO contractStudentDAO = new ContractStudentDAO();
			
			//List contractStudent
			searchContractStudentToBeDeleted = contractStudentDAO.listByContractCodeDescription(contractDomain.getContractCodeDescription());
			
			//Delete all data form contracStudentDomain which contain contractDomain as Foreign Key
			contractStudentDAO.deleteListOfEntities(searchContractStudentToBeDeleted);

			//Delete Contract
			ContractDAO contractDAO = new ContractDAO();
			contractDAO.delete(contractDomain);			
			
			//Instantiate Contracts
			contractsDomain = new ArrayList<ContractDomain>();
			
			//List again Contract (very import to update the list)
			contractsDomain = contractDAO.descendList("id");
			
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
	 * DeleteStudent Contract
	 * @param event
	 */
	public void doDeleteStudentContract(ActionEvent event){
		try {
			
			//Capture the event from the cursor in contract.xhtml
			contractStudentDomain = (ContractStudentDomain) event.getComponent().getAttributes()
					.get("selectedContractByCursor");

			//Delete Student
			ContractStudentDAO contractStudentDAO = new ContractStudentDAO();
			contractStudentDAO.delete(contractStudentDomain);
			
			//Instantiate ContractStudent
			contractStudentsDomain = new ArrayList<ContractStudentDomain>();
			
			//List again Contract Student (very import to update the list)
			contractStudentsDomain = contractStudentDAO.listByContractCodeDescription(contractDomain.getContractCodeDescription());
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo(contractStudentDomain.getPersonDomain().getCompleteName() + " foi excluido com sucesso!!!");
		
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar pois os dados de \"" + contractStudentDomain.getPersonDomain().getCompleteName() + "\" está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: \"" + contractStudentDomain.getPersonDomain().getCompleteName() + "\"");
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
	 * Edit Student Contract
	 * @param event
	 */
	public void doEditStudentContract(ActionEvent event){
		
		try {		
			
			//Set enable component Value
			enableComponentOnContractStudent = true;			
			
			//Capture the event from the cursor in contract.xhtml
			contractStudentDomain = (ContractStudentDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
			
			//set personCPF
			personCPF= contractStudentDomain.getPersonDomain().getCpf();
			
			//Set NewContract
			newPersonDomain = contractStudentDomain.getPersonDomain();
			
			//List PaymentType
			doDisableStudentPaymentTypeSelectItem();
			
			//list Student
			//doListStudents(contractDomain);
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações desse(a) Aluno(a).");
			e.printStackTrace();			
		}		
	}	

	
	/**
	 * Edit New Student Contract
	 * @param event
	 */
	public void doEditNewStudentContract(ActionEvent event){
		
		try {
			
			//Set enable component Value
			enableComponentOnContractStudent = false;
			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
			
			//Clean Student Info
			doCleanStudentInfo();
			
			//list Student
			doListStudents(contractDomain);
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de \"" + contractDomain.getContractCodeDescription() +"\"");
			e.printStackTrace();			
		}		
	}
	

	/**
	 * Clean Student Informations while trying
	 * to do new Student register
	 */
	public void doCleanStudentInfo(){
		
		//Instantiate constractStudent
		contractStudentDomain = new ContractStudentDomain();
		
		//Instantiate newPersonDomain
		newPersonDomain = new PersonDomain();
		
		//Set personCPF
		personCPF = "";
		
		//Set enable component Value
		enableComponentOnContractStudent = false;
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
		String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
				
		//Set Report Name
		String reportName = "Contrato_" + contractDomain.getFirstSubstituteProfesssorPersonDomain().getUserName().replaceAll(".", "");
		
		//Create Map to store parameters
		Map<String, Object> parameters = new HashMap<>();
		
		//Get contract Model Professor Contract Body and Convert Bytes into String
		contractBody = new String(contractDomain.getProfessorContractModelDomain().getContractModelDescription(), Charset.defaultCharset());
		
		//Load replace Map
    	mapParameters(parameters);
    	
    	//replaceMultiple(String target, Map<String, String> replacements, boolean caseSensitive)
    	
		
		//Define Map's Parameters
		//parameters.put("ContractBody", /*doGetRealContractBody(contractBody)*/contractDomain.getContractCodeDescription());
		parameters.put("abracoLogo", imagePath);
    	

		
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
            return "languageTabID";
        } else {
        	
        	if(event.getNewStep().equals("contractModelTabID")){       		
        		if(contractModelsDomain.isEmpty()){
        			Messages.addGlobalWarn("Favor, criar um modelo de contrato antes de continuar!");
        			return event.getOldStep();
        		}
        	}
        	
        	if(event.getNewStep().equals("placeTabID")){
        		
        		if(contractDomain.getBeginDate().after(contractDomain.getEndDate())){
        			Messages.addGlobalWarn("A data do início informado é superior à data de término.");
        			return event.getOldStep();
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
   
 
    public String doGetRealContractBody(String body){
		
    	
        Velocity.init();

        VelocityContext context = new VelocityContext();
        /*context.put("name", "Mark");
        context.put("invoiceNumber", "42123");
        context.put("dueDate", "June 6, 2009");*/

        /**
         * Person who is contracting TAG
         */
        context.put("PROFESSOR_CURSO", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCompleteName());
        context.put("E-MAIL_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getEmail());
        context.put("TELEFONE_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getTelephone());
        context.put("CPF_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCpf());
        context.put("CNPJ_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getCnpj());
        context.put("ENDERECO_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getAddress());
        
    	/**
    	 * Course TAG
    	 */	
        context.put("CODIGO_CONTRATO", contractDomain.getContractCodeDescription());
	   	context.put("CODIGO_CONTRATO", contractDomain.getContractCodeDescription());	   	 
	   	context.put("UNIDADE_CURSO", methodUtil.toCamelCase(contractDomain.getClassPlaceDescription()));
	   	context.put("MODULO_CURSO", contractDomain.getClassModuleDescription().toLowerCase());	   		
	   	context.put("LINGUA_CURSO", methodUtil.toCamelCase(contractDomain.getClassLanguageDescription()));
	   	context.put("NIVEL_CURSO", contractDomain.getClassLevelDescription());
	   	context.put("HORA_INICIO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getBeginClassHour()));
	   	context.put("HORA_TERMINO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getEndClassHour()));
	   	context.put("PAUSA_CURSO", 
	   				contractDomain.getBreakClassHour() == 0? "sem pausa" : "com uma pausa de " + dateUtil.formatDurationTime(contractDomain.getBreakClassHour()));
	   	context.put("TEMPO_CURSO", dateUtil.formatDurationTime(contractDomain.getClassTimeHour()));
	   	context.put("HORA_SEMANAL_CURSO", dateUtil.formatDurationTime(contractDomain.getClassWeeklyTimeHour()));
	   	context.put("PRIMEIRO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getFirstClassDayDescription()));
	   	context.put("SEGUNDO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getSecondClassDayDescription()));	   	
	   	
	   	//Change after this value
	   	context.put("PRECO_PADRAO_CURSO", methodUtil.currencyFormat(contractDomain.getRealPriceDescription()));  	
	   	
	   	//Change after this value
	   	context.put("PRECO_PROFESSOR_CURSO", methodUtil.currencyFormat(contractDomain.getProfessorPriceDescription()));
	   	context.put("PACOTE_HORA_CURSO", String.valueOf(contractDomain.getClassPackageHour())+"h");	
	   		   	
	   	//Change after this value
	   	context.put("PRECO_PROFESSOR_TOTAL_CURSO", methodUtil.currencyFormat(contractDomain.getTotalPackageProfessorPriceDescription()));
	   	
	   	
	   	/**
	   	 * DeadLine
	   	 */
	   	 	   		
	   	context.put("DATA_INICIO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getBeginDate()));	  		
	   	context.put("DATA_TERMINO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getEndDate()));   	
	   	
	   	
	   	/**
	   	 * Payment
	   	 */ 	   	 
	  	/*if(replaceContractBody.contains("TIPO_PAGAMENTO")){
	  		context.put("TIPO_PAGAMENTO", "");
	  	}*/	
	   	
	  	/*if(replaceContractBody.contains("FORMA_PAGAMENTO")){
  		context.put("TIPO_PAGAMENTO", "");
  		}*/
        
	   	
        String template = body;
        
        /*String template = "Hello $name. Please find attached invoice" +
                          " $invoiceNumber which is due on $dueDate.";*/
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "testeTemplateName", template);

        return writer.toString();
    	
    	
    	
	    	/**
	    	 * Person who is contracting TAG
	    	 */	
	    	/*if(replaceContractBody.contains("PROFESSOR_CURSO")){
	    		replaceContractBody = replaceContractBody.replaceAll("PROFESSOR_CURSO", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCompleteName());
	    	}
	    	
	    	if(replaceContractBody.contains("E-MAIL_PESSOA")){
	    		replaceContractBody = replaceContractBody.replaceAll("E-MAIL_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getEmail());
	    	}   	
	 
	    	if(replaceContractBody.contains("TELEFONE_PESSOA")){
	    		replaceContractBody = replaceContractBody.replaceAll("TELEFONE_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getTelephone());
	    	} 
	    	 
	    	if(replaceContractBody.contains("CPF_PESSOA")){
	    		replaceContractBody = replaceContractBody.replaceAll("CPF_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCpf());
	    	}     	
		   	 
		   	if(replaceContractBody.contains("CNPJ_PESSOA")){
		   		replaceContractBody = replaceContractBody.replaceAll("CNPJ_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getCnpj());
		   	}
		   	   	 
		   	if(replaceContractBody.contains("ENDERECO_PESSOA")){
		   		replaceContractBody = replaceContractBody.replaceAll("ENDERECO_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getAddress());
		   	}  	*/
	
		   	
	    	/**
	    	 * Course TAG
	    	 */	   	 
		   /*	if(replaceContractBody.contains("CODIGO_CONTRATO")){
		   		replaceContractBody = replaceContractBody.replaceAll("CODIGO_CONTRATO", contractDomain.getContractCodeDescription());
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("UNIDADE_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("UNIDADE_CURSO", methodUtil.toCamelCase(contractDomain.getClassPlaceDescription()));
		   	}
		   	 
		   	if(replaceContractBody.contains("MODULO_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("MODULO_CURSO", contractDomain.getClassModuleDescription().toLowerCase());
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("LINGUA_CURSO")){	   		
		   		replaceContractBody = replaceContractBody.replaceAll("LINGUA_CURSO", methodUtil.toCamelCase(contractDomain.getClassLanguageDescription()));
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("NIVEL_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("NIVEL_CURSO", contractDomain.getClassLevelDescription());
		   	} 	
		   	 
		   	if(replaceContractBody.contains("HORA_INICIO_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("HORA_INICIO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getBeginClassHour()));
		   	} 	   	
		   	 
		   	if(replaceContractBody.contains("HORA_TERMINO_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("HORA_TERMINO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getEndClassHour()));
		   	} 	   	
		   	 
		   	if(replaceContractBody.contains("PAUSA_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PAUSA_CURSO", 
		   				contractDomain.getBreakClassHour() == 0? "sem pausa" : "com uma pausa de " + dateUtil.formatDurationTime(contractDomain.getBreakClassHour()));
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("TEMPO_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("TEMPO_CURSO", dateUtil.formatDurationTime(contractDomain.getClassTimeHour()));
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("HORA_SEMANAL_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("HORA_SEMANAL_CURSO", dateUtil.formatDurationTime(contractDomain.getClassWeeklyTimeHour()));
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("PRIMEIRO_DIA_LETIVO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PRIMEIRO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getFirstClassDayDescription()));
		   	}	   	
		   	 
		   	if(replaceContractBody.contains("SEGUNDO_DIA_LETIVO")){
		   		replaceContractBody = replaceContractBody.replaceAll("SEGUNDO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getSecondClassDayDescription()));
		   	}	   	
		   	
		   	//Change after this value
		   	if(replaceContractBody.contains("PRECO_PADRAO_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PRECO_PADRAO_CURSO", methodUtil.currencyFormat(contractDomain.getRealPriceDescription()));
		   	}   	
		   	
		   	//Change after this value
		   	if(replaceContractBody.contains("PRECO_PROFESSOR_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PRECO_PROFESSOR_CURSO", methodUtil.currencyFormat(contractDomain.getProfessorPriceDescription()));
		   	}   	
		   	 
		   	if(replaceContractBody.contains("PACOTE_HORA_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PACOTE_HORA_CURSO", String.valueOf(contractDomain.getClassPackageHour())+"h");
		   	}	
		   		   	
		   	//Change after this value
		   	if(replaceContractBody.contains("PRECO_PROFESSOR_TOTAL_CURSO")){
		   		replaceContractBody = replaceContractBody.replaceAll("PRECO_PROFESSOR_TOTAL_CURSO", methodUtil.currencyFormat(contractDomain.getTotalPackageProfessorPriceDescription()));
		   	}*/
		   	
		   	
		   	/**
		   	 * DeadLine
		   	 */
		   	 
		   	/*if(replaceContractBody.contains("DATA_INICIO_CURSO")){	   		
		   		replaceContractBody = replaceContractBody.replaceAll("DATA_INICIO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getBeginDate()));
		   	}
		   	
		   	if(replaceContractBody.contains("DATA_TERMINO_CURSO")){	   		
		   		replaceContractBody = replaceContractBody.replaceAll("DATA_TERMINO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getEndDate()));
		   	}*/	   	
		   	
		   	
		   	/**
		   	 * Payment
		   	 */ 	   	 
		  	/*if(replaceContractBody.contains("TIPO_PAGAMENTO")){
		  		replaceContractBody = replaceContractBody.replaceAll("TIPO_PAGAMENTO", "");
		  	}*/	
		   	
		  	/*if(replaceContractBody.contains("FORMA_PAGAMENTO")){
	  		replaceContractBody = replaceContractBody.replaceAll("TIPO_PAGAMENTO", "");
	  		}*/

	   	
	   	
    	//return replaceContractBody;   	
    }
    
 
    
    /**
     * Performs simultaneous search/replace of multiple strings. Case Sensitive!
     */
    /*public String replaceMultiple(String target, Map<String, String> replacements) {   	
      return replaceMultiple(target, replacements, true);
    }*/

    /**
     * Performs simultaneous search/replace of multiple strings.
     * 
     * @param target        string to perform replacements on.
     * @param replacements  map where key represents value to search for, and value represents replacem
     * @param caseSensitive whether or not the search is case-sensitive.
     * @return replaced string
     */
	public String replaceMultiple(String target, Map<String, String> replacements, boolean caseSensitive) {
		if (target == null || "".equals(target) || replacements == null || replacements.size() == 0)
			return target;

		// if we are doing case-insensitive replacements, we need to make the
		// map case-insensitive--make a new map with all-lower-case keys
		if (!caseSensitive) {
			Map<String, String> altReplacements = new HashMap<String, String>(replacements.size());
			for (String key : replacements.keySet())
				altReplacements.put(key.toLowerCase(), replacements.get(key));

			replacements = altReplacements;
		}

		StringBuilder patternString = new StringBuilder();
		if (!caseSensitive)
			patternString.append("(?i)");

		patternString.append('(');
		boolean first = true;
		for (String key : replacements.keySet()) {
			if (first)
				first = false;
			else
				patternString.append('|');

			patternString.append(Pattern.quote(key));
		}
		patternString.append(')');

		Pattern pattern = Pattern.compile(patternString.toString());
		Matcher matcher = pattern.matcher(target);

		StringBuffer res = new StringBuffer();
		while (matcher.find()) {
			String match = matcher.group(1);
			if (!caseSensitive)
				match = match.toLowerCase();
			matcher.appendReplacement(res, replacements.get(match));
		}
		matcher.appendTail(res);

		return res.toString();
	}    
    
    
    public void mapParameters(Map<String, Object> parameters){
   	
        /**
         * Person who is contracting TAG
         */
    	parameters.put("PROFESSOR_CURSO", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCompleteName());
    	parameters.put("E-MAIL_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getEmail());
    	parameters.put("TELEFONE_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getTelephone());
    	parameters.put("CPF_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getCpf());
    	parameters.put("CNPJ_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getCnpj());
    	parameters.put("ENDERECO_PESSOA", contractDomain.getPrincipalProfesssorPersonDomain().getPersonDomain().getAddress());
        
    	/**
    	 * Course TAG
    	 */	
    	parameters.put("CODIGO_CONTRATO", contractDomain.getContractCodeDescription());	 
    	parameters.put("UNIDADE_CURSO", methodUtil.toCamelCase(contractDomain.getClassPlaceDescription()));
    	parameters.put("MODULO_CURSO", contractDomain.getClassModuleDescription().toLowerCase());	   		
    	parameters.put("LINGUA_CURSO", methodUtil.toCamelCase(contractDomain.getClassLanguageDescription()));
    	parameters.put("NIVEL_CURSO", contractDomain.getClassLevelDescription());
    	parameters.put("HORA_INICIO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getBeginClassHour()));
    	parameters.put("HORA_TERMINO_CURSO", dateUtil.convertIntoHHmm(contractDomain.getEndClassHour()));
    	parameters.put("PAUSA_CURSO", 
	   				contractDomain.getBreakClassHour() == 0? "sem pausa" : "com uma pausa de " + dateUtil.formatDurationTime(contractDomain.getBreakClassHour()));
    	parameters.put("TEMPO_CURSO", dateUtil.formatDurationTime(contractDomain.getClassTimeHour()));
    	parameters.put("HORA_SEMANAL_CURSO", dateUtil.formatDurationTime(contractDomain.getClassWeeklyTimeHour()));
    	parameters.put("PRIMEIRO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getFirstClassDayDescription()));
    	parameters.put("SEGUNDO_DIA_LETIVO", methodUtil.toCamelCase(contractDomain.getSecondClassDayDescription()));	   	
	   	
	   	//Change after this value
    	parameters.put("PRECO_PADRAO_CURSO", methodUtil.currencyFormat(contractDomain.getRealPriceDescription()));  	
	   	
	   	//Change after this value
    	parameters.put("PRECO_PROFESSOR_CURSO", methodUtil.currencyFormat(contractDomain.getProfessorPriceDescription()));
    	parameters.put("PACOTE_HORA_CURSO", String.valueOf(contractDomain.getClassPackageHour())+"h");	
	   		   	
	   	//Change after this value
    	parameters.put("PRECO_PROFESSOR_TOTAL_CURSO", methodUtil.currencyFormat(contractDomain.getTotalPackageProfessorPriceDescription()));
	   	
	   	
	   	/**
	   	 * DeadLine
	   	 */
	   	 	   		
    	parameters.put("DATA_INICIO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getBeginDate()));	  		
    	parameters.put("DATA_TERMINO_CURSO", dateUtil.convertIntoddMMyyyy(contractDomain.getEndDate()));   	
	   	
	   	
	   	/**
	   	 * Payment
	   	 */
	  	/*if(replaceContractBody.contains("TIPO_PAGAMENTO")){
	  		map.put("TIPO_PAGAMENTO", "");
	  	}*/	
	   	
	  	/*if(replaceContractBody.contains("FORMA_PAGAMENTO")){
  		map.put("TIPO_PAGAMENTO", "");
  		}*/
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
	 * Validate a CPF
	 */
	public void doValidateCPF(){
				
		//Set Save Flag
		saveStudentFlag = true;
		
		if(methodUtil.validateCPF(getPersonCPF())){ //put the negation to validate CPF

			//Set Save Flag to False
			saveStudentFlag = false;
			
			//Error Message
			Messages.addGlobalError("O CPF informado é invalido!");
			
		} else{
			
			//Instantiate PersonDAO
			PersonDAO personDAO = new PersonDAO();
			
			//Instantiate newPersonDomain
			newPersonDomain = new PersonDomain();
			
			//find newPersonDomain
			newPersonDomain = personDAO.findByCPF(getPersonCPF());
			
			if(newPersonDomain != null){
				
				if(newPersonDomain.getStatus()){
					
					//Set Short Language Description
					String language = contractUtil.getShortLanguageDescription(contractDomain.getClassLanguageDescription());
					
					if(newPersonDomain.getLanguage1().equals(language)
							|| newPersonDomain.getLanguage2().equals(language)
							|| newPersonDomain.getLanguage3().equals(language)){
						
						//Verify if newPersonDomain already exist in the student contract list.
						if(contractStudentsDomain.size() > 0){
							
							for(ContractStudentDomain c : contractStudentsDomain){
								
								//Check if the Student already exist in the list.
								if(c.getPersonDomain().getCpf().equals(getPersonCPF())){
									
									//Set save to false
									saveStudentFlag = false;
									
									//Advise Message
									Messages.addGlobalFatal("\"" + newPersonDomain.getCompleteName() + "\" já consta na lista dos Alunos desse contrato.");
								}
							}						
						}

					} else{
												
						//Set save to false
						saveStudentFlag = false;
						
						//Error Message
						Messages.addGlobalWarn("\"" + newPersonDomain.getCompleteName() + "\" não tem ainda o interesse de estudar o " + language);
					}					
					
				} else{
					
					//Set save to false
					saveStudentFlag = false;
					
					//Advise Message
					Messages.addGlobalFatal("\"" + newPersonDomain.getCompleteName() + "\" está inativo.");					
				}
				
			} else{			

				//Set Save Flag to False
				saveStudentFlag = false;
				
				//Error Message
				Messages.addGlobalError("O CPF informado não existe no cadastro de Pessoa!");				
			}
		}
	}
	

	/**
	 * List Students Contract
	 * @param contractDomain
	 */
	public void doListStudents(ContractDomain contractDomain){
		try {
			
			//Instantiate contractStudentsDomain
			contractStudentsDomain = new ArrayList<ContractStudentDomain>();
			
			//List Contract Students
			ContractStudentDAO contractStudentDAO = new ContractStudentDAO();
			contractStudentsDomain = contractStudentDAO.listByContractCodeDescription(contractDomain.getContractCodeDescription());
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao listar as informações !!!");
			e.printStackTrace();			
		}
	}
    

	/**
	 * Add Student Contract
	 */
	public void doAddStudent(){
		
		try {
			
			//List Contract Students
			/*ContractStudentDAO contractStudentDAO = new ContractStudentDAO();
			contractStudentsDomain = contractStudentDAO.findByContractCodeDescription(contractDomain.getContractCodeDescription());
			*/
			
			if(saveStudentFlag){
				
				//Instantiate ContractStudentDAO
				ContractStudentDAO contractStudentDAO = new ContractStudentDAO();
				
				//Set contractStudentDomain
				//ContractStudentDomain contractStudentDomain = new ContractStudentDomain();
				
				contractStudentDomain.setContractDomain(contractDomain);						
				contractStudentDomain.setPersonDomain(newPersonDomain);	
				contractStudentDomain.setContractStudentSaveLoginUser(loginController.getLoggedUser().getUserName());
				contractStudentDomain.setSaveContractStudentDate(new Date());
				
				//Save contractStudent
				contractStudentDAO.merge(contractStudentDomain);
				
				//Instantiate contractStudentsDomain
				contractStudentsDomain = new ArrayList<ContractStudentDomain>();
				
				//List Student Contract
				contractStudentsDomain = contractStudentDAO.listByContractCodeDescription(contractDomain.getContractCodeDescription());
				
				Messages.addGlobalInfo("\""+ newPersonDomain.getCompleteName() + "\" foi adicionado(a) com sucesso!");
			} else{
				Messages.addGlobalInfo("Favor entrar o CPF valido de um(a) aluno(a).");
			}
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações !!!");
			e.printStackTrace();			
		}
	}
  

	/**
	 * Add Student Contract
	 */
	public void doSaveStudent(){
		
		try {
							
			//Instantiate ContractStudentDAO
			ContractStudentDAO contractStudentDAO = new ContractStudentDAO();

			//Set Save Login User
			contractStudentDomain.setContractStudentSaveLoginUser(loginController.getLoggedUser().getUserName());
			
			//Set Save new Date
			contractStudentDomain.setSaveContractStudentDate(new Date());
			
			//Save contractStudent
			contractStudentDAO.merge(contractStudentDomain);
			
			//Instantiate ContractStudentDomain
			contractStudentsDomain = new ArrayList<ContractStudentDomain>();
			
			//List Student Contract
			contractStudentsDomain = contractStudentDAO.listByContractCodeDescription(
					contractStudentDomain.getContractDomain().getContractCodeDescription());
			
			Messages.addGlobalInfo("Salvou com sucesso!");
		
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações !!!");
			e.printStackTrace();			
		}
	}
	
	
	
	/**
	 * Disable Student payment Type in Select Item
	 */
	public void doDisableStudentPaymentTypeSelectItem(){
				
		//Set PaymentType
		paymentType = contractUtil.getFullPaymentTypeComboList();
		
		if(contractStudentDomain.getStudentPaymentContractDescription().equals("Parcelamento(x4)")){
			paymentType.remove("Cartão de Débito");
			paymentType.remove("Depósito");
			paymentType.remove("Dinheiro");
			paymentType.remove("Gratuito");
		} else if(contractStudentDomain.getStudentPaymentContractDescription().equals("Integral/a Vista")){
			paymentType.remove("PagSeguro/Crédito");
			paymentType.remove("Gratuito");			
		} else{
			paymentType.remove("PagSeguro/Crédito");
			paymentType.remove("Cartão de Débito");
			paymentType.remove("Boleto");
			paymentType.remove("Depósito");
			paymentType.remove("Dinheiro");
			
		}		
	}

	
	/**
	 * Edit Professor Contract
	 */
	public void doEditProfessorContract(ActionEvent event){
		
		try {			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
			
			//Instantiate List of person
			UserDAO userDAO = new UserDAO();
			
			//Find Professor with specific teach language
			professors = userDAO.findByActiveProfessorAndTeachingLanguage(
					contractUtil.getShortLanguageDescription(contractDomain.getClassLanguageDescription()));
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de \"" + contractDomain.getContractCodeDescription() +"\"");
			e.printStackTrace();			
		}		
	}	
	
	
	/**
	 * Save After Editing Professor Contract
	 */
	public void doSaveEditContract(){		
		
		try {
			//Set Principal Professor
			contractDomain.setPrincipalProfesssorPersonDomain(contractDomain.getPrincipalProfesssorPersonDomain());
			
			//Set firstSubstituateProfessor
			contractDomain.setFirstSubstituteProfesssorPersonDomain(contractDomain.getFirstSubstituteProfesssorPersonDomain());
			
			//Set secondSubstituateProfessor
			contractDomain.setSecondSubstituteProfesssorPersonDomain(contractDomain.getSecondSubstituteProfesssorPersonDomain());
			
			//Set Date
			contractDomain.setSaveContractDate(new Date());
			
			//Set LoginUser
			contractDomain.setContractSaveLoginUser(loginController.getLoggedUser().getUserName());

			//Instantiate ContracDAO
			ContractDAO contractDAO = new ContractDAO();
			
			//Save ContractDomain
			contractDAO.merge(contractDomain);
			
			//list Student
			doListStudents(contractDomain);
			
			//Inform Save message
			Messages.addGlobalInfo("Salvou com sucesso!");
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvaro o contrato.");
			e.printStackTrace();	
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
     * Contract Received Certificate Message
     */
	public void addContractReceivedCertificateMessage() {
		//Add Message for selectBooleanCheckBox Component
		Messages.addGlobalInfo(contractStudentDomain.getReceivedCertificateFlag() ? "Certificado recebido." : "Certificado não recebido.");
	}

    /**
     * Contract Break Class Hour Message
     */
	public void addContractStudentSignedMessage() {
		//Add Message for selectBooleanCheckBox Component
		Messages.addGlobalInfo(contractStudentDomain.getSignContractStudentFlag() ? "Aluno(a) assinou o contrato." : "Aluno(a) não assinou ainda o contrato.");
	}


    /**
     * Contract Break Class Hour Message
     */
	public void addContractStudentReceiveBookMessage() {
		//Add Message for selectBooleanCheckBox Component
		Messages.addGlobalInfo(contractStudentDomain.getStudentReceiveBookFlag() ? "Ativou recebimento da apostila." : "Aluno(a) sem a apostila.");
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


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
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


	public ContractStudentDomain getContractStudentDomain() {
		return contractStudentDomain;
	}


	public void setContractStudentDomain(ContractStudentDomain contractStudentDomain) {
		this.contractStudentDomain = contractStudentDomain;
	}


	public List<ContractStudentDomain> getContractStudentsDomain() {
		return contractStudentsDomain;
	}


	public void setContractStudentsDomain(List<ContractStudentDomain> contractStudentsDomain) {
		this.contractStudentsDomain = contractStudentsDomain;
	}


	public String getPersonCPF() {
		return personCPF;
	}


	public void setPersonCPF(String personCPF) {
		this.personCPF = personCPF;
	}


	public PersonDomain getNewPersonDomain() {
		return newPersonDomain;
	}


	public void setNewPersonDomain(PersonDomain newPersonDomain) {
		this.newPersonDomain = newPersonDomain;
	}


	public List<UserDomain> getProfessors() {
		return professors;
	}


	public void setProfessors(List<UserDomain> professors) {
		this.professors = professors;
	}


	public boolean isSaveStudentFlag() {
		return saveStudentFlag;
	}


	public void setSaveStudentFlag(boolean saveStudentFlag) {
		this.saveStudentFlag = saveStudentFlag;
	}


	public Map<String, String> getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(Map<String, String> paymentType) {
		this.paymentType = paymentType;
	}


	public boolean isEnableComponentOnContractStudent() {
		return enableComponentOnContractStudent;
	}


	public void setEnableComponentOnContractStudent(boolean enableComponentOnContractStudent) {
		this.enableComponentOnContractStudent = enableComponentOnContractStudent;
	}
	
	
}
