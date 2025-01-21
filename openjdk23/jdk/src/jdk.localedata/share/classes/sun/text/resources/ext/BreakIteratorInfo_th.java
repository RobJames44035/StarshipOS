/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class BreakIteratorInfo_th extends ListResourceBundle {
    protected final Object[][] getContents() {
        return new Object[][] {
            // BreakIteratorClasses lists the class names to instantiate for each
            // built-in type of BreakIterator
            {"BreakIteratorClasses",
                new String[] {
                    "DictionaryBasedBreakIterator",  // word-break iterator class
                    "DictionaryBasedBreakIterator",  // line-break iterator class
                    "RuleBasedBreakIterator"   // sentence-break iterator class
                }
            },

            // Data filename for each break-iterator
            {"WordData", "WordBreakIteratorData_th"},
            {"LineData", "LineBreakIteratorData_th"},

            // Dictionary filename for each dictionary-based break-iterator
            {"WordDictionary", "thai_dict"},
            {"LineDictionary", "thai_dict"},
        };
    }
}
