package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class Pet_DataProviders {

	private static String excel_path = System.getProperty("user.dir") + "/src/test/resources/test-data/PetStore.xlsx";
	private static String sheet_name = "Pet";
	private static String updated_sheet = "Updated Pet";
	
	@DataProvider (name = "PetData")
	public Object[][] getAllData() throws IOException {
		
		XL_Utility xl = new XL_Utility(excel_path);
		
		int row_count = xl.getRowCount(sheet_name); //Gives the count index of last row
		int col_count = xl.getCellCount(sheet_name, 1); // Total no of columns
		
		Object[][] apidata = new Object[row_count][col_count];
		
		for(int i = 1; i <= row_count; i++) {
			for(int j = 0; j < col_count; j++) {
				
				apidata[i-1][j] =xl.getCellData(sheet_name, i, j);
			}
		}
		
		return apidata;
		
	}
	
	@DataProvider (name = "PetID")
	public Object[][] getPetID() throws IOException {
		
		XL_Utility xl = new XL_Utility(excel_path);
		
		int row_count = xl.getRowCount(sheet_name); //Gives the count index of last row
		
		Object[][] apidata = new Object[row_count][1];
		
		for(int i = 1; i <= row_count; i++) {
				apidata[i-1][0] =xl.getCellData(sheet_name, i, 0);
		}
		
		return apidata;
		
	}
	
	@DataProvider (name = "PetPartialData")
	public Object[][] partailUpdateData() throws IOException {
		
		XL_Utility xl = new XL_Utility(excel_path);
		
		Object[][] apidata = new Object[1][3]; // Only require 1 row with 3 columns
		
		//If you want the partial update of all the pets then use loop as below:
		
//		for(int i = 1; i <= row_count; i++) {
//				apidata[i-1][0] =xl.getCellData(sheet_name, i, 0);
//				apidata[i-1][1] = xl.getCellData(sheet_name, i, 1);
//				apidata[i-1][2] = xl.getCellData(sheet_name, i, 7);
//				
//		}
		
		// For only the single pet at first
		
		apidata[0][0] = xl.getCellData(sheet_name, 1, 0); //petid
		apidata[0][1] = xl.getCellData(sheet_name, 1, 1); //name
		apidata[0][2] = xl.getCellData(sheet_name, 1, 7); //status
		
		return apidata;
		
	}
	
	@DataProvider (name = "PetFullUpdatedData")
	public Object[][] getUpdatedData() throws IOException {
		
		XL_Utility xl = new XL_Utility(excel_path);
		
		int row_count = xl.getRowCount(updated_sheet); //Gives the count index of last row
		int col_count = xl.getCellCount(updated_sheet, 1); // Total no of columns
		
		Object[][] apidata = new Object[row_count][col_count];
		
		for(int i = 1; i <= row_count; i++) {
			for(int j = 0; j < col_count; j++) {
				
				apidata[i-1][j] =xl.getCellData(updated_sheet, i, j);
			}
		}
		
		return apidata;
		
	}
	
	
	
	
}