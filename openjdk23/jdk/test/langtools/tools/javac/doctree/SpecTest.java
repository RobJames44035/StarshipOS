/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 6251738 8226279
 * @summary javadoc should support a new at-spec tag
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DocCommentTester
 * @run main DocCommentTester SpecTest.java
 */

class SpecTest {

    /**
     * abc.
     * @spec http://example.com title
     */
    void block() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Text[TEXT, pos:1, abc.]
  body: empty
  block tags: 1
    Spec[SPEC, pos:7
      url:
        Text[TEXT, pos:13, http://example.com]
      title: 1
        Text[TEXT, pos:32, title]
    ]
]
*/

    /**
     * abc.
     * @spec
     */
    void bad_no_url() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Text[TEXT, pos:1, abc.]
  body: empty
  block tags: 1
    Erroneous[ERRONEOUS, pos:7, prefPos:11
      code: compiler.err.dc.no.url
      body: @spec
    ]
]
*/

    /**
     * abc.
     * @spec http://example.com
     */
    void bad_no_label() {}
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: 1
    Text[TEXT, pos:1, abc.]
  body: empty
  block tags: 1
    Erroneous[ERRONEOUS, pos:7, prefPos:30
      code: compiler.err.dc.no.title
      body: @spec_http://example.com
    ]
]
*/

}
