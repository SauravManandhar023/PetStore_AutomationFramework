package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
	
	public static Response createUser(User user_payload) {
		
		Response res = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(user_payload)
		.when()
			.post(Routes.postuserURL);
		
		return res;
		
	}
	
	public static Response getUser(String userName){
		
		Response res = given()
			.pathParam("username", userName)
		.when()
			.get(Routes.getuserURL);
		
		return res;
	}
	
	public static Response updateUser(String userName, User user_payload){
		
		Response res = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(user_payload)
		.when()
			.put(Routes.updateuserURL);
		return res;
		
	}
	
	public static Response deleteUser(String userName) {
		
		Response res = given()
			.accept(ContentType.JSON)
			.pathParam("username", userName)
		.when()
			.delete(Routes.deleteuserURL);
				
			return res;
	}

}
