package steps;

import io.cucumber.java.en.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.Assert.*;

public class LoginSteps {

    WebDriver driver;
    WebDriverWait wait;
    private static final String URL = "https://practicetestautomation.com/practice-test-login/";

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        driver.get(URL);
    }

    @When("I enter username {string} and password {string}")
    public void iEnterCredentials(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("I click the login button")
    public void iClickLoginButton() {
        driver.findElement(By.id("submit")).click();
    }

    @Then("I should see the message {string}")
    public void iShouldSeeMessage(String message) {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertTrue(heading.getText().contains(message));
    }

    @Then("I should see an error {string}")
    public void iShouldSeeError(String error) {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(errorElement.getText().contains(error));
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }
}
