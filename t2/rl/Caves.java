package rl;

public class Caves extends Map {
  private int level;
  
  // craete caves of type t, and level l
  public Caves (int t, int l) {
    super(65,65);
    
    level=l;
    
    setTheme(new Theme("caves"));
    
    switch (t) {
      // top entrance of caves
      case 1: {
        fillArea(0,0,64,64,walltile);
        fillArea(1,1,63,63,0);
        fillArea(30,30,32,32,floortile);
        makeCaves(0,0,64,64);
        break;
      }

      // deeper caves level
      case 2: {
        fillArea(0,0,64,64,walltile);
        fillArea(1,1,63,63,0);
        fillArea(30,30,32,32,floortile);
        makeCaves(0,0,64,64);
        break;
      }
      
      // bandit's lair level
      case 3: {
        floortile=Tile.FLOOR;
        walltile=Tile.WALL;
        fillArea(0,0,64,64,walltile);
        fillArea(1,1,63,63,0);
        fillArea(27,27,35,35,floortile);
        addThing(new Fire(5,0),27,27,35,35);
        addThing(new Fire(5,0),27,27,35,35);
        
        // create the bandit leader.
        // wayhey!
        Being bandit=new Creature(Creature.BANDITLEADER);
        bandit.setAI(new GuardAI(27,27,35,35));
        Quest q=Game.hero.getQuest("bandit caves");
        q.setTarget(bandit);
        addThing(bandit,27,27,35,35);
        
        for (int i=RPG.d(2,4); i>0; i--) {
          Being b=new Creature(RPG.pick(new int[] {Creature.BANDIT,Creature.SWORDSMAN,Creature.WARLOCK,Creature.DOG})); 
          b.setAI(new FollowerAI(bandit));
          addThing(b,27,27,35,35);
        }
        
        makeCaves(0,0,64,64);
        break;
      }
      
      default: {
        break;
      }
    }
    
    {
      Point ts=findFreeSquare();
      addBeasties(ts.x,ts.y);
    }
     
    entrance=new Portal("ladder up");
    addThing(entrance);
  } 
  

  // Make the caves. Pure Art
  public void makeCaves(int x1,int y1,int x2,int y2) {
    for (int buildloop=0;buildloop<200;buildloop++) {
      
      // first choose a direction
      int dx=0;
      int dy=0;
      switch (RPG.d(4)) {
        case 1: dx=1; break;  //W
        case 2: dx=-1; break;  //E
        case 3: dy=1; break;  //N
        case 4: dy=-1; break;  //S
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
      switch (RPG.d(7)) {
        case 1: case 2: 
          makeThinPassage(p.x,p.y,dx,dy,false);
          break;
        
        case 3:
          makeThinPassage(p.x,p.y,dx,dy,true);
          break;
          
        case 4: case 5: case 6:
          makeOvalRoom(p.x,p.y,dx,dy);
          break;
          
        case 7:
          makeCrevice(p.x,p.y,dx,dy);
          break;
          
        /*
        case 8: { 
          int tx=p.x+dx*RPG.d(2,12)+(RPG.r(5)-2)*RPG.d(5);
          int ty=p.y+dy*RPG.d(2,12)+(RPG.r(5)-2)*RPG.d(5);
          if (makePath(p.x,p.y,tx,ty)) {
            makeOvalRoom(tx,ty,dx,dy);
          }
          break;
        }
        */
      }
    }

    // turn all blanks into walls
    replaceTiles(0,walltile);
    
    // now do some decoration
    for (int i=0; i<13; i++){ addThing(new Missile(Missile.ROCK,1)); }
    for (int i=0; i<6 ; i++){ addThing(new Missile(Missile.STONE,1)); }
  
    // bat cave
    {
      Portal p=new Portal("small cave");
      p.spawnsource=new int[] {Creature.BAT,Creature.BAT,Creature.BAT};
      p.spawnrate=10;
      addThing(p);
    }
  }

  // makes a thin passage
  // put a wall around first half of passage
  // this should prevent early branching
  public boolean makeThinPassage(int x, int y, int dx, int dy, boolean diagonals) {
    int len=RPG.d(2,12);
    int size=RPG.d(4);
    
    if (!isBlank(x+size*dy,y-size*dx,x-size*dy+len*dx,y+size*dx+len*dy)) return false;
    
    fillArea(x+size*dy,y-size*dx,x-size*dy+len*dx/2,y+size*dx+len*dy/2,walltile);   
    int p=0;
    for (int i=0; i<=len; i++) {
      setTile(x+i*dx+p*dy,y+i*dy-p*dx,floortile);
      if (RPG.d(2)==1) p=RPG.middle(-size,p+RPG.r(2)-RPG.r(2),size);
      if (!diagonals) setTile(x+i*dx+p*dy,y+i*dy-p*dx,floortile);
    }
    return true;
  }
  
  // nooks and crannies
  private boolean makeCrevice(int x,int y, int dx, int dy) {
    if (!isBlank(x+dy,y-dx,x-dy,y+dx)) return false;
    setTile(x+dy,y-dx,walltile); 
    setTile(x-dy,y+dx,walltile); 
    
    switch (RPG.d(10)) {
      case 1: // oval room continuation
        setTile(x,y,floortile);
        makeOvalRoom(x+dx,y+dy,dx,dy);
        break;
      
      case 2: // trap!
        setTile(x,y,floortile);
        addThing(new HeroTrap(getLevel()),x,y);
        if ((RPG.d(3)==1)&&(getTile(x+dx,y+dy)==0)) {
          setTile(x+dx,y+dy,floortile);
          if (RPG.d(3)==1) addThing(Lib.createItem(getLevel()),x+dx,y+dy); 
        }
        break;
        
      case 3: // recurse!
        setTile(x,y,floortile);  
        makeCrevice(x+dx+(RPG.r(3)-1)*dy,y+dy-(RPG.r(3)-1)*dx,dx,dy);
        break;
      
      case 4: // secret route
        makeSecretDoor(x,y);
        if (RPG.d(2)==1) {
          makeCrevice(x+dx,y+dy,dx,dy);
        } else {
          makeOvalRoom(x+dx,y+dy,dx,dy); 
        }
        break;
        
      default: setTile(x,y,floortile); 
    }
    
    return true;
  }
  
  private void makeSecretDoor(int x,int y) {
    setTile(x,y,walltile);
    addThing(new Secret(Secret.PASSAGE),x,y); 
  }
  
  // oval cave, pretty cool
  private void makeOvalRoom(int x,int y, int dx, int dy) {
    int w=RPG.d(7); 
    int h=RPG.d(7);
    int x1=x+(dx-1)*w;
    int y1=y+(dy-1)*h;
    int x2=x+(dx+1)*w;
    int y2=y+(dy+1)*h;
      
    if (!isBlank(x1,y1,x2,y2)) return;
    
    int cx=(x1+x2)/2;
    int cy=(y1+y2)/2;
    
    for (int lx=x1; lx<=(x1+w*2); lx++) for (int ly=y1; ly<(y1+h*2); ly++) {
      if ( (((lx-cx)*(lx-cx)*100)/(w*w) + ((ly-cy)*(ly-cy)*100)/(h*h)) < 100 ){
           setTile(lx,ly,floortile);
		}
    }    

    fillArea(cx,cy,x,y,floortile); 
  }

  // make a twisty path
  private boolean makePath(int x1,int y1, int x2, int y2) {
    int dx; int dy;
    while ((x1!=x2)||(y1!=y2)) {
      if (getTile(x1,y1)==0) {
        setTile(x1,y1,floortile);
      } else {
        // bail out if truly blocked
        if (isBlocked(x1,y1)) return false; 
      } 
      if (RPG.d(3)==1) {
        dx=RPG.sign(x2-x1);  
        dy=RPG.sign(y2-y1);  
      } else {
        dx=RPG.r(3)-1;  
        dy=RPG.r(3)-1;  
      }
      switch (RPG.d(3)) {
        case 1: dx=0; break;
        case 2: dy=0; break;  
      }
      x1+=dx;
      y1+=dy;
      x1=RPG.middle(1,x1,width-1);
      y1=RPG.middle(1,y1,height-1);
    }
    return true;  
  }

  // make a random twisty tunnel
  public boolean makeTunnel(int x, int y, int dx, int dy) {
    if (getTile(x,y)==0) {
      int ndx=dx; int ndy=dy;
      setTile(x,y,floortile);
      if (RPG.d(3)==1) {ndx=-dy; ndy=dx;}
      if (RPG.d(4)==1) {ndx=dy; ndy=-dx;}
      makeTunnel(x+ndx,y+ndy,ndx,ndy);  
      return true;
    }  
    return false;
  }

  // add outdoor critters
  public void addBeasties(int x, int y) {
    Creature m=Creature.createCreature(Creature.spawn,getLevel());
    for (int i=RPG.d(Creature.numbers[m.creaturetype]); i>0; i--) {
      int bx=x-2+RPG.r(5);
      int by=y-2+RPG.r(5);
      if (isClear(bx,by)) {
        addThing(m.cloneType(),bx,by);
      }  
    }
  }

  // can exit if there are no nearby hostiles
  public boolean canExit() {
    Hero h=Game.hero;
    Mobile m=getNearbyMobile(h.x,h.y,10);
    if (Game.hero.x!=(width-1)) return false;
    if ((m==null)||!(h.isHostile(m))) {
      return true;
    } else {
      Game.message("You cannot leave with enemy near!");
      return false; 
    }
  }

  private void addGuard(int x, int y) {
    if (isClear(x,y)) {
      Mobile m=Creature.createCreature(Creature.spawn,getLevel());
      addThing(m,x,y);
    }  
  }

  protected void addWandering() {
    Point p=findFreeSquare();
    int x=p.x; int y=p.y;
    if (!isVisible(x,y)) {
      if (RPG.d(3)==1) {
        addBaddie(x,y);
      } else {
        addBeasties(x,y); 
      };
    }  
  }

  private void addBaddie(int x, int y) {
    if (isClear(x,y)) {
      Mobile m=Creature.createCreature(Creature.spawn,getLevel());
      // m.addThing(Lib.createItem(0));
      addThing(m,x,y);
      if (RPG.d(10)==1) m.addThing(Lib.createItem(getLevel()));
    }  
  }
    
  public int getLevel() {
    return level; 
  }
  
  // set the message for this particular map
  public String getEnterMessage() {
    return "These caves are damp and sinister";  
  }

  // action in the caves
  // add wandering critters
  public void action(int time) {
    super.action(time);
    
    for (int i=RPG.po(time,5000); i>0; i--) {
      addWandering(); 
    }
  }

  // Level name for display on status panel
  public String getLevelName() {
    return "Caves";  
  }
}