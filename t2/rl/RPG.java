// The RPG classes contains:
//
// - Definition of all statistic codes
// - Definition of damage types
// - Random number routines

package rl;

import java.util.Random;
import java.util.ArrayList;

public class RPG
{

	// RPG CONSTANTS

	// damage types
	public final static int DT_SPECIAL             = -1;// unmodifiable
	public final static int DT_NORMAL              = 0;// standard damage
	public final static int DT_UNARMED             = 1;// soft impact
	public final static int DT_FIRE                = 2;// burning damage
	public final static int DT_ICE                 = 3;// freezing damage
	public final static int DT_PIERCING            = 4;// reduce armour
	public final static int DT_ACID                = 5;// contact acid
	public final static int DT_MAGIC               = 6;// disintegration etc.
	public final static int DT_IMPACT              = 7;// hard impact
	public final static int DT_CHILL               = 8;// demonic chill
	public final static int DT_POISON              = 9;// poison (no ARM)
	public final static int DT_DRAIN               = 10;// MP draining
	public final static int DT_DISPELL             = 11;// dispell magic

	// wield types
	public final static int WT_ERROR               = -1;
	public final static int WT_NONE                = 0;
	public final static int WT_MAINHAND            = 1;
	public final static int WT_SECONDHAND          = 2;
	public final static int WT_TWOHANDS            = 3;
	public final static int WT_RIGHTRING           = 4;
	public final static int WT_LEFTRING            = 5;
	public final static int WT_NECK                = 6;
	public final static int WT_HANDS               = 7;
	public final static int WT_BOOTS               = 8;
	public final static int WT_TORSO               = 9;
	public final static int WT_LEGS                = 10;
	public final static int WT_HEAD                = 11;
	public final static int WT_CLOAK               = 12;
	public final static int WT_FULLBODY            = 13;
	public final static int WT_BRACERS             = 14;
	public final static int WT_BELT                = 15;

	public final static int WT_RANGEDWEAPON        = 20;
	public final static int WT_MISSILE             = 21;

	public final static int WT_EFFECT              = 100;

	// creature state slot
	public final static int ST_STATE               = 0;

	// statistic numbers
	public final static int ST_SK                  = 1;
	public final static int ST_ST                  = 2;
	public final static int ST_AG                  = 3;
	public final static int ST_TG                  = 4;
	public final static int ST_IN                  = 5;
	public final static int ST_WP                  = 6;
	public final static int ST_CH                  = 7;
	public final static int ST_CR                  = 8;

	// current progression
	public final static int ST_LEVEL               = 11;
	public final static int ST_EXP                 = 12;
	public final static int ST_FATE                = 13;
	public final static int ST_FAME                = 14;
	public final static int ST_HUNGER              = 15;
	public final static int ST_SKILLPOINTS         = 16;

	// charcter class skills
	public final static int ST_RANGER              = 21;
	public final static int ST_FIGHTER             = 22;
	public final static int ST_THIEF               = 23;
	public final static int ST_PRIEST              = 24;
	public final static int ST_SCHOLAR             = 25;
	public final static int ST_MAGE                = 26;
	public final static int ST_BARD                = 27;
	public final static int ST_ARTISAN             = 28;

	// state values (mostly calculated)
	public final static int ST_ENCUMBERANCE        = 101;// 0=unencumbered 10=completely stuffed
	public final static int ST_MAXWEIGHT           = 102;// in grams!

	// ranger skills 21xx
	public final static int ST_RIDING              = 2101;
	public final static int ST_ARCHERY             = 2102;
	public final static int ST_ATHLETICS           = 2103;
	public final static int ST_THROWING            = 2104;
	public final static int ST_TRACKING            = 2106;
	public final static int ST_CLIMBING            = 2107;
	public final static int ST_DODGE               = 2108;
	public final static int ST_SWIMMING            = 2109;
	public final static int ST_SURVIVAL            = 2110;

	// fighter skills 22xx
	public final static int ST_ATTACK              = 2201;
	public final static int ST_DEFENCE             = 2202;
	public final static int ST_PARRY               = 2203;
	public final static int ST_BERSERK             = 2204;
	public final static int ST_FEROCITY            = 2205;
	public final static int ST_UNARMED             = 2206;
	public final static int ST_MIGHTYBLOW          = 2207;
	public final static int ST_BRAVERY             = 2208;
	public final static int ST_WEAPONLORE          = 2209;
	public final static int ST_TACTICS             = 2210;

	// thief skills 23xx
	public final static int ST_ALERTNESS           = 2301;
	public final static int ST_PICKPOCKET          = 2302;
	public final static int ST_PICKLOCK            = 2303;
	public final static int ST_DISARM              = 2304;
	public final static int ST_SEARCHING           = 2305;

	// priest skills 24xx
	public final static int ST_PRAYER              = 2401;
	public final static int ST_HOLYMAGIC           = 2402;
	public final static int ST_MEDITATION          = 2403;
	public final static int ST_HEALING             = 2403;

	// Scholar skills 25xx
	public final static int ST_LITERACY            = 2501;
	public final static int ST_IDENTIFY            = 2502;
	public final static int ST_ALCHEMY             = 2503;
	public final static int ST_LANGUAGES           = 2504;
	public final static int ST_RUNELORE            = 2505;
	public final static int ST_HERBLORE            = 2506;
	public final static int ST_STRATEGY            = 2507;

	// mage skills 26xx
	public final static int ST_BLACKMAGIC          = 2601;
	public final static int ST_TRUEMAGIC           = 2602;
	public final static int ST_BATTLEMAGIC         = 2603;
	public final static int ST_GOBLINMAGIC         = 2604;
	public final static int ST_MAGICRESISTANCE     = 2605;
	public final static int ST_CASTING             = 2606;
	public final static int ST_ANCIENTMAGIC        = 2607;

	// bard skills 27xx
	public final static int ST_MUSIC               = 2701;
	public final static int ST_CON                 = 2702;
	public final static int ST_PERCEPTION          = 2703;
	public final static int ST_SLEIGHT             = 2704;
	public final static int ST_STORYTELLING        = 2705;
	public final static int ST_SEDUCTION           = 2706;

	// artisan skills 28xx
	public final static int ST_PAINTING            = 2801;
	public final static int ST_SMITHING            = 2802;
	public final static int ST_APPRAISAL           = 2803;
	public final static int ST_WOODWORK            = 2804;
	public final static int ST_ROPEWORK            = 2805;
	public final static int ST_CONSTRUCTION        = 2806;
	public final static int ST_CARRYING            = 2807;
	public final static int ST_TRADING             = 2808;

	// general and miscellaneous skills 29xx
	public final static int ST_ALIGNMENT           = 2901;// +good -evil
	public final static int ST_ORDER               = 2902;// +lawful -chaotic

	// movement and action costs
	// 100 = normal
	// 200 = double speed
	// etc...
	public final static int ST_MOVESPEED           = 2911;
	public final static int ST_ATTACKSPEED         = 2912;
	public final static int ST_MAGICSPEED          = 2913;
	public final static int ST_MOVECOST            = 2921;
	public final static int ST_ATTACKCOST          = 2922;

	// For ethereal creatures set ST_ETHEREAL > 0
	public final static int ST_ETHEREAL            = 3001;

	// for undead:
	//   1 = lesser undead (skeletons, zombies)
	//   2 = greater undead (vampires, mummies, wights)
	//   3 = ethereal undead (ghosts, spectres, phantoms)
	public final static int ST_UNDEAD              = 3002;

	// resistances   dam=(dam*2^(-RESIST/6))
	public final static int ST_RESIST_BASE         = 5000;
	public final static int ST_RESISTUNARMED       = ST_RESIST_BASE + DT_UNARMED;// soft impact
	public final static int ST_RESISTFIRE          = ST_RESIST_BASE + DT_FIRE;// burning damage
	public final static int ST_RESISTICE           = ST_RESIST_BASE + DT_ICE;// freezing damage
	public final static int ST_RESISTPIERCING      = ST_RESIST_BASE + DT_PIERCING;// reduce armour
	public final static int ST_RESISTACID          = ST_RESIST_BASE + DT_ACID;// contact acid
	public final static int ST_RESISTMAGIC         = ST_RESIST_BASE + DT_MAGIC;// disintegration etc.
	public final static int ST_RESISTIMPACT        = ST_RESIST_BASE + DT_IMPACT;// hard impact
	public final static int ST_RESISTCHILL         = ST_RESIST_BASE + DT_CHILL;// demonic chill
	public final static int ST_RESISTPOISON        = ST_RESIST_BASE + DT_POISON;// poison (no ARM)
	public final static int ST_RESISTDRAIN         = ST_RESIST_BASE + DT_DRAIN;// MP draining
	public final static int ST_RESISTDISPELL       = ST_RESIST_BASE + DT_DISPELL;// MP draining

	// protection from physical damage
	public final static int ST_PROTECTION          = 5999;

	// armour      dam=dam*dam/(dam+arm)
	public final static int ST_ARM_BASE            = 6000;
	public final static int ST_ARMNORMAL           = ST_ARM_BASE + DT_NORMAL;// soft impact
	public final static int ST_ARMUNARMED          = ST_ARM_BASE + DT_UNARMED;// soft impact
	public final static int ST_ARMFIRE             = ST_ARM_BASE + DT_FIRE;// burning damage
	public final static int ST_ARMICE              = ST_ARM_BASE + DT_ICE;// freezing damage
	public final static int ST_ARMPIERCING         = ST_ARM_BASE + DT_PIERCING;// reduce armour
	public final static int ST_ARMACID             = ST_ARM_BASE + DT_ACID;// contact acid
	public final static int ST_ARMMAGIC            = ST_ARM_BASE + DT_MAGIC;// disintegration etc.
	public final static int ST_ARMIMPACT           = ST_ARM_BASE + DT_IMPACT;// hard impact
	public final static int ST_ARMCHILL            = ST_ARM_BASE + DT_CHILL;// demonic chill
	public final static int ST_ARMPOISON           = ST_ARM_BASE + DT_POISON;// poison (no ARM)
	public final static int ST_ARMDRAIN            = ST_ARM_BASE + DT_DRAIN;// MP draining
	public final static int ST_ARMDISPELL          = ST_ARM_BASE + DT_DISPELL;// dispell magic

	// item protection      dam=dam*dam/(dam+PROTECT)
	public final static int ST_PROTECT_BASE        = 7000;
	public final static int ST_PROTECTNORMAL       = ST_PROTECT_BASE + DT_NORMAL;// soft impact
	public final static int ST_PROTECTUNPROTECTED  = ST_PROTECT_BASE + DT_UNARMED;// soft impact
	public final static int ST_PROTECTFIRE         = ST_PROTECT_BASE + DT_FIRE;// burning damage
	public final static int ST_PROTECTICE          = ST_PROTECT_BASE + DT_ICE;// freezing damage
	public final static int ST_PROTECTPIERCING     = ST_PROTECT_BASE + DT_PIERCING;// reduce PROTECTour
	public final static int ST_PROTECTACID         = ST_PROTECT_BASE + DT_ACID;// contact acid
	public final static int ST_PROTECTMAGIC        = ST_PROTECT_BASE + DT_MAGIC;// disintegration etc.
	public final static int ST_PROTECTIMPACT       = ST_PROTECT_BASE + DT_IMPACT;// hard impact
	public final static int ST_PROTECTCHILL        = ST_PROTECT_BASE + DT_CHILL;// demonic chill
	public final static int ST_PROTECTPOISON       = ST_PROTECT_BASE + DT_POISON;// poison (no PROTECT)
	public final static int ST_PROTECTDRAIN        = ST_PROTECT_BASE + DT_DRAIN;// MP draining
	public final static int ST_PROTECTDISPELL      = ST_PROTECT_BASE + DT_DISPELL;// dispell magic

	// current and max points
	public final static int ST_APS                 = 10001;
	public final static int ST_APSMAX              = 10002;
	public final static int ST_HPS                 = 10003;
	public final static int ST_HPSMAX              = 10004;
	public final static int ST_MPS                 = 10005;
	public final static int ST_MPSMAX              = 10006;

	// attack skills
	public final static int ST_ASK                 = 10020;
	public final static int ST_ASKMULTIPLIER       = 10021;
	public final static int ST_ASKBONUS            = 10022;

	public final static int ST_AST                 = 10025;
	public final static int ST_ASTMULTIPLIER       = 10026;
	public final static int ST_ASTBONUS            = 10027;

	// defence skills
	public final static int ST_DSK                 = 10030;
	public final static int ST_DSKMULTIPLIER       = 10031;
	public final static int ST_DSKBONUS            = 10032;
	public final static int ST_DODGEMULTIPLIER     = 10040;
	public final static int ST_DSKPARRY            = 10041;

	public final static int ST_REGENERATE          = 10051;
	public final static int ST_RECHARGE            = 10052;

	// ranged skills
	public final static int ST_RSKBONUS            = 10060;
	public final static int ST_RSKMULTIPLIER       = 10061;
	public final static int ST_RSTBONUS            = 10062;
	public final static int ST_RSTMULTIPLIER       = 10063;

	public final static int ST_RANGE               = 10070;

	// special stats
	public final static int ST_ANTIMAGIC           = 10101;

	public final static int ST_SCORE               = 11000;
	public final static int ST_SCORE_BESTKILL      = 11001;
	public final static int ST_SCORE_BESTLEVEL     = 11002;
	public final static int ST_SCORE_KILLS         = 11003;

	// Item stat flags
	public final static int ST_ITEMBASE            = 20000;
	public final static int ST_ITEMRARITY          = ST_ITEMBASE + 1;
	public final static int ST_ITEMVALUE           = ST_ITEMBASE + 2;
	public final static int ST_ITEMFRAGILITY       = ST_ITEMBASE + 3;

	public final static int ST_MISSILETYPE         = ST_ITEMBASE + 10;

	// AI helper stats
	public final static int ST_AIMODE              = 30000;
	public final static int ST_TARGETX             = 30001;
	public final static int ST_TARGETY             = 30002;
	public final static int ST_CASTCHANCE          = 30003;

	public final static int ST_SIDE                = 30010;

	// Quest details
	public final static int ST_QUESTNUMBER         = 31000;
	public final static int ST_QUESTSTATE          = 31001;

	// Physical state
	public final static int ST_IMMOBILIZED         = 32001;
	public final static int ST_CONFUSED            = 32002;
	public final static int ST_BLINDED             = 32003;
	public final static int ST_PANICKED            = 32004;
	public final static int ST_NON_DISPLACEABLE    = 32005;// can't displace if >0

	// special abilities
	public final static int ST_TRUEVIEW            = 33001;

	// material type flags
	public final static int MF_EDIBLE              = 2 << 15;
	public final static int MF_MAGICAL             = 2 << 16;
	public final static int MF_RESISTANT           = 2 << 17;
	public final static int MF_LIVING              = 2 << 18;
	public final static int MF_DIVINE              = 2 << 19;
	public final static int MF_SKIN                = 2 << 20;
	public final static int MF_STONE               = 2 << 21;
	public final static int MF_GLASS               = 2 << 22;
	public final static int MF_GEMSTONE            = 2 << 23;
	public final static int MF_METAL               = 2 << 24;
	public final static int MF_PLANT               = 2 << 25;
	public final static int MF_CLOTH               = 2 << 26;
	public final static int MF_INDESTRUCTIBLE      = 2 << 27;
	public final static int MF_LIQUID              = 2 << 28;
	public final static int MF_GASEOUS             = 2 << 29;
	public final static int MF_ETHEREAL            = 2 << 30;
	public final static int MF_PLASTIC             = 2 << 31;

	/*
	 *  Old material flags, functionality replaced by Material class
	 *  / MATERIALS
	 *  / flesh and bones
	 *  public static final int MAT_FLESH=1|MF_SKIN|MF_LIVING;
	 *  public static final int MAT_BONE=2;
	 *  public static final int MAT_LEATHER=3|MF_SKIN;
	 *  public static final int MAT_HIDE=4|MF_SKIN|MF_CLOTH;
	 *  / metals
	 *  public static final int MAT_BRONZE=101|MF_METAL;
	 *  public static final int MAT_SILVER=102|MF_METAL;
	 *  public static final int MAT_GOLD=103|MF_METAL;
	 *  public static final int MAT_IRON=104|MF_METAL;
	 *  public static final int MAT_STEEL=105|MF_METAL;
	 *  public static final int MAT_MITHRIL=106|MF_METAL|MF_MAGICAL;
	 *  public static final int MAT_KRITHIUM=107|MF_METAL|MF_RESISTANT;
	 *  public static final int MAT_PARRIUM=108|MF_METAL|MF_INDESTRUCTIBLE;
	 *  / stone and minerals
	 *  public static final int MAT_STONE=201;
	 *  public static final int MAT_GRANITE=202;
	 */
	// missile types
	public final static int MISSILE_THROWN         = 0;
	public final static int MISSILE_ARROW          = 1;
	public final static int MISSILE_BOLT           = 2;
	public final static int MISSILE_STONE          = 3;

	// some standard description for internal use
	public final static Description DESC_GENERIC   =
		new Describer("thing", "A generic thing. Don't ask.");

	// stat power series
	public final static int[] POWER                = {2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10, 11, 13, 14, 16, 18, 20, 22, 25, 28, 32, 36, 40, 45, 51, 57, 64, 72, 81, 91, 102, 114, 128, 144, 161, 181, 203, 228, 256, 287, 323, 362, 406, 456, 512, 575, 645, 724, 813, 912, 1024, 1149, 1290, 1448, 1625, 1825, 2048, 2299, 2580, 2896, 3251, 3649, 4096};


	// KULT RPG FUNCTIONS

	// U[0,1] distribution
	public static float random()
	{
		return rand.nextFloat();
	}

	public static boolean test(int a, int b)
	{
		if(a <= 0)
			return false;
		if(b <= 0)
			return true;
		return rand.nextInt(a + b) < a;
	}

	public static int hitFactor(int a, int b)
	{
		int factor  = 0;
		while(test(a, RPG.max(1, b)))
		{
			factor += 1;
			a /= 2;
		}
		return factor;
	}

	public static int power(int a)
	{
		if((a < 0) || (a > 66))
			return (int)Math.pow(2, 1 + ((double)a) / 6);
		return POWER[a];
	}

	public static int level(int a)
	{
		if(a < 1)
			return -1000000;
		if(a == 1)
			return -6;
		if(a > 66)
			return (int)Math.round(Math.log(a) * 8.656170245 - 6);
		int i  = 0;
		while(POWER[i] < a)
			i++;
		return i;
	}

	// LOTS OF RANDOM FUNCTIONALITY

	private static KultRandom rand                 = new KultRandom();

	public static void setRandSeed(long n)
	{
		rand.setSeed(n);
	}

	// simulates a dice roll with the given number of sides
	public final static int d(int sides)
	{
		if(sides <= 0)
			return 0;
		return rand.nextInt(sides) + 1;
	}

	// USEFUL FUNCTIONS

	// return the squared distance between points
	public final static int dist(int x1, int y1, int x2, int y2)
	{
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	// return the radius equivalent of squard distance
	// rounds radius down (i.e. radius(r2)*radius(r2) <=r2)
	public final static int radius(int r2)
	{
		int r  = 0;
		while((r * r) <= r2)
			r++;
		return r - 1;
	}

	// pick a random integer from an array
	public final static int pick(int[] a)
	{
		return a[RPG.r(a.length)];
	}

	// pick a random string from an array
	public final static String pick(String[] a)
	{
		return a[RPG.r(a.length)];
	}


	// return the min value
	public final static int min(int a, int b)
	{
		return (a < b) ? a : b;
	}

	// return the min value
	public final static int max(int a, int b)
	{
		return (a > b) ? a : b;
	}

	// return the middle value
	public final static int middle(int a, int b, int c)
	{
		if(a > b)
		{
			if(b > c)
				return b;
			if(a > c)
				return c;
			return a;
		}
		else
		{
			if(a > c)
				return a;
			if(b > c)
				return c;
			return b;
		}
	}

	// round number to reasonable figure
	public final static int niceNumber(int x)
	{
		int p  = 1;
		while(x >= 100)
		{
			x /= 10;
			p *= 10;
		}
		if(x > 30)
			x = 5 * (x / 5);
		return x * p;
	}

	// returns exponentially distributed thing with mean n
	// increment probability p=n/n+1
	public static int e(int n)
	{
		if(n <= 0)
			return 0;
		int result  = 0;
		while((rand.nextInt() % (n + 1)) != 0)
			result++;
		return result;
	}

	// Poisson distribution
	public static int po(double x)
	{
		int r     = 0;
		double a  = rand.nextDouble();
		if(a >= 0.99999999)
			return 0;
		double p  = Math.exp(-x);
		while(a >= p)
		{
			r++;
			a = a - p;
			p = p * x / r;
		}
		return r;
	}

	public static int po(int numerator, int denominator)
	{
		return po(((double)numerator) / denominator);
	}

	// handy likelihood functions
	public static boolean sometimes()
	{
		return (rand.nextFloat() < 0.1);
	}

	public static boolean often()
	{
		return (rand.nextFloat() < 0.4);
	}

	public static boolean rarely()
	{
		return (rand.nextFloat() < 0.01);
	}

	public static boolean usually()
	{
		return (rand.nextFloat() < 0.8);
	}

	// return integer evenly distributed in range
	public static int rspread(int a, int b)
	{
		if(a > b)
		{
			int t  = a;
			a = b;
			b = t;
		}
		return rand.nextInt(b - a + 1) + a;
	}

	public final static int sign(double a)
	{
		return (a < 0) ? -1 : ((a > 0) ? 1 : 0);
	}

	public final static int sign(int a)
	{
		return (a < 0) ? -1 : ((a > 0) ? 1 : 0);
	}

	public final static int abs(int a)
	{
		return (a >= 0) ? a : -a;
	}

	public final static int r(int s)
	{
		return rand.nextInt(s);
	}

	// distribution for damage etc. mean a
	public final static int a(int s)
	{
		return r(s + 1) + r(s + 1);
	}

	// All the dice

	public final static int d3()
	{
		return d(3);
	}

	public final static int d4()
	{
		return d(4);
	}

	public final static int d6()
	{
		return d(6);
	}

	public final static int d8()
	{
		return d(8);
	}

	public final static int d10()
	{
		return d(10);
	}

	public final static int d12()
	{
		return d(12);
	}

	public final static int d20()
	{
		return d(20);
	}

	public final static int d100()
	{
		return d(100);
	}

	// multiple dice roll

	// sum of best r from n s-sided dice
	public static int best(int r, int n, int s)
	{
		if((n <= 0) || (r < 0) || (r > n) || (s < 0))
			return 0;

		int[] rolls    = new int[n];
		for(int i = 0; i < n; i++)
			rolls[i] = d(s);

		boolean found;
		do
		{
			found = false;
			for(int x = 0; x < n - 1; x++)
				if(rolls[x] < rolls[x + 1])
				{
					int t  = rolls[x];
					rolls[x] = rolls[x + 1];
					rolls[x + 1] = t;
					found = true;
				}

		}while (found);

		int sum        = 0;
		for(int i = 0; i < r; i++)
			sum += rolls[i];
		return sum;
	}

	// calculates the sum of (number) s-sided dice
	public static int d(int number, int sides)
	{
		int total  = 0;
		for(int i = 0; i < number; i++)
			total += d(sides);

		return total;
	}

	// calculates the index of a in aa
	public final static int index(int a, int[] aa)
	{
		for(int i = 0; i < aa.length; i++)
			if(aa[i] == a)
				return i;
		return -1;
	}

	public static int percentile(int var, int base)
	{
		// Don't divide by zero.
		if(base == 0)
			return 0;

		// Make the percentile
		int p  = var * 100 / base;

		// Sometimes very small percentages (0.80%) will be rounded down
		// to 0, which, if var is not 0, may look funky.  Under this condition,
		// make the percentile 1%.
		if((var > 0) && (p == 0))
			p = 1;

		return p;
	}

	public static Object[] subList(Object[] list, Class c)
	{
		Object[] temp    = new Object[list.length];
		int tempcount    = 0;
		for(int i = 0; i < list.length; i++)
		{
			Object t  = list[i];
			if(c.isInstance(t))
			{
				temp[tempcount] = t;
				tempcount++;
			}
		}
		Object[] result  = new Object[tempcount];
		if(tempcount > 0)
			System.arraycopy(temp, 0, result, 0, tempcount);
		return result;
	}

	public static int EvaluateValueExpression(String notation)
	{
		ArrayList al     = new ArrayList();
		int i            = 0;
		notation = notation.replaceAll("\\x2B", " + ");
		notation = notation.replaceAll("-", " - ");
		notation = notation.replaceAll("\\x2A", " * ");
		notation = notation.replaceAll("/", " / ");
		notation = notation.replaceAll("\\x5E", " ^ ");
		notation = notation.replaceAll("  ", " ");
		String[] pieces  = notation.split(" ");

		for(i = 0; i < pieces.length; i++)
			al.add(simplify(pieces[i]));

		for(i = 1; i < al.size(); )
			if(((String)al.get(i)).equals("*"))
			{
				al.set(i, String.valueOf(ale_int(al, i - 1) * ale_int(al, i + 1)));
				al_removeNeighbours(al, i);
			}
			else if(((String)al.get(i)).equals("/"))
			{
				al.set(i, String.valueOf(ale_int(al, i - 1) / ale_int(al, i + 1)));
				al_removeNeighbours(al, i);
			}
			else
				i++;

		for(i = 1; i < al.size(); )
			if(((String)al.get(i)).equals("+"))
			{
				al.set(i, String.valueOf(ale_int(al, i - 1) + ale_int(al, i + 1)));
				al_removeNeighbours(al, i);
			}
			else if(((String)al.get(i)).equals("-"))
			{
				al.set(i, String.valueOf(ale_int(al, i - 1) - ale_int(al, i + 1)));
				al_removeNeighbours(al, i);
			}
			else
				i++;

		return (Integer.parseInt((String)al.get(0)));
	}

	private static int ale_int(ArrayList al, int i)
	{
		return Integer.parseInt((String)al.get(i));
	}

	private static void al_removeNeighbours(ArrayList al, int i)
	{
		al.remove(i - 1);
		al.remove(i);
	}

	public final static String simplify(String e)
	{
		if(e.indexOf("d") == -1 && e.indexOf("D") == -1)
			return (e);
		String[] parts  = e.split("d|D");
		return Integer.toString(d(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
	}

}
