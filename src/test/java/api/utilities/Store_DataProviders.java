package api.utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class Store_DataProviders {
    
    private static String excel_path = System.getProperty("user.dir") + "/src/test/resources/test-data/PetStore.xlsx";
    private static String sheet_name = "Store";
    
    @DataProvider(name = "StoreData")
    public Object[][] getAllData() throws IOException { 
        
        XL_Utility xl = new XL_Utility(excel_path);
        
        int row_count = xl.getRowCount(sheet_name);
        int col_count = xl.getCellCount(sheet_name, 1);
        
        Object[][] apidata = new Object[row_count][col_count];
        
        for(int i = 1; i <= row_count; i++) {
            for(int j = 0; j < col_count; j++) {
                apidata[i-1][j] = xl.getCellData(sheet_name, i, j);
            }
        }
        
        // No close needed - keep as is
        return apidata;
    }
    
    @DataProvider(name = "OrderIDs")
    public Object[][] getOrderIDs() throws IOException { 
        
        XL_Utility xl = new XL_Utility(excel_path);
        
        int row_count = xl.getRowCount(sheet_name);
        
        Object[][] apidata = new Object[row_count][1];
        
        for(int i = 1; i <= row_count; i++) {
            apidata[i-1][0] = xl.getCellData(sheet_name, i, 0);
        }
        
        // No close needed - keep as is
        return apidata;
    }
}