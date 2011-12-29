package vision.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * converts a Wall object to a renderable Mesh
 */
public class CustomMeshCreator {

	float[] vertices = new float[] { };
	int[] indices = new int[] { };
	
	/**
	 * creates a mesh off of a wall object. builds in holes for windows if
	 * necessary.
	 */
	public Geometry convert(Wall w) {
			WallAdapter wall = new WallAdapter(w);
			//TODO (b0m0x): implement. depends on Wall to be finished
			CustomMesh wallMesh = new CustomMesh();
			
			//build first orthogonal, then transform
			float minHole = wall.getHeight();
			
			for (Hole h : wall.getHoles()) {
				minHole = Math.min(h.getPosition().getZ() - h.getSize().getZ() / 2f, minHole);
			}
			
			//add base rectangle
			addWallPlane(new Position(0f, 0f, 0f), new Size3D(wall.getWidth(), wall.getDepth(), minHole));
			
			for (Hole h : wall.getHoles()) {
				
				//fill gaps between holes and base rect
				addWallPlane(new Position(h.getPosition().getX(), h.getPosition().getY(), (minHole + (h.getPosition().getZ() - h.getSize().getZ() / 2f)) / 2f), new Size3D(wall.getWidth(), wall.getDepth(), (h.getPosition().getZ() - h.getSize().getZ() / 2f) - minHole));
				
				//fill gaps between holes and top of wall
				float upperHoleBound = h.getPosition().getZ() + h.getSize().getZ() / 2f;
				addWallPlane(new Position(h.getPosition().getX(), h.getPosition().getY(), (wall.getHeight() + upperHoleBound) / 2f), new Size3D(wall.getWidth(), wall.getDepth(), (wall.getHeight() - upperHoleBound)));
			}
			
			Collections.sort(wall.getHoles(), new Comparator<Hole>() {

				@Override
				public int compare(Hole a, Hole b) {
					return (int) Math.signum(a.getPosition().getX() - b.getPosition().getX());
				}
			});
			
			Hole prevH = null;
			for (Hole h : wall.getHoles()) {
				float leftBoundH = h.getPosition().getX() - h.getSize().getX() / 2f;
				if (prevH == null) {
					addWallPlane(new Position(leftBoundH / 2, 0, (wall.getHeight() + minHole) / 2f), new Size3D(leftBoundH, wall.getDepth() ,wall.getHeight() - minHole));
				}
				float rightBoundPrevH = prevH.getPosition().getX() + prevH.getSize().getX() / 2f;
				addWallPlane(new Position((leftBoundH + rightBoundPrevH) / 2, 0, (wall.getHeight() + minHole) / 2f), new Size3D(leftBoundH - rightBoundPrevH, wall.getDepth() ,wall.getHeight() - minHole));
				prevH = h;
			}
			CustomMesh m = assembleMesh();
			return transformCoordinates(m, wall); 
		}

	private Geometry transformCoordinates(CustomMesh wallMesh, WallAdapter wall) {
		Geometry wallGeometry = new Geometry();
		wallGeometry.setMesh(wallMesh);
		float a = 0;
		Vector3f wallDir = new Vector3f(wall.getStart().getX() - wall.getEnd().getX(), wall.getStart().getY() - wall.getEnd().getY(), 0); 
		a = wallDir.angleBetween(new Vector3f());
		wallGeometry.move(wall.getPosition().getX(), wall.getPosition().getY(), 0);
		return (Geometry) wallGeometry.rotate(0, 0, a);
	}

	private CustomMesh assembleMesh() {
		CustomMesh wallMesh = new CustomMesh();
		wallMesh.setBuffer(Type.Position, 3, vertices);
		wallMesh.setBuffer(Type.Index, 4, indices);
		return wallMesh;
	}

	private void addWallPlane(Position pos, Size3D dim) {
		float[] addvertices = new float[3 * 8];

		// lower left front
		vertices[0] = pos.getX() - dim.getX() / 2;
		vertices[1] = pos.getY() - dim.getY() / 2;
		vertices[2] = pos.getZ() - dim.getZ() / 2;
		// lower right front
		vertices[3] = pos.getX() + dim.getX() / 2;
		vertices[4] = pos.getY() - dim.getY() / 2;
		vertices[5] = pos.getZ() - dim.getZ() / 2;
		// upper right front
		vertices[6] = pos.getX() + dim.getX() / 2;
		vertices[7] = pos.getY() - dim.getY() / 2;
		vertices[8] = pos.getZ() + dim.getZ() / 2;
		// upper left front
		vertices[9] = pos.getX() - dim.getX() / 2;
		vertices[10] = pos.getY() - dim.getY() / 2;
		vertices[11] = pos.getZ() + dim.getZ() / 2;

		// lower left back
		vertices[0] = pos.getX() - dim.getX() / 2;
		vertices[1] = pos.getY() + dim.getY() / 2;
		vertices[2] = pos.getZ() - dim.getZ() / 2;
		// lower right back
		vertices[3] = pos.getX() + dim.getX() / 2;
		vertices[4] = pos.getY() + dim.getY() / 2;
		vertices[5] = pos.getZ() - dim.getZ() / 2;
		// upper right back
		vertices[6] = pos.getX() + dim.getX() / 2;
		vertices[7] = pos.getY() + dim.getY() / 2;
		vertices[8] = pos.getZ() + dim.getZ() / 2;
		// upper left back
		vertices[9] = pos.getX() - dim.getX() / 2;
		vertices[10] = pos.getY() + dim.getY() / 2;
		vertices[11] = pos.getZ() + dim.getZ() / 2;

		int[] addindices = new int[] { 0, 1, 2, 3, 
									4, 5, 6, 7, 
									0, 1, 4, 5, 
									6, 7, 2, 4, 
									3, 7, 4, 0, 
									1, 5, 6, 2 };
		
		float[] newvertices = new float[vertices.length + addvertices.length];
		int[] newindices = new int[indices.length + addindices.length];
		

		//add to indices list
		for (int i = 0; i < indices.length; i++) {
			newindices[i] = indices[i];
		}
		for (int i = 0; i < addindices.length; i++) {
			newindices[i + indices.length] = addindices[i] + vertices.length;
		}
		indices = newindices;
		
		//add to vertices list
		for (int i = 0; i < vertices.length; i++) {
			newvertices[i] = vertices[i];
		}
		for (int i = 0; i < addvertices.length; i++) {
			newvertices[i + vertices.length] = addvertices[i];
		}
		vertices = newvertices;
				
	}
}