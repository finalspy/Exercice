package net.ypetit;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * This test class is designed to contain Integration Tests for functional use
 * cases.
 * 
 * @author ypetit
 */
public class CompareITCase {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CompareITCase.class);

    /**
     * This method tests the simple files given as specification.
     */
    @Test
    public final void basicTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : basicTest()");
        Compare.main(new String[] { "src/test/resources/basic/Before.xml",
                "src/test/resources/basic/After.xml" });
    }

    /**
     * This method tests the add of a file in the second file system XML
     * description.
     */
    @Test
    public final void addFileTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : addFileTest()");
        Compare.main(new String[] { "src/test/resources/add-file/Before.xml",
                "src/test/resources/add-file/After.xml" });
    }

    /**
     * This method tests the add of a folder in the second file system XML
     * description.
     */
    @Test
    public final void addFolderTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : addFolderTest()");
        Compare.main(new String[] { "src/test/resources/add-folder/Before.xml",
                "src/test/resources/add-folder/After.xml" });
    }

    /**
     * This method tests the deletion of a file in the second file system XML
     * description.
     */
    @Test
    public final void deleteFileTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : deleteFileTest()");
        Compare.main(new String[] {
                "src/test/resources/delete-file/Before.xml",
                "src/test/resources/delete-file/After.xml" });
    }

    /**
     * This method tests the deletion of a folder in the second file system XML
     * description.
     */
    @Test
    public final void deleteFolderTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : deleteFolderTest()");
        Compare.main(new String[] {
                "src/test/resources/delete-folder/Before.xml",
                "src/test/resources/delete-folder/After.xml" });
    }

    /**
     * This method tests the update of a file date in the second file system XML
     * description.
     */
    @Test
    public final void updateDateFileTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : updateDateFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-date/Before.xml",
                "src/test/resources/update-file-date/After.xml" });
    }

    /**
     * This method tests the update of a file size in the second file system XML
     * description.
     */
    @Test
    public final void updateSizeFileTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : updateSizeFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-size/Before.xml",
                "src/test/resources/update-file-size/After.xml" });
    }

    /**
     * This method tests a mix of different type of changes in the second file
     * system XML description.
     */
    @Test
    public final void complexTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : complexTest()");
        Compare.main(new String[] { "src/test/resources/complex/Before.xml",
                "src/test/resources/complex/After.xml" });
    }

    // TODO test large

    /**
     * This method tests a file renaming without content changes in the second
     * file system XML description.
     */
    @Test
    public final void renameFileTest() {
        CompareITCase.LOGGER.info("INTEGRATION TEST : renameFileTest()");
        Compare.main(new String[] {
                "src/test/resources/update-file-name/Before.xml",
                "src/test/resources/update-file-name/After.xml" });
    }

    // TODO test rename folder
}
