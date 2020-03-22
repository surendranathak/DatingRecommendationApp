package com.apple.recommendation.tree;

import com.apple.recommendation.Player;

public class BinarySearchTree {
    // Root of BST
    public TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

   public void insert(int key, Player player) {
        root = insertRec(root, key,player);
    }

    public TreeNode insertRec(TreeNode root, int key, Player player) {

       if (root == null) {
            root = new TreeNode(key,player);
            return root;
        }

       if (key <= root.key) {
           root.left = insertRec(root.left, key, player);
       }else {
           root.right = insertRec(root.right, key, player);
       }

        return root;
    }

    public void deleteKey(int key)
    {
        root = deleteRec(root, key);
    }

    public TreeNode deleteRec(TreeNode root, int key)
    {
        if (root == null) {
            return root;
        }

        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else
        {
            if (root.left == null){
                return root.right;
            }else if (root.right == null) {
                return root.left;
            }

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    int minValue(TreeNode root)
    {
        int minv = root.key;
        while (root.left != null)
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

}
