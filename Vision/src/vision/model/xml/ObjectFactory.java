package vision.model.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the test package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Lon_QNAME = new QName("", "lon");
    private final static QName _WallNumber_QNAME = new QName("", "wallNumber");
    private final static QName _PositionY_QNAME = new QName("", "positionY");
    private final static QName _Number_QNAME = new QName("", "number");
    private final static QName _Wide_QNAME = new QName("", "wide");
    private final static QName _HoleNumber_QNAME = new QName("", "holeNumber");
    private final static QName _PositionY1_QNAME = new QName("", "positionY1");
    private final static QName _PositionX2_QNAME = new QName("", "positionX2");
    private final static QName _PositionX1_QNAME = new QName("", "positionX1");
    private final static QName _Angle_QNAME = new QName("", "angle");
    private final static QName _PositionY2_QNAME = new QName("", "positionY2");
    private final static QName _Height_QNAME = new QName("", "height");
    private final static QName _PositionX_QNAME = new QName("", "positionX");
    private final static QName _Path_QNAME = new QName("", "path");
    private final static QName _HeightWindow_QNAME = new QName("", "heightWindow");
    private final static QName _Z_QNAME = new QName("", "z");
    private final static QName _HeightGround_QNAME = new QName("", "heightGround");
    private final static QName _Y_QNAME = new QName("", "y");
    private final static QName _Lat_QNAME = new QName("", "lat");
    private final static QName _CeilingHeight_QNAME = new QName("", "ceilingHeight");
    private final static QName _X_QNAME = new QName("", "x");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: test
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
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link StaticGeometry }
     * 
     */
    public StaticGeometry createStaticGeometry() {
        return new StaticGeometry();
    }

    /**
     * Create an instance of {@link Light }
     * 
     */
    public Light createLight() {
        return new Light();
    }

    /**
     * Create an instance of {@link FloorCeiling }
     * 
     */
    public FloorCeiling createFloorCeiling() {
        return new FloorCeiling();
    }

    /**
     * Create an instance of {@link Benchmarks }
     * 
     */
    public Benchmarks createBenchmarks() {
        return new Benchmarks();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lon")
    public JAXBElement<Double> createLon(Double value) {
        return new JAXBElement<Double>(_Lon_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link int }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "wallNumber")
    public JAXBElement<Integer> createWallNumber(int value) {
        return new JAXBElement<Integer>(_WallNumber_QNAME, int.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionY")
    public JAXBElement<Float> createPositionY(Float value) {
        return new JAXBElement<Float>(_PositionY_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link int }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "number")
    public JAXBElement<Integer> createNumber(int value) {
        return new JAXBElement<Integer>(_Number_QNAME, int.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "wide")
    public JAXBElement<Float> createWide(Float value) {
        return new JAXBElement<Float>(_Wide_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link int }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "holeNumber")
    public JAXBElement<Integer> createHoleNumber(int value) {
        return new JAXBElement<Integer>(_HoleNumber_QNAME, int.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionY1")
    public JAXBElement<Float> createPositionY1(Float value) {
        return new JAXBElement<Float>(_PositionY1_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionX2")
    public JAXBElement<Float> createPositionX2(Float value) {
        return new JAXBElement<Float>(_PositionX2_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionX1")
    public JAXBElement<Float> createPositionX1(Float value) {
        return new JAXBElement<Float>(_PositionX1_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "angle")
    public JAXBElement<Float> createAngle(Float value) {
        return new JAXBElement<Float>(_Angle_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionY2")
    public JAXBElement<Float> createPositionY2(Float value) {
        return new JAXBElement<Float>(_PositionY2_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "height")
    public JAXBElement<Float> createHeight(Float value) {
        return new JAXBElement<Float>(_Height_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "positionX")
    public JAXBElement<Float> createPositionX(Float value) {
        return new JAXBElement<Float>(_PositionX_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "path")
    public JAXBElement<String> createPath(String value) {
        return new JAXBElement<String>(_Path_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "heightWindow")
    public JAXBElement<Float> createHeightWindow(Float value) {
        return new JAXBElement<Float>(_HeightWindow_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "z")
    public JAXBElement<Float> createZ(Float value) {
        return new JAXBElement<Float>(_Z_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "heightGround")
    public JAXBElement<Float> createHeightGround(Float value) {
        return new JAXBElement<Float>(_HeightGround_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "y")
    public JAXBElement<Float> createY(Float value) {
        return new JAXBElement<Float>(_Y_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lat")
    public JAXBElement<Double> createLat(Double value) {
        return new JAXBElement<Double>(_Lat_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ceilingHeight")
    public JAXBElement<Float> createCeilingHeight(Float value) {
        return new JAXBElement<Float>(_CeilingHeight_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "x")
    public JAXBElement<Float> createX(Float value) {
        return new JAXBElement<Float>(_X_QNAME, Float.class, null, value);
    }

}