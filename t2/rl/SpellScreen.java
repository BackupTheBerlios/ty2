package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class SpellScreen extends Screen {
	String title="List";
	Spell[] spells=null;
	static int page=0;
	
	private static int pagesize=13;
	private static int lineheight=20;
	
  public Spell getSpell() {
    while (true) {
      KeyEvent k=Game.getInput();
      
		  if (k.getKeyCode()==k.VK_ESCAPE) return null;
		  
		  char c=Character.toLowerCase(k.getKeyChar());
		  
		  if ((c=='p')&&(page>0)) {
		    page--;
		    repaint();	
		  }

		  if (c=='n'||c==' ') {
		    page++;
		    repaint();	
		  }
		  
		  int kv = (int)c - (int)'a';
		  if ((kv>=0)&&(kv<pagesize)) {
		    kv=kv+page*pagesize;
		    if ((kv>=0)&&(kv<spells.length)) return spells[kv];
      }
		}
	}
	
	public SpellScreen(String s,Art[] thespells) {
	  super();
	  setLayout(new GridLayout(15,1));	
    setForeground(QuestApp.infotextcolor);  
    setBackground(QuestApp.infoscreencolor);

	  setFont(QuestApp.mainfont);
	
	  // allocate array
	  if (thespells==null) spells=new Spell[0];
    else spells=new Spell[thespells.length];
	  
    for (int i=0; i<thespells.length; i++) spells[i]=(Spell)thespells[i];
    
	  // set the list title
	  title=s;
	}
	
	public void paint(Graphics g) {
		if ((page*pagesize)>spells.length) page=0;
		
		g.drawString(title,20,1*lineheight);
		
	  for(int i=0; i<Math.min(spells.length-(page*pagesize),pagesize); i++) {
			Spell t=spells[i+page*pagesize];
			String s="["+(char)((int)'a'+i)+"] "+t.getName();

      String ss="( +"+(t.level-t.getDifficulty())+" , "+t.cost+" )";

			g.drawString(s,50,lineheight*(i+2));
			g.drawString(ss,260,lineheight*(i+2));
		}
		
		String bottomstring="ESC=Cancel ";
 		if (page>0) bottomstring=bottomstring+" P=Previous ";
 		if (((page+1)*pagesize)<spells.length) bottomstring=bottomstring+" N=Next ";
		String powerstring="Power: "+Game.hero.getStat(RPG.ST_MPS)+" / "+Game.hero.getStat(RPG.ST_MPSMAX);
		g.drawString(Text.centrePad(bottomstring,powerstring,55),20,getSize().height-10);
	}
	
}