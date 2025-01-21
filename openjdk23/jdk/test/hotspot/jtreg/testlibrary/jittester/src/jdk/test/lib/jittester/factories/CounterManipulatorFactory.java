/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.LocalVariable;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Statement;
import jdk.test.lib.jittester.UnaryOperator;
import jdk.test.lib.jittester.loops.CounterManipulator;

class CounterManipulatorFactory extends Factory<CounterManipulator> {
    private final LocalVariable counter;

    CounterManipulatorFactory(LocalVariable counter) {
        this.counter = counter;
    }

    @Override
    public CounterManipulator produce() throws ProductionFailedException {
        // We'll keep it simple for the time being..
        IRNode manipulator = new UnaryOperator(OperatorKind.POST_DEC, counter);
        return new CounterManipulator(new Statement(manipulator, false));
    }
}
