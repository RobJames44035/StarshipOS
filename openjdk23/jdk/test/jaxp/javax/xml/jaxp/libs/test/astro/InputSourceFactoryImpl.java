/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package test.astro;

import static jaxp.library.JAXPTestUtilities.filenameToURL;

import org.xml.sax.InputSource;

/*
 * Default implementation of a input source factory. This is the most
 * straight forward way to create a sax input source and set it's
 * system id.
 *
 */
public class InputSourceFactoryImpl implements InputSourceFactory {
    public InputSourceFactoryImpl() {
    }

    public InputSource newInputSource(String filename) {
        InputSource catSrc = new InputSource(filename);
        catSrc.setSystemId(filenameToURL(filename));
        return catSrc;
    }
}
