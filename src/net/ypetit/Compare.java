package net.ypetit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class Compare {

	/**
	 * The goal of this project is to compare two XML files describing file systems structures and detects diffs.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Compare launched !");
		// TODO check program usage
		// TODO load input files 
		Document before = loadFile(new File("resources/Before.xml"));
		Document after = loadFile(new File("resources/After.xml"));
		// TODO process to detect differences (additions, modifications, deletions)
		// TODO render results
		System.out.println("Compare exited !");
	}
	
	/**
	 * This method is used to read an xml file and load a Dom Document from it.
	 * @param path Path of the xml file we want to load.
	 * @return
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

}
