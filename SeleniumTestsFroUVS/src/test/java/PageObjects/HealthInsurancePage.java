package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HealthInsurancePage {

	private WebDriver driver;
	
	// Explicity waits for special elements
	private WebDriverWait wait;	

	// Fields for insurance number
	private By number_field_1 = By.id("CreditCard0104");
	private By number_field_2 = By.id("CreditCard0508");
	private By number_field_3 = By.id("CreditCard0912");
	private By number_field_4 = By.id("CreditCard1316");
	private By number_field_5 = By.id("CreditCard1719");

	// Web Element to write value
	private WebElement number_field;

	// Field to enter the password
	private By password_field = By.id("Password");
	private WebElement enterPassw;

	// Link to press to change password
	private By remind_password_link = By.linkText("Pamiršote slaptažodį?");
	private WebElement pass_reminder_link;

	// Button to login to your account
	private By login_button = By.cssSelector("button[class='submit']");
	private WebElement buttonLogin;

	// Write email in this field so system will know where to send password
	private By email_field = By.id("Email");
	private WebElement emailField;

	public HealthInsurancePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
	}

	public void goToPage(String link) {
		driver.get(link);
	}

	public void fillTheNumberField(int fieldNumber, String number) {
		switch (fieldNumber) {
		case (1):
			number_field = driver.findElement(number_field_1);
			break;
		case (2):
			number_field = driver.findElement(number_field_2);
			break;
		case (3):
			number_field = driver.findElement(number_field_3);
			break;
		case (4):
			number_field = driver.findElement(number_field_4);
			break;
		case (5):
			number_field = driver.findElement(number_field_5);
			break;
		default:
			break;
		}
		// Clearing field before giving value
		number_field.clear();
		// Writing value to given field
		number_field.sendKeys(number);
	}

	public void writePassw(String passw) {
		enterPassw = driver.findElement(password_field);
		enterPassw.sendKeys(passw);
	}

	public void pressLoginButton() {
		buttonLogin = driver.findElement(login_button);
		buttonLogin.submit();
	}

	public void pressRemindPasswd() {
		pass_reminder_link = driver.findElement(remind_password_link);
		pass_reminder_link.click();
	}

	public void writeEmail(String email) {
		emailField = driver.findElement(email_field);
		emailField.sendKeys(email);
	}
	
	public boolean checkIfErrorAppeared(){
		WebElement error;
		try{			
			error = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//li[contains(text(),'Neteisingas slaptažodis arba kortelės numeris.')]"))));		
		}
		catch(Exception e){
			return false;
		}
		
		
		return error.isDisplayed();
	}
	
	public boolean isEmailFieldVisible(){	
		emailField = wait.until(ExpectedConditions.presenceOfElementLocated(email_field));
		return emailField.isDisplayed();
	}

}
