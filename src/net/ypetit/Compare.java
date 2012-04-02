package net.ypetit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class Compare {

	private static final String PATH_SEPARATOR = "/";

	/**
	 * The goal of this project is to compare two XML files describing file
	 * systems structures and detects diffs.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Compare launched !");
		// TODO check program usage
		// load input files
		Document beforeDocument = Compare.loadFile(new File("resources/Before.xml"));
		Document afterDocument = Compare.loadFile(new File("resources/After.xml"));
		// extract elements to treat (as List by default)
		List<Element> beforeList = Compare.extractData(beforeDocument);
		List<Element> afterList = Compare.extractData(afterDocument);
		// convert lists to map with useful keys.
		Map<String,Element> beforeMap = Compare.asMap(beforeList);
		Map<String,Element> afterMap = Compare.asMap(afterList);
		// TODO process to detect differences (additions, modifications,
		// deletions)

		// TODO render results
		System.out.println("Compare exited !");
	}

	/**
	 * This method is used to read an xml file and load a Dom Document from it.
	 * 
	 * @param path
	 *            Path of the xml file we want to load.
	 * @return a Dom Document representation of the input file, or null if file
	 *         hasn't been loaded properly.
	 */
	public static Document loadFile(File path) {
		Document document = null;
		try {
			SAXBuilder sxb = new SAXBuilder();
			document = sxb.build(path);
		} catch (IOException e) {
			System.out.println("Error reading file " + e.getMessage());
			e.printStackTrace();
		} catch (JDOMException e) {
			System.out.println("Error building JDOM " + e.getMessage());
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * This method is used to extract as a List of Element Dom object the tags
	 * to analyze in the document passed as a parameter. For the purpose of this
	 * exercise the Elements are tree and file tags.
	 * 
	 * @param treeFileDocument
	 *            the Dom Document to parse to extract targeted Elements.
	 * @return a List of Element objects matching the Xpath expression
	 *         //file|//tree (which mean any file or tree tag at any level in
	 *         the document tree), or return null if there was a problem loading
	 *         or parsing the input Document.
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> extractData(Document treeFileDocument) {
		List<Element> result = null;
		if (null != treeFileDocument) {
			try {
				Element racine = treeFileDocument.getRootElement();
				XPath xpathFiles = XPath.newInstance("//file|//tree");
				result = xpathFiles.selectNodes(racine);
				System.out.println(result);
			} catch (JDOMException e) {
				System.out.println("Error parsing JDOM " + e.getMessage());
				e.printStackTrace();
			} catch (IllegalStateException e) {
				System.out.println("Error parsing JDOM " + e.getMessage());
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * This method is used to convert a List of Element objects to a Map of
	 * <String,Element>.
	 * 
	 * @param elements
	 *            List of Element object to convert to a map.
	 * @return a Map of String as key and Element as value.
	 */
	public static Map<String, Element> asMap(List<Element> elements) {
		Map<String, Element> filesMap = null;
		if (null != elements) {
			filesMap = new HashMap<String, Element>();
			Iterator<Element> iter = elements.iterator();
			Element noeudCourant = null;
			while (iter.hasNext()) {
				noeudCourant = iter.next();
				// extract key from Element parent @name concatenation to
				// reflect filesystem hierarchy
				filesMap.put(getFullPath(noeudCourant), noeudCourant);
			}
		}
		return filesMap;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String getFullPath(Element node) {
		String fullPath = null;
		while (node != null) {
			if(node.getName().equals("tree"))
				fullPath = node.getAttributeValue("name") + PATH_SEPARATOR + fullPath;
			if(node.getName().equals("file"))
				fullPath = node.getAttributeValue("name");
			node = node.getParentElement();
		}
		return fullPath;
	}
}
