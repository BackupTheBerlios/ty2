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
	private ReaderOfRaces ror              = new ReaderOfRaces();
	private ReaderOfClasses roc            = new ReaderOfClasses();
	private Color chosenBlue               = new Color(51, 153, 255);

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
		//ReaderOfRaces ror  = new ReaderOfRaces();
		//System.out.println(ror.races.get(1));
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
		g.setColor(Color.GREEN);
		if(status == status_name)
			g.drawString("What is your name, adventurer ?", 20, 2 * lineheight);
		//Draw the name of the player
		g.setColor(Color.gray);
		g.drawString("Name", 20, 3 * lineheight);
		g.setColor(chosenBlue);
		if(status == status_name)
			g.drawString(pname.concat("_"), 60, 3 * lineheight);
		else
			g.drawString(pname, 60, 3 * lineheight);

		if(status == status_race)
		{
			g.setColor(Color.GREEN);
			g.drawString("What is your race, adventurer ?", 20, 2 * lineheight);
			//Draw the race of the player
			g.setColor(Color.GRAY);
			g.drawString("Race", 20, 4 * lineheight);
			g.setColor(chosenBlue);
			g.drawString(races[choice], 60, 4 * lineheight);
			//Draw the different races
			for(int i = 0; i < ror.races.size(); i++)
			{
				//Put letters in front of the choice
				String s  = "[" + (char)((int)'a' + i) + "] " + ror.races.get(i);
				//Highlight the current choice
				if(i == choice)
					g.setColor(Color.yellow);

				else
					g.setColor(Color.gray);

				g.drawString(s, 50, lineheight * (i + 5));
			}
			g.setColor(chosenBlue);
			String[] descs  = ((String)ror.descs.get(choice)).split("\n");
			for(int i = 0; i < descs.length; i++)
				g.drawString(descs[i], 20, lineheight * (ror.races.size() + 6 + i));
		}

		if(status == status_class)
		{
			g.setColor(Color.GREEN);
			g.drawString("What is your profession, adventurer ?", 20, 2 * lineheight);
			//Draw the race of the player
			g.setColor(Color.GRAY);
			g.drawString("Race", 20, 4 * lineheight);
			g.setColor(chosenBlue);
			g.drawString(prace, 60, 4 * lineheight);
			//draw the class of the player
			g.setColor(Color.GRAY);
			g.drawString("Class", 20, 5 * lineheight);
			g.setColor(chosenBlue);
			//Draw the different classes
			g.drawString(classes[choice], 60, 5 * lineheight);
			for(int i = 0; i < roc.classes.size(); i++)
			{//Put letters in front of the choices
				String s  = "[" + (char)((int)'a' + i) + "] " + roc.classes.get(i);
				//Highlight the current choices
				if(i == choice)
					g.setColor(Color.yellow);
				else
					g.setColor(Color.gray);

				g.drawString(s, 50, lineheight * (i + 6));
			}
			g.setColor(chosenBlue);
			String[] descs  = ((String)roc.descs.get(choice)).split("\n");
			for(int i = 0; i < descs.length; i++)
				g.drawString(descs[i], 20, lineheight * (roc.classes.size() + 6 + i));

		}
	}

}

