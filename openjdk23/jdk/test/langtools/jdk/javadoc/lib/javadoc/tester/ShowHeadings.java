/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package javadoc.tester;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public class ShowHeadings extends HtmlChecker {

    private int currLevel;

    private boolean copyContent;

    ShowHeadings(PrintStream out, Function<Path,String> fileReader) {
        super(out, fileReader);
    }

    @Override
    public void report() {
    }

    @Override
    public void startFile(Path path) {
        out.println("Headings: " + path);
        currLevel = 0;
    }

    @Override
    public void endFile() {
    }

    @Override
    public void docType(String doctype) {
    }

    @Override
    public void startElement(String name, Map<String,String> attrs, boolean selfClosing) {
        switch (name) {
            case "h1": case "h2": case "h3": case "h4": case "h5": case "h6":
                startHeading(name);
                break;
        }
    }

    @Override
    public void endElement(String name) {
        switch (name) {
            case "h1": case "h2": case "h3": case "h4": case "h5": case "h6":
                out.println();
                copyContent = false;
                break;
        }

    }

    private void startHeading(String h) {
        int level = Character.digit(h.charAt(1), 10);
        while (level > currLevel + 1) {
            currLevel++;
            out.println("*  ".repeat(currLevel - 1) + "H" + currLevel + ": *** MISSING ***");
        }
        currLevel = level;
        out.print("*  ".repeat(currLevel - 1) + "H" + currLevel + ": ");
        copyContent = true;
    }

    @Override
    public void content(String s) {
        if (copyContent) {
            out.print(s.replace("&nbsp;", " ")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replaceAll("\\s+", " ")
                );
        }
    }
}

