package vision.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}wallNumber"/>
 *         &lt;element ref="{}positionX1"/>
 *         &lt;element ref="{}positionY1"/>
 *         &lt;element ref="{}positionX2"/>
 *         &lt;element ref="{}positionY2"/>
 *         &lt;element ref="{}height"/>
 *         &lt;element ref="{}wide"/>
 *         &lt;element ref="{}hole" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wallNumber", "positionX1", "positionY1",
		"positionX2", "positionY2", "height", "wide", "hole" })
@XmlRootElement(name = "wall")
public class Wall {

	@XmlElement(required = true)
	protected int wallNumber;
	protected float positionX1;
	protected float positionY1;
	protected float positionX2;
	protected float positionY2;
	protected float height;
	protected float wide;
	@XmlElement(required = true)
	protected List<Hole> hole;

	/**
	 * Gets the value of the wallNumber property.
	 * 
	 * @return possible object is {@link int }
	 * 
	 */
	public int getWallNumber() {
		return wallNumber;
	}

	/**
	 * Sets the value of the wallNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link int }
	 * 
	 */
	public void setWallNumber(int value) {
		this.wallNumber = value;
	}

	/**
	 * Gets the value of the positionX1 property.
	 * 
	 */
	public float getPositionX1() {
		return positionX1;
	}

	/**
	 * Sets the value of the positionX1 property.
	 * 
	 */
	public void setPositionX1(float value) {
		this.positionX1 = value;
	}

	/**
	 * Gets the value of the positionY1 property.
	 * 
	 */
	public float getPositionY1() {
		return positionY1;
	}

	/**
	 * Sets the value of the positionY1 property.
	 * 
	 */
	public void setPositionY1(float value) {
		this.positionY1 = value;
	}

	/**
	 * Gets the value of the positionX2 property.
	 * 
	 */
	public float getPositionX2() {
		return positionX2;
	}

	/**
	 * Sets the value of the positionX2 property.
	 * 
	 */
	public void setPositionX2(float value) {
		this.positionX2 = value;
	}

	/**
	 * Gets the value of the positionY2 property.
	 * 
	 */
	public float getPositionY2() {
		return positionY2;
	}

	/**
	 * Sets the value of the positionY2 property.
	 * 
	 */
	public void setPositionY2(float value) {
		this.positionY2 = value;
	}

	/**
	 * Gets the value of the height property.
	 * 
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the value of the height property.
	 * 
	 */
	public void setHeight(float value) {
		this.height = value;
	}

	/**
	 * Gets the value of the wide property.
	 * 
	 */
	public float getWide() {
		return wide;
	}

	/**
	 * Sets the value of the wide property.
	 * 
	 */
	public void setWide(float value) {
		this.wide = value;
	}

	/**
	 * Gets the value of the hole property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the hole property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getHole().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Hole }
	 * 
	 * 
	 */
	public List<Hole> getHole() {
		if (hole == null) {
			hole = new ArrayList<Hole>();
		}
		return this.hole;
	}

	public void setHole(List<Hole> holes) {
		hole = holes;
	}

}