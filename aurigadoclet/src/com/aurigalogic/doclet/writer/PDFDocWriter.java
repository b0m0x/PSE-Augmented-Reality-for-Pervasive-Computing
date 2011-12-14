/*-- $Id: PDFDocWriter.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.net.URL;

import org.apache.fop.apps.Driver;
import org.apache.fop.configuration.Configuration;

import org.xml.sax.InputSource;

import org.apache.avalon.framework.logger.ConsoleLogger;

/**
 * PDF document generator.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */
public class PDFDocWriter extends FODocWriter {

	/**
	 * Writes the javadoc output to the specified file.
	 *
	 * @throws Exception in case of an error.
	 */
	public void writeDoc() throws Exception {
		log.info("Creating PDF output ..");
		writeDocUsingFOP(Driver.RENDER_PDF);
	}

	/**
	 * Convert the input stream to output stream using the given 
	 * xsl stream and the renderer.
	 *
	 * @param renderer the fop renderer to use
	 */
	protected void writeDocUsingFOP(int renderer) throws Exception {
		ByteArrayOutputStream foStream = new ByteArrayOutputStream();

		// create fo stream
		createFO(xmlStream, foStream);

		String baseDir = getBaseDir(outputFile);
		FileOutputStream outStream = new FileOutputStream(outputFile);

		// serialize
		serialize(renderer, new ByteArrayInputStream(foStream.toByteArray()), 
				outStream, baseDir);
	}

	/**
	 * Serializes the fo stream using FOP.
	 */
	private synchronized void serialize(int renderer, InputStream inStream,
			OutputStream outStream, String baseDir) throws Exception {
		InputSource is = new InputSource(inStream);
		Driver driver = new Driver(new InputSource(inStream), outStream);
		ConsoleLogger cLogger = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
		driver.setLogger(cLogger);
		driver.setRenderer(renderer);
		Configuration.put("baseDir", baseDir);
		driver.run();
	}

	/** 
	 * Gets the base directory for fop. 
	 * The base directory is the url of the output file directory.
	 */
	private String getBaseDir(String outputFile) {
		try {
			File outFile = new File(outputFile);
			URL baseURL = outFile.getParentFile().toURL();
			return baseURL.toString();
		} catch (Exception e) {
			return "";
		}
	}

}
