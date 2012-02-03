package vision.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
    "positionX",
    "positionY"
})
@XmlRootElement(name = "benchmarks")
public class Benchmarks {

    protected float positionX;
    protected float positionY;

    /**
     * Gets the value of the positionX property.
     * 
     */
    public final float getPositionX() {
        return positionX;
    }

    /**
     * Sets the value of the positionX property.
     * 
     */
    public final void setPositionX(float value) {
        this.positionX = value;
    }

    /**
     * Gets the value of the positionY property.
     * 
     */
    public final float getPositionY() {
        return positionY;
    }

    /**
     * Sets the value of the positionY property.
     * 
     */
    public final void setPositionY(float value) {
        this.positionY = value;
    }

}