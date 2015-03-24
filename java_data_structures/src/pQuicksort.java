/**
 * pQuicksort.java	v0.01	August 18-19th, 1998
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
 * Copyright(c) 1998, Particle
 */

import java.lang.*;
import java.io.*;

public class pQuicksort{

	public static void sort(Comparable[] c){
		int i,j,left = 0,right = c.length - 1,stack_pointer = -1;
		int[] stack = new int[128];
		Comparable swap,temp;
		while(true){
			/* see if to do insertion sort or quicksort */
			if(right - left <= 7){
				/* simple insertion sort */
				for(j=left+1;j<=right;j++){
					swap = c[j];
					i = j-1;
					while(i>=left && c[i].compareTo(swap) > 0)
						c[i+1] = c[i--];
					c[i+1] = swap;
				}
				if(stack_pointer == -1)
					break;
				right = stack[stack_pointer--];
				left = stack[stack_pointer--];
			}else{
				/* quicksort */
				/* find the median */
				int median = (left + right) >> 1;
				i = left + 1;
				j = right;
				/* swap the median */
				swap = c[median]; c[median] = c[i]; c[i] = swap;
				/* make sure: c[left] <= c[left+1] <= c[right] */
				if(c[left].compareTo(c[right]) > 0){
					swap = c[left]; c[left] = c[right]; c[right] = swap;
				}if(c[i].compareTo(c[right]) > 0){
					swap = c[i]; c[i] = c[right]; c[right] = swap;
				}if(c[left].compareTo(c[i]) > 0){
					swap = c[left];	c[left] = c[i]; c[i] = swap;
				}
				temp = c[i];
				while(true){
					do i++; while(c[i].compareTo(temp) < 0);
					do j--; while(c[j].compareTo(temp) > 0);
					if(j < i)
						break;
					swap = c[i]; c[i] = c[j]; c[j] = swap;
				}
				c[left + 1] = c[j];
				c[j] = temp;
				if(right-i+1 >= j-left){
					stack[++stack_pointer] = i;
					stack[++stack_pointer] = right;
					right = j-1;
				}else{
					stack[++stack_pointer] = left;
					stack[++stack_pointer] = j-1;
					left = i;
				}
			}
		}
	}

	public static void main(String[] args){
		int i;
		Integer[] arr = new Integer[20];
		System.out.println("inserting: ");
		for(i=0;i<arr.length;i++){
			arr[i] = new Integer((int)(Math.random()*99));
			System.out.print(arr[i]+" ");
		}
		pQuicksort.sort(arr);
		System.out.println("\nsorted: ");
		for(i=0;i<arr.length;i++)
			System.out.print(arr[i]+" ");
		System.out.println("\nDone ;-)");
	}
}