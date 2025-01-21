/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that AbstractSpinnerModel constructor and methods do not throw
 *          unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessAbstractSpinnerModel
 */

public class HeadlessAbstractSpinnerModel {
    public static void main (String[] args){
        AbstractSpinnerModel model = new AbstractSpinnerModel(){
            public Object getValue() { return null; }
            public void setValue(Object value) {}
            public Object getNextValue() { return null; }
            public Object getPreviousValue() { return null; }
        };
        model.getPreviousValue();
        model.getNextValue();
        model.setValue("next");
        model.getValue();
    }
}
