/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4909550
 * @summary 1.5 beta-b15 java compiler throws NPE
 * @author gafter
 *
 * @modules java.desktop
 * @compile InnerMethSig.java
 */

import javax.swing.text.html.*;
import javax.swing.text.*;

class InnerMethSig extends HTMLDocument {
    public AbstractDocument.AbstractElement createDefaultRoot() {
        return null;
    }
}
