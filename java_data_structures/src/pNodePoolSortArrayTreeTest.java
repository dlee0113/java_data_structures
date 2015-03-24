/*
 * pNodePoolSortArrayTreeTest.java
 *
 * Test module for the pNodePoolSortArrayTree
 *
 * Copyright (c) 1998, Particle
 */


public class pNodePoolSortArrayTreeTest{
	
	public static void main(String[] args){
		pNodePoolSortArrayTree tree = 
			new pNodePoolSortArrayTree(100);
		int i,n;
		System.out.println("Numbers inserted:");
		for(i=0;i<10;i++){
			tree.insert(new Integer(n=(int)(Math.random()*1000)));
			System.out.print(n+" ");
		}
		System.out.println("\nPre-order:");
		tree.print(1);
		System.out.println("\nIn-order:");
		tree.print(2);
		System.out.println("\nPost-order:");
		tree.print(3);
	}
}
