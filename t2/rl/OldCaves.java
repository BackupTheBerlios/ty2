package rl;

public class OldCaves extends Map {
  private int level;
  
  public OldCaves (int t, int l) {
    super(65,65);
    
    level=l;
    
    floortile=Tile.CAVEFLOOR;
    walltile=Tile.CAVEWALL;
    
    switch (t) {
      // top entrance of caves
      case 1: {
        fillArea(0,0,64,64,0);
        setTile(64,32,floortile);
        setTile(60,32,floortile);
        setTile(56,32,floortile);
        buildCaves(4);
        replaceTiles(0,walltile);
        fractalize(0,0,64,64,4);
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
     
    entrance=new Portal(0);
  } 
  
  
  // random cave construction
  // extends non-blank areas randomly
  //   g = size of cave units (4 expected)
  public void buildCaves(int g) {
    for (int i=0; i<5000; i++) {
      int x=RPG.d(width/g - 2)*g;
      int y=RPG.d(height/g - 2)*g;
    
      if (getTile(x,y)!=0) continue;
      
      int dir=1+RPG.r(4)*2;
      int dx=g*DX[dir];
      int dy=g*DY[dir];
      
      if (getTile(x+dx,y+dy)!=0) continue;
      
      int c = getTile(x+2*dx,y+2*dy);
      
      if (c!=0) {
        switch(RPG.d(15)) {
          case 1: c=Tile.RIVER; break;
          case 2: case 3: c=walltile; break;
        }
        setTile(x,y,c);
        setTile(x+dx,y+dy,c);
      }
    }
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
  
  public int getLevel() {
    return level; 
  }
  
  // set the message for this particular map
  public String getEnterMessage() {
    return "These caves are damp and sinister";  
  }


  // Level name for display on status panel
  public String getLevelName() {
    return "Caves";  
  }
}