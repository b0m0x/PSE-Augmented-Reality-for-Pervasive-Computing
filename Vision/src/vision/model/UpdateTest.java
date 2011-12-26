package vision.model;

import junit.framework.TestCase;

public class UpdateTest extends TestCase {
	
	Update update = new Update();
	
	public void testGetDatabase() {
		assertNotNull(update.getDatabase());
	}
	
}
