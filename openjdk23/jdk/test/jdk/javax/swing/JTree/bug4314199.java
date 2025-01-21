/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */


/*
 * @test
 * @bug 4314199
 * @summary Tests that JTree repaints correctly in a container with a JMenu
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual bug4314199
 */

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class bug4314199 {

    private static final String INSTRUCTIONS = """
            Select the last tree node (marked "Here") and click on the "Menu".
            Look at the vertical line connecting nodes "Bug" and "Here".
            If the connecting line does not disappear when the "Menu" drops down,
            press 'Pass' else 'Fail'. """;

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        PassFailJFrame.builder()
                .title("JTree Instructions")
                .instructions(INSTRUCTIONS)
                .rows(6)
                .splitUI(bug4314199::createAndShowGUI)
                .build()
                .awaitAndCheck();
    }

    private static JPanel createAndShowGUI() {
        JMenuBar mb = new JMenuBar();

        // needed to exactly align left edge of menu and angled line of tree
        mb.add(Box.createHorizontalStrut(27));

        JMenu mn = new JMenu("Menu");
        JMenuItem mi = new JMenuItem("MenuItem");
        mn.add(mi);
        mb.add(mn);

        DefaultMutableTreeNode n1 = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode n2 = new DefaultMutableTreeNode("Duke");
        n1.add(n2);
        DefaultMutableTreeNode n3 = new DefaultMutableTreeNode("Bug");
        n2.add(n3);
        n3.add(new DefaultMutableTreeNode("Blah"));
        n3.add(new DefaultMutableTreeNode("Blah"));
        n3.add(new DefaultMutableTreeNode("Blah"));
        DefaultMutableTreeNode n4 = new DefaultMutableTreeNode("Here");
        n2.add(n4);

        JTree tree = new JTree(new DefaultTreeModel(n1));
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.expandPath(new TreePath(new Object[]{n1, n2, n3}));

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setSize(200, 200);
        p.add(mb, BorderLayout.NORTH);
        p.add(tree);
        return p;
    }
}
