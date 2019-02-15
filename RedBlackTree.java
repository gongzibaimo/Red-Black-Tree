/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4_cs3345;

    public class RedBlackTree<E extends Comparable<E>> {
  // define root, count, red, black
    private Node root;
    private static final boolean red = false;
    private static final boolean black = true;
    //private String count = "";

    // to return parent of the node if there is a node
    private Node parentOf(Node mNode){
        if(mNode != null){
            return mNode.parent;
        }
        return null;
    }

    private boolean colorOf(Node mNode){
        if(mNode != null){
            return mNode.color;
        }
        return black;
    }

    private void setParent(Node mNode, Node parent){
        if(mNode != null){
            mNode.parent = parent;
        }
    }

    private void setColor(Node mNode, boolean color){
        if(mNode != null){
            mNode.color = color;
        }
    }

    //check if node is red or not
    private boolean isRed(Node mNode){
        return (mNode != null && mNode.color == red) ?true : false;
    }

    // check if node is black or not
    private boolean isBlack(Node mNode){
        return !isRed(mNode);
    }

    //set the color to red
    private void setRed(Node mNode){
        if(mNode != null){
            mNode.color = red;
        }
    }

    // set the color to black
    private void setBlack(Node mNode){
        if(mNode != null){
            mNode.color = black;
        }
    }


    // left rotate the node x
    private void leftRotate(Node x){
        // set node y a right child of x
        Node y = x.right;
        // if there exists a left child for y
        if(y.left != null){
            y.left.parent = x;
        }
        x.right = y.left;
        y.left = x;
        y.parent = x.parent;

        // if there x has parent, then
        if(x.parent != null){
            // if x is the left children of the parent,
            if(x.parent.left == x){
                x.parent.left = y;
            }
            // if x is the right children of the parent
            else{
                x.parent.right = y;
            }
        }
        // if x do not have parent
        else{
            this.root = y;
        }
        // and rotate the y to the position of x.parent
        x.parent = y;
    }

    // right rotate the node x
    private void rightRotate(Node x){
        // set y to the left child of x
        Node y = x.left;
        // if there exists right child for y
        if(y.right != null){
            y.right.parent = x;
        }

        y.parent = x.parent;
        x.left = y.right;
        y.right = x;

        // if there is a parent for x
        if(x.parent != null){
            // if x is the left child of x.parent
            if(x.parent.left == x){
                x.parent.left = y;
            }
            else{
                x.parent.right = y;
            }
        }
        else{
            this.root = y;
        }
        x.parent = y;
    }

    // to rotate or recolor the tree if needed
    private void insertFixUp(Node node){
        Node parent, grandParent;
        // if parent != null and parent is red
        while(((parent = parentOf(node)) != null) && isRed(parent)){
            grandParent = parentOf(parent);
            // case 1: if parent is the left child of gp
            if(grandParent.left == parent){
                // set uncle to the right of the gp
                Node uncle = grandParent.right;
               // System.out.println("print tree 1: " +  preOrder());
                // if uncle is red
                if(isRed(uncle)){
                    // no need to right or left rotate, just recolor parent and uncle to black,
                    //  gp to red
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(grandParent);

                    node = grandParent;
                    continue;
                }
                // if uncle is not red
                else{
                    // if insert node is right child of parent
                    if(parent.right == node){
                        // left rotate the parent node
                        leftRotate(parent);
                        Node temp = node;
                        node = parent;
                        parent = temp;
                    }
                    // else, right rotate the grand parent and set the parent to black, gp to red
                    setBlack(parent);
                    setRed(grandParent);
                    rightRotate(grandParent);
                }
            }
            else{
                // case2: if parent is the right child of gp
               // System.out.println("print tree 2: " +  preOrder());
                Node uncle = grandParent.left;
                if(isRed(uncle)){
                    // set parent and uncle to black, gp to red
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(grandParent);

                    node = grandParent;
                    continue;
                }
                else{
                    // if node is the left child of parent
                    if(parent.left == node){
                        // right rotate the parent node
                        rightRotate(parent);
                        Node temp = node;
                        node = parent;
                        parent = temp;
                    }
                    // else, left rotate gp and set parent to black, gp to red
                    setBlack(parent);
                    setRed(grandParent);
                    leftRotate(grandParent);
                }
            }
        }
        // if the node is first node in the tree, which is the root, then set the node to black
        if(root == node){
            setBlack(node);
        }
    }

      private void insert(Node node) {
          int compare;
          // set x = root
          Node x = this.root;
          Node y = null;
          // if there exists a root
          while(x != null){
              // set y = x and compare insert node to the x
              y = x;
              compare = node.element.compareTo(x.element);

              // if
              if(compare < 0){
                  x = x.left;
              }
              else{
                  x = x.right;
              }
          }

          node.parent = y;
          if(y != null){
              compare = node.element.compareTo(y.element);
              if(compare < 0){
                  y.left = node;
              }
              else{
                  y.right = node;
              }
          }
          else{
              this.root = node;
          }
          setRed(node);
          insertFixUp(node);
     }


    public boolean insert(E element) {
        Node node = new Node(black, element, null,null,null);
        if (element == null) {
            throw new NullPointerException("Error: there is no element");
        }
        else if(contains(element)){
            return  false;
        }
        else if(node != null){
            insert(node);
        }
            return true;
    }

    public boolean contains(E object){
        if(object == null){
            return false;
        }
        return contains(object, root);
    }

    private boolean contains(E e, Node x){
        if(x == null){
            return false;
        }
        int compare = e.compareTo(x.element);
        if(compare < 0){
            return contains(e, x.left);
        }
        else if(compare > 0){
            return contains(e, x.right);
        }
        else{
            return true;
        }
    }

    private String preOrder(Node node){
        String theCount = "";
        if(node != null) {
            if (node.color == red) {
                theCount = theCount + "*" ;
            }
            theCount = theCount + node.element + " ";
            theCount = theCount + preOrder(node.left);
            theCount = theCount + preOrder(node.right);
        }

        return theCount;
    }


    public String toString(){
        String theCount = "";
        if(root == null){
            theCount = "Tree is empty.";
        }
        else{
            theCount = preOrder(this.root);
        }
        return theCount;
    }

    private class Node{
        private E element;
        private Node left;
        private Node right;
        private Node parent;
        private boolean color;


        private Node(boolean mColor, E key, Node mParent, Node leftChild, Node rightChild){
            this.color = mColor;
            this.element = key;
            this.parent = mParent;
            this.left = leftChild;
            this.right = rightChild;
        }

    }
}
