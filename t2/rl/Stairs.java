package rl;

public class Stairs extends Portal {
  
   private int type;
   
   public static final Description[] descriptions = {DESC_UP, DESC_DOWN, DESC_LADDERUP, DESC_LADDERDOWN };
   public static final int[] images =              {0,       1,         2,             3};
      
 
   public Stairs(int t) {
     super(0);
     type=t;
   }
   
   public boolean isInvisible() {return false;}
   
   public int getImage() {return images[type];}
   
   public Description getDescription() {return descriptions[type];}
}