package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.models.TreeNode;

public class BinarySearchTree {
    public TreeNode mRoot;

    public BinarySearchTree() {
        mRoot = null;
    }

    public TreeNode insertNode(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }

        if (value < root.val) {
            root.left = insertNode(root.left, value);
        } else if (value > root.val) {
            root.right = insertNode(root.right, value);
        }
        return root;
    }

    public void insert(int value) {
        mRoot = insertNode(mRoot, value);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left != null && root.right == null) {
                return root.left;
            }
            if (root.left == null && root.right != null) {
                return root.right;
            }
            TreeNode leftMost = findLeftMostNode(root.right);
            root.val = leftMost.val;
            root.right = deleteNode(root.right, leftMost.val);
        }
        return root;
    }

    public void delete(int key) {
        mRoot = deleteNode(mRoot, key);
    }

    public TreeNode findLeftMostNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode leftMost = root;
        while (leftMost.left != null) {
            leftMost = leftMost.left;
        }
        return leftMost;
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        }
        return searchBST(root.right, val);
    }

    public TreeNode search(int val) {
        return searchBST(mRoot, val);
    }

    public void clear() {
        mRoot = null;
    }

    public void random() {
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int value = 1 + (int) (Math.random() * 100);
            insert(value);
        }
    }

    // N - L - R
    public static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    // L - N - R
    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    // L - R - N
    public static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }
}