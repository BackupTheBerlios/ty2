// Object representing the Tyrant World Map
//
// Contains code for handling:
//   long-distance travel
//   wilderness encounters
//   creating outdoor map locations
//

package rl;

public class WorldMap extends Map {
  
  protected int[] terrains = {Tile.PLAINS, Tile.PLAINS,Tile.FORESTS,Tile.HILLS,Tile.MOUNTAINS,Tile.SEA,Tile.SWAMP,Tile.SEA};
  
  public WorldMap(int w, int h) {
    super(w,h);
    
    
    for (int x=0; x<w; x+=4) {
      setTile(x,0,Tile.MOUNTAINS); 
      setTile(x,h-1,Tile.MOUNTAINS); 
    }
    for (int y=0; y<h; y+=4) {
      setTile(0,y,Tile.MOUNTAINS); 
      setTile(w-1,y,Tile.SEA); 
    }

    for (int y=4; y<h-4; y+=4) for (int x=4; x<w-4; x+=4){
      setTile(x,y,terrains[RPG.r(terrains.length)]);

    }
    
    setTile(16,8,Tile.FORESTS);
    setTile(16,12,Tile.FORESTS);
    setTile(16,16,Tile.PLAINS);
    setTile(12,12,Tile.FORESTS);
    setTile(8,12,Tile.HILLS);
    
    fractalizeBlock(0,0,w-1,h-1,4); 
    
    // starting town + quest
    addThing(new PlacePortal(PlacePortal.TOWN),16,16);
    addThing(new PlacePortal(PlacePortal.RUIN),16,8);

    // some other towns
    addThing(new PlacePortal(PlacePortal.TOWN),0,0,width-1,height-1);
    addThing(new PlacePortal(PlacePortal.TOWN),0,0,width-1,height-1);

    addThing(new PlacePortal(PlacePortal.GOBLINTRIBE),0,0,width-1,height-1);
  }
  
  
  
  // can we explore this square?
  public boolean canExit() {
    Game.message("There is nothing of interest here");
    return false;
  }
  
  // enter a world map square
  //  - if there is a portal to a special location then use it
  //  - else create the appropraite outdoor area map
  //
  public void exitMap(int tx, int ty) {
    Hero h=Game.hero;
    Portal p=(Portal)getObject(tx,ty,Portal.class);
    
    if (p!=null) {
      p.travel(h);
    } else {
      // put hero into a local area map
      Map m=createArea(tx,ty);
      p=new Portal(0);
      p.setDestination(m.entrance);
      // could build a portal here so we can return to exact location map
      // Don't think we really want this though
      //   addThing(p,tx,ty);
      //   m.entrance.setDestination(p);
      p.travel(h); 
    }
  }

  // move costs are much higher for outdoor distances
  public int getMoveCost(Thing mover,int x, int y) {
    return 10*super.getMoveCost(mover,x,y); 
  }

  // set the message for this particular map
  public String getEnterMessage() {
    return "This fertile valley is known as North Karrain";  
  }

  // Level name for display on status panel
  public String getLevelName() {
    return "The Great Outdoors";  
  }
  
  // override hitsqure for random encounters
  public void hitSquare(Thing m,int x, int y,boolean touchfloor) {
    if (m==Game.hero) {
      Thing p=getObject(x,y,Portal.class);
      if (p==null) encounter(x,y);
    }
    super.hitSquare(m,x,y,touchfloor);
  }
  
  // called to check for random encounters
  public void encounter(int x, int y) {
    if (RPG.d(10)>1) return;
    
    Hero h=Game.hero;
    Game.mappanel.viewPosition(x,y);

    switch (RPG.d(4)) {
      case 1: {
        Game.message("You see somebody in the distance"); 
        break;
      }
      
      case 2: {
        Creature c=Lib.createFoe(Game.hero.getStat(RPG.ST_LEVEL)-1);
        Game.message("You see "+c.getAName()+" waiting to ambush travellers"); 
        Game.message("Do you want to attack? (y/n)");

        if (Game.getOption("yn")=='y') {
          Game.message("");
          
          // create location map 
          Map m=createArea(x,y);
          
          m.addThing(Game.hero,32,32);
          m.addThing(c,32,25);
          
          // companions
          if (RPG.d(2)==1) {
            for (int i=RPG.d(6); i>0; i--) {
              int gx=28+RPG.r(9);
              int gy=18+RPG.r(7);
              if (!m.isBlocked(gx,gy)) {
                m.addThing(c.cloneType(),gx,gy); 
              } 
            }
            if ((RPG.d(2)==1)&&(!m.isBlocked(32,20))) {
              m.addThing(Lib.createFoe(Game.hero.getStat(RPG.ST_LEVEL)+1),32,20); 
            }
          }
        } else {
          Game.message("You avoid the encounter"); 
        }
        break;
      }
      
      case 3: {
        Creature c=Lib.createFoe(Game.hero.getStat(RPG.ST_LEVEL)-1);
        Game.message("You see some villagers being attacked by "+c.getDescription().getName(2,Description.ARTICLE_INDEFINITE)); 
        Game.message("Do you want to aid them? (y/n)");

        if (Game.getOption("yn")=='y') {
          // create location map 
          Map m=createArea(x,y);

          m.addThing(Game.hero,32,32);
          
          // villages
          for (int i=RPG.d(2,3); i>0; i--) {
            int gx=29+RPG.r(7);
            int gy=25+RPG.r(7);
            if (!m.isBlocked(gx,gy)) switch(RPG.d(3)) {
              case 1: m.addThing(new Person(Person.TOWNIE),gx,gy); break;
              case 2: m.addThing(new Person(Person.GIRL),gx,gy); break;
              case 3: m.addThing(new Person(Person.WOMAN),gx,gy); break;
            } 
          }
          
          // critters
          for (int i=RPG.d(2,6); i>0; i--) {
            int gx=28+RPG.r(9);
            int gy=18+RPG.r(7);
            if (!m.isBlocked(gx,gy)) {
              m.addThing(c.cloneType(),gx,gy); 
            } 
          }
          // boss critter
          m.addThing(Lib.createFoe(Game.hero.getStat(RPG.ST_LEVEL)+1),32,20); 
        } else {
          Game.message("You leave them to their fate"); 
        }
        break;
      }

      case 4: {
        Game.message("You are slowed by bad weather conditions."); 
        h.aps-=1000;
        break;
      }

    }
  }
  
  // create Outdoor Area for specified square
  // can drop hero strainght into this
  public Map createArea(int tx, int ty) {
    Outdoors m=new Outdoors(getTile(tx,ty));
    
    // link back to worldmap
    m.entrance.setDestination(new TargetPortal(this,tx,ty));
    
    return m;
  }
  
  public void action(int time) {
    super.action(time); 
  }
}