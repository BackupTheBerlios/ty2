package rl;

// Objects in the game should implememt Active if
// they need to take an action based on elapsed time
//
// Since the action(int) function can get called a lot
// in a large map, these functions should be kept to a
// pretty short execution time. 


public interface Active {

  // This is the function to be called, along with the
  // number of elspsed ticks
  
  // Since time can vary considerably, actions should
  // take this into account. e.g. if you want to run an
  // event randomly with an average of 1000 ticks between
  // events then use:
  //
  //   public void action(int time) {
  //     int n=RPG.po(time,1000);
  //     for (int i=0; i<n; i++) {
  //       //Do the event in here
  //     }
  //   }
  //
  
	public void action(int time);
}