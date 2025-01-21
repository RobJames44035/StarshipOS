/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6327047 8168851
 * @summary verify that TerminalFactory.getDefault() works
 * @author Andreas Sterbenz
 * @modules java.smartcardio/javax.smartcardio
 * @run main/manual TestDefault
 */

// This test requires special hardware.

import java.util.List;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

public class TestDefault {

    public static void main(String[] args) throws Exception {
        TerminalFactory factory = TerminalFactory.getDefault();
        System.out.println("Type: " + factory.getType());
        List<CardTerminal> terminals = factory.terminals().list();
        if (terminals.isEmpty()) {
            System.out.println("Skipping the test: " +
                    "no card terminals available");
            return;
        }
        System.out.println("Terminals: " + terminals);

        System.out.println("OK.");
    }

}
