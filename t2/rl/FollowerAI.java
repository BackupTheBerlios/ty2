package rl;

public class FollowerAI extends BaseAI {
  private Mobile leader;

  public FollowerAI(Mobile l) {
    leader=l;
  }
  
  public Mobile getLeader() {
    return leader; 
  }
  
  public int notify(Mobile m, int eventtype, int ext, Object o) {
    Map map=m.getMap();
    if (map==null) return 0;
    switch (eventtype) {
      case EVENT_ATTACKED:
        if ((o instanceof Hero) && (ext==m.getStat(RPG.ST_SIDE)) ) {
          Hero h=(Hero)o;
          turnNasty(m); 
        }
        return 0;
    }
    return 0;    
  }
  
  public boolean isHostile(Mobile m, Mobile b) {
    return leader.isHostile(b); 
  }
 
  // Follower turns on hero
  public void turnNasty(Mobile m) {
    if ((leader==Game.hero)&&RPG.test(m.getStat(RPG.ST_CH),Game.hero.getStat(RPG.ST_CH))) {
      m.setAI(NastyCritterAI.instance);
      m.setStat(RPG.ST_SIDE,0);
      m.setStat(RPG.ST_STATE,AI.STATE_HOSTILE);
    } else {
      
    }
  }
  
  // try to move into square, attacking if necessary
  // return true if sucessful
  public boolean tryMove(Mobile m,int nx, int ny) {
    Map map=m.getMap();
    if (map==null) return false;
    if (!map.isBlocked(nx,ny)) {
      m.moveTo(map,nx,ny);
      m.aps=m.aps-(m.getStat(RPG.ST_MOVECOST)*100)/m.getStat(RPG.ST_MOVESPEED);
      return true;
    } else {
      Mobile t=map.getMobile(nx,ny);
      if ((t!=null)&&(t.isHostile(m))) {
        m.attack(t);
        return true;
      }
      return false;  
    }
  }

  public boolean doAction(Mobile m) {
    int state=m.getStat(RPG.ST_STATE);
    
    Map map=m.getMap();
    if (map==null) return false;
    
    // go for nearest visible foe, else follow leader
    Thing target=map.findNearestFoe((Being)leader);
    if ((target==null)||(!target.isVisible())) target=leader;
    
    int tx=target.x;
    int ty=target.y;
    
    NastyCritterAI.instance.doAttack(m,tx,ty);
    
    return true;
    
/*  while (m.aps>=0) {
	    int dx=RPG.sign(tx-m.x);
	    int dy=RPG.sign(ty-m.y);

  	  if (RPG.d(10)==1) dx=RPG.r(3)-1;
   	  if (RPG.d(10)==1) dy=RPG.r(3)-1;
	  	
      if (tryMove(m,m.x+dx,m.y+dy)) continue; 
      if (tryMove(m,m.x+RPG.sign(dx-dy),m.y+RPG.sign(dy+dx))) continue; 
      if (tryMove(m,m.x+RPG.sign(dx+dy),m.y+RPG.sign(dy-dx))) continue; 
           
      if (RPG.d(2)==1) dx=RPG.r(3)-1;
      if (RPG.d(2)==1) dy=RPG.r(3)-1;
      if (!tryMove(m,m.x+dx,m.y+dy)) m.aps-=100;
	  }	*/
	}	
}