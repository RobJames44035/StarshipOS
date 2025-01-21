/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform.util;

import static jaxp.library.JAXPTestUtilities.USER_DIR;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

public abstract class TransformerUtil {

    protected String type;

    protected final String TEMP_FILE = USER_DIR + "tmp.xml";

    public abstract Source prepareSource(InputStream is) throws Exception;

    public abstract Result prepareResult() throws Exception;

    public abstract void checkResult(Result result, String version) throws Exception;

    public void checkResult(Result result, String version, String encoding) throws Exception {
        checkResult(result, version);
    }

    public DocumentBuilder getDomParser() throws Exception {
        DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
        return docBF.newDocumentBuilder();
    }
}
