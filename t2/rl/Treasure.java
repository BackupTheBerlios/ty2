package rl;

public class Treasure extends Item {
	private int image;
	
	private static Description desc=Lib.DESC_TREASURE;
	
	public Treasure() {
		image=140+RPG.r(4);
	}
	
	public Description getDescription() {return desc;}
	
	public int damage(int dam, int damtype) {
	  if (RPG.r(20)<(dam-8)) {
	    remove();	
	  }	
	  return dam;
	}
	
	public int getWeight() {return 500;}
	
	public int getImage() {return image;}
}