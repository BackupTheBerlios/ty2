package rl;

import java.util.Random;

class EmeraldDungeon extends Dungeon {
  
  public EmeraldDungeon(int w, int h) {
	  super(w,h,3);
  
    // now add the Emerald Sword
    Point es=findFreeSquare();
    {
    	for (int dx=-1; dx<=1; dx++) for (int dy=-1; dy<=1; dy++) {
    	  if (!isBlocked(es.x+dx,es.y+dy)) addThing(new MapStateTrap(1),es.x+dx,es.y+dy);	
    	}
      Portal dp=new Portal("stairs down");
      addThing(dp,es.x,es.y);
      dp.makeLink(new GuardianVault(new Creature(Creature.SHAMAN),StandardWeapon.createSpecificWeapon(StandardWeapon.EMERALDSWORD)));
    }
  }

  
  private void addGuard(int x, int y) {
    if (isClear(x,y)) {
    	Mobile m=Lib.createCreature(3);
    	addThing(m,x,y);
    }	
  }

  protected void addWandering() {
    Point p=findFreeSquare();
    int x=p.x; int y=p.y;
    if (!isVisible(x,y)) {
      if (RPG.d(3)==1) {
        Mobile m=Lib.createCreature(4);
        if (m!=null) addThing(m,x,y);	
      }
    }	
  }

  private void addBaddie(int x, int y) {
    if (isClear(x,y)) {
    	Mobile m=Lib.createCreature(3);
    	m.addThing(Lib.createItem(0));
    	addThing(m,x,y);
    }	
  }
  
  public void action(int time) {
    super.action(time);
    if (mapstate>0) {
      int count=RPG.po(time,300);
      for (int i=0; i<count; i++) addWandering();  
    }
  }

  public String getEnterMessage() {
    return "There's something scary about this place.";  
  }
  
  public String getLevelName() {
    return "Old ruin";  
  }
}