package rl;

public class MapStateTrap extends Special {
	protected int image;
	protected int tostate;
	
	private static final Description DESC_MAPSTATETRAP = 
		new Describer("runetrap","A set of runes. Maybe some kind of magical trigger.");
	
	public MapStateTrap(int s) {
	  image=160+RPG.r(3);
	  tostate=s;
	}
	
	public int getImage() {return image;}
	
	public int getZ() {return Z_FLOOR;}
	
	public void trigger(Thing m) {
	  if (m==Game.hero) {
	    Game.message("You feel a buzz of magic");
	    getMap().mapstate=tostate;
	    remove();
	  }
	}

  public Description getDescription() {return DESC_MAPSTATETRAP;}
}