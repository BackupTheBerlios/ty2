package rl;

public class Drink extends ItemType {
  protected int desc;
	
  private static final Description[] descriptions=
     {new Describer("bottle of wine","bottles of wine","A bottle of wine. Good vintage.")
     ,new Describer("keg of beer","kegs of beer","A keg of strong beer. It seems a lusty brew.")
     };

  public static final Drink WINE=     new Drink(223,0);
  public static final Drink BEERKEG=  new Drink(263,1);
	
  public Drink(int im, int d) {
    image=im;
    desc = d; 
  }
   
  public static TypeStack createDrink(int f) {
    if (f==0) f=RPG.d(2);
		
    switch (f) {
      case 1:
        return new TypeStack(WINE,1); 
      case 2:
        return new TypeStack(BEERKEG,1); 
      default:
        throw new Error("Drink type not recognised");
    }
  }
	
  public int getUse(TypeStack s) {return Thing.USE_NORMAL|Thing.USE_DRINK;}
	
  public void use(TypeStack s, Being user) {
    use(s,user,Thing.USE_DRINK);	
  }
	
	public int getWeight(TypeStack s) {return 750;}
	
	public void use(TypeStack s, Being user, int usetype) {
    boolean ishero=(user==Game.hero);
    
    if (usetype!=Thing.USE_DRINK) return;

    Thing t=s.remove(1);

    if (ishero) {
		  Game.message("You drink "+t.getTheName());
		  Game.message("Very refreshing");
    }
    user.aps-=500;
    
    if ((s.type==WINE)||(s.type==BEERKEG)) {
      user.mps=RPG.max(user.mps-RPG.d(2,6),0);
      if (ishero) Game.message("You feel a little giddy");
    }
  }
	
	public Description getDescription(TypeStack s) {return descriptions[desc];}
}