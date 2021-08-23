package itqs;

import java.util.HashMap;
import java.util.Map;

public class SharedVariableClass {

	static Map<String,Integer> IntegerSharedVariable;
	static Map<String,String> StringSharedVariable;
	
	public static void InitiateSharedVariableClass()
	{
		IntegerSharedVariable=new HashMap<String,Integer>();
		StringSharedVariable=new HashMap<String,String>();
		
	}
	public static void insertValueToStringMap(String KeyName,String value)
	{
		StringSharedVariable.put(KeyName, value);
	}
	public static void insertValueToIntegerMap(String KeyName,int value)
	{
		IntegerSharedVariable.put(KeyName, value);
	}
	
	public static String getSharedvariableValue(String KeyName)
	{
		String HashValue;
		HashValue=null;
		HashValue=StringSharedVariable.get(KeyName);
		return HashValue;
	}
	
	public static void clearSharedVariable()
	{
		StringSharedVariable.clear();
		IntegerSharedVariable.clear();
		
	}
	
	
}
