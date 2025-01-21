/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package pkg1;

import java.util.List;

/**
 * Test 3 passes.
 */

public class C1 {

    /**
     * Field in C1.
     */
    public UsedClass fieldInC1;

    /**
     * Method in C1.
     */
    public UsedClass methodInC1(UsedClass p) {
        return p;
    }

    public List<UsedClass> methodInC1ReturningType() {
        return null;
    }

    public void methodInC1ThrowsThrowable() throws UsedThrowable {
    }

    /*
     * this must not appear anywhere.
     */
    UsedClass methodInC1Protected(List<UsedClass> p){
        return p.get(0);
    }
}
