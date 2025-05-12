package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.models.TreeNode;

public class BinarySearchTree {
    public TreeNode mRoot;

    public BinarySearchTree() {}

    public TreeNode insertNode(TreeNode root, int value){
        TreeNode newNode = new TreeNode(value);
        if(root == null) return newNode;

        if(value < root.val){
            if(root.left == null){
                root.left = newNode;
            }else {
                insertNode(root.left, value);
            }
        }else{
            if(root.right == null){
                root.right = newNode;
            }else{
                insertNode(root.right, value);
            }
        }

        return root;
    }

    public TreeNode deleteNode(TreeNode root, int key){
        if(root == null) return null;

        if(key < root.val){
            root.left = deleteNode(root.left, key);
        }else if(key > root.val){
            root.right = deleteNode(root.right, key);
        }else{
            if(root.left == null && root.right == null){
                return null;
            }
            if(root.left != null && root.right == null){
                return root.left;
            }
            if(root.left == null && root.right != null){
                return root.right;
            }
            TreeNode leftMode = findLeftModeNode(root.right);
            root.val = leftMode.val;
            root.right = deleteNode(root.right, leftMode.val);
        }

        return root;
    }

    public TreeNode findLeftModeNode(TreeNode root) {
        if (root == null)
            return null;

        TreeNode leftMode = root;
        while (leftMode.left != null) {
            leftMode = leftMode.left;
        }
        return leftMode;
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;

        if (val < root.val) {
            searchBST(root.left, val);
        } else if (val > root.val) {
            searchBST(root.right, val);
        }
        return root;

    }

    // N - L - R
    public static void preOrder(TreeNode root) {
        if (root == null)
            return;
        System.out.println(root.val + " ");

        preOrder(root.left);
        preOrder(root.right);

    }

    // L - N - R
    public static void inOrder(TreeNode root) {
        if (root == null)
            return;

        inOrder(root.left);
        System.out.println(root.val + " ");
        inOrder(root.right);

    }

    // L - R - N
    public static void postOrder(TreeNode root) {
        if (root == null)
            return;

        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val + " ");

    }


}
