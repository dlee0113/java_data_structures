/*
 * pArrayStackObjectTest.java
 *
 * Copyright(c) 1998, Particle
 */


class pArrayStackObjectTest{
	public static void main(String[] args){
		pArrayStackObject s = new pArrayStackObject(10);
		Integer j = null;
		int i;
		System.out.println("starting...");
		for(i=0;i<10;i++){
			j = new Integer((int)(Math.random() * 100));
			s.push(j);
			System.out.println("push: " + j);
		}
		while(!s.isEmpty()){
			System.out.println("pop: " + ((Integer)s.pop()));
		}
		System.out.println("Done ;-)");
	}
}
