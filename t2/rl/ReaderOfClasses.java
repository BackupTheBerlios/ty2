package rl;

import java.io.*;
import java.util.*;
class ReaderOfClasses
{
	private static LineNumberReader lnr  = null;
	private static boolean eof           = false;
	public ArrayList classes             = new ArrayList();
	public ArrayList files               = new ArrayList();
	public ArrayList descs               = new ArrayList();

	public ReaderOfClasses()
	{

		String l     = null;
		String desc  = "";
		openReader("xtra/edit/classes.txt");
		while(!eof)
		{
			l = nextLine();
//			System.out.println(l);
			if(l != null && l.length() > 2)
			{
				if(l.charAt(0) == 'C')
				{
					classes.add(l.substring(2));
					if(desc.length() != 0)
					{
						descs.add(desc);
						desc = "";
					}
				}
				if(l.charAt(0) == 'F')
					files.add(l.substring(2));
				if(l.charAt(0) == 'D')
					desc = desc.concat(l.substring(2)).concat("\n");
			}
		}
		descs.add(desc);
//		System.out.println(races.toString());
//		System.out.println(files.toString());
//		System.out.println(descs.toString());
//
//		System.out.println(races.get(3));
//		System.out.println(files.get(3));
//		System.out.println(descs.get(3));
	}

	private static void openReader(String filename)
	{
		try
		{
			lnr = new LineNumberReader(new FileReader(filename));
		} catch(Exception e)
		{
		}
	}


	private static String nextLine()
	{
		try
		{
			String t  = lnr.readLine();
			if(t == null)
				eof = true;
			return (t);
		} catch(Exception e)
		{
		}
		eof = true;
		return null;
	}
}

