package itqs;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SpecificKeywords {

	// SpecificKeywords.logger=Logger.getLogger("SpecificKeywords");
	static String className = "SpecificKeywords";
	public static String ConcateMeeting;

	/**
	 * Open application of entered URL in Browser
	 * 
	 * @param driver
	 * @param URL
	 * @return int
	 */
	public static int openApplication(WebDriver driver, String URL) {
		int local_Result;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(URL);
		LoggerClass.WriteToLog(className, "OpenApplication : Application Launched");
		local_Result = 1;
		return local_Result;
	}

	/**
	 * Exit the browser
	 * 
	 * @param driver
	 * @param URL
	 * @return int
	 */
	public static int exitBrowser(WebDriver driver) {

		int local_Result;
		driver.close();
		driver.quit();
		local_Result = 1;
		return local_Result;
	}

	/**
	 * Enter Username and Password in Edit Box
	 * 
	 * @param driver
	 * @param locator
	 * @param keyValue
	 * @return int
	 */
	public static int setEditBoxUsernameAndPassword(WebDriver driver, By locator, String keyValue) {

		int localResult = 0;
		String username = System.getProperty("user.name");
		String userCredentialLocation = "C:\\Users\\" + username + "\\Documents\\UserCredentials.txt";
		String userDetail = SpecificKeywords.getDetails(keyValue, userCredentialLocation);
		localResult = SpecificKeywords.setEditBox(driver, locator, userDetail);

		if (localResult == 1) {
		} else {
			localResult = 0;
		}

		return localResult;
	}

	/**
	 * Get value for key from source file location
	 * 
	 * @param findKey
	 * @param location
	 * @return String
	 */
	public static String getDetails(String findKey, String location) {
		String keyValue;
		keyValue = "";

		try {
			File configSourceFile = new File(location);
			FileInputStream fis = new FileInputStream(configSourceFile);
			Properties propertyDetails = new Properties();
			propertyDetails.load(fis);
			keyValue = propertyDetails.getProperty(findKey);
		} catch (Exception e) {
		}

		return keyValue;
	}

	/**
	 * Enter text value in edit box and press Enter KEY
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int setEditBoxWithSendKeys(WebDriver driver, By locator, String userEnteredValue) {
		int functionReturnResult;
		functionReturnResult = -1;
		WebElement element;
		if (GenericKeywords.getWebElementCount(driver, locator) > 0) {
			element = driver.findElement(locator);
			element.click();
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
			element.sendKeys(userEnteredValue);
			element.sendKeys(Keys.ENTER);
			functionReturnResult = 1;
			LoggerClass.WriteToLog(className, "setEditBox : Data entered");
		} else {
			System.out.println("Inside else of SETEDITBOX");
			functionReturnResult = 0;
			LoggerClass.WriteToLog(className, "setEditBox : Data does not entered");
		}
		return functionReturnResult;
	}

	/**
	 * Click on user entered document IVA
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int clickOnDocumentIVA(WebDriver driver, String userEnteredValue) throws InterruptedException {
		int Local_Result;

		/*
		 * By locator = By .xpath("//div[@name='" + userEnteredValue +
		 * "']//a[@class='docLink doc_link_large vv_doc_title_link']");
		 */
		By locator = By.xpath("//div[@docnumber='" + userEnteredValue
				+ "']//span[@class='docName vv_doc_title_name vv_keep_whitespace']");
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		if (executionStatus == 1) {
			if (SpecificKeywords.isElementExist(driver, locator)) {
				driver.findElement(locator).click();
				Local_Result = 1;
			} else {
				Local_Result = 0;
			}
		} else {
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Click on user entered Slide
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int clickOnSlide(WebDriver driver, String userEnteredValue) throws InterruptedException {

		int Local_Result;
		/*
		 * By locator = By.xpath(
		 * "//div[@class='docInfo vv_doc_detail_content vv_col']//span[@class='docName vv_doc_title_name' and text()='"
		 * + userEnteredValue + "']");
		 */
		By locator = By.xpath("//span[text()='" + userEnteredValue
				+ "']/ancestor::div[@class='docInfo vv_doc_detail_content vv_col']//a[@class='docNameLink vv_doc_title_link veevaTooltipBound']");

		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		if (executionStatus == 1) {
			if (SpecificKeywords.isElementExist(driver, locator)) {
				driver.findElement(locator).click();
				Local_Result = 1;
			} else {
				Local_Result = 0;
			}
		} else {
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Enter user entered date in edit box
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int setEditBoxForDate(WebDriver driver, By locator, String userEnteredValue) {

		int functionReturnResult;
		functionReturnResult = -1;
		WebElement element;
		if (GenericKeywords.getWebElementCount(driver, locator) > 0) {
			element = driver.findElement(locator);
			element.click();
			element.clear();
			System.out.println("Before: " + userEnteredValue);
			userEnteredValue = userEnteredValue.replaceAll("-", " ");
			System.out.println("After: " + userEnteredValue);
			element.sendKeys(userEnteredValue);
			element.sendKeys(Keys.ENTER);
			functionReturnResult = 1;
			LoggerClass.WriteToLog(className, "setEditBox : Data entered");
		} else {
			functionReturnResult = 0;
			LoggerClass.WriteToLog(className, "setEditBox : Data does not entered");
		}
		return functionReturnResult;
	}

	/**
	 * Delete existing selection in drop down
	 * 
	 * @param driver
	 * @param parentElement
	 * @param childElement
	 * @return int
	 */
	public static int deleteExistingSelection(WebDriver driver, String parentElement, String childElement)
			throws Exception {

		int functionReturnResult;
		functionReturnResult = -1;
		/*
		 * String locatorMain = locator.toString().toString(); String[] splittedLocator
		 * = locatorMain.split("//"); String firstTag = splittedLocator[1]; String
		 * secondTag = splittedLocator[2]; String thirdTag = splittedLocator[3];
		 */

		WebElement elem = driver.findElement(GenericKeywords.getbjectLocator(GenericKeywords.getOR(parentElement)));
		if (elem.isDisplayed()) {
			elem.click();
			List<WebElement> element = driver
					.findElements(GenericKeywords.getbjectLocator(GenericKeywords.getOR(childElement)));

			if (element.size() > 0) {
				for (int i = 0; i < element.size(); i++) {
					elem.sendKeys(Keys.BACK_SPACE);
					elem.sendKeys(Keys.BACK_SPACE);
				}
				functionReturnResult = 1;
			} else {
				functionReturnResult = 1;
			}
		} else {
			functionReturnResult = 0;
		}
		return functionReturnResult;
	}

	/**
	 * Select user entered checkbox
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int selectCheckBox(WebDriver driver, String userEnteredValue) throws InterruptedException {

		int functionReturnResult = -1;
		String object = "";

		object = "//div[text()='" + userEnteredValue
				+ "']/parent::td/following-sibling::td/div[text()='CLM']/parent::td/parent::tr//td/div//input[@type='checkbox']";

		By locator = By.xpath(object);
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);

		if (executionStatus == 1) {
			WebElement element = driver.findElement(locator);
			element.click();
			functionReturnResult = 1;
		} else {
			functionReturnResult = 0;
		}

		return functionReturnResult;
	}

	/**
	 * Check publish status with user entered value
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int checkPublishStatus(WebDriver driver, By locator, String userEnteredValue)
			throws InterruptedException {

		int functionReturnResult = -1;

		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);

		if (executionStatus == 1) {
			List<WebElement> element = driver.findElements(locator);
			if (element.size() > 0) {
				for (int i = 0; i < element.size(); i++) {
					String status = element.get(i).getText();
					System.out.println("Status:" + status + " :userEntered:" + userEnteredValue);
					if (status.equalsIgnoreCase(userEnteredValue)) {
						functionReturnResult = 1;
					} else {
						functionReturnResult = 0;
						break;
					}
				}
			} else {
				functionReturnResult = 0;
			}
		} else {
			functionReturnResult = 0;
		}

		return functionReturnResult;
	}

	/**
	 * Select user entered data from drop down
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int selectDataFromDropDown(WebDriver driver, By locator, String userEnteredValue)
			throws InterruptedException {

		int functionReturnResult;
		functionReturnResult = -1;
		WebElement dropDownIcon = driver.findElement(locator);

		if (dropDownIcon.isDisplayed()) {
			dropDownIcon.click();
			Thread.sleep(5000);
			String str = "//a[text()='" + userEnteredValue + "']";
			WebElement dropDownElement = driver.findElement(By.xpath(str));
			if (dropDownElement.isDisplayed()) {
				dropDownElement.click();
				functionReturnResult = 1;
			} else {
				functionReturnResult = 0;
			}
		} else {
			functionReturnResult = 0;
		}

		return functionReturnResult;
	}

	/**
	 * Scroll on page
	 * 
	 * @param driver
	 * @param locator
	 * @throws InterruptedException
	 */
	public static void scrollOnPage(WebDriver driver, By locator) throws InterruptedException {

		SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);

	}

	/**
	 * Click on user entered View IVA
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 * @throws InterruptedException
	 */
	public static int clickOnViewIVA(WebDriver driver, String userEnteredValue) throws InterruptedException

	{
		int Local_Result;
		/*
		 * By locator = By
		 * .xpath("//div[@class='x-grid3-cell-inner x-grid3-col-NAME']//span[text()='" +
		 * userEnteredValue + "']");
		 */

		By locator = By.xpath("//div[text()='" + userEnteredValue
				+ "']/ancestor::table[@class='x-grid3-row-table']//div[@class='x-grid3-cell-inner x-grid3-col-NAME']");
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		if (executionStatus == 1) {
			if (SpecificKeywords.isElementExist(driver, locator)) {
				driver.findElement(locator).click();
				Local_Result = 1;
			} else {
				Local_Result = 0;
			}
		} else {
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Click on user entered CRM slide
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 * @throws InterruptedException
	 */
	public static int clickOnCRMSlide(WebDriver driver, String userEnteredValue) throws InterruptedException {
		int Local_Result;
		/*
		 * By locator = By.xpath("//td[@class=' dataCell  ']//a[text()='" +
		 * userEnteredValue + "']");
		 */
		By locator = By
				.xpath("//a[text()='" + userEnteredValue + "']/parent::*/parent::*//td[@class=' dataCell  ']//a");

		scrollOnPage(driver, locator);
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		if (executionStatus == 1) {
			if (SpecificKeywords.isElementExist(driver, locator)) {
				driver.findElement(locator).click();
				Local_Result = 1;
			} else {
				Local_Result = 0;
			}
		} else {
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Select option value from drop down
	 * 
	 * @param driver
	 * @param locator
	 * @param optionValue
	 * @return int
	 */
	public static int selectDropdownOption(WebDriver driver, By locator, String optionValue) {
		int local_Result;
		int Index_value;
		Boolean optionFoundFlag;
		Select dropDown;
		List<WebElement> optionList;
		dropDown = null;
		local_Result = 1;
		WebElement element;
		element = driver.findElement(locator);
		optionFoundFlag = false;
		Index_value = 0;

		if (element.isDisplayed()) {
			dropDown = new Select(element);

			optionList = dropDown.getOptions();
			for (WebElement el : optionList) {
				if (el.getText().toUpperCase().equals(optionValue.toUpperCase())) {
					if (el.isSelected()) {
						driver.findElement(By.xpath("//input[@class='btn' and @name='go']")).click();
						optionFoundFlag = true;
					} else {
						dropDown.selectByIndex(Index_value);
						optionFoundFlag = true;
					}
					break;
				}
				++Index_value;
			}
		}

		if (optionFoundFlag) {
			local_Result = 1;
		} else {
			local_Result = 0;
		}

		return local_Result;
	}

	/**
	 * Check status with status value
	 * 
	 * @param driver
	 * @param statusValue
	 * @param locator
	 * @return int
	 */
	public static int statusCheck(WebDriver driver, String statusValue, By locator) {

		String planstatus = driver.findElement(locator).getText();
		System.out.println("Status of Actionplan is  ::" + planstatus + "::" + statusValue);
		if (planstatus.equalsIgnoreCase(statusValue)) {
			return 1;
		}
		return 0;
	}

	/**
	 * Switch to html iframe
	 * 
	 * @param driver
	 * @param htmlIframe
	 * @return int
	 */
	public static int switchToIframe(WebDriver driver, String htmlIframe) {

		if (htmlIframe.equalsIgnoreCase("Default")) {
			Driver.driver = driver.switchTo().defaultContent();
		} else {
			Driver.driver = driver.switchTo().frame(htmlIframe);
		}
		return 1;
	}

	/**
	 * Click button using Java Executor
	 * 
	 * @param driver
	 * @param locator
	 * @return int
	 */
	public static int clickButtonJavaExecutor(WebDriver driver, By locator) {

		int Local_Result;
		WebElement element = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		System.out.println("Element is clicked");
		Local_Result = 1;

		return Local_Result;
	}

	/**
	 * Wait till element to be visible
	 * 
	 * @param driver
	 * @param timeToWait
	 * @param locator
	 * @return int
	 */
	public static int wait_For_Element_To_Be_Visible(WebDriver driver, int timeToWait, By locator) {

		System.out.println("Enter into visible keyword" + locator);
		int local_result;
		WebDriverWait wait = new WebDriverWait(driver, timeToWait);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			local_result = 1;
		} catch (Exception e) {
			local_result = 0;
		}
		return local_result;
	}

	/**
	 * Element visibility
	 * 
	 * @param driver
	 * @param locator
	 * @return Boolean
	 * @throws InterruptedException
	 */
	public static Boolean isElementExist(WebDriver driver, By locator) throws InterruptedException {

		Thread.sleep(5000);
		// System.out.println("Check Element exist: " +
		// driver.findElement(locator).isDisplayed());
		return driver.findElement(locator).isDisplayed();

	}

	/**
	 * Click on entered locator
	 * 
	 * @param driver
	 * @param locator
	 * @return int
	 */
	public static int clickButton(WebDriver driver, By locator) {
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		int Local_Result;
		if (executionStatus == 1) {
			driver.findElement(locator).click();
			Local_Result = 1;
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
		} else {
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Enter user entered value into edit box
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int setEditBox(WebDriver driver, By locator, String userEnteredValue) {

		int functionReturnResult;
		functionReturnResult = -1;
		WebElement element;
		if (GenericKeywords.getWebElementCount(driver, locator) > 0) {
			element = driver.findElement(locator);
			element.click();
			element.clear();
			element.sendKeys(userEnteredValue);
			functionReturnResult = 1;
			LoggerClass.WriteToLog(className, "setEditBox : Data entered");
		} else {
			functionReturnResult = 0;
			LoggerClass.WriteToLog(className, "setEditBox : Data does not entered");
		}
		return functionReturnResult;
	}

	/**
	 * Click on user entered checkbox
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int clickOnCheckBox(WebDriver driver, String userEnteredValue) throws InterruptedException

	{
		// input[@id='crmHidden_bNO']
		By locator = null;

		if (userEnteredValue.equalsIgnoreCase("Hidden|Yes")) {
			locator = By.xpath("//input[@id='crmHidden_bYES']");
		} else if (userEnteredValue.equalsIgnoreCase("Hidden|No")) {
			locator = By.xpath("//input[@id='crmHidden_bNO']");
		} else if (userEnteredValue.equalsIgnoreCase("Training|Yes")) {
			locator = By.xpath("//input[@id='crmTraining_bYES']");
		} else if (userEnteredValue.equalsIgnoreCase("Training|No")) {
			locator = By.xpath("//input[@id='crmTraining_bNO']");
		}

		scrollOnPage(driver, locator);
		int executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(driver, 2000, locator);
		int Local_Result;
		if (executionStatus == 1) {
			if (!driver.findElement(locator).isSelected()) {
				driver.findElement(locator).click();
			}
			Local_Result = 1;
			LoggerClass.WriteToLog(className, "clickButton : Button have been clicked");
		} else {
			Local_Result = 0;
		}
		return Local_Result;
	}

	/**
	 * Select user entered value from drop down
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 * @throws InterruptedException
	 */
	public static int selectCustomTypeDropDown(WebDriver driver, By locator, String userEnteredValue)
			throws InterruptedException {

		int functionReturnResult;
		functionReturnResult = -1;
		WebElement dropDownIcon = driver.findElement(locator);
		if (dropDownIcon.isDisplayed()) {
			dropDownIcon.click();
			dropDownIcon.sendKeys(Keys.BACK_SPACE);
			dropDownIcon.sendKeys(Keys.BACK_SPACE);
			Thread.sleep(5000);
			String str = "//a[text()='" + userEnteredValue + "']";
			WebElement dropDownElement = driver.findElement(By.xpath(str));
			if (dropDownElement.isDisplayed()) {
				dropDownElement.click();
				functionReturnResult = 1;
			} else {
				functionReturnResult = 0;
			}
		} else {
			functionReturnResult = 0;
		}
		return functionReturnResult;
	}

	/**
	 * Verify check box for Full2
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int verifyCheckBoxFull2(WebDriver driver, String userEnteredValue) {

		int functionResturnResult;
		String[] locatorValue = userEnteredValue.split("\\|");
		String firstSpiltLocatorVal = locatorValue[0];
		String secondSplitLocatorVal = locatorValue[1];

		if (firstSpiltLocatorVal.equalsIgnoreCase("Hidden")) {
			By locator = By.xpath("//div[@id='00NG0000005ys10_ileinner']//img");
			WebElement element = driver.findElement(locator);
			String elemValue = element.getAttribute("title");
			if (secondSplitLocatorVal.equalsIgnoreCase(elemValue)) {
				functionResturnResult = 1;
			} else {
				functionResturnResult = 0;
			}
		} else if (firstSpiltLocatorVal.equalsIgnoreCase("Training")) {
			By locator = By.xpath("//div[@id='00NG0000008KkE6_ileinner']//img");
			WebElement element = driver.findElement(locator);
			String elemValue = element.getAttribute("title");
			if (secondSplitLocatorVal.equalsIgnoreCase(elemValue)) {
				functionResturnResult = 1;
			} else {
				functionResturnResult = 0;
			}
		} else {
			functionResturnResult = 0;
		}
		return functionResturnResult;
	}

	/**
	 * Verify check box for CFG10QA and Full4
	 * 
	 * @param driver
	 * @param userEnteredValue
	 * @return int
	 */
	public static int verifyCheckBoxCFGFull4(WebDriver driver, String userEnteredValue) {

		int functionResturnResult;

		String[] locatorValue = userEnteredValue.split("\\|");
		String firstSpiltLocatorVal = locatorValue[0];
		String secondSplitLocatorVal = locatorValue[1];
		if (firstSpiltLocatorVal.equalsIgnoreCase("Hidden")) {
			By locator = By.xpath("//div[@id='00NU0000002HbQ7_ileinner']//img");
			WebElement element = driver.findElement(locator);
			String elemValue = element.getAttribute("title");

			if (secondSplitLocatorVal.equalsIgnoreCase(elemValue)) {
				functionResturnResult = 1;
			} else {
				functionResturnResult = 0;
			}
		} else if (firstSpiltLocatorVal.equalsIgnoreCase("Training")) {
			By locator = By.xpath("//div[@id='00NU00000015KCy_ileinner']//img");
			WebElement element = driver.findElement(locator);
			String elemValue = element.getAttribute("title");
			if (secondSplitLocatorVal.equalsIgnoreCase(elemValue)) {
				functionResturnResult = 1;
			} else {
				functionResturnResult = 0;
			}
		} else {
			functionResturnResult = 0;
		}
		return functionResturnResult;
	}

	/**
	 * Verify sharing groups
	 * 
	 * @param driver
	 * @param locator
	 * @param userEnteredValue
	 * @return int
	 */
	public static int verifySharingGroups(WebDriver driver, By locator, String userEnteredValue) {

		int local_Result;
		Boolean optionFoundFlag = false;
		local_Result = 1;

		List<WebElement> elementList = driver.findElements(locator);

		for (WebElement element : elementList) {
			System.out.println("Element Value: " + element.getText().toUpperCase());
			if (element.getText().toUpperCase().equals(userEnteredValue.toUpperCase())) {
				optionFoundFlag = true;
				break;
			}
		}

		if (optionFoundFlag) {
			local_Result = 1;
		} else {
			local_Result = 0;
		}

		return local_Result;
	}
}
