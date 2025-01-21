/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Break;
import jdk.test.lib.jittester.ProductionFailedException;

class BreakFactory extends Factory<Break> {
    @Override
    public Break produce() throws ProductionFailedException {
        return new Break();
    }
}
