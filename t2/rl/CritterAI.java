package rl;

// the critter is a neutral beast
// it hunts out food
// may attack anyone who comes too close....

// critters of similar type band together and all turn 
// nasty (NastyCritterAI) if one of them is attacked

public class CritterAI extends BaseAI {
	public static final CritterAI instance=new CritterAI();
	
  // action routine
  // MUST use up all aps
	public boolean doAction(Mobile m) {
	  Hero h=Game.hero;
    Map map=m.getMap();

    // sniff for food - this is a slow algorithm 
    // so only do occasionally or if visible
    if (m.isVisible()||(RPG.d(40)==1)) {
      int dis=4; //search distance
      Thing[] search = map.getThings(m.x-dis,m.y-dis,m.x+dis,m.y+dis);
      for (int i=0; i<search.length; i++) {
        Thing t=search[i];
        if ((t.getUse()&Thing.USE_EAT)>0) {
          m.setStat(RPG.ST_TARGETX,t.x);
          m.setStat(RPG.ST_TARGETY,t.y);
          m.setStat(RPG.ST_AIMODE,1);
        }
      }
      m.aps-=50;
    }
    
    while (m.aps>=0) {
      int d=RPG.dist(h.x,h.y,m.x,m.y);
	  	
      int state=m.getStat(RPG.ST_AIMODE);
      
      int dx; int dy;
      
      if (state==0) {
        // wandering
        dx=RPG.r(3)-1;
        dy=RPG.r(3)-1;
  
        // do search for food
        
        // randomize movement
        //if (d>5) {
          // could make them run... probably not worth it
          //if (RPG.d(2)==1) dx=-RPG.sign(h.x-m.x);
          //if (RPG.d(2)==1) dy=-RPG.sign(h.y-m.y);
        //} else {
		  if(!(d>5)){	  
          // make them charge vaguely at hero
          if (RPG.d(2)==1) dx=RPG.sign(h.x-m.x);
          if (RPG.d(2)==1) dy=RPG.sign(h.y-m.y);
        }
      } else {
        // scrounging!
        int tx=m.getStat(RPG.ST_TARGETX);
        int ty=m.getStat(RPG.ST_TARGETY);
      
        dx=RPG.sign(tx-m.x);
        dy=RPG.sign(ty-m.y);
        
        
        if ((tx==m.x)&&(ty==m.y)) {
          Thing[] stuff=map.getThings(tx,ty);
          for (int i=0; i<stuff.length; i++) {
            Thing t=stuff[i];
            if ((t.getUse()&Thing.USE_EAT)>0) {
              t.use((Being)m);
            } 
          }
          m.setStat(RPG.ST_AIMODE,0);
        }
      }
      
      int nx=m.x+dx;
      int ny=m.y+dy;
      
      if (!map.isBlocked(nx,ny)) {
        m.moveTo(map,nx,ny);
        m.aps=m.aps-10000/m.getStat(RPG.ST_MOVESPEED);  
      } else {
        m.setStat(RPG.ST_AIMODE,0);
        Mobile t=map.getMobile(nx,ny);
        if ((t!=null)&&(t.isHostile(m))) {
          m.attack(t);	
        }	else {
          m.aps-=100; 
        }
      }
	  }	
    return true;
	}	
  
  public int notify(Mobile m, int eventtype, int ext, Object o) {
    if ((eventtype==EVENT_ATTACKED)&&(o==Game.hero)&&(ext==m.getStat(RPG.ST_SIDE))) {
      turnNasty(m);
    }
    else if ((eventtype==EVENT_ALARM)&&(ext==m.getStat(RPG.ST_SIDE))) {
      turnNasty(m);
    }  
    return 0;
  }
  
  public void turnNasty(Mobile m) {
    // turn into nasty critter
    m.setAI(NastyCritterAI.instance);
    m.setStat(RPG.ST_STATE,AI.STATE_HOSTILE);
    
    //Map map=m.getMap();
    //if (map!=null) map.areaNotify(m.x,m.y,7,EVENT_ATTACKED,m.getStat(RPG.ST_SIDE),Game.hero);
  } 
}