package vision.model;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.Comparator;
import javax.vecmath.Quat4f;


import com.bulletphysics.linearmath.QuaternionUtil;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.bullet.util.Converter;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;

/**
 * converts a wall object to a renderable mesh.
 */
public class CustomMeshCreator {
	
	private static final float TEXTURE_SIZE = 1f;
	
	private void setTexCoords(Box box, float textureSize) {
		float x = box.xExtent / textureSize;
        float y = box.yExtent / textureSize;
        float z = box.zExtent / textureSize;
        
        float[] texCoordinates = { x, 0, 0, 0, 0, y, x, y, // back
                z, 0, 0, 0, 0, y, z, y, // right
                x, 0, 0, 0, 0, y, x, y, // front
                z, 0, 0, 0, 0, y, z, y, // left
                z, 0, 0, 0, 0, x, z, x, // top
                z, 0, 0, 0, 0, x, z, x // bottom
     
          };
        final FloatBuffer buffer = box.getFloatBuffer(Type.TexCoord);
        buffer.rewind();
        buffer.put(texCoordinates);
	}
	
	/**
	 * This method creates the floor.
	 * @param position
	 * @param size
	 * @return
	 */
	public Spatial createFloor(Vector3f position, Vector3f size) {
		Geometry floor = new Geometry("floor");
		Box box = new Box(size.x, size.y, size.z);
		setTexCoords(box, TEXTURE_SIZE);
		floor.setMesh(box);
		RigidBodyControl ctrl = new RigidBodyControl(new BoxCollisionShape(size), 0);
		floor.addControl(ctrl);
		ctrl.setPhysicsLocation(position);
		return floor;
	}
	
	/**
	 * This method creates the ceiling.
	 * @param position
	 * @param size
	 * @return
	 */
	public Spatial createCeiling(Vector3f position, Vector3f size) {
		Spatial ceiling = createFloor(position, size);
		ceiling.setName("ceiling");
		return ceiling;
	}
	
	/**
	 * creates a mesh off of a wall object. builds in holes for windows if
	 * necessary.
	 * @return
	 */
	public Spatial convert(Wall w) {
		WallAdapter wall = new WallAdapter(w);
		assert (wall.getDepth() != 0);
		
		Node wallMesh = new Node("Wall");
		
		boolean hasDoor = false;
		
		for (Hole hole : wall.getHoles()) {
			if (hole.getPositionY1() == 0) {
				hasDoor = true;
			}
			HoleAdapter h = new HoleAdapter(hole);
			float width = h.getSize().getX() / 2;
			float lowerHeight = h.getPosition().getY() - h.getSize().getY() / 2;
			float upperHeight = (w.getHeight() - (h.getPosition().getY() + h.getSize().getY() / 2));
			
			// plane under hole
			addWallBlock(wallMesh, new Vector3f(h.getPosition().getX() - wall.getWidth() / 2, - wall.getHeight()/2 + (h.getPosition().getY() - h.getSize().getY() / 2) / 2, 0), 
					new Vector3f(width, lowerHeight / 2, wall.getDepth() / 2));
			
			//plane over hole
			addWallBlock(wallMesh, new Vector3f(h.getPosition().getX() - wall.getWidth() / 2,  wall.getHeight()/2 - (w.getHeight() - h.getPosition().getY() - h.getSize().getY() / 2) / 2, 0), 
					new Vector3f(width, upperHeight /2 , wall.getDepth() / 2));
			
		}
		
		Collections.sort(wall.getHoles(), new Comparator<Hole>() {

			@Override
			public int compare(Hole a, Hole b) {
				return (int) Math.signum(a.getPositionX1() - b.getPositionX1());
			}
		});

		float lastHRightBound = 0;
		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			float hLeftBound = h.getPosition().getX() - h.getSize().getX() / 2;
			Vector3f position = new Vector3f((hLeftBound + lastHRightBound - wall.getWidth()) / 2, 0, 0);

			addWallBlock(wallMesh, position, new Vector3f((hLeftBound - lastHRightBound) / 2, wall.getHeight() / 2, wall.getDepth() /2));
			lastHRightBound = hLeftBound + h.getSize().getX();
		}

		float hLeftBound = wall.getWidth();
		
		addWallBlock(wallMesh, new Vector3f((hLeftBound + lastHRightBound - wall.getWidth()) / 2, 0, 0), new Vector3f((hLeftBound - lastHRightBound) / 2, wall.getHeight() / 2, wall.getDepth() /2));

		
		if (!hasDoor) {
			RigidBodyControl ctrl = new RigidBodyControl(new BoxCollisionShape(new Vector3f(wall.getWidth() / 2f, wall.getHeight() / 2f, wall.getDepth() /2f)), 0f);
			ctrl.setKinematic(false);
			wallMesh.addControl(ctrl);
		} else {
			CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(wallMesh);
			RigidBodyControl ctrl = new RigidBodyControl(sceneShape, 0);
			ctrl.setKinematic(false);
			wallMesh.addControl(ctrl);
		}
		
		transformCoordinates(wallMesh, wall);
		
		return wallMesh;
	}
	
	private void addWallBlock(Node node, Vector3f position, Vector3f halfExtends) {
		Geometry g = new Geometry("wallblock");
		Box box = new Box(position, halfExtends.getX(), halfExtends.getY(), halfExtends.getZ());
		setTexCoords(box, TEXTURE_SIZE);
		g.setMesh(box);
		node.attachChild(g);
		
	}

	private void transformCoordinates(Spatial wallGeometry, WallAdapter wall) {
		RigidBodyControl ctrl = (RigidBodyControl) wallGeometry.getControl(0);
		float a = wall.getRotation();
		
		ctrl.setPhysicsRotation(new Quaternion(new float[] {0, a, 0}));
		wallGeometry.setMaterial(new Material()); // TODO: set unshaded
													// material. requires
													// reference to an asset
													// manager

		ctrl.setPhysicsLocation(new Vector3f(wall.getPosition().getX(),
				0, wall.getPosition().getY()));
	}
}