package rl;

import java.awt.*;

public class Being extends Mobile implements ThingOwner, Talkable  {
	public int image;
  public int seed;
  public int hps;
  public int mps;
  
  public Inventory inv=new Inventory(this);
  public Stats stats;
  public ArtList arts=new ArtList();
  public Talkable personality=null;
  public Name name=null;
  
  
  public Being() {
    super();
    seed=RPG.r(1000000000);
	  
    // random aps to stagger movement points
    aps=RPG.r(100);
  }
  
  // for Talkable interface
  // t == this 
  public void talk(Thing t) {
    Talkable p=getPersonality();
    if (p!=null) {
      p.talk(t); 
    } else {
      Game.message("You get no response"); 
    } 
  }
  
  // gain multiple levels to a given target
  public void gainLevel(int targetlevel) {
    while (getStat(RPG.ST_LEVEL)<targetlevel) gainLevel(); 
  }
  
  // gain a level
  // enhance stats / skills as needed
  public void gainLevel() {
    int oldmpsmax=getStat(RPG.ST_MPSMAX);
    int oldhpsmax=getStat(RPG.ST_HPSMAX);

    int level=getStat(RPG.ST_LEVEL)+1;
    stats.setStat(RPG.ST_LEVEL,level); 
    stats.setStat(RPG.ST_SKILLPOINTS,stats.getStat(RPG.ST_SKILLPOINTS)+60);
    
    
    for (int i=1; i<=8; i++) {
      int stat=getBaseStat(i);
      stat+=RPG.po(stat,10);
      stats.setStat(i,stat);   
    }
    
    hps=(hps*getStat(RPG.ST_HPSMAX))/oldhpsmax;
    mps=(hps*getStat(RPG.ST_MPSMAX))/oldmpsmax;
    
    if (this==Game.hero) {
      Game.message("You have achieved level "+level);
    } else {
      // add skills automatically for creatures/NPCs
      autoLearnSkills(); 
    }
  }
  
  public Talkable getPersonality() {
    return personality;
  } 
  
  public void setPersonality(Talkable t) {
    personality=t;
  }
  
  public int getLevel() {
    return getStat(RPG.ST_LEVEL); 
  }
  
  public Object clone() {
    Being b=(Being)super.clone();
    
    // build inventory clone with b as owner
    b.inv=b.inv.buildcopy(b);
    
    // deep clone stats and arts
    b.stats=(Stats)b.stats.clone(); 
    b.arts=(ArtList)b.arts.clone();
    
    return b; 
  }  
    
	public void action(int t) {
    // check if dead
    if (hps<0) die();
    
    Hero h=Game.hero;
    boolean ishero=(this==h);
    
    int hunger=getStat(RPG.ST_HUNGER);
    if (hunger<Hero.HUNGERLEVEL) {
      // recharge magic points
      int recharge=getStat(RPG.ST_RECHARGE);
      if (ishero&&(!h.recharging)) recharge=0;
      
      if (recharge>0) {
        int mpsmax=getStat(RPG.ST_MPSMAX);
        mps=RPG.middle(mps,mps+RPG.po(((float)t*recharge*mpsmax)/100000),mpsmax);
      }
    
      // regenerate hit points
      int regenerate=getStat(RPG.ST_REGENERATE);
      if (ishero&&(!h.recharging)) regenerate=0;

      if (regenerate>0) {
        int hpsmax=getStat(RPG.ST_HPSMAX);
        hps=RPG.middle(hps,hps+RPG.po(((float)t*regenerate*hpsmax)/100000),hpsmax);
      }
    }

    // map effects
    Map map=getMap();
    if (map!=null) {
      if (map.isAngry()&&(getStat(RPG.ST_STATE)==AI.STATE_INHABITANT)) {
        setStat(RPG.ST_STATE,AI.STATE_HOSTILE); 
      } 
    }
    
    super.action(t);
    
    // action for inventory
    inv.action(t);
	}

  public void setStat(int s, int v) {
    stats.setStat(s,v);	
  }

	// return raw attribute
  public int getBaseStat(int s) {return stats.getStat(s);}

  // return attribute fully modified
  public int getStat(int s) {
    switch (s) {
    	case RPG.ST_HPS:
    		return hps;
    	case RPG.ST_MPS:
    		return mps;
    	case RPG.ST_APS:
    		return aps;
    	
    	case RPG.ST_HPSMAX:
    		return (getStat(RPG.ST_TG)*(5+getStat(RPG.ST_LEVEL)))/5+getModifiers(s);
    	case RPG.ST_MPSMAX:
    		return (getStat(RPG.ST_WP)*(5+getStat(RPG.ST_LEVEL)))/5+getModifiers(s);
    	
    	
    	case RPG.ST_AST:
    		return getStat(RPG.ST_ST)+getModifiers(s);
    	case RPG.ST_ASK:
    		return getStat(RPG.ST_SK)+getModifiers(s);
      case RPG.ST_DSK: {
        // DSK = weapon dsk + dodge ability
        int def=(getStat(RPG.ST_SK)*getModifiers(RPG.ST_DSKMULTIPLIER))/100+getModifiers(RPG.ST_DSKBONUS);
        def+=(getStat(RPG.ST_AG)*(100+getModifiers(RPG.ST_DODGEMULTIPLIER)))/100;
        return def+getModifiers(s);
      } 
       
    	// combat abilities
    	//ase RPG.ST_ATTACK:
      //return getBaseStat(RPG.ST_ATTACK)+getStat(RPG.ST_SK)+inv.getModifier(s);
    	//case RPG.ST_DEFEND:
    	//	return getBaseStat(RPG.ST_DEFEND)+getStat(RPG.ST_SK)+inv.getModifier(s);
    	//case RPG.ST_DODGE:
    	//	return getBaseStat(RPG.ST_DODGE)+getStat(RPG.ST_AG)+inv.getModifier(s);
    		  
    	// with skill, want to allow for encumberance of items
    	case RPG.ST_SK: {
    	  int sk=getBaseStat(RPG.ST_SK)+getModifiers(s);
    	  int enc=getStat(RPG.ST_ENCUMBERANCE);
    	 	return (sk*RPG.middle(100,100-enc,0))/100;
    	}

    	// with agility, want to allow for encumberance of items
    	case RPG.ST_AG: {
    	  int ag=getBaseStat(RPG.ST_AG)+getModifiers(s);
    	  int enc=getStat(RPG.ST_ENCUMBERANCE);
    	 	return (ag*RPG.middle(100,100-2*enc,0))/100;
    	}
    	
    	// armour modified by protection value
    	case RPG.ST_ARMUNARMED:
    	  return getBaseStat(s)+getModifiers(s)+getStat(RPG.ST_PROTECTION)*2;

    	case RPG.ST_ARMNORMAL: case RPG.ST_ARMIMPACT:
    	  return getBaseStat(s)+getModifiers(s)+getStat(RPG.ST_PROTECTION);
    		
    	case RPG.ST_ARMPIERCING: case RPG.ST_ARMFIRE: case RPG.ST_ARMICE: case RPG.ST_ARMACID:
    	  return getBaseStat(s)+getModifiers(s)+getStat(RPG.ST_PROTECTION)/2;
    	 	
    	// don't faff around with AI stats
    	case RPG.ST_STATE: case RPG.ST_TARGETX: case RPG.ST_TARGETY:
        return stats.getStat(s); 
        	
      case RPG.ST_MAXWEIGHT: {
    	  int st=getStat(RPG.ST_ST);
    	  return st*5000;
      }
      
      case RPG.ST_MOVESPEED: {
        int base=getBaseStat(s);
        return RPG.max(0,base+getModifiers(s)-getStat(RPG.ST_ENCUMBERANCE)/2); 
      }
      
      case RPG.ST_MOVECOST: {
        int base=getBaseStat(s);
        return 100+base+getModifiers(s); 
      }
      
      // return encumberance as a percentage
      case RPG.ST_ENCUMBERANCE: {
        int weight=(inv.getWeight()*100)/getStat(RPG.ST_MAXWEIGHT);
        return weight+getBaseStat(s)+getModifiers(s);
      }
      
      case RPG.ST_ANTIMAGIC: {
        return (getStat(RPG.ST_WP)*(1+getStat(RPG.ST_MAGICRESISTANCE)))/2+getBaseStat(s)+getModifiers(s); 
        
      }
      
      default:
      	// return stored stat + item/effect modifiers
        return getBaseStat(s)+getModifiers(s);
    }
  }
	
	public int getModifiers(int s) {
    return inv.getModifier(s)+arts.getModifier(s);    
  }
  
  public boolean isDead() {return hps<0;}
	
  
  // damage all items in inventory
  // todo: modification for item protection (ST_PROTECT*)
  public int damageAll(int dam, int damtype) {
    if (dam<=0) return 0;
    Thing[] stuff=inv.getContents();
    int arm=getArmour(damtype);
    int kitdamage=(dam*dam)/(arm+dam);
    for (int i=0; i<stuff.length; i++) {
      stuff[i].damageAll(kitdamage,damtype); 
    }
    return damage(dam,damtype);  
  }
  
  public void addSkill(int s,int l) {
    if (l==0) return;
    Art a=arts.getSkill(s); 
    if (a==null) {
      arts.addArt(new Skill(s,l)); 
    } else {
      a.setLevel(a.getLevel()+l);
    }
  }
  
  public void addSkill(String s, int l) {
    addSkill(Skill.number(s),l); 
  }
  
  public int damageLocation(int dam, int damtype) {
    armourDamage(dam,damtype);
    return damage(dam,damtype);    
  }
  
  // suffer damage
	public int damage(int dam, int damtype) {
    // react to a surprise attack
    if ((Game.actor!=null)&&(!isHostile((Mobile)Game.actor))) {
      getAI().notify(this,AI.EVENT_ATTACKED,getStat(RPG.ST_SIDE),Game.actor); 
    }
    
	  if (dam<=0) return 0;
    int arm=getArmour(damtype);
    
	  int res=getResistance(damtype);
	  if (res==0) {
      // commonest-case optimised
	    dam=(dam*dam)/(arm+dam); 
    } else if (res>-4) {
      dam=(dam*dam*4)/((arm+dam)*(4+res));	
	  } else {
      dam=dam*10; 
    }
	  
	  if (damtype==RPG.DT_DRAIN) {
	    mps=mps-dam;
	    if (mps<0) {hps=hps+mps; mps=0;}
	  } else {
	  	hps=hps-dam;
	  }	
	  
    if (hps<0) {
      int fate=getStat(RPG.ST_FATE);
      if (fate>0) {
        setStat(RPG.ST_FATE,fate-1);
        dam=dam+hps; // cheat damage
        hps=0;       // survive         
        if (this==Game.hero) {
          Game.message(fate==1?"You feel your luck is running out...":"That was too close for comfort..."); 
        }
      } else {
        die();
      }
    }
    
    return dam;	
	}
	
	// return armour value vs particular damage
	public int getArmour(int damtype) {
		return getStat(RPG.ST_ARM_BASE+damtype);
	}
	
	public int getResistance(int damtype) {
		return getStat(RPG.ST_RESIST_BASE+damtype);
	}

  // checks if being has item in possession
  public boolean hasItem(String s) {
    Thing[] stuff=inv.getContents();
    for (int i=0; i<stuff.length; i++) {
      if ((stuff[i].getName()==s)) return true;
    }
    return false;
  }
  
  // creature dies horribly
  public void die() {
  	if (place instanceof Map) {
  	  Map m=getMap();
  	  Thing[] stuff = inv.getContents();
  	  if (stuff!=null) for (int i=0; i<stuff.length; i++) {
  	    m.addThing(stuff[i],x,y);	
  	  }	
    }
    aps = -1000;// stop it from doing anything else
    super.die();	// delete from map
  }

  // damage a single random piece of armour
  // (NYI) or inventory item if not available
  public void armourDamage(int d,int dt) {
    int l=Lib.hitLocation();
    Thing t=getWielded(l); 
    if (t!=null) t.damage(d,dt);
  }


  public int getImage() {return image;}
  
  public String getName() { return "generic being";}
  
  // Mobile properties, override Thing
  public boolean isBlocking() {return true;}
  
  // Calculate funds available
  public int getMoney() {
    Thing[] cash = inv.getContents(Coin.class);
    int tot=0;
    for (int i=0; i<cash.length; i++) tot+=cash[i].getStat(RPG.ST_ITEMVALUE)*cash[i].getNumber();
    return tot;
  }
  
  public void addMoney(int v) {
    if (v<0) removeMoney(-v);
    if ((v/100)>0) addThing(new Coin(2,v/100));
    if (((v/10)%10)>0) addThing(new Coin(1,(v/10)%10));
    if ((v%10)>0) addThing(new Coin(0,v%10));
  }
  
  public void removeMoney(int v) {
    if (v<=0) {addMoney(-v); return;}
    int funds=getMoney()-v;
    Thing[] cash = inv.getContents(Coin.class);
    for (int i=0; i<cash.length; i++) cash[i].remove();
    if (funds>0) {
      addMoney(funds); 
    }
  }
  
  // remove thing from inventory
  public void removeThing(Thing thing) {
    inv.removeThing(thing);
  }
  
	// add thing to inventory
	public void addThing(Thing thing) {
	  inv.addThing(thing);
	}

  // add attribute and activate
  public void addAttribute(Attribute a) {
    inv.addThing(a);
    a.y=RPG.WT_EFFECT;
  }

	public void dropThing(Thing thing) {
	  thing.remove();
	  if (place instanceof Map) {
	    ((Map)place).addThing(thing,x,y);	
	  }
	}
	
  // pick up item
  public void pickUp(Thing t) {
    addThing(t);
  }
  
	public void throwThing(Thing t, int tx, int ty) {
    Map m=getMap();
    if (m==null) return;
    
    // find where thing hits
    Point p=m.tracePath(x,y,tx,ty);
    
    // check for current location
    if ((p.x==x)&&(p.y==y)) {
      dropThing(t); 
      return;
    } else {
      t.throwAt(this,m,p.x,p.y);
    }
  }
  
  
  // wields the item in appropriate slot
	// unwields other items to allow this
	
	public boolean wield(Item t, int wt) {
	  if (!inv.clearUsage(wt)) return false;
		
	  // remove other items if cannot be wielded together
    // remeber to avoid quick boolean evaluation!
	  if (wt==RPG.WT_TWOHANDS) {
  	  if (!inv.clearUsage(RPG.WT_MAINHAND)|!inv.clearUsage(RPG.WT_SECONDHAND)) return false;
	  }
	  if (wt==RPG.WT_MAINHAND) {
  	  if (!inv.clearUsage(RPG.WT_TWOHANDS)) return false;
	  }
	  if (wt==RPG.WT_SECONDHAND) {
  		if (!inv.clearUsage(RPG.WT_TWOHANDS)) return false;
		}
		
	  if (wt==RPG.WT_FULLBODY) {
      if (!inv.clearUsage(RPG.WT_TORSO)|!inv.clearUsage(RPG.WT_LEGS)) return false;
	  }
	  if (wt==RPG.WT_TORSO) {
      if (!inv.clearUsage(RPG.WT_FULLBODY)) return false;
	  }
	  if (wt==RPG.WT_LEGS) {
      if (!inv.clearUsage(RPG.WT_FULLBODY)) return false;
	  }
		
    t.y=wt;
    return true;
	}

  // placeholder function for special hit effects
  //
  // returns additional damage, if any
  public int hit(Thing t, int dam) {
    return 0;
  }

  public int attackWith(Thing target, Thing weapon) {
    if (target==null) return 0;
    boolean isvisible=target.isVisible();
    boolean ishero=(this==Game.hero);    
    int result=0;
    
    if (weapon instanceof Weapon) {
      Weapon w = (Weapon)weapon;
      
      Map map=target.getMap();
      
      if (w.use(this,target,USE_ATTACK)>0) {
        
        // get result from hitting with weapon
        // result = hps damage inflicted
        result=w.use(this,target,USE_HIT);
        
        // any special hit effects?
        result+=hit(target,result);
        
        // message to hero
        Mobile mob=(Mobile) target;
        if (mob.isDead()&&isvisible) {
          Game.message(Text.capitalise(this.getTheName()+ (ishero?" have slain ":" has slain ")+target.getTheName()));
        }
      }
      
      // notify nearby creatures of attack
      if (map!=null) map.areaNotify(x,y,8,AI.EVENT_ALARM,target.getStat(RPG.ST_SIDE),this); 
    }
    return result;
  }

  
  // get unarmed weapon object
  // pretty important for most monsters
  public Weapon getUnarmedWeapon() {
    int unarmed=getStat(RPG.ST_UNARMED);
    
    if (unarmed<=0) unarmed=0;
    
    return Lib.WEAPON_MAUL;
  }
  
  // check if a given mobile is hostile to this being
  public boolean isHostile(Mobile b) {
    return getAI().isHostile(this,b);
  }
  
  // return the wielded / worn item in particular location
  public Thing getWielded(int wt) {
    Thing w=inv.getWeapon(wt);
    
    if (w==null) {
      if (wt==RPG.WT_MAINHAND) w=inv.getWeapon(RPG.WT_TWOHANDS);
      if ((wt==RPG.WT_TORSO)||(wt==RPG.WT_LEGS)) w=inv.getWeapon(RPG.WT_FULLBODY);
    }
    return w;
  }
  
  // use initially gifted items
  // not very discerning
  public void utiliseItems() {
    if (getStat(RPG.ST_IN)<4) return;
    Item[] stuff = inv.getWieldableContents();
    if (stuff!=null) for (int i=0; i<stuff.length; i++) {
      wield(stuff[i],stuff[i].wieldType());
    } 
  }

  // try moving to specified square
  // - attempt to attack hostle creature
  // - chat/displace fiendly creature
  // - attempt default action on blocking object
  // - trigger square events while moving
  // returns true if move successful 
  private static final String[] confusionstrings={"What?","How?","Who?","Where?","Which?"};
  
  public boolean tryMove(Map map, int tx, int ty) {
    Mobile m=map.getMobile(tx,ty);
    boolean ishero=(this==Game.hero);
    boolean moving=false;
    
    if ((getStat(RPG.ST_CONFUSED)>0)) {
      if (RPG.test(getStat(RPG.ST_CONFUSED),getStat(RPG.ST_WP))) {
        if (ishero) {
          Game.message(confusionstrings[RPG.r(confusionstrings.length)]);
        }
        do {
          tx=x+RPG.r(3)-1;
          ty=y+RPG.r(3)-1;
        } while ((tx==0)&&(ty==0)); 
      } 
    }

    if (m!=null) {
      // we have a mobile in the way!
      if (isHostile(m)) {
        // it's hostile, so attack
        attack(m);
        return true;
      } else if (!(m instanceof Hero)) {
        // try to push past somebody friendly
        // test relative strength
        if ((m.getStat(RPG.ST_NON_DISPLACEABLE)<=0)&&(RPG.test(getStat(RPG.ST_ST),m.getStat(RPG.ST_ST)))) {
          if (ishero) Game.message("You push past "+m.getTheName()); 
          map.addThing(m,x,y);
          moveTo(map,tx,ty);
          m.aps-=100;
          moving=true;
        } else {
          if (ishero) Game.message("You can't get past "+m.getTheName()); 
          return false;
        } 
      } else {
        // it's the hero, so don't push past
        return false; 
      }
    }
    
    if (canMove(map,tx,ty)) moving=true;
    
    if ((getStat(RPG.ST_IMMOBILIZED)>0)||(getStat(RPG.ST_MOVESPEED)<=0)) {
      if (ishero) Game.message("You cannot move!");
      return false; 
    }
    
    if (moving) {
      aps=aps-(map.getMoveCost(this,tx,ty)*getStat(RPG.ST_MOVECOST))/getStat(RPG.ST_MOVESPEED);
      moveTo(map,tx,ty);
      return true;
    }
    
    // use doors etc.
    Thing head=map.getObjects(tx,ty);
    while (head!=null) {
      if (head.isBlocking()) {
        head.use(this);
        aps-=10;
        return false;       
      }
      if ((!ishero)&&head.isWarning(this)) {
        aps-=10;
        return false;
      }
      head=head.next;
    }
    
    // can't do this move, will have to try something else!
    return false;
  }

  // attack a given target with HtH weapon
  public int attack(Thing target) {
    if (target==null) return 0;

    int result=0;
    int acost=0;
 
    if (getStat(RPG.ST_PANICKED)>0) {
      if (RPG.test(getStat(RPG.ST_PANICKED),getStat(RPG.ST_WP))) {
        if (isVisible()) Game.message(getTheName()+" freeze"+((this==Game.hero)?"":"s")+" in terror"); 
        return 0;
      }
    }
    
    int aspeed=getStat(RPG.ST_ATTACKSPEED);
    if (aspeed<=0) {
      aps=-1;
      return 0;
    }
    
    // get main weapon
    Thing w=getWielded(RPG.WT_MAINHAND);
    
    if (w instanceof Weapon) {
      // main attack
      result+=attackWith(target,w);
      acost += w.getStat(RPG.ST_ATTACKCOST)+getStat(RPG.ST_ATTACKCOST);
  
      // attack with second weapon if available
      w=getWielded(RPG.WT_SECONDHAND);
      if (w instanceof Weapon) {
        result+=attackWith(target,w);
        acost += w.getStat(RPG.ST_ATTACKCOST)+getStat(RPG.ST_ATTACKCOST);
      }
    } else {
      // use default weapon for unarmed creatures
      w=Lib.WEAPON_MAUL; 
      result+=attackWith(target,w);
      acost += w.getStat(RPG.ST_ATTACKCOST)+getStat(RPG.ST_ATTACKCOST);
    }

    // spend action points on attack
    aps=aps-(100*acost)/getStat(RPG.ST_ATTACKSPEED);  
    
    return result;
  }
  
  // automatic skill gains for creatures/NPCs
  public void autoLearnSkills() {
    while(getStat(RPG.ST_SKILLPOINTS)>0) {
      int s=Skill.randomSkill(this);
      if (Skill.canLearnSkill(this,Skill.name(s))) {
        Skill.increaseSkill(this,Skill.name(s),1);
      }
    } 
  }
  
  // turn creature into a follower
  public void makeFollower(Being leader) {
    setAI(new FollowerAI(leader));
    if (leader==Game.hero) {
    	setStat(RPG.ST_STATE,AI.STATE_FOLLOWER);
    }
    setStat(RPG.ST_SIDE,leader.getStat(RPG.ST_SIDE));
  }
  
  public void makeGuard(Map map, int x1, int y1, int x2, int y2) {
    Thing b1=new Secret(Secret.MARKER); map.addThing(b1,x1,y1);
    Thing b2=new Secret(Secret.MARKER); map.addThing(b2,x2,y2);
    setAI(new GuardAI(b1,b2));
    map.addThing(this,(x1+x2)/2,(y1+y2)/2);
  }
}