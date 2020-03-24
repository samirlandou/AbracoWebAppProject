package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.abracowebmanagement.dao.ScheduleEventDAO;
import br.com.abracowebmanagement.domain.ScheduleEventDomain;
import br.com.abracowebmanagement.util.DateUtil;

@ManagedBean
@ViewScoped
//@ApplicationScoped
public class ScheduleEventController implements Serializable {
 
	private static final long serialVersionUID = 1065769617077195953L;

	//DateUtil
	DateUtil dateUtil = new DateUtil();
	
	//Login Controller
	LoginController loginController = new LoginController();
	
	//Domain
	private ScheduleEventDomain scheduleEventDomain;
	private List<ScheduleEventDomain> scheduleEventsDomain;
	
	//Default Schedule Model for Calendar
	private ScheduleModel lazyScheduleModel;
	
	//Default Schedule Event for Calendar
	private ScheduleEvent scheduleEvent;
	
	//Default Schedule Entry  Move Event
	private ScheduleEntryMoveEvent entryMoveEvent;
	
	//Variable New Register
	boolean newRegisterFlag = false;
	
	//Delete Button
	boolean onEventSelectFlag;
	
	//On Event Move
	boolean onEventMoveFlag = false;
	
	//On Event Resize
	boolean onEventResizeFlag = false;
	
	//Begin Period
	private Date loadEventsBeginPeriod;
	
	//End Period
	private Date loadEventsEndPeriod;
	
	//Begin Date
	private Date beginDate;
	
	//End Date
	private Date endDate;
	
		
	//Constructor
	public ScheduleEventController(){
		
	}
	
	
	@PostConstruct
	public void doList(){
		
		//Instantiate Calendar Event
		scheduleEvent = new DefaultScheduleEvent();
		
		//Instantiate Domain List
		scheduleEventsDomain = new ArrayList<ScheduleEventDomain>();
		
		//Login
		FacesContext fc = FacesContext.getCurrentInstance();
		loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");

		
		//Instantiate Lazy Schedule Model
		//lazyScheduleModel = new DefaultScheduleModel();
		lazyScheduleModel = new LazyScheduleModel(){

			private static final long serialVersionUID = -6109817173734864738L;

			@Override
           public void loadEvents(Date start, Date end) {
				
				//Initialize start and end dates
				loadEventsBeginPeriod = start;
				loadEventsEndPeriod = end;
				
				//Populate ScheduleEventDomain List
				ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
				scheduleEventsDomain = scheduleEventDAO.searchByBeginDateAndEndDate(start,end);
				
				//Populate Schedule Event Model List
				for(ScheduleEventDomain scheduleEventDomain: scheduleEventsDomain){
					lazyScheduleModel.addEvent(new DefaultScheduleEvent(scheduleEventDomain.getScheduleEventDescription(),
							scheduleEventDomain.getScheduleEventBeginDate(),
							scheduleEventDomain.getScheduleEventEndDate()));
				}
			}
		};
	}
	
	
	public void doClean(){
		//scheduleEventDomain = new ScheduleEventDomain();
		newRegisterFlag = false;
		onEventSelectFlag = false;
		onEventMoveFlag = false;
		onEventResizeFlag = false;
	}

	
	public void doSelect(SelectEvent selectEvent){
		onEventSelectFlag = true;
		
		//Cast selected object to Event
		scheduleEvent = (ScheduleEvent) selectEvent.getObject();
		
		//Instantiate Domain
		scheduleEventDomain = new ScheduleEventDomain();

		//Find Object in data base
		ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
		scheduleEventDomain = scheduleEventDAO.searchByDescriptionAndBeginDateAndEndDate(scheduleEvent.getTitle(), 
				scheduleEvent.getStartDate(), scheduleEvent.getEndDate());
	}
	
	
	public void doNewRegister(SelectEvent selectEvent){
		newRegisterFlag = true;
		onEventSelectFlag = false;
		
		//Instantiate Domain
		scheduleEventDomain = new ScheduleEventDomain();
		
		//Set Begin Date
		scheduleEventDomain.setScheduleEventBeginDate((Date) selectEvent.getObject());
		
		//Set End Date
		scheduleEventDomain.setScheduleEventEndDate((Date) selectEvent.getObject());
		
		//Set LoginUser
		scheduleEventDomain.setScheduleEventLoginUser(loginController.getLoggedUser().getUserName());
		
		
	}
	
	
	public void doSave(){
		try {
			
			//Instantiate DAO
			ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
			
			//Instantiate local Domain object
			ScheduleEventDomain uniqueScheduleEventDomain = new ScheduleEventDomain();
			
			//Search event by Description in the current loadEvents
			uniqueScheduleEventDomain = scheduleEventDAO.searchByDescriptionAndLoadEventsPeriod(
					scheduleEventDomain.getScheduleEventDescription(),
					loadEventsBeginPeriod,
					loadEventsEndPeriod);
			
			//Verify if the description already exist in the current loadEvents period
			if(uniqueScheduleEventDomain != null){
				
				if(onEventMoveFlag || onEventResizeFlag){
					
					//Set scheduleEventDomain
					scheduleEventDomain = uniqueScheduleEventDomain;
			    	
			    	//Set New Begin Date
			    	scheduleEventDomain.setScheduleEventBeginDate(beginDate);
			    	
					//Set new End Date
					scheduleEventDomain.setScheduleEventEndDate(endDate);
					
				} else if(onEventSelectFlag){
					
					if(!scheduleEventDomain.getScheduleEventDescription().
							equalsIgnoreCase(uniqueScheduleEventDomain.getScheduleEventDescription())
									&& uniqueScheduleEventDomain != null){
						//Inform that this register already exist
						Messages.addGlobalInfo("Essa descrição já existe nesse periodo. Favor escolher outra.");
						return;	
					}
				}
			}

			
			//Set LogginUser
			if(!newRegisterFlag || onEventMoveFlag || onEventResizeFlag){
				scheduleEventDomain.setScheduleEventLoginUser(loginController.getLoggedUser().getUserName());				
			}
			
			//Check if End Date is after Begin before Saving
			if(scheduleEventDomain.getScheduleEventEndDate().
				after(scheduleEventDomain.getScheduleEventBeginDate())){
				
				//Merge informations in database
				scheduleEventDAO.merge(scheduleEventDomain);
				
				//Populate ScheduleEventDomain List
				//If not executed yet, search all events Domain between begin and end Date period					
				scheduleEventsDomain = scheduleEventDAO.searchByBeginDateAndEndDate(loadEventsBeginPeriod, loadEventsEndPeriod);
				
				//Populate Schedule Event Model according to the event
				if (onEventMoveFlag || onEventResizeFlag) {
					
					//Clean LazyScheduleModel
					lazyScheduleModel.clear();
					
					//Populate Schedule Event Model List
					for (ScheduleEventDomain scheduleEventDomain : scheduleEventsDomain) {
						lazyScheduleModel.addEvent(new DefaultScheduleEvent(scheduleEventDomain.getScheduleEventDescription(),
							scheduleEventDomain.getScheduleEventBeginDate(),
							scheduleEventDomain.getScheduleEventEndDate()));
					}
					
				} else{
					
					//Populate Schedule Event Model List
					doList();
				}
				
				//Show Success message
				if(onEventMoveFlag){
			    	
			    	//Inform the new date
			    	Messages.addGlobalInfo("Evento movido com sucesso.");
			    	
				} else if(onEventResizeFlag){			    	
			    	//Inform the new Hour
					Messages.addGlobalInfo("Horário do Evento ajustado com sucesso.");					
				
				} else{					
					//Inform generic message
					Messages.addGlobalInfo("Salvou com sucesso!");
				
				}
				
			} else{				
				//Show Error message
				Messages.addGlobalError("Datas e/ou horários incorretos !!!");
			}
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar esse evento !!!");
			e.printStackTrace();
			doList();
		}
	}
	
	
	public void doDelete(){
		try {
			//Deleting
			ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
			scheduleEventDAO.delete(scheduleEventDomain);
			
			//Clean Flags
			doClean();
			
			//Populate event model in the calendar
			doList();
			
			//Show Success message
			Messages.addGlobalInfo("Evento excluido com sucesso!");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao deletar esse evento !!!");
			e.printStackTrace();
			doList();
		}
	}
	
	
    public void doEventMove(ScheduleEntryMoveEvent event) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
    	
    	//Set Event Move Flag
    	onEventMoveFlag = true;
    	
    	//Set Begin Date
    	beginDate = (Date) event.getScheduleEvent().getStartDate();
    	
    	//Set End Date
    	endDate = (Date) event.getScheduleEvent().getEndDate();
    	
		//Instantiate Domain object
		scheduleEventDomain = new ScheduleEventDomain();
		
		//Set Description of Domain object
		scheduleEventDomain.setScheduleEventDescription((String) event.getScheduleEvent().getTitle());
		
		//Save the change
    	doSave();
    	
		//Clean Flags
		doClean();
    }
     
    public void doEventResize(ScheduleEntryResizeEvent event) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
    	
    	//Set Event Resize Flag
    	onEventResizeFlag = true;
    	
    	//Set Begin Date
    	beginDate = (Date) event.getScheduleEvent().getStartDate();
    	
    	//Set End Date
    	endDate = (Date) event.getScheduleEvent().getEndDate();
    	
		//Instantiate Domain object
		scheduleEventDomain = new ScheduleEventDomain();
				
		//Set Description of Domain object
		scheduleEventDomain.setScheduleEventDescription((String) event.getScheduleEvent().getTitle());
		
		//Save the change
    	doSave();
    	
		//Clean Flags
		doClean();
    }

	
    /*
     * Getters and Setters
     */
	public ScheduleEventDomain getScheduleEventDomain() {
		return scheduleEventDomain;
	}

	public void setScheduleEventDomain(ScheduleEventDomain scheduleEventDomain) {
		this.scheduleEventDomain = scheduleEventDomain;
	}

	public ScheduleModel getLazyScheduleModel() {
		return lazyScheduleModel;
	}

	public void setEvent(ScheduleModel event) {
		this.lazyScheduleModel = event;
	}

	public ScheduleEvent getScheduleEvent() {
		return scheduleEvent;
	}

	public void setScheduleEvent(ScheduleEvent scheduleEvent) {
		this.scheduleEvent = scheduleEvent;
	}


	public boolean isOnEventSelectFlag() {
		return onEventSelectFlag;
	}


	public void setOnEventSelectFlag(boolean onEventSelectFlag) {
		this.onEventSelectFlag = onEventSelectFlag;
	}


	public ScheduleEntryMoveEvent getEntryMoveEvent() {
		return entryMoveEvent;
	}


	public void setEntryMoveEvent(ScheduleEntryMoveEvent entryMoveEvent) {
		this.entryMoveEvent = entryMoveEvent;
	}


	public Date getLoadEventsBeginPeriod() {
		return loadEventsBeginPeriod;
	}


	public void setLoadEventsBeginPeriod(Date startDate) {
		this.loadEventsBeginPeriod = startDate;
	}


	public Date getLoadEventsEndPeriod() {
		return loadEventsEndPeriod;
	}


	public void setLoadEventsEndPeriod(Date endDate) {
		this.loadEventsEndPeriod = endDate;
	}


	public Date getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
