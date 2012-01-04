package vision.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
 *         &lt;element ref="{}hole"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wallNumber",
    "positionX1",
    "positionY1",
    "positionX2",
    "positionY2",
    "height",
    "wide",
    "hole"
})
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
    protected Hole hole;

    /**
     * Gets the value of the wallNumber property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getWallNumber() {
        return wallNumber;
    }

    /**
     * Sets the value of the wallNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
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
     * @return
     *     possible object is
     *     {@link Hole }
     *     
     */
    public Hole getHole() {
        return hole;
    }

    /**
     * Sets the value of the hole property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hole }
     *     
     */
    public void setHole(Hole value) {
        this.hole = value;
    }

}