package org.acme.vlille;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * /!\ /!\ /!\ None deterministic test. Require proper configuration and
 * VLilledataset api available. /!\ /!\ /!\
 * 
 * Run with Junit 5
 * 
 * @author jcsimonnet
 *
 */
@QuarkusTest
public class SeleniumWebDriverE2ETest {


	private WebDriver driver;

	@BeforeEach
	public void setUp() throws Exception {
		// chromedriver --whitelisted-ips
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);
		chromeOptions.addArguments("--no-sandbox");
		// TODO set selenium container alias in properties
		this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), chromeOptions);
		if (this.driver == null)
			throw new Exception("driver is null, could reach selenium container");

	}

	@Test
	public void Login() throws Exception {
		int i = 0;
		i = printStep(i);
		// Here because Junit 4 won't recognize before all annotation
		if (this.driver == null)
			throw new Exception("Driver is null, could reach selenium container");

		// retry every 3 seconde for 30 sec
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)//
				.withTimeout(Duration.ofSeconds(30))//
				.pollingEvery(Duration.ofSeconds(3))//
				.ignoring(NoSuchElementException.class);

		i = printStep(i);

		// TODO set container alias as properties
		// Can the driver reach the page inside the container
		driver.get("http://gvm-mvn-tb:8083/index.html");
//        driver.manage().window().setSize(new Dimension(742, 789));
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		i = printStep(i);

		String str = driver.getPageSource();
		// Print source for debugging purpose
//		System.out.println(str);
		// If it fails here the selenium container couldn't reach test env
		Assertions.assertTrue(str.contains("placeholder=\"RUE ROYAL , FLANDRE...\""),
				"Selenium couldn't reach container try curl");

		// Test AngularJs is downloaded on client selenium chrome.
		fluentFind(wait, By.id("exampleInputEmail1")).click();
		i = printStep(i);
		fluentFind(wait, By.id("exampleInputEmail1")).sendKeys("RUE ROYALE");
		i = printStep(i);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		// Get the row value.
		String nbVelodispo = fluentFind(wait, By.cssSelector(".ng-binding:nth-child(3)")).getText();

		Assertions.assertTrue(NumberUtils.isParsable(nbVelodispo));

	}

	private int printStep(int i) {
		// TODO replace with logger
//		System.out.println("\n E2E step° " + i++);
		return i;
	}

	protected WebElement fluentFind(Wait<WebDriver> wait, By by) {
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}

}
