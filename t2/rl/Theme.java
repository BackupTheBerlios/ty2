//  Defines Themes for different dungeons
//
//  Themes can be responsible for any or all of the following:
//   - Tile Types
//   - Random decorations
//   - Random objects
//   - Room decorations
//   - Creature generation
//
//
//  usage:
//       map.setTheme(new Theme("woods"));
//

package rl;

import java.io.*;

public class Theme implements Serializable {
  public static final int HARDFLOOR=1;
  public static final int HARDWALL=2;
  public static final int SOFTFLOOR=3;
  public static final int SOFTWALL=4;
  public static final int STREAM=5;
  public static final int RIVER=6;

  public static final String[] names   = {"woods",          "standard",     "village",       "town",           "darktown",       "city",           "goblins",       "hills",         "plains",       "forest",       "deepforest",     "caves",         "dungeon",    "labyrinthe",      "sewers",        "swamp"};
  public static final int[] hardwalls  = {Tile.WALL       , Tile.WALL,      Tile.WALL,       Tile.WALL,        Tile.WALL,        Tile.STONEWALL,   Tile.CAVEWALL,    Tile.CAVEWALL,  Tile.CAVEWALL,  Tile.CAVEWALL,  Tile.TREE,        Tile.CAVEWALL,   Tile.WALL,    Tile.STONEWALL,    Tile.CAVEWALL,   Tile.CAVEWALL};
  public static final int[] hardfloors = {Tile.FLOOR      , Tile.FLOOR,     Tile.FLOOR,      Tile.FLOOR,       Tile.FORESTFLOOR, Tile.STONEFLOOR,  Tile.CAVEFLOOR,   Tile.CAVEFLOOR, Tile.PLAINS,    Tile.CAVEFLOOR, Tile.FORESTFLOOR, Tile.CAVEFLOOR,  Tile.FLOOR,   Tile.STONEFLOOR,   Tile.GUNK,       Tile.GUNK};
  public static final int[] softwalls  = {Tile.TREE       , Tile.WALL,      Tile.CAVEWALL,   Tile.CAVEWALL,    Tile.CAVEWALL,    Tile.CAVEWALL,    Tile.CAVEWALL,    Tile.CAVEWALL,  Tile.CAVEWALL,  Tile.CAVEWALL,  Tile.CAVEWALL,    Tile.CAVEWALL,   Tile.WALL,    Tile.STONEWALL,    Tile.CAVEWALL,   Tile.CAVEWALL};
  public static final int[] softfloors = {Tile.FORESTFLOOR, Tile.FLOOR,     Tile.GRASS,      Tile.FORESTFLOOR, Tile.FORESTFLOOR, Tile.STONEFLOOR,  Tile.CAVEFLOOR,   Tile.CAVEFLOOR, Tile.PLAINS,    Tile.CAVEFLOOR, Tile.CAVEFLOOR,   Tile.CAVEFLOOR,  Tile.FLOOR,   Tile.STONEFLOOR,   Tile.CAVEFLOOR,  Tile.CAVEFLOOR};
  public static final int[] streams    = {Tile.STREAM     , Tile.STREAM,    Tile.STREAM,     Tile.STREAM,      Tile.STREAM,      Tile.STREAM,      Tile.STREAM,      Tile.STREAM,    Tile.STREAM,    Tile.STREAM,    Tile.STREAM,      Tile.STREAM,     Tile.STREAM,  Tile.STREAM,       Tile.GUNK,       Tile.GUNK};
  public static final int[] rivers     = {Tile.RIVER      , Tile.RIVER,     Tile.RIVER,      Tile.RIVER,       Tile.RIVER,       Tile.RIVER,       Tile.RIVER,       Tile.RIVER,     Tile.RIVER,     Tile.RIVER,     Tile.RIVER,       Tile.RIVER,      Tile.RIVER,   Tile.RIVER,        Tile.GUNK,       Tile.GUNK};
  
  // keep a Theme pointer for the Map's theme stack
  // this is so maps can use a theme temporarily like this:
  //
  // ** using the original theme **
  // map.newTheme(newtheme);
  // ** using the new theme **
  // map.oldTheme();
  // ** using the original theme again **
  //
  public Theme next;
  
  public int[] inhabitants;
  
  private int type;
    
  public int floor;
  public int wall;
  public int hardfloor;
  public int hardwall;
  public int softfloor;
  public int softwall;
  public int stream;
  public int river;
  
  // get the tile code for the particular theme wall type
  public int getTile(int t) {
    switch (t) {
      case HARDFLOOR: return hardfloors[type];
      case HARDWALL: return hardwalls[type];
      case SOFTFLOOR: return softfloors[type];
      case SOFTWALL: return softwalls[type];
      case STREAM: return streams[type];
      case RIVER: return rivers[type];
    } 
    return Tile.WALL;
  }
  
  public Theme(String s) {
    type=Text.index(s,names); 
    if (type<0) throw new Error("Theme not found: "+s);
    
    // set up default wall types
    hardwall=getTile(HARDWALL);
    hardfloor=getTile(HARDFLOOR);
    softwall=getTile(SOFTWALL);
    softfloor=getTile(SOFTFLOOR);
    stream=getTile(STREAM);
    river=getTile(RIVER);
    
    floor=hardfloor;
    wall=hardwall;
  }
  
  // Theme generation functions
  
  public Being createCreature(int level) {
    if (inhabitants!=null) {
      return new Creature(inhabitants[RPG.r(inhabitants.length)]); 
    }
    
    return Creature.createCreature(level); 
  }
  
  public void createDoor(Map map, int tx, int ty, int level) {
    map.setTile(tx,ty,map.floortile);
    map.addThing(Door.createDoor(level),tx,ty); 
  }
}