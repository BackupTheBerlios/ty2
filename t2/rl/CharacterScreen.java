package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;

public class CharacterScreen extends Screen {

	private Being character;

	public void handleScreen() {
		boolean end=false;
		while (!end) {
			KeyEvent k=Game.getInput();
			if (k!=null) {
				if (k.getKeyCode()==k.VK_ESCAPE) { end = true;}
				char c = Character.toLowerCase(k.getKeyChar());
				if (c =='f') {
					doDump();
					end = true;
				}
			}
		}
	}

	private void writeFile( FileWriter f, String line ){
		if( f != null & line != null ){
		try {
			f.write(line);
			f.write("\n");
		} catch (Exception e) {
			System.out.println("An error occured while dumping"+ line + " to " + f.toString());
		}
		}
	}

	private boolean doDump(){

		if (QuestApp.isapplet) {
			Game.message("You cannot save in the applet version of Tyrant");
			Game.message("This is due to browser security restrictions");
			Game.message("Run the downloaded application version instead");
			return false; 
		}
		
		if (character==Game.hero) {
			try {
			String filename = Game.hero.getName() + "_dump.txt";
			FileWriter f = new FileWriter( filename );

			writeFile(f,Game.hero.getName());
			writeFile(f,Game.hero.race + " " + Game.hero.profession);
		
		
			writeFile(f,"SK: " + statString(RPG.ST_SK)  + "\tArtisan : " + character.getBaseStat(RPG.ST_ARTISAN ));
			writeFile(f,"ST: " + statString(RPG.ST_ST)  + "\tBard    : " + character.getBaseStat(RPG.ST_BARD    ));

			writeFile(f,"AG: " + statString(RPG.ST_AG) + "\tFighter : " + character.getBaseStat(RPG.ST_FIGHTER ));

			writeFile(f,"TG: " + statString(RPG.ST_TG)  + "\tRanger  : " + character.getBaseStat(RPG.ST_RANGER  ));
			writeFile(f,"IN: " + statString(RPG.ST_IN)  + "\tMage    : " + character.getBaseStat(RPG.ST_MAGE    ));
			writeFile(f,"WP: " + statString(RPG.ST_WP)  + "\tPriest  : " + character.getBaseStat(RPG.ST_PRIEST  ));
			writeFile(f,"CH: " + statString(RPG.ST_CH)  + "\tScholar : " + character.getBaseStat(RPG.ST_SCHOLAR ));
			writeFile(f,"CR: " + statString(RPG.ST_CR)  + "\tThief   : " + character.getBaseStat(RPG.ST_THIEF   ));
			writeFile(f,"");
			writeFile(f,"");
			writeFile(f,"Hit Points      : "+character.getStat(RPG.ST_HPS)+"/"+character.getStat(RPG.ST_HPSMAX  ));
			writeFile(f,"Magic Points    : "+character.getStat(RPG.ST_MPS)+"/"+character.getStat(RPG.ST_MPSMAX  ));
			writeFile(f,"Move Speed      : "+character.getStat(RPG.ST_MOVESPEED));
			writeFile(f,"Attack Speed    : "+character.getStat(RPG.ST_ATTACKSPEED));
			writeFile(f,"");
			writeFile(f,"Current Level   : "+character.getStat(RPG.ST_LEVEL));
			writeFile(f,"Current Exp.    : "+character.getStat(RPG.ST_EXP));
			writeFile(f,"Hunger level    : "+RPG.percentile(character.getStat(RPG.ST_HUNGER), Hero.HUNGERLEVEL)+"%");
			writeFile(f,"");
			
			Thing weapon=character.getWielded(RPG.WT_MAINHAND);
			Weapon w=null;
			if (weapon instanceof Weapon) {
				writeFile(f,"Wielding "+weapon.getName());
				w=(Weapon)weapon;
			} else {
				writeFile(f,"Unarmed");
				w=Lib.WEAPON_MAUL;
			}

			writeFile(f,"Attack Cost     : "+w.getStat(RPG.ST_ATTACKCOST));
			writeFile(f,"Attack Skill    : "+w.getASK(character,null));
			writeFile(f,"Attack Strength : "+w.getAST(character,null));
			writeFile(f,"");
			writeFile(f,"Defence Skill   : "+character.getStat(RPG.ST_DSK));
			writeFile(f,"Armour Class    : "+character.getStat(RPG.ST_ARMNORMAL));
	
			writeFile(f,"Encumberance    : "+RPG.middle(0,character.getStat(RPG.ST_ENCUMBERANCE),100)+"%");
			writeFile(f,"Wealth          : "+Integer.toString(character.getMoney()));
			f.flush();
			f.close();
			Game.message("Dump saved in file " + filename );
			} catch (Exception e) {
				Game.message("File dump saved");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}

	public CharacterScreen(Being ch) {
		super();

		setForeground(QuestApp.infotextcolor);	
		setBackground(QuestApp.infoscreencolor);

		setFont(QuestApp.mainfont);
	
		character=ch;
	}
	
	private String statString(int s) {
		int bs=character.getBaseStat(s);
		int ms=character.getStat(s)-bs;
		return Text.centrePad( String.valueOf(bs),((ms>=0)?"(+"+ms:"("+ms)+")",8);
	}
	
	public void paint(Graphics g) {
		if (character==Game.hero) {
			g.drawString(Game.hero.getName(),40,20);
			g.drawString(Game.hero.race+" "+Game.hero.profession,240,20);
		} else {
			g.drawString(character.getName(),40,20);
		}
		
		{
		 	int image=character.getImage();
			int sx=(image%20)*MapPanel.TILEWIDTH;
			int sy=(image/20)*MapPanel.TILEHEIGHT;
			int px=16;
			int py=8;	
			g.drawImage(QuestApp.creatures,px,py,px+MapPanel.TILEWIDTH,py+MapPanel.TILEHEIGHT,sx,sy,sx+MapPanel.TILEWIDTH,sy+MapPanel.TILEHEIGHT,null);
		}
		
		g.drawString("SK: "+statString(RPG.ST_SK),40,40);
		g.drawString("ST: "+statString(RPG.ST_ST),40,40+1*16);
		g.drawString("AG: "+statString(RPG.ST_AG),40,40+2*16);
		g.drawString("TG: "+statString(RPG.ST_TG),40,40+3*16);
		g.drawString("IN: "+statString(RPG.ST_IN),40,40+4*16);
		g.drawString("WP: "+statString(RPG.ST_WP),40,40+5*16);
		g.drawString("CH: "+statString(RPG.ST_CH),40,40+6*16);
		g.drawString("CR: "+statString(RPG.ST_CR),40,40+7*16);
		g.drawString("(a) Artisan : "+character.getBaseStat(RPG.ST_ARTISAN),240,40+7*16);
		g.drawString("(b) Bard		: "+character.getBaseStat(RPG.ST_BARD),240,40+6*16);
		g.drawString("(f) Fighter : "+character.getBaseStat(RPG.ST_FIGHTER),240,40+1*16);
		g.drawString("(r) Ranger	: "+character.getBaseStat(RPG.ST_RANGER),240,40);
		g.drawString("(m) Mage		: "+character.getBaseStat(RPG.ST_MAGE),240,40+5*16);
		g.drawString("(p) Priest	: "+character.getBaseStat(RPG.ST_PRIEST),240,40+3*16);
		g.drawString("(s) Scholar : "+character.getBaseStat(RPG.ST_SCHOLAR),240,40+4*16);
		g.drawString("(t) Thief	 : "+character.getBaseStat(RPG.ST_THIEF),240,40+2*16);

		g.drawString("Hit Points		: "+character.getStat(RPG.ST_HPS)+"/"+character.getStat(RPG.ST_HPSMAX),40,40+9*16);
		g.drawString("Magic Points	: "+character.getStat(RPG.ST_MPS)+"/"+character.getStat(RPG.ST_MPSMAX),40,40+10*16);
		g.drawString("Move Speed		: "+character.getStat(RPG.ST_MOVESPEED),40,40+11*16);
		g.drawString("Attack Speed	: "+character.getStat(RPG.ST_ATTACKSPEED),40,40+12*16);

		g.drawString("Current Level : "+character.getStat(RPG.ST_LEVEL),40,40+14*16);
		g.drawString("Current Exp.	: "+character.getStat(RPG.ST_EXP),	40,40+15*16);
//		g.drawString("Hunger level	: "+character.getStat(RPG.ST_HUNGER)/100,	40,40+16*16);
		g.drawString("Hunger level	: "+RPG.percentile(character.getStat(RPG.ST_HUNGER), Hero.HUNGERLEVEL)+"%",	40,40+16*16);
		
		Thing weapon=character.getWielded(RPG.WT_MAINHAND);
		Weapon w=null;
		if (weapon instanceof Weapon) {
			g.drawString("Wielding "+weapon.getName(),240,40+9*16);
			w=(Weapon)weapon;
		} else {
			g.drawString("Unarmed",240,40+9*16);
			w=Lib.WEAPON_MAUL;
		}
		
		g.drawString("Attack Cost		 : "+w.getStat(RPG.ST_ATTACKCOST),240,40+10*16);
		g.drawString("Attack Skill		: "+w.getASK(character,null),240,40+11*16);
		g.drawString("Attack Strength : "+w.getAST(character,null),240,40+12*16);

		g.drawString("Defence Skill	 : "+character.getStat(RPG.ST_DSK),240,40+13*16);

		g.drawString("Armour Class		: "+character.getStat(RPG.ST_ARMNORMAL),240,40+14*16);
		g.drawString("Encumberance		: "+RPG.middle(0,character.getStat(RPG.ST_ENCUMBERANCE),100)+"%",240,40+15*16);
		g.drawString("Wealth					: "+Integer.toString(character.getMoney()),240,40+16*16);
		
		String bottomstring="F=File Dump  ESC=Exit";
		g.drawString(bottomstring,20,getSize().height-10);
	}
	
	

}