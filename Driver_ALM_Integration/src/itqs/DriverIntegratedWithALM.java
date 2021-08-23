package itqs;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import alm.AlmActions;
import alm.AlmConnector;
import alm.AlmEntity;
import alm.AlmInitialize;
import alm.AlmRestConnector;
import alm.AlmRunCreation;
import alm.JSONParser;
import alm.QCRestConnector;
import alm.Response;

public class DriverIntegratedWithALM {

	public static String testSetsID;
	public static String testSetUrl;
	private static String testCaseAttachmentFileLocation = ".//Data//TestAutomationSheet.xlsx";
	private static String executionReportPath = ".//Result//ExecutionReport.docx";
	static String className = "DriverIntegratedWithALM";

	/**
	 * Establise ALM Connection using Test Set ID and fetch Test Case Details,
	 * initiate Driver, create run for each test case in ALM and attach execution
	 * report with respective run.
	 * 
	 */
	public static void main(String[] args) {

		AlmInitialize.getInstance().almInitialize();
		String almTestSets = GenericKeywords.getALMDetails("ALMTestSetId");
		String runFlagInAlm = GenericKeywords.getALMDetails("RunFlagInAlm");
		List<String> almTestSetIds = new ArrayList<String>();
		for (int k = 0; k < almTestSets.split(",").length; k++) {
			almTestSetIds.add(almTestSets.split(",")[k]);
		}
		try {
			AlmRestConnector conn = AlmRestConnector.getInstance();
			conn.init(new HashMap<String, String>(), AlmActions.almHost, AlmActions.almDomain, AlmActions.almProject);
			AlmConnector alm = new AlmConnector();
			try {
				alm.login(AlmActions.almUsername, AlmActions.almPassword);
				QCRestConnector.getInstance().getQCSession();

				for (String testSetID : almTestSetIds) {

					GenericKeywords.deleteMultiple_File_With_Specific_Word_In_Name_FromFolder("./Result/", "ALM");
					AlmEntity crud = new AlmEntity();
					testSetUrl = conn.buildEntityCollectionUrl("test-instance");
					String queryParam = "query={cycle-id[" + testSetID + "]}";
					Response testSetresponse = crud.readEntity(testSetUrl, queryParam);
					String testSetJSON = testSetresponse.toString();
					int TotalTestCaseNo = JSONParser.totalResultsJSONParser(testSetJSON, "TotalResults");

					for (int j = 0; j < TotalTestCaseNo; j++) {

						String testCaseId = JSONParser.entitiesJSONParser(testSetJSON, j, "test-id");
						String testCaseUrl = conn.buildEntityCollectionUrl("test");
						testCaseUrl += "/" + testCaseId;
						Response testCaseResponse = crud.readEntity(testCaseUrl, null);
						String TestCaseJSON = testCaseResponse.toString();
						String testCaseAttachment = JSONParser.fieldSpanTextJSONParser(TestCaseJSON, "attachment");

						if (testCaseAttachment.equalsIgnoreCase("Y")) {
							Response testCaseAttachmentResponse = crud.readEntity(testCaseUrl + "/attachments", null);
							String testCaseAttachmentXml = testCaseAttachmentResponse.toString();
							System.out.println("testCaseAttachmentXml:" + testCaseAttachmentXml);
							String testCaseAttachmentName = JSONParser.entitiesJSONParser(testCaseAttachmentXml, 0,
									"name");
							byte[] readResponse = crud
									.readAttachmentData(testCaseUrl + "/attachments" + "/" + testCaseAttachmentName);
							GenericKeywords.deleteFile(testCaseAttachmentFileLocation);
							AlmRunCreation.copyByteDataIntoExcelFile(readResponse, testCaseAttachmentFileLocation);

							// Initialize The Driver
							Driver.main();
							String getALMResult = SpecificKeywords.getDetails("ALM_UPDATE",
									".//Result//ALM_RESULT_UPDATE.txt");

							if (runFlagInAlm.equalsIgnoreCase("Y")) {

								// Create New Run in ALM
								AlmRunCreation.getInstance().createRun(testSetUrl, testSetID, testCaseId);
								AlmRunCreation.update_TestRun_And_Instance_In_ALM(testSetUrl, testSetID, testCaseId,
										getALMResult, executionReportPath);
							}
						}
					}
				}
			} catch (Exception e) {
				LoggerClass.WriteToLog(className, "ALM Connection is not stablised");
			}
		} catch (Exception e) {
			LoggerClass.WriteToLog(className, e.getMessage());
		}
	}

	/**
	 * Logout from Alm
	 * 
	 * @throws Exception
	 */
	public void almLogout() throws Exception {

		AlmConnector alm = new AlmConnector();
		alm.logout();
		alm = null;
	}
}
