package rl;

public class PowderKeg extends Item {
	private static final Description DESC_KEG=
		new Describer("powder keg","A keg of black powder.");

	public PowderKeg() {
	}
	
	public int getWeight() {return 8000;}
	
  public int damage(int dam, int damtype) {
    Map map=getMap();
    int tx=getMapX();
    int ty=getMapY();
    if ((map!=null)&&(dam>=RPG.d(8))) {
      remove();
      if (map.isVisible(tx,ty)) Game.message("Kaboom!");
      new Spell(Spell.BLAZE).castAtLocation(null,map,tx,ty);
      return dam;
    }
    return 0; 
  }
  
  public int getImage() {return 263;}
  
	public Description getDescription() {return DESC_KEG;}
}