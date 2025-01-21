/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.stress.gcbasher;

import java.io.IOException;

/*
 * @test TestGCBasherWithZ
 * @key stress
 * @library /
 * @requires vm.gc.Z
 * @requires vm.flavor == "server" & !vm.emulatedClient
 * @summary Stress ZGC
 * @run main/othervm/timeout=200 -Xlog:gc*=info -Xmx384m -server -XX:+UseZGC gc.stress.gcbasher.TestGCBasherWithZ 120000
 */

/*
 * @test TestGCBasherDeoptWithZ
 * @key stress
 * @library /
 * @requires vm.gc.Z
 * @requires vm.flavor == "server" & !vm.emulatedClient & vm.opt.ClassUnloading != false
 * @summary Stress ZGC with nmethod barrier forced deoptimization enabled.
 * @run main/othervm/timeout=200 -Xlog:gc*=info,nmethod+barrier=trace -Xmx384m -server -XX:+UseZGC
 *   -XX:+UnlockDiagnosticVMOptions -XX:+DeoptimizeNMethodBarriersALot -XX:-Inline
 *   gc.stress.gcbasher.TestGCBasherWithZ 120000
 */

public class TestGCBasherWithZ {
    public static void main(String[] args) throws IOException {
        TestGCBasher.main(args);
    }
}
