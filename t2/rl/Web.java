package rl;

public class Web extends Special {
	protected int image;
	protected int density;
    
	private static final Description DESC_WEB = 
		new Describer("web","A large spider web with sticky strands.");
	
  // new web of given density
  //   10 = weak
  //   25 = normal
  //   50 = strong
	public Web(int webdensity) {
	  image=48;
    density=webdensity;
	}
	
	public int damage(int dam, int damtype) {
    switch (damtype) {
      case RPG.DT_NORMAL: case RPG.DT_IMPACT: case RPG.DT_FIRE:
        if (dam>=RPG.r(4)) remove();
        return dam; 
      default:
        return 0;
    } 
  }
  
  public int getImage() {return image;}
	
	public int getZ() {return Z_ITEM;}
	
	public void trigger(Map map, int tx, int ty) {
    if (map==null) return;
    Being b=(Being)map.getObject(tx,ty,Being.class);
    if (b!=null) {
      if ((b instanceof Creature)&&(((Creature)b).creaturetype==Creature.SPIDER)) return;
      remove();
      b.addAttribute(new TemporaryAttribute("Webbed",100*density,new int[] {RPG.ST_MOVECOST,density,RPG.ST_ATTACKCOST,density},"You untangle yourself"));
    }
  }

  public Description getDescription() {return DESC_WEB;}
}