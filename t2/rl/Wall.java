package rl;

import java.awt.*;

public class Wall extends GameTile {
  public Wall(int i, int tough) {
    super(i,true,false);  
  }
  
  public boolean isBlocking() {return true;}
  
  public boolean isTranparent() {return false;}

}