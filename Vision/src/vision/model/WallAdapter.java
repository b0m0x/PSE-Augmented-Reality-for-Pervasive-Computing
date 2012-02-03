package vision.model;

import java.util.List;

import vision.model.xml.Hole;
import vision.model.xml.Wall;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class WallAdapter {

	private final Wall wall;
	
	/**
	 * Contructs a WallAdapter.
	 * @param wall
	 */
	public WallAdapter(Wall wall) {
		this.wall = wall;
	}
	
	/**
	 * gets the position.
	 * @return
	 */
	public Position getPosition() {
		return new Position((wall.getPositionX1() + wall.getPositionX2()) / 2,
				(wall.getPositionY1() + wall.getPositionY2()) / 2, 0);
	}
	
	/**
	 * gets the startposition.
	 * @return
	 */
	public Position getStart() {
		return new Position(wall.getPositionX1(), wall.getPositionY1(), 0);
	}
	/**
	 * gets the endposition.
	 * @return
	 */
	public Position getEnd() {
		return new Position(wall.getPositionX2(), wall.getPositionY2(), 0);
	}
	
	/**
	 * gets the size.
	 * @return
	 */
	public Size3D getSize() {
		return new Size3D(getWidth(), getHeight(), getDepth());
	}
	/**
	 * gets a list of holes.
	 * @return
	 */
	public List<Hole> getHoles() {
		return wall.getHole();
	}
	
	/**
	 * gets the width
	 * @return
	 */
	public float getWidth() {
		Vector3f d = new Vector3f(wall.getPositionX2() - wall.getPositionX1(),
				wall.getPositionY2() - wall.getPositionY1(), 0);
		return d.length();
	}
	
	/**
	 * gets the height.
	 * @return
	 */
	public float getHeight() {
		return wall.getHeight();
	}
	/**
	 * gets the depth.
	 * @return
	 */
	public float getDepth() {
		return wall.getWide();
	}
	/**
	 * gets the rotation.
	 * @return
	 */
	public float getRotation() {
		final Vector2f wallDir = new Vector2f(getStart().getX()
				- getEnd().getX(), getStart().getY()
				- getEnd().getY());
		return wallDir.angleBetween(new Vector2f(1f, 0f));
	}
}
