package com.codingfreeks.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.codingfreeks.action.BaseAction;
import com.codingfreeks.action.HomePageAction;
import com.codingfreeks.constants.ApplicationContants;
import com.codingfreeks.helper.Logger;
import com.codingfreeks.helper.TestResultGenerator;



public class CommonTest {
	static WebDriver driver = null;
	BaseAction action;
	HomePageAction homePageAction;
	
	
	
	String url=null;
	
	@BeforeTest(alwaysRun = true)
	public void doCreateResultSheet() {
		TestResultGenerator.createExcelBook();
	}

	
	@BeforeClass(alwaysRun=true)	
	public void sessionSetup(ITestContext testContext) {

		action = new BaseAction();

		String browserName = testContext.getCurrentXmlTest().getParameter("browserType");
		
		Map<String,String> testData= new HashMap<>();
		int totalParameter=testContext.getCurrentXmlTest().getAllParameters().size();
		Logger.info("total parameters passed "+totalParameter);
		Iterator params=testContext.getCurrentXmlTest().getAllParameters().keySet().iterator();
		while(params.hasNext()) {
			String key=(String) params.next();
			testData.put(key, testContext.getCurrentXmlTest().getAllParameters().get(key));
		}
		
		Logger.info("testData  size "+testData);
		
		//action.setUpTestData(storeNameTestData,dispatchAreaNameTestData);
		url = testContext.getCurrentXmlTest().getParameter("url");
		action.storeTestDate(testData);
		action.initializeBrowser(browserName, url);

		
	}
	@BeforeMethod(alwaysRun=true)
	public void appURLSetup() {
		action.launchURL(url);
		
	}
	
	@AfterMethod
	public void getMethodDetails(ITestResult testResult) {
		
		String methodDescription=testResult.getMethod().getDescription();
	
		
		Reporter.log("methodDescription ===="+methodDescription);
		if(testResult.getStatus()==2) {
			action.takeScreenShot(testResult.getName());
		}
		
		writeResultInExcel( testResult);
		
	}
	
	
	public void writeResultInExcel(ITestResult testResult){
		//testResult.gets
		
		Logger.info("testResult.getName() "+testResult.getName());
		
		Logger.info("testResult.getTestClass().getName() "+testResult.getTestClass().getName());
		
		String methodDescription=testResult.getMethod().getDescription();
		
		Logger.info("methodDescription  "+methodDescription);
		
		String[] totalTestCaseIds=methodDescription.split("&");
		
		Logger.info("totalTestCaseIds  "+totalTestCaseIds.length);
		int i=totalTestCaseIds.length;
		do {
		Logger.info("Entering "+i+ "  Row");
		
		Map<String,String> testResultData=new HashMap<String,String>();
		testResultData.put(ApplicationContants.MASTER_TEST_METHOD_NAME, testResult.getName());
		testResultData.put(ApplicationContants.MASTER_TEST_CASE_ID, totalTestCaseIds[i-1]);
		//testResult.get
		if(testResult.getStatus()==1){
			testResultData.put(ApplicationContants.TEST_METHOD_STATUS,ApplicationContants.TEST_RESULT_PASS );
			
		}
		else if(testResult.getStatus()==2){
			testResultData.put(ApplicationContants.TEST_METHOD_STATUS,ApplicationContants.TEST_RESULT_FAIL );
			testResultData.put(ApplicationContants.TEST_FAILURE_MESSAGE, testResult.getThrowable().getMessage());
			
		}
		//testResultData.put("ClassName", testResult.getTestClass().getName());
		
		TestResultGenerator.writeResult(testResultData);
		i--;
		}
		while(i>0);
		
	}
	
	@AfterClass
	public void tearDown() {
		action.closeBrowser();
		
		
	}
	
	
}
