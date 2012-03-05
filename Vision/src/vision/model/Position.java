package vision.model;

/**
 * 3-dimensional vector
 */
public class Position {

	/**
	 * @uml.property name="x"
	 */
	private float x;

	public Position(float f, float g, float h) {
		x = f;
		y = g;
		z = h;
	}

	/**
	 * Getter of the property <tt>x</tt>
	 * 
	 * @return Returns the x.
	 * @uml.property name="x"
	 */
	public float getX() {
		return x;
	}

	/**
	 * Setter of the property <tt>x</tt>
	 * 
	 * @param x
	 *            The x to set.
	 * @uml.property name="x"
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @uml.property name="y"
	 */
	private float y;

	/**
	 * Getter of the property <tt>y</tt>
	 * 
	 * @return Returns the y.
	 * @uml.property name="y"
	 */
	public float getY() {
		return y;
	}

	/**
	 * Setter of the property <tt>y</tt>
	 * 
	 * @param y
	 *            The y to set.
	 * @uml.property name="y"
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @uml.property name="z"
	 */
	private float z;

	/**
	 * Getter of the property <tt>z</tt>
	 * 
	 * @return Returns the z.
	 * @uml.property name="z"
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Setter of the property <tt>z</tt>
	 * 
	 * @param z
	 *            The z to set.
	 * @uml.property name="z"
	 */
	public void setZ(float z) {
		this.z = z;
	}

	
	public String toString() {
		String s = "Position X: " + this.x + "\nPosition Y: " + this.y
				+ "\nPosition Z: " + this.z;
		return s;
	}

}
