package rl;

public class ItemType extends Object implements java.io.Serializable {
  protected int image;
  protected boolean typeidentified=false;
  
  public int getStat(TypeStack s, int stat) {
    return 0;
  }

  public int getUse(TypeStack s) {return 0;}

  public void use(TypeStack s,Being user) {use(s,user,Thing.USE_NORMAL);}

  // called when player tries specific action on object
  public void use(TypeStack s,Being user, int usetype) {}

  public int wieldType(TypeStack s) {return RPG.WT_NONE;}

  public int getImage(TypeStack s) {return image;}

  public int getWeight(TypeStack s) {return 0;}

  public boolean isIdentified(TypeStack s) {
    return typeidentified||((s.flags&Item.ITEM_IDENTIFIED)!=0); 
  }

  public int damage(TypeStack s, int dam, int damtype) {
    return 0;
  }
  
  public boolean identify(TypeStack s, int idskill) {
    if (!isIdentified(s)) {
      if (idskill>=s.getStat(RPG.ST_ITEMRARITY)) {
        s.flags=s.flags|Item.ITEM_IDENTIFIED;
        typeidentified=true;
        return true;  
      } else {
        return false;        
      }   
    }  
    return true;
  }

  public void throwAt(TypeStack s,Being thrower, Map m, int tx, int ty) {
    s.moveTo(m,tx,ty); 
  }
  
  public Description getDescription(TypeStack s) {
    return Junk.DESC_JUNK;
  }
}