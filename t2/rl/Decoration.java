package rl;

// Decoration for dungeon
// no real game effects, but looks nice :-)
public class Decoration extends Scenery {
	public static final int DEC_REDBLOOD=0;
	public static final int DEC_GREENBLOOD=1;

  private static final Description DESC_BLOOD = 
  	new Describer("blood pool","A pool of recently spilled blood.");
	
  public Decoration(int n) {
	  blocking=false;
	  switch (n) {
	    case 0: image=100+RPG.r(4); break;	
	    case 1: image=104; break;	
	  }				
	}
	
	public int damage(int dam, int damtype) {
	  remove();
	  return dam;
	}
	
	public int getZ() {return Z_ONFLOOR;}

  public boolean isInvisible() {return false;}
  
  public Description getDescription() {
	  return DESC_BLOOD;	
	}
}