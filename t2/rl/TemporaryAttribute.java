package rl;

public class TemporaryAttribute extends ActiveAttribute {
	public int timeleft;
  private int[] modifiers;
  private String attributestring="";
  private String endmessage;
  
  public TemporaryAttribute(int duration, int attribute, int modifier) {
    timeleft=duration;
    modifiers=new int[2];
    modifiers[0]=attribute;
    modifiers[1]=modifier;
  }

  public TemporaryAttribute(String name, int duration, int attribute, int modifier) {
    this(duration,attribute,modifier);
    attributestring=name;
  }
  
  public TemporaryAttribute(String name, int duration, int[] mods, String end) {
    timeleft=duration;
    attributestring=name;
    modifiers=mods;
    endmessage=end;
  }
  
  // called for each time period
  public void action(int time) {
    if (time>=timeleft) time=timeleft;
    
    doEffect(time);
    
    timeleft-=time;
    if (timeleft<=0) {
      if ((place==Game.hero)&&(endmessage!=null)) Game.message(endmessage);
      remove();
    }
  }

  // placeholder for effects
  public void doEffect(int time) {
    
  }	
  
  // return stat modifier
  //   method: loop through modifiers array
  public int getModifier(int s) {
    int length=modifiers.length;
    for (int i=0; i<length ; i=i+2) {
      if (modifiers[i]==s) return modifiers[i+1];
    } 
    return 0;
  }
  
  // return attribute description
  public String getAttributeString() {
    if (QuestApp.debug) {
      int s=modifiers[1];
      return attributestring+" {"+ ((s>0) ? "+"+s : ""+s) +"}";
    }
      
    return attributestring;
  }
}