package rl;

import java.awt.*;

public class TPanel extends Panel {
  
  public TPanel() {
    super();
    setBackground(QuestApp.panelcolor);
    addKeyListener(Game.questapp.keyadapter);
  }
  
  public Image getTexture() {
    return QuestApp.paneltexture; 
  }
  
  Image buffer;

  // update with double buffering. 
  public void update(Graphics g) {
    // graphics context for the buffer
    Graphics bg; 
    
    // only build when needed
    if ((buffer==null) || (buffer.getWidth(this) != this.getSize().width) || (buffer.getHeight(this) != this.getSize().height)) {
      buffer = this.createImage(getSize().width, getSize().height);
    }

    bg = buffer.getGraphics();

    // paint the panel
    paint(bg);
    
    // call this to paint the sub-components
    super.paint(bg);
    g.drawImage(buffer, 0, 0, this);
  }

  public void paint(Graphics g) {
    Rectangle bounds=getBounds();
    int width=bounds.width;
    int height=bounds.height;

    Image texture=getTexture();
    if (texture!=null) {
      for (int lx=0; lx<bounds.width; lx+=texture.getWidth(null)) {
        for (int ly=0; ly<bounds.height; ly+=texture.getHeight(null)) {
          g.drawImage(texture,lx,ly,null); 
        } 
      }
    }
    // super.paint(g);
    
    g.setColor(QuestApp.panelhighlight);
    g.fillRect(0,0,width,2);
    g.fillRect(0,0,2,height);
    g.setColor(QuestApp.panelshadow);
    g.fillRect(1,height-2,width-1,2);
    g.fillRect(width-2,1,2,height-1);
    
  }
} 