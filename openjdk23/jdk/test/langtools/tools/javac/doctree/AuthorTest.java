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
 * @run main DocCommentTester AuthorTest.java
 */

class AuthorTest {
    /** abc @author jjg &amp; others */
    void standard() { }
/*
DocComment[DOC_COMMENT, pos:0
  firstSentence: 3
    Text[TEXT, pos:0, abc_@author_jjg_]
    Entity[ENTITY, pos:16, amp]
    Text[TEXT, pos:21, _others]
  body: empty
  block tags: empty
]
*/

}
