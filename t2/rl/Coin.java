package rl;

// in transition to ItemType

public class Coin extends Stack {
  
  public static final Description DESC_COPPER = 
    new Describer("copper coin","A common copper coin");
  public static final Description DESC_SILVER=
    new Describer("silver coin","A shiny silver coin");
  public static final Description DESC_GOLD=
    new Describer("gold coin","A gleaming gold coin");
  public static final Description DESC_SOVEREIGN=
    new Describer("sovereign","An imperial gold sovereign");

  public static final int[] COINVALUES={1,10,100,1000};
  public static final int[] IMAGES={144,143,140,140};
  public static final Description[] DESCS={DESC_COPPER,DESC_SILVER,DESC_GOLD,DESC_SOVEREIGN};
    
  public static final int COPPER=    0;
  public static final int SILVER=    1;
  public static final int GOLD=      2;
  public static final int SOVEREIGN= 3;

  public Coin(int t,int q) {
    super(q);
    type=t;
  }
  
  public static String valueString(int v) {
    int t=0;
    while ((t<2)&&(v>0)&&((v%10)==0)) {
      t++;
      v/=10;
    }
    if (v<=0) return "nothing";
    switch (t) {
      case 0: return v+" copper";
      case 1: return v+" silver";
      case 2: return v+" gold";
      default: return "nothing";
    }
  }
  
  // stack of approximate value
  public static Coin createMoney(int v) {
    int type=0;
    int number=RPG.d(2,v);
    
    if ((number>=1000)&&(RPG.d(4)==1)) {
      type++;
    }
    if (number>=100) {
      type++;
    }
    if ((RPG.d(2)==1)&&(number>=10)) {
      type++;
    }
    for (int i=0; i<type; i++){ 
		 number/=10;
	 } 

    if (number<=0) number=1;
    
    if (type==1) return new Coin(SILVER,number);
    if (type==2) return new Coin(GOLD,number);
    if (type>=3) return new Coin(SOVEREIGN,number);
    return new Coin(COPPER,number);
  }
  
  public int getStat(int s) {
    switch(s) {
      case RPG.ST_ITEMVALUE: return COINVALUES[type];
      default: return super.getStat(s); 
    } 
  }
  
  public int getWeight() {
    return 10*number;
  }
  
  public int getUse() {return Thing.USE_MONEY;}
  
  public int getImage() {
    return IMAGES[type];
  }
  
  public Description getDescription() {
    return DESCS[type];
  }
}