package com;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TC02_DragAndDropSliders {
    
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
        caps.setCapability("name", "TC02_Drag and Drop Sliders");
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };

        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test(timeOut = 20)
    public void dragDropTest(){
        driver.get("https://www.lambdatest.com/selenium-playground/");
        driver.findElement(By.xpath("//a[@href='https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo']")).click();
        WebElement slider15 = driver.findElement(By.cssSelector("input[value='15']"));
        
        Actions move = new Actions(driver);
        Actions action = (Actions)move.dragAndDropBy(slider15, 212, 0);
        action.perform();
        WebElement Expected_Range = driver.findElement(By.xpath(".//*[@id='slider3']/div/output"));
        String Expe_range = Expected_Range.getText();
        System.out.println(Expe_range+"_______________");
        String Actual_Range = "95";

        if (Expe_range.contains(Actual_Range)) {
            System.out.println("Range is matched");
        } else {
            System.out.println("Range is not matched!");
        }

        status = "passed";

    }

    @AfterMethod
    public void tearDown() {
        // driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
        driver.executeScript("lambda-status=" + status);
        driver.quit();
    }
}
