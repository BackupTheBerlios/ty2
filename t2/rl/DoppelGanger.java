//
// A deeply cunning monster that imitates it's target
//

package rl;

public class DoppelGanger extends Being {
  public Being target=Game.hero;
  
  public DoppelGanger() {
    super(); 
  }
  
  
  public int getImage() {return target.getImage();}
  
  public int getStat(int s) {
    switch (s) {
      case RPG.ST_SK:
      case RPG.ST_ST:
      case RPG.ST_AG:
      case RPG.ST_TG:
      case RPG.ST_IN:
      case RPG.ST_WP:
      case RPG.ST_CH:
      case RPG.ST_CR:
        return target.getStat(s);
    }
    
    return super.getStat(s); 
  } 
}