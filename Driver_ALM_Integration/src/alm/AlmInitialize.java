package alm;

import itqs.GenericKeywords;

public class AlmInitialize {
	
	private static AlmInitialize instance;
	
	public static AlmInitialize getInstance() {
		if (instance == null) {
			instance = new AlmInitialize();
		}
		return instance;
	}
	
	public void almInitialize() {
		
		AlmActions.setUsername(GenericKeywords.getALMDetails("ALMUsername"));
		AlmActions.setPassword(GenericKeywords.getALMDetails("ALMPassword"));
		AlmActions.setHOST(GenericKeywords.getALMDetails("ALMHost"));
		AlmActions.setPORT(GenericKeywords.getALMDetails("ALMPort"));
		AlmActions.setDOMAIN(GenericKeywords.getALMDetails("ALMDomain"));
		AlmActions.setPROJECT(GenericKeywords.getALMDetails("ALMProject"));
	}

}
