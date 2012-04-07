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
     * Element object from source.
     */
    private Element source;
    /**
     * Element object from target.
     */
    private Element target;

    /**
     * Getter method for type attribute.
     * 
     * @return a type from the Type enumeration matching the involved difference
     *         between source and target.
     */
    public final ResultType getType() {
        return type;
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
     * Getter method for source attribute.
     * 
     * @return the source Element involved in the current difference between
     *         source and target file systems.
     */
    public final Element getSource() {
        return source;
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
        return target;
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
