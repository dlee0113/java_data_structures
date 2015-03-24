/*
 * pJDKVectorSortingDown.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */

import java.io.*;
import java.util.*;

public class pJDKVectorSortingDown implements Comparator{
	
	public int compare(Object o1,Object o2){
		return -((Comparable)o1).compareTo(o2);
	}
	
	public static void main(String[] args){
		Vector v = new Vector();
		System.out.print("starting...\nadding:");
		for(int i=0;i<10;i++){
			Integer j = new Integer((int)(Math.random()*100));
			v.addElement(j);
			System.out.print(" " + j);
		}
		Collections.sort(v,new pJDKVectorSortingDown());
		System.out.print("\nprinting:");
		Enumeration elements = v.elements();
		while(elements.hasMoreElements())
			System.out.print(" "+(Integer)elements.nextElement());
		System.out.println("\nDone ;-)");
	}
}
