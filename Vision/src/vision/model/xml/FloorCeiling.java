package vision.model.xml;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element ref="{}benchmarks" maxOccurs="unbounded"/>
 *         &lt;element ref="{}ceilingHeight"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "benchmarks",
    "ceilingHeight"
})
@XmlRootElement(name = "floorCeiling")
public class FloorCeiling {

    @XmlElement(required = true)
    protected List<Benchmarks> benchmarks;
    protected float ceilingHeight;

    /**
     * Gets the value of the benchmarks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the benchmarks property.
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBenchmarks().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Benchmarks }

     */
    public List<Benchmarks> getBenchmarks() {
        if (benchmarks == null) {
            benchmarks = new ArrayList<Benchmarks>();
        }
        return this.benchmarks;
    }

    /**
     * Gets the value of the ceilingHeight property.
     * 
     */
    public float getCeilingHeight() {
        return ceilingHeight;
    }

    /**
     * Sets the value of the ceilingHeight property.
     * 
     */
    public void setCeilingHeight(float value) {
        this.ceilingHeight = value;
    }

}
