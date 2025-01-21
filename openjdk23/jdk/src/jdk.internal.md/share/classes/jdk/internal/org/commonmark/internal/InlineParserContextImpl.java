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

package jdk.internal.org.commonmark.internal;

import jdk.internal.org.commonmark.node.LinkReferenceDefinition;
import jdk.internal.org.commonmark.parser.InlineParserContext;
import jdk.internal.org.commonmark.parser.delimiter.DelimiterProcessor;

import java.util.List;
import java.util.Map;

public class InlineParserContextImpl implements InlineParserContext {

    private final List<DelimiterProcessor> delimiterProcessors;
    private final LinkReferenceDefinitions linkReferenceDefinitions;

    public InlineParserContextImpl(List<DelimiterProcessor> delimiterProcessors,
                                   LinkReferenceDefinitions linkReferenceDefinitions) {
        this.delimiterProcessors = delimiterProcessors;
        this.linkReferenceDefinitions = linkReferenceDefinitions;
    }

    @Override
    public List<DelimiterProcessor> getCustomDelimiterProcessors() {
        return delimiterProcessors;
    }

    @Override
    public LinkReferenceDefinition getLinkReferenceDefinition(String label) {
        return linkReferenceDefinitions.get(label);
    }
}
