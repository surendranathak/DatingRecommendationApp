package com.apple.recommendation.tree;

import com.apple.recommendation.Player;

public class TreeNode {
    public int key;
    public Player player;
     public TreeNode left;
    public TreeNode right;
    TreeNode(int key, Player player) {
        this.key = key;
        this.player = player;
        this.left = this.right =null;
    }
}
