// Represents a stack of similar items that can be treated as a group
//
// Typically used for small or common items that you don't want to
// appear individually in the inventory (e.g. arrows, gold coins)
//
// Different stackable item classes should extend Stack
//
// Note that all stackable items must be the same, so they can't have 
// different properties or qualities. The canStackWith() method is
// responsible for determining if two groups of stackable items
// are the same and can therefore be combined into a single stack.

package rl;

public class Stack extends Item {
  public int number=1;
  protected int type=0;
  
  public Stack(int n) {
    number=n;
  }
  
  public int getNumber() {
    return number; 
  }
  
  public void setNumber(int num) {
	  number=num;
	  if (number<=0) remove();	
  }

  public int getIndividualWeight() {
    return 1; 
  }
  
  public int getWeight() {
    return number*getIndividualWeight();	
  }

  // descriptions etc.
  public String getName() {
    if (number==1) return super.getName();
    else return number + " "+ getDescription().getName(number,Description.ARTICLE_NONE);
  }
  
  public String getAName() { 
    if (number==1) return super.getAName();
    else return getName();
  }
  
  public String getTheName() { 
    if (number==1) return super.getTheName();
    else return "the "+getName();
  }

  // check if stacking possible
	public boolean canStackWith(Thing t) {
	  if ((t.getClass())!=(getClass())) return false;
    Stack l=(Stack)t;
    return (l.type==type)&&(l.quality==quality)&&(l.flags==flags);
	}
	
  // split n items from stack
  public Stack split(int n) {
    if (n>=number) {
      remove();
      return this;
    }
    if (n<1) return null;
    Stack s=(Stack)this.clone();
    s.number=n;    
    number=number-n; 
    return s; 
  }
  
  public Thing remove(int n) {
    if (n>=number) {
      remove();
      return this;
    } else {
      return split(n); 
    }
  }
  
	// transfers contents to other stack
	public boolean stackWith(Thing thing) {
	  if (!canStackWith(thing)) return false;
	  ((Stack)thing).number += number;
	  number=0;
	  remove();
    return true;
	}
  
  // INTERACTION HANDLING
  
  public int damage(int dam, int damtype) {
    int f=getStat(RPG.ST_ITEMFRAGILITY);
    if (RPG.d(1000)<=(dam*f)) {
      die();
    }
    return dam; 
  }
  
  // called when player tries specific action on object

  public int getZ() {return Z_ITEM;}

}