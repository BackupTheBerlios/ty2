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
		}
		eof = true;
		return null;
	}
}

