/*
 * javadata_dog3d.java		v0.04, February 28, 1998
 * (July 25th, 1998)
 * 
 * Part of Java Data Structures Tutorial 2nd Edition!
 * 
 * conventions used:
 *	points are represented by a 2 element integer array.
 *	lines are represented by a 5 element integer array (with color).
 *	planes are represented by a 3 element float array; line equation.
 * actually, because "planes" are not normalized, they can be
 * integer arrays... (making it run faster on 486)
 *
 * Copyright (c) 1998, Particle
 */

import java.applet.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 * the BSP tree node class.
 * (02/28/1998,07/25/1998)
 */
class javadata_dog3dBSPNode{	
	public float[] partition = null; 
	public Object[] lines = null;
	public javadata_dog3dBSPNode front = null;
	public javadata_dog3dBSPNode back = null;
}

/**
 * the Binary Space Partition Tree class....
 * 
 * (the "heart" of the whole thing...)
 * (02/28/1998)
 */
class javadata_dog3dBSPTree{

	/**
	 * the root of the tree...
	 */
	private javadata_dog3dBSPNode root;

	/**
	 * the eye point, (to avoid passing it as a parameter...)
	 */
	public int eye_x,eye_y;

	/**
	 * the eye angle... (viewing direction) <for on the spot culling>
	 */
	public double eye_angle;

	/**
	 * the class that "instantiated" this class...
	 * (there goes the modularity...)
	 */
	private javadata_dog3d theParent = null;

	/**
	 * for use with eval functions... and in relation to splitting
	 */
	private final static int SPANNING = 0;
	private final static int IN_FRONT = 1;
	private final static int IN_BACK = 2;
	private final static int COINCIDENT = 3;

	/**
	 * constructor
	 *
	 * @param p the dog3dv2 class that "instantiated" this class.
	 */
	public javadata_dog3dBSPTree(javadata_dog3d p){
		root = null;
		eye_x = eye_y = 0;
		eye_angle = 0.0;
		theParent = p;
	}
	
	/** 
	 * a private method to get the line equation...
	 *
	 * @param line an int array representing a line...
	 * @return a float array representing the line equaiton.
	 */
	private float[] getLineEquation(int[] line){
		float[] equation = new float[3];
     	int dx = line[2] - line[0];
    	int dy = line[3] - line[1];    		
		equation[0] = -dy;
    	equation[1] = dx;    		
		equation[2] = dy*line[0] - dx*line[1];
		return equation;
	}

	/**
	 * evaluate a point...
	 *
	 * @param x the x coordinate...
	 * @param y the y coordinate...
	 * @param p the partition...
	 * @return the eval code... 
	 */
	private int evalPoint(int x,int y,float[] p){
		double c = p[0]*x + p[1]*y + p[2];
		if(c > 0)
			return IN_FRONT;
		else if(c < 0)
			return IN_BACK;
		else return SPANNING;
	}

	/**
	 * evaluate the line in relation to this partion.
	 *
	 * @param line the line to evaluate.
	 * @param partition the partition
	 * @return the eval code... 
	 */
	private int evalLine(int[] line,float[] partition){
		int a = evalPoint(line[0],line[1],partition);
		int b = evalPoint(line[2],line[3],partition);
		if(a == SPANNING){
			if(b == SPANNING)
				return COINCIDENT;
			else return b;
		}if(b == SPANNING){
			if(a == SPANNING)
				return COINCIDENT;
			else return a;
		}if((a == IN_FRONT) && (b == IN_BACK))
			return SPANNING;
		if((a == IN_BACK) && (b == IN_FRONT))
			return SPANNING;
		return a;
	}
	
	/** 
	 * function to split a line, and return 2 pieces of it...
	 * 
	 * @param l the line to be split...
	 * @param p the partition to use in spliting...
	 * @return the 2 lines... 
	 */
	public int[][] splitLine(int[] l,float[] p){
		int[][] q = new int[2][5];
		q[0][4] = q[1][4] = l[4];
		int cross_x = 0,cross_y = 0;
		float[] lEq = getLineEquation(l);
		double divider = p[0] * lEq[1] - p[1] * lEq[0];
      	if(divider == 0){            	
			if(lEq[0] == 0)
				cross_x = l[0];
			if(lEq[1] == 0)
				cross_y = l[1];
			if(p[0] == 0)
				cross_y = (int)-p[1];
			if(p[1] == 0)
				cross_x = (int)p[2];
		}else{
			cross_x = (int)((-p[2]*lEq[1] + p[1]*lEq[2])/divider);
			cross_y = (int)((-p[0]*lEq[2] + p[2]*lEq[0])/divider);
		}
		int p1 = evalPoint(l[0],l[1],p);
		int p2 = evalPoint(l[2],l[3],p);
		if((p1 == IN_BACK) && (p2 == IN_FRONT)){
			q[0][0] = cross_x;	q[0][1] = cross_y;
			q[0][2] = l[2];		q[0][3] = l[3];
			q[1][0] = l[0];		q[1][1] = l[1];
			q[1][2] = cross_x;	q[1][3] = cross_y;
		}else if((p1 == IN_FRONT) && (p2 == IN_BACK)){
			q[0][0] = l[0];		q[0][1] = l[1];
			q[0][2] = cross_x;	q[0][3] = cross_y;
			q[1][0] = cross_x;	q[1][1] = cross_y;
			q[1][2] = l[2];		q[1][3] = l[3];
		}else
			return null;
		return q;
	}

	/**
	 * a function to build the BSP tree...
	 * 
	 * @param tree the "current" node
	 * @param lines the lines Vector
	 */
	private void build(javadata_dog3dBSPNode tree,Vector lines){
		int[] current_line = null;
		Enumeration elements = lines.elements();
		if(elements.hasMoreElements())
			current_line = (int[])elements.nextElement();
		tree.partition = getLineEquation(current_line);
		Vector _lines = new Vector();
		
		_lines.addElement(current_line);
		Vector front_list = new Vector();
		Vector back_list = new Vector();
		int[] line = null;
		while(elements.hasMoreElements()){
			line = (int[])elements.nextElement();
			int result = evalLine(line,tree.partition);
			if(result == IN_FRONT)			/* in front */
				front_list.addElement(line);
			else if(result == IN_BACK)		/* in back */
				back_list.addElement(line);
			else if(result == SPANNING){	/* spanning */
				int[][] split_line = splitLine(line,tree.partition);
				if(split_line != null){
					front_list.addElement(split_line[0]);
					back_list.addElement(split_line[1]);
				}else{
					/* error here! */
				}
			}else if(result == COINCIDENT)
				_lines.addElement(line);			
		}
		if(!front_list.isEmpty()){
			tree.front = new javadata_dog3dBSPNode();
			build(tree.front,front_list);
		}if(!back_list.isEmpty()){
			tree.back = new javadata_dog3dBSPNode();
			build(tree.back,back_list);
		}
		tree.lines = new Object[_lines.size()];
		_lines.copyInto(tree.lines);
	}

	/**
	 * friendly BSP build function... 
	 * 
	 * @param lines the lines to insert into the tree...
	 */
	public void build(Vector lines){
		if(root == null)
			root = new javadata_dog3dBSPNode();
		build(root,lines);
	}

	/**
	 * a function to render the tree... (not the 
	 * user friendly type...)
	 * 
	 * the user should call "renderTree()" below...
	 *
	 * @param tree the root of the tree at the current interation
	 */
	private void renderTree(javadata_dog3dBSPNode tree){
		int[] tmp = null;
		if(tree == null)
			return; /* check for end */
		int i,j = tree.lines.length;
		int result = evalPoint(eye_x,eye_y,tree.partition);
		if(result == IN_FRONT){
			renderTree(tree.back);
			for(i=0;i<j;i++){
				tmp = (int[])tree.lines[i];
				if(evalPoint(eye_x,eye_y,getLineEquation(tmp)) == IN_FRONT)
					theParent.renderLine(tmp);
			}
			renderTree(tree.front);
		}else if(result == IN_BACK){
			renderTree(tree.front);
			for(i=0;i<j;i++){
				tmp = (int[])tree.lines[i];
				if(evalPoint(eye_x,eye_y,getLineEquation(tmp)) == IN_FRONT)
					theParent.renderLine(tmp);
			}
			renderTree(tree.back);
		}else{   /* the eye is on the partition plane */
			renderTree(tree.front);
			renderTree(tree.back);
		}
	}

	/** 
	 * a function to recursively march through the tree, and call
	 * the renderer function of... 
	 */
	public void renderTree(){
		renderTree(root);
	}
}

/**
 * The class that does most of the work...
 * (02/28/1998)
 */
public class javadata_dog3d extends Applet implements Runnable{
	/**
	 * variables...
	 */
	private Thread m_dog3d = null;
	private String m_map = "javadata_dog3dmap.txt";
	private int m_width,m_height;
	private int m_mousex = 0,m_mousey = 0;
	private Vector initial_map = null;
	private javadata_dog3dBSPTree theTree = null;
	
	private Image double_image = null;
	private Graphics double_graphics = null;

	/**
	 * the eye point...
	 */
	public int eye_x = 220,eye_y = 220;

	/**
	 * the eye angle...
	 */
	public double eye_angle = 0;
	
	/**
	 * some stuff for the key handler & mouse (controls)...
	 */
	private boolean KEYUP=false,KEYDOWN=false,
		KEYLEFT=false,KEYRIGHT=false;
	private boolean MOUSEUP=true;

	/**
	 * using current eye_x, eye_y, & eye_angle, project 
	 * the line onto the double buffer...
	 *
	 * @param l the line to render... 
	 */
	public void renderLine(int[] l){
		double x1=l[2];
		double y1=l[3];
		double x2=l[0];
		double y2=l[1];
		double pCos = Math.cos(eye_angle);
		double pSin = Math.sin(eye_angle);
		int[] x = new int[4];
		int[] y = new int[4];
		double pD=-pSin*eye_x+pCos*eye_y;
		double pDp=pCos*eye_x+pSin*eye_y;
		double rz1,rz2,rx1,rx2;
		int Screen_x1=0,Screen_x2=0;
		double Screen_y1,Screen_y2,Screen_y3,Screen_y4;
		rz1=pCos*x1+pSin*y1-pDp;     //perpendicular line to the players
		rz2=pCos*x2+pSin*y2-pDp;     //view point
		if((rz1<1) && (rz2<1))
			return;
		rx1=pCos*y1-pSin*x1-pD;
		rx2=pCos*y2-pSin*x2-pD;
		double pTan = 0;
		if((x2-x1) == 0)
			pTan = Double.MAX_VALUE;
		else
			pTan = (y2-y1)/(x2-x1);
		pTan = (pTan-Math.tan(eye_angle))/(1+
			(pTan*Math.tan(eye_angle)));
		if(rz1 < 1){
			rx1+=(1-rz1)*pTan;
			rz1=1;
		}if(rz2 < 1){
			rx2+=(1-rz2)*pTan;
			rz2=1;
		}
		double z1 = m_width/2/rz1;
		double z2 = m_width/2/rz2;
		Screen_x1=(int)(m_width/2-rx1*z1);
		Screen_x2=(int)(m_width/2-rx2*z2);
		if(Screen_x1 > m_width)
			return;
		if(Screen_x2<0)
			return;
		int wt=88;
		int wb=-40;
		Screen_y1=(double)m_height/2-(double)wt*z1;
		Screen_y4=(double)m_height/2-(double)wb*z1;
		Screen_y2=(double)m_height/2-(double)wt*z2;
		Screen_y3=(double)m_height/2-(double)wb*z2;
		if(Screen_x1 < 0){
			Screen_y1+=(0-Screen_x1)*(Screen_y2-Screen_y1)
				/(Screen_x2-Screen_x1);
			Screen_y4+=(0-Screen_x1)*(Screen_y3-Screen_y4)
				/(Screen_x2-Screen_x1);
			Screen_x1=0;
		}if(Screen_x2 > (m_width)){
			Screen_y2-=(Screen_x2-m_width)*(Screen_y2-Screen_y1)
				/(Screen_x2-Screen_x1);
			Screen_y3-=(Screen_x2-m_width)*(Screen_y3-Screen_y4)
				/(Screen_x2-Screen_x1);
			Screen_x2=m_width;
		}if((Screen_x2-Screen_x1) == 0)
			return;
		x[0] = (int)Screen_x1;
		y[0] = (int)(Screen_y1);
		x[1] = (int)Screen_x2;
		y[1] = (int)(Screen_y2);
		x[2] = (int)Screen_x2;
		y[2] = (int)(Screen_y3);
		x[3] = (int)Screen_x1;
		y[3] = (int)(Screen_y4);
		double_graphics.setColor(new Color(l[4]));
		double_graphics.fillPolygon(x,y,4);
	}

	/**
	 * loads the map file... 
	 */
	private void loadInputMap(){
		initial_map = new Vector();
		int[] tmp = null;
		int current;
		StreamTokenizer st = null;
   		try{
			st = new StreamTokenizer(
				(new URL(getDocumentBase(),m_map)).openStream());
		}catch(java.net.MalformedURLException e){
			System.out.println(e);
		}catch(IOException f){
			System.out.println(f);
		}
		st.eolIsSignificant(true);
		st.slashStarComments(true);
		st.ordinaryChar('\'');
		try{
			for(st.nextToken(),tmp = new int[5],current=1;
			st.ttype != StreamTokenizer.TT_EOF;
			st.nextToken(),current++){
				if(st.ttype == StreamTokenizer.TT_EOL){
					if(tmp != null)
						initial_map.addElement(tmp);
					tmp = null; tmp = new int[5];
					current=0;
				}else{
					if(current == 1)
						System.out.println("getting: "+st.nval);
					else if(current == 2)
						tmp[0] = (int)st.nval;
					else if(current == 3)
						tmp[1] = (int)st.nval;
					else if(current == 4)
						tmp[2] = (int)st.nval;
					else if(current == 5)
						tmp[3] = (int)st.nval;
					else if(current == 6)
						tmp[4] = (int)Integer.parseInt(st.sval,0x10);
				}
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/**
	 * the init method... initialization stuff
	 */
	public void init(){
		String param;
		param = getParameter("map");
		if (param != null)
			m_map = param;
		m_width = size().width;
		m_height = size().height;

		loadInputMap();

		double_image = createImage(m_width,m_height);
		double_graphics = double_image.getGraphics();

		theTree = new javadata_dog3dBSPTree(this);
		theTree.build(initial_map);

		theTree.eye_x = eye_x;
		theTree.eye_y = eye_y;
		theTree.eye_angle = eye_angle;

		repaint();
	}

	/**
	 * the pint method...
	 *
	 * @param g The Graphics context into which to draw.
	 */
	public void paint(Graphics g){
		g.drawImage(double_image,0,0,null);
	}

	/**
	 * the update, called every time "repaint()" is called,
	 * or when ImageObserver stuff happens...
	 * 
	 * recieves a graphics context, with clipping regions
	 * set to the "update" region... 
	 * 
	 * @param g The graphics context into which to draw.
	 */
	public void update(Graphics g){
		double_graphics.setColor(Color.black);
		double_graphics.fillRect(0,0,m_width,m_height);
		theTree.renderTree();		
		paint(g);
	}

	/**
	 * start method, for thread stuff... 
	 */
	public void start(){
		if(m_dog3d == null){
			m_dog3d = new Thread(this);
			m_dog3d.start();
		}
	}

	/**
	 * the run method, thread runs here...
	 */
	public void run(){
		boolean call_update;
		while(true){
			call_update = false;
			if(MOUSEUP){
				if(KEYUP){
					eye_x += (int)(Math.cos(eye_angle)*10);
					eye_y += (int)(Math.sin(eye_angle)*10);
					call_update = true;
				}if(KEYDOWN){
					eye_x -= (int)(Math.cos(eye_angle)*10);
					eye_y -= (int)(Math.sin(eye_angle)*10);
					call_update = true;
				}if(KEYLEFT){
					eye_angle += Math.PI/45;
					call_update = true;
				}if(KEYRIGHT){
					eye_angle -= Math.PI/45;
					call_update = true;
				}if(call_update){
					theTree.eye_x = eye_x;
					theTree.eye_y = eye_y;
					theTree.eye_angle = eye_angle;
					repaint();
				}
			}
			try{
				Thread.sleep(5);
			}catch(java.lang.InterruptedException e){
				System.out.println(e);
			}
		}
	}
	
	/**
	 * a key handler...
	 */
	public boolean keyUp(Event evt,int key){
		if(key == Event.UP){
			KEYUP = false;
		}else if(key == Event.DOWN){
			KEYDOWN = false;
		}else if(key == Event.LEFT){
			KEYLEFT = false;
		}else if(key == Event.RIGHT){
			KEYRIGHT = false;
		}
		return true;
	}

	/**
	 * a key handler...
	 */
	public boolean keyDown(Event evt,int key){
		if(key == Event.UP){
			KEYUP = true;
		}else if(key == Event.DOWN){
			KEYDOWN = true;
		}else if(key == Event.LEFT){
			KEYLEFT = true;
		}else if(key == Event.RIGHT){
			KEYRIGHT = true;
		}
		return true;
	}

	/**
	 * capture even when mouse is pressed...
	 *
	 * @param evt The event...
	 * @param x the x mouse position.
	 * @param y the y mouse posiiton.
	 * @return whether or not we handled the event.
	 */
	public boolean mouseDown(Event evt, int x, int y){
		MOUSEUP = false;
		m_mousex = x;
		m_mousey = y;
		return true;
	}

	/**
	 * capture mouse release..
	 *
	 * @param evt The event...
	 * @param x the x mouse position.
	 * @param y the y mouse posiiton.
	 * @return whether or not we handled the event.
	 */
	public boolean mouseUp(Event evt, int x, int y){
		MOUSEUP = true;
		m_mousex = x;
		m_mousey = y;
		return true;
	}

	/**
	 * capture mouse drag...
	 *
	 * @param evt The event...
	 * @param x the x mouse position.
	 * @param y the y mouse posiiton.
	 * @return whether or not we handled the event.
	 */
	public boolean mouseDrag(Event evt, int x, int y){
		if(m_mousey > y){
			eye_x += (int)(Math.cos(eye_angle)*(7));
			eye_y += (int)(Math.sin(eye_angle)*(7));
		}
		if(m_mousey < y){
			eye_x -= (int)(Math.cos(eye_angle)*(7));
			eye_y -= (int)(Math.sin(eye_angle)*(7));
		}
		if(m_mousex > x){
			eye_angle += Math.PI/32;
        }
		if(m_mousex < x){
			eye_angle -= Math.PI/32;
        }
		theTree.eye_x = eye_x;
		theTree.eye_y = eye_y;
		theTree.eye_angle = eye_angle;
		m_mousex = x;
		m_mousey = y;
		repaint();
		return true;
	}
}
