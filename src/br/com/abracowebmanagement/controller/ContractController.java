package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import br.com.abracowebmanagement.dao.ContractDAO;
import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.domain.ContractDomain;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.ContractUtil;
import br.com.abracowebmanagement.util.DateUtil;
import br.com.abracowebmanagement.util.NumberFormatUtil;

@ManagedBean
@ViewScoped
public class ContractController implements Serializable {
	
	private static final long serialVersionUID = 3166453786287189367L;
	
	//Classes with Utilities
	public DateUtil dateUtil = new DateUtil();
	public ContractUtil contractUtil = new ContractUtil();
	public NumberFormatUtil numberFormatUtil = new NumberFormatUtil();
	
	private ContractDomain contractDomain;
	private List<ContractDomain> contractsDomain;
	
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
	
	//to calculate total time
	private String total;
	private String contractStatus;
	private String semester;
	private String contractCode;
	private String dayClass;
	private String dayClassFullDescription;
	private String classLanguage;
	private String classPlace;
	
	private String decimalPriceDescription;
	

	
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
			contractsDomain= contractDAO.list();
			
			//List first class day
			firstClassDay = contractUtil.getClassDayComboList();
						
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
		
		//set skipRendered
		skipRendered = false;
		
		//set secondClassDayRendered
		//secondClassDayRendered = false;
	}

	public void doUpdateLevel(){
		
		//Instantiate Levels
		levels = new TreeMap<String, String>();
		
		if(contractDomain != null){
			
			levels = contractUtil.getLevelComboList(contractDomain.getLanguageDescription());
			
			//List Students
			PersonDAO personDAO = new PersonDAO();
			professors = personDAO.findByActiveProfessorAndLanguage(contractDomain.getLanguageDescription());
			//students = personDAO.findByActiveStudentAndLanguage(contractDomain.getLanguageDescription());
		}
	}
	
	public void doUpdateSecondClassDay(){
		
		//Instantiate second class day
		secondClassDay = new TreeMap<String, String>();
		
		//List secondClassDay
		if(contractDomain.getDayClass1() != null){
			if(contractDomain.getDayClass1().equals("SEX")
					|| contractDomain.getDayClass1().equals("SAB")){
				secondClassDayRendered = false;
			} else{
				secondClassDay.putAll(firstClassDay);
				secondClassDay.remove(contractDomain.getDayClass1());
				secondClassDay.remove("SEX");
				secondClassDay.remove("SAB");
				secondClassDayRendered = true;
			}
		}else{
			secondClassDayRendered = false;
		}
	}
	
	
	public void doCalculateClassHour(){
		Date begin = contractDomain.getHourBeginClass();
		Date end = contractDomain.getHourEndClass();
		int pause = contractDomain.getHourPauseClass();
		
		if(begin != null && end != null && begin.before(end)){
			contractDomain.setHourTotalClass(DateUtil.returnDiffInMinutes(begin, end, pause));
			total = DateUtil.returnDiffBetweenDates2(begin, end, pause);
		} else{
			Messages.addGlobalError("Ocorreu um erro. Favor verificar as datas");
		}	
	}
	
	public void doCreateContractCode(){
		String year = DateUtil.formatDate(new Date(), "yyyy");
		String reference = "01/06/" + year;
		Date semesterReference;
		try {
			semesterReference = DateUtil.parse(reference, "dd/mm/yyyy");
			
			if(contractDomain.getBeginDate().before(semesterReference)){
				semester = "1°SEM"+ year.substring(2, 4);
			} else{
				semester = "2°SEM"+ year.substring(2, 4);
			}
			
			if(contractDomain.getDayClass2().isEmpty()){
				dayClass = contractDomain.getDayClass1();
				dayClassFullDescription = contractUtil.getClassDayFullDescription(contractDomain.getDayClass1());
			} else{
				dayClass = contractDomain.getDayClass1()
						+ "_"
						+ contractDomain.getDayClass2();
				
				dayClassFullDescription = contractUtil.getClassDayFullDescription(contractDomain.getDayClass1())
						+ " e "
						+ contractUtil.getClassDayFullDescription(contractDomain.getDayClass2());
			}
			
			
			//Code Description Example: 1°SEM19_EN1_EXT_PINH_SEG_TER_19H00_20H30			
			String classHour = DateUtil.formatDate(contractDomain.getHourBeginClass(), "HH:mm")
								+ "_"
								+ DateUtil.formatDate(contractDomain.getHourEndClass(), "HH:mm");
			
			contractCode = semester
							+ "_"
							+ contractDomain.getLevelDescription()
							+ "_"
							+ contractDomain.getClassType()
							+ "_"
							+ contractDomain.getPlaceDescription()
							+ "_"
							+ dayClass
							+ "_"
							+ classHour.replace(":", "H");
			
			contractDomain.setCodeDescription(contractCode);
							
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
		
		try {
			//Save Contract with merge method
			ContractDAO contractDAO = new ContractDAO();
			contractDAO.merge(contractDomain);
			
			//Clean informations in the panelGrid
			doNewRegister();
			
			//List again Contract (very import to update the list)
			contractsDomain = contractDAO.list();
			
			//This code is used with OmniFaces and it is more practice than PrimeFaces implementation.
			Messages.addGlobalInfo("Salvou com sucesso!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar as informações de uma pessoa!");
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
			Messages.addGlobalInfo(contractDomain.getCodeDescription() + " foi excluido com sucesso!!!");
		} catch (Exception e) {
			if(e.equals("ConstraintViolationException")){
				Messages.addGlobalError("Não pode deletar pois os dados de " + contractDomain.getCodeDescription() + " está sendo usado em outro processo!!!");
			} else{
				Messages.addGlobalError("Ocorreu um erro ao tentar excluir as informações de: " + contractDomain.getCodeDescription());
			}
			e.printStackTrace();			
		}
	}

	
	public void addMessage() {
		//Add Message for toggleSwitch Component from contract.xhtml
		String summary = contractDomain.getClosedFlag() ? "Ativo(a)" : "Desativado(a)";
		Messages.addGlobalInfo(summary);
	}
	
	/**
	 * Edit Method. <br/>
	 * @author samirlandou <br/>
	 * @param event <br/>
	 * @since 19/12/2019
	 */
	public void doEdit(ActionEvent event){
		
		//Set skip rendered
		skipRendered = true;
		
		try {			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes().get("selectedContractByCursor");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao editar as informações de: " + contractDomain.getCodeDescription());
			e.printStackTrace();			
		}		
	}
	
	/**
	 * Flow Process to fill forms
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 * @param event
	 * @return
	 */
    public String doFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
        	
        	/*if(event.getNewStep().equals("placeTabID")){
        		doCalculateClassHour();
        	}*/
        	
        	if(event.getNewStep().equals("resumeTabID")){
        		doCreateContractCode();
        		doCalculateClassHour();
        		addContractMessage();
        		classPlace = contractUtil.getPlaceFullDescription(contractDomain.getPlaceDescription());
        		classLanguage = contractUtil.getLanguageFullDescription(contractDomain.getLanguageDescription());
        		decimalPriceDescription = numberFormatUtil.currencyFormat(contractDomain.getPriceDescription());
        	}
        	
            return event.getNewStep();
        }
    }
    
    
	public void addContractMessage() {
		//Add Message for toggleSwitch Component from person.xhtml
		contractStatus = contractDomain.getClosedFlag() ? "Contrato Ativo!" : "Contrato não Ativo!";
		Messages.addGlobalInfo(contractStatus);
	}
	
	
	public void successListener(final ClipboardSuccessEvent successEvent){
		Messages.addGlobalInfo("O código foi copiado com sucesso!");
	}
	
	
	public void errorListener(final ClipboardErrorEvent errorEvent){
		Messages.addGlobalError("Erro ao copiar o código do contrato!");
	}
	
	
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


	public String getDecimalPriceDescription() {
		return decimalPriceDescription;
	}


	public void setDecimalPriceDescription(String decimalPriceDescription) {
		this.decimalPriceDescription = decimalPriceDescription;
	}	
	
}
