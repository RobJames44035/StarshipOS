/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package gc.gctests.StringInternSyncWithGC;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import nsk.share.TestBug;
import nsk.share.TestFailure;
import nsk.share.gc.gp.string.RandomStringProducer;
import nsk.share.test.ExecutionController;
import nsk.share.test.LocalRandom;

class StringGenerator implements Runnable {

    private final RandomStringProducer gp;
    private final List<String> stringsToIntern;
    private final int threadNumber;
    private final int numberOfActions;
    private final int maxStringSize;
    private final StringInternSyncWithGC base;
    private static final ReadWriteLock RWLOCK = new ReentrantReadWriteLock();

    public StringGenerator(int threadId, StringInternSyncWithGC base) {
        this.base = base;
        threadNumber = threadId;
        stringsToIntern = base.getStringsToIntern();
        numberOfActions = base.getNumberOfThreads() > 4 ? base.getNumberOfThreads() : 4;
        gp = base.getGarbageProducer();
        maxStringSize = base.getMaxStringSize();
    }

    /* This field is public just to be not optimized */
    public String copy;

    @Override
    public void run() {
        ExecutionController stresser = base.getExecController();
        try {
            RWLOCK.readLock().lock();
            Object[] refToInterned = new Object[stringsToIntern.size()];
            for (int i = 0; i < stringsToIntern.size(); i++) {
                if (!stresser.continueExecution()) {
                    return;
                }
                int index = LocalRandom.nextInt(stringsToIntern.size());
                String str = stringsToIntern.get(index);
                int action = LocalRandom.nextInt(numberOfActions);

                /* We want to provoke a lot of collections for each interned copy
                 * so map should be "sparse".
                 */
                copy = new String(str);
                switch (action) {
                    case 0:
                        refToInterned[index] = copy.intern();
                        break;
                    case 1:
                        refToInterned[index] = new WeakReference(copy.intern());
                        break;
                    case 2:
                        refToInterned[index] = new SoftReference(copy.intern());
                        break;
                    default:
                        refToInterned[index] = null;
                        break;
                }
            }
            for (int index = 0; index < stringsToIntern.size(); index++) {
                verify(refToInterned[index], stringsToIntern.get(index));
            }
        } finally {
            RWLOCK.readLock().unlock();
        }

        if (threadNumber == 0) {
            try {
                RWLOCK.writeLock().lock();
                for (int index = 0; index < stringsToIntern.size(); index++) {
                    stringsToIntern.set(index, gp.create(maxStringSize).intern());
                }
            } finally {
                RWLOCK.writeLock().unlock();
            }
        }
    }

    /*
     * Verify that all exist interned strings in a map
     * a same objects.
     */
    private void verify(Object obj, String str) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Reference) {
            obj = ((Reference) obj).get();
            if (obj == null) {
                return;
            }
        }
        if (!(obj instanceof String)) {
            throw new TestBug("Expected String. Find :" + obj.getClass());
        }
        String interned = (String) obj;
        if (!interned.equals(str)) {
            throw new TestFailure("Interned not equals to original string.");
        }
        if (obj != str.intern()) {
            throw new TestFailure("Interned not same as original string.");
        }
    }
}
