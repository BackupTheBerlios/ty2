package rl;

public class TypeStack extends Stack {
  public ItemType type;
  
  public TypeStack(ItemType t, int n) {
    super(n);
    type=t; 
  }
  
  public int getWeight() {
    return type.getWeight(this)*number;	
  }

  public int getStat(int s) {return type.getStat(this,s);}
  
  public void throwAt(Being thrower, Map m, int tx, int ty) {
    type.throwAt(this,thrower,m,tx,ty);
  }

  // descriptions etc.
  public String getName() {
    if (number==1) return super.getName();
    else return number + " "+ getDescription().getName(number,Description.ARTICLE_NONE);
  }
  
  public String getAName() { 
    if (number==1) return super.getAName();
    else return getName();
  }
  
  public String getTheName() { 
    if (number==1) return super.getTheName();
    else return "the "+getName();
  }

  public Description getDescription() {
    return type.getDescription(this); 
  }

  // check if stacking possible
	public boolean canStackWith(Thing t) {
	  if (!(t instanceof TypeStack)) return false;
    TypeStack l=(TypeStack)t;
    return (l.type==type)&&super.canStackWith(t);
	}
	
  // INTERACTION HANDLING
  
  // try to identify item
  public boolean identify(int idskill) {
    return type.identify(this,idskill);
  }

  public boolean isIdentified() {
    return type.isIdentified(this); 
  }
  
  public int damage(int dam, int damtype) {
    int f=getStat(RPG.ST_ITEMFRAGILITY);
    if (RPG.d(1000)<=(dam*f)) {
      remove();
    }
    return dam; 
  }
  
  public int getUse() {return type.getUse(this);}

  public void use(Being user) {type.use(this,user);}

  // called when player tries specific action on object
  public void use(Being user, int usetype) {type.use(this,user,usetype);}

  public int getZ() {return Z_ITEM;}

  public int getImage() {return type.getImage(this);}
  
  public int wieldType() {return type.wieldType(this);}
  
}