// A magic scroll
//
// If read, it will cast the spell desribed upon it
//
// Since scrolls are single-use, they are useful for giving the hero
// access to powerful or unusual spells that they must use wisely.
//
// Scrolls may also contain spells which will never be available to 
// the player by other means, e.g. uncurse weapon.

package rl;

public class Scroll extends Stack implements Description {
	private static final Description DESC_SCROLL=
		new Describer("scroll","A magical scroll");
	
	// titles for scrolls
	protected static final String[] titles = {
		"thgil eb ereht tel",
		"gniht yxes ouy",
		"ybab atsiv al atsah",
		"senob meht fo elip dlo gib",
		"ekul ecrof eht esu",
		"strohs ym tae",
		"eno ylno eb nac ereht",
		"evah reven nac i gnihtemos",
		"eloh a ekil daeh",
		"ereh morf bup eht ees nac i",
		"naeb a dna sbeeh owt hsart etihw",
		"siht tuoba gnileef egnarts a teg i",
		"gniht taht ezis eht ta kool",
		"elbmur ot ydaer teg stel",
		"kcab eb lli",
		"em ta gnikool uoy era",
		"su ot gnoleb era esab ruoy lla",
		"su ot gnoleb era srevres ruoy lla",
		"su ot gnoleb era slous ruoy lla",
		"su ot gnoleb era sllorcs ruoy lla",
		"su ot gnoleb si lirhtim ruoy lla",
		"dnuora uoy deciton evi",
		"nosredna leahcim yb tnaryt",
		"tyumed siroj mot yb ii tnaryt",
		"ereh saw rennureldnurt eztlawfpmad",	
		"ereh saw toofgib daorb",
		"ereh saw rakabraz igons",
		"no og tsum wohs eht",
		"xinu ton sung",
		"xunil esu nem laer",
		"dnalrob ot xnaht",
		"nitram ot tcepser ffun",
		"ettolrahc dna sirhc gniddew yppah",
		"evol i eno eht ot detacided",
		"uoy htiw eb ecrof eht yam",
		"meht dnib to gnir eno",	 
		"meht elur to gnir eno"	 
 }; 
		 
	
	protected static final int[][] spells= {
		{Spell.IDENTIFY,Spell.LIGHTHEAL,Spell.ACIDBOLT},
		{Spell.RECHARGE,Spell.HEAL,Spell.FLAME},	 
		{Spell.IDENTIFY,Spell.HEAL,Spell.POISONCLOUD,Spell.LAYRUNE},	 
		{Spell.BROOMSTICK,Spell.HEAL,Spell.FLAMECLOUD,Spell.REPAIR},	 
		{Spell.IDENTIFY,Spell.REMOVECURSE,Spell.FIREBALL},	 
		{Spell.RUNETRAP,Spell.REPAIR,Spell.BLAST,Spell.TELEPORT},	 
		{Spell.IDENTIFY,Spell.RECHARGE,Spell.BLAZE,Spell.BECOMEETHEREAL},	 
		{Spell.IDENTIFY,Spell.REMOVECURSE,Spell.STRONGHEAL,Spell.FIREDART},	 
		{Spell.IDENTIFY,Spell.INFESTATION,Spell.TELEPORT,Spell.ACIDBLAST},	 
		{Spell.POWERHEAL,Spell.RECHARGE,Spell.FIREPATH},	 
		{Spell.IDENTIFY,Spell.ENCHANT,Spell.DEVASTATE},	 
		{Spell.IDENTIFY,Spell.SUMMON,Spell.ENCHANT,Spell.DEATHRAY},
		{Spell.IDENTIFY,Spell.TOTALHEALING,Spell.TELEPORT,Spell.BECOMEETHEREAL}
	};
				 
	public Spell spell;			 
														
	// create scroll of approximate level value
	public static Stack createScroll(int level) {
		if (RPG.d(2)==1) {
			// create from common scroll types
			level=level+RPG.d(5)-RPG.d(5);
			level=RPG.middle(0,level,spells.length-1);
			return new Scroll(spells[level][RPG.r(spells[level].length)],1+RPG.r(2)*RPG.r(4));
		} else {
			// let's have a random spell!!
			// probably better, so give less on average
			return new Scroll(Spell.randomSpell(level+RPG.r(6)),1+RPG.r(2)*RPG.r(3)); 
		}
	}
	
	public Scroll(int t, int num) {
		super(num);
		type=t;
		spell=new Spell(t);
	}
	
	public int damage(int dam, int damtype) {
		switch(damtype) {
			case RPG.DT_FIRE: case RPG.DT_DISPELL: case RPG.DT_ACID:
				dam=dam*5;
			case RPG.DT_NORMAL: case RPG.DT_PIERCING: case RPG.DT_SPECIAL:
				break;
			case RPG.DT_ICE: case RPG.DT_IMPACT: case RPG.DT_UNARMED:
				dam=dam/2;
				break;
			default:	
				dam=0;
		}
		
		if (!RPG.test(dam,3)) return 0;
 		ThingOwner p=place;
 		remove(1);
		if (p instanceof Map) {
		 	spell.castAtLocation(this,(Map)p,x,y);	
		}
		return dam;
	}
	
	public int getUse() {return Thing.USE_NORMAL|Thing.USE_READ;}
	
	public int getWeight() {return 200;}
	
	public int getStat(int s) {
		switch(s) {
			case RPG.ST_ITEMVALUE: return isIdentified()?(100*spell.level*spell.level):600;
			default: return super.getStat(s); 
		} 
	}
	
	public Spell getSpell() {return spell;}
	
	public void use(Being user) {
		use(user, USE_READ);
	}
	
	public void use(Being user, int usetype) {
		if (usetype!=USE_READ) return;
		
		boolean ishero=(user==Game.hero);
		if (!ishero) return;
		
		if (user.getStat(RPG.ST_LITERACY)>0) {
			identify(1000);
			QuestApp.questapp.screen.castSpell(spell); 
			remove(1);
		} else {
			Game.message("You can't read!");	
		}

	}

	public Description getDescription() {
		return isIdentified()?this:DESC_SCROLL;
	}
	
	public int getImage() {
		return 280+type%4; 
	}
	
	// Description interface 
	public String getName(int number, int article)	{
		return Describer.getArticleName(spell.getName()+((number>1)?" scrolls":" scroll"),number,article);	
	}
	
 	public String getPronoun(int number, int acase) {
 		return (number==1)?"it":"them";	
 	}
 	
	public String getDescriptionText() {
		return DESC_SCROLL.getDescriptionText()+" ("+Text.capitalise(getName(1,ARTICLE_INDEFINITE))+")";
	}
}