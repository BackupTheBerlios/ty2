package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Date;

class GameScreen extends Screen {
	public MapPanel mappanel;
	public MessagePanel messagepanel;
	public StatusPanel statuspanel;
	public Map map;

	private char key_showQuests = '#';
	private char key_pickUp = 'g';
	private char key_eat = 'E';
	private char key_inventory = 'i';
	private char key_control_messages = 'P';
	private char key_control_quit = 'C';
	private char key_skills = 'a';
	private char key_saveAs = '-';
	private char key_control_save = 'S';
	private char key_control_save_quit = 'X';
	
	// this is the main game loop
	// it catches any exceptions for stability
	// and lets the game continue
	//
	// very important that endTurn() gets called after the player moves
	// this ensures that the rest of the map stays up to date
	//
	// use Game.hero here - it might change due to save/load
	public void mainLoop() {
		while (true) {
			KeyEvent k=Game.getInput();
			try {
				map=Game.hero.getMap();
				keyAction(k);
				endTurn();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
			// has the hero died?
			if (Game.hero.hps<0) {
				Game.message("You have died.....");
				Game.getInput();
				questapp.gameOver();
				return;
			}
			
		} 
	}
	
	public GameScreen() {
		super();
		
		setForeground(Color.white);	
		setBackground(Color.black);

		//set the default layout for the main screen
		setLayout(new BorderLayout());	
		
		// Add the message panel
		messagepanel = new MessagePanel();
		add("South", messagepanel);

		mappanel = new MapPanel();
		add("Center", mappanel);
		Game.mappanel=mappanel;
 
		statuspanel=new StatusPanel();
		add("East",statuspanel);
		
		Map m=new WorldMap(33,33);
		questapp.screen=this;
		Game.enterMap(m,16,14);
		
		setFont(QuestApp.mainfont);
	}

	// main keypress handling routine
	// Called when key is pressed in main screen
	public void keyAction(KeyEvent e) {
		int dx=0;
		int dy=0;
		final Hero h=Game.hero;	
		if (h==null) throw new Error("No hero found!");

		messagepanel.setText("");

		char K = e.getKeyChar();
		char k = Character.toLowerCase(e.getKeyChar());
		boolean controlIsDown = e.isControlDown();
		if (controlIsDown)
		{
			String keyText = e.getKeyText( e.getKeyCode() );
			if ( keyText != "Ctrl"){
				K = keyText.charAt(0); 
			}
			System.out.println( K );
			System.out.println( e.paramString() );
		}
			
		// handle key conversions
		if (e.getKeyCode()== e.VK_UP) {k='8';}	
		if (e.getKeyCode()== e.VK_DOWN) {k='2';}
		if (e.getKeyCode()== e.VK_LEFT) {k='4';}	
		if (e.getKeyCode()== e.VK_RIGHT) {k='6';}	
		if (e.getKeyCode()== e.VK_HOME) {k='7';}	
		if (e.getKeyCode()== e.VK_END) {k='1';}	
		if (e.getKeyCode()== e.VK_PAGE_UP) {k='9';}
		if (e.getKeyCode()== e.VK_PAGE_DOWN) {k='3';}	
		if (e.getKeyCode()== e.VK_ESCAPE) {k ='q';}
		if (e.getKeyCode()== e.VK_F1) {k ='?';}
		if (e.getKeyCode()== e.VK_F2) {k ='(';}
		if (e.getKeyCode()== e.VK_F3) {k =')';}
		if (e.getKeyCode()== e.VK_F4) {k ='*';}
		if (e.getKeyCode()== e.VK_F5) {k =':';}
			
		if (map instanceof WorldMap) {
			if ("123456789avix>?()-=#:".indexOf(k)<0) return;
		}
		
		if (k=='8') {
				 dx=0; dy=-1;}
		if (k=='2') {
				 dx=0; dy=1;}
		if (k=='4') {
				 dx=-1; dy=0;}
		if (k=='6') {		
				 dx=1; dy=0;}
		if (k=='7') {
				 dx=-1; dy=-1;}
		if (k=='9') {
				 dx=1; dy=-1;}
		if (k=='1') {
				 dx=-1; dy=1;}
		if (k=='3') {
				 dx=1; dy=1; }
		 
		 // handle movement commands	
		if ((dx!=0)||(dy!=0)) {
			if (map.getTile(h.x+dx,h.y+dy)!=0) {
				h.tryMove(map,h.x+dx,h.y+dy);
			} else if (map.canExit()) {
				Game.message("Exit area? (y/n)");
				char c = Game.getOption("yn");
				Game.messagepanel.setText("");
				if (c=='y') {
					map.exitMap(h.x,h.y);
				}
			}
		}
	
		// wait on spot
		if ((k==' ')||(k=='5')||(k=='.')) {
			// subtract action points to delay hero
			h.aps=h.aps-100;
		}

		// save and load games
		if (k==key_saveAs ) {
			if (!Game.save()) Game.message("Save game failed.");
		}
		if (K==key_control_save && controlIsDown ) {
			if (!Game.save(true)) Game.message("Save game failed.");
		}

		if ((k=='+')||(k=='=')) {
			Game.message("Restore game - are you sure? (y/n)");
			char opt=Game.getOption("yn");
			Game.message("");
			if (opt=='y') {
				if (!Game.restore()) Game.message("Load game failed.");
			}
			return;
		}

		if( K == key_control_save_quit && controlIsDown ){
			if (!Game.save()) Game.message("Save game failed.");
			h.hps=-10;
		}

		//Show previous messages
		if( K == key_control_messages && controlIsDown ){
			MessagesScreen ls = new MessagesScreen("Messages" ,	messagepanel.getMessages().split("\n") );
			questapp.switchOtherScreen(ls);
			ls.display();
			questapp.switchBack(ls);
		}
			 
		// show quests
		if (k== key_showQuests ) {
			Quest[] quests=h.getQuests();
			String s="Your quests:\n\n";
			for (int i=0; i<quests.length; i++) {
				s+=quests[i].questString()+"\n\n"; 
			}
			s+="[Press space to continue]";
			Game.infoScreen(s);
			return;	 
		}	 
			 
		// debugger's special keys
		if (k==':') {
			if (!QuestApp.debug) return;
			String command=Game.getLine("What is your bidding?");
			char ch=command.charAt(0);
			command=command.substring(1);
				
			if (ch=='m') try { // summon a monster
				int n=Integer.parseInt(command);
				map.addThing(new Creature(n),h.x-1,h.y-1,h.x+1,h.y+1);
			} catch (Exception exception) {return;}
				
			if (ch=='a') try { // create an artifact
				int n=Integer.parseInt(command);
				map.addThing(Lib.createArtifact(n),h.x-1,h.y-1,h.x+1,h.y+1);
			} catch (Exception exception) {return;}
			
			if (ch=='P'){
				for(int tt=1; tt <= Potion.getImplemented(); tt++){
					map.addThing(Potion.createPotion(tt),h.x,h.y);	
				}
			}
			if (ch=='p') {
				Thing[] portals=map.getObjects(0,0,map.width-1,map.height-1,Portal.class); 
				if (portals.length>0) {
					int r=RPG.r(portals.length);
					map.addThing(h,portals[r].x,portals[r].y);
				}
			}
		} 

		if ( controlIsDown && K == key_control_quit ) {
				Game.message("Are you sure you want to quit this game (y/n)");
				if (Game.getOption("yn")=='y') h.hps=-10;
		}

		if (k== key_skills) {
			ArtScreen ls=new ArtScreen("Your Skills:",h.arts.getArts(Skill.class));
			questapp.switchOtherScreen(ls);
			Art a=ls.getArt();
			questapp.switchBack(ls);
			repaint();
			if (a!=null) {
				((Skill)a).apply();
			}
			
		}
		
				
		// chat to creature
		if (k=='c') {
			Thing person=null;
			if (map.countNearbyMobiles(h.x,h.y,1)>2) {
				Game.message("Chat: select direction");
				Point p=Game.getDirection();
				person=(Person)map.getObject(h.x+p.x,h.y+p.y,Being.class);
			} else {
				try {person=map.getNearbyMobile(h.x,h.y,1);} catch (Exception anyex) {} 
			}
				
			if ((person!=null)&&(person instanceof Talkable)) {
				((Talkable)person).talk(person);	
			} else {
				Game.message("You can't talk to "+person.getTheName());
			};
			// h.aps-=100 make it quick
			return;
		}
				
		// zoom/unzoom screen
		if (k=='(') { 
			mappanel.zoomfactor+=25;
			Game.message("Zooming in.... ("+mappanel.zoomfactor+"%)");
			mappanel.repaint();
		}
		
		if (k==')') {
			if (mappanel.zoomfactor>25) {
				mappanel.zoomfactor-=25;
				Game.message("Zooming out... ("+mappanel.zoomfactor+"%)");
			}
			mappanel.repaint();
		}
						
		// drop an item
		if (k=='d') {
			Thing o=getInventoryItem("Select item to drop:",h.inv.getContents());
			if (o instanceof Item) {
				// break if worn and cursed
				if ((o.y>0)&&(!h.inv.clearUsage(o.y))) return;
					 
				if ((o instanceof Stack)&&(((Stack)o).number>1)) {
					Game.message("Drop how many? (Enter=All)");
					char c=Game.getOption("0123456789");
					if ((c>='0')&&(c<='9')) {
						o=o.remove((int)c-(int)'0');
					} 
				}
				map.addThing((Thing)o,h.x,h.y);	
				h.message("Dropped "+((Thing)o).getName());
				h.aps-=100;
			}
		}

		// eat an item
		if ( K==key_eat ) {
			Thing o=getInventoryItem("Select item to eat:",h.inv.getUsableContents(Item.USE_EAT));
			if (o instanceof Item) {
				h.aps=h.aps-500;
				((Item)o).use(h,Thing.USE_EAT);
			}
		}

		if (k=='f') {
			Thing w=h.getWielded(RPG.WT_RANGEDWEAPON);
				
			if (w==null) {
				Thing[] rws=h.inv.getContents(RangedWeapon.class);
				if (rws.length>0) {
					w=getInventoryItem("Select a ranged weapon:",rws); 
				} else {
					Game.message("You have no ranged weapon"); 
				}
			}
				
			if (w!=null) {
				w.use(h); 
			} else {
			}
		}
			
		// give item
		if (k=='G') {
		 
			// select mobile to give to
			Mobile mobile=null;
			if (map.countNearbyMobiles(h.x,h.y,1)>2) {
				Game.message("Give: select direction");
				Point p=Game.getDirection();
				mobile=(Mobile)map.getObject(h.x+p.x,h.y+p.y,Person.class);
			} else {
				try {mobile=map.getNearbyMobile(h.x,h.y,1);} catch (Exception anyex) {} 
			}
				
			if ((mobile!=null)&&(!h.isHostile(mobile))) {
				Thing gift=Game.selectItem("Select item to give:",h.inv.getContents());
				if (gift!=null) {
	
					// can't give a cursed item
					if ((gift.y>0)&&(!h.inv.clearUsage(gift.y))) return;
	
					mobile.give(h,gift);	
					h.aps-=100; // make it quick
				}
			} else {
				Game.message("You get no response");
			}
				
		}
			
		// inventory
		if (k== key_inventory ) {
			getInventoryItem("Your inventory:");
			System.out.println( "Getting back				: " + new Date().toString() );
		}

		// kick something
		if (k=='k') {
			Game.message("Kick: select direction");
			Point p=Game.getDirection();
			if ((p!=null) && ((p.x!=0)||(p.y!=0))) {
				messagepanel.setText("");
				h.kick(p.x,p.y);	
			};
		}

		// look at something
		if (k=='l') {
			Game.message("Look: select location");
			Point p=getTargetLocation(); 
			if (p!=null) {
				messagepanel.setText("");
				Game.message((QuestApp.debug)?("You see: "+(map.getTile(p.x,p.y)&65535)):"You see:");
				if (map.isVisible(p.x,p.y)) {
					Thing t= map.getObjects(p.x,p.y);
					while(t!=null) {
						String st="";
						if (t.isVisible()) {
							st=st+t.getDescription().getDescriptionText();
						}
						if (t instanceof Being) {
							Being b=(Being) t;
							if (t.getStat(RPG.ST_HPS)<(t.getStat(RPG.ST_HPSMAX)/2)) {
								st=st+" (Injured) "; 
							}
							if (QuestApp.debug) {
								st=st+" ("+b.getAI().getClass().getName()+":"+Integer.toString(b.getStat(RPG.ST_STATE))+") ";
								if (b.isHostile(Game.hero)) {
									st=st+" (Hostile) "; 
								}
							}
						}
						Game.message(st);
						t=t.next;	
					}
				}
			}
		}

		// open something
		if (k=='o') {
			Game.message("Select direction");
			Point p=Game.getDirection();
			if ((p!=null) && ((p.x!=0)||(p.y!=0))) {
				messagepanel.setText("");
				Thing t=map.getObject(h.x+p.x,h.y+p.y,Door.class);
				if (t!=null) {
					t.use(h);
					h.aps-=500;
				}
			}
		}

		// pick up items
		if ( k==key_pickUp ) {
			Thing[] th=map.getThings(h.x,h.y);
			for (int i=0; i<th.length; i++) {
				Thing t=th[i];
				if (t instanceof Item) {
					boolean pickup=true;
					
					if (t.isOwned()&&((map.mapstate&1)==0)) {
						// get item value
						int val = RPG.niceNumber(t.getStat(RPG.ST_ITEMVALUE)*t.getNumber());
						if (t instanceof Coin) val=0;
						
						// present options
						char c;
						if (val>0) {
							Game.message(t.getTheName()+" costs "+Coin.valueString(val));
							Game.message("Buy, Steal or Leave?");
							c=Game.getOption("sbl");
						} else {
							Game.message(t.getTheName()+" is not yours");
							Game.message("Steal it? (y/n)");
							c=Game.getOption("synl");
							if (c=='y') c='s';
						}
						messagepanel.setText("");

						if (c=='l') pickup=false;
						if (c=='b') {
							int funds=h.getMoney();
							if (val>funds) {
								Game.message("You can't afford that!"); 
								Game.message("You have "+Coin.valueString(funds)); 
								pickup=false;
							} else {
								Game.message("You pay "+Coin.valueString(val));
								h.removeMoney(val);
								((Item)t).flags&=(~Item.ITEM_OWNED);
							} 
						} else if (c=='s') {
							Game.message("You sneakily grab "+t.getTheName()); 
							map.areaNotify(h.x,h.y,10,AI.EVENT_THEFT,0,h);
							((Item)t).flags&=(~Item.ITEM_OWNED);
							h.addThing(t);
							pickup=false;
						} else {
							pickup=false; 
						}
					}
						
					if (pickup) {
						h.message("You pick up "+t.getTheName());
						((Item)t).flags&=(~Item.ITEM_OWNED);
						h.addThing(t);
					}
				}
			}
			h.aps-=200;
		}
			
		// quaff an item
		if (k=='q') {
			Thing o=getInventoryItem("Select item to quaff:",h.inv.getUsableContents(Item.USE_DRINK));
			if (o instanceof Item) {
				h.aps=h.aps-300;
				((Item)o).use(h,Thing.USE_DRINK);
			} 
		}

		// read an item
		if (k=='r') {
			Thing o=getInventoryItem("Select item to read:",h.inv.getUsableContents(Item.USE_READ));
			if (o instanceof Item) {
				h.aps=h.aps-500;
				((Item)o).use(h,Thing.USE_READ);
			}
		}
			
		// search for interesting things
		if (k=='s') {
			h.message("Searching...");
			h.searchAround();
			h.aps-=600;
		}
			
		// if (k=='+': h.addMoney(RPG.d(100)); break;
		// if (k=='-': h.removeMoney(RPG.d(10)); break;

		// throw an item
		if (k=='t') {
			Thing o=getInventoryItem("Throw Item:",h.inv.getContents());
			if (o != null) {
				// can't throw cursed worn item
				if ((o.y>0)&&(!h.inv.clearUsage(o.y))) return;

				// get initial target
				Thing f=map.findNearestFoe(h);
				if ((f!=null)&&(!map.isVisible(f.x,f.y))) f=null;
				// get user target selection
				Point p=getTargetLocation(f);
					
				if (p!=null) {
					o=o.remove(1);
					h.throwThing(o,p.x,p.y);
				}
			}
		}

		// use an item
		if (k=='u') {
			Thing o=getInventoryItem("Use Item:",h.inv.getUsableContents(Item.USE_NORMAL));
			if (o instanceof Item) {
				((Item)o).use(h);
			}
		}
			
			
		// view hero stats
		if ((k=='v')||(k=='@')) {
			CharacterScreen ls=new CharacterScreen(h);
			questapp.switchOtherScreen(ls);
			Game.getInput();
			questapp.switchBack(ls);				
		}

		// wield an item
		if (k=='w') {
//			Thing o=Game.selectItem("Wield/wear which item?",h.inv.getWieldableContents());
			Thing o=getInventoryItem("Wield/wear which item?",h.inv.getWieldableContents());

			if (o instanceof Item) {
				final Item item=(Item)o;
				final int wt=item.wieldType();
						
				if ((wt==RPG.WT_LEFTRING)||(wt==RPG.WT_RIGHTRING)) {
					Game.message("Which finger? (r/l)");
					char c = Game.getOption("lr");
					if (c=='r') {
						if (h.wield(item,RPG.WT_RIGHTRING)) Game.message("You put "+item.getTheName()+" on your right finger");
					} else if (c=='l') {
						if (h.wield(item,RPG.WT_LEFTRING)) Game.message("You put "+item.getTheName()+" on your left finger");
					} else messagepanel.setText("");
				}

				else if ((wt==RPG.WT_MAINHAND)||(wt==RPG.WT_SECONDHAND)) {
					Game.message("Which hand? (r/l)");
					char c = Game.getOption("lr");
					if (c=='r') {
						if (h.wield(item,RPG.WT_MAINHAND)) Game.message("You wield "+item.getTheName()+" in your right hand");
					} else if (c=='l') {
						if (h.wield(item,RPG.WT_SECONDHAND)) Game.message("You wield "+item.getTheName()+" in your left hand");
					} else messagepanel.setText("");
				}

				else if ((wt==RPG.WT_TWOHANDS)) {
					if (h.wield(item,RPG.WT_TWOHANDS)) Game.message("You wield "+item.getTheName()+" in both hands");
				}

				else if ((wt==RPG.WT_RANGEDWEAPON)) {
					if (h.wield(item,wt)) Game.message("You wield "+item.getTheName()+" as your ranged weapon");
				}

				else if ((wt==RPG.WT_MISSILE)) {
					if (h.wield(item,wt)) Game.message("You prepare to fire "+item.getTheName());
				}
				 
				else {
					if (h.wield(item,wt)) Game.message ("You are now wearing "+item.getTheName());
				}
			}
		}

		// enter or exit area
		if ((k=='x')||(k=='<')||(k=='>')) { 
			map.exitMap(h.x,h.y);
		}
			
		// pray for aid
		if (k=='y') {
			if (QuestApp.debug||(h.getStat(RPG.ST_FATE)>1)) {
				h.stats.incStat(RPG.ST_FATE,-1);
				Creature c=Lib.createCreature(RPG.d(12));
				//Creature c=new Creature(Creature.SHAMAN);
				c.makeFollower(h);
				map.addThing(c,h.x+RPG.r(3)-1,h.y+RPG.r(3)-1);
				Game.message("Looks like the gods have granted you a companion...");
			} else {
				Game.message("The gods do not seem interested in helping you"); 
			}
		}
				
		// cast a spell!
		if (k=='z') {
			SpellScreen ls=new SpellScreen("Select spell to cast:",h.arts.getArts(Spell.class));
			questapp.switchOtherScreen(ls);
			Spell s=ls.getSpell();
			questapp.switchBack(ls);
			if (s!=null) {
				if (h.mps>=s.cost) {
					h.aps=h.aps-s.castTime(h);
					h.mps-=s.cost;
					castSpell(s);
					s.train(h);
				} else {
					Game.message("You are too exhausted to cast "+s.getName());	
				}
			}
		}

		if (k=='?') {
		InfoScreen info = new InfoScreen(
				"Key Commands:\n"
			 +"	c = chat to somebody							= = load game\n"
			 +"	d = drop item										 - = save game\n"
			 +"	e = eat item\n"
			 +"	f = fire ranged weapon\n"
			 +"	i = view inventory\n"
//			 +"	j = jump\n"
			 +"	k = kick something\n"
			 +"	l = look\n"
			 +"	o = open / close door\n"
			 +"	p = pick up item\n"
			 +"	q = quaff potion\n"
			 +"	r = read book or scroll\n"
			 +"	s = search area\n"
			 +"	t = throw / shoot missile\n"
			 +"	u = use item\n"
			 +"	v = view hero statistics\n"
			 +"	w = wield weapon / wear armour\n"
			 +"	x = exit area / climb staircase\n"
			 +"	y = pray for divine aid\n"
			 +"	z = cast spell\n"
			 +"\n");
		questapp.switchOtherScreen(info);
			Game.getInput();
			questapp.switchBack(info);
		}
		 
	}

	
	public Thing getInventoryItem(String s) {
	Thing thing;
		Hero h=Game.hero;
		InventoryScreen is = new InventoryScreen(s,h.inv.getContents());
		questapp.switchOtherScreen(is);
		thing =	(Thing)is.getObject();
		questapp.switchBack(is);
	return thing;
	}

	public Thing getInventoryItem(String s, Thing[] inv) {
	Thing thing;
		Hero h=Game.hero;
		InventoryScreen is = new InventoryScreen(s,inv);
		questapp.switchOtherScreen(is);
		thing =	(Thing)is.getObject();
		questapp.switchBack(is);
	return thing;
	}

	// get user to unput a location
	public Point getTargetLocation() {
		return getTargetLocation(Game.hero); 
	}

	// get location, initially place crosshairs at start
	public Point getTargetLocation(Thing start) {
		if (start==null) return getTargetLocation();
		mappanel.setCursor(start.x,start.y);
		mappanel.viewPosition(start.x,start.y);
		
		// repaint the status panel
		statuspanel.repaint();
		
		while (true) {
			KeyEvent e=Game.getInput(); 
			if (e==null) continue;
			
			char k = Character.toLowerCase(e.getKeyChar());
			
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

			int dx=0; int dy=0;
			if (k=='8') { dx=0; dy=-1; }
			if (k=='2') { dx=0; dy=1; }
			if (k=='4') { dx=-1; dy=0; }
			if (k=='6') { dx=1; dy=0; }
			if (k=='7') { dx=-1; dy=-1; }
			if (k=='9') { dx=1; dy=-1; }
			if (k=='1') { dx=-1; dy=1; }
			if (k=='3') { dx=1; dy=1; }
			
			if (k=='q') {
				mappanel.clearCursor();
				return null;
			}
			
			if (k==' ') {
				mappanel.clearCursor();
				return new Point(mappanel.curx,mappanel.cury);
			}
			
			mappanel.setCursor(mappanel.curx+dx,mappanel.cury+dy);
			mappanel.viewPosition(mappanel.curx,mappanel.cury);
		}
	}
	
	//override update to stop flicker
	//public void update(Graphics g) {
	//	paintAll(g);
	//statuspanel.repaint();
	//}
	
	// draw things	
	//public void paint(Graphics g) {
	//	g.drawImage(questapp.title,240,0,null);
	//	Hero h=Game.hero;
	//	setForeground(QuestApp.textcolor);
	//	g.drawString("HPs:	"+h.hps+" / "+h.getStat(RPG.ST_HPSMAX),260,200);
	//	g.drawString("MPs:	"+h.mps+" / "+h.getStat(RPG.ST_MPSMAX),260,216);
	//}
	
	public void castSpell(final Spell s) {
		final Hero h=Game.hero;
		if (s==null) return;
		
		Game.message("You cast "+s.getName());
		
		switch (s.getTarget()) {
			case Spell.TARGET_SELF:
				s.castAtSelf(h);
				break;
			case Spell.TARGET_LOCATION:
				Thing f=(s.getUsage()==Spell.SPELL_OFFENCE)?map.findNearestFoe(h):null;
				if ((f!=null)&&(!map.isVisible(f.x,f.y))) f=null;
				Point p=getTargetLocation(f);
				if (p!=null) {
					// don't fire offensive spell at self by accident
					if ((p.x==h.x)&&(p.y==h.y)&&(s.getUsage()==Spell.SPELL_OFFENCE)) {
						Game.message("Are you sure you want to target yourself? (y/n)");
						char opt=Game.getOption("yn");
						if (opt=='n') break; 
					}
					
					if (map.isVisible(p.x,p.y)) {
						s.castAtLocation(h,map,p.x,p.y);
					} else {
						Game.message("You cannot see to focus your power");
					}
				}
				break;
			 case Spell.TARGET_ITEM:
				Thing t = getInventoryItem(s.getName()+":");
				if (t!=null) {
					 s.castAtObject(h,t);
				}
				break;
		}	
	}
	
	public void endTurn() {
		Hero h = Game.hero;
		
		if (h.getMap()!=map) {
			map=h.getMap();
			if (map==null) return;
			Game.enterMap(map,h.x,h.y); 
		}
		
		// Game.message(Integer.toString(h.aps));
		
		// test for adjacent foe
		Thing f=map.findNearestFoe(h);
		if ((f!=null)&&(f.x>=(h.x-1))&&(f.x<=(h.x+1))&&(f.y>=(h.y-1))&&(f.y<=(h.y+1))) {
			h.recharging=false;
		} else {
			h.recharging=true; 
		}
		
		Game.actor=null; // player's move is over
		
		int advance=-h.aps;
		while (advance>0) {
			int step=advance;
			if (step>500) step=500;
			
			// call map action
			// this includes increment of h.aps via Mobile.action(t)
			map.action(step);
			advance-=step;
			Game.time=Game.time+step;
		}
		Game.actor=Game.hero; // hero is now active
		
		// update screen info
		mappanel.viewPosition(h.x,h.y);	
		statuspanel.repaint();
		messagepanel.repaint();
	}
}