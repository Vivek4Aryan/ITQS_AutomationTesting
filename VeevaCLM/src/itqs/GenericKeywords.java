package itqs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.sql.Connection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GenericKeywords {

	static String configFileLocation;
	static String ObjectRepositoryFileLocation;
	// static Logger logger;
	static String className = "GenericKeywords";

	/**
	 * Intialize the resource properties file
	 */
	public static void Initialize() {
		configFileLocation = "./Resources/configuration.property";
		ObjectRepositoryFileLocation = "./Resources/ObjectRepository.property";
		// GenericKeywords.logger=Logger.getLogger("GenericKeywords");
		// PropertyConfigurator.configure("./Resources/log4j.properties");
	}

	/**
	 * Launch the Browser (i.e Chrome, IE, Firefox)
	 * 
	 * @param BrowserType
	 * @return WebDriver
	 */
	@SuppressWarnings("deprecation")
	public static WebDriver launchBrowser(String BrowserType) throws IOException {
		WebDriver driver;
		driver = null;
		if (BrowserType.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", GenericKeywords.getConfigDetails("fireFoxDriverPath"));
			driver = new FirefoxDriver();
		}
		if (BrowserType.equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver", ".\\libs\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", GenericKeywords.getConfigDetails("ieDriverPath"));
			// DesiredCapabilitiesCap = DesiredCapabilities.internetExplorer();
			driver = new InternetExplorerDriver();
		}
		if (BrowserType.equalsIgnoreCase("chrome")) {
			// System.setProperty("webdriver.chrome.driver", ".\\libs\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", GenericKeywords.getConfigDetails("chromeDriverPath"));
			driver = new ChromeDriver();
			driver.get("https://www.google.com/");
			// GenericKeywords.logger.info("LaunchBrowser : Chrome Browser launched");
			LoggerClass.WriteToLog(className, "LaunchBrowser : Chrome Browser launched");
		}
		if (BrowserType.equalsIgnoreCase("chrome_sl")) {
			String username = System.getProperty("user.name");
			String userCredentialLocation = "C:\\Users\\" + username + "\\Documents\\UserCredentials.txt";
			String accessKey = SpecificKeywords.getDetails("SauceLabAccessKey", userCredentialLocation);
			String sauceLabUserName = SpecificKeywords.getDetails("SauceLabUserName", userCredentialLocation);
			String sauceLabTestCaseName = new Driver().testCaseName;
			// System.out.println("SauceLabUserName is " + sauceLabUserName);
			// String sauceLabTestCaseName =
			// GenericKeywords.getConfigDetails("SauceLabTestCaseName");
			// System.out.println("SauceLabTestCaseName is " + sauceLabTestCaseName);
			// String sauceLabTagName = GenericKeywords.getConfigDetails("SauceLabTagName");
			// System.out.println("SauceLabTagName is " + sauceLabTagName);
			System.setProperty("http.proxyHost", "proxy.gtm.lilly.com");
			System.setProperty("http.proxyPort", "9000");
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("platform", "Windows 7");
			caps.setCapability("browser", "Chrome");
			caps.setCapability("version", "90.0");
			caps.setCapability("name", sauceLabTestCaseName);
			// caps.setCapability("tags", sauceLabTagName);
			// caps.setCapability("passed", true);
			// driver=new RemoteWebDriver(new URL("http://" + "sso-lilly-C280518" + ":" +
			// "0d20dd10-d923-4c69-8074-20b9de6c5b3e" +
			// "@ondemand.saucelabs.com:80/wd/hub"), caps);
			driver = new RemoteWebDriver(
					new URL("http://" + sauceLabUserName + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub"),
					caps);
		}
		return driver;
	}

	/**
	 * Get configuration details for Key frome configuration file
	 * 
	 * @param findKey
	 * @return String
	 */
	public static String getConfigDetails(String findKey) {
		String keyValue;
		keyValue = "";
		configFileLocation = "./Resources/configuration.property";
		try {
			File configSourceFile = new File(configFileLocation);
			FileInputStream fis = new FileInputStream(configSourceFile);
			Properties propertyDetails = new Properties();
			propertyDetails.load(fis);
			keyValue = propertyDetails.getProperty(findKey);
			LoggerClass.WriteToLog(className, "getConfigDetails : Configuration Details Fetched");
		} catch (Exception e) {
			LoggerClass.WriteToLog(className, "getConfigDetails : Configuration Details does not Fetched");
		}
		return keyValue;
	}

	/**
	 * To get Object Repository from Object repository file
	 * 
	 * @param ORKey
	 * @return String
	 */
	public static String getOR(String ORKey) {

		String ORValue;
		ORValue = "";
		try {
			File configSourceFile = new File(ObjectRepositoryFileLocation);
			FileInputStream fis = new FileInputStream(configSourceFile);
			Properties propertyDetails = new Properties();
			propertyDetails.load(fis);
			ORValue = propertyDetails.getProperty(ORKey);
			LoggerClass.WriteToLog(className, "getOR : OR Details Fetched");
		} catch (Exception e) {
			LoggerClass.WriteToLog(className, "getOR : OR Details does not Fetched");
		}
		return ORValue;
	}

	/**
	 * To get Object locator
	 * 
	 * @param locatorProperty
	 * @return By
	 * @throws Exception
	 */
	public static By getbjectLocator(String locatorProperty) throws Exception {

		String locatorType = "";
		String locatorValue = "";
		String ele[] = locatorProperty.split(":");
		int length = ele.length;
		if (length == 2) {
			locatorType = ele[0];
			locatorValue = ele[1];
		} else if (length > 2) {
			locatorType = ele[0];
			for (int i = 1; i < length; i++) {
				if (i == 1) {
					locatorValue = locatorValue + ele[i];
				} else {
					locatorValue = locatorValue + ":" + ele[i];
				}
			}
		}

		By locator = null;
		System.out.println(locatorType);
		System.out.println(locatorValue);
		if (locatorType.equalsIgnoreCase("Id"))
			locator = By.id(locatorValue);
		else if (locatorType.equalsIgnoreCase("Name"))
			locator = By.name(locatorValue);
		else if (locatorType.equalsIgnoreCase("CssSelector"))
			locator = By.cssSelector(locatorValue);
		else if (locatorType.equalsIgnoreCase("LinkText"))
			locator = By.linkText(locatorValue);
		else if (locatorType.equalsIgnoreCase("PartialLinkText"))
			locator = By.partialLinkText(locatorValue);
		else if (locatorType.equalsIgnoreCase("TagName"))
			locator = By.tagName(locatorValue);
		else if (locatorType.equalsIgnoreCase("Xpath"))
			locator = By.xpath(locatorValue);
		LoggerClass.WriteToLog(className, "getbjectLocator : Object Locator Identified");
		return locator;
	}

	/*
	 * public static By getbjectLocator(String locatorProperty) throws Exception {
	 * // String locatorProperty = propertyFile.getProperty(locatorName); //
	 * System.out.println(locatorProperty.toString()); String locatorType =
	 * locatorProperty.split(":")[0]; // System.out.println("LocatorType : "
	 * +locatorType ); String locatorValue = locatorProperty.split(":")[1]; //
	 * System.out.println("LocatorValue : " +locatorValue ); By locator = null;
	 * 
	 * if (locatorType.equalsIgnoreCase("Id")) locator = By.id(locatorValue); else
	 * if (locatorType.equalsIgnoreCase("Name")) locator = By.name(locatorValue);
	 * else if (locatorType.equalsIgnoreCase("CssSelector")) locator =
	 * By.cssSelector(locatorValue); else if
	 * (locatorType.equalsIgnoreCase("LinkText")) locator =
	 * By.linkText(locatorValue); else if
	 * (locatorType.equalsIgnoreCase("PartialLinkText")) locator =
	 * By.partialLinkText(locatorValue); else if
	 * (locatorType.equalsIgnoreCase("TagName")) locator = By.tagName(locatorValue);
	 * else if (locatorType.equalsIgnoreCase("Xpath")) locator =
	 * By.xpath(locatorValue); //
	 * GenericKeywords.logger.info("getbjectLocator : Object Locator Identified");
	 * LoggerClass.WriteToLog(className,
	 * "getbjectLocator : Object Locator Identified"); return locator; }
	 */

	/**
	 * To get Web Element Count
	 * 
	 * @param driver
	 * @param locator
	 * @return int
	 */
	public static int getWebElementCount(WebDriver driver, By locator) {

		List<WebElement> WebElementList = driver.findElements(locator);
		// GenericKeywords.logger.info("getWebElementCount : Get webelement count");
		LoggerClass.WriteToLog(className, "getWebElementCount : Get webelement count");
		return WebElementList.size();
	}

	/**
	 * To get SQL Record Count
	 * 
	 * @param excelFileLocation
	 * @param SQLQuery
	 * @return int
	 */
	public static int getSQLRecordCount(String excelFileLocation, String SQLQuery) {

		int localRecordCount;
		localRecordCount = 0;
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(excelFileLocation);
			Recordset recordset = connection.executeQuery(SQLQuery);
			localRecordCount = recordset.getCount();
			recordset.close();
			connection.close();
			LoggerClass.WriteToLog(className, "getSQLRecordCount : get Record counts ");
		} catch (Exception e) {
			localRecordCount = -1;
			System.out.println(e.getMessage());
			LoggerClass.WriteToLog(className, "getSQLRecordCount : Record counts in negative ");
		}
		return localRecordCount;
	}

	/**
	 * To get SQL Record
	 * 
	 * @param excelFileLocation
	 * @param SQLQuery
	 * @param recordCountNumber
	 * @param fieldName
	 * @return int
	 */
	public static String getSQLRecord(String excelFileLocation, String SQLQuery, int recordCountNumber,
			String fieldName) {

		String RecordValue;
		RecordValue = "";
		int localRecordCount;
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(excelFileLocation);
			Recordset recordset = connection.executeQuery(SQLQuery);
			localRecordCount = recordset.getCount();
			if (localRecordCount > 0) {
				if (localRecordCount >= recordCountNumber) {
					for (int i = 1; i <= recordCountNumber; i++) {
						recordset.next();
					}
					LoggerClass.WriteToLog(className, "getSQLRecord : get SQL Record details ");
					RecordValue = recordset.getField(fieldName);
				} else {
					LoggerClass.WriteToLog(className, "getSQLRecord :  SQL Record details in negative ");
					RecordValue = "-1";
				}
			} else {
				LoggerClass.WriteToLog(className, "getSQLRecord :  SQL Record details is zero ");
				RecordValue = "" + 0;
			}
			recordset.close();
			connection.close();
		} catch (Exception e) {
			RecordValue = "-1";
			// System.out.println(e.getMessage());
		}
		return RecordValue;
	}

	/**
	 * Execute Keyword
	 * 
	 * @param dobj
	 * @return int
	 */
	public static int executeKeyWord(Driver dobj) {

		int keywordExecutionStatus;
		keywordExecutionStatus = 1;
		LoggerClass.WriteToLog(className, "executeKeyWord :  Keyword execution Start ");
		keywordExecutionStatus = KeywordMapping.keywordActionMapping(dobj);
		return keywordExecutionStatus;
	}

	/**
	 * Take ScreenShot of driver window and copy at location
	 * 
	 * @param driver
	 * @param FileName
	 */
	public static void takeScreenShot(WebDriver driver, String FileName) {

		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(FileName));
		} catch (Exception e) {
			System.out.println("screenprint error :" + e.getMessage());
		}
	}

	/**
	 * Create word document
	 * 
	 * @param FileName
	 * @return XWPFDocument
	 */
	public static XWPFDocument createWordDocument(String FileName) {

		XWPFDocument document = null;
		File fileObj = new File(FileName);
		if (!fileObj.exists()) {
			document = new XWPFDocument();
			return document;
		} else {
			try {
				FileInputStream fis = new FileInputStream(FileName);
				document = new XWPFDocument(OPCPackage.open(fis));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return document;
		}
	}

	/**
	 * Write to word document
	 * 
	 * @param document
	 * @param FileName
	 */
	public static void writeToWordDocument(XWPFDocument document, String FileName) {

		try {
			FileOutputStream out = new FileOutputStream(new File(FileName));
			document.write(out);
			out.close();
		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	/**
	 * Write text to word document
	 * 
	 * @param document
	 * @param TextToAdd
	 * @param alignment
	 */
	public static void writeTextToWordDocument(XWPFDocument document, String TextToAdd, int alignment) {

		XWPFParagraph subTitle = document.createParagraph();
		if (alignment == 0) {
			subTitle.setAlignment(ParagraphAlignment.LEFT);
		} else if (alignment == 1) {
			subTitle.setAlignment(ParagraphAlignment.CENTER);
		}
		XWPFRun subTitleRun = subTitle.createRun();
		subTitleRun.setFontSize(16);
		subTitleRun.setBold(true);
		subTitleRun.setUnderline(UnderlinePatterns.SINGLE);
		subTitleRun.setText(TextToAdd.trim());
	}

	/**
	 * Page or Line break to word document
	 * 
	 * @param document
	 * @param breaktype
	 */
	public static void addBreakToWordDocument(XWPFDocument document, String breaktype) {

		XWPFParagraph subTitle = document.createParagraph();
		XWPFRun subTitleRun = subTitle.createRun();
		if (breaktype.equalsIgnoreCase("page")) {
			subTitleRun.addBreak(BreakType.PAGE);
		} else if (breaktype.equalsIgnoreCase("line")) {
			subTitleRun.addBreak(BreakType.TEXT_WRAPPING);
		}
	}

	/**
	 * Add Screenshot image to word document
	 * 
	 * @param document
	 * @param imageFileName
	 */
	public static void addImageToWordDocument(XWPFDocument document, String imageFileName) {

		XWPFParagraph subTitle = document.createParagraph();
		subTitle.setAlignment(ParagraphAlignment.LEFT);
		subTitle.setBorderBottom(Borders.BASIC_THIN_LINES);
		subTitle.setBorderTop(Borders.BASIC_THIN_LINES);
		subTitle.setBorderLeft(Borders.BASIC_THIN_LINES);
		subTitle.setBorderRight(Borders.BASIC_THIN_LINES);
		XWPFRun subTitleRun = subTitle.createRun();
		try {
			FileInputStream pic = new FileInputStream(imageFileName);
			subTitleRun.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, imageFileName, Units.toEMU(450),
					Units.toEMU(300));
			subTitleRun.addBreak(BreakType.PAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Delete File
	 * 
	 * @param FileName
	 */
	public static void deleteFile(String FileName) {

		try {
			File file = new File(FileName);
			if (file.exists()) {
				file.delete();
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create Table to word document
	 * 
	 * @param document
	 * @param dataValue
	 * @param bigIntegerValue
	 * @param alignmentValue
	 * @return XWPFTable
	 */
	public static XWPFTable createTableToWordDocument(XWPFDocument document, String dataValue, long bigIntegerValue,
			String alignmentValue) {

		XWPFTableCell VCell;
		XWPFTable table = document.createTable();
		VCell = table.getRow(0).getCell(0);
		VCell.setVerticalAlignment(XWPFVertAlign.TOP);
		XWPFParagraph paragraph = VCell.addParagraph();
		XWPFRun run = paragraph.createRun();
		if (alignmentValue.equalsIgnoreCase("left")) {
			paragraph.setAlignment(ParagraphAlignment.LEFT);
		} else if (alignmentValue.equalsIgnoreCase("center")) {
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		}
		paragraph.setVerticalAlignment(TextAlignment.TOP);
		paragraph.setWordWrapped(true);
		run.setText(dataValue.trim());
		table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
		table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(bigIntegerValue));
		return table;
	}

	/**
	 * Create Table row to word document
	 * 
	 * @param table
	 * @return XWPFTable
	 */
	public static XWPFTableRow createTableRowToWordDocument(XWPFTable table) {

		XWPFTableRow tableRow = table.createRow();
		return tableRow;
	}

	/**
	 * Add cell and set cell value of table to word document
	 * 
	 * @param table
	 * @param RowNumber
	 * @param DataValue
	 * @param bigIntegerValue
	 * @param alignmentValue
	 */
	public static void addCellAndSetCellValueOfTableToWordDocument(XWPFTable table, int RowNumber, String DataValue,
			long bigIntegerValue, String alignmentValue) {

		XWPFTableCell VCell;
		VCell = table.getRow(RowNumber).createCell();
		VCell.setVerticalAlignment(XWPFVertAlign.TOP);
		XWPFParagraph paragraph = VCell.addParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run = paragraph.createRun();
		run.setText(DataValue.trim());
		if (alignmentValue.equalsIgnoreCase("left")) {
			paragraph.setAlignment(ParagraphAlignment.LEFT);
		} else if (alignmentValue.equalsIgnoreCase("center")) {
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		}
		paragraph.setVerticalAlignment(TextAlignment.TOP);
		paragraph.setWordWrapped(true);
		VCell.getCTTc().addNewTcPr().addNewTcW().setType(STTblWidth.DXA);
		VCell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(bigIntegerValue));
	}

	/**
	 * Set cell value of table to word document
	 * 
	 * @param table
	 * @param CellRowNumber
	 * @param CellColumnNumber
	 * @param DataValue
	 * @param alignmentValue
	 */
	public static void SetCellValueOfTableToWordDocument(XWPFTable table, int CellRowNumber, int CellColumnNumber,
			String DataValue, String alignmentValue) {

		XWPFTableCell VCell;
		VCell = table.getRow(CellRowNumber).getCell(CellColumnNumber);
		VCell.setVerticalAlignment(XWPFVertAlign.TOP);
		XWPFParagraph paragraph = VCell.addParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		paragraph.setWordWrapped(true);
		XWPFRun run = paragraph.createRun();
		run.setText(DataValue.trim());
		if (alignmentValue.equalsIgnoreCase("left")) {
			paragraph.setAlignment(ParagraphAlignment.LEFT);
		} else if (alignmentValue.equalsIgnoreCase("center")) {
			paragraph.setAlignment(ParagraphAlignment.CENTER);
		}
		paragraph.setVerticalAlignment(TextAlignment.TOP);
	}

	/**
	 * Delete table cell text of table to word document
	 * 
	 * @param table
	 * @param CellRowNumber
	 * @param CellColumnNumber
	 * @param DataValue
	 */
	public static void deleteTableCellTextOfTableToWordDocument(XWPFTable table, int CellRowNumber,
			int CellColumnNumber, String DataValue) {

		XWPFTableCell VCell;
		XWPFParagraph currectParagraph;
		List<XWPFParagraph> existingParagraphList;
		XWPFDocument doc;
		int paragraphPosition;
		VCell = table.getRow(CellRowNumber).getCell(CellColumnNumber);
		existingParagraphList = VCell.getParagraphs();
		if (existingParagraphList.size() > 0) {
			for (int pg = 0; pg <= existingParagraphList.size() - 1; pg++) {
				currectParagraph = existingParagraphList.get(pg);
				if (currectParagraph.getText().equalsIgnoreCase(DataValue)) {
					doc = currectParagraph.getDocument();
					paragraphPosition = doc.getPosOfParagraph(currectParagraph);
					doc.removeBodyElement(paragraphPosition);
				}
			}
		}
	}

	/**
	 * Get Word Document table column index
	 * 
	 * @param table
	 * @param CellRowNumber
	 * @param cellDataValue
	 * @return int
	 */
	public static int getWordDocumentTableColumnIndex(XWPFTable table, int CellRowNumber, String cellDataValue) {

		int columnIndex;
		columnIndex = -1;
		List valueDetails;
		XWPFTableCell VCell;
		valueDetails = table.getRow(CellRowNumber).getTableCells();
		for (int ik = 0; ik < valueDetails.size(); ik++) {
		}
		return columnIndex;
	}

	/**
	 * Get Word table row index
	 * 
	 * @param table
	 * @param fieldValue
	 * @return int
	 */
	public static int getWordTableRowIndex(XWPFTable table, String fieldValue) {

		int rowIndex;
		String textValue;
		rowIndex = -1;
		List<XWPFTableCell> cells;
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			cells = table.getRow(i).getTableCells();
			textValue = cells.get(0).getText().toString();
			if (fieldValue.equalsIgnoreCase(table.getRow(i).getCell(0).getText())) {
				break;
			}
		}
		return rowIndex;
	}

	/**
	 * Set row or column color
	 * 
	 * @param table
	 * @param RowColumn
	 * @param coloreCode
	 */
	public static void setRowOrColumnColor(XWPFTable table, String RowColumn, String coloreCode) {

		if (RowColumn.equalsIgnoreCase("row")) {
			for (int i = 0; i < table.getRow(0).getTableCells().size(); i++) {
				table.getRow(0).getCell(i).setColor(coloreCode);
			}
		} else if (RowColumn.equalsIgnoreCase("column")) {
			for (int j = 0; j < table.getNumberOfRows(); j++) {
				table.getRow(j).getCell(0).setColor(coloreCode);
			}
		}
	}

	/**
	 * Create text file
	 * 
	 * @param FilePath
	 * @param FileName
	 * @return int
	 */
	public static int createTXTfile(String FilePath, String FileName) {
		
		int local_Result;
		local_Result = 1;
		String Full_File_Path;
		if (FilePath.lastIndexOf("/") == FilePath.length() - 1) {
			Full_File_Path = FilePath + FileName;
		} else {
			Full_File_Path = FilePath + "/" + FileName;
		}
		File fileObject = new File(Full_File_Path);
		try {
			if (!fileObject.exists()) {
				fileObject.createNewFile();
				local_Result = 1;
			}
		} catch (Exception e) {
			System.out.println("Error at createTXTfile : " + e.getMessage());
			local_Result = 0;
		}
		fileObject = null;
		return local_Result;
	}

	/**
	 * Write to text file
	 * 
	 * @param FilePath
	 * @param FileName
	 * @param valueToWrite
	 * @return int
	 */
	public static void WriteToTXTFile(String FilePath, String FileName, String valueToWrite) {

		String Full_File_Path;
		if (FilePath.lastIndexOf("/") == FilePath.length() - 1) {
			Full_File_Path = FilePath + FileName;
		} else {
			Full_File_Path = FilePath + "/" + FileName;
		}
		File fileObject = new File(Full_File_Path);
		try {
			FileOutputStream fos = new FileOutputStream(fileObject, false);
			Writer writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			writer.write("" + valueToWrite);
			writer.close();
		} catch (Exception e) {
			System.out.println("Error at WriteToTXTFile : " + e.getMessage());
		}
		fileObject = null;
	}

	/**
	 * Delete multiple file with specific word in name from folder
	 * 
	 * @param FilePath
	 * @param WordInFileName
	 */
	public static void deleteMultiple_File_With_Specific_Word_In_Name_FromFolder(String FilePath,
			String WordInFileName) {
		File folder = new File(FilePath);
		File[] files = folder.listFiles();
		for (File singleFile : files) {
			if (singleFile.getName().contains(WordInFileName)) {
				singleFile.delete();
			}
		}
	}
}
