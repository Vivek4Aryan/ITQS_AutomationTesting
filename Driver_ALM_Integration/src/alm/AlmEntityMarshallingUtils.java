package alm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * A utility class for converting between jaxb annotated objects and xml.
 */
public class AlmEntityMarshallingUtils {

	private AlmEntityMarshallingUtils() {
	}

	/**
	 * A utility method for converting between jaxb annotated objects and xml.
	 * 
	 * @param cls
	 * @param xml
	 * @return <T> T
	 * @throws JAXBException
	 */
	public static <T> T marshal(Class<T> cls, String xml) throws JAXBException {
		T res;
		if (cls == xml.getClass()) {
			@SuppressWarnings("unchecked")
			T uncheckedXml=(T) xml;
			res = (T) uncheckedXml;
		} else {
			JAXBContext ctx = JAXBContext.newInstance(cls);
			Unmarshaller marshaller = ctx.createUnmarshaller();
			@SuppressWarnings("unchecked")
			T uncheckedXml=(T) marshaller.unmarshal(new StringReader(xml));
			res = (T) uncheckedXml;
		}
		return res;
	}

	/**
	 * A utility method for converting between jaxb annotated objects and xml.
	 * 
	 * @param cls
	 * @param obj
	 * @return <T> string
	 * @throws Exception
	 */
	public static <T> String unmarshal(Class<T> cls, Object obj) throws JAXBException {

		JAXBContext ctx = JAXBContext.newInstance(cls);
		Marshaller marshaller = ctx.createMarshaller();
		StringWriter entityXml = new StringWriter();
		marshaller.marshal(obj, entityXml);
		String entityString = entityXml.toString();
		return entityString;
	}
}