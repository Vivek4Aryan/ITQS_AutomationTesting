package alm;

public class AlmActions {

	public AlmActions() {
	}

	public static String almUsername;
	public static String almPassword;
	public static String almHost;
	public static String almPort;
	public static String almDomain;
	public static String almProject;

	/**
	 * To set the alm username
	 * 
	 * @param username
	 */
	public static void setUsername(String username) {
		almUsername = username;
	}

	/**
	 * To set the alm password
	 * 
	 * @param password
	 */
	public static void setPassword(String password) {
		almPassword = password;
	}

	/**
	 * To set the alm host
	 * 
	 * @param host
	 */
	public static void setHOST(String host) {
		almHost = host;
	}

	/**
	 * To set the alm port
	 * 
	 * @param port
	 */
	public static void setPORT(String port) {
		almPort = port;
	}

	/**
	 * To set the alm domain
	 * 
	 * @param domain
	 */
	public static void setDOMAIN(String domain) {
		almDomain = domain;
	}

	/**
	 * To set the alm project
	 * 
	 * @param project
	 */
	public static void setPROJECT(String project) {
		almProject = project;
	}

	/**
	 * Returns boolean after checking versioning
	 * 
	 * @param entityType
	 * @param domain
	 * @param project
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isVersioned(String entityType, final String domain, final String project) throws Exception {

		AlmRestConnector con = AlmRestConnector.getInstance();
		String descriptorUrl = con
				.buildUrl("rest/domains/" + domain + "/projects/" + project + "/customization/entities/" + entityType);
		String descriptorXml = con.httpGet(descriptorUrl, null, null).toString();
		AlmEntityDescriptor descriptor = AlmEntityMarshallingUtils.marshal(AlmEntityDescriptor.class, descriptorXml);
		boolean isVersioned = descriptor.getSupportsVC().getValue();
		return isVersioned;
	}

	/**
	 * Generate entities in xml format
	 * 
	 * @param field
	 * @param value
	 * @return string
	 */
	public static String generateFieldXml(String field, String value) {
		return "<Field Name=\"" + field + "\"><Value>" + value + "</Value></Field>";
	}

	/**
	 * Returns alm test instance entity in xml format
	 * 
	 * @param cycleId
	 * @param testCaseId
	 * @return string
	 */
	public static String createNewEntityForTestInstanceUpdate(String cycleId, String testCaseId, String status) {

		String entityTestInstanceActualTesterValue = AlmActions.almUsername;
		String entityTestInstanceTestIdValue = testCaseId;
		String entityTestInstanceCycleIdValue = cycleId;
		String entityTestInstanceStatusValue = status;
		String entityTestInstanceFormat = "<Entity Type=\"test-instance\">" + "<Fields>"
				+ AlmActions.generateFieldXml("actual-tester", "%1$s") + AlmActions.generateFieldXml("test-id", "%2$s")
				+ AlmActions.generateFieldXml("cycle-id", "%3$s") + AlmActions.generateFieldXml("status", "%4$s")
				+ "</Fields>" + "</Entity>";
		String entityTestInstanceXml = String.format(entityTestInstanceFormat, entityTestInstanceActualTesterValue,
				entityTestInstanceTestIdValue, entityTestInstanceCycleIdValue, entityTestInstanceStatusValue);
		return entityTestInstanceXml;
	}

	/**
	 * Returns new alm test run entity in xml format
	 * 
	 * @param testCycleId
	 * @param testCaseId
	 * @return string
	 */
	public static String createNewEntityForNewRun(String testCycleId, String testCaseId) {

		String entityNewRunName = "Run_" + Double.toHexString(Math.random());
		String entityNewRunTesterValue = AlmActions.almUsername;
		String entityNewRunTestIdValue = testCaseId;
		String entityNewRunTestCycleIdValue = testCycleId;
		String entityNewRunSubTypeIdValue = "hp.qc.run.MANUAL";

		String entityNewRunFormat = "<Entity Type=\"run\">" + "<Fields>" + AlmActions.generateFieldXml("name", "%1$s")
				+ AlmActions.generateFieldXml("owner", "%2$s") + AlmActions.generateFieldXml("test-id", "%3$s")
				+ AlmActions.generateFieldXml("testcycl-id", "%4$s") + AlmActions.generateFieldXml("subtype-id", "%5$s")
				+ "</Fields>" + "</Entity>";
		String entityNewRunXml = String.format(entityNewRunFormat, entityNewRunName, entityNewRunTesterValue,
				entityNewRunTestIdValue, entityNewRunTestCycleIdValue, entityNewRunSubTypeIdValue);
		return entityNewRunXml;
	}

	/**
	 * Returns created run entity in xml format
	 * 
	 * @param runStatus
	 * @return string
	 */
	public static String retieveValueFromALMForNewRunStatus(String runStatus) {

		String entityRunStatusValue = runStatus;
		String entityRunFormat = "<Entity Type=\"run\">" + "<Fields>" + AlmActions.generateFieldXml("status", "%s")
				+ "</Fields>" + "</Entity>";
		String entityRunXml = String.format(entityRunFormat, entityRunStatusValue);
		return entityRunXml;
	}

	/**
	 * Returns created run step entity in xml format
	 * 
	 * @param runStepStatus
	 * @param runStepExpected
	 * @return string
	 */
	public static String retrieveValueFromALMForRunSteps(String runStepStatus, String runStepExpected) {

		String entityRunStepStatusValue = runStepStatus;
		String entityRunStepActualValue = runStepExpected;
		String entityRunStepFormat = "<Entity Type=\"run-step\">" + "<Fields>"
				+ AlmActions.generateFieldXml("status", "%1$s") + AlmActions.generateFieldXml("actual", "%2$s")
				+ "</Fields>" + "</Entity>";
		String entityRunStepXml = String.format(entityRunStepFormat, entityRunStepStatusValue,
				entityRunStepActualValue);
		return entityRunStepXml;
	}
}
