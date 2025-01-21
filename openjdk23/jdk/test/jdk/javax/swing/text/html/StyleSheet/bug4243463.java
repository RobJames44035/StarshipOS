/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4243463
   @summary Tests that StyleSheet has following methods:
            public void addStyleSheet(StyleSheet ss);
            public void removeStyleSheet(StyleSheet ss);
            public Enumeration getStyleSheets()
   @run main bug4243463
*/

import javax.swing.text.html.StyleSheet;

public class bug4243463 {

    public static void main(String[] argv) throws Exception {
        StyleSheet main = new StyleSheet();
        StyleSheet ss = new StyleSheet();
        ss.addRule("p {color:red;}");

        main.addStyleSheet(ss);
        StyleSheet[] sheets = main.getStyleSheets();
        if (sheets.length != 1 || sheets[0] != ss) {
            throw new RuntimeException("getStyleSheets failed");
        }

        main.removeStyleSheet(ss);
        sheets = main.getStyleSheets();
        if (sheets != null) {
            throw new RuntimeException("StyleSheet is not removed");
        }
    }
}
