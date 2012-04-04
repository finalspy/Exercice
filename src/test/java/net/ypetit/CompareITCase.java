/**
 * 
 */
package net.ypetit;

import org.junit.Test;

/**
 * This test class is designed to contain Integration Tests for functionnal use
 * cases.
 * 
 * @author ypetit
 */
public class CompareITCase {

    /**
     * This method tests the simple files given as specification.
     */
    @Test
    public void basicTest() {
        System.out.println("INTEGRATION TEST : basicTest()");
        Compare.main(new String[] { "src/test/resources/basic/Before.xml",
                "src/test/resources/basic/After.xml" });
    }

    /**
     * This method tests the add of a file in the second filesystem xml.
     * description.
     */
    @Test
    public void addFileTest() {
        System.out.println("INTEGRATION TEST : addFileTest()");
        Compare.main(new String[] { "src/test/resources/add-file/Before.xml",
                "src/test/resources/add-file/After.xml" });
    }

    /**
     * This method tests the add of a folder in the second filesystem xml.
     * description.
     */
    @Test
    public void addFolderTest() {
        System.out.println("INTEGRATION TEST : addFolderTest()");
        Compare.main(new String[] { "src/test/resources/add-folder/Before.xml",
                "src/test/resources/add-folder/After.xml" });
    }

    /**
     * This method tests the deletion of a file in the second filesystem xml.
     * description.
     */
    @Test
    public void deleteFileTest() {
        System.out.println("INTEGRATION TEST : deleteFileTest()");
        Compare.main(new String[] {
                "src/test/resources/delete-file/Before.xml",
                "src/test/resources/delete-file/After.xml" });
    }

    /**
     * This method tests the deletion of a folder in the second filesystem xml.
     * description.
     */
    @Test
    public void deleteFolderTest() {
        System.out.println("INTEGRATION TEST : deleteFolderTest()");
        Compare.main(new String[] {
                "src/test/resources/delete-folder/Before.xml",
                "src/test/resources/delete-folder/After.xml" });
    }

    /**
     * This method tests the update of a file date in the second filesystem xml.
     * description.
     */
    @Test
    public void updateDateFileTest() {
        System.out.println("INTEGRATION TEST : updateDateFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-date/Before.xml",
                "src/test/resources/update-file-date/After.xml" });
    }

    /**
     * This method tests the update of a file size in the second filesystem xml.
     * description.
     */
    @Test
    public void updateSizeFileTest() {
        System.out.println("INTEGRATION TEST : updateSizeFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-size/Before.xml",
                "src/test/resources/update-file-size/After.xml" });
    }

    // TODO test complex
    // TODO test large
    // TODO test rename file
    // TODO test rename folder
}
