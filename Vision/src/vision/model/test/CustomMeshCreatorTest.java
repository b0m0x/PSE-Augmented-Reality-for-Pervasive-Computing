package vision.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import vision.model.CustomMeshCreator;
import vision.model.Hole;
import vision.model.Wall;
import com.jme3.bullet.control.RigidBodyControl;

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
		Spatial g = c.convert(wall);
		
		RigidBodyControl ctrl = ((RigidBodyControl)g.getControl(0));

		assertEquals(Math.PI / 4, ctrl.getPhysicsRotation().toAngleAxis(
				new Vector3f(0f, 1f, 0f)), 0.01f);
		assertEquals(new Vector3f(0.5f, 0f, 0.5f), ((RigidBodyControl)g.getControl(0)).getPhysicsLocation());
	}

	@Test
	public void testConvertWall2() {
		Wall wall = new Wall();
		wall.setPositionX1(-1f);
		wall.setPositionY1(-1f);

		wall.setPositionX2(0f);
		wall.setPositionY2(0f);

		CustomMeshCreator c = new CustomMeshCreator();
		Spatial g = c.convert(wall);

		RigidBodyControl ctrl = ((RigidBodyControl)g.getControl(0));
		
		assertEquals(Math.PI / 2 + Math.PI / 4, ctrl.getPhysicsRotation()
				.toAngleAxis(new Vector3f(0f, 1f, 0f)), 0.01f);
		assertEquals(new Vector3f(-0.5f, 0f, -0.5f), ctrl.getPhysicsLocation());
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
		Spatial g = c.convert(wall);
		

		RigidBodyControl ctrl = ((RigidBodyControl)g.getControl(0));

		assertEquals(Math.PI / 2 + Math.PI / 4, ctrl.getPhysicsRotation()
				.toAngleAxis(new Vector3f(0f, 1f, 0f)), 0.01f);
		assertEquals(new Vector3f(1.5f, 0f, 0.5f), ctrl.getPhysicsLocation());
	}

	/**
	 * Same as Test 3, but with 1 hole
	 */
	@Test
	public void testConvertWall2WithTranslationAndHole() {
		Wall wall = new Wall();
		wall.setPositionX1(1f);
		wall.setPositionY1(0f);

		wall.setPositionX2(2f);
		wall.setPositionY2(1f);

		List<Hole> holes = new ArrayList<Hole>();

		Hole h1 = new Hole();
		h1.setPositionX1(1f);
		h1.setPositionY1(1f);
		h1.setPositionX2(2f);
		h1.setPositionY2(2f);
		holes.add(h1);

		wall.setHole(holes);

		CustomMeshCreator c = new CustomMeshCreator();
		Spatial g = c.convert(wall);

		assertEquals(Math.PI / 2 + Math.PI / 4, g.getLocalRotation()
				.toAngleAxis(new Vector3f(0f, 0f, 1f)), 0.01f);
		assertEquals(new Vector3f(1.5f, 0.5f, 0f), g.getLocalTranslation());
	}

	/**
	 * Same as Test 4, but with 2 Holes
	 */
	@Test
	public void testConvertWall2WithTranslationAndHoles() {
		Wall wall = new Wall();
		wall.setPositionX1(1f);
		wall.setPositionY1(0f);

		wall.setPositionX2(2f);
		wall.setPositionY2(1f);

		List<Hole> holes = new ArrayList<Hole>();

		Hole h1 = new Hole();
		h1.setPositionX1(1f);
		h1.setPositionY1(1f);
		h1.setPositionX2(2f);
		h1.setPositionY2(2f);
		holes.add(h1);

		Hole h2 = new Hole();
		h2.setPositionX1(3f);
		h2.setPositionY1(1.5f);
		h2.setPositionX2(3f);
		h2.setPositionY2(2f);
		holes.add(h2);

		wall.setHole(holes);

		CustomMeshCreator c = new CustomMeshCreator();
		Spatial g = c.convert(wall);

		assertEquals(Math.PI / 2 + Math.PI / 4, g.getLocalRotation()
				.toAngleAxis(new Vector3f(0f, 0f, 1f)), 0.01f);
		assertEquals(new Vector3f(1.5f, 0.5f, 0f), g.getLocalTranslation());
	}
	


}
