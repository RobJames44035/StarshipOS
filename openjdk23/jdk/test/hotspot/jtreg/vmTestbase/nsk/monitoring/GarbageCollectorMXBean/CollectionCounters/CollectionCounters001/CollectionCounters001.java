/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key randomness
 *
 * @summary converted from VM Testbase nsk/monitoring/GarbageCollectorMXBean/CollectionCounters/CollectionCounters001.
 * VM Testbase keywords: [monitoring]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      nsk.monitoring.GarbageCollectorMXBean.CollectionCounters.CollectionCounters001.CollectionCounters001
 *      -testMode=directly
 */

package nsk.monitoring.GarbageCollectorMXBean.CollectionCounters.CollectionCounters001;

import java.util.List;
import java.lang.management.*;
import nsk.share.TestFailure;
import nsk.share.test.*;
import nsk.monitoring.share.*;
import nsk.share.gc.Algorithms;
import nsk.share.runner.RunParams;
import nsk.share.runner.RunParamsAware;

/**
 * Test counters from GarbageCollectorMXBean.
 *
 * In this test, we do some operations on heap and check that total
 * counters from all GarbageCollectorBeans do not decrease and actually
 * increase in some situations.
 *
 * This also checks that counters increase after MemoryMXBean.gc().
 *
 * Note: we assume that System.gc() increases collection count and
 * time. It may be false with -XX:+DisableExplicitGC.
 */
public class CollectionCounters001 extends MonitoringTestBase implements RunParamsAware, Initializable {
        private List<GarbageCollectorMXBean> gcBeans;
        private MemoryMXBean memory;
        Stresser stresser;
        RunParams runParams;
        private long collectionCount;
        private long collectionTime;
        private long collectionCountOld;
        private long collectionTimeOld;

        public void initialize() {
                gcBeans = monitoringFactory.getGarbageCollectorMXBeans();
                memory = monitoringFactory.getMemoryMXBean();
        }

        private void runOne(ExecutionController stresser) {
                updateCounters();
                validate();
                Algorithms.eatMemory(stresser);
                        if(stresser.continueExecution()) {
                    updateCounters();
                    validateNonTrivial();
                    System.gc();
                    updateCounters();
                    validateNonTrivial();
                    memory.gc();
                    updateCounters();
                    validateNonTrivial();
                        }
        }

        public void run() {
                stresser = new Stresser(runParams.getStressOptions());
                stresser.start(runParams.getIterations());
                while (stresser.iteration()) {
                    runOne(stresser);
                }
        }

        private void validate() {
                if (collectionCount < 0)
                        throw new TestFailure("collectionCount negative: " + collectionCount);
                if (collectionTime < 0)
                        throw new TestFailure("collectionTime negative: " + collectionTime);
                if (collectionCount < collectionCountOld)
                        throw new TestFailure("collectionCount decreased: " + collectionCount + " -> " + collectionCountOld);
                if (collectionTime < collectionTimeOld)
                        throw new TestFailure("collectionTime decreased: " + collectionTime + " -> " + collectionTimeOld);
        }

        private void validateNonTrivial() {
                if (collectionCount < 0)
                        throw new TestFailure("collectionCount negative: " + collectionCount);
                if (collectionTime < 0)
                        throw new TestFailure("collectionTime negative: " + collectionTime);
                if (collectionCount <= collectionCountOld)
                        throw new TestFailure("collectionCount not increased: " + collectionCount + " -> " + collectionCountOld);
                if (collectionTime < collectionTimeOld)
                        throw new TestFailure("collection time became smaller: " + collectionTime + " -> " + collectionTimeOld);
        }

        private void updateCounters() {
                collectionCountOld = collectionCount;
                collectionTimeOld = collectionTime;
                collectionCount = 0;
                collectionTime = 0;
                for (GarbageCollectorMXBean gcBean : gcBeans) {
                        collectionCount += gcBean.getCollectionCount();
                        collectionTime += gcBean.getCollectionTime();
                }
        }

        public static void main(String[] args) {
                Monitoring.runTest(new CollectionCounters001(), args);
        }

    @Override
    public void setRunParams(RunParams runParams) {
        this.runParams = runParams;
    }
}
