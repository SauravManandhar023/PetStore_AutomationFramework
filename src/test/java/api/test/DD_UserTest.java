package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.User_DataProviders;
import io.restassured.response.Response;

public class DD_UserTest {
	
	@Test (priority = 1, dataProvider= "Data", dataProviderClass = User_DataProviders.class)
	public void testCreateUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
		
		User userPayload = new User();
		userPayload.setId(id);
		userPayload.setUsername(username);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setUserStatus(userStatus);
		
		
		Response res = UserEndPoints.createUser(userPayload);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}
	
	@Test(priority = 2, dataProvider = "Username", dataProviderClass = User_DataProviders.class)
	public void testGetUser(String username) {
		
		Response res = UserEndPoints.getUser(username);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	@Test(priority = 3, dataProvider = "SpecificUsername", dataProviderClass = User_DataProviders.class)
	public void testUpdateSpecificUser(String username) {  // Single method cannot have multiple data-providers so we get the specifc username using data provider and manually update the data for the request body
		
		User updt_userpayload = new User();
		updt_userpayload.setId("106");
		updt_userpayload.setUsername("updated_name");
		updt_userpayload.setFirstName("UpdatedFirst");
	    updt_userpayload.setLastName("UpdatedLast");
		updt_userpayload.setEmail("updatedemail@gmail.com");
		updt_userpayload.setPassword("Updated_Password@123");
		updt_userpayload.setPhone("9800000000");
	    updt_userpayload.setUserStatus("1");
		
		Response res = UserEndPoints.updateUser(username, updt_userpayload);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(),200);
		
		Response res1 = UserEndPoints.getUser(username);
		res1.then().log().all();
		res1.then().statusCode(200);
		
	}

}
