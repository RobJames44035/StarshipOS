/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package test.astro;

import static jaxp.library.JAXPTestUtilities.filenameToURL;
import static test.astro.AstroConstants.DECXSL;
import static test.astro.AstroConstants.RADECXSL;
import static test.astro.AstroConstants.RAXSL;
import static test.astro.AstroConstants.STYPEXSL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class StreamFilterFactoryImpl extends SourceFilterFactory {
    @Override
    protected Source getSource(String xslFileName) {
        return new StreamSource(filenameToURL(xslFileName));
    }

    @Override
    protected String getRAXsl() {
        return RAXSL;
    }

    @Override
    protected String getDECXsl() {
        return DECXSL;
    }

    @Override
    protected String getRADECXsl() {
        return RADECXSL;
    }

    @Override
    protected String getStellarXsl() {
        return STYPEXSL;
    }
}
