//
// Graphical component to display an area of text
//

package rl;

import java.awt.*;

public class TextZone extends Component {
  public String text;
  public Font font;
  
  private int border=5;
  
  public TextZone() {
    this("");
  }
  
  public TextZone(String t) {
    text=t;
    font=QuestApp.mainfont;
    setFont(font);
  }
  
  public void setText(String s) {
    text=new String(s);	
  }
    
  public String getText() {
    return text;	
  }  
  
  public void paint(Graphics g) {
    FontMetrics met=g.getFontMetrics(g.getFont());
    
    g.setColor(QuestApp.infotextcolor);

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