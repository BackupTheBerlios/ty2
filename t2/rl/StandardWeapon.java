package rl;

// A standard weapon, with no facny bonuses
// note Weapon.getStat(int) determines ASK,AST etc.
public class StandardWeapon extends Weapon {
  public int type;
  
  // swords
  public static final Description DESC_SWORD = 
    new Describer("sword","A slightly battered sword.");
  public static final Description DESC_RUSTYSWORD = 
    new Describer("sword","A rusty sword.");
  public static final Description DESC_OLDSWORD = 
    new Describer("sword","A well-used sword. It looks like it may once have been quite valuable.");
  public static final Description DESC_ANCIENTSWORD = 
    new Describer("sword","An ancient and rusty sword.");
  public static final Description DESC_LOVELYSWORD = 
    new Describer("sword","A beautiful sword with fine runes etched on the shining blade.");
  public static final Description DESC_GREENSWORD = 
    new Describer("sword","A fine sword with a mysterious greenish sheen.");
  public static final Description DESC_LONGSWORD = 
    new Describer("long sword","A fine longsword.");
  public static final Description DESC_SCIMITAR = 
    new Describer("scimitar","A razor-sharp curved scimitar.");
  public static final Description DESC_TWOHANDEDSWORD = 
    new Describer("two-handed sword","A huge sword designed to be used two-handed.");
  public static final Description DESC_SHORTSWORD = 
    new Describer("short sword","A heavy steel short sword.");
  public static final Description DESC_LONGDAGGER = 
    new Describer("long dagger","A long dagger.");
  public static final Description DESC_ICESWORD = 
    new Describer("ice sword","A magic sword of ice.");
  public static final Description DESC_MITHRILSWORD = 
    new Describer("mithril sword","A sword of magically sharpened mithril steel.");
  public static final Description DESC_FLAMESWORD = 
    new Describer("flaming sword","A magical sword blazing with fire.");
  public static final Description DESC_VAPOURSWORD = 
    new Describer("vapour sword","A magic sword of vapourization.");
  
  // daggers and knives
  public static final Description DESC_KNIFE = 
    new Describer("knife","knives","A crude hunting knife.",Description.NAMETYPE_NORMAL,Description.GENDER_NEUTER);
  public static final Description DESC_DAGGER = 
    new Describer("dagger","A well-sharpened dagger.");
  public static final Description DESC_WIERDKNIFE = 
    new Describer("wierd knife","wierd knives","A wierd knife, etched with goblin runes and strangely twisted.",Description.NAMETYPE_NORMAL,Description.GENDER_NEUTER);
  public static final Description DESC_MITHRILDAGGER = 
    new Describer("mithril dagger","A charmed mithril dagger.");
  public static final Description DESC_ASSASINDAGGER = 
    new Describer("poison dagger","A poison dagger.");
  public static final Description DESC_CHILLDAGGER = 
    new Describer("chill dagger","An evil dagger of chill.");
  public static final Description DESC_MAGICDAGGER = 
    new Describer("magic dagger","A magic dagger of sharpness.");
  public static final Description DESC_POWERDAGGER = 
    new Describer("dagger of power","A magic dagger of power.");
  public static final Description DESC_POISONDAGGER = 
    new Describer("poisoned dagger","A poisoned assassin's dagger.");
  public static final Description DESC_KRAZYKNIFE = 
    new Describer("krazy knife","A deadly goblin krazy knife.");

  // axes
  public static final Description DESC_AXE = 
    new Describer("axe","A heavy axe.");
  public static final Description DESC_BATTLEAXE = 
    new Describer("battleaxe","A meaty battleaxe.");

  // weapons (misc)
  public static final Description DESC_EMERALDSWORD = 
    new Describer("The Emerald Sword","","The fabled Emerald Sword of Jendar. Legend has it that the Emerald Sword was forged by the Creator himself, and is matchless among blades.",Description.NAMETYPE_PROPER,Description.GENDER_NEUTER);
  public static final Description DESC_WHIP = 
    new Describer("whip","A studded leather whip.");
  public static final Description DESC_STRANGEWEAPON = 
    new Describer("strange weapon","A perculiar but effective-looking weapon that you are unable to recognise.");
  public static final Description DESC_SCYTHE = 
    new Describer("scythe","A rusty scythe.");
  public static final Description DESC_SPEAR = 
    new Describer("spear","A long spear.");
  public static final Description DESC_HAMMER = 
    new Describer("hammer","A crude hammer.");
  public static final Description DESC_WARHAMMER = 
    new Describer("warhammer","A heavy two-handed warhammer.");
  public static final Description DESC_MACE = 
    new Describer("mace","A one-handed mace.");
  public static final Description DESC_MORNINGSTAR = 
    new Describer("morningstar","A heavy two-handed morning star.");
  public static final Description DESC_TRIDENT = 
    new Describer("trident","A metal three-pronged trident.");
  public static final Description DESC_CLUB = 
    new Describer("club","A large wooden club.");
  public static final Description DESC_QUARTERSTAFF = 
    new Describer("quarterstaff","A wooden quarterstaff etched with runes.");
  public static final Description DESC_SHAMANSTAFF = 
    new Describer("shaman staff","A magical staff of shamanic fire.");


  // normal weapons
  public static final int KNIFE=1;
  public static final int DAGGER=2;
  public static final int LONGSWORD=3;
  public static final int SCIMITAR=4;
  public static final int AXE=5;
  public static final int WHIP=6;
  public static final int STRANGEWEAPON=7;
  public static final int LOVELYSWORD=8;
  public static final int HARMLESSSWORD=9;
  public static final int WIERDKNIFE=10;
  public static final int BATTLEAXE=11;
  public static final int WARHAMMER=12;
  public static final int HAMMER=13;
  public static final int SCYTHE=14;
  public static final int SPEAR=15;
  public static final int MACE=16;
  public static final int MORNINGSTAR=17;
  public static final int TRIDENT=18;
  public static final int CLUB=19;
  public static final int QUARTERSTAFF=20;
  
  public static final int SHORTSWORD=33;
  public static final int TWOHANDEDSWORD=34;
  public static final int LONGDAGGER=35;
  
  // special weapons
  public static final int EMERALDSWORD=21;
  public static final int BLUEKNIFE=22;
  public static final int FLAMESWORD=26;
  public static final int POISONDAGGER=27;
  public static final int KRAZYKNIFE=28;
  public static final int VAPOURSWORD=29;
  public static final int SHAMANSTAFF=30;
  public static final int ICESWORD=31;
  public static final int MITHRILSWORD=32;
  
  // unarmed combat pseudo-weapons
  public static final int BITE=23;
  public static final int BRAWL=24;
  public static final int MAUL=25;
  
  public static final int[]         types            = {0   , KNIFE,           DAGGER,          LONGSWORD,       SCIMITAR,        AXE,             WHIP,            STRANGEWEAPON,      LOVELYSWORD,      HARMLESSSWORD,    WIERDKNIFE,      BATTLEAXE,       WARHAMMER,       HAMMER,          SCYTHE,          SPEAR,           MACE,            MORNINGSTAR,      TRIDENT,         CLUB,            QUARTERSTAFF,      EMERALDSWORD,      BLUEKNIFE,       BITE,            BRAWL,           MAUL,            FLAMESWORD,      POISONDAGGER,      KRAZYKNIFE,      VAPOURSWORD,      SHAMANSTAFF,       ICESWORD,        MITHRILSWORD,      SHORTSWORD,      TWOHANDEDSWORD,      LONGDAGGER};
  public static final Description[] uidescriptions   = {null, DESC_KNIFE,      DESC_DAGGER,     DESC_LONGSWORD,  DESC_SCIMITAR,   DESC_AXE,        DESC_WHIP,       DESC_STRANGEWEAPON, DESC_LOVELYSWORD, DESC_LOVELYSWORD, DESC_WIERDKNIFE, DESC_BATTLEAXE,  DESC_WARHAMMER,  DESC_HAMMER,     DESC_SCYTHE,     DESC_SPEAR,      DESC_MACE,       DESC_MORNINGSTAR, DESC_TRIDENT,    DESC_CLUB,       DESC_QUARTERSTAFF, DESC_EMERALDSWORD, DESC_KNIFE,      null,            null,            null,            DESC_FLAMESWORD, DESC_DAGGER,       DESC_WIERDKNIFE, DESC_VAPOURSWORD, DESC_QUARTERSTAFF, DESC_ICESWORD,   DESC_LONGSWORD,    DESC_SHORTSWORD, DESC_TWOHANDEDSWORD, DESC_LONGDAGGER};
  public static final Description[] iddescriptions   = {null, DESC_KNIFE,      DESC_DAGGER,     DESC_LONGSWORD,  DESC_SCIMITAR,   DESC_AXE,        DESC_WHIP,       DESC_STRANGEWEAPON, DESC_LOVELYSWORD, DESC_LOVELYSWORD, DESC_WIERDKNIFE, DESC_BATTLEAXE,  DESC_WARHAMMER,  DESC_HAMMER,     DESC_SCYTHE,     DESC_SPEAR,      DESC_MACE,       DESC_MORNINGSTAR, DESC_TRIDENT,    DESC_CLUB,       DESC_QUARTERSTAFF, DESC_EMERALDSWORD, DESC_KNIFE,      null,            null,            null,            DESC_FLAMESWORD, DESC_POISONDAGGER, DESC_KRAZYKNIFE, DESC_VAPOURSWORD, DESC_SHAMANSTAFF,  DESC_ICESWORD,   DESC_MITHRILSWORD, DESC_SHORTSWORD, DESC_TWOHANDEDSWORD, DESC_LONGDAGGER};
  public static final int[]         wieldtypes       = {0,    RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND,    RPG.WT_MAINHAND,  RPG.WT_MAINHAND,  RPG.WT_MAINHAND, RPG.WT_TWOHANDS, RPG.WT_TWOHANDS, RPG.WT_MAINHAND, RPG.WT_TWOHANDS, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_TWOHANDS,  RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_TWOHANDS,   RPG.WT_MAINHAND,   RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND,   RPG.WT_MAINHAND, RPG.WT_MAINHAND,  RPG.WT_TWOHANDS,   RPG.WT_MAINHAND, RPG.WT_MAINHAND,   RPG.WT_MAINHAND, RPG.WT_TWOHANDS,     RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND, RPG.WT_MAINHAND};
  public static final int[]         images           = {0,    0,               2,               3,               61,              12,              22,              42,                 4,                4,                13,              11,              10,              9,               49,              20,              44,              45,               23,              26,              63,                3,                 1,               0,               0,               0,               65,              2,                 13,              67,               63,                66,              3,                 5,               4,                   6}; 
  public static final int[]         attackcosts      = {0,    160,             150,             300,             270,             350,             200,             180,                250,              280,              150,             400,             400,             400,             600,             300,             330,             300,              280,             370,             200,               200,               150,             200,             200,             200,             250,             150,               100,             250,              200,               250,             250,               300,             350,                 150}; 
  public static final int[]         skillbonuses     = {0,    1,               1,               1,               0,               1,               -2,              0,                  2,                3,                2,               3,               2,               2,               4,               1,               1,               3,                1,               0,               0,                 8,                 2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               2,                 2,               4,                   0}; 
  public static final int[]         strengthbonuses  = {0,    0,               0,               1,               2,               5,               -2,              0,                  2,                3,                3,               8,               9,               4,               8,               1,               2,               8,                1,               0,               0,                 12,                2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 2,               4,                   0}; 
  public static final int[]         defencebonuses   = {0,    1,               1,               0,               0,               0,               -2,              0,                  2,                3,                0,               0,               0,               0,               0,               1,               0,               0,                1,               0,               0,                 8,                 2,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               2,                 1,               0,                   0}; 
  public static final int[]         skillmuls        = {0,    50,              65,              100,             100,             60,              120,             100,                130,              120,              80,              100,             95,              65,              90,              70,              60,              110,              70,              65,              110,               200,               55,              40,              40,              50,              100,             60,                90,              100,              120,               100,             100,               75,              120,                 70}; 
  public static final int[]         strengthmuls     = {0,    40,              55,              80,              90,              110,             60,              60,                 110,              0,                80,              130,             120,             90,              110,             60,              95,              140,              60,              80,              70,                180,               40,              70,              30,              50,              100,             50,                90,              0,                80,                100,             100,               75,              125,                 60}; 
  public static final int[]         defencemuls      = {0,    20,              40,              27,              20,              10,              15,              30,                 60,               80,               50,              30,              20,              5,               40,              35,              10,              0,                38,              5,               120,               70,                30,              0,               20,              0,               50,              30,                50,              60,               120,               50,              70,                30,              45,                  40}; 
  public static final int[]         weights          = {0,    400,             600,             3000,            4000,            5000,            1500,            2000,               2500,             2000,             750,             7000,            8000,            4000,            4000,            2800,            5000,            5000,             2500,            4700,            2500,              2000,              300,             0,               0,               0,               2000,            500,               800,             1000,             1500,              2300,            1200,              1600,            5000,                750}; 
  public static final int[]         values           = {0,    150,             300,             1500,            1200,            900,             800,             1800,               12000,            6000,             3000,            1800,            1500,            750,             300,             600,             1500,            1500,             1000,            100,             120,               10000000,          1200,            0,               0,               0,               15000,           1800,              30000,           24000,            9000,              12000,           12000,             1500,            4500,                600}; 
  public static final int[]         stats            = {0,    0,               0,               0,               0,               0,               0,               0,                  0,                0,                0,               0,               0,               0,               0,               0,               0,               0,                0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 
  public static final int[]         bonuses          = {0,    0,               0,               0,               0,               0,               0,               0,                  0,                0,                0,               0,               0,               0,               0,               0,               0,               0,                0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 
  public static final int[]         qualities        = {0,    5,               5,               5,               5,               5,               5,               5,                  5,                5,                7,               5,               5,               5,               5,               5,               5,               5,                5,               5,               5,                 13,                5,               5,               5,               5,               6,               6,                 7,               6,                6,                 6,               6,                 5,               5,                   6}; 
  public static final int[]         toughnesses      = {0,    45,              45,              65,              35,              25,              25,              55,                 175,              165,              287,             85,              95,              55,              25,              15,              55,              65,               35,              35,              45,                0,                 40,              0,               0,               0,               306,             66,                807,             0,                100,               106,             206,               45,              75,                  16}; 
  public static final int[]         zeroes           = {0,    0,               0,               0,               0,               0,               0,               0,                  0,                0,                0,               0,               0,               0,               0,               0,               0,               0,                0,               0,               0,                 0,                 0,               0,               0,               0,               0,               0,                 0,               0,                0,                 0,               0,                 0,               0,                   0}; 

  public static final Spell ICESPELL=new Spell(Spell.ICEBOLT);
  public static final Spell VAPOURSPELL=new Spell(Spell.VAPOURIZER);
  public static final Spell FIRESPELL=new Spell(Spell.FLAME);
  
  public static final int[] qualitymuls={0,30,60,80,90,100,110,120,130,140,150,160,170,180};
  
  public int getWeight() {return weights[type];}

  public StandardWeapon(int n) {
    type=n;
    quality=qualities[n]; 
  }
  
  public static final int[][] source =
     {{CLUB,KNIFE},
      {DAGGER,KNIFE,WHIP}, 
      {BLUEKNIFE,DAGGER,HAMMER},
      {AXE,QUARTERSTAFF,LONGDAGGER}, 
      {TRIDENT,SCIMITAR,SPEAR},
      {	MACE,SCYTHE,STRANGEWEAPON}, 
      {WARHAMMER,LONGSWORD,MORNINGSTAR}, 
      {BATTLEAXE,LONGSWORD,HARMLESSSWORD,POISONDAGGER}, 
      {BATTLEAXE,TWOHANDEDSWORD,SCIMITAR}, 
      {LOVELYSWORD,TWOHANDEDSWORD} 
    };
     
  public static final int[][] specialsource = {
  	{CLUB},
  	{BLUEKNIFE},
  	{LONGDAGGER},
  	{POISONDAGGER},
  	{WIERDKNIFE},
  	{TWOHANDEDSWORD},
  	{LOVELYSWORD},
  	{SHAMANSTAFF},
  	{FLAMESWORD},
  	{ICESWORD},
  	{KRAZYKNIFE},
  	{VAPOURSWORD}
  };
  
  public static final int[][] swords = {
      {KNIFE},
      {KNIFE,DAGGER},
      {KNIFE,DAGGER,LONGDAGGER},
      {BLUEKNIFE,SCIMITAR,SHORTSWORD},
      {LONGSWORD,SCIMITAR,LONGDAGGER},
      {LONGSWORD},
      {LONGSWORD,POISONDAGGER},
      {LONGSWORD,TWOHANDEDSWORD}};     
  
  public static Weapon createWeapon(int[][] list,int level) {
    // add a few bonus/negative quality levels
    int improve=RPG.r(2)*(RPG.d(3,3)-6);
    level-=RPG.sign(improve);

    do {level=level+RPG.d(2)-RPG.d(2);}
    while (RPG.d(2)==1);
    level=RPG.middle(0,level,list.length-1);
      
    Weapon w=createSpecificWeapon(list[level][RPG.r(list[level].length)]);

    // curse a few!
    if (RPG.d(100)<=level) {
      w.flags|=ITEM_CURSED;
      improve-=2; 
    }

    w.quality=RPG.middle(1,w.quality+improve,12);
    return w;
  }
  
  public static Weapon createSpecificWeapon(int n) {
    if (n==0) {
      n=RPG.d(22);
      if (n==21) n=RPG.d(20); // don't randomly make TES
    }
    
    return new StandardWeapon(n);  
  }

  // hit target with weapon
  public int use(Thing wielder, Thing target, int action) {
    switch (action) {
      case USE_HIT:
        int dam=super.use(wielder,target,action);
        
        Map map=target.getMap();

        boolean ishero=(target==Game.hero);
        
        // special weapon effects if 
        if (map!=null) switch (type) {
          case KRAZYKNIFE: case POISONDAGGER:
            if (dam>0) {
              target.addThing(new PoisonAttribute(2,RPG.DT_POISON,500*RPG.d(2,6),5));
              if (ishero) Game.message("You feel that you have been poisoned!");
            }
            break;
            
          case FLAMESWORD: case SHAMANSTAFF:
            Fire.createFire(map,target.x,target.y,2);
            break;
            
          case VAPOURSWORD:
            VAPOURSPELL.castAtLocation(null,map,target.x,target.y);
            break;  
            
          case ICESWORD:
            ICESPELL.castAtLocation(null,map,target.x,target.y);
        }
        
        return dam; 

      default:
      	return super.use(wielder,target,action); 	
  	}
  }

  public int wieldType() {return wieldtypes[type];}
  
  public int getStat(int s) {
    switch(s) {
      case RPG.ST_ATTACKCOST: return  attackcosts[type];
      case RPG.ST_ASKMULTIPLIER: return	skillmuls[type]*qualitymuls[quality]/100;
      case RPG.ST_ASTMULTIPLIER: return	strengthmuls[type]*qualitymuls[quality]/100;
      case RPG.ST_DSKMULTIPLIER: return	defencemuls[type]*qualitymuls[quality]/100;
      case RPG.ST_ASKBONUS: return	skillbonuses[type];
      case RPG.ST_ASTBONUS: return	strengthbonuses[type];
      case RPG.ST_DSKBONUS: return	defencebonuses[type];
      
      case RPG.ST_ITEMVALUE: return RPG.niceNumber(values[type])*Lib.qualityvalues[quality]/100;
      
      default: return super.getStat(s);
    }	
  }
  
  public int getModifier(int s) {
    switch(s) {
      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type]*qualitymuls[quality]/100;
      case RPG.ST_DSKBONUS: 
        return defencebonuses[type];
      default: {
        // check for special bonus
        if (s==stats[type]) return bonuses[type]+super.getModifier(s); 
        
        return super.getModifier(s);
      }
    }  
  }
  
  // return toughness for the weapon
  // increases with higher quality
  public int getToughness() {
    if (quality>=13) return 0;
    return (toughnesses[type]*quality*quality)/10; 
  }
  
  // damage routine causes weapon to deteriorate
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
  
  public int getImage() {return images[type];}
  
  public Description getDescription() {
    return isIdentified()?iddescriptions[type]:uidescriptions[type];
  }
}