package net.ypetit;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Compare {

	/**
	 * The goal of this project is to compare two XML files describing file systems structures and detects diffs.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Compare launched !");
		// TODO check program usage
		// TODO load input files 
		// TODO process to detect differences (additions, modifications, deletions)
		// TODO render results
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

}
