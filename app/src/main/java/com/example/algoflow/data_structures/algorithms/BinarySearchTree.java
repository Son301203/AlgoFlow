package com.example.algoflow.data_structures.algorithms;

import com.example.algoflow.models.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BinarySearchTree {
    public TreeNode mRoot;
    public List<Integer> listOrderTraversal = new ArrayList<>();
    private OnTraversalStepListener traversalListener;
    private static final long TRAVERSAL_DELAY_MS = 1000;
    private AtomicBoolean isTraversalRunning = new AtomicBoolean(false);
    private volatile boolean cancellationFlag = false;

    public interface OnTraversalStepListener {
        void onStep(int value);
        void onTraversalComplete();
    }

    public void setTraversalListener(OnTraversalStepListener listener) {
        this.traversalListener = listener;
    }

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
        resetTraversalList();
    }

    public void random() {
        clear();
        int nodeCount = 5 + (int) (Math.random() * 6);
        for (int i = 0; i < nodeCount; i++) {
            int value = 1 + (int) (Math.random() * 100);
            insert(value);
        }
    }

    public void resetTraversalList() {
        listOrderTraversal.clear();
    }

    // N - L - R
    public void preOrderTraversal() {
        if (isTraversalRunning.get() || mRoot == null) {
            return;
        }
        resetTraversalList();
        cancellationFlag = false;
        isTraversalRunning.set(true);
        preOrderTraversalStep(mRoot, () -> {
            isTraversalRunning.set(false);
            if (traversalListener != null) {
                traversalListener.onTraversalComplete();
            }
        });
    }

    private void preOrderTraversalStep(TreeNode root, Runnable onComplete) {
        if (root == null || cancellationFlag) {
            onComplete.run();
            return;
        }

        // add value
        listOrderTraversal.add(root.val);
        if (traversalListener != null) {
            traversalListener.onStep(root.val);
        }

        // delay
        new android.os.Handler().postDelayed(() -> {
            if (cancellationFlag) {
                onComplete.run();
                return;
            }
            // Traversal left
            preOrderTraversalStep(root.left, () -> {
                if (cancellationFlag) {
                    onComplete.run();
                    return;
                }
                // Traversal right
                preOrderTraversalStep(root.right, onComplete);
            });
        }, TRAVERSAL_DELAY_MS);
    }

    // L - N - R
    public void inOrderTraversal() {
        if (isTraversalRunning.get() || mRoot == null) {
            return;
        }
        resetTraversalList();
        cancellationFlag = false;
        isTraversalRunning.set(true);
        inOrderTraversalStep(mRoot, () -> {
            isTraversalRunning.set(false);
            if (traversalListener != null) {
                traversalListener.onTraversalComplete();
            }
        });
    }

    private void inOrderTraversalStep(TreeNode root, Runnable onComplete) {
        if (root == null || cancellationFlag) {
            onComplete.run();
            return;
        }

        // Traversal left
        inOrderTraversalStep(root.left, () -> {
            if (cancellationFlag) {
                onComplete.run();
                return;
            }
            // add value
            listOrderTraversal.add(root.val);
            if (traversalListener != null) {
                traversalListener.onStep(root.val);
            }

            //delay
            new android.os.Handler().postDelayed(() -> {
                if (cancellationFlag) {
                    onComplete.run();
                    return;
                }
                // Traversal right
                inOrderTraversalStep(root.right, onComplete);
            }, TRAVERSAL_DELAY_MS);
        });
    }

    // L - R - N
    public void postOrderTraversal() {
        if (isTraversalRunning.get() || mRoot == null) {
            return;
        }
        resetTraversalList();
        cancellationFlag = false;
        isTraversalRunning.set(true);
        postOrderTraversalStep(mRoot, () -> {
            isTraversalRunning.set(false);
            if (traversalListener != null) {
                traversalListener.onTraversalComplete();
            }
        });
    }

    private void postOrderTraversalStep(TreeNode root, Runnable onComplete) {
        if (root == null || cancellationFlag) {
            onComplete.run();
            return;
        }

        // Traversal left
        postOrderTraversalStep(root.left, () -> {
            if (cancellationFlag) {
                onComplete.run();
                return;
            }
            // Traversal right
            postOrderTraversalStep(root.right, () -> {
                if (cancellationFlag) {
                    onComplete.run();
                    return;
                }
                // add value
                listOrderTraversal.add(root.val);
                if (traversalListener != null) {
                    traversalListener.onStep(root.val);
                }

                // delay
                new android.os.Handler().postDelayed(onComplete, TRAVERSAL_DELAY_MS);
            });
        });
    }

    public void cancelTraversal() {
        cancellationFlag = true;
    }
}