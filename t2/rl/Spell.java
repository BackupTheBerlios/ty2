//
// Implementation of all Spells and Special effects in Tyrant
//
//

package rl;

// Root class for all spells and special abilities
public class Spell extends Art {
  
  // Target flags
  // These indicate the target type of the specified spell
  public static final int TARGET_NONE=0;
  public static final int TARGET_SELF=1;
  public static final int TARGET_LOCATION=2;
  public static final int TARGET_DIRECTION=3;
  public static final int TARGET_ITEM=4;
  
  // these tags notify the AI of the type of spell
  // AI won't try to use any SPELL_USELESS
  // Should try SPELL_DEFENCE if wounded
  // Will shoot random SPELL_OFFENCE at enemy
  // Won't generally use any SPELL_ENCHANT
  public static final int SPELL_USELESS=0;
  public static final int SPELL_OFFENCE=1;
  public static final int SPELL_DEFENCE=2;
  public static final int SPELL_TACTICAL=3;
  public static final int SPELL_ENCHANT=4;
  public static final int SPELL_SUMMON=5;
  
  // The magical order to which the spell belongs
  // This primarily affects who can learn the spell
  public static final int ORDER_NONE=0;
  public static final int ORDER_TRUE=1;
  public static final int ORDER_BATTLE=2;
  public static final int ORDER_GOOD=3;
  public static final int ORDER_NEUTRAL=4;
  public static final int ORDER_EVIL=5;
  public static final int ORDER_GOBLIN=6;
  public static final int ORDER_HEALING=7;
  public static final int ORDER_ALCHEMY=8;
  public static final int ORDER_TRICKERY=9;
  public static final int ORDER_RUNECASTING=10;
  public static final int ORDER_ANCIENT=11;  // inaccessible to heroes!
	public static final int ORDER_BLACKMAGIC=12;
  
  public static final int[] orderskills={
            0,
            RPG.ST_TRUEMAGIC,
            RPG.ST_BATTLEMAGIC,
            RPG.ST_HOLYMAGIC,
            RPG.ST_HOLYMAGIC,
            RPG.ST_HOLYMAGIC,
            RPG.ST_GOBLINMAGIC,
            RPG.ST_ALCHEMY,
            0,
            RPG.ST_RUNELORE,
            RPG.ST_ANCIENTMAGIC,
            RPG.ST_BLACKMAGIC
              };
  
  public static final int MISSILE=1;
  public static final int FORCEBOLT=2;
  public static final int POISONBOLT=3;
  public static final int ACIDBOLT=4;
  public static final int ICEBOLT=5;
  public static final int FIREDART=6;
  public static final int THUNDERBOLT=7;
  public static final int DEATHRAY=8;
  public static final int VAPOURIZER=9;
  public static final int SPARK=10;
  public static final int FLAME=11;
  public static final int BLAST=12;
  public static final int BLAZE=13;
  public static final int FIREBALL=14;
  public static final int FIREPATH=15;
  public static final int WEB=16;
  public static final int POISONCLOUD=17;
  public static final int DEVASTATE=18;
  public static final int POISON=19;
  public static final int LIGHTHEAL=20;
  public static final int HEAL=21;
  public static final int STRONGHEAL=22;
  public static final int POWERHEAL=23;
  public static final int TOTALHEALING=24;
  public static final int CUREPOISON=25;
  public static final int HASTE=26;
  public static final int SLOW=27;
  public static final int SUICIDE=28;
  public static final int IDENTIFY=29;
  public static final int REMOVECURSE=30;
  public static final int BLESS=31;
  public static final int TELEPORT=32;
  public static final int TELEPORTOTHER=33;
  public static final int TUNNEL=34;
  public static final int FLAMECLOUD=35;
  public static final int ICEBLAST=36;
  public static final int ACIDBLAST=37;
  public static final int RECHARGE=38;
  public static final int SUMMON=39;
  public static final int BROOMSTICK=40;
  public static final int INFESTATION=41;
  public static final int ARMOUR=42;
  public static final int RUNETRAP=43;
  public static final int DRAIN=44;
  public static final int REPAIR=45;
  public static final int ENCHANT=46;
  public static final int BECOMEETHEREAL=47;
  public static final int LAYRUNE=48;
  public static final int CONFUSE=49;
  public static final int TRUEVIEW=50;

  public static final String[] names         = {"Magic Spell", "Magic Missile", "Force Bolt",    "Poison Bolt",   "Acid Bolt",     "Ice Bolt",      "Firedart",      "Thunderbolt",   "Death Ray",     "Vapourizer",     "Spark",          "Flame",          "Blast",          "Blaze",          "Fireball",          "Firepath",          "Web",            "Poison Cloud",       "Devastate",         "Poison",           "Light Heal",          "Heal",           "Strong Heal",    "Power Heal",     "Super Heal",        "Cure Poison",      "Haste",          "Slow",           "Suicide",        "Identify",       "Remove Curse",   "Bless",          "Teleport",       "Teleport Other",   "Tunnel",            "Flame Cloud",    "Ice Blast",      "Acid Blast",     "Recharge",       "Summon",         "Broomstick",     "Infestation",    "Armour",         "Rune of Fire",    "Drain",          "Repair",          "Enchant",         "Become Ethereal", "Lay Rune",       "Confuse",        "Trueview"}; 
  public static final int[]    levels        = {0,             1,               1,               2,               4,               4,               3,               7,               10,              13,               1,                2,                5,                8,                4,                   9,                   4,                6,                    15,                  3,                  1,                     3,                5,                7,                10,                  5,                  3,                4,                13,               6,                2,                2,                6,                7,                  2,                   4,                5,                7,                8,                5,                3,                5,                4,                2,                 3,                5,                 10,                18,                2,                8,                10,               2,                2,                2,                2,                2,                0}; 
  public static final int[]    costs         = {0,             2,               2,               4,               6,               6,               5,               20,              50,              100,              1,                1,                20,               25,               6,                   30,                  5,                7,                    100,                 2,                  2,                     6,                12,               20,               40,                  12,                 2,                3,                100,              40,               100,              50,               60,               80,                 1,                   8,                12,               10,               30,               20,               6,                5,                8,                1,                 3,                20,                50,                30,                1,                10,               1,                1,                1,                1,                1,                1,                1,                0}; 

  public static final int[]    rarity        = {0,             1,               1,               2,               2,               3,               1,               2,               10,              10,               1,                1,                3,                5,                1,                   4,                   1,                2,                    10,                  3,                  1,                     1,                1,                1,                1,                   1,                  1,                1,                8,                1,                3,                1,                1,                1,                  1,                   1,                1,                1,                1,                1,                1,                1,                1,                1,                 1,                1,                 1,                 10,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1,                1}; 

  public static final int[]    bolts         = {0,             0,               20,              40,              20,              20,              0,               100,             80,              40,               60,               1,                2,                2,                1,                   2,                   42,               45,                   2,                   40,                 20,                    21,               22,               23,               24,                  20,                 20,               40,               0,                0,                0,                0,                0,                0,                  0,                   1,                21,               41,               0,                0,                0,                0,                0,                0,                 80,               0,                 0,                 0,                 0,                0,                10,               0,                0,                0,                0,                0,                0  }; 
  public static final int[]    orders        = {ORDER_NONE,    ORDER_TRUE,      ORDER_BATTLE,    ORDER_TRUE,      ORDER_TRUE,      ORDER_TRUE,      ORDER_TRUE,      ORDER_TRUE,      ORDER_TRUE,      ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,          ORDER_TRUE,          ORDER_GOBLIN,     ORDER_GOBLIN,         ORDER_TRUE,          ORDER_EVIL,         ORDER_GOOD,            ORDER_GOOD,       ORDER_GOOD,       ORDER_GOOD,       ORDER_TRUE,          ORDER_TRUE,         ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_NEUTRAL,    ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,         ORDER_TRUE,          ORDER_TRUE,       ORDER_TRUE,       ORDER_EVIL,       ORDER_ANCIENT,    ORDER_TRUE,       ORDER_TRUE,       ORDER_GOBLIN,     ORDER_TRUE,       ORDER_RUNECASTING, ORDER_EVIL,       ORDER_RUNECASTING, ORDER_ANCIENT,     ORDER_NEUTRAL,     ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE,       ORDER_TRUE      }; 
  public static final int[]    targets       = {TARGET_NONE,   TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION, TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,     TARGET_LOCATION,     TARGET_LOCATION,  TARGET_LOCATION,      TARGET_LOCATION,     TARGET_LOCATION,    TARGET_SELF,           TARGET_SELF,      TARGET_SELF,      TARGET_SELF,      TARGET_SELF,         TARGET_LOCATION,    TARGET_LOCATION,  TARGET_LOCATION,  TARGET_SELF,      TARGET_ITEM,      TARGET_ITEM,      TARGET_ITEM,      TARGET_SELF,      TARGET_LOCATION,    TARGET_LOCATION,     TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_ITEM,      TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_SELF,       TARGET_LOCATION,  TARGET_ITEM,       TARGET_ITEM,       TARGET_SELF,       TARGET_SELF,      TARGET_LOCATION,  TARGET_SELF,      TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,  TARGET_LOCATION,     TARGET_LOCATION,     TARGET_LOCATION}; 
  public static final int[]    radii         = {0,             0,               0,               0,               0,               0,               0,               0,               0,               0,                0,                0,                8,                8,                2,                   2,                   8,                2,                    10,                  0,                  0,                     0,                0,                0,                0,                   0,                  0,                0,                0,                0,                0,                0,                0,                0,                  0,                   6,                2,                2,                0,                0,                0,                0,                0,                0,                 0,                0,                 0,                 0,                 0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 
  public static final int[]    damages       = {0,             RPG.DT_NORMAL,   RPG.DT_IMPACT,   RPG.DT_POISON,   RPG.DT_ACID,     RPG.DT_ICE,      RPG.DT_FIRE,     RPG.DT_MAGIC,    RPG.DT_CHILL,    RPG.DT_SPECIAL,   RPG.DT_MAGIC,     RPG.DT_FIRE,      RPG.DT_NORMAL,    RPG.DT_FIRE,      RPG.DT_FIRE,         RPG.DT_FIRE,         0,                RPG.DT_POISON,        RPG.DT_NORMAL,       RPG.DT_POISON,      0,                     0,                0,                0,                0,                   0,                  0,                0,                RPG.DT_SPECIAL,   0,                0,                0,                0,                0,                  0,                   RPG.DT_FIRE,      RPG.DT_ICE,       RPG.DT_ACID,      0,                0,                0,                0,                0,                0,                 RPG.DT_DRAIN,     0,                 0,                 0,                 0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 
  public static final int[]    strengthbonus = {0,             5,               3,               4,               3,               5,               12,              20,              20,              100,              0,                4,                15,               10,               6,                   20,                  0,                3,                    50,                  3,                  2,                     6,                12,               20,               30,                  0,                  0,                0,                0,                0,                0,                0,                0,                0,                  0,                   8,                6,                4,                0,                0,                0,                0,                5,                0,                 4,                0,                 0,                 0,                 0,                0,                1,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 
  public static final int[]    strengthmuls  = {0,             25,              25,              30,              30,              30,              50,              60,              80,              60,               25,               0,                15,               10,               20,                  30,                  0,                0,                    30,                  0,                  0,                     0,                0,                0,                0,                   0,                  0,                0,                0,                0,                0,                0,                0,                0,                  0,                   0,                0,                0,                0,                0,                0,                0,                0,                0,                 0,                0,                 0,                 0,                 0,                0,                10,               0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 
  public static final int[]    durations     = {0,             0,               0,               0,               0,               0,               0,               0,               0,               0,                0,                0,                0,                0,                0,                   0,                   0,                6000,                 0,                   5000,               0,                     0,                0,                0,                0,                   0,                  2000,             3000,             0,                0,                0,                0,                0,                0,                  0,                   0,                0,                0,                0,                0,                0,                0,                5000,             0,                 0,                0,                 0,                 5000,              0,                2000,             10000,            0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 
  public static final int[]    usages        = {SPELL_USELESS, SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,   SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,       SPELL_OFFENCE,       SPELL_OFFENCE,    SPELL_OFFENCE,        SPELL_OFFENCE,       SPELL_OFFENCE,      SPELL_DEFENCE,         SPELL_DEFENCE,    SPELL_DEFENCE,    SPELL_DEFENCE,    SPELL_DEFENCE,       SPELL_DEFENCE,      SPELL_DEFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_ENCHANT,    SPELL_ENCHANT,    SPELL_ENCHANT,    SPELL_DEFENCE,    SPELL_OFFENCE,      SPELL_TACTICAL,      SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_ENCHANT,    SPELL_SUMMON,    SPELL_SUMMON,      SPELL_SUMMON,     SPELL_DEFENCE,    SPELL_DEFENCE,     SPELL_OFFENCE,    SPELL_ENCHANT,     SPELL_ENCHANT,     SPELL_DEFENCE,     SPELL_DEFENCE,    SPELL_OFFENCE,    SPELL_TACTICAL,   SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,    SPELL_OFFENCE,       SPELL_OFFENCE,       TARGET_LOCATION}; 
  public static final int[]    zeroes        = {0,             0,               0,               0,               0,               0,               0,               0,               0,               0,                0,                0,                0,                0,                0,                   0,                   0,                0,                    0,                   0,                  0,                     0,                0,                0,                0,                   0,                  0,                0,                0,                0,                0,                0,                0,                0,                  0,                   0,                0,                0,                0,                0,                0,                0,                0,                0,                 0,                0,                 0,                 0,                 0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0,                0}; 

  public static final int[] levelpowers     = {9,10,11,12,14,16,18,20,22,25,28,32,36,40,44,50,56,64,72,80,88,100,112,128,144,160,176,200,224,256,288,320};
  public static final int[] powermodifiers  = {25,50,75,90,100,110,120,125,130,135,140,144,147,150,153,156,158,160,162,163,164,165,166,168,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200};
      
  public int type;
  public int level=0; 
  public int cost=1;
  public int power=0;

    
  public Spell(int t) {
    this(t,levels[t]);
  }
  
  public Spell(int t, int l) {
    type=t;
    level=l;
    cost=costs[type];
    power=defaultLevelPower(level); 
  }
  
  public static int numSpells() {
    return names.length; 
  }
  
  public static int randomSpell() {
    return randomSpell(1000); 
  }
  
  public static int randomOffensiveSpell(int maxlevel) {
    if (maxlevel<1) maxlevel=1;
    
    for (int i=0; i<200; i++) {
      int r=RPG.d(numSpells()-1);
      if ((levels[r]<=maxlevel)&&(usages[r]==SPELL_OFFENCE)&&(RPG.d(rarity[r])==1)) return r; 
    } 
    return MISSILE;
  }

  public static int randomSpell(int maxlevel) {
    if (maxlevel<1) maxlevel=1;
    
    for (int i=0; i<100; i++) {
      int r=RPG.d(numSpells()-1);
      if ((levels[r]<=maxlevel)&&(RPG.d(rarity[r])==1)) return r; 
    } 
    return MISSILE;
  }

  public static int randomSpell(int maxlevel, int order) {
    if (maxlevel<1) maxlevel=1;
    
    for (int i=0; i<100; i++) {
      int r=RPG.d(numSpells()-1);
      if ((orders[r]==order)&&(levels[r]<=maxlevel)&&(RPG.d(rarity[r])==1)) return r; 
    } 
    return MISSILE;
  }



  // default power for each spell level
  public int defaultLevelPower(int lev) {
    if (lev<0) return ((defaultLevelPower(lev+24)+8)/16);
    if (lev>=levelpowers.length) return ((defaultLevelPower(lev-12)*4));
    return (levelpowers[lev]*levelpowers[lev])/5; 
  }

  public Spell() {
    this(1); 
  }

  public String getName() {return names[type];}
	
  public int getLevel() {return level;}
  
  // difficulty level for spell
  // character must have at least this ability level to cast
  public int getDifficulty() {
    return levels[type];	
  }
	
  public int castTime(Thing caster) {
    return 3000/(10+getAbility(caster)-getDifficulty()); 
  }
  
  // power modifier
  // should only call with a valid caster
  // should make this dependant on level / practice?
  public int getPowerModifier(Thing caster) {
    int skillnumber=orderskills[orders[type]];
    if (skillnumber>0) {
      return 50+25*caster.getStat(skillnumber);
    } else {
      return 100; 
    }
  } 
  
  // the power of the spell, indication of caster ability
  // roughly in line with caster IN, with modifications
  public int getPower(Thing caster) {
    if (caster==null) {
      return power>=0 ? power : defaultLevelPower(getLevel()); 
    } else {
      // return intelligence of the caster
      // this modifies spell power
      // really need to modify for spell learning / magic skillz
      int pow = caster.getStat(RPG.ST_IN);
      pow=(pow*getPowerModifier(caster))/100;
      return pow;
    } 
  }
  
  public int getStrength(Thing caster) {
    return (strengthmuls[type]*getPower(caster))/100+strengthbonus[type]; 
  }
  
  public int getCost(Thing caster) {
    if (caster==null) {
      return cost; 
    } else {
      return cost;
    }  
  }
  
	// make caster cast spell automatically
  // make most effective use of spell
  // returns true if spell cast
  public boolean castAI(Being caster) {
    // this cheat allows creatures to cast lots
    cost=3;
    
    // bail out if we have insufficient energy to cast
    if (cost>caster.mps) return false;
    
    Map map=caster.getMap();

    if (usages[type]==SPELL_OFFENCE) {
      
      if (map==null) return false;
      
      // find enemy to shoot at
      Thing p=map.findNearestFoe(caster);
      
      if ((p instanceof Mobile)&&caster.canSee(p)) {
        // check if friend is too close!!
        // if not, then cast at target as planned
        Thing f=map.findNearestFoe((Mobile)p);
        if ((f==null)||(RPG.dist(f.x,f.y,p.x,p.y)>radii[type])) {
          castAtLocation(caster,map,p.x,p.y);
          caster.aps-=castTime(caster);
          caster.mps-=cost;
          return true;
        }
      } 
      return false;
      
    } else if ((usages[type]==SPELL_DEFENCE)&&(caster.hps<=caster.getStat(RPG.ST_HPSMAX))) {
      
      // we're wounded, so cast a defensive spell
      castAtSelf(caster);
      caster.aps-=castTime(caster);
      caster.mps-=cost;
      return true;
    
    } else if ((usages[type]==SPELL_SUMMON)&&caster.isVisible()) {
      
      // cast summon spell between caster and nearest foe
      Thing f=map.findNearestFoe(caster);
      if (f!=null) {
        
        int tx=(caster.x+f.x)/2;
        int ty=(caster.y+f.y)/2;
        
        if (!map.isBlocked(tx,ty)) {
          Game.message(caster.getTheName()+" shouts words of summoning!");
          castAtLocation(caster,map,tx,ty);
          caster.aps-=castTime(caster);
          caster.mps-=cost;
          return true;
        }

      }
    }
    return false;
  }
  
  // return the maximum skill level which the caster can achieve
  public int getAbility(Thing caster) {
		int limit=0;
	  switch (getOrder()) {
	    case ORDER_GOOD: case ORDER_NEUTRAL: case ORDER_EVIL: case ORDER_HEALING:	
	  		limit=caster.getStat(RPG.ST_PRIEST); break;
	  	case ORDER_ALCHEMY:
	  		limit=caster.getStat(RPG.ST_SCHOLAR);
		  case ORDER_BATTLE: 	
	  		limit=caster.getStat(RPG.ST_FIGHTER); break;
	  	default: 
	  		limit=caster.getStat(RPG.ST_MAGE); break;
	  }	
    return limit;
	}
	
	// train the caster in the given spell
  public void train(Being caster) {
	  int cl=caster.getStat(RPG.ST_LEVEL);
    if (RPG.d(cl)<=level) return;
    
	  int learning=caster.getStat(RPG.ST_IN)*caster.getStat(RPG.ST_IN);
	  int difficulty=level*level*cost;
	  
	  if (RPG.test(learning,difficulty)) {
	    level=level+1;
	  }
	} 
	
	public int getOrder() {return orders[type];}

  public int getUsage() {return usages[type];}

  public String castMessage() {return "You cast "+getName();}
	
	public int getTarget() { return targets[type];	}
	
	public void castAtSelf(Thing caster) {
    if (caster==null) return;
	  if (getTarget()==TARGET_LOCATION) {
      castAtLocation(caster,caster.getMap(),caster.x,caster.y);
      return; 
    }
    
    Hero h=Game.hero;
    Map map=caster.getMap();
    
    switch (type) {
      case LIGHTHEAL: case HEAL: case STRONGHEAL: case POWERHEAL: case TOTALHEALING: {
        Being t=(Being)caster;
        int max=t.getStat(RPG.ST_HPSMAX);

        // calculate heal amount (hps)
        int heal=RPG.a((cost*(10+level-getDifficulty()))/10);
        if (type==TOTALHEALING) heal=1000;
        heal=RPG.middle(0,heal,max-t.hps);
        
        t.hps+=heal;
        if (t==h) {
          if (heal>0) {
            Game.message("You feel much better");
          }  else {
            Game.message("The healing has no effect");
          }
        }
        break;
      }
      
      case TELEPORT: {
        Point p=map.findFreeSquare();
        if (p!=null) {
          if (caster==h) {
            Game.message("You teleport...");
          } else if (caster.isVisible()) {
            Game.message(caster.getTheName()+" teleports away!"); 
          }  
          caster.moveTo(map,p.x,p.y);
        } else {
        }
        break;
      }
      
      case RUNETRAP: {
        if (caster==Game.hero) Game.message("You weave a cunning RuneTrap");  
        map.addThing(new RuneTrap(caster,Spell.FIREBALL),caster.x,caster.y);
        break;        
      }
        
      case LAYRUNE: {
        if (caster==Game.hero) {
          SpellScreen ls=new SpellScreen("Select spell to scribe in this location:",h.arts.getArts(Spell.class));
          Game.questapp.switchOtherScreen(ls);
          Spell s=ls.getSpell();
          Game.questapp.switchBack(ls);
          
          if (s!=null) {
            Game.message("You carefully scribe the runes of "+s.getName());  
            map.addThing(new RuneTrap(caster,s.type),caster.x,caster.y);
          }
        }
        break;        
      }

      case BECOMEETHEREAL: {
        if (caster==Game.hero) Game.message("You feel a sudden sense of lightness");  
        caster.addAttribute(new TemporaryAttribute("Ethereal",durations[type],RPG.ST_ETHEREAL,1));
      }
      
      case TRUEVIEW: {
        if (caster==Game.hero) Game.message("Your vision sharpens dramatically");
        caster.addAttribute(new TemporaryAttribute("Trueview",durations[type],RPG.ST_TRUEVIEW,getStrength(caster))); break;
      }
        
      default:
        
    }
  }
	
	public void castAtLocation(Thing caster, Map map, int tx, int ty) {
    if (getTarget()!=TARGET_LOCATION) {
      return; 
    }

	  // special effects for location targetted spells
    if (map.isVisible(tx,ty)) {
      // spell projectile
      if (caster!=null) {
        Game.mappanel.doShot(caster.x,caster.y,tx,ty,bolts[type],0.5);
      }
      
      // explosion
      switch (type) {
        case FIREBALL: case BLAST: case BLAZE: case FLAMECLOUD: case FIREPATH: case WEB: case DEVASTATE: case POISONCLOUD: case ACIDBLAST: {
          int rr=(int)Math.sqrt(radii[type]);    
          Game.mappanel.doExplosion(tx,ty,bolts[type]/20,rr);
          break;
        }
        
        default: Game.mappanel.doSpark(tx,ty,bolts[type]/20);

      }
    }
    
    // en route effects
    if (caster!=null) {
      double sx=caster.x+0.5; double sy=caster.y+0.5;
      int px=caster.x; int py=caster.y;
      double d=Math.sqrt((px-tx)*(px-tx)+(py-ty)*(py-ty));
      while((px!=tx)||(py!=ty)) {
        while((px==(int)sx)&&(py==(int)sy)) {
          sx+=0.5*(tx-caster.x)/d;
          sy+=0.5*(ty-caster.y)/d; 
        }
        px=(int)sx;
        py=(int)sy;
        switch (type) {
          case FIREPATH: {
            Fire.createFire(map,px,py,getStrength(caster));
          }  
        }
      } 
    }
        
    // ball spell damage effects
    if ((radii[type]>0)) {
      affectArea(caster,map,tx,ty,radii[type]);
      return; 
    }
    
    affectLocation(caster,map,tx,ty);
  }

	public void castAtObject(Thing caster, Thing target) {
	  Hero h=Game.hero;
    if (caster!=h) return;
    
    switch (type) {
      case IDENTIFY: {
        if (target instanceof Item) {
          Item i=(Item)target;
      
          if (i.identify(level+RPG.d(20))) {
            Game.message("You identify: "+i.getFullName());
          } else {
            Game.message("You are unable to identify "+i.getTheName());
          }      
        }
        return;
      }  
        
      case RECHARGE: {
        if (target instanceof Wand) {
          Wand w=(Wand)target;
          w.recharge(cost/7);
          return;
        } else {
          Game.message("Your attempt to recharge "+target.getTheName()+" does not seem to work");  
        }
        return;
      }  
      
      case BLESS: {
        if (target instanceof Item) {
          Item i=(Item)target;
          i.setBlessed(true);
          Game.message(target.getTheName()+" gives a brief golden glow");  
          return;
        } else {
          Game.message("Your attempt to bless "+target.getTheName()+" fails");  
        }
        return;
      }  
      
      case REPAIR: {
        if (target instanceof Item) {
          Item i=(Item) target;
          int q=i.getQuality();
          if ((q>0)&&(q<7)&&RPG.test(caster.getStat(RPG.ST_IN)*(7-q),q*q*q)) {
            Game.message(i.getTheName()+" is briefly lit by a brilliant orange glow");
            i.quality++;
          } else {
            Game.message(i.getTheName()+" glows orange for a second"); 
          } 
        } else {
          Game.message("Spell failed"); 
        } 
        return;
      }
      
      case ENCHANT: {
        if (target instanceof Item) {
          Item i=(Item) target;
          int q=i.getQuality();
          if ((q>0)&&(q<12)&&RPG.test(caster.getStat(RPG.ST_IN)*level*level*level,i.getStat(RPG.ST_ITEMVALUE))) {
            Game.message(i.getTheName()+" is lit by a brilliant white light");
            i.quality++;
          } else {
            Game.message(i.getTheName()+" lets off a few feeble sparks"); 
          } 
        } else {
          Game.message("Spell failed"); 
        } 
        return;
      }
      
      case REMOVECURSE: {
        if (target instanceof Item) {
          Item i=(Item)target;
      
          if ((i.flags&Item.ITEM_CURSED)==0) {
            Game.message("The spell fails - it appears there was no curse to remove");
          } else {
            Game.message("There is a shower of white sparks as the curse is lifted");
            i.flags&= ~Item.ITEM_CURSED;
          }      
        }
        return;
      }  
    }

    Game.message(getName()+" spell not working!");
  }

	public void castInDirection(Thing caster, int dx, int dy) {
	}
	
  // apply spell affect over map area
  // calls affectArea for each individual square
  public void affectArea(Thing caster, Map map, int tx, int ty, int r2) {
    int d=0;
    for (d=0; d*d<=r2;d++);
    d=d-1;
    for (int dx=-d; dx<=d; dx++) for (int dy=-d; dy<=d; dy++) {
      if (((dx*dx)+(dy*dy))<=r2) {
        affectLocation(caster,map,tx+dx,ty+dy);
      }
    }
  }
  
  // return true if being is able to learn this art
  public boolean canLearn(Being b) {
    return RPG.test(b.getStat(RPG.ST_LEVEL),levels[type]); 
  }

  
  // apply spell affect to individual square
  public void affectLocation(Thing caster, Map map, int tx, int ty) {

    switch(type) {
      // Webbing spells  
      case WEB: {
        if ((RPG.d(2)==1)&&((map.getTile(tx,ty)&Tile.TF_BLOCKED)==0)) {
          map.addThing(new Web(30),tx,ty);
        }
        break;
      }
      
      // standard creature summoning
      case SUMMON: {
        Creature c=Lib.createMonster(level); 
        if (caster!=null) c.makeFollower((Being)caster);
        c.setStat(RPG.ST_REGENERATE,0);
        map.addThing(c,tx,ty);
        break;
      } 
      
      // infestation summoning
      case INFESTATION: {
        Creature c=Lib.createMonster(level-3); 
        c.setStat(RPG.ST_SIDE,caster.getStat(RPG.ST_SIDE));
        c.setStat(RPG.ST_STATE,AI.STATE_HOSTILE);
        c.setStat(RPG.ST_REGENERATE,0);
        for (int i=0; i<10; i++) {
          int px=tx+RPG.d(5)-2; int py=ty+RPG.d(5)-2;
          if (!map.isBlocked(px,py)) {
            map.addThing((Thing)c.clone(),px,py); 
          } 
        } 
        break;         
      }

      // broomstick summoning
      case BROOMSTICK: {
        Creature c=new Creature(Creature.BROOMSTICK); 
        c.setStat(RPG.ST_REGENERATE,0);
        map.addThing(c,tx,ty);
        break;
      } 

      // tunneling
      case TUNNEL: {
        map.setTile(tx,ty,map.floortile);
        break; 
      }
      
      // not a location affect spell, so we affect individual items
      default: {
        if (radii[type]>0) {
          // affect everything in the square
          Thing[] stuff=map.getThings(tx,ty); 
          for (int i=0; i<stuff.length; i++) {
            Thing t=stuff[i];
            affectThing(caster,t);
          }
        } else {
          // affect the primary targey only
          Thing t=map.getMobile(tx,ty);  
          if (t==null) t=map.getObjects(tx,ty);  
          if (t!=null) {
            affectThing(caster,t); 
          }
        } 
      }  
    } 
    
    // secondary effects
    switch (type) {
      // flame filling spells
      case FLAME: case FLAMECLOUD: case BLAZE: case FIREPATH: {
        if (((type==FLAME)||(RPG.d(2)==1))&&((map.getTile(tx,ty)&Tile.TF_BLOCKED)==0)) {
          Fire.createFire(map,tx,ty,getStrength(caster));
        }
        break;
      }
    }
  }
  
  public void affectThing(Thing caster, Thing t) {  
    Hero h=Game.hero;

    // special tretment for offensive spells
    if (getUsage()==SPELL_OFFENCE) {
      if (t instanceof Mobile) {
        Mobile mob=(Mobile)t;
        if (mob!=null) mob.notify(AI.EVENT_ATTACKED,mob.getStat(RPG.ST_SIDE),caster);       
      }
      
      // magic save
      if (RPG.test(t.getStat(RPG.ST_ANTIMAGIC),getPower(caster))) {
        if (t==h) Game.message("You resist the "+getName()+" spell");
        return; 
      } else {
        if (t==h) Game.message("You are hit by the "+getName()+" spell"); 
      }
    } 

    switch (type) {
      // poison effect spells
      case POISON: case POISONCLOUD: {
       if (t instanceof Mobile) {
          t.addThing(new PoisonAttribute(getStrength(caster),damages[type],durations[type],level));
        }
        break;
      }
       
      
      // single hit missile spells
      case MISSILE: case DRAIN: case FIREDART: case POISONBOLT: case SPARK: case THUNDERBOLT: case ACIDBOLT: case VAPOURIZER: case ICEBOLT: {
        t.damageLocation(getStrength(caster),damages[type]); 
        break; 
      }
        
      // damage spells
      case FIREPATH: case BLAST: case FORCEBOLT: case FIREBALL: case BLAZE: case ICEBLAST: case ACIDBLAST: case DEVASTATE: case SUICIDE: {
        t.damage(RPG.a(getStrength(caster)),damages[type]);
        break;
      }  
      
      // standard attribute alteration spells
      case HASTE: case SLOW: case ARMOUR: case CONFUSE: {
        if (!(t instanceof Being)) break;
        
        Being b=(Being)t;
        switch (type) {
          case HASTE: b.addAttribute(new TemporaryAttribute("Hasted",durations[type],RPG.ST_MOVESPEED,RPG.d(2,40))); break;
          case SLOW: b.addAttribute(new TemporaryAttribute("Slowed",durations[type],RPG.ST_MOVECOST,RPG.d(2,40))); break;
          case ARMOUR: b.addAttribute(new TemporaryAttribute("Magic Armour",durations[type],RPG.ST_PROTECTION,getStrength(caster))); break;
          case CONFUSE: {
            Game.message("Confused "+b.getName());
            b.addAttribute(new TemporaryAttribute("Confused",durations[type],RPG.ST_CONFUSED,getPower(caster))); break;
          }
        }
        break;
      }
      
        

      // pass-through
      case FLAMECLOUD: case FLAME:
        break;
      
      default:
        Game.message(getName()+" spell seems useless!");
    }

  }
  
}