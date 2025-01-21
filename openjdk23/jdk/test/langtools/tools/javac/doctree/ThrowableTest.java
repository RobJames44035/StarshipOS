/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7021614 8273244
 * @summary extend com.sun.source API to support parsing javadoc comments
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester ThrowableTest.java
 */

class ThrowableTest {
    /**
     * @throws Exception
     */
    void exception() throws Exception { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Throws[THROWS, pos:1
      exceptionName:
        Reference[REFERENCE, pos:9, Exception]
      description: empty
    ]
]
*/

    /**
     * @throws Exception text
     */
    void exception_text() throws Exception { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Throws[THROWS, pos:1
      exceptionName:
        Reference[REFERENCE, pos:9, Exception]
      description: 1
        Text[TEXT, pos:19, text]
    ]
]
*/

    /**
     * @throws Exception#member text
     */
    void exception_member() throws Exception { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Erroneous[ERRONEOUS, pos:1, prefPos:18
      code: compiler.err.dc.ref.unexpected.input
      body: @throws_Exception#member_text
    ]
]
*/

    /**
     * @throws Exception##fragment text
     */
    void exception_fragment() throws Exception { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Erroneous[ERRONEOUS, pos:1, prefPos:18
      code: compiler.err.dc.ref.unexpected.input
      body: @throws_Exception##fragment_text
    ]
]
*/
}

