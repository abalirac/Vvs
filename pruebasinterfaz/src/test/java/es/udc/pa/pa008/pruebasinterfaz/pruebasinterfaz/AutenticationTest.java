package es.udc.pa.pa008.pruebasinterfaz.pruebasinterfaz;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AutenticationTest {

	@Test
	public void autenticateTest(){
    
    // Create a new instance of the Firefox driver
    WebDriver driver = new FirefoxDriver();
    
    // And now use this to visit FicBay
    driver.get("http://localhost:9090/practicapa/datagen");
    
    // Find the link element with matching visible text and click
    WebElement autenticarse = driver.findElement(By.linkText("Autenticarse"));
    autenticarse.click();
    
    // Find element of user name
    WebElement loginName = driver.findElement(By.id("loginName"));
    loginName.sendKeys("user");
    
    // Find element of user password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("user");
    
    // Find button to login and click
    WebElement button = driver.findElement(By.className("btn-primary"));
    button.click();
    
    // Check the firstName of the user
    WebElement firstName = driver.findElement(By.className("dropdown-toggle"));
    assertEquals("username", firstName.getText());

    //Close the browser
    driver.quit();
    
	}
}
