package rl;

public class Trap extends Special {
  protected boolean discovered=false;
  protected boolean activated=true;
  protected int uses=1;
  protected int level=1;

  public static Trap createTrap(int l) {
    switch (RPG.d(3)) {
      case 1: {
        return new RuneTrap(null,Spell.randomOffensiveSpell(l)); 
      }
      default: return new HeroTrap(l);
    }
  }
  
  
  public int getLevel() {
    return level; 
  }
  
  // trap is invisible until discovered
  public boolean isInvisible() {return !discovered;}
  
  public boolean isDiscovered() {return discovered;}
  
  // returns true if t is "wary" of this trap
  public boolean isWarning(Thing t) {
    return ((!discovered)&&(t instanceof Creature))||(t.getStat(RPG.ST_IN)>5); 
  }
  
  public boolean tryDisarm(Thing b) {
    return RPG.test(b.getStat(RPG.ST_CR)*b.getStat(RPG.ST_DISARM),15*getLevel());
  }
  
  // trap is right on the floor
  public int getZ() {return Z_FLOOR;}

  // trigger, activated by Thing t
  public void trigger(Thing t) {
    if (t!=null) {
      Map map=t.getMap();
      if (map!=null) {
        int tx=t.getMapX();
        int ty=t.getMapY();
        trigger(map,tx,ty);
        return;  
      } 
    }
    
    Map map=getMap();
    if (map!=null) {
      int tx=getMapX();
      int ty=t.getMapY();
      trigger(map,tx,ty);
      return;
    }
    
    throw new Error("Trap triggered off-map");
  }
  
  public void setActivated(boolean b) {
    activated=b; 
  }
  
  public boolean isActivated() {
    return activated; 
  }
  
  // return true if the trap should target an adjacent actor
  // that triggers the trap
  // trap types that should probably return true are:
  //    pit traps
  //    dart traps
  //    single-square spell traps (but not ball spells)
  public boolean actAdjacent() {
    return false; 
  }
  
  // trigger in specific location
  public void trigger(Map map, int tx, int ty) {
    if (activated) {
      // turn off trap to prevent self-triggering
      setActivated(false);
      affectSquare(map,tx,ty);
      
      // turn trap back on
      setActivated(true);
      
      // deactivate if uses are exhausted
      if (uses>0) {
        uses=uses-1;
        if (uses==0) {
          setActivated(false); 
          remove();
        }
      } 
    }
  }
  
  // actually do the trap affect
  public void affectSquare(Map map, int tx, int ty) {
    
  }
  
  // make the trap appear
  public boolean appear() {
    if (discovered) return false;
    discovered=true;
    return true;
  }
}