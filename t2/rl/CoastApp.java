package rl;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class CoastApp extends Applet {
  
  // map size: must be a power of 2
  public static final int size=128;
	
  // allocate map storage
  public int[] map= new int[size*size];
	
  public void paint(Graphics g) {
  super.paint(g);
		
  // draw the map
    for (int y=0; y<size; y++) for (int x=0; x<size; x++) {
      g.setColor( (getCell(x,y)==0) ? Color.blue : Color.green );
      g.fillRect(x*2,y*2,2,2);
    }
  }

  public void setCell(int x, int y, int c) {
    map[x+size*y]=c;	
  }
  
  public int getCell(int x, int y) {
    return map[x+size*y];	
  }
  
  // this routine creates the fractal landscape
  public void createFractal() {
    // initial pattern
    setCell(0,0,0);
    setCell(size/2,0,0);
    setCell(0,size/2,0);
    setCell(size/2,size/2,1);
  	
    // call the fractal process
  	makeFractal(size/4);   
    
    // redraw the screen
    repaint();
  }
  
  // this routine implements the fractal building process
  public void makeFractal(int step) {
    for (int y=0; y<size; y+=step) {
      for (int x=0; x<size; x+=step) {
        // add random offsets
        int cx=x+ ((Math.random()<0.5) ? 0 : step);
        int cy=y+ ((Math.random()<0.5) ? 0 : step);
			   
        // round to nearest multiple of step*2
        cx=(cx/(step*2))*step*2;
        cy=(cy/(step*2))*step*2;
			  
        // wrap around if needed
        cx=cx%size; 
        cy=cy%size; 
			  
        // copy from (cx,cy)
        setCell(x,y,getCell(cx,cy));
      }
    }
		
    // recursiveley calculate finer detail levels
    if (step>1) makeFractal(step/2);
  }
  
  // applet initialisation
  public void init() {
    super.init();
  
    // build the first fractal
    createFractal();
	  
    // rebuild on mouse clicks
    addMouseListener(new MouseAdapter() {
      public void mouseClicked( MouseEvent e ) {
        createFractal();	
      }
    });	
  }
	
  // main function allows applet to run as an application
  public static void main(String[] args) {
    
    // create a frame
    Frame f = new Frame("Fractal Coastlines");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    
    // set up frame and add applet
    f.setSize(300,300);
    f.setLayout(new BorderLayout());
    CoastApp q=new CoastApp();
    q.init();
    f.add(q);
    
    // go live....
    f.show();
  }
}