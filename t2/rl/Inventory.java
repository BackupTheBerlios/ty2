package rl;

public class Inventory extends Object implements Active, Cloneable, java.io.Serializable {
  private Thing[] inv;
  private int invsize;
  private int invcount;
  private ThingOwner place;

  public Inventory(ThingOwner owner) {
    place=owner;	
  }

  // deep clone of inventory
  public Inventory buildcopy(ThingOwner owner) {
    Inventory i;
    try {
      i=(Inventory)super.clone();
    } catch (CloneNotSupportedException e) {throw new Error("Inventory clone failed");} 
    if (inv!=null) {
      i.inv=(Thing[])i.inv.clone();
      for (int t=0; t<i.invcount ; t++) {
        i.inv[t]=(Thing)i.inv[t].clone();
        i.inv[t].place=owner; 
      }
    }
    i.place=owner;
    return i;
  }

  public void setUsage(Thing t,int wt) {
    if (t.place!=place) return;
    t.y=wt;	
  }
  
  // get total weight of inventory items
  public int getWeight() {
    int result=0;
    for (int i=0; i<invcount; i++) {
      result=result+inv[i].getWeight();	
    }	
    return result;
  }
  
  // get all contents
  public Thing[] getContents() {
    if (invcount==0) return new Thing[0];
    Thing[] temp = new Thing[invcount];
    int tempcount=0;
    for (int i=0; i<invcount; i++) {
      Thing t=inv[i];
      if (t instanceof Item) {
        temp[tempcount]=t;
        tempcount++;	
      }
    }
    Thing[] result = new Thing[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;    	
  }

  public Thing getContents(String s) {
    Thing[] stuff=getContents();
    for (int i=0; i<stuff.length; i++) {
      if ((stuff[i].getName()==s)) return stuff[i];
    }
    return null;
  }
  
  // get all contents of specified class
  public Thing[] getContents(Class c) {
    if (invcount==0) return null;
    Thing[] temp = new Thing[invcount];
    int tempcount=0;
    for (int i=0; i<invcount; i++) {
      Thing t=inv[i];
      if (c.isInstance(t)) {
        temp[tempcount]=t;
        tempcount++;  
      }
    }
    Thing[] result = new Thing[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;      
  }

  public Thing getWeapon(int wt) {
    for (int i=0; i<invcount; i++) {
      if (inv[i].y==wt) return inv[i];	
    }
    return null;
  }
  
  public Thing[] getUsableContents(int use) {
    if (invcount==0) return null;
    Thing[] temp = new Thing[invcount];
    int tempcount=0;
    for (int i=0; i<invcount; i++) {
      Thing t=inv[i];
      if (t instanceof Item) {
        if ((t.getUse()&use)>0) {
          temp[tempcount]=t;
          tempcount++;	
        }	
      }
    }
    Thing[] result = new Thing[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;    	
  }
  
  // get all contents with a given stat value
  public Thing[] getStatContents(int stat, int val) {
    if (invcount==0) return null;
    Thing[] temp = new Thing[invcount];
    int tempcount=0;
    for (int i=0; i<invcount; i++) {
      Thing t=inv[i];
      if (t.getStat(stat)==val) {
        temp[tempcount]=t;
        tempcount++;  
      }
    }
    Thing[] result = new Thing[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;      
  }

  // get all contents that can be worn/wielded with 'w'
  public Item[] getWieldableContents() {
    if (invcount==0) return null;
    Item[] temp = new Item[invcount];
    int tempcount=0;
    for (int i=0; i<invcount; i++) {
      Thing t=inv[i];
      if (t instanceof Item) {
        if (((Item)t).wieldType()>RPG.WT_NONE) {
          temp[tempcount]=(Item)t;
          tempcount++;	
        }	
      }
    }
    Item[] result = new Item[tempcount];
    if (tempcount>0) System.arraycopy(temp,0,result,0,tempcount);
    return result;    	
  }
  
  // get the net modifier for all used inventory items
  public int getModifier(int stat) {
    int result=0;
    for (int i=0; i<invcount; i++) {
    	Thing t=inv[i];
    	if (t.y!=RPG.WT_NONE) {
    	  result=result+t.getModifier(stat);
    	}    	 
    }	
    return result;
  }
  
  // time effect - pass to active sub-items
  public void action(int time) {
    for (int i=0; i<invcount;i++)	{
    	Thing t=inv[i];
    	if (t instanceof Active) ((Active)t).action(time);
    }
  }

  public Thing getThing(int i) {
    return ((i>=0)&(i<invcount))?inv[i]:null;
  }

  public int getUsage(int i) {
    return ((i>=0)&(i<invcount))?inv[i].y:RPG.WT_ERROR;
  }
  
  public void ensureSize (int s) {
    if (s<=invsize) return;
    invsize=s+5;
    Thing[] newinv=new Thing[invsize];
    if (inv!=null) {
      System.arraycopy(inv,0,newinv,0,invcount);
    }	
    inv=newinv;
  } 
	
  public void removeThing(Thing thing) {
    int pos=thing.x;
      if (inv==null) throw new Error("Empty Inventory bug!");
      if (thing.place!=place) throw new Error("Thing in wrong place!");
      if (inv[pos]!=thing) throw new Error("Thing in wrong position!");
    if (pos<(invcount-1)) System.arraycopy(inv,pos+1,inv,pos,invcount-pos-1);
    inv[invcount-1]=null;
    thing.place=null;
    thing.next=null;
    thing.y=RPG.WT_NONE;   // definitely not using anymore
    invcount=invcount-1;
    for (int i=0; i<invcount; i++) inv[i].x=i; // new positions
  }

  public void swapItems(int a, int b) {
    if ((a>=invcount)||(b>=invcount)||(a<0)||(b<0))	return;
    Thing ti=inv[a];
    inv[a]=inv[b];
    inv[b]=ti;
  	inv[a].x=a;
  	inv[b].x=b;
  }

	public void addThing(Thing thing) {
	  if (thing==null) return;
	  thing.remove();
    
    // see if we can stack with existing items
    if (thing instanceof Stack) {
      Stack sthing=(Stack)thing;
      for (int i=0; i<invcount; i++) {
        if (sthing.stackWith(inv[i])) return;	
      }	
    }
    
    ensureSize(invcount+1);
    inv[invcount]=thing;
    thing.place=place;
    thing.next=null;   // don't care about list 
    thing.x=invcount;  // might be useful to know slot!
    thing.y=RPG.WT_NONE; // item not in use
    invcount=invcount+1;
  }

  public boolean clearUsage(int wt) {
  	for (int i=0; i<invcount; i++) {
  		Thing t=inv[i];
      int it = t.y;
  	  if ((it==wt)&&(t instanceof Item)) {
  		  if ((place!=Game.hero)||((((Item)t).flags&Item.ITEM_CURSED)==0)) {
          inv[i].y=RPG.WT_NONE;
        } else {
          Game.message("You are unable to remove your "+t.getName()+"!"); 
          return false;
        }
      }
  	}
    return true;
  }
}