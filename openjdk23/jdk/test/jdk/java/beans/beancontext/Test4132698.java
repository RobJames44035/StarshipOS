/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4132698
 * @summary Tests that changes is not null when creating a BeanContextMembershipEvent
 * @author Janet Koenig
 */

import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextMembershipEvent;
import java.beans.beancontext.BeanContextSupport;

public class Test4132698 extends BeanContextMembershipEvent {
    public static void main(String[] args) throws Exception {
        BeanContextSupport bcs = new BeanContextSupport();
        try {
            new Test4132698(bcs, null);
        }
        catch (NullPointerException exception) {
            return; // expected null pointer exception
        }
        catch (Exception exception) {
            throw new Error("Should have caught NullPointerException but caught something else.", exception);
        }
        // If test got this far then expected exception wasn't thrown.
        throw new Error("Failure to catch null changes argument.");
    }

    public Test4132698(BeanContext bc, Object[] objects) {
        super(bc, objects);
    }
}
