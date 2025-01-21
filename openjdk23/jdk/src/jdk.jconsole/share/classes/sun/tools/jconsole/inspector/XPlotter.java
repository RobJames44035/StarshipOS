/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.tools.jconsole.inspector;

import sun.tools.jconsole.Plotter;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class XPlotter extends Plotter {
    JTable table;
    public XPlotter(JTable table,
                    Plotter.Unit unit) {
        super(unit,0,false);
        this.table = table;
    }
    @Override
    public void addValues(long time, long... values) {
        super.addValues(time, values);
        table.repaint();
    }
}
