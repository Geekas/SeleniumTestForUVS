package SeleniumTestsFroUVS.SeleniumTestsForUVS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageObjects.HealthInsurancePage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HealthInsurancePageTests {

	private WebDriverWait wait;
	private WebDriver driver = null;
	private HealthInsurancePage healthPage;

	@Parameters("browserName")
	@BeforeTest
	public void setDriver(String browserName) {
		// Driver manager lets to initialize webdriver on many platforms without
		// geckoDriver
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// We will do test for this HealthInsurance page. We are using page
		// object pattern.
		healthPage = new HealthInsurancePage(driver);
		wait = new WebDriverWait(driver, 5);
	}

	@Test
	public void testIfLoginWithGoodCredentials() {
		// Go to web page
		driver.get("https://testinsurance.eps.lt/IF/apdraustiesiems/SelfServiceWeb/Login");

		// Filling the first 4 fields
		fillFourFieldsWithFourDigits();

		healthPage.fillTheNumberField(5, "5678");

		// Fill password
		healthPage.writePassw("1012");

		// Press login button
		healthPage.pressLoginButton();

		// Checks if error appeared or not. To pass test error shouldnt appear
		Assert.assertFalse(healthPage.checkIfErrorAppeared());
	}

	private void fillFourFieldsWithFourDigits() {
		for (int i = 1; i <= 4; i++) {
			healthPage.fillTheNumberField(i, "1234");
		}
	}

	@Test
	public void testIfOpenPasswReminder() {
		driver.get("https://testinsurance.eps.lt/IF/apdraustiesiems/SelfServiceWeb/Login");

		// Clicks forgot password
		healthPage.pressRemindPasswd();

		// Checks if email field is opened
		Assert.assertTrue(healthPage.isEmailFieldVisible());
	}

	@Test
	public void testIfShowsErrorWhenNotFillAllFields() {
		// Go to web page
		driver.get("https://testinsurance.eps.lt/IF/apdraustiesiems/SelfServiceWeb/Login");

		// Filling the first 4 fields
		fillFourFieldsWithFourDigits();

		// Fill password
		healthPage.writePassw("1012");

		// Press login button
		healthPage.pressLoginButton();

		WebElement error = wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.xpath("//li[contains(text(),'Visi kortelės numerio laukai privalo būti užpildyt')]"))));
		// Check if error is shown. It should show error
		Assert.assertTrue(error.isDisplayed());
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
