package vision.model;

import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class WallAdapter {
	private Wall wall;

	public WallAdapter(Wall wall) {
		this.wall = wall;
	}

	public Position getPosition() {
		return new Position((wall.getPositionX1() + wall.getPositionX2()) / 2,
				(wall.getPositionY1() + wall.getPositionY2()) / 2, 0);
	}

	public Position getStart() {
		return new Position(wall.getPositionX1(), wall.getPositionY1(), 0);
	}

	public Position getEnd() {
		return new Position(wall.getPositionX2(), wall.getPositionY2(), 0);
	}

	public Size3D getSize() {
		return new Size3D(getWidth(), getHeight(), getDepth());
	}

	public List<Hole> getHoles() {
		return wall.getHole();
	}

	public float getWidth() {
		Vector3f d = new Vector3f(wall.getPositionX2() - wall.getPositionX1(),
				wall.getPositionY2() - wall.getPositionY1(), 0);
		return d.length();
	}

	public float getHeight() {
		return wall.getHeight();
	}

	public float getDepth() {
		return wall.getWide();
	}
	
	public float getRotation() {
		Vector2f wallDir = new Vector2f(getStart().getX()
				+ getEnd().getX(), getStart().getY()
				+ getEnd().getY());
		return wallDir.angleBetween(new Vector2f(1f, 0f));
	}
}
