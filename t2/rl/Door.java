package rl;

public class Door extends Scenery {
  // use blocking flag to determine whether door is open
  protected boolean locked;
  protected int hits=10;
  protected int arm=10;
  protected Trap trap=null;
  
  public static final int DOOR = 1;
  public static final int SAFEDOOR = 2;
  public static final int TRAPPEDDOOR = 3;
  public static final int SUPERDOOR = 4;
  public static final int VAULTDOOR = 5;
  public static final int SHOPDOOR = 6;
  public static final int TOWNDOOR = 7;
  public static final int STABLEDOOR = 8;
  
  public static final int PORTCULLIS = 11;
  public static final int MIGHTYPORTCULLIS = 12;
  
  public static final String[] names    =   {"door",   "strong door",  "stone door",   "vault door",  "magic door",   "portcullis",    "stable door",   "heavy portcullis",   "fortress gate",   "ornate door",  "shop door", "town door", "goblin door",  "black door"};
  public static final int[]    basehits =   {15,       30,             60,             200,           500,            40,              10,              400,                  4000,              25,             50,          15,          60,             100};    
  public static final int[]    rarity=      {1,        4,              6,              10,            50,             10,              10,              20,                   30,                4,              0,           0,           20,             10};
  public static final int[]   baselevel=    {0,        3,              4,              6,             8,              5,               2,               7,                    10,                1,              1,           1,           7,              4};
  public static final int[]   lockedchance= {10,       40,             40,             80,            90,             30,              10,              50,                   40,                30,             0,           0,           40,             20};

  public static final int[]   zeros=        {0,        0,              0,              0,             0,              0,               0,               0,                    0,                 0,              0,           0,           0,              0};
  public static final int[]   transparency= {0,        0,              0,              0,             0,              1,               1,               1,                    1,                 0,              0,           0,           0,              0};
  
  public static final int[]   closedimage=  {144,      144,            146,            146,           148,            140,             150,             140,                  142,               148,            148,         144,         152,            154};
  public static final int[]   openimage=    {145,      145,            147,            147,           149,            141,             151,             141,                  143,               149,            149,         145,         153,            155};

  private int type;
  
  public String getSingularName() {
    return names[type]; 
  }
  
  public Description getDescription() {
	  return this;	
	}
	
	public static int randomType(int level) {
    for (int i=0; i<20; i++) {
      int t=RPG.r(names.length);
      if ((baselevel[t]<=level)&&(RPG.d(rarity[t])==1)) return t; 
    }
    return 0;
  } 
  
  public Door(int level) {
    this(names[randomType(level)],level); 
  }
  
  public Door(String n, int level) {
    type=Text.index(n,names);
    hits=basehits[type];
    blocking=true;
    locked=(RPG.r(100)<lockedchance[type]);
    //upgrade hits for high-level door
    for (int i=level; i>baselevel[type]; i--) {
      hits=(hits*(10+RPG.r(7)))/10; 
    }
    
    if (RPG.test(level,50)) makeTrapped(level);
    
  }
  
  private void makeTrapped(int level) {
    trap=Trap.createTrap(level);
  }
  
  // create a door of the right type
  public Door(String s) {
    this(s,Game.hero.getLevel()); 
  }
  
  // create a bog standard door
  public Door() {
    this("door",Game.hero.getLevel());
	}

  public static Door createDoor(int level) {
    return new Door(level);
  }
  
  public static Door createToughDoor(int level) {
    Door d=new Door(level+2);
    d.locked=true;
    d.hits*=2;
    
    // higher probability of being trapped
    if (RPG.test(level,10)) d.makeTrapped(level+2);
    
    return d;
  }

	public void setOpen(boolean open) {
	  blocking=!open;
	}

  public int damage(int dam, int damtype) {
    switch (damtype) {
      case RPG.DT_SPECIAL: case RPG.DT_NORMAL: case RPG.DT_IMPACT: case RPG.DT_UNARMED:
        break;       	
      case RPG.DT_FIRE:
        if (image!=144) dam=0;
        break;
      default:
        dam=0;
        break;		
    }	
    
    // set off trap if door is damaged
    if (dam>0) triggerTrap();
    
    hits-=dam;
    
    if ((hits<0)&&(basehits[type]>0)) {
      Map m=getMap(); if (m==null) return 0;
      if (isVisible()) {
        Game.message(getTheName()+" is destroyed"); 
      }
      
      remove();
    }
    return dam;
  }

  // triggers the trap, targetting the adjacent square if neccesary
  public void triggerTrap() {
    if (trap==null) return;
     
    Thing a=Game.actor;
    if ((a!=null)&&trap.actAdjacent()&&(a.x>=x-1)&&(a.x<=x+1)&&(a.y>=y-1)&&(a.y<=y+1)) {
      trap.trigger(getMap(),a.x,a.y);
    } else {
      trap.trigger(getMap(),x,y); 
    }
  }
  
  public boolean isLocked() {
    return locked; 
  }
  
  public void setLocked(boolean b) {
    locked=b;
  } 
  
  public void lockedOptions(Being user) {
    Game.message(getTheName()+" is locked");
    int difficulty=hits+baselevel[type]*(baselevel[type]+5);
    int skill=user.getStat(RPG.ST_CR)*user.getStat(RPG.ST_PICKLOCK);
    if (user.getStat(RPG.ST_PICKLOCK)>0) {
      Game.message("Attempt to pick lock? (y/n)");
      char c=Game.getOption("yn");
      if (c=='y') {
        if (RPG.test(skill,difficulty)) {
          Game.message("You succeed in picking the lock!");
          locked=false; 
        } else {
          Game.message("You fail to pick the lock");
          // lose a lockpick here
        }
      } else {
        Game.message(""); 
      } 
    }    
  }
  
  public void use(Being user) {
    boolean ishero=(user==Game.hero);
    
    if (blocking) {
      if (locked||((!ishero)&&(user.getStat(RPG.ST_CR)<7))) {
        if (ishero) {
          lockedOptions(user);
        }
      } else {
        setOpen(true);
        if (ishero) Game.message("You open "+getTheName());
        user.aps-=200;
      }
    } else {
      setOpen(false);
      if (ishero) Game.message("You close "+getTheName());
      user.aps-=200;
    }
  }

  public boolean isTransparent() {
    return (!blocking)||(transparency[type]>0);	
  }
  
  public int getImage() {
    return blocking ? closedimage[type] : openimage[type];	
  }
}