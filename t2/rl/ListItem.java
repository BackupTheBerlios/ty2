package rl;

import java.awt.*;

public class ListItem extends Panel {
	private int image;
	private String text;
	private Thing thing;
	
	public ListItem(Thing t) {
		super();
		text=t.getName();
		thing=t;
	}
	
	public void paint(Graphics g) {
	  super.paint(g);
	  g.drawString(text,32,getHeight());	
    //int sx=(image%20)*MapPanel.TILEWIDTH;
    //int sy=(image/20)*MapPanel.TILEHEIGHT;	
    //g.drawImage(QuestApp.items,0,0,16,16,sx,sy,sx+MapPanel.TILEWIDTH,sy+MapPanel.TILEHEIGHT,null);
	}	
}