package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.StoreEndPoints;
import api.payload.Store;
import io.restassured.response.Response;

public class StoreTest {
	
	Faker fake;
	Store str;
	long orderId;
	
	public Logger logger;
	
	@BeforeClass
	
	public void dataSetup() {
		
		fake = new Faker();
		str= new Store();
		
		str.setId(fake.number().numberBetween(1, 10));
		str.setPetId(fake.number().numberBetween(101, 201));
		str.setQuantity(fake.number().numberBetween(1, 5));
		str.setShipDate(null); // can be null server generates
		
		String[] stautses = {"placed", "approved", "delivered", "cancelled"};
		str.setStatus(stautses[fake.number().numberBetween(0, 3)]);
		
		str.setComplete(fake.bool().bool());
		
		orderId = str.getId();
		
		logger = LogManager.getLogger(this.getClass());
				
	}
	
	
	@Test(priority = 1)
	
	public void testPlaceOrder() {
		logger.info("******** Placing Order ********");
		Response res = StoreEndPoints.placeOrder(str);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		logger.info("******** Order Placed ********");
	}
	
	@Test(priority = 2)
	
	public void testFindOrder() {
		logger.info("******** Finding Order ********");
		Response res = StoreEndPoints.findOrder(orderId);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.jsonPath().getInt("id"), orderId);
		logger.info("******** Order Found ********");
	}
	
	@Test (priority = 3)
	
	public void testGetInventory() {
		
		logger.info("******** Getting Inventory ********");
		
		Response res =StoreEndPoints.getInventory();
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertNotNull(res.jsonPath().getMap("$"));  // Inventory is a map
		
		logger.info("******** Inventory Rettrieved ********");
	}
	
@Test(priority = 4)
	
	public void testDeleteOrder() {
		
		logger.info("******** Deleting Order ********");
		Response res = StoreEndPoints.deleteOrder(orderId);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		//Verify order is deleted
		Response res1 = StoreEndPoints.findOrder(orderId);
		res1.then().log().all();
		res1.then().statusCode(404);
		logger.info("******** Order Deleted ********");

	}

}
