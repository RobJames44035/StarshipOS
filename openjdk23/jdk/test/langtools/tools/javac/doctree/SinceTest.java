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
 * @run main DocCommentTester SinceTest.java
 */

class SinceTest {
    /**
     * abc.
     * @since then &amp; now.
     */
    void standard() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Text[TEXT, pos:1, abc.]
  body: empty
  block tags: 1
    Since[SINCE, pos:7
      body: 3
        Text[TEXT, pos:14, then_]
        Entity[ENTITY, pos:19, amp]
        Text[TEXT, pos:24, _now.]
    ]
]
*/

}
