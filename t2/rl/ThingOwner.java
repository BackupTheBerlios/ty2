//
// Interface to descibe an object capable of containing 
// one or more instances of Thing
//
// e.g.
//  Maps
//  Beings with inventories
//  Boxes/bags
//

package rl;

public interface ThingOwner {
	
	public void removeThing(Thing thing);
	
	public Map getMap();
}