// 
// Class contining utility functions for text manipulations
//

package rl;

public class Text {
	
  // return Roman numerals
  public static String roman(int n) {
    String r="";
    switch (n/1000) {
      case 1: r=r+ "M"   ; break;
      case 2: r=r+ "MM"  ; break;
      case 3: r=r+ "MMM" ; break;
    }
    n=n%1000;
    
    switch (n/100) {
      case 1: r=r+ "C"        ; break;
      case 2: r=r+ "CC"       ; break;
      case 3: r=r+ "CCC"      ; break;
      case 4: r=r+   "CD"     ; break;
      case 5: r=r+    "D"     ; break;
      case 6: r=r+    "DC"    ; break;
      case 7: r=r+    "DCC"   ; break;
      case 8: r=r+    "DCCC"  ; break;
      case 9: r=r+       "CM" ; break;
    }
    n=n%100;

    switch (n/10) {
      case 1: r=r+ "X"        ; break;
      case 2: r=r+ "XX"       ; break;
      case 3: r=r+ "XXX"      ; break;
      case 4: r=r+   "XL"     ; break;
      case 5: r=r+    "L"     ; break;
      case 6: r=r+    "LX"    ; break;
      case 7: r=r+    "LXX"   ; break;
      case 8: r=r+    "LXXX"  ; break;
      case 9: r=r+       "XC" ; break;
    }
    n=n%10;

    switch (n) {
      case 1: r=r+ "I"        ; break;
      case 2: r=r+ "II"       ; break;
      case 3: r=r+ "III"      ; break;
      case 4: r=r+   "IV"     ; break;
      case 5: r=r+    "V"     ; break;
      case 6: r=r+    "VI"    ; break;
      case 7: r=r+    "VII"   ; break;
      case 8: r=r+    "VIII"  ; break;
      case 9: r=r+       "IX" ; break;
    }
    
    return r;
  }
  
  
	// return index of string s in array ss
  public static int index(String s, String[] ss) {
    for (int i=0; i<ss.length; i++) {
      if (s.equals(ss[i])) return i;
    }
    return -1; 
  }
  
  private static final String whitespace="                                                                                          ";
	// return whitesapce of specified length
	public static String whiteSpace(int l) {
	  if (l>0) return whitespace.substring(0,l);
    else return "";
	}
	
	// returns a+whitesapce+b with total length len
	public static String centrePad(String a, String b, int len) {
	  len=len-a.length();
	  len=len-b.length();
	  return a+whiteSpace(len)+b;
	}
			
	public static String capitalise(String s) {
		char c=s.charAt(0);
		if (Character.isUpperCase(c)) return s;
		StringBuffer sb=new StringBuffer(s);
		sb.setCharAt(0,Character.toUpperCase(c));
		return sb.toString();
	}
	
	public static int countChar(String s, char c) {
	  int count=0;
	  for (int i=0; i<s.length(); i++) {
	  	if (s.charAt(i)==c) count++;
	  }	
	  return count;
	}
	
	public static int wrapLength(String s, int start, int len) {
		if ((s.length()-start)<=len) return (s.length()-start);
		
		for (int i=len; i>=0; i--) {
			if (Character.isWhitespace(s.charAt(i+start))) return i;
		}
		
		return len;
	}
	
	public static String[] wrapString(String s, int len) {
		String[] working = new String[1+((2*s.length())/len)];
		
		int end=s.length();
		int pos=0;
		int i=0;
	  while (pos<end) {
			int inc=wrapLength(s,pos,len);
			working[i]=s.substring(pos,pos+inc);
			i++;
      pos=pos+inc;
      while ((pos<end)&&Character.isWhitespace(s.charAt(pos))) pos++;
		};
		
		// return empty string if size zero
		if (i==0) {
		  i=1;
		  working[0]=""; 	
		}
		
		String[] result = new String[i];
		System.arraycopy(working,0,result,0,i);
		return result;
	}
	
	public static int pond(String s) {
    // mildly tricky encryption hash
    // can't be bothered with anything tougher yet
    int len=s.length();
    
    int a=0;
    int f=1;
    for (int i=len-1; i>=0; i--) {
      a+=((int)s.charAt(i))*f;
      f*=31; 
    }
    
    return a;
  }
  
  public static String[] separateString(String s, char c) {
	  int num=countChar(s,c);
	  int start=0;
	  int finish=0;
	  String[] result=new String[num+1];
	  for (int i=0; i<(num+1); i++) {
	    finish=s.indexOf(c,start);
	    if (finish<start) finish=s.length();
	    if (start<finish) {
	    	result[i]=s.substring(start,finish);	
	    } else {
	      result[i]="";	
	    }
	  	start=finish+1;
	  }	
		return result;
	} 
	
	public static boolean isVowel(char c) {
		c=Character.toLowerCase(c);
		return ((c=='a')||(c=='e')||(c=='i')||(c=='o')||(c=='u')); 
	}
}