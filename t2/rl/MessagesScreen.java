package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MessagesScreen extends Screen {
	String title="";
	String[] strings=null;

	Object result;
	
	private static int pagesize=12;
	private static int lineheight=20;
	private int page=0;
	
	public Object display() {
		boolean end=false;
		while (!end) {
			KeyEvent k=Game.getInput();
			if (k!=null) {
				if (k.getKeyCode()==k.VK_ESCAPE) return null;
			
				char c=Character.toLowerCase(k.getKeyChar());

				if ((c=='n'||c=='-')&&(page>0)) {
					page--;
					repaint();	
				}

				if ((c=='p'||c=='+'||c==' ')&&(((page+1)*pagesize)<strings.length)) {
					page++;
					repaint();
				}
			}
		} 
		return null;
	}

	public void activate() {
		// questapp.setKeyHandler(keyhandler);
	}
	
	public MessagesScreen(String s,String[] invstrings) {
		setLayout(new GridLayout(15,1));	
		setBackground(new Color(150,75,20));
		setFont(QuestApp.mainfont);

		setBackground(QuestApp.infoscreencolor);
		setForeground(QuestApp.infotextcolor);
	
		// get the strings
		strings=invstrings;
		if (strings==null) strings=new String[0];
		
		// set the list title
		title=s;
	}

	public void paint(Graphics g) {
		if ((page*pagesize)>strings.length) page=0;
		int ppage = (strings.length / pagesize) - page;

		Dimension d = getSize();
		
		g.drawString(title,20,1*lineheight);

		if ( strings.length - (ppage*pagesize) - 1 == -1){
			ppage--;
		}
		
		for( int i = Math.min(strings.length-(ppage*pagesize),pagesize) - 1; i>=0	; i--) {
			String t=strings[i+ppage*pagesize];
			g.drawString(t,50,lineheight*(pagesize-i+2));
		}
		
		String bottomstring="ESC=Cancel ";
 		if (ppage>0) bottomstring=bottomstring+" P=Previous ";
 		if (((ppage+1)*pagesize)<strings.length) bottomstring=bottomstring+" N=Next ";
		g.drawString(bottomstring,20,d.height-20);
	}
	
}