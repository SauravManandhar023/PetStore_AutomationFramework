package api.endpoints;

import static io.restassured.RestAssured.given;

import java.io.File;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoints {
	
	public static Response createPet(Pet pet_payload) {
		
		Response res = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(pet_payload)
		.when()
			.post(Routes.postPetURL);  // ✅ FIXED: Was using getPetURL, should be postPetURL
		
		return res;
	}
	
	public static Response getPet(int petID){
		
		Response res = given()
			.accept(ContentType.JSON)
			.pathParam("petId", petID)
		.when()
			.get(Routes.getPetURL);
		
		return res;
	}
	
	public static Response fullUpdatePet(Pet pet_payload) {
		
		Response res = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(pet_payload)  // ✅ FIXED: Missing body!
		.when()
			.put(Routes.fullUpdatePetURL);
		
		return res;
	}
	
	public static Response partialUpdatePet(int petID, String Name, String Status) {
		
		Response res = given()
			.contentType("application/x-www-form-urlencoded")
			.pathParam("petId", petID)
			.formParam("name", Name)
			.formParam("status", Status)
		.when()
			.post(Routes.partialUpdatePetURL);
		
		return res;
	}
	
	public static Response deletePet(int PetID) {
		
		Response res = given()
			.pathParam("petId", PetID)
		.when()
			.delete(Routes.deletePetURL);
		
		return res;
	}
	
	public static Response uploadPetImage(int PetId, String additionalMetadata, File imgFile) {
		
		Response res = given()
				.pathParam("petId", PetId)
				.multiPart("additionalMetadata", additionalMetadata)
				.multiPart("file", imgFile)
			.when()
				.post(Routes.uploadImageURL);
		
		return res;
				
	}
}