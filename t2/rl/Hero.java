package rl;

import java.util.Vector;
import java.util.Hashtable;

public class Hero extends Being
{
	public String name;
	public String race;
	public String profession;
	public int gameseed;

	public Hashtable uniques             = new Hashtable();

	// set tick count at which hero becomes hungry
	public final static int HUNGERLEVEL  = 250000;

	// flag for whether we can recharge/regenerate naturally
	public boolean recharging            = true;

	private Description desc             =
		new Describer("you", "you", "Yourself, the brave hero of this adventure.", Description.NAMETYPE_PROPER, Description.GENDER_MALE);

	public String getName()
	{
		return name;
	}

	public Hero(String n, String r, String p)
	{
		super();
		name = n;

		ReaderOfRace ror   = new ReaderOfRace(r);
		race = ror.name;

		ReaderOfClass roc  = new ReaderOfClass(p);
		profession = roc.name;

		stats = new Stats(new int[]{AI.STATE_NEUTRAL, 0, 0, 0, 0, 0, 0, 0, 0});
		setAI(HeroAI.instance);

		// set up a game seed
		// use for game-specific settings
		gameseed = RPG.r(1000000000);

		addThing(Weapon.createWeapon(3));
		addThing(Food.createFood(Food.MEATRATION));

		// defaults
		stats.setStat(RPG.ST_MOVESPEED, 100);
		stats.setStat(RPG.ST_ATTACKSPEED, 100);
		stats.setStat(RPG.ST_RECHARGE, 10);
		stats.setStat(RPG.ST_REGENERATE, 3);
		stats.setStat(RPG.ST_FATE, 2);

		// use a large negative side
		// don't want this to clash with anything else
		stats.setStat(RPG.ST_SIDE, -999);

		// ****************
		//      Races
		// ****************

		if(race.equals("Human"))
		{
			// humans are the most common inhabitants in the world of Tyrant
			// they are good all-round characters

			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			addMoney(100 * RPG.d(4, 10));
		}

		if(race.equals("dwarf"))
		{
			// dwarves are sturdy and industrious cave dwellers
			// they are famed for their skill in smithing and mining

			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4) + 1);
			stats.incStat(RPG.ST_AG, RPG.d(1, 4));
			stats.incStat(RPG.ST_TG, RPG.d(3, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(3, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 6));
			stats.incStat(RPG.ST_CR, RPG.d(3, 4));

			addMoney(100 * RPG.d(5, 10));

			addSkill("Smithing", RPG.po(0.7));
			addSkill("Berserk", RPG.po(0.5));

			stats.incStat(RPG.ST_MOVESPEED, -10);
		}

		if(race.equals("hobbit"))
		{
			// hobbits are just three feet high
			// they are peaceful folk, renowned as farmers

			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(1, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(3, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(3, 4));
			stats.incStat(RPG.ST_CR, RPG.d(3, 4));

			addSkill("Herb Lore", 1 + RPG.po(1));
			addSkill("Storytelling", RPG.po(1.5));

			addMoney(10 * RPG.d(6, 10));

		}

		if(race.equals("high elf"))
		{
			// high elves are noble and wise

			stats.incStat(RPG.ST_SK, RPG.d(3, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 3));
			stats.incStat(RPG.ST_AG, RPG.d(3, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(3, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(3, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			stats.incStat(RPG.ST_MOVESPEED, 5);

			addMoney(100 * RPG.d(6, 10));

			addSkill("Literacy", 1 + RPG.po(0.5));

		}

		if(race.equals("wood elf"))
		{
			// wood elves are shy of other races
			// they are agile and talented archers

			stats.incStat(RPG.ST_SK, RPG.d(3, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(3, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 5));
			stats.incStat(RPG.ST_IN, RPG.d(2, 5));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 3));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			stats.incStat(RPG.ST_MOVESPEED, 10);

			addSkill("Literacy", RPG.po(0.5));

			addSkill("Archery", 1 + RPG.po(1));
		}

		if(race.equals("dark elf"))
		{
			// dark elves are vicious and powerful
			// they prefer throwing weapons, darts and shurikens

			stats.incStat(RPG.ST_SK, RPG.d(3, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(3, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 6));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(1, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			stats.incStat(RPG.ST_MOVESPEED, 5);
			stats.incStat(RPG.ST_ATTACKSPEED, 5);

			addMoney(100 * RPG.d(3, 10));

			addSkill("Literacy", RPG.po(0.5));

			addSkill("Literacy", 1 + RPG.po(1));
		}

		if(race.equals("gnome"))
		{
			// gnomes are disadvantage by their small size
			// they make up for this with igenuity

			stats.incStat(RPG.ST_SK, RPG.d(2, 3));
			stats.incStat(RPG.ST_ST, RPG.d(1, 4));
			stats.incStat(RPG.ST_AG, RPG.d(1, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(3, 4));
			stats.incStat(RPG.ST_WP, RPG.d(3, 4));
			stats.incStat(RPG.ST_CH, RPG.d(3, 4));
			stats.incStat(RPG.ST_CR, RPG.d(3, 4));

			stats.incStat(RPG.ST_MOVESPEED, -20);

			addMoney(100 * RPG.d(10, 10));

			addSkill("Literacy", RPG.po(1));
			addSkill("Appraisal", RPG.po(1));
		}

		if(race.equals("goblin"))
		{
			// goblins are mischevious, chaotic
			// give lots of nice randomness

			stats.incStat(RPG.ST_SK, RPG.d(1, 11));
			stats.incStat(RPG.ST_ST, RPG.d(1, 7));
			stats.incStat(RPG.ST_AG, RPG.d(1, 14));
			stats.incStat(RPG.ST_TG, RPG.d(1, 7));
			stats.incStat(RPG.ST_IN, RPG.d(1, 9));
			stats.incStat(RPG.ST_WP, RPG.d(1, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 9));
			stats.incStat(RPG.ST_CR, RPG.d(1, 9));

			stats.incStat(RPG.ST_MOVESPEED, RPG.d(10));

			addMoney(RPG.d(150) * RPG.d(150));

		}

		if(race.equals("orc"))
		{
			// orcs are related to goblins
			// but are larger, tougher and stupider

			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(3, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 3));
			stats.incStat(RPG.ST_TG, RPG.d(3, 4));
			stats.incStat(RPG.ST_IN, RPG.d(1, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 4));
			stats.incStat(RPG.ST_CR, RPG.d(1, 4));

			addMoney(100 * RPG.d(4, 10));
		}

		if(race.equals("troll"))
		{
			// trolls are lumbering hunks of muscle
			// with fearsome regenerative powers
			// they are not very bright

			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(4, 4));
			stats.incStat(RPG.ST_AG, RPG.d(1, 4));
			stats.incStat(RPG.ST_TG, RPG.d(4, 4));
			stats.incStat(RPG.ST_IN, 0);
			stats.incStat(RPG.ST_WP, RPG.d(3, 4));
			stats.incStat(RPG.ST_CH, 0);
			stats.incStat(RPG.ST_CR, 0);

			stats.incStat(RPG.ST_MOVESPEED, -10);
			stats.setStat(RPG.ST_REGENERATE, 10);

			addSkill("Mighty Blow", RPG.po(0.4));

			addThing(Food.createFood(Food.MEATRATION));
			addThing(Food.createFood(Food.MEATRATION));
			addThing(Food.createFood(Food.MEATRATION));

		}

		// ****************
		//   Professions
		// ****************

		if(profession.equals("Warrior"))
		{
			image = 3;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4) + 1);
			stats.incStat(RPG.ST_ST, RPG.d(2, 6) + 2);
			stats.incStat(RPG.ST_AG, RPG.d(1, 4) + 4);
			stats.incStat(RPG.ST_TG, RPG.d(2, 6) + 2);
			stats.incStat(RPG.ST_IN, RPG.d(1, 3));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 6));
			stats.incStat(RPG.ST_CR, RPG.d(1, 6));
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_RANGER);
			addSkill("Attack", RPG.po(1));
			addSkill("Defence", RPG.po(1));
			addSkill("Unarmed Combat", RPG.po(1));

			// fighter's extra kit
			Item pot               = new Potion(Potion.FRENZY, RPG.d(4));
			pot.setIdentified(true);
			addThing(pot);
			addThing(Weapon.createWeapon(4));
			StandardArmour shield  = StandardArmour.createShield(0);
			inv.addThing(shield);
			wield(shield, shield.wieldType());
		}

		if(profession.equals("fighter-mage"))
		{
			image = 2;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(1, 4) + 4);
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_MAGE);

			addSkill("Attack", RPG.po(1));
			addSkill("Defence", RPG.po(1));
			addSkill("Literacy", 1 + RPG.po(0.5));
			addSkill("True Magic", 1);
			arts.addArt(new Spell(Spell.FIREBALL));
			arts.addArt(new Spell(Spell.SPARK));
			addThing(Missile.createMissile(0));
			addThing(new SpellBook());
		}

		if(profession.equals("sorceror"))
		{
			image = 6;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(1, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_WP, RPG.d(3, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_MAGE);
			gainSkill(RPG.ST_MAGE);
			addSkill("Identify", RPG.po(0.5));
			addSkill("Literacy", 1 + RPG.po(0.6));
			addSkill("True Magic", 1 + RPG.po(0.5));
			arts.addArt(new Spell(Spell.FIREBALL));
			arts.addArt(new Spell(Spell.MISSILE));
			arts.addArt(new Spell(Spell.BLAZE));
			arts.addArt(new Spell(Spell.HEAL));
			addThing(new SpellBook());
			addThing(new SpellBook());
		}

		if(profession.equals("runecaster"))
		{
			image = 6;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(1, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4) + 4);
			gainSkill(RPG.ST_MAGE);
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_ARTISAN);
			addSkill("Alchemy", RPG.po(0.5));
			addSkill("Herb Lore", RPG.po(0.5));
			addSkill("Identify", RPG.po(1));
			addSkill("Literacy", 1 + RPG.po(0.7));
			addSkill("Rune Lore", 1 + RPG.po(0.6));
			arts.addArt(new Spell(Spell.RUNETRAP));
			arts.addArt(new Spell(Spell.POISON));
			arts.addArt(new Spell(Spell.LAYRUNE));
			arts.addArt(new Spell(Spell.FIREDART));
			addThing(new SpellBook());
			addThing(new Scroll(Spell.BECOMEETHEREAL, 1));
		}

		if(profession.equals("priest"))
		{
			image = 1;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_WP, 8);
			stats.incStat(RPG.ST_CH, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_PRIEST);
			gainSkill(RPG.ST_PRIEST);

			addSkill("Prayer", 1 + RPG.po(1));
			addSkill("Literacy", RPG.po(1.5));
			addSkill("Holy Magic", 1 + RPG.po(0.2));
			addSkill("Healing", RPG.po(1.5));
			addSkill("Meditation", RPG.po(0.5));

			arts.addArt(new Spell(Spell.LIGHTHEAL));
			arts.addArt(new Spell(Spell.HEAL));
			arts.addArt(new Spell(Spell.ACIDBOLT));
			addThing(new SpellBook());
			addThing(new Ring());
		}

		if(profession.equals("bard"))
		{
			image = 2;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_CR, RPG.d(2, 4) + 4);

			addSkill("Music", RPG.po(0.5));
			addSkill("Perception", RPG.po(0.5));
			addSkill("Sleight of Hand", RPG.po(0.5));
			addSkill("Storytelling", RPG.po(0.5));
			addSkill("Seduction", RPG.po(1.2));
			addSkill("Literacy", RPG.po(0.8));
			gainSkill(RPG.ST_BARD);
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_THIEF);
			arts.addArt(new Spell(Spell.LIGHTHEAL));
			addThing(new SpellBook());
			addThing(new SpellBook());
			addThing(Missile.createMissile(0));
		}

		if(profession.equals("warrior-priest"))
		{
			image = 7;
			stats.incStat(RPG.ST_SK, RPG.d(1, 4) + 4);
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(1, 4) + 4);
			stats.incStat(RPG.ST_WP, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_CH, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			addSkill("Prayer", RPG.po(1));
			addSkill("Attack", RPG.po(1));
			addSkill("Defence", RPG.po(1));
			addSkill("Literacy", RPG.po(1));
			addSkill("Holy Magic", 1 + RPG.po(0.1));
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_PRIEST);
			arts.addArt(new Spell(Spell.ACIDBOLT));
			arts.addArt(new Spell(Spell.HEAL));
			arts.addArt(new Spell(Spell.FIREBALL));
		}

		if(profession.equals("barbarian"))
		{
			image = 3;
			stats.incStat(RPG.ST_SK, RPG.d(1, 4) + 6);
			stats.incStat(RPG.ST_ST, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_AG, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_TG, RPG.d(1, 4) + 4);
			stats.incStat(RPG.ST_IN, RPG.d(1, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(1, 4));
			stats.incStat(RPG.ST_CR, RPG.d(1, 4));
			stats.incStat(RPG.ST_ATTACKSPEED, 120);

			addSkill("Attack", RPG.po(0.5));
			addSkill("Defence", RPG.po(0.5));
			addSkill("Mighty Blow", RPG.po(0.5));
			addSkill("Berserk", RPG.po(0.5));
			addSkill("Searching", RPG.po(0.7));
			addSkill("Survival", RPG.po(1));
			addSkill("Pick-Pocket", RPG.po(1));
			addSkill("Unarmed Combat", RPG.po(1));
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_THIEF);
			gainSkill(RPG.ST_RANGER);

			// fighter's extra kit
			addThing(StandardArmour.createShield(0));
			addThing(Missile.createMissile(0));
		}

		if(profession.equals("thief"))
		{
			image = 0;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(3, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(3, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_ATTACKSPEED, 120);

			addSkill("Searching", 1 + RPG.po(0.7));
			addSkill("Pick-Pocket", RPG.po(1));
			addSkill("Pick-Lock", RPG.po(1));
			addSkill("Disarm Trap", RPG.po(1));
			gainSkill(RPG.ST_BARD);
			gainSkill(RPG.ST_THIEF);
			gainSkill(RPG.ST_RANGER);

			// fighter's extra kit
			addThing(Missile.createMissile(0));
			RangedWeapon rw  = RangedWeapon.createRangedWeapon(2);
			addThing(rw);
			addThing(rw.createAmmo());
		}

		if(profession.equals("ranger"))
		{
			image = 10;
			stats.incStat(RPG.ST_SK, RPG.d(1, 4) + 8);
			stats.incStat(RPG.ST_ST, RPG.d(2, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_TG, RPG.d(2, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4));
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			addSkill("Archery", RPG.po(1));
			addSkill("Throwing", RPG.po(1));
			addSkill("Survival", RPG.po(1));
			addSkill("Swimming", RPG.po(0.5));
			addSkill("Riding", RPG.po(0.5));
			addSkill("Tracking", RPG.po(0.5));
			gainSkill(RPG.ST_FIGHTER);
			gainSkill(RPG.ST_RANGER);
			gainSkill(RPG.ST_RANGER);

			// fighter's extra kit
			addThing(Lib.createItem(0));
			RangedWeapon rw  = RangedWeapon.createRangedWeapon(4);
			addThing(rw);
			addThing(rw.createAmmo());
			addThing(Missile.createMissile(0));
		}

		if(profession.equals("enchanter"))
		{
			image = 6;
			stats.incStat(RPG.ST_SK, RPG.d(2, 4));
			stats.incStat(RPG.ST_ST, RPG.d(1, 4));
			stats.incStat(RPG.ST_AG, RPG.d(2, 4));
			stats.incStat(RPG.ST_TG, RPG.d(1, 4));
			stats.incStat(RPG.ST_IN, RPG.d(2, 4) + 4);
			stats.incStat(RPG.ST_WP, RPG.d(2, 4));
			stats.incStat(RPG.ST_CH, RPG.d(2, 4));
			stats.incStat(RPG.ST_CR, RPG.d(2, 4));

			addSkill("Identify", RPG.po(0.3));
			addSkill("Literacy", 1 + RPG.po(0.4));
			addSkill("True Magic", 1 + RPG.po(0.3));
			addSkill("Herb Lore", RPG.po(0.7));
			addSkill("Perception", RPG.po(0.6));
			addSkill("Sleight of Hand", RPG.po(0.6));
			addSkill("Pick-Pocket", RPG.po(0.6));
			gainSkill(RPG.ST_MAGE);
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_BARD);
			arts.addArt(new Spell(Spell.BROOMSTICK));
			arts.addArt(new Spell(Spell.INFESTATION));
			arts.addArt(new Spell(Spell.ACIDBOLT));
			addThing(new SpellBook());
		}

		if(profession.equals("avatar"))
		{
			image = 7;
			stats.incStat(RPG.ST_SK, RPG.d(6, 24));
			stats.incStat(RPG.ST_ST, RPG.d(6, 24));
			stats.incStat(RPG.ST_AG, RPG.d(6, 24));
			stats.incStat(RPG.ST_TG, RPG.d(6, 24));
			stats.incStat(RPG.ST_IN, RPG.d(6, 24));
			stats.incStat(RPG.ST_WP, RPG.d(6, 24));
			stats.incStat(RPG.ST_CH, RPG.d(6, 24));
			stats.incStat(RPG.ST_CR, RPG.d(6, 24));

			addSkill("Literacy", 2 + RPG.po(3));
			addSkill("Berserk", 2 + RPG.po(3));
			addSkill("Mighty Blow", 2 + RPG.po(3));
			addSkill("Parry", 2 + RPG.po(3));
			addSkill("Identify", 4 + RPG.po(5));
			addSkill("Rune Lore", 2 + RPG.po(3));
			addSkill("Herb Lore", 2 + RPG.po(3));
			addSkill("Prayer", 2 + RPG.po(3));
			addSkill("True Magic", 2 + RPG.po(3));
			addSkill("Pick-Lock", 2 + RPG.po(3));
			addSkill("Storytelling", 2 + RPG.po(3));

			gainSkill(RPG.ST_MAGE);
			gainSkill(RPG.ST_SCHOLAR);
			gainSkill(RPG.ST_BARD);
			arts.addArt(new Spell(Spell.CONFUSE));
			arts.addArt(new Spell(Spell.HASTE));
			arts.addArt(new Spell(Spell.POWERHEAL));
			arts.addArt(new Spell(Spell.TRUEVIEW));
			arts.addArt(new Spell(Spell.IDENTIFY));
			arts.addArt(new Spell(Spell.BLAZE));
			arts.addArt(new Spell(Spell.VAPOURIZER));
			arts.addArt(new Spell(Spell.POISON));
			arts.addArt(new Spell(Spell.LAYRUNE));
			arts.addArt(new Spell(Spell.FIREPATH));
			arts.addArt(new Spell(Spell.BROOMSTICK));
			addThing(new SpellBook());
		}

		stats.setStat(RPG.ST_LEVEL, 1);
		stats.setStat(RPG.ST_SKILLPOINTS, (QuestApp.debug ? 60000 : 60));

		utiliseItems();
		initStats();

		addQuest(new StandardQuest("emerald sword"));
	}

	private void initStats()
	{
		stats.setStat(RPG.ST_HPSMAX, getBaseStat(RPG.ST_TG));
		stats.setStat(RPG.ST_MPSMAX, getBaseStat(RPG.ST_WP));

		hps = getStat(RPG.ST_HPSMAX);
		mps = getStat(RPG.ST_MPSMAX);
	}

	public void improveSlightly()
	{
		stats.incStat(RPG.ST_MOVESPEED, 100);
		stats.incStat(RPG.ST_ATTACKSPEED, 100);
		stats.incStat(RPG.ST_PROTECTION, 10);
		stats.incStat(RPG.ST_REGENERATE, 50);
		stats.incStat(RPG.ST_RECHARGE, 50);
	}

	public void searchAround()
	{
		Map map     = getMap();
		if(map == null)
			return;
		Thing[] th  = map.getThings(x - 1, y - 1, x + 1, y + 1);
		for(int i = 0; i < th.length; i++)
		{
			Thing st  = th[i];
			if(st instanceof Special)
				((Special)st).discover();

		}
	}

	public void gainExperience(int x)
	{
		int exp    = getStat(RPG.ST_EXP) + x;
		int level  = getStat(RPG.ST_LEVEL);
		if(exp > ((1 << level) * 10))
		{
			gainLevel();
			exp = 0;
		}
		stats.setStat(RPG.ST_EXP, exp);
	}

	public void gainSkill(int skill)
	{
		switch (skill)
		{
				case RPG.ST_RANGER:
					stats.incStat(RPG.ST_MOVESPEED, 5);
					stats.incStat(RPG.ST_PROTECTION, 1);
					break;
				case RPG.ST_FIGHTER:
					stats.incStat(RPG.ST_ATTACKSPEED, 5);
					stats.incStat(RPG.ST_PROTECTION, 1);
					break;
				case RPG.ST_THIEF:
					break;
				case RPG.ST_SCHOLAR:
					break;
				case RPG.ST_MAGE:
					break;
				case RPG.ST_BARD:
					break;
				case RPG.ST_PRIEST:
					break;
				case RPG.ST_ARTISAN:
					break;
				default:
					// not recognised, so bail out
					return;
		}
		stats.incStat(skill, 1);
	}

	public void message(String s)
	{
		Game.message(s);
	}

	public int getHunger()
	{
		int hunger  = getStat(RPG.ST_HUNGER);
		return (hunger >= HUNGERLEVEL) ? 1 : 0;
	}

	// kick in specified direction
	public void kick(Thing t, int dx, int dy)
	{
		Game.message("You kick " + t.getTheName());
		if(t instanceof Item)
		{
			Map m   = getMap();
			int nx  = x + dx * 2;
			int ny  = y + dy * 2;
			if(!(m.isBlocked(nx, ny) || t.isOwned()))
			{
				m.addThing(t, nx, ny);
				m.hitSquare(t, nx, ny, true);
			}
		}
		t.damage(RPG.a(getStat(RPG.ST_ST) / 2), RPG.DT_IMPACT);
	}

	// kick in specified direction
	public void kick(int dx, int dy)
	{
		aps = aps - 50000 / getStat(RPG.ST_ATTACKSPEED);
		Map m    = (Map)place;
		Thing t  = m.getObjects(x + dx, y + dy);
		while(t != null)
		{
			if(t.isBlocking())
			{
				kick(t, dx, dy);
				return;
			}
			t = t.next;
		}
		if(m.isBlocked(x + dx, y + dy))
		{
			Game.message("You kick the wall - Ouch!");
			return;
		}
		t = m.getObjects(x + dx, y + dy);

		while(t != null)
		{
			if((t instanceof Item) || (t instanceof Scenery))
			{
				if(!(t instanceof Decoration))
				{
					kick(t, dx, dy);
					return;
				}
			}
			t = t.next;
		}
		Game.message("You kick thin air");
	}


	// do message for items in location
	public void locationMessage()
	{
		Thing t  = getMap().getObjects(x, y);
		while(t != null)
		{
			if((t instanceof Item) && t.isVisible())
				message("There " + ((t.getNumber() == 1) ? "is " : "are ") + t.getAName() + " here");

			t = t.next;
		}
	}

	// do we regard creature <b> as hostile?
	public boolean isHostile(Mobile b)
	{
		return (b instanceof Hero) ? false : b.isHostile(this);
	}

	public void moveTo(Map m, int tx, int ty)
	{
		super.moveTo(m, tx, ty);
		locationMessage();
	}


	// add a Quest to the hero
	public void addQuest(Quest q)
	{
		addThing(q);
	}

	// get a quest by name
	public Quest getQuest(String s)
	{
		Quest[] quests  = getQuests();
		for(int i = 0; i < quests.length; i++)
			if(quests[i].questName().equals(s))
				return quests[i];
		return null;
	}

	// get all quests currenty active
	public Quest[] getQuests()
	{
		Thing[] quests  = inv.getContents(Quest.class);
		Quest[] temp    = new Quest[quests.length];

		for(int i = 0; i < quests.length; i++)
		{
			temp[i] = (Quest)quests[i];
			if(QuestApp.debug)
				Game.message(quests[i].getName() + " : " + quests[i].getClass().getName());
		}

		return temp;
	}

	public void registerKill(Thing t)
	{
		if(t instanceof Being)
		{
			Being b        = (Being)t;
			int killlevel  = b.getLevel();
			gainExperience((1 << killlevel) / (RPG.max(1, killlevel + 1) * RPG.max(1, 1 + getLevel() - killlevel)));
			if(getStat(RPG.ST_SCORE_BESTKILL) < killlevel)
				stats.setStat(RPG.ST_SCORE_BESTKILL, killlevel);

			stats.incStat(RPG.ST_SCORE_KILLS, 1);
		}
	}

	public Description getDescription()
	{
		return desc;
	}

	// can't do anything in monster action phase
	// but allow for hunger effects
	public void action(int t)
	{
		// hunger
		int hunger  = getStat(RPG.ST_HUNGER);
		hunger = RPG.min(HUNGERLEVEL * 2, hunger + (t * 10) / (10 + getStat(RPG.ST_RANGER)));
		stats.setStat(RPG.ST_HUNGER, hunger);

		// SPECIAL ABILITIES
		// thief searches
		for(int i = RPG.po(t * getStat(RPG.ST_SEARCHING), 2000); i > 0; i--)
			searchAround();

		super.action(t);// iventory action etc.
	}
}

