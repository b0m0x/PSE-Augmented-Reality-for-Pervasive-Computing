package vision.model;

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
 *         &lt;element ref="{}x"/>
 *         &lt;element ref="{}y"/>
 *         &lt;element ref="{}angle"/>
 *         &lt;element ref="{}path"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "x",
    "y",
    "angle",
    "path"
})
@XmlRootElement(name = "staticGeometry")
public class StaticGeometry {

    protected float x;
    protected float y;
    protected float angle;
    @XmlElement(required = true)
    private String path;

    /**
     * Gets the value of the x property.
     */
    public final float getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     */
    public final void setX(final float value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     */
    public final float getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     */
    public final void setY(float value) {
        this.y = value;
    }

    /**
     * Gets the value of the angle property.
     */
    public final float getAngle() {
        return angle;
    }

    /**
     * Sets the value of the angle property.
     */
    public final void setAngle(float value) {
        this.angle = value;
    }

    /**
     * Gets the value of the path property.
     * @return
     *     possible object is
     *     {@link String }
     */
    public final String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * @param value
     *     allowed object is
     *     {@link String }
     */
    public final void setPath(String value) {
        this.path = value;
    }

}