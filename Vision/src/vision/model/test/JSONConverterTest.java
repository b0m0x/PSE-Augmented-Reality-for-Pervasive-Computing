package vision.model.test;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import vision.model.JSONConverter;
import vision.model.Model;
import vision.model.Sensor;
import vision.view.View;


public class JSONConverterTest extends TestCase {
	
	Model model;
	
	
	private JSONConverter init() {
		try {
			model = new Model(null);
			} catch (JAXBException e) {
				
			}
		JSONConverter json = new JSONConverter(model);
		return json;
	}
	JSONConverter json = init();
	
	@Test
	public void testConnect() {
		
	

		String offlinestream = json.offlineStream();
		String stream = json.getJSONStream();
		
		assertNotSame(stream, offlinestream);
		
	}
	
	@Test
	public void testConvert() {		
		json.convert();
		assertTrue(json.getSensorList().size() > 0);
	}
	
	@Test
	public void testResetList() {
		json.resetList();
		ArrayList<Sensor> empty = new ArrayList<Sensor>();
		assertEquals(json.getSensorList(), empty);
	}
	

}
