package rl;

public class FriendlyCritterAI extends BaseAI {
	public static final FriendlyCritterAI instance=new FriendlyCritterAI();
	
	public boolean doAction(Mobile m) {
	  Hero h=Game.hero;
	  
	  int tx=m.getStat(RPG.ST_TARGETX);
	  int ty=m.getStat(RPG.ST_TARGETY);
	  
	  while (m.aps>=0) {
      // leisurely thinking time      
      m.aps-=10;
      
      Map map=m.getMap();
      if (m==null) return false;

	  	int dx=RPG.r(3)-1;
	  	int dy=RPG.r(3)-1;

      if ((tx>0)&&(ty>0)) {
    	  if (RPG.d(2)==1) dx=RPG.sign(tx-m.x);
    	  if (RPG.d(2)==1) dy=RPG.sign(ty-m.y);
      }
	  	
      
      int nx=m.x+dx;
      int ny=m.y+dy;
      
      if (!map.isBlocked(nx,ny)) {
      	m.moveTo(map,nx,ny);
      } //else {        // don't do anything. we are nice!!!      }
	    m.aps=m.aps-10000/m.getStat(RPG.ST_MOVESPEED);	
	  }	
    return true;
	}	
  
  public boolean isHostile(Mobile m, Mobile b) {
    return false; 
  }
}