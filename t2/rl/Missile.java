//
// A missile weapon, which can either be throw or in some cases
// used with a bow, crossbow or blowpipe
//

package rl;

public class Missile extends Stack {
  
  public static final Description DESC_=null;
  public static final Description DESC_BOOMERANG=
      new Describer("curved stick","A peculiar curved stick.");
  public static final Description DESC_SHURIKEN=
      new Describer("shuriken","A sharp-pointed throwing star.");
  public static final Description DESC_STONE=
      new Describer("stone","A rounded stone.");
  public static final Description DESC_THROWINGKNIFE=
      new Describer("throwing knife","throwing knives","An elegant and well-balanced throwing knife.");
  public static final Description DESC_MAGICTHROWINGKNIFE=
      new Describer("magic throwing knife","magic throwing knives","A throwing knife etched with mystic runes and glowing with a strange magical light.");
  public static final Description DESC_FIREFLASK=
      new Describer("fire flask","A glass flask containing a ball of magical flame.");
  public static final Description DESC_ARROW=
      new Describer("arrow","A sharp-pointed wooden arrow.");
  public static final Description DESC_BOLT=
      new Describer("crossbow bolt","A heavy crossbow bolt.");
  public static final Description DESC_MAGICARROW=
      new Describer("magic arrow","A magically charged arrow.");
  public static final Description DESC_FLAMEARROW=
      new Describer("flame arrow","A magic arrow of fire.");
  public static final Description DESC_POISONARROW=
      new Describer("poison arrow","A poisoned arrow.");
  public static final Description DESC_ROCK=
      new Describer("rock","A heavy rock.");
  public static final Description DESC_DART=
      new Describer("dart","A small throwing dart.");
  public static final Description DESC_HEAVYDART=
      new Describer("heavy dart","A heavy throwing dart.");
  public static final Description DESC_POISONDART=
      new Describer("poison dart","A poisoned throwing dart.");
  public static final Description DESC_TANGLER=
      new Describer("tangler","A tangler.");

  protected static final int[]         types          = {0,    1,                     2,                     3,                     4,                     5,                        6,                     7,                     8,                     9,                     10,                    11,                    12,                    13,                     14,                    15,                    16,                    17,                    18,                    19,                    20,                    21,                    22,                    23,                    24,                    25,                    26,                    27,                    28,                    29,                    30,                    21                   };   
  protected static final Description[] uidescriptions = {null, DESC_BOOMERANG       , DESC_SHURIKEN        , DESC_STONE           , DESC_THROWINGKNIFE   , DESC_MAGICTHROWINGKNIFE , DESC_FIREFLASK       , DESC_ARROW           , DESC_MAGICARROW      , DESC_FLAMEARROW      , DESC_BOLT            , DESC_ARROW           , DESC_TANGLER         , DESC_ROCK             , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                };
  protected static final Description[] iddescriptions = {null, DESC_BOOMERANG       , DESC_SHURIKEN        , DESC_STONE           , DESC_THROWINGKNIFE   , DESC_MAGICTHROWINGKNIFE , DESC_FIREFLASK       , DESC_ARROW           , DESC_MAGICARROW      , DESC_FLAMEARROW      , DESC_BOLT            , DESC_POISONARROW     , DESC_TANGLER         , DESC_ROCK             , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                , DESC_                };
  protected static final int[]         images         = {0,    100,                   101,                   102,                   106,                   106,                      249,                   80,                    81,                    84,                    85,                    82,                    103,                   105,                    360,                   323,                   347,                   351,                   346,                   381,                   380,                   382,                   383,                   384,                   385,                   386,                   387,                   388,                   389,                   390,                   0,                     0                    };   
  protected static final int[]         ranges         = {0,    6,                     3,                     3,                     3,                     8,                        6,                     5,                     8,                     5,                     4,                     5,                     4,                     2,                      00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         rskmuls        = {0,    100,                   70,                    60,                    80,                    100,                      70,                    100,                   100,                   120,                   100,                   100,                   80,                    40,                     00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         rskbonuses     = {0,    -6,                    -2,                    0,                     -2,                    0,                        0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         rstmuls        = {0,    15,                    20,                    15,                    15,                    20,                       10,                    100,                   100,                   120,                   100,                   100,                   10,                    20,                     00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         rstbonuses     = {0,    2,                     4,                     1,                     2,                     3,                        0,                     0,                     0,                     3,                     0,                     0,                     0,                     0,                      00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         throwcosts     = {0,    250,                   200,                   200,                   200,                   150,                      250,                   200,                   200,                   200,                   200,                   200,                   150,                   250,                    00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         weights        = {0,    300,                   250,                   400,                   300,                   240,                      350,                   50,                    50,                    50,                    70,                    50,                    500,                   800,                    00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };
  protected static final int[]         survivals      = {0,    80,                    95,                    98,                    80,                    99,                       0,                     60,                    90,                    30,                    70,                    40,                    90,                    97,                     00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    }; 
  protected static final int[]         missiletypes   = {0,    RPG.MISSILE_THROWN,    RPG.MISSILE_THROWN,    RPG.MISSILE_STONE,     RPG.MISSILE_THROWN,    RPG.MISSILE_THROWN,       RPG.MISSILE_THROWN,    RPG.MISSILE_ARROW,     RPG.MISSILE_ARROW,     RPG.MISSILE_ARROW,     RPG.MISSILE_BOLT,      RPG.MISSILE_ARROW,     RPG.MISSILE_THROWN,    RPG.MISSILE_THROWN,     00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]         values         = {0,    300,                   800,                   2,                     400,                   3000,                     1000,                  20,                    400,                   200,                   40,                    300,                   1000,                  7500,                   1800,                  250,                   500,                   800,                   2000,                  500,                   800,                   700,                   1000,                  5000,                  1500,                  1000,                  2000,                  800,                   1800,                  6000,                  0,                     0                    };   

  public static final int BOOMERANG=          1;
  public static final int SHURIKEN=           2;
  public static final int STONE=              3;
  public static final int THROWINGKNIFE=      4;
  public static final int MAGICTHROWINGKNIFE= 5;
  public static final int FIREFLASK=          6;
  public static final int ARROW=              7;
  public static final int MAGICARROW=         8;
  public static final int FLAMEARROW=         9;
  public static final int BOLT=              10;
  public static final int POISONARROW=       11;
  public static final int TANGLER=           12;
  public static final int ROCK=              13;
  
  public int getWeight() {return weights[type];}

  public static Stack createThrowingWeapon(int t) {
    while (true) {
      Stack s=createMissile(0); 
      if (missiletypes[s.type]==RPG.MISSILE_THROWN) return s;
      t=0;
    }
  }
  
  public static Stack createMissileType(int t) {
    while (true) {
      Stack s=createMissile(0); 
      if (missiletypes[s.type]==t) return s;
    }
  }
  
  
  public static Stack createMissile(int t) {
    if (t==0) t=RPG.d(13);
    
    // return correct multiples
    switch (t) {
      case BOOMERANG:
        return new Missile(BOOMERANG,RPG.d(2));
      case SHURIKEN: 
        return new Missile(SHURIKEN,RPG.d(4)); 
      case THROWINGKNIFE: 
        return new Missile(THROWINGKNIFE,RPG.d(3)); 
      case MAGICTHROWINGKNIFE: 
        return new Missile(MAGICTHROWINGKNIFE,1); 
      case FIREFLASK: 
        return new Missile(FIREFLASK,RPG.d(4)); 
      case TANGLER: 
        return new Missile(TANGLER,RPG.d(2)); 
      default:
        return new Missile(t,RPG.d(2,6));
    }
  }

  public int getStat(int s) {
    switch(s) {
      case RPG.ST_RANGE:
        return ranges[type];
      case RPG.ST_RSKMULTIPLIER:
        return rskmuls[type];
      case RPG.ST_RSTMULTIPLIER:
        return rstmuls[type];
      case RPG.ST_RSKBONUS:
        return rskbonuses[type];
      case RPG.ST_RSTBONUS:
        return rstbonuses[type];
      case RPG.ST_MISSILETYPE:
        return missiletypes[type];
      case RPG.ST_ITEMVALUE:
        return values[type];
      default: return super.getStat(s);  
    }
  }
  
  public int shotDifficulty(Thing shooter, Thing target, int dist) {
    int difficulty=5*dist;
    if (target!=null) difficulty+=target.getStat(RPG.ST_AG);
    return difficulty;
  }
  
  public void throwAt(Being thrower, Map m, int tx, int ty) {
    Thing t = m.getMobile(tx,ty);
    
    thrower.aps-=(throwcosts[type]*10)/(10+thrower.getStat(RPG.ST_THROWING));
    
    if (type==FIREFLASK) {
      remove(1);
      new Spell(Spell.BLAZE).castAtLocation(thrower,m,tx,ty);
      return;
    }
    
    // weapon skill calculation
    int wsk=thrower.getStat(RPG.ST_SK);
    wsk=(wsk*(4+thrower.getStat(RPG.ST_THROWING)))/4;
    
    // range
    int range=ranges[type];
    
    // improvisation modifiers
    if (missiletypes[type]!=RPG.MISSILE_THROWN) {
      // we have improvised, so reduce hit chance
      wsk/=2; 
      range/=2;
    }

    if (t!=null) {
      // calculate distance and offset
      int dx=tx-thrower.x;
      int dy=ty-thrower.y;
      int dist=(int)Math.sqrt(dx*dx+dy*dy);

      Game.mappanel.doShot(thrower.x,thrower.y,tx,ty,100,0.5);

      int rsk=(rskmuls[type]*wsk)/100 + rskbonuses[type];
      if (RPG.test(rsk,shotDifficulty(thrower,t,dist))) {
        //have scored a hit!
        hit(thrower,t,RPG.MISSILE_THROWN);
        
        // drop item if missile survives
        if (RPG.d(100)<=survivals[type]) moveTo(m,tx,ty);
        
        return;
      } else {
        Game.message(getTheName()+" misses"); 
      }
      moveTo(m,tx,ty);
      return;
    }

    // make boomerang come back!
    if ((thrower==Game.hero)&&(type==BOOMERANG)&&RPG.test(wsk,10)) {
      Game.message(getTheName()+" returns!");
      if (RPG.test(wsk,10)) {
        Game.message("You catch "+getTheName());
        thrower.addThing(this);
        return; 
      } else {
        Game.message(getTheName()+" lands near your feet");
        tx=thrower.x; 
        ty=thrower.y;
        moveTo(m,tx,ty);
        displace();
        return;
      }
    }
    
    // item lands
    moveTo(m,tx,ty);
  }

  // thrown item hits
  public void hit(Being thrower, Thing target, int firetype) {
    // weapon skill calculation
    int rst=(rstmuls[type]*(thrower.getStat(RPG.ST_ST)))/100 + rstbonuses[type];
    
    if (firetype==RPG.MISSILE_THROWN) {
      rst=(rst*(6+thrower.getStat(RPG.ST_THROWING)))/6;
    }
    
    if (missiletypes[type]!=firetype) {
      // we have improvised, so attack does minimal damage
      rst/=5; 
    }
    
    if (target.isVisible()) Game.message(getTheName()+" hits "+target.getTheName());
    hit(target,rst);
  }
  
  // calculate hit effect
  public void hit(Thing target, int rst) {

    switch (type) {
      case FLAMEARROW:
        target.damage(rst,RPG.DT_FIRE); break;
      
      case POISONARROW: {
        int d=target.damage(rst,RPG.DT_NORMAL); 
        if (d>0) {
          target.addThing(new PoisonAttribute(2,RPG.DT_POISON,500*RPG.d(2,9),3));
        }
        break;
      }
      
      case TANGLER:
        target.damage(rst,RPG.DT_NORMAL);
        if (RPG.po(20,target.getStat(RPG.ST_ST))>1) {
          target.addAttribute(new TemporaryAttribute("Tangled",5000/target.getStat(RPG.ST_ST),new int[] {RPG.ST_IMMOBILIZED,1},"You untangle yourself"));
        }
        break;
      
      default:
        target.damage(rst,RPG.DT_NORMAL); break;
    }
  }

  public int wieldType() {
    return RPG.WT_MISSILE; 
  }
    
	public int getUse() {
    return USE_FIRE; 
  }
  
  public int getImage() {return images[type];}
  
  public Missile (int t, int q) {
    super(q);
    type=t;
  }

  public Description getDescription() {return isIdentified()?iddescriptions[type]:uidescriptions[type];}
}