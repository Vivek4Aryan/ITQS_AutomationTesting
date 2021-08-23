package alm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import alm.JSONParser;
import alm.QCRestConnector;
import alm.Response;

public class AlmRunCreation {

	private static Log log = LogFactory.getLog(AlmRunCreation.class.getName());
	private static AlmRunCreation instance;
	private AlmRestConnector connection;
	private static String testCyclId;
	private static String createdRunEntityUrl;
	private static String screenshotFileLocation;
	private static String wordDocumentFile;
	private static XWPFDocument wordDocument;
	private static XWPFTable overallExecutionTable;
	private static String runStepsXml;
	private static String createdRunStepEntityUrl;

	public static String getTestCyclId() {
		return testCyclId;
	}

	public static String getCreatedRunEntityUrl() {
		return createdRunEntityUrl;
	}

	public static String getScreenshotFileLocation() {
		return screenshotFileLocation;
	}

	public static String getWordDocumentFile() {
		return wordDocumentFile;
	}

	public static XWPFDocument getWordDocument() {
		return wordDocument;
	}

	public static XWPFTable getOverallExecutionTable() {
		return overallExecutionTable;
	}

	public static String getRunStepsXml() {
		return runStepsXml;
	}

	public static String getCreatedRunStepEntityUrl() {
		return createdRunStepEntityUrl;
	}

	private static Map<String, XWPFTable> stepTableNameMap;

	public static Map<String, XWPFTable> getStepTableNameMap() {
		return stepTableNameMap;
	}

	public AlmRunCreation() {

		connection = AlmRestConnector.getInstance();
		connection.init(new HashMap<String, String>(), AlmActions.almHost, AlmActions.almDomain, AlmActions.almProject);
	}

	public static AlmRunCreation getInstance() {
		if (instance == null) {
			instance = new AlmRunCreation();
		}
		return instance;
	}

	/**
	 * Creates new alm test run and returns test run id
	 * 
	 * @param testSetUrl
	 * @param testSetID
	 * @param testCaseID
	 * @return string
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws Exception
	 */
	public String createRun(String testSetUrl, String testSetID, String testCaseID)
			throws IOException, URISyntaxException, Exception {

		AlmConnector alm = new AlmConnector();
		alm.login(AlmActions.almUsername, AlmActions.almPassword);
		QCRestConnector.getInstance().getQCSession();
		AlmEntity crud = new AlmEntity();
		Response testInstanceresponse = crud.readEntity(testSetUrl,
				"query={cycle-id[" + testSetID + "]" + ";test-id[" + testCaseID + "]}");
		String testInstanceXML = testInstanceresponse.toString();
		testCyclId = JSONParser.entitiesJSONParser(testInstanceXML, 0, "id");
		String testRunUrl = connection.buildEntityCollectionUrl("run");
		createdRunEntityUrl = crud.createEntity(testRunUrl,
				AlmActions.createNewEntityForNewRun(testCyclId, testCaseID));
		Response runIdResponse = crud.readEntity(createdRunEntityUrl, null);
		String runIdXml = runIdResponse.toString();
		String dataRunId = JSONParser.fieldJSONParser(runIdXml, "id");
		return dataRunId;
	}

	/**
	 * Returns total number of run steps in created new alm test run
	 * 
	 * @param createdRunEntityUrl
	 * @return int
	 * @throws Exception
	 *//*
		 * public static int getTotalRunStepCount(String createdRunEntityUrl) throws
		 * Exception {
		 * 
		 * AlmEntity crud = new AlmEntity(); createdRunStepEntityUrl =
		 * createdRunEntityUrl + "/run-steps"; Response getResponse =
		 * crud.readEntity(createdRunStepEntityUrl, null); runStepsXml =
		 * getResponse.toString(); int runStepsNo =
		 * JSONParser.totalResultsJSONParser(runStepsXml, "TotalResults"); return
		 * runStepsNo; }
		 */

	/**
	 * Updates overall status in alm test instance
	 * 
	 * @param runStatus
	 * @throws Exception
	 */
	public static void updateExecutionStatusInALM(String runStatus) throws Exception {

		AlmEntity crud = new AlmEntity();
		Response putRunResponse = crud.updateEntity(createdRunEntityUrl,
				AlmActions.retieveValueFromALMForNewRunStatus(runStatus));
	}

	/**
	 * Attach execution word report with respective alm run
	 * 
	 * @param testSetId
	 * @param testCaseId
	 * @throws Exception
	 */
	public static void attachExecutionReportInALM(String testSetId, String testCaseId, String wordDocumentFile)
			throws Exception {

		AlmEntity attach = new AlmEntity();
		Path path = Paths.get(wordDocumentFile);
		byte[] data;
		data = Files.readAllBytes(path);
		String octetStreamFileName = "ExecutionReport.docx";
		attach.attachExecutionWordDocReport(AlmRunCreation.createdRunEntityUrl, data, octetStreamFileName);
	}

	/**
	 * Returns alm test-instance url
	 * 
	 * @return testSetId
	 * @throws URISyntaxException
	 * @throws MalformedURLException
	 */
	public static String testSetUrlForReporting() throws MalformedURLException, URISyntaxException {

		String testSetUrl = AlmRestConnector.getInstance()
				.init(new HashMap<String, String>(), AlmActions.almHost, AlmActions.almDomain, AlmActions.almProject)
				.buildEntityCollectionUrl("test-instance");
		return testSetUrl;
	}

	public static void copyByteDataIntoExcelFile(byte[] attachmentData, String copiedFilePath) {

		FileOutputStream fos = null;
		File file;
		try {
			file = new File(copiedFilePath);
			fos = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos.write(attachmentData);
			fos.flush();
			System.out.println("File Written Successfully");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error in closing the Stream");
			}
		}
	}

	/**
	 * Update test run and test instance in ALM
	 * 
	 * @param testSetID
	 * @param testSetURL
	 * @param testCaseId
	 * @param runStatus
	 * @throws Exception
	 */
	public static void update_TestRun_And_Instance_In_ALM(String testSetURL, String testSetID, String testCaseId,
			String runStatus, String reportLocation) throws Exception {

		AlmEntity crud = new AlmEntity();
		AlmRunCreation.updateExecutionStatusInALM(runStatus);
		AlmRunCreation.attachExecutionReportInALM(testSetID, testCaseId, reportLocation);
		String testInstanceUrl = testSetURL;
		Response testSetresponse;
		testSetresponse = crud.readEntity(testInstanceUrl,
				"query={cycle-id[" + testSetID + "];" + "test-id[" + testCaseId + "]}");
		String testInstanceXML = testSetresponse.toString();
		String testInstanceID = JSONParser.entitiesJSONParser(testInstanceXML, 0, "id");
		String entityUrl = testInstanceUrl + "/" + testInstanceID;
		Response testInstanceResponse = crud.updateEntity(entityUrl,
				AlmActions.createNewEntityForTestInstanceUpdate(testSetID, testCaseId, runStatus));
	}
}
