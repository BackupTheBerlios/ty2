// Root class for scenic items and decorations on maps

package rl;

public class Scenery extends Special {
	protected int image;
	protected boolean blocking;
	protected Description desc;
	
	public Scenery() {
	  image=40;
	  blocking=false;	
	}
	
	public Scenery(int imagenumber, boolean solid, Description d) {
		image = imagenumber;
		blocking = solid;
		desc=d;
	}
	
	public void use() {
	}
	
  // consider scenery owned
  public boolean isOwned() {return true;}
  
  public boolean isBlocking() {return blocking;}
	
  public boolean isInvisible() {return false;}
  
  public int getImage() {return image;}
	
	public Description getDescription() {return desc;}
  
  public void die() {
    // local folk don't approve of vandalism!
    if (Game.actor==Game.hero) {
      getMap().areaNotify(x,y,6,AI.EVENT_THEFT,1,Game.hero);
    }
    super.die();
  }
}