package rl;

public class PoisonAttribute extends ActiveAttribute {
	protected int damage;
	protected int probability;
	protected int duration; 
	protected int damtype;
	
	public PoisonAttribute(int d, int dt, int dur, int prob) {
		damage=d;
		damtype=dt;
		duration=dur;
		probability=prob;
	}
	
	public void action(int time) {
    if (duration<=0) {
      remove();  
      return;
    }
    
    duration=duration-time;

    for (int i=RPG.po(probability*time,10000); i>0; i--) {
      Mobile m=getMobile();
      int dam=m.damage(RPG.a(damage),damtype);
      if (m==Game.hero) {
        if (dam>0) Game.message("You feel poisoned");	
      }
    }
  }
	
	public String getAttributeString() {return "Poisoned";}
	
}