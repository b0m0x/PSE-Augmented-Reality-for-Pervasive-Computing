/*-- $Id: Doclet.java,v 1.1 2003/12/24 12:41:39 kshaikh Exp $ --*/
package com.aurigalogic.doclet.core;

import com.sun.javadoc.*;

import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

import com.aurigalogic.doclet.writer.*;

/**
 * Doclet class for AurigaDoclet
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1 $ $Date: 2003/12/24 12:41:39 $
 */
public class Doclet extends com.sun.javadoc.Doclet {
	
	protected static final String NO_LINKS = "-nolinks";
	protected static final String NO_INDEX = "-noindex";
	protected static final String NO_TOC = "-notoc";
	protected static final String NO_NAVIGATION = "-nonavigation";
	protected static final String HEADER_TEXT = "-headertext";
	protected static final String HEADER_HEIGHT = "-headerheight";
	protected static final String FOOTER_TEXT = "-footertext";
	protected static final String FOOTER_HEIGHT = "-footerheight";
	protected static final String HEADER_FILE = "-headerfile";
	protected static final String FOOTER_FILE = "-footerfile";
	protected static final String COVER_TEXT = "-covertext";
	protected static final String COVER_FILE = "-coverfile";
	protected static final String CSS_FILE = "-cssfile";
	protected static final String XSL_FILE = "-xslfile";
	protected static final String FORMAT = "-format";
	protected static final String OUTPUT = "-out";
	protected static final String LEFT_MARGIN = "-leftmargin";
	protected static final String RIGHT_MARGIN = "-rightmargin";
	protected static final String TOP_MARGIN = "-topmargin";
	protected static final String BOTTOM_MARGIN = "-bottommargin";
	private static final String SUPPORTED_FORMATS = "fo,pdf,ps,pcl,svg";
		
	private String generateToc = "true";
	private String generateNavigation = "true";
	private String generateLinks = "true";
	private String generateIndex = "true";
	private String cssFile;
	private String xslFile;
	private String format = "pdf";
	private String output;
	private String headerHeight = "50";
	private String footerHeight = "20";
	private String leftMargin = "30";
	private String rightMargin = "30";
	private String topMargin = "10";
	private String bottomMargin = "10";
			
	private static final String DEFAULT_XSL = "/com/aurigalogic/doclet/resources/xml2fo.xsl";
	private static final String DEFAULT_CSS = "/com/aurigalogic/doclet/resources/default.css";
			
	
	/**
	 * Default constructor.
	 */
	public Doclet (String[][] options) {
		for (int i = 0; i < options.length; i++) {
			String[] option = options[i];
			String name = option[0];
			if (name.equals(NO_TOC)) {
				generateToc = "false";
			} else if (name.equals(NO_NAVIGATION)) {
				generateNavigation = "false";
			} else if (name.equals(NO_LINKS)) {
				generateLinks = "false";
			} else if (name.equals(NO_INDEX)) {
				generateIndex = "false";
			} else if (name.equals(CSS_FILE)) {
				cssFile = option[1];
			} else if (name.equals(XSL_FILE)) {
				xslFile = option[1];
			} else if (name.equals(FORMAT)) {
				format = option[1];
			} else if (name.equals(OUTPUT)) {
				output = option[1];
			} else if (name.equals(HEADER_HEIGHT)) {
				headerHeight = option[1];
			} else if (name.equals(FOOTER_HEIGHT)) {
				footerHeight = option[1];
			} else if (name.equals(LEFT_MARGIN)) {
				leftMargin = option[1];
			} else if (name.equals(RIGHT_MARGIN)) {
				rightMargin = option[1];
			} else if (name.equals(TOP_MARGIN)) {
				topMargin = option[1];
			} else if (name.equals(BOTTOM_MARGIN)) {
				bottomMargin = option[1];
			}
		}
	}
		
	/**
	 * Entry point for this doclet.
	 */
	public static boolean start(RootDoc rootDoc) {
		boolean success = false;
		try {
			Doclet doclet = new Doclet(rootDoc.options());
			doclet.generate(rootDoc);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Returns the option length for supported command line options.
	 */
	public static int optionLength(String option) {
		int optionLength = 0;
		if (option.equals(HEADER_TEXT)
				|| option.equals(FOOTER_TEXT)
				|| option.equals(HEADER_FILE)
				|| option.equals(FOOTER_FILE)
				|| option.equals(COVER_TEXT)
				|| option.equals(COVER_FILE)
				|| option.equals(CSS_FILE)
				|| option.equals(XSL_FILE)
				|| option.equals(FORMAT)
				|| option.equals(HEADER_HEIGHT)
				|| option.equals(FOOTER_HEIGHT)
				|| option.equals(LEFT_MARGIN)
				|| option.equals(RIGHT_MARGIN)
				|| option.equals(TOP_MARGIN)
				|| option.equals(BOTTOM_MARGIN)
				|| option.equals(OUTPUT)) {
			optionLength = 2;
		} else if (option.equals(NO_LINKS)
				|| option.equals(NO_TOC)
				|| option.equals(NO_NAVIGATION)
				|| option.equals(NO_INDEX)) {
			optionLength = 1;
		}
		return optionLength;
	}

	/** 
	 * Validates the options.
	 */
	public static boolean validOptions(String options[][], 
					DocErrorReporter reporter) {
		boolean formatSpecified = false;
		boolean formatValid = false;
		boolean outputValid = false;
		for (int i = 0; i < options.length; i++) {
			String[] option = options[i];
			if (option[0].equals(OUTPUT)) {
				outputValid = true;
			} else if (option[0].equals(FORMAT)) {
				formatSpecified = true;
				String format = option[1];
				if (format.equals("pdf")
					|| format.equals("ps")
					|| format.equals("pcl")
					|| format.equals("svg")
					|| format.equals("fo")) {
					formatValid = true;
				}
			}
		}
		if (!formatSpecified) {
			reporter.printError("Please specify the output format.");
		}
		if (formatSpecified && !formatValid) {
			reporter.printError("Invalid output format. "
					+ "Supported formats are: " + SUPPORTED_FORMATS);
		}
		if (!outputValid) {
			reporter.printError("Please specify the output file.");
		}
		if (!formatValid || !outputValid) {
			printOptions(reporter);
		}
		return (formatValid && outputValid && formatSpecified);
	}

	private static void printOptions(DocErrorReporter reporter) {
		String options = "AurigaDoclet Options:\n"
			+ FORMAT + "			The output format.\n"
			+ "			Supported values: " + SUPPORTED_FORMATS + ".\n"
			+ OUTPUT + "			Output file path.\n"
			+ NO_TOC + "			Do not generate TOC page.\n"
			+ NO_NAVIGATION + "		Do not generate navigation tree.\n"
			+ NO_LINKS	+ "		Do not use hyperlinks.\n"
			+ NO_INDEX + "		Do not generate a keyword index.\n"
			+ LEFT_MARGIN + "		Left margin in points. Default is 30.\n"
			+ RIGHT_MARGIN + "		Right margin in points. Default is 30.\n"
			+ TOP_MARGIN + "		Top margin in points. Default is 10.\n"
			+ BOTTOM_MARGIN + "		Bottom margin in points. Default is 10.\n"
			+ HEADER_TEXT + "		XHTML text to be used as page header.\n"
			+ HEADER_FILE + "		XHTML file to be used as page header.\n"
			+ FOOTER_TEXT + " 		XHTML text to be used as page footer.\n"
			+ FOOTER_FILE + " 		XHTML file to be used as page footer.\n"
			+ HEADER_HEIGHT + "		Height of page headers in points. Default is 50.\n"
			+ FOOTER_HEIGHT + "		Height of page footer in points. Default is 20\n"
			+ COVER_FILE + "		XHTML file to be used a cover page.\n"
			+ CSS_FILE + "		CSS file to used for formatting the output.\n"
			+ XSL_FILE + "		Custom xsl file to be used for formatting the output.\n";
		reporter.printNotice(options);
	}

	private void generate(RootDoc rootDoc) throws Exception {
		XMLGenerator generator = new XMLGenerator(rootDoc.options());
		generator.generateXML(rootDoc);
		InputStream xslStream = getXSLStream();
		InputStream xmlStream = generator.getXMLStream();
		InputStream cssStream = getCssStream();
		Properties params = getConverterParams();
		DocWriter docWriter = null;
		if (format.equals("fo")) {
			docWriter = new FODocWriter();
		} else if (format.equals("pdf")) {
			docWriter = new PDFDocWriter();
		} else if (format.equals("ps")) {
			docWriter = new PSDocWriter();
		} else if (format.equals("mif")) {
			//docWriter = new MIFDocWriter();
		} else if (format.equals("pcl")) {
			docWriter = new PCLDocWriter();
		} else if (format.equals("svg")) {
			docWriter = new SVGDocWriter();
		}
		if (docWriter != null) {
			docWriter.setXmlStream(xmlStream);
			docWriter.setXslStream(xslStream);
			docWriter.setCssStream(cssStream);
			docWriter.setOutputFile(output);
			docWriter.setParameters(params);
			docWriter.writeDoc();
		}
	}

	private InputStream getXSLStream() throws Exception {
		InputStream xslStream;
		if (xslFile != null) {
			xslStream = new FileInputStream(xslFile);
		} else {
			xslStream = getClass().getResourceAsStream(DEFAULT_XSL);	
		}
		return xslStream;
	}

	private InputStream getCssStream() throws Exception {
		InputStream cssStream;
		if (cssFile != null) {
			cssStream = new FileInputStream(cssFile);
		} else {
			cssStream = getClass().getResourceAsStream(DEFAULT_CSS);	
		}
		return cssStream;
	}

	private Properties getConverterParams() {
		Properties params = new Properties();
		params.setProperty("generate-toc", generateToc);
		params.setProperty("generate-navigation", generateNavigation);
		params.setProperty("generate-links", generateLinks);
		params.setProperty("generate-index", generateIndex);
		params.setProperty("header-height", headerHeight);
		params.setProperty("footer-height", footerHeight);
		params.setProperty("left-margin", leftMargin);
		params.setProperty("right-margin", rightMargin);
		params.setProperty("top-margin", topMargin);
		params.setProperty("bottom-margin", bottomMargin);
		return params;
	}
}
