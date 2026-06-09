package api.test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.PetEndPoints;
import api.payload.Pet;
import api.payload.Pet.Category;
import api.payload.Pet.Tag;
import api.utilities.Pet_DataProviders;
import io.restassured.response.Response;

public class DD_PetTest {
	
	@Test (priority = 1, dataProvider = "PetData", dataProviderClass = Pet_DataProviders.class)
	public void testCreatePet(String id, String name, String categoryID, String categoryName, String photoURL, String tagID, String tagName, String status) {
		
		Category category = new Category();
		category.setId(Integer.parseInt(categoryID));
		category.setName(categoryName);
		
		Tag tag = new Tag();
		tag.setId(Integer.parseInt(tagID));
		tag.setName(tagName);
		
		
		Pet pet_payload = new Pet();
		pet_payload.setId(Integer.parseInt(id));
		pet_payload.setName(name);
		pet_payload.setCategory(category);
		pet_payload.setPhotoUrls(Arrays.asList(photoURL));
		pet_payload.setTags(Arrays.asList(tag));
		pet_payload.setStatus(status);
		
		Response res =PetEndPoints.createPet(pet_payload);
		res.then().log().all();
		
		Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test (priority = 2, dataProvider = "PetID", dataProviderClass = Pet_DataProviders.class)
	public void testRetrievesPet(String id) {
		
		Response res =PetEndPoints.getPet(Integer.parseInt(id));
		res.then().log().all();
		
		Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test (priority = 3, dataProvider = "PetPartialData", dataProviderClass = Pet_DataProviders.class)
	public void testPartialPetUpdate (String id, String name, String status) {
		
		//Update
		name = "Bommer";
		status = "sold";
		
		Response res =PetEndPoints.partialUpdatePet(Integer.parseInt(id), name, status);
		res.then().log().all();
		
		Assert.assertEquals(res.statusCode(), 200);
		
		// Verify update
	    Response getRes = PetEndPoints.getPet(Integer.parseInt(id));

	    Assert.assertEquals(getRes.statusCode(), 200);
	    Assert.assertEquals(getRes.jsonPath().getString("name"), name);
	    Assert.assertEquals(getRes.jsonPath().getString("status"), status);
	}
	
	
	// Update All User
	
	@Test (priority = 4, dataProvider = "PetFullUpdatedData", dataProviderClass = Pet_DataProviders.class)
	public void testFullPetUpdate(String id, String name, String categoryID, String categoryName, String photoURL, String tagID, String tagName, String status) {
		
		Category category = new Category();
		category.setId(Integer.parseInt(categoryID));
		category.setName(categoryName);
		
		Tag tag = new Tag();
		tag.setId(Integer.parseInt(tagID));
		tag.setName(tagName);
		
		
		Pet pet_payload = new Pet();
		pet_payload.setId(Integer.parseInt(id));
		pet_payload.setName(name);
		pet_payload.setCategory(category);
		pet_payload.setPhotoUrls(Arrays.asList(photoURL));
		pet_payload.setTags(Arrays.asList(tag));
		pet_payload.setStatus(status);
		
		Response res =PetEndPoints.fullUpdatePet(pet_payload);
		res.then().log().all();
		
		Assert.assertEquals(res.statusCode(), 200); 
	}
	
	@Test (priority = 5, dataProvider = "PetID", dataProviderClass = Pet_DataProviders.class)
	public void testDeleteAllPet(String id) {
		
		Response res =PetEndPoints.deletePet(Integer.parseInt(id));
		res.then().log().all();
		
		Assert.assertEquals(res.statusCode(), 200);
		
		// Verify delete
	    Response getRes = PetEndPoints.getPet(Integer.parseInt(id));

	    Assert.assertEquals(getRes.statusCode(), 404);
		
	}
}
