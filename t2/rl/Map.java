// The root class for all maps in the tyrant universe
//
// Map contains a wide variety of functions for creating and drawing
// map elements, and also for managing the set of all objects placed
// on the map
//
// All specific types of map (dungeons, towns etc) should extend Map
//
// Some particularly useful Map creation functions:
//   setTile
//   copyArea
//   fillArea
//   fillBorder
//   rotateArea
//
// Particularly useful object management functions:
//   addThing
//   getThings
//
// Some useful AI helper functions
//   getMobile
//   getNearbyMobile
//   countNearbyMobiles
//   findNearestFoe


package rl;

import java.io.Serializable;

public class Map extends Object implements ThingOwner, Serializable {
  // Map storage  
  protected int[] tiles;
  protected Thing[] objects;
  protected int width;
  protected int height;
  protected int size;
  protected boolean constructed=false;
  private int level;
  
  public Portal entrance; // starting point
  public Portal exit;     // ending point
 
  // create the default theme
  public Theme theme=new Theme("standard");
  
  // special state flag
  // used when dungeons switch mode
  // e.g. Emerald Dungeon super-spawn rate
  protected int mapstate=0;
  
  // zero is friendly
  // negative irreperable
  // postive will forget eventually
  public int angryinhabitants=0;

  // pathfinding temporary sorage
  // low byte = dist to hero
  public transient int[] path;

  // default floor+wall tiles
  public int walltile=Tile.WALL;
  public int floortile=Tile.FLOOR;
  
  
  // Internal constants
  public static final int LOS_DETAIL=26;
  
  
  //Direction stuff
  public static final int DIR_NONE=0;
  public static final int DIR_N=1;
  public static final int DIR_NE=2;
  public static final int DIR_E=3;
  public static final int DIR_SE=4;
  public static final int DIR_S=5;
  public static final int DIR_SW=6;
  public static final int DIR_W=7;
  public static final int DIR_NW=8;
  
  public static final int[] DX={0,0,1,1,1,0,-1,-1,-1};
  public static final int[] DY={0,-1,-1,0,1,1,1,0,-1};
  
  public Map(int w, int h) {
    // allocate arrays
    size=w*h;
    tiles=new int[size];
    path=new int[size];
    objects=new Thing[size];
    width=w;
    height=h;
    entrance=null;
  }
  
  public void setTheme(String s) {
    setTheme(new Theme(s)); 
  }
  
  // set the theme for the map
  public void setTheme(Theme t) {
    theme=t;
    floortile=theme.floor;
    walltile=theme.wall;
  }
  
  // set the theme for the map and keep original theme for later
  public void newTheme(Theme t) {
    t.next=theme;
    setTheme(t);
  }
  
  // return to the original theme
  // only use after corresponding newTheme()
  public void oldTheme() {
    setTheme(theme.next);
  }
  
  // set size (resets everything to blank)
  public void setSize(int w, int h) {
    size=w*h;
    tiles=new int[size];
    path=new int[size];
    objects=new Thing[size];
    width=w;
    height=h;
  }
  
  // checks if hero can exit from current location
  public boolean canExit() {
    Hero h=Game.hero;
    if ((h.x==0)||(h.y==0)||(h.x==(width-1))||(h.y==height-1)) {
      return true; 
    } else {
      Game.message("There is no way to exit here");
      return false; 
    }
  }
  
  public void setAngry(int a) {
    angryinhabitants=a; 
  }
  
  public boolean isAngry() {
    return (angryinhabitants!=0); 
  }
  
  // min of eight values
  private static final int min8(int a1,int a2,int a3,int a4,int a5,int a6,int a7,int a8) {
    if (a2<a1) a1=a2;
    if (a3<a1) a1=a3;
    if (a4<a1) a1=a4;
    if (a5<a1) a1=a5;
    if (a6<a1) a1=a6;
    if (a7<a1) a1=a7;
    if (a8<a1) a1=a8;
    return a1;
  }
  
  // calculate local paths to target square
  // i.e. creatures get fully accurate route to hero
  //   resulting path[] array contains:
  //                       0 at (px,py)
  //     distance to (px,py) at all reachable squares
  //                      -1 everywhere else
  
  public void calcPath(int px, int py, int distance) {
    // clear the path grid
    for (int x=0; x<width; x++) for (int y=0; y<height; y++) path[x+y*width]=-1;  
  
    path[px+py*height]=0;
    
    // calculate distance for all surrounding squares
    for (int i=1; i<distance; i++) {
      int x1=RPG.max(1,px-i);
      int y1=RPG.max(1,py-i);
      int x2=RPG.min(width-2,px+i);
      int y2=RPG.min(width-2,py+i);
      
      for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
        int pos=x+y*width;
        if (
         
         (path[pos]==-1) 
             
         &&( (getTile(x,y)&Tile.TF_BLOCKED)==0 ) 
                // note - don't use isBlocked(x,y) here
                // since we are only concerned with PERMANENT obstructions
         
         &&(   (path[pos-width-1]==i)
             ||(path[pos-width]==i)
             ||(path[pos-width+1]==i)
             ||(path[pos-1]==i)
             ||(path[pos+1]==i)
             ||(path[pos+width-1]==i)
             ||(path[pos+width]==i)
             ||(path[pos+width+1]==i)    )
        ) {
          path[pos]=i+1;  
        }  
      }
    }
    
    int x1=RPG.max(1,px-distance);
    int y1=RPG.max(1,py-distance);
    int x2=RPG.min(width-2,px+distance);
    int y2=RPG.min(width-2,py+distance);
    
    // now fill in individual square direction pointers
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      int pos=x+y*width;
      int v=path[pos];
      if (v>0) {
        for (int i=1; i<=8; i++) {
          if (path[pos+DX[i]+width*DY[i]] == (v-1)) {
            setTile(x,y,(getTileFull(x,y)&(~Tile.TF_DIRECTION))|(i*Tile.TF_DIRECTIONBASE));
            continue; 
          }
        }
      }
    }
  }
  
  // fractally builds an area - square technique
  // very cool algorithm!!
  // gran must be a power of 2
  public void fractalize(int x1, int y1, int x2, int y2, int gran) {
    int g=gran/2;
    if (g<1) return;
    for (int y=y1; y<=y2; y+=gran) for (int x=x1; x<=x2; x+=gran) {
      if (RPG.r(2)==0) setTile(x+g,y,getTileFull(x,y));       
      else setTile(x+g,y,getTileFull(x+gran,y));
      if (RPG.r(2)==0) setTile(x,y+g,getTileFull(x,y));       
      else setTile(x,y+g,getTileFull(x,y+gran));
    }
    
    // now do middle tile
    for (int y=y1; y<=y2; y+=gran) for (int x=x1; x<=x2; x+=gran) {
      int c;
      switch(RPG.d(4)) {
        case 1: c=getTileFull(x+g,y); break;
        case 2: c=getTileFull(x+g,y+gran); break;
        case 3: c=getTileFull(x,y+g); break;
        default: c=getTileFull(x+gran,y+g); break;
      }
      setTile(x+g,y+g,c);
    }
    
    // continue down to next level of detail
    if (g>1) fractalize(x1,y1,x2,y2,g);
  }
 
  // fractally builds an area - wrapping block technique
  // areas are NOT guaranteed to be connected
  // but looks prettier than vanilla fractalize
  // gran must be a power of 2
  public void fractalizeBlock(int x1, int y1, int x2, int y2, int gran) {
    int g=gran/2;
    int mask=~(gran-1);
    if (g<1) return;
    for (int y=y1; y<=y2; y+=g) for (int x=x1; x<=x2; x+=g) {
      int tx=(RPG.r(2)==0)?x:x+g;
      int ty=(RPG.r(2)==0)?y:y+g;       
      tx=((tx-x1)&mask)+x1;
      ty=((ty-y1)&mask)+y1;
      setTile(x,y,getTileFull(tx,ty));
    }
    if (g>1) fractalizeBlock(x1,y1,x2,y2,g);
  }  
  
  public void calcReachable(int px, int py) {
    // clear the path grid
    for (int x=0; x<width; x++) for (int y=0; y<height; y++) path[x+y*width]=0;  
  
    path[px+py*height]=1;
    
    boolean found=true;
    
    while (found) {
      found=false;
      for (int x=1; x<(width-1); x++) for (int y=1; y<(height-1); y++) {
        if (
         (path[x+y*width]==0) 
           
         &&( (getTile(x,y)&Tile.TF_BLOCKED)==0 )
         
         &&(   (path[x+(y-1)*width]==1)
             ||(path[x+1+(y-1)*width]==1)
             ||(path[x+1+y*width]==1)
             ||(path[x+1+(y+1)*width]==1)
             ||(path[x+(y+1)*width]==1)
             ||(path[x-1+(y+1)*width]==1)
             ||(path[x-1+y*width]==1)
             ||(path[x-1+(y-1)*width]==1)   )
        ) {
          path[x+y*width]=1;
          found=true;  
        }  
      }
    }
  }

  // replace all tiles of a certain type
  public void replaceTiles(int from, int to) {
    for (int x=0; x<width; x++) for (int y=0; y<height; y++) {
      if (getTile(x,y)==from) setTile(x,y,to);
    }  
  }

  // check if area is completely empty 
  public boolean isBlank(int x1,int y1,int x2,int y2) {
    // get right order for co-ordinates
    if (x1>x2) {int t=x1; x1=x2; x2=t;}
    if (y1>y2) {int t=y1; y1=y2; y2=t;}

    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      if (getTile(x,y)!=0) return false;
    }  
    return true;  
  }
  
  public void setLevel(int l) {
    level=l; 
  }
  
  // get level, if not set then use hero's current level
  public int getLevel() {
    if (level>0) return level;
    setLevel((Game.hero==null)?1:Game.hero.getStat(RPG.ST_LEVEL));
    return level; 
  }
  
  // count tiles of particular type in rectangular area
  public int countTiles(int x1,int y1,int x2,int y2, int c) {
    int count=0;
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      if (getTile(x,y)==c) count++;
    }  
    return count;
  }
  
  // fills rectangular map area with specified tile
  public void fillArea(int x1,int y1,int x2,int y2, int c) {
    if (x1>x2) {int t=x1; x1=x2; x2=t;}
    if (y1>y2) {int t=y1; y1=y2; y2=t;}
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      setTile(x,y,c);  
    }  
  }
  
  public void fillRoom(int x1, int y1,int x2,int y2, int wall, int floor) {
    fillArea(x1+1,y1+1,x2-1,y2-1,floor);
    fillBorder(x1,y1,x2,y2,wall); 
  }
  
  // clears all objects from area
  // use with EXTREME caution
  // don't kill portals, secret doors, artifacts etc!!!
  public void clearArea(int x1, int y1, int x2, int y2) {
    Thing[] things=getThings(x1,y1,x2,y2);
    for (int i=0; i<things.length; i++) {
      things[i].remove(); 
    }
  }

  // clear area and fill with specified tile
  public void clearArea(int x1, int y1, int x2, int y2,int c) {
    clearArea(x1,y1,x2,y2);
    fillArea(x1,y1,x2,y2,c);
  }  
  
  // spread src tiles over dst tiles
  // do this by filling adjacent squares
  public void spreadTiles(int x1,int y1,int x2,int y2, int src, int des) {
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      if (getTile(x,y)==des) {
        for (int dx=-1; dx<=1; dx++) for (int dy=-1; dy<=1; dy++) {
          if (getTile(x+dx,y+dy)==src) setTile(x,y,65535);
        }  
      }  
    }
    replaceTiles(65535,src);  
  }
 
  // randomly shakes tiles in area to blur outlines
  // useful to create irregular patterns
  public void blurArea(int x1, int y1, int x2, int y2) {
    for (int i=0; i<((x2-x1+1)*(y2-y1+1)/4); i++) {
      int sx=RPG.rspread(x1+1,x2-1); 
      int sy=RPG.rspread(y1+1,y2-1); 
      int dx=sx+RPG.r(3)-1;
      int dy=sy+RPG.r(3)-1;
      int t=getTileFull(sx,sy);
      setTile(sx,sx,getTileFull(dx,dy));
      setTile(dx,dy,t);
    }
  }

  // rotates a square map region by <count> right angles
  // rotates in a clockwize direction
  public void rotateArea(int x, int y, int size, int count) {
    // make rotation in range 0-3
    count&=3;
    
    // bail out or recurse as needed
    if (count==0) return;
    if (count>1) rotateArea(x,y,size,count-1);
    
    // rotate map objects
    Thing[] things=getThings(x,y,x+size-1,y+size-1);
    for (int i=0; i<things.length; i++) {
      Thing t=things[i];
      int nx=x+size-1-(t.y-y);
      int ny=y+(t.x-x);
      addThing(t,nx,ny); 
    }
    
    // rotate tiles
    for (int p=0; p<((size+1)/2); p++) for (int q=0; q<(size/2); q++) {
      int temp=getTileFull(x+p,y+q);
      setTile(x+p,y+q,getTileFull(x+q,y+size-1-p)); 
      setTile(x+q,y+size-1-p,getTileFull(x+size-1-p,y+size-1-q)); 
      setTile(x+size-1-p,y+size-1-q,getTileFull(x+size-1-q,y+p)); 
      setTile(x+size-1-q,y+p,temp); 
    } 
  } 

  // copy a complte map template
  public void copyArea(int tx, int ty, Map src) {
    copyArea(tx,ty,src,0,0,src.width,src.height);
  }
 
  // copy map contents to specified position
  public void copyArea(int tx, int ty, Map src, int sx, int sy, int sw, int sh) {
    // delete original contents
    clearArea(tx,ty,tx+sx,ty+sy);
    
    // square-by-square copy
    for (int x=tx; x<tx+sw; x++) for (int y=ty; y<ty+sh; y++) {
      setTile(x,y,src.getTileFull(x-tx+sx,y-ty+sy));
      Thing[] things=src.getThings(x-tx+sx,y-ty+sy);
      for (int i=0; i<things.length; i++) {
        addThing((Thing)things[i].clone(),x,y); 
      } 
    }
  }
  
  // creates a border around specified rectangular area
  public void fillBorder(int x1,int y1,int x2,int y2, int c) {
    if (x1>x2) {int t=x1; x1=x2; x2=t;}
    if (y1>y2) {int t=y1; x1=y2; y2=t;}
    for (int x=x1; x<=x2; x++) {
      setTile(x,y1,c); 
      setTile(x,y2,c); 
    }
    for (int y=y1; y<=y2; y++) {
      setTile(x1,y,c); 
      setTile(x2,y,c); 
    }
  }

  // fill all blank squares in area with specified tile
  public void completeArea(int x1,int y1,int x2,int y2, int c) {
    if (x1>x2) {int t=x1; x1=x2; x2=t;}
    if (y1>y2) {int t=y1; x1=y2; y2=t;}
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      if (getTile(x,y)==0) setTile(x,y,c);  
    }  
  }
  
  //set tile and return true if currently blank
  public boolean completeTile(int x, int y, int c) {
    if (getTile(x,y)==0) {
      setTile(x,y,c); return true;
    } else {
      return false;     
    }
  }
  
  // make a random path
  //   from (x1,y1) to (x2,y2)
  //   staying within region (x3,y3,x4,y4);
  public void makeRandomPath(int x1,int y1, int x2, int y2, int x3, int y3, int x4, int y4, int c, boolean diagonals) {
    int dx; int dy;
    while ((x1!=x2)||(y1!=y2)) {
      setTile(x1,y1,c);
      if (RPG.d(3)==1) {
        dx=RPG.sign(x2-x1);  
        dy=RPG.sign(y2-y1);  
      } else {
        dx=RPG.r(3)-1;  
        dy=RPG.r(3)-1;  
      }
      switch (RPG.d(diagonals?3:2)) {
        case 1: dx=0; break;
        case 2: dy=0; break;  
      }
      x1+=dx;
      y1+=dy;
      x1=RPG.middle(x3,x1,x4);
      y1=RPG.middle(y3,y1,y4);
    }
    setTile(x2,y2,c);  
  }
  
  public Point findClearTile() {
    int x;
    int y;
    for (int i=0; i<(width*height); i++) {
      x=RPG.r(width);
      y=RPG.r(height);
      if (isClear(x,y)) return new Point(x,y);
    }  
    return null;
  } 
  
  public Point findTile(int c) {
    int x;
    int y;
    c=c&Tile.TF_TYPE; //use tile type for comparison
    for (int i=0; i<(10*width*height); i++) {
      x=RPG.r(width);
      y=RPG.r(height);
      if ((getTile(x,y)&Tile.TF_TYPE)==c) return new Point(x,y);
    }  
    return null;
  }
  
  // find a free area, returning top left point
  public Point findFreeArea(int x1,int y1, int x2,int y2,int w,int h) {
    int px=x1; int py=y1;
    for (int i=(x2-x1)*(y2-y1); i>0; i--) {
      px=RPG.rspread(x1,x2-w+1);
      py=RPG.rspread(y1,y2-h+1);
      
      for (int lx=px; lx<px+w; lx++) for (int ly=py; ly<py+h; ly++) {
        if (!isClear(lx,ly)) continue; 
      } 
      return new Point(px,py);
    } 
    return null;
  }
  
  // find a random square in rectangular area which is:
  // a) not 0
  // b) not blocked
  // c) contains no stuff 
  public Point findFreeSquare(int x1, int y1, int x2, int y2) {
    int x;
    int y;
    // prefer completely free squares
    for (int i=(2*(x2-x1+2)*(y2-y1+2)); i>0; i--) {
      x=RPG.rspread(x1,x2);
      y=RPG.rspread(y1,y2);
      if (getTile(x,y)==0) continue;
      if ((getObjects(x,y)==null)&&(!isBlocked(x,y))) return new Point(x,y);
    }  
    // make do with unblocked squares
    for (int i=(2*(x2-x1+2)*(y2-y1+2)); i>0; i--) {
      x=RPG.rspread(x1,x2);
      y=RPG.rspread(y1,y2);
      if (getTile(x,y)==0) continue;
      if (!isBlocked(x,y)) return new Point(x,y);
    }  
    return null;
  }

  // find free square on entire map
  public Point findFreeSquare() {
    return findFreeSquare(0,0,width-1,height-1);
  }

  // find unblocked square with tile type c adjacent in specified direction
  public Point findEdgeSquare(int dx, int dy, int c) {
    int x;
    int y;
    for (int i=0; i<(10*width*height); i++) {
      x=RPG.r(width-2)+1;
      y=RPG.r(height-2)+1;
      if ((getTile(x,y)==0)||isBlocked(x,y)) continue;
      if (getTile(x+dx,y+dy)==c) return new Point(x,y);
    }  
    return null;
  }

  // find nearest enemy for a given mobile
  public Thing findNearestFoe(Mobile b) {
    if (b.place!=this) return null;
    int sx=b.x; 
    int sy=b.y;
    Mobile mob;
    
    for (int i=1; i<=7; i++) {
      for (int p=-i; p<i; p++) {
        mob=getMobile(sx+p,sy+i);
        if ((mob!=null)&&b.isHostile(mob)) return mob;
        mob=getMobile(sx-p,sy-i);
        if ((mob!=null)&&b.isHostile(mob)) return mob;
        mob=getMobile(sx+i,sy+p);
        if ((mob!=null)&&b.isHostile(mob)) return mob;
        mob=getMobile(sx-i,sy-p);
        if ((mob!=null)&&b.isHostile(mob)) return mob;
      }
    } 
    return null;
  }
  
  // return movement cost for entering square
  // 100=normal, 150=slowed, 300=difficult
  public int getMoveCost(Thing mover, int x, int y) {
    return Tile.getMoveCost(mover,getTileFull(x,y)); 
  }
  
  public String getName(int idlevel) { return "Special Map";}

  // add thing to map at given location
  // make this final for performance reasons.....
  public final void addThing(Thing thing,int x, int y) {
    if (thing==null) return;
    thing.remove();
    addObject(thing,x,y); 
  }
  
  // add thing to map in random location
  public final void addThing(Thing thing) {
    for (int i=0; i<width*height; i++) {
      int x=RPG.r(width);
      int y=RPG.r(height);
      if (!isBlocked(x,y)) {
        addThing(thing,x,y); 
        return;
      }
    }
    throw new Error("Can't add thing to map");
  }
  
  // add thing in random location in given area
  public final void addThing(Thing thing,int x1, int y1, int x2, int y2) {
    Point p=findFreeSquare(x1,y1,x2,y2);
    if (p!=null) addThing(thing,p.x,p.y);  
    else addThing(thing,x1,y1);
  }
  
  //move thing to location. basically same as addThing
  public void moveThing(Thing thing,int x, int y) {
    addThing(thing,x,y);
  }
  
  // removes thing from map, returns thing if found, null otherwise
  public void removeThing(Thing thing) {
    if (thing.place!=this) throw new Error("Thing in wrong place!");
    
    int loc=thing.x+thing.y*width;
    if (objects[loc]==thing) {
      objects[loc]=thing.next;
      thing.next=null;
      thing.place=null;
      return;
    }
    
    for (Thing head=objects[loc]; head!=null; head=head.next) {
      if (head.next==thing) {
        head.next=thing.next;
        thing.next=null;
        thing.place=null;
        return;
      }  
    }
  }
  
  public final Map getMap() {return this;}
  
  public boolean isTransparent(int x,int y) {
    int i=x+y*width;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return false;
    if ((tiles[i]&Tile.TF_TRANSPARENT)==0) return false;
    Thing track=objects[i];
    while (track!=null) {
      if (!track.isTransparent()) return false;
      track=track.next;
    }
    return true;
  }
  
  // is location on valid map area
  public final boolean isValid(int x,int y) {
    return (x>=0)&&(y>=0)&&(x<width)&&(y<height); 
  }
  
  // fast integer LOS algorithm
  public final boolean isLOS(int x1, int y1, int x2,int y2) {
    if (!(isValid(x1,y1)&&isValid(x2,y2))) return false;
    int x=(x1<<8)+128;
    int y=(y1<<8)+128;
    int xd=x2-x1;
    int yd=y2-y1;
    int dx;
    int dy;
    int count;
    if (RPG.abs(xd)<=RPG.abs(yd)) { 
      if (yd==0) return true; // check for (0,0)
      dx=(xd<<8)/RPG.abs(yd);
      dy=RPG.sign(yd)<<8;
      count=RPG.abs(yd);
    } else {
      dx=RPG.sign(xd)<<8;
      dy=(yd<<8)/RPG.abs(xd);
      count=RPG.abs(xd);
    }
    while (count>0) {
      x+=dx;
      y+=dy;
      if ((getTile((x>>8),(y>>8))&Tile.TF_TRANSPARENT)==0) return false;
      count--;
    }
    return true;
  }
  
  // return max visibility range for map
  public int visibleRange() {return 7;}
  
  // calculates all the visible squares for the hero at given position
  public void calcVisible(int x, int y, int r) {
    for(int i=0;i<size;i++) {
      tiles[i]&=(~Tile.TF_VISIBLE);
    }    
    setVisible(x,y);
    
    int tx=0;
    int ty=0;
    int px=0;
    int py=0;
    int c=0;
    
    boolean c1;
    boolean c2;
    boolean c3;
    boolean c4;
    
    for (int l=-LOS_DETAIL;l<=LOS_DETAIL;l++) {
      c1=true; c2=true; c3=true; c4=true;
      
      for (int d=1;d<r;d++) {
        if ((d*d+((d*l)/LOS_DETAIL)*((d*l)/LOS_DETAIL))>(r*r)) {
          c1 = c2 = c3 = c4 = false;  
        }
        
        if (c1) {
          tx=x+d; ty=(LOS_DETAIL*y+l*d+LOS_DETAIL/2)/LOS_DETAIL;
          if (isTransparent(tx,ty)) {
            px=x+d-1; py=(LOS_DETAIL*y+l*(d-1)+LOS_DETAIL/2)/LOS_DETAIL;
            c=getTileFull(tx,ty)|Tile.TF_VISIBLE|Tile.TF_DISCOVERED;
            setTile(tx,ty,c);
          } else {setVisible(tx,ty); c1=false;}
        } 
        
        if (c2) {
          tx=x-d; ty=(LOS_DETAIL*y+l*d+LOS_DETAIL/2)/LOS_DETAIL;
          if (isTransparent(tx,ty)) {
            px=x-d+1; py=(LOS_DETAIL*y+l*(d-1)+LOS_DETAIL/2)/LOS_DETAIL;
            c=getTileFull(tx,ty)|Tile.TF_VISIBLE|Tile.TF_DISCOVERED;
            setTile(tx,ty,c);
          } else {setVisible(tx,ty); c2=false;}
        }
        
        if (c3) {
          ty=y+d; tx=(LOS_DETAIL*x+l*d+LOS_DETAIL/2)/LOS_DETAIL;
          if (isTransparent(tx,ty)) {
            py=y+d-1; px=(LOS_DETAIL*x+l*(d-1)+LOS_DETAIL/2)/LOS_DETAIL;
            c=getTileFull(tx,ty)|Tile.TF_VISIBLE|Tile.TF_DISCOVERED;
            setTile(tx,ty,c);
          } else {setVisible(tx,ty); c3=false;}
        }
        
        if (c4) {
          ty=y-d; tx=(LOS_DETAIL*x+l*d+LOS_DETAIL/2)/LOS_DETAIL;
          if (isTransparent(tx,ty)) {
            py=y-d+1; px=(LOS_DETAIL*x+l*(d-1)+LOS_DETAIL/2)/LOS_DETAIL;
            c=getTileFull(tx,ty)|Tile.TF_VISIBLE|Tile.TF_DISCOVERED;
            setTile(tx,ty,c);
          } else {setVisible(tx,ty); c4=false;}
        }
      }    
    }
    
    // blindnes / trueview pass
    int blinded=Game.hero.getStat(RPG.ST_BLINDED);
    int trueview=Game.hero.getStat(RPG.ST_TRUEVIEW);
    for (int lx=x-r;lx<=x+r; lx++) for (int ly=y-r;ly<=y+r; ly++) {
      if ((blinded>0)&&((lx!=0)||(ly!=0))) {
        int t=getTileFull(lx,ly);
        setTile(lx,ly,t&(~Tile.TF_VISIBLE));
      }
      if ((trueview>0)&&(trueview>=((lx-x)*(lx-x)+(ly-y)*(ly-y)))) {
        setVisible(lx,ly); 
      }
    }
  }
  
  // Make a square visible
  public void setVisible(int x, int y) {
  int i=x+y*width;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return;
    if ((tiles[i]&Tile.TF_VISIBLE)==0) {
      tiles[i]|=Tile.TF_VISIBLE|Tile.TF_DISCOVERED;
      Mobile m=getMobile(x,y);
      if (m!=null) {
        m.notify(AI.EVENT_VISIBLE,0,null);
      }
    }
  }

  // Can the square currently be seen by the hero
  public boolean isVisible(int x,int y) {
    return (getTileFlags(x,y)&Tile.TF_VISIBLE)>0;  
  }
  
  // has the square been viewed at some point?
  public boolean isDiscovered(int x,int y) {
    return (getTileFlags(x,y)&Tile.TF_DISCOVERED)>0;  
  }

  public boolean isEmpty(int x,int y) {
    return getObjects(x,y)==null;  
  }
  
  public boolean isClear(int x,int y) {
    return isEmpty(x,y)&&(!isBlocked(x,y));  
  }

  public Point tracePath(int x1,int y1, int x2, int y2) {
    int xd=x2-x1; 
    int yd=y2-y1;
    if ((xd==0)&&(yd==0)) return new Point(x1,y1);
    double d=Math.sqrt(xd*xd+yd*yd);
    double dx=xd/d;
    double dy=yd/d;
    double x =x1+0.5+dx;
    double y= y1+0.5+dy;
    while ((((int)x)!=x2)||(((int)y)!=y2)) {
      x=x+dx;
      y=y+dy;
      int cx=(int)x;
      int cy=(int)y;
      if ((getTileFull(cx,cy)&Tile.TF_BLOCKED)!=0) return new Point((int)(x-dx),(int)(y-dy));
      if (isBlocked(cx,cy)) return new Point(cx,cy);
    }
    return new Point(x2,y2);
  }

  // Does the square contain a solid wall, monster or hard scenery
  public boolean isBlocked(int x,int y) {
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return true;
    int i=x+y*width;
    if ((tiles[i]&Tile.TF_BLOCKED)!=0) return true;
    Thing tracker = objects[i];
    while (tracker!=null) {
      if (tracker.isBlocking()) return true;  
      tracker=tracker.next;
    }
    return false;  
  }
  
  // return true if a tile is blocked by a sold wall
  public final boolean isTileBlocked(int x, int y) {
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return true;
    if ((tiles[x+y*width]&Tile.TF_BLOCKED)!=0) return true;
    return false;
  }
  
  public boolean isPassable(int x, int y) {
    Secret s=(Secret)getObject(x,y,Secret.class);
    return (!isBlocked(x,y)||(s!=null)); 
  }

  // get path code
  public int getPath(int x, int y) {
    return path[x+y*width];
  }
  
  // map metrics
  public int getWidth() {return width;}
  public int getHeight() {return height;}
  
  // get tile type
  public final int getTile(int x, int y) {
    int i=x+width*y;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return 0; // off-map tiles blank
    return tiles[i]&Tile.TF_TYPE;
  }    

  // get tile including flags
  protected final int getTileFull(int x, int y) {
    int i=x+width*y;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return Tile.TF_BLOCKED; //off-map tiles blocked
    return tiles[i];
  }    

  // get Tile.TF_ flags for specified tile
  public final int getTileFlags(int x, int y) {
    int i=x+width*y;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return Tile.TF_BLOCKED; //off-map tiles blocked
    return tiles[i]&(~Tile.TF_TYPE);
  }    
  
  // get linked list of objects in square
  public Thing getObjects(int x, int y) {
    int i=x+width*y;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return null;
    return objects[i];
  }    
  
  // get object of specified class
  public Thing getObject(int x, int y, Class c) {
    int i=x+width*y;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return null;
    Thing t=objects[i];
    while (t!=null) {
      if (c.isInstance(t)) return t;
      t=t.next;
    }
    return null;    
  }

  // add something to the map, keeping all references updated
  // final for performance reasons
  protected final void addObject(Thing thing, int x, int y) {
    if (thing==null) return;
    if ((x>=width)||(x<0)) return;
    if ((y>=height)||(y<0)) return;
      
    thing.next=objects[x+y*width];  
    objects[x+y*width]=thing;
    thing.place=(ThingOwner)this;
    thing.x=x;
    thing.y=y;
  }

  // set tile, including tile flags
  public final void setTile(int x, int y, int t) {
    int i=x+y*width;
    if ((x<0)||(y<0)||(x>=width)||(y>=height)) return;
    tiles[i]=t;
  }    
  
  public void hitSquare(Thing m,int x, int y,boolean touchfloor) {
    //Area events and trap logic goes here
    Thing t=getObjects(x,y);
    while (t!=null) {
      if (t instanceof Special) {
        ((Special)t).enter(m,touchfloor);  
      }  
      t=t.next;
    }
  }
  
  // gets a mobile from given square
  // returns Mobile or null if none available  
  public Mobile getMobile(int x, int y) {
    Thing tracker = getObjects(x,y);
    while (tracker!=null) {
      if (tracker.isMobile()) return (Mobile)tracker;
      tracker=tracker.next;  
    }
    return null;  
  }

  // functions to detect/return obvious nearby mobiles
  public int countNearbyMobiles(int x, int y, int r) {
    int count=0;
    for (int lx=x-r; lx<=x+r; lx++) for (int ly=y-r; ly<=y+r; ly++) {
      if (getMobile(lx,ly)!=null) count++;
    }
    return count;
  }
  
  public Mobile getNearbyMobile(int x, int y, int r) {
    for (int lx=x-r; lx<=x+r; lx++) for (int ly=y-r; ly<=y+r; ly++) {
      if ((lx==x)&&(ly==y)) continue;
      Mobile m=getMobile(lx,ly);
      if (m!=null) return m;
    }
    return getMobile(x,y);
  }
  
  // do damage in circular area (size r2 = radius squared)
  public void areaDamage(int x,int y, int r2,int dam, int damtype) {
    int d=0;
    for (d=0; d*d<=r2;d++);
    d=d-1;
    Thing[] things=getThings(x-d,y-d,x+d,y+d);
    for (int i=0; i<things.length; i++) {
      Thing t=things[i];
      if (((t.x-x)*(t.x-x)+(t.y-y)*(t.y-y))<=r2) {
        t.damageAll(RPG.a(dam),damtype);
      }
    }
  }
  
  // notify mobiles in an area
  public void areaNotify(final int x, final int y, final int r, int eventtype, int ext, Object o) {
    for (int px=x-r; px<=x+r; px++) for (int py=y-r; py<=y+r; py++) {
      Mobile m=getMobile(px,py);
      if (m!=null) m.notify(eventtype,ext,o);  
    }    
  }
  
  //Sorts objects in square into correct Z order
  //returns head item (lowest Z value)
  public Thing sortZ(int x, int y) {
    Thing head=getObjects(x,y);
    if (head==null) return null;
    return objects[x+y*width]=sortZGetFirst(head);
  }
  //utility function for sortZ
  private Thing sortZGetFirst(Thing top) {
    if (top.next==null) return top;
    top.next=sortZGetFirst(top.next);
    
    if (top.getZ()<=top.next.getZ()) {
      return top;  
    } else {
      Thing t=top.next;
      top.next=t.next;
      t.next=sortZGetFirst(top);
      return t;  
    }
  }
  
  // Functions to return array of things in area
  
  // return all things on map 
  public Thing[] getThings() {
    return getThings(0,0,width-1,height-1);  
  }

  // return all things in square
  public Thing[] getThings(int x, int y) {
    return getThings(x,y,x,y);  
  }
  
  // return all objects of given class in rectangular area
  public Thing[] getObjects(int x1, int y1, int x2, int y2, Class c) {
    int count=0;
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      Thing tracker=getObjects(x,y);
      while (tracker!=null) {
        if (c.isInstance(tracker)) count++;
        tracker=tracker.next;
      }
    }
    
    if (count==0) return new Thing[0];
    Thing[] things = new Thing[count];
    
    count=0;
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      Thing tracker=getObjects(x,y);
      while (tracker!=null) {
        if (c.isInstance(tracker)) {
          things[count]=tracker;
          count++;
        }
        tracker=tracker.next;
      }
    }
    return things;
  }
  
  // return all things in rectangular area
  public Thing[] getThings(int x1, int y1, int x2, int y2) {
    int count=0;
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      Thing tracker=getObjects(x,y);
      while (tracker!=null) {
        count++;
        tracker=tracker.next;
      }
    }
    
    if (count==0) return new Thing[0];
    Thing[] things = new Thing[count];
    
    count=0;
    for (int x=x1; x<=x2; x++) for (int y=y1; y<=y2; y++) {
      Thing tracker=getObjects(x,y);
      while (tracker!=null) {
        things[count]=tracker;
        count++;
        tracker=tracker.next;
      }
    }
    return things;
  }
  
  // execute map action for <time> ticks
  // this calls all monster, trap and item actions
  public void action(int time) {
    Thing[] things = getThings();
    
    for (int i=0; i<things.length; i++) {
      Thing t=things[i];
      // note: place could theoretically change (e.g. dead)
      if ((t instanceof Active)&&(t.place==this)) {
        Active m=(Active) t;
        m.action(time);
      } 
    }    
  }
  
  // get portal for specific square
  public Portal getPortal(int tx,int ty) {
    Portal p=(Portal)getObject(tx,ty,Portal.class);
    if (p!=null) return p;
    
    if (entrance instanceof EdgePortal) {
      EdgePortal e=(EdgePortal)entrance;
      if (e.isEdge(tx,ty)) return e;
    }
     
    if (exit instanceof EdgePortal) {
      EdgePortal e=(EdgePortal)exit;
      if (e.isEdge(tx,ty)) return e;
    } 
    
    // no portal here
    return null;
  }
  
  // exit map from specified location
  public void exitMap(int tx, int ty) {
    Hero h=Game.hero;
    Portal p=getPortal(tx,ty);
    
    // use default portal
    if ((p==null)&&canExit()) p=entrance;
    
    if (p!=null) {
      p.travel(h);
    } 
  }
  
  
  // set the message for this particular map
  public String getEnterMessage() {
    return "Welcome to The Tyrant's dungeon!";  
  }

  // Level name for display on status panel
  public String getLevelName() {
    return "The Tyrant's Dungeon";  
  }

}