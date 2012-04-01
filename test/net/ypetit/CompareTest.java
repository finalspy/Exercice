/**
 * 
 */
package net.ypetit;

import java.io.File;

import junit.framework.Assert;

import org.jdom.Document;
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

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		Document document = Compare.loadFile(new File("resources/Before.xml"));
		Assert.assertNotNull(document);

		// Load an unexisting file
		document = Compare.loadFile(new File("resources/unknown.txt"));
		Assert.assertNull(document);

		// Load not xml file
		document = Compare.loadFile(new File("README"));
		Assert.assertNull(document);
	}

}
