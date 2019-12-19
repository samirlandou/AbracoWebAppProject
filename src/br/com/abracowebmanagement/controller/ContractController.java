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

import br.com.abracowebmanagement.dao.ContractDAO;
import br.com.abracowebmanagement.dao.PersonDAO;
import br.com.abracowebmanagement.domain.ContractDomain;
import br.com.abracowebmanagement.domain.PersonDomain;
import br.com.abracowebmanagement.domain.UserDomain;
import br.com.abracowebmanagement.util.DateUtil;

@ManagedBean
@ViewScoped
public class ContractController implements Serializable {
	
	private static final long serialVersionUID = 3166453786287189367L;
	
	//DateUtils
	public DateUtil dateUtil = new DateUtil();
	
	private ContractDomain contractDomain;
	private List<ContractDomain> contractsDomain;
	
	//Create Map to store levels values
	private Map<String, String> levels;
	
	//User and Person
	private List<UserDomain> usersDomain;
	private List<PersonDomain> students;
	private List<PersonDomain> professors;
	
	//Skip for wizard component
	private boolean skip;
	
	//to calculate total time
	private Date total;
	

	
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
		/*if(contractDomain.getLanguageDescription() != null){
			doUpdateLevel();
		}	*/
	}

	/*
	 * Class level
	 * Example:
	 * 
	 * Spanish:	Espanhol 1 (A1); Espanhol 1 (A2); Espanhol 3 (B1.1);
	 * 			Espanhol 4 (B1.2); Espanhol 5 (B1.3); Conversação (B1/B2);
	 * 
	 * French:	Francês 1 (A1.1); Francês 2 (A1.2); Francês 3 (A2.1);
	 *  		Francês 4 (A2.2); Francês 5 (B1.1); Francês 6 (B1.2);
	 *  		Francês 7 (B1.3); Conversação (B1/B2);
	 *  
	 * English:	Inglês 1 (A1.1); Inglês 2 (A1.2); Inglês 3 (A2.1);
	 * 			Inglês 4 (A2.2); Inglês 5 (B1.1); Inglês 6 (B1.2);
	 * 			Conversação (B1/B2);
	 * 
	 * Arab:	Arábe 1; Arábe 2; Arábe 3; Arábe 4;
	 */	
	public void doUpdateLevel(){
		
		//Store Levels
		levels = new TreeMap<String, String>();
		if(contractDomain != null){
			if(contractDomain.getLanguageDescription().equals("AR")){
				levels.put("Arábe 1", "AR1");
				levels.put("Arábe 2", "AR2");
				levels.put("Arábe 3", "AR3");
				levels.put("Arábe 4", "AR4");
			} else if(contractDomain.getLanguageDescription().equals("ES")){
				levels.put("Espanhol 1 (A1)", "ES1");
				levels.put("Espanhol 2 (A2)", "ES2");
				levels.put("Espanhol 3 (B1.1)", "ES3");
				levels.put("Espanhol 4 (B1.2)", "ES4");
				levels.put("Espanhol 5 (B1.3)", "ES5");
				levels.put("Espanhol Conv.(B1/B2)", "ESconv");	
			} else if(contractDomain.getLanguageDescription().equals("EN")){
				levels.put("Inglês 1 (A1.1)", "EN1");
				levels.put("Inglês 2 (A1.2)", "EN2");
				levels.put("Inglês 3 (A2.1)", "EN3");
				levels.put("Inglês 4 (A2.2)", "EN4");
				levels.put("Inglês 5 (B1.1)", "EN5");
				levels.put("Inglês 6 (A1.2)", "EN6");
				levels.put("Inglês Conv.(B1/B2)", "ENconv");
			} else if(contractDomain.getLanguageDescription().equals("FR")){
				levels.put("Francês 1 (A1.1)", "FR1");
				levels.put("Francês 2 (A1.2)", "FR2");
				levels.put("Francês 3 (A2.1)", "FR3");
				levels.put("Francês 4 (A2.2)", "FR4");
				levels.put("Francês 5 (B1.1)", "FR5");
				levels.put("Francês 6 (B1.2)", "FR6");
				levels.put("Francês 7 (B1.3)", "FR7");
				levels.put("Francês Conv.(B1/B2)", "FRconv");
			}
			
			//List Students
			PersonDAO personDAO = new PersonDAO();
			professors = personDAO.findByActiveProfessorAndLanguage(contractDomain.getLanguageDescription());
			//students = personDAO.findByActiveStudentAndLanguage(contractDomain.getLanguageDescription());
		}
	}
	
	
	public void doCalculateClassHour(){
		Date begin = contractDomain.getHourBeginClass();
		Date end = contractDomain.getHourEndClass();
		Date pause = contractDomain.getHourPauseClass();
		long diff =0, diffMinutes = 0;
		
		
		if(begin != null && end != null && begin.before(end)){
			
			if(pause != null){
				if(pause.before(end)){
					diff = end.getTime() - begin.getTime() - pause.getTime();
					diffMinutes = diff / (60 * 1000);									
				} else{
					Messages.addGlobalError("Ocorreu um erro: o horário total é negativo");
				}
			} else{
				diff = end.getTime() - begin.getTime();
				diffMinutes = diff / (60 * 1000);
			}
			total = dateUtil.getFormatedDate(Long.toString(diffMinutes), "mm", "HH:mm");
		}
		
	}

	/**
	 * Save Method. <br/>
	 * @author samirlandou <br/>
	 * @since 19/12/2019
	 */
	public void doSave(){
		/* 
		//This code is used with PrimeFaces
		String messageText = "Programação Web com java";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);*/		
		
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
		try {			
			//Capture the event from the cursor in contract.xhtml
			contractDomain = (ContractDomain) event.getComponent().getAttributes()
					.get("selectedContractByCursor");
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
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
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


	public Date getTotal() {
		return total;
	}


	public void setTotal(Date total) {
		this.total = total;
	}	
	
}
