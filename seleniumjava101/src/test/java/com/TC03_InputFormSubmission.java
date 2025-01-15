package com;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC03_InputFormSubmission {

    private RemoteWebDriver driver;
    private String status = "failed";
    @Parameters({"browser", "version", "platform"})
    @BeforeMethod
    public void setup(String browser, String version, String platform) throws MalformedURLException{
        String username = System.getenv("LT_USERNAME") == null ? "saurabhgunturkar07" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "J3D9dVPixy0cg3u7f7CaRtwYlhj2bvMXAS3XqhwQMOyokFEtkA" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", platform); 
        caps.setCapability("browserName", browser); 
        caps.setCapability("version", version);
        caps.setCapability("build", "SeleniumJava101");
        caps.setCapability("name", "TC03_InputFormSubmission");
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };

        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }


    @Test(timeOut = 20)
    public void formSubmissionTest(){
        
        driver.get("https://www.lambdatest.com/selenium-playground/");
        driver.findElement(By.xpath("//a[@href='https://www.lambdatest.com/selenium-playground/input-form-demo']")).click();
        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        WebElement name = driver.findElement(By.xpath("//div[@class='form-group w-4/12 smtablet:w-full text-section pr-20 smtablet:pr-0']/input[@type='text']"));
		String expectedMsg = name.getAttribute("validationMessage");
		String actualMsg = "Please fill out this field.";
		Assert.assertEquals(actualMsg, expectedMsg);

        name.sendKeys("Robot");
        
        driver.findElement(By.xpath("//div[@class='form-group w-4/12 smtablet:w-full text-section pr-20 smtablet:pr-0']/input[@type='email']")).sendKeys("Test123@gmail.com");
    
        driver.findElement(By.xpath("//div[@class='form-group w-4/12 smtablet:w-full']/input[@type='password']")).sendKeys("Test@1234");

        driver.findElement(By.xpath("//input[@id='company']")).sendKeys("LambdaTest organization");

        driver.findElement(By.xpath("//input[@id='websitename']")).sendKeys("www.lambdaTest.com");

        //select USA code
        WebElement country = driver.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full pr-20 smtablet:pr-0']/select[@name='country']"));
        Select countrySelect = new Select(country);
        countrySelect.selectByValue("US");

        driver.findElement(By.xpath("//input[@id='inputCity']")).sendKeys("Pune");

        driver.findElement(By.xpath("//input[@id='inputAddress1']")).sendKeys("ABC tech park");

        driver.findElement(By.xpath("//input[@id='inputAddress2']")).sendKeys("near shivaji nagar");

        driver.findElement(By.xpath("//input[@id='inputState']")).sendKeys("Maharashtra");

        driver.findElement(By.xpath("//input[@id='inputZip']")).sendKeys("000234");

        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        WebElement successElement = driver.findElement(By.xpath("//p[text()='Thanks for contacting us, we will get back to you shortly.']"));
        String ExpectedMsg = successElement.getText();
        String ActualMsg = "Thanks for contacting us, we will get back to you shortly.";
        Assert.assertEquals(ActualMsg, ExpectedMsg);

        status="passed";
    }





     @AfterMethod
    public void tearDown() {
        // driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
        driver.executeScript("lambda-status=" + status);
        driver.quit();
    }
    
}
