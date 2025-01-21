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
 * @run main DocCommentTester SerialDataTest.java
 */

class SerialDataTest {
    /**
     * @serialData description
     */
    void writeObject(ObjectOutputStream stream) { }
/*
DocComment[DOC_COMMENT, pos:1
  firstSentence: empty
  body: empty
  block tags: 1
    SerialData[SERIAL_DATA, pos:1
      description: 1
        Text[TEXT, pos:13, description]
    ]
]
*/

}

