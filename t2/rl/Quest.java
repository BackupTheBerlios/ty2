// This is the root object for all Quests that can be assigned to the hero
//
// Use as follows:
//   Game.hero.addThing(new Quest(Quest.SOMEQUEST));

package rl;


public class Quest extends Thing implements Active {
  public static final int ASSIGNED=0;
  public static final int COMPLETE=1;
  public static final int FAILED=2;
  
  // the quest target (item, creature, person etc.)
  protected Thing target;
  
  
  public Quest() {
  }

  protected int state=ASSIGNED;
  
  public int getState() {
    return state; 
  }
  
  public Thing getTarget() {return target;}
  
  public void setTarget(Thing t) {target=t;}
  
  public void setState(int a) {
    state=a; 
    switch(state) {
      case COMPLETE: complete(); break;
      case FAILED: failed(); break; 
    }
  }

  public String questName() {
    return "null";
  }
  
  // return the written description of the quest
  // Can be two or three lines long as required to give full detail
  public String questString() {
    return "You must cut down the tallest tree in the forest with.... a herring!";
  }
  
  // called to initialise quest, make map modifications etc.
  public void setup() {}
  
  // called when quest is complete
  public void complete() {}
  
  // called if quest is failed
  public void failed() {}

  public void action (int t) {}
}