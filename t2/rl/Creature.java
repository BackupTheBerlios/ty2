//
// Class for all evel critters
//

package rl;

public class Creature extends Being {
  public static final int RAT=1;
  public static final int GOBLIN=2;
  public static final int MUTANT=3;
  public static final int IMPLING=4;
  public static final int PITBEAST=5;
  public static final int SKELETONWARRIOR=6;
  public static final int SNAKE=7;
  public static final int KOBOLD=8;
  public static final int SPIDER=9;
  public static final int RABBIT=10;
  public static final int HYDRA=11;
  public static final int WARLOCK=12;
  public static final int ORC=13;
  public static final int SERPENT=14;
  public static final int DOG=15;
  public static final int ASSASSIN=16;
  public static final int LION=17;
  public static final int WRAITH=18;
  public static final int GHOUL=19;
  public static final int TRIFFID=20;
  public static final int GRIFFON=21;
  public static final int SHAMAN=22;
  public static final int THIEF=23;
  public static final int SLUG=24;
  public static final int GOBLINARCHER=25;
  public static final int ORCWARRIOR=26;
  public static final int BUG=27;
  public static final int BEESWARM=28;
  public static final int WASPSWARM=29;
  public static final int FLYSWARM=30;
  public static final int BEZERKOID=31;
  public static final int BEZERKOIDLORD=32;
  public static final int COCKROACH=33;
  public static final int CROCODILE=34;
  public static final int GOBBO=35;
  public static final int GREMLIN=36;
  public static final int SNOT=37;
  public static final int SCORPION=38;
  public static final int DWARF=39;
  public static final int BROWNBEAR=40;
  public static final int BROOMSTICK=41;
  public static final int SWORDSMAN=42;
  public static final int THUG=43;
  public static final int BANDIT=44;
  public static final int GIANTSPIDER=45;
  public static final int RALKAN=46;
  public static final int GREYOOZE=47;
  public static final int WHITLING=48;
  public static final int KOBOLDWARRIOR=49;
  public static final int REDOOZE=50;
  public static final int YELLOWOOZE=51;
  public static final int PURPLEOOZE=52;
  public static final int MOUTH=53;
  public static final int GHOST=54;
  public static final int PURPLEHORROR=55;
  public static final int SNAIL=56;
  public static final int ESCARGOT=57;
  public static final int BAT=58;
  public static final int BABYRED=59;
  public static final int MINOTAUR=60;
  public static final int GOLEM=61;
  public static final int SPECTRE=62;
  public static final int MUMMY=63;
  public static final int SENTINEL=64;
  public static final int ORANGE=65;
  public static final int KESTREL=66;
  public static final int KILLERRABBIT=67;
  public static final int BANDITLEADER=68;
  
  
  
  public static final Description DESC_GOBLIN =
    new Describer("goblin","A wild goblin fighter.");
  public static final Description DESC_GOBLINARCHER =
    new Describer("goblin archer","A goblin archer.");
  public static final Description DESC_ORC =
    new Describer("orc","A nasty-looking orc.");
  public static final Description DESC_ORCWARRIOR =
    new Describer("orc warrior","A blood-thirsty orc warrior.");

  public static final Description DESC_RAT=
    new Describer("rat","A large dungeon rat.");
  public static final Description DESC_MUTANT=
    new Describer("mutant","A hideous mutated humanoid, thirsty for your blood.");
  public static final Description DESC_IMPLING=
    new Describer("impling","An angry impling.");
  public static final Description DESC_PITBEAST=
    new Describer("pit beast","A beast from the pit.");
  public static final Description DESC_SKELETONWARRIOR=
    new Describer("skeleton","A fearsome skeleton warrior.");
  public static final Description DESC_SNAKE =
    new Describer("snake","A small venomous snake.");
  public static final Description DESC_KOBOLD =
    new Describer("kobold","A nasty kobold.");
  public static final Description DESC_LARGEKOBOLD =
    new Describer("large kobold","A large kobold.");
  public static final Description DESC_KOBOLDWARRIOR =
    new Describer("kobold warrior","A vicious kobold warrior.");
  public static final Description DESC_SPIDER =
    new Describer("spider","A fearsome spider.");
  public static final Description DESC_RABBIT =
    new Describer("rabbit","A fluffy rabbit.");
  public static final Description DESC_HYDRA =
    new Describer("hydra","A ferocious hydra.");
  public static final Description DESC_WARLOCK =
    new Describer("warlock","An angry warlock.");


  public static final Description DESC_SERPENT =
    new Describer("serpent","A venomous serpent.");
  public static final Description DESC_DOG =
    new Describer("dog","A vicious wild dog.");
  public static final Description DESC_ASSASSIN =
    new Describer("assassin","A deadly assassin.");
  public static final Description DESC_LION =
    new Describer("lion","A snarling cave lion.");
  public static final Description DESC_WRAITH =
    new Describer("wraith","A wraith.");
  public static final Description DESC_GHOUL =
    new Describer("ghoul","A fearsome ghoul.");
  public static final Description DESC_TRIFFID =
    new Describer("plant","An attractive flowering plant.");
  public static final Description DESC_GRIFFON =
    new Describer("griffon","A mighty griifon.");
  public static final Description DESC_SHAMAN =
    new Describer("shaman","A goblin shaman.");
  public static final Description DESC_THIEF =
    new Describer("thief","A suspicious looking sneak thief. He's up to no good.");
  public static final Description DESC_SLUG =
    new Describer("giant slug","A noxious giant slug.");
  public static final Description DESC_BUG=
    new Describer("bug","A huge armoured bug.");
	
  public static final Description DESC_BEESWARM=
    new Describer("swarm of bees","A swarm of malevolent bees.");
  public static final Description DESC_WASPSWARM=
    new Describer("swarm of wasps","A swarm of malevolent wasps.");
  public static final Description DESC_FLYSWARM=
    new Describer("swarm of flies","A swarm of malevolent flies.");

  public static final Description DESC_BEZERKOID=
    new Describer("b'zekroi warrior","A mighty b'zekroi warrior. Oh shit.");
  public static final Description DESC_BEZERKOIDLORD=
    new Describer("b'zekroi lord","A legendary lord of the b'zekroi.");

  public static final Description DESC_COCKROACH=
    new Describer("giant cockroach","A hideous giant cockroach.");
  public static final Description DESC_CROCODILE =
    new Describer("crocodile","A huge crocodile.");
  public static final Description DESC_GOBBO =
    new Describer("goblin","A weedy goblin.");
  public static final Description DESC_GREMLIN =
    new Describer("gremlin","A nasty gremlin.");
  public static final Description DESC_SNOT =
    new Describer("snot","A hideous pile of living goo.");
  public static final Description DESC_SCORPION =
    new Describer("scorpion","A deadly-poisonous wild scorpion.");
  public static final Description DESC_DWARF =
    new Describer("dwarf","A particularly nasty little dwarf.");
  public static final Description DESC_BROWNBEAR =
    new Describer("brown bear","A large male bear with terminal toothache.");

  public static final Description DESC_BROOMSTICK =
    new Describer("broomstck","A magically animated broomstick.");

  public static final Description DESC_SWORDSMAN =
    new Describer("swordsman","A swordsman with an evil grin.");
  public static final Description DESC_THUG =
    new Describer("thug","A beefy thug. Doesn't look too bright.");
  public static final Description DESC_BANDIT =
    new Describer("bandit","A bandit.");

  public static final Description DESC_GIANTSPIDER =
    new Describer("giant spider","A monstrous giant spider. It's fangs are dripping with venom.");
  public static final Description DESC_RALKAN =
    new Describer("ralkan","A chittering ralkan-beast. It is covered with thick armoured plates.");

  public static final Description DESC_GREYOOZE =
    new Describer("grey ooze","A mass of slithering grey ooze.");
  public static final Description DESC_YELLOWOOZE =
    new Describer("yellow ooze","A mass of slimy yellow ooze.");
  public static final Description DESC_REDOOZE =
    new Describer("red ooze","A mass of gibbering red ooze.");
  public static final Description DESC_PURPLEOOZE =
    new Describer("purple ooze","A mass of grotesque purple ooze.");
  public static final Description DESC_WHITLING =
    new Describer("whitling","A pesky whitling.");
  public static final Description DESC_MOUTH =
    new Describer("mouth","A huge chomping mouth.");
  public static final Description DESC_GHOST =
    new Describer("ghost","A terrifying.");
  public static final Description DESC_PURPLEHORROR =
    new Describer("purple horror","A hideous purple horror.");
  public static final Description DESC_SNAIL =
    new Describer("snail","A large garden snail.");
  public static final Description DESC_ESCARGOT =
    new Describer("escargot","A giant edible snail.");
  public static final Description DESC_BAT =
    new Describer("bat","An angry cave bat.");
  public static final Description DESC_BABYRED =
    new Describer("baby red","A baby red dragon.");
  public static final Description DESC_MINOTAUR =
    new Describer("minotaur","A ferocious minotour.");

  public static final Description DESC_GOLEM =
    new Describer("golem","A terrifying stone golem.");
  public static final Description DESC_SPECTRE =
    new Describer("spectre","A terrifying spectre.");
  public static final Description DESC_MUMMY =
    new Describer("mummy","A mummy.");
  public static final Description DESC_SENTINEL =
    new Describer("sentinel","A sentinel crystal.");
  public static final Description DESC_ORANGE =
    new Describer("orange dragon","A hungry orange dragon.");
  public static final Description DESC_KESTREL =
    new Describer("kestrel","A kestrel.");
  public static final Description DESC_KILLERRABBIT =
    new Describer("killer rabbit","A ferocious killer rabbit.");
  public static final Description DESC_BANDITLEADER =
    new Describer("bandit leader","A ferocious bandit leader.");


  public int creaturetype;
  
  public static final int[]         types         = {0,    RAT,         GOBLIN,        MUTANT,        IMPLING,       PITBEAST,      SKELETONWARRIOR,      SNAKE,      KOBOLD,      SPIDER,      RABBIT,      HYDRA,      WARLOCK,      ORC,      SERPENT,      DOG,      ASSASSIN,      LION,      WRAITH,      GHOUL,      TRIFFID,       GRIFFON,       SHAMAN,        THIEF,       SLUG,        GOBLINARCHER,        ORCWARRIOR,      BUG,        BEESWARM,        WASPSWARM,        FLYSWARM,        BEZERKOID,       BEZERKOIDLORD,      COCKROACH,       CROCODILE,        GOBBO,        GREMLIN,        SNOT,        SCORPION,        DWARF,        BROWNBEAR,      BROOMSTICK,      SWORDSMAN,      THUG,      BANDIT,      GIANTSPIDER,      RALKAN,      GREYOOZE,      WHITLING,      KOBOLDWARRIOR,     REDOOZE,      YELLOWOOZE,      PURPLEOOZE,      MOUTH,      GHOST,      PURPLEHORROR,      SNAIL,      ESCARGOT,      BAT,      BABYRED,      MINOTAUR,      GOLEM,      SPECTRE,      MUMMY,      SENTINEL,      ORANGE,      KESTREL,      KILLERRABBIT,      BANDITLEADER};   
  public static final Description[] descriptions  = {null, DESC_RAT,    DESC_GOBLIN,   DESC_MUTANT,   DESC_IMPLING,  DESC_PITBEAST, DESC_SKELETONWARRIOR, DESC_SNAKE, DESC_KOBOLD, DESC_SPIDER, DESC_RABBIT, DESC_HYDRA, DESC_WARLOCK, DESC_ORC, DESC_SERPENT, DESC_DOG, DESC_ASSASSIN, DESC_LION, DESC_WRAITH, DESC_GHOUL, DESC_TRIFFID,  DESC_GRIFFON,  DESC_SHAMAN,   DESC_THIEF,  DESC_SLUG,   DESC_GOBLINARCHER,   DESC_ORCWARRIOR, DESC_BUG,   DESC_BEESWARM,   DESC_WASPSWARM,   DESC_FLYSWARM,   DESC_BEZERKOID,  DESC_BEZERKOIDLORD, DESC_COCKROACH,  DESC_CROCODILE,   DESC_GOBBO,   DESC_GREMLIN,   DESC_SNOT,   DESC_SCORPION,   DESC_DWARF,   DESC_BROWNBEAR, DESC_BROOMSTICK, DESC_SWORDSMAN, DESC_THUG, DESC_BANDIT, DESC_GIANTSPIDER, DESC_RALKAN, DESC_GREYOOZE, DESC_WHITLING, DESC_KOBOLDWARRIOR,DESC_REDOOZE, DESC_YELLOWOOZE, DESC_PURPLEOOZE, DESC_MOUTH, DESC_GHOST, DESC_PURPLEHORROR, DESC_SNAIL, DESC_ESCARGOT, DESC_BAT, DESC_BABYRED, DESC_MINOTAUR, DESC_GOLEM, DESC_SPECTRE, DESC_MUMMY, DESC_SENTINEL, DESC_ORANGE, DESC_KESTREL, DESC_KILLERRABBIT, DESC_BANDITLEADER};    
  public static final int[]         levels        = {0,    1,           3,             2,             5,             7,             6,                    3,          1,           3,           0,           9,          8,            6,        4,            1,        7,             8,         5,           4,          3,             10,            9,             6,           3,           4,                   7,               4,          3,               4,                2,               15,              25,                 4,               8,                2,            4,              6,           6,               6,            7,              5,               9,              5,         7,           9,                8,           7,             2,             3,                 10,           6,               13,              8,          9,          10,                0,          0,             2,        13,           12,            10,         8,            8,          7,             15,          3,            6,                 11};   
  public static final int[]         intelligent   = {0,    0,           1,             0,             1,             0,             1,                    0,          1,           0,           0,           0,          1,            1,        0,            0,        1,             0,         1,           0,          0,             0,             1,             1,           0,           1,                   1,               0,          0,               0,                0,               1,               1,                  0,               0,                1,            1,              0,           0,               1,            0,              0,               1,              1,         1,           0,                0,           0,             1,             1,                 0,            0,               0,               0,          0,          0,                 0,          0,             0,        1,            0,             0,          0,            0,          1,             1,           0,            0,                 1};   
  
  // rarity numbers, 1=very common, 10=unusual, 100=rare, 1000=mythical
  public static final int[]         rarity        = {0,    1,           1,             2,             10,            20,            5,                    2,          1,           3,           3,           20,         10,           2,        3,            2,        10,            5,         5,           3,          2,             40,            10,            2,           2,           2,                   4,               2,          3,               3,                3,               10,              20,                 6,               12,               6,            3,              1,           6,               4,            2,              3,               3,              4,         5,           3,                5,           3,             6,             3,                 3,            5,               3,               2,          1,          1,                 4,          2,             2,        1,            1,             1,          1,            1,          2,             1,           3,            6,                 1};   
  // how many in a typical pack?
  public static final int[]         numbers       = {0,    8,           5,             4,             2,             3,             5,                    6,          3,           5,           5,           1,          1,            4,        6,            5,        1,             3,         1,           3,          5,             1,             1,             2,           2,           2,                   4,               2,          3,               3,                3,               1,               1,                  6,               2,                6,            3,              1,           6,               4,            2,              3,               3,              4,         5,           3,                5,           3,             6,             3,                 3,            5,               3,               2,          1,          1,                 4,          2,             2,        1,            1,             1,          1,            1,          2,             1,           3,            6,                 1};   

  public static final int[]         zeros         = {0,    0,           0,             0,             0,             0,             0,                    0,          0,           0,           0,           0,          0,            0,        0,            0,        0,             0,         0,           0,          0,             0,             0,             0,           0,           0,                   0,               0,          0,               0,                0,               0,               0,                  0,               0,                0,            0,              0,           0,               0,            0,              0,               0,              0,         0,           0,                0,           0,             0,             0,                 0,            0,               0,               0,          0,          0,                 0,          0,             0,        0,            0,             0,          0,            0,          0,             0,           0,            0,                 0};   

  // creatures for spawning
  public static final int[][] spawn={
    {RABBIT,SNAIL,RAT,ESCARGOT},
    {DOG,RAT,BAT,KOBOLD},
    {GOBBO,MUTANT,FLYSWARM,WHITLING},
    {GOBLIN,TRIFFID,KOBOLDWARRIOR,SNAKE,SPIDER,BEESWARM,YELLOWOOZE,KESTREL},
    {DWARF,SLUG,GOBLINARCHER,BUG,GREMLIN,ORC,GHOUL,IMPLING,WASPSWARM,SERPENT,COCKROACH},
    {SNOT,THIEF,PITBEAST,ORCWARRIOR,WRAITH,SKELETONWARRIOR,SCORPION,THUG},
    {ASSASSIN,CROCODILE,BROWNBEAR,SENTINEL,MUMMY},
    {HYDRA,WARLOCK,BANDIT,GREYOOZE,SPECTRE},
    {LION,SHAMAN,RALKAN},
    {SWORDSMAN,GIANTSPIDER,GOLEM},
    {MINOTAUR,REDOOZE,PURPLEHORROR},
    {MINOTAUR, GRIFFON},
    {GRIFFON,BABYRED},
    {BABYRED,ORANGE},
    {ORANGE,BEZERKOID}
  };
  
  // forest beasties
  public static final int[][] beasties={
    {RABBIT,SNAIL,RAT,ESCARGOT},
    {DOG,RAT,BAT},
    {FLYSWARM,SNAKE,KESTREL},
    {TRIFFID,SPIDER,BEESWARM,YELLOWOOZE},
    {SLUG,BUG,GHOUL,IMPLING,SERPENT,WASPSWARM,COCKROACH},
    {SNOT,PITBEAST,SKELETONWARRIOR,SCORPION},
    {CROCODILE,BROWNBEAR},
    {HYDRA,GREYOOZE},
    {LION,RALKAN},
    {GIANTSPIDER},
    {GRIFFON,REDOOZE,PURPLEHORROR}
  };

  // (semi)intelligent races
  public static final int[][] scum={
    {GOBBO},
    {KOBOLD},
    {MUTANT,WHITLING},
    {GOBLIN,KOBOLDWARRIOR},
    {DWARF,GOBLINARCHER,GREMLIN,ORC,IMPLING},
    {THIEF,ORCWARRIOR,THUG},
    {ASSASSIN},
    {WARLOCK,BANDIT},
    {SHAMAN},
    {SWORDSMAN}
  };
  
  // Creature Generation
  //
  // For brains specify:
  //  1 Intelligent
  //  0 Monster
  // -1 Don't care
  
  public static Creature createFoe(int level) {
    return createCreature(types,level,1);
  }
  
  public static Creature createMonster(int level) {
    return createCreature(types,level,0);
  }

  public static Creature createCreature(int level) {
    return createCreature(types,level,-1);
  }
  
  public static Creature createCreature(int[] source, int level, int brains) {
    if (level<0) level=0;
    for (int tries=0; tries<100; tries++) {
      int thelevel=level;
      
      // Adjust level randomly. Not too much.
      while (RPG.d(3)==1) thelevel+=RPG.r(3)-1; 
      
      // get random creature type from source
      int i=source[RPG.r(source.length)];
      // if zero, pick from whole creature array
      if (i==0) i=RPG.d(types.length-1);  
        
      // brains check
      if ((brains>=0)&&(brains!=intelligent[i])) continue;
      
      // rarity check
      if (RPG.d(rarity[i])!=1) continue;
      
      if (levels[i]==thelevel) {
        return new Creature(types[i],thelevel);
      } else if (levels[i]<thelevel) {
        switch (RPG.d(8)) {
          case 1: case 2: case 3:
            // create levelled-up creature now and again
            return new Creature(types[i],thelevel);
          case 4:
            // occasionally just an easy critter
            return new Creature(types[i],levels[i]);
        } 
      }
    }
    return Creature.createCreature(Creature.spawn,level);
  }
  
  // creates a creature randomly for the given level
  public static Creature createCreature(int[][] source, int level) {
    while (RPG.d(2)==1) {level=level+RPG.d(2)-RPG.d(2);}
    
    level=RPG.middle(0,level,source.length-1);
    int type=source[level][RPG.r(source[level].length)];
    return new Creature(type);
  }
  
  // creates a random creature from list
  public static Creature createCreature(int[] source, int level) {
    return createCreature(source,level,-1);
  }
  
  // create a friend of same creature type
	public Creature cloneType() {
    return new Creature(creaturetype); 
  }
  
  
  // creature constructor at default level
  public Creature (int type) {
    this(type,levels[type]); 
  }
  
  // creature constructor at enhanced level
  //
  // creature is created initially at it's base level
  // extra levels are then added at end
  public Creature (int type, int level) {
    super();
    creaturetype=type;
    int baselevel=levels[type]; 
     
	  switch (type) {
	    case RAT:
        image=283+RPG.r(2);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,6,4,7,3,2,2,1,1});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_SIDE,-1);
        setStat(RPG.ST_ATTACKSPEED,160);
        setAI(CritterAI.instance);
        break;
      
      case GOBLIN:
        image=243;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,8,12,7,6,5,5,5});
        setStat(RPG.ST_PROTECTION,1);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,110);
        setAI(NastyCritterAI.instance);
        if (RPG.d(3)==1) addThing(Lib.createWeapon(2));
        if (RPG.d(10)==1) addThing(Coin.createMoney(RPG.d(10)*RPG.d(5)));
        break;
      
      case GOBLINARCHER:
        image=244;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,13,6,14,7,6,5,6,6});
        setStat(RPG.ST_PROTECTION,2);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,110);
        setAI(NastyCritterAI.instance);
        if (RPG.d(2)==1) addThing(Lib.createWeapon(level));
        {RangedWeapon it=RangedWeapon.createRangedWeapon(0);
          addThing(it);
          addThing(it.createAmmo());
        }
        break;

      case MUTANT:
        image=260+RPG.r(5);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,11,8,7,9,4,5,2,5});
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,1);
        setAI(NastyCritterAI.instance);
        break;
      
      case IMPLING:
        image=220;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,10,28,10,8,16,6,8});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,2);
        setStat(RPG.ST_ARMFIRE,20);
        setStat(RPG.ST_CASTCHANCE,30);
        setStat(RPG.ST_RECHARGE,10);
        arts.addArt(new Spell(Spell.FIREBALL));
        arts.addArt(new Spell(Spell.MISSILE));
        arts.addArt(new Spell(Spell.SPARK));
        setAI(NastyCritterAI.instance);
        break;
      
      case PITBEAST:
        image=420;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,20,25,22,2,12,1,4});
        setStat(RPG.ST_PROTECTION,6);
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;
      
      case SKELETONWARRIOR:
        image=305+RPG.r(2);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,16,16,15,0,17,0,13});
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,3);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        break;
      
      case SNAKE:
        image=286;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,12,5,9,3,4,5,2,2});
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;
      
      case KOBOLD:
        image=342;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,6,5,8,4,4,6,3,8});
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;
      
      case SPIDER:
        image=183+RPG.r(2);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,12,8,10,8,2,6,1,1});
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,140);
        setStat(RPG.ST_PROTECTION,3);
        setStat(RPG.ST_SIDE,-1);
        setAI(CritterAI.instance);
        break;
      
      case RABBIT:
        image=285;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,2,1,6,1,2,6,2,1});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        setStat(RPG.ST_SIDE,-2);
        break;
      
      case HYDRA:
        image=362;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,30,20,66,8,20,3,3});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,200);
        setStat(RPG.ST_PROTECTION,10);
        setAI(NastyCritterAI.instance);
        break;
      
      case WARLOCK:
        image=121;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,13,8,20,25,15,20,10,18});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,12);
        setStat(RPG.ST_ARMFIRE,20);
        setStat(RPG.ST_CASTCHANCE,70);
        setStat(RPG.ST_RECHARGE,30);
        setStat(RPG.ST_REGENERATE,10);
        arts.addArt(new Spell(Spell.FIREBALL));
        arts.addArt(new Spell(Spell.POISON));
        arts.addArt(new Spell(Spell.SPARK));
        arts.addArt(new Spell(Spell.FLAMECLOUD));
        setAI(NastyCritterAI.instance);
        break;
	     
      case DOG:
        image=287;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,6,5,10,6,4,10,5,3});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;
      
      case ORC:
        image=241+RPG.r(2);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,13,12,13,5,9,5,6});
        setStat(RPG.ST_PROTECTION,4);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        addThing(Coin.createMoney(RPG.d(20)*RPG.d(20)));
        addThing(Lib.createWeapon(level-1));
        break;
      
      case ORCWARRIOR:
        image=246;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,13,16,12,16,6,13,7,9});
        setStat(RPG.ST_PROTECTION,4);
        setStat(RPG.ST_ATTACKSPEED,110);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        addThing(Missile.createThrowingWeapon(0));
        addThing(StandardArmour.createArmour(0));
        break;

      case SERPENT:
        image=288;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,18,9,12,8,6,8,3,3});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;
      
      case ASSASSIN:
        image=120;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,18,10,18,12,10,13,8,13});
        setStat(RPG.ST_MOVESPEED,130);
        setStat(RPG.ST_ATTACKSPEED,130);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        addThing(Missile.createMissile(Missile.SHURIKEN));
        break;
      
      case LION:
        image=212;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,26,25,20,5,10,4,2});
        setStat(RPG.ST_PROTECTION,6);
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,180);
        setStat(RPG.ST_SIDE,-1);
        setAI(CritterAI.instance);
        break;
      
      case WRAITH:
        image=311;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,10,18,15,0,8,0,0});
        setStat(RPG.ST_PROTECTION,5);
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_RESISTICE,20);
        setStat(RPG.ST_CASTCHANCE,20);
        setStat(RPG.ST_RECHARGE,5);
        setStat(RPG.ST_REGENERATE,15);
        setStat(RPG.ST_ETHEREAL,1);
        arts.addArt(new Spell(Spell.DRAIN));
        setAI(NastyCritterAI.instance);
        break;
      
      case GHOUL:
        image=301;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,15,8,12,15,0,14,0,4});
        setStat(RPG.ST_PROTECTION,3);
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,60);
        setAI(NastyCritterAI.instance);
        break;
      
      case TRIFFID:
        image=322;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,16,7,12,15,7,12,2,1});
        setStat(RPG.ST_PROTECTION,5);
        setStat(RPG.ST_MOVESPEED,20);
        setStat(RPG.ST_ATTACKSPEED,120);
        setAI(NastyCritterAI.instance);
        break;

      case GRIFFON:
        image=361;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,66,60,60,80,6,40,3,3});
        setStat(RPG.ST_MOVESPEED,130);
        setStat(RPG.ST_ATTACKSPEED,150);
        setStat(RPG.ST_PROTECTION,15);
        setAI(NastyCritterAI.instance);
        break;

      case SHAMAN:
        image=245;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,18,12,23,25,15,30,10,18});
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,15);
        setStat(RPG.ST_ARMFIRE,25);
        setStat(RPG.ST_CASTCHANCE,70);
        setStat(RPG.ST_RECHARGE,30);
        setStat(RPG.ST_REGENERATE,20);
        arts.addArt(new Spell(Spell.FIREBALL));
        arts.addArt(new Spell(Spell.POISON));
        arts.addArt(new Spell(Spell.BLAZE));
        arts.addArt(new Spell(Spell.WEB));
        arts.addArt(new Spell(Spell.ACIDBOLT));
        arts.addArt(new Spell(Spell.HEAL));
        arts.addArt(new Spell(Spell.INFESTATION));
        arts.addArt(new Spell(Spell.SUMMON));
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level+1));
        addThing(Coin.createMoney(RPG.d(60)*RPG.d(60)));
        addThing(Lib.createMagicItem(6));
        break;

      case THIEF:
        image=102;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,22,12,18,10,10,13,8,18});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        addThing(Lib.createWeapon(level));
        addThing(Lib.createItem(level));
        addThing(Coin.createMoney(RPG.d(30)*RPG.d(30)));
        break;

      case SLUG:
        image=386;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,6,4,5,1,6,1,1});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;
      
      case BUG:
        image=181;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,13,10,8,15,2,9,1,2});
        setStat(RPG.ST_PROTECTION,15);
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case BEESWARM:
        image=281;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,7,13,5,1,3,1,1});
        setStat(RPG.ST_FATE,2);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case WASPSWARM:
        image=282;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,25,8,16,5,1,3,1,1});
        setStat(RPG.ST_FATE,3);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case FLYSWARM:
        image=280;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,17,6,10,3,1,3,1,1});
        setStat(RPG.ST_FATE,1);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case BEZERKOID:
        image=500+RPG.r(3);
        stats=new Stats(new int[] {AI.STATE_HOSTILE,140,190,120,270,100,250,100,120});
        setStat(RPG.ST_PROTECTION,50);
        setStat(RPG.ST_ATTACKSPEED,180);
        setStat(RPG.ST_MOVESPEED,130);
        setStat(RPG.ST_REGENERATE,20);
        setStat(RPG.ST_RECHARGE,20);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level+2));
        addThing(new Missile(Missile.MAGICTHROWINGKNIFE,10));
        break;

      case COCKROACH:
        image=189;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,16,10,13,10,2,9,1,2});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case CROCODILE:
        image=367;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,25,27,20,36,4,19,3,3});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,120);
        setAI(NastyCritterAI.instance);
        break;

      case GOBBO:
        image=244;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,8,6,10,7,5,5,5,5});
        setStat(RPG.ST_PROTECTION,0);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,110);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level-1));
        if (RPG.d(4)==1) addThing(Lib.createItem(getLevel()));
        break;

      // nasty critter that steals items
      case GREMLIN:
        image=340;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,17,9,20,7,5,5,5,5});
        setStat(RPG.ST_PROTECTION,0);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,130);
        setAI(NastyCritterAI.instance);
        if (RPG.d(6)==1) addThing(Lib.createItem(level+1));
        break;

      case SNOT:
        image=426;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,15,20,15,30,8,20,3,3});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,5);
        setAI(NastyCritterAI.instance);
        break;

      // poisonous but weak
      case SCORPION:
        image=188;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,32,12,10,8,3,15,2,2});
        setStat(RPG.ST_PROTECTION,5);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      // tough and persistent
      case DWARF:
        image=343;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,11,12,9,16,13,17,9,14});
        setStat(RPG.ST_PROTECTION,3);
        setStat(RPG.ST_ATTACKSPEED,110);
        setStat(RPG.ST_MOVESPEED,90);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        if (RPG.d(3)==1) addThing(Missile.createThrowingWeapon(0));
        addThing(StandardArmour.createArmour(0));
        break;

      case BROWNBEAR:
        image=201;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,34,15,40,7,28,4,2});
        setStat(RPG.ST_PROTECTION,7);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,130);
        setStat(RPG.ST_SIDE,-1);
        setAI(CritterAI.instance);
        break;

      case BROOMSTICK:
        image=441;
        stats=new Stats(new int[] {AI.STATE_NEUTRAL,10,5,10,10,5,10,0,10});
        setStat(RPG.ST_SIDE,1000);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(FriendlyCritterAI.instance);
        break;

      // fast and dangerous swordie
      case SWORDSMAN:
        image=80;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,37,26,28,26,13,21,12,14});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_ATTACKSPEED,130);
        setStat(RPG.ST_MOVESPEED,120);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createSword(6));
        addThing(Missile.createThrowingWeapon(0));
        addThing(StandardArmour.createShield(0));
        break;

      // thug
      case THUG:
        image=82;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,18,15,15,20,6,17,8,11});
        setStat(RPG.ST_PROTECTION,3);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level-1));
        break;

      // bandit
      case BANDIT:
        image=84;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,15,17,24,8,15,8,11});
        setStat(RPG.ST_PROTECTION,5);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        addThing(Missile.createThrowingWeapon(0));
        break;

      case GIANTSPIDER:
        image=187;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,20,22,25,6,17,1,6});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case RALKAN:
        image=186;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,36,25,50,4,32,2,3});
        setStat(RPG.ST_PROTECTION,30);
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case GREYOOZE:
        image=381;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,20,20,20,10,20,0,2});
        setStat(RPG.ST_PROTECTION,6);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case WHITLING:
        image=346;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,7,5,10,5,2,6,1,3});
        setStat(RPG.ST_MOVESPEED,140);
        setStat(RPG.ST_ATTACKSPEED,140);
        setAI(CritterAI.instance);
        break;

      case KOBOLDWARRIOR:
        image=348;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,12,7,12,8,5,8,4,6});
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,120);
        setAI(NastyCritterAI.instance);
        if (RPG.d(3)==1) addThing(Lib.createWeapon(level-1));
        break;

      case REDOOZE:
        image=382;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,30,30,30,30,30,0,3});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case YELLOWOOZE:
        image=383;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,10,10,10,10,10,0,1});
        setStat(RPG.ST_PROTECTION,3);
        setStat(RPG.ST_MOVESPEED,90);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case PURPLEOOZE:
        image=380;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,50,50,50,50,50,50,50,50});
        setStat(RPG.ST_PROTECTION,16);
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case MOUTH:
        image=424;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,50,60,50,80,10,50,3,3});
        setStat(RPG.ST_PROTECTION,15);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case GHOST:
        image=310;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,40,50,80,5,50,10,14});
        setStat(RPG.ST_ETHEREAL,1);
        setAI(NastyCritterAI.instance);
        break;
        
      case PURPLEHORROR:
        image=423;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,40,30,50,10,50,3,3});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,120);
        setAI(NastyCritterAI.instance);
        break;

      case SNAIL:
        image=387;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,3,3,2,3,1,3,1,1});
        setStat(RPG.ST_MOVESPEED,30);
        setStat(RPG.ST_ATTACKSPEED,30);
        setAI(CritterAI.instance);
        break;


      case ESCARGOT:
        image=387;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,3,3,2,3,1,3,1,1});
        setStat(RPG.ST_MOVESPEED,30);
        setStat(RPG.ST_ATTACKSPEED,30);
        setAI(CritterAI.instance);
        break;

      case BAT:
        image=290;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,3,20,3,2,6,2,1});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case BABYRED:
        image=460;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,50,5,100,15,40,16,20});
        setStat(RPG.ST_PROTECTION,20);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,120);
        setAI(CritterAI.instance);
        break;

      case MINOTAUR:
        image=360;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,56,50,50,100,2,40,0,6});
        setStat(RPG.ST_MOVESPEED,110);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,20);
        setAI(NastyCritterAI.instance);
        break;

      case GOLEM:
        image=405;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,50,5,100,0,40,0,0});
        setStat(RPG.ST_PROTECTION,40);
        setStat(RPG.ST_MOVESPEED,50);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case SPECTRE:
        image=312;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,30,15,24,20,0,16,0,0});
        setStat(RPG.ST_PROTECTION,6);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_RESISTICE,30);
        setStat(RPG.ST_CASTCHANCE,30);
        setStat(RPG.ST_RECHARGE,5);
        setStat(RPG.ST_REGENERATE,15);
        setStat(RPG.ST_ETHEREAL,1);
        arts.addArt(new Spell(Spell.ICEBLAST));
        arts.addArt(new Spell(Spell.DRAIN));
        setAI(NastyCritterAI.instance);
        break;

      case MUMMY:
        image=313;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,20,30,10,20,0,20,0,0});
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(NastyCritterAI.instance);
        break;

      case SENTINEL:
        image=440;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,13,8,20,25,15,20,10,18});
        setStat(RPG.ST_MOVESPEED,0);
        setStat(RPG.ST_ATTACKSPEED,0);
        setStat(RPG.ST_PROTECTION,40);
        setStat(RPG.ST_CASTCHANCE,100);
        setStat(RPG.ST_RECHARGE,60);
        setStat(RPG.ST_REGENERATE,10);
        setStat(RPG.ST_NON_DISPLACEABLE,1);
        arts.addArt(new Spell(Spell.FIREBALL));
        arts.addArt(new Spell(Spell.ACIDBLAST));
        arts.addArt(new Spell(Spell.FLAMECLOUD));
        arts.addArt(new Spell(Spell.SUMMON));
        arts.addArt(new Spell(Spell.DRAIN));
        arts.addArt(new Spell(Spell.ICEBLAST));
        setAI(NastyCritterAI.instance);
        break;

      case ORANGE:
        image=465;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,60,100,40,200,25,70,20,100});
        setStat(RPG.ST_PROTECTION,30);
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,150);
        setAI(CritterAI.instance);
        break;

      case KESTREL:
        image=291;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,4,18,3,3,6,2,1});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(CritterAI.instance);
        break;

      case KILLERRABBIT:
        image=285;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,10,10,13,20,5,16,2,1});
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_PROTECTION,6);
        setStat(RPG.ST_ATTACKSPEED,150);
        setAI(NastyCritterAI.instance);
        break;

      // fast and dangerous swordie
      case BANDITLEADER:
        image=80;
        stats=new Stats(new int[] {AI.STATE_HOSTILE,65,40,50,80,33,55,30,40});
        setStat(RPG.ST_PROTECTION,15);
        setStat(RPG.ST_ATTACKSPEED,130);
        setStat(RPG.ST_MOVESPEED,100);
        setAI(NastyCritterAI.instance);
        addThing(Lib.createWeapon(level));
        addThing(Missile.createThrowingWeapon(0));
        addThing(Missile.createThrowingWeapon(0));
        addThing(StandardArmour.createShield(0));
        addThing(StandardArmour.createArmour(0));
        break;

      
      default:
        Game.message("Creature type "+Integer.toString(type)+" not recognised.");
	      return;
        
    }
    
    // get to correct level
    setStat(RPG.ST_LEVEL,baselevel);
    while (baselevel<level) {
      gainLevel();
      baselevel++; 
    }
    
    stateInit();
    utiliseItems();
	}
	
  
  // basic stat setup
  public void stateInit() {
	  hps=getStat(RPG.ST_HPSMAX);
	  mps=getStat(RPG.ST_MPSMAX);	
	}
	
  public int hit(Thing t, int dam) {
	  Map map=getMap();
    
    switch (creaturetype) {
	  	// impling fire blast SFX
      case IMPLING:
	  	  if (dam>0) 	Game.mappanel.doSpark(t.x,t.y,0);
	  		break;
        
      // special swarm damage
      case BEESWARM: case WASPSWARM:
        if (RPG.test(getStat(RPG.ST_ST),t.getStat(RPG.ST_TG))) {
          t.damage(2,RPG.DT_SPECIAL);
          if (t==Game.hero) {
            Game.message("You are stung seriously!");  
          }
        }
        break;
      
      // Poisonous critters  
      case SNAKE: case SERPENT: case ASSASSIN: case SNOT:
        if (dam>0) {
        	t.addThing(new PoisonAttribute(2,RPG.DT_POISON,500*RPG.d(2,6),5));
          if (t==Game.hero) {
            Game.message("You feel a venomous sting!");	
          }
        }
        break;
      
      // *really* poisonous critters
      case SCORPION: case GIANTSPIDER:
        if (dam>0) {
          t.addThing(new PoisonAttribute(3,RPG.DT_POISON,1500*RPG.d(2,6),5));
          if (t==Game.hero) {
            Game.message("You feel a burning poison fill your body with pain!");  
            ((Being)t).addAttribute(new TemporaryAttribute("Weakened",RPG.d(5,2000),new int[] {RPG.ST_SK,-RPG.d(4),RPG.ST_ST,-RPG.d(4)},"You now feel less weak"));
          }
        }
        break;
      
      // weapon stealers
      case HYDRA: case SWORDSMAN: {
          if ((t instanceof Being)&&(RPG.d(3)==1)&&RPG.test(getStat(RPG.ST_ST),t.getStat(RPG.ST_ST))) {
            Thing w=((Being)t).getWielded(RPG.WT_MAINHAND);
            if (w!=null) {
              if (t==Game.hero) Game.message(getTheName()+" swipes your "+w.getName()+" from your hand!");
              map.addThing(w,t.x,t.y);
              w.displace();
            }
          }
          break;
        }        
      
      // paralysers
      case GHOUL: case TRIFFID: case SPECTRE:
        if ((t instanceof Being)&&(dam>0)&&(RPG.test(getStat(RPG.ST_WP),t.getStat(RPG.ST_WP)))) {
          Being b=(Being)t;
          b.aps=-200*getStat(RPG.ST_WP);
          if (b==Game.hero) Game.message("You are paralysed!"); 
        }
        break;
      
      case THIEF: case GREMLIN:
        if ((t instanceof Being)&&RPG.test(getStat(RPG.ST_SK),t.getStat(RPG.ST_SK))) {
          Being b=(Being)t;
          Thing[] steal=b.inv.getContents();
          if ((steal==null)||(steal.length<=0)) break;
          Thing pick=steal[RPG.r(steal.length)];
          if (pick.getQuality()<13) {
            if (b==Game.hero) Game.message(getTheName()+" steals "+pick.getAName());
            pick.remove();
            remove(); 
          } 
        }
        break;

      case SLUG: case GREYOOZE:
        if ((t==Game.hero)&&(RPG.d(6)==1)) {
          Game.message("You are covered in acidic slime!");
          t.damageAll(getLevel()-2,RPG.DT_ACID);
        }
        break;
      
      default:
        break;
	  }
    return 0;	
	}
	
	public Description getDescription() {return descriptions[creaturetype];}
	
	public int damage(int dam, int damtype) {

		int ret=super.damage(dam,damtype);

		switch (creaturetype) {
			// splitting type creatures
			// these split *after* damage
			case SLUG: case REDOOZE: case PURPLEHORROR: {
				Map map=getMap();
				if ((map!=null)&&(ret>0)&&(RPG.d(2)==1)) {
					Point p=map.findFreeSquare(x-1,y-1,x+1,y+1);
         if (p!=null) {
           if (isVisible()) Game.message(getTheName()+" splits in two!");  
           Being split=(Being)this.clone();
           split.hps=ret; // the amount cut off
           map.addThing(split,x-1,y-1,x+1,y+1);
         }
       }
				break;
			} 
		}
		
		return ret;
	}
	
	
  
  public void die() {
    Map m=getMap();
    Hero h=Game.hero;
    
    if (m==null) return;
	  switch (creaturetype) {
	    case WARLOCK: case GOBLIN:
        if (m.getObject(x,y,Decoration.class)==null) {
          m.addThing(new Decoration(Decoration.DEC_GREENBLOOD),x,y);
        }
        break;
      case IMPLING:
        if (m.getObject(x,y,Fire.class)==null) {
        	m.addThing(new Fire(RPG.d(6)),x,y);
        }
	      break;
        
      default:
        if (creaturetype==ESCARGOT) m.addThing(Food.createFood(Food.ESCARGOT),x,y);
        
        if (m.getObject(x,y,Decoration.class)==null) {
          m.addThing(new Decoration(Decoration.DEC_REDBLOOD),x,y);
        }
        break;
	  }	
    super.die();
	}
	
  // get unarmed weapon object
  // pretty important for most monsters
  public Weapon getUnarmedWeapon() {
    int unarmed=getStat(RPG.ST_UNARMED);
    
    if (unarmed<=0) unarmed=0;
    
    return Lib.WEAPON_MAUL;
  }

  
  
  public int getStat(int s) {
    switch (s) {
      // provide default critter stats      
      case RPG.ST_REGENERATE:
        if (getBaseStat(s)==0) {return 3+super.getStat(s);}
        break;
      case RPG.ST_RECHARGE:
        if (getBaseStat(s)==0) {return 3+super.getStat(s);}
        break;
    }
    return super.getStat(s);
  } 
  
	public void action(int time) {
    super.action(time);
    
    Map m=getMap();
    if (m==null) return;
    
    switch (creaturetype) {
      case SPIDER:
        if (RPG.po(((double)time)/2000)>0) {
          if ((m!=null)&&(m.getObject(x,y,Web.class)==null)) {
            m.addThing(new Web(25),x,y);
          }
          aps=-1;
          return;
        }
        break;

      // broomstick eats any items lying around
      case BROOMSTICK: {
        Thing[] stuff=m.getThings(x,y);
        for (int i=0; i<stuff.length; i++) {
          if ((stuff[i] instanceof Decoration)||(stuff[i] instanceof Item)) {
            stuff[i].remove();
          }
        }
        break;
      }
        
      default: 
    }
	}
}