package rl;

import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;

public class Hero extends Being
{
	public String name;
	public String race;
	public String profession;
	public int gameseed;
	public Hashtable uniques             = new Hashtable();
	private ArrayList rlskills           = new ArrayList();
	private Hashtable rlstats            = new Hashtable();
	private ArrayList rlitems            = new ArrayList();//Most likely to be changed in future

	public final static int HUNGERLEVEL  = 250000;// set tick count at which hero becomes hungry
	public boolean recharging            = true;// flag for whether we can recharge/regenerate naturally
	private Description desc             = new Describer("you", "you", "Yourself, the brave hero of this adventure.", Description.NAMETYPE_PROPER, Description.GENDER_MALE);

	/**
	 *  Gets the name of the Hero
	 *
	 * @return    The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 *  Check if the hero has a certain skill
	 *
	 * @param  skillname  Name of the skill
	 * @return            Boolean, indicating posession
	 */
	public boolean hasSkill(String skillname)
	{
		return rlskills.contains(skillname);
	}

	/**
	 *  Sets a stat of the Hero
	 *
	 * @param  stat   The name of the stat
	 * @param  value  The value of the stat
	 */
	public void setStat(String stat, int value)
	{
		rlstats.put(stat, new Integer(value));
	}

	/**
	 *  Sets a stat of the Hero IF the stat isnt set yet
	 *
	 * @param  stat   The name of the stat
	 * @param  value  The value of the stat
	 */
	public void mendStat(String stat, int value)
	{//Mendstat checks if this stat has a value, if not it adds the value
		if(!rlstats.containsKey(stat))
			setStat(stat, value);
	}

	/**
	 *  Gets a stat of the Hero
	 *
	 * @param  stat  The name of the stat
	 * @return       The value of the stat
	 */
	public int getStat(String stat)
	{
		Integer i  = (Integer)rlstats.get(stat);
		if(i == null)
			return (getStat(stat.concat("def")));
		else
			return i.intValue();
	}

	/**
	 *  Gets a stat of the Hero
	 *
	 * @param  stat  Name of the stat
	 * @param  def   default value in case of non-existing stat
	 * @return       The stat value
	 */
	public int getStat(String stat, int def)
	{
		Integer i  = (Integer)rlstats.get(stat);
		if(i == null)
			return (def);
		else
			return i.intValue();
	}

	/**
	 *  Constructor for the Hero object
	 *
	 * @param  n  Name of the hero
	 * @param  r  Filename for the race, ex. : xtra/edit/r_???.txt
	 * @param  p  Filename for the class ex. : xtra/edit/c_???.txt
	 */
	public Hero(String n, String r, String p)
	{
		super();
		name = n;
		stats = new Stats(new int[]{AI.STATE_NEUTRAL, 0, 0, 0, 0, 0, 0, 0, 0});
		setAI(HeroAI.instance);
		gameseed = RPG.r(1000000000);// set up a game seed , use for game-specific settings
		stats.setStat(RPG.ST_SIDE, -999);

		ReaderOfRace ror   = new ReaderOfRace(r);
		ReaderOfClass roc  = new ReaderOfClass(p);

		race = ror.name;
		profession = roc.name;

		for(int i = 0; i < ror.skills.size(); i++)
			rlskills.add(ror.skills.get(i));
		for(int i = 0; i < roc.skills.size(); i++)
			rlskills.add(roc.skills.get(i));

		for(int i = 0; i < ror.stats.size(); i++)
		{
			String[] parts  = ((String)ror.stats.get(i)).split(":");
			if(parts.length == 2)
				setStat(parts[0], RPG.EvaluateValueExpression(parts[1]));
			if(parts.length == 3)
			{
				int part1  = getStat(parts[1], 0);
				int part2  = Integer.parseInt(parts[2]);
				if(parts[0].equals("ADD"))
					setStat(parts[1], part1 + part2);
				if(parts[0].equals("REMOVE"))
					setStat(parts[1], part1 - part2);
				if(parts[0].equals("MULTIPLY"))
					setStat(parts[1], part1 * part2);
				if(parts[0].equals("DIVIDE") && part2 != 0)
					setStat(parts[1], part1 / part2);
			}
		}

		for(int i = 0; i < roc.stats.size(); i++)
		{
			String[] parts  = ((String)roc.stats.get(i)).split(":");
			if(parts.length == 2)
				setStat(parts[0], RPG.EvaluateValueExpression(parts[1]));
			if(parts.length == 3)
			{
				int part1  = getStat(parts[1], 0);
				int part2  = Integer.parseInt(parts[2]);
				if(parts[0].equals("ADD"))
					setStat(parts[1], part1 + part2);
				if(parts[0].equals("REMOVE"))
					setStat(parts[1], part1 - part2);
				if(parts[0].equals("MULTIPLY"))
					setStat(parts[1], part1 * part2);
				if(parts[0].equals("DIVIDE") && part2 != 0)
					setStat(parts[1], part1 / part2);
			}
		}

		for(int i = 0; i < roc.items.size(); i++)
		{
			if(roc.items.get(i).equals("Longsword"))
				addThing(Weapon.createWeapon(5, 10, 0));
			if(roc.items.get(i).equals("Potion of Berserk"))
				addThing(new Potion(Potion.FRENZY, RPG.d(4)));
			if(roc.items.get(i).equals("Shield"))
				addThing(StandardArmour.createShield(0));
			if(roc.items.get(i).equals("Arrows"))
				addThing(Missile.createMissile(0));
			if(roc.items.get(i).equals("Spellbook"))
				addThing(new SpellBook());
			if(roc.items.get(i).equals("Scroll of Wraithform"))
				addThing(new Scroll(Spell.BECOMEETHEREAL, 1));
			if(roc.items.get(i).equals("Ring"))
				addThing(new Ring());
			if(roc.items.get(i).equals("Bow"))
			{
				RangedWeapon rw  = RangedWeapon.createRangedWeapon(2);
				addThing(rw);
				addThing(rw.createAmmo());
			}
		}

		System.out.print(rlstats.toString());

		addThing(Weapon.createWeapon(3));
		addThing(Food.createFood(Food.MEATRATION));

		mendStat("MOVESPEED", 100);
		mendStat("ATTACKSPEED", 100);
		mendStat("RECHARGE", 10);
		mendStat("REGENERATE", 3);
		mendStat("FATE", 2);
		mendStat("EXP", 0);//You start with 0 experience
		mendStat("LEVEL", 0);

		stats.setStat(RPG.ST_LEVEL, 1);
		stats.setStat(RPG.ST_SKILLPOINTS, (QuestApp.debug ? 60000 : 60));

		utiliseItems();
		initStats();

		addQuest(new StandardQuest("emerald sword"));
	}

	/**  Set hitpoints & Spell Points */
	private void initStats()
	{
		stats.setStat(RPG.ST_HPSMAX, getBaseStat(RPG.ST_TG));
		stats.setStat(RPG.ST_MPSMAX, getBaseStat(RPG.ST_WP));

		hps = getStat(RPG.ST_HPSMAX);
		mps = getStat(RPG.ST_MPSMAX);
	}

	/**  Improve stats slightly ( called after level increase ) */
	public void improveSlightly()
	{
		stats.incStat(RPG.ST_MOVESPEED, 100);
		stats.incStat(RPG.ST_ATTACKSPEED, 100);
		stats.incStat(RPG.ST_PROTECTION, 10);
		stats.incStat(RPG.ST_REGENERATE, 50);
		stats.incStat(RPG.ST_RECHARGE, 50);
	}

	/**  Search around the level for hidden traps & doors */
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

	/**
	 *  Lets the Hero gain experience
	 *
	 * @param  x  Amount of experience (xp)
	 */
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

	/**
	 *  Lets the hero gain a skill ( deprecated )
	 *
	 * @param  skill  Number of the skill ( defined in class RPG )
	 */
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

	/**
	 *  Send a message to the User Interface
	 *
	 * @param  s  Message
	 */
	public void message(String s)
	{
		Game.message(s);
	}

	/**
	 *  Gets the hunger attribute of the Hero object
	 *
	 * @return    The hunger value
	 */
	public int getHunger()
	{
		int hunger  = getStat(RPG.ST_HUNGER);
		return (hunger >= HUNGERLEVEL) ? 1 : 0;
	}

	// kick in specified direction
	/**
	 *  Kick something in a specified direction, the item will move that way
	 *
	 * @param  t   The item
	 * @param  dx  Horizontal vector [ -1 (left) , 0 , +1 (right)]
	 * @param  dy  Vertical vector [ -1 (up) , 0 , +1 (down)]
	 */
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
	/**
	 *  Kick in a specified direction
	 *
	 * @param  dx  Horizontal vector [ -1 (left) , 0 , +1 (right)]
	 * @param  dy  Vertical vector [ -1 (up) , 0 , +1 (down)]
	 */
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
	/**
	 *  What you see (on MessagePanel) when moving on a location, should give a
	 *  string in the future [TDM]
	 */
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

	//
	/**
	 *  do we regard creature b as hostile?
	 *
	 * @param  b  A creature
	 * @return    Boolean, is the creature hostile ?
	 */
	public boolean isHostile(Mobile b)
	{
		return (b instanceof Hero) ? false : b.isHostile(this);
	}

	/**
	 *  Move to a location, then show the location message
	 *
	 * @param  m   which map ( wilderness, zone, dungeon ?)
	 * @param  tx  target horizatontal position
	 * @param  ty  target vertical position
	 */
	public void moveTo(Map m, int tx, int ty)
	{
		super.moveTo(m, tx, ty);
		locationMessage();
	}


	// add a Quest to the hero
	/**
	 *  Adds a Quest for the Hero
	 *
	 * @param  q  The Quest
	 */
	public void addQuest(Quest q)
	{
		addThing(q);
	}

	/**
	 *  get a quest by name
	 *
	 * @param  s  Description of the Quest
	 * @return    The quest Object
	 */
	public Quest getQuest(String s)
	{
		Quest[] quests  = getQuests();
		for(int i = 0; i < quests.length; i++)
			if(quests[i].questName().equals(s))
				return quests[i];
		return null;
	}

	/**
	 *  get all quests currenty active
	 *
	 * @return    The quests value
	 */
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

	/**
	 *  Register the killing of a creature
	 *
	 * @param  t  The creature that just got killed
	 */
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

	/**
	 *  Gets the description attribute of the Hero object
	 *
	 * @return    The description value
	 */
	public Description getDescription()
	{
		return desc;
	}

	/**
	 *  can't do anything in monster action phase, but allow for hunger effects
	 *
	 * @param  t  Dont know at all [TDM]
	 */
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

