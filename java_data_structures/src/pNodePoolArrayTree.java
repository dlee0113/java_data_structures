/*
 * pNodePoolArrayTree.java
 *
 * A binary tree class that has a pre-set number of 
 * nodes each instance of the class can have, by using
 * a nodepool implementation for allocation and deletion
 * of nodes.
 *
 * Copyright (c) 1998, Particle
 */


public abstract class pNodePoolArrayTree{

	/**
	 * Nodepool is made up of nodes, with a RIGHT
	 * pointer pointing to the next available node, 
	 * They're allocated in a stack sequance, with 
	 * nextAvail being the "stack pointer".
	 */
	private int numNodes,nextAvail;
	private pNodePoolTreeNode[] arrayNodes;

	/** 
	 * the root of the tree... (pointing to the
	 * node in the nodepool.
	 */
	protected int root;

	/**
	 * function to initialize everything, like the nodepool,
	 */
	private void init(){
		int i;
		root = -1;
		arrayNodes = new pNodePoolTreeNode[numNodes];
		nextAvail = 0;
		for(i=0;i<numNodes;i++)
			arrayNodes[i] = new pNodePoolTreeNode(-1,i+1);
		getNode(i-1).setRight(-1);
	}

	/**
	 * a default no paramter constructor, defaults to 
	 * 500 nodes.
	 */
	public pNodePoolArrayTree(){
		numNodes = 500;
		init();
	}

	/**
	 * a constructor to accept an integer, and create
	 * that number of nodes (the capacity of this tree)
	 *
	 * @param n the capacity of this tree
	 */
	public pNodePoolArrayTree(int n){
		numNodes = n;
		init();
	}

	/**
	 * method to reserve a node in the nodepool, returns
	 * a -1 if all the nodes have been used up.
	 *
	 * @return the new node, or -1 if error.
	 */
	protected int getNode(){
		int i = nextAvail;
		nextAvail = getNode(nextAvail).getRight();
		if(nextAvail == -1){
			nextAvail = i;
			return -1;
		}
		return i;
	}

	/**
	 * function to make a specified node available
	 * inside the nodepool.
	 */
	protected void freeNode(int n){
		getNode(n).setRight(nextAvail);
		nextAvail = n;
	}
	
	/**
	 * function to return whether this tree is 
	 * empty...
	 */
	public boolean isEmpty(){
		return getRoot() == -1;
	}

	/**
	 * function to determine whether the current tree
	 * is full, (all nodes used up)
	 */
	public boolean isFull(){
		int i = getNode();
		if(i != -1){
			freeNode(i);
			return false;
		}
		return true;
	}

	/**
	 * function to get the current data 
	 * (current root's data)
	 */
	protected Object getData(){
		if(isEmpty())
			return null;
		return getNode(getRoot()).getData();
	}

	/**
	 * function to set the data of the root 
	 * node, if no root node, then create one.
	 */
	protected void setData(Object o){
		if(isEmpty())
			root = getNode();
		getNode(root).setData(o);
		getNode(root).setLeft(-1);
		getNode(root).setRight(-1);
	}

	/**
	 * function to return the left node...
	 */
	protected int getLeft(){
		if(isEmpty())
			return -1;
		return getNode(getRoot()).getLeft();
	}

	/**
	 * function to set the left node to the root.
	 */
	protected void setLeft(int n){
		if(isFull())
			return;
		if(isEmpty()){
			root = getNode();
			getNode(getRoot()).setRight(-1);
		}
		getNode(getRoot()).setLeft(n);
	}
		
	/**
	 * function to set the left node to the root.
	 */
	protected void setRight(int n){
		if(isFull())
			return;
		if(isEmpty()){
			root = getNode();
			getNode(getRoot()).setLeft(-1);
		}
		getNode(getRoot()).setRight(n);
	}

	/**
	 * function to get the right node...
	 */
	protected int getRight(){
		if(isEmpty())
			return -1;
		return getNode(root).getRight();
	}

	/**
	 * get root...
	 */
	protected int getRoot(){
		return root;
	}

	/**
	 * returns the node inside the node pool.
	 */
	protected pNodePoolTreeNode getNode(int n){
		if(n != -1)
			return arrayNodes[n];
		else return null;
	}

	/**
	 * given a node, this method inserst an object
	 * onto the left of the node.
	 */
	protected void insertLeft(int node,Object o){
		if((node != -1) && (getNode(node).getLeft() == -1) 
			&& !isFull()){
			int i = getNode();
			getNode(i).setData(o);
			getNode(i).setRight(-1);
			getNode(node).setLeft(i);
		}
	}

	/**
	 * given a node, this method inserst an object
	 * onto the right of the node.
	 */
	protected void insertRight(int node,Object o){
		if((node != -1) && (getNode(node).getRight() == -1) 
			&& !isFull()){
			int i = getNode();
			getNode(i).setData(o);
			getNode(i).setRight(-1);
			getNode(node).setRight(i);
		}
	}

	/**
	 * function to print stuff in diff modes
	 */
	public void print(int mode){
		switch(mode){
		case 1: 
			pretrav();
			break;
		case 2:
			intrav();
			break;
		case 3:
			postrav();
			break;
		}
	}
	
	/**
	 * function to print the whole tree in pre-traversal.
	 */
	public void pretrav(){
		pretrav(getRoot());
	}

	/**
	 * a work-horse function for pre-trav.
	 */
	private void pretrav(int p){
		if(p == -1)
			return;
		System.out.print(getNode(p).getData()+" ");
		pretrav(getNode(p).getLeft());
		pretrav(getNode(p).getRight());
	}
	
	/** 
	 * a user friendly function for in-trav
	 */
	public void intrav(){
		intrav(getRoot());
	}
	
	/**
	 * a work-horse function for in-trav.
	 */
	private void intrav(int p){	
		if(p == -1)
			return;
		intrav(getNode(p).getLeft());		
		System.out.print(getNode(p).getData()+" ");
		intrav(getNode(p).getRight());
	}

	/** 
	 * a user friendly function for post-trav
	 */
	public void postrav(){
		postrav(getRoot());
	}

	/**
	 * a work-horse function for post-trav.
	 */
	private void postrav(int p){
		if(p == -1)
			return;
		postrav(getNode(p).getLeft());
		postrav(getNode(p).getRight());
		System.out.print(getNode(p).getData()+" ");
	}
}