package rl;

public class Weapon extends Item {
  // use to hit target
  public int use(Thing wielder, Thing target, int action) {
    switch (action) {
      // Weapon used to attack
      // return >0 if hit is successful
      case USE_ATTACK: {
        int attack=getASK(wielder,target);
        int defence=target.getStat(RPG.ST_DSK);

        int result=RPG.test(attack,defence)?1:0;
        
        if ((result==0)&&(target.isVisible())) {
          boolean ishero=(wielder==Game.hero);
          Game.message(Text.capitalise(wielder.getTheName()+
          	       (ishero?" miss ":" misses ")+
          	       target.getTheName()+
          	       ".  ")+
                       Text.capitalise((target==Game.hero?(target.getTheName()+" are"):"it is")+
                       " now at "+
                       RPG.percentile(target.getStat(RPG.ST_HPS), target.getStat(RPG.ST_HPSMAX))+
                       "% health."));
        }  

        return result;
      }
      
      // Weapon has hit the given target
      case USE_HIT: {
        Map map=target.getMap();

        int ast=getAST(wielder,target);

        ast=RPG.r(ast+1);
        int dam=target.damageLocation(ast,RPG.DT_NORMAL);
        
        // report the attack
        if (target.isVisible()) {
          boolean heroattack = (wielder==Game.hero);
          //Game.message(Text.capitalise(wielder.getTheName()+ (heroattack?" hit ":" hits ")+target.getTheName()+" for "+Lib.damageDescription(dam,target.getStat(RPG.ST_HPSMAX))));
          Game.message(Text.capitalise(wielder.getTheName()+
                       (heroattack?" hit ":" hits ")+
                       target.getTheName()+
                       " for "+
                       Lib.damageDescription(dam,target.getStat(RPG.ST_HPSMAX))+
          	       ".  ")+
                       Text.capitalise((target==Game.hero?(target.getTheName()+" are"):"it is")+
                       " now at "+
                       RPG.percentile(target.getStat(RPG.ST_HPS), target.getStat(RPG.ST_HPSMAX))+
                       "% health."));
        }

        // slight wear and tear to weapon 
        // lower hit damage = more armour = higher damage to weapon
        //   note: (ast-dam) is the number of hps deflected by armour
        damage((ast-dam)/3,RPG.DT_NORMAL);
        
        return dam;
      }
    }
    return 0;
  }
  
  public int getUse() {return USE_HIT;}
  
  // Get weapon attack skill
  public int getASK(Thing wielder, Thing target) {
    int ASK=wielder.getStat(RPG.ST_SK);
    ASK=(ASK*getStat(RPG.ST_ASKMULTIPLIER))/100;
    ASK=ASK+getStat(RPG.ST_ASKBONUS);
    return ASK;
  }
  
  // Get weapon attack strength
  public int getAST(Thing wielder, Thing target) {
    int AST=wielder.getStat(RPG.ST_ST);
    AST=(AST*getStat(RPG.ST_ASTMULTIPLIER))/100;
    AST=AST+getStat(RPG.ST_ASTBONUS);
    return AST;
  }
  
  // get weapon defence skill
  public int getDSK(Thing wielder, Thing attacker) {
    int DSK=wielder.getStat(RPG.ST_SK);
    DSK=(DSK*getStat(RPG.ST_DSKMULTIPLIER))/100;
    DSK=DSK+getStat(RPG.ST_DSKBONUS);
    return DSK;
  }
  
  public static Weapon createWeapon(int level) {
    return CommonWeapon.createWeapon(level);
    //switch (RPG.d(20)) {
    // 	case 1: return StandardWeapon.createWeapon(StandardWeapon.specialsource,n);
    //	default: return StandardWeapon.createWeapon(StandardWeapon.source,n);
    //}
  }
  		
  public int wieldType() {return RPG.WT_MAINHAND;}
}