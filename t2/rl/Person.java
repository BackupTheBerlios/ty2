package rl;

public class Person extends Being {
  public static final int TOWNIE=1;
  public static final int SHOPPIE=2;
  public static final int GUARD=3;
  public static final int WIZARD=4;
  public static final int GIRL=5;
  public static final int WOMAN=6;
  public static final int PRIEST=7;
  public static final int RANGER=8;
  public static final int FOOL=9;
  public static final int SHEEPDOG=10;
  public static final int ARMOURER=11;
  public static final int BLACKSMITH=12;
  public static final int TEACHER=13;
	
  public int chat=0;
  
  public static final Description DESC_TOWNIE     =    new Describer("townie","A burly townsman");
  public static final Description DESC_SHOPPIE    =    new Describer("shopkeeper","A tough-looking shopkeeper");
  public static final Description DESC_GUARD      =    new Describer("guard","A beefy town guard");
  public static final Description DESC_WIZARD     =    new Describer("wizard","A bumbling old wizard");
  public static final Description DESC_GIRL       =    new Describer("village girl","A young village girl");
  public static final Description DESC_WOMAN      =    new Describer("woman","A townswoman");
  public static final Description DESC_PRIEST     =    new Describer("goodly priest","A gentle priest");
  public static final Description DESC_RANGER     =    new Describer("ranger","A young ranger");
  public static final Description DESC_SHEEPDOG   =    new Describer("sheepdog","A friendly sheepdog.");
  public static final Description DESC_ARMOURER   =    new Describer("armourer","A battle-hardened town armourer.");
  public static final Description DESC_BLACKSMITH =    new Describer("blacksmith","A blacksmith.");
  public static final Description DESC_TEACHER    =    new Describer("teacher","A Teacher of Skills.");

  // item arrays
  protected static final Thing[] RANGERSTUFF= {
    Missile.createMissile(7), 
    Missile.createMissile(10), 
    Food.createFood(3),
    Food.createFood(4)
  };
  
  protected static final String[][] comments = {
    // normal townie 
    {"Nice day, innit?", "Bugger off.", "There be evil beasties in that forest!", "Mark my words, eh?", "Arrr?", "What is you wantin'?","You be wantin' some gruel?", "There'll be much drinkin' at the inn tonight!"},
    // guard 
    {"You better watch yerself!","Don't mess with me!","They say I'm a nooligan.","I be eatin' dragon steak for breakfast.","I'm 'ard.","Don't you go causin' no trouble.","I'm the toughest guard in town","Wanna fight?","Ra! I'm the mightiest hero in the world!"},
    // wise words  
    {"Mithril is light!","Eat mushrooms for beauty and strength!","Don't play with fire!","Never step on strange runes.","Always wear tough boots!"},
    // pleasant chat
    {"Hello!","Hi there!","So what's it like being an adventurer?","Don't go near the forest.... it's dangerous!","That shopkeeper is a real stingy villain.","Meet me later at the inn."},
    // goodly priest
    {"Peace be with you!","Seek the healing waters of Aramis!","May the blessings of Aramis be upon you.","Greetings, my child.","Let us pray for peace in these troubled times."},
    // adventurer banter
    {"Greetings friend","Let us swap tales in the tavern, my friend.", "They say the Eastern Isles are the most wondrous of all places.","'Tis a great day to wander wild and free!","They say the forests are beautiful this time of year.","'Tis always best to travel light and free."}
  };
  
  protected Description desc;
  protected int creaturetype;
  
  // for Talkable interface
  public void talk(Thing t) {
    if (getPersonality()==null) {
      talk(); 
    } else {
      super.talk(t); 
    }
  }
  
  public void talk() {
    Hero h=Game.hero;
   Thing o;
    
    switch(creaturetype) {
      // wizard identifies stuff
      case WIZARD: {
        Game.message("\"You want Identification, Training or Advice?\" (i/t/a)");
        char c=Game.getOption("ita");
        Game.messagepanel.setText("");
        if (c=='i') {
          if (h.getMoney()>=500) { 
            o=Game.selectItem("Select item to sell:",h.inv.getContents());
            if (o instanceof Item) {
              ((Item)o).identify(10);
              Game.message(getTheName()+" inspects "+o.getTheName()+" carefully.");
              Game.message("\""+Text.capitalise(o.getFullName())+", I believe.\"");
              h.addMoney(-500);
            } else {
              Game.message("\"Hmmm.... come back later eh?\""); 
            }
          } else {
            Game.message("\"My talents cost 5 gold!\"");
            Game.message("\"Get yerself a job!\""); 
          }
        } else if (c=='t') {
          Game.message("\"Hmmm.... go practice first.\""); 
          Game.message("\"Then I'll give you a lesson!\""); 
        } else {
          Game.message("\""+chat()+"\""); 
        }
        break;
      }
      
      case BLACKSMITH: {
        Game.message("\"Yo Ho.\"");
        Game.message("\"You be needin' Repairs, Tools or Lessons?\" (r/t/l)");
        
        char c=Game.getOption("rtla");
        
        Game.messagepanel.setText("");
        if (c=='r') {

          Game.messagepanel.setText("");
          o=Game.selectItem("Select item to sell:",h.inv.getContents());
          if (o instanceof Item) {
            int q=o.getQuality();
            if ((q<=0)||(q>5)) {
              Game.message("\"Can't do much with that, I'm afraid.\"");
              break;
            }

            int val=o.getStat(RPG.ST_ITEMVALUE)/2;  
            val=RPG.niceNumber(val);
            int funds=h.getMoney();

            Game.message("\"It'll cost "+Coin.valueString(val)+" to repair your "+o.getName()+((val<=funds)?".\" (y/n)":".\""));
            if ((val<=funds)&&(Game.getOption("yn")=='y')) {
              h.addMoney(-val);
              ((Item)o).quality++;           
              Game.message("\"There you are.... good as new!\"");
            } else {
              Game.message("\"Well, come back when you need it doing!\"");
              h.addThing(o);
            }
            break;
          }

        } else if (c=='t') {
          Game.message("Hmmm.... no stock right now. Come back in a day or so, eh?"); 
        } else if (c=='l') {
          Game.message(getTheName()+" helps you practice your fighting skills."); 
        }
        break;
      }
      
      // rangers chats away or sells useful stuff
      case RANGER: {
        if (RPG.d(3)==1) {
          Game.message("\"Do you need to buy any supplies?\" (y/n)"); 
          if (Game.getOption("yn")=='y') {
            Game.messagepanel.setText("");
            o=Game.selectItem("What would you like to buy?",RANGERSTUFF);
            if (o instanceof Item) {
              o=(Thing)o.clone();
              int cost=o.getStat(RPG.ST_ITEMVALUE)*o.getNumber();
              Game.message(o.getTheName()+" will cost "+Coin.valueString(cost));
              int funds=h.getMoney();
              if (cost<=funds) {
                Game.message("Buy? (y/n)");
                if (Game.getOption("yn")=='y') {
                  Game.messagepanel.setText("");
                  h.addThing(o);
                  h.addMoney(-cost);
                } 
              }
            } else {
              Game.message("See you later!"); 
            }
          }
        } else {
          Game.message("\""+chat()+"\""); 
        }
        break;
      }
      
      // shopkeeper buys stuff off you for shite prices
      case SHOPPIE: {
        Game.message("\"Wanna sell something?\" (y/n)");
        if (Game.getOption("yn")=='y') {
          do { 
          Game.messagepanel.setText("");
          o=Game.selectItem("Select item to sell:",h.inv.getContents());
          if (o instanceof Item) {
            if ((o instanceof Stack)&&(((Stack)o).number>1)) {
              Game.message("Sell how many? (Enter=All)");
              char c=Game.getOption("0123456789");
              if ((c>='0')&&(c<='9')) {
                o=o.remove((int)c-(int)'0');
              }
            }
            o.remove(); 
            int val=o.getStat(RPG.ST_ITEMVALUE)*o.getNumber();  
            int funds=getMoney();
            val=val/3;
            if (val>funds) val=funds;
            val=RPG.niceNumber(val);
            if (val<=0) {
              Game.message("\"Sorry, can't give you anything for that.\""); 
              h.addThing(o);
              break;
            }
            Game.message("\"I'll give you "+Coin.valueString(val)+" for your "+o.getName()+".\" (y/n)");
            if (Game.getOption("yn")=='y') {
              h.addMoney(val);
              addMoney(-val);
              o.remove();
              addThing(o);          
//Identify unidentified sold potions
				if( o instanceof Potion){
					if( !((Potion)o).isIdentified()){
						((Potion)o).setIdentified(true);
						Game.message("\"Ah, it's a " + ((Potion)o).getSingularName() + "\"");
						Game.getInput();
					}
				}
              Game.message("\"Nice doing business with yer!\"");
            } else {
              Game.message("\"Well, you won't get a better price!\"");
              h.addThing(o);
            }
          }
}while( o != null );
        } else {
          Game.message("\"Feel free to browse!\""); 
        }
        break; 
      }
      
      // priest gives you a fate point if you haven't got one
      case PRIEST: {
        h.setStat(RPG.ST_FATE,RPG.max(1,h.getStat(RPG.ST_FATE))); 
        Game.message("\""+chat()+"\"");
        break;
      }
      
      // standard chat response
      default: Game.message("\""+chat()+"\"");
    } 
  }
  
  public String chat() {
    switch(creaturetype) {
      case GUARD: return comments[1][RPG.r(comments[1].length)]; 
      case GIRL: return comments[3][RPG.r(comments[3].length)]; 
      case PRIEST: return comments[4][RPG.r(comments[4].length)]; 
      case WIZARD: return comments[2][RPG.r(comments[2].length)]; 
      case RANGER: return comments[5][RPG.r(comments[5].length)]; 
      default: return comments[0][RPG.r(comments[0].length)]; 
    }
  }
  
  public Person (int type) {
    // pick a random chat topic 
    chat=RPG.r(10000);
     
    creaturetype=type;
    switch (type) {
      case TOWNIE:
        image=100+RPG.r(2);
        desc=DESC_TOWNIE;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,10,8,10,7,8,7,5,6});
        setStat(RPG.ST_MOVESPEED,100-RPG.d(10));
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(TownieAI.instance);
        addThing(Coin.createMoney(RPG.r(10)*RPG.r(10)));
        addThing(Lib.createItem(3));
        break;
      
      case GIRL:
        image=105+RPG.r(2);
        desc=DESC_GIRL;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,7,6,10,7,11,12,13,9});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,100);
        setAI(TownieAI.instance);
        break;
        
      case WOMAN:
        image=103+RPG.r(2);
        desc=DESC_WOMAN;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,7,6,6,9,8,10,9,13});
        setStat(RPG.ST_MOVESPEED,70);
        setStat(RPG.ST_ATTACKSPEED,70);
        setAI(TownieAI.instance);
        break;

      case GUARD:
        image=81;
        desc=DESC_GUARD;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,20,16,18,24,8,14,7,8});
        setStat(RPG.ST_MOVESPEED,100+RPG.d(10));
        setStat(RPG.ST_ATTACKSPEED,150);
        setStat(RPG.ST_PROTECTION,10);
        setAI(TownieAI.instance);
        addThing(Weapon.createWeapon(6));
        addThing(Lib.createItem(0));
        if (RPG.d(2)==1) addThing(Missile.createMissile(RPG.d(6)));
        break;
      
      case SHOPPIE:
        image=108;
        desc=DESC_SHOPPIE;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,25,15,20,27,18,17,25,16});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,130);
        setStat(RPG.ST_PROTECTION,20);
        setStat(RPG.ST_CASTCHANCE,RPG.d(100));
        arts.addArt(new Spell(Spell.THUNDERBOLT));
        arts.addArt(new Spell(Spell.ACIDBLAST));
        setAI(TownieAI.instance);
        addThing(Coin.createMoney(RPG.d(5)*RPG.d(5,2000)));
        addThing(Weapon.createWeapon(7));
        break;

      case WIZARD:
        image=121;
        desc=DESC_WIZARD;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,14,8,12,15,28,27,15,26});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,10);
        setStat(RPG.ST_CASTCHANCE,RPG.d(2,40));
        arts.addArt(new Spell(Spell.FIREDART));
        arts.addArt(new Spell(Spell.BLAZE));
        setAI(TownieAI.instance);
        addThing(Coin.createMoney(RPG.r(20)*RPG.r(20)*RPG.r(20)));
        addThing(Weapon.createWeapon(6));
        break;

      case PRIEST:
        image=122;
        desc=DESC_PRIEST;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,14,9,12,18,28,27,15,26});
        setStat(RPG.ST_MOVESPEED,80);
        setStat(RPG.ST_ATTACKSPEED,100);
        setStat(RPG.ST_PROTECTION,20);
        setStat(RPG.ST_CASTCHANCE,RPG.d(2,40));
        arts.addArt(new Spell(Spell.SPARK));
        setAI(TownieAI.instance);
        addThing(Food.createFood(0));
        break;
        
      case RANGER: {
        image=107;
        desc=DESC_RANGER;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,36,15,23,25,12,16,14,16});
        setStat(RPG.ST_RANGER,4);
        setStat(RPG.ST_MOVESPEED,120);
        setStat(RPG.ST_ATTACKSPEED,110);
        setStat(RPG.ST_PROTECTION,10);
        setAI(TownieAI.instance);
        addThing(Weapon.createWeapon(6));
        RangedWeapon it=RangedWeapon.createRangedWeapon(0);
        addThing(it.createAmmo());
        addThing(it);
        break;
      }

      case BLACKSMITH:
        image=109;
        desc=DESC_BLACKSMITH;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,30,20,25,38,12,30,13,38});
        setStat(RPG.ST_MOVESPEED,100+RPG.d(10));
        setStat(RPG.ST_ATTACKSPEED,150);
        setStat(RPG.ST_PROTECTION,12);
        setAI(TownieAI.instance);
        addThing(Weapon.createWeapon(6));
        addThing(Lib.createItem(0));
        addThing(Missile.createMissile(RPG.d(6)));
        break;

      case TEACHER:
        image=122;
        desc=DESC_TEACHER;
        stats=new Stats(new int[] {AI.STATE_INHABITANT,30,29,32,38,38,37,35,36});
        setStat(RPG.ST_MOVESPEED,100);
        setStat(RPG.ST_ATTACKSPEED,130);
        setStat(RPG.ST_PROTECTION,25);
        setStat(RPG.ST_CASTCHANCE,RPG.d(2,40));
        arts.addArt(new Spell(Spell.SPARK));
        arts.addArt(new Spell(Spell.FIREBALL));
        arts.addArt(new Spell(Spell.ACIDBLAST));
        arts.addArt(new Spell(Spell.SUMMON));
        addSkill("Literacy",RPG.d(3));
        setAI(TownieAI.instance);
        addThing(Food.createFood(0));
        addThing(Lib.createWeapon(8));
        setPersonality(new Personality(Personality.TEACHER));
        setStat(RPG.ST_SKILLPOINTS,500);
        autoLearnSkills();
        break;

	    default:
        throw new Error("Person type not recognised.");
	  }
	  
    // add some minimal levels and skills
    gainLevel(RPG.d(6));
    
    utiliseItems();
    stateInit();
    
    // side for all townies
    setStat(RPG.ST_SIDE,-99);
	}
	
	public void stateInit() {
	  hps=getStat(RPG.ST_HPSMAX);
	  mps=getStat(RPG.ST_MPSMAX);	
	}
		
	public Description getDescription() {return desc;}
	
  public int getStat(int stat) {
    switch (stat) {
  
      default: return super.getStat(stat); 
    }
  }
  
	public void die() {
    Map m=getMap();
    if (m==null) return;
	  switch (creaturetype) {
      default: m.addThing(new Decoration(Decoration.DEC_REDBLOOD),x,y);
	  }	
    super.die();
	}
	
}