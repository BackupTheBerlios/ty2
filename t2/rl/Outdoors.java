//
// Standard outdoor map locations
//
// Created based on the WoldMap terrain type
//

package rl;

public class Outdoors extends Map {
  
  public Outdoors (int t) {
    super(65,65);
    buildOutdoors(this,0,0,width-1,height-1,t);
    
    entrance=new EdgePortal(EdgePortal.ZONE);
    addThing(entrance,32,32);
  }
  
  public static void buildOutdoors(Map map, int x1, int y1, int x2, int y2, int t) {  
    int w=x2-x1+1;
    int h=y2-y1+1;
    int area=w*h;

    switch (t) {
      case Tile.PLAINS: {
        map.setTheme("plains");
        map.fillArea(x1,y1,x2,y2,map.floortile); 
        for (int i=0; i<(area/50); i++) {
          Point ts=map.findFreeSquare();
          map.addThing(GameScenery.create(1),ts.x,ts.y);
        }
        for (int i=0; i<(area/400); i++) {
          map.addThing(new Missile(Missile.STONE,1),x1,y1,x2,y2);
        }
        Point ts=map.findFreeSquare(x1,y1,x2,y2);
        map.addThing(GameScenery.create(1),ts.x,ts.y);
        map.addThing(new SecretItem(Lib.createItem(4)),ts.x,ts.y);
        break;
      }
         
      case Tile.HILLS: {
        map.setTheme("plains");
        map.fillArea(x1,y1,x2,y2,map.floortile); 
        for (int i=0; i<(area/10); i++) {
          map.setTile(x1+RPG.r(w),y1+RPG.r(h),Tile.HILLS);
        }
        for (int i=0; i<(area/40); i++) {
          Point ts=map.findFreeSquare(x1,y1,x2,y2);
          map.addThing(GameScenery.create(1),ts.x,ts.y);
        }
        for (int i=0; i<(area/80); i++) {
          Point ts=map.findFreeSquare(x1,y1,x2,y2);
          map.addThing(GameScenery.create(3),ts.x,ts.y);
        }
        break;
      }

      case Tile.SWAMP: {
        map.setTheme("swamp");
        map.fillArea(x1,y1,x2,y2,map.floortile); 
        
        for (int lx=x1; lx<=x2; lx+=8) for (int ly=y1; ly<=y2; ly+=8) {
          int r=RPG.pick(new int[] {Tile.PLAINS,map.floortile,map.floortile});
          map.setTile(lx,ly,r);
        }
        map.fractalize(x1,y1,x2,y2,8);
        
        //for (int i=0; i<(area/10); i++) {
        //  int px=x1+RPG.r(w);
        // int py=y1+RPG.r(h);
        //  map.setTile(px,py,Tile.CAVEFLOOR);
        //}
        for (int i=0; i<(area/40); i++) {
          Point ts=map.findFreeSquare(x1,y1,x2,y2);
          map.addThing(GameScenery.create(3),ts.x,ts.y);
        }
        break;
      }

      case Tile.FORESTS: {
        map.setTheme("woods");
        map.fillArea(x1,y1,x2,y2,map.floortile); 
        for (int i=0; i<(area/20); i++) {
          Point ts=map.findFreeSquare(x1,y1,x2,y2);
          map.addThing(GameScenery.create(3),ts.x,ts.y);
        }
        for (int i=0; i<(area/80); i++) {
          Point ts=map.findFreeSquare(x1,y1,x2,y2);
          map.addThing(GameScenery.create(5),ts.x,ts.y);
        }
        break;
      }

      default: {
        map.fillArea(x1,y1,x2,y2,t); 
        break;
      }
    }
    
    {
      Point ts=map.findFreeSquare(x1,y1,x2,y2);
      addBeasties(map,ts.x,ts.y);
    }
     
  } 
  
  // add outdoor critters
  public static void addBeasties(Map map, int x, int y) {
    Creature m=Creature.createCreature(Creature.spawn,map.getLevel());
    for (int i=RPG.d(Creature.numbers[m.creaturetype]); i>0; i--) {
      int bx=x-2+RPG.r(5);
      int by=y-2+RPG.r(5);
      if (map.isClear(bx,by)) {
        map.addThing(m.cloneType(),bx,by);
      }  
    }
  }

  // can exit if there are no nearby hostiles
  public boolean canExit() {
    Hero h=Game.hero;
    Mobile m=getNearbyMobile(h.x,h.y,10);
    if ((m==null)||!(h.isHostile(m))) {
      return true;
    } else {
      Game.message("You cannot leave with enemy near!");
      return false; 
    }
  }
  
  // set the message for this particular map
  public String getEnterMessage() {
    return "";  
  }

  // Level name for display on status panel
  public String getLevelName() {
    return "Somewhere in the Wild";  
  }
}