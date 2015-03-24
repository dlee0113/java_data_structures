/*
 * pPriorityVectorTest.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */

import java.util.Enumeration;

class pPriorityVectorTest{
	public static void main(String[] args){
		pPriorityVector v = new pPriorityVector();
		System.out.print("starting...\nadding:");
		for(int i=0;i<10;i++){
			Integer j = new Integer((int)(Math.random()*100));
			v.pAddElement(j);
			System.out.print(" " + j);
		}
		System.out.print("\nprinting:");
		Enumeration elements = v.elements();
		while(elements.hasMoreElements())
			System.out.print(" "+(Integer)elements.nextElement());
		System.out.println("\nDone ;-)");
	}
}
