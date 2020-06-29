package br.com.abracowebmanagement.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import br.com.abracowebmanagement.domain.scheduleevent.ScheduleEventDomain;
import br.com.abracowebmanagement.util.DateUtil;

@ManagedBean
@ViewScoped
//@ApplicationScoped
public class ScheduleEventController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7599927276668462475L;

	//Domain
	private ScheduleEventDomain scheduleEventDomain;
	private List<ScheduleEventDomain> scheduleEventsDomain;
	
	//Default Schedule Model for Calendar
	private ScheduleModel lazyScheduleModel;
	
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
	
	//DateUtil
	DateUtil dateUtil = new DateUtil();
	
	//Login Controller
	LoginController loginController = new LoginController();
	
	//Schedule Event
	@SuppressWarnings("rawtypes")
	private ScheduleEvent scheduleEvent;
	
	//Begin Period
	private LocalDateTime loadEventsBeginPeriod;
	
	//End Period
	private LocalDateTime loadEventsEndPeriod;	
		
	//Begin Date
	private LocalDateTime beginDate;
	
	//End Date
	private LocalDateTime endDate;	
	
	//Constructor
	public ScheduleEventController(){
		
	}


	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void doList(){
		
		scheduleEvent = new DefaultScheduleEvent();
		
		//Instantiate Domain List
		scheduleEventsDomain = new ArrayList<ScheduleEventDomain>();
		
		//Login
		FacesContext fc = FacesContext.getCurrentInstance();
		loginController = (LoginController) fc.getExternalContext().getSessionMap().get("loginController");
				
		//Lazy Event Model - Search data  in database
		lazyScheduleModel = new LazyScheduleModel() {

			private static final long serialVersionUID = 1L;

			@Override
            public void loadEvents(LocalDateTime start, LocalDateTime end) {
				
				
				//Initialize start and end dates
				loadEventsBeginPeriod = start;
				loadEventsEndPeriod = end;
				
				//Populate ScheduleEventDomain List
				ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
				scheduleEventsDomain = scheduleEventDAO.searchByBeginDateAndEndDate(
						dateUtil.convertLocalDateTimeToDate(start),
						dateUtil.convertLocalDateTimeToDate(end),
						loginController.getLoggedUser().getUserName());		
				
				
				//Populate Schedule Event Model List
				for(ScheduleEventDomain scheduleEventDomain: scheduleEventsDomain){
					
					//AddEvent
					lazyScheduleModel.addEvent(DefaultScheduleEvent.builder().
							title(scheduleEventDomain.getScheduleEventDescription()).
							description(scheduleEventDomain.getScheduleEventType()).
							startDate(dateUtil.convertDateToLocalDateTime(scheduleEventDomain.getScheduleEventBeginDate())).
							endDate(dateUtil.convertDateToLocalDateTime(scheduleEventDomain.getScheduleEventEndDate())).
							editable(scheduleEventDomain.getEditFlag()).
							allDay(scheduleEventDomain.getAllDayFlag()).
							styleClass(scheduleEventDomain.getStyleclassName()).
							build());					
				}				
            }
        };				
	}	

	
	//Clean Boolean's Flag
	public void doClean(){
		
		//scheduleEventDomain = new ScheduleEventDomain();
		newRegisterFlag = false;
		onEventSelectFlag = false;
		onEventMoveFlag = false;
		onEventResizeFlag = false;
	}
	
	
	/**
	 * Select an event
	 * @param selectEvent
	 */
	@SuppressWarnings("rawtypes")
	public void doSelect(SelectEvent<ScheduleEvent> selectEvent){
		
		//Set event Select Flag to true.
		onEventSelectFlag = true;
		
		//Cast selected object to Event
		scheduleEvent = (ScheduleEvent) selectEvent.getObject();
		
		//Instantiate Domain
		scheduleEventDomain = new ScheduleEventDomain();
		
		for(ScheduleEventDomain schedule : scheduleEventsDomain){
			
			//find the object
			if(schedule.getScheduleEventDescription().equals(scheduleEvent.getTitle())
				&& schedule.getScheduleEventBeginDate().compareTo(dateUtil.convertLocalDateTimeToDate(scheduleEvent.getStartDate())) == 0
				&& schedule.getScheduleEventEndDate().compareTo(dateUtil.convertLocalDateTimeToDate(scheduleEvent.getEndDate())) == 0){
				
				scheduleEventDomain = schedule;
				break;
			}
		}

		//Find Object in data base
		/*ScheduleEventDAO scheduleEventDAO = new ScheduleEventDAO();
		scheduleEventDomain = scheduleEventDAO.searchByDescriptionAndBeginDateAndEndDate(scheduleEvent.getTitle(), 
				dateUtil.convertLocalDateTimeToDate(scheduleEvent.getStartDate()), 
				dateUtil.convertLocalDateTimeToDate(scheduleEvent.getEndDate()));*/
	}
	
	/**
	 * Do New Register
	 * @param selectEvent
	 */
	@SuppressWarnings("rawtypes")
	public void doNewRegister(SelectEvent selectEvent){
		newRegisterFlag = true;
		onEventSelectFlag = false;
		
		//Instantiate Domain
		scheduleEventDomain = new ScheduleEventDomain();
		
		//Set Begin Date
		scheduleEventDomain.setScheduleEventBeginDate(dateUtil.convertLocalDateTimeToDate((LocalDateTime) selectEvent.getObject()));
		
		//Set End Date
		scheduleEventDomain.setScheduleEventEndDate(dateUtil.convertLocalDateTimeToDate((LocalDateTime) selectEvent.getObject()));
		
		//Set Editable Value
		scheduleEventDomain.setEditFlag(false);
		
		//Set LoginUser
		scheduleEventDomain.setUserDomain(loginController.getLoggedUser());
				
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
					dateUtil.convertLocalDateTimeToDate(loadEventsBeginPeriod),
					dateUtil.convertLocalDateTimeToDate(loadEventsEndPeriod));
			
			//Verify if the description already exist in the current loadEvents period
			if(uniqueScheduleEventDomain != null){
				
				if(onEventMoveFlag || onEventResizeFlag){
					
					//Set scheduleEventDomain
					scheduleEventDomain = uniqueScheduleEventDomain;
			    	
			    	//Set New Begin Date
			    	scheduleEventDomain.setScheduleEventBeginDate(dateUtil.convertLocalDateTimeToDate(beginDate));
			    	
					//Set new End Date
					scheduleEventDomain.setScheduleEventEndDate(dateUtil.convertLocalDateTimeToDate(endDate));					
					
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
				scheduleEventDomain.setUserDomain(loginController.getLoggedUser());
			}
			
			//Check if EndDate comes after BeginDate before Saving
			if(scheduleEventDomain.getScheduleEventEndDate().
				after(scheduleEventDomain.getScheduleEventBeginDate())){
				
				//Set StyleClass 
				scheduleEventDomain.setStyleclassName(scheduleEventDomain.getPublicFlag() ? "" : "eventColor");
				
				//Merge informations in database
				scheduleEventDAO.merge(scheduleEventDomain);
				
				//Populate ScheduleEventDomain List
				//If not executed yet, search all events Domain between begin and end Date period					
				scheduleEventsDomain = scheduleEventDAO.searchByBeginDateAndEndDate(
						dateUtil.convertLocalDateTimeToDate(loadEventsBeginPeriod),
						dateUtil.convertLocalDateTimeToDate(loadEventsEndPeriod),
						loginController.getLoggedUser().getUserName());
				
				//Populate Schedule Event Model according to the event
				if (onEventMoveFlag || onEventResizeFlag) {
					
					//Clean LazyScheduleModel
					lazyScheduleModel.clear();
					
					//Populate Schedule Event Model List
					for (ScheduleEventDomain scheduleEventDomain : scheduleEventsDomain) {
						//AddEvent
						lazyScheduleModel.addEvent(DefaultScheduleEvent.builder().
								title(scheduleEventDomain.getScheduleEventDescription()).
								description(scheduleEventDomain.getScheduleEventType()).
								startDate(dateUtil.convertDateToLocalDateTime(scheduleEventDomain.getScheduleEventBeginDate())).
								endDate(dateUtil.convertDateToLocalDateTime(scheduleEventDomain.getScheduleEventEndDate())).
								editable(scheduleEventDomain.getEditFlag()).
								allDay(scheduleEventDomain.getAllDayFlag()).
								styleClass(scheduleEventDomain.getStyleclassName()).
								build());
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
					Messages.addGlobalInfo("Data do evento ajustado com sucesso.");					
				
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
	

	/**
	 * Delete Event
	 */
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

	
	/**
	 * Event Move
	 * @param event
	 */
    public void doEventMove(ScheduleEntryMoveEvent event) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
    	
    	//Set Event Move Flag
    	onEventMoveFlag = true;
    	
    	//Set Begin Date
    	beginDate = (LocalDateTime) event.getScheduleEvent().getStartDate();
    	
    	//Set End Date
    	endDate = (LocalDateTime) event.getScheduleEvent().getEndDate();
    	
		//Instantiate Domain object
		scheduleEventDomain = new ScheduleEventDomain();
		
		//Set Description of Domain object
		scheduleEventDomain.setScheduleEventDescription((String) event.getScheduleEvent().getTitle());
		
		//Save the change
    	doSave();
    	
		//Clean Flags
		doClean();
    }
 
    
    /**
     * Event Resize
     * @param event
     */
    public void doEventResize(ScheduleEntryResizeEvent event) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
    	
    	//Set Event Resize Flag
    	onEventResizeFlag = true;
    	
    	//Set Begin Date
    	beginDate = (LocalDateTime) event.getScheduleEvent().getStartDate();
    	
    	//Set End Date
    	endDate = (LocalDateTime) event.getScheduleEvent().getEndDate();
    	
		//Instantiate scheduleEventDomain
		scheduleEventDomain = new ScheduleEventDomain();
				
		//Set Description of scheduleEventDomain
		scheduleEventDomain.setScheduleEventDescription((String) event.getScheduleEvent().getTitle());
		
		//Save the change
    	doSave();
    	
		//Clean Flags
		doClean();
    }
	
	
	/**
	 * 
	 * Getters and Setters
	 */
	public ScheduleEventDomain getScheduleEventDomain() {
		return scheduleEventDomain;
	}


	public void setScheduleEventDomain(ScheduleEventDomain scheduleEventDomain) {
		this.scheduleEventDomain = scheduleEventDomain;
	}


	public List<ScheduleEventDomain> getScheduleEventsDomain() {
		return scheduleEventsDomain;
	}


	public void setScheduleEventsDomain(List<ScheduleEventDomain> scheduleEventsDomain) {
		this.scheduleEventsDomain = scheduleEventsDomain;
	}


	public LocalDateTime getLoadEventsBeginPeriod() {
		return loadEventsBeginPeriod;
	}


	public void setLoadEventsBeginPeriod(LocalDateTime loadEventsBeginPeriod) {
		this.loadEventsBeginPeriod = loadEventsBeginPeriod;
	}


	public LocalDateTime getLoadEventsEndPeriod() {
		return loadEventsEndPeriod;
	}


	public void setLoadEventsEndPeriod(LocalDateTime loadEventsEndPeriod) {
		this.loadEventsEndPeriod = loadEventsEndPeriod;
	}


	@SuppressWarnings("rawtypes")
	public ScheduleEvent getScheduleEvent() {
		return scheduleEvent;
	}


	@SuppressWarnings("rawtypes")
	public void setScheduleEvent(ScheduleEvent scheduleEvent) {
		this.scheduleEvent = scheduleEvent;
	}


	public ScheduleModel getLazyScheduleModel() {
		return lazyScheduleModel;
	}


	public void setLazyScheduleModel(ScheduleModel lazyScheduleModel) {
		this.lazyScheduleModel = lazyScheduleModel;
	}


	public LocalDateTime getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(LocalDateTime beginDate) {
		this.beginDate = beginDate;
	}


	public LocalDateTime getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}


	public ScheduleEntryMoveEvent getEntryMoveEvent() {
		return entryMoveEvent;
	}


	public void setEntryMoveEvent(ScheduleEntryMoveEvent entryMoveEvent) {
		this.entryMoveEvent = entryMoveEvent;
	}


}
