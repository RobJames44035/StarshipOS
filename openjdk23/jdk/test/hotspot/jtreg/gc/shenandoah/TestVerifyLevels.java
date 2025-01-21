/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test id=default
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=0 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=1 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=2 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=3 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=4 TestVerifyLevels
 */

/*
 * @test id=generational
 * @requires vm.gc.Shenandoah
 *
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=0 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=1 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=2 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=3 TestVerifyLevels
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCMode=generational -XX:+UnlockDiagnosticVMOptions -Xmx128m -XX:+ShenandoahVerify -XX:ShenandoahVerifyLevel=4 TestVerifyLevels
 */
public class TestVerifyLevels {

    static final long TARGET_MB = Long.getLong("target", 1_000); // 1 Gb allocation

    static Object sink;

    public static void main(String[] args) throws Exception {
        long count = TARGET_MB * 1024 * 1024 / 16;
        for (long c = 0; c < count; c++) {
            sink = new Object();
        }
    }

}
