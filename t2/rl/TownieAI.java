package rl;

public class TownieAI extends BaseAI {
	public static final TownieAI instance=new TownieAI();
	
  public boolean doAction(Mobile m) {
    Hero h=Game.hero;
    
    int state=m.getStat(RPG.ST_STATE);
    
    // get target location
    int tx=m.getStat(RPG.ST_TARGETX);
    int ty=m.getStat(RPG.ST_TARGETY);
    
    // if initial value, then set target to current location
    if (tx==0) {
      m.setStat(RPG.ST_TARGETX,m.x);
      m.setStat(RPG.ST_TARGETY,m.y);
    }
    
	  while (m.aps>=0) {
      Map map=m.getMap();

      // go for nearest foe if applicable
      Mobile target=(Mobile)map.findNearestFoe(m);
      if ((target!=null)&&m.canSee(target)) {
        NastyCritterAI.instance.doAttack(m,target.x,target.y);
        return true;
      };
    
	    int dx=RPG.r(3)-1;
	    int dy=RPG.r(3)-1;

  	  if (RPG.d(4)>1) dx=RPG.sign(tx-m.x);
   	  if (RPG.d(4)>1) dy=RPG.sign(ty-m.y);
	  	
      int nx=m.x+dx;
      int ny=m.y+dy;
      
      
      if (m.canMove(map,nx,ny)) {
        NastyCritterAI.instance.doAttack(m,nx,ny);
        return true;
      } else {
        if (RPG.d(6)==1) {
          m.setStat(RPG.ST_TARGETX,RPG.rspread(0,map.getWidth()-1));
          m.setStat(RPG.ST_TARGETY,RPG.rspread(0,map.getHeight()-1));
        }
      }
	    m.aps=m.aps-10000/m.getStat(RPG.ST_MOVESPEED);	
	  }	
    return true;
	}	
  
}