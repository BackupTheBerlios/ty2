package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class ListScreen extends Screen {
	String title="List";
	String[] strings=null;
	static int page=0;
	Object result;
  
	private static int pagesize=12;
	private static int lineheight=20;
	
  public Object getObject() {
    boolean end=false;
    while (!end) {
      KeyEvent k=Game.getInput();
      if (k!=null) {
        if (k.getKeyCode()==k.VK_ESCAPE) return null;
      
        char c=Character.toLowerCase(k.getKeyChar());

        if ((c=='p'||c=='-')&&(page>0)) {
          page--;
          repaint();  
        }

        if ((c=='n'||c=='+'||c==' ')&&(((page+1)*pagesize)<strings.length)) {
          page++;
          repaint();
        }

        int kv = (int)c - (int)'a';
        if ((kv>=0)&&(kv<pagesize)) {
          kv=kv+page*pagesize;
          if ((kv>=0)&&(kv<strings.length)) return strings[kv];
        }
      }
    } 
    return null;
  }

  public void activate() {
	  // questapp.setKeyHandler(keyhandler);
  }
	
  public ListScreen(String s,String[] invstrings) {
    setLayout(new GridLayout(15,1));  
    setBackground(new Color(150,75,20));
    setFont(QuestApp.mainfont);
  
    // get the strings
    strings=invstrings;
    if (strings==null) strings=new String[0];
    
    // set the list title
    title=s;
  }

	public void paint(Graphics g) {
		if ((page*pagesize)>strings.length) page=0;
		
		Dimension d = getSize();
    
    g.drawString(title,20,1*lineheight);
		
	  for(int i=0; i<Math.min(strings.length-(page*pagesize),pagesize); i++) {
			String t=strings[i+page*pagesize];
			String s="["+(char)((int)'a'+i)+"] "+t;
			g.drawString(s,50,lineheight*(i+2));
		}
		
		String bottomstring="ESC=Cancel ";
 		if (page>0) bottomstring=bottomstring+" P=Previous ";
 		if (((page+1)*pagesize)<strings.length) bottomstring=bottomstring+" N=Next ";
    g.drawString(bottomstring,20,d.height-20);
	}
	
}