// 
//  Weapon subclass to represent unarmed combat attacks and special moves
//

package rl;

public class UnarmedWeapon extends Weapon {
  private int type;
  
  public static final int MAUL=0;
  public static final int BITE=1;
  public static final int BRAWL=2;
  public static final int KICK=3;
  public static final int DISARM=4;

  public static final int[] skillmuls       = {50,    50,    60,     50,      40};
  public static final int[] strengthmuls    = {50,    70,    30,     70,      0};
  public static final int[] defencemuls     = {50,    40,    30,     40,      50};
  public static final int[] skillbonuses    = {0,     -2,    -2,     -2,      0};
  public static final int[] strengthbonuses = {0,     0,     -2,     -2,      0};
  public static final int[] defencebonuses  = {0,     0,     0,      0,       0};
  public static final int[] attackcosts     = {300,   250,   350,    250,     400};
    
  
  public static final int[] powermodifiers  = {25,50,75,90,100,110,120,125,130,135,140,144,147,150,153,156,158,160,162,163,164,165,166,168,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200};

  public UnarmedWeapon(int t) {
    type=t; 
  }
  
  // use to hit target
  public int use(Thing wielder, Thing target, int action) {
    switch (action) {

      // Weapon has hit the given target
      case USE_HIT: {
        int dam=super.use(wielder,target,action);
        
        if (dam>0) {
          
          
        }
        
        return dam;
      }
    }
    return super.use(wielder,target,action);
  }
  
  public int getUse() {return USE_HIT;}
  
  // Get weapon attack skill
  public int getASK(Thing wielder, Thing target) {
    int ASK=super.getASK(wielder,target);
    return ASK;
  }
  
  // Get weapon attack strength
  public int getAST(Thing wielder, Thing target) {
    int AST=super.getAST(wielder,target);
    return AST;
  }
  
  // get weapon defence skill
  public int getDSK(Thing wielder, Thing attacker) {
    int DSK=super.getDSK(wielder,attacker);
    return DSK;
  }
  
  
  public int getStat(int s) {
    switch (s) {
      case RPG.ST_ATTACKCOST: return  attackcosts[type];
      case RPG.ST_ASKMULTIPLIER: 
        return skillmuls[type];
      case RPG.ST_ASTMULTIPLIER: 
        return strengthmuls[type];
      case RPG.ST_DSKMULTIPLIER: 
        return defencemuls[type];
      
      case RPG.ST_ASKBONUS: 
        return skillbonuses[type];
      case RPG.ST_ASTBONUS: 
        return strengthbonuses[type];
      case RPG.ST_DSKBONUS: 
        return defencebonuses[type];
      
    } 
    return super.getStat(s);
    
  }
  
  // we don't have a wield type		
  public int wieldType() {return RPG.WT_NONE;}
}