//
// Panel for displaying game status messages at the botton of the screen
//

package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MessagePanel extends TPanel {
	
	private TextZone textzone=new TextZone();
	
	public MessagePanel() {
		super();
		setLayout(new BorderLayout());
		textzone.setBackground(QuestApp.panelcolor);
		textzone.setForeground(QuestApp.infotextcolor);
	 
		add("Center",textzone);
		Game.messagepanel=this;
	}
	
	public void setText(String s) {
		textzone.setText(s);
		repaint();
	}
	
	public String getText() {
		return textzone.getText();
	}
	
	public void addMessage(String s) {
		String t=getText();
		if (t.length()>2000) {
			setText(t.substring(t.length()-2000,t.length()));
		}
		
		String newtext=getText()+s;	
		setText(newtext);
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(500,80);
	}

	public void paint(Graphics g) {
		super.paint(g);
		textzone.paint(g);
	}


}