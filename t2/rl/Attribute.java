package rl;

// attributes are added to inventory of mobile
// grant special abilities, modifiers, duration effects etc.
public class Attribute extends Thing {
	
	public Mobile getMobile() {
	  if (place instanceof Mobile) {
	    return (Mobile)place;	
	  }	else {
	    return null;	
	  }
	}
	
	public String getAttributeString() {return "";}
}