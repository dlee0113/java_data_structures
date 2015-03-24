/*
 * pGeneralSorting.java	v0.01	July 14th, 1998
 *
 * General Sorting Algorithms...
 *
 * Copyright(c) 1998, Particle
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class pGeneralSorting{

	public static void BubbleSort(Comparable[] a){
		boolean switched = true;
		for(int i=0;i<a.length-1 && switched;i++){
			switched = false;
			for(int j=0;j<a.length-i-1;j++)
				if(a[j].compareTo(a[j+1]) > 0){
					switched = true;
					Comparable hold = a[j];
					a[j] = a[j+1];
					a[j+1] = hold;
				}
		}
	}

	public static void SelectionSort(Comparable[] a){
		for(int i = a.length-1;i>0;i--){
			Comparable large = a[0];
			int indx = 0;
			for(int j = 1;j <= i;j++)
				if(a[j].compareTo(large) > 0){
					large = a[j];
					indx = j;
				}
			a[indx] = a[i];
			a[i] = large;
		}
	}

	public static void InsertionSort(Comparable[] a){
		int i,j;
		Comparable e;
		for(i=1;i<a.length;i++){
			e = a[i];
			for(j=i-1;j>=0 && a[j].compareTo(e) > 0;j--)
				a[j+1] = a[j];
			a[j+1] = e;
		}
	}

	public static void HeapSort(Comparable[] a){
		int i,f,s;
		for(i=1;i<a.length;i++){
			Comparable e = a[i];
			s = i;
			f = (s-1)/2;
			while(s > 0 && a[f].compareTo(e) < 0){
				a[s] = a[f];
				s = f;
				f = (s-1)/2;
			}
			a[s] = e;
		}
		for(i=a.length-1;i>0;i--){
			Comparable value = a[i];
			a[i] = a[0];
			f = 0;
			if(i == 1)
				s = -1;
			else
				s = 1;
			if(i > 2 && a[2].compareTo(a[1]) > 0)
				s = 2;
			while(s >= 0 && value.compareTo(a[s]) < 0){
				a[f] = a[s];
				f = s;
				s = 2*f+1;
				if(s+1 <= i-1 && a[s].compareTo(a[s+1]) < 0)
					s = s+1;
				if(s > i-1)
					s = -1;
			}
			a[f] = value;
		}
	}

	public static void MergeSort(Comparable[] a){
		Comparable[] aux = new Comparable[a.length];
		int i,j,k,l1,l2,size,u1,u2;
		size = 1;
		while(size < a.length){
			l1 = k = 0;
			while((l1 + size) < a.length){
				l2 = l1 + size;
				u1 = l2 - 1;
				u2 = (l2+size-1 < a.length) ? 
					l2 + size-1:a.length-1;
				for(i=l1,j=l2;i <= u1 && j <= u2;k++)
					if(a[i].compareTo(a[j]) <= 0)
						aux[k] = a[i++];
					else
						aux[k] = a[j++];
				for(;i <= u1;k++)
					aux[k] = a[i++];
				for(;j <= u2;k++)
					aux[k] = a[j++];
				l1 = u2 + 1;
			}
			for(i=l1;k < a.length;i++)
				aux[k++] = a[i];
			for(i=0;i < a.length;i++)
				a[i] = aux[i];
			size *= 2;
		}
	}

	public static void main(String[] args){
		Integer[] a = new Integer[10];
		System.out.print("starting...\nadding:");
		for(int i=0;i<a.length;i++){
			a[i] = new Integer((int)(Math.random()*100));
			System.out.print(" " + a[i]);
		}
		BubbleSort(a);
		System.out.print("\nprinting:");
		for(int i=0;i<a.length;i++){
			System.out.print(" " + a[i]);
		}
		System.out.println("\nDone ;-)");
	}
}
