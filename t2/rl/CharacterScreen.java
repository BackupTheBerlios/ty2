package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterScreen extends Screen
{
	public Being character;

	public CharacterScreen(Being ch)
	{
		super();
		setForeground(QuestApp.infotextcolor);
		setBackground(QuestApp.infoscreencolor);
		setFont(QuestApp.mainfont);
		character = ch;
	}

	public void paint(Graphics g)
	{
		if(character == Game.hero)
		{
			g.drawString(Game.hero.getName(), 40, 20);
			g.drawString(Game.hero.race + " " + Game.hero.profession, 240, 20);
		}
		else
			g.drawString(character.getName(), 40, 20);
		{
			int image  = character.getImage();
			int sx     = (image % 20) * MapPanel.TILEWIDTH;
			int sy     = (image / 20) * MapPanel.TILEHEIGHT;
			int px     = 16;
			int py     = 8;
			g.drawImage(QuestApp.creatures, px, py, px + MapPanel.TILEWIDTH, py + MapPanel.TILEHEIGHT, sx, sy, sx + MapPanel.TILEWIDTH, sy + MapPanel.TILEHEIGHT, null);
		}

		//This is a temporal mess until stats are organized on Being level
		g.drawString("STR " + String.valueOf(((Hero)character).getStat("DEX")), 40, 40);
		g.drawString("DEX " + String.valueOf(((Hero)character).getStat("DEX")), 40, 40 + 1 * 16);
		g.drawString("CON " + String.valueOf(((Hero)character).getStat("CON")), 40, 40 + 2 * 16);
		g.drawString("INT " + String.valueOf(((Hero)character).getStat("INT")), 40, 40 + 3 * 16);
		g.drawString("WIS " + String.valueOf(((Hero)character).getStat("WIS")), 40, 40 + 4 * 16);
		g.drawString("CHA " + String.valueOf(((Hero)character).getStat("CHA")), 40, 40 + 5 * 16);

		g.drawString("(a) Artisan : " + character.getBaseStat(RPG.ST_ARTISAN), 240, 40 + 7 * 16);
		g.drawString("(b) Bard    : " + character.getBaseStat(RPG.ST_BARD), 240, 40 + 6 * 16);
		g.drawString("(f) Fighter : " + character.getBaseStat(RPG.ST_FIGHTER), 240, 40 + 1 * 16);
		g.drawString("(r) Ranger  : " + character.getBaseStat(RPG.ST_RANGER), 240, 40);
		g.drawString("(m) Mage    : " + character.getBaseStat(RPG.ST_MAGE), 240, 40 + 5 * 16);
		g.drawString("(p) Priest  : " + character.getBaseStat(RPG.ST_PRIEST), 240, 40 + 3 * 16);
		g.drawString("(s) Scholar : " + character.getBaseStat(RPG.ST_SCHOLAR), 240, 40 + 4 * 16);
		g.drawString("(t) Thief   : " + character.getBaseStat(RPG.ST_THIEF), 240, 40 + 2 * 16);

		g.drawString("Hit Points    : " + character.getStat(RPG.ST_HPS) + "/" + character.getStat(RPG.ST_HPSMAX), 40, 40 + 9 * 16);
		g.drawString("Magic Points  : " + character.getStat(RPG.ST_MPS) + "/" + character.getStat(RPG.ST_MPSMAX), 40, 40 + 10 * 16);
		g.drawString("Move Speed    : " + String.valueOf(((Hero)character).getStat("MOVESPEED")), 40, 40 + 11 * 16);
		g.drawString("Attack Speed  : " + String.valueOf(((Hero)character).getStat("ATTACKSPEED")), 40, 40 + 12 * 16);

		g.drawString("Current Level : " + character.getStat(RPG.ST_LEVEL), 40, 40 + 14 * 16);
		g.drawString("Current Exp.  : " + character.getStat(RPG.ST_EXP), 40, 40 + 15 * 16);
//    g.drawString("Hunger level  : "+character.getStat(RPG.ST_HUNGER)/100,  40,40+16*16);
		g.drawString("Hunger level  : " + RPG.percentile(character.getStat(RPG.ST_HUNGER), Hero.HUNGERLEVEL) + "%", 40, 40 + 16 * 16);

		Thing weapon         = character.getWielded(RPG.WT_MAINHAND);
		Weapon w             = null;
		if(weapon instanceof Weapon)
		{
			g.drawString("Wielding " + weapon.getName(), 240, 40 + 9 * 16);
			w = (Weapon)weapon;
		}
		else
		{
			g.drawString("Unarmed", 240, 40 + 9 * 16);
			w = Lib.WEAPON_MAUL;
		}

		g.drawString("Attack Cost     : " + w.getStat(RPG.ST_ATTACKCOST), 240, 40 + 10 * 16);
		g.drawString("Attack Skill    : " + w.getASK(character, null), 240, 40 + 11 * 16);
		g.drawString("Attack Strength : " + w.getAST(character, null), 240, 40 + 12 * 16);

		g.drawString("Defence Skill   : " + character.getStat(RPG.ST_DSK), 240, 40 + 13 * 16);

		g.drawString("Armour Class    : " + character.getStat(RPG.ST_ARMNORMAL), 240, 40 + 14 * 16);
		g.drawString("Encumberance    : " + RPG.middle(0, character.getStat(RPG.ST_ENCUMBERANCE), 100) + "%", 240, 40 + 15 * 16);
		g.drawString("Wealth          : " + Integer.toString(character.getMoney()), 240, 40 + 16 * 16);

		String bottomstring  = "ESC=Exit";
		g.drawString(bottomstring, 20, getSize().height - 10);
	}

	/*
	 *  public void addItem(Object o, String s, image i) {
	 *  ListItem li=new ListItem(o,s,i));
	 *  li.addMouseListener(new MouseAdapter() {
	 *  public void mouseClicked(MouseEvent e) {
	 *  getParent().remove(thislist);
	 *  selectcommand.select(o);
	 *  }
	 *  });
	 *  add(li);
	 *  }
	 */
}
