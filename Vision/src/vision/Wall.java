package vision;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type. <p>The following schema fragment specifies the expected content contained within this class. <pre> &lt;complexType> &lt;complexContent> &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"> &lt;sequence> &lt;element ref="{}raeume"/> &lt;element ref="{}nummer"/> &lt;element ref="{}position_x1"/> &lt;element ref="{}position_y1"/> &lt;element ref="{}position_x2"/> &lt;element ref="{}position_y2"/> &lt;/sequence> &lt;/restriction> &lt;/complexContent> &lt;/complexType> </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "raeume",
    "nummer",
    "positionX1",
    "positionY1",
    "positionX2",
    "positionY2"
})
@XmlRootElement(name = "raum")
public class Wall {

    /**
	 * @uml.property  name="nummer"
	 */
    @XmlElement(required = true)
    protected BigInteger nummer;
    /** 
	 * @uml.property name="positionX1"
	 */
    @XmlElement(name = "position_x1", required = true)
	protected float positionX1;
    /** 
	 * @uml.property name="positionY1"
	 */
    @XmlElement(name = "position_y1", required = true)
	protected float positionY1;
    /** 
	 * @uml.property name="positionX2"
	 */
    @XmlElement(name = "position_x2", required = true)
	protected float positionX2;
    /** 
	 * @uml.property name="positionY2"
	 */
    @XmlElement(name = "position_y2", required = true)
	protected float positionY2;


    /**
	 * Gets the value of the nummer property.
	 * @return  possible object is {@link BigInteger  }  
	 * @uml.property  name="nummer"
	 */
    public BigInteger getNummer() {
		return nummer;
	}

    /**
	 * Sets the value of the nummer property.
	 * @param value  allowed object is {@link BigInteger  }  
	 * @uml.property  name="nummer"
	 */
    public void setNummer(BigInteger value) {
		this.nummer = value;
	}

    /** 
	 * Gets the value of the positionX1 property.
	 * @return  possible object is {@link BigInteger  } 
	 * @uml.property  name="positionX1"
	 */
    public float getPositionX1() {
		return positionX1;
	}

    /** 
	 * Gets the value of the positionY1 property.
	 * @return  possible object is {@link BigInteger  } 
	 * @uml.property  name="positionY1"
	 */
    public float getPositionY1() {
		return positionY1;
	}

    /** 
	 * Gets the value of the positionX2 property.
	 * @return  possible object is {@link BigInteger  } 
	 * @uml.property  name="positionX2"
	 */
    public float getPositionX2() {
		return positionX2;
	}

    /** 
	 * Gets the value of the positionY2 property.
	 * @return  possible object is {@link BigInteger  } 
	 * @uml.property  name="positionY2"
	 */
    public float getPositionY2() {
		return positionY2;
	}

    /** 
	 * Sets the value of the positionX2 property.
	 * @param value  allowed object is {@link BigInteger  } 
	 * @uml.property  name="positionX2"
	 */
	public void setPositionX2(float positionX2) {
		this.positionX2 = positionX2;
	}

	/** 
	 * Sets the value of the positionY1 property.
	 * @param value  allowed object is {@link BigInteger  } 
	 * @uml.property  name="positionY1"
	 */
	public void setPositionY1(float positionY1) {
		this.positionY1 = positionY1;
	}

	/** 
	 * Sets the value of the positionX1 property.
	 * @param value  allowed object is {@link BigInteger  } 
	 * @uml.property  name="positionX1"
	 */
	public void setPositionX1(float positionX1) {
		this.positionX1 = positionX1;
	}

	/** 
	 * Sets the value of the positionY2 property.
	 * @param value  allowed object is {@link BigInteger  } 
	 * @uml.property  name="positionY2"
	 */
	public void setPositionY2(float positionY2) {
		this.positionY2 = positionY2;
	}
}
