package rl;

// A ring which can be worn by the hero on either the right or left hand
// this may confer a number of possible advantages/disadvantages
// 

public class Ring extends Item implements Active, Description {
	protected static final Description DESC_PLAINRING=
		new Describer("plain ring","A dull metal ring with no visible markings");
	protected static final Description DESC_GOLDENRING=
		new Describer("golden ring","A polished golden ring");
	protected static final Description DESC_CRYSTALRING=
		new Describer("crystal ring","A delicate crystal ring");
	protected static final Description DESC_ORNATERING=
		new Describer("ornate ring","A beautifully engraved ring, with writing in some foregin tongue.");
  protected static final Description DESC_SKULLRING=
    new Describer("skull ring","A heavy gold ring decorated with a twisted skull.");
  protected static final Description DESC_EMERALDRING=
    new Describer("emerald ring","A delicate emerald ring.");
  protected static final Description DESC_RUBYRING=
    new Describer("ruby ring","A finely engraved ruby ring.");
  protected static final Description DESC_SAPPHIRERING=
    new Describer("saphire ring","A gleaming sapphire ring.");
  protected static final Description DESC_MOONRING=
    new Describer("moon ring","A golden ring featuring a shining crescent moon.");
	
  protected static final Description[] descriptions = {null,DESC_PLAINRING,DESC_GOLDENRING,DESC_ORNATERING,DESC_CRYSTALRING, DESC_SKULLRING, DESC_EMERALDRING,  DESC_RUBYRING,       DESC_SAPPHIRERING, DESC_MOONRING, DESC_MOONRING, DESC_SAPPHIRERING, DESC_MOONRING, DESC_SKULLRING, DESC_ORNATERING};
  protected static final String []     powernames   = {null,"speed",       "unburdening",  "adornment",    "godly might",    "ferocity",     "fire resistance", "poison protection", "intelligence",    "skill",       "searching",   "cold",            "the wild",    "burden",       "poison resistance"};
  protected static final int []        images       = {0   ,201,           200,            202,            208,              205,            204,               202,                 203,               206,           206,           203,               206,           205,            202};
  protected static final int []        zeroes       = {0   ,0  ,           0,              0,              0,                0,              0,                 0,                   0,                 0,             0,             0,                 0,             0,              0};
  
  
  protected int type;
  
  protected final Stats stats;
	
  public Ring() {
    stats=new Stats();
    type=RPG.d(descriptions.length-1);
    
    switch (type) {
      case 1:
        stats.setStat(RPG.ST_MOVESPEED,RPG.d(50));
        return;
      case 2:
        stats.setStat(RPG.ST_ENCUMBERANCE,-RPG.d(20));
        return;	    	
      case 3:
        stats.setStat(RPG.ST_CH,RPG.d(6));
        return;	    	
      case 4:
        stats.setStat(RPG.ST_ST,RPG.d(2,6));
        return;	    	
      case 5:
        stats.setStat(RPG.ST_ATTACKSPEED,RPG.d(40));
        return;        
      case 6:
        stats.setStat(RPG.ST_RESISTFIRE,RPG.d(10));
        return;        
      case 7:
        stats.setStat(RPG.ST_ARMPOISON,RPG.d(6));
        return;        
      case 8:
        stats.setStat(RPG.ST_IN,RPG.d(4));
        return;        
      case 9:
        stats.setStat(RPG.ST_SK,RPG.d(4));
        return;        
      case 10:
        return;
      case 11:
        stats.setStat(RPG.ST_ARMFIRE,RPG.d(2,10));
        return;
      case 12:
        stats.setStat(RPG.ST_RANGER,1);
        return;
      case 13:
        stats.setStat(RPG.ST_ENCUMBERANCE,RPG.d(30));
        return;
      case 14:
        stats.setStat(RPG.ST_RESISTPOISON,RPG.d(5));
        return;
      default:
        return;        
	  }
	}
	
	public int getModifier(int s) {
	  return stats.getStat(s)+super.getModifier(s);	
	}
	
	public int damage(int dam, int damtype) {
	  dam=Lib.physicalDamage(dam,damtype);
    if (RPG.d(5,10)>dam) return 0;
    remove();
    return dam;
	}
	
  public int getWeight() {return 50;}
	
  public void use() {
	}

  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ITEMVALUE: {
        return isIdentified()?5000:3000;
      }
      default: return super.getStat(s); 
    } 
  }

  public int wieldType() {return RPG.WT_RIGHTRING;}

  public Description getDescription() {return isIdentified()?this:descriptions[type];}
  
  public void action(int time) {
    if ((type==10)&&(place==Game.hero)) {
      for(int i=RPG.po(time,300);i>0; i--) {
        Game.hero.searchAround(); 
      }
    }
  }

  public int getImage() {return images[type];}

  // Description interface 
  public String getName(int number, int article)	{
    return Describer.getArticleName((number>1)?"ring of ":"ring of "+powernames[type],number,article);	
  }
  

 	public String getPronoun(int number, int acase) {
 	  return (number==1)?"it":"them";	
 	}
 	
  public String getDescriptionText() {
    return descriptions[type].getDescriptionText()+" ("+Text.capitalise(getName(1,ARTICLE_INDEFINITE))+")";
  }
}