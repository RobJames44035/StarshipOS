/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6520635
  @summary Test that modality and modal exclusion types are handled
correctly according Toolkit.isModalityTypeSupported() and
Toolkit.isModalExclusionTypeSupported() methods
  @author artem.ananiev: area=awt.modal
  @run main SupportedTest
*/

import java.awt.*;

public class SupportedTest
{
    public static void main(String[] args)
    {
        boolean passed = true;
        Toolkit tk = Toolkit.getDefaultToolkit();

        // check for modality types

        Frame f = new Frame("F");
        for (Dialog.ModalityType mt : Dialog.ModalityType.values())
        {
            if (!tk.isModalityTypeSupported(mt))
            {
                Dialog d = new Dialog(f, "D", mt);
                if (!d.getModalityType().equals(Dialog.ModalityType.MODELESS))
                {
                    System.err.println("Error: modality type " + mt + " is not supported\n" +
                                       "but a dialog with this modality type can be created");
                    passed = false;
                }
            }
        }

        // check for modal exclusion types
        for (Dialog.ModalExclusionType et : Dialog.ModalExclusionType.values())
        {
            if (!tk.isModalExclusionTypeSupported(et))
            {
                Frame g = new Frame("G");
                g.setModalExclusionType(et);
                if (!g.getModalExclusionType().equals(Dialog.ModalExclusionType.NO_EXCLUDE))
                {
                    System.err.println("Error: modal exclusion type " + et + "is not supported\n" +
                                       "but a window with this modal exclusion type can be created");
                    passed = false;
                }
            }
        }

        if (!passed)
        {
            throw new RuntimeException("Test FAILED: some of modality types and/or modal exclusion types are handled incorrectly");
        }
    }
}
