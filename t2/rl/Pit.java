package rl;

import java.awt.*;

public class Pit extends Map {
  public int type;

  public Pit(int level) {
    this(0,level); 
  }
  
  public Pit(int t, int level) {
    super(9,9); 
  
    if (t==0) t=RPG.d(4);
    type=t;
    
    switch(type) {
      // regular pit
      case 1: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        fillArea(0,0,width-1,height-1,floortile);
        for (int x=0; x<=8; x+=8) for (int y=0; y<=8; y+=8) {
          setTile(x,y,walltile); 
        }
        fractalize(0,0,8,8,4);
        fillBorder(0,0,width-1,height-1,walltile);
        
        entrance=new Portal("ladder up");
        addThing(new SecretItem(entrance));

        addThing(Lib.createCreature(level)); 
        break;
      }
      
      // room pit
      case 2: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        fillArea(0,0,width-1,height-1,floortile);
        fillBorder(0,0,width-1,height-1,walltile);

        entrance=new Portal("stairs up");
        addThing(entrance);

        addThing(Lib.createCreature(level)); 
        if (RPG.d(3)==1) addThing(Lib.createCreature(level+1)); 
        if (RPG.d(3)==1) addThing(new SecretItem(Lib.createItem(level)));
        if (RPG.d(3)==1) addThing(new HeroTrap(level));
        break;
      }
    
      // trapped pit
      case 3: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        fillArea(0,0,width-1,height-1,floortile);
        for (int x=0; x<=8; x+=8) for (int y=0; y<=8; y+=8) {
          setTile(x,y,walltile); 
        }
        fractalize(0,0,8,8,4);
        fillBorder(0,0,width-1,height-1,walltile);
        
        entrance=new Portal("ladder up");
        addThing(new SecretItem(entrance));

        addThing(new HeroTrap(level)); 
        addThing(new HeroTrap(level)); 
        addThing(new HeroTrap(level)); 
        addThing(new HeroTrap(level)); 
        addThing(new HeroTrap(level)); 
        addThing(new HeroTrap(level)); 
        if (RPG.d(3)==1) addThing(Lib.createCreature(level+1)); 
        break;
      }
      
      // critter pit
      case 4: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        fillArea(0,0,width-1,height-1,floortile);
        for (int x=0; x<=8; x+=8) for (int y=0; y<=8; y+=8) {
          setTile(x,y,walltile); 
        }
        fractalize(0,0,8,8,4);
        fillBorder(0,0,width-1,height-1,walltile);

        Creature c=Lib.createMonster(level-3);
        for (int x=0; x<=8; x+=1) for (int y=0; y<=8; y+=1) {
          if ((RPG.d(3)==1)&&(!isBlocked(x,y))) addThing(c.cloneType(),x,y); 
        }
        
        entrance=new Portal("stairs up");
        addThing(entrance);
        break;
      }
      
      //
      // Debug/teString pits follow
      //
      
      // magic item pit!!
      case 1000: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        fillArea(0,0,width-1,height-1,floortile);
        fillBorder(0,0,width-1,height-1,walltile);

        entrance=new Portal("ladder up");
        addThing(entrance,1,1);
        
        break;
      }

      // commonweapon pit
      case 1001: {
        setTheme(new Theme(RPG.pick(new String[] {"standard","caves","labyrinthe","sewers"})));
        
        fillArea(0,0,width-1,height-1,floortile);
        fillBorder(0,0,width-1,height-1,walltile);

        entrance=new Portal("ladder up");
        addThing(entrance,1,1);
        
        break;
      }
    }
  }

  // override hitsqure for random encounters
  public void hitSquare(Thing m,int x, int y,boolean touchfloor) {
    if (m==Game.hero) switch (type) {
      case 1000: if ((x==1)&&(y>1)) {
        clearArea(2,1,width-2,height-2);
        for (int px=2; px<width-1; px++) for (int py=1; py<(height-1); py++) {
          Item it=Lib.createItem(RPG.r(10));
          // it.flags|=Item.ITEM_IDENTIFIED;
          addThing(it,px,py); 
        } 
        break;
      }  

      case 1001: if ((x==1)&&(y>1)) {
        clearArea(2,1,width-2,height-2);
        for (int px=2; px<width-1; px++) for (int py=1; py<(height-1); py++) {
          Item it;
          switch(y) {
            case 2: case 3: it=CommonWeapon.createWeapon(RPG.d(px*4)); break;
            default: it=Lib.createItem(RPG.d(px*4)); break;
          }
          // it.flags|=Item.ITEM_IDENTIFIED;
          addThing(it,px,py); 
        } 
        break;
      }  
    }
    
    super.hitSquare(m,x,y,touchfloor);
  }

  public String getLevelName() {
    return "A dangerous pit";  
  }

  public String getEnterMessage() {
    return "You find yourself at the bottom of a dangerous pit";  
  }

}