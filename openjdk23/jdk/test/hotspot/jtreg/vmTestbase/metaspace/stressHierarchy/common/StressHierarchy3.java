/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package metaspace.stressHierarchy.common;

import metaspace.stressHierarchy.common.classloader.tree.Tree;
import nsk.share.test.ExecutionController;
import nsk.share.test.Tests;

/**
 * 1. Test case cleans up all levels except bottom, then checks that bottom level is alive (and whole tree).
 * 2. Then test cleans up whole tree and checks that it is reclaimed.
 */
public class StressHierarchy3 extends StressHierarchyBaseClass {

    public static void main(String[] args) {
        try {
            StressHierarchyBaseClass.args = args;
            Tests.runTest(new StressHierarchy3(), args);
        } catch (OutOfMemoryError error) {
            System.out.print("Got OOME: " + error.getMessage());
        }
    }

    @Override
    protected void runTestLogic(Tree tree, ExecutionController stresser)
            throws Throwable {

        for (int cleanupLevel = tree.getMaxLevel() - 1; cleanupLevel >= 0; cleanupLevel--) {
            if (! stresser.continueExecution()) { return; }
            tree.cleanupLevel(cleanupLevel);
            log.info("cleanupLevel=" + cleanupLevel);
        }

        triggerUnloadingHelper.triggerUnloading(stresser);

        if (! stresser.continueExecution()) { return; }

        log.info("Check bottom level alive ");
        performChecksHelper.checkLevelAlive(tree, tree.getMaxLevel());

        if (! stresser.continueExecution()) { return; }

        log.info("Cleanup all");
        tree.cleanupLevel(tree.getMaxLevel());

        triggerUnloadingHelper.triggerUnloading(stresser);

        log.info("Check bottom level reclaimed");
        performChecksHelper.checkLevelReclaimed(tree, tree.getMaxLevel());
    }

}
