//
// Class representing a range of material types
//

package rl;

public class Material {
  
  // weight is relative to iron=100
  // value  is copper coins per kilo (10 stones)
  // levels is the player/dungeon level at which the material is most likely to be found
  
  private static final int[] numbers =        {0          , 1     , 2         , 3                  ,4      , 5      , 6       , 7     , 8           , 9             ,10     ,11          ,12         ,13           ,14      , 15    , 16        , 17       , 18          , 19         , 20    , 21        , 22 };                                 
  private static final String[] names =       {"wierdness", "wood", "pearwood", "sapient pearwood" ,"flint", "stone", "bronze", "iron", "dwarf iron", "elven steel" ,"steel","blue steel","red steel","black steel","silver", "gold", "krythium", "mithril", "adamantite", "parillite", "ruby", "sapphire", "emerald"};
  private static final int[] levels =         {0          , 2     , 4         , 10                 ,0      , 1      , 1       , 3     , 10          , 12            ,5      ,9           ,8          ,11           ,9       , 13    , 6         , 14       , 18          , 24         , 16    , 19        , 27};                                 
  private static final int[] weights =        {100        , 50    , 40        , 40                 ,150    , 120    , 90      , 100   , 150         , 60            ,90     ,70          ,80         ,70           ,100     , 200   , 250       , 50       , 60          , 55         , 80    , 70        , 60};                                 
  private static final int[] values =         {0          , 1     , 20        , 1000000            ,2      , 5      , 10      , 20    , 3000        , 25000         ,50     ,1200        ,500        ,10000        ,100     , 1000  , 300       , 100000   , 300000      , 1000000    , 50000 , 500000    , 5000000};                                 

  private static final int[] rustable =       {0          , 1     , 0         , 0                  ,0      , 0      , 1       , 1     , 1           , 0             ,0      ,0           ,1          ,0            ,0       , 0     , 1         , 0        , 0           , 0          , 0     , 0         , 0 };                                 
  private static final int[] toughness =      {0          , 30    , 60        , 700                ,40     , 60     , 80      , 100   , 300         , 180           ,150    ,250         ,400        ,500          ,100     , 100   , 300       , 600      , 800         , 2500       , 1000  , 2000      , 5000 };                                 
  
  // rarity values, 0=never
  private static final int[] weaponrarity =   {0          , 20    , 40        , 100                ,3      , 5      , 1       , 1     , 3           , 5             ,1      ,2           ,3          ,4            ,5       , 10    , 3         , 3        , 4           , 15         , 6     , 10        , 20 };                                 
  private static final int[] armourrarity =   {0          , 0     , 0         , 0                  ,0      , 0      , 1       , 1     , 3           , 5             ,1      ,2           ,3          ,4            ,5       , 10    , 3         , 3        , 4           , 15         , 6     , 10        , 20 };                                 

  private static final int[] weaponskill =    {100        , 100   , 100       , 100                ,70     , 80     , 90      , 100   , 100         , 140           ,110    ,130         ,110        ,140          ,100     , 80    , 80        , 160      , 150         , 170        , 130   , 160       , 200 };                                 
  private static final int[] weaponstrength = {100        , 30    , 50        , 70                 ,60     , 70     , 80      , 100   , 140         , 120           ,110    ,120         ,130        ,140          ,130     , 120   , 150       , 140      , 160         , 180        , 150   , 180       , 200 };                                 

  private static final int[] armourstrength = {100        , 30    , 40        , 200                ,40     , 60     , 80      , 100   , 580         , 750           ,150    ,450         ,350        ,550          ,250     , 200   , 250       , 1000     , 1500        , 2000       , 1200  , 1700      , 2500 };                                 

  private static final int[] zeros =          {0          , 0     , 0         , 0                  ,0      , 0      , 0       , 0     , 0           , 0             ,0      ,0           ,0          ,0            ,0       , 0     , 0         , 0        , 0           , 0          , 0     , 0         , 0 };                                 

  public static final int WOOD            = 1;
  public static final int PEARWOOD        = 2;
  public static final int SAPIENTPEARWOOD = 3;
  public static final int FLINT           = 4;
  public static final int STONE           = 5;
  public static final int BRONZE          = 6;
  public static final int IRON            = 7;
  public static final int DWARFIRON       = 8;
  public static final int ELVENSTEEL      = 9;
  public static final int STEEL           = 10;
  public static final int BLUESTEEL       = 11;
  public static final int REDSTEEL        = 12;
  public static final int BLACKSTEEL      = 13;
  public static final int SILVER          = 14;
  public static final int GOLD            = 15;
  public static final int KRYTHIUM        = 16;
  public static final int MITHRIL         = 17;
  public static final int ADAMANTITE      = 18;
  public static final int PARILLITE       = 19;
  public static final int RUBY            = 20;
  public static final int SAPPHIRE        = 21;
  public static final int EMERALD         = 22;
  
  // get the value of a given volume (size) of material
  // use long type for calculation in case of overflow  
  public static int getValue(int type, int quantity) {
    return (int) (((long)(values[type])*quantity)/1000); 
  }
  
  // modify item flags for material
  public static int modifyFlags(int material, int flags) {
    switch (material) {
      case SAPIENTPEARWOOD: case SILVER: case GOLD: {
        flags|=Item.ITEM_MAGIC;
        break; 
      }
      case BLACKSTEEL: {
        flags|=Item.ITEM_CURSED; 
        break;
      }
      case REDSTEEL: {
        flags|=Item.ITEM_RUSTY; 
        break;
      }
      case EMERALD: {
        flags|=Item.ITEM_ARTIFACT; 
        break;
      }
    } 
    return flags;
  }
  
  // return a material appropriate for given level or below
  public static int getWeaponMaterial(int level) {
    for (int i=1; i<100; i++) {
      int r=RPG.d(numbers.length-1);
      if (((levels[r])<=level)) {
        // check if this is a valid weapon material
        if (weaponrarity[r]==0) continue;
        
        // randomise away from rare materials
        if (RPG.d(weaponrarity[r])!=1) continue;
        
        return r; 
      }
    }  
    return STEEL;
  }
  
  // return a material appropriate for given level or below
  public static int getArmourMaterial(int level) {
    for (int i=1; i<100; i++) {
      int r=RPG.d(numbers.length-1);
      if (((levels[r])<=level)) {
        // check if this is a valid weapon material
        if (armourrarity[r]==0) continue;
        
        // randomise away from rare materials
        if (RPG.d(armourrarity[r])!=1) continue;
        
        return r; 
      }
    }  
    return STEEL;
  }
  
  // can a character identify this material? 
  public static boolean canIdentifyMaterial(Thing person, int mat) {
    if (person.getStat(RPG.ST_SMITHING)>0) {
      if (levels[mat]<10) return true;
      if (person.getStat(RPG.ST_SMITHING)>1) return true;
    }
    return false; 
  }
  
  // return a random material
  public static int random() {
    return RPG.d(names.length-1);
  }
  
  // weapon stat modifiers
  public static int getWeaponSkill(int type) {
    return weaponskill[type];
  }
  
  public static int getWeaponStrength(int type) {
    return weaponstrength[type];
  }

  public static int getArmourStrength(int type) {
    return armourstrength[type];
  }

  // returns weight of material for a given size
  // note: size 100 of iron weighs 1s (100 weight units)
  public static int getWeight(int type, int size) {
    return (weights[type]*size)/100;
  }
  
  public static int getLevel(int type) {
    return levels[type];
  } 
  
  public static int getToughness(int type) {
    return toughness[type]; 
  }
  
  public static String getName(int type) {return names[type];}
  
  public static String getRustyAdjective(int type) {
    switch (type) {
      case 1: return "rotten";
      default: return "rusty";
    }
  }

  public static String getAdjective(int type) {
    switch (type) {
      case 0: return "wierd";
      case 1: return "wooden";
      default: return names[type];
    }
  }
  
  
}