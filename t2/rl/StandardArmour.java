package rl;

// Standard armour types
// *still need to implement defence skill, encumberance*
public class StandardArmour extends Armour {
  protected int type;
  
  // armour descriptions
  public static final Description DESC_ = 
    new Describer("armour","pieces of armour",Description.NAMETYPE_QUANTITY,"Nondescript piece of armour.");

  public static final Description DESC_PLATEARMOUR = 
    new Describer("plate armour","suits of plate armour",Description.NAMETYPE_QUANTITY,"Plate armour, heavy but effective.");
  public static final Description DESC_RUSTYPLATEARMOUR = 
    new Describer("rusty plate armour","suits of rusty plate armour",Description.NAMETYPE_QUANTITY,"Rusty plate armour. It seems very heavy and cumbersome.");
  public static final Description DESC_MITHRILPLATE = 
    new Describer("mithril plate armour","suits of mithril plate armour","Beautifully crafted mithril plate armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);

  public static final Description DESC_CHAINMAIL = 
    new Describer("chain mail","suits of chain mail",Description.NAMETYPE_QUANTITY,"Well made chain mail.");
  public static final Description DESC_LIGHTCHAINMAIL = 
    new Describer("light chain mail","suits of light chain mail",Description.NAMETYPE_QUANTITY,"Well made light chain mail.");
  public static final Description DESC_HEAVYCHAINMAIL = 
    new Describer("heavy chain mail","suits of heavy chain mail",Description.NAMETYPE_QUANTITY,"Well made heavy chain mail.");
  public static final Description DESC_ELVENCHAINMAIL = 
    new Describer("elven chain mail","suits of elven chain mail",Description.NAMETYPE_QUANTITY,"Beautiful elven chain mail.");
  public static final Description DESC_DWARVENCHAINMAIL = 
    new Describer("dwarven chain mail","suits of dwarven chain mail",Description.NAMETYPE_QUANTITY,"High quality dwarven chain mail.");
  public static final Description DESC_MAGICCHAINMAIL = 
    new Describer("magic chain mail","suits of magic chain mail",Description.NAMETYPE_QUANTITY,"Enchanted chain mail.");
  public static final Description DESC_MITHRILMAIL = 
    new Describer("mithril mail","suits of mithril mail","Beautifully crafted mithril mail shirt.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_HEAVYMITHRILMAIL = 
    new Describer("heavy mithril mail","suits of mithril mail","Beautifully crafted mithril mail shirt.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);

  public static final Description DESC_LEATHER = 
    new Describer("leather armour","suits of leather armour","A suit of tough leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_DRAGONLEATHER = 
    new Describer("dragon leather armour","suits of dragon leather armour","A suit of dragon leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_LIGHTLEATHER = 
    new Describer("light leather armour","suits of leather armour","A suit of tough leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_HEAVYLEATHER = 
    new Describer("heavy leather armour","suits of heavy leather armour","A suit of tough leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_ORCISHLEATHER = 
    new Describer("orcish leather armour","suits of orcish leather armour","A suit of tough orcish leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_MAGICLEATHER = 
    new Describer("magic leather armour","suits of magic leather armour","A suit of magical leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_BLACKLEATHER = 
    new Describer("black leather armour","suits of black leather armour","A suit of jet black leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_DEMONICLEATHER = 
    new Describer("demonic leather armour","suits of demonic leather armour","A suit of jet black demonic leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
  public static final Description DESC_STUDDEDLEATHER = 
    new Describer("studded leather armour","suits of studded leather armour","A suit of metal-studded leather armour.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);

  public static final Description DESC_SCALEMAIL = 
    new Describer("scale mail","suits of scale mail",Description.NAMETYPE_QUANTITY,"A suit of scale mail.");
  public static final Description DESC_BANDEDMAIL = 
    new Describer("banded mail","suits of banded mail",Description.NAMETYPE_QUANTITY,"A suit of banded mail.");

  // helmets and headgear
  public static final Description DESC_HORNEDHELM = 
    new Describer("horned helm","A heavy helmet, with sharp horns.");
  public static final Description DESC_SPIKEHELM = 
    new Describer("spike helm","A strange looking helmet with vicious spikes.");
  public static final Description DESC_HELMET = 
    new Describer("helmet","A plain-looking helmet.");

  // boots
  public static final Description DESC_BOOTS = 
    new Describer("boots","pairs of boots",Description.NAMETYPE_QUANTITY,"Tough leather boots.");
  public static final Description DESC_SPEEDBOOTS = 
    new Describer("boots of swiftness","pairs of boots of swiftness",Description.NAMETYPE_QUANTITY,"Boots of swiftness. These boots magically enhance the sped of the wearer.");
  public static final Description DESC_IRONCLADBOOTS = 
    new Describer("ironclad boots","pairs of ironclad boots",Description.NAMETYPE_QUANTITY,"Heavy metal boots.");
  public static final Description DESC_KEROMBAIBOOTS = 
    new Describer("ironclad boots of war \"Ker-Ombai\"","",Description.NAMETYPE_QUANTITY,"The fabled ironclad boots \"Ker-Ombai\".");
  public static final Description DESC_SHOES = 
    new Describer("shoes","pairs of shoes",Description.NAMETYPE_QUANTITY,"Tough leather shoes.");
  public static final Description DESC_STEELSHOES = 
    new Describer("steel shoes","pairs of steel shoes",Description.NAMETYPE_QUANTITY,"Steel reinforced shoes.");
  public static final Description DESC_DANCINGSHOES = 
    new Describer("dancing shoes","pairs of dancing shoes",Description.NAMETYPE_QUANTITY,"Magic dancing shoes.");
  public static final Description DESC_BLACKLEATHERBOOTS = 
    new Describer("black leather boots","pairs of black leather boots",Description.NAMETYPE_QUANTITY,"Tall black leather boots. Very fashionable.");
  public static final Description DESC_NITROZACBOOTS = 
    new Describer("black leather boots \"Nitrozac\"","",Description.NAMETYPE_QUANTITY,"The artifact black leather boots \"Nitrozac\".");

  // jewelry and crowns
  public static final Description DESC_GOLDCROWN = 
    new Describer("golden crown","A polished golden crown.");
  public static final Description DESC_CRYSTALCROWN = 
    new Describer("crystal crown","A sparkling crystal crown.");
  public static final Description DESC_TIARA = 
    new Describer("tiara","A dazzling blue tiara. The patterns on its surface seem to shift before your eyes.");
  public static final Description DESC_NECKLACE = 
    new Describer("necklace","A plain necklace set with a yellow stone.");
  public static final Description DESC_GOLDNECKLACE = 
    new Describer("golden necklace","A heavy gold necklace.");
  public static final Description DESC_SILVERNECKLACE = 
    new Describer("silver necklace","A delicate silver necklace.");
  
  
  
  // shield descriptions
  public static final Description DESC_SHIELD = 
    new Describer("shield","A battered medium shield.");
  public static final Description DESC_SMALLSHIELD = 
    new Describer("small shield","A small shield.");
  public static final Description DESC_SUNSHIELD = 
    new Describer("sun shield","A beautifully crafted glowing shield.");
  public static final Description DESC_GREATSHIELD = 
    new Describer("great shield","A heavy great shield.");
  public static final Description DESC_SPIKESHIELD = 
    new Describer("spike shield","A medium shield with vicious spikes.");
  public static final Description DESC_BUCKLER = 
    new Describer("buckler","A light buckler.");
  public static final Description DESC_GOBLINSHIELD = 
    new Describer("goblin shield","A decorated goblin shield.");

  // random clothing
  public static final Description DESC_HAT = 
    new Describer("hat","A hat.");
  public static final Description DESC_CLOTHES = 
    new Describer("clothes","suits of clothes","A suit of town clothes.");
  public static final Description DESC_RAGS = 
    new Describer("rags","suits of rags","A pile of smelly rags.");
  public static final Description DESC_FINECLOTHES = 
    new Describer("fine clothes","suits of fine clothes","A suit of fine clothes.");
  public static final Description DESC_ROBES = 
    new Describer("robe","A spellcaster's robe.");
  public static final Description DESC_FINEHAT = 
    new Describer("fine hat","A finely made hat.");
  
  // other stuff
	public static final Description DESC_RINGOFPROTECTION = 
		new Describer("ring of protection","rings of protection","A magic ring of protection.",Description.NAMETYPE_NORMAL,Description.GENDER_NEUTER);
	public static final Description DESC_RINGOFUNBURDENING = 
		new Describer("ring of unburdening","rings of unburdening","A magic ring of unburdening.",Description.NAMETYPE_NORMAL,Description.GENDER_NEUTER);

  public static final int USELESSPLATE=1;
  public static final int CHAINMAIL=2;
  public static final int LEATHER=3;
  public static final int PLATE=4;
  public static final int LIGHTCHAINMAIL=5;
  public static final int MITHRILMAIL=6;
  public static final int HEAVYHORNEDHELM=7;
  public static final int HORNEDHELM=8;
  public static final int SPIKEHELM=9;
  public static final int RINGOFPROTECTION=10;
  public static final int BOOTS=11;
  public static final int IRONCLADBOOTS=12;
  public static final int RINGOFUNBURDENING=13;
  public static final int SPEEDBOOTS=14;
  public static final int HELMET=15;
  public static final int LIGHTLEATHER=16;
  public static final int STUDDEDLEATHER=17;
  public static final int BLACKLEATHER=18;
  public static final int LIGHTSHIELD=19;
  public static final int SMALLSHIELD=20;
  public static final int SHIELD=21;
  public static final int BUCKLER=22;
  public static final int SUNSHIELD=23;
  public static final int GREATSHIELD=24;
  public static final int MEDIUMSHIELD=25;
  public static final int SPIKESHIELD=26;
  public static final int LIGHTBUCKLER=27;
  public static final int HEAVYGREATSHIELD=28;
  public static final int GOBLINSHIELD=29;
  public static final int SHOES=30;
  public static final int STEELSHOES=31;
  public static final int DANCINGSHOES=32;
  public static final int BLACKLEATHERBOOTS=33;
  public static final int NITROZACBOOTS=34;
  public static final int KEROMBAIBOOTS=35;
  public static final int ALTERNATELEATHER=36;
  public static final int DRAGONLEATHER=37;
  public static final int HEAVYLEATHER=38;
  public static final int ORCISHLEATHER=39;
  public static final int MAGICLEATHER=40;
  public static final int DEMONICLEATHER=41;
  public static final int HEAVYCHAINMAIL=42;
  public static final int ELVENCHAINMAIL=43;
  public static final int DWARVENCHAINMAIL=44;
  public static final int MAGICCHAINMAIL=45;
  public static final int HEAVYMITHRILMAIL=46;
  public static final int Z=1;

  // Standard armour data table
  protected static final  int[]         types          = {0,    1,                     2,                     3,                     4,                     5,                     6,                     7,                     8,                     9,                     10,                    11,                    12,                    13,                     14,                    15,                    16,                    17,                    18,                    19,                    20,                    21,                    22,                    23,                    24,                    25,                    26,                    27,                    28,                    29,                    30,                    31                   , 32                   , 33                    , 34                    , 35                   , 36                   , 37                   , 38                   , 39                   , 40                   , 41                   , 42                   , 43                   , 44                   , 45                   , 46                   , 21                   , };   
  protected static final  Description[] uidescriptions = {null, DESC_RUSTYPLATEARMOUR, DESC_CHAINMAIL       , DESC_LEATHER         , DESC_RUSTYPLATEARMOUR, DESC_CHAINMAIL       , DESC_MITHRILMAIL     , DESC_HORNEDHELM      , DESC_HORNEDHELM      , DESC_SPIKEHELM       , Ring.DESC_PLAINRING  , DESC_BOOTS           , DESC_IRONCLADBOOTS   , Ring.DESC_GOLDENRING  , DESC_BOOTS           , DESC_HELMET          , DESC_LEATHER         , DESC_STUDDEDLEATHER  , DESC_BLACKLEATHER    , DESC_SHIELD          , DESC_SMALLSHIELD     , DESC_SHIELD          , DESC_BUCKLER         , DESC_SUNSHIELD       , DESC_GREATSHIELD     , DESC_SHIELD          , DESC_SPIKESHIELD     , DESC_BUCKLER         , DESC_GREATSHIELD     , DESC_GOBLINSHIELD    , DESC_SHOES           , DESC_STEELSHOES      , DESC_SHOES           , DESC_BLACKLEATHERBOOTS, DESC_BLACKLEATHERBOOTS, DESC_KEROMBAIBOOTS   , DESC_LEATHER         , DESC_DRAGONLEATHER   , DESC_LEATHER         , DESC_LEATHER         , DESC_LEATHER         , DESC_BLACKLEATHER    , DESC_HEAVYCHAINMAIL  , DESC_CHAINMAIL       , DESC_HEAVYCHAINMAIL  , DESC_CHAINMAIL       , DESC_MITHRILMAIL     , DESC_                , DESC_                , DESC_                , };
  protected static final  Description[] iddescriptions = {null, DESC_RUSTYPLATEARMOUR, DESC_CHAINMAIL       , DESC_LEATHER         , DESC_PLATEARMOUR     , DESC_LIGHTCHAINMAIL  , DESC_MITHRILMAIL     , DESC_HORNEDHELM      , DESC_HORNEDHELM      , DESC_SPIKEHELM       , DESC_RINGOFPROTECTION, DESC_BOOTS           , DESC_IRONCLADBOOTS   , DESC_RINGOFUNBURDENING, DESC_SPEEDBOOTS      , DESC_HELMET          , DESC_LIGHTLEATHER    , DESC_STUDDEDLEATHER  , DESC_BLACKLEATHER    , DESC_SHIELD          , DESC_SMALLSHIELD     , DESC_SHIELD          , DESC_BUCKLER         , DESC_SUNSHIELD       , DESC_GREATSHIELD     , DESC_SHIELD          , DESC_SPIKESHIELD     , DESC_BUCKLER         , DESC_GREATSHIELD     , DESC_GOBLINSHIELD    , DESC_SHOES           , DESC_STEELSHOES      , DESC_DANCINGSHOES    , DESC_BLACKLEATHERBOOTS, DESC_NITROZACBOOTS    , DESC_IRONCLADBOOTS   , DESC_LEATHER         , DESC_DRAGONLEATHER   , DESC_HEAVYLEATHER    , DESC_ORCISHLEATHER   , DESC_MAGICLEATHER    , DESC_DEMONICLEATHER  , DESC_HEAVYCHAINMAIL  , DESC_ELVENCHAINMAIL  , DESC_DWARVENCHAINMAIL, DESC_MAGICCHAINMAIL  , DESC_HEAVYMITHRILMAIL, DESC_                , DESC_                , DESC_                , };
  protected static final  int[]         areas          = {0,    RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_HEAD          , RPG.WT_HEAD          , RPG.WT_HEAD          , RPG.WT_LEFTRING      , RPG.WT_BOOTS         , RPG.WT_BOOTS         , RPG.WT_LEFTRING       , RPG.WT_BOOTS         , RPG.WT_HEAD          , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_SECONDHAND    , RPG.WT_BOOTS         , RPG.WT_BOOTS         , RPG.WT_BOOTS         , RPG.WT_BOOTS          , RPG.WT_BOOTS          , RPG.WT_BOOTS         , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , RPG.WT_FULLBODY      , };             
  protected static final  int[]         images         = {0,    340,                   349,                   348,                   340,                   349,                   350,                   330,                   330,                   329,                   201,                   360,                   361,                   200,                    360,                   323,                   347,                   351,                   346,                   381,                   380,                   382,                   383,                   384,                   385,                   386,                   387,                   388,                   389,                   390,                   362,                   363,                   362,                   364,                    364,                    361,                   347,                   348,                   348,                   347,                   348,                   346,                   349,                   349,                   349,                   8,                     9,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         arms           = {0,    6,                     3,                     2,                     10,                    2,                     8,                     2,                     1,                     1,                     2,                     1,                     2,                     0,                      1,                     1,                     1,                     2,                     2,                     1,                     1,                     2,                     1,                     3,                     4,                     3,                     3,                     0,                     6,                     5,                     0,                     2,                     1,                     1,                      4,                      6,                     2,                     10,                    3,                     4,                     5,                     5,                     5,                     4,                     7,                     -2,                    -1,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         dsks           = {0,    -4,                    -2,                    -1,                    -2,                    -1,                    0,                     -1,                    0,                     1,                     0,                     1,                     1,                     2,                      1,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      0,                      0,                     -1,                    0,                     -2,                    -4,                    0,                     0,                     -4,                    -1,                    -4,                    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         dskmuls        = {0,    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      0,                     0,                     0,                     0,                     0,                     50,                    60,                    40,                    70,                    60,                    40,                    40,                    50,                    70,                    30,                    80,                    0,                     0,                     0,                     0,                      0,                      0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         encs           = {0,    50,                    20,                    10,                    30,                    20,                    5,                     5,                     3,                     4,                     0,                     -4,                    -10,                   -20,                    -5,                    2,                     3,                     6,                     4,                     5,                     3,                     4,                     3,                     0,                     10,                    6,                     4,                     1,                     15,                    0,                     -5,                    0,                     -10,                   -5,                     -20,                    -10,                   6,                     11,                    12,                    6,                     6,                     8,                     25,                    10,                    23,                    15,                    10,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         weights        = {0,    10000,                 6000,                  4000,                  8000,                  4000,                  2000,                  2500,                  2000,                  1500,                  50,                    1200,                  3000,                  50,                     1200,                  1500,                  2000,                  3000,                  2500,                  3000,                  2000,                  3000,                  2000,                  3000,                  5000,                  4000,                  2500,                  1500,                  5000,                  2000,                  400,                   1400,                  300,                   800,                    600,                    2500,                  4000,                  4500,                  5000,                  4000,                  3000,                  4000,                  7000,                  3000,                  7000,                  4200,                  3000,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         stats          = {0,    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      RPG.ST_MOVESPEED,      0,                     0,                     0,                     RPG.ST_RESISTPOISON,   0,                     0,                     0,                     0,                     RPG.ST_AG,             0,                     0,                     RPG.ST_SK,             0,                     0,                     0,                     0,                     0,                     0,                     0,                      RPG.ST_SK,              RPG.ST_ST,             0,                     RPG.ST_ST,             0,                     RPG.ST_ST,             RPG.ST_SK,             RPG.ST_IN,             0,                     0,                     0,                     RPG.ST_RESISTFIRE,     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         bonuses        = {0,    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      10,                    0,                     0,                     0,                     3,                     0,                     0,                     0,                     0,                     5,                     0,                     0,                     1,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      8,                      3,                     0,                     1,                     0,                     1,                     1,                     -1,                    0,                     0,                     0,                     2,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         values         = {0,    3000,                  1700,                  500,                   3000,                  1300,                  35000,                 600,                   400,                   1400,                  5500,                  300,                   4500,                  7500,                   1800,                  250,                   500,                   800,                   2000,                  500,                   800,                   700,                   1000,                  5000,                  1500,                  1000,                  2000,                  800,                   1800,                  6000,                  150,                   600,                   7500,                  1000,                   200000,                 100000,                500,                   50000,                 900,                   1500,                  15000,                 10000,                 2000,                  5500,                  4500,                  40000,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         qualities      = {0,    4,                     5,                     5,                     6,                     5,                     7,                     5,                     5,                     6,                     5,                     5,                     6,                     5,                      6,                     5,                     5,                     5,                     6,                     5,                     6,                     5,                     5,                     7,                     6,                     6,                     6,                     5,                     5,                     7,                     5,                     6,                     7,                     6,                      13,                     13,                    5,                     6,                     6,                     5,                     6,                     8,                     6,                     6,                     6,                     7,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         toughnesses    = {0,    40,                    40,                    25,                    80,                    35,                    200,                   15,                    20,                    60,                    15,                    25,                    330,                   5,                      36,                    15,                    15,                    35,                    66,                    45,                    36,                    35,                    25,                    77,                    60,                    40,                    70,                    25,                    75,                    300,                   30,                    80,                    60,                    90,                     0,                      0,                     25,                    150,                   40,                    30,                    120,                   60,                    60,                    50,                    80,                    180,                     0,                     0,                     0,                     0,                     0,                     0                    };   
  protected static final  int[]         zeroes         = {0,    0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                      0,                      0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0,                     0                    };   

  public static final int[][] battlearmours ={
    {LIGHTLEATHER},
    {LEATHER},
    {HEAVYLEATHER},
    {LIGHTCHAINMAIL,STUDDEDLEATHER},
    {CHAINMAIL,USELESSPLATE},
    {HEAVYCHAINMAIL,BLACKLEATHER},
    {ORCISHLEATHER,PLATE},  
    {PLATE,DEMONICLEATHER},
    {PLATE,ELVENCHAINMAIL},
    {DWARVENCHAINMAIL},
    {MITHRILMAIL},
    {MAGICCHAINMAIL},
    {HEAVYMITHRILMAIL},
  };

  public static final int[][] shields = {
    {SMALLSHIELD},
    {LIGHTSHIELD},
    {SHIELD},
    {BUCKLER},
    {MEDIUMSHIELD},
    {LIGHTBUCKLER},
    {GREATSHIELD},
    {HEAVYGREATSHIELD},
    {SUNSHIELD},
    {GOBLINSHIELD}  
  };
    
  public StandardArmour(int t) {
    type=t; 
    quality=qualities[t];
  }

  public static StandardArmour createShield(int n) {
    if (n==0) n=19+RPG.r(11);
    return new StandardArmour(n); 
  }
  
  public static StandardArmour createArmour(int n) {
    if (n==0) n=RPG.d(18);
    
    return new StandardArmour(n);

  }

  // create armour from given source matrix
  public static StandardArmour createArmour(int[][] source, int level) {
    while (RPG.d(2)==1) {level=level+RPG.d(2)-RPG.d(2);}
    int improve=RPG.r(2)*(RPG.d(3,3)-6);
    level-=RPG.sign(improve);

    level=RPG.middle(0,level,source.length-1);
    int type=source[level][RPG.r(source[level].length)];

    StandardArmour w=new StandardArmour(type);
    
    // curse a few!
    if (RPG.d(100)<=level) {
      w.flags|=ITEM_CURSED;
      improve-=2; 
    }

    w.quality=RPG.middle(1,w.quality+improve,12);
    return w;
  }
  
  // return the armour type
  public int wieldType() {return areas[type];}

  // damage routine causes armour to deteriorate
  public int damage(int dam, int damtype) {
    switch (damtype) {
      case RPG.DT_ACID:
        dam=dam*10; break;
    } 
    int det=RPG.po(dam*(13-quality),toughnesses[type]*(quality+4));
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
  
  public int getWeight() {
  	return weights[type];
  }
  
  public int getImage() {
    return images[type]; 
  }

  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ITEMVALUE: {
        Description d=getDescription();
        for (int i=0; i<iddescriptions.length; i++) {
          if (d==iddescriptions[i]) return (Lib.qualityvalues[getQuality()]*values[i])/100;           
        }
        return values[type];
      }
      default: return super.getStat(s); 
    } 
  }
  
  public int getModifier(int s) {
    if (y==areas[type]) {
      if (s==stats[type]) return bonuses[type]+super.getModifier(s);
      int arm=(arms[type]*quality)/5;
      switch (s) {
        case RPG.ST_ARMUNARMED: 
        return arm*2;
        case RPG.ST_ARMICE: 
        case RPG.ST_ARMFIRE:
        case RPG.ST_ARMNORMAL:	
        case RPG.ST_ARMIMPACT:	
          return arm;
        case RPG.ST_ARMPIERCING:
        case RPG.ST_ARMMAGIC:
          return arm/2;
        case RPG.ST_DSKBONUS:
          return dsks[type];
        case RPG.ST_ENCUMBERANCE:
          return encs[type];
        default:
          return super.getModifier(s);
      }
    } else {
      return super.getModifier(s);
	  }
  }
  
  public Description getDescription() {return isIdentified()?iddescriptions[type]:uidescriptions[type];}
}