package br.com.abracowebmanagement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {

	//private Date beginDate;


	public void Dateutil(){
		
	}
	
	
	public String getDay(){
		Calendar.getInstance();
		return String.valueOf(Calendar.DAY_OF_MONTH);		
	}

	
	public String getMonth(){
		Calendar.getInstance();
		return String.valueOf(Calendar.MONTH);		
	}

	
	public String getYear(){
		Calendar.getInstance();
		return String.valueOf(Calendar.YEAR);		
	}
	
	
	public long getTime(Date beginDate, Date endDate, TimeUnit timeUnit){		
		long time = endDate.getTime() - beginDate.getTime();
		return timeUnit.convert(time,TimeUnit.MILLISECONDS);		
	}
	
	
	public void getStringDate(){
		final long timestamp = new Date().getTime();

		// with java.util.Date/Calendar api
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		// here's how to get the minutes
		final int minutes = cal.get(Calendar.MINUTE);
		// and here's how to get the String representation
		final String timeString =
		    new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());
		System.out.println(minutes);
		System.out.println(timeString);
	}
	
	
	public Date getFormatedDate(String value, String inputFormat, String outputFormat){
		Date outputDate = null;
		String convertDate;
		try {
			
			// Set InputFormat
			SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
			inputDateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));			
			
            //Parse value into Date object
            Date inputDate = inputDateFormat.parse(value);
			
            //Set OutputFormat
			SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
			outputDateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
			
			//Convert result to String
			convertDate = outputDateFormat.format(inputDate);
			
			//parse result into Date object
			outputDate = outputDateFormat.parse(convertDate);		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDate;		
	}
	
}
