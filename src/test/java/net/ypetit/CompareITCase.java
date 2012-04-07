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
     * This method tests the add of a file in the second filesystem xml
     * description.
     */
    @Test
    public void addFileTest() {
        System.out.println("INTEGRATION TEST : addFileTest()");
        Compare.main(new String[] { "src/test/resources/add-file/Before.xml",
                "src/test/resources/add-file/After.xml" });
    }

    /**
     * This method tests the add of a folder in the second filesystem xml
     * description.
     */
    @Test
    public void addFolderTest() {
        System.out.println("INTEGRATION TEST : addFolderTest()");
        Compare.main(new String[] { "src/test/resources/add-folder/Before.xml",
                "src/test/resources/add-folder/After.xml" });
    }

    /**
     * This method tests the deletion of a file in the second filesystem xml
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
     * This method tests the deletion of a folder in the second filesystem xml
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
     * This method tests the update of a file date in the second filesystem xml
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
     * This method tests the update of a file size in the second filesystem xml
     * description.
     */
    @Test
    public void updateSizeFileTest() {
        System.out.println("INTEGRATION TEST : updateSizeFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-size/Before.xml",
                "src/test/resources/update-file-size/After.xml" });
    }

    /**
     * This method tests a mix of different type of changes in the second
     * filesystem xml description.
     */
    @Test
    public void complexTest() {
        System.out.println("INTEGRATION TEST : complexTest()");
        Compare.main(new String[] { "src/test/resources/complex/Before.xml",
                "src/test/resources/complex/After.xml" });
    }

    // TODO test large
    // test rename file
    /**
     * This method tests a file renaiming without content changes in the second
     * filesystem xml description.
     */
    @Test
    public void renameFileTest() {
        System.out.println("INTEGRATION TEST : renameFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-name/Before.xml",
                "src/test/resources/update-file-name/After.xml" });
    }
    // TODO test rename folder
}
