package vision.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

/**
 * converts a Wall object to a renderable Mesh
 */
public class CustomMeshCreator {

	float[] vertices = new float[] {};
	int[] indices = new int[] {};

	/**
	 * creates a mesh off of a wall object. builds in holes for windows if
	 * necessary.
	 */
	public Geometry convert(Wall w) {
		WallAdapter wall = new WallAdapter(w);

		// build first orthogonal, then transform
		float minHole = wall.getHeight();

		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			minHole = Math.min(
					h.getPosition().getY() - h.getSize().getY() / 2f, minHole);
		}

		// add base rectangle
		addWallPlane(new Position(0f, 0f, 0f), new Size3D(wall.getWidth(), wall
				.getDepth(), minHole));

		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			// fill gaps between holes and base rect
			if (Math.abs(h.getPosition().getY() - h.getSize().getY() / 2f
					- minHole) > 0.002f) {
				addWallPlane(new Position(h.getPosition().getX(), h
						.getPosition().getY(), (minHole + (h.getPosition()
						.getZ() - h.getSize().getZ() / 2f)) / 2f), new Size3D(
						wall.getWidth(), wall.getDepth(), (h.getPosition()
								.getZ() - h.getSize().getZ() / 2f)
								- minHole));
			}

			// fill gaps between holes and top of wall
			float upperHoleBound = h.getPosition().getZ() + h.getSize().getZ()
					/ 2f;
			addWallPlane(new Position(h.getPosition().getX(), h.getPosition()
					.getY(), (wall.getHeight() + upperHoleBound) / 2f),
					new Size3D(wall.getWidth(), wall.getDepth(), (wall
							.getHeight() - upperHoleBound)));
		}

		Collections.sort(wall.getHoles(), new Comparator<Hole>() {

			@Override
			public int compare(Hole a, Hole b) {
				return (int) Math.signum(a.getPositionX1() - b.getPositionX1());
			}
		});

		HoleAdapter prevH = null;
		for (Hole hole : wall.getHoles()) {
			HoleAdapter h = new HoleAdapter(hole);
			float leftBoundH = h.getPosition().getX() - h.getSize().getX() / 2f;
			if (prevH == null) {
				addWallPlane(new Position(leftBoundH / 2, 0,
						(wall.getHeight() + minHole) / 2f),
						new Size3D(leftBoundH, wall.getDepth(), wall
								.getHeight()
								- minHole));
			}

			float rightBoundPrevH = -wall.getWidth() / 2f;
			if (prevH != null) {
				rightBoundPrevH = prevH.getPosition().getX()
						+ prevH.getSize().getX() / 2f;
			}
			addWallPlane(new Position((leftBoundH + rightBoundPrevH) / 2, 0,
					(wall.getHeight() + minHole) / 2f), new Size3D(leftBoundH
					- rightBoundPrevH, wall.getDepth(), wall.getHeight()
					- minHole));
			prevH = h;
		}
		CustomMesh m = assembleMesh();
		return new Geometry("a", m);
		//return transformCoordinates(m, wall);
	}

	private Geometry transformCoordinates(CustomMesh wallMesh, WallAdapter wall) {
		Geometry wallGeometry = new Geometry("Static Wall " + UUID.randomUUID());
		wallGeometry.setMesh(wallMesh);
		float a = 0;
		Vector2f wallDir = new Vector2f(wall.getStart().getX()
				- wall.getEnd().getX(), wall.getStart().getY()
				- wall.getEnd().getY());
		a = wallDir.angleBetween(new Vector2f(1f, 0f));
		wallGeometry = (Geometry) wallGeometry.move(wall.getPosition().getX(),
				wall.getPosition().getY(), 0);
		wallGeometry.setMaterial(new Material()); // TODO: set unshaded
													// material. requires
													// reference to an asset
													// manager
		return (Geometry) wallGeometry.rotate(0, 0, a);
	}

	private CustomMesh assembleMesh() {
		CustomMesh wallMesh = new CustomMesh();
		wallMesh.setMode(Mesh.Mode.TriangleStrip);
		wallMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		wallMesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indices));
		return wallMesh;
	}

	/**
	 * adds a 3D-Rectangle to the Mesh.
	 * 
	 * @param pos
	 *            the position of the rectangle, in center coordinates
	 * @param dim
	 *            dimension (x,y,z) of the rectangle
	 */
	private void addWallPlane(Position pos, Size3D dim) {
		float[] addvertices = new float[3 * 8];

		// lower left front
		addvertices[0] = pos.getX() - dim.getX() / 2;
		addvertices[1] = pos.getY() - dim.getY() / 2;
		addvertices[2] = pos.getZ() - dim.getZ() / 2;
		// lower right front
		addvertices[3] = pos.getX() + dim.getX() / 2;
		addvertices[4] = pos.getY() - dim.getY() / 2;
		addvertices[5] = pos.getZ() - dim.getZ() / 2;
		// upper right front
		addvertices[6] = pos.getX() + dim.getX() / 2;
		addvertices[7] = pos.getY() - dim.getY() / 2;
		addvertices[8] = pos.getZ() + dim.getZ() / 2;
		// upper left front
		addvertices[9] = pos.getX() - dim.getX() / 2;
		addvertices[10] = pos.getY() - dim.getY() / 2;
		addvertices[11] = pos.getZ() + dim.getZ() / 2;

		// lower left back
		addvertices[12] = pos.getX() - dim.getX() / 2;
		addvertices[13] = pos.getY() + dim.getY() / 2;
		addvertices[14] = pos.getZ() - dim.getZ() / 2;
		// lower right back
		addvertices[15] = pos.getX() + dim.getX() / 2;
		addvertices[16] = pos.getY() + dim.getY() / 2;
		addvertices[17] = pos.getZ() - dim.getZ() / 2;
		// upper right back
		addvertices[18] = pos.getX() + dim.getX() / 2;
		addvertices[19] = pos.getY() + dim.getY() / 2;
		addvertices[20] = pos.getZ() + dim.getZ() / 2;
		// upper left back
		addvertices[21] = pos.getX() - dim.getX() / 2;
		addvertices[22] = pos.getY() + dim.getY() / 2;
		addvertices[23] = pos.getZ() + dim.getZ() / 2;

		int[] addindices = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 4, 5, 6,
				7, 2, 4, 3, 7, 4, 0, 1, 5, 6, 2 };

		float[] newvertices = new float[vertices.length + addvertices.length];
		int[] newindices = new int[indices.length + addindices.length];

		// add to indices list
		for (int i = 0; i < indices.length; i++) {
			newindices[i] = indices[i];
		}
		for (int i = 0; i < addindices.length; i++) {
			newindices[i + indices.length] = addindices[i] + vertices.length;
		}
		indices = newindices;

		// add to vertices list
		for (int i = 0; i < vertices.length; i++) {
			newvertices[i] = vertices[i];
		}
		for (int i = 0; i < addvertices.length; i++) {
			newvertices[i + vertices.length] = addvertices[i];
		}
		vertices = newvertices;
	}
}