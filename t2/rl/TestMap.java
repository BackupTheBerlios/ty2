package rl;

public class TestMap extends Map {
	
	public TestMap(int w, int h) {
	  super(w,h);
	  
	  for (int x=0; x<w; x++) for (int y=0; y<h; y++) {
	  	setTile(x,y,Tile.FLOOR);
	  }
	  
	  for (int x=0; x<w; x++) {
	    setTile(x,0,Tile.WALL);
	    setTile(x,h-1,Tile.WALL);
	  }	
		
	  for (int y=0; y<h; y++) {
	    setTile(0,y,Tile.WALL);
	    setTile(w-1,y,Tile.WALL);
	  }	
		
		for (int x=5; x<w-5; x++) {
	    setTile(x,10,Tile.WALL);
	    setTile(x,12,Tile.WALL);
		}
	}
	
	
}