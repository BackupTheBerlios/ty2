// Represents an array of attributes
//
// A stats object is attacked to each Being to store character attributes,
// and may also be used as a handy way to store item modifiers
//
// Use getStat(index) and setStat(index,value) to modify stats
//
// The array automatically grows to accomodate new values

package rl;

public class Stats extends Object implements Cloneable, java.io.Serializable {
	// define default size for new array
	private static final int DEFAULTSIZE=5;
	private static final int DELTA=3;
	
	// fields are private to prevent any interference
	private int[] stats=new int[DEFAULTSIZE*2];
	private int size=DEFAULTSIZE;  // size of array
	private int count=0; // number of array elements used
	
	// default constructor - empty stats
	public Stats() {
	}
	
  // deep copy
  public Object clone() {
    try {
      Stats s=(Stats)super.clone();
      s.stats=(int[])s.stats.clone();
      return s;
    } catch (Exception e) {throw new Error("Stats clone error");}
  }
  
	// create Stats object with the first attributes specified
	public Stats(int[] mainstats) {
	  ensureSize(mainstats.length+DELTA);
	  count=mainstats.length;
	  for (int i=0; i<count; i++) {
	  	stats[i*2]=i;
	  	stats[i*2+1]=mainstats[i];
	  }
	}
	
	// this function makes sure that the array is large enough
	// must have room for newsize*(index,value) pairs
	private void ensureSize(int newsize) {
	  if (newsize>size) {
	    int[] newstats=new int[newsize*2];
	    System.arraycopy(stats,0,newstats,0,count*2);
	    stats=newstats;
	    size=newsize;	
	  }	
	}

	// returns the attribute with given index number
	public int getStat(int index) {
    for (int i=0; i<count; i++) {
    	if (stats[i*2]==index) return stats[i*2+1];
    }
	  return 0;
	}
	
	public void incStat(int index, int value) {
    // search the array, adding value if found
    for (int i=0; i<count; i++) {
    	if (stats[i*2]==index) {
    		stats[i*2+1]+=value; // increment
    	  return;	
    	}
    }
  	// grow the array and append new value
  	ensureSize(count+DELTA);
  	stats[count*2]=index;
  	stats[count*2+1]=value;
  	count=count+1;
	}
	
	
	// set the attribute with give index number
	public void setStat(int index, int value) {
    // search the array, updating value if found
    for (int i=0; i<count; i++) {
    	if (stats[i*2]==index) {
    		stats[i*2+1]=value;
    	  return;	
    	}
    }
  	// grow the array and append new value
  	ensureSize(count+DELTA);
  	stats[count*2]=index;
  	stats[count*2+1]=value;
  	count=count+1;
	}
}