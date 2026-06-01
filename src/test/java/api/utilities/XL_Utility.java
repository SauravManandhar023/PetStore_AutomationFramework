package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XL_Utility {
		
	public FileInputStream FI;  		//	read file
	public FileOutputStream FO; 		//	write file
	public XSSFWorkbook workbook; 		//	Represents entire excel workbook
	public XSSFSheet sheet;				//  Represents single sheet inside workbook
	public XSSFRow row;					//	Represents row inside a sheet
	public XSSFCell cell;				//  Represents cell inside a row 
	public CellStyle style;				// Used for styling cells (color, format, etc.
	
	String path;						// Stores Excel file path
	
	
	//Constructor to initialize the Excel file path
	
	public XL_Utility (String path) {
		this.path = path;
	}
	
	// Get total number of rows
	public int getRowCount(String sheetName) throws IOException {
		
		FI = new FileInputStream(path);   		// Open Excel File
		workbook = new XSSFWorkbook(FI);  		// Load workbook
		sheet = workbook.getSheet(sheetName);   // Access Specific Sheet
		int rowCount = sheet.getLastRowNum();   // Get last row number in that sheet
		
		workbook.close();
		FI.close();
		
		return rowCount;
		
	}
	
	//Get total number of cells
	public int getCellCount(String sheetName, int rowNum)  throws IOException {
		
		FI = new FileInputStream(path);
		workbook = new XSSFWorkbook(FI);
		sheet = workbook.getSheet(sheetName) ;
		row = sheet.getRow(rowNum);               //Access specfic row
		int cellCount = row.getLastCellNum();
		
		workbook.close();
		FI.close();
		
		return cellCount;
	}
	
	//Read data from cell
	
	public String getCellData (String sheetName, int rowNum, int column) throws IOException {
		
		FI = new FileInputStream(path);
		workbook = new XSSFWorkbook(FI);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		
		if(row !=null) {
			
			// Access specific cell
			cell = row.getCell(column);
		}
		
		String data;
		
		try {
			
			DataFormatter formatter = new DataFormatter(); // DataFormatter converts ant data-type to String
			data = formatter.formatCellValue(cell);
			
		}catch (Exception e) {
			
			//Return empty if cell is null
			data = "";
			
		}
		
		workbook.close();
		FI.close();
		
		return data;
		
		
	}
	
	
	//Set cell data
	
	public void setCellData(String sheetName, int rownum, int column, String data)
            throws IOException {
		
		FI = new FileInputStream(path);
		workbook = new XSSFWorkbook(FI);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		
		//Create row if it doesn't exist
		
		if(row == null) {
			row = sheet.createRow(rownum); 
		}
		
		//Access cell
		cell = row.getCell(column);
		
		//Create cell it not exists
		
		if(cell == null) {
			cell = row.createCell(column);
		}
		
		//Set data into cell
		cell.setCellValue(data);
		
		workbook.write(FO);
		
		workbook.close();
		FI.close();
		FO.close();
	}
	
	//Fill cell with green color
	 public void fillGreenColor(String sheetName, int rownum, int column)
	            throws IOException {

	        // Open Excel file
	        FI = new FileInputStream(path);

	        // Load workbook
	        workbook = new XSSFWorkbook(FI);

	        // Access sheet
	        sheet = workbook.getSheet(sheetName);

	        // Access row
	        row = sheet.getRow(rownum);

	        // Access cell
	        cell = row.getCell(column);

	        // Create style object
	        style = workbook.createCellStyle();

	        // Set green background color
	        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());

	        // Apply solid fill pattern
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        // Apply style to cell
	        cell.setCellStyle(style);

	        // Open output stream
	        FO = new FileOutputStream(path);

	        // Write workbook changes
	        workbook.write(FO);

	        // Close resources
	        workbook.close();
	        FI.close();
	        FO.close();
	    }
	
	 // Fill cell with red color
	 
	 public void fillRedColor(String sheetName, int rownum, int column)
	            throws IOException {

	        // Open Excel file
	        FI = new FileInputStream(path);

	        // Load workbook
	        workbook = new XSSFWorkbook(FI);

	        // Access sheet
	        sheet = workbook.getSheet(sheetName);

	        // Access row
	        row = sheet.getRow(rownum);

	        // Access cell
	        cell = row.getCell(column);

	        // Create style object
	        style = workbook.createCellStyle();

	        // Set red background color
	        style.setFillForegroundColor(IndexedColors.RED.getIndex());

	        // Apply solid fill pattern
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        // Apply style to cell
	        cell.setCellStyle(style);

	        // Open output stream
	        FO = new FileOutputStream(path);

	        // Write workbook changes
	        workbook.write(FO);

	        // Close resources
	        workbook.close();
	        FI.close();
	        FO.close();
	    }
	
		
}
