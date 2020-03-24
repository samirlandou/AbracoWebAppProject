package br.com.abracowebmanagement.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.abracowebmanagement.hibernate.HibernateUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportController {

    private HttpServletResponse response;
    private FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream stream;
    private Connection con;


    public ReportController() {
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }
 
    
    //-------------- Report With DataBase Connection ---------------------------------------    
    
    /**
     * Show Report. </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void showReport(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
        	//Instantiate ByteArrayOutputStream
        	baos = new ByteArrayOutputStream();
            
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
            
            //Set Report's Name
            //print.setName("Contrato_teste");
            
            //Export the Report using printed report and ByteArrayOutputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=" + reportName + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
            //Close Connection
            closeConnection();
            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Download Report. </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void downloadReport(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
        	//Instantiate ByteArrayOutputStream
        	baos = new ByteArrayOutputStream();
            
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
            
            //Set Report's Name
            //print.setName("Contrato_teste");
            
            //Export the Report using printed report and ByteArrayOutputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            
            //Close Connection
            closeConnection();
            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * Print Report. </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void printReport(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
            
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
            
			//Print Report
			JasperPrintManager.printReport(jasperPrint, true);
            
			//Close Connection
            closeConnection();
            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

 
    
    //-------------- Report Without DataBase Connection ---------------------------------------
    
    
      
    /**
     * Show Report without Connection </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void showReportWithoutConnection(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
        	//Instantiate ByteArrayOutputStream
        	baos = new ByteArrayOutputStream();
            
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            //Set Report's Name
            //print.setName("Contrato_teste");
            
            //Export the Report using printed report and ByteArrayOutputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=" + reportName + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();

            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Download Report without Connection </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void downloadReportWithoutConnection(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
        	//Instantiate ByteArrayOutputStream
        	baos = new ByteArrayOutputStream();
            
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            //Set Report's Name
            //print.setName("Contrato_teste");
            
            //Export the Report using printed report and ByteArrayOutputStream
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();

            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Print Report without Connection </br>
     * Define a parameter: List<Object> list when using <Object>
     * 
     * @param reportPath
     * @param params
     * @param reportName
     */
    public void printReportWithoutConnection(String reportPath, Map<String, Object> params, String reportName){

    	//Set Report as Stream
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        try {
        	//Load Stream in JasperReport
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
            
            //Print Report using Parameters using HIBERNATE Connection.
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            //Set Report's Name
            //print.setName("Contrato_teste");
            
			//Print Report
			JasperPrintManager.printReport(jasperPrint, true);
            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Open Connection
     * @return
     */
    public Connection getConnection(){        
	    try {            
	    	
			con = HibernateUtil.getOpenConnection();
	        
	    } catch (Exception ex) {
	        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    	return con;
    }   

    
    /**
     * Close Connection
     */
    public void closeConnection(){
        try {
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    /*public Connection getConnection(){        
        try {            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pessoa", "root", "12345");
            return con;
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }*/
    
    
    /*
     * Define a parameter: List<Objeto> list when using <Objeto>
    */
    /*public void getReport2( ){
        //stream = this.getClass().getResourceAsStream("/report/reportPessoaBD.jasper");
    	
    	//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	//stream = classLoader.getResourceAsStream("reports/contract_professor.jasper");
    	String reportPath =  Faces.getRealPath("reports/contract_professor.jasper");
    	try {
			stream = new FileInputStream(reportPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	//stream = this.getClass().getResourceAsStream(reportPath);
        String imagePath = Faces.getRealPath("reports/logo_abraco_cultura.png");
        
        
        Map<String, Object> params = new HashMap<String, Object>();
        baos = new ByteArrayOutputStream();
        
        try {
            
            JasperReport report = (JasperReport) JRLoader.loadObject(stream);
            
			//Define Parameters
			params.put("Parameter1", "texto do Paramêtro 1");
			params.put("abracoLogo", imagePath);
            

            JasperPrint print = JasperFillManager.fillReport(report, params, getConnection());
            //print.setName("Contrato_test");
            JasperExportManager.exportReportToPdfStream(print, baos);
            
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
            context.responseComplete();
            closeConnection();
            
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

}
