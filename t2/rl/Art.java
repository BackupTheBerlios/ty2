package rl;

public class Art extends Object implements Cloneable, java.io.Serializable {

  public Object clone() {
    try {
      return super.clone();  
    } catch (Exception e) {return null;}
  }
  
  public boolean compare(Art a) {
    return a.getName().equals(getName()); 
  }
  
  public int getLevel() {
    return 0; 
  }
  
  public void setLevel(int l) {
    
  }
  
  public String getName() {return "Art";}

  // return true if being is able to learn this art
  public boolean canLearn(Being b) {
    return false; 
  }
  
  // lists available actions
  //    use sl.add() to add action description
  //    return total number of action
  public int listActions(StringList sl) {
    return 0;
  }
  
  public int getModifier(int s) {
    return 0;
  }
  
  public void doAction(String s) {
    return; 
  }
}