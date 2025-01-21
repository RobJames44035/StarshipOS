/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7021614 8078320 8247788 8273244 8298405
 * @summary extend com.sun.source API to support parsing javadoc comments
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester ElementTest.java
 */

class ElementTest {
    /**
     * <p>para</p>
     */
    void simple() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 2
    StartElement[START_ELEMENT, pos:1
      name:p
      attributes: empty
    ]
    Text[TEXT, pos:4, para]
  body: 1
    EndElement[END_ELEMENT, pos:8, p]
  block tags: empty
]
*/

    /**
     * abc <hr/>
     */
    void self_closing() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 2
    Text[TEXT, pos:1, abc_]
    StartElement[START_ELEMENT, pos:5
      name:hr
      attributes: empty
    ]
  body: empty
  block tags: empty
]
*/

    /**
     * abc < def
     */
    void bad_lt() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, _def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc > def
     */
    void bad_gt() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Text[TEXT, pos:1, abc_>_def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc <p 123> def
     */
    void bad_chars_start();
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, p_123>_def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc </p 123> def
     */
    void bad_chars_end();
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, /p_123>_def]
  body: empty
  block tags: empty
]
*/

    /**
     * abc <hr
     */
    void unterminated_eoi() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, hr]
  body: empty
  block tags: empty
]
*/

    /**
     * abc <hr
     * @author jjg
     */
    void unterminated_block() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, hr]
  body: empty
  block tags: 1
    Author[AUTHOR, pos:10
      name: 1
        Text[TEXT, pos:18, jjg]
    ]
]
*/


    /**
     * abc </p
     */
    void unterminated_end_eoi() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, /p]
  body: empty
  block tags: empty
]
*/

    /**
     * abc </p
     * @author jjg
     */
    void unterminated_end_block() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc_]
    Erroneous[ERRONEOUS, pos:5
      code: compiler.err.dc.malformed.html
      body: <
    ]
    Text[TEXT, pos:6, /p]
  body: empty
  block tags: 1
    Author[AUTHOR, pos:10
      name: 1
        Text[TEXT, pos:18, jjg]
    ]
]
*/

    /**
     * abc
     * <!-- comment -->
     * def
     */
    void comment() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 3
    Text[TEXT, pos:1, abc|_]
    Comment[COMMENT, pos:6, <!--_comment_-->]
    Text[TEXT, pos:22, |_def]
  body: empty
  block tags: empty
]
*/

// In Markdown mode, < does not introduce an element
    ///abc < def
    public void markdown() { }
/*
DocComment[DOC_COMMENT, pos:0
  firstSentence: 1
    RawText[MARKDOWN, pos:0, abc_<_def]
  body: empty
  block tags: empty
]
*/

}
