// A potion represents a magical liquid which can be drunk with
// various effects. Some may be good, some may be bad so drinking
// an unidentified Potion may be dangerous.....
//
// ***IMPORTANT TO-DO*** 
// make all potion effects into spells and change Potion so that
// it is in effect casting the spell on the drinker. This will also
// allow potion effects to be re-used as spells or special effects for
// other items.

package rl;

public class Potion extends Stack implements Description {
  protected int desc;
  protected String name;
  
  private static String[] potionstyles= {"frothy","bubbling","oily","small","tiny","smelly","fragrant","boiling","glowing","milky","murky","cloudy","sizzling","flaming","fuming","shimmering"};
  private static String[] potioncolours={"red","burgundy","orange","yellow","green","blue","aquamarine","cyan","sky blue","brown","indigo","violet","purple","pink","grey","white","black"};
  private static int[]    images =      {245,  245,       240,     240,     247,    246,   248,         248,   248,       243,    244,     244,     244,     244,   243,   242,    241};    
  
  
  // potions
  public static final int HEALING=1;
  public static final int GAINSTAT=2;
  public static final int WATER=3;
  public static final int POWER=4;
  public static final int FRENZY=5;
  public static final int POISON=6;
  public static final int CUREPOISON=7;
  public static final int LOSESTAT=8;
  public static final int EXPERIENCE=9;
  public static final int HUNGER=10;
  public static final int SPEED=11;
  public static final int FATE=12;
  public static final int CLUMSINESS=13;
  public static final int AMNESIA=14;
	
  // item data arrays
  private static final String[]           effects = {"null",  "healing", "advancement", "water",  "power", "berserker fury", "poison", "cure poison", "weakening", "experience", "hunger", "speed", "fate", "clumsiness", "amnesia"};
  public static final  int[] potions =              {0,       HEALING,   GAINSTAT,      WATER,    POWER,   FRENZY,           POISON,   CUREPOISON   , LOSESTAT,    EXPERIENCE,   HUNGER,   SPEED,   FATE,   CLUMSINESS,   AMNESIA};

  public int getUse() {return Thing.USE_NORMAL|Thing.USE_DRINK;}
	
  public static Stack createPotion() {
    return new Potion(potions[RPG.d(potions.length-1)]); 
  }
  
  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ITEMVALUE: return 2000;
      default: return super.getStat(s); 
    } 
  }
  
  public Potion(int t, int num) {
    this(t);
    number=num; 
  }
  
  public Potion(int t) {
    super(1);
    if (t==0) t=potions[RPG.r(potions.length)];
    
    type=t;
  }
	
  public int getIndividualWeight() {
    return 400; 
  }
  
  public void use(Being user) {
	  use(user, USE_DRINK);	
	}
	
	public void use(Being user, int usetype) {
    Hero h=Game.hero;
    boolean ishero=(user==Game.hero);
    
    if (usetype!=Thing.USE_DRINK) return;
    
    switch (type) {
      case POWER: {
        user.mps=RPG.min(user.getStat(RPG.ST_MPSMAX)+1,user.mps+RPG.d(2,5));
        Game.message("You feel energized");
        break;
      }  	

      case GAINSTAT: {
        user.stats.incStat(RPG.d(8),1);
        Game.message("You feel more capable");
        break;
      }
    
      case WATER: {
        Game.message("You feel refreshed");
        break;
      }
    
      case POISON: {
        int dam=user.damage(RPG.a(3),RPG.DT_POISON);
        if (dam>0) {
          Game.message("You feel really sick");
          user.addThing(new PoisonAttribute(3,RPG.DT_POISON,RPG.d(3,6)*250,10));
        } else Game.message("This potion tastes foul");
        break;
      }

      case HEALING: {
        int max=user.getStat(RPG.ST_HPSMAX);
        if (user.hps<max) {
          user.hps=RPG.min(max,user.hps+RPG.d(2,5));
          if (ishero) Game.message("You feal healthier");
        } else {
          if (ishero) Game.message("You feel a warm glow inside");
        }
        break;
      }

      case FRENZY: {
        if (ishero) Game.message("Your blood is filled with an unstoppable rage!"); 
        user.addAttribute(new TemporaryAttribute("frenzied",RPG.d(5,1000),new int[] {RPG.ST_ATTACKSPEED,RPG.d(2,100)},"You start to calm down"));
        break;
      }
    
      case CUREPOISON: {
        if (ishero) Game.message("You feel cleansed."); 
        Thing[] poisons=user.inv.getContents(PoisonAttribute.class);
        for (int i=0; i<poisons.length; i++) {
          poisons[i].remove(); 
        }
        break;
      }

      case LOSESTAT: {
        user.stats.incStat(RPG.d(8),-1);
        Game.message("You feel less capable");
        break;
      }

      case EXPERIENCE: {
        if (ishero) {
          h.gainExperience(RPG.d(2,100));
          Game.message("You feel a sudden surge of knowledge");
        }
        break;
      }

      case HUNGER: {
        if (ishero) Game.message("You suddenly feel very hungry"); 
        user.setStat(RPG.ST_HUNGER,200000);
        break; 
      }
      
      case SPEED: {
        if (ishero) Game.message("You suddenly feel the urge to run very fast"); 
        user.addAttribute(new TemporaryAttribute("Accelerated",RPG.d(5,1000),new int[] {RPG.ST_MOVESPEED,RPG.d(2,100)},"You start to slow down"));
        break;
      }

      case FATE: {
        if (ishero) Game.message("You feel invincible"); 
        user.stats.incStat(RPG.ST_FATE,1);
        break;
      }

      case CLUMSINESS: {
        if (ishero) Game.message("You seem less able to control your limbs"); 
        user.addAttribute(new TemporaryAttribute("Clumsy",RPG.d(10,2000),new int[] {RPG.ST_SK,-RPG.d(6),RPG.ST_AG,-RPG.d(6)},"You stop feeling clumsy"));
        break;
      }

      case AMNESIA: {
        if (ishero) {
          Art[] artlist=h.arts.getArts();
          if (artlist.length>0) {
            Art a=artlist[RPG.r(artlist.length)];
            Game.message("You forget about "+a.getName()); 
            // todo: delete art here
          } else {
            Game.message("Doh. You feel very stupid"); 
          }
        }
        break;
      }
    }

    identify(1000);
    remove(1);
  }

  public Description getDescription() {
    return this;
  }
  
  private String getPotionString() {
    return 
      potionstyles[(Game.seed()+type*3)%potionstyles.length]
     +" " 
     +potioncolours[(Game.seed()+type)%potioncolours.length];
  }
  
  public int getImage() {
    return images[(Game.seed()+type)%potioncolours.length];
  }
  
  // description stuff
  public String getSingularName()	{
   	if (isIdentified()) {
       return "potion of "+effects[type];	
    } else {
       return getPotionString()+" potion";
    }
  }
  
  // description stuff
  public String getPluralName()  {
    if (isIdentified()) {
       return "potions of "+effects[type];  
    } else {
       return getPotionString()+" potions";
    }
  }
  
}