/*
 * pEasyQueue.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */


public class pEasyQueue{
	protected pLinkedList l;

	public pEasyQueue(){
		l = new pLinkedList();
	}
	public boolean isEmpty(){
		return l.isEmpty();
	}
	public void insert(Object o){
		l.insert(o);
	}
	public Object remove(){
		return l.removeEnd();
	}
}