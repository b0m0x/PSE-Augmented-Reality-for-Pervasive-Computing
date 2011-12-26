package vision.model;

import java.util.ArrayList;
import java.util.List;

public class WallAdapter {
	private Wall wall;
	
	public WallAdapter(Wall wall) {
		this.wall = wall;
	}
	
	public Position getPosition() {
		throw new UnsupportedOperationException();
	}
	
	public Position getStart() {
		throw new UnsupportedOperationException();
	}
	
	public Position getEnd() {
		throw new UnsupportedOperationException();
	}
	
	public Size3D getSize() {
		throw new UnsupportedOperationException();
	}
	
	public List<Hole> getHoles() {
		return new ArrayList<Hole>();
	}

	public float getWidth() {
		throw new UnsupportedOperationException();
	}

	public float getHeight() {
		throw new UnsupportedOperationException();
	}

	public float getDepth() {
		throw new UnsupportedOperationException();
	}
}
