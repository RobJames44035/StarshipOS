/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package jit.graph;

import jdk.test.lib.Utils;
import nsk.share.TestFailure;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Vector;

public class test6 {
    private static final int[] MethodID = {Globals.MethodID_Array[11]};

    private static Random localNumGen = new Random(Utils.SEED);
    private static final int maxEntries = 25;

    // flattens the binary tree into an array
    private void getSortedArray(Node root, int[] dataArray, int[] index) {
        if ((root != null) && (root != RBTree.treeNull)) {
            getSortedArray(root.getNode(Node.Left_son), dataArray, index);
            dataArray[index[0]++] = root.getKey();
            getSortedArray(root.getNode(Node.Right_son), dataArray, index);
        }
    }

    public synchronized void rbTest(Vector summation, Vector ID, Long functionDepth, Integer staticFunctionDepth)
            throws InvocationTargetException {
        Globals.appendSumToSummationVector(MethodID[0], summation);

        if (CGT.shouldFinish()) {
            return;
        }
        if (Globals.VERBOSE) {
            System.out.println("test6.rbTest");
        }

        if ((functionDepth.longValue() <= 0) && (staticFunctionDepth.intValue() <= 0)) {
            return;
        }
        MethodData methodCallStr;
        Long numFcalls;
        Integer staticFcalls;

        if (staticFunctionDepth.intValue() > 0) {
            numFcalls = functionDepth;
            staticFcalls = Integer.valueOf(staticFunctionDepth.intValue() - 1);
            methodCallStr = Globals.returnNextStaticMethod(MethodID[0]);
        } else {
            numFcalls = Long.valueOf(functionDepth.longValue() - 1);
            staticFcalls = staticFunctionDepth;
            methodCallStr = Globals.nextRandomMethod();
        }

        RBTree myTree = new RBTree();
        int numElements = 1 + localNumGen.nextInt(maxEntries);
        int dataArray[] = new int[numElements];
        boolean insertArray[] = new boolean[numElements];

        Vector temp = new Vector(numElements);
        // code guarantees no duplicates
        for (int i = 0; i < numElements; i++) {
            int nextKey = localNumGen.nextInt(16385);
            while (temp.indexOf(Integer.valueOf(nextKey)) != -1) {
                nextKey = localNumGen.nextInt(16385);
            }

            temp.addElement(Integer.valueOf(nextKey));
            dataArray[i] = nextKey;

            insertArray[i] = false;
        }
        temp = null;

        int numLoops = 10 + localNumGen.nextInt(1024);
        for (int i = 0; i < numLoops; i++) {
            int nextIndex = localNumGen.nextInt(numElements);
            if (!insertArray[nextIndex]) {
                myTree.RBInsert(dataArray[nextIndex]);
                insertArray[nextIndex] = true;
            } else {
                myTree.RBDelete(dataArray[nextIndex]);
                insertArray[nextIndex] = false;
            }
        }

        int numValid = 0;
        for (int i = 0; i < numElements; i++) {
            Node searchNode = myTree.Search(dataArray[i]);
            if (insertArray[i] && (searchNode == RBTree.treeNull)) {
                throw new TestFailure("Valid Node Not Found in Binary Tree. Node " + dataArray[i]);
            } else if ((!insertArray[i]) && (searchNode != RBTree.treeNull)) {
                throw new TestFailure("Deleted Node Found in Binary Tree. Node " + dataArray[i]);
            } else if (insertArray[i]) {
                numValid++;
            }
            // so that verification is only done once
            insertArray[i] = true;
        }

        int[] sortedArray = new int[numValid];
        getSortedArray(myTree.getRoot(), sortedArray, new int[]{0});

        for (int i = 1; i < numValid; i++) {
            if (sortedArray[i] <= sortedArray[i - 1]) {
                StringBuilder outStr = new StringBuilder("Actual ");
                for (int aSortedArray : sortedArray) {
                    outStr.append(aSortedArray)
                          .append(", ");
                }
                System.out.println("Binary Tree Property Not Held");
                System.out.println("Root " + myTree.getRoot()
                                                   .getKey());
                throw new TestFailure(outStr.toString());
            }
        }

        Globals.addFunctionIDToVector(methodCallStr.id, ID);
        Globals.callMethod(methodCallStr, summation, ID, numFcalls, staticFcalls);
    }
}
