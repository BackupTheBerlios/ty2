package rl;

public class InfinitePortal extends Portal {
  public int tolevel;
  
  public InfinitePortal(int t,int lev) {
    super(t);
    tolevel=lev;
	}
	
  // go through the portal - create a new dungeon as needed
  public void travel(Thing t) {
    // use as normal portal if target dungeon already created
    // this is important for e.g. followers
    if ((t!=Game.hero)&&(getDestination()!=null)) {
      super.travel(t);
      return; 
    }
    
    // have we esacped the infinite dungeon?
    if (tolevel<1) {
      Portal outplace=((InfiniteDungeon)getMap()).wayout;
      setDestination(outplace);
      super.travel(t);
      return;
    }
    
    // remember out portal
    Portal p;
    if (getMap() instanceof InfiniteDungeon) {
      p=((InfiniteDungeon)getMap()).wayout;
    } else {
      p=this; 
    }
    
    try {
      InfiniteDungeon nextlevel=new InfiniteDungeon(tolevel,getImage()==1,p);
      Portal targ = (getImage()==1) ? nextlevel.entrance : nextlevel.exit;
      setDestination(targ);
      super.travel(t);
    } catch (Exception e) {
      Game.message("Failed to build target InfiniteDungeon"); 
    }
  }
}