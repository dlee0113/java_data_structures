/*
 * pArrayListTest.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */



class pArrayListTest{
	public static void main(String[] args){
		pArrayList l = new pArrayList(10);
		Integer j = null;
		int i;
		System.out.println("starting...");
		for(i=0;i<5;i++){
			j = new Integer((int)(Math.random() * 100));
			l.insert(j);
			System.out.println("insert: " + j);
		}	
		while(!l.isFull()){
			j = new Integer((int)(Math.random() * 100));
			l.insertEnd(j);
			System.out.println("insertEnd: " + j);
		}
		for(i=0;i<l.size();i++)
			System.out.println("peek "+i+": "+l.peek(i));
		for(i=0;i<5;i++)
			System.out.println("remove: " + ((Integer)l.remove()));
		while(!l.isEmpty())
			System.out.println("removeEnd: " + ((Integer)l.removeEnd()));
		System.out.println("Done ;-)");
	}
}
