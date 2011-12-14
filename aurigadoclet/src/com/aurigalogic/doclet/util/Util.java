/*-- $Id: Util.java,v 1.1.1.1 2003/12/24 12:38:12 kshaikh Exp $ --*/
package com.aurigalogic.doclet.util;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import org.w3c.tidy.Tidy;

import org.apache.oro.text.perl.Perl5Util;

/**
 * Utility class
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2003/12/24 12:38:12 $
 */
public class Util {

	private Util () {
		// hide constructor
	}
		
	/**
	 * Reads a file and returns the content as string.
	 */
	public static String readFile(String path) throws Exception {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer content = new StringBuffer();
		String line;
		while((line = br.readLine())!=null) {
			content = content.append(line);
			content = content.append("\n");
		}
		br.close();
		fr.close();
		return content.toString();
	}

	/**
	 * Writes the given given text to the specified file.
	 */
	public static void writeFile(String path, String content) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
		FileOutputStream os = new FileOutputStream(path);
		copyStream(is, os);
	}
	
	/**
	 * Cleans the given html using Tidy.
	 */
	public static String tidy(String html) {
		String safeHtml = html;
		try {
			Tidy tidy = new Tidy();	
			tidy.setXHTML(true);
			tidy.setNumEntities(true);
			tidy.setDocType("omit");
			tidy.setQuiet(true);
			tidy.setShowWarnings(false);
			ByteArrayInputStream is = new ByteArrayInputStream(html.getBytes()); 
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			tidy.parseDOM(is, os);
			if (tidy.getParseErrors() == 0) {
				safeHtml = new String(os.toByteArray());
				Perl5Util regex = new Perl5Util();
				safeHtml = regex.substitute("s/.*<body>//gs", safeHtml);
				safeHtml = regex.substitute("s/<\\/body>.*//gs", safeHtml);
			} else {
				System.out.println("The following html could not be cleaned.\n" + html);
			}
		} catch (Exception e) {
			safeHtml = html;
		}
		return safeHtml;
	}

	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[4096];
		int len;  
		while ((len = in.read(buffer)) != -1) {    
			out.write(buffer, 0, len);  
		}  
		out.flush();
		out.close();  
		in.close();
		in = null;
		out = null;
	}
}
