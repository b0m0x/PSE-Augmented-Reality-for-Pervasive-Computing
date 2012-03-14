package vision.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import vision.model.Position;
import vision.model.WallAdapter;
import vision.model.xml.Wall;

public class WallAdapterTest {

	@Test
	public void testGetSize() {
		Wall w = new Wall();
		w.setPositionX1(0);
		w.setPositionX2(0);
		w.setPositionY1(1);
		w.setPositionY2(0);
		w.setHeight(2.5f);
		WallAdapter wall = new WallAdapter(w);
		assertEquals(1.f, wall.getSize().getX(), 0.0001f);

		assertEquals(2.5f, wall.getSize().getY(), 0.0001f);
	}


	@Test
	public void testGetRotation() {
		Wall w = new Wall();
		w.setPositionX1(0);
		w.setPositionY1(1);
		
		w.setPositionX2(0);
		w.setPositionY2(0);
		
		WallAdapter wall = new WallAdapter(w);
		assertEquals(-Math.PI/2 , wall.getRotation(), 0.0001f);
		assertEquals(0, wall.getPosition().getX(), 0.00f);
		assertEquals(0.5f, wall.getPosition().getY(), 0.00f);
		assertEquals(0, wall.getPosition().getZ(), 0.00f);
		
		w.setPositionX1(0);
		w.setPositionY1(2);
		
		w.setPositionX2(0);
		w.setPositionY2(0);
		
		assertEquals(-Math.PI/2 , wall.getRotation(), 0.0001f);
		
		
		w.setPositionX1(1);
		w.setPositionY1(2);
		
		w.setPositionX2(1);
		w.setPositionY2(0);
		
		assertEquals(-Math.PI/2 , wall.getRotation(), 0.0001f);
		
		
		w.setPositionX1(0);
		w.setPositionY1(0);
		
		w.setPositionX2(1);
		w.setPositionY2(1);
		
		assertEquals(Math.PI / 2 + Math.PI/4 , wall.getRotation(), 0.0001f);
		
		assertEquals(true, wall.getHoles().isEmpty());
	}

}
