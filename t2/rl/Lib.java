package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class Lib extends Object {
	
	public static Color getTileColor(int x) {
		return null;
	}	
	
  // And now for the standard descriptions......
  // PLEASE keep alphabetical by type!!!


  // artifacts
	public static final Description DESC_IMPERIALCROWN = 
		new Describer("The Crown of Daedor","","The priceless crown of the Daedorian Empire. This artifact is rumoured to bestow remarkable powers on the wearer.",Description.NAMETYPE_PROPER,Description.GENDER_NEUTER);

  // Clothing
	public static final Description DESC_HAT = 
		new Describer("hat","A hat of fine quality.");
	public static final Description DESC_TROUSERS = 
		new Describer("trousers","pairs of trousers","A pair of serviceable trousers.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
	public static final Description DESC_ROBE = 
		new Describer("robe","A well-made robe.");
	public static final Description DESC_MAGICROBE = 
		new Describer("robe","A robe covered with runes and mystic sigils.");
	public static final Description DESC_GLOVES = 
		new Describer("gloves","pairs of gloves","A pair of soft leather gloves.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);

  // treasure
  public static final Description DESC_TREASURE = 
		new Describer("treasure","treasure","Valuable treasure.",Description.NAMETYPE_QUANTITY,Description.GENDER_NEUTER);
		
  // quality descriptions
  public static final String[] qualitystrings = 
      {"useless",
       "pathetic",
       "very poor",
       "poor",
       "mediocre",
       "average",
       "fair",
       "good",
       "very good",
       "excellent",
       "superb",
       "brilliant",
       "divine",
       "perfect"
      };             
       
  public static final int[] qualityvalues = {0,20,40,60,80,100,150,200,400,800,1800,5000,20000,100000};
    
  // standard unarmed combat weapons
  public static final Weapon 
    WEAPON_BRAWL=new UnarmedWeapon(UnarmedWeapon.BRAWL);
  public static final Weapon 
    WEAPON_BITE=new UnarmedWeapon(UnarmedWeapon.BITE);
  public static final Weapon 
    WEAPON_MAUL=new UnarmedWeapon(UnarmedWeapon.MAUL);

  // get wield description for any wield slot
  public static String wieldDescription(int wieldtype) {
    switch (wieldtype) {
      case RPG.WT_MAINHAND: return "Right hand";
      case RPG.WT_SECONDHAND: return "Left hand";
      case RPG.WT_TWOHANDS: return "Both hands";
      case RPG.WT_RIGHTRING: return "Right finger";
      case RPG.WT_LEFTRING: return "Left finger";
      case RPG.WT_NECK: return "Neck";
      case RPG.WT_HANDS: return "Worn";	
      case RPG.WT_BOOTS: return "Feet";
      case RPG.WT_TORSO: return "Body";
      case RPG.WT_LEGS: return "Worn";
      case RPG.WT_HEAD: return "Head";
      case RPG.WT_CLOAK: return "Worn";
      case RPG.WT_FULLBODY: return "Body";
      case RPG.WT_BRACERS: return "Worn";
      case RPG.WT_RANGEDWEAPON: return "Ranged weapon";
      case RPG.WT_MISSILE: return "Missile";
      default: return null;
    }	
  }

  // get a random hit location
  public static int hitLocation() {
    switch(RPG.d(30)) {
      case 1: case 2: return RPG.WT_HEAD; 
      case 3:         return RPG.WT_HANDS;
      case 4:         return RPG.WT_BRACERS;
      case 5:         return RPG.WT_NECK;
      case 6: case 7: return RPG.WT_BOOTS;
      
      default:
        switch (RPG.d(5)) {
          case 1: case 2: return RPG.WT_TORSO;
          case 3:         return RPG.WT_MAINHAND; 
          case 4:         return RPG.WT_SECONDHAND; 
          case 5:         return RPG.WT_LEGS; 
        };
    } 
    throw new Error("Invalid Hit Location");
  }

  // describe damage
  public static String damageDescription(int dam, int max) {
    if (dam<=0) return "no damage";
    if (max<=0) max=10;
    double d=((double)dam)/max; 
    if (d<0.2) return "minor damage";
    if (d<0.4) return "moderate damage";
    if (d<0.7) return "serious damage";
    return "critical damage";
  }
  
  // calculate physical (impact/cutting) damage
  public static int physicalDamage(int dam, int damtype) {
    switch(damtype) {
      case RPG.DT_SPECIAL:
      case RPG.DT_NORMAL:
      case RPG.DT_IMPACT:
      case RPG.DT_PIERCING:
      case RPG.DT_UNARMED:
        return dam;
      default:
        return 0;
    } 
  }

  // item creation routines
  
  public static Item createShield(int level) {
    return StandardArmour.createArmour(StandardArmour.shields,level);
  }
  
  public static Item createFood(int level) {
    return Food.createFood(0);
  }

  public static Item createWeapon(int level) {
    switch(RPG.d(6)) {
      case 1: case 2: case 3: case 4: case 5: return CommonWeapon.createWeapon(level);
      case 6: return StandardWeapon.createWeapon(StandardWeapon.source,level); 
    } 
    return CommonWeapon.createWeapon(level);
  }

  public static Item createSword(int level) {
    return StandardWeapon.createWeapon(StandardWeapon.swords,level);
  }

  public static Item createLightArmour(int level) {
    return createArmour(level); 
  }

  public static Item createArmour(int level) {
    if (RPG.d(3)==1) {
      return StandardArmour.createArmour(StandardArmour.battlearmours,level);
    } else {
      return CommonArmour.createArmour(level); 
    }
  }

  public static Thing createArtifact(int code) {
    if (code<=0) code=RPG.d(2);
    switch (code) {
      case 1: if (checkArtifact("\"Nitrozac\"")==null) {
        return addArtifact("Nitrozac Boots",new StandardArmour(StandardArmour.NITROZACBOOTS)); 
      }
      case 2: if (checkArtifact("\"Ker-Ombai\"")==null) {
        return addArtifact("Ker-Ombai Boots",new StandardArmour(StandardArmour.KEROMBAIBOOTS)); 
      }
    }	
    return createMagicItem(Game.hero.getStat(RPG.ST_LEVEL));
  }

  // creates a random item
  // can be added to maps and inventories
  //   level = approximate level of item to create
  // NB: allows for various out-of-depth items to appear
  public static Item createItem(int level) {
    switch(RPG.d(15)) {
      case 1: return createArmour(level);  
      case 2: return StandardWeapon.createWeapon(level);  
      case 3: return new SpellBook();  
      case 4: return new Wand();  
      case 5: return new Ring();  
      case 6: return Potion.createPotion();  
      case 7: case 8: return Food.createFood(0);  
      case 9: return Drink.createDrink(0);  
      case 10: return Scroll.createScroll(level);
      case 11: return createArmour(level);
      case 12: return Missile.createMissile(0);
      case 13: return Coin.createMoney(RPG.d(10+level*5)*RPG.d(3+level*5));
      case 14: return RangedWeapon.createRangedWeapon(level);
      case 15: return CommonWeapon.createWeapon(level);  
    }
    return new Treasure();
  }


  public static Thing addArtifact(String s, Thing a) {
    Game.hero.uniques.put(s,a); 
    return a;
  }

  public static Thing checkArtifact(String s) {
    return (Thing)Game.hero.uniques.get(s);    
  }

  // World generation function
  public static void createWorld() {
  	
  }

  public static Creature createFoe(int level) {
    return Creature.createFoe(level); 
  }
  
  public static Creature createMonster(int level) {
    return Creature.createMonster(level); 
  }

  public static Creature createCreature(int level) {
    return Creature.createCreature(level); 
  }

  public static Item createMagicItem(int level) {
    level=RPG.middle(0,level+RPG.d(6)-RPG.d(6),15);
    switch (level) {
      case 5: return Potion.createPotion();  
      case 8: return new Ring();
      case 12: return new Ring();
      
      default: switch (RPG.d(9)) {
        case 1: return new SpellBook();
        case 2: return new Wand();
        case 3: case 4: return Potion.createPotion();
        case 5: return StandardWeapon.createWeapon(StandardWeapon.specialsource,level);  
        default: return Scroll.createScroll(level); 
      }
    }
  }

}