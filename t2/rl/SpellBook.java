//
// When read, the SpellBook confers the ability to cast a particular spell
// on the reader if they are sufficiently skilled
//

package rl;

public class SpellBook extends Item implements Description {
	private static final Description DESC_BOOK=
		new Describer("book","A slightly tattered book");
	private static final Description DESC_WORNBOOK=
		new Describer("book","A worn book");
	private static final Description DESC_DUSTYTOME=
		new Describer("tome","A dusty tome");
	private static final Description DESC_HEAVYTOME=
		new Describer("tome","A heavy tome");
	private static final Description DESC_WORNSPELLBOOK=
		new Describer("spell book","A worn spell book");
	 
	protected Spell spell;
	protected Description desc;
	//There is 50% chance that the cover states what the book is about
	//this makes a 50% chance for literate people to find out what the
	//book is about.
	protected boolean readableCover;
	
	public int getUse() {return USE_NORMAL|USE_READ;}
	
	public SpellBook() {
		this(1000); 
	}

	public SpellBook(int p_spell, int booktype){
		switch( booktype ){
			case 0: desc=DESC_DUSTYTOME;		image=286;break;
			case 1: desc=DESC_BOOK;				image=287;break;
			case 2: desc=DESC_WORNBOOK;			image=284;break;
			case 3: desc=DESC_DUSTYTOME;		image=286;break;
			case 4: desc=DESC_WORNSPELLBOOK;	image=285;break;
			case 5: desc=DESC_WORNSPELLBOOK;	image=285;break;
			case 6: desc=DESC_HEAVYTOME;		image=285+RPG.r(2);break;
		}
		spell = new Spell(p_spell);
		decideReadableCover();
	}

	public SpellBook(int level) {
		switch (RPG.d(6)) {
			case 1:
				desc=DESC_BOOK;	image=287;
				switch (RPG.d(6)) {
					case 1: spell=new Spell(Spell.HEAL); break;
					case 2: spell=new Spell(Spell.LIGHTHEAL); break;
					case 3: spell=new Spell(Spell.STRONGHEAL); break;
					case 4: spell=new Spell(Spell.ACIDBLAST); break;
					default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;
			case 2:
				desc=DESC_WORNBOOK;	image=284;
				switch (RPG.d(5)) {
					case 1: spell=new Spell(Spell.HEAL); break;
					case 2: spell=new Spell(Spell.FIREBALL); break;
					case 3: spell=new Spell(Spell.POISON); break;
				 default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;				
			case 3:
				desc=DESC_DUSTYTOME; image=286;	
				switch (RPG.d(5)) {
					case 1: spell=new Spell(Spell.TELEPORT); break;
					case 2: spell=new Spell(Spell.RECHARGE); break;
					case 3: spell=new Spell(Spell.REPAIR); break;
					default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;				
			case 4:
				desc=DESC_WORNSPELLBOOK; image=285;	
				switch (RPG.d(8)) {
					case 1: spell=new Spell(Spell.FORCEBOLT); break;
					case 2: spell=new Spell(Spell.POISONBOLT); break;
					case 3: spell=new Spell(Spell.FLAME); break;
					case 4: spell=new Spell(Spell.THUNDERBOLT); break;
					case 5: spell=new Spell(Spell.MISSILE); break;
				 default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;				
			case 5:
				desc=DESC_WORNSPELLBOOK; image=285;	
				switch (RPG.d(4)) {
					case 1: spell=new Spell(Spell.BLAST); break;
					case 2: spell=new Spell(Spell.BLAZE); break;
					case 3: spell=new Spell(Spell.FLAMECLOUD); break;
					default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;				
			case 6:
				desc=DESC_HEAVYTOME; image=285+RPG.r(2);	
				switch (RPG.d(4)) {
					case 1: spell=new Spell(Spell.HASTE); break;
					case 2: spell=new Spell(Spell.SLOW); break;
					case 3: spell=new Spell(Spell.ARMOUR); break;
					default: spell=new Spell(Spell.randomSpell(level)); break;
				}
				break;				
		}
		decideReadableCover();
	}

	private void decideReadableCover(){
		if( RPG.r(2) == 1 ){
			readableCover = true;
		}
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

		if (!RPG.test(dam,13)) return 0;
		Map map=getMap();
		int tx=getMapX();
		int ty=getMapY();
		remove();
		if (map!=null) {
		 	spell.castAtLocation(null,map,tx,ty);	
		}
		return dam;
	}
	
	public int getWeight() {return 1000;}
	
	public Spell getSpell() {return spell;}
	
	public void use(Being user) {
		use(user, USE_READ);
	}
	
	public void use(Being user, int usetype) {
		if (usetype!=USE_READ) return;
		
		Hero h=Game.hero;
		// see if book can be read
		if (h.getStat(RPG.ST_LITERACY)>0) {
			if (spell.canLearn(h)) {
				Spell s;
				s=(Spell)(getSpell().clone());
			
				Art added=h.arts.addArt(s);
				if (added==s) {
					Game.message("Your mind is filled with mystic energies!");
					Game.message("You learn the "+s.getName()+" spell");	
					flags=flags|ITEM_IDENTIFIED;
				} else {
					if (!isIdentified()) Game.message("You recognise this as a spell book of "+spell.getName());
					else Game.message("You already know "+spell.getName());
					flags=flags|ITEM_IDENTIFIED;
				}
				if (RPG.d(2)==1) {
					Game.message("The spellbook vanishes in a puff of smoke.");
					remove();
				}
			} else {
				Game.message("The runes are too complex for you to decipher");
			}
		} else {	
			Game.message ("You can't read!");
		}
	}

	public int getStat(int s) {
		switch (s) {
			case RPG.ST_ITEMVALUE: return isIdentified()?(750*spell.level*spell.level):1500;
			default: return super.getStat(s); 
		} 
	}

	public Description getDescription() {return this;}
	
	// Description interface 
	public String getName(int number, int article)	{
		if (isIdentified()) return Describer.getArticleName((number>1)?"spell books of ":"spell book of "+spell.getName(),number,article);	
		else return desc.getName(number,article);
	}
	
 	public String getPronoun(int number, int acase) {
 		return "it";	
 	}
 	
	public String getDescriptionText() {
		if (!isIdentified()) return desc.getDescriptionText();
		else return desc.getDescriptionText()+" ("+Text.capitalise(getName(1,ARTICLE_INDEFINITE))+")";
	}

	public boolean isIdentified() {
		boolean b = super.isIdentified();
		Hero h=Game.hero;
		if(!b){
			if (h.getStat(RPG.ST_LITERACY)>0 && readableCover ) {
				Game.message("You recognize from the cover it's a spell book of " + spell.getName() );
				setIdentified(true);
				return true;
			}else{
				return false;
			}
		}else{
			return b;
		}
	}
}