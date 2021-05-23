package org.acme.vlille.WebServices;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class VlilleWSE2E {
	private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--kiosk");
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1920, 1080));

    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  public void tearDown() {
    driver.quit();
  }
    public void Login() {
        //myselenium
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

        driver.get("http://localhost:8081/");
        driver.manage().window().setSize(new Dimension(742, 789));
        fluentFind(wait, By.id("exampleInputEmail1")).click();
        fluentFind(wait, By.id("exampleInputEmail1")).sendKeys("RUE ROYALE");
        System.out.println(fluentFind(wait, By.cssSelector(".ng-binding:nth-child(3)")).getText());
  }

    protected WebElement fluentFind(Wait<WebDriver> wait, By by) {
        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
    }
}