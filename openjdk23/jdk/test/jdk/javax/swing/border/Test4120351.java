/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4120351
 * @summary Tests that the methods createEtchedBorder(int type) and
 *          createEtchedBorder(int type, Color highlight, Color shadows) are added
 * @author Andrey Pikalev
 */

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

public class Test4120351 {
    public static void main(String[] args) {
        BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.WHITE);
        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.WHITE, Color.BLACK);
    }
}
