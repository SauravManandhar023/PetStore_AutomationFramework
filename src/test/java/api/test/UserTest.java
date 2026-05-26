package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker fake;
	User userPayload;
	String username;
	
	@BeforeClass
	public void dataSetup() {
		
		fake = new Faker();
		userPayload = new User();
		
		userPayload.setId(fake.idNumber().hashCode());
		// FIX: Use username() instead of fullName() to avoid spaces
		userPayload.setUsername(fake.name().username());
		userPayload.setFirstName(fake.name().firstName());
		userPayload.setLastName(fake.name().lastName());
		userPayload.setEmail(fake.internet().safeEmailAddress());
		userPayload.setPassword(fake.internet().password(8, 16, true, true));
		userPayload.setPhone(fake.phoneNumber().cellPhone());
		
		username = userPayload.getUsername();
	}
	
	@Test(priority = 1)
	public void testCreateUser() {
		
		Response res = UserEndPoints.createUser(userPayload);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void testGetUser() {
		Response res = UserEndPoints.getUser(username);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		// Verify the retrieved user matches what we created
		Assert.assertEquals(res.jsonPath().getString("username"), username);
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		
		// Update user data
		userPayload.setFirstName(fake.name().firstName());
	    userPayload.setLastName(fake.name().lastName());
	    userPayload.setEmail(fake.internet().safeEmailAddress());
		
		Response res = UserEndPoints.updateUser(username, userPayload);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		
		// IMPORTANT FIX: The update response doesn't contain user details
		// We need to do a GET request to verify the updates
		Response getRes = UserEndPoints.getUser(username);
		getRes.then().log().all();
		
		// Verify the updates using the GET response
		Assert.assertEquals(getRes.getStatusCode(), 200);
		Assert.assertEquals(getRes.jsonPath().getString("firstName"), userPayload.getFirstName());
		Assert.assertEquals(getRes.jsonPath().getString("lastName"), userPayload.getLastName());
		Assert.assertEquals(getRes.jsonPath().getString("email"), userPayload.getEmail());
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		
		Response res = UserEndPoints.deleteUser(username);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200);
		
		// Verify user is deleted
		Response getRes = UserEndPoints.getUser(username);
		Assert.assertEquals(getRes.getStatusCode(), 404);
	}
}