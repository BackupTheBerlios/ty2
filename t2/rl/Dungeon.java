//
// Standard Tyrant dungeon
//

package rl;

import java.util.Random;

class Dungeon extends Map {
  // local dungeon stylee
  public int level;
  
  public static final Description DESC_ENTRANCE=
  	new Describer ("entrance","The entrance to this smelly dungeon.");
  
  public Dungeon(int w, int h, int lev) {
	  super(w,h);
    level=lev;
    
    // randomly choose wall type
    switch (RPG.d(6)) {
      case 1: 
      	 setTheme(new Theme("labyrinthe")); break;	
      case 2: case 3:
        setTheme(new Theme("dungeon")); break;	
      case 4: 
        setTheme(new Theme("caves")); break;  
      case 5: 
        setTheme(new Theme("sewers")); break;  
      case 6: 
        setTheme(new Theme("goblins")); break;  
    }
    
    makeDungeon(0,0,w-1,h-1);
  
    // fill in the blanks
    replaceTiles(0,walltile);

    // drop in some critters
    for (int i=0; i<((w*h)/500); i++) {
      addBeasties(RPG.r(w),RPG.r(h));
      Point p=findFreeSquare();
      addBaddie(p.x,p.y);	
    }

    Point ent=findFreeSquare();
    entrance=new Portal("stairs up");
    addThing(entrance,ent.x, ent.y);
  }

  
  // scan in direction (dx,dy) to find blank square
  public Point findEdge(int x,int y, int dx, int dy) {
    while (getTile(x,y)!=0) {
      x+=dx;
      y+=dy;
    }	
    if (isBlocked(x-dx,y-dy))	return null;
    return new Point(x,y);
  }
  
  // add a random trap to specified square
  // trap is invisible by default
  public void addTrap(int x, int y) {
    addThing(new HeroTrap(getLevel()),x,y); 
  }
  
  public void makeDungeon(int x1,int y1,int x2,int y2) {
  	fillArea(x1,y1,x2,y2,walltile);
  	fillArea(x1+1,y1+1,x2-1,y2-1,0);
  	
    // create a central room
  	fillArea((x1+x2)/2-RPG.d(3),(y1+y2)/2-RPG.d(3),(x1+x2)/2+RPG.d(3),(y1+y2)/2+RPG.d(3),floortile);
  	
    for (int buildloop=0;buildloop<300;buildloop++) {
      
      // first choose a direction
      int dx=0;
      int dy=0;
      switch (RPG.d(4)) {
        case 1: dx=1; break;	//W
        case 2: dx=-1; break;	//E
        case 3: dy=1; break;	//N
        case 4: dy=-1; break;	//S
      }	
      
      // now find a free extension point
      // Point p=findFreeSquare();
      // p=findEdge(p.x,p.y,dx,dy);
      Point p=findEdgeSquare(dx,dy,0);
      if (p==null) continue;
      // advance onto blank square
      p.x+=dx;
      p.y+=dy;
      
      // choose new feature to add
      switch(RPG.d(18)) {
      	case 1: case 2: case 3:
      		makeCorridor(p.x,p.y,dx,dy);
      		break;
       case 4: case 5: case 6:
      		makeOvalRoom(p.x,p.y,dx,dy);
      		break;
      	case 7: case 8: case 9: case 10: case 11: case 12:
      		makeRoom(p.x,p.y,dx,dy);
      		break;
      	case 13: case 14: case 15:
       		makeChamber(p.x,p.y,dx,dy);
       		break;
        case 16:
          makeTunnel(p.x,p.y,dx,dy);
          break;
        case 17: case 18:
          makeSquare(p.x,p.y,dx,dy);
          break;  
      }
    }
  }

  // make a dungeon door
  public void makeDoor(int x,int y, int dx,int dy) {
    switch (RPG.d(10)) {
    	case 1: case 2:
    		setTile(x,y,walltile);
    		addThing(new SecretDoor(),x,y);
    		break;
    	case 3:
    		setTile(x,y,floortile);
    	  addThing(new Door("portcullis",getLevel()),x,y);
    	  break;
    	default:
    	  setTile(x,y,floortile);
    	  addThing(Door.createDoor(getLevel()),x,y);
    	  break;
    }
  }
  
  private boolean makeRoom(int x,int y, int dx, int dy) {
   	// random dimesions and offset
   	int x1=x-RPG.d(RPG.abs(dx-1),5);
   	int y1=y-RPG.d(RPG.abs(dy-1),5);
   	int x2=x+RPG.d(RPG.abs(dx+1),5);
   	int y2=y+RPG.d(RPG.abs(dy+1),5);
   	
   	if (((x2-x1)<3)||((y2-y1)<3)||(!isBlank(x1,y1,x2,y2))) return false;
    
    if (RPG.d(3)==1) fillArea(x1,y1,x2,y2,walltile);
    fillArea(x1+1,y1+1,x2-1,y2-1,floortile);
    
    // make entrance wall
    fillArea((dx==0)?x1:x,(dy==0)?y1:y,(dx==0)?x2:x,(dy==0)?y2:y,walltile);
    // make the door
    setTile(x,y,floortile);
    addThing(new Door(getLevel()),x,y);
    
    addRoomFeatures(x1+1,y1+1,x2-1,y2-1);
    
    return true;
  }
  
  // adds spice to room area.... assumes blank floor
  private void addRoomFeatures(int x1, int y1, int x2, int y2) {
    int w=x2-x1+1; int h=y2-y1+1;
    switch (RPG.d(30))  {
      case 1: case 2:
        if ((w*h)<66) for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
          if (RPG.d(5)==1) addGuard(x,y);	
        } 
        if (RPG.d(20)==1) addThing(new PowderKeg(),x1+RPG.r(w),y1+RPG.r(h));
        break;

      case 3: case 4:
      	if ((w>=5)&&(h>=5)) {
      	  if ((w%2)==1) for (int lx=1; lx<w; lx+=2) {
      	    setTile(x1+lx,y1+1,walltile);	
      	    setTile(x1+lx,y2-1,walltile);	
      	  }	
      	  if ((h%2)==1) for (int ly=1; ly<h; ly+=2) {
      	    setTile(x1+1,y1+ly,walltile);	
      	    setTile(x2-1,y1+ly,walltile);	
      	  }	
      	}
      	break;

      case 5: case 6:
      	int planttype=RPG.d(3); // 1, 2 or 3 plant types
      	for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
      	  if (RPG.d(5)==1) addThing(GameScenery.create(planttype),x,y);	
      	}
      	break; 

      case 7: case 8:
      	addThing(Lib.createItem(0),x1+RPG.r(w),y1+RPG.r(h));
      	break;

      case 9: // unfilled central area
      	fillArea(x1+1,y1+1,x2-1,y2-1,0);
      	break;

      case 10: case 11: // central room
      	if ((w>5)&&(h>5)) {
      		fillArea(x1+1,y1+1,x2-1,y2-1,walltile);
      		fillArea(x1+2,y1+2,x2-2,y2-2,floortile);
      		addRoomFeatures(x1+2,y1+2,x2-2,y2-2);
      		switch (RPG.d(4)) {
            case 1: makeDoor(x1+1,y1+1+RPG.d(h-4),1,0); break; 			
            case 2: makeDoor(x2-1,y1+1+RPG.d(h-4),1,0); break; 			
            case 3: makeDoor(x1+1+RPG.d(w-4),y1+1,0,1); break; 			
            case 4: makeDoor(x1+1+RPG.d(w-4),y2-1,0,1); break; 			
      		}
      	}	
      	break;

      case 12: // room with runetraps;
      	for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
      	  if (RPG.d(4)==1) addThing(new RuneTrap(),x,y);	
      	}
      	break; 

      case 13: 
      	addThing(Lib.createItem(0),x1+RPG.r(w),y1+RPG.r(h));
      	addThing(new HeroTrap(),x1+RPG.r(w),y1+RPG.r(h));
      	break;

      case 14: case 15: case 16: // vertically partitioned room
      	if (w>3) {
      	  int x=x1+RPG.d(w-2);
      	  if (isBlank(x,y1-1,x,y1-1)&&isBlank(x,y2+1,x,y2+1)) {
      	    fillArea(x,y1,x,y2,walltile);	
        		int y=y1+RPG.r(h);
        		addThing(new SecretDoor(),x,y); // opens walltile
        		addGuard(x+1,y);
        		addGuard(x-1,y);
     	    }	
      	}	
      	break;
      
      case 17: case 18: case 19: // horizontally partitioned room
      	if (h>3) {
      	  int y=y1+RPG.d(h-2);
      	  if (isBlank(x1-1,y,x1-1,y)&&isBlank(x2+1,y,x2+1,y)) {
      	    fillArea(x1,y,x2,y,walltile);	
        		int x=x1+RPG.r(w);
        		addThing(new SecretDoor(),x,y); // opens walltile
        		addGuard(x,y+1);
        		addGuard(x,y-1);
     	    }	
      	}	
      	break;

      case 20:
        addThing(new SpellBook(),x1+RPG.r(w),y1+RPG.r(h));
        break;	

      case 21:
        fillArea(x1+1,y1+1,x2-1,y2-1,Tile.POOL);
        break;
        
      case 22: case 23:
        addThing(Food.createFood(0),x1,y1,x2,y2);
        break;
      
      case 24:
        // traps and bones
        for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
          if (RPG.d(4)==1) addThing(new HeroTrap(),x,y);  
        }
        addThing(new Food("corpse"),x1,y1,x2,y2);
        addThing(new Food("corpse"),x1,y1,x2,y2);
        break; 
        
        
      default:
        break;
    }
  }
  
  public void addBeasties(int x, int y) {
    Creature m=Lib.createMonster(getLevel());
    for (int i=RPG.d(Creature.numbers[m.creaturetype]); i>0; i--) {
      Point p=findFreeSquare(x-2,y-2,x+2,y+2);
      if (p!=null) {
        addThing(m.cloneType(),p.x,p.y);
      }  
    }
  }

  private void addGuard(int x, int y) {
    if (isClear(x,y)) {
    	Mobile m=Lib.createCreature(getLevel());
    	addThing(m,x,y);
    }	
  }

  protected void addWandering() {
    Point p=findFreeSquare();
    int x=p.x; int y=p.y;
    if (!isVisible(x,y)) {
      addBaddie(x,y);
    }	
  }

  private void addBaddie(int x, int y) {
    if (isClear(x,y)) {
      Mobile m=Lib.createFoe(getLevel());
      // m.addThing(Lib.createItem(0));
      addThing(m,x,y);
      if (RPG.d(10)==1) m.addThing(Lib.createItem(getLevel()));
    }	
  }
  
  // oval room, pretty cool
  private void makeOvalRoom(int x,int y, int dx, int dy) {
    int w=RPG.d(2,3); 
    int h=RPG.d(2,3);
    int x1=x+(dx-1)*w;
    int y1=y+(dy-1)*h;
    int x2=x+(dx+1)*w;
    int y2=y+(dy+1)*h;
      
    if (!isBlank(x1,y1,x2,y2)) return;
    
    int cx=(x1+x2)/2;
    int cy=(y1+y2)/2;
    
    for (int lx=x1; lx<=(x1+w*2); lx++) for (int ly=y1; ly<(y1+h*2); ly++) {
      if ( (((lx-cx)*(lx-cx)*100)/(w*w) + ((ly-cy)*(ly-cy)*100)/(h*h)) < 100 )
      	   setTile(lx,ly,floortile);
    }    

    fillArea(cx,cy,x,y,floortile); 
  }

  // make a 3*3 special chamber with interesting stuff
  public boolean makeChamber(int x,int y, int dx, int dy) {
  	int x1=x+(dx-1)*2;
  	int y1=y+(dy-1)*2;
  	int x2=x+(dx+1)*2;
  	int y2=y+(dy+1)*2;
  	int cx=x1+2;
  	int cy=y1+2;
  	
  	if (!isBlank(x1,y1,x2,y2)) return false;
  	
  	fillArea(x1+1,y1+1,x2-1,y2-1,floortile);
  	setTile(x,y,floortile);
  	makeDoor(x,y,dx,dy);
  	
  	boolean continued=false;
  	
  	switch (RPG.d(4)) {
  	  case 1: case 2: continued=(makeRoom(cx+2*dx,cy+2*dy,dx,dy)||continued); break;
  	  case 3: continued=(makeChamber(cx+2*dx,cy+2*dy,dx,dy)||continued); break;
   	}
  	if (RPG.d(3)==1) continued=(makeChamber(cx+2*dy,cy+2*dx,dy,dx)||continued);
  	if (RPG.d(3)==1) continued=(makeChamber(cx-2*dy,cy-2*dx,-dy,-dx)||continued);
  	
  	if (!continued) {
  	  // we have an ending chamber, so add something interesting
  	  switch (RPG.d(4)) {
  	  	case 1: 
  	  		addThing(new HeroTrap(),cx,cy); break;
  	  	case 2:
  	  		addThing(new RuneTrap(),cx,cy); 
  	  		addThing(new RuneTrap(),cx+dy,cy-dx); 
  	  		addThing(new RuneTrap(),cx-dy,cy+dx);
  	  		break; 
  	  }
  	  addThing(new Chest(getLevel()),cx+dx,cy+dy);	
  	}
  		
  	completeArea(x1,y1,x2,y2,walltile);
  	
  	return true;	
  }
  
  // make a 5*5 special chamber with interesting stuff
  public boolean makeSquare(int x,int y, int dx, int dy) {
    int x1=x+(dx-1)*3;
    int y1=y+(dy-1)*3;
    int x2=x+(dx+1)*3;
    int y2=y+(dy+1)*3;
    int cx=x1+3;
    int cy=y1+3;
    
    if (!isBlank(x1,y1,x2,y2)) return false;
    
    fillArea(x1+1,y1+1,x2-1,y2-1,floortile);
    setTile(x,y,floortile);
    makeDoor(x,y,dx,dy);
    
    boolean continued=false;
    
    // room ahead
    switch (RPG.d(4)) {
      case 1: case 2: continued=(makeRoom(cx+3*dx,cy+3*dy,dx,dy)||continued); break;
      case 3: continued=(makeChamber(cx+3*dx,cy+3*dy,dx,dy)||continued); break;
     }
    
    // side rooms
    if (RPG.d(2)==1) {
      if (RPG.d(2)==1) continued=(makeChamber(cx+3*dy,cy+3*dx,dy,dx)||continued);
      else continued=(makeRoom(cx+3*dy,cy+3*dx,dy,dx)||continued);
    }
    if (RPG.d(2)==1) {
      if (RPG.d(2)==1) continued=(makeChamber(cx-3*dy,cy-3*dx,-dy,-dx)||continued);
      else continued=(makeChamber(cx-3*dy,cy-3*dx,-dy,-dx)||continued); 
    }
    
    if (continued) {
      switch(RPG.d(4)) {
        case 1: 
          addThing(new Chest(getLevel()),cx,cy); 
          addThing(new HeroTrap(),cx+dx,cy+dy);
          break;
        case 2:
          addBeasties(cx,cy);
          break;
        case 3:
          addThing(new Fire(5,0),cx+1,cy+1);
          addThing(new Fire(5,0),cx-1,cy+1);
          addThing(new Fire(5,0),cx+1,cy-1);
          addThing(new Fire(5,0),cx-1,cy-1);
          break;
        case 4:
          addRoomFeatures(x1+1,y1+1,x2-1,y2-1);
          break;
      }
    } else {
      // we have an ending chamber, so add something really interesting
      switch (RPG.d(1)) {
        case 1: 
          addThing(Creature.createCreature(Creature.spawn,getLevel()+3),cx,cy); 
          addThing(new Chest(getLevel()+3),cx+dx,cy+dy); 
          break;
      }
    }
      
    completeArea(x1,y1,x2,y2,walltile);
    
    return true;  
  }

  // probably want to override this to add wandering critters
  // remember to call super.action(time) so that things move
  public void action(int time) {
    super.action(time);
  }
  
  // make a random twisty tunnel
  public boolean makeTunnel(int x, int y, int dx, int dy) {
    if ((getTile(x,y)==0)&&isValid(x,y)) {
      int ndx=dx; int ndy=dy;
      setTile(x,y,floortile);
      if (RPG.d(3)==1) {ndx=-dy; ndy=dx;}
      if (RPG.d(4)==1) {ndx=dy; ndy=-dx;}
      makeTunnel(x+ndx,y+ndy,ndx,ndy);	
      return true;
    }	
    return false;
  }
  
  // determine how nasty the critters are
  public int getLevel() {
    return level; 
  }

  // make a long corridor
  public boolean makeCorridor(int x, int y, int dx, int dy) {
    int l=RPG.d(2,8);
    
    // don't build if there is no space
    if (!isBlank(x,y,x+dx*l,y+dy*l)) return false;
    
    for (int i=0;i<l;i++) {
   	  setTile(x+i*dx,y+i*dy,floortile);	
    }
     
    // add a door if there is space
    if ((l>4)&&(RPG.d(2)==1)) {
    	if (completeTile(x+dy,y+dx,walltile)
    	   &completeTile(x-dy,y-dx,walltile)) 
    	{
    	 	makeDoor(x,y,dx,dy);
    	}
    }
    
    // add a room to end
    if ((l>3)&&(RPG.d(2)==1)) makeRoom(x+dx*l,y+dy*l,dx,dy);
  
    if (RPG.d(100)==1) addTrap(x+RPG.r(l)*dx,y+RPG.r(l)*dy);
  
    return true;
  }
}