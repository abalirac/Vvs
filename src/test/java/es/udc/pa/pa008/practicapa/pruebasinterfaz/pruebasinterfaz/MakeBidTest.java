package es.udc.pa.pa008.pruebasinterfaz.pruebasinterfaz;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MakeBidTest {

	@Test
	public void makeBidTest(){
    
    // Create a new instance of the Firefox driver
    WebDriver driver = new FirefoxDriver();
    
    // And now use this to visit FicBay
    driver.get("http://localhost:9090/practicapa/datagen");
    
    // Find the link element for view products and click
    WebElement viewProducts = driver.findElement(By.linkText("Ver productos"));
    viewProducts.click();
    
    // Find button to view products and click
    WebElement buttonViewProducts = driver.findElement(By.className("btn-primary"));
    buttonViewProducts.click();
    
    // Find the link element for product details and click
    WebElement product = driver.findElement(By.linkText("Audi A2"));
    product.click();
    
    //WebElement currentValue = driver.findElement(By.id("ajaxAuctionValue"));
    //assertEquals(30, currentValue);
    
    // Find button for make bid and click
    WebElement makeBid = driver.findElement(By.linkText("Pujar por el producto"));
    makeBid.click();
    
    // Find element of user name
    WebElement loginName = driver.findElement(By.id("loginName"));
    loginName.sendKeys("user");
    
    // Find element of user password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("user");
    
    // Find button to login and click
    WebElement buttonLogin = driver.findElement(By.className("btn-primary"));
    buttonLogin.click();
    
    // Find element of bid value
    WebElement bidValue = driver.findElement(By.id("bidValue"));
    //double incremento = Double.valueOf(currentValue.getAttribute()) + 1;
    //bidValue.sendKeys("60");
    
    // Find button to make bid and click
    WebElement buttonMakeBid = driver.findElement(By.className("btn-primary"));
    buttonMakeBid.click();
    
    WebElement buttonOptions = driver.findElement(By.className("dropdown-toggle"));
    buttonOptions.click();
    
    // Find history user bids
    //List<WebElement> menu = driver.findElements(By.className("dropdown-menu"));
    //menu.get(3).click();
    
    //List<WebElement> menuBids = driver.findElements(By.linkText("Audi A2"));
    //menu.get(0).click();
    
    //assertEquals(currentPrice, precioActual);
    //assertEquals("user", ganadorActual);
    
    //Close the browser
    driver.quit();
    
	}
}
