package vision;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;


/**
 * <p>Java class for anonymous complex type. <p>The following schema fragment specifies the expected content contained within this class. <pre> &lt;complexType> &lt;complexContent> &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"> &lt;sequence> &lt;element ref="{}raum" maxOccurs="unbounded" minOccurs="0"/> &lt;/sequence> &lt;/restriction> &lt;/complexContent> &lt;/complexType> </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "raeume")
public class Groundplan {

    /**
	 * @uml.property  name="content"
	 */
    @XmlElementRef(name = "raum", type = Wall.class)
    @XmlMixed
    protected List<Object> content;

    /**
	 * Gets the value of the content property. <p> This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the content property. <p> For example, to add a new item, do as follows: <pre> getContent().add(newItem); </pre> <p> Objects of the following type(s) are allowed in the list {@link Raum  } {@link String  } 
	 * @uml.property  name="content"
	 */
    public List<Object> getContent() {
		if (content == null) {
			content = new ArrayList<Object>();
		}
		return this.content;
	}

	/**
	 * @uml.property  name="wall"
	 * @uml.associationEnd  multiplicity="(1 -1)" inverse="groundplan:vision.Wall"
	 */
	private Collection<Wall> wall;

	/**
	 * Getter of the property <tt>wall</tt>
	 * @return  Returns the wall.
	 * @uml.property  name="wall"
	 */
	public Collection<Wall> getWall() {
		return wall;
	}

	/**
	 * Setter of the property <tt>wall</tt>
	 * @param wall  The wall to set.
	 * @uml.property  name="wall"
	 */
	public void setWall(Collection<Wall> wall) {
		this.wall = wall;
	}

}

