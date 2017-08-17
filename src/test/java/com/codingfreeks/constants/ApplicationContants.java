package com.codingfreeks.constants;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ApplicationContants {
	
	public static final String SEPERATOR = File.separator;
	
	public static final String LOCATOR_FILE_PATH="src\\test\\resources\\uiRepository";
	public static final String TESTDATA_FILE_PATH="src\\test\\resources\\testData";
	
	public static final String LOGGER_FILE_PATH="src\\test\\java\\com\\codingfreeks\\helper";
	public static final String TEST_RESULT_SCREENSHOT_FILE_LOC="src\\test\\resources\\testResultScreenShots";
	
	public static final String LOG4J_PROPERTY_FILE = "\\src\\test\\resources\\logProperties";
	public static final String LOGGER_LOCATION = SEPERATOR+"Logger";
	public static final String TEST_RESULT_FILE_LOC="src"+SEPERATOR+"test"+SEPERATOR+"resources"+SEPERATOR+"testResult"+SEPERATOR;
	
	public static final String MASTER_TEST_METHOD_NAME="TestMethodName";
	public static final String MASTER_TEST_CASE_ID="TestCaseId";
	public static final String TEST_FAILURE_MESSAGE="TestFailureMessage";
	public static final String TEST_RESULT_PASS="Pass";
	public static final String TEST_RESULT_FAIL="Fail";
	public static final String TEST_RESULT_SKIP="Skip";
	public static final String TEST_METHOD_STATUS="testResult";
	public static final String TEST_METHOD_STARTTIME="testStartTime";
	public static final String TEST_METHOD_ENDTIME="testEndTime";
	

}
