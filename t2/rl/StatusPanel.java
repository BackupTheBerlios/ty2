package rl;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class StatusPanel extends TPanel {
  public static final int boxborder=1;
  public static final Color healthcolor=new Color(180,80,0);
  public static final Color powercolor=new Color(0,128,60);

  public StatusPanel() {
    super();
    setBackground(QuestApp.panelcolor); 
  }
  
  public Dimension getPreferredSize() {
    return new Dimension(208,272); 
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    
    FontMetrics met=g.getFontMetrics(g.getFont());
    int charwidth=met.charWidth(' ');
    int charheight=met.getMaxAscent()+met.getMaxDescent();

    Hero h=Game.hero;
    Map m=h.getMap();

    Rectangle bounds=getBounds();
    int width=bounds.width;
    int height=bounds.height;
    
    // old code to do panel borders
    //g.setColor(QuestApp.panelhighlight);
    //g.fillRect(0,0,width,2);
    //g.fillRect(0,0,2,height);
    //g.setColor(QuestApp.panelshadow);
    //g.fillRect(1,height-2,width-1,2);
    //g.fillRect(width-2,1,2,height-1);
    
    
    // draw energy bars
    int hp=h.getStat(RPG.ST_HPS);
    int hpm=h.getStat(RPG.ST_HPSMAX);
    float hel=((float)hp)/hpm;
    
    if (hel<0) hel=0;
    if (hel>1) hel=1;
    paintLabel(g,"Health: "+hp+"/"+hpm,10,18);
    paintBar(g,10+16*charwidth,10,width-20-16*charwidth,16,healthcolor,Color.black,hel);

    int mp=h.getStat(RPG.ST_MPS);
    int mpm=h.getStat(RPG.ST_MPSMAX);
    float pow=((float)mp)/mpm;

    paintLabel(g,"Power:  "+mp+"/"+mpm,10,44);
    if (pow<0) pow=0;
    if (pow>1) pow=1;
    paintBar(g,10+16*charwidth,36,width-20-16*charwidth,16,powercolor,Color.black,pow);
  
    paintLabel(g,(m==null)?"Six Feet Under":m.getLevelName(),10,70);
  
    // list current attributes
    { 
      Thing[] atts = h.inv.getContents(Attribute.class);
      StringList sl = new StringList();
      for (int i=0; i<atts.length; i++) {
        sl.add(((Attribute)atts[i]).getAttributeString()); 
      }
      
      // other effects to note
      int hunger=h.getHunger();
      if (hunger>0) sl.add("Hungry");
      
      Thing w=h.getWielded(RPG.WT_MAINHAND);
      if (w==null) sl.add("Unarmed");
      
      int followers=h.inv.getContents(Being.class).length;
      if (followers>0) sl.add(Integer.toString(followers)+((followers>1)?" companions":" companion"));
      
      sl=sl.compress();
      for (int i=0; i<sl.getCount(); i++) {
        paintLabel(g,sl.getString(i),20,70+(i+1)*charheight);
      }
    }
  }
  
  public static void paintBar(Graphics g, int x, int y, int w, int h, Color f, Color b, float amount) {
    if (amount>1) amount=1;
    g.setColor(f);
    g.fillRect(x,y,(int)(w*amount),h);
    g.setColor(b);
    g.fillRect(x+(int)(w*amount),y,(int)(w*(1-amount)),h);
    paintBox(g,x,y,w,h,false);
  } 
  
  public static int paintLabel(Graphics g, String s, int x, int y) {
    g.setColor(QuestApp.textcolor);
    
    FontMetrics met=g.getFontMetrics(g.getFont());
    int charwidth=met.charWidth(' ');
    int charheight=met.getMaxAscent()+met.getMaxDescent();

    g.drawString(s,x,y+met.getMaxAscent()-charheight/2);
    
    return charwidth*s.length();
  } 

  // paint a boxed area, raised or lowered
  public static void paintBox(Graphics g, int x,int y,int w,int h, boolean raised) {
    if (raised) g.setColor(QuestApp.panelhighlight);
      else g.setColor(QuestApp.panelshadow);
      
    g.fillRect(x,y,w,boxborder);
    g.fillRect(x,y,boxborder,h);

    if (!raised) g.setColor(QuestApp.panelhighlight);
      else g.setColor(QuestApp.panelshadow);

    g.fillRect(x+1,y+h-boxborder,w-1,boxborder);
    g.fillRect(x+w-boxborder,y+1,boxborder,h-1);
  }
}