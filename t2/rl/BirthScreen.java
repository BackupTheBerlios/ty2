package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BirthScreen extends Screen
{
	String title                         = "Character Creation Screen";
	String[] strings                     = null;
	static int page                      = 0;
	static int status                    = 0;
	static int choice                    = 0;
	Object result;
	String pname                         = "";
	String prace                         = "";
	String pclass                        = "";

	private static int pagesize          = 12;
	private static int lineheight        = 20;
	private static int status_name       = 0;
	private static int status_race       = 1;
	private static int status_class      = 2;
	private static int status_sign       = 3;
	private final static String[] races  = {"human", "hobbit", "dwarf", "high elf", "wood elf", "dark elf", "gnome", "orc", "goblin", "troll"};

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
				if(status == status_name)
				{
					if(k.getKeyCode() == k.VK_ENTER)
//						return (pname);
						status = status_race;

					if(k.getKeyCode() != k.VK_BACK_SPACE)
					{
						if(Character.isWhitespace(c) || Character.isLetterOrDigit(c))
							pname = pname + c;
					}
					else
					{
						try
						{
							pname = pname.substring(0, pname.length() - 1);
						}
						catch(Exception e)
						{
						}

						repaint();
					}

					if(status == status_race)
						repaint();
					if(status == status_class)
						repaint();
					repaint();
				}
			}
		}
		return null;
	}


	public void activate()
	{
		// questapp.setKeyHandler(keyhandler);
	}


	public BirthScreen()
	{
		setLayout(new GridLayout(15, 1));
		setBackground(new Color(150, 75, 20));
		setFont(QuestApp.mainfont);

		// set the list title
		title = "Character Creation Screen";
	}


	public void paint(Graphics g)
	{
		Dimension d  = getSize();
		g.drawString(title, 20, 1 * lineheight);
		//Draw the name of the player
		setForeground(new Color(128, 128, 128));
		g.drawString("Name", 20, 3 * lineheight);
		setForeground(new Color(0, 0, 128));
		g.drawString(pname, 60, 3 * lineheight);

		if(status == status_race)
		{
			//Draw the race of the player
			setForeground(new Color(128, 128, 128));
			g.drawString("Race", 20, 4 * lineheight);
			setForeground(new Color(0, 0, 128));
			g.drawString(prace, 60, 4 * lineheight);
			for(int i = 0; i < races.length; i++)
			{
				String s  = "[" + (char)((int)'a' + i) + "] " + races[i];
				if(i == choice)
					setForeground(new Color(128, 128, 128));
				else
					setForeground(new Color(128, 128, 128));
				g.drawString(s, 50, lineheight * (i + 5));
			}
		}

	}

}

