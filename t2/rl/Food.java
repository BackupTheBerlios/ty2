package rl;

public class Food extends Stack {
	protected Description desc;
	
  private static final Description DESC_APPLE=
    new Describer("apple","A large green apple.");
  private static final Description DESC_PORKPIE=
    new Describer("pork pie","A tasty pork pie.");
  private static final Description DESC_BREADRATION=
    new Describer("bread ration","A stale bread ration.");
  private static final Description DESC_MEATRATION=
    new Describer("meat ration","A dry meat ration.");
  private static final Description DESC_REDAPPLE=
    new Describer("apple","A large red apple.");
  private static final Description DESC_MUSHROOM=
    new Describer("red mushroom","A large red mushroom. It looks very poisonous.");
  private static final Description DESC_PIE=
    new Describer("slice of pie","A tasty slice of pie.");
  private static final Description DESC_PRIMESTEAK=
    new Describer("prime steak","A beautiful piece of steak.");
  private static final Description DESC_STEAK=
    new Describer("steak","A piece of steak.");
  private static final Description DESC_ESCARGOT=
    new Describer("escargot steak","A piece of prime escargot steak.");
  private static final Description DESC_SAUSAGE=
    new Describer("sausage","A sausage.");
  private static final Description DESC_GIANTSAUSAGE=
    new Describer("giant sausage","A giant sausage.");
  private static final Description DESC_CHICKEN=
    new Describer("chicken","A chicken.");
  private static final Description DESC_PLUMPIE=
    new Describer("plum pie","A piece of plum pie. Mmmm.");
  private static final Description DESC_SLIMEMOLD=
    new Describer("slime mold","A slime mold.");

  public static final int APPLE=         1;
  public static final int PORKPIE=       2;
  public static final int BREADRATION=   3;
  public static final int MEATRATION=    4;
  public static final int REDAPPLE=      5;
  public static final int MUSHROOM=      6;
  public static final int PIE=           7;
  public static final int ESCARGOT=      8;
  public static final int PLUMPIE=       9;
  public static final int SAUSAGE=      10;
  public static final int GIANTSAUSAGE= 11;
  public static final int CHICKEN=      12;
  public static final int SLIMEMOLD=    13;
  public static final int STEAK=        14;
  public static final int PRIMESTEAK=   15;
  
  // FOOD DATA
  // each calorie counts 1 turn of hunger
  
  //public static final Description[] descriptions = {null,DESC_APPLE, DESC_PORKPIE, DESC_BREADRATION, DESC_MEATRATION, DESC_REDAPPLE, DESC_MUSHROOM, DESC_PIE        , DESC_ESCARGOT,    DESC_PLUMPIE, DESC_SAUSAGE, DESC_GIANTSAUSAGE, DESC_CHICKEN, DESC_SLIMEMOLD, DESC_STEAK, DESC_PRIMESTEAK};
  public static final String[]             names = {null,"apple"   , "pork pie",   "bread ration",   "meat ration"  , "red apple",   "mushroom",      "slice of pie"  , "escargot steak", "plum pie",   "sausage"   , "giant sausage",   "chicken",    "slime mold",   "steak",    "prime steak",  "bone",  "skull",  "corpse"};
  public static final String[]       pluralnames = {null,"apples"  , "pork pies",  "bread rations",  "meat rations" , "red apples",  "mushrooms",     "slices of pie",  "escargot steak", "plum pies",  "sausages"  , "giant sausages",  "chickens",   "slime molds",  "steaks",   "prime steaks", "bones", "skulls", "corpses"};
  public static final int[]               images = {0,   221,        225,          222,              220,             228,           229,             227,              230,              232,          231,          231,               224,          226,            230,        230,            302,     301,      300 };
  public static final int[]             calories = {0,   250,        800,          1200,             2000,            350,           150,             600,              400,              1000,         500,          2500,              1500,         700,            1300,       2300,           30,      100,      150};       
  
  public static final int[]               cooked = {0,   0,          0,            0,                0,               0,             0,               0,                0,                0,            0,            0,                 0,            0,              0,          0,              0,       0,        0};
  
  public static final int[]              weights = {0,   300,        500,          900,              900,             300,           100,             400,              50,               400,          400,          1000,              1000,         300,            500,        500,            400,     500,      2000};
  public static final int[]                zeros = {0,   0,          0,            0,                0,               0,             0,               0,                0,                0,            0,            0,                 0,            0,              0,          0,              0,       0,        0};
    
  public Food(String s) {
    this(Text.index(s,names),1); 
  }
  
  public Food(int t, int num) {
    super(num);
    if (RPG.d(5)==1) flags|=ITEM_CURSED;
    type=t; 
  }

  public String getSingularName() {
    return names[type]; 
  }
  
  public String getPluralName() {
    return pluralnames[type]; 
  }
  
  public Description getDescription() {
    return this;
  }
  
  public static Stack createFood() {
    return createFood(0);    
  }

	public static Stack createFood(int f) {
		if (f<=0) f=RPG.d(names.length-1);
		
	  return new Food(f,1);
	}
	
  public int getUse() {return USE_NORMAL|USE_EAT;}
	
	public void use(Being user) {
	  use(user,USE_EAT);	
	}
	
	public int getIndividualWeight() {return weights[type];}
	
  public int getStat(int stat) {
    switch (stat) {
      case RPG.ST_ITEMFRAGILITY: return 30;
      case RPG.ST_ITEMVALUE: return (calories[type]*calories[type])/(weights[type]*100);
    }
    return super.getStat(stat); 
  }
  
  public int damage(int dt, int dam) {
    if ((dt==RPG.DT_FIRE)&&(cooked[type]>0)) {
      type=cooked[type]; 
    } 
    return super.damage(dt,dam);
  }
  
  public int getImage() {return images[type];}
  
  public void use(Being user, int usetype) {
    Thing t=remove(1);
    
    // nutrition for hero only!
    Hero h=Game.hero;
    if (user!=h) return;
    
    Game.message("You eat "+t.getTheName());
    
    h.aps-=500;
    int hunger=h.getStat(RPG.ST_HUNGER);

    if ((flags&ITEM_CURSED)>0) {
      Game.message("That tasted pretty foul!  Your hunger is still "+
      		   RPG.percentile(hunger, Hero.HUNGERLEVEL)+
      		   "%.");
    } else {
      // update hunger level
      // 1 calorie = 100 ticks of hunger
      int old_hunger=hunger;
      hunger=RPG.max(0,hunger-100*calories[type]);
      h.setStat(RPG.ST_HUNGER,hunger);
      Game.message("Yum yum!  Your hunger has decreased to "+
      		   RPG.percentile(hunger, Hero.HUNGERLEVEL)+
      		   "%.");
    }
	}
	
}