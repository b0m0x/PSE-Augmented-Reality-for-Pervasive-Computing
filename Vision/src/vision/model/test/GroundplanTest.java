package vision.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import vision.model.Groundplan;
import vision.model.Wall;

public class GroundplanTest {

	private Groundplan gp;
	
	@Before
	public void setUp() throws JAXBException {
		gp = vision.model.Groundplan.load();
		}

	@Test
	public void testGroundplan() {
		Wall test = (Wall) gp.getWall().get(0);
		int number1 = test.getWallNumber();
		float posx1 = test.getPositionX1();
		assertEquals(number1, 1);
		assertEquals(posx1, 23.422332333316, 0.000005);
	}

}