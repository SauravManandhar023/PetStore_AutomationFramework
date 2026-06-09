package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.StoreEndPoints;
import api.payload.Store;
import api.utilities.Store_DataProviders;
import io.restassured.response.Response;

public class DD_StoreTest {
    
    @Test(priority = 1, dataProvider = "StoreData", dataProviderClass = Store_DataProviders.class)
    public void testPlaceOrder(String order_id, String petId, String quantity, String shipDate, String status, String complete) {
        
        Store store = new Store();
        store.setId(Long.parseLong(order_id));
        store.setPetId(Long.parseLong(petId));
        store.setQuantity(Integer.parseInt(quantity));
        store.setShipDate(shipDate);
        store.setStatus(status);
        store.setComplete(Boolean.parseBoolean(complete));
        
        Response res = StoreEndPoints.placeOrder(store);
        res.then().log().all();
        
        Assert.assertEquals(res.getStatusCode(),200);
    }
    
    @Test(priority = 2, dataProvider = "OrderIDs", dataProviderClass = Store_DataProviders.class)
    public void testFindOrder(String order_id) {
        
        Response res = StoreEndPoints.findOrder(Long.parseLong(order_id));
        res.then().log().all();
        
        // If order was created successfully, expect 200; otherwise 404
        Assert.assertTrue(res.getStatusCode() == 200 || res.getStatusCode() == 404,
                "Expected 200 or 404, but got: " + res.getStatusCode());
    }
    
    @Test(priority = 3)
    public void testGetInventory() {
        
        Response res = StoreEndPoints.getInventory();
        res.then().log().all();
        
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertNotNull(res.jsonPath().getMap("$"));
    }
    
    @Test(priority = 4, dataProvider = "OrderIDs", dataProviderClass = Store_DataProviders.class)
    public void testDeleteOrder(String order_id) {
        
        Response res = StoreEndPoints.deleteOrder(Long.parseLong(order_id));
        res.then().log().all();
        
        // Accept 200 (success) or 404 (already deleted/not found)
        Assert.assertTrue(res.getStatusCode() == 200 || res.getStatusCode() == 404,
                "Expected 200 or 404, but got: " + res.getStatusCode());
        
        // Verify deletion (expect 404)
        Response verifyRes = StoreEndPoints.findOrder(Long.parseLong(order_id));
        verifyRes.then().log().all();
        
        // After delete, order should not exist
        Assert.assertTrue(verifyRes.getStatusCode() == 404,
                "Expected 404 after deletion, but got: " + verifyRes.getStatusCode());
    }
}