package rl;

import java.awt.*;
import java.awt.event.*;

public class Interface extends Object {
  KeyEvent keyevent;
  boolean active;
  
  public synchronized void go(KeyEvent k) {
    if (!active) {
      keyevent=k;
      active=true;
      notifyAll();
    }
  }
  
  public synchronized void getInput() {
    keyevent=null;
    active=false;
    try {
      wait();
    } catch (InterruptedException e) {}
    active=true;
  } 
}