package rl;

public class ArtList extends Object implements Cloneable, java.io.Serializable {
  private Art[] arts;
  private int size=0;
  private int count=0;
  
  public Art[] getArts() {
    if (count==0) return new Art[0];
    Art[] result = new Art[count];
    System.arraycopy(arts,0,result,0,count);
    return result;    	
  }

  // gets all arts of a particular class
  public Art[] getArts(Class c) {
    Object[] objects=RPG.subList(getArts(),c); 
    Art[] temp=new Art[objects.length];
    System.arraycopy(objects,0,temp,0,objects.length);
    return temp;      
  }
  
  public Skill getSkill(int s) {
    for (int i=0; i<count; i++) {
      if ((arts[i] instanceof Skill)&&(((Skill)arts[i]).getStatCode()==s)) {
        return (Skill)arts[i]; 
      }
    }
    return null; 
  }
  
  // deep copy method
  public Object clone() {
    ArtList a;
    try {
      a=(ArtList)super.clone(); 
    } catch (Exception e) { throw new Error("ArtList clone error");}  
    if (arts!=null) {
      a.arts=(Art[])a.arts.clone();
      for (int i=0; i<count; i++) {
        a.arts[i]=(Art)a.arts[i].clone();
      }
    }
    return a;
  }

  public Art getArt(int i) {
    return ((i>=0)&(i<count))?arts[i]:null;
  }

  public void ensureSize (int s) {
    if (s<=size) return;
    s=s+3;
    Art[] newarts=new Art[s];
    if (arts!=null) System.arraycopy(arts,0,newarts,0,count);
    arts=newarts;
    size=s;
  } 
	
  public int getModifier(int s) {
    int t=0;
    for (int i=0; i<count; i++) {
      t=t+arts[i].getModifier(s);
    } 
    return t;
  }
  
  // add art, returns reference to added art
	public Art addArt(Art newart) {
	  if (newart==null) return null;
	  boolean found=false;
	  for (int i=0; i<count; i++) {
	    if (arts[i].compare(newart)) return arts[i];
	  }
    ensureSize(count+1);
    arts[count]=newart;
    count=count+1;
    return newart;
  }
}