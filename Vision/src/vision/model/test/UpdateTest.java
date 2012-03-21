package vision.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vision.model.Database;
import vision.model.JSONConverter;
import vision.model.Model;
import vision.model.Update;

public class UpdateTest {
	@Test
	public void test() {
		Model model = Model.createModel(null);
		Update update = new Update(model);
		
		assertEquals(model, update.getDaten());
		
		JSONConverter json = new JSONConverter(model);
		update.setJSONConverter(json);
				
		assertEquals(json, update.getJSONConverter());
		
		Database data = new Database();
		data.connect(null);
		update.setDatabase(data);
		
		assertEquals(data, update.getDatabase());
		
		update.store(0);
		
	}

}
