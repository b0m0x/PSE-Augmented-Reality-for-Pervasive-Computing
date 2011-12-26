package vision.model;

import java.util.ArrayList;

public class WallAdapter {
	private Wall wall;
	
	public WallAdapter(Wall wall) {
		this.wall = wall;
	}
	
	public Position getPosition() {
		//TODO: stub
		return null;
	}
	
	public Position getStart() {
		throw new UnsupportedOperationException();
	}
	
	public Position getEnd() {
		throw new UnsupportedOperationException();
	}
	
	public Size3D getSize() {
		return null;
	}
	
	public List<Hole> getHoles() {
		return new ArrayList<Hole>();
	}
}
