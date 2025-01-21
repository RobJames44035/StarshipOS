/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7021614 8273244 8284908
 * @summary extend com.sun.source API to support parsing javadoc comments
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester ParamTest.java
 */

class ParamTest {
    /**
     * @param x
     */
    void no_description(int x) { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Param[PARAM, pos:1
      name:
        Identifier[IDENTIFIER, pos:8, x]
      description: empty
    ]
]
*/

    /**
     * @param x description
     */
    void with_description(int x) { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Param[PARAM, pos:1
      name:
        Identifier[IDENTIFIER, pos:8, x]
      description: 1
        Text[TEXT, pos:10, description]
    ]
]
*/
    /**
     * @param <T type
     */
    <T> void type_param(int x) { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    Erroneous[ERRONEOUS, pos:1, prefPos:10
      code: compiler.err.dc.gt.expected
      body: @param_<T_type
    ]
]
*/

}
