//
// Class to describe a large class of combat weapons which can be made
// from a range of different materials and posess varying quality.
//
//   e.g. [excellent] [black steel] [broadsword]
//
// CommonWeapon can be subclassed to create enhanced weapons
// with additional properties, e.g. slay undead, extreme damage
//

package rl;

public class CommonWeapon extends Weapon {
  public int type;
  public int material;
  
  // KEY TO MAIN WEAPON ATTRIBUTES
  // =============================
  //
  // wieldtypes  = wield type of weapon, either WT_MAINHAND or WT_TWOHANDS
  // levels =      modification for the level at which item can be recieved
  // images =      image index for each weapon type from items.gif
  // attackcosts = how much an atack with the weapon costs in APs
  //               as a guide, sword = 250
  //               can be modified by the material weight and other factors
  //
  //
  // sizes = size of object, size 100 of iron = weight 100 (1 stone)
  //         e.g. sword size = 3500   battleaxe size = 9000
  
  public static final String[]      names            = {"thing" , "knife",         "dagger",        "wierd knife",   "short sword",   "sword",         "scimitar",      "axe",           "long sword",    "broadsword",    "two-handed sword", "battle axe",    "rapier",        "stiletto dagger", "halberd",       "mace",          "two-handed mace", "morning star",   "trident"};
  public static final int[]         wieldtypes       = {0,        RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_TWOHANDS,    RPG.WT_TWOHANDS, RPG.WT_MAINHAND, RPG.WT_MAINHAND,   RPG.WT_TWOHANDS, RPG.WT_MAINHAND, RPG.WT_TWOHANDS,   RPG.WT_TWOHANDS,  RPG.WT_MAINHAND};
  public static final int[]         levels           = {0,        -3,              -2,              1,               -1,              0,               0,               0,               1,               2,               2,                  1,               2,               3,                 3,               0,               1,                 3,                2};                           
  public static final int[]         rarity           = {0,        1,               1,               10,              2,               1,               3,               2,               2,               5,               3,                  5,               10,              15,                8,               4,               10,                10,               12,                  0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 

  public static final int[]         images           = {0,        0,               2,               13,              5,               5,               61,              12,              3,               4,               4,                  11,              3,               6,                 21,              44,              44,                45,               23}; 
  public static final int[]         sizes            = {0,        500,             800,             1000,            2500,            3500,            4000,            3000,            4500,            6000,            8000,               9000,            3000,            700,               12000,           5000,            10000,             6000,             3000,             2500,            4700,            2500,              2000,              300,             0,               0,               0,               2000,            500,               800,             1000,             1500,              2300,            1200,              1600,            5000,                750}; 
  public static final int[]         attackcosts      = {0,        150,             150,             150,             200,             250,             300,             350,             250,             400,             350,                375,             120,             170,               450,             350,             425,               300,              200}; 
  public static final int[]         skillbonuses     = {0,        1,               1,               2,               2,               2,               3,               2,               3,               2,               3,                  3,               4,               1,                 4,               2,               3,                 5,                1,               0,               0,                 8,                 2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               2,                 2,               4,                   0}; 
  public static final int[]         strengthbonuses  = {0,        2,               2,               2,               2,               3,               3,               3,               3,               4,               4,                  5,               2,               3,                 5,               3,               4,                 4,                1,               0,               0,                 12,                2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 2,               4,                   0}; 
  public static final int[]         defencebonuses   = {0,        3,               3,               4,               2,               2,               1,               1,               2,               1,               2,                  1,               4,               2,                 4,               1,               1,                 0,                1,               0,               0,                 8,                 2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               2,                 1,               0,                   0}; 
  public static final int[]         skillmuls        = {0,        60,              65,              70,              70,              75,              70,              60,              80,              50,              100,                70,              90,              70,                70,              65,              75,                95,               80,              65,              110,               200,               55,              40,              40,              50,              100,             60,                90,              100,              120,               100,             100,               75,              120,                 70}; 
  public static final int[]         strengthmuls     = {0,        40,              55,              80,              60,              65,              80,              90,              80,              100,             100,                130,             70,              70,                140,             85,              125,               120,              80,              80,              70,                180,               40,              70,              30,              50,              100,             50,                90,              0,                80,                100,             100,               75,              125,                 60}; 
  public static final int[]         defencemuls      = {0,        25,              35,              35,              35,              40,              30,              20,              35,              10,              50,                 20,              60,              40,                60,              25,              25,                20,               50,              5,               120,               70,                30,              0,               20,              0,               50,              30,                50,              60,               120,               50,              70,                30,              45,                  40}; 
  
  public static final int[]         relativevalues   = {0,        200,             200,             1000,            200,             200,             200,             150,             200,             200,             200,                200,             1000,            1000,              300,             200,             200,               400,              800,            100,             120,               10000000,          1200,            0,               0,               0,               15000,           1800,              30000,           24000,            9000,              12000,           12000,             1500,            4500,                600}; 
  public static final int[]         toughnesses      = {0,        40,              60,              100,             80,              100,             140,             120,             130,             160,             175,                200,             100,             60,                250,             180,             400,               300,              70,              35,              45,                0,                 40,              0,               0,               0,               306,             66,                807,             0,                100,               106,             206,               45,              75,                  16}; 
  public static final int[]         zeroes           = {0,        0,               0,               0,               0,               0,               0,               0,               0,               0,               0,                  0,               0,               0,                 0,               0,               0,                 0,                0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 

  public static final int[] qualitymuls   ={0, 30, 60, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180};
  public static final int[] qualitylevels ={0, -4, -3, -2, -1, 0,   2,   4,   6,   9,   13,  18,  25,  40};
  
  // get weight given size and material type
  public int getWeight() {
    return Material.getWeight(material,sizes[type]);
  }

  public CommonWeapon(int t, int m, int q) {
    super();
    type=t;
    material=m;
    quality=q; 
    
    // modify flags for material used
    flags=Material.modifyFlags(material,flags);
  }
  
  public static Weapon createWeapon(int level) {
    // add some randomness to level
    level=level+RPG.r(3)-RPG.r(3);
    
    // decide the material
    int m=Material.getWeaponMaterial(level);
    
    for (int i=0; i<100; i++) {
      int t=RPG.d(names.length-1);
      if (RPG.d(rarity[t])!=1) continue;
      
      int ql=Material.getLevel(m)+levels[t];     // level of average weapon
      if ((ql+qualitylevels[3])>level) continue; // can't make it bad enough
      
      int q=3;
      
      // improve the weapon if luck and level allows it
      //
      //  could use : (RPG.d(13)>q) &&   to limit qualities?
      while ( (ql+qualitylevels[q+1]<=level) ) {q++;}
      
      return new CommonWeapon(t,m,q);
    }
    
    return new CommonWeapon(1,Material.IRON,4);
  }
  
  // hit target with weapon
  public int use(Thing wielder, Thing target, int action) {
    return super.use(wielder,target,action);
  }

  public int wieldType() {return wieldtypes[type];}
  
  public int getStat(int s) {
    switch(s) {
      case RPG.ST_ATTACKCOST: return  attackcosts[type];
      case RPG.ST_ASKMULTIPLIER: 
        return skillmuls[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      case RPG.ST_ASTMULTIPLIER: 
        return strengthmuls[type]*qualitymuls[quality]*Material.getWeaponStrength(material)/10000;
      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      
      case RPG.ST_ASKBONUS: 
        return skillbonuses[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      case RPG.ST_ASTBONUS: 
        return strengthbonuses[type]*qualitymuls[quality]*Material.getWeaponStrength(material)/10000;
      case RPG.ST_DSKBONUS: 
        return defencebonuses[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      
      case RPG.ST_ITEMVALUE: {
        int val=Material.getValue(material,sizes[type]);
        val=val*relativevalues[type]/100;
        return RPG.niceNumber(val*Lib.qualityvalues[quality]/100);
      }
      
      default: return super.getStat(s);
    }	
  }
  
  public int getModifier(int s) {
    switch(s) {
      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      case RPG.ST_DSKBONUS: 
        return defencebonuses[type]*qualitymuls[quality]*Material.getWeaponSkill(material)/10000;
      default: {
        return super.getModifier(s);
      }
    }  
  }
  
  // return toughness for the weapon
  // increases with higher quality and better materials
  public int getToughness() {
    if ((quality>=13)||isArtifact()) return 0;
    
    // base toughness for average iron sword
    int tuff=200;
    
    // rusty items are much weaker!
    if (isRusty()) tuff=tuff/4;
    
    // modify for quality
    tuff=tuff*quality/5;
    
    // modify for type and material
    tuff = (((tuff*toughnesses[type])/100)*Material.getToughness(material))/100;
    
    return tuff; 
  }
  
/*  // damage routine causes weapon to deteriorate
  public int damage(int dam, int damtype) {
    int tuff=toughnesses[type];
    if (tuff<=0) return 0;
    
    switch (damtype) {
      case RPG.DT_ACID:
        dam=dam*10; break;
    } 
    
    int det=RPG.po(dam*(13-quality),5*tuff*(quality+4));
    if ((quality<13)&&(det>0)) {
      quality-=det;
      if (place==Game.hero) {
        if (quality>0) {
          Game.message("Your "+getName()+" is damaged!"); 
        } else {
          die(); 
        }
      }
    }
    return dam; 
  }
 */ 
  
  public String getSingularName() {
    if (isIdentified()||Material.canIdentifyMaterial(Game.hero,material)) {
      return Material.getAdjective(material)+" "+names[type];
    } else {
      return names[type];
    }
  }
  
  public int getImage() {return images[type];}
  
  public Description getDescription() {
    return this;
  }
}