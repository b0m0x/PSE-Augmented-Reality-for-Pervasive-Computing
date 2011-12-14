/*-- $Id: CssTreeBuilder.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.net.URL;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.dom.CSSStyleRuleImpl;

import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.css.sac.InputSource;

/**
 * This class parses the given css stream and builds a xml tree.
 * 
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */

public class CssTreeBuilder {
	
	private HashMap cssMap;

	private String output;

	private String cssTreeFileName;

	private String cssTreeFileUrl;

	/**
	 * Constructor.
	 * @param output the output document
	 * @throws Exception is an error occurs
	 */
	public CssTreeBuilder(InputStream cssStream, String output) throws Exception {
		this.output = output;
		this.cssTreeFileName = (new File(output)).getAbsolutePath() + ".css.xml";
		cssTreeFileUrl = (new File(cssTreeFileName)).toURL().toString();
		cssMap = new HashMap();
		buildCssMap(cssStream);
		writeCssTree();
	}

	/**
	 * Returns the path of the cssTree file
	 * @return the css tree file path
	 */
	public String getCssTreeFileUrl() {
		return cssTreeFileUrl;
	}
	
	private void buildCssMap(InputStream in) throws Exception {
		CSSOMParser parser = new CSSOMParser();
		InputSource is = new InputSource(new InputStreamReader(in));
		CSSStyleSheet styleSheet = parser.parseStyleSheet(is);
	
		CSSRuleList ruleList = styleSheet.getCssRules();
		int ruleLength = ruleList.getLength();
		for (int i = 0; i < ruleLength; i++) {
			CSSStyleRuleImpl rule = (CSSStyleRuleImpl) ruleList.item(i);
			CSSStyleDeclaration cssDecl = rule.getStyle();
			String cssText = cssDecl.getCssText();
			cssText = cssText.substring(1, cssText.length() - 1);
			String selector = rule.getSelectorText().toLowerCase();
			StringTokenizer tokenizer = new StringTokenizer(selector, ",");
			int attribCount = cssDecl.getLength();
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				HashMap attributeMap = new HashMap();
				if (cssMap.get(token) != null) {
					attributeMap = (HashMap) cssMap.get(token);
				}
				for (int j = 0; j < attribCount; j++) {
					String name = cssDecl.item(j);
					String value = cssDecl.getPropertyValue(name);
					attributeMap.put(name, value);
				}
				cssMap.put(token, attributeMap);
			}
		}
	}

	private void writeCssTree() throws Exception {
		StringBuffer cssTree = new StringBuffer("<css>");
		Set keySet = cssMap.keySet();
		Iterator iter = keySet.iterator();
		while (iter.hasNext()) {
			String selector = iter.next().toString();
			cssTree.append("<selector name=\"");
			cssTree.append(selector);
			cssTree.append("\">");
			HashMap attribs = (HashMap) cssMap.get(selector);
			Set arrtibNames = attribs.keySet();
			Iterator iter2 = arrtibNames.iterator();
			while (iter2.hasNext()) {
				String name = iter2.next().toString();
				String value = attribs.get(name).toString();
				cssTree.append("<attrib name=\"");
				cssTree.append(name);
				cssTree.append("\" value=\"");
				cssTree.append(value);
				cssTree.append("\" />");
			}
			cssTree.append("</selector>");
		}
		cssTree.append("</css>");


		// write the string to the cssTreeFileName
		FileWriter fw = new FileWriter(cssTreeFileName);
		PrintWriter pw = new PrintWriter(fw, true);
		pw.println(cssTree.toString());
		pw.close();
		fw.close();
	}

}
