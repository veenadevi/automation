package com.codingfreeks.action;

import com.codingfreeks.helper.SeleniumConnector;

public class HomePageAction extends BaseAction{
	
	public String checkTitle(){
		
		String titleText=SeleniumConnector.getCurrentPageTitle();
		
		System.out.println("titleText "+titleText);
		
		return titleText;
	}

}
