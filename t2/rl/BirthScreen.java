package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BirthScreen extends Screen
{

	static int status                      = 0;
	static int choice                      = 0;
	String title                           = "Character Creation Screen";
	String pname                           = "";
	String prace                           = "";
	String pclass                          = "";

	private static int lineheight          = 20;
	private final static int status_name   = 0;
	private final static int status_race   = 1;
	private final static int status_class  = 2;
	private final static int status_sign   = 3;
	private final static String[] races    = {"human", "hobbit", "dwarf", "high elf", "wood elf", "dark elf", "gnome", "orc", "goblin", "troll"};
	private final static String[] classes  = {"fighter", "fighter-mage", "sorceror", "runecaster", "priest", "bard", "warrior-priest", "barbarian", "ranger", "enchanter", "thief", "avatar"};

	public BirthScreen()
	{
		setLayout(new GridLayout(15, 1));
		setBackground(new Color(150, 75, 20));
		setFont(QuestApp.mainfont);
		title = "Character Creation Screen";
	}

	public Object getInfo()
	{
		boolean end  = false;
		while(!end)
		{
			KeyEvent k  = Game.getInput();
			if(k != null)
			{
				if(k.getKeyCode() == k.VK_ESCAPE)
					return null;
				char c  = Character.toLowerCase(k.getKeyChar());

				switch (status)
				{
						case status_name:
						{
							if(k.getKeyCode() == k.VK_ENTER)
							{
								choice = 0;
								status = status_race;
							}

							if(k.getKeyCode() != k.VK_BACK_SPACE)
							{
								if(Character.isWhitespace(c) || Character.isLetterOrDigit(c))
									pname = pname + k.getKeyChar();
							}
							else
								try
								{
									pname = pname.substring(0, pname.length() - 1);
								}
								catch(Exception e)
								{
								}

							repaint();
						}
							break;
						case status_race:
						{
							if(k.getKeyCode() == k.VK_ESCAPE)
								status = status_name;

							if(k.getKeyCode() == k.VK_DOWN)
								choice++;

							if(k.getKeyCode() == k.VK_UP)
								choice--;

							if(choice < 0)
								choice = races.length - 1;

							if(choice == races.length)
								choice = 0;

							int kv  = (int)c - (int)'a';
							if((kv < races.length && kv > 0) || k.getKeyCode() == k.VK_ENTER)
							{
								if(k.getKeyCode() == k.VK_ENTER)
									kv = choice;

								status = status_class;
								prace = races[kv];
								choice = 0;
							}
							repaint();
						}
							break;
						case status_class:
						{
							if(k.getKeyCode() == k.VK_ESCAPE)
							{
								choice = 0;
								status = status_name;
							}

							if(k.getKeyCode() == k.VK_DOWN)
								choice++;

							if(k.getKeyCode() == k.VK_UP)
								choice--;

							if(choice < 0)
								choice = classes.length - 1;

							if(choice == classes.length)
								choice = 0;

							int kv  = (int)c - (int)'a';
							if((kv < races.length && kv > 0) || k.getKeyCode() == k.VK_ENTER)
							{
								if(k.getKeyCode() == k.VK_ENTER)
									kv = choice;

								status = status_sign;
								pclass = classes[kv];
								choice = 0;
								return new Hero(pname, prace, pclass);
							}

							repaint();
						}
							break;
				}

			}
		}
		return null;
	}


	public void activate()
	{
		// questapp.setKeyHandler(keyhandler);
	}


	public void paint(Graphics g)
	{
		Dimension d  = getSize();
		//Put the title
		g.drawString(title, 20, 1 * lineheight);
		//Draw the name of the player
		g.setColor(QuestApp.textcolor);
		g.drawString("Name", 20, 3 * lineheight);
		g.setColor(QuestApp.trueblue);
		g.drawString(pname, 60, 3 * lineheight);

		if(status == status_race)
		{
			//Draw the race of the player
			g.setColor(QuestApp.textcolor);
			g.drawString("Race", 20, 4 * lineheight);
			g.setColor(QuestApp.trueblue);
			g.drawString(races[choice], 60, 4 * lineheight);
			//Draw the different races
			for(int i = 0; i < races.length; i++)
			{
				//Put letters in front of the choice
				String s  = "[" + (char)((int)'a' + i) + "] " + races[i];
				//Highlight the current choice
				if(i == choice)
					g.setColor(QuestApp.texthighlight);

				else
					g.setColor(QuestApp.textcolor);

				g.drawString(s, 50, lineheight * (i + 5));
			}
		}

		if(status == status_class)
		{
			//Draw the race of the player
			g.setColor(QuestApp.textcolor);
			g.drawString("Race", 20, 4 * lineheight);
			g.setColor(QuestApp.trueblue);
			g.drawString(prace, 60, 4 * lineheight);
			//draw the class of the player
			g.setColor(QuestApp.textcolor);
			g.drawString("Class", 20, 5 * lineheight);
			g.setColor(QuestApp.trueblue);
			//Draw the different classes
			g.drawString(classes[choice], 60, 5 * lineheight);
			for(int i = 0; i < classes.length; i++)
			{//Put letters in front of the choices
				String s  = "[" + (char)((int)'a' + i) + "] " + classes[i];
				//Highlight the current choices
				if(i == choice)
					g.setColor(QuestApp.texthighlight);

				else
					g.setColor(QuestApp.textcolor);

				g.drawString(s, 50, lineheight * (i + 6));
			}
		}
	}

}

