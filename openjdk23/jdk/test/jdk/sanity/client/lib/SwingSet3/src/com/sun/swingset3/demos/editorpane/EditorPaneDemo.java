/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package com.sun.swingset3.demos.editorpane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import com.sun.swingset3.DemoProperties;

/**
 * EditorPane Demo (was HTMLDemo in SwingSet2)
 */
@DemoProperties(
        value = "JEditorPane Demo",
        category = "Text",
        description = "Demonstrates JEditorPane, a text component which supports display and editing of rich text formats (such as HTML)",
        sourceFiles = {
                "com/sun/swingset3/demos/editorpane/EditorPaneDemo.java",
                "com/sun/swingset3/demos/editorpane/book/ant.html",
                "com/sun/swingset3/demos/editorpane/book/bug.html",
                "com/sun/swingset3/demos/editorpane/book/index.html",
                "com/sun/swingset3/demos/editorpane/book/king.html",
                "com/sun/swingset3/demos/editorpane/book/preface.html",
                "com/sun/swingset3/demos/editorpane/book/seaweed.html",
                "com/sun/swingset3/demos/editorpane/book/title.html",
                "com/sun/swingset3/demos/editorpane/book/editorpane/back.jpg",
                "com/sun/swingset3/demos/editorpane/book/editorpane/forward.jpg",
                "com/sun/swingset3/demos/editorpane/book/editorpane/header.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/ant.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/book.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/bug.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/bug2.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/COPYRIGHT",
                "com/sun/swingset3/demos/editorpane/book/Octavo/crest.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/king.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/micro.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/seaweed.jpg",
                "com/sun/swingset3/demos/editorpane/resources/EditorPaneDemo.properties",
                "com/sun/swingset3/demos/editorpane/resources/images/EditorPaneDemo.gif"
                }
)
public class EditorPaneDemo extends JPanel {

    public static final String DEMO_TITLE = EditorPaneDemo.class.getAnnotation(DemoProperties.class).value();
    public static final String[] SOURCE_FILES = EditorPaneDemo.class.getAnnotation(DemoProperties.class).sourceFiles();
    private JEditorPane html;

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(EditorPaneDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new EditorPaneDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * EditorPaneDemo Constructor
     */
    public EditorPaneDemo() {
        setLayout(new BorderLayout());

        try {
            URL url;
            // System.getProperty("user.dir") +
            // System.getProperty("file.separator");
            String path = null;
            try {
                path = "book/index.html";
                url = getClass().getResource(path);
            } catch (Exception e) {
                System.err.println("Failed to open " + path);
                url = null;
            }

            if (url != null) {
                html = new JEditorPane(url);
                html.setEditable(false);
                html.addHyperlinkListener(createHyperLinkListener());

                JScrollPane scroller = new JScrollPane();
                JViewport vp = scroller.getViewport();
                vp.add(html);
                add(scroller, BorderLayout.CENTER);
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private HyperlinkListener createHyperLinkListener() {
        return new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (e instanceof HTMLFrameHyperlinkEvent) {
                        ((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent(
                                (HTMLFrameHyperlinkEvent) e);
                    } else {
                        try {
                            html.setPage(e.getURL());
                        } catch (IOException ioe) {
                            System.out.println("IOE: " + ioe);
                        }
                    }
                }
            }
        };
    }
}
