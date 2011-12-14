/*-- $Id: FODocWriter.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import java.io.*;

/**
 * This class is used for generating javadoc in FO format.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */

public class FODocWriter extends AbstractDocWriter {

	/**
	 * Converts the given xml stream to fo and writes it to the output file.
	 * @throws Exception in case of an error.
	 */
	public void writeDoc() throws Exception {
		FileOutputStream fos = new FileOutputStream(outputFile);
		createFO(xmlStream, fos);
		fos.close();
	}

	/**
	 * Generates the fo document
	 * @param in the xml stream
	 * @param out the fo output stream
	 * @throws Exception in case of an error.
	 */
	protected void createFO(InputStream xmlStream, OutputStream outputStream) 
			throws Exception {
		log.info("Creating FO stream..");
		String cssTreeFile = null;
		try {
			if (cssStream != null) {
				CssTreeBuilder cssBuilder = new CssTreeBuilder(cssStream, outputFile);
				cssTreeFile = cssBuilder.getCssTreeFileUrl();
				parameters.setProperty("css-file", cssTreeFile);
			}
			doTransform(xmlStream, xslStream, outputStream, OUTPUT_XML, parameters);
		} catch (Exception e) {
			throw e;
		} finally {
			if (cssTreeFile != null) {
				try {
					(new File(cssTreeFile.substring(5, cssTreeFile.length()))).delete();
				} catch (Exception e2) {
					log.warn("Could not delete temporay file " + cssTreeFile, e2);
				}
			}
		}
		log.info("Created FO stream..");
	}
}
