// This is the base class for ALL objects which can exist on a Map in
// the Tyrant universe.
//
// All functions which are common to all types of objects should be
// defined here

package rl;

import java.io.Serializable;

public class Thing extends Object implements Cloneable, Serializable, Description {
	//these should not be changed - must keep consistent 
	public Thing next;
	public ThingOwner place;
  //meaning of these defined by place, usually a Map
	public int x;
	public int y;
	
  //Z ordering constanrs
  public static final int Z_ELSEWHERE=-10;
  public static final int Z_FLOOR=0;
  public static final int Z_ONFLOOR=5;
  public static final int Z_ITEM=20;
  public static final int Z_MOBILE=40;
  public static final int Z_OVERHEAD=60;
  public static final int Z_SYSTEM=80;
  
  // use constants
  public static final int USE_NONE=0;
  public static final int USE_NORMAL=1;
  public static final int USE_EAT=2;
  public static final int USE_DRINK=4;
  public static final int USE_READ=8;
  public static final int USE_WIELD=16;
  public static final int USE_MONEY=32;
  public static final int USE_HIT=256;
  public static final int USE_FIRE=512;
  public static final int USE_ATTACK=1024;

	//generic thing properties
	public boolean isMobile() {return false;}
	public boolean isBlocking() {return false;}
	
  // object state functions
  public boolean isIdentified() {return true;}

  public Description getDescription() { return RPG.DESC_GENERIC; }

  public int getUse() {return USE_NONE;}

  public int getImage() {return 0;}
	
  public int getWeight() {return 0;}

  public int getStat(int s) {return 0;}

  public int getModifier(int s) {return 0;}

  public int getZ() {return Z_ITEM;}
	
  // add attribute placeholder
  public void addAttribute(Attribute a) {
  }

  // called when player tries default action on object
  public void use(Being user) {}

  // called when player tries specific action on object
  public void use(Being user, int usetype) {}
	
  public ThingOwner getPlace() {return place;}
	
  // Description interface
  public String getName(int number, int article) {
    if (number==1) {
      return Describer.getArticleName(getSingularName(),number,article);
    } else {
      return Describer.getArticleName(getPluralName(),number,article); 
    }
  }
  
  public String getPronoun(int number, int acase) {
    return Describer.getPronoun(number,acase,getNameType(),getGender());
  }

  // return nametype and gender for a generic thing
  // override as necessary.
  public int getNameType() {return Description.NAMETYPE_NORMAL;}
  public int getGender() {return Description.GENDER_NEUTER;}
  
  public String getDescriptionText() {
    return Text.capitalise(getName()+"."); 
  }
  
  public String getName() { return getDescription().getName(getNumber(),Description.ARTICLE_NONE);}
  public String getAName() { return getDescription().getName(getNumber(),Description.ARTICLE_INDEFINITE);}
  public String getTheName() { return getDescription().getName(getNumber(),Description.ARTICLE_DEFINITE);}
  public String getFullName() {return getAdjectives()+getName();}
  
  // return describing adjectives if applicable
  public String getAdjectives() {
    return ""; 
  }
  
	public String getSingularName() { return "thing";}
  public String getPluralName() { return getSingularName()+"s";}

	public boolean isOwned() {
    return false;
  }
  
  public void removeThing(Thing thing) {
	  throw new Error("Can't remove from Thing!");	
	}

  public int getQuality() {return 0;}
  
  public void addThing(Thing thing) {
    throw new Error("Can't add to Thing!");
  }	
  
  public final Map getMap() {
    return (place==null)?null:place.getMap();	
  }

  public final int getMapX() {
    if (place instanceof Map) return x;
    return (place==null)?-1:((Thing)place).getMapX();  
  }

  // can thing "see" specified map location?
  public boolean canSee(Map m, int tx, int ty) {
    if ((m==null)||(m!=place)) return false;
    return m.isLOS(x,y,tx,ty);
  }
  
  // can thing see other thing
  public boolean canSee(Thing t) {
    if (t.place!=place) return false;
    return canSee(getMap(),t.x,t.y); 
  }
  
  public final int getMapY() {
    if (place instanceof Map) return y;
    return (place==null)?-1:((Thing)place).getMapY();  
  }

  public void remove() {
    if (place!=null) place.removeThing(this);	
  }

  public int getNumber() {
    return 1; 
  }
  
  public Thing remove(int n) {
    if (n<1) return null;
    remove();
    return this; 
  }

  public void die() {
    Game.registerDeath(this);
    remove(); 
  }

  // move to square, landing on floor
	public void moveTo(Map m, int tx, int ty) {
		m.addThing(this,tx,ty);
		m.hitSquare(this,tx,ty,true);
	}

  // move randomly to on side
  public void displace() {
    if (place instanceof Map) {
      Map m= (Map) place;
      int nx=x+RPG.r(3)-1;
      int ny=y+RPG.r(3)-1;
      if ((m.getTile(nx,ny)&Tile.TF_BLOCKED)!=0) return;
      if (isBlocking()&&m.isBlocked(nx,ny)) return;
      moveTo(m,nx,ny);
    } 
  }

  public void throwAt(Being thrower, Map m, int tx, int ty) {
    moveTo(m,tx,ty); 
  }

  public boolean isWithin(ThingOwner thing) {
    for (Thing head=this; head!=null; head=(Thing)head.place) {
    	if (head.place==thing) return true;
    	if (!(head.place instanceof Thing)) return false;
    }
    return false;
  }
  
  public boolean isTransparent() { return true;}
  
  // make (wielder) use (this) to (action) the (target) 
  public int use(Thing wielder, Thing target, int action) {
    return 0;	
  }
  
  // returns true f the Thing is damaged
  // used for Items mainly
  public boolean isDamaged() {
    return false; 
  }
  
  // Message to be given if Thing is visible to the player 
  public void visibleMessage(String s) {
    if ((isVisible())&&(!isInvisible())) {
      Game.message(s);
    }  
  }
  
  // returns true if item can currently be seen
  // doesn't account for invisible items, 
  //   i.e. assumes hero can see through all invisibilities
  public boolean isVisible() {
    if (place instanceof Map) {
      // can see it if it is in a visible square
      return ((Map)place).isVisible(x,y);
    } else {
      // can see it if we are carrying it!
      return place==Game.hero; 
    } 
  }
  
  // returns true if the item should be displayed
  public boolean isInvisible() {return false;}
    
  // clone method. Creates displaced copy of this
  public Object clone() {
    try {
      Thing t=(Thing)super.clone();
      t.place=null;
      t.next=null; 
      return t;
    } catch (CloneNotSupportedException e) { return null; }
  }

  // inflict damage on thing
  public int damage(int dam, int damtype) {return 0;}

  // inflict damage on thing and all sub-items/inventory
  public int damageAll(int dam, int damtype) {return damage(dam,damtype);}

  // inflict damage on thing in randomized location
  // e.g. could damage particular armour item
  public int damageLocation(int dam, int damtype) {return damage(dam,damtype);}

  // is this a warning/dangerous object for thing t?
  public boolean isWarning(Thing t) {return false;}

  public int getLevel() {
    return 1; 
  }
  
/*
  //move thing within map by call Map moveThing method
  public void moveTo(int newx, int newy) {
  	if (place!=null) {
  	  place.moveThing(this,x,y);	
  	}	
  }   */
  
}