package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.openqa.selenium.By;
import stepDefinations.stepDefination;

public class Utils {
	
	public static String getValue(String key) throws IOException {
		// Load the locators from the properties file
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("D:\\Users\\gsingh3813\\eclipse-workspace\\McPizzaChatBotAutomation\\src\\test\\java\\resources\\locators.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public static String chatBoxEngine(String str) throws InterruptedException, IOException {
		stepDefination.driver.findElement(By.id(Utils.getValue("chatWindowTextBox"))).sendKeys(str);
		stepDefination.driver.findElement(By.xpath(Utils.getValue("chatWindowSendTextButton"))).click();
		Thread.sleep(3000);
		String response = stepDefination.driver.findElement(By.xpath(Utils.getValue("chatWindowAIResponse"))).getText();
		return response;
	}

	public static String assertValue(String str,String expRes) {
		ArrayList<String> parts = new ArrayList<>(Arrays.asList(str.split(",")));
		String status=null;
		for(int i=0;i<parts.size();i++) {
			//System.out.println(parts.get(i));
			if(parts.get(i).contains(expRes)) {
				status =expRes;
				System.out.println(status);
			}
		}
		System.out.println(status);
		return status;
	}
	
}
