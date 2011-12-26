package vision.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.jme3.scene.VertexBuffer;

/**
 * converts a Wall object to a renderable Mesh
 */
public class CustomMeshCreator {

		
		/**
		 * creates a mesh off of a wall object.
		 * builds in holes for windows if necessary.
		 */
		public CustomMesh convert(Wall wall) {
			//TODO (b0m0x): implement. depends on Wall to be finished
			CustomMesh wallMesh = new CustomMesh();
			
			//build first orthogonal, then transform
			float minHole = wallMesh.getHeight();
			
			for (Hole h : wall.getHoles()) {
				minHole = Math.min(h.getPosition() - h.getSize().getZ() / 2f, minHole);
			}
			
			//add base rectangle
			addWallPlane(wallMesh, new Position(0f, 0f, 0f), new Size3D(wall.getWidth(), wall.getDepth(), minHole));
			
			for (Hole h : wall.getHoles()) {
				
				//fill gaps between holes and base rect
				addWallPlane(wallMesh, new Position(h.getPosition().getX(), h.getPosition().getY(), (minHole + (h.getPosition().getZ() - h.getSize().getZ() / 2f)) / 2f), new Size3D(wall.getWidth(), wall.getDepth(), (h.getPosition().getZ() - h.getSize().getZ() / 2f) - minHole));
				
				//fill gaps between holes and top of wall
				float upperHoleBound = h.getPosition().getZ() + h.getSize().getZ() / 2f;
				addWallPlane(wallMesh, new Position(h.getPosition().getX(), h.getPosition().getY(), (wall.getHeight() + upperHoleBound) / 2f), new Size3D(wall.getWidth(), wall.getDepth(), (wall.getHeight() - upperHoleBound)));
			}
			
			Collections.sort(wall.getHoles(), new Comparator<Hole>() {

				@Override-
				public int compare(Hole a, Hole b) {
					return a.getPosition().getX() - b.getPosition().getX();
				}
			});
			
			Hole prevH = null;
			for (Hole h : wall.getHoles()) {
				float leftBoundH = h.getPosition().getX() - h.getSize().getX() / 2f;
				if (prevH == null) {
					addWallPlane(wallMesh, new Position(leftBoundH / 2, 0, (wall.getHeight() + minHole) / 2f), new Size3D(leftBoundH, wall.getDepth() ,wall.getHeight() - minHole));
				}
				float rightBoundPrevH = prevH.getPosition().getX() + prevH.getSize().getX() / 2f;
				addWallPlane(wallMesh, new Position((leftBoundH + rightBoundPrevH) / 2, 0, (wall.getHeight() + minHole) / 2f), new Size3D(leftBoundH - rightBoundPrevH, wall.getDepth() ,wall.getHeight() - minHole));
				prevH = h;
			}
			
			return wallMesh;
		}
		
		private void addWallPlane(CustomMesh wallMesh, Position pos, Size3D dim) {
			VertexBuffer vb = new VertexBuffer();
			wallMesh.setBuffer(vb);
			throw new UnsupportedOperationException("stub");
		}
}