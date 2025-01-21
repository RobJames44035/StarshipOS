/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8266666
 * @summary Implementation for snippets
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester SnippetTest.java
 */

class SnippetTest {
    /**
     * {@snippet attr1="val1" :
     *     Hello, Snippet!
     * }
     */
    void inline() { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Snippet[SNIPPET, pos:1
      attributes: 1
        Attribute[ATTRIBUTE, pos:11
          name: attr1
          vkind: DOUBLE
          value: 1
            Text[TEXT, pos:18, val1]
        ]
      body:
        Text[TEXT, pos:26, _____Hello,_Snippet!|_]
    ]
  body: empty
  block tags: empty
]
*/
}
