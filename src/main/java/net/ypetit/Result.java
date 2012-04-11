package net.ypetit;

import org.jdom.Element;

/**
 * This object is used to store result of a comparison for further processing.
 * 
 * @author ypetit
 */
public class Result {

    /**
     * Type of the difference between source and target.
     */
    private ResultType type;
    /**
     * Path in the file system affected by this result.
     */
    private String path;
    /**
     * Element object from source.
     */
    private Element source;
    /**
     * Element object from target.
     */
    private Element target;

    /**
     * Constructor for Result Object.
     * 
     * @param type
     *            Type attribute to set.
     * @param source
     *            Element attribute to set.
     * @param target
     *            Element attribute to set.
     */
    public Result(final ResultType type, final String path,
            final Element source, final Element target) {
        this.type = type;
        this.path = path;
        this.source = source;
        this.target = target;
    }

    /**
     * Getter method for type attribute.
     * 
     * @return a type from the Type enumeration matching the involved difference
     *         between source and target.
     */
    public final ResultType getType() {
        return this.type;
    }

    /**
     * Setter method for type attribute.
     * 
     * @param type
     */
    public final void setType(final ResultType type) {
        this.type = type;
    }

    /**
     * Getter method for path attribute.
     * 
     * @return the path involved in the current difference between source and
     *         target file systems.
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Setter method for path attribute.
     * 
     * @param path
     *            the path to set.
     */
    public final void setPath(final String path) {
        this.path = path;
    }

    /**
     * Getter method for source attribute.
     * 
     * @return the source Element involved in the current difference between
     *         source and target file systems.
     */
    public final Element getSource() {
        return this.source;
    }

    /**
     * Setter method for source attribute.
     * 
     * @param source
     *            the Element to set as source element.
     */
    public final void setSource(final Element source) {
        this.source = source;
    }

    /**
     * Getter method for target attribute.
     * 
     * @return the target Element involved in the current difference between
     *         source and target file systems.
     */
    public final Element getTarget() {
        return this.target;
    }

    /**
     * Setter method for target attribute.
     * 
     * @param target
     *            the Element to set as target element.
     */
    public final void setTarget(final Element target) {
        this.target = target;
    }

    @Override
    public final String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.type).append(" ").append(this.path);
        return result.toString();
    }

    /**
     * Enumeration representing the different kind of modifications that can
     * arise between two file systems objects.
     * 
     * @author ypetit
     */
    public static enum ResultType {
        /**
         * Denotes an addition between two file systems.
         */
        ADDED,
        /**
         * Denotes a modification between two file systems.
         */
        MODIFIED,
        /**
         * Denotes a deletion between two file systems.
         */
        DELETED,
        /**
         * Denotes a renaming between two file systems.
         */
        RENAMED
    }

}
