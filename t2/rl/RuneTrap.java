package rl;

public class RuneTrap extends Trap {
  protected int image;
  protected int spell;
  protected Thing actor;
  
  private static final Description DESC_RUNETRAP = 
		new Describer("runetrap","A magical runetrap carefully inscribed on the floor.");
	
  public RuneTrap() {
    // runetraps are clearly visible by default
    discovered=true;
    
    actor=null;
    image=160+RPG.r(3);
    spell=Spell.FIREBALL;
	}
	
  public RuneTrap(Thing owner, int thespell) {
    this();
    actor=owner;
    spell=thespell; 
  }
  
  // target adjacent hero directly if spell is not a ball spell
  public boolean actAdjacten() {
    return Spell.radii[spell]==0; 
  }
  
	public int getImage() {return image;}
	
	public void affectSquare(Map map, int tx, int ty) {
    remove();
    Thing temp=Game.actor;
    
    if (map!=null) {
      Spell sp=new Spell(spell);
      sp.castAtLocation(null,map,tx,ty);
      if (actor!=null) {
        ((Being)actor).mps-=sp.cost;
        if (actor==Game.hero) Game.message("Your power is drained by the runes"); 
      }
    }
    
    Game.actor=temp;
  }

  public Description getDescription() {return DESC_RUNETRAP;}
}