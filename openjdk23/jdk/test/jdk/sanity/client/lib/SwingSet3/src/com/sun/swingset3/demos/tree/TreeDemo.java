/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package com.sun.swingset3.demos.tree;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * JTree Demo
 *
 * @version 1.13 11/17/05
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "JTree Demo",
        category = "Data",
        description = "Demonstrates JTree, a component which supports display/editing of hierarchical data",
        sourceFiles = {
            "com/sun/swingset3/demos/tree/TreeDemo.java",
            "com/sun/swingset3/demos/ResourceManager.java",
            "com/sun/swingset3/demos/tree/resources/tree.txt",
            "com/sun/swingset3/demos/tree/resources/TreeDemo.properties",
            "com/sun/swingset3/demos/tree/resources/images/TreeDemo.gif"
        }
)
public class TreeDemo extends JPanel {

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());
    public static final String DEMO_TITLE = TreeDemo.class.getAnnotation(DemoProperties.class).value();

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(DEMO_TITLE);

        frame.getContentPane().add(new TreeDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * TreeDemo Constructor
     */
    public TreeDemo() {
        setLayout(new BorderLayout());

        add(new JScrollPane(createTree()), BorderLayout.CENTER);
    }

    private JTree createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(resourceManager.getString("TreeDemo.music"));
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode artist = null;
        DefaultMutableTreeNode record = null;

        // open tree data
        URL url = getClass().getResource("resources/tree.txt");

        try {
            // convert url to buffered string
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);

            // read one line at a time, put into tree
            String line = reader.readLine();
            while (line != null) {
                // System.out.println("reading in: ->" + line + "<-");
                char linetype = line.charAt(0);
                switch (linetype) {
                    case 'C':
                        category = new DefaultMutableTreeNode(line.substring(2));
                        top.add(category);
                        break;
                    case 'A':
                        if (category != null) {
                            category.add(artist = new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    case 'R':
                        if (artist != null) {
                            artist.add(record = new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    case 'S':
                        if (record != null) {
                            record.add(new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    default:
                        break;
                }
                line = reader.readLine();
            }
        } catch (IOException ignored) {
        }

        JTree tree = new JTree(top) {
            @Override
            public Insets getInsets() {
                return new Insets(5, 5, 5, 5);
            }
        };

        tree.setEditable(true);

        return tree;
    }
}
