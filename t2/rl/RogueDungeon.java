//
// Dungeon in traditional rogue style
//

package rl;

//import java.util.Random;

class RogueDungeon extends Map {
  // size of map in terms of blocks
  protected int bh;
  protected int bw;

  // size of blocks in number of tiles
  protected int th;
  protected int tw;
  
  // room links
  protected static final int LN=1;
  protected static final int LE=2;
  protected static final int LS=4;
  protected static final int LW=8;
  protected int[][] links;
  protected int[][] linkedzones;
  
  // room dimensions
  protected int[][] rx;
  protected int[][] ry;
  protected int[][] rw;
  protected int[][] rh;
  
  
  private RogueDungeon(int width, int height, int tilewidth, int tileheight) { 
    super(width*tilewidth,height*tileheight);
    try {
      th=tileheight;
      tw=tilewidth;
      bw=width;
      bh=height;
    
      rx=new int[width][];
      ry=new int[width][];
      rw=new int[width][];
      rh=new int[width][];
      links=new int[width][];
      linkedzones=new int[width][];
    
      for (int w=0; w<width; w++) {
        rx[w]=new int[height]; 
        ry[w]=new int[height]; 
        rw[w]=new int[height]; 
        rh[w]=new int[height]; 
        links[w]=new int[height]; 
        linkedzones[w]=new int[height]; 
      } 
    } catch (Exception e) {
      Game.message("Failed to construct RogueDungeon"); 
      Game.message(e.toString());
    }
  }  
  
  public static Map create(int l, Theme theme) {
    try {
      RogueDungeon map=new RogueDungeon(3+RPG.r(l/3),3+RPG.r(l/3),8+RPG.d(l),8+RPG.d(l));
      map.setLevel(l);
      map.setTheme(theme);
      map.build();
      return map;
    } catch (Exception e) {
      Game.message("Failed to create RogueDungeon"); 
      Game.message(e.toString());
      return new Dungeon(50,50,l);
    }
  }
  
  public void replaceLinkedZones(int fromzone, int tozone) {
    for (int lx=0; lx<bw; lx++) for (int ly=0; ly<bh; ly++) {
      if (linkedzones[lx][ly]==fromzone) linkedzones[lx][ly]=tozone; 
    }
    // System.out.println(fromzone+" -> "+tozone);
  }
  
  // link two adjacent linkedzones
  public void makeLink(int x, int y, int dx, int dy) {
    if (dx==1) {
      links[x][y]|=LE; 
      links[x+dx][y+dy]|=LW; 
    }
    if (dx==-1) {
      links[x][y]|=LW; 
      links[x+dx][y+dy]|=LE; 
    }
    if (dy==1) {
      links[x][y]|=LS; 
      links[x+dx][y+dy]|=LN; 
    }
    if (dy==-1) {
      links[x][y]|=LN; 
      links[x+dx][y+dy]|=LS; 
    }
    
    try {
      int fromzone=linkedzones[x][y];
      int tozone=linkedzones[x+dx][y+dy];
      replaceLinkedZones(fromzone,tozone);
    } catch (Exception e) {
      Game.message(e.toString()+" ("+x+","+y+") ("+(x+dx)+","+(y+dy)+")"); 
    }
  }
  
  // ensure the whole map is a single zone
  public void makeConnected() {
    
    boolean connected=false;
    for (int i=0; (i<100000)&&(!connected); i++) {
      int tx=RPG.r(bw);
      int ty=RPG.r(bh);
      int dx=RPG.r(3)-1;
      int dy=RPG.r(3)-1;
      
      while (
              ( ((dy==0)&&(dx==0)) || ((dx*dy)!=0) )
           || ((tx+dx)<0)||((tx+dx)>=bw)||((ty+dy)<0)||((ty+dy)>=bh)
           || (linkedzones[tx][ty]==linkedzones[tx+dx][ty+dy])
        ) 
      {
        tx=RPG.r(bw);
        ty=RPG.r(bh);
        dx=RPG.r(3)-1;
        dy=RPG.r(3)-1;
      }
      
      makeLink(tx,ty,dx,dy);
      
      // test to see if everything is connected
      int minzone=10000;
      int maxzone=0;
      for (int x=0; x<bw; x++) for (int y=0; y<bh; y++) {
        int z=linkedzones[x][y];
        if (z<minzone) minzone=z;
        if (z>maxzone) maxzone=z;
      }   
      connected=(minzone==maxzone);
    };
    
  }
  
  public void createDoor(int tx, int ty) {
    switch(RPG.d(3)) {
      case 1: 
        setTile(tx,ty,floortile);
        break; 
      
      default:
        theme.createDoor(this,tx,ty,getLevel());
    } 
  }
  
  public void build() {
    if (constructed) return;
    
    // set up lots of individual linkedzones
    for (int x=0; x<bw; x++) for (int y=0; y<bh; y++) {
      linkedzones[x][y]=x+bw*y; 
    }
    
    // connect all the linkedzones
    makeConnected();
    
    // set the room dimensions (ex. wall)
    for (int x=0; x<bw; x++) for (int y=0; y<bh; y++) {
      rw[x][y]=RPG.rspread(2,tw-3);
      rh[x][y]=RPG.rspread(2,th-3);
      rx[x][y]=x*tw+RPG.d(tw-rw[x][y]-2); 
      ry[x][y]=y*th+RPG.d(th-rh[x][y]-2); 
    }
    
    // make all the rooms
    for (int x=0; x<bw; x++) for (int y=0; y<bh; y++) {
      fillArea(rx[x][y],ry[x][y],rx[x][y]+rw[x][y]-1,ry[x][y]+rh[x][y]-1,floortile); 
    }
    
    // make all the corridors, looking E
    for (int x=0; x<(bw-1); x++) for (int y=0; y<bh; y++) {
      if ((links[x][y]&LE)>0) {
        int py1=ry[x][y]+RPG.r(rh[x][y]); 
        int py2=ry[x+1][y]+RPG.r(rh[x+1][y]); 
        
        int minx=rx[x][y]+rw[x][y]+1;
        int maxx=rx[x+1][y]-2;
        
        createDoor(minx-1,py1);
        createDoor(maxx+1,py2);

        int px=RPG.rspread(minx,maxx);
        
        fillArea(minx,py1,px,py1,floortile);
        fillArea(px,py1,px,py2,floortile);
        fillArea(px,py2,maxx,py2,floortile);
      }      
    }
    
    // make all the corridors, looking S
    for (int x=0; x<bw; x++) for (int y=0; y<(bh-1); y++) {
      if ((links[x][y]&LS)>0) {
        int px1=rx[x][y]+RPG.r(rw[x][y]); 
        int px2=rx[x][y+1]+RPG.r(rw[x][y+1]); 
        
        int miny=ry[x][y]+rh[x][y]+1;
        int maxy=ry[x][y+1]-2;
        
        createDoor(px1,miny-1);
        createDoor(px2,maxy+1);
        
        int py=RPG.rspread(miny,maxy);
        
        fillArea(px1,miny,px1,py,floortile);
        fillArea(px1,py,px2,py,floortile);
        fillArea(px2,py,px2,maxy,floortile);
      }      
    }
    
    replaceTiles(0,walltile);
    
    constructed=true; 
  }
  
}