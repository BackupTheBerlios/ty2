//
// Little placeholder class for the Hero AI
// This is just here for completeness.
// 
// Guess that if we wanted any auto-playing features 
// then they could go somewhere in here

package rl;

public class HeroAI extends BaseAI {
  public static final HeroAI instance=new HeroAI();
  
  public int notify(Mobile m, int eventtype, int ext, Object o) {
    // obviously don't respond to any events
    return 0;
  }
}