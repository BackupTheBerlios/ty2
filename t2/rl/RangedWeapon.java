package rl;

// Standard armour types
// *still need to implement defence skill, encumberance*
public class RangedWeapon extends Item {
  protected int type;

  // Standard ranged weapon data table
  protected static final String[] uinames        = {null, "bow",                 "bow",                 "bow",               "fine bow",          "crossbow",            "crossbow",            "sling",               "sling",               "bow",                 "long bow",            "heavy metal crossbow"};
  protected static final String[] names          = {null, "bow",                 "short bow",           "elven bow",         "Bow of Galadriel",  "crossbow",            "Saviour of Kradeth",  "sling",               "Sling of David",      "goblin bow",          "long bow",            "B'Zekroi crossbow"};
  protected static final int[]    levels         = {0,    3,                     2,                     6,                   20,                  5,                     10,                    1,                     20,                    8,                     9,                     15};
  protected static final int[]    rarity         = {0,    1,                     1,                     10,                  0,                   5,                     0,                     1,                     0,                     5,                     1,                     20};
  protected static final int[]    images         = {0,    121,                   121,                   121,                 121,                 124,                   124,                   120,                   120,                   121,                   122,                   131,                   360,                   361,                   200,                    360,                   323,                   347,                   351,                   346,                   381,                   380,                   382,                   383,                   384,                   385,                   386,                   387,                   388,                   389,                   390,                   0,                     0                    };   
  protected static final int[]    rskmuls        = {0,    80,                    70,                    90,                  200,                 80,                    100,                   70,                    80,                    80,                    80,                    120,                   1,                     2,                     0,                      1,                     1,                     1,                     2,                     2,                     1,                     1,                     2,                     1,                     3,                     4,                     3,                     3,                     0,                     6,                     5,                     0,                     0                    };   
  protected static final int[]    rskbonuses     = {0,    -3,                    -2,                    -2,                  0,                   -2,                    0,                     -4,                    -3,                    -4,                    -2,                    -10,                   1,                     1,                     2,                      1,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]    rstmuls        = {0,    0,                     0,                     0,                   0,                   0,                     0,                     20,                    40,                    10,                    10,                    0,                     0,                     0,                     0,                      0,                     0,                     0,                     0,                     0,                     50,                    60,                    40,                    70,                    60,                    40,                    40,                    50,                    70,                    30,                    80,                    0,                     0                    };   
  protected static final int[]    rstbonuses     = {0,    8,                     6,                     11,                  20,                  13,                    25,                    4,                     10,                    8,                     10,                    50,                    -4,                    -10,                   -20,                    -5,                    2,                     3,                     6,                     4,                     5,                     3,                     4,                     3,                     0,                     10,                    6,                     4,                     1,                     15,                    0,                     0,                     0                    };   
  protected static final int[]    speeds         = {0,    300,                   250,                   250,                 200,                 500,                   50,                    200,                   200,                   250,                   300,                   500,                   0,                     0,                     0,                      00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]    weights        = {0,    1000,                  700,                   700,                 500,                 1500,                  1200,                  300,                   300,                   250,                   1500,                  30000,                 1200,                  3000,                  50,                     1200,                  1500,                  2000,                  3000,                  2500,                  3000,                  2000,                  3000,                  2000,                  3000,                  5000,                  4000,                  2500,                  1500,                  5000,                  2000,                  0,                     0                    };   
  protected static final int[]    stats          = {0,    0,                     0,                     0,                   0,                    0,                     0,                     0,                     0,                     0,                     0,                    0,                     0,                     0,                     0,                      RPG.ST_MOVESPEED,      0,                     0,                     0,                     RPG.ST_RESISTPOISON,   0,                     0,                     0,                     0,                     RPG.ST_AG,             0,                     0,                     RPG.ST_SK,             0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]    bonuses        = {0,    0,                     0,                     0,                   0,                   0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      10,                    0,                     0,                     0,                     3,                     0,                     0,                     0,                     0,                     5,                     0,                     0,                     1,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]    values         = {0,    1500,                  1200,                  2500,                400000,              3000,                  8000,                  200,                   1600,                  1400,                  1800,                  5500,                  300,                   4500,                  7500,                   1800,                  250,                   500,                   800,                   2000,                  500,                   800,                   700,                   1000,                  5000,                  1500,                  1000,                  2000,                  800,                   1800,                  6000,                  0,                     0                    };   
  protected static final int[]    missiletypes   = {0,    RPG.MISSILE_ARROW,     RPG.MISSILE_ARROW,     RPG.MISSILE_ARROW,   RPG.MISSILE_ARROW,   RPG.MISSILE_BOLT,      RPG.MISSILE_BOLT,      RPG.MISSILE_STONE,     RPG.MISSILE_STONE,     RPG.MISSILE_ARROW,     RPG.MISSILE_ARROW,     RPG.MISSILE_BOLT,      0,                     0,                     0,                      00,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final int[]    missiles       = {0,    7,                     7,                     7,                   7,                   10,                    10,                    3,                     3,                     7,                     7,                     0,                     0,                     0,                     0,                      10,                    0,                     0,                     0,                     3,                     0,                     0,                     0,                     0,                     5,                     0,                     0,                     1,                     0,                     0,                     0,                     0,                     0                    };   

  public static final int[] qualitymuls   ={0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260};
  public static final int[] qualitylevels ={0, -4, -3, -2, -1, 0,   2,   4,   6,   9,   13,  18,  25,  40};
  
  public static RangedWeapon createRangedWeapon(int level) {
    int t=1;
    int q=1;
    
    // adjust level slightly
    level=level+RPG.r(3)-RPG.r(3);
    
    for (int i=0; i<100; i++) {
      t=RPG.d(names.length-1);
      if (RPG.d(rarity[t])!=1) continue;
      
      int ql=levels[t];     // level of average weapon
      if ((ql+qualitylevels[3])>level) continue; // can't make it bad enough
      
      q=3;
      
      // improve the weapon if luck and level allows it
      //
      //  could use : (RPG.d(13)>q) &&   to limit qualities?
      while ( (ql+qualitylevels[q+1]<=level) ) {q++;}
      break;
    }
    return new RangedWeapon(t,q);
  }

  public int wieldType() {return RPG.WT_RANGEDWEAPON;}

  public RangedWeapon(int t, int q) {
    type=t;
    quality=q; 
  }


  public int damage(int dam, int damtype) {
    return super.damage(dam,damtype); 
  }
  
  public int getWeight() {
  	return weights[type];
  }
  
  public int getImage() {
    return images[type]; 
  }

  public int useType() {
    return USE_NORMAL|USE_FIRE;
  }
  
  // used by hero
  public void use(Being user) {
    use(user, USE_FIRE);
  }
  
  public void use(Being user, int usetype) {
    if (user==Game.hero) {
      // get appropraite missile
      Thing missile=user.getWielded(RPG.WT_MISSILE); 
      if (!isValidAmmo(missile)) {
        Thing [] ms = user.inv.getStatContents(RPG.ST_MISSILETYPE,missiletypes[type]);
        if (ms.length>1) {
          missile=Game.selectItem("Select ammunition for your "+getName()+":",user.inv.getContents(Missile.class)); 
        } else {
          if (ms.length==1) missile=ms[0]; 
        }
      }

      // fire away!
      if (missile!=null) {
        user.wield((Item)missile,RPG.WT_MISSILE);
        fire(user,(Missile)missile);
      } else {
        Game.message("You have no ammunition for your "+getName()); 
      }
    }
  }
  
  // check ammo
  public boolean isValidAmmo(Thing m) {
    return (m!=null)&&(m.getStat(RPG.ST_MISSILETYPE)==missiletypes[type]); 
  }
  
  // fire the weapon
  public void fire(Being shooter, Missile missile) {
    Hero h=Game.hero;
    if (shooter==h) {
      if (isValidAmmo(missile)) {
        GameScreen gs=(GameScreen)Game.questapp.screen;

        Point p=gs.getTargetLocation(gs.map.findNearestFoe(h));
        if (p!=null) {
          missile=(Missile)missile.remove(1);
          fireAt(shooter,missile,gs.map,p.x,p.y);
        }

      } else {
        Game.message("You are unable to use "+missile.getTheName()+" as ammunition for your "+getName());
      } 
    } else {
      // perhaps some AI here later
    }
  }
  
  public Thing createAmmo() {
    int atype=missiletypes[type];
    if (atype>0) return Missile.createMissileType(atype);
    return null;
  }
  
  public void fireAt(Being shooter, Missile missile, Map m, int tx, int ty) {
    boolean ishero=(shooter==Game.hero);
    
    if (missile!=null) {
      shooter.aps-=(speeds[type]*10)/(10+shooter.getStat(RPG.ST_ARCHERY));
      
       // find where thing hits
      Point p=m.tracePath(shooter.x,shooter.y,tx,ty);
    
      // check for current location
      if ((p.x==x)&&(p.y==y)) {
        shooter.dropThing(missile); 
        return;
      } else {
        // calculate ranged skill
        int rsk=(shooter.getStat(RPG.ST_SK)*(3+shooter.getStat(RPG.ST_ARCHERY)))/3;
        rsk = (rsk * rskmuls[type] * missile.getStat(RPG.ST_RSKMULTIPLIER)) / 10000;
        rsk = rsk + rskbonuses[type] + missile.getStat(RPG.ST_RSKBONUS);
 
        // calculate distance and offset
        int dx=tx-shooter.x; 
        int dy=ty-shooter.y;
        int dist=(int)Math.sqrt(dx*dx+dy*dy);

        // what are we firing at?
        Thing target = m.getMobile(tx,ty);
    
        int range=missile.getStat(RPG.ST_RANGE);
        
        Game.mappanel.doShot(shooter.x,shooter.y,tx,ty,100,0.5);

        int shot_difficulty=missile.shotDifficulty(shooter,target,dist);
        
        // get the hit factor
        // 0          = miss
        // 1          = normal hit
        // 2 or above = critical hit
        int factor = RPG.hitFactor(rsk,shot_difficulty);
          
        if ((target!=null)&&RPG.test(rsk,shot_difficulty)) {
          //have scored a hit!
          int rst=shooter.getStat(RPG.ST_ST)+shooter.getStat(RPG.ST_RANGER)*4;
          rst = (rst * rstmuls[type] * missile.getStat(RPG.ST_RSTMULTIPLIER)) / 10000;
          rst = rst + rstbonuses[type] + missile.getStat(RPG.ST_RSTBONUS);
          
          if (target.isVisible()) {
            if (shooter==Game.hero) {
              switch (factor) {
                case 1: Game.message("You fire and hit "+target.getTheName()); break;
                case 2: Game.message("You fire and score a great hit on "+target.getTheName()); break;
                default: Game.message("You fire and score a perfect hit on "+target.getTheName()); break;
              }              
            } else {
              Game.message(shooter.getTheName()+" fires and hits "+target.getTheName());
            }
          }
          missile.hit(target,rst*factor);
        
          // drop item if missile survives
          if (RPG.d(100)<=missile.survivals[missile.type]) missile.moveTo(m,tx,ty);
        
          return;
        } else {
          Game.message(missile.getTheName()+" misses"); 
          missile.moveTo(m,tx,ty);
        }
     
        
      }
    } else {
      if (ishero) Game.message("You have no ammunition!"); 
    }
  }
  
  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ITEMVALUE: {
        return values[type]*Lib.qualityvalues[getQuality()]/100;
      }
      default: return super.getStat(s); 
    } 
  }
  
  public String getSingularName() {
    return isIdentified()?names[type]:uinames[type];
  }
  
  public Description getDescription() {return this;}
}