package rl;

public class KultRandom extends java.util.Random {
  public int nextInt(int n) {
    if (n<=0) return 0;
    
     if ((n & -n) == n)  // i.e., n is a power of 2
         return (int)((n * (long)next(31)) >> 31);

     int bits, val;
     //do {
         bits = next(31);
         val = bits % n;
     //} while(bits - val + (n-1) 0);
     return val;
   }
 }