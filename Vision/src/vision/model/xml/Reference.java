package vision.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}x"/>
 *         &lt;element ref="{}y"/>
 *         &lt;element ref="{}lat"/>
 *         &lt;element ref="{}lon"/>
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
    "lat",
    "lon"
})
@XmlRootElement(name = "reference")
public class Reference {

    protected float x;
    protected float y;
    protected double lat;
    protected double lon;

    /**
     * Gets the value of the x property.
     */
    public final float getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     */
    public final void setX(float value) {
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
     * Gets the value of the lat property.
     */
    public final double getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     */
    public final void setLat(double value) {
        this.lat = value;
    }

    /**
     * Gets the value of the lon property.
     */
    public final double getLon() {
        return lon;
    }

    /**
     * Sets the value of the lon property.
     */
    public final void setLon(double value) {
        this.lon = value;
    }

}