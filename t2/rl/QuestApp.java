package rl;

// This is the main Applet class for Tyrant

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Vector;

public class QuestApp extends Applet implements Runnable {
	public final static String version              = "0.100";

	public static String imagesLocation             = "/xtra/images/";
	public static Image tiles;
	public static Image greytiles;
	public static Image scenery;
	public static Image creatures;
	public static Image items;
	public static Image effects;
	public static Image title;
	public static Image paneltexture;

	public static Font mainfont                     = new Font("Monospaced", Font.PLAIN, 12);
	public static int charsize;
	public final static Color textcolor             = new Color(192, 192, 192);
	public final static Color backcolor             = new Color(0, 0, 0);

	public final static Color panelcolor            = new Color(64, 64, 64);
	public final static Color panelhighlight        = new Color(96, 96, 96);
	public final static Color panelshadow           = new Color(32, 32, 32);

	public final static Color infoscreencolor       = new Color(0, 0, 0);
	public final static Color infotextcolor         = new Color(192, 192, 192);
//  public static final Color panelcolor=new Color(128,64,32);
//  public static final Color panelhighlight=new Color(208,80,48);
//  public static final Color panelshadow=new Color(80,32,16);

	public GameScreen screen;

	public static QuestApp questapp;
	public static boolean debug                     = false;
	public static boolean isapplet                  = true;

	// hero names used for search
	private final static String[] herostrings       = {"fighter", "fighter-mage", "sorceror", "runecaster", "priest", "bard", "warrior-priest", "barbarian", "ranger", "enchanter", "thief"};
	private final static String[] raceherostrings   = {"human", "hobbit", "dwarf", "high elf", "wood elf", "dark elf", "gnome", "orc", "goblin", "troll"};
	private final static String[] debugherostrings  = {"fighter", "fighter-mage", "sorceror", "runecaster", "priest", "bard", "warrior-priest", "barbarian", "ranger", "enchanter", "avatar"};

	// key handling stuff
	private KeyAdapter keyhandler;

	//Map resing stuff
	private Dimension oldSize;

	// All keypresses get directed here.....
	public final KeyAdapter keyadapter              =
		new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// call the currently registered keyhandler
				//if (keyhandler!=null) keyhandler.keyPressed(e);
				if(e.getID() == e.KEY_PRESSED) {
					Game.userinterface.go(e);
				}
			}
		};

	public Dimension getPreferredSize() {
		return new Dimension(640, 480);
	}

	// stop the applet, freeing all resources used
	public void stop() {
		super.stop();
		questapp = null;
		Game.messagepanel = null;
		Game.hero = null;
		Game.thread = null;
	}

	// inits the applet, loading all necessary resources
	// also kicks off the actual game thread
	public void init() {
		questapp = this;
		Game.questapp = this;

		mainfont = new Font("Courier", Font.PLAIN, 12);

		super.init();
		setLayout(new BorderLayout());
		setBackground(Color.black);
		setFont(mainfont);


		tiles = getImage(imagesLocation + "tiles.gif");
		scenery = getImage(imagesLocation + "scenery.gif");
		creatures = getImage(imagesLocation + "creature.gif");
		items = getImage(imagesLocation + "items.gif");
		effects = getImage(imagesLocation + "effects.gif");
		title = getImage(imagesLocation + "title.gif");
		paneltexture = getImage(imagesLocation + "texture1.gif");

		// Create mediatracker for the images
		MediaTracker mt  = new MediaTracker(this);
		mt.addImage(tiles, 1);
		mt.addImage(scenery, 1);
		mt.addImage(creatures, 1);
		mt.addImage(items, 1);
		mt.addImage(effects, 1);
		mt.addImage(title, 1);
		mt.addImage(paneltexture, 1);

		// create grey-filtered background tiles
		ImageFilter imf  = new GreyFilter();
		greytiles = createImage(new FilteredImageSource(tiles.getSource(), imf));

		// Wait for images to load
		try {
			mt.waitForID(1);
		} catch(Exception e) {
			System.out.println("Error loading images.");
		}

		// Add key listeners
		addKeyListener(keyadapter);

		// set game in action
		Game.userinterface = new Interface();
		Game.thread = new Thread(this);
		Game.thread.start();
	}

	// creates a hero according to specified parameters
	public Hero createHero(String hname) {
		String race        = null;

		if(true) {
			ListScreen ls  = new ListScreen("What race are you, " + hname + "?", raceherostrings);
			ls.setForeground(new Color(128, 128, 128));
			ls.setBackground(new Color(0, 0, 0));
			switchScreen(ls);
			while(race == null) {
				race = (String)ls.getObject();
			}
		} else {
			race = "human";
		}

		ListScreen ls      = new ListScreen("What is your profession, " + hname + "?", debug ? debugherostrings : herostrings);
		ls.setForeground(new Color(128, 128, 128));
		ls.setBackground(new Color(0, 0, 0));
		switchScreen(ls);

		String profession  = null;

		while(profession == null) {
			profession = (String)ls.getObject();
		}

		// randomise until correct hero found

		return new Hero(hname, race, profession);
	}

	// switches to a new screen, discarding the old one
	public void switchScreen(Component s) {
		if(s == null) {
			return;
		}
		removeAll();
		add("Center", s);
		s.addKeyListener(keyadapter);
		validate();
		repaint();
		requestFocus();

		if(s instanceof GameScreen) {
			((GameScreen)s).mappanel.viewPosition(Game.hero.x, Game.hero.y);
			((GameScreen)s).messagepanel.repaint();
		} else {
			s.repaint();
		}
	}

	public void switchOtherScreen(Component s) {
		if(s == null) { return;}
		screen.setVisible(false);
		add("Center", s);
		s.addKeyListener(keyadapter);
		validate();
		repaint();
		requestFocus();
	}

	public void switchBack(Component s) {
		if(s == null) { return;}
		remove(s);
		screen.setVisible(true);
		s.addKeyListener(keyadapter);
		validate();
		repaint();
		requestFocus();
		if( oldSize == null){
			oldSize = getSize();
		}else{
			if( ! oldSize.equals(getSize()) ){
				switchGameScreen();
				oldSize = getSize();
			}
		}
	}

	public void switchGameScreen() {
		removeAll();
		add(screen);
		validate();
		repaint();
		requestFocus();
	}

	// this is the actual game thread start
	// it loops for each complete game played
	public void run() {
		while(true) {
			if(!debug) {
				Screen ss        = new Screen();
				ss.setBackground(new Color(0, 0, 0));
				ss.setLayout(new BorderLayout());
				 {
					TitleScreen ts  = new TitleScreen();
					ts.setBackground(new Color(0, 0, 0));
					ss.add("Center", ts);
				}
				MessagePanel mp  = new MessagePanel();
				Game.messagepanel = mp;
				ss.add("South", mp);
				switchScreen(ss);

				// get hero name
				String hname     = Game.getLine("Enter your name: ");

				// set debug mode if necessary
				if(Text.pond(hname) == 2011275650) {
					debug = true;
					Game.message("We who are about to die, salute you!");
					Game.getInput();
				} else {
					// Game.message(Integer.toString(Text.pond(hname)));
					// Game.getInput();
					debug = false;
				}

				// do hero creation
				Game.hero = createHero(hname);

				// first display starting info....
				InfoScreen l     = new InfoScreen(
					"            Episode I: Quest for the Emerald Sword\n"
					 + "\n"
					 + "Times are hard for the humble adventurer. Lawlessness has ravaged the land, and few can afford to pay for your services.\n"
					 + "\n"
					 + "Fortunately, a piece of work has come your way that could turn your fortunes. A reward has been offered for the retrieval of an ancient artefact, the Emerald Sword.\n"
					 + "\n"
					 + "You hear rumours that the sword is hidden in the cellars of a ruined house in the forest to the north. Now all you need to do is dig up the sword and get away before your rivals turn up. Should be simple.....\n"
					 + "\n"
					 + "                 [ Press a key to continue ]\n"
					 + "\n"
					 + "\n"
					 + "\n"
					 + "\n"
					 + "\n");

				l.setForeground(new Color(128, 128, 128));
				l.setBackground(new Color(0, 0, 0));
				switchScreen(l);
				Game.getInput();
			} else {
				Game.hero = createHero("Gladiator");
			}
			gameStart();
		}
	}

	public void gameStart() {
		screen = new GameScreen();
		screen.mappanel.addKeyListener(keyadapter);
		screen.messagepanel.addKeyListener(keyadapter);

		switchScreen(screen);

		Game.over = false;

		// run the game
		screen.mainLoop();
	}

	// calculates the Hero's final score
	public int calcScore(Hero h) {
		int score  = 0;
		score = h.getStat(RPG.ST_SCORE);
		score += 2 * h.getStat(RPG.ST_LEVEL) * h.getStat(RPG.ST_LEVEL);
		score += 5 * h.getStat(RPG.ST_SCORE_BESTLEVEL) * h.getStat(RPG.ST_SCORE_BESTLEVEL);
		score += 3 * h.getStat(RPG.ST_SCORE_BESTKILL) * h.getStat(RPG.ST_SCORE_BESTKILL);
		score += h.getStat(RPG.ST_SCORE_KILLS) / 100;
		return score;
	}

	public void gameOver() {
		Hero h             = Game.hero;

		int sc             = calcScore(h);
		String score       = Integer.toString(sc);
		String level       = Integer.toString(h.getLevel());
		String seed        = Integer.toString(h.seed);
		String name        = h.name;
		String profession  = h.profession;
		String race        = h.race;

		String check       = Integer.toString((sc + name.length() * profession.length() * race.length()) ^ 12345678);

		String hresult     = "No high score available in debug mode";

		if(!debug) {
			try {
				hresult = "";
				URL u          = new URL("http://www.mikera.net/cgi-bin/hiscore.pl?client=tyrant&name=" + name + "&race=" + race + "&profession=" + profession + "&level=" + level + "&score=" + score + "&check=" + check + "&version=" + QuestApp.version + "&seed=" + seed);
				InputStream s  = u.openStream();
				int b          = s.read();
				while(b >= 0) {
					hresult = hresult + (char)b;
					b = s.read();
				}
			} catch(Exception e) {
				hresult = "High score feature not available";
			}
		}

		InfoScreen l       = new InfoScreen(
			"\n"
			 + "It's all over......\n"
			 + "\n"
			 + "You have failed in your adventures and died a hideous death.\n"
			 + "\n"
			 + "You reached level " + level + "\n"
			 + "Your score is " + score + "\n"
			 + "\n"
			 + hresult + "\n"
			 + "\n"
			 + "[ press ESC to try again ]\n"
			 + "\n"
			 + "\n");

		l.setForeground(new Color(255, 0, 0));
		l.setBackground(new Color(0, 0, 0));

		switchScreen(l);
		screen = null;
		Game.getInput();
		Game.over = true;
	}

	public void victory() {
		InfoScreen l  = new InfoScreen(
			"\n"
			 + "You emerge victorious!\n"
			 + "\n"
			 + "With the proceeds of this little adventure, you are able to live like a lord for several weeks and buy a sack of the finest adventuring gear. Your tale of heroism will be told around the local inns for many years.\n"
			 + "\n"
			 + "However, you get the feeling that this is only the beginning of something much, much greater......\n"
			 + "\n"
			 + "\n");

		l.setForeground(new Color(255, 255, 0));
		l.setBackground(new Color(0, 128, 0));

		switchScreen(l);
		screen = null;
		Game.getInput();
		Game.over = true;
	}

	public void chicken() {
		InfoScreen l  = new InfoScreen(
			"\n"
			 + "You flee in terror!\n"
			 + "\n"
			 + "You live to fight another day after bravely running away. All the villagers laugh loudly and call you a chicken. But at least you have not been eaten alive by the evil critters from the forest.\n"
			 + "\n"
			 + "You feel that you could have achieved a little more in your life.....\n"
			 + "\n"
			 + "\n");

		l.setForeground(new Color(0, 0, 0));
		l.setBackground(new Color(255, 255, 0));

		switchScreen(l);
		screen = null;
		Game.getInput();
		Game.over = true;
	}

	public void destroy() {
		removeAll();
	}

	// loads an image from wherever possible
	// ideally, from the .jar resource bundle
	// not sure if all of this is necessary
	// but try to cover all possible environments
	public Image getImage(String filename) {
		Toolkit toolkit  = Toolkit.getDefaultToolkit();

		Image image      = null;

		try {
			URL imageURL  = getClass().getResource(filename);
			if(imageURL != null) {
				image = toolkit.getImage(imageURL);
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		if(image == null) {
			image = toolkit.getImage(filename);
			if(image == null) {
				image = getImage(getCodeBase(), filename);
			}
		}

		return image;
	}

	// image filter object to create greyed tiles
	class GreyFilter extends RGBImageFilter {
		public GreyFilter() {
			canFilterIndexColorModel = true;
		}

		public int filterRGB(int x, int y, int rgb) {
			return (rgb & 0xff000000)
				 | (0x10101 * (((rgb & 0xff0000) >> 18)
				 + ((rgb & 0xff00) >> 10)
				 + ((rgb & 0xff) >> 2)));
		}
	}
}
