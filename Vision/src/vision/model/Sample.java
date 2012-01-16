package vision.model;



/**
 * holds a sensor measurement and the time it was taken
 * 
 */
public class Sample {

	/**
	 * @uml.property name="typ"
	 */
	private String typ;

	/**
	 * Getter of the property <tt>typ</tt>
	 * 
	 * @return Returns the typ.
	 * @uml.property name="typ"
	 */
	public String getTyp() {
		return typ;
	}

	/**
	 * Setter of the property <tt>typ</tt>
	 * 
	 * @param typ
	 *            The typ to set.
	 * @uml.property name="typ"
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * @uml.property name="unit"
	 */
	private String unit;

	/**
	 * Getter of the property <tt>unit</tt>
	 * 
	 * @return Returns the unit.
	 * @uml.property name="unit"
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Setter of the property <tt>unit</tt>
	 * 
	 * @param unit
	 *            The unit to set.
	 * @uml.property name="unit"
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @uml.property name="value"
	 */
	private float value;

	/**
	 * Getter of the property <tt>value</tt>
	 * 
	 * @return Returns the value.
	 * @uml.property name="value"
	 */
	public float getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * 
	 * @param value
	 *            The value to set.
	 * @uml.property name="value"
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * @uml.property name="update"
	 */
	private long update;

	/**
	 * Getter of the property <tt>update</tt>
	 * 
	 * @return Returns the update.
	 * @uml.property name="update"
	 */
	public long getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * 
	 * @param l
	 *            The update to set.
	 * @uml.property name="update"
	 */
	public void setUpdate(long l) {
		this.update = l;
	}

	public Sample(String typ, String unit, float value, long update) {
		this.typ = typ;
		this.unit = unit;
		this.value = value;
		this.update = update;
	}
	
<<<<<<< HEAD

=======
	
>>>>>>> 2fcfa292b90a7fb2fa48ed3ef3a69c974be8bbe1
}
