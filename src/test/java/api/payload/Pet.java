package api.payload;

import java.util.List;

public class Pet {
	
	// Request Body Structure
	
//	{
//		  "id": 9223372000001084000,
//		  "category": {
//		    "id": 0,
//		    "name": "string"
//		  },
//		  "name": "doggie",
//		  "photoUrls": [
//		    "string"
//		  ],
//		  "tags": [
//		    {
//		      "id": 0,
//		      "name": "string"
//		    }
//		  ],
//		  "status": "available"
//		}
	
	
	int id;
	Category category;           
	String name;			
	List<String> photoUrls;      // Array of Strings
	List<Tag> tags;              // Array of Tag (Custom) Objects
	String status;
	
	
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public Category getCategory() {
			return category;
		}
		
		public void setCategory(Category category) {
			this.category = category;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public List<String> getPhotoUrls() {
			return photoUrls;
		}
		
		public void setPhotoUrls(List<String> photoUrls) {
			this.photoUrls = photoUrls;
		}
		
		public List<Tag> getTags() {
			return tags;
		}
		
		public void setTags(List<Tag> tags) {
			this.tags = tags;
		}
		
		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
	
	public static class Category{
		
		int id;
		String name;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Tag{
		
		int id;
		String name;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	}
}
