package vision.model;

import com.jme3.math.Vector3f;

public class HoleAdapter {
	private Hole hole;
	
	public HoleAdapter(Hole hole) {
		this.hole = hole;
	}

	/**
	 * return the position of the hole.
	 * 
	 * @return the position of the center of the hole. the position is relative to the lower left corner of the wall, in x, y coordinates, z is set to 0
	 */
	public Vector3f getPosition() {
		return new Vector3f((hole.getPositionX1() + hole.getPositionX2()) / 2, (hole.getPositionY1() + hole.getPositionY2()) / 2, 0);
	}

	/**
	 * returns the size of the hole
	 * @return the size of the hole. x and y are local coordinates, z is set to 0
	 */
	public Vector3f getSize() {
		return new Vector3f(hole.getPositionX2() - hole.getPositionX1(), hole.getPositionY2() - hole.getPositionY1(), 0);
	}

}
