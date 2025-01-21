/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package metaspace.stressHierarchy.common;

import nsk.share.test.ExecutionController;
import nsk.share.test.Tests;
import metaspace.stressHierarchy.common.classloader.tree.Tree;

/**
 * Test checks that ancestors will not be reclaimed while references on descenders are alive.
 * We create hierarchy of classes, each loaded by dedicated classloader. Then we cleanup
 * references to all nodes except those that are in bottom level. Finally we check that classes
 * in bottom level are alive.
 */
public class StressHierarchy1 extends StressHierarchyBaseClass {

    public static void main(String[] args) {
        try {
            StressHierarchyBaseClass.args = args;
            Tests.runTest(new StressHierarchy1(), args);
        } catch (OutOfMemoryError error) {
            System.out.print("Got OOME: " + error.getMessage());
        }
    }

    @Override
    protected void runTestLogic(Tree tree, ExecutionController stresser)
            throws Throwable {

        for (int cleanupLevel = tree.getMaxLevel() - 1; cleanupLevel > 1; cleanupLevel--) {
            tree.cleanupLevel(cleanupLevel);
            log.info("cleanupLevel=" + cleanupLevel);
            triggerUnloadingHelper.triggerUnloading(stresser);
            if (!stresser.continueExecution()) {
                // pass test
                return;
            }
        }

        log.info("Check bottom level");
        performChecksHelper.checkLevelAlive(tree, tree.getMaxLevel());
    }

}
