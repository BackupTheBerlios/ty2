//
// AI class for agressive behaviour
// 

package rl;

public class NastyCritterAI extends BaseAI {
	public static final NastyCritterAI instance=new NastyCritterAI();
	
	// try to move into square, attacking if necessary
	// return true if sucessful
	public boolean tryMove(Mobile m,int nx, int ny) {
    return m.tryMove(m.getMap(),nx,ny);
  }

/*    Map map=m.getMap();
    if (map==null) return false;
    if (m.canMove(map,nx,ny)) {
      try {
        m.aps=m.aps-(m.getStat(RPG.ST_MOVECOST)*100)/m.getStat(RPG.ST_MOVESPEED);
      } catch(Exception e) {return false;}
      m.moveTo(map,nx,ny);
      return true;
    } else {
      Mobile t=map.getMobile(nx,ny);
      if ((t!=null)&&(m.isHostile(t))) {
        m.attack(t);
        return true;
      }

      // use doors etc.
      Thing head=map.getObjects(nx,ny);
      while (head!=null) {
        if (head.isBlocking()) {
          head.use((Being)m);
          m.aps-=1;
          return false;       
        }
        if (head.isWarning(m)) {
          m.aps-=1;
          return false;
        }
        head=head.next;
      }

      return false;	
    }
	}  */
	
  public int notify(Mobile m, int eventtype, int ext, Object o) {
 		int state=m.getStat(RPG.ST_AIMODE);
    switch (eventtype) {
    	case EVENT_ALARM: case EVENT_VISIBLE:
     		reTarget(m,Game.hero.x,Game.hero.y);
     	  if (state!=1) {
	    		m.getMap().areaNotify(m.x,m.y,5,AI.EVENT_ALARM,0,null);
    		}
    		return 1;
    		
      case EVENT_ATTACKED: {
        if (o==Game.hero) m.setStat(RPG.ST_AIMODE,AI.STATE_HOSTILE); 
        return 1;
      }
      
      default:
      	return super.notify(m,eventtype, ext, o);	
    }
  }
	
  public void reTarget(Mobile m, int tx, int ty) {
 		m.setStat(RPG.ST_TARGETX,tx);
		m.setStat(RPG.ST_TARGETY,ty);
 	  m.setStat(RPG.ST_AIMODE,1);
  }
  
  // creature attacks towards target tx,ty
  public void doAttack(Mobile m, int tx, int ty) {
    Map map=m.getMap();
    if (map==null) return;
    boolean visible=m.isVisible();

    while (m.aps>=0) {
      if (RPG.d(100)<=m.getStat(RPG.ST_CASTCHANCE)) {
        if (CasterAI.instance.doAction(m)) continue; 
      }
          
      if (visible&&(RPG.d(3)==1)&&(m.getWielded(RPG.WT_MISSILE)!=null)) {
        ShooterAI.instance.doAction(m);
        continue; 
      }
          
      int dx=RPG.sign(tx-m.x);
      int dy=RPG.sign(ty-m.y);
          
      // run away?
      if ((m.getStat(RPG.ST_IN)>=6)&&((m.getStat(RPG.ST_HPS)*3)<=m.getStat(RPG.ST_HPSMAX))) {
        if (tryMove(m,m.x-dx,m.y-dy)) continue; 
        if (tryMove(m,m.x-RPG.sign(dx-dy),m.y-RPG.sign(dy+dx))) continue; 
        if (tryMove(m,m.x-RPG.sign(dx+dy),m.y-RPG.sign(dy-dx))) continue; 
      }
           
      if (tryMove(m,m.x+dx,m.y+dy)) continue; 
      if (tryMove(m,m.x+RPG.sign(dx-dy),m.y+RPG.sign(dy+dx))) continue; 
      if (tryMove(m,m.x+RPG.sign(dx+dy),m.y+RPG.sign(dy-dx))) continue; 
           
      if (RPG.d(2)==1) dx=RPG.r(3)-1;
      if (RPG.d(2)==1) dy=RPG.r(3)-1;
      if (!tryMove(m,m.x+dx,m.y+dy)) {
        m.aps-=100;
        m.xdirection=0;
        m.ydirection=0;
      }
    }
  }
  
  public boolean doAction(Mobile m) {
	  int state=m.getStat(RPG.ST_AIMODE);
	  Hero h=Game.hero;
	  Map map=m.getMap();
	  if (map==null) return true;
	  boolean visible=m.isVisible();
      
	  switch (state) {
	    case 0:
	    	if (visible) {
      		reTarget(m,Game.hero.x,Game.hero.y);
	    		map.areaNotify(m.x,m.y,6,AI.EVENT_ALARM,0,null);
	    	} else {
    	    // wander randomly
    	    while (m.aps>=0) {
	          int dx=RPG.r(3)-1;
	          int dy=RPG.r(3)-1;
            int nx=m.x+dx;
            int ny=m.y+dy;
            if (!tryMove(m,nx,ny)) m.aps-=100;;	        	
          }
          
          // why not pick up some items?
          if ((RPG.d(10)==1)&&(m.getStat(RPG.ST_CR)>=5)) {
            Thing[] stuff=map.getThings(m.x-1,m.y-1,m.x+1,m.y+1);
            boolean found=false;
            for (int i=0; i<stuff.length; i++) {
              if (stuff[i] instanceof Item) {
                m.addThing(stuff[i]);
                found=true; 
              }
            }
            if (found) ((Being)m).utiliseItems();
          }
	    	}
   	    break;
	    case 1: {
        if (visible) {
          Thing f=map.findNearestFoe(m);
          if (f==null) f=Game.hero;

          // pick up and use stuff
          if ((RPG.d(6)==1)&&(m.getStat(RPG.ST_CR)>=5)) {
            Thing t=map.getObject(m.x,m.y,Item.class);
            if (t!=null) {
              m.addThing(t); 
              ((Being)m).utiliseItems();
            }
          }
          
          doAttack(m,f.x,f.y);
        } else {
          if (RPG.d(6)==1) {
            m.setStat(RPG.ST_AIMODE,0);
          }
        }
        break;
	    }
	  }	
    return true;
	}	
}