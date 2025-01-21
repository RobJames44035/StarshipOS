/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package gc.gctests.JumbleGC;

import java.util.Vector ;

class treeNode {
   static int memorySinkSize = 100;
   int info;
   treeNode left;
   treeNode right;
   int [] memory_sink;

   public treeNode(int info) {
      this.info = info;
      memory_sink = new int[memorySinkSize];
   }
}


public class Tree {

    private treeNode TreeRoot;      // root of Tree
    private int elementCount;   // number of elements in Tree
    Vector TreeValues;   // all the nodal values in the tree
                                // duplicated in this array.
    private int TreeValueIndex; // Where to store next Tree value


    Tree(int TreeSize) { TreeValues = new Vector(TreeSize); }

    synchronized void addElement(int o) {
      treeNode p,q;

      treeNode newnode = new treeNode(o);
      p = TreeRoot;
      q = null;

      while(p != null){
         q = p;
         if(newnode.info <= p.info)
            p = p.left;
         else
            p = p.right;
      }

      if ( q == null ){
          TreeRoot = newnode;
          return;
      }
      if (newnode.info <= q.info )
          q.left = newnode;
      else
          q.right = newnode;
      elementCount++;
      TreeValues.addElement(Integer.valueOf(o));
   }


int getTreeValue(int index) {
      Integer num;
      num = (Integer) TreeValues.elementAt(index);
      TreeValues.removeElementAt(index);
      return num.intValue();
   }


   int vectorSize(){ return TreeValues.size(); }


   synchronized void PrettyPrint(){
      Print(TreeRoot, "");
   }

   private void Print( treeNode root, String indent) {
      if(root == null){
         return;
      }
      Print(root.right, indent + "    ");
      System.out.println(indent + root.info);
      Print(root.left, indent + "    ");
   }


   synchronized int getNodeNumber(){return elementCount; }

   synchronized private treeNode findNode(int o) {
      treeNode p, q;
      p = TreeRoot;
      while(p != null && p.info != o){
          q = p;
          if (o < p.info )
              p = p.left;
          else if(o > p.info)
              p = p.right;
      }
      return p;
   }

   // destroy subtree rooted at treeNode containing int o
   // creating a subtree of garbage rooted at treeNode containing int o

   void destroySubTree(int o) {
       treeNode p,q;

       // find treeNode containing p.
       p = TreeRoot;
       q = null;
       while(p != null && p.info != o){
          q = p;
          if (o < p.info )
              p = p.left;
          else if(o > p.info)
              p = p.right;
       }

       if (p == null){  // couldnt find treeNode
           return;
       }

      //  decrease elementCount of tree by the number of treeNodes
      //  in sub-tree rooted at p

       elementCount -= getCount(p);
       if (q == null){ // destroy the whole tree
           TreeRoot = null;
           return;
       }

       if (p.info > q.info ) // deleting right child
           q.right = null;
       else
           q.left = null;
   }


   synchronized void deleteElement(int o){
      treeNode p,q;
      treeNode rc, sub_node, leftmost, leftmost_parent,s;

      p = TreeRoot;
      q = null;
      sub_node = null;

      while(p != null && p.info != o){
          q = p;
          if (o < p.info )
              p = p.left;
          else if(o > p.info)
              p = p.right;
      }

      if ( p == null) // couldnt find treeNode
           return;

      rc = p.right;

      if (rc == null){
          sub_node = p.left;
      } else if (rc.left == null) {
          rc.left = p.left;
          sub_node = p.right;
      }else if ( rc.left != null && rc.right != null) {
          s = rc;
          leftmost_parent = null;
          leftmost = null;
          while ( s != null){
              leftmost_parent = leftmost;
              leftmost = s;
              s = s.left;
          }
          leftmost_parent.left = leftmost.right;
          leftmost.left = p.left;
          leftmost.right= p.right;
          sub_node = leftmost;
      }

      if ( q == null ){
           TreeRoot = sub_node;
           return;
      }

      if (p.info > q.info ) // deleting right child
           q.right = sub_node;
      else
           q.left = sub_node;

      return;
   }


   private int getCount( treeNode root) {
      if (root == null )
         return 0;
      if (root.left == null && root.right == null)
         return 1;
      else
         return getCount(root.left) + getCount(root.right) + 1;
   }
}
