//
// A critter-infested forest map, used in the Emerald Sword quest
//

package rl;

class DeepForest extends Map {
  
  public DeepForest(int w, int h) {
	  super(w,h);
  
    setTheme(new Theme("deepforest"));
    
    fillArea(0,0,w-1,h-1,walltile);
    fillArea(1,1,w-2,h-1,0);

    // put in the entrance
    //Point es=new Point(w/4,RPG.rspread(w/4,(w*3)/4));
    //if (RPG.d(2)==1) {
    //  int t=es.x; es.x=es.y; es.y=t;
    //}
    //if (RPG.d(2)==1) {
    //  es.x=w-es.x; es.y=h-es.y;
    //}
    entrance=new EdgePortal(EdgePortal.SOUTH);
    addThing(entrance,w/2,h-1);
    
    Point es=new Point(w/2,(3*h)/4);
    
    makePath(w/2,h-4,es.x,es.y);
    fillArea(w/2-1,h-8,w/2+1,h-1,floortile);
    addThing(Coin.createMoney(600),es.x,es.y);
    // addThing(new Coin(1,20),es.x,es.y);
    
    // your goal
    makeClearing(w-es.x,h-es.y);
    makeHut(w-es.x-5,h-es.y-4,w-es.x+5,h-es.y+4);
    
    //make a route
    Point tp=new Point(RPG.r(width-2)+1,RPG.r(height-2)+1);
    makePath(es.x,es.y,tp.x,tp.y);
    makePath(tp.x,tp.y,w-es.x,h-es.y);
    
    for (int loop=0; loop<10; loop++) {
      Point ls=findFreeSquare();
      int cx=RPG.r(width-2)+1;	
      int cy=RPG.r(width-2)+1;
      makeClearing(cx,cy);	
      makePath(ls.x,ls.y,cx,cy);
    }
    
    
    replaceTiles(0,walltile);
    
    decorateForest(0,0,w-1,h-1);
  
    // fill in the blanks
    replaceTiles(0,walltile);

    // drop in some critters
    for (int i=0; i<8; i++) {
      addBeasties(RPG.r(w),RPG.r(h));
      Point p=findFreeSquare();
      addBaddie(p.x,p.y);	
    }

    // the lair
    Point dps=findFreeSquare();
    //Point dps=new Point(w/2,h-3);
    Portal dp=new Portal("stairs down");
    addThing(dp,dps.x,dps.y);
    dp.makeLink(new GuardianVault(Lib.createCreature(6), StandardArmour.createArmour(6)));

    // the infinite dungeon
    Point ids=findFreeSquare();
    //Point dps=new Point(w/2,h-3);
    Portal id=new InfinitePortal(2,1);
    addThing(id,ids.x,ids.y);
  }

  private void makePath(int x1,int y1, int x2, int y2) {
    int dx; int dy;
    while ((x1!=x2)||(y1!=y2)) {
      if (getTile(x1,y1)==0) {
      	setTile(x1,y1,floortile);
      }	
      if (RPG.d(3)==1) {
        dx=RPG.sign(x2-x1);	
        dy=RPG.sign(y2-y1);	
      } else {
        dx=RPG.r(3)-1;	
        dy=RPG.r(3)-1;	
      }
      switch (RPG.d(3)) {
        case 1: dx=0; break;
        case 2: dy=0; break;	
      }
      x1+=dx;
      y1+=dy;
      x1=RPG.middle(1,x1,width-1);
      y1=RPG.middle(1,y1,height-1);
    }	
  }

  private void makeClearing(int x, int y) {
    int w=RPG.d(8); 
    int h=RPG.d(8);
    int x1=x-w;
    int y1=y-h;
    int x2=x+w;
    int y2=y+h;
      
    int cx=(x1+x2)/2;
    int cy=(y1+y2)/2;
    
    for (int lx=x1; lx<=(x1+w*2); lx++) for (int ly=y1; ly<(y1+h*2); ly++) {
      if ( (((lx-cx)*(lx-cx)*100)/(w*w) + ((ly-cy)*(ly-cy)*100)/(h*h)) < 100 ) {
    	  if (getTile(lx,ly)==0) setTile(lx,ly,floortile);
      }
    }  
    
    if ((w>=4)&&(h>=4)&&(RPG.d(6)==1)&&(x!=entrance.x)) {
      // do a fairy ring
      for (int i=0; i<RPG.d(10); i++) {
        float a=RPG.random()*100;
        int mx=(int)(0.5+cx+3*Math.sin(a));
        int my=(int)(0.5+cy+3*Math.cos(a));
        if (!isBlocked(mx,my)) addThing(Food.createFood(Food.MUSHROOM),mx,my);	
      }
      
    	for (int i=0; i<=RPG.d(2,4); i++) {
    	  Creature c=new Creature(Creature.RABBIT);
    	  c.setStat(RPG.ST_TARGETX,cx);	
    	  c.setStat(RPG.ST_TARGETX,cy);
    	  addThing(c,cx,cy);	
    	}
    }  
  }


  public void makeHut(int x1,int y1,int x2,int y2) {
  	fillArea(x1,y1,x2,y2,floortile);
  	int w=x2-x1-4;
  	int h=y2-y1-4;
  	
  	if ((h<3)||(w<3)) return;
  	
  	fillArea(x1+1,y1+1,x2-1,y2-1,Tile.CAVEWALL);
  	fillArea(x1+2,y1+2,x2-2,y2-2,Tile.FLOOR);
  	
  	int doorx; int doory;
  	
  	if (RPG.d(2)==1) {
  		doorx=RPG.rspread(x1+2,x2-2);
  		doory=(RPG.d(2)==1)?y1+1:y2-1;
  	} else {
  		doory=RPG.rspread(y1+2,y2-2);
  		doorx=(RPG.d(2)==1)?x1+1:x2-1;
  	}
  	
    setTile(doorx,doory,floortile);
    addThing(new Door(),doorx,doory);
  	
    Portal p=new Portal("stairs down");
    addThing(p,RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    p.makeLink(new EmeraldDungeon(71,71));

    addThing(new Creature(Creature.GOBLIN),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(new Creature(Creature.GOBLIN),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Lib.createItem(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Lib.createItem(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Food.createFood(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Drink.createDrink(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Food.createFood(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(Drink.createDrink(0),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
    addThing(new PowderKeg(),RPG.rspread(x1+2,x2-2),RPG.rspread(y1+2,y2-2));
  }

  
  public void decorateForest(int x1,int y1,int x2,int y2) {
   	for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
   	  if (isBlocked(x,y)) continue;
   	  if (!(getTile(x,y)==floortile)) continue;
   	  else {
   	  	switch(RPG.d(60)) {
   	  	  case 1: case 2:	
 	  		    addThing(GameScenery.create(4),x,y);
 	  		    break;
   	  	  case 5:	
 	  		    addThing(GameScenery.create(5),x,y);
 	  		    break;   	  
   	  	  case 6:	
 	  		    addThing(Food.createFood(Food.MUSHROOM),x,y);
 	  		    break;   	  
 	  		}
   	  }	
   	}

  }

  private void addBeasties(int x, int y) {
    Creature m;
    switch (RPG.d(5)) {
      case 1: m=new Creature(Creature.TRIFFID); break;
      default: m=new Creature(Creature.SPIDER); break;
    }    
        
    for (int i=0; i<4; i++) {
      int bx=x-2+RPG.r(5);
      int by=y-2+RPG.r(5);
      if (isClear(bx,by)) {
    	  addThing(m,bx,by);
        m=m.cloneType(); // create more of the same
      }	
    }
  }

  private void addGuard(int x, int y) {
    if (isClear(x,y)) {
    	Mobile m=new Creature(Creature.SNAKE);
    	addThing(m,x,y);
    }	
  }

  private void addWandering() {
    Point p=findFreeSquare();
    int x=p.x; int y=p.y;
    if (!isVisible(x,y)) {
      Mobile m=null;
      m=Creature.createMonster(getLevel());
      if (m!=null) addThing(m,x,y);	
    }	
  }

  private void addBaddie(int x, int y) {
    if (isClear(x,y)) {
    	Mobile m=new Creature(Creature.MUTANT);
    	m.addThing(Lib.createItem(0));
    	addThing(m,x,y);
    }	
  }
  
  
  public int getLevel() {return 1;}
  
  public void action(int time) {
    super.action(time);
    for (int i=RPG.po(time,3000); i>0; i--) addWandering();	
  }
  
  public String getEnterMessage() {
    return "The forest here is dark and dangerous.....";	
  }
  
  public String getLevelName() {
    return "Deep in the forest";  
  }
}