package rl;

public class CasterAI extends BaseAI {
  public static final CasterAI instance=new CasterAI();
  
  public boolean doAction(Mobile m) {
    Hero h=Game.hero;
    Being b=(Being)m;
    Art[] ar=b.arts.getArts();
    int c=ar.length;
    m.aps-=10; // pause to think
    
    if (c<=0) return false;
    
    if (b.isVisible()||(RPG.d(10)==1)) {
      int i=RPG.r(c);
      Art a=ar[i];
      if (a instanceof Spell) {
        return ((Spell)a).castAI(b);
      }
    }
    return false;
  }
}