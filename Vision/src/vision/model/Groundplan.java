package vision.model;

import java.io.File;
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
 *         &lt;element ref="{}wall" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wall" })
@XmlRootElement(name = "groundplan")
public class Groundplan {

	/**
	 * @throws MalformedURLException
	 */
	public Groundplan load() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("vision.model");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return (Groundplan) unmarshaller.unmarshal(new File(
				Config.groundplanPath));

	}

	@XmlElement(required = true)
	protected List<Wall> wall;

	/**
	 * Gets the value of the wall property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the wall property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getWall().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Wall }
	 * 
	 * 
	 */
	public List<Wall> getWall() {
		if (wall == null) {
			wall = new ArrayList<Wall>();
		}
		return this.wall;
	}

}
