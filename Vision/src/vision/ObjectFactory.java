package vision;

import java.lang.Float;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import java.util.Collection;



@XmlRegistry
public class ObjectFactory {

    private final static QName _PositionY1_QNAME = new QName("", "position_y1");
    private final static QName _PositionY2_QNAME = new QName("", "position_y2");
    private final static QName _PositionX1_QNAME = new QName("", "position_x1");
    private final static QName _Nummer_QNAME = new QName("", "nummer");
    private final static QName _PositionX2_QNAME = new QName("", "position_x2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: layout
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Raum }
     * 
     */
    public Wall createWall() {
        return new Wall();
    }

    /**
     * Create an instance of {@link Raeume }
     * 
     */
    public Groundplan createGroundplan() {
        return new Groundplan();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "position_y1")
    public JAXBElement<Float> createPositionY1(Float value) {
        return new JAXBElement<Float>(_PositionY1_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "position_y2")
    public JAXBElement<Float> createPositionY2(Float value) {
        return new JAXBElement<Float>(_PositionY2_QNAME, Float.class, null, value);
    }

    	
		/** 
		 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
		 */
		public JAXBElement<Float> createPositionX1(Float value){
		
		        return new JAXBElement<Float>(_PositionX1_QNAME, Float.class, null, value);
		     }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nummer")
    public JAXBElement<Float> createNummer(Float value) {
        return new JAXBElement<Float>(_Nummer_QNAME, Float.class, null, value);
    }

    	
		/** 
		 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
		 */
		public JAXBElement<Float> createPositionX2(Float value){
		
		        return new JAXBElement<Float>(_PositionX2_QNAME, Float.class, null, value);
		     }


		/**
		 * @uml.property  name="wall"
		 * @uml.associationEnd  multiplicity="(0 -1)" inverse="objectFactory:vision.Wall"
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


		/**
		 * @uml.property  name="groundplan"
		 * @uml.associationEnd  multiplicity="(0 -1)" inverse="objectFactory:vision.Groundplan"
		 */
		private Collection<Groundplan> groundplan;

		/**
		 * Getter of the property <tt>groundplan</tt>
		 * @return  Returns the groundplan.
		 * @uml.property  name="groundplan"
		 */
		public Collection<Groundplan> getGroundplan() {
			return groundplan;
		}

		/**
		 * Setter of the property <tt>groundplan</tt>
		 * @param groundplan  The groundplan to set.
		 * @uml.property  name="groundplan"
		 */
		public void setGroundplan(Collection<Groundplan> groundplan) {
			this.groundplan = groundplan;
		}

}