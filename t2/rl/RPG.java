// The RPG classes contains:
//
// - Definition of all statistic codes
// - Definition of damage types 
// - Random number routines

package rl;

import java.util.Random;

public class RPG {
	
  // RPG CONSTANTS
	
  // damage types 
  public static final int DT_SPECIAL=-1; // unmodifiable
  public static final int DT_NORMAL=0; // standard damage
  public static final int DT_UNARMED=1; // soft impact
  public static final int DT_FIRE=2; // burning damage
  public static final int DT_ICE=3; // freezing damage
  public static final int DT_PIERCING=4; // reduce armour
  public static final int DT_ACID=5; // contact acid
  public static final int DT_MAGIC=6; // disintegration etc.
  public static final int DT_IMPACT=7; // hard impact
  public static final int DT_CHILL=8; // demonic chill	
  public static final int DT_POISON=9; // poison (no ARM)
  public static final int DT_DRAIN=10; // MP draining
  public static final int DT_DISPELL=11; // dispell magic
	 
  // wield types
  public static final int WT_ERROR=-1;
  public static final int WT_NONE=0;
  public static final int WT_MAINHAND=1;
  public static final int WT_SECONDHAND=2;
  public static final int WT_TWOHANDS=3;
  public static final int WT_RIGHTRING=4;
  public static final int WT_LEFTRING=5;
  public static final int WT_NECK=6;
  public static final int WT_HANDS=7;	
  public static final int WT_BOOTS=8;
  public static final int WT_TORSO=9;
  public static final int WT_LEGS=10;
  public static final int WT_HEAD=11;
  public static final int WT_CLOAK=12;
  public static final int WT_FULLBODY=13;
  public static final int WT_BRACERS=14;
  public static final int WT_BELT=15;

  public static final int WT_RANGEDWEAPON=20;
  public static final int WT_MISSILE=21;
	
  public static final int WT_EFFECT=100;
	
  // creature state slot
  public static final int ST_STATE=0;
	
  // statistic numbers
  public static final int ST_SK=1;
  public static final int ST_ST=2;
  public static final int ST_AG=3;
  public static final int ST_TG=4;
  public static final int ST_IN=5;
  public static final int ST_WP=6;
  public static final int ST_CH=7;
  public static final int ST_CR=8;
	
	// current progression
  public static final int ST_LEVEL=11;
  public static final int ST_EXP=12;
  public static final int ST_FATE=13;
  public static final int ST_FAME=14;
  public static final int ST_HUNGER=15;
  public static final int ST_SKILLPOINTS=16;
	
	// charcter class skills
	public static final int ST_RANGER=21;
	public static final int ST_FIGHTER=22;
	public static final int ST_THIEF=23;
	public static final int ST_PRIEST=24;
	public static final int ST_SCHOLAR=25;
	public static final int ST_MAGE=26;
	public static final int ST_BARD=27;
	public static final int ST_ARTISAN=28;
	
	// state values (mostly calculated)
	public static final int ST_ENCUMBERANCE=101; // 0=unencumbered 10=completely stuffed
	public static final int ST_MAXWEIGHT=102; // in grams!
	
	
	// ranger skills 21xx
	public static final int ST_RIDING=2101;
	public static final int ST_ARCHERY=2102;
	public static final int ST_ATHLETICS=2103;
	public static final int ST_THROWING=2104;
	public static final int ST_TRACKING=2106;
	public static final int ST_CLIMBING=2107;
	public static final int ST_DODGE=2108;
  public static final int ST_SWIMMING=2109;
  public static final int ST_SURVIVAL=2110;
	
	// fighter skills 22xx
	public static final int ST_ATTACK=2201;
	public static final int ST_DEFENCE=2202;
	public static final int ST_PARRY=2203;
	public static final int ST_BERSERK=2204;
	public static final int ST_FEROCITY=2205;
	public static final int ST_UNARMED=2206;
  public static final int ST_MIGHTYBLOW=2207;
  public static final int ST_BRAVERY=2208;
  public static final int ST_WEAPONLORE=2209;
  public static final int ST_TACTICS=2210;
  
  // thief skills 23xx
  public static final int ST_ALERTNESS=2301;
  public static final int ST_PICKPOCKET=2302;
  public static final int ST_PICKLOCK=2303;
  public static final int ST_DISARM=2304;
  public static final int ST_SEARCHING=2305;
  

	// priest skills 24xx
	public static final int ST_PRAYER=2401;
	public static final int ST_HOLYMAGIC=2402;
  public static final int ST_MEDITATION=2403;
  public static final int ST_HEALING=2403;
  

	// Scholar skills 25xx
	public static final int ST_LITERACY=2501;
	public static final int ST_IDENTIFY=2502;
	public static final int ST_ALCHEMY=2503;
	public static final int ST_LANGUAGES=2504;
  public static final int ST_RUNELORE=2505;
  public static final int ST_HERBLORE=2506;
  public static final int ST_STRATEGY=2507;
	
	// mage skills 26xx
  public static final int ST_BLACKMAGIC=2601;
	public static final int ST_TRUEMAGIC=2602;
  public static final int ST_BATTLEMAGIC=2603;
  public static final int ST_GOBLINMAGIC=2604;
  public static final int ST_MAGICRESISTANCE=2605;
  public static final int ST_CASTING=2606;
  public static final int ST_ANCIENTMAGIC=2607;

	
	// bard skills 27xx
	public static final int ST_MUSIC=2701;
	public static final int ST_CON=2702;
	public static final int ST_PERCEPTION=2703;
	public static final int ST_SLEIGHT=2704;
	public static final int ST_STORYTELLING=2705;
  public static final int ST_SEDUCTION=2706;
	
	// artisan skills 28xx
	public static final int ST_PAINTING=2801;
	public static final int ST_SMITHING=2802;
	public static final int ST_APPRAISAL=2803;
	public static final int ST_WOODWORK=2804;
	public static final int ST_ROPEWORK=2805;
  public static final int ST_CONSTRUCTION=2806;
	public static final int ST_CARRYING=2807;
  public static final int ST_TRADING=2808;

	// general and miscellaneous skills 29xx
	public static final int ST_ALIGNMENT=2901; // +good -evil
	public static final int ST_ORDER=2902; // +lawful -chaotic
	
  // movement and action costs
  // 100 = normal   
  // 200 = double speed
  // etc...
  public static final int ST_MOVESPEED=2911;
  public static final int ST_ATTACKSPEED=2912;
  public static final int ST_MAGICSPEED=2913;
  public static final int ST_MOVECOST=2921;
  public static final int ST_ATTACKCOST=2922;
  
	// For ethereal creatures set ST_ETHEREAL > 0
  public static final int ST_ETHEREAL=3001;
  
  // for undead:
  //   1 = lesser undead (skeletons, zombies)
  //   2 = greater undead (vampires, mummies, wights)
  //   3 = ethereal undead (ghosts, spectres, phantoms)
  public static final int ST_UNDEAD=3002;
  
  
	// resistances   dam=(dam*2^(-RESIST/6))
	public static final int ST_RESIST_BASE=5000;
	public static final int ST_RESISTUNARMED=ST_RESIST_BASE+DT_UNARMED; // soft impact
	public static final int ST_RESISTFIRE=ST_RESIST_BASE+DT_FIRE; // burning damage
	public static final int ST_RESISTICE=ST_RESIST_BASE+DT_ICE; // freezing damage
	public static final int ST_RESISTPIERCING=ST_RESIST_BASE+DT_PIERCING; // reduce armour
	public static final int ST_RESISTACID=ST_RESIST_BASE+DT_ACID; // contact acid
	public static final int ST_RESISTMAGIC=ST_RESIST_BASE+DT_MAGIC; // disintegration etc.
	public static final int ST_RESISTIMPACT=ST_RESIST_BASE+DT_IMPACT; // hard impact
	public static final int ST_RESISTCHILL=ST_RESIST_BASE+DT_CHILL; // demonic chill	
	public static final int ST_RESISTPOISON=ST_RESIST_BASE+DT_POISON; // poison (no ARM)
	public static final int ST_RESISTDRAIN=ST_RESIST_BASE+DT_DRAIN; // MP draining
	public static final int ST_RESISTDISPELL=ST_RESIST_BASE+DT_DISPELL; // MP draining
	
  // protection from physical damage
	public static final int ST_PROTECTION=5999;
	
	// armour      dam=dam*dam/(dam+arm)
	public static final int ST_ARM_BASE=6000;
	public static final int ST_ARMNORMAL=ST_ARM_BASE+DT_NORMAL; // soft impact
	public static final int ST_ARMUNARMED=ST_ARM_BASE+DT_UNARMED; // soft impact
	public static final int ST_ARMFIRE=ST_ARM_BASE+DT_FIRE; // burning damage
	public static final int ST_ARMICE=ST_ARM_BASE+DT_ICE; // freezing damage
	public static final int ST_ARMPIERCING=ST_ARM_BASE+DT_PIERCING; // reduce armour
	public static final int ST_ARMACID=ST_ARM_BASE+DT_ACID; // contact acid
	public static final int ST_ARMMAGIC=ST_ARM_BASE+DT_MAGIC; // disintegration etc.
	public static final int ST_ARMIMPACT=ST_ARM_BASE+DT_IMPACT; // hard impact
	public static final int ST_ARMCHILL=ST_ARM_BASE+DT_CHILL; // demonic chill	
	public static final int ST_ARMPOISON=ST_ARM_BASE+DT_POISON; // poison (no ARM)
	public static final int ST_ARMDRAIN=ST_ARM_BASE+DT_DRAIN; // MP draining
	public static final int ST_ARMDISPELL=ST_ARM_BASE+DT_DISPELL; // dispell magic
	
  // item protection      dam=dam*dam/(dam+PROTECT)
  public static final int ST_PROTECT_BASE=7000;
  public static final int ST_PROTECTNORMAL=ST_PROTECT_BASE+DT_NORMAL; // soft impact
  public static final int ST_PROTECTUNPROTECTED=ST_PROTECT_BASE+DT_UNARMED; // soft impact
  public static final int ST_PROTECTFIRE=ST_PROTECT_BASE+DT_FIRE; // burning damage
  public static final int ST_PROTECTICE=ST_PROTECT_BASE+DT_ICE; // freezing damage
  public static final int ST_PROTECTPIERCING=ST_PROTECT_BASE+DT_PIERCING; // reduce PROTECTour
  public static final int ST_PROTECTACID=ST_PROTECT_BASE+DT_ACID; // contact acid
  public static final int ST_PROTECTMAGIC=ST_PROTECT_BASE+DT_MAGIC; // disintegration etc.
  public static final int ST_PROTECTIMPACT=ST_PROTECT_BASE+DT_IMPACT; // hard impact
  public static final int ST_PROTECTCHILL=ST_PROTECT_BASE+DT_CHILL; // demonic chill  
  public static final int ST_PROTECTPOISON=ST_PROTECT_BASE+DT_POISON; // poison (no PROTECT)
  public static final int ST_PROTECTDRAIN=ST_PROTECT_BASE+DT_DRAIN; // MP draining
  public static final int ST_PROTECTDISPELL=ST_PROTECT_BASE+DT_DISPELL; // dispell magic

	// current and max points
	public static final int ST_APS=10001;
  public static final int ST_APSMAX=10002;
	public static final int ST_HPS=10003;
	public static final int ST_HPSMAX=10004;
	public static final int ST_MPS=10005;
  public static final int ST_MPSMAX=10006;
	
  
  // attack skills
	public static final int ST_ASK=10020;
	public static final int ST_ASKMULTIPLIER=10021;
	public static final int ST_ASKBONUS=10022;
	
	public static final int ST_AST=10025;
  public static final int ST_ASTMULTIPLIER=10026;
	public static final int ST_ASTBONUS=10027;
	
	// defence skills
	public static final int ST_DSK=10030;
	public static final int ST_DSKMULTIPLIER=10031;
	public static final int ST_DSKBONUS=10032;
	public static final int ST_DODGEMULTIPLIER=10040;
	public static final int ST_DSKPARRY=10041;
	
  public static final int ST_REGENERATE=10051;
  public static final int ST_RECHARGE=10052;

  // ranged skills
  public static final int ST_RSKBONUS=10060;
  public static final int ST_RSKMULTIPLIER=10061;
  public static final int ST_RSTBONUS=10062;
  public static final int ST_RSTMULTIPLIER=10063;
  
  public static final int ST_RANGE=10070;

  // special stats
  public static final int ST_ANTIMAGIC=10101;
  
  public static final int ST_SCORE=11000;
  public static final int ST_SCORE_BESTKILL=11001;
  public static final int ST_SCORE_BESTLEVEL=11002;
  public static final int ST_SCORE_KILLS=11003;

  
  // Item stat flags
	public static final int ST_ITEMBASE=20000;
	public static final int ST_ITEMRARITY=ST_ITEMBASE+1;
	public static final int ST_ITEMVALUE=ST_ITEMBASE+2;
  public static final int ST_ITEMFRAGILITY=ST_ITEMBASE+3;
  
  public static final int ST_MISSILETYPE=ST_ITEMBASE+10;

  
  
  // AI helper stats
	public static final int ST_AIMODE=30000;
	public static final int ST_TARGETX=30001;
	public static final int ST_TARGETY=30002;
  public static final int ST_CASTCHANCE=30003;

  public static final int ST_SIDE=30010;

  // Quest details
  public static final int ST_QUESTNUMBER=31000;
  public static final int ST_QUESTSTATE=31001;

  // Physical state
  public static final int ST_IMMOBILIZED=32001;
  public static final int ST_CONFUSED=32002;
  public static final int ST_BLINDED=32003;
  public static final int ST_PANICKED=32004;
  public static final int ST_NON_DISPLACEABLE=32005; // can't displace if >0
  
  
  
  // special abilities
  public static final int ST_TRUEVIEW=33001;

  // material type flags
  public static final int MF_EDIBLE=2<<15;
  public static final int MF_MAGICAL=2<<16;
  public static final int MF_RESISTANT=2<<17;
  public static final int MF_LIVING=2<<18;
  public static final int MF_DIVINE=2<<19;
  public static final int MF_SKIN=2<<20;
  public static final int MF_STONE=2<<21;
  public static final int MF_GLASS=2<<22;
  public static final int MF_GEMSTONE=2<<23;
  public static final int MF_METAL=2<<24;
  public static final int MF_PLANT=2<<25;
  public static final int MF_CLOTH=2<<26;
  public static final int MF_INDESTRUCTIBLE=2<<27;
  public static final int MF_LIQUID=2<<28;
  public static final int MF_GASEOUS=2<<29;
  public static final int MF_ETHEREAL=2<<30;
  public static final int MF_PLASTIC=2<<31;


/*
  Old material flags, functionality replaced by Material class

  // MATERIALS
  // flesh and bones
  public static final int MAT_FLESH=1|MF_SKIN|MF_LIVING;
  public static final int MAT_BONE=2;
  public static final int MAT_LEATHER=3|MF_SKIN;
  public static final int MAT_HIDE=4|MF_SKIN|MF_CLOTH;
  
  // metals
  public static final int MAT_BRONZE=101|MF_METAL;
  public static final int MAT_SILVER=102|MF_METAL;
  public static final int MAT_GOLD=103|MF_METAL;
  public static final int MAT_IRON=104|MF_METAL;
  public static final int MAT_STEEL=105|MF_METAL;
  public static final int MAT_MITHRIL=106|MF_METAL|MF_MAGICAL;
  public static final int MAT_KRITHIUM=107|MF_METAL|MF_RESISTANT;
  public static final int MAT_PARRIUM=108|MF_METAL|MF_INDESTRUCTIBLE;
	
	// stone and minerals
  public static final int MAT_STONE=201;
  public static final int MAT_GRANITE=202;
	*/
  
  // missile types
  public static final int MISSILE_THROWN=0;
  public static final int MISSILE_ARROW=1;
  public static final int MISSILE_BOLT=2;
  public static final int MISSILE_STONE=3;
  


  // some standard description for internal use
  public static final Description DESC_GENERIC = 
		new Describer("thing","A generic thing. Don't ask.");


	
	// stat power series
	public static final int[] POWER={2,2,3,3,3,3,4,4,5,5,6,7,8,9,10,11,13,14,16,18,20,22,25,28,32,36,40,45,51,57,64,72,81,91,102,114,128,144,161,181,203,228,256,287,323,362,406,456,512,575,645,724,813,912,1024,1149,1290,1448,1625,1825,2048,2299,2580,2896,3251,3649,4096}; 
	
  
  // KULT RPG FUNCTIONS
  
  // U[0,1] distribution
  public static float random() {
  	return rand.nextFloat();
  }
  
  public static boolean test(int a, int b) {
  	if (a<=0) return false;
  	if (b<=0) return true;
  	return rand.nextInt(a+b)<a;
  }	
  
  public static int hitFactor(int a, int b) {
    int factor=0;
    while (test(a,RPG.max(1,b))) {
      factor+=1;
      a/=2; 
    } 
    return factor;
  }
  
  public static int power(int a) {
    if ((a<0)||(a>66)) return (int)Math.pow(2,1+((double)a)/6); 
    return POWER[a]; 
  }
  
  public static int level(int a) {
    if (a<1) return -1000000;
    if (a==1) return -6;
    if (a>66) return (int)Math.round(Math.log(a)*8.656170245-6);
    int i=0;
    while (POWER[i]<a) i++;
    return i;
  }

	// LOTS OF RANDOM FUNCTIONALITY
	
	private static KultRandom rand = new KultRandom();
	
	public static void setRandSeed(long n) {
	  rand.setSeed(n);
	}
	
	// simulates a dice roll with the given number of sides 
  public static final int d(int sides) {
	  if (sides<=0) return 0;
	  return rand.nextInt(sides)+1;	
	}

  // USEFUL FUNCTIONS
  
  // return the squared distance between points
  public static final int dist(int x1, int y1, int x2, int y2) {
    return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);	
  }
  
  // return the radius equivalent of squard distance
  // rounds radius down (i.e. radius(r2)*radius(r2) <=r2)
  public static final int radius(int r2) {
    int r=0;
    while((r*r)<=r2) r++;
    return r-1;	
  }
  
  // pick a random integer from an array
  public static final int pick(int[] a) {
    return a[RPG.r(a.length)]; 
  }
  
  // pick a random string from an array
  public static final String pick(String[] a) {
    return a[RPG.r(a.length)]; 
  }
  
  
  // return the min value
  public static final int min(int a, int b) {
    return (a<b)?a:b;	
  }

  // return the min value
  public static final int max(int a, int b) {
    return (a>b)?a:b;	
  }
  
  // return the middle value
  public static final int middle(int a, int b, int c) {
  	if (a>b) {
  		if (b>c) return b;
  		if (a>c) return c;
  		return a;
  	} else {
  	  if (a>c) return a;
  	  if (b>c) return c;
  	  return b;	
  	}
  }

  // round number to reasonable figure 
  public static final int niceNumber(int x) {
    int p=1;
    while (x>=100) {
      x/=10;
      p*=10; 
    }
    if (x>30) x=5*(x/5); 
    return x*p;
  }
  
  // returns exponentially distributed thing with mean n
  // increment probability p=n/n+1 
  public static int e(int n) {
  	if (n<=0) return 0;
  	int result=0;
  	while ( (rand.nextInt()%(n+1)) != 0 ) result++;
  	return result;
  }
  
  // Poisson distribution
  public static int po(double x) {
    int r=0;
    double a=rand.nextDouble();
    if (a>=0.99999999) return 0;
    double p=Math.exp(-x);
    while (a>=p) {
      r++;
      a=a-p;
      p=p*x/r;
    }
    return r;
  }

  public static int po(int numerator, int denominator) {
    return po(((double)numerator)/denominator);
  }
  
  // handy likelihood functions
  public static boolean sometimes() { return (rand.nextFloat()<0.1);}

  public static boolean often() { return (rand.nextFloat()<0.4); }

  public static boolean rarely() { return (rand.nextFloat()<0.01); }

  public static boolean usually() { return (rand.nextFloat()<0.8); }

	// return integer evenly distributed in range
	public static int rspread(int a, int b) {
	  if (a>b) {int t=a; a=b; b=t;}
	  return rand.nextInt(b-a+1)+a;	
	}

  public static final int sign(double a) {
    return (a<0) ? -1 : ((a>0) ? 1 : 0);	
  }

  public static final int sign(int a) {
    return (a<0) ? -1 : ((a>0) ? 1 : 0);	
  }

  public static final int abs(int a) {
    return (a>=0) ? a : -a;	
  }

	public static final int r(int s) {
	  return rand.nextInt(s);	
	}

  // distribution for damage etc. mean a
  public static final int a(int s) {
  	return r(s+1)+r(s+1);
  }

	// All the dice
	
	public static final int d3() {
	  return d(3);	
	}

	public static final int d4() {
	  return d(4);	
	}
	
	public static final int d6() {
	  return d(6);	
	}

	public static final int d8() {
	  return d(8);	
	}

	public static final int d10() {
	  return d(10);	
	}

	public static final int d12() {
	  return d(12);	
	}

	public static final int d20() {
	  return d(20);	
	}

	public static final int d100() {
	  return d(100);	
	}

	// multiple dice roll
	
  // sum of best r from n s-sided dice
	public static int best(int r, int n, int s) {
    if ((n<=0)||(r<0)||(r>n)||(s<0)) return 0;
    
    int[] rolls=new int[n];
    for (int i=0; i<n; i++) rolls[i]=d(s);
    
    boolean found;
    do {
      found=false;
      for (int x=0; x<n-1; x++) {
        if (rolls[x]<rolls[x+1]) {
          int t=rolls[x];
          rolls[x]=rolls[x+1];
          rolls[x+1]=t;
          found=true; 
        } 
      }
    } while (found);
    
    int sum=0;
    for (int i=0; i<r; i++) sum+=rolls[i];
    return sum; 
  }
  
  // calculates the sum of (number) s-sided dice
  public static int d(int number ,int sides) {
	  int total=0;
	  for (int i=0; i<number; i++) {
	    total+=d(sides);	
	  }
	  return total;
	}	
  
  // calculates the index of a in aa
  public static final int index(int a, int[] aa) {
    for (int i=0; i<aa.length; i++) {
      if (aa[i]==a) return i; 
    } 
    return -1;
  }

	public static int percentile(int var, int base) {
		// Don't divide by zero.
		if (base == 0) { return 0; }
		
		// Make the percentile
		int p = var*100/base;
		
		// Sometimes very small percentages (0.80%) will be rounded down
		// to 0, which, if var is not 0, may look funky.  Under this condition,
		// make the percentile 1%.
		if ((var > 0) && (p == 0)) {
			p = 1;
		}
		
		return p;
	}
  
  public static Object[] subList(Object[] list, Class c) {
    Object[] temp = new Object[list.length];
    int tempcount=0;
    for (int i=0; i<list.length; i++) {
      Object t=list[i];
      if (c.isInstance(t)) {
        temp[tempcount]=t;
        tempcount++;  
      }
    }
    Object[] result = new Object[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;      
  } 
}