package vision.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;

/**
 * converts a Wall object to a renderable Mesh
 */
public class CustomMeshCreator {

	private float[] vertices = new float[] {};
	private int[] indices = new int[] {};
	private float[] normals = new float[] {};

	/**
	 * creates a mesh off of a wall object. builds in holes for windows if
	 * necessary.
	 */
	public Spatial convert(Wall w) {
		WallAdapter wall = new WallAdapter(w);
		assert(wall.getDepth() != 0);
		Node wallMesh = new Node("Wall");
		
		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			// plane under hole
			Geometry plane = new Geometry("wallplane");
			plane.setMesh(new Box(h.getSize().getX() / 2,  h.getPosition().getY() / 2 - h.getSize().getY() / 4, wall.getDepth() /2));
			plane.setLocalTranslation(h.getPosition().getX() - wall.getWidth() / 2, - wall.getHeight()/2 + (h.getPosition().getY() - h.getSize().getY() / 2) / 2, 0);
			
			wallMesh.attachChild(plane);
			
			//plane over hole
			plane = new Geometry("wallplane2");
			plane.setMesh(new Box(h.getSize().getX() / 2, (w.getHeight() - (h.getPosition().getY() + h.getSize().getY() / 2)) /2 , wall.getDepth() / 2));
			plane.setLocalTranslation(h.getPosition().getX() - wall.getWidth() / 2,  wall.getHeight()/2 - (w.getHeight() - h.getPosition().getY() - h.getSize().getY() / 2) / 2, 0);
			
			wallMesh.attachChild(plane);
		}
		
		Collections.sort(wall.getHoles(), new Comparator<Hole>() {

			@Override
			public int compare(Hole a, Hole b) {
				return (int) Math.signum(a.getPositionX1() - b.getPositionX1());
			}
		});
		
		//fill horizontal gaps between holes
		int numHoles = wall.getHoles().size();
		
		float lastHRightBound = 0;
		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			float hLeftBound = h.getPosition().getX() - h.getSize().getX() / 2;
			Geometry plane = new Geometry("wallplane");
			plane.setMesh(new Box((hLeftBound - lastHRightBound) / 2, wall.getHeight() / 2, wall.getDepth() /2));
			plane.setLocalTranslation((hLeftBound + lastHRightBound - wall.getWidth()) / 2, 0, 0);			
			wallMesh.attachChild(plane);
			lastHRightBound = hLeftBound + h.getSize().getX();
		}
		
		float hLeftBound = wall.getWidth();
		Geometry plane = new Geometry("wallplane");
		plane.setMesh(new Box((hLeftBound - lastHRightBound) / 2, wall.getHeight() / 2, wall.getDepth() /2));
		plane.setLocalTranslation((hLeftBound + lastHRightBound - wall.getWidth()) / 2, 0, 0);			
		wallMesh.attachChild(plane);
				

		return transformCoordinates(wallMesh, wall);
	}

	private Spatial transformCoordinates(Spatial wallGeometry, WallAdapter wall) {
		float a = 0;
		Vector2f wallDir = new Vector2f(wall.getStart().getX()
				+ wall.getEnd().getX(), wall.getStart().getY()
				+ wall.getEnd().getY());
		a = wallDir.angleBetween(new Vector2f(1f, 0f));
		wallGeometry = wallGeometry.rotate(0, a, 0);
		wallGeometry.setMaterial(new Material()); // TODO: set unshaded
													// material. requires
													// reference to an asset
													// manager
		return wallGeometry.move(wall.getPosition().getX(),
				0, wall.getPosition().getY());
	}
}