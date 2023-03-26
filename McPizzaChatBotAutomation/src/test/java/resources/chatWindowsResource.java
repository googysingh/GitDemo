package resources;

//enum is special class in java which has collection of constants or methods
public enum chatWindowsResource {

	chatButton("//img[@alt='Chat agent button']"),
	chatButtonGetStarted("//a[contains(text(),'Get Started')]"),
	GetPlaceAPI("/maps/api/place/get/json");
	private String resource;

	chatWindowsResource(String resource) {
		
		this.resource = resource;
	}
	
	public String getReource() {
		return resource;
	}

	
}
