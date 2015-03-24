/**
 * pQuicksortNative.java	v0.02 June 27th, 1999
 * 
 * Native implementation; sort routine is found in file 
 * qsort.c, which is pure C, and should work under all
 * platforms.
 *
 * Comparable type has been changed to int to make
 * implementation easier. The point of this code
 * is to illustrate the use of native methods.
 *
 * An optimized version of Quicksort, with insertion
 * sort for smaller values of N, (ie: insertion sort
 * has less overhead, and thus, is faster for small
 * values of N)
 *
 * the size of the stack is determined using:
 * size = (int)Math.ceil(2*Math.log(c.length)/Math.log(2.0);
 * this value is pre-set at 128, since that's the highest
 * it can go even for a 64 bit integer. (it only goes 
 * upto 64 for a 32 bit integer.)
 *
 * Copyright(c) 1998-1999, Particle
 */

import java.lang.*;
import java.io.*;

public class pQuicksortNative{
		
	public static native void qsort(int[] c);

	public static void main(String[] args){
		
		if(args.length > 0)
			try{
				System.load(args[0]);
			}catch(java.lang.UnsatisfiedLinkError e){
				System.out.println("bad lib name: "+args[0]);
				return;
			}
		else{
			System.out.println("include lib name as parameter");
			return;
		}
		
		int i;
		int[] arr = new int[20];
		System.out.println("inserting: ");
		for(i=0;i<arr.length;i++){
			arr[i] = (int)(Math.random()*99);
			System.out.print(arr[i]+" ");
		}
		qsort(arr);
		System.out.println("\nsorted: ");
		for(i=0;i<arr.length;i++)
			System.out.print(arr[i]+" ");
		System.out.println("\nDone ;-)");
	}
}
