// stores information and codes for map tile objects

package rl;

import java.awt.*;

public class Tile  {
  
  // Tile flags
  public static final int TF_BLOCKED=65536;
  public static final int TF_TRANSPARENT=131072;
  public static final int TF_DISCOVERED=262144;
  public static final int TF_ANTIMAGIC=524288;
  public static final int TF_VISIBLE=2097152;
  public static final int TF_LIT=4194304;
  public static final int TF_DIRECTIONBASE=8388608;
  public static final int TF_DIRECTION=15*TF_DIRECTIONBASE;
  
  public static final int TF_TYPE=65535+TF_TRANSPARENT+TF_BLOCKED;


  // public static final Tile WALL=new GameTile(
  // Tile types
  public static final int FLOOR= 1|TF_TRANSPARENT ;
  public static final int WALL= 2|TF_BLOCKED ;
  public static final int STONEFLOOR= 3|TF_TRANSPARENT ;
  public static final int STONEWALL= 4|TF_BLOCKED ;
  public static final int METALFLOOR= 5|TF_TRANSPARENT ;
  public static final int METALWALL= 6|TF_BLOCKED ;
  public static final int CAVEFLOOR= 7|TF_TRANSPARENT ;
  public static final int CAVEWALL= 8|TF_BLOCKED ;
  public static final int ICEFLOOR= 9|TF_TRANSPARENT ;
  public static final int ICEWALL= 10|TF_BLOCKED ;
  public static final int POSHFLOOR= 11|TF_TRANSPARENT ;
  public static final int POSHWALL= 12|TF_BLOCKED ;
  public static final int FORESTFLOOR= 13|TF_TRANSPARENT ;
  public static final int TREE= 14|TF_BLOCKED ;

  //public static final int STREAM= 15|TF_TRANSPARENT ;
  public static final int RIVER= 15|TF_TRANSPARENT ;
  public static final int GRASS= 16|TF_TRANSPARENT ;
  
  // outdoor tiles
  public static final int PLAINS= 17|TF_TRANSPARENT ;
  public static final int FORESTS= 18|TF_TRANSPARENT ;
  public static final int HILLS= 19|TF_TRANSPARENT ;
  public static final int MOUNTAINS= 20|TF_BLOCKED ;
  public static final int PLAINSROCK= 21|TF_TRANSPARENT ;
  public static final int MOREPLAINS= 22|TF_TRANSPARENT ;
  public static final int SEA= 23|TF_TRANSPARENT|TF_BLOCKED ;
  
  public static final int STREAM= 24|TF_TRANSPARENT ;
  public static final int GUNK= 25|TF_TRANSPARENT ;
  public static final int POOL= 26|TF_TRANSPARENT ;

  public static final int SWAMP= 27|TF_TRANSPARENT ;
  
  // tiles images specification
  public static final String[]   names      = {"",          "floor",     "wall",    "stone floor", "stone wall",  "metal floor", "metal wall", "cave floor", "cave wall",  "ice floor",  "ice wall",   "fancy floor", "fancy wall",  "forest floor", "tree",      "river",     "grass",    "plains",   "forests",   "hills",     "mountains",   "plains",    "plains",   "sea",     "stream",    "gunk",    "pool",     "swamps" };
  public static final int[]      images     = {0,           0,           20,        2,             22,            4,             24,           6,            26,           8,            28,            10,            30,             12,          32,          66,          60,          60,          61,          62,          63,          64,           65,         66,         66,         202,        66,         203};
  public static final int[]      imagefill  = {0,           0,           21,        2,             23,            4,             25,           6,            27,           8 ,           29,            10,            31,             12,          32,          66,          60,          60,          61,          62,          63,          64,           65,         66,         66,         202,        66,         203};
  public static final boolean[]  filling    = {false,       false,       true,      false,         true,          false,         true,         false,        true,         false,        true,          false,         true,           false,       true,        false,       false,       false,       false,       false,       false,       false,        false,      false,      false,      false,      false,      false};
  public static final int[]      borders    = {0    ,       0    ,       0   ,      0    ,         0   ,          0    ,         0   ,         0    ,        0   ,         0    ,        0   ,          0    ,         0   ,           0    ,       0   ,        1    ,       0    ,       0    ,       0    ,       0    ,       0    ,       0    ,        0    ,      1    ,      1    ,      1,          1,          0};
  public static final int[]      movecost   = {100    ,     100,         100    ,   100   ,        100    ,       100   ,        100    ,      100   ,       100    ,      100   ,       100    ,      100   ,         100     ,       120   ,      120    ,     300   ,      130    ,     100    ,     200    ,     300    ,     500    ,     100    ,      100    ,    400    ,    250    ,    150    ,    200,        250};
  public static final int[]      zeros      = {0    ,       0    ,       0   ,      0    ,         0   ,          0    ,         0   ,         0    ,        0   ,         0    ,        0   ,          0    ,         0   ,           0    ,       0   ,        0    ,       0    ,       0    ,       0    ,       0    ,       0    ,       0    ,        0    ,      0    ,      0    ,      0,          0,          0};
  
  public static int getMoveCost(Thing mover, int t) {
    return movecost[t&65535]; 
  }
  
  public static int getImage(int t) {return images[t&65535];}

  public static int getFilledImage(int t) {return imagefill[t&65535];}
  
}