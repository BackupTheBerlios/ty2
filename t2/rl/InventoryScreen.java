package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryScreen extends Screen {
  SelectCommand selectcommand;
	String title="List";
	Thing[] things=null;
	static int page=0;
	
	private static final int pagesize=12;
	private static final int lineheight=20;
	
	KeyAdapter keyhandler = new KeyAdapter () {
		public void keyPressed(KeyEvent k) {
		  if (k.getKeyCode()==k.VK_ESCAPE) select(null);
		  if (k.getKeyCode()==k.VK_SPACE) select(null);
		  
		  char c=Character.toLowerCase(k.getKeyChar());

		  if ((c=='p'||c=='-')&&(page>0)) {
		    page--;
		    repaint();	
		    return;	
		  }

		  if ((c=='n'||c=='+')&&(((page+1)*pagesize)<things.length)) {
		    page++;
		    repaint();
		    return;	
		  }

		  int kv = (int)c - (int)'a';
		  if ((kv<0)||(kv>=pagesize)) return;
		  kv=kv+page*pagesize;
		  if ((kv>=0)&&(kv<things.length)) select(things[kv]);
		}
	};
	
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

        if ((c=='n'||c=='+'||c==' ')&&(((page+1)*pagesize)<things.length)) {
          page++;
          repaint();
        }

        int kv = (int)c - (int)'a';
        if ((kv>=0)&&(kv<pagesize)) {
          kv=kv+page*pagesize;
          if ((kv>=0)&&(kv<things.length)) return things[kv];
        }
      }
    }
    return null;    
  }
  
	public void select(Object ob) {
    getParent().remove(this);
	  selectcommand.select(ob);	
	}
	
  public InventoryScreen(String s,Thing[] invthings) {
    super();
    setLayout(new GridLayout(15,1));  
    
    setBackground(QuestApp.infoscreencolor);
    setForeground(QuestApp.infotextcolor);
    
    setFont(QuestApp.mainfont);
  
    // get the things
    things=invthings;
    if (things==null) things=new Thing[0];
    
    // set the list title
    title=s;
  }

	public InventoryScreen(String s,SelectCommand sel,Thing[] invthings, QuestApp qapp) {
	  super();
	  setLayout(new GridLayout(15,1));	
	  selectcommand=sel;
	  setBackground(new Color(150,75,20));
	  
	  setFont(QuestApp.mainfont);
	
	  // get the things
	  things=invthings;
	  if (things==null) things=new Thing[0];
	  
	  // set the list title
	  title=s;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if ((page*pagesize)>things.length) page=0;
		
		g.drawString(title,20,1*lineheight);
		
	  for(int i=0; i<Math.min(things.length-(page*pagesize),pagesize); i++) {
			Thing t=things[i+page*pagesize];
			String s="["+(char)((int)'a'+i)+"] "+t.getFullName();
		  String ws=Lib.wieldDescription(t.y);
		  if (ws!=null) ws="("+ws+")"; else ws="";
		  s=Text.centrePad(s,ws,50);
		  s=s+Text.centrePad("  ",t.getWeight()/100+"s",7);
			g.drawString(s,50,lineheight*(i+2));
      if (t instanceof Item) {
        int image=t.getImage();
        int sx=(image%20)*MapPanel.TILEWIDTH;
        int sy=(image/20)*MapPanel.TILEHEIGHT;
        int px=30;
        int py=lineheight*(i+1)+8;	
        g.drawImage(QuestApp.items,px,py,px+MapPanel.TILEWIDTH,py+MapPanel.TILEHEIGHT,sx,sy,sx+MapPanel.TILEWIDTH,sy+MapPanel.TILEHEIGHT,null);
      }
		}
		
		String bottomstring="ESC=Cancel ";
 		if (page>0) bottomstring=bottomstring+" P=Previous ";
 		if (((page+1)*pagesize)<things.length) bottomstring=bottomstring+" N=Next ";
		String weightstring="Weight: "+Game.hero.inv.getWeight()/100+" / "+Game.hero.getStat(RPG.ST_MAXWEIGHT)/100+"s";
		g.drawString(Text.centrePad(bottomstring,weightstring,60),20,getSize().height-10);
	}
	
 /* public void addItem(Object o, String s, image i) {
  	ListItem li=new ListItem(o,s,i));
    li.addMouseListener(new MouseAdapter() {
    	public void mouseClicked(MouseEvent e) {
		    getParent().remove(thislist);
	   	  selectcommand.select(o);	
    	}
    });
    add(li);  	
  }	*/
}