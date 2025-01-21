/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lambda.chain;

import org.openjdk.jmh.infra.Blackhole;

public class ChainBase {

    protected void process(Blackhole bh, Level start) {
        Level curr = start;
        while (true) {
            curr = curr.up();
            if(curr == null) break;
            bh.consume(curr);
        }
    }
}
