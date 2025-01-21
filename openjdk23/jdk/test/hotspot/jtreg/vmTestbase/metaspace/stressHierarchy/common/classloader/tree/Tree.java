/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package metaspace.stressHierarchy.common.classloader.tree;

import java.util.*;

import metaspace.stressHierarchy.common.classloader.StressClassloader;
import metaspace.stressHierarchy.common.generateHierarchy.NodeDescriptor;


/**
 * Tree of hierarchy.
 *
 * The tree consists of {@link Nodes}s. Each node contains classloader {@link metaspace.stressHierarchy.common.classloader.StressClassloader} that is
 * associated with one class (or interface) and can load only it. Edge in this
 * tree correspond to parent relation between corresponding classloaders.
 * Each classloader delegates to parent only after failed attempt to load class itself.
 *
 */
public class Tree {

    private final List<Node> allNodes = new LinkedList<Node>(); //order matters

    private int maxLevel;

    private Node root;

    public void cleanupLevel(int level) {
        for (Node node : getNodesInLevel(level)) {
            node.cleanup();
        }
    }

    public int getMaxLevel() { return maxLevel; }

    public List<Node> getNodes() {
        return allNodes;
    }

    public List<Node> getNodesInLevel(int level) {
        List<Node> result = new LinkedList<Node>();
        for (Iterator<Node> iter = allNodes.iterator(); iter.hasNext();) {
            Node current = iter.next();
            if (current.getLevel() == level) {
                result.add(current);
            }
        }
        return result;
    }

    private Node findParent(Node node) {
        for (Iterator<Node> iter = allNodes.iterator(); iter.hasNext();) {
            Node current = iter.next();
            if (current.equals(node)) {
                return current;
            }
        }
        return null;
    }

    public void addNode(NodeDescriptor nodeDescriptor) {
        if (nodeDescriptor.level == 0) {
            root = new Node(0, 0);
            root.setClassLoader(new StressClassloader(nodeDescriptor, null));
            allNodes.add(root);
            return;
        }
        Node newOne = new Node(nodeDescriptor.level, nodeDescriptor.index);
        Node parent = findParent(new Node(nodeDescriptor.level - 1, nodeDescriptor.parentIndex));

        //add a payload to new node
        newOne.setClassLoader(new StressClassloader(nodeDescriptor, parent.getClassLoader()));

        newOne.setParent(parent);
        allNodes.add(newOne);
        maxLevel = maxLevel < newOne.getLevel() ? newOne.getLevel() : maxLevel;
    }

}
