//
// A chest, possibly trapped, possibly containing something useful
//

package rl;

public class Chest extends Scenery {
  private int trap;
  private boolean trapdetected;
  private boolean locked;
  private int level;
  private Thing contents;
	
  private static final Description DESC_CHEST =
		new Describer ("chest","A solid wooden chest.");
  private static final Description DESC_OPENCHEST =
		new Describer ("chest","An open chest.");
  private static final Description DESC_TRAPPEDCHEST =
		new Describer ("chest","A solid chest protected by a devious trap.");
	
	public Description getDescription() {
	  if (!locked) return DESC_OPENCHEST;
	  if ((trap>0)&&trapdetected) return DESC_TRAPPEDCHEST;
    return DESC_CHEST;	  
	}
	
  // create a chest 
  // value determines usefulness of contents....
  // ....and also potential danger of traps involved
	public Chest(int l) {
		super(120+RPG.r(2),true,null);
		level=l;
    contents=Lib.createItem(level+RPG.d(6));
    if (RPG.d(10)<=level) trap=RPG.d(3);
    trapdetected=false;
    locked=true;
	}
	
	public int getImage() {
		return super.getImage()+(locked?0:2);
	}
	
  public void die() {
    Map map=getMap();
    if (map!=null) getMap().addThing(contents,x,y);
    super.die(); 
  }
  
  public int damage(int dam, int damtype) {
    if (RPG.d(2,10)<dam) {
      Game.message("The chest is destroyed");
      triggerTrap();
      die();
    } else {
      triggerTrap();
    }
    return dam;
	}
	
	public void use(Being user) {
    // chest are only for heroes....
    Hero h=Game.hero;
    if (user!=h) return;
    
    if (trap==0) {
			if (!locked) {
				if (contents!=null){
					Game.message("You find "+contents.getAName());
					h.getMap().addThing(contents,h.x,h.y);
					contents=null;
				} else {
				  Game.message("You find nothing in the chest");	
				}
			} else {
			  Game.message("You unlock the chest....");
			  locked=false;	
			}
		} else {
		  if (!trapdetected) {
		    // have blundered into this one....
		    Game.message("The chest is trapped!");
		    if (RPG.test(h.getStat(RPG.ST_CR),2)&&RPG.test(h.getStat(RPG.ST_IN),10)) {
		    	Game.message("But you manage to disarm it");
		      trap=0;	
		    } else {
		      triggerTrap();	
		    }
		  } else {
		  	// try to disarm
		    if (RPG.test(h.getStat(RPG.ST_CR),2)) {
		    	Game.message("You manage to disarm the trap");
		      trap=0;	
		    } else {
		      Game.message("You trigger the trap by accident!");
		      triggerTrap();	
		    }
		  }
		}
	}
	
  public void triggerTrap () {
    Hero h=Game.hero;
    Map map=getMap();
		int dist=(h.x-x)*(h.x-x)+(h.y-y)*(h.y-y);
		switch (trap) {
		  case 0:
		  	return;	
			case 1:
        trap=0;
        if (dist<=2) {
        	Game.message("You are hit by a violent explosion!");				
        }
        Game.mappanel.doExplosion(x,y,0,4,RPG.DT_NORMAL);
        trap=0;
        break;
			case 2:
        trap=0;
        if (dist<=6) {
          Game.message("You are surrounded by poisonous fumes!");				
        }
        Game.mappanel.doExplosion(x,y,2,2,RPG.DT_POISON);
        break;
      case 3:
        trap=0;
        Being c=Lib.createFoe(level+1);
        if (isVisible()) Game.message("Ambush!");
        die();
        map.addThing(c,x,y);
        break;
 	  }
	}
	
	public void discover() {
	  if ((trap==0)||trapdetected) return;
	  
	  if (RPG.d(3)==1) {
	    Game.message("You have discovered a trap!");
	    trapdetected=true;
	  }	
	}	
}