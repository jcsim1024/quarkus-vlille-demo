package org.acme.vlille.WebServices;


import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.quarkus.test.junit.QuarkusTest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@QuarkusTest
public class LoginTestE2E {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() throws MalformedURLException {
    
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setHeadless(true);
    chromeOptions.addArguments("--no-sa,dbox");
    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();

  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void Login() {
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
              .pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);
      int i=0;
      System.out.println("\n E2E step° " + i++ );
      driver.get("http://172.17.0.1:8081/");
      System.out.println("\n E2E step° " + i++ );
      String str = driver.getPageSource();
      System.out.println(str);

      // chromedriver --whitelisted-ips
//      driver.manage().window().setSize(new Dimension(742, 789));
      fluentFind(wait, By.id("exampleInputEmail1")).click();
      System.out.println("\n E2E step° " + i++ );
      fluentFind(wait, By.id("exampleInputEmail1")).sendKeys("RUE ROYALE");
      System.out.println("\n E2E step° " + i++ );
      driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
      System.out.println("Result= "+fluentFind(wait, By.cssSelector(".ng-binding:nth-child(3)")).getText());
      System.out.println("\n E2E step° " + i++ );


}

  protected WebElement fluentFind(Wait<WebDriver> wait, By by) {
      return wait.until(new Function<WebDriver, WebElement>() {
          public WebElement apply(WebDriver driver) {
              return driver.findElement(by);
          }
      });
  }
}
