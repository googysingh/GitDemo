package stepDefinations;

import java.io.IOException;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import resources.Utils;

public class stepDefination extends Utils {
	
	public static WebDriver driver;
	String response5=null;
	String pizzaType;
	String crust;
	String size;
	String topping1;
	String topping2;
	String OrderSummary;
	@Given("user had open chat bot window")
		public void user_had_open_chat_bot_window() throws IOException, InterruptedException {
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get(Utils.getValue("URL"));
			driver.manage().window().maximize();
			//Thread.sleep(10000);
			
			driver.findElement(By.xpath(Utils.getValue("chatButton"))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(Utils.getValue("getStarted"))).click();
			
			// Switch to the iframe
			WebElement iframe = driver.findElement(By.xpath(Utils.getValue("chatWindowiFrame")));
					driver.switchTo().frame(iframe);
					
			//Entering User details
			driver.findElement(By.id(Utils.getValue("chatWindowFirstName"))).sendKeys("Gurpreet");
			driver.findElement(By.id(Utils.getValue("chatWindowLastName"))).sendKeys("Singh");
			driver.findElement(By.id(Utils.getValue("chatWindowEmail"))).sendKeys("abc@xyz.com");
			driver.findElement(By.xpath(Utils.getValue("chatWindowSubmitButton"))).click();
		}
		
		@When("user ordered {string} {string} {string} pizza details")
		public void user_ordered_pizza_details(String pizzaType,String crust, String size) throws InterruptedException, IOException {
			this.pizzaType=pizzaType;
			this.crust = crust;
			this.size = size;
			
			//Pizza Order placing flow
			// Wait for the element to appear
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Utils.getValue("chatWindowAIResponse"))));
			
			String response=Utils.chatBoxEngine("I want to order pizza");
			
			//Asserting Response from AI bot
			String botRes1="We have a wide variety of freshly backed pizza . What would you like to Order ?";

			Assert.assertEquals(Utils.assertValue(response, botRes1),botRes1);
			
			//Pizza Veg Non-Veg Selection
			
			Thread.sleep(3000);
			String response1=Utils.chatBoxEngine(pizzaType);
			System.out.println(response1);
			
			 // Create an instance of Actions class
	        Actions actions = new Actions(driver);
	        
	        if(pizzaType.contentEquals("veg")) {
			 // Find the element to click
	        WebElement cheeseOption = driver.findElement(By.xpath(Utils.getValue("chatWindowCheeseOption")));
	        WebElement tomatoOption = driver.findElement(By.xpath(Utils.getValue("chatWindowTomatoOption")));
	        WebElement submitButton = driver.findElement(By.xpath(Utils.getValue("chatWindowFlaSubmitButton")));
	        
	        this.topping1= "tomato";
	        this.topping2= "cheese";
	        // Perform the mouse click
	        actions.click(cheeseOption).click(tomatoOption).click(submitButton).perform();
	        Thread.sleep(5000);
	        }else {
	        	WebElement cheeseOption = driver.findElement(By.xpath(Utils.getValue("chatWindowBaconOption")));
	 	        WebElement tomatoOption = driver.findElement(By.xpath(Utils.getValue("chatWindowPepperoniOption")));
	 	        WebElement submitButton = driver.findElement(By.xpath(Utils.getValue("chatWindowFlaSubmitButton")));
	 	       this.topping1= "pepperoni";
	 	       this.topping2= "bacon";

	 	        // Perform the mouse click
	 	        actions.click(cheeseOption).click(tomatoOption).click(submitButton).perform();
	 	        Thread.sleep(5000);
	        }
			String response3 = driver.findElement(By.xpath(Utils.getValue("chatWindonOrderDetails"))).getText();
			System.out.println(response3);
	        
			
			// Type of Pizza Thin Crust and Thick Crust
			Thread.sleep(3000);
			String response4=Utils.chatBoxEngine(crust);
			System.out.println(response4);
			
	        
			//  Select Your Pizza Size 
			Thread.sleep(3000);
			response5=Utils.chatBoxEngine(size);
			System.out.println(response5);
		}
		
		@Then("chatbot is able to place order")
		public void chatbot_is_able_to_place_order() throws InterruptedException, IOException {
			//Fetching Pizza order details
			String[] arrOfStr = response5.split("\n");
			System.out.println(arrOfStr[0]);
			
	        if(arrOfStr[0].contains(Utils.getValue("confirmBeforOrder"))) {
	        	OrderSummary= driver.findElement(By.xpath(Utils.getValue("chatWindonOrderDetails"))).getText();
	        	System.out.println(OrderSummary);
	        }
	        
	      //Asserting Order Details from AI bot
			
	        Assert.assertEquals(Utils.assertValue(OrderSummary, pizzaType),pizzaType);
	        Assert.assertEquals(Utils.assertValue(OrderSummary, topping1),topping1);
	        Assert.assertEquals(Utils.assertValue(OrderSummary, topping2),topping2);
	        Assert.assertEquals(Utils.assertValue(OrderSummary, crust),crust);
	        Assert.assertEquals(Utils.assertValue(OrderSummary, size),size);
	        	        
	        //  Confirming Pizza order 
			Thread.sleep(3000);
			String orderConfirmMessage=Utils.chatBoxEngine("Yes");
			
			//Asserting Order Confirm Message
			System.out.println(orderConfirmMessage);
			Assert.assertEquals(Utils.assertValue(orderConfirmMessage, Utils.getValue("confirmMessage")),Utils.getValue("confirmMessage"));
		}
		
		@Then("user is able to provide feedback")
		public void user_is_able_to_provide_feedback() throws InterruptedException, IOException {
			//Providing Feedback
			Thread.sleep(3000);
			driver.findElement(By.xpath(Utils.getValue("chatWindowThumbsupButton"))).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(Utils.getValue("chatWndowComboBox"))).sendKeys("Excellent");
			driver.findElement(By.xpath(Utils.getValue("chatWindowsFeedBackSubmitButton"))).click();
			Thread.sleep(3000);
			
			String feedBackMessage = driver.findElement(By.xpath(Utils.getValue("chatWindowThankyou"))).getText();
			System.out.println(feedBackMessage);
			Assert.assertEquals(Utils.getValue("expectedFeedBackMessage"), feedBackMessage);
			
			//Drive Closing
			driver.close();
		}
}
