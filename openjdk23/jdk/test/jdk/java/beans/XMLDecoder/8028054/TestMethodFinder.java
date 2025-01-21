/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import com.sun.beans.finder.MethodFinder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @test
 * @bug 8028054
 * @summary Tests that cached methods have synchronized access
 * @author Sergey Malenkov
 * @modules java.desktop/com.sun.beans.finder
 * @compile -XDignore.symbol.file TestMethodFinder.java
 * @run main TestMethodFinder
 */

public class TestMethodFinder {
    public static void main(String[] args) throws Exception {
        List<Class<?>> classes = Task.getClasses(4000);
        List<Method> methods = new ArrayList<>();
        for (Class<?> type : classes) {
            Collections.addAll(methods, type.getMethods());
        }
        Task.print("found " + methods.size() + " methods in " + classes.size() + " classes");

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tasks.add(new Task<Method>(methods) {
                @Override
                protected void process(Method method) throws NoSuchMethodException {
                    MethodFinder.findMethod(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
                }
            });
        }
        int alarm = 0;
        while (true) {
            int alive = 0;
            int working = 0;
            for (Task task : tasks) {
                if (task.isWorking()) {
                    working++;
                    alive++;
                } else if (task.isAlive()) {
                    alive++;
                }
            }
            if (alive == 0) {
                break;
            }
            Task.print(working + " out of " + alive + " threads are working");
            if ((working == 0) && (++alarm == 10)) {
                throw new RuntimeException("FAIL - DEADLOCK DETECTED");
            }
            Thread.sleep(1000);
        }
    }
}
