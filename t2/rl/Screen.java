package rl;

import java.awt.*;

public class Screen extends Panel {
	public QuestApp questapp;

  public Screen() {
    super();
    setFont(QuestApp.mainfont);
    setForeground(QuestApp.infotextcolor);  
    setBackground(QuestApp.infoscreencolor);
    questapp=Game.questapp;
    addKeyListener(questapp.keyadapter);
  }  
  
} 