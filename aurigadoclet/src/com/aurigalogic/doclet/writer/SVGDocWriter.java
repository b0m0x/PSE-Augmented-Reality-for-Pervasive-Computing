/*-- $Id: SVGDocWriter.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import org.apache.fop.apps.Driver;

/**
 * SVG document writer.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */
public class SVGDocWriter extends PDFDocWriter {

	/**
	 * Writes the javadoc output to the specified file.
	 *
	 * @throws Exception in case of an error.
	 */
	public void writeDoc() throws Exception {
		writeDocUsingFOP(Driver.RENDER_SVG);
	}
}
