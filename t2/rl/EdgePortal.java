// The EdgePortal class represents a special kind of portal which can
// be accessed via any point on specified edges of a Map. 
// It is intended mainly for outdoor locations etc.

package rl;

public class EdgePortal extends Portal {
  // Constants indicate which map edge is used
  // can use ZONE to allow  arrivals anywhere in the map
  public static final int NORTH=1;
  public static final int SOUTH=2;
  public static final int EAST=4;
  public static final int WEST=8;
  public static final int ALL=15;
  public static final int ZONE=16;

  // what side the edge portal is on
  protected int side;
  
  public EdgePortal(int s) {
    super(0);  // invisible portal superclass
    side=s; 
  }
  
  public boolean isEdge(int tx, int ty) {
    if ((side&ZONE)>0) return true;
    if (((side&NORTH)>0)&&(ty==0)) return true;
    if (((side&WEST)>0)&&(tx==0)) return true;
    if (((side&EAST)>0)&&(tx==getMap().getWidth()-1)) return true;
    if (((side&SOUTH)>0)&&(ty==(getMap().getHeight()-1))) return true;
    return false;
  }
  
  public void arrive(Thing t) {
    
    Map map=getMap();
    
    if (map==null) throw new Error("EdgePortal Error - no map specified");
    
    int tx=-1; int ty=-1; int i=0;
    
    while (tx==-1) {
      switch (RPG.d(5)) {
        case 1: if ((side&NORTH)>0) {ty=0; tx=RPG.r(map.getWidth());}; break;
        case 2: if ((side&SOUTH)>0) {ty=map.getHeight()-1; tx=RPG.r(map.getWidth());}; break;
        case 3: if ((side&WEST)>0) {tx=0; ty=RPG.r(map.getHeight());}; break;
        case 4: if ((side&EAST)>0) {tx=map.getWidth()-1; ty=RPG.r(map.getHeight());}; break;
        case 5: if ((side&ZONE)>0) {tx=RPG.r(map.getWidth()); ty=RPG.r(map.getHeight());}; break;
      } 
      
      i++; if (i>5000) throw new Error("EdgePortal Error - unable to find space to add "+t.getName());
    
      if ((tx>=0)&&(map.isBlocked(tx,ty))) tx=-1;
    };
    
    t.moveTo(map,tx,ty);
  }
}