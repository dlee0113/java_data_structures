/*
 * pNodePoolSortArrayTree.java
 *
 * A binary sort tree class, that utilizes the 
 * general binary tree.
 *
 * Copyright (c) 1998, Particle
 */


public class pNodePoolSortArrayTree extends 
	pNodePoolArrayTree{

	/** 
	 * a constructor
	 */
	public pNodePoolSortArrayTree(){
		super();
	}

	/**
	 * constructor that also intitializes how
	 * many nodes this tree will have.
	 */
	public pNodePoolSortArrayTree(int n){
		super(n);
	}

	/**
	 * print the sorted tree in a meaningful fashion
	 */
	public void print(){
		print(2);
	}

	/** 
	 * insert an Comparable Object into this binary 
	 * search tree 
	 */	
	public void insert(Comparable obj){
		int t,q = -1;
		t = getRoot();
		while(t != -1 && !(obj.equals(getNode(t).getData()))){
			q = t;
			if(obj.compareTo(getNode(t).getData()) < 0)
				t = getNode(t).getLeft();
			else 
				t = getNode(t).getRight();
		}
		if(t != -1)
			return;
		if(q == -1){
			setData(obj);
			return;
		}
		if(obj.compareTo(getNode(q).getData()) < 0)
			insertLeft(q,obj);
		else
			insertRight(q,obj);
	}
}