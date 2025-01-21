/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8144287 8273244 8284908 8305620
 * @summary extend com.sun.source API to support parsing javadoc comments
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester IndexTest.java
 */

class IndexTest {
    /**
     * abc {@index xyz} def
     */
    void simple_term() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, xyz]
      description: empty
    ]
    Text[TEXT, pos:17, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index lmn pqr stu} def
     */
    void simple_term_with_description() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, lmn]
      description: 1
        Text[TEXT, pos:17, pqr_stu]
    ]
    Text[TEXT, pos:25, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index ijk {lmn opq} } def
     */
    void phrase_with_curly_description() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, ijk]
      description: 1
        Text[TEXT, pos:17, {lmn_opq}_]
    ]
    Text[TEXT, pos:28, _def]
  body: empty
  block tags: empty
]
*/
    /**
     * abc {@index ijk lmn
     * opq
     * rst
     * } def
     */
    void phrase_with_nl_description() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, ijk]
      description: 1
        Text[TEXT, pos:17, lmn|_opq|_rst|_]
    ]
    Text[TEXT, pos:33, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index "lmn pqr"} def
     */
    void phrase_no_description() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, "lmn_pqr"]
      description: empty
    ]
    Text[TEXT, pos:23, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index "ijk lmn" pqr xyz} def
     */
    void phrase_with_description() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, "ijk_lmn"]
      description: 1
        Text[TEXT, pos:23, pqr_xyz]
    ]
    Text[TEXT, pos:31, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index {@xyz} "{@see xyz}"} def
     */
    void term_and_description_with_nested_tag() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, {@xyz}]
      description: 3
        Text[TEXT, pos:20, "]
        UnknownInlineTag[UNKNOWN_INLINE_TAG, pos:21
          tag:see
          content: 1
            Text[TEXT, pos:27, xyz]
        ]
        Text[TEXT, pos:31, "]
    ]
    Text[TEXT, pos:33, _def]
  body: empty
  block tags: empty
]
*/
    /**
     * abc {@index @def lmn } xyz
     */
    void term_with_at() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, @def]
      description: 1
        Text[TEXT, pos:18, lmn_]
    ]
    Text[TEXT, pos:23, _xyz]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index ijk@lmn pqr } xyz
     */
    void term_with_text_at() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, ijk@lmn]
      description: 1
        Text[TEXT, pos:21, pqr_]
    ]
    Text[TEXT, pos:26, _xyz]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index ""} def
     */
    void empty_index_in_quotes() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Index[INDEX, pos:5
      term:
        Text[TEXT, pos:13, ""]
      description: empty
    ]
    Text[TEXT, pos:16, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc {@index
     * @return def} xyz
     */
    @NormalizeTags(false) // see DocCommentTester.PrettyChecker
    void bad_nl_at_in_term() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 2
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5, prefPos:11
      code: compiler.err.dc.no.content
      body: {@index
    ]
  body: empty
  block tags: 1
    Return[RETURN, pos:14
      description: 1
        Text[TEXT, pos:22, def}_xyz]
    ]
]
*/
    /**
     * abc {@index "xyz } def
     */
    void bad_unbalanced_quote() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 2
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5, prefPos:22
      code: compiler.err.dc.no.content
      body: {@index_"xyz_}_def
    ]
  body: empty
  block tags: empty
]
*/
    /**
     * abc {@index} def
     */
    void bad_no_index() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5, prefPos:11
      code: compiler.err.dc.no.content
      body: {@index
    ]
    Text[TEXT, pos:12, }_def]
  body: empty
  block tags: empty
]
*/

}
