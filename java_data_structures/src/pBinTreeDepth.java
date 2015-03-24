/*
 * pBinTreeDepth.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */

import java.lang.*;
import java.io.*;

public class pBinTreeDepth{
	public pBinTreeDepth left,right;
	public Integer data;
	private static int tree_depth,curr_depth = 0;
	public static int[] numbers = {7,3,11,2,5,9,12,4,6,8,10};

	public static pBinTreeDepth add(pBinTreeDepth r,Integer n){
		if(r == null){
			r = new pBinTreeDepth();
			r.left = r.right = null;
			r.data = n;
		}else if(r.data.compareTo(n) < 0)
			r.right = add(r.right,n);
		else
			r.left = add(r.left,n);
		return r;
	}

	public static void print(pBinTreeDepth r){
		if(r != null){
			print(r.left);
			System.out.print(" "+r.data);
			print(r.right);
		}
	}

	public static void _getdepth(pBinTreeDepth r){
		if(r != null){
			curr_depth++;
			if(curr_depth > tree_depth)
				tree_depth = curr_depth;
			_getdepth(r.left);
			_getdepth(r.right);
			curr_depth--;
		}
	}

	public static int getdepth(pBinTreeDepth r){
		tree_depth = 0;
		_getdepth(r);
		return tree_depth;
	}

	public static void main(String[] args){
		pBinTreeDepth tree = null;
		System.out.print("inserting: ");

		for(int i=0;i<numbers.length;i++){
			Integer n = new Integer(numbers[i]);
			System.out.print(" "+n);
			tree = add(tree,n);
		}

		System.out.print("\ntree: ");
		print(tree);
		System.out.println("\ndepth: "+getdepth(tree));
		System.out.println("done ;-)");
	}
}