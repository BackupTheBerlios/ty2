package rl;

public class InfiniteDungeon extends Dungeon {
  public Portal wayout;
  
  public InfiniteDungeon(int lev, boolean goingdown, Portal out) {
    super(70,70,lev); 
    wayout=out;

    Portal wayup=new InfinitePortal(1,level-1);
    Portal waydown=new InfinitePortal(2,level+1);

    // interchange portals if necessary
    // not needed since entrance=up, exit=down always
    //if (!goingdown) {
    //  Portal t=waydown;
    //  waydown=wayup;
    //  wayup=t; 
    //}
    
    // where we come in
    addThing(wayup,entrance.x,entrance.y);
    entrance.remove();
    entrance=wayup;
    
    // where we go out
    Point down=findFreeSquare();
    addThing(waydown, down.x, down.y);
    exit=waydown;

  }

  public void action(int time) {
    super.action(time);
    int count=RPG.po(time,10000);
    for (int i=0; i<count; i++) addWandering();  
  }

  public String getEnterMessage() {
    return "This place is a maze of endless tunnels.";  
  }
  
  public String getLevelName() {
    return "Infinite dungeon level "+level;  
  }
}