// Map object for standard towns
//

package rl;

import java.awt.*;

public class Town extends Map {

  // out of town aresa (buildarea -1)
  public static final int[] OUTER={0,1,2,7,14};

  // inner town aresa (buildarea -2)
  public static final int[] INNER={4,15,5,3};
  
  // build town sector of type t
  // -2 = random inner town area
  // -1 = random outer town area
  //  0 = wooded
  //  1 = cleared
  //  2 = animals
  //  3 = huts
  //  4 = block
  //  5 = hall
  //  6 = square 
  //  7 = pond
  //  8 = N/S river
  //  9 = N/S river + bridge
  //  10 = E/W river
  //  11 = E/W river + bridge
  //  12 = N/S street
  //  13 = E/W street
  //  14 = orchard
  //  15 = smithy (north exit)
  //  16 = N/W river bend    
  //  17 = N/E river bend     
  //  18 = S/E river bend    
  //  19 = S/W river bend    
  //  20 = N/W/E river fork  
  //  21 = N/S/E river fork  
  //  22 = S/W/E river fork  
  //  23 = N/S/W river fork  
  
  // uber-cool array of town arrays
  public int[][][] towns = {
    {{ 17, 22, 16, -2, -1 },
     { -2,  9,  6, 13, -2},
     { -2,  8, -2, 15, -1}}
     ,   
    {{ 8, -1, -2, -1, 7 },
     { 9, 13,  6, -2, -1},
     {17, 19, 12, -2, 14}}   

     ,   
    {{-1, 17, 19, -2, 13 },
     {-1, -2, 9,  6,  13}}   

     ,   
    {{-1, 12, -2 },
     {13, 6,  13 },
     {-2, 12, -1 }}   
  
     ,   
    {{-1, -2, 14 },
     {-2, 6,  -2 },
     {7 , -2, -1 }}   
  };
  
  public Town(int w, int h) {
    // basic 80*48 town
    super(1,1); 

    // select a town layout to use
    int[][] layout=towns[RPG.r(towns.length)];

    // change to correct map size
    setSize(16*layout[0].length,16*layout.length);
    
    buildTown(layout); 
  }
  
  private void buildTown(int[][] areas) {
    int cx=0; int cy=0; 
    
    floortile=Tile.PLAINS;
    walltile=Tile.CAVEWALL;
    
    fillArea(0,0,width-1,height-1,floortile);

    for (int ay=0; ay<areas.length; ay++) for (int ax=0; ax<areas[ay].length; ax++) {
       buildArea(ax*16,ay*16,areas[ay][ax]);
       if (areas[ay][ax]==6) {
         // this is the centre
         cx=16*ax;
         cy=16*ay; 
       } 
    }
    
    //buildArea(0,0,8);   
    //buildArea(0,16,9);   
    //buildArea(0,32,8);   
    //buildArea(16,0,RPG.pick(OUTER));    
    //buildArea(16,16,13);    
    //buildArea(16,32,RPG.pick(INNER));    
    //buildArea(32,0,(RPG.d(3)==1)?12:RPG.pick(INNER));    
    //buildArea(32,16,6);    
    //buildArea(32,32,(RPG.d(3)==1)?12:RPG.pick(INNER));    
    //buildArea(48,0,3);    
    //buildArea(48,16,4);    
    //buildArea(48,32,RPG.pick(INNER));    
    //buildArea(64,0,RPG.pick(OUTER));    
    //buildArea(64,16,RPG.pick(OUTER));    
    //buildArea(64,32,RPG.pick(OUTER));    
    addTownies();
    
    entrance=new Portal(0);
    addThing(entrance,cx+8,cy+7);
    
    if (QuestApp.debug) try {
      // add test dungeon
      Portal id=new InfinitePortal(2,1);
      addThing(id,cx+8,cy+8);
    
      Being b=new Person(Person.TEACHER);
      for (int i=0; i<10; i++) b.gainLevel();
      b.makeGuard(this, cx+4, cy+4, cx+12,cy+12);
      
    
      Portal pt=new Portal("ladder down");
      addThing(pt,cx+8,cy+9);
      pt.makeLink(new Pit(1001,0));
    
      Portal pt2=new Portal("ladder down");
      Portal pt3=new Portal("ladder up");
      addThing(pt2,cx+9,cy+8);
      Map rd=RogueDungeon.create(1,new Theme("sewers"));
      rd.addThing(pt3);
      rd.entrance=pt3;
      pt2.makeLink(rd);

      Door door=new Door();
      door.trap=new RuneTrap(null, Spell.BLAZE);
      addThing(door,cx+10,cy+8);
      

      addThing(new StandardWeapon(StandardWeapon.EMERALDSWORD),cx+8,cy+10);
    
      Item it=(Item)Lib.createArmour(0);
      it.flags|=Item.ITEM_CURSED;
      addThing(it,cx+8,cy+11);
      
      
      addThing(new PlacePortal(PlacePortal.BANDITCAVES),cx+8,cy+12);

      addThing(new HeroTrap(HeroTrap.PITTRAP,2),cx+8,cy+13);
    } catch (Exception e) {
      Game.message("Error adding Town debug zone");
    }
  }
  
  
  
  private void buildArea(int x, int y, int t) {
    if (t==-1) t=RPG.pick(OUTER);
    if (t==-2) t=RPG.pick(INNER);
    
    switch (t) {
      
      // wooded area
      case 0: {
        for (int i=0; i<20; i++) {
          addThing(GameScenery.create(3),x,y,x+15,y+15);
        } 
        addThing(new Person(Person.RANGER),x,y,x+15,y+15);
        
        break;
      }
      
      // hut
      case 3: {
        int hw=RPG.r(3)+3;  
        int hh=RPG.r(3)+3;  
        int hx=x+1+RPG.r(14-hw);
        int hy=y+1+RPG.r(14-hh);
        int dx=RPG.r(2)*2-1; 
        int dy=RPG.r(2)*2-1;
        if (RPG.d(2)==1) dx=0; else dy=0;
        addRoom(hx,hy,hx+hw,hy+hh,dx,dy);
        break;
      }
      
      // block
      case 4: {
        addRoom(x+8-RPG.d(2,3),y+8-RPG.d(2,3),x+8,y+8,0,-1); 
        addRoom(x+8-RPG.d(2,3),y+8,x+8,y+8+RPG.d(2,3),0,1); 
        addRoom(x+8,y+8-RPG.d(2,3),x+8+RPG.d(2,3),y+8,0,-1); 
        addRoom(x+8,y+8,x+8+RPG.d(2,3),y+8+RPG.d(2,3),0,1); 
        break;
      }
      
      // hall
      case 5: {
        int left=RPG.d(2,3);
        int right=RPG.d(2,3);
        fillArea(x+8-left,y+3,x+8+right,y+12,Tile.STONEFLOOR);
        fillBorder(x+8-left,y+3,x+8+right,y+12,Tile.STONEWALL);
        
        if (left<=4) {
          fillRoom(x+1,y+3+RPG.r(2),x+8-left,y+8+RPG.r(3),Tile.STONEWALL,Tile.STONEFLOOR); 
          setTile(x+8-left,y+5+RPG.r(3),Tile.STONEFLOOR);
        }
        
        // main door
        setTile(x+8,y+12,Tile.STONEFLOOR);
        addThing(new Door("ornate door",getLevel()),x+8,y+12);
        
        addThing(new Person(Person.PRIEST),x,y,x+15,y+15);
    
        // random rotate
        rotateArea(x,y,16,RPG.r(4));
        break;
      }

      // central crossroads with shops
      case 6: {
        addShop(x,y,x+RPG.d(3,2),y+RPG.d(3,2),1,0,4);
        addShop(x+15-RPG.d(3,2),y,x+15,y+RPG.d(3,2),0,1,0);
        addShop(x,y+15-RPG.d(3,2),x+RPG.d(3,2),y+15,0,-1,2);
        addShop(x+15-RPG.d(3,2),y+15-RPG.d(3,2),x+15,y+15,-1,0,3);
        break;
      }
      
      // pond
      case 7: {
        setTile(x+8,y+8,Tile.SEA);
        fractalize(x+4,y+4,x+11,y+11,4);
        for (int i=0; i<10; i++) {
          addThing(GameScenery.create(GameScenery.BUSH),x,y,x+15,y+15); 
        } 
        break;
      }
      
      // river
      case 8: case 9: case 10: case 11: {
        // N/S river
        makeRandomPath(x+8,y,x+8,y+15,x,y,x+15,y+15,Tile.SEA,false);
        spreadTiles(x,y,x+15,y+15,Tile.SEA,floortile);
        if ((t&1)==1) {
          // add the bridge
          fillArea(x,y+7,x+15,y+9,floortile);
        }
        
        // rotate for E/W river
        if ((t&2)>0) rotateArea(x,y,16,1);
        break;
      }
      
      // NS and EW streets
      case 12: case 13: {
        int p1,p2;
        p1=8-RPG.d(4);
        p2=8+RPG.d(4);
        addFeature(x,y+RPG.r(2),x+p1,y+5+RPG.r(3),0,1,0);
        addFeature(x+p1,y+RPG.r(2),x+p2,y+5+RPG.r(3),0,1,0);
        addFeature(x+p2,y+RPG.r(2),x+15,y+5+RPG.r(3),0,1,0);

        p1=8-RPG.d(4);
        p2=8+RPG.d(4);
        addFeature(x,y+9+RPG.r(3),x+p1,y+15-RPG.r(2),0,-1,0);
        addFeature(x+p1,y+9+RPG.r(3),x+p2,y+15-RPG.r(2),0,-1,0);
        addFeature(x+p2,y+9+RPG.r(3),x+15,y+15-RPG.r(2),0,-1,0);
        
        // rotate for N/S street
        if (t==12) rotateArea(x,y,16,1);
        break;
      } 

      // orchard
      case 14: {
        for (int i=0; i<20; i++) {
          int px=x+1+RPG.r(14);
          int py=y+1+RPG.r(14);
          if (!isBlocked(px,py)) addThing(GameScenery.create(7),px,py);
        } 
        for (int i=0; i<20; i++) {
          int px=x+1+RPG.r(14);
          int py=y+1+RPG.r(14);
          if (!isBlocked(px,py)) addThing(Food.createFood(1+4*RPG.r(2)),px,py);
        } 
        break;
      }
      
      // smithy
      case 15: {
        addShop(x+5,y+5,x+10,y+10,0,-1,5);
        rotateArea(x,y,16,RPG.r(4));
        break; 
      }
      
      // river bends
      case 16: case 17: case 18: case 19: {
        makeRandomPath(x+8,y,x,y+8,x,y,x+15,y+15,Tile.SEA,false);
        spreadTiles(x,y,x+15,y+15,Tile.SEA,floortile);
        
        // rotate clockwise to correct orientation
        // 16 = N/W position etc..
        rotateArea(x,y,16,t-16);
        break;
      }
      
      // river forks
      case 20: case 21: case 22: case 23: {
        makeRandomPath(x+8,y,x,y+8,x,y,x+15,y+15,Tile.SEA,false);
        makeRandomPath(x+8,y,x+15,y+8,x,y,x+15,y+15,Tile.SEA,false);
        spreadTiles(x,y,x+15,y+15,Tile.SEA,floortile);
        
        // rotate clockwise to correct orientation
        // 20 = N/W/E position etc..
        rotateArea(x,y,16,t-20);
        break;
      }
      
    }
  }
  
  // adds a town feature in specified area
  public void addFeature(int x1, int y1, int x2, int y2,int dx, int dy, int t) {
    // randomize feature
    if (t==0) t=RPG.d(10);
    
    switch (t) {
      case 1:
        addShop(x1,y1,x2,y2,dx,dy,0); 
        break;
      
      default: 
        addRoom(x1,y1,x2,y2,dx,dy); 
        break; 
    } 
    
  }
  
  public void addTownies() {
    for (int i=0; i<=10; i++) {
      Point ts=findFreeSquare();
      addThing(new Person(Person.TOWNIE),ts.x,ts.y);
    } 
    for (int i=0; i<=6; i++) {
      Point ts=findFreeSquare();
      addThing(new Person(Person.WOMAN),ts.x,ts.y);
    } 
    for (int i=0; i<=4; i++) {
      Point ts=findFreeSquare();
      addThing(new Person(Person.GIRL),ts.x,ts.y);
    } 
    for (int i=0; i<=8; i++) {
      Point ts=findFreeSquare();
      addThing(new Person(Person.GUARD),ts.x,ts.y);
    } 
    
    Point ts=findFreeSquare();
    addThing(new Person(Person.TEACHER),ts.x,ts.y);
  }
  
  // Create standard shops
  //
  // note that areas are likely to be rotated
  // this will mess up GuardAI(x1,y1,x2,y2) objects
  // hence use secret markers to mark out the shop area
  public void addShop(int x1, int y1, int x2, int y2,int dx, int dy, int t) {
    int doorx,doory;
    doorx=(dx==0)?RPG.rspread(x1+1,x2-1): ((dx==-1)?x1:x2);
    doory=(dy==0)?RPG.rspread(y1+1,y2-1): ((dy==-1)?y1:y2);
    fillArea(x1,y1,x2,y2,Tile.FLOOR);
    fillBorder(x1,y1,x2,y2,Tile.CAVEWALL);
    setTile(doorx,doory,Tile.FLOOR);
    
    Door door=new Door("shop door",getLevel());
    
    if (t==0) t=RPG.d(8);
    
    switch(t) {
      // magic type shop
      case 1: {
        for (int x=x1+1; x<x2; x++) for (int y=y1+1; y<y2; y++) {
          Item it=(Item)Lib.createMagicItem(Game.hero.getStat(RPG.ST_LEVEL));
          if (RPG.d(2)==1) it.identify(1000);
          it.flags|=Item.ITEM_OWNED;  // shop owned
          addThing(it,x,y); 
        }
        Being wiz=new Person(Person.WIZARD);

        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1,y1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        wiz.setAI(new GuardAI(b1,b2));
        addThing(wiz,(x1+x2)/2,(y1+y2)/2);
        addThing(GameScenery.create("magic shop sign"),doorx+dx+dy*(RPG.r(2)*2-1),doory+dy+dx*(RPG.r(2)*2-1)); 
        break;
      }
      
      // weapon store
      case 2: {
        for (int x=x1+1; x<x2; x++) for (int y=y1+1; y<y2; y++) {
          Item it=null;
          switch (RPG.d(6)) {
            case 1: it=StandardWeapon.createWeapon(4); break; 
            case 2: it=StandardArmour.createArmour(0); break;
            case 3: it=StandardArmour.createShield(0); break; 
            case 4: it=Missile.createMissile(0); break; 
            case 5: it=RangedWeapon.createRangedWeapon(0); break; 
            default: continue;
          };  
          it.flags|=Item.ITEM_OWNED;  // shop owned
          addThing(it,x,y); 
        }
        Being guard=new Person(Person.GUARD);

        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1+1,y1+1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        guard.setAI(new GuardAI(b1,b2));
        addThing(guard,(x1+x2)/2,(y1+y2)/2); 
        
        addThing(GameScenery.create("armoury sign"),doorx+dx+dy*(RPG.r(2)*2-1),doory+dy+dx*(RPG.r(2)*2-1)); 
        break; 
      }
      
      // food store
      case 3: {
        for (int x=x1+1; x<x2; x++) for (int y=y1+1; y<y2; y++) {
          Item it= Food.createFood(0);
          it.flags|=Item.ITEM_OWNED;  // shop owned
          addThing(it,x,y); 
        }
        Being shopkeeper=new Person(Person.SHOPPIE);

        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1+1,y1+1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        shopkeeper.setAI(new GuardAI(b1,b2));
        addThing(shopkeeper,(x1+x2)/2,(y1+y2)/2); 

        addThing(GameScenery.create("food shop sign"),doorx+dx+dy*(RPG.r(2)*2-1),doory+dy+dx*(RPG.r(2)*2-1)); 
        break;
      }

      // Smithy
      // note that door is assumed to face north
      case 5: {
        Being smith=new Person(Person.BLACKSMITH);
        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1+1,y1+1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        smith.setAI(new GuardAI(b1,b2));

        addThing(smith,(x1+x2)/2,(y1+y2)/2); 
        addThing(new Fire(5,0),x2-1,y2-1);
        addThing(new Fire(5,0),x2-2,y2-1);
        
        addThing(GameScenery.create("smithy sign"),doorx+dx+dy*(RPG.r(2)*2-1),doory+dy+dx*(RPG.r(2)*2-1)); 
        break;
      }
      
      // Goblin store
      case 6: {
        door=new Door("goblin door",10);
        door.setLocked(false);
        
        Being gobbo=new Creature(Creature.SHAMAN,15);
        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1+1,y1+1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        gobbo.setAI(new GuardAI(b1,b2));
        gobbo.setStat(RPG.ST_STATE,AI.STATE_INHABITANT);
        gobbo.setPersonality(new Personality(Personality.CHATTER,Personality.CHATTER_GOBLIN));
        addThing(gobbo,(x1+x2)/2,(y1+y2)/2); 
        
        for (int x=x1+1; x<x2; x++) for (int y=y1+1; y<y2; y++) {
          Item it;
          switch(RPG.d(4)) {
            case 1: it=Lib.createMagicItem(4); break;
            case 2: it=Lib.createItem(5); break;
            default: it=Potion.createPotion(); break;
          }
          it.flags|=Item.ITEM_OWNED;  // shop owned
          addThing(it,x,y); 
        }
        break;
      }

      // general store
      default: {
        for (int x=x1+1; x<x2; x++) for (int y=y1+1; y<y2; y++) {
          Item it= Lib.createItem(5);
          it.flags|=Item.ITEM_OWNED;  // shop owned
          addThing(it,x,y); 
        }
        Being shopkeeper=new Person(Person.SHOPPIE);

        Thing b1=new Secret(Secret.MARKER); addThing(b1,x1+1,y1+1);
        Thing b2=new Secret(Secret.MARKER); addThing(b2,x2-1,y2-1);
        shopkeeper.setAI(new GuardAI(b1,b2));
        addThing(shopkeeper,(x1+x2)/2,(y1+y2)/2); 
        
        addThing(GameScenery.create("store sign"),doorx+dx+dy*(RPG.r(2)*2-1),doory+dy+dx*(RPG.r(2)*2-1)); 
      }
      
    }
  
    addThing(door,doorx,doory);
  }
  
  public void addBuilding() {
    int w=RPG.d(2,4)+3;
    int h=RPG.d(2,4)+3;
    int x1=RPG.r(width-w); 
    int y1=RPG.r(height-h);
    int x2=x1+w;
    int y2=y1+h;
    int dx=RPG.r(3)-1; 
    int dy=RPG.r(3)-1;
    if (RPG.d(2)==1) dx=0; else dy=0;
    
    if (countTiles(x1,y1,x2,y2,floortile)!=(w+1)*(h+1)) return;
    addRoom(x1+1,y1+1,x2-1,y2-1,dx,dy); 
  }
  
  
  public void addRoom(int x1, int y1, int x2, int y2, int dx, int dy) {
    int doorx,doory;
    doorx=(dx==0)?RPG.rspread(x1+1,x2-1): ((dx==-1)?x1:x2);
    doory=(dy==0)?RPG.rspread(y1+1,y2-1): ((dy==-1)?y1:y2);
    addRoom(x1,y1,x2,y2,dx,dy,doorx,doory);
  }
  
  
  // add a standard town room
  // all kinds of fun stuff inside
  public void addRoom(int x1, int y1, int x2, int y2,int dx, int dy, int doorx, int doory) {
    fillArea(x1,y1,x2,y2,Tile.FLOOR);
    fillBorder(x1,y1,x2,y2,walltile);
    
    boolean secret=false;
    Door door=new Door("door",getLevel());
    
    switch(RPG.d(12)) {
      case 1: {
        door.setLocked(true);
        Item it=Food.createFood(0);
        it.flags|=Item.ITEM_OWNED;
        addThing(it,x1+1,y1+1,x2-1,y2-1); 
        break;
      }
      case 2: { // alarmed room
        door.setLocked(true);
        Item it=Lib.createItem(8);
        it.flags|=Item.ITEM_OWNED;
        addThing(it,x1+1,y1+1,x2-1,y2-1); 
        addThing(new MapStateTrap(1),doorx,doory);
        break;
      }
      case 3: { // rats!
        Item it=Lib.createItem(3);
        addThing(new SecretItem(it),x1+1,y1+1,x2-1,y2-1); 
        for (int i=RPG.d(8); i>0; i--) addThing(new Creature(Creature.RAT),x1+1,y1+1,x2-1,y2-1); 
        break;
      }
      case 4: { // table
        addThing(GameScenery.create("table"),x1+1,y1+1,x2-1,y2-1); 
        break;
      }
      case 5: {
        if (RPG.d(6)==1) {
          Item it=Lib.createItem(3);
          addThing(new SecretItem(it),x1+1,y1+1,x2-1,y2-1); 
        }
        door=null;
        break;
      }
      case 6: case 7: case 8: case 9:
        door.setLocked(RPG.d(4)==1);
        decorateRoom(x1+1,y1+1,x2-1,y2-1);
        break;
      case 10:
        door=new Door("stable door");
        break;
    }
     
    if (secret) {
      addThing(new SecretDoor(),doorx,doory);
    } else {
      if (door!=null) addThing(door,doorx,doory);
      setTile(doorx,doory,Tile.FLOOR);
    }
  }
  
  // decorate room, given co-ordinates of interior space
  public void decorateRoom(int x1,int y1,int x2,int y2) {
    int w=x2-x1+1;
    int h=y2-y1+1;
    
    switch (RPG.d(6)) {
      case 1:
        for (int lx=x1; lx<=x2; lx++) for (int ly=y1; ly<=y2; ly++) {
           
        } 
        break;
      case 2: 
        if ((w>3)&&(h>=3)) {
          Person p=new Person(Person.WOMAN);
          Thing b1=new Secret(Secret.MARKER); addThing(b1,x1,y1);
          Thing b2=new Secret(Secret.MARKER); addThing(b2,x2,y2);
          p.setAI(new GuardAI(b1,b2));
          addThing(p,x1,y1,x2,y2);
          int tx=RPG.rspread(x1+1,x2-1);
          int ty=RPG.rspread(y1+1,y2-1);
          addThing(GameScenery.create("table"),tx,ty);
          addThing(GameScenery.create("stool"),tx+1-2*RPG.r(2),ty);
          addThing(GameScenery.create("stool"),tx,ty+1-2*RPG.r(2));
          addThing(Food.createFood(),x1,y1,x2,y2);
          addThing(Food.createFood(),x1,y1,x2,y2);
        }
        break;
      
      case 3:
        if ((w>3)&&(h>=3)) {
          Person p=new Person(RPG.pick(new int[] {Person.WIZARD,Person.PRIEST, Person.TEACHER}));
          Thing b1=new Secret(Secret.MARKER); addThing(b1,x1,y1);
          Thing b2=new Secret(Secret.MARKER); addThing(b2,x2,y2);
          p.setAI(new GuardAI(b1,b2));
          addThing(p,x1,y1,x2,y2);
          int tx=RPG.rspread(x1+1,x2-1);
          int ty=RPG.rspread(y1+1,y2-1);
          addThing(GameScenery.create("table"),tx,ty);
          addThing(GameScenery.create("stool"),tx+1-2*RPG.r(2),ty+1-2*RPG.r(2));
          addThing(Lib.createMagicItem(3),x1,y1,x2,y2);
          addThing(Lib.createMagicItem(3),x1,y1,x2,y2);
          addThing(Scroll.createScroll(6),x1,y1,x2,y2);
          addThing(Scroll.createScroll(8),x1,y1,x2,y2);
        }
        break;
      case 4:
        if ((w>3)&&(h>=3)) {
          fillArea(x1+1,y1+1,x2-1,y2-1,Tile.STREAM);
        }         
        break;
    }
  }
  
  public void decorateTown(int x1,int y1,int x2,int y2) {
    for (int x=x1; x<=x2; x++) for (int y=y1;y<=y2; y++) {
      if (isBlocked(x,y)) continue;
      if (!(getTile(x,y)==floortile)) continue;
      else {
        switch(RPG.d(200)) {
          case 1: case 2: case 3:  
            addThing(GameScenery.create(4),x,y);
            break;
        }
      }  
    }
  }

  // can exit automatically if town friendly
  public boolean canExit() {
    return (!isAngry())?true:super.canExit(); 
  }

  public String getEnterMessage() {
    return "You stand in the middle of a small town";  
  }
  
  // Level name for display on status panel
  public String getLevelName() {
    return isAngry()?"Angry small town":"Small town";  
  }
  
}