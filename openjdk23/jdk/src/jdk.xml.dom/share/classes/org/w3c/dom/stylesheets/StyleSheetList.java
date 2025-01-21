/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.stylesheets;

/**
 * The <code>StyleSheetList</code> interface provides the abstraction of an
 * ordered collection of style sheets.
 * <p> The items in the <code>StyleSheetList</code> are accessible via an
 * integral index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since 1.4, DOM Level 2
 */
public interface StyleSheetList {
    /**
     *  The number of <code>StyleSheets</code> in the list. The range of valid
     * child stylesheet indices is <code>0</code> to <code>length-1</code>
     * inclusive.
     */
    public int getLength();

    /**
     *  Used to retrieve a style sheet by ordinal index. If index is greater
     * than or equal to the number of style sheets in the list, this returns
     * <code>null</code>.
     * @param index Index into the collection
     * @return The style sheet at the <code>index</code> position in the
     *   <code>StyleSheetList</code>, or <code>null</code> if that is not a
     *   valid index.
     */
    public StyleSheet item(int index);

}
