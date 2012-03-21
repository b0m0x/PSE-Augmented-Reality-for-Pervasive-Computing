package vision.model.xml;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import vision.Config;


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
 *         &lt;element ref="{}wall" maxOccurs="unbounded"/>
 *         &lt;element ref="{}reference" maxOccurs="unbounded"/>
 *         &lt;element ref="{}staticGeometry" maxOccurs="unbounded"/>
 *         &lt;element ref="{}light" maxOccurs="unbounded"/>
 *         &lt;element ref="{}floorCeiling" maxOccurs="unbounded"/>
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
    "wall",
    "reference",
    "staticGeometry",
    "light",
    "floorCeiling"
})
@XmlRootElement(name = "groundplan")
public class Groundplan {

	/**
	 * @throws MalformedURLException
	 * @return
	 */
	public Groundplan load() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("vision.model.xml");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return (Groundplan) unmarshaller.unmarshal(new File(
				Config.GROUNDPLAN_PATH));

	}

	@XmlElement(required = true)
    protected List<Wall> wall;
    @XmlElement(required = true)
    protected List<Reference> reference;
    @XmlElement(required = true)
    protected List<StaticGeometry> staticGeometry;
    @XmlElement(required = true)
    protected List<Light> light;
    @XmlElement(required = true)
    protected List<FloorCeiling> floorCeiling;

    /**
     * Gets the value of the wall property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wall property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWall().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wall }
     * 
     * 
     */
    public List<Wall> getWalls() {
        if (wall == null) {
            wall = new ArrayList<Wall>();
        }
        return this.wall;
    }

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reference }
     * 
     * 
     */
    public List<Reference> getReferencePoints() {
        if (reference == null) {
            reference = new ArrayList<Reference>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the staticGeometry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the staticGeometry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStaticGeometry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StaticGeometry }
     * 
     * 
     */
    public List<StaticGeometry> getStaticGeometries() {
        if (staticGeometry == null) {
            staticGeometry = new ArrayList<StaticGeometry>();
        }
        return this.staticGeometry;
    }

    /**
     * Gets the value of the light property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the light property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Light }
     * 
     * 
     */
    public List<Light> getLights() {
        if (light == null) {
            light = new ArrayList<Light>();
        }
        return this.light;
    }

    /**
     * Gets the value of the floorCeiling property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the floorCeiling property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFloorCeiling().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FloorCeiling }
     * 
     * 
     */
    public List<FloorCeiling> getFloorAndCeilings() {
        if (floorCeiling == null) {
            floorCeiling = new ArrayList<FloorCeiling>();
        }
        return this.floorCeiling;
    }
    
    /**
     * Sets the value of the floorCeiling property.
     * 
     * @param value
     *     allowed object is
     *     {@link FloorCeiling }
     *     
     */
    public final void setFloorCeiling(List<FloorCeiling> value) {
        this.floorCeiling = value;
    }

}