// Class to manage names of creatures and characters

package rl;

public class Name extends Object implements Description, java.io.Serializable {
  public static final String[][] names=
    {{"The Tyrant"},
     {"Benn","Kiern","Rodrik","Glabe","Jobe","Mert","Janik","Andrin"},
     {"Sarra","Gail","Jeela","Ciina","Waydia","Kaydia","Faydia","Kassara","Jenn","Keri"},
     {"Rutlud","Gombag","Snogi","Grashnak","Gruttug","Gritnak","Gumnak","Shondag","Stugrat","Kretlig","Gromnark","Bitnak"},
     
     {}
    }; 
  
  
  private String name;
  private String job;
  private String title;

  public Name(int nametype) {
    name=names[nametype][RPG.r(names[nametype].length)]; 
  }
  
  public String getName(int number, int article) {
    return name; 
  }

  public String getDescriptionText() {
    if (job!=null) {
      return name+", "+job;
    } else {
      return name; 
    } 
  }  
  
  public String getPronoun(int number, int acase) {
    return ""; 
  }
   
}