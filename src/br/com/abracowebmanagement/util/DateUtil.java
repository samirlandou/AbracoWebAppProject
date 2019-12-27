package br.com.abracowebmanagement.util;

import java.text.DateFormat;
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
	
/**
 * Calculate difference between two hours (date1 & date2) and minutes (date3).	
 * @param data1
 * @param data2
 * @param date3
 * @return
 */
	public static String returnDiffBetweenDates(Date data1, Date data2, int date3){
		long difMilli = data2.getTime() - data1.getTime();
		
		int timeInSeconds = (int)((difMilli / 1000) - (date3 * 60)) ;  
		int hours, minutes, seconds;
		hours = timeInSeconds / 3600;  
		timeInSeconds = timeInSeconds - (hours * 3600);  
		minutes = timeInSeconds / 60;  
		timeInSeconds = timeInSeconds - (minutes * 60);  
		seconds = timeInSeconds;  
		return fillZero(hours) + ":" + fillZero(minutes) + ":" + fillZero(seconds);
		
	}
	
	public static String returnDiffBetweenDates2(Date data1, Date data2, int date3){
		long difMilli = data2.getTime() - data1.getTime();
		
		int timeInSeconds = (int)((difMilli / 1000) - (date3 * 60)) ;  
		int hours, minutes;
		hours = timeInSeconds / 3600;  
		timeInSeconds = timeInSeconds - (hours * 3600);  
		minutes = timeInSeconds / 60;  
		timeInSeconds = timeInSeconds - (minutes * 60);
		return fillZero(hours) + ":" + fillZero(minutes);
		
	}
	
	public static int returnDiffInMinutes(Date data1, Date data2, int date3){
		long diffMilli = data2.getTime() - data1.getTime();
		int diffMin = (int)(((diffMilli / 1000) - (date3 * 60))/60);
		return diffMin;
	}
	
	private static String fillZero(int number ){
		if(number >=0 && number <10){
			return "0"+number;
		}
		return String.valueOf(number);
	}
	
	public static synchronized String formatDate(Date data, String commonsPattern) {
		if (data == null || commonsPattern == null || "".equals(commonsPattern)) {
			return "";
		}
		DateFormat formatter = new SimpleDateFormat(commonsPattern);
		formatter.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		return formatter.format(data);
	}
	
	public static synchronized Date parse(String value, String commonsPattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		sdf.applyPattern(commonsPattern);
		return sdf.parse(value);
		
	}
	
}
