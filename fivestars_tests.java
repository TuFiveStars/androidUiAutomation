package fivestars;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class fivestars_tests {
	 AndroidDriver <AndroidElement> dr;

  @BeforeTest
  public void start_emulator() throws MalformedURLException, InterruptedException {	
		File app = new File("/Users/turosales/Documents/Tu doc/APKs/FiveStars-2-3-0-prod-release.apk");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		 capabilities.setCapability("deviceName","Android Emulator"); 
		 capabilities.setCapability("platformVersion", "5.0");
	        capabilities.setCapability("platformName", "Android");		        
		   capabilities.setCapability("app", app.getAbsolutePath());
	       dr = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	       dr.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);	  
	       Thread.sleep(5000L); 
     
	}		
  @BeforeMethod
  public void login() throws MalformedURLException, InterruptedException {

	     dr.findElement(By.xpath("//android.widget.LinearLayout[@bounds='[102,742][978,918]']")).sendKeys("2092769960");//enter phone number
	     										
	    dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/requestpin_button_smscode\")").click();//click the text me
	    dr.findElement(By.xpath("//android.widget.LinearLayout[@bounds='[262,745][817,921]']")).sendKeys("1234");//enter verification code
	   
	       Thread.sleep(5000L); 
	       WebElement texts = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/et_edit_profile_name"));	       
	       Assert.assertEquals(texts.getText(), "TU TEST");
	       System.out.println("log in as Tu");
	       dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/tv_complete_profile_save")).click();//click "that's me"
	       Thread.sleep(5000L);
  			}		
  
  //@Test
  public void verify_check_in() {
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/tv_check_in_card_check_in_text\")").click();//click check in button on carousel
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/rl_checkin_modal_content_container\")").click();//click on check in pop up modal
	  WebElement texts = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/tv_check_in_card_checked_in_text"));	
	  String actual_text = texts.getText();
	  System.out.println(texts.getText());
	  Assert.assertEquals(actual_text, "You're checked in. Mention FiveStars\n"+
	  "before purchase to get your points.");

	  System.out.println("you are checked in");
 	  }
@Test
 public void verify_accept_bonus_reward_prompt_text_present() {
	  
	  WebElement texts = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/tv_bonus_rewards_description"));	
	  String actual_text = texts.getText();
	  System.out.println(texts.getText());
	  Assert.assertEquals(actual_text, "Accept bonus rewards to redeem them in-store. We'll send you reminders before they expire.");
	  System.out.println("prompt to accept bonus reward is present");
	  
	  }
 // @Test
  public void verify_slide_to_accept_bonus_reward() throws Exception {
	  Thread.sleep(2000);
	 WebElement seekBar = dr.findElementById("com.fivestars.FiveStarsConsumer:id/sb_swipe_to_claim");
	 int startX = seekBar.getLocation().getX();// get start point of seekbar
	 int endX = seekBar.getSize().getWidth();// get end point of seekbar
	 int yAxis = seekBar.getLocation().getY();//get vertical location of seekbar
	 int moveToXDirectionAt = 880;//set slidebar move to position. this number is calculated based on (offset + 3/4width)
	 TouchAction act = new TouchAction(dr);//move seekbar using TouchAction class
	 act.longPress(startX, yAxis).moveTo(moveToXDirectionAt, yAxis).release().perform();
	 Thread.sleep(3000L);
	 WebElement texts = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/tv_claim_success_text"));	
	  String actual_text = texts.getText();
	  System.out.println(texts.getText());
	  Assert.assertEquals(actual_text, "You've claimed this bonus reward");
	 

	  System.out.println("you just claimed your bonus reward"); 

 	  }
  
  
  
  
  
  //@Test
  public void verify_search_result_all_businesses_in_San_Jose() throws InterruptedException {
	  dr.findElement(By.xpath("//android.widget.ImageView[@bounds='[668,105][912,213]']")).click();//click explore button
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/omnibar_search_bar\")").click();//click the search field
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/tv_all_businesses\")").click();//click "all businesses"
	 
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/et_search_location\")").sendKeys("san jose");;//type "san jose" in location search
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/action_item_search\")").click();//click the magnify glass
	  
	  
	  AndroidElement box = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/rv_nearby"));//identify results box
      List <MobileElement> names = box.findElements(By.id("com.fivestars.FiveStarsConsumer:id/tv_nearby_card_distance_away"));//the list of business location
      
      int y_max = dr.manage().window().getSize().height;//max phone screen height
      System.out.println("y_max "+y_max);
      //float screen_length = 0;
      while(true){
      for(int i=0; i<names.size(); i++){
      String businesses_location = names.get(i).getText();
     // System.out.println("businesses location: "+ businesses_location);
      Thread.sleep(2000L);
      dr.swipe(500, y_max-75, 500, 75, 5000); //scroll down
      System.out.println("businesses location: "+ businesses_location);
      
      }
      }
  	  }
  
 // @Test
  public void verify_become_a_member_n_removing_membership() throws InterruptedException {
	  dr.findElement(By.xpath("//android.widget.ImageView[@bounds='[668,105][912,213]']")).click();//click explore button
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/omnibar_search_bar\")").click();//click the search field
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/et_search_keywords\")").sendKeys("cream");//type cream in business keyword search
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/et_search_location\")").sendKeys("san francisco");//enter a location
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/action_item_search\")").click();//click the magnify glass
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/iv_nearby_card_add_membership\")").click();//click the plus sign
	  dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/rl_nearby_card_add_membership\")").click();//click become a member
	  Thread.sleep(6000L);	
	  WebElement texts = dr.findElement(By.id("com.fivestars.FiveStarsConsumer:id/tv_nearby_card_is_member"));	       
      Assert.assertEquals(texts.getText(), "Member");
      System.out.println("you became a member of cream");
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/iv_nearby_card_business_logo\")").click();//click cream business profile
      
      int y_max = dr.manage().window().getSize().height;//max phone screen height
      dr.swipe(500, y_max-75, 500, 75, 5000); //scroll down
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/tv_business_info_membership_settings_link\")").click();//click the settings
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/btn_business_info_membership_settings_remove_membership\")").click();//click the remove membership button
      dr.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,75][168,243]']")).click();;//click back arrow
  
  
  }
  
  @AfterMethod
  public void logout() throws MalformedURLException, InterruptedException {
	  Thread.sleep(5000L);
      dr.findElement(By.xpath("//android.widget.ImageView[@bounds='[996,87][1080,231]']")).click();//click settings dots
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/title\")").click();//click account
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"com.fivestars.FiveStarsConsumer:id/tv_edit_profile_sign_out\")").click();//click sign out
      dr.findElementByAndroidUIAutomator("UiSelector().resourceId(\"android:id/button1\")").click();//click ok
	    
		}
 

  @AfterTest
  public void quit(){
		if(dr!=null)
			dr.quit();
	}

	}