package AutomationTestingCertfication;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestScenario {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @Parameters({"browser", "version", "platform"})
    @BeforeMethod
    public void setup(String browser, String version, String platform) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "saurabhgunturkar07" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "J3D9dVPixy0cg3u7f7CaRtwYlhj2bvMXAS3XqhwQMOyokFEtkA" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", platform);
        caps.setCapability("browserName", browser);
        caps.setCapability("version", version);
        caps.setCapability("build", "AutomationTestingCertification");
        caps.setCapability("name", "Test Scenario");
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[]{"Feature", "Falcon", "Severe"};
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    public void automationTestScenario() {
        // Check if the driver is properly initialized
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized. Setup method failed.");
        }

        // Open the LambdaTest website
        driver.get("https://www.lambdatest.com/");

        // Scroll the "Explore all Integrations" link into view if needed
        WebElement seeAllIntegrations = driver.findElement(By.linkText("Explore all Integrations"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", seeAllIntegrations);

        // Perform Control + Click action to open link in new tab
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.keyDown(org.openqa.selenium.Keys.CONTROL).click(seeAllIntegrations).keyUp(org.openqa.selenium.Keys.CONTROL).perform();

        // Get all opened tabs
        Set<String> handles = driver.getWindowHandles();
        String originalTab = driver.getWindowHandle();
        for (String handle : handles) {
            if (!handle.equals(originalTab)) {
                // Switch to the new tab
                driver.switchTo().window(handle);
                break;
            }
        }

        // Ensure the new page is fully loaded
        WebElement body = driver.findElement(By.tagName("body"));
        if (body.isDisplayed()) {
            System.out.println("New tab title: " + driver.getTitle());
            Status = "passed";
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.executeScript("lambda-status=" + Status);
            driver.quit();
        }
    }
}
