/*
 * pArrayStackInt.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */

public class pArrayStackInt{
	protected int head[];
	protected int pointer;

	public pArrayStackInt(int capacity){
        head = new int[capacity];
		pointer = -1;        
	}
	public boolean isEmpty(){
		return pointer == -1;
	}
	public void push(int i){
		if(pointer+1 < head.length)
			head[++pointer] = i;
	}
	public int pop(){
		if(isEmpty())
			return 0;
		return head[pointer--];
	}
}