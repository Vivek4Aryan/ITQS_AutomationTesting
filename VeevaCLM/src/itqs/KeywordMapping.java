package itqs;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;

public class KeywordMapping {
	static String className = "Keywordmapping";

	public enum KeyWord {
		
		OPENAPP, 
		KILLBROWSER, 
		SETEDITBOX, 
		CLICKBUTTON, 
		WAIT, 
		WAIT_FOR_ELEMENT_TO_BE_VISIBLE, 
		CLICK_ON_DOCUMENT_IVA,
		SETEDITBOX_SEND_KEYS, 
		SETEDITBOX_DATE, 
		SCROLL_ON_PAGE, 
		CLICK_ON_SLIDE, 
		DELETE_EXISTING_SELECTION,
		SELECT_DATA_FROM_DROPDOWN, 
		CHECK_PUBLISH_STATUS, 
		SELECT_CHECKBOX, 
		CLICKBUTTON_JEXECUTOR, 
		SELECT_DROPDOWN, 
		VERIFY_STATUS_ALL,
		CLICK_ON_VIEW_IVA, 
		CLICK_ON_CRM_SLIDE, 
		SETEDITBOX_USERNAME_PASSWORD, 
		SWITCH_TO_IFRAME,
		CLICK_ON_CHECKBOX,
		SELECT_CUSTOM_TYPE_DROPDOWN,
		VERIFY_CHECKBOX_CFG_FULL4,
		VERIFY_CHECKBOX_FULL2,
		VERIFY_SHARING_GROUPS,

		OTHER,

	}

	public static int keywordActionMapping(Driver dobj) {
		int executionStatus;
		By locator;
		executionStatus = 1;
		locator = null;

		KeyWord enumKeyWordValue = KeyWord.valueOf(dobj.TestStep_Keyword_Used.toUpperCase());
		try {
			switch (enumKeyWordValue) {

			case OPENAPP:
				Driver.driver = GenericKeywords.launchBrowser(GenericKeywords.getConfigDetails("BrowserDetails"));
				executionStatus = SpecificKeywords.openApplication(Driver.driver, dobj.Data_Step_Value);
				// executionStatus=1;
				break;

			case KILLBROWSER:
				executionStatus = SpecificKeywords.exitBrowser(Driver.driver);
				// executionStatus=1;
				break;

			case SETEDITBOX:
				System.out.println("SetEdit Issue value: " + (dobj.Data_Step_Value));
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				System.out.println("SetEdit locator : " + locator);
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.setEditBox(Driver.driver, locator, dobj.Data_Step_Value);
					System.out.println("Editbox does  exist : " + dobj.TestStep_ObjectName);
				} else {
					executionStatus = 0;
					System.out.println("Editbox does not exist : " + dobj.TestStep_ObjectName);
				}
				break;

			case SETEDITBOX_USERNAME_PASSWORD:
				System.out.println("SetEdit Issue value: " + (dobj.Data_Step_Value));
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				System.out.println("SetEdit locator : " + locator);
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.setEditBoxUsernameAndPassword(Driver.driver, locator,
							dobj.Data_Step_Value);
					System.out.println("Editbox does  exist : " + dobj.TestStep_ObjectName);
				} else {
					executionStatus = 0;
					System.out.println("Editbox does not exist : " + dobj.TestStep_ObjectName);
				}
				break;

			case CLICKBUTTON:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.clickButton(Driver.driver, locator);
				break;

			case CLICKBUTTON_JEXECUTOR:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				SpecificKeywords.clickButtonJavaExecutor(Driver.driver, locator);
				executionStatus = 1;

				break;

			case WAIT:
				Driver.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				executionStatus = 1;
				break;

			case SCROLL_ON_PAGE:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				SpecificKeywords.scrollOnPage(Driver.driver, locator);
				executionStatus = 1;
				break;

			case WAIT_FOR_ELEMENT_TO_BE_VISIBLE:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.wait_For_Element_To_Be_Visible(Driver.driver, 2000, locator);
				break;

			case CLICK_ON_DOCUMENT_IVA:
				executionStatus = SpecificKeywords.clickOnDocumentIVA(Driver.driver, dobj.Data_Step_Value);
				break;

			case SWITCH_TO_IFRAME:
				executionStatus = SpecificKeywords.switchToIframe(Driver.driver, dobj.Data_Step_Value);
				break;

			case SELECT_CHECKBOX:
				executionStatus = SpecificKeywords.selectCheckBox(Driver.driver, dobj.Data_Step_Value);
				break;

			case DELETE_EXISTING_SELECTION:
				//locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.deleteExistingSelection(Driver.driver, dobj.TestStep_ObjectName,
						dobj.Data_Step_Value);
				break;

			case CHECK_PUBLISH_STATUS:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.checkPublishStatus(Driver.driver, locator, dobj.Data_Step_Value);
				break;

			case SELECT_DATA_FROM_DROPDOWN:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.selectDataFromDropDown(Driver.driver, locator, dobj.Data_Step_Value);
				break;

			case CLICK_ON_SLIDE:
				executionStatus = SpecificKeywords.clickOnSlide(Driver.driver, dobj.Data_Step_Value);
				break;
			case SETEDITBOX_SEND_KEYS:
				System.out.println("SetEdit Issue value: " + (dobj.Data_Step_Value));
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				System.out.println("SetEdit locator : " + locator);
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.setEditBoxWithSendKeys(Driver.driver, locator,
							dobj.Data_Step_Value);
					System.out.println("Editbox does  exist : " + dobj.TestStep_ObjectName);
				} else {
					executionStatus = 0;
					System.out.println("Editbox does not exist : " + dobj.TestStep_ObjectName);
				}
				break;

			case SETEDITBOX_DATE:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.setEditBoxForDate(Driver.driver, locator, dobj.Data_Step_Value);
				} else {
					executionStatus = 0;
				}
				break;

			case CLICK_ON_VIEW_IVA:
				executionStatus = SpecificKeywords.clickOnViewIVA(Driver.driver, dobj.Data_Step_Value);
				break;

			case CLICK_ON_CRM_SLIDE:
				executionStatus = SpecificKeywords.clickOnCRMSlide(Driver.driver, dobj.Data_Step_Value);
				break;
				
			case CLICK_ON_CHECKBOX:
				executionStatus = SpecificKeywords.clickOnCheckBox(Driver.driver, dobj.Data_Step_Value);
				break;

			case SELECT_DROPDOWN:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.selectDropdownOption(Driver.driver, locator,
							dobj.Data_Step_Value);

				} else {
					executionStatus = 0;
				}

				break;
				
			case SELECT_CUSTOM_TYPE_DROPDOWN:
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				if (SpecificKeywords.isElementExist(Driver.driver, locator)) {
					executionStatus = SpecificKeywords.selectCustomTypeDropDown(Driver.driver, locator,
							dobj.Data_Step_Value);

				} else {
					executionStatus = 0;
				}

				break;

			case VERIFY_STATUS_ALL:

				String Status_Value = dobj.Data_Step_Value;
				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.statusCheck(Driver.driver, Status_Value, locator);
				break;
				
			case VERIFY_CHECKBOX_CFG_FULL4:

				executionStatus = SpecificKeywords.verifyCheckBoxCFGFull4(Driver.driver, dobj.Data_Step_Value);
				break;

			case VERIFY_CHECKBOX_FULL2:

				executionStatus = SpecificKeywords.verifyCheckBoxFull2(Driver.driver, dobj.Data_Step_Value);
				break;
				
			case VERIFY_SHARING_GROUPS:

				locator = GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.TestStep_ObjectName));
				executionStatus = SpecificKeywords.verifySharingGroups(Driver.driver, locator, dobj.Data_Step_Value);
				break;
				
			case OTHER:
				executionStatus = 0;
				break;

			default:
				break;
			}
		} catch (Exception e) {
			executionStatus = -1;
			LoggerClass.WriteToLog(className, enumKeyWordValue.toString() + " KeyWord Failed : " + e.getMessage());
		}
		/*
		 * locator=GenericKeywords.getbjectLocator(GenericKeywords.getOR(dobj.
		 * TestStep_ObjectName)); if(GenericKeywords.isElementExist(Driver.driver,
		 * locator)) {
		 * 
		 * executionStatus=1 } else//isElementExist { executionStatus=-1;
		 * }//isElementExist return executionStatus;
		 */
		return executionStatus;
	}

}
