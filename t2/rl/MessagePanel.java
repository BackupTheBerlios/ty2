//
// Panel for displaying game status messages at the botton of the screen
//

package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MessagePanel extends TPanel {
	//private String text="";
	
//	public MessagePanel() {
//	  super("",0,0,SCROLLBARS_VERTICAL_ONLY);	
//	  setEditable(false);
//	  setForeground(QuestApp.textcolor);
//	  setBackground(QuestApp.backcolor);
//	  setFont(QuestApp.mainfont);
//	  Game.messagepanel=this;
//	}
	
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
    
//    try {
//      setCaretPosition(newtext.length()-1);
//    } catch (Exception e) {}
  }
	
	//public void append(String s){
	//  text=text+s;	
	//  repaint();
	//}
	
	//public void setText(String s) {
	//	text=s;
	//	repaint();
	//}
	
	public Dimension getPreferredSize() {
		return new Dimension(500,80);
	}


}