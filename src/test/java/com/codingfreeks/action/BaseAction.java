package com.codingfreeks.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.codingfreeks.constants.ApplicationContants;
import com.codingfreeks.helper.Logger;
import com.codingfreeks.helper.SeleniumConnector;


public class BaseAction {
	static String filePath =ApplicationContants.TEST_RESULT_SCREENSHOT_FILE_LOC;
	static String parantPath = System.getProperty("user.dir");
	
	static Map<String,String> dateToTest=null;
	
	public  void initializeBrowser(String browserName,String url)  {    	
    	SeleniumConnector.getbrowserInstance(browserName);
    	
    	
    	Logger.info("URL launched");
    
    }
	public void storeTestDate(Map<String,String> testDataMap) {
		dateToTest=testDataMap;
	
		
		System.out.println("dateToTest  "+dateToTest.size());
		
		
	}
	
	
	
	public void launchURL(String url) {
		SeleniumConnector.launchUrl(url);
		
	}
	
	public void closeBrowser() {
		SeleniumConnector.closeBrowser();
	}
	
	public void takeScreenShot(String testName) {
		 String fileNameString=parantPath+File.separator+filePath+File.separator+testName+"_"+new SimpleDateFormat("yyyy_MM_dd_hh_mm").format(new Date());
		 SeleniumConnector.getScreenShot(fileNameString);
	}
	
	

	
	public void doSleep() {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
		String formattedDate = dateFormat.format(new Date()).toString();
		System.out.println(formattedDate);
		
		return formattedDate;
	}
	
	public int  currentHour(Calendar cal ) {
		
		
		int hour =cal.get(Calendar.HOUR);
		if(hour==0){
			return 12;
		}
		else
			return hour;
		
	
	}
	
	public String getTimeKey(Calendar cal) {
		
		if(cal.get(Calendar.AM_PM)==0)
			return "AM";
	       
	    else
	    	return "PM";
		
		
	}
	
	
	


}
