package com.codingfreeks.helper;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	
	private int maxRetryCount=1;
	 private int retryCount= 0;

	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {
			if (retryCount < maxRetryCount) {
				retryCount++;
				System.out.println("Error seen in "+ result.getName() +" Retrying " + retryCount + " times in progress");
				result.getTestContext().getFailedTests().removeResult(result.getMethod());//Remove the   falied test case from report and entry only final result
				
				return true;
			}

		}
		return false;
	}

}
