// A magic wand casts a particular type of spell
// It has it's own supply of magic points (mps)
//
// It can be recharged, although this will occasionally destroy the wand
//
// If broken, the wand will fire it's ramianing charge randomly

package rl;

public class Wand extends Item implements Description {
	private static final Description DESC_WILLOWWAND=
		new Describer("willow wand","A willow wand");
	private static final Description DESC_METALWAND=
		new Describer("metal wand","A metal wand");
	private static final Description DESC_CRYSTALWAND=
		new Describer("crystal wand","A crystal wand");
	private static final Description DESC_BLUEWAND=
		new Describer("blue wand","A painted blue wand");
	
	protected Spell spell;
	protected Description desc;
	protected int mps;
	
	public Wand() {
	  switch (RPG.d(4)) {
	    case 1:
	    	desc=DESC_WILLOWWAND;	image=288;
	      switch (RPG.d(10)) {
	      	case 1: spell=new Spell(Spell.SPARK); break;
	      	case 2: spell=new Spell(Spell.POISONBOLT); break;
	      	case 3: spell=new Spell(Spell.ACIDBLAST); break;
	      	case 4: spell=new Spell(Spell.FORCEBOLT); break;
	      	case 5: spell=new Spell(Spell.THUNDERBOLT); break;
         case 6: spell=new Spell(Spell.IDENTIFY); break;
	       default: spell=new Spell(Spell.randomSpell()); break;
        }
	      break;
	    case 2:
	    	desc=DESC_METALWAND;	image=292;
	      switch (RPG.d(5)) {
          case 1: spell=new Spell(Spell.FIREBALL); break;
          case 2: spell=new Spell(Spell.FLAMECLOUD); break;
          case 3: spell=new Spell(Spell.BLAST); break;
          case 4: spell=new Spell(Spell.BLAZE); break;
          default: spell=new Spell(Spell.randomSpell()); break;
	      }
        break;	    	
      case 3:
        desc=DESC_CRYSTALWAND; image=290;	
        switch (RPG.d(4)) {
          case 1: spell=new Spell(Spell.TELEPORT); break;
          case 2: spell=new Spell(Spell.SUMMON); break;
          default: spell=new Spell(Spell.randomSpell()); break;
	      }
        break;	    	
      case 4:
        desc=DESC_BLUEWAND; image=289;	
        switch (RPG.d(4)) {
          case 1: spell=new Spell(Spell.IDENTIFY); break;
          case 2: spell=new Spell(Spell.RECHARGE); break;
          case 3: spell=new Spell(Spell.REPAIR); break;
          default: spell=new Spell(Spell.randomSpell()); break;
        }
      break;	    	
	  }
    
    // charge the wand
    mps=RPG.d(20)+RPG.d(3)*spell.cost;
	}
	
  public void recharge(int power) {
    if (RPG.r(10*spell.cost)<(mps-power)) {
      Game.message(Text.capitalise(getTheName())+" is destroyed by overcharging!");	
      die();
    } else {
      mps=mps+RPG.d(power);
      Game.message("You recharge "+getTheName()); 
    }
  }
	
	public int damage(int dam, int damtype) {
		if (RPG.d(3,10)>dam) return 0;
 	  ThingOwner p=place;
 	  die();
	  return dam;
	}
	
  public void die() {
    Map map=getMap();
    if ((map!=null)&&(spell!=null)) {
      spell.castAtLocation(null,map,getMapX(),getMapY());  
    }
    super.die();
  }
  
  public int getWeight() {return 300;}
	
	public Spell getSpell() {return spell;}
	
	public int getUse() {return USE_NORMAL;}
	
	public void use(Being user) {
    boolean ishero=(user==Game.hero);
    
    if ((spell!=null)&&(mps>=spell.cost)) {
      mps=mps-spell.cost;
      if (ishero) {
        QuestApp.questapp.screen.castSpell(spell); 
        flags=flags|ITEM_IDENTIFIED;
      }
      user.aps-=120;
    } else {
      if (ishero) Game.message("This wand appears to be useless");	
    }
	}

  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ITEMVALUE: {
        if (isIdentified()) {
          return (spell!=null)?(2000+(200*mps)):50;
        } else {
          return 1000; 
        }
      }
      default: return super.getStat(s); 
    } 
  }

  public Description getDescription() {return this;}
  
  // Description interface 
  public String getName(int number, int article)	{
    if (isIdentified()) return Describer.getArticleName(((number>1)?"wands":"wand")+((spell==null)?"":(" of "+spell.getName()+" ("+mps/spell.cost+" charges)")),number,article);	
    else return desc.getName(number,article);
  }
  
 	public String getPronoun(int number, int acase) {
 	  return "it";	
 	}
 	
  public String getDescriptionText() {
    if (!isIdentified()) return desc.getDescriptionText();
    else return desc.getDescriptionText()+" ("+Text.capitalise(getName(1,ARTICLE_INDEFINITE))+")";
  }
}