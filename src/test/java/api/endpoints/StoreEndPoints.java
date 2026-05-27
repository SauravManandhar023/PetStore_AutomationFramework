package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.Store;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndPoints {
	
	
	public static Response getInventory(){
		Response res = given()
			.accept(ContentType.JSON)
		.when()
			.get(Routes.getInventoryURL);
		
		return res;
	}
	
	public static Response placeOrder(Store order_payload) {
		
		Response res = given()
				.contentType("application/json")
				.accept(ContentType.JSON) 
				.body(order_payload)
		.when()
				.post(Routes.postOrderURL);
		return res;
	}
	
	public static Response findOrder(int oId) {
		
		Response res = given()
				.accept(ContentType.JSON)
				.pathParam("orderId", oId)
			.when()
				.get(Routes.findOrderURL);
		
		return res;
	}
	
	public static Response deleteOrder(int order_id) {
		
		Response res = given()
				.accept(ContentType.JSON)
				.pathParam("orderId", order_id)
			.when()
				.delete(Routes.deleteOrderURL);
		return res;
	}
	
	

}
