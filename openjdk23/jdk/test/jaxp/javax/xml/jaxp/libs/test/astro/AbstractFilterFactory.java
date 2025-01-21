/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.astro;

import static test.astro.AstroConstants.HTMLXSL;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;

public abstract class AbstractFilterFactory implements FilterFactory {
    @Override
    public TransformerHandler newRAFilter(double min, double max) throws TransformerConfigurationException, SAXException, ParserConfigurationException,
            IOException {
        TransformerHandler retval = getTransformerHandler(getRAXsl());
        Transformer xformer = retval.getTransformer();
        xformer.setParameter("ra_min_hr", String.valueOf(min));
        xformer.setParameter("ra_max_hr", String.valueOf(max));
        return retval;
    }

    @Override
    public TransformerHandler newDECFilter(double min, double max) throws TransformerConfigurationException, SAXException, ParserConfigurationException,
            IOException {
        TransformerHandler retval = getTransformerHandler(getDECXsl());
        Transformer xformer = retval.getTransformer();
        xformer.setParameter("dec_min_deg", String.valueOf(min));
        xformer.setParameter("dec_max_deg", String.valueOf(max));
        return retval;
    }

    @Override
    public TransformerHandler newRADECFilter(double rmin, double rmax, double dmin, double dmax) throws TransformerConfigurationException, SAXException,
            ParserConfigurationException, IOException {
        TransformerHandler retval = getTransformerHandler(getRADECXsl());
        Transformer xformer = retval.getTransformer();
        xformer.setParameter("ra_min_hr", String.valueOf(rmin));
        xformer.setParameter("ra_max_hr", String.valueOf(rmax));
        xformer.setParameter("dec_min_deg", String.valueOf(dmin));
        xformer.setParameter("dec_max_deg", String.valueOf(dmax));
        return retval;
    }

    @Override
    public TransformerHandler newStellarTypeFilter(String type) throws TransformerConfigurationException, SAXException, ParserConfigurationException,
            IOException {
        TransformerHandler retval = getTransformerHandler(getStellarXsl());
        Transformer xformer = retval.getTransformer();
        xformer.setParameter("type", type);
        return retval;
    }

    @Override
    public TransformerHandler newHTMLOutput() throws TransformerConfigurationException, SAXException, ParserConfigurationException, IOException {
        return getTransformerHandler(HTMLXSL);
    }

    abstract protected TransformerHandler getTransformerHandler(String xslFileName) throws SAXException, ParserConfigurationException,
            TransformerConfigurationException, IOException;

    abstract protected String getRAXsl();

    abstract protected String getDECXsl();

    abstract protected String getRADECXsl();

    abstract protected String getStellarXsl();
}
