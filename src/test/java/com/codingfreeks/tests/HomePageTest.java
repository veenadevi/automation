package com.codingfreeks.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.codingfreeks.action.HomePageAction;

public class HomePageTest extends CommonTest{
  @Test(description="TC001")
  public void verifyPageTitle() {
	  
	  
	  
	  homePageAction=new HomePageAction();
	  String currentTitle=homePageAction.checkTitle();
	  Reporter.log("currentTitle" +currentTitle);
	  
	  
	  Assert.assertTrue(currentTitle.trim().equalsIgnoreCase("Selenium - Web Browser Automation"), "Title Mismatch");
	  
  }
}
