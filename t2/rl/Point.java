package rl;

public final class Point implements java.io.Serializable {
  int x;
  int y; 
  
  public Point() { }
  
  public Point(int px, int py) {
    x=px;
    y=py; 
  }
  
  public Point(Thing t) {
    x=t.x;
    y=t.y; 
  }
  
  public Point(Point p) {
    x=p.x;
    y=p.y; 
  }
  
  public void offset(int dx, int dy) {
    x+=dx; y+=dy; 
  }  
}