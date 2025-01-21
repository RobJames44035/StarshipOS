/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
  @test
  @bug 4114268
  @key headful
  @summary Checkbox.setCheckboxGroup(null) alters selection for CB's previous CBGroup
*/

import java.awt.Checkbox;
import java.awt.CheckboxGroup;

public class NullCheckboxGroupTest {


    public static void main(String[] args) {
        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox chbox1 = new Checkbox("First", cbg, true);
        Checkbox chbox2 = new Checkbox("Second", cbg, false);

        chbox2.setCheckboxGroup(null);

        System.out.println("chbox1="+chbox1);
        System.out.println("chbox2="+chbox2);
        System.out.println("cbg="+cbg);

        if (cbg.getSelectedCheckbox() != chbox1) {
            System.out.println("FAILED");
            throw new RuntimeException("Test FAILED");
        } else {
            System.out.println("PASSED");
        }
    }
 }
