/*
 * pArrayStackIntTest.java
 *
 * Copyright(c) 1998, Particle
 */


class pArrayStackIntTest{
	public static void main(String[] args){
		pArrayStackInt s = new pArrayStackInt(10);
		int i,j;
		System.out.println("starting...");
		for(i=0;i<10;i++){
			j = (int)(Math.random() * 100);
			s.push(j);
			System.out.println("push: " + j);
		}
		while(!s.isEmpty()){
			System.out.println("pop: " + s.pop());
		}
		System.out.println("Done ;-)");
	}
}
