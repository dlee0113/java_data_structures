/*
 * pBreadthFirstTraversal.java
 *
 * Copyright(c) 1998, Particle
 */


public class pBreadthFirstTraversal extends pBinarySearchTree{

	public void breadth_first(){
		pEasyQueue q = new pEasyQueue();
		pTwoChildNode tmp;
		q.insert(getRoot());
		while(!q.isEmpty()){
			tmp = (pTwoChildNode)q.remove();
			if(tmp.getLeft() != null)
				q.insert(tmp.getLeft());
			if(tmp.getRight() != null)
				q.insert(tmp.getRight());
			System.out.print(tmp.getData()+" ");
		}
	}
}
