package rl;

public class ThiefArt extends Art {
  public String getName() {return "Art";}
  
  public boolean canLearn(Being b) {
    return b.getStat(RPG.ST_THIEF)>0; 
  }
  
  public int listActions(StringList sl) {
    sl.add("PickPocket");
    return 1+super.listActions(sl);
  }
  
  public void doAction(String s) {
    
    return; 
  }
}