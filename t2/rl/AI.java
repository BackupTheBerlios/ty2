package rl;

public interface AI  {
  // state flags
  public static final int STATEFLAG_HOSTILE=1;
  public static final int STATEFLAG_PARALYSED=2;
  public static final int STATEFLAG_FEAR=4;
  public static final int STATEFLAG_SLEEPING=8;
  public static final int STATEFLAG_OCCUPIED=16;

  // ai state constants
  // Used with RPG.ST_STATE characteristic
  public static final int STATE_NEUTRAL=0;
  public static final int STATE_HOSTILE=1;
  public static final int STATE_FOLLOWER=2;
  public static final int STATE_INHABITANT=3;
  public static final int STATE_CHAOTIC=4;
   
  // notify event constats
	
  // ATTACKED event
  // ext = side attacked
  // o   = attacker  (usually Game.actor)
  public static final int EVENT_ATTACKED=1;
  
  public static final int EVENT_VISIBLE=2;
  public static final int EVENT_MORALECHECK=3;
  public static final int EVENT_FEARCHECK=4;
  public static final int EVENT_ALARM=5;
  public static final int EVENT_THEFT=6;
    // ext=id of side alarmed
  
	public boolean isHostile(Mobile m, Mobile t);
	
	public boolean doAction(Mobile m);	
  
  public void register(Mobile m);
  	
  public void unregister(Mobile m); 
  	
  public int getState(Mobile m);
  
  public int notify(Mobile m, int eventtype, int ext, Object o);
  
  public Mobile getLeader();
}