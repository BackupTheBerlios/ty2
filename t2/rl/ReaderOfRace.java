package rl;
import java.util.ArrayList;
public class ReaderOfRace extends Reader
{
	public ArrayList stats   = new ArrayList();
	public ArrayList skills  = new ArrayList();
	public ArrayList items   = new ArrayList();
	public String name       = null;

	public ReaderOfRace(String filename)
	{
		String l  = null;
		openReader(filename);
		while(!eof)
		{
			l = nextLine();
			if(l != null && l.length() > 2)
			{
				if(l.startsWith("STAT"))
					stats.add(l.substring(5));
				if(l.startsWith("ITEM"))
					items.add(l.substring(5));
				if(l.startsWith("SKILL"))
					skills.add(l.substring(6));
				if(l.startsWith("R"))
					name = l.substring(2);
			}
		}
		System.out.println(stats.toString());
		System.out.println(skills.toString());
		System.out.println(name);
	}
}

