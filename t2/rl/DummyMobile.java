package rl;

public class DummyMobile extends Being {
	private static final Description dummydesc =
		new Describer("evil critter","An obnoxious inhabitant of the dungeon");
	
  public Description getDescription() { return dummydesc;}
  
  public DummyMobile(String s, int i) {
    super();
    image=i;	
    hps=RPG.d(4);
  }
  
  public void die() {
  	if (place==null) return;
  	Map m=getMap();
    
    if (m.getObject(x,y,Decoration.class)==null)	{
    	m.addThing(new Decoration(Decoration.DEC_REDBLOOD),x,y);
    }
    if (RPG.sometimes())	{
    	m.addThing(StandardArmour.createArmour(0),x,y);
    }
    super.die();
  }
  	
	public void action() {
	  if (!(place instanceof Map)) throw new Error("Lost mobile....");
    Map m=(Map) place;
    Mobile h=Game.hero;
    	
	  while (aps>=0) {
	  
	    int nx=x-1+RPG.r(3);
	    int ny=y-1+RPG.r(3);
	  
	    if (RPG.often()) nx=x+RPG.sign(h.x-x);
	    if (RPG.often()) ny=y+RPG.sign(h.y-y);
	  
	    // move the mobile
	    if (!m.isBlocked(nx,ny)) moveTo(m,nx,ny);
	    aps=aps-100;
	  }	
	}
	
	public int getStat(int s) {
		switch (s) {
      case RPG.ST_ASK: return 4;			
      case RPG.ST_AST: return 4;			
      case RPG.ST_DSK: return 4;			
			default: return super.getStat(s);
		}
	}
}