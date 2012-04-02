/**
 * 
 */
package net.ypetit;

import java.io.File;
import java.util.ArrayList;
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

		// Load an unexisting file
		document = Compare.loadFile(unknownFile);
		Assert.assertNull(document);

		// Load not xml file
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
		// Load an existing XML file to Document structure
		List<Element> elementsList = new ArrayList<Element>();
		elementsList.add(new Element("tree"));
		elementsList.add(new Element("file"));
		elementsList.add(new Element("file"));
		Map<String, Element> elementsMap = Compare.asMap(elementsList);
		Assert.assertNotNull(elementsMap);
		// Expected 2 as Map doesn't accept duplicte keys
		// TODO once fully implemented two element of same name will result in different map entries as the key would be based on ancestors name attribute.
		Assert.assertEquals(2, elementsMap.size());

		// Convert a null list
		elementsMap = Compare.asMap(null);
		Assert.assertNull(elementsMap);
	}

}
