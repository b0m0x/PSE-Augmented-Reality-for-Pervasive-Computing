package vision.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the test package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Height_QNAME = new QName("", "height");
	private final static QName _WallNumber_QNAME = new QName("", "wallNumber");
	private final static QName _HeightWindow_QNAME = new QName("",
			"heightWindow");
	private final static QName _HoleNumber_QNAME = new QName("", "holeNumber");
	private final static QName _Wide_QNAME = new QName("", "wide");
	private final static QName _PositionX2_QNAME = new QName("", "positionX2");
	private final static QName _PositionY1_QNAME = new QName("", "positionY1");
	private final static QName _PositionX1_QNAME = new QName("", "positionX1");
	private final static QName _HeightGround_QNAME = new QName("",
			"heightGround");
	private final static QName _PositionY2_QNAME = new QName("", "positionY2");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: test
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Groundplan }
	 * 
	 */
	public Groundplan createGroundplan() {
		return new Groundplan();
	}

	/**
	 * Create an instance of {@link Wall }
	 * 
	 */
	public Wall createWall() {
		return new Wall();
	}

	/**
	 * Create an instance of {@link Hole }
	 * 
	 */
	public Hole createHole() {
		return new Hole();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "height")
	public JAXBElement<Float> createHeight(Float value) {
		return new JAXBElement<Float>(_Height_QNAME, Float.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link int }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "wallNumber")
	public JAXBElement<Integer> createWallNumber(int value) {
		return new JAXBElement<Integer>(_WallNumber_QNAME, int.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "heightWindow")
	public JAXBElement<Float> createHeightWindow(Float value) {
		return new JAXBElement<Float>(_HeightWindow_QNAME, Float.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link int }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "holeNumber")
	public JAXBElement<Integer> createHoleNumber(int value) {
		return new JAXBElement<Integer>(_HoleNumber_QNAME, int.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "wide")
	public JAXBElement<Float> createWide(Float value) {
		return new JAXBElement<Float>(_Wide_QNAME, Float.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "positionX2")
	public JAXBElement<Float> createPositionX2(Float value) {
		return new JAXBElement<Float>(_PositionX2_QNAME, Float.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "positionY1")
	public JAXBElement<Float> createPositionY1(Float value) {
		return new JAXBElement<Float>(_PositionY1_QNAME, Float.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "positionX1")
	public JAXBElement<Float> createPositionX1(Float value) {
		return new JAXBElement<Float>(_PositionX1_QNAME, Float.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "heightGround")
	public JAXBElement<Float> createHeightGround(Float value) {
		return new JAXBElement<Float>(_HeightGround_QNAME, Float.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "positionY2")
	public JAXBElement<Float> createPositionY2(Float value) {
		return new JAXBElement<Float>(_PositionY2_QNAME, Float.class, null,
				value);
	}

}