package vision.model;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;

public class WallAdapter {
	private Wall wall;
	
	public WallAdapter(Wall wall) {
		this.wall = wall;
	}
	
	public Position getPosition() {
		return new Position((wall.positionX1 + wall.positionX2) / 2, (wall.positionY1 + wall.positionY2)/2, 0);
	}
	
	public Position getStart() {
		return new Position(wall.positionX1, wall.positionY1, 0);
	}
	
	public Position getEnd() {
		return new Position(wall.positionX2, wall.positionY2, 0);
	}
	
	public Size3D getSize() {		
		return new Size3D(getWidth(), getDepth(), getHeight());
	}
	
	public List<Hole> getHoles() {
		return new ArrayList<Hole>();
	}

	public float getWidth() {
		Vector3f d = new Vector3f(wall.positionX2 - wall.positionX1, wall.positionY2 - wall.positionY1, 0);
		return d.length();
	}

	//TODO: STUB!
	public float getHeight() {
		return 1f;
		//throw new UnsupportedOperationException();
	}

	public float getDepth() {
		return wall.getWide();
	}
}
