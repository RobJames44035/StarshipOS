/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.net.*;
import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

abstract class Task<T> implements Runnable {
    private transient boolean working = true;
    private final List<T> methods;
    private final Thread thread;

    Task(List<T> methods) {
        this.methods = methods;
        this.thread = new Thread(this);
        this.thread.start();
    }

    boolean isAlive() {
        return this.thread.isAlive();
    }

    boolean isWorking() {
        boolean working = this.working && this.thread.isAlive();
        this.working = false;
        return working;
    }

    @Override
    public void run() {
        long time = -System.currentTimeMillis();
        for (T method : this.methods) {
            this.working = true;
            try {
                for (int i = 0; i < 100; i++) {
                    process(method);
                }
            } catch (NoSuchMethodException ignore) {
            }
        }
        time += System.currentTimeMillis();
        print("thread done in " + time / 1000 + " seconds");
    }

    protected abstract void process(T method) throws NoSuchMethodException;

    static synchronized void print(Object message) {
        System.out.println(message);
        System.out.flush();
    }

    static List<Class<?>> getClasses(int count) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        FileSystem fs = null;

        try {
            fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        } catch (ProviderNotFoundException | FileSystemNotFoundException e) {
            throw new RuntimeException("FAIL - JRT Filesystem not found");
        }

        List<String> fileNames;
        Path modules = fs.getPath("/modules");

        Predicate<String> startsWithJavaBase          = path -> path.toString().startsWith("java.base/java");
        Predicate<String> startsWithJavaDesktop       = path -> path.toString().startsWith("java.desktop/java");
        Predicate<String> startsWithJavaDataTransfer  = path -> path.toString().startsWith("java.datatransfer/java");
        Predicate<String> startsWithJavaRMI           = path -> path.toString().startsWith("java.rmi/java");
        Predicate<String> startsWithJavaSmartCardIO   = path -> path.toString().startsWith("java.smartcardio/java");
        Predicate<String> startsWithJavaManagement    = path -> path.toString().startsWith("java.management/java");
        Predicate<String> startsWithJavaXML           = path -> path.toString().startsWith("java.xml/java");
        Predicate<String> startsWithJavaScripting     = path -> path.toString().startsWith("java.scripting/java");
        Predicate<String> startsWithJavaNaming        = path -> path.toString().startsWith("java.naming/java");
        Predicate<String> startsWithJavaSQL           = path -> path.toString().startsWith("java.sql/java");
        Predicate<String> startsWithJavaCompiler      = path -> path.toString().startsWith("java.compiler/java");
        Predicate<String> startsWithJavaLogging       = path -> path.toString().startsWith("java.logging/java");
        Predicate<String> startsWithJavaPrefs         = path -> path.toString().startsWith("java.prefs/java");

        fileNames = Files.walk(modules)
                .map(Path::toString)
                .filter(path -> path.toString().contains("java"))
                .map(s -> s.substring(9))  // remove /modules/ from beginning
                .filter(startsWithJavaBase
                    .or(startsWithJavaDesktop)
                    .or(startsWithJavaDataTransfer)
                    .or(startsWithJavaRMI)
                    .or(startsWithJavaSmartCardIO)
                    .or(startsWithJavaManagement)
                    .or(startsWithJavaXML)
                    .or(startsWithJavaScripting)
                    .or(startsWithJavaNaming)
                    .or(startsWithJavaSQL)
                    .or(startsWithJavaCompiler)
                    .or(startsWithJavaLogging)
                    .or(startsWithJavaPrefs))
                .map(s -> s.replace('/', '.'))
                .filter(path -> path.toString().endsWith(".class"))
                .map(s -> s.substring(0, s.length() - 6))  // drop .class
                .map(s -> s.substring(s.indexOf(".")))
                .filter(path -> path.toString().contains("java"))
                .map(s -> s.substring(s.indexOf("java")))
                .collect(Collectors.toList());

        ClassLoader scl = ClassLoader.getSystemClassLoader();
        for (String name : fileNames) {
            classes.add(Class.forName(name, false, scl));
            if (count == classes.size()) {
                break;
            }
        }

        return classes;
    }
}
