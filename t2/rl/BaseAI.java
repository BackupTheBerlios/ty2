package rl;

// the root AI object
public class BaseAI implements AI, java.io.Serializable {
	public boolean doAction(Mobile m) {
    // don't do anything
    m.aps=RPG.min(m.aps,-1);
    return true;	
	}	
	
	// don't need these except for complex AI objects
	public void register(Mobile m) {}
	public void unregister(Mobile m) {}
	
	public int getState(Mobile m) {return m.getStat(RPG.ST_STATE);}
	
  public boolean isHostile(Mobile m, Mobile b) {
    int bstate=b.getStat(RPG.ST_STATE);
    int mstate=m.getStat(RPG.ST_STATE);
    
    // everyone fights chaotics
    // including other chaotics
    if (bstate==AI.STATE_CHAOTIC) return true;
    
    // if it's a follower, decide whether we like the leader
    if (bstate==AI.STATE_FOLLOWER) return isHostile(m,b.getAI().getLeader());
    
    switch (getState(m)) {
      case AI.STATE_CHAOTIC: return true;
       
      case AI.STATE_HOSTILE: {
        // attacks hero
        if (b==Game.hero) return true;
        // attacks townies
        if (bstate==AI.STATE_INHABITANT) return true;
        // attack anyone not on the same side
        if (b.getStat(RPG.ST_SIDE)!=m.getStat(RPG.ST_SIDE)) return true;
        
        // can't see a reason to attack. damn.
        return false;
      }
    
      case AI.STATE_INHABITANT: {
        // fight against hostiles
        if (bstate==AI.STATE_HOSTILE) return true;
        
        // fight the hero if the map is angry
        if ((b==Game.hero)&&(m.getMap()!=null)&&(m.getMap().isAngry())) return true;
        return false; 
      }
      
      case AI.STATE_FOLLOWER: {
        // we like our own leader, naturally
        if (b==getLeader()) return false;
        
        // follow leader's example
        return isHostile(getLeader(),b);  
      }
      
      default:
        // neutrals don't attack anyone
        return false;
    }
  }
  
  public Mobile getLeader() {
    return null; 
  }
  
  public void turnNasty(Mobile m) {
    // Could turn townies into nasties
    // but prefer changing entire map state
    // m.setAI(NastyCritterAI.instance);
    m.setStat(RPG.ST_STATE,AI.STATE_HOSTILE);
    m.getMap().setAngry(-1);
  }
  
  public int notify(Mobile m, int eventtype, int ext, Object o) {
    Map map=m.getMap();
    if (map==null) return 0;
    if (m.isHostile(Game.hero)) return 0;
    switch (eventtype) {
      case EVENT_ATTACKED:
        if ( (o instanceof Hero) && (ext==m.getStat(RPG.ST_SIDE)) ) {
          Game.message(m.getTheName()+" shouts angrily!"); 
          Hero h=(Hero)o;
          turnNasty(m); 
        }
        return 0;
      case EVENT_THEFT:
        if (m.isVisible()) {
          if (RPG.test(m.getStat(RPG.ST_IN),((Hero)o).getStat(RPG.ST_SK))) {
            Game.message(m.getTheName()+" shouts angrily!"); 
            turnNasty(m); 
            map.areaNotify(m.x,m.y,10,eventtype,1,o);
          } else {
            Game.message(m.getTheName()+" doesn't notice"); 
          }
        }
        return 0;
    }
    return 0;
  }
}