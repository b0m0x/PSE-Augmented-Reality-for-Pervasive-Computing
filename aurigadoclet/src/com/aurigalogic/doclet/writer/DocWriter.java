/*-- $Id: DocWriter.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Enumeration;

/**
 * DocWriter interface.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */
public interface DocWriter {

	/**
	 * Sets the xml stream.
	 * @param xmlStream the xml stream.
	 */
	public void setXmlStream(InputStream xmlStream);

	/**
	 * Sets the xsl stream.
	 * @param xslStream the xsl stream.
	 */
	public void setXslStream(InputStream xslStream);

	/**
	 * Sets the css stream.
	 * @param cssStream the css stream.
	 */
	public void setCssStream(InputStream cssStream);

	/**
	 * Sets the output file.
	 * @param outputFile the output file.
	 */
	public void setOutputFile(String outputFile);

	/**
	 * Sets the parameters.
	 */
	public void setParameters(Properties params);
	
	/**
	 * Write the output document.
	 */
	public void writeDoc() throws Exception;
}
