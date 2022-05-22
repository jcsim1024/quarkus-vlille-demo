package org.acme.vlille;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import io.quarkus.logging.Log;
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


	public static final String INPUT_VLILLE_STATION_SEARCH_BAR = "input-vlille-station-search-bar";
	private WebDriver driver;
	private Wait<WebDriver> wait;
	private int debugId=0;

	@ConfigProperty(name = "selenium.test-url",defaultValue ="http://selenium:4444/wd/hub")
	String SELENIUM_URL;
	
	@ConfigProperty(name = "quarkus.http.test-url",defaultValue ="http://gvm-mvn-tb:8083/index.html")
	String URL_TEST_APP;
	

	@BeforeEach
	public void setUp() throws Exception {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true);
		chromeOptions.addArguments("--no-sandbox");
		this.driver = new RemoteWebDriver(new URL(SELENIUM_URL), chromeOptions);
		wait = new FluentWait<WebDriver>(driver)//
			.withTimeout(Duration.ofSeconds(30))//
			.pollingEvery(Duration.ofSeconds(3))//
			.ignoring(NoSuchElementException.class);
	}

	@Test
	public void Login() throws Exception {
		debugStep();

		// Can the driver reach the page inside the container
		driver.get(URL_TEST_APP);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		debugStep();

		String str = driver.getPageSource();
		
		Assertions.assertTrue(str.contains("placeholder=\"RUE ROYAL , FLANDRE...\""),
				"Selenium couldn't reach container, try curl");

		// Test AngularJs is downloaded on client selenium chrome.
		fluentFind(wait, By.id(INPUT_VLILLE_STATION_SEARCH_BAR)).click();
		debugStep();
		fluentFind(wait, By.id(INPUT_VLILLE_STATION_SEARCH_BAR)).sendKeys("RUE ROYALE");
		debugStep();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		// Get the row value.
		String nbVelodispo = fluentFind(wait, By.cssSelector(".ng-binding:nth-child(3)")).getText();

		Assertions.assertTrue(NumberUtils.isParsable(nbVelodispo));

	}

	private void debugStep() {
		Log.debug(	"\n E2E step° " + debugId++);
	}

	protected WebElement fluentFind(Wait<WebDriver> wait, By by) {
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}

}
