/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package test.astro;

import org.xml.sax.InputSource;

/*
 * Interface for all input source factory objects. The default implementation
 * 'InputSourceFactoryImpl' is provided as a straight forward factory
 * class that creates a new sax input source from a filename.
 *
 */
public interface InputSourceFactory {
    /*
     * Creates a new sax InputSource object from a filename.
     * Also sets the system id of the input source.
     * @param file filename of the XML input to create the input source.
     */
    InputSource newInputSource(String file) throws Exception;
}
