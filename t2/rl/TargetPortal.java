// Special Portal class to drop Things at a specific map location
//
// TargetPortals should not be placed on Maps, as they represent
// pointer to Map locations
//
// This is a one-way portal only: You cannot travel back through this portal
//
// Usage: 
//   someportal.setDestination(new TargetPortal(somemap,tx,ty));
//
// This can be especially useful in situations where you want maps to be disposed
// after they are exited, e.g. outdoor encounter Maps. The route to
// return to the WorldMap will be a TargetPortal
//

package rl;

public class TargetPortal extends Portal {
  private Map map;
  private int tx;
  private int ty; 
  
  public TargetPortal(Map m,int p,int q) {
    super(0);
    if (m==null) throw new Error("Can create TargetPortal without a target Map");
    map=m;
    tx=p;
    ty=q; 
  }
  
  public void arrive(Thing t) {
    map.addThing(t,tx,ty);
  }
  
}
