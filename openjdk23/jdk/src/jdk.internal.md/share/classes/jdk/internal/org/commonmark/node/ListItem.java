/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, a notice that is now available elsewhere in this distribution
 * accompanied the original version of this file, and, per its terms,
 * should not be removed.
 */

package jdk.internal.org.commonmark.node;

public class ListItem extends Block {

    private Integer markerIndent;
    private Integer contentIndent;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the indent of the marker such as "-" or "1." in columns (spaces or tab stop of 4) if available, or null
     * otherwise.
     * <p>
     * Some examples and their marker indent:
     * <pre>- Foo</pre>
     * Marker indent: 0
     * <pre> - Foo</pre>
     * Marker indent: 1
     * <pre>  1. Foo</pre>
     * Marker indent: 2
     */
    public Integer getMarkerIndent() {
        return markerIndent;
    }

    public void setMarkerIndent(Integer markerIndent) {
        this.markerIndent = markerIndent;
    }

    /**
     * Returns the indent of the content in columns (spaces or tab stop of 4) if available, or null otherwise.
     * The content indent is counted from the beginning of the line and includes the marker on the first line.
     * <p>
     * Some examples and their content indent:
     * <pre>- Foo</pre>
     * Content indent: 2
     * <pre> - Foo</pre>
     * Content indent: 3
     * <pre>  1. Foo</pre>
     * Content indent: 5
     * <p>
     * Note that subsequent lines in the same list item need to be indented by at least the content indent to be counted
     * as part of the list item.
     */
    public Integer getContentIndent() {
        return contentIndent;
    }

    public void setContentIndent(Integer contentIndent) {
        this.contentIndent = contentIndent;
    }
}
