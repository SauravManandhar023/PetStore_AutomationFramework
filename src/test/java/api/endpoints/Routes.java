package api.endpoints;

public class Routes {
	
	public static String base_url = "https://petstore.swagger.io/v2";

	//User Module
	public static String postUserURL = base_url + "/user";
	public static String getUserURL = base_url + "/user/{username}";
	public static String updateUserURL = base_url + "/user/{username}";
	public static String deleteUserURL = base_url + "/user/{username}"; 
	
	
	//Pet Module
	public static String postPetURL = base_url + "/pet";
	public static String getPetURL = base_url + "/pet/{petId}";
	public static String fullUpdatePetURL = base_url + "/pet";  // FULL update (ID in body)
	public static String partialUpdatePetURL = base_url + "/pet/{petId}";
	public static String deletePetURL = base_url + "/pet/{petId}";
	public static String uploadImageURL = base_url + "/pet/{petId}/uploadImage";
	
	
	//Store Module
	
	public static String getInventoryURL = base_url + "/store/inventory";
	public static String postOrderURL = base_url + "/store/order";
	public static String findOrderURL = base_url + "/store/order/{orderId}";
	public static String deleteOrderURL = base_url + "/store/order/{orderId}";
	
	
	
 	
	
	
	

}
