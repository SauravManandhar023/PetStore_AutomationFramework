package api.test;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PetEndPoints;
import api.payload.Pet;
import api.payload.Pet.Category;
import api.payload.Pet.Tag;
import io.restassured.response.Response;

public class PetTest {
	
	Faker fake;
	Pet petObj;
	
	int petId;
	Logger logger;
	
	
	// Helper method for image URL
	private String getImageUrl() {
		return "https://picsum.photos/id/" + fake.number().numberBetween(1, 200) + "/640/480";
	}
	

	@BeforeClass
	
	public void dataSetup() {
		
		
		fake = new Faker(); 
		
		petObj = new Pet(); 
		
		Category category = new Category();
		category.setId(fake.number().numberBetween(1, 100));
		category.setName(fake.animal().name());
		
		Tag tag = new Tag();
		tag.setId(fake.number().numberBetween(1, 100));
		tag.setName(fake.lorem().word());
		
		petObj.setId(fake.number().numberBetween(1000, 9999));
		petObj.setCategory(category);
		petObj.setName(fake.dog().name());
		petObj.setPhotoUrls(Arrays.asList(getImageUrl()));
		petObj.setTags(Arrays.asList(tag));
		petObj.setStatus("available");
		
		petId = petObj.getId();		
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	
	public void testCreatePet() {
		
		logger.info("******** Creating Pet ********");
		Response res = PetEndPoints.createPet(petObj);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		logger.info("******** Pet Created ********");
	}
	
	@Test(priority = 2)  // Changed from priority 1 to 2
	
	public void testRetrievePet() {
		
		logger.info("******** Retriecing Pet ********");
		Response res = PetEndPoints.getPet(petId);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		logger.info("******** Pet Retrieved ********");
	}
	
	@Test(priority = 3)
	
	public void testPartialUpdatePet() {
		
		logger.info("******** Partial Updating Pet ********");
		String name = "Boomer";
		String status = "sold";
		
		Response res = PetEndPoints.partialUpdatePet(petId, name, status);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		// FIX: Verify the update correctly
		Response getRes = PetEndPoints.getPet(petId);
		getRes.then().log().all();
		Assert.assertEquals(getRes.jsonPath().getString("name"), name); 
		Assert.assertEquals(getRes.jsonPath().getString("status"), status); 
		logger.info("******** Pet Partailly Updated ********");
	}

	
	@Test(priority = 4)
	
	public void testFullUpdatePet() {
		
		logger.info("******** Fully Updating Pet ********");
		Category category = new Category();
		category.setId(fake.number().numberBetween(1, 100));
		category.setName(fake.animal().name());
		
		Tag tag = new Tag();
		tag.setId(fake.number().numberBetween(1, 100));
		tag.setName(fake.lorem().word());
		
		// Create a NEW pet object for full update (don't reuse the same reference)
		Pet updatedPet = new Pet();
		updatedPet.setId(petId);  // Use existing petId, not new random!
		updatedPet.setCategory(category);
		updatedPet.setName(fake.dog().name());
		updatedPet.setPhotoUrls(Arrays.asList(getImageUrl()));
		updatedPet.setTags(Arrays.asList(tag));
		updatedPet.setStatus("available");
		
		Response res = PetEndPoints.fullUpdatePet(updatedPet);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		// Verify the update
		Response getRes = PetEndPoints.getPet(petId);
		getRes.then().log().all();
		Assert.assertEquals(getRes.jsonPath().getInt("id"), updatedPet.getId());
		Assert.assertEquals(getRes.jsonPath().getString("name"), updatedPet.getName());
		logger.info("******** Pet Fully Updated ********");
	}
	

	@Test(priority = 6)
	
	public void testUploadPetImage() {
		
		logger.info("******** Uploading Pet Image ********");
		String metadata = "Pet profile picture - " + fake.lorem().sentence();
		
		File img = new File("src/test/resources/test-image.jpg");
		
		Response res = PetEndPoints.uploadPetImage(petId, metadata, img);
		res.then().statusCode(200);
		
		Assert.assertEquals(res.jsonPath().getString("type"), "unknown");
		Assert.assertNotNull(res.jsonPath().getString("message"));
		
		res.then().log().all();
		logger.info("******** Pet Image Uploaded  ********");
		
	}
	
	
	
	@Test(priority = 7)
	
	public void testDeletePet() {
		
		logger.info("******** Deleting Pet ********");
		Response res = PetEndPoints.deletePet(petId);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
		// Verify pet is deleted (should return 404)
		Response getRes = PetEndPoints.getPet(petId);
		Assert.assertEquals(getRes.getStatusCode(), 404);
		logger.info("******** Pet Deleted ********");
	}
}