// Extension of portal class which creates maps when they are entered
// quite useful since sub-maps only get generated if entered
// This improves speed and save game filesize.
//
// Each type of PlacePortal creates a specific kind of target map
//
// Once the target place map is created, it will not be deleted
// i.e. the same location can be entered again and again.
//
// Typically, new PlacePortals will be created either:
//  - At the generation of the map (or WorldMap) on which they are placed
//      e.g. the initial towns on the starting WorldMap
//  - As a result of being awarded a Quest
//  - As a result of completing part or all of a quest

package rl;

public class PlacePortal extends Portal {
  public static final int TOWN=1;
  public static final int RUIN=2;
  public static final int BANDITCAVES=3;
  public static final int DARKCAVES=4;
  public static final int BANDITCAVESDEEP=5;
  public static final int BANDITLAIR=6;
  public static final int GOBLINTRIBE=7;
  
  
  private int type;
  private int level;
  private Map map;
  
  public static final int[] images= {0,305,302,303,-1,3,3,304};  
  
  public PlacePortal(int t) {
    super(1);
    type=t;
  }
  
  public PlacePortal(int t, int l) {
    this(t);
    level=l;
  }
  
  public void travel(Thing t) {
    try {
      if (map==null) {
        switch (type) {
        
          case TOWN:
            map=new Town(51,51);
            makeLink(map);
            break;
        
          case RUIN:
            map=new DeepForest(71,71);
            makeLink(map);
            break;  
          
          
          
          case BANDITCAVES:
            map=new Caves(1,4);
            makeLink(map);
            map.addThing(new PlacePortal(BANDITCAVESDEEP,5));
            break;

          case DARKCAVES:
            map=new Caves(1,Game.hero.getStat(RPG.ST_LEVEL));
            map.entrance=new Portal(1);
            map.entrance.setDestination(getMap(),x,y);
            setDestination(map,64,32);
            break;
          
          case BANDITCAVESDEEP:
            map=new Caves(2,level);
            makeLink(map);
            if (RPG.d(2)==1) {
              map.addThing(new PlacePortal(BANDITCAVESDEEP,level+1));
            } else {
              map.addThing(new PlacePortal(BANDITLAIR,7));
            }
            break;
          
          case BANDITLAIR:
            map=new Caves(3,level);
            makeLink(map);
            map.addThing(new PlacePortal(BANDITCAVESDEEP,8));
            break;
        
          case GOBLINTRIBE: {
            map = new Outdoors(Tile.FORESTS);
            
            for (int i=0; i<10; i++) {
              map.addThing(GameScenery.create("tent")); 
            }
            
            map.entrance.remove();
            EdgePortal e=new EdgePortal(EdgePortal.ALL);
            map.entrance=e;
            map.addThing(e,0,0);
            
            
            // define the central area
            int p1=22; int q1=22;
            int p2=42; int q2=42;
            map.clearArea(p1,q1,p2,q2);
            
            
            for (int i=RPG.d(4,10); i>0; i--) {
              Being b=new Creature(Creature.GOBBO,2+RPG.po(3));               
              b.setPersonality(new Personality(Personality.CHATTER,Personality.CHATTER_GOBLIN));
              b.setAI(TownieAI.instance);
              b.setStat(RPG.ST_STATE,AI.STATE_INHABITANT);
              map.addThing(b);
            } 
            
            
            for (int i=RPG.d(4,10); i>0; i--) {
              Being b=new Creature(Creature.GOBLIN,4+RPG.po(2));               
              b.setPersonality(new Personality(Personality.CHATTER,Personality.CHATTER_GOBLIN));
              b.setAI(TownieAI.instance);
              b.setStat(RPG.ST_STATE,AI.STATE_INHABITANT);
              map.addThing(b);
            } 
            
            for (int i=RPG.d(3,4); i>0; i--) {
              Being b=new Creature(Creature.ORCWARRIOR,5+RPG.po(3));               
              b.setPersonality(new Personality(Personality.CHATTER,Personality.CHATTER_ORC));
              b.makeGuard(map,p1,q1,p2,q2);
              b.setStat(RPG.ST_STATE,AI.STATE_INHABITANT);
            } 
            
            for (int i=RPG.d(2,3); i>0; i--) {
              Being b=new Creature(Creature.SHAMAN,8+RPG.po(3));               
              b.setPersonality(new Personality(Personality.CHATTER,Personality.CHATTER_GOBLIN));
              b.makeGuard(map,p1,q1,p2,q2);
              b.setStat(RPG.ST_STATE,AI.STATE_INHABITANT);
            } 

            

            
            makeLink(map);
            break;
            
          } 
        }
      }
    } catch (Exception e) {
      Game.message("Failed to build target map in PlacePortal");
      return; 
    }
    super.travel(t);
  }
  
  public boolean isInvisible() {return false;}
  
  public int getImage() {
    return images[type]; 
  }
}