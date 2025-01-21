/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Continue;
import jdk.test.lib.jittester.ProductionFailedException;

class ContinueFactory extends Factory<Continue> {
    @Override
    public Continue produce() throws ProductionFailedException {
        return new Continue();
    }
}
