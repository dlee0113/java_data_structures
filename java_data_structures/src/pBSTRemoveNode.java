/*
 * pBSTRemoveNode.java	v0.01	August 2nd, 1998
 *
 * Node removal from a Binary Search Tree (BST).
 *
 * Part of Java Data Structures 2nd Edition. 
 * Copyright(c) 1998, Particle
 */

import java.lang.*;
import java.io.*;

/**
 * an example class to illustrate deleting a node from 
 * a binary search tree. 
 *
 * (08/02/1998)
 *
 * Copyright(c) 1998, Particle
 */
public class pBSTRemoveNode{
	
	/**
	 * the left and right children of this node.
	 */
	public pBSTRemoveNode left,right;

	/**
	 * the tree supports any Comparable, but ALL objects
	 * in the tree must be of same class! (they need
	 * to be comparable among themselves)
	 */
	public Comparable data;

	/**
	 * binary search tree insert function. doesn't
	 * allow for multiple insertions. if multiple
	 * insertion, returns pointer to the duplicate
	 * element inside the tree.
	 *
	 * @param r the root node of the tree (the tree).
	 * @param n the Comparable to insert into the tree.
	 * @return pointer to the inserted node.
	 */
	public static pBSTRemoveNode tree_AddNumber(
		pBSTRemoveNode r,Comparable n){
		if(r == null){
			r = new pBSTRemoveNode();
			r.left = r.right = null;
			r.data = n;
		}else if(r.data.compareTo(n) < 0)
			r.right = tree_AddNumber(r.right,n);
		else if(r.data.compareTo(n) > 0)
			r.left = tree_AddNumber(r.left,n);
		return r;
	}

	/**
	 * given a Comparable object, this method removes
	 * an object from the tree which is equal to the
	 * Comparable parameter object. (ie: Comparable.compareTo()
	 * method returns a zero.)
	 *
	 * if the item is not in the tree, then nothing is
	 * removed. if the removal removes the last item in
	 * the tree, then null is returned.
	 *
	 * @param r the root of the tree.
	 * @param n the Comparable object to remove.
	 * @return the resulting tree after the remove.
	 */
	public static pBSTRemoveNode tree_removeNumber(
		pBSTRemoveNode r,Comparable n){
		if(r != null){
			if(r.data.compareTo(n) < 0){
				r.right = tree_removeNumber(r.right,n);
			}else if(r.data.compareTo(n) > 0){
				r.left = tree_removeNumber(r.left,n);
			}else{
				if(r.left == null && r.right == null){
					r = null;
				}else if(r.left != null && r.right == null){
					r = r.left;
				}else if(r.right != null && r.left == null){
					r = r.right;
				}else{
					if(r.right.left == null){
						r.right.left = r.left;
						r = r.right;
					}else{	
						pBSTRemoveNode q,p = r.right;
						while(p.left.left != null)
							p = p.left;
						q = p.left;
						p.left = q.right;
						q.left = r.left;
						q.right = r.right;
						r = q;
					}
				}
			}
		}
		return r;
	}

	/**
	 * print the tree in the in-order form. (sorted
	 * elements. (left to right)
	 *
	 * @param r the root node of the tree to print.
	 */
	public static void tree_InOrderPrint(
		pBSTRemoveNode r){
		if(r != null){
			tree_InOrderPrint(r.left);
			System.out.print(" "+r.data);
			tree_InOrderPrint(r.right);
		}
	}

	/**
	 * main method to test this whole thing.
	 *
	 * @param args Command line arguments to this program.
	 */
	public static void main(String[] args){
		pBSTRemoveNode tree = null;
		/* non-duplicate numbers to insert onto the tree */
		int[] numbers = {56,86,71,97,82,99,65,36,16,10,28,52,46};
		System.out.print("inserting: ");
		for(int i = 0;i<numbers.length;i++){
			Integer n = new Integer(numbers[i]);
			System.out.print(" "+n);
			tree = tree_AddNumber(tree,n);
		}
		System.out.print("\ntree: ");
		tree_InOrderPrint(tree);
		for(int j = 0;j < numbers.length;j++){
			Integer n = new Integer(numbers[j]);
			System.out.print("\nremove: "+n+" tree: ");
			tree = tree_removeNumber(tree,n);
			tree_InOrderPrint(tree);
		}
		System.out.println("\ndone ;-)");
	}
}
