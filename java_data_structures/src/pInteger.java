/*
 * pInteger.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */

public class pInteger implements pComparable{
	int i;

	public pInteger(){
	
	}
	public pInteger(int j){
		set(j);
	}
	public int get(){
		return i;
	}
	public void set(int j){
		i = j;
	}
	public String toString(){
		return ""+get();
	}
	public int compareTo(Object o){
		if(o instanceof pInteger)
			if(get() > ((pInteger)o).get())
				return 1;
			else if(get() < ((pInteger)o).get())
				return -1;
		return 0;
	}
}
