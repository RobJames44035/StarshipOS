/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.PrintVariables;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.types.TypeKlass;

class PrintVariablesFactory extends Factory<PrintVariables> {
    private final TypeKlass ownerClass;
    private final int level;

    PrintVariablesFactory(TypeKlass ownerClass, int level) {
        this.ownerClass = ownerClass;
        this.level = level;
    }

    @Override
    public PrintVariables produce() throws ProductionFailedException {
        return new PrintVariables(ownerClass, level);
    }
}
