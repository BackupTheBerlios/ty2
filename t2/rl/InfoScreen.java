package rl;

import java.awt.*;
import java.awt.event.*;

public class InfoScreen extends Screen {
	public String text;
	public Font font;
	
	private int border=20;
	
	public InfoScreen(String t) {
    text=t;
    font=QuestApp.mainfont;
    setForeground(QuestApp.infotextcolor);  
    setBackground(QuestApp.infoscreencolor);
    setFont(font);
  }
  
	public void activate() {
		// questapp.setKeyHandler(keyhandler);
	}
	
  public void paint(Graphics g) {
    FontMetrics met=g.getFontMetrics(g.getFont());
    
    Rectangle r=getBounds();
    int charsize=met.charWidth(' ');
    
    int linelength; int y; int lineinc;
    {
      linelength = (r.width-border*2)/charsize;
      y=border+met.getMaxAscent();
      lineinc=met.getMaxAscent()+met.getMaxDescent();
    }
    		
	  String[] st=Text.separateString(text,'\n');
		
	  for (int i=0; i<st.length ;i++) { 
      String s=st[i];      
      
      String[] lines=Text.wrapString(s,linelength);
      
      for (int l=0; l<lines.length ; l++) {
      	String line=lines[l];
      	
      	g.drawString(line,border,y);
        y+=lineinc;
      }
		}
	}
}