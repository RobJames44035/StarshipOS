/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform.util;

import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;

import org.testng.Assert;
import org.xml.sax.InputSource;

import transform.VersionDefaultHandler;

public class SAXUtil extends TransformerUtil {

    private static SAXUtil instance = null;

    /** Creates a new instance of SAXUtil */
    private SAXUtil() {

    }

    public static synchronized SAXUtil getInstance() throws Exception {
        if (instance == null)
            instance = new SAXUtil();
        return instance;
    }

    public Source prepareSource(InputStream is) throws Exception {
        return new SAXSource(new InputSource(is));
    }

    public Result prepareResult() throws Exception {
        VersionDefaultHandler dh = new VersionDefaultHandler();
        return new SAXResult(dh);
    }

    public void checkResult(Result result, String inputVersion) throws Exception {
        String version = ((VersionDefaultHandler) ((SAXResult) result).getHandler()).getVersion();
        Assert.assertTrue(inputVersion.equals(version), "Expected XML Version is 1.1, but actual version " + version);
    }

    public void checkResult(Result result, String inputVersion, String encoding) throws Exception {
        checkResult(result, inputVersion);
        String resultEncoding = ((VersionDefaultHandler) ((SAXResult) result).getHandler()).getEncoding();
        Assert.assertTrue(encoding.equals(resultEncoding), "Expected XML Version is " + encoding + " , but actual  encoding " + resultEncoding);
    }
}
