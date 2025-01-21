/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;

public abstract class Factory<T extends IRNode> {
    public abstract T produce() throws ProductionFailedException;
}
