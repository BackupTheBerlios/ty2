package rl;

public class Junk extends ItemType {
  private int image;
  private int weight;
  private int fragility;
  private Description idesc;
  private Description udesc;
  
  public static final Description DESC_JUNK=
     new Describer("junk","pieces of junk","some old junk.");
  
  public static final Junk STONE=new Junk(102,50,3,DESC_JUNK);
  
  public Junk(int i, int w, int f, Description id) {
    super();
    image = i;	
    weight = w;
    idesc=id;
    udesc=id;
  }	
  
  public int getStat(Stack  s, int stat) {
    switch (stat) {
      case RPG.ST_ITEMFRAGILITY: return fragility;
      default: return 0;
    }
  }

  public int getImage(Stack s) {return image;}

  public int getWeight(Stack s) {return weight;}
  
  public Description getDescription(Stack s) {
    return s.isIdentified()?idesc:udesc;
  }
}