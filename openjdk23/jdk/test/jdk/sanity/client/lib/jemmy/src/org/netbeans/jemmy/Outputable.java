/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

/**
 * Communicate the identity of the output streams or writers used by the
 * application. Communicate the identity of the input stream, too. Any object
 * with methods that generates print output should implement this interface.
 *
 * @see org.netbeans.jemmy.TestOut
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface Outputable {

    /**
     * Defines print output streams or writers.
     *
     * @param out Identify the streams or writers used for print output.
     * @see #getOutput
     */
    public void setOutput(TestOut out);

    /**
     * Returns print output streams or writers.
     *
     * @return an object that contains references to objects for printing to
     * output and err streams.
     * @see #setOutput
     */
    public TestOut getOutput();
}
