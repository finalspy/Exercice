package net.ypetit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.ypetit.Result.ResultType;

import org.apache.commons.lang.StringUtils;
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
    public static void main(final String[] args) {
        System.out.println("Compare launched !");
        // TODO check program usage
        // load input files
        final Document beforeDocument = Compare.loadFile(new File(args[0]));
        final Document afterDocument = Compare.loadFile(new File(args[1]));
        // extract elements to treat (as List by default)
        final List<Element> beforeList = Compare.extractData(beforeDocument);
        final List<Element> afterList = Compare.extractData(afterDocument);
        // convert lists to map with useful keys.
        final Map<String, Element> beforeMap = Compare.asMap(beforeList);
        final Map<String, Element> afterMap = Compare.asMap(afterList);

        // process to detect differences (additions, modifications, deletions)
        final Map<String, Result> results = Compare.findDifferences(beforeMap,
                afterMap);
        // render results
        System.out.println(results.values().toString());
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
    public static Document loadFile(final File path) {
        Document document = null;
        try {
            final SAXBuilder sxb = new SAXBuilder();
            document = sxb.build(path);
        } catch (final IOException e) {
            System.out.println("Error reading file " + e.getMessage());
            e.printStackTrace();
        } catch (final JDOMException e) {
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
    public static List<Element> extractData(final Document treeFileDocument) {
        List<Element> result = null;
        if (null != treeFileDocument) {
            try {
                final Element racine = treeFileDocument.getRootElement();
                final XPath xpathFiles = XPath.newInstance("//file|//tree");
                result = xpathFiles.selectNodes(racine);
            } catch (final JDOMException e) {
                System.out.println("Error parsing JDOM " + e.getMessage());
                e.printStackTrace();
            } catch (final IllegalStateException e) {
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
    public static Map<String, Element> asMap(final List<Element> elements) {
        Map<String, Element> filesMap = null;
        if (null != elements) {
            filesMap = new HashMap<String, Element>();
            final Iterator<Element> iter = elements.iterator();
            Element noeudCourant = null;
            while (iter.hasNext()) {
                noeudCourant = iter.next();
                // extract key from Element parent @name concatenation to
                // reflect filesystem hierarchy
                filesMap.put(Compare.getFullPath(noeudCourant), noeudCourant);
            }
        }
        return filesMap;
    }

    /**
     * This method is used to extract full path from an element. It uses a
     * concatenation of each ancestor "name" attribute value and the current
     * element "hash" attribute separated with FILE_SEPARATOR. (previous version
     * used the current element "name" attribute, but this method didn't allow
     * to detects renaming.)
     * 
     * @param element
     *            The element to extract the path from.
     * @return a String representing the extracted path.
     */
    public static String getFullPath(final Element element) {
        String fullPath = null;
        Element current = element;
        while (current != null) {
            if (current.getName().equals("tree")) {
                fullPath = current.getAttributeValue("name")
                        + Compare.PATH_SEPARATOR + fullPath;
            }
            if (current.getName().equals("file")) {
                fullPath = current.getAttributeValue("name");
            }
            current = current.getParentElement();
        }
        return fullPath;
    }

    // TODO simplify method in more unitary other methods
    /**
     * This method contains the core treatment to find differences between two
     * map containing file and tree elements extracted from an XML file
     * representing a file system structure. It returns a map of Result objects
     * by path. A Result objects contains the source and target Dom Element, and
     * the type of the difference noticed between them. The Result objects map
     * can further be used to apply some kind of patch between two file systems.
     * 
     * @param input
     *            the first Map to compare.
     * @param output
     *            the second Map to compare
     * @return results, a Map of String (path) as key and Result objects as
     *         value.
     */
    public static Map<String, Result> findDifferences(
            final Map<String, Element> input, final Map<String, Element> output) {
        final Map<String, Result> results = new HashMap<String, Result>();
        Element value = null;
        String key = null;
        for (final Map.Entry<String, Element> entry : output.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            // needs to check that value isn't null and has a name attribute
            if (null != value
                    && StringUtils.isNotEmpty(value.getAttributeValue("name"))) {
                // ADDED : key not present so path tree/file has been added
                if (!input.containsKey(key)) {
                    results.put(key, new Result(Result.ResultType.ADDED, key,
                            null, value));
                } else {
                    // MODIFIED : compare size and name
                    if ("file".equals(value.getName())) {
                        if (StringUtils.isNotEmpty(value
                                .getAttributeValue("size"))
                                && StringUtils.isNotEmpty(value
                                        .getAttributeValue("modif-date"))
                                && (!value.getAttributeValue("size").equals(
                                        input.get(key)
                                                .getAttributeValue("size")) || !value
                                        .getAttributeValue("modif-date")
                                        .equals(input
                                                .get(key)
                                                .getAttributeValue("modif-date")))) {
                            results.put(key,
                                    new Result(Result.ResultType.MODIFIED, key,
                                            input.get(key), value));
                        }
                    }
                    // remove treated key (at the end remaining keys are those
                    // which where removed in outputfile)
                    input.remove(key);
                }
            }
        }
        // DELETED : remaining key denotes file/trees not present in output file
        for (final Map.Entry<String, Element> entry : input.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (null != value
                    && StringUtils.isNotEmpty(value.getAttributeValue("name"))) {
                results.put(key, new Result(Result.ResultType.DELETED, key,
                        value, null));
            }
        }
        // Keep track of renaming/moving by comparing hashes from deleted/added
        // files remap using hash
        Map<ResultType, Map<String, Element>> hashDeletedAddedMap = asHashMap(results);
        Map<String, Element> deletedHashMap = hashDeletedAddedMap
                .get(Result.ResultType.DELETED);
        Map<String, Element> addedHashMap = hashDeletedAddedMap
                .get(Result.ResultType.ADDED);
        // iterate over deleted
        for (final Map.Entry<String, Element> entry : deletedHashMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            // verify if added contains it
            if (addedHashMap.containsKey(key)) {
                // RENAMED : add as renamed and remove deleted/added ones.
                results.put(getFullPath(value), new Result(
                        Result.ResultType.RENAMED, getFullPath(value), value,
                        addedHashMap.get(key)));
                results.remove(getFullPath(addedHashMap.get(key)));
            }
        }

        return results;
    }

    /**
     * @param results
     * @return
     */
    public static Map<ResultType, Map<String, Element>> asHashMap(
            Map<String, Result> results) {
        Map<ResultType, Map<String, Element>> result = new HashMap<ResultType, Map<String, Element>>();
        Map<String, Element> deleted = new HashMap<String, Element>();
        Map<String, Element> added = new HashMap<String, Element>();
        Result value;
        for (final Map.Entry<String, Result> entry : results.entrySet()) {
            value = entry.getValue();
            if (Result.ResultType.ADDED.equals(value.getType())) {
                added.put(value.getTarget().getAttributeValue("hash"),
                        value.getTarget());
            } else if (Result.ResultType.DELETED.equals(value.getType())) {
                deleted.put(value.getSource().getAttributeValue("hash"),
                        value.getSource());
            }
        }
        result.put(Result.ResultType.ADDED, added);
        result.put(Result.ResultType.DELETED, deleted);
        return result;
    }
}
