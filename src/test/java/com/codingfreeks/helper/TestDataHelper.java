package com.codingfreeks.helper;

public class TestDataHelper {/*
	
	static Workbook testDataBook = null;
	static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	static Calendar cal = Calendar.getInstance();
	
	
	public static void readFile(){
		
		String filePath = OrbitzConstant.TESTDATA_FILE_PATH;
		String parantPath = System.getProperty("user.dir");
		
		String fileNameString=parantPath+File.separator+filePath+File.separator+"OrbitzTestData.xls";
				 
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileNameString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String fileExtensionName = fileNameString.substring(fileNameString.indexOf("."));
		 try {
		 if(fileExtensionName.equals(".xlsx")){
			
				testDataBook= new XSSFWorkbook(fis);
			} 
		 else if(fileExtensionName.equals(".xls")){
				
				testDataBook= new HSSFWorkbook(fis);
			} 
		 }
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}
	
	public static List<FlightDetails> loadFlightDetails(){
			readFile();
			
			
			FlightDetails newFlightDetails=null;
			
			List<FlightDetails> listOfFlights= new ArrayList<FlightDetails>();
			//workBook.getS
		
		 Sheet multiDestFlightDetails=testDataBook.getSheetAt(0);
		 Logger.info("Test Data Sheet Name "+multiDestFlightDetails.getSheetName());
		 
		 
			int indexflightFrom= readColumnIndx(multiDestFlightDetails,"From");//0
			int indexFlightTo=readColumnIndx(multiDestFlightDetails,"To");
			int indexFlightStart=readColumnIndx(multiDestFlightDetails,"StartDate(mm/dd/yyyy)");
			int indexTripGap= readColumnIndx(multiDestFlightDetails,"tripGap");
			
			
			//Iterator<Row> iterator = multiDestFlightDetails.iterator();
			
			// while (iterator.hasNext())
			 for(int i=1;i<=multiDestFlightDetails.getLastRowNum();i++){
				 newFlightDetails= new  FlightDetails();
		            Row currentRow =multiDestFlightDetails.getRow(i);
		            
		            //Read Each Column of current row and load the Object
		            
		            Cell fromDetails=currentRow.getCell(indexflightFrom,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK );
		            
		            newFlightDetails.setFlyingFrom(returnCellValue(fromDetails));
		            
		            Cell toDetail=currentRow.getCell(indexFlightTo,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		            
		            newFlightDetails.setFlyingTo(returnCellValue(toDetail));
		            
		            Cell startDate=currentRow.getCell(indexFlightStart,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		            
		            newFlightDetails.setDepartingDate(returnCellValue(startDate));	
		            
		            Cell tripGap=currentRow.getCell(indexTripGap,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		            
		           
		            
		            newFlightDetails.setTripGap((returnCellValue(tripGap)));
		            if(newFlightDetails.getTripGap().isEmpty()){
		            	newFlightDetails.setTripGap("7)");
		            }
		             		                
		            listOfFlights.add(newFlightDetails) ; 
			
			
			 }
		
			return setTripDateValues(listOfFlights);
			//return listOfFlights;
		
		
	}
	
	public static List<FlightDetails> setTripDateValues(List<FlightDetails> originalList){
	List<FlightDetails> listOfFlights= originalList;
	
	String tripStartDate=selectTripStartDate(listOfFlights.get(0).getDepartingDate());
	
	listOfFlights.get(0).setDepartingDate(tripStartDate);
	
	
	if(listOfFlights.get(0).getFlyingTo().isEmpty()){
		listOfFlights.get(0).setFlyingTo("KUL");
		listOfFlights.get(1).setFlyingFrom("KUL");
	}
	String currentStartDate=tripStartDate;
	for(int i=1;i<listOfFlights.size();i++){
		
		int tripGap=Integer.parseInt(listOfFlights.get(i).getTripGap());
		
		currentStartDate=getNextTripDate(tripGap,currentStartDate);
		listOfFlights.get(i).setDepartingDate(currentStartDate);
		
		if(listOfFlights.get(i).getFlyingTo().isEmpty()){
			listOfFlights.get(i).setFlyingTo("CHI");
			listOfFlights.get(i+1).setFlyingFrom("CHI");;
		}
		

		
		
	}
	
	if(listOfFlights.get(listOfFlights.size()-1).getFlyingTo().isEmpty()){
		listOfFlights.get(0).setFlyingTo(listOfFlights.get(0).getFlyingFrom());
		;
	}
	
	
	
	return listOfFlights;
	
	}
	
public  static String selectTripStartDate(String departingDate){
		
		String tripStartDate=null;
		Date date = null;		
		
		if(!departingDate.isEmpty()){
			tripStartDate=getNextTripDate(35,departingDate);
			
		}
		
		else {
			String currentDate	=new SimpleDateFormat("MM/dd/yyyy").format(System.currentTimeMillis());
			
			try {
				date = formatter.parse(currentDate);				
				cal.setTime(date);
				cal.add(Calendar.DATE, 35); // add 10 days
				 
				date = cal.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//tripStartDate=new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
			
			tripStartDate=getNextTripDate(35,currentDate);
			
		}
		
		
	return tripStartDate;
	}
	
	public static String getNextTripDate(int dateTobeAdded,String tempDate){
		
		Date date;
		try {
			date = formatter.parse(tempDate);
						
		cal.setTime(date);
		cal.add(Calendar.DATE, dateTobeAdded); // add 10 days
		 
		date = cal.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
		
	}
	
	private static  String returnCellValue(Cell cell){
		
		String cellValue = null;		
		
		
		switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            cellValue=cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
        	
        	cellValue=String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_NUMERIC:
        	cell.setCellType(Cell.CELL_TYPE_STRING);
        	cellValue=String.valueOf(cell.getStringCellValue());
            break; 
        case Cell.CELL_TYPE_BLANK:
        	cellValue = "";
            break;
        case Cell.CELL_TYPE_ERROR:
        	cellValue = "";
            break;
		}
		
		return cellValue;
		
	}
	
	private  static int readColumnIndx(Sheet DetailsSheet,String ColunName){
		
		int index=-1;
		
		String tempColumnValue=DetailsSheet.getRow(0).getCell(0).getStringCellValue();
		
		int totalColumns=DetailsSheet.getRow(0).getLastCellNum();//Fiding the Total Columns based on First Row
		
		Logger.info("totalColumns  :"+totalColumns);
		
		//start reading from the last column
		
		int tempindex=totalColumns-1;
	
		
		while(tempindex>=0){
			String columnValue=DetailsSheet.getRow(0).getCell(tempindex).getStringCellValue();//column,row			
			if(columnValue.equalsIgnoreCase(ColunName)){			
			index=tempindex;
			
		}
		tempindex--;
		
		}
		
		return index;
		
		
	}

	

*/}
