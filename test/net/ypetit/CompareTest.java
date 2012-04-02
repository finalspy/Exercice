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
		// TODO mock getFullPath method
		// Load an existing XML file to Document structure
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
		// Load an existing XML file to Document structure
		Element elementA = new Element("file").setAttribute("name", "a.txt");
		Element elementB = new Element("file").setAttribute("name", "b.txt");
		Element elementHome = new Element("tree").setAttribute("name", "home");
		elementHome.addContent(elementA).addContent(elementB);

		String resultKey = Compare.getFullPath(elementB);
		Assert.assertNotNull(resultKey);
		Assert.assertEquals("home/b.txt", resultKey);

		// Convert a null list
		resultKey = Compare.getFullPath(null);
		Assert.assertNull(resultKey);
	}
}
