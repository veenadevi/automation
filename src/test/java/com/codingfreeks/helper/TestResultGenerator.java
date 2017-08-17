package com.codingfreeks.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codingfreeks.constants.ApplicationContants;




public class TestResultGenerator {
	static String filePath =ApplicationContants.TEST_RESULT_FILE_LOC;
	static String parantPath = System.getProperty("user.dir");
	static String fileNameString=parantPath+File.separator+filePath+"TestResult_"+new SimpleDateFormat("yyyy_MM_dd_hh_mm").format(new Date())+".xlsx";
	static Workbook testResultBook;
	static File fileName;
	static Sheet resultSheet;
	
	
	public static void createExcelBook(){
	testResultBook= new XSSFWorkbook();
	
	System.out.println("fileNameString  "+fileNameString);
	 fileName = new File(fileNameString);
	resultSheet = testResultBook.createSheet("E2E_TestResult");  	
		writeHeader(resultSheet);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileNameString);
			testResultBook.write(fileOut);
	        fileOut.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			
			
		}
		
		//return testResultBook;
	}
	
	public static void writeResult(Map<String,String> testResultData ){
		
		
		Workbook resultBook =null;
		Sheet mySheet=null;
		System.out.println("writing data for "+testResultData.get(ApplicationContants.MASTER_TEST_METHOD_NAME));
		
		
		
		System.out.println("fileName   :"+ fileName );
		try {
			FileInputStream fis = new FileInputStream(fileName);
			resultBook=new XSSFWorkbook(fis);
			System.out.println("resultBook :"+resultBook.getNumberOfSheets());
			
			 mySheet = resultBook.getSheetAt(0);
			 System.out.println("Entering to write coloumns  :" +mySheet.getSheetName());
		} 
		
		catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		Row currentRow=null;
		Iterator<Row> totalRows=mySheet.iterator();
		int totalRow=mySheet.getLastRowNum();
		System.out.println(totalRow+"totalRow");
		if(totalRow==0){
			System.out.println("row has only headrs");
		 //currentRow=mySheet.getRow(1);
		 currentRow= mySheet.createRow(1);
		}
		else{
			System.out.println("now going to update  row ...."+totalRow);
			
			currentRow=mySheet.createRow(totalRow+1);
		}
		
			CellStyle cellStyle = resultBook.createCellStyle();
			//successStyle.setFillPattern(HSSFCellStyle.);
			
			currentRow.createCell(0).setCellValue(currentRow.getRowNum());
			currentRow.createCell(1).setCellValue(testResultData.get(ApplicationContants.MASTER_TEST_METHOD_NAME));
			currentRow.createCell(2).setCellValue(testResultData.get(ApplicationContants.MASTER_TEST_CASE_ID));
			currentRow.createCell(3).setCellValue(testResultData.get(ApplicationContants.TEST_METHOD_STATUS));
			
			
			if(testResultData.get(ApplicationContants.TEST_METHOD_STATUS).equalsIgnoreCase(ApplicationContants.TEST_RESULT_FAIL)){
				cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				currentRow.createCell(6).setCellValue(testResultData.get(ApplicationContants.TEST_FAILURE_MESSAGE));
				
			}
			else if(testResultData.get(ApplicationContants.TEST_METHOD_STATUS).equalsIgnoreCase(ApplicationContants.TEST_RESULT_SKIP)){
				cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				currentRow.createCell(4).setCellValue(testResultData.get(ApplicationContants.TEST_FAILURE_MESSAGE));
				
			}
			else{
				cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				currentRow.createCell(4).setCellValue("No Commemts");
			}
			currentRow.getCell(3).setCellStyle(cellStyle);
			
			
			try {
				FileOutputStream out = new FileOutputStream(fileNameString);
				resultBook.write(out);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void writeHeader(Sheet sheet){
		Row rowhead = sheet.createRow((short)0);
        rowhead.createCell(0).setCellValue("No.");
        rowhead.createCell(1).setCellValue("TestMethodName");
       rowhead.createCell(2).setCellValue("TestCaseID");
        rowhead.createCell(3).setCellValue("Status");     
        rowhead.createCell(4).setCellValue("Comments");
        
	}
	
	
	
	public static void takeScreenShot(String testMethod){
		 String fileNameString=parantPath+File.separator+filePath+File.separator+testMethod+"_"+new SimpleDateFormat("yyyy_MM_dd_hh_mm").format(new Date());
		 SeleniumConnector.getScreenShot(fileNameString);
	}

}
