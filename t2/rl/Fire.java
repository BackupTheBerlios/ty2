package rl;

public class Fire extends Special implements Active {
  protected int strength;
  protected int decay;
	
	public static final Description DESC_FIRE =
		new Describer("fire","A blazing fire");
  public static final Description DESC_FIRE1 =
    new Describer("small fire","A small fire");
  public static final Description DESC_FIRE2 =
    new Describer("fire","A hot fire");
  public static final Description DESC_FIRE3 =
    new Describer("blazing fire","A blazing fire");
  public static final Description DESC_FIRE4 =
    new Describer("roaring fire","A large roaring fire");
  public static final Description DESC_FIRE5 =
    new Describer("inferno","An inferno of hellish flames");
	
  public Fire(int firestrength) {
	  strength=firestrength;
    decay=2;
	}
  
  public Fire(int firestrength, int dec) {
    strength=firestrength;
    decay=dec;
  }

  // create fire at specified map location
  // add to existing fire if present
  public static void createFire(Map m, int tx, int ty, int s) {
    if (m==null) return;
    Fire f=(Fire)m.getObject(tx,ty,Fire.class);
    if (f==null) {
      m.addThing(new Fire(s),tx,ty);
    } else {
      f.strength+=s; 
    }
  }
  
  // feared unless creature resists fire
  public boolean isWarning(Thing t) {
    int arm=t.getStat(RPG.ST_ARMFIRE);
    int d=(strength*strength)/(arm+strength);
    return d >= RPG.power(t.getStat(RPG.ST_RESISTFIRE)); 
  }
  
  public void fireDamage(Thing t) {
	  int dam=t.damageAll(RPG.r(strength+1),RPG.DT_FIRE);
	  if (t==Game.hero) {
	    if (dam>0) {
	    	Game.message("The fire burns you!");
	    } else {
	    	Game.message("You are unharmed by the flames");
	    }
	  }	
	}
	
	public int damage(int dam, int damtype) {
	  if (damtype==RPG.DT_ICE) {
	    strength=strength-dam;
	    return dam;
	  }	
	  return 0;
	}
	
	public Description getDescription() {
    if (strength<=2) return DESC_FIRE1;
    if (strength<=5) return DESC_FIRE2;
    if (strength<=15) return DESC_FIRE3;
    if (strength<=40) return DESC_FIRE4;
    return DESC_FIRE5;
  }
	
  public int getZ() {return Z_OVERHEAD;}
	
  public void action(int time) {
		if (place instanceof Map) {
      Map m = (Map) place;
      Thing[] t=m.getThings(x,y);
      for (int i=0; i<t.length; i++) {
        fireDamage(t[i]);	
		  }
    } 
    int dec=RPG.po(time*decay/1000.0);
    for (int i=0; i<dec; i++) strength=(3*strength)/4;
    if (strength<=0) remove();	
	}
	
	public int getImage() {return 180+RPG.r(4);}
}