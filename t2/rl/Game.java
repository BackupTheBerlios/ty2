package rl;

import java.awt.event.*;
import java.io.*;
import java.util.zip.*;

public class Game extends Object {
	public static MessagePanel messagepanel;
	public static MapPanel mappanel;
	public static QuestApp questapp;

	//Filename of the savegame, used for Ctrl C, Control S , Control X
	private static String filename = "";

	// static field to reference the Hero object
	public static Hero hero;
	
	// The actor field stores which being is currently
	// taking an action. Primary use is to determine
	// whether the hero is responsible for inflicting 
	// damage, i.t. whether beasties get angry or not.
	public static Thing actor;
	
	// Elapsed game time in ticks
	// 1 move is approx 100 ticks	
	public static int time = 0;
	
	// Game over flag
	// Set to true during game to terminate main loop
	public static boolean over=true;

	// Interface helper object
	public static Interface userinterface;
	
	// Thread for recieveing user input
	public static Thread thread;
	
	public static int seed() {
		return hero.seed; 
	}
	
	// Print a general game message
	// All messages should be routed through here
	// Note that they're not.
	public static void message(String s) {
		if (s.equals("")) {
			messagepanel.setText("");
		} else {
			messagepanel.addMessage(Text.capitalise(s+"\n"));
		}
	}
	
	// input functionality
	public static KeyEvent getInput() {
		userinterface.getInput();
		return userinterface.keyevent;
	}
	
	// input functionality
	public static String getLine(String prompt) {
		return getLine(prompt,"");
	}

	public static String getLine(String prompt, String result) {
		messagepanel.addMessage(prompt+result+"_");
		while(true) {
			KeyEvent k=getInput();
			char ch=k.getKeyChar();
			if (k.getKeyCode()==k.VK_ENTER) break;
			if (k.getKeyCode()!=k.VK_BACK_SPACE) {
				// add the character to the input string if typed
				// i.e. don't include SHIFT, ALT etc.
				if (Character.isWhitespace(ch)||Character.isLetterOrDigit(ch)) {
					result = result+ch;
				}
			} else {
				try {
					result=result.substring(0,result.length()-1);
				} catch (Exception e) {}
			}
			messagepanel.setText(prompt+result+"_");
		} 
		Game.message("");
		return result;
	}

	// Simulate a key press
	// mainly used for handling equivalent mouse clicks
	public static void simulateKey(char c) {
		if (userinterface!=null) {
			KeyEvent k= new KeyEvent(mappanel,0,System.currentTimeMillis(),0,0,'c'); 
			k.setKeyChar(c);
			userinterface.go(k);
		}
	}
	
	public static void infoScreen(String s) {
		Screen old=questapp.screen;
		InfoScreen is = new InfoScreen(s);
		questapp.switchOtherScreen(is);
		getOption(" q");
		questapp.switchBack(is);
	}
	
	
	// transport to location of particular map
	public static void enterMap(Map m,int tx, int ty) {
		GameScreen gs=(GameScreen)questapp.screen;
		
		gs.map=m;
		gs.mappanel.map=m;
		m.addThing(Game.hero,tx,ty);
		Game.message(m.getEnterMessage());
		gs.mappanel.viewPosition(tx,ty);
		
		// update highest reached level if necessary
		if (hero.getStat(RPG.ST_SCORE_BESTLEVEL)<m.getLevel()) {
			hero.setStat(RPG.ST_SCORE_BESTLEVEL,m.getLevel()); 
		}
	}

	// has same effect as pressing stipulated direction key
	public static void simulateDirection(int dx, int dy) {
		switch (dy) {
			case -1:
				switch (dx) {
					case 1: simulateKey('9'); return;	
					case 0: simulateKey('8'); return;	
					case -1: simulateKey('7'); return;	
				} 
			case 0:
				switch (dx) {
					case 1: simulateKey('6'); return;	
					case 0: simulateKey('5'); return;	
					case -1: simulateKey('4'); return;	
				} 
			case 1:
				switch (dx) {
					case 1: simulateKey('3'); return;	
					case 0: simulateKey('2'); return;	
					case -1: simulateKey('1'); return;	
				} 
		}
		
		return;
	}
	
	// Waits for user to select one of specified keys
	// returns: key pressed (from list)
	//			or: 'q' if ESC is pressed
	//			or: 'e' if ENTER is pressed
	public static char getOption(String s) {
		while (true) {
			KeyEvent k=getInput();
			char c=Character.toLowerCase(k.getKeyChar());
			if (s.indexOf(c)>=0) return c;
			if (k.getKeyCode()==k.VK_ESCAPE) return 'q';
			if (k.getKeyCode()==k.VK_ENTER) return 'e';
		}	
	}

	// Choose a direction, given as a Pont offset
	public static Point getDirection() {
		while (true) {
			KeyEvent e=Game.getInput();
	
			char k=Character.toLowerCase(e.getKeyChar());
			int i=e.getKeyCode();
			
			// handle key conversions
			if (i==e.VK_UP) k='8';	 
			if (i==e.VK_DOWN) k='2'; 
			if (i==e.VK_LEFT) k='4';	 
			if (i==e.VK_RIGHT) k='6';				 
			if (i==e.VK_HOME) k='7';	 
			if (i==e.VK_END) k='1';	 
			if (i==e.VK_PAGE_UP) k='9';	 
			if (i==e.VK_PAGE_DOWN) k='3';	 
			if (i==e.VK_ESCAPE) k ='q'; 

			int dx=-2; int dy=0;
			if (k=='8') { dx=0; dy=-1; }
			if (k=='2') { dx=0; dy=1; }
			if (k=='4') { dx=-1; dy=0; }
			if (k=='6') { dx=1; dy=0; }
			if (k=='7') { dx=-1; dy=-1; }
			if (k=='9') { dx=1; dy=-1; }
			if (k=='1') { dx=-1; dy=1; }
			if (k=='3') { dx=1; dy=1; }
			
			if ((k=='5')||(k=='.')) { dx=0; dy=0;}
			if (k=='q') {return null;}
					 
			if (dx!=-2) return new Point(dx,dy);
		}
	}

	 
	// Gets player to select a string from given list
	// Calls inventory-style screen
	// Restores original screen before returning
	public static String selectString(String message, String[] strings) {
		Screen old=questapp.screen;
		ListScreen ls= new ListScreen(message,strings); 
		questapp.switchOtherScreen(ls);
		String ret=(String)ls.getObject();
		questapp.switchBack(ls);
		return ret;
	} 
	 
	// Gets player to select an item from given list
	// Calls inventory-style screen
	// Restores original screen before returning
	public static Thing selectItem(String message, Thing[] things) {
		Screen old=questapp.screen;
		InventoryScreen is = new InventoryScreen(message,things);
		questapp.switchOtherScreen(is);
		Thing ret=(Thing)is.getObject();
		questapp.switchBack(is);
		return ret;
	}
	 
	
	
	// save game to local ZIP file
	public static boolean save() {
		return save(false);
	}

	public static boolean save(boolean useDefault) {
		if (QuestApp.isapplet) {
			Game.message("You cannot save in the applet version of Tyrant");
			Game.message("This is due to browser security restrictions");
			Game.message("Run the downloaded application version instead");
			return false; 
		}
		
		try {
			if(!useDefault || filename.equals("") ){
				filename=getLine("Enter a filename: ", filename);
			}
			FileOutputStream f=new FileOutputStream(filename);
			ZipOutputStream z=new ZipOutputStream(f);
			
			z.putNextEntry(new ZipEntry("data"));
			
			if (!save(new ObjectOutputStream(z))) {
				throw new Error("Save game failed"); 
			} else {
				Game.message("Game saved - "+filename);
			}
			z.closeEntry();
			z.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;	
	}
	
	// restore game from local zip file
	public static boolean restore() {
		if (QuestApp.isapplet) {
			Game.message("You cannot load in the applet version of Tyrant");
			Game.message("This is due to browser security restrictions");
			Game.message("Run the downloaded application version instead");
			return false; 
		}
		
		try {
			String filename=getLine("Enter save game filename: ","tyrant.sav");
			FileInputStream f=new FileInputStream(filename);
			ZipInputStream z=new ZipInputStream(f);
			z.getNextEntry();
			if (!restore(new ObjectInputStream(z))) {
				Game.message("Load game failed - "+filename); 
			}
			z.closeEntry();
			z.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
		
	}
	
	// craetes a pseudo-random number based on:
	//	1. The seed vale
	//	2. The hero instance
	//	3. The max value
	public static int hash(int seed, int max) {
		return (seed^hero.gameseed)%max;
	}
	
	public static boolean save(ObjectOutputStream o) {
		try {
			o.writeObject(Game.hero); 
		} catch (Exception e) {return false;}
		return true;
	}

	public static Object setUnique(String s, Object a) {
		Game.hero.uniques.put(s,a); 
		return a;
	}

	public static Object getUnique(String s) {
		return Game.hero.uniques.get(s);		
	}


	public static boolean restore(ObjectInputStream i) {
		try {
			Hero h=(Hero)i.readObject(); 
			hero=h;
			Game.enterMap((Map)h.place,h.x,h.y);
		} catch (Exception e) {return false;}
		
		return true;
	}
	
	// called when something is kiled or destroyed
	public static void registerDeath(Thing t) {
		if (Game.actor==Game.hero) {
			Game.hero.registerKill(t);
		}
		
	}

}