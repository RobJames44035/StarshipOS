/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8334394
 * @summary ensure there is no race condition in Class::protectionDomain
 * @run main/othervm ProtectionDomainRace
 */
import javax.security.auth.Subject;
import java.security.PrivilegedAction;

/**
 * Without the code fix, this test would fail with
 * java.lang.AssertionError: sun.security.util.ResourcesMgr (PD)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller.checkInjectedInvoker(MethodHandleImpl.java:1209)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller.makeInjectedInvoker(MethodHandleImpl.java:1110)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller$1.computeValue(MethodHandleImpl.java:1117)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller$1.computeValue(MethodHandleImpl.java:1114)
 *         at java.base/java.lang.ClassValue.getFromHashMap(ClassValue.java:229)
 *         at java.base/java.lang.ClassValue.getFromBackup(ClassValue.java:211)
 *         at java.base/java.lang.ClassValue.get(ClassValue.java:117)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller.bindCallerWithInjectedInvoker(MethodHandleImpl.java:1089)
 *         at java.base/java.lang.invoke.MethodHandleImpl$BindCaller.bindCaller(MethodHandleImpl.java:1077)
 *         at java.base/java.lang.invoke.MethodHandleImpl.bindCaller(MethodHandleImpl.java:1032)
 *         at java.base/java.lang.invoke.MethodHandles$Lookup.maybeBindCaller(MethodHandles.java:4149)
 *         at java.base/java.lang.invoke.MethodHandles$Lookup.getDirectMethodCommon(MethodHandles.java:4133)
 *         at java.base/java.lang.invoke.MethodHandles$Lookup.getDirectMethodNoSecurityManager(MethodHandles.java:4077)
 *         at java.base/java.lang.invoke.MethodHandles$Lookup.getDirectMethodForConstant(MethodHandles.java:4326)
 *         at java.base/java.lang.invoke.MethodHandles$Lookup.linkMethodHandleConstant(MethodHandles.java:4274)
 *         at java.base/java.lang.invoke.MethodHandleNatives.linkMethodHandleConstant(MethodHandleNatives.java:628)
 *         at java.base/sun.security.util.ResourcesMgr.getBundle(ResourcesMgr.java:54)
 *         at java.base/sun.security.util.ResourcesMgr.getString(ResourcesMgr.java:40)
 *         at java.base/javax.security.auth.Subject.doAs(Subject.java:517)
 *         ...
 * as the Class::protectionDomain might assign different objects to the (original) allPermDomain field.
 */
public class ProtectionDomainRace {
    private static volatile Throwable failed = null;
    public static void main(String[] args) throws Throwable {
        PrivilegedAction<?> pa = () -> null;
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Subject.doAs(null, pa);
                } catch (Throwable t) {
                    failed = t;
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 100; i++) {
            threads[i].join();
        }
        if (failed != null) {
            throw failed;
        }
    }
}
