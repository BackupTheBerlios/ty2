package rl;

public class Projectile extends Stack {
  protected int type;
  
  public static final Description DESC_ARROW=
     new Describer("arrow","An arrow.");
  public static final Description DESC_DART=
     new Describer("dart","A small blowpipe dart");
     
  public Projectile(int n, int ptype) {
    super(n);
    type=ptype; 
  }   
}