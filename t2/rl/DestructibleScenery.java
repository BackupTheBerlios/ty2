package rl;

public class DestructibleScenery extends Scenery {
  protected int hps;
	
	public DestructibleScenery(int imagenumber, boolean solid, int hps) {
		//super(imagenumber,solid);
	}
	
	public int damage(int dam, int damtype) {
	  hps-=dam;
    if (hps<=0) remove();
	  return dam;	
	}
}