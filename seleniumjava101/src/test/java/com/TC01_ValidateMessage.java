package com;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC01_ValidateMessage {

     private RemoteWebDriver driver;
    private String Status = "failed";
    @Parameters({"browser", "version", "platform"})
    @BeforeMethod
    public void windows10_chrome_133_setup(String browser, String version, String platform) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "saurabhgunturkar07" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "J3D9dVPixy0cg3u7f7CaRtwYlhj2bvMXAS3XqhwQMOyokFEtkA" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", platform); 
        caps.setCapability("browserName", browser); 
        caps.setCapability("version", version);
        caps.setCapability("build", "SeleniumJava101");
        caps.setCapability("name", "TC01_Validate Message Display in Simple Form Demo");
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };

        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    @Test(timeOut = 20)
    public void basicTest() throws InterruptedException {
       driver.get("https://www.lambdatest.com/selenium-playground/");

       driver.findElement(By.xpath("//a[text()='Simple Form Demo']")).click();
       Assert.assertTrue((driver.getCurrentUrl()).contains("simple-form-demo"));
       String message = "Welcome to LambdaTest";
    //    driver.findElement(By.cssSelector("input[placeholder=\"Please enter your Message\"]")).click();
       driver.findElement(By.cssSelector("input[placeholder=\"Please enter your Message\"]")).sendKeys(message);
       driver.findElement(By.id("showInput")).click();
       Thread.sleep(1500);
       String displayedValue = driver.findElement(By.xpath("//p[@id='message']")).getText();
       Thread.sleep(150);
       Assert.assertEquals(displayedValue, message);
        Status = "passed";
        Thread.sleep(150);

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        // driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
    
}
