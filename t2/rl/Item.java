// Item is the base class for all in-game objects
// Can be picked up and carried by Mobiles

package rl;

public class Item extends Thing {
	// 0=identified, 1=unidentified
  protected static final int ITEM_IDENTIFIED=1;
  protected static final int ITEM_OWNED=2;
  protected static final int ITEM_FORSALE=4;
  protected static final int ITEM_CURSED=8;
  protected static final int ITEM_BLESSED=16;
  protected static final int ITEM_ARTIFACT=32;
  protected static final int ITEM_DAMAGED=64;
  protected static final int ITEM_MAGIC=128;
  protected static final int ITEM_BROKEN=256;
  protected static final int ITEM_RUSTY=512;
  protected static final int ITEM_CHARMED=1024;
  protected static final int ITEM_ID_ATTEMPTED=2048;
  
  protected int flags=0;
  protected int image=0;
  protected int quality=0;
  
  // queries whether an item is identified
  // if so, the player will see the item's full (true) description
  public boolean isIdentified() {
    if (QuestApp.debug&&Game.hero.profession.equals("avatar")) return true;
    
    if ((flags&ITEM_ID_ATTEMPTED)==0) tryIdentify(Game.hero);
    
    return ((flags&ITEM_IDENTIFIED)!=0);
  }
  
  public boolean tryIdentify(Being viewer) {
    flags|=ITEM_ID_ATTEMPTED;
    if (RPG.test(viewer.getStat(RPG.ST_IDENTIFY)*viewer.getStat(RPG.ST_CR),40+5*getLevel()+5*getStat(RPG.ST_ITEMRARITY))) {
      flags|=ITEM_IDENTIFIED;
      return true;
    } 
    return false;
  }
  
  public void setIdentified(boolean ident) {
    if (ident) {
      flags |= ITEM_IDENTIFIED;
    } else {
      flags &= ~ITEM_IDENTIFIED; 
    } 
  }
  
  public void setBlessed(boolean bless) {
    if (bless) {
      flags |= ITEM_BLESSED;
    } else {
      flags &= ~ITEM_BLESSED; 
    } 
  }
  
  public void setDamaged(boolean damage) {
    if (damage) {
      flags |= ITEM_DAMAGED;
    } else {
      flags &= ~ITEM_DAMAGED; 
    } 
  }
  
  
  public boolean isOwned() {
    return (flags&ITEM_OWNED)!=0;
  }
  
  public boolean isArtifact() {
    return (flags&ITEM_ARTIFACT)!=0; 
  }
  
  public boolean isDamaged() {
    return (flags&ITEM_DAMAGED)!=0; 
  }
  
  public boolean isRusty() {
    return (flags&ITEM_RUSTY)!=0; 
  }
  
  // what is the item made of?
  // no default material
  public int getMaterial() {
    return 0; 
  }
  
  // returns the toughness of the item
  // this the the average amount of damage needed
  // to cause the item to deteriorate
  // 
  // for indestructible items, return zero
  public int getToughness() {
    return 0; 
  }
  
  public boolean identify(int idskill) {
	  if (!isIdentified()) {
	  	if (idskill>=getStat(RPG.ST_ITEMRARITY)) {
	  	  flags=flags|ITEM_IDENTIFIED;
	  	  return true;	
	  	} else {
        return false;	  		
	  	} 	
	  }	
	  return true;
	}
	
  public void deteriorate(int levels) {
    // can't deteriorate artifacts
    if (isArtifact()) return;
    
    // repeat for each stage of damage
    for (int i=levels; i>0; i--) {
      // destroy item if already damaged
      if ((flags&ITEM_DAMAGED)>0) {
        die();
        return; 
      }
      
      // if charmed, remove charm else set as damaged
      if ((flags&ITEM_CHARMED)>0) {
        flags&=~ITEM_CHARMED;
      } else {
        if ((i==1)&&(place==Game.hero)) Game.message("Your "+getName()+" is damaged");
        flags|=ITEM_DAMAGED; 
      }
    } 
  }
  
  public int damage(int dam, int damtype) {
    dam=Lib.physicalDamage(dam,damtype);    
    
    // can't destroy artifacts etc...
    if (getQuality()>=13) return 0;
    
    int t=getToughness();
    int d=RPG.po(dam,t);
    deteriorate(d);
    return (d>0)?dam:0; 
  }
  
  public void die() {
    if (place==Game.hero) {
      Game.message("Your "+getFullName()+" is destroyed!");
    } else {
      visibleMessage(getTheName()+" is destroyed"); 
    }
    super.die();
  }
  
  
  public String getAdjectives() {
    Hero h=Game.hero;
    boolean isidentified=isIdentified();
    int q=getQuality();
    
    String n=super.getAdjectives();
    
    if ((q>0)) {
      if (isidentified||(h.getStat(RPG.ST_APPRAISAL)>=3)) {
        n=Lib.qualitystrings[q]+" "+n; 
      } else if (h.getStat(RPG.ST_APPRAISAL)>=1) {
        switch (q) {
          case 1: case 2: case 3: case 4: n="dodgy "+n; break;
          case 5: case 6: case 7: n="reasonable "+n; break;
          case 8: case 9: case 10: case 11: case 12: case 13: n="nice "+n; break;
        }
      }
    } 
    
    if ( ((flags&ITEM_CURSED)>0) && (isidentified||(h.getStat(RPG.ST_PRIEST)>0)) ) {
      n="cursed "+n; 
    }

    if ( ((flags&ITEM_BLESSED)>0) && (isidentified||(h.getStat(RPG.ST_PRIEST)>0)) ) {
      n="blessed "+n; 
    }

    if ( (flags&ITEM_DAMAGED)>0) {
      n="damaged "+n; 
    }

    if ( ((flags&ITEM_MAGIC)>0) && (isidentified||(h.getStat(RPG.ST_MAGE)>0)) ) {
      n="magic "+n; 
    }

    if ( ((flags&ITEM_CHARMED)>0) && (isidentified||(h.getStat(RPG.ST_MAGE)>0)) ) {
      n="charmed "+n; 
    }

    return n;
  }
  
  public int getQuality() {return quality;}
  
  // modify effective quality
  public int getEffectiveQuality() {
    int q=getQuality();
    if ((flags&ITEM_CURSED)>0) q=q-2;
    if ((flags&ITEM_BLESSED)>0) q=q+1;
    if ((flags&ITEM_MAGIC)>0) q=q+1;
    if ((flags&ITEM_CHARMED)>0) q=q+1;

    if ((flags&ITEM_DAMAGED)>0) q=q-1;
    if ((flags&ITEM_RUSTY)>0) q=q-2;
    if ((flags&ITEM_BROKEN)>0) q=0;
    return RPG.middle(0,q,13);    
  }
  
  public int getWeight() {return 0;}
  
  public int getZ() {return Z_ITEM;}

  public int getImage() {return image;}
  
  public int wieldType() {return RPG.WT_NONE;}

  public int getModifier(int stat) {return 0;}  
}