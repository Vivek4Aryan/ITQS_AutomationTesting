package alm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import alm.HtmlToPlainText;

public class JSONParser {

	/**
	 * Parse JSON and returns entity field value
	 * 
	 * @param parserJSON
	 * @param fieldName
	 * @return string
	 * @throws JSONException 
	 */
	public static String fieldJSONParser(String parserJSON, String fieldName) throws JSONException {

			String textValue = "";
			JSONObject obj = new JSONObject(parserJSON);
			JSONArray fieldsArr = obj.getJSONArray("Fields");
			label: for (int i = 0; i < fieldsArr.length(); i++) {
				String fieldNameInArray = fieldsArr.getJSONObject(i).getString("Name");
				if (fieldNameInArray.equalsIgnoreCase(fieldName)) {
					for (int j = 0; j < fieldsArr.getJSONObject(i).getJSONArray("values").length(); j++) {
						if (fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j).length() != 0) {
							textValue = fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j)
									.getString("value");
							break label;
						}
					}
				}
			}
			return textValue;
	}

	/**
	 * Parse JSON, convert html response to plain text and returns entity field
	 * value
	 * 
	 * @param parserJSON
	 * @param fieldName
	 * @return string
	 * @throws JSONException 
	 */
	public static String fieldSpanTextJSONParser(String parserJSON, String fieldName) throws JSONException {

			String spanText = "";
			JSONObject obj = new JSONObject(parserJSON);
			JSONArray fieldsArr = obj.getJSONArray("Fields");
			label: for (int i = 0; i < fieldsArr.length(); i++) {
				String fieldNameInArray = fieldsArr.getJSONObject(i).getString("Name");
				if (fieldNameInArray.equalsIgnoreCase(fieldName)) {
					for (int j = 0; j < fieldsArr.getJSONObject(i).getJSONArray("values").length(); j++) {
						if (fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j).length() != 0) {
							String textValue = fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j)
									.getString("value");
							spanText = HtmlToPlainText.convertScenarioFromHtmlToPlainText(textValue);
							break label;
						}
					}
				}
			}
			return spanText;
	}

	/**
	 * Parse JSON and returns entity field value
	 * 
	 * @param parserJSON
	 * @param fieldNo
	 * @param fieldName
	 * @return string
	 * @throws JSONException 
	 */
	public static String entitiesJSONParser(String parserJSON, int fieldNo, String fieldName) throws JSONException {

			String textValue = "";
			JSONObject obj = new JSONObject(parserJSON);
			JSONArray entitiesArr = obj.getJSONArray("entities");
			JSONArray fieldsArr = entitiesArr.getJSONObject(fieldNo).getJSONArray("Fields");
			label: for (int i = 0; i < fieldsArr.length(); i++) {
				String fieldNameInArray = fieldsArr.getJSONObject(i).getString("Name");
				if (fieldNameInArray.equalsIgnoreCase(fieldName)) {
					for (int j = 0; j < fieldsArr.getJSONObject(i).getJSONArray("values").length(); j++) {
						if (fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j).length() != 0) {
							textValue = fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j)
									.getString("value");
							break label;
						}
					}
				}

			}
			return textValue;
	}

	/**
	 * Parse JSON, convert html response to plain text and returns entity field
	 * value
	 * 
	 * @param parserJSON
	 * @param fieldNo
	 * @param fieldName
	 * @return string
	 * @throws JSONException 
	 */
	public static String entitiesSpanFieldJSONParser(String parserJSON, int fieldNo, String fieldName) throws JSONException {

			String spanText = "";
			JSONObject obj = new JSONObject(parserJSON);
			JSONArray entitiesArr = obj.getJSONArray("entities");
			JSONArray fieldsArr = entitiesArr.getJSONObject(fieldNo).getJSONArray("Fields");
			label: for (int i = 0; i < fieldsArr.length(); i++) {
				String fieldNameInArray = fieldsArr.getJSONObject(i).getString("Name");
				if (fieldNameInArray.equalsIgnoreCase(fieldName)) {
					for (int j = 0; j < fieldsArr.getJSONObject(i).getJSONArray("values").length(); j++) {
						if (fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j).length() != 0) {
							String textValue = fieldsArr.getJSONObject(i).getJSONArray("values").getJSONObject(j)
									.getString("value");
							spanText = HtmlToPlainText.convertScenarioFromHtmlToPlainText(textValue);
							break label;
						}
					}
				}
			}
			return spanText;
	}

	/**
	 * Retrieve total result number from JSON response and returns total result
	 * field value
	 * 
	 * @param parserJSON
	 * @param fieldName
	 * @return int
	 * @throws JSONException 
	 */
	public static int totalResultsJSONParser(String parserJSON, String fieldName) throws JSONException {

			JSONObject obj = new JSONObject(parserJSON);
			int totalResults = (Integer) obj.get(fieldName);
			return totalResults;
	}
}