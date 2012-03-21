package vision.model.test;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import vision.model.xml.Groundplan;
import vision.model.xml.Wall;

public class GroundplanTest {

	private Groundplan gp;

	@Before
	public void setUp() throws JAXBException {
		gp = new vision.model.xml.Groundplan().load();
	}

	@Test
	public void testGroundplan() {
		Wall test = (Wall) gp.getWalls().get(0);
		int number1 = test.getWallNumber();
		float posx1 = test.getPositionX1();
		assertEquals(number1, 1);
		assertEquals(0.0, posx1, 0.000005);
		
		assertEquals(2, gp.getFloorAndCeilings().size());
		assertEquals(40, gp.getStaticGeometries().size());

		assertEquals(5, gp.getLights().size());

	}

}