package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.Vector;
import java.util.Date;

// Panel descendant used to display a Map
// contains most of graphics redraw logic
// also some animation/explosion handling
public class MapPanel extends Panel {
	// tile size in pixels
	public final static int TILEWIDTH=16;
	public final static int TILEHEIGHT=16;
	// size of viewable area
	private int width=17;
	private int height=17;
	
	// zoom factor 
	public int zoomfactor=100;
	private int lastzoomfactor=100;
	
	private int PIXWIDTH=TILEWIDTH*zoomfactor;
	private int PIXHEIGHT=TILEHEIGHT*zoomfactor;
	
	// back buffer
	private Graphics buffergraphics;
	private Image buffer;	
		
	// which map to draw	
	public Map map=new Map(5,5);

	// viewing state
	private int scrollx=0;
	private int scrolly=0;
	public int curx=0;
	public int cury=0;
	public boolean cursor=false;

	// use cache
	public boolean useCache = false;
		
	public MapPanel () {
		super();
		
		setBackground(Color.black);
		
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				int dir=(e.getModifiers() & InputEvent.BUTTON3_MASK)
						== InputEvent.BUTTON3_MASK ? -1 : 1;

				Rectangle rect=getBounds();
				int rw=rect.width;
				int rh=rect.height;
				int x=(e.getX()-(rw-width*TILEWIDTH)/2)/TILEWIDTH+scrollx;
				int y=(e.getY()-(rh-height*TILEHEIGHT)/2)/TILEHEIGHT+scrolly;

				if (dir==1) {
					
					Mobile h=Game.hero;
					int rx=x-h.x;
					int ry=y-h.y;
					if ((rx==0)&&(ry==0)) return;
					int dx=-1;
					if (ry<(2*rx)) dx++;
					if (ry>(-2*rx)) dx++;
					int dy=-1;
					if (rx<(2*ry)) dy++;
					if (rx>(-2*ry)) dy++;
					Game.simulateDirection(dx,dy);
				} else {
					if (QuestApp.debug) {
						map.addThing(Game.hero,x,y);
						repaint();
					}
				}	
			} 
			
		});	
	}	
	
	// sets current scroll position and repaints map
	public void viewPosition(int x, int y) {
		scrollx=RPG.middle(0,x-width/2,map.getWidth()-width);
		scrolly=RPG.middle(0,y-height/2,map.getHeight()-width);
	
		repaint();
	}
	
	//override update to stop flicker
	public void update(Graphics g) {
		paint(g);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width*TILEWIDTH,height*TILEHEIGHT);
	}
	
	// draws tiles in box (x1,y1)-->(x2,y2) to back buffer
	public void drawTiles(int x1, int y1, int x2, int y2) {

		map.calcVisible(Game.hero.x,Game.hero.y,map.visibleRange()); //do LOS calculations
		
		//draw the specified area in buffer
		for (int y=y1; y<=y2 ; y++ ) {
			for (int x=x1 ; x<=x2 ; x++) {
				drawTile(x,y);
			}
		}
	}
	
	// draws tile at (x,y)
	// includes logic for mapping tile number to image
	// draw blank if outside map area
	public void drawTile(int x, int y) {
		int m=map.getTile(x,y);

		if (!map.isDiscovered(x,y)) m=0;
		Image source=map.isVisible(x,y)?QuestApp.tiles:QuestApp.greytiles; 
			
		int tile=m&65535;
		int px=(x-scrollx)*TILEWIDTH;
		int py=(y-scrolly)*TILEHEIGHT;
		
		switch (tile) {
			case 0:	//blank
				buffergraphics.setColor(Color.black);
				buffergraphics.fillRect((x-scrollx)*TILEWIDTH,(y-scrolly)*TILEHEIGHT,TILEWIDTH,TILEHEIGHT);
				break;
		
			// default method
			default: {
				int image;
				if (map.isDiscovered(x,y+1)&&Tile.filling[map.getTile(x,y+1)&65535]) {
					image=Tile.imagefill[tile];
				} else {
					image=Tile.images[tile];
				}
				 
				int sx=(image%20)*TILEWIDTH; 
				int sy=(image/20)*TILEHEIGHT; 
				buffergraphics.drawImage(source,px,py,px+16,py+16,sx,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);			
			
				// draw in coastlines
				if ((Tile.borders[tile]>0)&&(source==QuestApp.tiles)) {
					if ((x>0)							&& (map.getTile(x-1,y)!=m)) buffergraphics.drawImage(QuestApp.scenery,px,py,px+16,py+16,0,256,16,256+16,null);
					if ((x<(map.width-1))	&& (map.getTile(x+1,y)!=m)) buffergraphics.drawImage(QuestApp.scenery,px,py,px+16,py+16,16,256,32,256+16,null);
					if ((y>0)							&& (map.getTile(x,y-1)!=m)) buffergraphics.drawImage(QuestApp.scenery,px,py,px+16,py+16,32,256,48,256+16,null);
					if ((y<(map.height-1)) && (map.getTile(x,y+1)!=m)) buffergraphics.drawImage(QuestApp.scenery,px,py,px+16,py+16,48,256,64,256+16,null);
				}
				break;
			}
			//end of switch	 
		}
				
		if (map.isVisible(x,y)) drawThings(x,y);
	}	
	
	// Draw all visible objects on map to back buffer
	// side effect: sorts map objects in increasing z-order	
	private void drawThings(int x,int y) {
		Image im=null;
		Thing head=map.sortZ(x,y);
		if (head==null) return;
		int px=(x-scrollx)*TILEWIDTH;
		int py=(y-scrolly)*TILEHEIGHT;
		do {
			int i=head.getImage();
			int sx=(i%20)*TILEWIDTH;
			int sy=(i/20)*TILEHEIGHT;	
			if (!head.isInvisible()) {
				if (head instanceof Special)	im=QuestApp.scenery;			
				if (head instanceof Mobile) im=QuestApp.creatures;			
				if (head instanceof Item) im=QuestApp.items;	
				buffergraphics.drawImage(im, px,py,px+16,py+16, sx ,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);			
			}
			head=head.next;
		} while (head!=null);
	}
	
	// place cursor at specified position
	public void setCursor(int x,int y) {
		cursor=true;
		curx=x;
		cury=y;
		repaint();	
	}
	
	// remove cursor from map
	public void clearCursor() {
		cursor=false;
		repaint();	
	}
	
	// animates a shot from (x1,y1) to (x2,y2)
	public void doShot(int x1, int y1, int x2, int y2, int c, double speed) {
		int rx=x2-x1;
		int ry=y2-y1;
		if ((rx==0)&&(ry==0)) return;
		double d=Math.sqrt(rx*rx+ry*ry);
		double dx=rx*(speed/d);
		double dy=ry*(speed/d);
		double x=x1;
		double y=y1;
		int sx=TILEWIDTH*(c%20);
		int sy=TILEHEIGHT*(c/20);
		drawTiles(scrollx,scrolly,scrollx+width-1,scrolly+height-1);
		for (int i=0; i<(int)(d/speed); i++) {
			int px=(int) ((x-scrollx)*TILEWIDTH*zoomfactor/100);
			int py=(int) ((y-scrolly)*TILEHEIGHT*zoomfactor/100);
			Graphics g=getGraphics();
			if (g!=null) {
				Rectangle rect=getBounds();
				int ox=(rect.width-width*TILEWIDTH*zoomfactor/100)/2;
				int oy=(rect.height-width*TILEHEIGHT*zoomfactor/100)/2;
				drawMap(g);
				g.drawImage(QuestApp.effects,px+ox,py+oy,px+ox+TILEWIDTH*zoomfactor/100,py+oy+TILEHEIGHT*zoomfactor/100,sx,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);
			}
			x+=dx;
			y+=dy;
		}
		repaint();
	}
 
	// makes an explosion (3*3) of the specified style
	public void doExplosion(int x, int y, int c, int r) {
		if (!map.isVisible(x,y)) return;
		int explosiontype=c;
		for (int i=0;i<=5;i++) {
			drawTiles(scrollx,scrolly,scrollx+width-1,scrolly+height-1);
			int px=(x-scrollx)*TILEWIDTH;
			int py=(y-scrolly)*TILEHEIGHT;
			int sx=i*TILEWIDTH;
			int sy=TILEHEIGHT*explosiontype;
			for (int dy=-1;dy<=1;dy++) for (int dx=-1;dx<=1;dx++) {
				buffergraphics.drawImage(QuestApp.effects,px+3*r*dx*i,py+3*dy*r*i,px+3*r*dx*i+TILEWIDTH,py+3*r*dy*i+TILEHEIGHT,sx,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);
			}
	 		Graphics g=getGraphics();
	 		if (g!=null) drawMap(g);
		}
		repaint();
	}

	public void doSpark(int x, int y, int c) {
		if (!map.isVisible(x,y)) return;
		int explosiontype=c;
		for (int i=0; i<=5; i++) {
			drawTiles(scrollx,scrolly,scrollx+width-1,scrolly+height-1);
			int px=(x-scrollx)*TILEWIDTH;
			int py=(y-scrolly)*TILEHEIGHT;
			int sx=i*TILEWIDTH;
			int sy=TILEHEIGHT*explosiontype;
			buffergraphics.drawImage(QuestApp.effects,px,py,px+TILEWIDTH,py+TILEHEIGHT,sx,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);
	 		Graphics g=getGraphics();
	 		if (g!=null) drawMap(g);
		}
		repaint();
	}


	// simple explosion
	public void doExplosion(int x, int y, int c, int dam, int damtype) {
		doExplosion(x,y,c,1);
		map.areaDamage(x,y,2,dam,damtype);	
	}

	
	// draws cursor at given location to buffer
	public void drawCursor(int x, int y) {
		int px=(x-scrollx)*TILEWIDTH;
		int py=(y-scrolly)*TILEHEIGHT;
		int sx=6*TILEWIDTH;
		int sy=0*TILEHEIGHT;
		buffergraphics.drawImage(QuestApp.effects,px,py,px+TILEWIDTH,py+TILEHEIGHT,sx,sy,sx+TILEWIDTH,sy+TILEHEIGHT,null);
	}
	
	// draw buffer to screen in correct location
	public void drawMap(Graphics g) {
		Rectangle rect=getBounds();
		int w=rect.width;
		int h=rect.height;
		//g.drawImage(buffer,(w-width*TILEWIDTH*zoomfactor)/2,(h-height*TILEHEIGHT*zoomfactor)/2,null);		
		g.drawImage(buffer,(w-width*TILEWIDTH*zoomfactor/100)/2,(h-height*TILEHEIGHT*zoomfactor/100)/2,width*TILEWIDTH*zoomfactor/100,height*TILEHEIGHT*zoomfactor/100,null);		
	}
	
	// standard paint method
	// - builds map image in back buffer then copies to screen
	public void paint(Graphics g) {
		Rectangle rect=getBounds();
		if (zoomfactor!=lastzoomfactor) {
			int w=rect.width;
			int h=rect.height;
			g.setColor(Color.black);
			g.fillRect(0,0,w,h);
			lastzoomfactor=zoomfactor;
		}
		
		// create back buffer if needed
		if (buffer==null) {
			System.out.println("Getting buffer." + new Date().toString());
			buffer = createImage(width*TILEWIDTH,height*TILEHEIGHT);
			buffergraphics = buffer.getGraphics();
		}
		
		Rectangle b=rect; //g.getClipBounds();
		
		// draw area to back buffer
		if(!useCache){
			drawTiles((b.x)/TILEWIDTH+scrollx,(b.y/TILEHEIGHT+scrolly),(b.x+b.width)/TILEWIDTH+scrollx,((b.y+b.height)/TILEHEIGHT+scrolly));
			if (cursor) drawCursor(curx,cury);
		}else{
			useCache = false;
		}
			
		drawMap(g);
 	}	
}