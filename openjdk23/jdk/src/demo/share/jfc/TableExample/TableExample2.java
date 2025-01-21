/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/**
 * A minimal example, using the JTable to view data from a database.
 *
 * @author Philip Milne
 */
public class TableExample2 {

    public TableExample2(String URL, String driver, String user,
            String passwd, String query) {
        JFrame frame = new JFrame("Table");
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JDBCAdapter dt = new JDBCAdapter(URL, driver, user, passwd);
        dt.executeQuery(query);

        // Create the table
        JTable tableView = new JTable(dt);

        JScrollPane scrollpane = new JScrollPane(tableView);
        scrollpane.setPreferredSize(new Dimension(700, 300));

        frame.getContentPane().add(scrollpane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Needs database parameters eg. ...");
            System.err.println(
                    "java TableExample2 \"jdbc:derby://localhost:1527/sample\" "
                    + "org.apache.derby.jdbc.ClientDriver app app "
                    + "\"select * from app.customer\"");
            return;
        }

        // Trying to set Nimbus look and feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TableExample2.class.getName()).log(Level.SEVERE,
                    "Failed to apply Nimbus look and feel", ex);
        }

        new TableExample2(args[0], args[1], args[2], args[3], args[4]);
    }
}
