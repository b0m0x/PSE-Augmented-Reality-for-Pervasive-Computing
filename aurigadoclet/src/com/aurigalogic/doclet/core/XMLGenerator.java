/*-- $Id: XMLGenerator.java,v 1.1 2003/12/24 12:41:39 kshaikh Exp $ --*/
package com.aurigalogic.doclet.core;

import com.sun.javadoc.*;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;

import com.aurigalogic.doclet.util.Util;

/**
 * Class which generates an xml tree from the RootDoc.
 * This xml is then transformed to various formats.
 *
 * @author <a href="mailto:kshaikh@aurigalogic.com">Khurshidali Shaikh</a>
 * @version $Revision: 1.1 $ $Date: 2003/12/24 12:41:39 $
 */
public class XMLGenerator {
	
	private StringBuffer xmlTree;
	private String headerText;
	private String headerFile;
	private String footerText;
	private String footerFile;
	private String coverText;
	private String coverFile;
	
		
	/**
	 * Default constructor.
	 */
	public XMLGenerator (String[][] options) {
		xmlTree = new StringBuffer();
		for (int i = 0; i < options.length; i++) {
			String[] option = options[i];
			String name = option[0];
			if (name.equals(Doclet.HEADER_TEXT)) {
				headerText = option[1];
			} else if (name.equals(Doclet.HEADER_FILE)) {
				headerFile = option[1];
			} else if (name.equals(Doclet.FOOTER_TEXT)) {
				footerText = option[1];
			} else if (name.equals(Doclet.FOOTER_FILE)) {
				footerFile = option[1];
			} else if (name.equals(Doclet.FOOTER_FILE)) {
				footerFile = option[1];
			} else if (name.equals(Doclet.COVER_TEXT)) {
				coverText = option[1];
			} else if (name.equals(Doclet.COVER_FILE)) {
				coverFile = option[1];
			}
		}
	}

	/**
	 * Generates the xml tree.
	 * @param rootDoc the javadoc root.
	 */
	public void generateXML(RootDoc rootDoc) throws Exception {
		startDoc();
		if (rootDoc.specifiedClasses() != null) {
			buildClassList(rootDoc.specifiedClasses());
		}
		
		if (rootDoc.specifiedPackages() != null) {
			buildPackageList(rootDoc);
		}
		endDoc();
	}
		
	public String getXMLString() {
		return xmlTree.toString();
	}
	
	public InputStream getXMLStream() {
		return new ByteArrayInputStream(xmlTree.toString().getBytes());
	}
	
	private void startDoc() throws Exception {
		xmlTree.append("<javadoc>");
		buildHeader();
		buildFooter();
		buildCover();
	}

	private void endDoc() {
		xmlTree.append("</javadoc>");
	}

	private void buildPackageList(RootDoc rootDoc) {
		PackageDoc[] packages = rootDoc.specifiedPackages(); 
		Object[] sorted = sortList(packages);
		for (int i = 0; i < packages.length; i++) {
			PackageDoc packageDoc = (PackageDoc) sorted[i];
			buildPackageNode(packageDoc);
			buildClassList(packageDoc.allClasses());
			buildPackageNodeEnd();
		}
	}

	private void buildClassList(ClassDoc[] classes) {
		Object[] sortedClasses = sortList(classes);
		for (int j = 0; j < classes.length; j++) {
			ClassDoc classDoc = (ClassDoc) sortedClasses[j];
			buildClasNode(classDoc);
		}
	}

	private void buildPackageNode(PackageDoc packageDoc) {
		xmlTree.append("<package name=\"");
		xmlTree.append(packageDoc);
		xmlTree.append("\"><comment>");
		xmlTree.append(makeSafe(packageDoc.commentText()));
		xmlTree.append("</comment>");
		Tag[] tags = packageDoc.tags();
		buildTagList(tags);
	}

	private void buildTagList(Tag[] tags) {
		for (int  i = 0 ; i < tags.length; i++) {
			Tag tag = tags[i];
			xmlTree.append("<tag kind=\"");
			xmlTree.append(tag.kind());
			xmlTree.append("\" name=\"");
			xmlTree.append(tag.name());
			xmlTree.append("\"><text>");
			xmlTree.append(makeSafe(tag.text()));
			xmlTree.append("</text></tag>");
		}
	}
	
	private void buildPackageNodeEnd() {
		xmlTree.append("</package>");
	}

	private void buildClasNode(ClassDoc classDoc) {
		String classTag = "<class " 
			+ "name=\"" + classDoc.name() + "\" "
			+ "qualified-name=\"" + classDoc.qualifiedTypeName() + "\" "
			+ "interface=\"" + classDoc.isInterface() + "\" "
			+ getProgramElementAttributes(classDoc);
		if (classDoc.superclass() != null) {
			classTag += " super-class=\"" + classDoc.superclass() + "\" ";
		}
		classTag +=  ">";
		xmlTree.append(classTag);
		xmlTree.append("<summary>");
		xmlTree.append(getFirstSentenceTags(classDoc.firstSentenceTags()));
		xmlTree.append("</summary>");
		xmlTree.append("<comment>");
		xmlTree.append(makeSafe(classDoc.commentText()));
		xmlTree.append("</comment>");
			
		// build class hierarchy
		buildClassHierarchy(classDoc);
		
		ConstructorDoc[] constructors = classDoc.constructors();
		buildConstructorList(constructors);

		buildInterfaceList(classDoc);

		Tag[] tags = classDoc.tags();
		buildTagList(tags);

		FieldDoc[] fields = classDoc.fields();
		buildFieldList(fields);

		MethodDoc[] methods = classDoc.methods();
		buildMethodList(methods);

		xmlTree.append("</class>");
	}

	private String getFirstSentenceTags(Tag[] tags) {
		String sentence = "";
		for (int i = 0; i < tags.length; i++) {
			Tag tag = tags[i];
			sentence += tag.text() + " ";
		}
		return makeSafe(sentence);
	}

	private void buildFieldList(FieldDoc[] fields) {
		Object[] sorted = sortList(fields);
		for (int i = 0; i < fields.length; i++) {
			FieldDoc fieldDoc = (FieldDoc) sorted[i];
			xmlTree.append("<field name=\"");
			xmlTree.append(fieldDoc.name());
			xmlTree.append("\" type=\"");
			xmlTree.append(fieldDoc.type());
			xmlTree.append("\" transient=\"");
			xmlTree.append(fieldDoc.isTransient());
			xmlTree.append("\" volatile=\"");
			xmlTree.append(fieldDoc.isVolatile());
			xmlTree.append("\" ");
			xmlTree.append(getProgramElementAttributes(fieldDoc));
			xmlTree.append("><comment>");
			xmlTree.append(makeSafe(fieldDoc.commentText()));
			xmlTree.append("</comment></field>");
		}
	}
	
	private void buildConstructorList(ConstructorDoc[] constructors) {
		Object[] sorted = sortList(constructors);
		for (int i = 0; i < constructors.length; i++) {
			ConstructorDoc constructorDoc = (ConstructorDoc) sorted[i];
			if (constructorDoc == null) {
				continue;
			}
			xmlTree.append("<constructor name=\"");
			xmlTree.append(constructorDoc.name());
			xmlTree.append("\" signature=\"");
			xmlTree.append(constructorDoc.signature());
			xmlTree.append("\" ");
			xmlTree.append(getProgramElementAttributes(constructorDoc)); 
			xmlTree.append(">");
			buildMethodDetails(constructorDoc);	
			xmlTree.append("</constructor>");
		}
	}
	
	private void buildMethodList(MethodDoc[] methods) {
		Object[] sorted = sortList(methods);
		for (int i = 0; i < methods.length; i++) {
			MethodDoc methodDoc = (MethodDoc) sorted[i];
			if (methodDoc == null) {
				continue;
			}
			String method = "<method name=\"" + methodDoc.name() + "\" "
				+ " native=\"" + methodDoc.isNative() + "\" "
				+ " synchronized=\"" + methodDoc.isSynchronized() + "\" "
				+ " abstract=\"" + methodDoc.isAbstract() + "\" "
				+ " signature=\"" + methodDoc.signature() + "\" "
				+ " return-type=\"" + methodDoc.returnType() + "\" "
				+ " interface=\"" + methodDoc.isInterface() + "\" "
				+ getProgramElementAttributes(methodDoc);
			if (methodDoc.overriddenClass() != null) {
				method += " overridden-class=\"" 
					+ methodDoc.overriddenClass() + "\"";
			}
			method += ">";
			xmlTree.append(method);
			buildMethodDetails(methodDoc);	
			xmlTree.append("</method>");
		}
	}

	private void buildMethodDetails(ExecutableMemberDoc methodDoc) {
		xmlTree.append("<comment>");
		xmlTree.append(makeSafe(methodDoc.commentText()));
		xmlTree.append("</comment>");
		Tag[] tags = methodDoc.tags();
		buildTagList(tags);

		Parameter[] parameters = methodDoc.parameters();
		buildParameterList(parameters);
		
		ParamTag[] paramTags = methodDoc.paramTags();
		buildParamTagList(paramTags);

		ThrowsTag[] throwsTags = methodDoc.throwsTags();
		buildThrowsTagList(throwsTags);
	}
	
	private void buildParameterList(Parameter[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			xmlTree.append("<parameter name=\"");
			xmlTree.append(parameter.name());
			xmlTree.append("\" type=\"");
			xmlTree.append(parameter.type());
			xmlTree.append("\" />");
		}
	}
	
	private void buildParamTagList(ParamTag[] paramTags) {
		for (int i = 0; i < paramTags.length; i++) {
			ParamTag paramTag = paramTags[i];
			xmlTree.append("<param name=\"");
			xmlTree.append(paramTag.parameterName());
			xmlTree.append("\"><comment>");
			xmlTree.append(makeSafe(paramTag.parameterComment()));
			xmlTree.append("</comment></param>");
		}
	}
	
	private void buildThrowsTagList(ThrowsTag[] throwsTags) {
		for (int i = 0; i < throwsTags.length; i++) {
			ThrowsTag throwsTag = throwsTags[i];
			xmlTree.append("<throws name=\"");
			xmlTree.append(throwsTag.exceptionName());
			xmlTree.append("\" class=\"");
			xmlTree.append(throwsTag.exception());
			xmlTree.append("\" ><comment>");
			xmlTree.append(makeSafe(throwsTag.exceptionComment()));
			xmlTree.append("</comment></throws>");
		}
	}
	
	private String getProgramElementAttributes(ProgramElementDoc peDoc) {
		String attribs = "final=\"" + peDoc.isFinal() + "\" "
			+ "private=\"" + peDoc.isPrivate() + "\" "
			+ "protected=\"" + peDoc.isProtected() + "\" "
			+ "public=\"" + peDoc.isPublic() + "\" "
			+ "static=\"" + peDoc.isStatic() + "\" "
			+ "modifiers=\"" + peDoc.modifiers() + "\"";
		return attribs;
	}

	private static String makeSafe(String text) {
		return Util.tidy(text);
	}

	private void buildHeader() throws Exception {
		String header = headerText;
		if (headerFile != null) {
			header = Util.readFile(headerFile);
		}
		if (header != null) {
			xmlTree.append("<header>");
			xmlTree.append(makeSafe(header));
			xmlTree.append("</header>");
		}
	}

	private void buildFooter() throws Exception {
		String footer = footerText;
		if (footerFile != null) {
			footer = Util.readFile(footerFile);
		}
		if (footer != null) {
			xmlTree.append("<footer>");
			xmlTree.append(makeSafe(footer));
			xmlTree.append("</footer>");
		}
	}

	private void buildCover() throws Exception {
		String cover = coverText;
		if (coverFile != null) {
			cover = Util.readFile(coverFile);
		}
		if (cover != null) {
			xmlTree.append("<cover>");
			xmlTree.append(makeSafe(cover));
			xmlTree.append("</cover>");
		}
	}

	private Object[] sortList(Object[] list) {
		Object[] sorted = new Object[list.length];
		TreeMap map = new TreeMap();
		for (int i = 0; i < list.length; i++) {
			Object object = list[i];
			map.put(object.toString(), object);
		}
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Object key = iter.next();
			sorted[index] = map.get(key);
			index++;
		}
		return sorted;
	}

	/**
	 * Builds the interface list.
	 */
	private void buildInterfaceList(ClassDoc classDoc) {
		Stack hierarchy = new Stack();
		getSuperClass(classDoc, hierarchy);

		ArrayList interfaces = new ArrayList();
		ClassDoc[] directInterfaces = classDoc.interfaces();
		append(interfaces, directInterfaces);

		HashMap directMap = new HashMap();
		for (int i = 0; i < directInterfaces.length; i++) {
			directMap.put(directInterfaces[i].toString(), directInterfaces);
		}
		
		// parent class interfaces
		while (!hierarchy.empty()) {
			ClassDoc superClass = (ClassDoc) hierarchy.pop();
			ClassDoc[] superClassInterfaces = superClass.interfaces(); 
			append(interfaces, superClassInterfaces);
		}

		// build the list
		Object[] sorted = sortList(interfaces.toArray());
		for (int i = 0; i < sorted.length; i++) {
			ClassDoc interfaceDoc = (ClassDoc) sorted[i];
			if (interfaceDoc == null) {
				continue;
			}
			boolean direct = false;
			if (directMap.containsKey(interfaceDoc.toString())) {
				direct = true;
			}
			xmlTree.append("<interface qualified-name=\"");
			xmlTree.append(interfaceDoc.qualifiedTypeName());
			xmlTree.append("\" direct=\"");
			xmlTree.append(direct);
			xmlTree.append("\" />");
		}
	}

	private void append(ArrayList list, ClassDoc[] interfaces) {
		for (int i = 0; i < interfaces.length; i++) {
			list.add(interfaces[i]);
		}
	}
	
	/**
	 * Builds the class hierarchy.
	 */
	private void buildClassHierarchy(ClassDoc classDoc) {
		Stack hierarchy =  new Stack();
		getSuperClass(classDoc, hierarchy);
		while (!hierarchy.empty()) {
			xmlTree.append("<super-class name=\"");
			xmlTree.append(hierarchy.pop().toString());
			xmlTree.append("\" />");
		}
	}

	private void getSuperClass(ClassDoc classDoc, Stack hierarchy) {
		if (classDoc.superclass() != null) {
			ClassDoc superClass = classDoc.superclass();
			hierarchy.push(superClass);
			getSuperClass(superClass, hierarchy);
		}
	}

	private static void debug(String msg) {
		System.out.println(msg);
	}
}
