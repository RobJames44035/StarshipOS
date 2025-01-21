/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 *
 * @summary converted from VM Testbase gc/gctests/StringInternSync.
 * VM Testbase keywords: [gc, stress, stressopt, feature_perm_removal_jdk7, nonconcurrent]
 * VM Testbase readme:
 * The test verifies that String.intern is correctly synchronized.
 * Test interns same strings in different threads and verifies that all interned equal
 * strings are same objects.
 * This test interns a few large strings.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      -XX:+UnlockDiagnosticVMOptions
 *      -XX:+VerifyStringTableAtExit
 *      gc.gctests.StringInternSync.StringInternSync
 *      -ms low
 */

package gc.gctests.StringInternSync;

import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import nsk.share.TestBug;
import nsk.share.TestFailure;
import nsk.share.gc.*;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.gc.gp.MemoryStrategyAware;
import nsk.share.gc.gp.string.RandomStringProducer;

public class StringInternSync extends ThreadedGCTest implements MemoryStrategyAware {

    // The list of strings which will be interned
    static final List<String> stringsToIntern = new ArrayList();
    // The global container for references to internded strings
    static final List<List<String>> internedStrings = new ArrayList<List<String>>();
    // Approximate size occupied by all interned strings
    long sizeOfAllInteredStrings = 0;
    // maximum size of one string
    int maxStringSize;
    RandomStringProducer gp = new RandomStringProducer();
    MemoryStrategy memoryStrategy;
    ReadWriteLock rwlock = new ReentrantReadWriteLock();

    @Override
    public void setMemoryStrategy(MemoryStrategy memoryStrategy) {
        this.memoryStrategy = memoryStrategy;
    }

    private class StringGenerator implements Runnable {

        List<String> internedLocal;

        public StringGenerator(List<String> internedLocal) {
            this.internedLocal = internedLocal;
        }

        public void run() {
            try {
                rwlock.readLock().lock();
                internedLocal.clear();
                for (String str : stringsToIntern) {
                    // Intern copy of string
                    // and save reference to it
                    internedLocal.add(new String(str).intern());
                }
            } finally {
                rwlock.readLock().unlock();
            }

            // after each iteration 0 thread
            // lock our main resource and verify String.intern
            if (internedLocal == internedStrings.get(0)) {
                try {
                    rwlock.writeLock().lock();
                    // We select first list and compare all other with it
                    // if 2 strings are equal they should be the same "=="
                    List<String> interned = internedStrings.get(0);

                    for (List<String> list : internedStrings) {
                        if (list == interned) {
                            continue;
                        }
                        if (list.size() == 0) {
                            continue; // this thread haven't got lock
                        }

                        if (list.size() != interned.size()) {
                            throw new TestFailure("Size of interned string list differ from origial."
                                    + " interned " + list.size() + " original " + interned.size());
                        }
                        for (int i = 0; i < interned.size(); i++) {
                            String str = interned.get(i);
                            if (!str.equals(list.get(i))) {
                                throw new TestFailure("The interned strings are not the equals.");
                            }
                            if (str != list.get(i)) {
                                throw new TestFailure("The equal interned strings are not the same.");
                            }
                        }
                        list.clear();

                    }
                    interned.clear();
                    stringsToIntern.clear();
                    for (long currentSize = 0; currentSize <= sizeOfAllInteredStrings; currentSize++) {
                        stringsToIntern.add(gp.create(maxStringSize));
                        currentSize += maxStringSize;
                    }
                } finally {
                    rwlock.writeLock().unlock();
                }
            }
        }
    }

    @Override
    public void run() {
        sizeOfAllInteredStrings = 10 * 1024 * 1024; // let use 100 * strings of size 10000
        maxStringSize = (int) (sizeOfAllInteredStrings / memoryStrategy.getSize(sizeOfAllInteredStrings));
        log.debug("The overall size of interned strings  : " + sizeOfAllInteredStrings / (1024 * 1024) + "M");
        log.debug("The count of interned strings : " + sizeOfAllInteredStrings / maxStringSize);
        for (long currentSize = 0; currentSize <= sizeOfAllInteredStrings; currentSize++) {
            stringsToIntern.add(gp.create(maxStringSize));
            currentSize += maxStringSize;
        }
        super.run();
    }

    @Override
    protected Runnable createRunnable(int i) {
        ArrayList list = new ArrayList();
        internedStrings.add(list);
        return new StringGenerator(list);
    }

    public static void main(String[] args) {
        GC.runTest(new StringInternSync(), args);
    }
}
