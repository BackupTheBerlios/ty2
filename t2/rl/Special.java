package rl;

// Effect object represent base class for all special map objects
// including scenery, duration spells, fires, traps
// secret doors etc.
// Ability to detect actions that happen on a square
public class Special extends Thing {
	// called when the square is searched
	public void discover() {}
	
	// called when objects lans/steps on square
	public void trigger(Thing m) {}

  public void trigger(Map map, int tx, int ty) {}

  // called when object enters same sqiare
	public void enter(Thing m, boolean touchfloor) {
    if (touchfloor) {
      trigger(getMap(),x,y); 
    }  
  }
}