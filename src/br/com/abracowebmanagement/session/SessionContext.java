package br.com.abracowebmanagement.session;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SessionContext {
    private static SessionContext instance;
    
    /**
     * Get Instance
     * @return
     */
    public static SessionContext getInstance(){
       if (instance == null){
           instance = new SessionContext();
       }
        
       return instance;
    }
  
    
    /**
     * Constructor
     */
    private SessionContext(){
        
    }
     
    
    /**
     * CurrentExternalContext
     * @return
     */
    private ExternalContext currentExternalContext(){
       if (FacesContext.getCurrentInstance() == null){
           throw new RuntimeException("FacesContext can not be called out of an HTTP requisition !!!");
       }else{
           return FacesContext.getCurrentInstance().getExternalContext();
       }
    }
     
     
    public void endSession(){
       currentExternalContext().invalidateSession();
    }
     
    public Object getAttribute(String name){
       return currentExternalContext().getSessionMap().get(name);
    }
     
    public void setAttribute(String name, Object value){
       currentExternalContext().getSessionMap().put(name, value);
    }
     
}
