package rl;

public class SecretDoor extends Secret {
	public SecretDoor() {
    super((RPG.d(3)==1)?PASSAGE:DOOR); 
  }
}