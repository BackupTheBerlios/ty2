//
// A simple class to display the title image
//
package rl;

import java.awt.*;

public class TitleScreen extends Screen {
  
  public void paint(Graphics g) {
    super.paint(g);
    Image im=QuestApp.title;
    g.drawImage(im,(getBounds().width-im.getWidth(null))/2,(getBounds().height-im.getHeight(null))/2,null);
  	
  }

} 