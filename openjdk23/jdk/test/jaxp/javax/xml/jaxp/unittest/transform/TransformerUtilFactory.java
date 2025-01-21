/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import transform.util.DOMUtil;
import transform.util.SAXUtil;
import transform.util.StAXUtil;
import transform.util.StreamUtil;
import transform.util.TransformerUtil;

public class TransformerUtilFactory {

    public final static String DOM = "dom";

    public final static String SAX = "sax";

    public final static String StAX = "stax";

    public final static String STREAM = "stream";

    /** Creates a new instance of TransformerUtilFactory */
    private TransformerUtilFactory() {
    }

    public static TransformerUtil getUtil(String type) throws Exception {
        if (type.equals(DOM)) {
            return DOMUtil.getInstance();
        } else if (type.equals(STREAM))
            return StreamUtil.getInstance();
        else if (type.equals(SAX))
            return SAXUtil.getInstance();
        else if (type.equals(StAX))
            return StAXUtil.getInstance();
        else
            return null;
    }
}
