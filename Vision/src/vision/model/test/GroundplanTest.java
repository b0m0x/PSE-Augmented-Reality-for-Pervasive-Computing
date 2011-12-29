package vision.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import vision.model.Groundplan;
import vision.model.Wall;

public class GroundplanTest {

	private Groundplan gp;
	
	@Before
	public void setUp() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("vision.model");
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		gp = (Groundplan) unmarshaller
				.unmarshal(new File(
						"C://Users//Benedikt//PSE-Augmented-Reality-for-Pervasive-Computing//Vision//libs//groundplan.xml"));
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
