/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.util;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

import org.netbeans.jemmy.operators.JTextComponentOperator.TextChooser;

/**
 * Makes easier to implement searching criteria for
 * {@code javax.swing.text.StyledDocument}
 * {@code JTextComponentOperator.getPositionByText(String, JTextComponentOperator.TextChooser, int)}.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public abstract class AbstractTextStyleChooser implements TextChooser {

    /**
     * Constructor.
     */
    public AbstractTextStyleChooser() {
    }

    /**
     * Should return true if position fulfils criteria.
     *
     * @param doc a styled document to be searched.
     * @param element an element to be checked.
     * @param offset checked position.
     * @return true if position fits the criteria.
     */
    public abstract boolean checkElement(StyledDocument doc, Element element, int offset);

    @Override
    public abstract String getDescription();

    @Override
    public String toString() {
        return "AbstractTextStyleChooser{description = " + getDescription() + '}';
    }

    @Override
    public final boolean checkPosition(Document document, int offset) {
        return (checkElement(((StyledDocument) document),
                ((StyledDocument) document).getCharacterElement(offset),
                offset));
    }
}
