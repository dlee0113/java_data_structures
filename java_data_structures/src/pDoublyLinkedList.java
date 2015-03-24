/*
 * pDoublyLinkedList.java	v0.01	August 13th, 1998
 *
 * Copyright(c) 1998, Particle
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

/*
 * a doubly linked list class; the list has two
 * dummy nodes at it's head and tail, this eliminates
 * most of "special cases," speeding up the insertion
 * and removal... the code could be optimized a bit by
 * making more efficient accesses in the add and remove
 * methods; but as it stands, it is more clear, and 
 * better shows that goes where.
 */
public class pDoublyLinkedList{

	/**
	 * the head and tail (two dummy nodes)
	 */
	private pTwoChildNode head,tail;

	/**
	 * number of objects in the list
	 */
	protected long num;

	/**
	 * gets the head of the list
	 *
	 * @return The head of the list.
	 */
	protected pTwoChildNode getHead(){
		return head;
	}

	/**
	 * gets the tail of the list
	 *
	 * @return The tail of the list.
	 */
	protected pTwoChildNode getTail(){
		return tail;
	}

	/**
	 * sets the head of the tree.
	 *
	 * @param p The node to set as head.
	 */
	protected void setHead(pTwoChildNode p){
		head = p;
	}

	/**
	 * sets the tail of the list.
	 *
	 * @param p The node to set as tail.
	 */
	protected void setTail(pTwoChildNode p){
		tail = p;
	}

	/**
	 * the constructor to init stuff; and make
	 * head point to tail, and tail point to head.
	 */
	public pDoublyLinkedList(){
		setHead(new pTwoChildNode());
		setTail(new pTwoChildNode());
		getTail().setLeft(head);
		getHead().setRight(tail);
		num = 0;
	}
	
	/**
	 * returns the number of elements in the list.
	 *
	 * @return number of elements in the list.
	 */
	public long size(){
		return num;
	}

	/** 
	 * returns whether this list is empty.
	 *
	 * @return a boolean value indicating whether this list is empty.
	 */
	public boolean isEmpty(){
		return num == 0;
	}
	
	/**
	 * adds an object to the head of the list.
	 *
	 * @param o the object to add to the head of the list.
	 */
	public void addHead(Object o){
		pTwoChildNode p = new pTwoChildNode(o);
		p.setLeft(getHead());
		p.setRight(getHead().getRight());		
		getHead().setRight(p);		
		p.getRight().setLeft(p);
		num++;
	}
	
	/**
	 * removes an object from the head of the list, if 
	 * the list is empty, a null is returned.
	 *
	 * @return the previous head of the list (now removed)
	 */
	public Object removeHead(){
		Object o = null;
		if(!isEmpty()){
			pTwoChildNode p = getHead().getRight();
			getHead().setRight(p.getRight());
			p.getRight().setLeft(getHead());
			o = p.getData();
			num--;
		}
		return o;
	}

	/**
	 * adds an object to the tail of the list.
	 *
	 * @param o the object to add at the end of the list.
	 */
	public void addTail(Object o){
		pTwoChildNode p = new pTwoChildNode(o);
		p.setRight(getTail());
		p.setLeft(getTail().getLeft());
		getTail().setLeft(p);
		p.getLeft().setRight(p);
		num++;
	}

	/**
	 * removes an object from the tail of the list; and
	 * returns it... if the list is empty, a null is returned.
	 *
	 * @return the previous tail of the list (now removed)
	 */
	public Object removeTail(){
		Object o = null;
		if(!isEmpty()){
			pTwoChildNode p = getTail().getLeft();
			getTail().setLeft(p.getLeft());
			p.getLeft().setRight(getTail());
			o = p.getData();
			num--;
		}
		return o;
	}

	/**
	 * a default add function to simplify stuff.
	 *
	 * @param o the object to add to the list.
	 */
	public void add(Object o){
		addHead(o);
	}

	/**
	 * a default remove to simplify things.
	 *
	 * @return an object from the list
	 */
	public Object remove(){
		return removeHead();
	}
	
	/**
	 * returns the Enumeration of elements from 
	 * head to tail.
	 *
	 * @return the elementseration of elements.
	 */
	public Enumeration elementsHeadToTail(){
		return new Enumeration(){
			
			pTwoChildNode p = getHead();
			
			public boolean hasMoreElements(){
				return p.getRight() != getTail();
			}
			
			public Object nextElement(){
				synchronized(pDoublyLinkedList.this){
					if(hasMoreElements()){
						p = p.getRight();
						return p.getData();
					}
				}
				throw new NoSuchElementException(
					"pDoublyLinkedList Enumeration");
			}
		};
	}

	/**
	 * returns the Enumeration of elements from tail 
	 * to head.
	 *
	 * @return the Enumeration of elements
	 */
	public Enumeration elementsTailToHead(){
		return new Enumeration(){
			
			pTwoChildNode p = getTail();
			
			public boolean hasMoreElements(){
				return p.getLeft() != getHead();
			}
			
			public Object nextElement(){
				synchronized(pDoublyLinkedList.this){
					if(hasMoreElements()){
						p = p.getLeft();
						return p.getData();
					}
				}
				throw new NoSuchElementException(
					"pDoublyLinkedList Enumeration");
			}
		};
	}

	/**
	 * a static main method to test this class.
	 *
	 * @param args command line arguments.
	 */
	public static void main(String[] args){
		pDoublyLinkedList list = new pDoublyLinkedList();
		int i;
		System.out.println("inserting head:");
		for(i=0;i<5;i++){
			Integer n = new Integer((int)(Math.random()*99));
			list.addHead(n);
			System.out.print(n+" ");
		}
		System.out.println("\ninserting tail:");
		for(i=0;i<5;i++){
			Integer n = new Integer((int)(Math.random()*99));
			list.addTail(n);
			System.out.print(n+" ");
		}
		System.out.println("\nhead to tail print...");
		Enumeration elements = list.elementsHeadToTail();
		while(elements.hasMoreElements())
			System.out.print(((Integer)elements.nextElement())+" ");
		System.out.println("\ntail to head print...");
		elements = list.elementsTailToHead();
		while(elements.hasMoreElements())
			System.out.print(((Integer)elements.nextElement())+" ");
		System.out.println("\nremoving head:");
		for(i=0;i<5;i++){
			Integer n = (Integer)list.removeHead();
			System.out.print(n+" ");
		}
		System.out.println("\nremoving tail:");
		while(!list.isEmpty()){
			Integer n = (Integer)list.removeTail();
			System.out.print(n+" ");
		}
		System.out.println("\ndone ;-)");
	}
}