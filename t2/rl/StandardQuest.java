// This is the root object for all Quests that can be assigned to the hero
//
// Use as follows:
//   Game.hero.addThing(new Quest(Quest.SOMEQUEST));

package rl;


public class StandardQuest extends Quest {
  public int type;
  
  public int phase=0;
  
  // names of the quests, access through questName()
  private static final String[] names={"null","emerald sword","bandit caves"};
  
  public static final int EMERALDSWORD=1;
  public static final int BANDITCAVES=2;
  
  public static final String[][] orders = 
  {  null,
    {"Retrieve the legendary Emerald Sword from the ruin in the deep forest.","Take the Emerald Sword back to the small town to collect your reward.","You successfully retrieved The Emerald Sword from the forest ruin and collected your reward."},
    {"Journey to the bandit caves and kill their villainous leader","Find and kill the bandit leader in his lair","You defeated the Bandits of the Western Caves"},
    {""},
  };
  
  
  public String questName() {
    return names[type]; 
  }
  
  // set up a standard quest
  public StandardQuest(String s) {
    super();
    type=Text.index(s,names);
  }

  public String questString() {
    try {
      return orders[type][phase]; 
    } catch (Exception e) {
      // this shouldn't happen, but if it does....
      return "You must cut down the tallest tree in the forest with - a herring!";
    }
  }
  
// used to be able to query quest state through getStat
//   seems rather unnecessaey
//
//  public int getStat(int s) {
//    if (s==RPG.ST_QUESTNUMBER) return type; 
//    if (s==RPG.ST_QUESTSTATE) return queststate; 
//    return super.getStat(s);
// }
  
  public void action (int t) {
    Hero h=Game.hero;
    
    switch (type) {
      case EMERALDSWORD: 
        if ((phase==0)&&h.hasItem("The Emerald Sword")) {
          phase++;
          Game.message(questString());
        }
        if ((phase==1)&&(h.place instanceof Town)&&h.hasItem("The Emerald Sword")) {
          phase++;
          setState(COMPLETE);

          Game.infoScreen("You return to the small town with the Emerald Sword\n"
            +"\n"
            +"It seems your deeds have already been noted. Sigmill, Master of the local Adventurers Guild, greets you and congratulates you upon your success. He pays you the reward of 500 gold for the ancient artifact.\n"
            +"\n"
            +"He also tells you of an abandoned mine somewhere in the hills to the West. Apparently a group of bandits are using it as a base, and falling upon travellers who pass that region. Kill their leader, and you will be rewarded with membership of the Adventurer's Guild.\n"
            +"\n"
            +"After passing on this information, Sigmill strides away quickly, clearly intent on some other important mission.\n"
            +"\n"
            +"\n[ Press space to continue ]"
          ); 
          h.addMoney(50000);
          
          h.stats.incStat(RPG.ST_SCORE,500);
          
          Thing es=h.inv.getContents("The Emerald Sword");
          es.remove();
          h.addQuest(new StandardQuest("bandit caves"));
          ((Town)h.place).entrance.target.getMap().addThing(new PlacePortal(PlacePortal.BANDITCAVES),7,11,9,12);
        }
        break;
      
      case BANDITCAVES: {
          if ((phase==0)&&(getTarget()!=null)) {
            //bandit hero created, ready to go!
            Game.infoScreen("You get the feeling that this place has been recently occupied.\n"
              +"\n"
              +"There are a few broken bottles lying on the ground, one of which is stained with blood. You also find the charred remains of a small fire and sniff the distinctive smell of tobacco smoke.\n"
              +"\n[ Press space to continue ]"
            ); 
            phase++; 
          }
          if ((phase==1)&&(getTarget().place==null)) {
            // bandit leader is dead!
            
            Game.infoScreen("You have defeated the evil bandit leader!\n"
              +"\n"
              +"Truly thou art a mighty hero, and all the world shall speak in awe of your deeds.\n"
              +"\n[ Press space to continue ]"
            ); 
            phase++;   
            setState(COMPLETE);
            
            h.stats.incStat(RPG.ST_SCORE,1000);
          }
        } 
        break;
        
      
      default:
        return;   
    }
  } 
}