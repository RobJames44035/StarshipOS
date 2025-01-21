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

package jdk.internal.org.commonmark.internal.inline;

import jdk.internal.org.commonmark.internal.util.Escaping;
import jdk.internal.org.commonmark.node.HardLineBreak;
import jdk.internal.org.commonmark.node.Text;
import jdk.internal.org.commonmark.parser.beta.Scanner;

import java.util.regex.Pattern;

/**
 * Parse a backslash-escaped special character, adding either the escaped  character, a hard line break
 * (if the backslash is followed by a newline), or a literal backslash to the block's children.
 */
public class BackslashInlineParser implements InlineContentParser {

    private static final Pattern ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);

    @Override
    public ParsedInline tryParse(InlineParserState inlineParserState) {
        Scanner scanner = inlineParserState.scanner();
        // Backslash
        scanner.next();

        char next = scanner.peek();
        if (next == '\n') {
            scanner.next();
            return ParsedInline.of(new HardLineBreak(), scanner.position());
        } else if (ESCAPABLE.matcher(String.valueOf(next)).matches()) {
            scanner.next();
            return ParsedInline.of(new Text(String.valueOf(next)), scanner.position());
        } else {
            return ParsedInline.of(new Text("\\"), scanner.position());
        }
    }
}
