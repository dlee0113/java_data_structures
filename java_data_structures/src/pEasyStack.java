/*
 * pEasyStack.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */


public class pEasyStack{
	protected pLinkedList l;

	public pEasyStack(){
		l = new pLinkedList();
	}
	public boolean isEmpty(){
		return l.isEmpty();
	}
	public void push(Object o){
		l.insert(o);
	}
	public Object pop(){
		return l.remove();
	}
}