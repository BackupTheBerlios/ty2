package rl;

import java.io.*;

// The personality base class

public class Personality implements Talkable, Serializable {
  // personalities
  public static final int CHATTER=1;
  public static final int TEACHER=2;
  
  // chatter subtypes
  public static final int CHATTER_TOWNIE=0;
  public static final int CHATTER_GUARD=1;
  public static final int CHATTER_WISE=2;
  public static final int CHATTER_PLEASANT=3;
  public static final int CHATTER_GOODLY=4;
  public static final int CHATTER_ADVENTURER=5;
  public static final int CHATTER_GOBLIN=6;
  public static final int CHATTER_ORC=7;
  
  private int type;
  private int subtype;
  private int state;
  
  protected static final String[][] chattercomments = {
    // normal townie 
    {"Nice day, innit?", "Bugger off.", "There be evil beasties in that forest!", "Mark my words, eh?", "Arrr?", "What is you wantin'?","You be wantin' some gruel?", "There'll be much drinkin' at the inn tonight!"},
    // guard 
    {"You better watch yerself!","Don't mess with me!","They say I'm a nooligan.","I be eatin' dragon steak for breakfast.","I'm 'ard.","Don't you go causin' no trouble.","I'm the toughest guard in town","Wanna fight?","Ra! I'm the mightiest hero in the world!"},
    // wise words  
    {"Mithril is light!","Eat mushrooms for beauty and strength!","Don't play with fire!","Never step on strange runes.","Always wear tough boots!","You will find great beauty in the darkest places"},
    // pleasant chat
    {"Hello!","Hi there!","So what's it like being an adventurer?","Don't go near the forest.... it's dangerous!","That shopkeeper is a real stingy villain.","Meet me later at the inn."},
    // goodly priest
    {"Peace be with you!","Seek the healing waters of Aramis!","May the blessings of Aramis be upon you.","Greetings, my child.","Let us pray for peace in these troubled times."},
    // adventurer banter
    {"Greetings friend","Let us swap tales in the tavern, my friend.", "They say the Eastern Isles are the most wondrous of all places.","'Tis a great day to wander wild and free!","They say the forests are beautiful this time of year.","'Tis always best to travel light and free."},
    // goblin
    {"Hello hooman.","Is you wanting da mushrooms?", "You is fick.", "Wot is you wanting?", "Gimme da mushrooms, hooman.", "Der is gold in dem hills.","Gargash is da tuffest.","He He.","Orcs are scary.","I is tuffer dan a B'Zekroi Lord!","Is good day for findin' mushrooms","'oomans are yucky","i like huntin' in da forests"},
    // orc
    {"'Ooman.","**BELCH**", "You fick.", "You ugly.", "You stooopid.", "Ug.", "Want fooood. Now.", "**GROWL**"}
  };

  public Personality(int t) {
    type=t; 
  }
  
  public Personality(int t, int st) {
    this(t);
    subtype=st;
    switch(type) {
       
    } 
  }  

  public int offerTeaching(Thing t) {
    if (!(t instanceof Being)) return 0;
    
    Being b=(Being) t;
    Being h=Game.hero;
    
    String[] options=Skill.getTrainableSkills(h,b);
    
    String choice=Game.selectString("Choose a skill to train:",options);
    
    if (choice==null) return 0;
    
    return Skill.increaseSkill(h,choice,1);    
  }

  public void talk(Thing t) {
    talk(t,type); 
  }
  
  public void talk(Thing t, int p) {
    Hero h=Game.hero;
    try { 
      switch (p) {
        case CHATTER:
          Game.message(chattercomments[subtype][RPG.r(chattercomments[subtype].length)]); 
          return;        
        
        case TEACHER:
          if (Skill.getTrainableSkills(h,(Being)t).length>0) {
            Game.message("I see you have potential");
            Game.message("Would you like to enhance your skills? (y/n)");
            if (Game.getOption("yn")=='y') {
              offerTeaching(t);
            } else {
              Game.message("Fare thee well!"); 
            }
          } else {
            Game.message("There is nothing I can teach you now."); 
          }
          return;
          
        default: 
          Game.message("You get no response."); 
      } 
    } catch (Exception e) {
      e.printStackTrace(); 
      Game.message("Mumble mumble mumble"); 
    }
  }
  
}