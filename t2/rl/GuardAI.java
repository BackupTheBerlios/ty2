//
// AI class to guard a particulat region
//
package rl;

public class GuardAI extends BaseAI {
  int x1; 
  int y1; 
  int x2;
  int y2;
  Thing bound1;
  Thing bound2;
  
  public GuardAI(int p1, int q1, int p2, int q2) {
    super(); 
    setArea(p1,q1,p2,q2);
  }
  
  public GuardAI(Thing b1, Thing b2) {
    super();
    bound1=b1;
    bound2=b2; 
  }
  
  // set area to guard, reversing co-ordinates if necessary
  public void setArea(int p1, int q1, int p2, int q2) {
    if (p1<p2) {x1=p1; x2=p2;} else {x2=p1; x1=p2;};
    if (q1<q2) {y1=q1; y2=q2;} else {y2=q1; y1=q2;};
  }
  
  public boolean doAction(Mobile m) {
    if ((x1==0)&&(bound1!=null)) {
      setArea(bound1.getMapX(),bound1.getMapY(),bound2.getMapX(),bound2.getMapY());
    }
    
    int tx=(x1+x2)/2; 
    int ty=(y1+y2)/2;
    
    Map map=m.getMap();
    
    if (map==null) return false;
    
    Thing f=map.findNearestFoe(m);
    if ((f!=null)&&RPG.dist(m.x,m.y,f.x,f.y)<=8) {
      NastyCritterAI.instance.doAttack(m,f.x,f.y);
    } else if ((m.x<x1)||(m.x>x2)||(m.y<y1)||(m.y>y2)) {
      // outside area, need to get back in! 
      NastyCritterAI.instance.doAttack(m,tx,ty);
    } else {
      boolean changedirection=(RPG.d(6)==1)||((m.xdirection==0)&&(m.ydirection==0));
      if (changedirection) m.xdirection=RPG.r(3)-1;
      if (changedirection) m.ydirection=RPG.r(3)-1;
      NastyCritterAI.instance.doAttack(m,m.x+m.xdirection,m.y+m.ydirection);
    }
    return true;
  }
}