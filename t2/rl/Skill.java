// represents a skill in the tyrant skill system

package rl;

public class Skill extends Art {
  private int skill; // the skill index, internal use only
  private int code;  // the stat code, i.e. RPG.ST_XXX constants
  private int level; // the level of the skill attained
 
  private static final String[] names   =  { "Riding",      "Archery",      "Athletics",        "Throwing",      "Tracking",      "Climbing",      "Dodge",         "Swimming",      "Survival",      "Attack",         "Defence",        "Parry",          "Berserk",        "Ferocity",       "Unarmed Combat",  "Mighty Blow",     "Bravery",        "Weapon Lore",     "Alertness",      "Pick-Pocket",     "Pick-Lock",     "Disarm Trap",   "Searching",      "Prayer",        "Holy Magic",     "Meditation",      "Healing",       "Literacy",       "Identify",       "Alchemy",        "Languages",      "Rune Lore",      "Herb Lore",      "Black Magic",     "True Magic",     "Battle Magic",     "Goblin Magic",     "Magic Resistance",     "Spell Casting",   "Music",       "Perception",      "Sleight of Hand",   "Storytelling",      "Seduction",      "Painting",       "Smithing",       "Appraisal",      "Woodwork",       "Ropework",       "Construction",      "Carrying",       "Trading"}; 
  private static final int[]    classes =  { RPG.ST_RANGER, RPG.ST_RANGER,  RPG.ST_RANGER,      RPG.ST_RANGER,   RPG.ST_RANGER,   RPG.ST_RANGER,   RPG.ST_RANGER,   RPG.ST_RANGER,   RPG.ST_RANGER,   RPG.ST_FIGHTER,   RPG.ST_FIGHTER,   RPG.ST_FIGHTER,   RPG.ST_FIGHTER,   RPG.ST_FIGHTER,   RPG.ST_FIGHTER,    RPG.ST_FIGHTER,    RPG.ST_FIGHTER,   RPG.ST_FIGHTER,    RPG.ST_THIEF,     RPG.ST_THIEF,      RPG.ST_THIEF,    RPG.ST_THIEF,    RPG.ST_THIEF,     RPG.ST_PRIEST,   RPG.ST_PRIEST,    RPG.ST_PRIEST,     RPG.ST_PRIEST,   RPG.ST_SCHOLAR,   RPG.ST_SCHOLAR,   RPG.ST_SCHOLAR,   RPG.ST_SCHOLAR,   RPG.ST_SCHOLAR,   RPG.ST_SCHOLAR,   RPG.ST_MAGE,       RPG.ST_MAGE,      RPG.ST_MAGE,        RPG.ST_MAGE,        RPG.ST_MAGE,            RPG.ST_MAGE,       RPG.ST_BARD,   RPG.ST_BARD,       RPG.ST_BARD,         RPG.ST_BARD,         RPG.ST_BARD,      RPG.ST_ARTISAN,   RPG.ST_ARTISAN,   RPG.ST_ARTISAN,   RPG.ST_ARTISAN,   RPG.ST_ARTISAN,   RPG.ST_ARTISAN,      RPG.ST_ARTISAN,   RPG.ST_ARTISAN};
  private static final int[]    stats   =  { RPG.ST_RIDING, RPG.ST_ARCHERY, RPG.ST_ATHLETICS,   RPG.ST_THROWING, RPG.ST_TRACKING, RPG.ST_CLIMBING, RPG.ST_DODGE,    RPG.ST_SWIMMING, RPG.ST_SURVIVAL, RPG.ST_ATTACK,    RPG.ST_DEFENCE,   RPG.ST_PARRY,     RPG.ST_BERSERK,   RPG.ST_FEROCITY,  RPG.ST_UNARMED,    RPG.ST_MIGHTYBLOW, RPG.ST_BRAVERY,   RPG.ST_WEAPONLORE, RPG.ST_ALERTNESS, RPG.ST_PICKPOCKET, RPG.ST_PICKLOCK, RPG.ST_DISARM,   RPG.ST_SEARCHING, RPG.ST_PRAYER,   RPG.ST_HOLYMAGIC, RPG.ST_MEDITATION, RPG.ST_HEALING,  RPG.ST_LITERACY,  RPG.ST_IDENTIFY,  RPG.ST_ALCHEMY,   RPG.ST_LANGUAGES, RPG.ST_RUNELORE,  RPG.ST_HERBLORE,  RPG.ST_BLACKMAGIC, RPG.ST_TRUEMAGIC, RPG.ST_BATTLEMAGIC, RPG.ST_GOBLINMAGIC, RPG.ST_MAGICRESISTANCE, RPG.ST_CASTING,    RPG.ST_MUSIC,  RPG.ST_PERCEPTION, RPG.ST_SLEIGHT,      RPG.ST_STORYTELLING, RPG.ST_SEDUCTION, RPG.ST_PAINTING,  RPG.ST_SMITHING,  RPG.ST_APPRAISAL, RPG.ST_WOODWORK,  RPG.ST_ROPEWORK,  RPG.ST_CONSTRUCTION, RPG.ST_CARRYING,  RPG.ST_TRADING};
  private static final int[]    costs   =  { 25,            20,             30,                 20,              8,               10,              15,              15,              20,              20,               20,               20,               35,               25,               15,                30,                15,               10,                10,               30,                20,              25,              15,               20,              45,               15,                30,              30,               30,               40,               10,               35,               25,               60,                50,               40,                 30,                 20,                     35,                30,            30,                20,                  25,                  40,               20,               50,               30,               20,               20,               40,                  20,               30};     
  
  // returns stat number for given skill name
  public static int number(String s) {
    return stats[Text.index(s,names)]; 
  }
  
  // returns stat number for given skill name
  public static String name(int stat) {
    return names[RPG.index(stat,stats)]; 
  }
  
  // Can [a] learn skill number [i]?
  private static boolean canLearnSkill(Being a, int i) {
    return true;
  }
  
  public static boolean canLearnSkill(Being a, String skill) {
    return canLearnSkill(a,Text.index(skill,names)); 
  }
  
  // return a random skill code for advancement
  public static int randomSkill(Being a) {
    switch (RPG.d(10)) {
      case 1:
        break;
      default:
        // pick a random skill already learnt
        Art[] arts=a.arts.getArts();
        for (int i=0; i<arts.length; i++) {
          Art art=arts[RPG.r(arts.length)];
          if (art instanceof Skill) return ((Skill)art).getStatCode();
        }
        break; 
    }
    // return any old skill
    return RPG.pick(stats);
  }
  
  // gets list of skills trainable by being b
  public static String[] getTrainableSkills(Being a, Being b) {
    int maxcost=a.getStat(RPG.ST_SKILLPOINTS);
    
    // make temporary array with space for all the skills
    int skillcount=names.length;
    String[] temp=new String[skillcount];
    int count=0;
    
    for (int i=0; i<skillcount; i++) {
      int code=stats[i];
      if (b.arts.getModifier(code)>a.arts.getModifier(code)) {
        if ((maxcost>=costs[i])&&canLearnSkill(a,i)) {
          temp[count]=names[i];
          count++;
        }
      }
    }
    String[] result=new String[count];
    System.arraycopy(temp,0,result,0,count);
    return result;
  }
  
  // gain a skill
  public static int increaseSkill(Being a, String skill, int inc) {
    int i=Text.index(skill,names);
    Skill s=a.arts.getSkill(stats[i]);
    if (s==null) {
      a.arts.addArt(new Skill(stats[i],inc));
    } else {
      s.setLevel(s.getLevel()+inc);
    }
    int cost=inc*costs[i];
    a.setStat(RPG.ST_SKILLPOINTS,a.getStat(RPG.ST_SKILLPOINTS)-cost);
    
    if (a==Game.hero) Game.message("You improve you ability in "+skill);
    
    return cost;
  }
  
  // creates skill by name
  public Skill(String s, int l) {
    skill=Text.index(s,names);
    code=stats[skill];
    if (skill<0) throw new Error("Skill not found: "+s);
    level=l;
  }
 
  // creates skill by stat number
  public Skill(int s, int l) {
    skill=RPG.index(s,stats);
    code=s;
    if (skill<0) throw new Error("Skill not found: "+s);
    level=l;
  }
 
  public int getStatCode() {
    return code; 
  }
 
  public int getLevel() {return level;}
  
  public void setLevel(int l) {level=l;}
  
  public String getName() {
    return names[skill]+" "+Text.roman(level); 
  }
  
  // returns any attribute modifiers associated with the skill
  public int getModifier(int s) {
    if (s==stats[skill]) return level;
    
    int result=0;
    switch (s) {
      case RPG.ST_MOVESPEED:
        if (code==RPG.ST_ATHLETICS) result += level*5;
        if (code==RPG.ST_DODGE)     result += level*1;
        break;
        
      case RPG.ST_ATTACKSPEED:
        if (code==RPG.ST_FEROCITY)  result += level*10;
        if (code==RPG.ST_ATTACK)    result += level*2;
        break;

      case RPG.ST_ASKMULTIPLIER:
        if (code==RPG.ST_ATTACK)  result += level*15;
        break;
        
      case RPG.ST_DSKMULTIPLIER:
        if (code==RPG.ST_DEFENCE)  result += level*15;
        break;
        
      case RPG.ST_DODGEMULTIPLIER:
        if (code==RPG.ST_DODGE)  result += level*20;
        if (code==RPG.ST_DEFENCE)  result += level*3;
        if (code==RPG.ST_ATHLETICS)  result += level*5;
        break;

      case RPG.ST_ASKBONUS:
        if (code==RPG.ST_ATTACK)  result += level*1;
        if (code==RPG.ST_BRAVERY)  result += level*2;
        break;

      case RPG.ST_DSKBONUS:
        if (code==RPG.ST_DEFENCE)  result += level*1;
        if (code==RPG.ST_DODGE)    result += level*1;
        break;

      case RPG.ST_REGENERATE:
        if (code==RPG.ST_HEALING)  result += level/2;
        break;

      case RPG.ST_RECHARGE:
        if (code==RPG.ST_MEDITATION)  result += level;
        break;
        
      case RPG.ST_PROTECTION:
        if (code==RPG.ST_DEFENCE) result += level;
        if (code==RPG.ST_DODGE) result += level;
        break;
    }
    return result;  
  } 
 
  public void apply() {
    Hero h=Game.hero;
    Map map=h.getMap();
    
    switch(code) {
      case RPG.ST_PICKPOCKET: {
        Game.message("Pickpocket: select direction");
        Point p=Game.getDirection();
        
        if (p!=null) {
          Being b=(Being)map.getObject(h.x+p.x,h.y+p.y,Being.class);
          
          int skill=(h.getStat(RPG.ST_SK)*level)/2;
                  
          if (RPG.test(skill,b.getStat(RPG.ST_SK))) {
            Thing[] stuff=b.inv.getContents(Item.class);
            if (stuff!=null) {
              Thing nick=stuff[RPG.r(stuff.length)];
              if (nick.y==0) {
                Game.message("You steal "+nick.getAName());
                h.addThing(nick);
              } else {
                Game.message("You almost manage to steal "+nick.getAName()); 
              }
            } else {
              Game.message("You can find nothing worth stealing"); 
            }
          } else if (RPG.test(skill,b.getStat(RPG.ST_IN))) {
            Game.message("You are unable to steal anything"); 
          } else {
            Game.message(b.getTheName()+" spots you!"); 
            b.anger(h);
          }
        }
        break;
      }
       
    }
  }
}