// This is the application version of tyrant
// runs in special 'debug' mode by default

package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class QuestApplication {	
	public static void main(String args[]) {
  	Frame f = new Frame("Tyrant - The Adventure");
	  f.addWindowListener(new WindowAdapter() {
	  	public void windowClosing(WindowEvent e) {
  	    System.exit(0);
  	  }
    });
		
    f.setLayout(new BorderLayout());
    QuestApp q=new QuestApp();
    q.isapplet=false;
    
    // do we have a script to execute?
    // activate debug mode if we do....
    java.io.File script=new java.io.File("mikeradebug");
    
    // old method to activate debug cheat
    if (script.exists()) QuestApp.debug=true;
    
    
		f.add(q);
		q.init();
		f.setSize(640+10,480+50);
		f.show();	
    
	}    

}