package rl;

public class HeroTrap extends Trap {
  protected int type;
  protected Portal portal;
  
  public static final int ICETRAP=1;
  public static final int PITTRAP=2;
  public static final int BEARTRAP=3;
  public static final int FIRETRAP=4;
  public static final int GASTRAP=5;
  public static final int TELEPORTTRAP=6;
  public static final int AMBUSHTRAP=7;
  
  private static final Description DESC_HEROTRAP = 
		new Describer("trap","It's rather lucky that you spotted this dangerous trap.");
  private static final Description DESC_ICETRAP = 
    new Describer("ice trap","A magical ice trap.");
  private static final Description DESC_PITTRAP = 
    new Describer("pit trap","A deep and dangerous pit. Someone could have a nasty accident.");
  private static final Description DESC_BEARTRAP = 
    new Describer("bear trap","A bear trap.");
  private static final Description DESC_FIRETRAP = 
    new Describer("fire trap","A fire trap.");
  private static final Description DESC_GASTRAP = 
    new Describer("gas trap","A gas trap.");
  private static final Description DESC_TELEPORTTRAP = 
    new Describer("teleport trap","A teleport trap.");
  private static final Description DESC_AMBUSHTRAP = 
    new Describer("ambush trap","A magical ambush trap.");

  public static final int[]         images= {0,             47,           41,           42,            40,            45,           40,                44};
  public static final Description[]  descs= {DESC_HEROTRAP, DESC_ICETRAP, DESC_PITTRAP, DESC_BEARTRAP, DESC_FIRETRAP, DESC_GASTRAP, DESC_TELEPORTTRAP, DESC_AMBUSHTRAP};
  public static final int[]       usecount= {0,             6,            0,            3,             6,             4,            5,                 1};

  public HeroTrap() {
    this(Game.hero.getStat(RPG.ST_LEVEL));
  }
  
  public HeroTrap(int l) {
    this(RPG.d(images.length-1),l); 
  }

  public HeroTrap(int t, int l) {
    type=t;
    level=l;
    uses=RPG.d(usecount[type]);
  }
  
  public int getImage() {return images[type];}
	
  public void discover() {
		if (!discovered) Game.message("Whoa! That looks like a nasty trap!");
		appear();
	}
	
  // make trap appear, with any necessary modifications!
  public boolean appear() {
    if (!super.appear()) return false;
    switch (type) {
      case PITTRAP:
        Map map=getMap();
        portal=new Portal("pit");
        map.addThing(portal,x,y);
        Pit thepit=new Pit(level);
        thepit.entrance.setDestination(portal);
        Point ts=thepit.findFreeSquare(); 
        portal.setDestination(thepit,ts.x,ts.y);
        remove();
        break; 
    }
    return true;
  }
  
	public void affectSquare(Map map, int tx, int ty) {
    Hero h=Game.hero;
    if (map!=h.getMap()) return;
    
    Thing t=map.getMobile(tx,ty);
    boolean ishero=((h.x==tx)&&(h.y==ty));
    
    if (!discovered) {
      if (ishero) {
        Game.message("You have stepped on a trap!");
      }
    }

	  switch (type) {
      case ICETRAP:
        if (ishero) Game.message("There is a sudden blast of deathly cold!");	
        Game.mappanel.doExplosion(x,y,1,4+level,RPG.DT_ICE);
        remove();
        break;
        
      case PITTRAP:
        if (discovered&&RPG.test(t.getStat(RPG.ST_AG),2*level)) {
          appear();
          if (ishero) Game.message("You sidestep the pit");
        } else {
          if (ishero) Game.message("You take a painful fall down a deep pit....."); 
          appear();
          portal.travel(t);
          t.damage(RPG.DT_IMPACT,RPG.a(2));
        } 
        break;
        
      case GASTRAP:
        if (ishero) Game.message("The air is filled with poisonous fumes!");  
        new Spell(Spell.POISONCLOUD).castAtLocation(null,map,x,y);
        appear();;
        break;

      case FIRETRAP:
        if (ishero) Game.message("There is a sudden blast of fire!");  
        new Spell(Spell.FIREBALL).castAtLocation(null,map,x,y);
        appear();;
        break;

      case BEARTRAP:
        if (ishero&&((!discovered)||RPG.test(50,h.getStat(RPG.ST_AG)))) {
          Game.message("The bear trap snaps shut around you leg!");
          h.addAttribute(new TemporaryAttribute("Stuck",15000/h.getStat(RPG.ST_ST),new int[] {RPG.ST_IMMOBILIZED,1},"You break free from the trap"));
        }
        appear();;
        break;
 
      case TELEPORTTRAP:
        new Spell(Spell.TELEPORT).castAtSelf(t);
        appear();;
        break;

      case AMBUSHTRAP:
        if (ishero) {
          int added=0;
          Creature c = Lib.createCreature(level);
          for (int dx=h.x-1; dx<=h.x+1; dx++) for (int dy=h.y-1; dy<=h.y+1; dy++) {
            if ((!map.isBlocked(dx,dy))&&(RPG.d(2)==1)) {
              map.addThing(c.cloneType(),dx,dy);
              added++; 
            } 
          }
          
          if (added>0) Game.message("Ambush!!"); else Game.message("You feel a strange draft of cold air");  
          remove();
        }
        break;

      default:
        if (ishero) Game.message("The trap appears to be broken");
        break;
    }
  }

  public Description getDescription() {return descs[type];}
}