/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

/**
 *
 * @author tkrodrig
 */
public class StringUtils {

    public static String escapeHTML(String s) {
        StringBuilder str = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':
                case '<':
                case '>':
                case '"':
                case '\'':
                    if (str == null) {
                        str = new StringBuilder();
                        str.append(s, 0, i);
                    }
                    switch (c) {
                        case '&':
                            str.append("&amp;");
                            break;
                        case '<':
                            str.append("&lt;");
                            break;
                        case '>':
                            str.append("&gt;");
                            break;
                        case '"':
                            str.append("&quot;");
                            break;
                        case '\'':
                            str.append("&#39;");
                            break;
                        default:
                            assert false;
                    }
                    break;
                case '\u0000':
                case '\u0001':
                case '\u0002':
                case '\u0003':
                case '\u0004':
                case '\u0005':
                case '\u0006':
                case '\u0007':
                case '\u0008':
                case '\u000b':
                case '\u000c':
                case '\u000e':
                case '\u000f':
                case '\u0010':
                case '\u0011':
                case '\u0012':
                case '\u0013':
                case '\u0014':
                case '\u0015':
                case '\u0016':
                case '\u0017':
                case '\u0018':
                case '\u0019':
                case '\u001a':
                case '\u001b':
                case '\u001c':
                case '\u001d':
                case '\u001e':
                case '\u001f':
                    if (str == null) {
                        str = new StringBuilder();
                        str.append(s, 0, i);
                    }
                    str.append("'0x").append(Integer.toHexString(c));
                    break;
                default:
                    if (str != null) {
                        str.append(c);
                    }
                    break;
            }
        }
        if (str == null) {
            return s;
        } else {
            return str.toString();
        }
    }

    /**
     * Rank a match of a query in a word. Full matches of a word rank highest,
     * followed by partial matches at the word start, followed by the rest of
     * matches in increasing size of the partially matched word, for example:
     * <p>
     *   rank("5", "5")   = 1 (full match)
     *   rank("5", "554") = 2 (start match)
     *   rank("5", "25")  = 3 (middle match with excess 1)
     *   rank("5", "253") = 4 (middle match with excess 2)
     */
    public static int rankMatch(String query, String word) {
        if (word.equals(query)) {
            return 1;
        } else if (word.startsWith(query)) {
            return 2;
        } else if (word.contains(query)) {
            return word.length() - query.length() + 2;
        }
        return Integer.MAX_VALUE;
    }

}
