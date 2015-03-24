/*
 * pPriorityVector.java	v0.01		March 29th, 1998
 * (July 14th, 1998)
 *
 * Copyright (c) 1998, Particle
 * http://www.geocities.com/SiliconValley/Way/7650
 */

import java.lang.Comparable;

public class pPriorityVector extends java.util.Vector{

	public void pAddElement(Comparable o){
		int i,j = size();
		for(i=0;i<j&&(((Comparable)(elementAt(i))).compareTo(o)<0);i++);
		insertElementAt(o,i);
	}		
}