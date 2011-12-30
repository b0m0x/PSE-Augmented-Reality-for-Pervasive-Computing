package vision.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

import vision.model.CustomMeshCreator;
import vision.model.Wall;

public class CustomMeshCreatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertWall1() {
		Wall wall = new Wall();
		wall.setPositionX1(1f);
		wall.setPositionY1(1f);

		wall.setPositionX2(0f);
		wall.setPositionY2(0f);
		
		CustomMeshCreator c = new CustomMeshCreator();
		Geometry g = c.convert(wall);
		
		assertEquals(24, g.getMesh().getIndexBuffer().size());
		assertEquals(Math.PI/4, g.getLocalRotation().toAngleAxis(new Vector3f(0f, 0f, 1f)), 0.01f);
		assertEquals(new Vector3f(0.5f, 0.5f, 0f), g.getLocalTranslation());
	}
		
	@Test
	public void testConvertWall2() {
		Wall wall = new Wall();
		wall.setPositionX1(-1f);
		wall.setPositionY1(-1f);

		wall.setPositionX2(0f);
		wall.setPositionY2(0f);
		
		CustomMeshCreator c = new CustomMeshCreator();
		Geometry g = c.convert(wall);
		
		assertEquals(24, g.getMesh().getIndexBuffer().size());
		assertEquals(Math.PI/2 + Math.PI/4, g.getLocalRotation().toAngleAxis(new Vector3f(0f, 0f, 1f)), 0.01f);
		assertEquals(new Vector3f(-0.5f, -0.5f, 0f), g.getLocalTranslation());
	}

	/**
	 * Same as Test 2, but with translation applied to test wall
	 */
	@Test
	public void testConvertWall2WithTranslation() {
		Wall wall = new Wall();
		wall.setPositionX1(1f);
		wall.setPositionY1(0f);

		wall.setPositionX2(2f);
		wall.setPositionY2(1f);
		
		CustomMeshCreator c = new CustomMeshCreator();
		Geometry g = c.convert(wall);
		
		assertEquals(24, g.getMesh().getIndexBuffer().size());
		assertEquals(Math.PI/2 + Math.PI/4, g.getLocalRotation().toAngleAxis(new Vector3f(0f, 0f, 1f)), 0.01f);
		assertEquals(new Vector3f(1.5f, 0.5f, 0f), g.getLocalTranslation());
	}


}
