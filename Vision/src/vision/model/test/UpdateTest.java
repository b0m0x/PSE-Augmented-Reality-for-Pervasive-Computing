package vision.model.test;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.model.Database;
import vision.model.JSONConverter;
import vision.model.Update;
import vision.model.Model;

public class UpdateTest {
	@Test
	public void test() throws JAXBException {
		Model model = new Model(null);
		Update update = new Update(model);
		
		assertEquals(model, update.getDaten());
		
		JSONConverter json = new JSONConverter(model);
		update.setJSONConverter(json);
				
		assertEquals(json, update.getJSONConverter());
		
		Database data = new Database();
		update.setDatabase(data);
		
		assertEquals(data, update.getDatabase());
		
		update.store(0);
		
	}

}
