package rl;

import java.io.*;

public class Reader
{
	private static LineNumberReader lnr  = null;
	public static boolean eof            = false;

	public static void openReader(String filename)
	{
		try
		{
			lnr = new LineNumberReader(new FileReader(filename));
		}
		catch(Exception e)
		{
			if( filename == null ){
				System.out.println("Passed a null value for filename");
			}else{
				System.out.println("Could not open file " + filename);
			}
		}
		eof = false;
	}


	public static String nextLine()
	{
		try
		{
			String t  = lnr.readLine();
			if(t == null)
				eof = true;
			return (t);
		}
		catch(Exception e)
		{
			System.out.println("Error occured while reading file " + lnr.toString());
		}
		eof = true;
		return null;
	}
}

