package rl;

public class GuardianVault extends Vault {
  public Being guardian;
  
  public int visibleRange() {return 4;}
  
  public GuardianVault(Being guard, Thing treasure) {
    super(21,21);
  
    guardian=guard;
    entrance=new Portal("stairs up");
  
    switch(RPG.d(1)) {
      case 1: {
        fillArea(0,0,20,20,Tile.CAVEWALL);
        fillArea(6,5,14,15,Tile.CAVEFLOOR);
  
        for (int y=6; y<16; y=y+2) {
          setTile(7,y,Tile.CAVEWALL);
          setTile(13,y,Tile.CAVEWALL);
        }
  
        guardian.addThing(treasure); 
  
        addThing(guardian);
        addThing(entrance);
        break;
      }
      
      case 2: {
        makeRandomPath(0,0,20,20,0,0,20,20,Tile.CAVEFLOOR,false);
        for (int i=0; i<10; i++) {
          Point ss=findFreeSquare();
          Point fs=findFreeSquare();
          makeRandomPath(ss.x,ss.y,fs.x,fs.y,0,0,20,20,Tile.CAVEFLOOR,false);
        }
        replaceTiles(0,Tile.CAVEWALL);
        fillBorder(0,0,20,20,Tile.CAVEWALL);
        
        guardian.addThing(treasure); 
        Point es=findFreeSquare();
        addThing(entrance,es.x,es.y);
        Point gs=findFreeSquare();
        addThing(guardian,gs.x,gs.y);
      }  
    }
  }

  public String getEnterMessage() {
    return "This place seems to be a lair of some sort.";  
  }
  
  public String getLevelName() {
    return guardian.isVisible()?"Lair of "+guardian.getTheName():"A dark lair";  
  }
}