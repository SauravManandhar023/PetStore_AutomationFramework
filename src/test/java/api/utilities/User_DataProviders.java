package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class User_DataProviders {
	
	private static String excel_path = System.getProperty("user.dir") + "/src/test/resources/test-data/PetStore.xlsx";
	private static String sheet_name = "User";
	
	
	@DataProvider(name = "Data")
	public Object[][] getAllData() throws IOException{
		
		XL_Utility xl = new XL_Utility(excel_path);
		
		int row_count = xl.getRowCount(sheet_name);
		int col_count = xl.getCellCount(sheet_name, 1);
		
		Object apidata[][] = new Object[row_count][col_count];
		
		for(int i = 1; i <= row_count ; i++) {
			for(int j = 0; j < col_count; j++) {
				
				apidata[i-1][j] = xl.getCellData(sheet_name, i, j);
			}
		}
	
		return apidata;
		
	}
	
	@DataProvider(name = "Username")
	public String[] getUserNames() throws IOException{
		XL_Utility xl = new XL_Utility(excel_path);
		
		int row_count = xl.getRowCount(sheet_name);
		
		String apidata[] = new String[row_count];
		
		for(int i = 1; i<= row_count; i++) {
			apidata[i-1] = xl.getCellData(sheet_name, i, 1);
		}
		
		return apidata;
		
	}
	
	@DataProvider(name = "SpecificUsername")
	public Object[][] getSpecificUsername() throws IOException {
	    
	    XL_Utility xl = new XL_Utility(excel_path);
	    String username = xl.getCellData(sheet_name, 1, 1); // Only gets the username of first perosn
	    
	    return new Object[][] {
	        { username }  // This is a 2D array with one row and one column
	    };
	}
}
