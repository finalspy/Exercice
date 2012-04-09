package net.ypetit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.ypetit.Result.ResultType;

import org.jdom.Document;
import org.jdom.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test class for the Compare object methods.
 * 
 * @author ypetit
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
        CompareTest.beforeFile = new File("src/test/resources/basic/Before.xml");
        if (!CompareTest.beforeFile.exists()) {
            throw new Exception("Missing resource file for tests!");
        }
        CompareTest.unknownFile = new File("unknown.txt");
        CompareTest.notXMLFile = new File("README");
        CompareTest.otherFile = new File("pom.xml");
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
        Document document = Compare.loadFile(CompareTest.beforeFile);
        Assert.assertNotNull(document);

        // Load a not existing file
        document = Compare.loadFile(CompareTest.unknownFile);
        Assert.assertNull(document);

        // Load not XML file
        document = Compare.loadFile(CompareTest.notXMLFile);
        Assert.assertNull(document);
    }

    @Test
    public void extractData() {
        List<Element> elements = null;
        // extract elements of type tree/file
        elements = Compare
                .extractData(Compare.loadFile(CompareTest.beforeFile));
        Assert.assertNotNull(elements);
        Assert.assertEquals(4, elements.size());

        // xml document not containing tree or file
        elements = Compare.extractData(Compare.loadFile(CompareTest.otherFile));
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        // empty document
        elements = Compare.extractData(new Document());
        Assert.assertNull(elements);

        // null document
        final Document nullDocument = null;
        elements = Compare.extractData(nullDocument);
        Assert.assertNull(elements);
    }

    @Test
    public void asMap() {
        // TODO mock getFullPath method
        final List<Element> elementsList = new ArrayList<Element>();
        elementsList.add(new Element("tree").setAttribute("name", "home"));
        elementsList.add(new Element("file").setAttribute("name", "a.txt")
                .setAttribute("hash", "0123456789abcdef0"));
        elementsList.add(new Element("file").setAttribute("name", "b.txt")
                .setAttribute("hash", "0123456789abcdef1"));
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
        final Element elementA = new Element("file").setAttribute("name",
                "a.txt").setAttribute("hash", "0123456789abcdef0");
        final Element elementB = new Element("file").setAttribute("name",
                "b.txt").setAttribute("hash", "0123456789abcdef1");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
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
    public void asHashMap() {
        Map<String, Result> results = new HashMap<String, Result>();
        final Element elementA = new Element("file").setAttribute("name",
                "a.txt").setAttribute("hash", "0123456789abcdef0");
        final Element elementB = new Element("file").setAttribute("name",
                "b.txt").setAttribute("hash", "0123456789abcdef0");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
        elementHome.addContent(elementA).addContent(elementB);
        results.put("home/b.txt", new Result(ResultType.ADDED, "home/b.txt",
                null, elementB));
        results.put("home/a.txt", new Result(ResultType.DELETED, "home/a.txt",
                elementA, null));
        Map<ResultType, Map<String, Element>> result = Compare
                .asHashMap(results);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.get(ResultType.DELETED).size() == 1);
        Assert.assertTrue(result.get(ResultType.ADDED).size() == 1);
        Assert.assertTrue(result.get(ResultType.DELETED).containsKey(
                "0123456789abcdef0"));
        Assert.assertTrue(result.get(ResultType.ADDED).containsKey(
                "0123456789abcdef0"));
        Assert.assertTrue("home/a.txt".equals(Compare.getFullPath(result.get(
                ResultType.DELETED).get("0123456789abcdef0"))));
        Assert.assertTrue("home/b.txt".equals(Compare.getFullPath(result.get(
                ResultType.ADDED).get("0123456789abcdef0"))));
    }

    @Test
    public void findDifferencesAdded() {
        // Initialize some elements
        final Element elementA = new Element("file")
                .setAttribute("name", "a.txt").setAttribute("size", "5032")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef0");
        final Element elementB = new Element("file")
                .setAttribute("name", "b.txt").setAttribute("size", "1234")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef1");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
        elementHome.addContent(elementA).addContent(elementB);
        final Map<String, Element> input = new HashMap<String, Element>();
        input.put("home", elementHome);
        input.put("home/a.txt", elementA);
        final Map<String, Element> output = new HashMap<String, Element>();
        output.put("home", elementHome);
        output.put("home/a.txt", elementA);
        output.put("home/b.txt", elementB);
        Map<String, Result> results = Compare.findDifferences(input, output);
        // assertions on resulting object
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.containsKey("home/b.txt"));
        Assert.assertEquals(Result.ResultType.ADDED, results.get("home/b.txt")
                .getType());
        Assert.assertEquals("home/b.txt", results.get("home/b.txt").getPath());
        Assert.assertNull(results.get("home/b.txt").getSource());
        Assert.assertNotNull(results.get("home/b.txt").getTarget());
        System.out.println(results);
    }

    @Test
    public void findDifferencesDeleted() {
        // Initialize some elements
        final Element elementA = new Element("file")
                .setAttribute("name", "a.txt").setAttribute("size", "5032")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef0");
        final Element elementB = new Element("file")
                .setAttribute("name", "b.txt").setAttribute("size", "1234")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef1");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
        elementHome.addContent(elementA).addContent(elementB);
        final Map<String, Element> input = new HashMap<String, Element>();
        input.put("home", elementHome);
        input.put("home/a.txt", elementA);
        input.put("home/b.txt", elementB);
        final Map<String, Element> output = new HashMap<String, Element>();
        output.put("home", elementHome);
        output.put("home/a.txt", elementA);
        Map<String, Result> results = Compare.findDifferences(input, output);
        // assertions on resulting object
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.containsKey("home/b.txt"));
        Assert.assertEquals(Result.ResultType.DELETED, results
                .get("home/b.txt").getType());
        Assert.assertEquals("home/b.txt", results.get("home/b.txt").getPath());
        Assert.assertNotNull(results.get("home/b.txt").getSource());
        Assert.assertNull(results.get("home/b.txt").getTarget());
        System.out.println(results);
    }

    @Test
    public void findDifferencesModified() {
        // Initialize some elements
        final Element elementA = new Element("file")
                .setAttribute("name", "a.txt").setAttribute("size", "5032")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef1");
        final Element elementA2 = new Element("file")
                .setAttribute("name", "a.txt").setAttribute("size", "5031")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash", "0123456789abcdef1");
        final Element elementB = new Element("file")
                .setAttribute("name", "b.txt").setAttribute("size", "1234")
                .setAttribute("modif-date", "20120102T1030");
        final Element elementB2 = new Element("file")
                .setAttribute("name", "b.txt").setAttribute("size", "1234")
                .setAttribute("modif-date", "20120402T1030");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
        elementHome.addContent(elementA).addContent(elementB);
        final Map<String, Element> input = new HashMap<String, Element>();
        input.put("home", elementHome);
        input.put("home/a.txt", elementA);
        input.put("home/b.txt", elementB);
        final Map<String, Element> output = new HashMap<String, Element>();
        output.put("home", elementHome);
        output.put("home/a.txt", elementA2);
        output.put("home/b.txt", elementB2);
        Map<String, Result> results = Compare.findDifferences(input, output);
        // assertions on resulting object
        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.containsKey("home/b.txt"));
        Assert.assertEquals(Result.ResultType.MODIFIED,
                results.get("home/b.txt").getType());
        Assert.assertEquals("home/b.txt", results.get("home/b.txt").getPath());
        Assert.assertNotNull(results.get("home/b.txt").getSource());
        Assert.assertNotNull(results.get("home/b.txt").getTarget());
        Assert.assertTrue(results.containsKey("home/a.txt"));
        Assert.assertEquals(Result.ResultType.MODIFIED,
                results.get("home/a.txt").getType());
        Assert.assertEquals("home/a.txt", results.get("home/a.txt").getPath());
        Assert.assertNotNull(results.get("home/a.txt").getSource());
        Assert.assertNotNull(results.get("home/a.txt").getTarget());
        System.out.println(results);
    }

    @Test
    public void findDifferencesRenamed() {
        // Initialize some elements
        final Element elementA = new Element("file")
                .setAttribute("name", "a.txt")
                .setAttribute("size", "5032")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash",
                        "f3ac5e6dd11b7a3ceb00e10c74f79769cfd5db9b");
        final Element elementB = new Element("file")
                .setAttribute("name", "b.txt")
                .setAttribute("size", "5032")
                .setAttribute("modif-date", "20120102T1030")
                .setAttribute("hash",
                        "f3ac5e6dd11b7a3ceb00e10c74f79769cfd5db9b");
        final Element elementHome = new Element("tree").setAttribute("name",
                "home");
        elementHome.addContent(elementA).addContent(elementB);
        final Map<String, Element> input = new HashMap<String, Element>();
        input.put("home", elementHome);
        input.put("home/a.txt", elementA);
        final Map<String, Element> output = new HashMap<String, Element>();
        output.put("home", elementHome);
        output.put("home/b.txt", elementB);
        Map<String, Result> results = Compare.findDifferences(input, output);
        // assertions on resulting object
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.containsKey("home/a.txt"));
        Assert.assertEquals(Result.ResultType.RENAMED, results
                .get("home/a.txt").getType());
        Assert.assertEquals("home/a.txt", results.get("home/a.txt").getPath());
        Assert.assertNotNull(results.get("home/a.txt").getSource());
        Assert.assertNotNull(results.get("home/a.txt").getTarget());
        Assert.assertEquals("home/a.txt",
                Compare.getFullPath(results.get("home/a.txt").getSource()));
        Assert.assertEquals("home/b.txt",
                Compare.getFullPath(results.get("home/a.txt").getTarget()));
        System.out.println(results);
    }
}
