package rl;

public class Mobile extends Thing implements Active {
  public int aps;
  public int xdirection;
  public int ydirection;
  
  protected AI ai;
  
  private static final Description DESC_MOBILE =
  	new Describer("mobile", "A malevolent mobile");
  
  public int getZ() {return Z_MOBILE;}
  
  public AI getAI() {return ai;}
  
  public void setAI(AI newai) {
    if (ai!=null) ai.unregister(this);
    ai=newai;
    ai.register(this);	
  }
  
  public void give(Thing giver,Thing gift) {
    if (giver==Game.hero) {
      Game.message(getTheName()+" does not seem interested"); 
    }
  }
  
  public Thing getWielded(int wt) {return null;}
  
  public int notify(int eventtype, int ext, Object o) {
    AI a=getAI();
    if (a!=null) {
    	return a.notify(this,eventtype,ext,o);
    }
    return 0;
  }
  
  public Description getDescription() { return DESC_MOBILE;}

  public boolean isMobile() {return true;}
  
  public boolean isHostile(Mobile b) {
    return false;
  }

  public boolean isDead() {return false;}
  
  public int attack(Thing target) {
  	return 0;	
  }
  
  // return total combat values
  public int getArmour(int damtype) {return 0;}
  
  public int getBaseStat(int s) {return 0;}

  public void setStat(int s, int v) {}
  	 
  public Thing getWeapon() {return null;}
  
  public void action(int time) {
    aps=aps+time;
    if ((ai!=null)&&(place!=null)) ai.doAction(this);
  }
  
  public boolean tryMove(Map m, int tx, int ty) {
    if (!canMove(m,tx,ty)) return false;
    moveTo(m,tx,ty); 
    return true;
  }
  
  public boolean canMove(Map m, int tx, int ty) {
    if ((this instanceof Creature)||(this instanceof Person)) {
      Thing head=m.getObjects(tx,ty);
      while (head!=null) {
        if (head.isWarning(this)) {
          return false;
        }
        head=head.next;
      }
    }
    return (!m.isBlocked(tx,ty));
  } 
  
  public void moveTo(Map map, int tx, int ty) {
    xdirection=RPG.sign(tx-x);
    ydirection=RPG.sign(ty-y);
    super.moveTo(map,tx,ty); 
  }
  
  public void anger(Mobile a) {
    if (!isHostile(a)) {
      getAI().notify(this,AI.EVENT_ATTACKED,getStat(RPG.ST_SIDE),a);
    }
  }
}