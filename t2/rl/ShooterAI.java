//
// AI for throwing and firing missile weapons
//
// Not used directly by any creatures, but can be called to provide
// targetting AI. e.g. NastyCritterAI will call ShooterAI when it decides
// to try a ranged attack
//
//

package rl;

public class ShooterAI extends BaseAI {
  public static final ShooterAI instance=new ShooterAI();
  
  public boolean doAction(Mobile m) {
    Hero h=Game.hero;
    Being b=(Being)m;
    Map map=b.getMap();

    m.aps-=10; // pause to think

    if (map==null) return false;

    if (b.isVisible()) {
      // find weapons
      RangedWeapon rw=(RangedWeapon)b.getWielded(RPG.WT_RANGEDWEAPON);
      Missile miss=(Missile)b.getWielded(RPG.WT_MISSILE);
    
      Thing p=map.findNearestFoe(m);
      if ((p!=null)&&m.canSee(p)) {
        if (rw==null) {
          if ((miss!=null)&&(miss.getStat(RPG.ST_MISSILETYPE)==0)) {
            ((Missile)miss.remove(1)).throwAt(b,map,p.x,p.y);
          } else {
            return false; 
          }
        } else if (rw.isValidAmmo(miss)) {
          rw.fireAt(b,(Missile)miss.remove(1),map,p.x,p.y);
        }  
        b.aps+=10; // make up for lost time
        return true;
      }
      return false;
    } else {
      return false; 
    }
  }
}