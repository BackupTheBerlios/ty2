package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class ArtScreen extends Screen {
	String title="List";
	Art[] arts=null;
	static int page=0;
	
	private static int pagesize=13;
	private static int lineheight=20;
	
  public Art getArt() {
    while (true) {
      KeyEvent k=Game.getInput();
      
		if (k.getKeyCode()==k.VK_ESCAPE){ return null; }
		  
		  char c=Character.toLowerCase(k.getKeyChar());
		  
		  if ((c=='p')&&(page>0)) {
		    page--;
		    repaint();	
		  }

		  if ((c=='n'||c==' ')&&(((page+1)*pagesize)<arts.length)) {
		    page++;
		    repaint();	
		  }
		  
		  int kv = (int)c - (int)'a';
		  if ((kv>=0)&&(kv<pagesize)) {
		    kv=kv+page*pagesize;
		    if ((kv>=0)&&(kv<arts.length)){ return arts[kv]; }
      }
		}
	}
	
  public ArtScreen(String s,Art[] thearts) {
    super();
    setLayout(new GridLayout(15,1));	

    setForeground(QuestApp.infotextcolor);  
    setBackground(QuestApp.infoscreencolor);
	  setFont(QuestApp.mainfont);
	
	  // get the things
	  arts=thearts;
	  if (arts==null){ arts=new Art[0]; }
	  
	  // set the list title
	  title=s;
	}
	
	public void paint(Graphics g) {
		if ((page*pagesize)>arts.length){ page=0; }
		
		g.drawString(title,20,1*lineheight);
		
	  for(int i=0; i<Math.min(arts.length-(page*pagesize),pagesize); i++) {
			Art t=arts[i+page*pagesize];
			String s="["+(char)((int)'a'+i)+"] "+t.getName();

			g.drawString(s,50,lineheight*(i+2));
		}
		
		String bottomstring="ESC=Cancel ";
 		if (page>0){  bottomstring=bottomstring+" P=Previous "; }
 		if (((page+1)*pagesize)<arts.length){ bottomstring=bottomstring+" N=Next "; }
		String powerstring="Power: "+Game.hero.getStat(RPG.ST_MPS)+" / "+Game.hero.getStat(RPG.ST_MPSMAX);
		g.drawString(Text.centrePad(bottomstring,powerstring,55),20,getSize().height-10);
	}
	
}