//
// Class to describe a large class of combat armours which can be made
// from a range of different materials and posess varying quality.
//
//   e.g. [excellent] [black steel] [broadsword]
//
// CommonArmour can be subclassed to create enhanced armours
// with additional properties, e.g. slay undead, extreme damage
//

package rl;

public class CommonArmour extends Armour {
  public int type;
  public int material;
  
  // KEY TO MAIN ARMOUR ATTRIBUTES
  // =============================
  //
  // wieldtypes  = wearing location for armour
  // levels =      modification for the level at which item can be recieved
  // images =      image index for each armour type from items.gif
  //
  // sizes = size of object, size 100 of iron = weight 100 (1 stone)
  //         e.g. sword size = 3500   battleaxe size = 9000
  
  public static final String[]      names            = {"thing" , "chain mail",    "light chain mail", "heavy chain mail",  "banded mail",   "scale mail",    "plate mail",    "full plate",    "B'Zekroi plate",   "helm",          "spike helm",       "horned helm",    "buckler",         "shield",          "small shield",    "great shield",    "large shield",       "spike shield",     "crown"};
  public static final int[]         wieldtypes       = {0,        RPG.WT_TORSO,    RPG.WT_TORSO,       RPG.WT_TORSO,        RPG.WT_TORSO,    RPG.WT_TORSO,    RPG.WT_TORSO,    RPG.WT_FULLBODY, RPG.WT_FULLBODY,    RPG.WT_HEAD,     RPG.WT_HEAD,        RPG.WT_HEAD,      RPG.WT_SECONDHAND, RPG.WT_SECONDHAND, RPG.WT_SECONDHAND, RPG.WT_SECONDHAND, RPG.WT_SECONDHAND,    RPG.WT_SECONDHAND,  RPG.WT_HEAD};
  public static final int[]         levels           = {0,        0,               0,                  1,                   2,               2,               3,               4,               8,                  0,               1,                  2,                0,                 1,                 0,                 3,                 2,                    3,                  4};                           
  public static final int[]         rarity           = {0,        1,               1,                  10,                  2,               1,               3,               2,               2,                  5,               3,                  5,                10,                15,                8,                 4,                 10,                   10,                 12,                  0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 

  public static final int[]         images           = {0,        349,             349,                349,                 353,             350,             340,             340,             340,                323,             329,                330,              383,               380,               388,               389,               381,                  387,                327}; 
  public static final int[]         sizes            = {0,        10000,           6000,               17000,               18000,           12000,           25000,           40000,           80000,              2000,            3000,               4000,             3000,              6000,              4000,              15000,             9000,                 7000,               2500,             2500,            4700,            2500,              2000,              300,             0,               0,               0,               2000,            500,               800,             1000,             1500,              2300,            1200,              1600,            5000,                750}; 
  
  public static final int[]         basearmour       = {0,        5,               3,                  7,                   8,               4,               13,              16,              25,                 1,               2,                  3,                1,                 0,                 0,                 0,                 0,                    0,                  0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 
  public static final int[]         encumberance     = {0,        7,               5,                  8,                   7,               4,               15,              18,              15,                 1,               1,                  1,                3,                 0,                 0,                 0,                 0,                    0,                  0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 
  public static final int[]         defencemuls      = {0,        0,               0,                  0,                   0,               0,               0,               0,               0,                  0,               0,                  0,                40,                0,                 0,                 0,                 0,                    0,                  0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 
  public static final int[]         defencebonuses   = {0,        0,               0,                  0,                   0,               0,               0,               0,               0,                  0,               0,                  0,                0,                 0,                 0,                 0,                 0,                    0,                  0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 


  public static final int[]         relativevalues   = {0,        200,             250,                300,                 500,             600,             600,             700,             2000,               200,             300,                250,              200,               200,               200,               300,               250,                  300,                800,            100,             120,               10000000,          1200,            0,               0,               0,               15000,           1800,              30000,           24000,            9000,              12000,           12000,             1500,            4500,                600}; 
  public static final int[]         zeroes           = {0,        0,               0,                  0,                   0,               0,               0,               0,               0,                  0,               0,                  0,                0,                 0,                 0,                 0,                 0,                    0,                  0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 

  
  public static final int[] qualitymuls   ={0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260};
  public static final int[] qualitylevels ={0, -4, -3, -2, -1, 0,   2,   4,   6,   9,   13,  18,  25,  40};
  
  // get weight given size and material type
  public int getWeight() {
    return Material.getWeight(material,sizes[type]);
  }

  public CommonArmour(int t, int m, int q) {
    super();
    type=t;
    material=m;
    quality=q; 
    
    // modify flags for material used
    flags=Material.modifyFlags(material,flags);
  }
  
  public static Armour createArmour(int level) {
    // add some randomness to level
    level=level+RPG.r(3)-RPG.r(3);
    
    // decide the material
    int m=Material.getArmourMaterial(level);
    
    for (int i=0; i<100; i++) {
      int t=RPG.d(names.length-1);
      if (RPG.d(rarity[t])!=1) continue;
      
      int ql=Material.getLevel(m)+levels[t];     // level of average armour
      if ((ql+qualitylevels[3])>level) continue; // can't make it bad enough
      
      int q=3;
      
      // improve the armour if luck and level allows it
      //
      //  could use : (RPG.d(13)>q) &&   to limit qualities?
      while ( (ql+qualitylevels[q+1]<=level) ) {q++;}
      
      return new CommonArmour(t,m,q);
    }
    
    return new CommonArmour(1,Material.IRON,4);
  }
  
  // hit target with armour
  public int use(Thing wielder, Thing target, int action) {
    return super.use(wielder,target,action);
  }

  public int wieldType() {return wieldtypes[type];}
  
  public int getStat(int s) {
    switch(s) {
      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type];
      
      case RPG.ST_DSKBONUS: 
        return defencebonuses[type];
      
      case RPG.ST_ITEMVALUE: {
        int val=Material.getValue(material,sizes[type]);
        val=val*relativevalues[type]/100;
        return RPG.niceNumber(val*Lib.qualityvalues[quality]/100);
      }
      
      default: return super.getStat(s);
    }	
  }
  
  public int getArmour() {
    return (basearmour[type]*qualitymuls[getEffectiveQuality()]*Material.getArmourStrength(material))/10000;
  }
  
  public int getModifier(int s) {
    
    switch(s) {
      
      case RPG.ST_ARMUNARMED: 
        return getArmour()*2;
      case RPG.ST_ARMICE: 
      case RPG.ST_ARMFIRE:
      case RPG.ST_ARMNORMAL:  
      case RPG.ST_ARMIMPACT:  
        return getArmour();
      case RPG.ST_ARMPIERCING:
      case RPG.ST_ARMMAGIC:
        return getArmour()/2;
      
      case RPG.ST_ENCUMBERANCE:
        return encumberance[type];

      case RPG.ST_DSKBONUS:
        return defencebonuses[type]*qualitymuls[getEffectiveQuality()]/100;

      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type]*qualitymuls[getEffectiveQuality()]/100;
      
      default: {
        return super.getModifier(s);
      }
    }  
  }
  
  // return toughness for the armour
  // increases with higher quality and better materials
  public int getToughness() {
    if ((quality>=13)||isArtifact()) return 0;
    
    // base toughness for average thing
    int tuff=100*basearmour[type];
    
    // rusty items are much weaker!
    if (isRusty()) tuff=tuff/4;
    
    // modify for quality
    tuff=tuff*quality/5;
    
    // modify for type and material
    tuff = ((tuff)*Material.getToughness(material))/100;
    
    return tuff; 
  }
  
  // damage routine causes armour to deteriorate
  public int damage(int dam, int damtype) {
    int tuff=getToughness();
    if (tuff<=0) return 0;
    
    switch (damtype) {
      case RPG.DT_ACID:
        dam=dam*10; break;
    } 
    
    int det=RPG.po(dam,tuff);
    if ((quality<13)&&(det>0)) {
      if (isDamaged()) {
        quality-=det;
      } else {
        setDamaged(true);
      }
      if (quality>0) {
        if (place==Game.hero) {
          Game.message("Your "+getName()+" is damaged!"); 
        }
      } else {
        die(); 
      }
    }
    return dam; 
  }

  
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