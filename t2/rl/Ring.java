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
  private static boolean ringKnown[] = new boolean[15];
  private int val;
  private int stat;
  private int max;
	
  public Ring(){
	  this(RPG.d(descriptions.length-1),false);
  }
	
  public Ring( int p_type, boolean perfect) {
    stats=new Stats();
    type=p_type;
    
    switch (type) {
      case 1:
		  stat  = RPG.ST_MOVESPEED;
		  val = RPG.d(5)*10;
		  max = 50;
        break;
      case 2:
		  stat = RPG.ST_ENCUMBERANCE;
        val  = -RPG.d(5)*4;
		  max = -20;
        break;	    	
      case 3:
		  stat = RPG.ST_CH;
		  val = RPG.d(6);
		  max = 6;
        break;
      case 4:
		  stat = RPG.ST_ST;
		  val = RPG.d(2,6);
		  max = 12;
        break;	    	
      case 5:
		  stat = RPG.ST_ATTACKSPEED;
		  val = RPG.d(5)*8;
		  max = 40;
        break;
      case 6:
		  stat = RPG.ST_RESISTFIRE;
		  val = RPG.d(5)*2;
		  max = 10;
        break;
      case 7:
		  stat = RPG.ST_ARMPOISON;
		  val = RPG.d(6);
		  max = 6;
        break;        
      case 8:
		  stat = RPG.ST_IN;
		  val = RPG.d(4);
        max = 4;
        break;       
      case 9:
		  stat = RPG.ST_SK;
		  val = RPG.d(4);
        max = 4;
        break;        
      case 10:
		  stat = RPG.ST_SEARCHING;
		  val = RPG.d(6);
		  max = 6;
        break;
      case 11:
		  stat = RPG.ST_ARMFIRE;
		  val = RPG.d(5)*4;
		  max = 20;
        break;
      case 12:
		  stat = RPG.ST_RANGER;
		  val = 1;
		  max = 1;
        break;
      case 13:
		  stat = RPG.ST_ENCUMBERANCE;
		  val = RPG.d(5)*6;
		  max = 30;
        break;
      case 14:
		  stat = RPG.ST_RESISTPOISON;
		  val = RPG.d(5);
        max = 5;
        break;
      default:
        break;        
	  }
	  if(perfect){
		  val = max;
	  }
	  stats.setStat( stat , val );
	}
	
	public static int getImplemented(){
		return powernames.length-1;
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
  
  public void action(int time) {
    if ((type==10)&&(place==Game.hero)) {
      for(int i=RPG.po(time,300);i>0; i--) {
        Game.hero.searchAround(); 
      }
    }
  }

  public int getImage() {return images[type];}

  //If we dont know yet the type, we return
  public Description getDescription() {return getRingKnown(type)?this:descriptions[type];}
  
  // Description interface 
  public String getName(int number, int article)	{
	  String perfectname = (val==max)?"perfect ":"";
	  String basename = "ring of " + powernames[type];
	  String idvalue  = isIdentified()?" ["+String.valueOf(val)+"]":"";
    return Describer.getArticleName( perfectname + basename + idvalue ,number,article);	
  }

	// description stuff
	public String getSingularName()	{
		return Text.capitalise(getName( 1 , ARTICLE_NONE ));
	}
  

 	public String getPronoun(int number, int acase) {
 	  return (number==1)?"it":"them";	
 	}
 	
  public String getDescriptionText() {
    return descriptions[type].getDescriptionText()+" ("+Text.capitalise(getName(1,ARTICLE_INDEFINITE))+")";
  }
  
  	public void setIdentified(boolean ident) {
		super.setIdentified(ident);
		if(ident){
			setRingKnown(type);
		}
	}

	public boolean isIdentified() {
		boolean b = super.isIdentified();
		Hero h=Game.hero;
		if(b){
			setRingKnown(type);
		}
		return b;
	}
	
	public void setRingKnown( int i ){
		ringKnown[i] = true;
	}

	public boolean getRingKnown( int i ){
		return ringKnown[i];
	}  
  
}