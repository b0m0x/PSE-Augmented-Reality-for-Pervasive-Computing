package vision.model.test;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import vision.model.JSONConverter;
import vision.model.Model;
import vision.view.View;


public class JSONConverterTest extends TestCase {
	
	Model model;
	
	
	
	
	@Test
	public void testConnect() {
		
		try {
			model = new Model(null);
			} catch (JAXBException e) {
				
			}
		JSONConverter json = new JSONConverter(model);

		String offlinestream = json.offlineStream();
		String stream = json.getJSONStream();
		
		assertNotSame(stream, offlinestream);
		
	}
	
	@Test
	public void testConvert() {
		try {
			model = new Model(null);
			} catch (JAXBException e) {
				
			}
		JSONConverter json = new JSONConverter(model);
		
		json.convert();
		assertTrue(json.getSensorList().size() > 0);
	}
	

}
