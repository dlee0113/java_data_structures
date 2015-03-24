/*
 * pEasyQueueTest.java	v0.01
 *
 * Copyright(c) 1998, Particle
 */


class pEasyQueueTest{
	public static void main(String[] args){
		pEasyQueue s = new pEasyQueue();
		Integer j = null;
		int i;
		System.out.println("starting...");
		for(i=0;i<10;i++){
			j = new Integer((int)(Math.random() * 100));
			s.insert(j);
			System.out.println("insert: " + j);
		}
		while(!s.isEmpty()){
			System.out.println("remove: " + ((Integer)s.remove()));
		}
		System.out.println("Done ;-)");
	}
}
