// A Portal is a special object which represents a way to travel either
//   - from one map to another
//   - between distant locations on the same map
//
// use Portal.travel(thing) to send an object, create or Hero to the
// target destination.
//
// Portals will often operate in pairs, e.g. stairs on two different levels
// which link to each other.
//
// If of Portal is not twinned with another Portal, then a secret marker
// is added to the destination location.
//
// Portals also implement functionality for creatures to follow the hero
// through the portal if they are in pursuit.

package rl;

public class Portal extends Scenery implements Active, ThingOwner {
  // the target map, which needs to be private
  protected Portal target;
	
  // average number of creatures to spawn 
  // per 1000 turns (i.e. 100000 ticks)
  public int spawnrate;
  
  // level of spawned creatures
  public int spawnlevel;
  
  // source of creature IDs to spawn
  public int[] spawnsource;
  
  // linked list of critters about to emerge
  // e.g. if they have followed the player
  public Thing critters;
    
	
  private int type;
  
  public static final String[] names =   {"void",  "stairs up",  "stairs down",  "ladder up",  "ladder down",  "small cave",  "signpost", "pit"};
  public static final int[] images =     {0,       0,            1,              2,            3,              303,           66,         41};
  public static final int[] visible =    {0,       1,            1,              1,            1,              1,             1,          1};
  public static final int[] cantravel =  {1,       1,            1,              1,            1,              0,             1,          1};
  
  public int getImage() {
    return images[type]; 
  }
  
  // image=0
  public static final Description DESC_UP=
      new Describer("stairs up","Stairs leading upwards");
  // image=1
  public static final Description DESC_DOWN=
      new Describer("stairs down","Stairs leading downwards");
  // image=2
  public static final Description DESC_LADDERUP=
      new Describer("ladder up","Ladder leading upwards");
  // image=3
  public static final Description DESC_LADDERDOWN=
      new Describer("ladder down","Ladder leading downwards");
  
  // image=303
  public static final Description DESC_SMALLCAVE=
      new Describer("small cave","A small cave. You cannot fit down it.");
  
  public Portal(String s) {
    int i=Text.index(s,names);
    type= i>=0 ? i : 0;
  }
  
  // create a dummy portal object
  public Portal(int t) {
    type=t; 
  }

  // doesn't block movement
  public boolean isBlocking() {return false;}
  
  
  //return the current target object
  public Thing getTarget() {
    return target;	
  }
  
  public Map getTargetMap() {
  	 return getTarget().getMap();
  }
  
  // arrive at the portal
  
  public void arrive(Thing t) {
    if (t==Game.hero) {
      t.moveTo(getMap(),x,y);
      return; 
    }
     
    if (t instanceof Mobile) {
      Mobile b=(Mobile)t;

      if (getMap() instanceof WorldMap) {
        if (b.getAI().getLeader()==Game.hero) {
      
          // add to inventory trick if map is a WorldMap
          Game.hero.addThing(b); 
          return;
        } 
        
        // don't do anything - creatures don't follow onto WorldMaps yet
        return;
      }
    } 
     
    addThing(t);
  }
  
  
  public boolean canTravel(Thing t) {return cantravel[type]>0;}
  
  // go through the portal
  public void travel(Thing t) {
    if (!canTravel(t)) return;
    
    // is there a destination portal?
    Portal destportal=getDestination(); 

    // special handling if it is the hero
    if (t==Game.hero) { 
      // retrieve current map
      Map curmap=t.getMap();
      int cx=t.x;
      int cy=t.y;
      
      if (curmap!=null) {
        // see if there are any critters going to try to follow
        Thing[] things=curmap.getThings(cx-10,cy-10,cx+10,cy+10);
        // System.out.println(things.length);
        for (int i=0; i<things.length; i++) if (things[i] instanceof Being) {
          Being a=(Being)things[i];
          if (a.isVisible()&&((a.getAI() instanceof FollowerAI)||((Being)a).isHostile(Game.hero))) {
            travel(a);           
          }
        }
      }
      
      // reverse direction of critters in transit
      while (critters!=null) {
        travel(getCritter()); 
      }
      
      // add followers to destination portal
      Thing[] followers= Game.hero.inv.getContents(Being.class);
      for (int i=0; i<followers.length; i++) {
        travel(followers[i]); 
      }
    }
      
    // move traveller to target map

    // add followers to map on arrival
    //if ((t==Game.hero)&&(!(map instanceof WorldMap))) {
    //  Thing[] followers= Game.hero.inv.getContents(Being.class);
    //  for (int i=0; i<followers.length; i++) {
    //    Thing f=followers[i];
    //    f.moveTo(map,tx,ty);
    //    f.displace(); 
    //  }
    //}
    
    destportal.arrive(t); 
  }
  
  public void addThing(Thing c) {
    if (c==null) return;
    c.remove();
    c.place=this;
    c.next=critters;
    critters=c; 
  }
  
  // add a critter to portal monster list
  // give to hero if a follower and no map
  public void addCritter(Thing c) {
    if (place!=null) {
      addThing(c); 
    } else {
      if (c instanceof Being) {
        Being b=(Being)c;
        if (b.getAI().getLeader()!=null) {
          // add to leader's inventory 
          b.getAI().getLeader().addThing(c);
        } 
      }
    }
  }
  
  public Thing getCritter() {
    Thing c=critters;
    c.remove();
    return c; 
  }
  
  // link portal to entrance of given map
  public void makeLink(Map m) {
    if (!(place instanceof Map)) throw new Error("Bad portal link attempted");
    if (m.entrance==null) {
      throw new Error("Portal make link failed.");
      // m.entrance=new Portal(0,false,null); 
    }
    target=m.entrance;
    m.entrance.target=this; 
  }
  
  // Portal may introduce new creatures to dungeon
  // or do other stuff based on time elapsed
  public void action(int time) {
    Map m=getMap();
    
    // creature spawning
    if ( (critters==null) && (RPG.po(time*spawnrate,100000)>0) ) {
      addCritter(Creature.createCreature(spawnsource,spawnlevel));
    }
     
    // bring creatures out of list
    if (critters!=null) {
      int cx=x+RPG.r(3)-1; int cy=y+RPG.r(3)-1;
      if (!m.isBlocked(cx,cy)) {
        m.addThing(critters,cx,cy);
      } 
    }
  }
 
  public void setDestination(Portal t) {
    target=t; 
  }

  public Portal getDestination() {
    return target; 
  }

  public void removeThing(Thing t) {
    if (critters==t) {
      // first thing in list
      critters=t.next;
      t.next=null;
      t.place=null; 
    } else {
      // scan list
      Thing h=critters;
      while (h!=null) {
        if (h.next==t) {
          h.next=t.next;
          t.next=null;
          t.place=null;
          return; 
        }
        h=h.next;
      }
    }
  }

  public void setDestination(Map m, int x, int y) {
    target=new Portal(0);
    m.addThing(target,x,y);
  }
  
  public boolean isInvisible() {return visible[type]==0;}
  
  public String getName() {return names[type];}
  
  public Description getDescription() {return this;}
}