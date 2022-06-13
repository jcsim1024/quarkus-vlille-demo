package org.acme.vlille;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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
 * Run with Junit 5
 * 
 * @author jcsimonnet
 *
 */
@QuarkusTest
@Slf4j
public class SeleniumWebDriverE2ETest {


	public static final String INPUT_VLILLE_STATION_SEARCH_BAR = "input-vlille-station-search-bar";
	public static final String TEST_DATASET_STATION_NAME = "DELESALLE MEDIATHEQUE";
	public static final String CSS_SELECTOR_FIRST_OCC_STATION_NAME = "#stations tr td[data-name='station-name'].ng-binding ";


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
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		wait = new FluentWait<WebDriver>(driver)//
			.withTimeout(Duration.ofSeconds(30))//
			.pollingEvery(Duration.ofSeconds(3))//
			.ignoring(NoSuchElementException.class)
				.withMessage(()->{
					log.error(driver.getPageSource());
					return "wait failed NoSuchElementException";
				});
	}

	@Test
	public void should_filter_station_by_name() {
		debugStep();

		driver.get(URL_TEST_APP);
		
		debugStep();


		Assertions.assertTrue(driver.getPageSource().contains("placeholder=\"RUE ROYAL , FLANDRE...\""),
				"Selenium couldn't reach container, try curl inside it");

		fluentFind(wait, By.id(INPUT_VLILLE_STATION_SEARCH_BAR)).sendKeys(TEST_DATASET_STATION_NAME);
		debugStep();


		String result = fluentFind(wait, By.cssSelector(CSS_SELECTOR_FIRST_OCC_STATION_NAME)).getText();

		Assertions.assertEquals(TEST_DATASET_STATION_NAME, result);

	}

	private void debugStep() {
		log.debug(	"\n E2E step° " + debugId++);
	}

	protected WebElement fluentFind(Wait<WebDriver> wait, By by) {
		return wait.until(driver -> driver.findElement(by));
	}

}
