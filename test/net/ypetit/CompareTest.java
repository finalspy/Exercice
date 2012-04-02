/**
 * 
 */
package net.ypetit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.jdom.Document;
import org.jdom.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ypetit
 * 
 */
public class CompareTest {

	private static File beforeFile;
	private static File unknownFile;
	private static File notXMLFile;
	private static File otherFile;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Initialize files
		beforeFile = new File("resources/Before.xml");
		if (!beforeFile.exists()) {
			throw new Exception("Missing resource file for tests!");
		}
		unknownFile = new File("resources/unknown.txt");
		notXMLFile = new File("README");
		otherFile = new File("build.xml");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loadFile() {
		// Load an existing XML file to Document structure
		Document document = Compare.loadFile(beforeFile);
		Assert.assertNotNull(document);

		// Load a not existing file
		document = Compare.loadFile(unknownFile);
		Assert.assertNull(document);

		// Load not XML file
		document = Compare.loadFile(notXMLFile);
		Assert.assertNull(document);
	}

	@Test
	public void extractData() {
		List<Element> elements = null;
		// extract elements of type tree/file
		elements = Compare.extractData(Compare.loadFile(beforeFile));
		Assert.assertNotNull(elements);
		Assert.assertEquals(4, elements.size());

		// xml document not containing tree or file
		elements = Compare.extractData(Compare.loadFile(otherFile));
		Assert.assertNotNull(elements);
		Assert.assertEquals(0, elements.size());

		// empty document
		elements = Compare.extractData(new Document());
		Assert.assertNull(elements);

		// null document
		Document nullDocument = null;
		elements = Compare.extractData(nullDocument);
		Assert.assertNull(elements);
	}

	@Test
	public void asMap() {
		// TODO mock getFullPath method
		List<Element> elementsList = new ArrayList<Element>();
		elementsList.add(new Element("tree").setAttribute("name", "home"));
		elementsList.add(new Element("file").setAttribute("name", "a.txt"));
		elementsList.add(new Element("file").setAttribute("name", "b.txt"));
		Map<String, Element> elementsMap = Compare.asMap(elementsList);
		Assert.assertNotNull(elementsMap);
		// Expected 3 not 2 as Map doesn't accept duplicate keys but two element
		// of same name will result in different map entries as the key would be
		// based on ancestors name attribute.
		Assert.assertEquals(3, elementsMap.size());
		// Convert a null list
		elementsMap = Compare.asMap(null);
		Assert.assertNull(elementsMap);
	}

	@Test
	public void getFullPath() {
		// Initialize some elements
		Element elementA = new Element("file").setAttribute("name", "a.txt");
		Element elementB = new Element("file").setAttribute("name", "b.txt");
		Element elementHome = new Element("tree").setAttribute("name", "home");
		elementHome.addContent(elementA).addContent(elementB);

		// Extract from real Element
		String resultKey = Compare.getFullPath(elementB);
		Assert.assertNotNull(resultKey);
		Assert.assertEquals("home/b.txt", resultKey);

		// Extract from null String
		resultKey = Compare.getFullPath(null);
		Assert.assertNull(resultKey);
	}

	@Test
	public void findDifferencesAdded() {
		// Initialize some elements
		Element elementA = new Element("file").setAttribute("name", "a.txt").setAttribute("size", "5032").setAttribute("modif-date", "20120102T1030");
		Element elementB = new Element("file").setAttribute("name", "b.txt").setAttribute("size", "1234").setAttribute("modif-date", "20120102T1030");
		Element elementHome = new Element("tree").setAttribute("name", "home");
		elementHome.addContent(elementA).addContent(elementB);
		Map<String, Element> input = new HashMap<String, Element>();
		input.put("home", elementHome);
		input.put("home/a.txt", elementA);
		Map<String, Element> output = new HashMap<String, Element>();
		output.put("home", elementHome);
		output.put("home/a.txt", elementA);
		output.put("home/b.txt", elementB);
		Compare.findDifferences(input, output);
	}
	
	@Test
	public void findDifferencesDeleted() {
		// Initialize some elements
		Element elementA = new Element("file").setAttribute("name", "a.txt").setAttribute("size", "5032").setAttribute("modif-date", "20120102T1030");
		Element elementB = new Element("file").setAttribute("name", "b.txt").setAttribute("size", "1234").setAttribute("modif-date", "20120102T1030");
		Element elementHome = new Element("tree").setAttribute("name", "home");
		elementHome.addContent(elementA).addContent(elementB);
		Map<String, Element> input = new HashMap<String, Element>();
		input.put("home", elementHome);
		input.put("home/a.txt", elementA);
		input.put("home/b.txt", elementB);
		Map<String, Element> output = new HashMap<String, Element>();
		output.put("home", elementHome);
		output.put("home/a.txt", elementA);
		Compare.findDifferences(input, output);
	}
	
	@Test
	public void findDifferencesModified() {
		// Initialize some elements
		Element elementA = new Element("file").setAttribute("name", "a.txt").setAttribute("size", "5032").setAttribute("modif-date", "20120102T1030");
		Element elementA2 = new Element("file").setAttribute("name", "a.txt").setAttribute("size", "5031").setAttribute("modif-date", "20120102T1030");
		Element elementB = new Element("file").setAttribute("name", "b.txt").setAttribute("size", "1234").setAttribute("modif-date", "20120102T1030");
		Element elementB2 = new Element("file").setAttribute("name", "b.txt").setAttribute("size", "1234").setAttribute("modif-date", "20120402T1030");
		Element elementHome = new Element("tree").setAttribute("name", "home");
		elementHome.addContent(elementA).addContent(elementB);
		Map<String, Element> input = new HashMap<String, Element>();
		input.put("home", elementHome);
		input.put("home/a.txt", elementA);
		input.put("home/b.txt", elementB);
		Map<String, Element> output = new HashMap<String, Element>();
		output.put("home", elementHome);
		output.put("home/a.txt", elementA2);
		output.put("home/b.txt", elementB2);
		Compare.findDifferences(input, output);
	}
}
