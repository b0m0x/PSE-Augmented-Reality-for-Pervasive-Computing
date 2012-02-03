package vision.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}number"/>
 *         &lt;element ref="{}positionX"/>
 *         &lt;element ref="{}positionY"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "number",
    "positionX",
    "positionY"
})
@XmlRootElement(name = "light")
public class Light {

    @XmlElement(required = true)
    private int number;
    protected float positionX;
    protected float positionY;

    /**
     * Gets the value of the number property.
     * @return
     *     possible object is
     *     {@link int }
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * @param value
     *     allowed object is
     *     {@link int }
     */
    public final void setNumber(int value) {
        this.number = value;
    }

    /**
     * Gets the value of the positionX property.
     */
    public final float getPositionX() {
        return positionX;
    }

    /**
     * Sets the value of the positionX property.
     */
    public void setPositionX(final float value) {
        this.positionX = value;
    }

    /**
     * Gets the value of the positionY property.
     */
    public final float getPositionY() {
        return positionY;
    }

    /**
     * Sets the value of the positionY property.
     */
    public final void setPositionY(final float value) {
        this.positionY = value;
    }

}