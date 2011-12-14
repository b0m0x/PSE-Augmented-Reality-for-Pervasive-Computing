/*-- $Id: AbstractDocWriter.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Enumeration;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import org.apache.avalon.framework.logger.ConsoleLogger;

/**
 * Abstract doc writer.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */
public abstract class AbstractDocWriter implements DocWriter {

	protected static final String OUTPUT_HTML = "html";
	protected static final String OUTPUT_XML = "xml";

	protected InputStream xmlStream;
	protected InputStream xslStream;
	protected InputStream cssStream;
	protected String outputFile;
	protected Properties parameters;
	protected ConsoleLogger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);

	/**
	 * Sets the xml stream.
	 * @param xmlStream the xml stream.
	 */
	public void setXmlStream(InputStream xmlStream) {
		this.xmlStream = xmlStream;
	}

	/**
	 * Sets the xsl stream.
	 * @param xslStream the xsl stream.
	 */
	public void setXslStream(InputStream xslStream) {
		this.xslStream = xslStream;
	}

	/**
	 * Sets the css stream.
	 * @param cssStream the css stream.
	 */
	public void setCssStream(InputStream cssStream) {
		this.cssStream = cssStream;
	}

	/**
	 * Sets the output file.
	 * @param outputFile the output file.
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * Sets the parameters.
	 */
	public void setParameters(Properties params) {
		this.parameters = params;
	}

	public abstract void writeDoc() throws Exception;
	
	/**
	 * Takes the xml source and write the output to the given stream by 
	 * doing an xsl transformation.
	 *
	 * @param xmlStream the xml stream
	 * @param xslStream the xsl stream
	 * @param outputStream the output stream
	 * @param outputMethod the output method(text,html,xml)
	 * @param parameters additonal xsl parameters
	 * @throws Exception in case of an error.
	 */
	public void doTransform(InputStream xmlStream, InputStream xslStream,  
			OutputStream outputStream, String outputMethod, Properties parameters) 
			throws Exception {
		long l1 = System.currentTimeMillis();
		log.info("Applying XSL..");
		// Instantiate a TransformerFactory
		TransformerFactory tFactory = 
			TransformerFactory.newInstance();
		
		// Use the TransformerFactory to process the stylesheet Source and 
		// generate a Transformer.
		StreamSource xslSource = new StreamSource(xslStream);
		Transformer transformer = tFactory.newTransformer (xslSource);
		transformer.setOutputProperty(OutputKeys.METHOD, outputMethod);

		// set the transformer parameters which will be avilable to xsl
		setTransformerParameters(transformer);
		
		// Use the Transformer to transform an XML Source and send the
		// output to a Result object.
		StreamSource xmlSource = new StreamSource(xmlStream);
		StreamResult result = new StreamResult(outputStream);
		transformer.transform (xmlSource, result);
		long l2 = System.currentTimeMillis();
		log.info("XSL transformation done in " + (l2 - l1) + " msecs.");
	}

	/**
	 * Sets the transformer parameters.
	 */
	private void setTransformerParameters(Transformer transformer) {
		Enumeration e = parameters.propertyNames();
		String paramName;
		while (e.hasMoreElements()) {
			paramName = e.nextElement().toString();
			transformer.setParameter(paramName, 
				parameters.getProperty(paramName));
		}
	}
}
