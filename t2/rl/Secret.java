// This represents a secret/hidden map element
// Does not appear unless discovered

package rl;

public class Secret extends Special {
  // secret item types
  public static final int MARKER=-1;
  public static final int ITEM=0;
  public static final int DOOR=1;
  public static final int PASSAGE=2;
  public static final int AMBUSH=3;
  
  protected int type;
  protected Thing item;
  
  public Secret() {
    type=MARKER;
  }
  
  public Secret(int t) {
    type=t; 
  }
  
  public Secret(Thing it) {
    type=ITEM;
    item=it; 
  }

	// this gets called if secret gets found
	public void discover() {
    Map map=getMap();
 
 	  switch (type) {
      case MARKER:
        // don't do anything with this
        // especially don't remove it!
        // used as a potential portal target
        return;
      
      case ITEM:
        Game.message("You have found a hidden item!");	
        map.addThing(item,x,y);
        item=null;
        break;
        
      case DOOR:  
        Game.message("You have found a secret door!");
        map.setTile(x,y,map.floortile);
        map.addThing(Door.createDoor(map.getLevel()),x,y);
        break;
                
      case PASSAGE:  
        Game.message("You have found a secret passage!");
        map.setTile(x,y,map.floortile);
        break;
        
      case AMBUSH:
        Point p=map.findFreeSquare(x-1,y-1,x+1,y+1);
        if (p!=null) {
          Game.message("Ambush!");
          map.addThing(Lib.createCreature(map.getLevel()),p.x,p.y);
        }
        break;
    }
    
    // put the secret out of action
    remove();
  }

  // can't see a secret thing!!
  public boolean isInvisible() {return true;}
}