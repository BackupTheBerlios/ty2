package rl;

public class GameScenery extends Scenery {
  protected int type;
  protected int hps;

  protected static final int BUSH=1;
  protected static final int PLANT=2;
  protected static final int TREE=3;
  protected static final int THORNYBUSH=4;
  protected static final int TREESTUMP=5;
  protected static final int MUSHROOM=6;
  protected static final int APPLETREE=7;
  protected static final int ROUNDTABLE=8;
  protected static final int HTABLE=9;
  protected static final int VTABLE=10;
  protected static final int EVILBUSH=11;
  
  public static final String[] names=    {"",  "bush",  "plant",   "tree",   "thorny bush",  "tree stump",  "mushroom", "apple tree", "table",  "table",  "table",  "evil thorny bush", "stool",   "tent",  "armoury sign", "bank sign",  "store sign",  "magic shop sign",  "food shop sign", "smithy sign"};
  public static final int[] images=      {0,   80,      81,        82,       83,             84,            85,         80,           200,      201,      202,      83,                 203,       304,     63,             67,           67,            62,                 60,               61};
  public static final int[] hits=        {0,   6,       4,         20,       8,              15,            2,          25,           6,        10,       10,       60,                 5,         5,       10,             30,           10,            50,                 10,               10};
  public static final int[] blocking=    {0,   1,       1,         1,        1,              0,             0,          1,            1,        1,        1,        1,                  0,         1,       0,              0,            0,             0,                  0,                0};
  public static final int[] pushability= {0,   0,       0,         0,        0,              0,             0,          0,            10,       20,       20,       0,                  2,         0,       0,              0,            0,             0,                  0,                0};

  public static final int[] transparent= {0,   0,       1,         0,        1,              1,             1,          0,            1,        1,        1,        0,                  1,         0,       1,              1,            1,             1,                  1,                1};
  
  public static final int[] zeros=       {0,   0,       0,         0,        0,              0,             0,          0,            0,        0,        0,        0,                  0,         0,       0,              0,            0,             0,                  0,                0};
  
  public static GameScenery create(String s) {
    return new GameScenery(Text.index(s,names));    
  }
  
  public static GameScenery create(int t) {
		return new GameScenery(t);
	}
	
	public int getImage() {
    return images[type]; 
  }
  
  public GameScenery(int t) {
    type=t;
    hps=hits[type];
	}
	
  public Description getDescription() {return this;}
  
  public String getSingularName() {
    return names[type]; 
  }  
    
  // Can the item be pushed around?
  //   0 = definitely not
  //   1 = always
  //   x = yes, with difficulty increasing with x (compare to character ST)
  public int getPushability() {
    return pushability[type]; 
  }
  
  
  public void use(Being user) {
    boolean ishero=(user==Game.hero);
    
    if (type==THORNYBUSH) {
      if (RPG.test(user.getStat(RPG.ST_ST),4)) {
        if (ishero) {
          Game.message("You fight your way through "+getTheName());  
          int d=user.damage(RPG.r(3),RPG.DT_NORMAL);
          if (d>0) Game.message("You are scratched by the sharp thorns");
        }
        user.moveTo(getMap(),x,y);
        return;
      } 
    }
    
    if (type==THORNYBUSH) {
      if (RPG.test(user.getStat(RPG.ST_ST),10)) {
        if (ishero) {
          Game.message("You fight your way through "+getTheName());  
          int d=user.damage(RPG.d(2,4),RPG.DT_POISON);
          if (d>0) Game.message("You are scratched by the poisonous thorns");
        }
        user.moveTo(getMap(),x,y);
        return;
      } 
    }

    if (ishero&&isBlocking()) {
	    Game.message("You are blocked by "+getTheName());	
	  }	
	}
	
	public boolean isTransparent() { return (transparent[type]>0);}
  
  public boolean isBlocking() {return blocking[type]>0;}
  
	
	public int damage(int dam, int damtype) {
    if (hps<0) return 0;
    hps-=dam;
    if (hps<=0) {
    	Game.message(Text.capitalise(getTheName()+" is destroyed"));
    	die();
    }

    if ((dam>0)&&(damtype==RPG.DT_FIRE)&&(place instanceof Map)) {
      Fire.createFire((Map)place,x,y,1);
    }
	  return dam;	
	}
}