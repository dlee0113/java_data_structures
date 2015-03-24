/*
 * pNodePoolTreeNode.java
 *
 * Copyright (c) 1998, Particle
 */

public class pNodePoolTreeNode{

	private Object data;
	private int left;
	private int right;

	public pNodePoolTreeNode(){
		left = right = -1;
		data = null;
	}
	public pNodePoolTreeNode(int l,int r){
		left = l;
		right = r;
		data = null;
	}
	public Object getData(){
		return data;
	}
	public void setData(Object o){
		data = o;
	}
	public int getLeft(){
		return left;
	}
	public void setLeft(int l){
		left = l;
	}
	public int getRight(){
		return right;
	}
	public void setRight(int r){
		right = r;
	}
	public String toString(){
		return new String(data.toString());
	}
}