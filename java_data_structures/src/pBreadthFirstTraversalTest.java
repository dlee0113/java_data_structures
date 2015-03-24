/*
 * pBreadthFirstTraversalTest.java
 *
 * Copyright(c) 1998, Particle
 */


class pBreadthFirstTraversalTest{
        public static void main(String[] args){
                pBreadthFirstTraversal tree = new pBreadthFirstTraversal();
                pInteger n;
                int i;
                System.out.println("Numbers inserted:");
                for(i=0;i<10;i++){
                        tree.insert(n=new pInteger((int)(Math.random()*1000)));
                        System.out.print(n+" ");
                }
                System.out.println("\nPre-order:");
                tree.print(1);
                System.out.println("\nIn-order:");
                tree.print();
                System.out.println("\nPost-order:");
                tree.print(3);
                System.out.println("\nBreadth-First:");
                tree.breadth_first();
        }
}
