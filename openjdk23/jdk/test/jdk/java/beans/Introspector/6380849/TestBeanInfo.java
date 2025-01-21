/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6380849
 * @summary Tests BeanInfo finder
 * @modules java.desktop/java.beans:open
 * @author Sergey Malenkov
 */

import beans.FirstBean;
import beans.FirstBeanBeanInfo;
import beans.SecondBean;
import beans.ThirdBean;

import infos.SecondBeanBeanInfo;
import infos.ThirdBeanBeanInfo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Method;

public class TestBeanInfo implements Runnable {

    private static final String[] SEARCH_PATH = { "infos" }; // NON-NLS: package name

    public static void main(String[] args) throws InterruptedException {
        TestBeanInfo test = new TestBeanInfo();
        test.run();
        // the following tests fails on previous build
        ThreadGroup group = new ThreadGroup("$$$"); // NON-NLS: unique thread name
        Thread thread = new Thread(group, test);
        thread.start();
        thread.join();
    }

    private static void test(Class<?> type, Class<? extends BeanInfo> expected) {
        BeanInfo actual;
        try {
            actual = Introspector.getBeanInfo(type);
            type = actual.getClass();
            Method method = type.getDeclaredMethod("getTargetBeanInfo"); // NON-NLS: method name
            method.setAccessible(true);
            actual = (BeanInfo) method.invoke(actual);
        }
        catch (Exception exception) {
            throw new Error("unexpected error", exception);
        }
        if ((actual == null) && (expected != null)) {
            throw new Error("expected info is not found");
        }
        if ((actual != null) && !actual.getClass().equals(expected)) {
            throw new Error("found unexpected info");
        }
    }

    private boolean passed;

    public void run() {
        Introspector.flushCaches();

        test(FirstBean.class, FirstBeanBeanInfo.class);
        test(SecondBean.class, null);
        test(ThirdBean.class, null);
        test(ThirdBeanBeanInfo.class, ThirdBeanBeanInfo.class);

        Introspector.setBeanInfoSearchPath(SEARCH_PATH);
        Introspector.flushCaches();

        test(FirstBean.class, FirstBeanBeanInfo.class);
        test(SecondBean.class, SecondBeanBeanInfo.class);
        test(ThirdBean.class, null);
        test(ThirdBeanBeanInfo.class, ThirdBeanBeanInfo.class);
    }
}
